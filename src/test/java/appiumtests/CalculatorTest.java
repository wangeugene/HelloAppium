package appiumtests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Eugene_Wang<br/>
 * Date: 7/30/2020<br/>
 * Time: 10:14 AM<br/>
 * To change this template use File | Settings | File Templates.
 * Failed to connect to 5.1.1 emulator
 * ANDROID_HOME set to emulator provided adb tools. not from official SDK
 * Tutorial from YOUTUBE:
 * https://www.youtube.com/watch?v=P2lM4NY4CTU&list=PLhW3qG5bs-L8npSSZD6aWdYFQ96OEduhk&index=8
 */
public class CalculatorTest {
    final static Logger log = LoggerFactory.getLogger(CalculatorTest.class.getName());
    static AppiumDriver<MobileElement> driver;
    static final long ONE_MINUTES_IN_MILLIS = 60000;

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        openMiguReader();
    }

    public static void openMiguReader() throws MalformedURLException, InterruptedException {
        // Row: 0 _id=57, name=android_id, value=500b83af3f3c4598
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("noReset", true);
        cap.setCapability("deviceName", "127.0.0.1:62001 device"); // adb devices
        cap.setCapability("platformName", "Android");
        cap.setCapability("platformVersion", "5.1.1");
        cap.setCapability("appPackage", "com.ophone.reader.ui");
        cap.setCapability("appActivity", "com.cmread.bplusc.bookshelf.LocalMainActivity");
        cap.setCapability("unicodeKeyboard", true);
        cap.setCapability("resetKeyboard", true);
        cap.setCapability("noSign", true);

        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AppiumDriver<MobileElement>(url, cap);
        System.out.println("Application Started...");
        TouchAction touchAction = new TouchAction(driver);

        skipFirstAdPage();
        homeToReadActivityPage(touchAction);
        readFromActivityPage(touchAction);
        returnToHomePage(touchAction);
        homeToReadActivityPage(touchAction);
        signedTheActivityPage(touchAction);

    }

    private static void signedTheActivityPage(TouchAction touchAction) throws InterruptedException {
        // 点击我知道了。
        touchAction.press(PointOption.point(430, 1300)).release().perform();
        Thread.sleep(2000);
        // 点击签到
        touchAction.press(PointOption.point(725, 1111)).release().perform();
        Thread.sleep(2000);
        // 点击签到 * 2
        touchAction.press(PointOption.point(725, 1111)).release().perform();
        Thread.sleep(2000);
        System.out.println("Task should be done");
    }

    private static void readFromActivityPage(TouchAction touchAction) throws InterruptedException {
        // start Reading Task
        // 点击我知道了。
        touchAction.press(PointOption.point(430, 1300)).release().perform();
        Thread.sleep(2000);
        // 点击去阅读
        touchAction.press(PointOption.point(728, 963)).release().perform();
        Thread.sleep(2000);
        // 点击一本书 //
        touchAction.press(PointOption.point(375, 1194)).release().perform();
        Thread.sleep(2000);
        // 点击免费试读
        touchAction.press(PointOption.point(623, 1527)).release().perform();
        Thread.sleep(2000);
        // 开始翻页
        enterReadingLoop(touchAction);
    }

    private static void returnToHomePage(TouchAction touchAction) throws InterruptedException {
        // center click
        touchAction.press(PointOption.point(100, 100)).release().perform();
        // <=
        touchAction.press(PointOption.point(50, 100)).release().perform();
        List<MobileElement> homeButton = driver.findElementsById("com.ophone.reader.ui:id/icon_common_home");
        while (CollectionUtils.isEmpty(homeButton)) {
            System.out.println("Home Button not found at: " + new Date().getTime());
            Thread.sleep(2000);
            List<MobileElement> leftArrowId = driver.findElementsById("com.ophone.reader.ui:id/web_back_arrowhead");
            if (!CollectionUtils.isEmpty(leftArrowId)) {
                leftArrowId.get(0).click();
            }
            homeButton = driver.findElementsById("com.ophone.reader.ui:id/icon_common_home");
        }
        homeButton.get(0).click();
    }

    private static void homeToReadActivityPage(TouchAction touchAction) throws InterruptedException {
        // touch 图书
        touchAction.press(PointOption.point(274, 1524)).release().perform();

        // 检查有没有后退箭头
        List<MobileElement> leftArrowId = driver.findElementsById("com.ophone.reader.ui:id/web_back_arrowhead");
        while(!CollectionUtils.isEmpty(leftArrowId)) {
            System.out.println("<= found by id");
            leftArrowId.get(0).click();
            // 退出到没有箭头
        }
        // touch 图书 again
        touchAction.press(PointOption.point(274, 1524)).release().perform();

        List<MobileElement> searchBox1 = driver.findElementsById("com.ophone.reader.ui:id/recom_bookstore_default_search_text");
        while (CollectionUtils.isEmpty(searchBox1)) {
            System.out.println("Waiting for search box 1 shows up at: " + new Date().getTime());
            Thread.sleep(3000);
            searchBox1 = driver.findElementsById("com.ophone.reader.ui:id/recom_bookstore_default_search_text");
            List<MobileElement> leftArrow = driver.findElementsByAccessibilityId("返回箭头");
            if (!CollectionUtils.isEmpty(leftArrow)) {
                System.out.println("<= found by access id");
                leftArrow.get(0).click();
            }
            leftArrowId = driver.findElementsById("com.ophone.reader.ui:id/titlebar_level_2_back_button");
            while(!CollectionUtils.isEmpty(leftArrowId)) {
                System.out.println("<= found by id");
                leftArrowId.get(0).click();
                // 退出到没有箭头
            }
        }

        Thread.sleep(3000);
        searchBox1.get(0).click();

        List<MobileElement> searchBox2 = driver.findElementsById("com.ophone.reader.ui:id/etSearch");
        while (CollectionUtils.isEmpty(searchBox2)) {
            Thread.sleep(3000);
            searchBox2 = driver.findElementsById("com.ophone.reader.ui:id/etSearch");
            System.out.println("Waiting for search box 2 shows up at: " + new Date().getTime());
        }

        MobileElement searchBoxElement = searchBox2.get(0);
        searchBoxElement.sendKeys("天天爱阅读");

        // Touch History
        touchAction.press(PointOption.point(200, 950)).release().perform();
        Thread.sleep(2000);
        // 点击进入天天爱阅读活动页面
        touchAction.press(PointOption.point(782, 333)).release().perform();
        Thread.sleep(2000);
        System.out.println("天天爱阅读活动界面");
    }

    private static void skipFirstAdPage() throws InterruptedException {
        List<MobileElement> elementsById = driver.findElementsById("com.ophone.reader.ui:id/tv_classification");
        if (CollectionUtils.isEmpty(elementsById)) {
            Thread.sleep(3000);
            System.out.println("skip button no found, Wait 3 seconds for ad pages loading");
        }
        // click skip ad page
        if (!CollectionUtils.isEmpty(elementsById)) {
            System.out.println("skip button found clicking ");
            MobileElement mobileElement = elementsById.get(0);
            mobileElement.click();
            Thread.sleep(1000);
        }
    }

    private static void coordinateBasedTouch(TouchAction touchAction) throws InterruptedException {
        Thread.sleep(1000);
        // click mine tab
        touchAction.press(PointOption.point(795, 1537)).release().perform();
        Thread.sleep(1000);
        System.out.println("点击我的分页");
        // click recent reading list
        touchAction.press(PointOption.point(242, 562)).release().perform();
        Thread.sleep(1000);
        System.out.println("点击我的最近阅读列表");
        // click latest book I read
        touchAction.press(PointOption.point(140, 347)).release().perform();
        Thread.sleep(1000);
        System.out.println("点击最近阅读的那一本书");
    }

    private static void enterReadingLoop(TouchAction touchAction) throws InterruptedException {
        // enter reading loop, click next page every 5 seconds
        Calendar calendar = Calendar.getInstance();
        long nowTimeInMillis = calendar.getTimeInMillis();
        Date now = calendar.getTime();
        Date date15MinutesLater = new Date(nowTimeInMillis + (15 * ONE_MINUTES_IN_MILLIS));
        while (now.before(date15MinutesLater)) {
            touchAction.press(PointOption.point(888, 888)).release().perform();
            System.out.println("turning a page at: " + new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date()));
            Thread.sleep(5000);
            now = calendar.getTime();
        }
        System.out.println("Reading Task Done at: " + new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date()));
    }
}
