package online.exam.coindesk.dto;

public class CoindeskDetailDTO {

    private String code;
    private String rate;
    private String symbol;
    private String description;
    private Float rate_float;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
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

    public Float getRate_float() {
        return rate_float;
    }

    public void setRate_float(Float rate_float) {
        this.rate_float = rate_float;
    }
}
