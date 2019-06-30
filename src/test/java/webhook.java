import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;


public class webhook {

    private String accessKey = "eyJ4cC51IjoyLCJ4cC5wIjoxLCJ4cC5tIjoiTVRVMk1UWTBNalkyTlRjMk1BIiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE4NzcwMDI2NjUsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.B3ptPrCxbg06_BubE9SQUNzWvDa0iNepN7bqOD0w4C4";
  //  private String accessKey = "eyJ4cC51IjoxNDAsInhwLnAiOjgyLCJ4cC5tIjoiTVRVME5qUXhOemd4TnpJd05BIiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE4NjE3Nzc4MTcsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.uYd6r4Ax5FfuemmDlb4CUF1xoXDixXcbzqRMIvJ0KT4";

    protected IOSDriver<IOSElement> driver = null;
    DesiredCapabilities dc = new DesiredCapabilities();
    private String uid=System.getenv("deviceID");
  //  private String uid="CVH7N15B04005855";
    private String status="failed";

    @Before
    public void setUp() throws MalformedURLException {

        dc.setCapability("testName", "web hook cleanup");
        dc.setCapability("accessKey", accessKey);
        dc.setCapability("deviceQuery", "@serialnumber='"+uid+"'");
      //  dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank");
        dc.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.experitest.ExperiBank");
        driver = new IOSDriver<>(new URL("https://mastercloud.experitest.com/wd/hub"), dc);
    }

    @Test
    public void quickStartAndroidNativeDemo() {
        driver.rotate(ScreenOrientation.PORTRAIT);
        driver.findElement(By.xpath("//*[@id='usernameTextField']")).sendKeys("company");
        driver.hideKeyboard();
        driver.findElement(By.xpath("//*[@id='passwordTextField']")).sendKeys("company");
        driver.findElement(By.xpath("//*[@id='loginButton']")).click();
        driver.findElement(By.xpath("//*[@id='makePaymentButton']")).click();
        driver.findElement(By.xpath("//*[@id='phoneTextField']")).sendKeys("0541234567");
        driver.findElement(By.xpath("//*[@id='nameTextField']")).sendKeys("Jon Snow");
        driver.findElement(By.xpath("//*[@id='amountTextField']")).sendKeys("50");
        driver.findElement(By.xpath("//*[@id='countryButton']")).click();
        driver.findElement(By.xpath("//*[@id='Switzerland']")).click();
        driver.findElement(By.xpath("//*[@id='sendPaymentButton']")).click();
        driver.findElement(By.xpath("//*[@id='Yes']")).click();
        status="passed";
    }

    @After
    public void tearDown() {
        sendResponseToCloud();
        System.out.println("Report URL: "+ driver.getCapabilities().getCapability("reportUrl"));
        driver.quit();
    }

    private void sendResponseToCloud() {
        HttpPost post = new HttpPost("https://mastercloud.experitest.com/api/v1/cleanup-finish?deviceId=" + uid + "&status=" + status);
        post.addHeader(HttpHeaders.AUTHORIZATION, "bearer " + accessKey);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {
        } catch (Exception ignore){ }
    }


}
