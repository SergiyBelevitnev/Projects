package base;

import com.aventstack.extentreports.ExtentTest;
import com.google.common.base.Function;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.testng.annotations.Listeners;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

@Listeners({base.Listeners.class})

public class BaseTest {

    private static WebDriver driver;

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal();
    private String suiteName;
    private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal();


//    public WebElement apply(String s) {
//        System.out.println("Checking for the element!!");
//        WebElement element = BaseTest.getDriver().findElement(By.xpath(s));
//        if(element == null)
//        {do {
//            int i =1; i++;
//            scrollToBottom((i-1)*1000, i*1000);
//        }
//        while (element == null);
//
//        }
//        return element;
//    }

    public void scrollToBottom() {
        JavascriptExecutor jse = (JavascriptExecutor) BaseTest.getDriver();

//        String xpath = "//*[@id=\"home-just-for-you-area\"]/div[2]/ul/li[2]/a/div";
//
//        WebElement Element = base.BaseTest.getDriver().findElement(By.xpath(xpath));
        jse.executeScript("window.scrollBy(0,800)");
        goSleep(1000);
    }

    @BeforeClass
    public synchronized void beforeClass(ITestContext ctx) {

        suiteName = ctx.getCurrentXmlTest().getSuite().getName();
        ExtentTest parent = ExtentManager.getInstance(suiteName).createTest(getClass().getName());
        parentTest.set(parent);

    }

    @BeforeMethod
    public void setup(Method method) throws IOException {
        //Extent report config
        ExtentTest child = parentTest.get().createNode(method.getName());
        test.set(child);
        base.Reporter.log("Method -" + method.getName() + " - is started.");
        base.Reporter.log("---------------------------------------------------------------------------------------------");
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public void selectCheckBox(String xpath) {
        WebElement element= driver.findElement(By.xpath(xpath));

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);

    }


    @Parameters({"browser"})
    @BeforeTest
    public static void LaunchBrowser(String browser) {

        if ("chrome".equalsIgnoreCase(browser)) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        } else if ("firefox".equalsIgnoreCase(browser)) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
        }

        DRIVER.set(driver);
    }

    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.manage().deleteAllCookies();
            driver.quit();
            DRIVER.remove();
        }
        ExtentManager.getInstance(suiteName).flush();

        Reporter.log("Tests PERFORMED");
    }

    public static ThreadLocal<ExtentTest> getTest() {
        return test;
    }


    public WebElement findElement(By element) {
        return BaseTest.getDriver().findElement(element);
    }

    public void moveToElement(String xpath){
        Actions action = new Actions(driver);
        WebElement element = driver.findElement(By.xpath(xpath));
        action.moveToElement(element);
        action.perform();
        this.goSleep(2000);

    }






    public void goSleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public void scrollUntilElementIsVisible(String xpath) {


        FluentWait<WebDriver> wait2 = new FluentWait<>(BaseTest.getDriver())
                .withTimeout(Duration.ofSeconds(200))
                .pollingEvery(Duration.ofMillis(1000))
                .ignoring(NoSuchElementException.class);


        Function<WebDriver, WebElement> function = new Function<WebDriver, WebElement>() {


            public WebElement apply(WebDriver arg0) {
//                Robot robot = null;
//                try {
//                    robot = new Robot();
//                } catch (AWTException e) {
//                    e.printStackTrace();
//                }
//                robot.keyPress(KeyEvent.VK_PAGE_DOWN);
//                robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
                scrollToBottom();

                base.Reporter.log("Searching for the element, scrolling");
                WebElement element = arg0.findElement(By.xpath(xpath));
                if (element != null) { base.Reporter.log("Element found");     }
                return element;}
        };
        wait2.until(function);
    }



    public void explicitWaitPresenceOfElement(String xpath){
        WebElement dynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        base.Reporter.log("Explicit wait performed");
    }


//    public void waitForCertainTitle(String title) {
//
//        wait.until(ExpectedConditions.titleIs(title));
//    }
//    public void waitForPresenceOfElement(By element) {
//        wait.until(ExpectedConditions.presenceOfElementLocated(element));
//    }

    //    public void clickWhenReady(WebElement element, int timeout) {
//
//        wait.until(ExpectedConditions.elementToBeClickable());
//        element.click();
//    }
//    public void waitUntilElementBeVisible(By element) {
//
//        wait.until(visibilityOf(findElement(element)));
//    }


//    public void waitWhileBottomElementIsPresent(By element, int timeWaitSeconds) {
//        while(wait2.until(ExpectedConditions.visibilityOf(findElement(element))))
//        {
//            int i =1; i++;
//            scrollToBottom((i-1)*1000,(i*1000));
//            goSleep(timeWaitSeconds * 1000);
//
//        }
//        do {
//            Тело цикла;
//        } while (условие выполения);
//        if (!(isElementPresent(element)) {
//            int i =1; i++;
//            scrollToBottom((i-1)*1000,(i*1000));


//        }


//        for (int i = 1; i <= numberOfTries; i++) {
//            if (!isElementPresent(element)) {
//                Reporter.log("Searching for element, scrolling to bottom");
//                scrollToBottom((i-1)*1000,(i*1000));
//                          } else {
//                Reporter.log("Element is ");
//            }
//           goSleep(timeWaitSeconds * 1000);,
//        }
//    }


//    public boolean isElementPresent(By element) {
//
//        try {
//            FluentWait<WebDriver> wait = new FluentWait<>(DRIVER)
//                    .withTimeout(Duration.ofSeconds(1));
//            wait.until(visibilityOf(findElement(element)));
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//
//    }
//
//
//}
//

    public boolean isElementPresentWithWait(By element, int timeWaitSeconds) {

        BaseTest.getDriver().manage().timeouts().implicitlyWait(timeWaitSeconds, TimeUnit.SECONDS);

        try {
            BaseTest.getDriver().findElement(element).getAttribute("value");
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            BaseTest.getDriver().manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        }
    }

    public boolean isElementPresent(By element) {

        try {
            FluentWait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(1));
            wait.until(visibilityOf(findElement(element)));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

}

