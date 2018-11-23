package base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

//public class Waiters {


//    public WebElement findElement(By element) {
//        return BaseTest.getDriver().findElement(element);
//    }
//    FluentWait<WebDriver> wait = new FluentWait<>(BaseTest.getDriver())
//            .withTimeout(Duration.ofSeconds(40))
//            .pollingEvery(Duration.ofMillis(200))
//            .ignoring(NoSuchElementException.class);
//
//
//    public void goSleep(int time) {
//        try {
//            Thread.sleep(time);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//    public void waitForCertainTitle(String title) {
//
//        wait.until(ExpectedConditions.titleIs(title));
//    }
//    public void waitForPresenceOfElement(By element) {
//        wait.until(ExpectedConditions.presenceOfElementLocated(element));
//    }
//
////    public void clickWhenReady(WebElement element, int timeout) {
////
////        wait.until(ExpectedConditions.elementToBeClickable());
////        element.click();
////    }
//    public void waitUntilElementBeVisible(By element) {
//
//        wait.until(visibilityOf(findElement(element)));
//    }
//
//}