package app;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.<br/>
 *
 * @author: Eugene_Wang<br />
 * Date: 8/4/2020<br/>
 * Time: 2:38 PM<br/>
 * To change this template use File | Settings | File Templates.
 */
public class SimulateAndroid {
    static AppiumDriver<MobileElement> driver;
    static final long ONE_MINUTES_IN_MILLIS = 60000;

    public static AppiumDriver getDriver(String appPackage, String appActivity) {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("noReset", true);
        cap.setCapability("unicodeKeyboard", true);
        cap.setCapability("resetKeyboard", true);
        cap.setCapability("noSign", true);
        cap.setCapability("deviceName", "127.0.0.1:62001 device"); // adb devices
        cap.setCapability("platformName", "Android");
        cap.setCapability("platformVersion", "5.1.1");
        cap.setCapability("appPackage", appPackage);
        cap.setCapability("appActivity", appActivity);
        URL url = null;
        try {
            url = new URL("http://127.0.0.1:4723/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if(driver != null) {
            return driver;
        }
        driver = new AppiumDriver<MobileElement>(url, cap);
        System.out.println("Appium Virtual Device Created");
        return driver;
    }
}
