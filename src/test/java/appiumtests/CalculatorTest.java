package appiumtests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

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
 *
 */
public class CalculatorTest {
    static AppiumDriver<MobileElement> driver;

    public static void main(String[] args) throws MalformedURLException {
        openMiguReader();
    }

    public static void openMiguReader() throws MalformedURLException {
        // Row: 0 _id=57, name=android_id, value=500b83af3f3c4598
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("deviceName", "127.0.0.1:62001 device"); // adb devices
        cap.setCapability("platformName", "Android");
        cap.setCapability("platformVersion", "5.1.1");
        cap.setCapability("appPackage", "com.ophone.reader.ui");
        cap.setCapability("appActivity", "com.cmread.bplusc.bookshelf.LocalMainActivity");

        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AppiumDriver<MobileElement>(url, cap);
        System.out.println("Application Started...");

    }
}
