package appiumtests;


import app.SimulateAndroid;
import io.appium.java_client.AppiumDriver;
import org.junit.Test;
import org.openqa.selenium.By;

import java.util.List;

/**
 * created by intellij idea.<br/>
 * user: eugene_wang<br/>
 * date: 8/3/2020<br/>
 * time: 10:15 am<br/>
 * to change this template use file | settings | file templates.
 */
public class SimulateAndroidTest {

    @Test
    public void getDriver() {
        AppiumDriver driver = SimulateAndroid.getDriver("com.ophone.reader.ui", "com.cmread.bplusc.bookshelf.LocalMainActivity", "7.1.2");
        List elements = driver.findElements(By.className("图书"));
        System.out.println(elements);
    }

}