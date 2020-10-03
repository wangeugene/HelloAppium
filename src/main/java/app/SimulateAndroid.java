package app;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.slf4j.LoggerFactory.getLogger;

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

    private static final Logger logger = getLogger(SimulateAndroid.class);

    public static void main(String[] args) throws InterruptedException {
        AndroidDriver<MobileElement> driver = getDriver("com.ophone.reader.ui", "com.cmread.bplusc.bookshelf.LocalMainActivity", "7.1.2");
        if (driver == null) {
            return;
        }
        AndroidTouchAction touchAction = new AndroidTouchAction(driver);
        clickById("com.ophone.reader.ui:id/tv_classification", driver);
        try {
            MobileElement backArrowElement = driver.findElement(By.id("com.ophone.reader.ui:id/titlebar_level_2_back_button"));
            if (backArrowElement != null) {
                logger.info("backArrowElement={}", backArrowElement);
                backArrowElement.click();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        delayTouch("天天爱阅读", 568, 1206, 5000, touchAction);
        delayTouch("我知道了", 520, 1492, 5000, touchAction);
        delayTouch("去阅读", 876, 1188, 5000, touchAction);
        delayTouch("正在读的一本书", 169, 1557, 5000, touchAction);
        Instant now = Instant.now();
        Instant nowPlus15minsEpoch = now.plus(15, ChronoUnit.MINUTES);
        while (now.isBefore(nowPlus15minsEpoch)) {
            now = Instant.now();
            delayTouch("翻页", 1000, 1210, 5000, touchAction);
        }
        delayTouch("签到", 867, 1371, 5000, touchAction);
    }

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
        AndroidDriver<MobileElement> driver = null;
        try {
            url = new URL("http://127.0.0.1:4723/wd/hub");
            driver = new AndroidDriver<>(url, cap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (driver != null) {
            logger.info("Appium Virtual Device Created at={}", LocalTime.now());
            return driver;
        } else {
            return null;
        }
    }


    private static void clickById(String elementId, AndroidDriver<MobileElement> driver) {
        try {
            MobileElement element = driver.findElement(By.id(elementId));
            if (element != null) {
                logger.info("clicked skip found={} ", elementId);
                element.click();
            } else {
                logger.info("skip failed no found={} ", elementId);
            }
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void delayTouch(String msg, int xOffset, int yOffset, int delayMilliseconds, AndroidTouchAction action) throws InterruptedException {
        logger.info("click {} touch at coordinates x={}, y={}", msg, xOffset, yOffset);
        Thread.sleep(delayMilliseconds);
        action.tap(PointOption.point(xOffset, yOffset)).release().perform();
    }

}
