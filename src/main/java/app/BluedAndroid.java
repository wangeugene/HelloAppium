package app;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
 * powershell
 * gci env:* | sort-object name
 * https://account.jetbrains.com/a/p8qpblcx
 * <p>
 * com.soft.blued:id/tv_areacode
 * com.soft.blued:id/edit_lay
 * com.soft.blued:id/search_edt
 * com.soft.blued:id/search_view
 * com.soft.blued:id/tv_sign_up
 * com.soft.blued:id/cb_terms
 * com.soft.blued:id/tv_to_register
 * <p>
 * {
 * "platformName": "android",
 * "appActivity": "com.soft.blued.ui.welcome.FirstActivity",
 * "appPackage": "com.soft.blued",
 * "deviceName": "127.0.0.1:21503",
 * "noReset": true,
 * "noSign": true,
 * "platformVersion": "7.1.2",
 * "resetKeyboard": true,
 * "unicodeKeyboard": true,
 * "deviceUDID": "127.0.0.1:21503",
 * "deviceApiLevel": 25,
 * "deviceScreenSize": "1080x1920",
 * "deviceScreenDensity": 360,
 * "deviceModel": "PCRT00",
 * "deviceManufacturer": "OPPO"
 * }
 * adb devicecs
 * adb shell
 * ipconfig
 * ip route
 * 192.168.123.207
 */
public class BluedAndroid {

    private static final Logger logger = getLogger(BluedAndroid.class);
    public static final int APPIUM_PORT = 4237;
    public static final int EMULATOR_PORT = 25001;
    private static final Random random = new Random();
    private static boolean initialState = true;
    private static Robot robot;
    private static AndroidTouchAction touchAction;
    private static AndroidDriver<MobileElement> driver;
    private static String deviceName;
    private static String version;
    private static String appActivity;

    /**
     * @param
     * @throws InterruptedException
     * @throws IOException          start Appium and MeMu before running this
     */
    public static void main(String[] args) throws InterruptedException, IOException, AWTException {
        tryDailyTasks();
    }

    private static void tryDailyTasks() throws AWTException, IOException, InterruptedException {
        if (!checkIfServerIsRunning(EMULATOR_PORT)) {
            startAndroidEmulator();
        }
        if (!checkIfServerIsRunning(APPIUM_PORT)) {
            startAppiumServer();
        }
        String registerPhone = "19965412403";
        String receiveSMSValidCode = "199101";
        setEmulator();
        Runtime.getRuntime().exec("adb connect " + deviceName);
        AndroidDriver<MobileElement> driver = getDriver("com.soft.blued", appActivity, version);
        if (driver == null) {
            logger.info("driver={} empty", driver);
            return;
        } else {
            logger.info("driver={} found", driver);
        }
        touchAction = new AndroidTouchAction(driver);
        Thread.sleep(5000);
//        driver.findElementById("com.soft.blued:id/tv_positive_ordinary").click();
        Thread.sleep(2000);
        driver.findElementById("com.soft.blued:id/tv_sign_up").click();
        Thread.sleep(1000);
//        driver.findElementById("com.soft.blued:id/tv_areacode").click();
//        Thread.sleep(1000);
//        driver.findElementById("com.soft.blued:id/edit_lay").click();
//        Thread.sleep(1000);
//        driver.findElementById("com.soft.blued:id/search_edt").sendKeys("China");
//        Thread.sleep(1000);
//        touch("[0,178][946,299]"); // touch +86
//        Thread.sleep(3000);
        List<MobileElement> elementsByClassName = driver.findElementsByClassName("android.widget.EditText");
        elementsByClassName.get(0).sendKeys(registerPhone);
        elementsByClassName.get(1).sendKeys("Public@pass1");
        Thread.sleep(500);
        driver.findElementById("com.soft.blued:id/cb_terms").click();
        Thread.sleep(5000);
        System.out.println("给你5秒拖动滑块到位置");
        driver.findElementById("com.soft.blued:id/tv_to_register").click();
        Thread.sleep(1000);
        System.out.println("给你7秒输入验证码");
        Thread.sleep(7000);
        driver.findElementById("com.soft.blued:id/inputView").click();
        Thread.sleep(1000);
        for (int i = 0; i < 6; i++) {
            driver.findElementById("com.soft.blued:id/inputView").sendKeys(String.valueOf(receiveSMSValidCode.charAt(i)));
            Thread.sleep(1000);
        }
//        driver.findElementById("com.soft.blued:id/tv_confirm").click();
        Thread.sleep(6000000);
        System.out.println("DONE");

    }

    /**
     * start MEMU if port is not in used
     */
    public static void startAndroidEmulator() throws AWTException {
        robot = new Robot();
        robot.keyPress(java.awt.event.KeyEvent.VK_WINDOWS);
        robot.keyRelease(java.awt.event.KeyEvent.VK_WINDOWS);
        robotSendString("MEMU");
    }

    private static void setEmulator() {
        deviceName = "127.0.0.1:21503"; // Emulator
        version = "7.1.2";
        appActivity = "com.soft.blued.ui.welcome.FirstActivity";
    }

    public static void setHuaWeiPhone() throws AWTException {
        deviceName = "MLCBB20901213542"; // Emulator
        version = "10";
        appActivity = "com.soft.blued.ui.home.HomeActivity";
    }

    private static void robotSendString(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);
        robot.keyPress(java.awt.event.KeyEvent.VK_CONTROL);
        robot.keyPress(java.awt.event.KeyEvent.VK_V);
        robot.keyRelease(java.awt.event.KeyEvent.VK_V);
        robot.keyRelease(java.awt.event.KeyEvent.VK_CONTROL);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.keyPress(java.awt.event.KeyEvent.VK_ENTER);
        robot.keyRelease(java.awt.event.KeyEvent.VK_ENTER);
    }

    /**
     * start the default appium server
     * just like running appium in CLI (powershell)
     * or start appium desktop server by clicking the UI
     * it automatically enables the debug level log
     */
    public static void startAppiumServer() {
        AppiumDriverLocalService server = AppiumDriverLocalService.buildDefaultService();
        server.start();
    }

    public static AndroidDriver<MobileElement> getDriver(String appPackage, String appActivity, String version) {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("noReset", true);
        cap.setCapability("unicodeKeyboard", true);
        cap.setCapability("resetKeyboard", true);
        cap.setCapability("noSign", true);
        cap.setCapability("deviceName", deviceName); // adb devices
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
            logger.info("Appium Virtual Device Created at={}", LocalTime.now());
            return driver;
        } else {
            return null;
        }
    }

    public static boolean checkIfServerIsRunning(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)
        ) {
            logger.info("serverSocket is  available!");
            return false;
        } catch (IOException e) {
            logger.info("serverSocket is not available!");
            return true;
        }
    }

    /**
     * @param touchMatrix
     * @throws InterruptedException suitable for the format for appium desktop
     *                              [946,178][1080,299]
     */
    private static void touch(String touchMatrix) throws InterruptedException {
        String spaceString = touchMatrix.replaceAll("\\[|\\]|\\,", " ");
        List<Integer> list = Arrays.stream(spaceString.split("\\s+"))
                .filter(Predicate.not(StringUtils::isEmpty))
                .map(v -> v.trim())
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        System.out.println(list);
        // find the center of the matrix
        int xOffset = (list.get(0) + list.get(2)) / 2;
        int yOffset = (list.get(1) + list.get(3)) / 2;
        System.out.println(xOffset + "  :  " + yOffset);
        logger.info("touch at coordinates x={}, y={}", xOffset, yOffset);
        System.out.println("touch at coordinates x={}:" + xOffset + "  " + yOffset);
        touchAction.tap(PointOption.point(xOffset, yOffset)).release().perform();
    }

//    public static void main(String[] args) throws InterruptedException {
//        touch("[946,178][1080,299]");
//    }

}

