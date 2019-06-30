import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.*;


public class webhook {

     private String accessKey = "eyJ4cC51IjoyLCJ4cC5wIjoxLCJ4cC5tIjoiTVRVMk1UWTBNalkyTlRjMk1BIiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE4NzcwMDI2NjUsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.B3ptPrCxbg06_BubE9SQUNzWvDa0iNepN7bqOD0w4C4";
 //   private String accessKey = "eyJ4cC51IjoxMzYsInhwLnAiOjIsInhwLm0iOiJNVFUwTlRZMU1EYzRNVEF4T1EiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE4NjEwMTA3ODEsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.5D_cBCiQ77GeL8_7p0m2IdFPaJr4ieGqtRtprKDv1PI";

    protected Client client = null;
    protected GridClient grid = null;
    private String host = "https://mastercloud.experitest.com";
    private String uid=System.getenv("deviceID");
 //   private String uid="CVH7N15B04005855";
    private String status="failed";

    @Before
    public void setUp()  {

        grid = new GridClient(accessKey, host);
        client = grid.lockDeviceForExecution("web hook cleanup", "@serialnumber='"+uid+"'", 10, 50000);
        client.setReporter("xml", "", "web hook cleanup");
    }

    @Test
    public void quickStartAndroidNativeDemo() {
////        client.run("adb shell pm list packages");
////        client.getInstalledApplications();
//          client.reboot(120 * 1000);
        client.launch("com.experitest.ExperiBank", true, true);
        client.elementSendText("NATIVE", "placeholder=Username", 0, "company");
        client.elementSendText("NATIVE", "placeholder=Password", 0, "company");
        client.click("NATIVE", "text=Login", 0, 1);
        client.click("NATIVE", "text=Make Payment", 0, 1);
        client.elementSendText("NATIVE", "placeholder=Phone", 0, "1234567");
        client.elementSendText("NATIVE", "placeholder=Name", 0, "Jon Snow");
        client.elementSendText("NATIVE", "placeholder=Amount", 0, "50");
        client.click("NATIVE", "placeholder=Country", 0, 1);
        client.click("NATIVE", "text=Select", 0, 1);
        client.click("NATIVE", "text=Switzerland", 0, 1);
        client.click("NATIVE", "text=Send Payment", 0, 1);
        client.click("NATIVE", "text=Yes", 0, 1);
        client.click("NATIVE", "text=Logout", 0, 1);
        status="passed";
    }

    @After
    public void tearDown() {
        client.generateReport(false);
        sendResponseToCloud();
        // Generates a report of the test case.
        // For more information - https://docs.experitest.com/display/public/SA/Report+Of+Executed+Test
        // Releases the client so that other clients can approach the agent in the near future.
//        client.releaseClient();
    }

    private void sendResponseToCloud() {
        HttpPost post = new HttpPost("https://mastercloud.experitest.com/api/v1/cleanup-finish?deviceId=" + uid + "&status=" + status);
        post.addHeader(HttpHeaders.AUTHORIZATION, "bearer " + accessKey);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {
        } catch (Exception ignore){ }
    }


}
