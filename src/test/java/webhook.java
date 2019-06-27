
import com.experitest.client.*;
import org.junit.*;
/**
 *
 */
public class webhook {
    private String host = "https://mastercloud.experitest.com";

    private String accessKey = "eyJ4cC51IjoxMzYsInhwLnAiOjIsInhwLm0iOiJNVFUwTlRZMU1EYzRNVEF4T1EiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE4NjEwMTA3ODEsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.5D_cBCiQ77GeL8_7p0m2IdFPaJr4ieGqtRtprKDv1PI";
    protected Client client = null;
    protected GridClient grid = null;
    private String uid=System.getenv("d eviceID");
    @Before
    public void setUp(){
        // In case your user is assign to a single project you can provide an empty string,
        // otherwise please specify the project name
        grid = new GridClient(accessKey, host);
        client = grid.lockDeviceForExecution("Quick Start seetest iOS NATIVE Demo", "@serialnumber="+uid, 10, 50000);
        client.setReporter("xml", "", "Quick Start seetest iOS Native Demo");

    }

    @Test
    public void quickStartiOSNativeDemo() {
        client.install("cloud:com.experitest.ExperiBank", true, false);
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
    }

    @After
    public void tearDown(){
        // Generates a report of the test case.
        // For more information - https://docs.experitest.com/display/public/SA/Report+Of+Executed+Test
        client.generateReport(false);
        // Releases the client so that other clients can approach the agent in the near future.
        client.releaseClient();
    }
}
