package online.exam.coindesk.entity;

import javax.persistence.*;

@Entity
@Table
public class CoinInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String code;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coin_name_id")
    private CoinNameLocale coinNameLocale;

    @Column
    private String symbol;

    @Column
    private String description;

    @Column
    private String rate;

    @Column
    private Float rate_float;

    @Column
    private String updated_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CoinNameLocale getCoinNameLocale() {
        return coinNameLocale;
    }

    public void setCoinNameLocale(CoinNameLocale coinNameLocale) {
        this.coinNameLocale = coinNameLocale;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Float getRate_float() {
        return rate_float;
    }

    public void setRate_float(Float rate_float) {
        this.rate_float = rate_float;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }
}
