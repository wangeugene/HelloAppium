package app;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;

/**
 * Created by IntelliJ IDEA.<br/>
 * <p>
 * author: Eugene_Wang<br />
 * Date: 8/4/2020<br/>
 * Time: 2:38 PM<br/>
 * To change this template use File | Settings | File Templates.
 * // working simple demo
 * //        String appPackage = "com.android.browser";
 * //        String appActivity = "com.android.browser.BrowserActivity";
 * private static void demoFindOperation(AppiumDriver driver)throws InterruptedException{
 * System.out.println("search1");
 * String searchBox1Id="com.ophone.reader.ui:id/recom_bookstore_default_search_text";
 * driver.findElement(By.id(searchBox1Id)).click();
 * Thread.sleep(2000);
 * System.out.println("search2");
 * String searchBox2Id="com.ophone.reader.ui:id/etSearch";
 * driver.findElement(By.id(searchBox2Id)).sendKeys("天天爱阅读");
 * }
 * passed sonarLint and code inspection
 */
public class SimulateAndroid {
    static AndroidDriver<MobileElement> driver;
    static final AndroidTouchAction touchAction;

    static {
        driver = getDriver("com.ophone.reader.ui", "com.cmread.bplusc.bookshelf.LocalMainActivity", "7.1.2");
        touchAction = new AndroidTouchAction(driver);
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);//设置超时等待时间,默认250ms
    }

    private static final Logger logger = LoggerFactory.getLogger(SimulateAndroid.class);

    public static AndroidDriver<MobileElement> getDriver(String appPackage, String appActivity, String version) {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("noReset", true);
        cap.setCapability("unicodeKeyboard", true);
        cap.setCapability("resetKeyboard", true);
        cap.setCapability("noSign", true);
        cap.setCapability("deviceName", "127.0.0.1:21503"); // adb devices
        cap.setCapability("platformName", "Android");
        cap.setCapability("platformVersion", version);
        cap.setCapability("appPackage", appPackage);
        cap.setCapability("appActivity", appActivity);
        URL url;
        try {
            url = new URL("http://127.0.0.1:4723/wd/hub");
            driver = new AndroidDriver<>(url, cap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (driver != null) {
            return driver;
        }
        logger.info("Appium Virtual Device Created at={}", LocalTime.now());
        return driver;
    }

    public static void main(String[] args) throws InterruptedException {
        String skipId = "com.ophone.reader.ui:id/tv_classification";
        clickById(skipId);
        int xOffset = 484;
        int yOffset = 1218;
        delayTouch(xOffset, yOffset);
    }

    private static void clickById(String elementId) {
        int count = 0;
        while (count < 2) {
            try {
                WebElement element = driver.findElement(By.id(elementId));
                if (element != null) {
                    element.click();
                } else {
                    logger.info("clicked failed no found={} ", elementId);
                }
                count++;
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void delayTouch(int xOffset, int yOffset) throws InterruptedException {
        logger.info("touch at coordinates x={}, y={}", xOffset, yOffset);
        Thread.sleep(2000);
        touchAction.press(new ElementOption().withCoordinates(xOffset, yOffset));
    }

}
