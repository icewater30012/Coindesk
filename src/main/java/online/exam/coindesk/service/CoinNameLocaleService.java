package online.exam.coindesk.service;

import online.exam.coindesk.dao.CoinNameLocaleDao;
import online.exam.coindesk.entity.CoinNameLocale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CoinNameLocaleService {

    @Autowired
    private CoinNameLocaleDao coinNameLocaleDao;

    public Optional<CoinNameLocale> findCoinNameLocale(final Integer i){
        return coinNameLocaleDao.findById(i);
    }
}
