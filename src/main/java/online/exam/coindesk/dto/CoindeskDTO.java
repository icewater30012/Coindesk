package online.exam.coindesk.dto;

import java.util.Map;

public class CoindeskDTO {

    private CoindeskTimeDTO time;
    private String disclaimer;
    private String chartName;
    private Map<String, CoindeskDetailDTO> bpi;

    public CoindeskTimeDTO getTime() {
        return time;
    }

    public void setTime(CoindeskTimeDTO time) {
        this.time = time;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public Map<String, CoindeskDetailDTO> getBpi() {
        return bpi;
    }

    public void setBpi(Map<String, CoindeskDetailDTO> bpi) {
        this.bpi = bpi;
    }
}
