package online.exam.coindesk.populator;

import online.exam.coindesk.dto.CoindeskDetailDTO;
import online.exam.coindesk.entity.CoinInfo;
import online.exam.coindesk.service.CoinInfoService;
import online.exam.coindesk.service.CoinNameLocaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CoinInfoPopulator {

    @Autowired
    private CoinInfoService coinInfoService;

    @Autowired
    private CoinNameLocaleService coinNameLocaleService;

    public CoinInfo populate(CoindeskDetailDTO source, Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        CoinInfo target = new CoinInfo();
        Integer id = BigDecimal.valueOf(coinInfoService.count()).intValue() + 1;
        target.setId(id);
        target.setCode(source.getCode());
        target.setSymbol(source.getSymbol());
        target.setDescription(source.getDescription());
        target.setRate(source.getRate());
        target.setRate_float(source.getRate_float());
        target.setUpdated_time(dateFormat.format(date));
        coinNameLocaleService.findCoinNameLocale(id).ifPresent(target::setCoinNameLocale);
        return target;
    }
}
