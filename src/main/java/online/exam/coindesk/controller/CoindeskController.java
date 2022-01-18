package online.exam.coindesk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import online.exam.coindesk.client.CoindeskClient;
import online.exam.coindesk.dto.CoindeskDTO;
import online.exam.coindesk.dto.CoindeskDetailDTO;
import online.exam.coindesk.entity.CoinInfo;
import online.exam.coindesk.populator.CoinInfoPopulator;
import online.exam.coindesk.service.CoinInfoService;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;


@RestController
public class CoindeskController {

    private static final Logger logger = LoggerFactory.getLogger(CoindeskController.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String COINDESK_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";

    @Autowired
    private CoinInfoService coinInfoService;

    @Autowired
    private CoinInfoPopulator coinInfoPopulator;

    @GetMapping("/coindesk")
    public CoindeskDTO invokeCoindesk() throws IOException{
        CoindeskClient client  = new CoindeskClient();
        CoindeskDTO coindeskDTO = objectMapper.readValue(client.getCoindeskDto(COINDESK_URL), CoindeskDTO.class);
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(coindeskDTO));

        return coindeskDTO;
    }

    @GetMapping("/transfer")
    public List<CoinInfo> transfer() throws IOException, ParseException {
        CoindeskDTO coindeskDTO = invokeCoindesk();
        Calendar calendar = DatatypeConverter.parseDateTime(coindeskDTO.getTime().getUpdatedISO());
        List<CoinInfo> coinInfoList = new ArrayList<>();
        for (Map.Entry<String, CoindeskDetailDTO> coinInfoEntry : coindeskDTO.getBpi().entrySet()){
            CoinInfo coinInfo = coinInfoPopulator.populate(coinInfoEntry.getValue(), calendar.getTime());
            coinInfoList.add(coinInfo);
            CreateOrUpdateCoin(coinInfo);
        }
        return coinInfoList;
    }

    @PostMapping("/coin")
    public String CreateOrUpdateCoin(@RequestBody CoinInfo coinInfo) {

        Optional<CoinInfo> oCoinInfo = coinInfoService.findCoin(coinInfo.getId());
        try {
            coinInfoService.createCoin(coinInfo);
        }
        catch(Exception e){
            String message = "Duplicate code whiling create/update coin.";
            logger.error(message, e);
            return message;
        }

        return oCoinInfo.isPresent() ? String.format("Update Coin %s successfully", coinInfo.getId())
                : String.format("Create Coin with id %s successfully", coinInfo.getId());
    }

    @GetMapping("/coin/{id}")
    public String searchCoin(@PathVariable("id") Integer id) {
        Optional<CoinInfo> oCoinInfo = coinInfoService.findCoin(id);
        String result = Strings.EMPTY;
        try {
            result = oCoinInfo.isPresent() ? objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(oCoinInfo.get())
                    : String.format("Can't find coin with id %s", id);
        }
        catch (JsonProcessingException e){
            logger.error(String.format("Exception occur when parsing coin with id %s", id), e);
        }
        return result;
    }

    @GetMapping("/coins")
    public List<CoinInfo> searchAllCoins() {
        return coinInfoService.findAllCoins();
    }

    @DeleteMapping("/coin/{id}")
    public String deleteCoin(@PathVariable("id") Integer id) {

        Optional<CoinInfo> oCoinInfo = coinInfoService.findCoin(id);
        if(oCoinInfo.isPresent()){
            oCoinInfo.ifPresent(coinInfo -> coinInfoService.deleteCoin(coinInfo));
        }
        else{
            return String.format("Can't find Coin with id %s", id);
        }
        return String.format("Delete Coin %s successfully", id);
    }

    @DeleteMapping("/coins")
    public String deleteAllCoins() {

        coinInfoService.deleteAllCoins();
        return "Delete all Coins successfully";
    }
}
