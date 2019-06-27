import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.*;

import java.net.MalformedURLException;


public class webhook {

    private String accessKey = "eyJ4cC51IjoyLCJ4cC5wIjoxLCJ4cC5tIjoiTVRVMk1UWTBNalkyTlRjMk1BIiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE4NzcwMDI2NjUsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.B3ptPrCxbg06_BubE9SQUNzWvDa0iNepN7bqOD0w4C4";

    protected Client client = null;
    protected GridClient grid = null;
    private String host = "https://mastercloud.experitest.com";
    private String uid=System.getenv("deviceID");
    private String status="failed";

    @Before
    public void setUp()  {

        grid = new GridClient(accessKey, host);
        client = grid.lockDeviceForExecution("Quick Start seetest iOS NATIVE Demo", "@serialnumber='"+uid+"'", 10, 50000);
        client.setReporter("xml", "", "Quick Start seetest iOS Native Demo");
    }

    @Test
    public void quickStartAndroidNativeDemo() {
        client.run("adb shell pm list packages");
        client.getInstalledApplications();
        status="passed";
    }

    @After
    public void tearDown() {
        sendResponseToCloud();
        // Generates a report of the test case.
        // For more information - https://docs.experitest.com/display/public/SA/Report+Of+Executed+Test
    //    client.generateReport(false);
        // Releases the client so that other clients can approach the agent in the near future.
        client.releaseClient();
    }

    private void sendResponseToCloud() {
        HttpPost post = new HttpPost("https://mastercloud.experitest.com/api/v1/cleanup-finish?deviceId=" + uid + "&status=" + status);
        post.addHeader(HttpHeaders.AUTHORIZATION, "bearer " + accessKey);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {
        } catch (Exception ignore){ }
    }


}
