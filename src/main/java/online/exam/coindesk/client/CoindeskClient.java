package online.exam.coindesk.client;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class CoindeskClient {

    private static final Logger logger = LoggerFactory.getLogger(CoindeskClient.class);

    public String getCoindeskDto(final String url){

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        String result = Strings.EMPTY;
        try {
            CloseableHttpResponse httpResponse = httpclient.execute(get);
            result = EntityUtils.toString(httpResponse.getEntity());

        }
        catch (IOException e){
            logger.error(String.format("IOException occur while invoking %s.  %s", url, e));
        }
        return result;
    }
}
