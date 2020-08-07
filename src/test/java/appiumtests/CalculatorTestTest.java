package appiumtests;


import appium.CalculatorTest;
import org.junit.Test;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * created by intellij idea.<br/>
 * user: eugene_wang<br/>
 * date: 8/3/2020<br/>
 * time: 10:15 am<br/>
 * to change this template use file | settings | file templates.
 */
public class CalculatorTestTest {

    @Test
    public void getReferenceImageB64() throws IOException, URISyntaxException {
        String referenceImageB64 = CalculatorTest.getReferenceImageB64("IGOTIT.png");
        Assert.notNull(referenceImageB64, "Image Found");
    }
}