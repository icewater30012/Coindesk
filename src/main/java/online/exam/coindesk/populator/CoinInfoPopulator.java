package online.exam.coindesk.populator;

import online.exam.coindesk.dto.CoindeskDetailDTO;
import online.exam.coindesk.entity.CoinInfo;
import online.exam.coindesk.service.CoinInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CoinInfoPopulator {

    @Autowired
    private CoinInfoService coinInfoService;

    public CoinInfo populate(CoindeskDetailDTO source, Date date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        CoinInfo target = new CoinInfo();
        target.setId(BigDecimal.valueOf(coinInfoService.count()).intValue()+1);
        target.setCode(source.getCode());
        target.setName(source.getCode());
        target.setSymbol(source.getSymbol());
        target.setDescription(source.getDescription());
        target.setRate(source.getRate());
        target.setRate_float(source.getRate_float());
        target.setUpdated_time(dateFormat.format(date));
        return target;
    }
}
