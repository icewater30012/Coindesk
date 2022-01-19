package online.exam.coindesk;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import online.exam.coindesk.dto.CoindeskDTO;
import online.exam.coindesk.entity.CoinInfo;
import online.exam.coindesk.entity.CoinNameLocale;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest
class CoindeskApplicationTests {

    private final String url = "http://localhost:8080/";
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 1. Invoke coindesk API and print the result.
     *
     * @throws IOException
     */
    @Test
    public void coindesk_test() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url + "coindesk");
        CloseableHttpResponse httpResponse = httpclient.execute(get);
        String result = EntityUtils.toString(httpResponse.getEntity());
        CoindeskDTO coindeskDTO = objectMapper.readValue(result, CoindeskDTO.class);
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(coindeskDTO));
        assertInstanceOf(CoindeskDTO.class, coindeskDTO);
    }

    /**
     * 2. This function will invoke coindesk first then convert the response into CoinInfo,
     * which contains coin information and updated time.
     * Then store the CoinInfos into database as initialization.
     *
     * @throws IOException
     */
    @Test
    public void dataTransfer_test() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url + "transfer");
        CloseableHttpResponse httpResponse = httpclient.execute(get);
        String result = EntityUtils.toString(httpResponse.getEntity());
        List<CoinInfo> coinInfoList = objectMapper.readValue(result, new TypeReference<List<CoinInfo>>() {
        });
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(coinInfoList));
        for (CoinInfo coinInfo : coinInfoList) {
            assertInstanceOf(CoinInfo.class, coinInfo);
        }
    }

    /**
     * 3. Search all coins in database and print coin list result.
     *
     * @throws IOException
     */
    @Test
    public void searchAllCoins_test() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url + "coins");
        CloseableHttpResponse httpResponse = httpclient.execute(get);
        String result = EntityUtils.toString(httpResponse.getEntity());
        List<CoinInfo> coinInfoList = objectMapper.readValue(result, new TypeReference<List<CoinInfo>>() {
        });
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(coinInfoList));
        for (CoinInfo coinInfo : coinInfoList) {
            assertInstanceOf(CoinInfo.class, coinInfo);
        }
    }

    /**
     * 4. Search specific coin success and print the result.
     *
     * @throws IOException
     */
    @Test
    public void searchCoin_success_test() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        int id = 1;
        HttpGet get = new HttpGet(url + "coin/" + id);
        CloseableHttpResponse httpResponse = httpclient.execute(get);
        String result = EntityUtils.toString(httpResponse.getEntity());
        CoinInfo coinInfo = objectMapper.readValue(result, CoinInfo.class);
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(coinInfo));
        assertInstanceOf(CoinInfo.class, coinInfo);
    }

    /**
     * 5. Search specific coin fail and print error message.
     *
     * @throws IOException
     */
    @Test
    public void searchCoin_fail_test() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        int id = 20;
        HttpGet get = new HttpGet(url + "coin/" + id);
        CloseableHttpResponse httpResponse = httpclient.execute(get);
        String result = EntityUtils.toString(httpResponse.getEntity());
        System.out.println(result);
        assertEquals(String.format("Can't find coin with id %s", id), result);
    }

    /**
     * 6. Create a Taiwan dollar coin successfully.
     *
     * @throws IOException
     */
    @Test
    public void createCoin_success_test() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url + "coin");
        int id = 4;
        CoinInfo twCoin = new CoinInfo();
        twCoin.setId(id);
        twCoin.setCode("TWD");
        twCoin.setSymbol("$");
        twCoin.setDescription("Taiwan Dollar");
        twCoin.setRate("30,123.456");
        twCoin.setRate_float(30123.456F);
        twCoin.setUpdated_time("2022/01/18 02:31:00");
        CoinNameLocale twCoinName = new CoinNameLocale();
        twCoinName.setId(id);
        twCoinName.setName("New Taiwan Dollar");
        twCoinName.setChineseName("台幣");
        twCoin.setCoinNameLocale(twCoinName);
        StringEntity stringEntity = new StringEntity(objectMapper.writeValueAsString(twCoin), "UTF-8");
        stringEntity.setContentType("application/json");
        stringEntity.setContentEncoding("UTF-8");
        post.setEntity(stringEntity);
        CloseableHttpResponse httpResponse = httpclient.execute(post);
        String result = EntityUtils.toString(httpResponse.getEntity());
        assertEquals(String.format("Create Coin with id %s successfully", id), result);
    }

    /**
     * 7. If there exist TWD in database, it will return error message if trying to
     * create a coin with exist coin code but different id.
     *
     * @throws IOException
     */
    @Test
    public void createCoin_fail_test() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url + "coin");
        int id = 5;
        CoinInfo twCoin = new CoinInfo();
        twCoin.setId(id);
        twCoin.setCode("TWD");
        twCoin.setSymbol("$");
        twCoin.setDescription("Taiwan Dollar");
        twCoin.setRate("30,123.456");
        twCoin.setRate_float(30123.456F);
        twCoin.setUpdated_time("2022/01/18 02:31:00");
        CoinNameLocale twCoinName = new CoinNameLocale();
        twCoinName.setId(id);
        twCoinName.setName("New Taiwan Dollar");
        twCoinName.setChineseName("台幣");
        twCoin.setCoinNameLocale(twCoinName);
        StringEntity stringEntity = new StringEntity(objectMapper.writeValueAsString(twCoin), "UTF-8");
        stringEntity.setContentType("application/json");
        stringEntity.setContentEncoding("UTF-8");
        post.setEntity(stringEntity);
        CloseableHttpResponse httpResponse = httpclient.execute(post);
        String result = EntityUtils.toString(httpResponse.getEntity());
        assertEquals("Duplicate code whiling create/update coin.", result);
    }

    /**
     * 8. If there exist a coin with specific id and code in database,
     * it will updated the information of existed coin if trying to create a coin with same specific id and code.
     *
     * @throws IOException
     */
    @Test
    public void updateCoin_test() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url + "coin");
        int id = 4;
        CoinInfo twCoin = new CoinInfo();
        twCoin.setId(id);
        twCoin.setCode("TWD");
        twCoin.setSymbol("$$");
        twCoin.setDescription("New Taiwan Dollar");
        twCoin.setRate("23,456.789");
        twCoin.setRate_float(23456.789F);
        twCoin.setUpdated_time("2022/01/18 02:31:00");
        CoinNameLocale twCoinName = new CoinNameLocale();
        twCoinName.setId(id);
        twCoinName.setName("New Taiwan Dollar");
        twCoinName.setChineseName("台幣");
        twCoin.setCoinNameLocale(twCoinName);
        StringEntity stringEntity = new StringEntity(objectMapper.writeValueAsString(twCoin), "UTF-8");
        stringEntity.setContentType("application/json");
        stringEntity.setContentEncoding("UTF-8");
        post.setEntity(stringEntity);
        CloseableHttpResponse httpResponse = httpclient.execute(post);
        String result = EntityUtils.toString(httpResponse.getEntity());
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(twCoin));
        assertEquals(String.format("Update Coin %s successfully", id), result);
    }

    /**
     * 9. Delete specific coin and print successful message.
     *
     * @throws IOException
     */
    @Test
    public void deleteCoin_test() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        Integer id = 2;
        HttpDelete get = new HttpDelete(url + "coin/" + id);
        CloseableHttpResponse httpResponse = httpclient.execute(get);
        String result = EntityUtils.toString(httpResponse.getEntity());
        assertEquals(String.format("Delete Coin %s successfully", id), result);
    }

    /**
     * 10. Delete all coins and print successful message.
     *
     * @throws IOException
     */
    @Test
    public void deleteAllCoins_test() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpDelete get = new HttpDelete(url + "coins/");
        CloseableHttpResponse httpResponse = httpclient.execute(get);
        String result = EntityUtils.toString(httpResponse.getEntity());
        assertEquals("Delete all Coins successfully", result);
    }
}
