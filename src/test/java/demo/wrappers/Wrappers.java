package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.NoSuchElementException;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */
    public static void clickOnElement(ChromeDriver driver, By locator) {
        System.out.println("clicking on About page");

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement clickableElement = driver.findElement(locator);
            clickableElement.click();

        } catch (Exception e) {
            System.out.println("Exception Occured! " + e.getMessage());

        }
    }

    // public static void clickUntilElementVisible(WebDriver driver, By xpath){
    // WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

    // boolean elementIsVisible=false;
    // while(!elementIsVisible){
    // try{
    // WebElement button = driver.findElement((xpath));
    // button.click();
    // System.out.println("button is clicked");
    // Thread.sleep(3000);
    // WebElement
    // vissibleElement=wait.until(ExpectedConditions.visibilityOfElementLocated((xpath)));

    // if(vissibleElement!=null){
    // elementIsVisible=true;
    // }
    // }catch(Exception e){
    // System.out.println("Element not visible, clicking again...");
    // }
    // }
    // System.out.println("Element is now visible");
    // }

    // public static void clickUntilElementVisible(WebDriver driver, By locator)
    // throws InterruptedException{
    // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    // while (true) {
    // try{
    // WebElement arrowButton = driver.findElement(locator);
    // if(arrowButton.isDisplayed()){
    // arrowButton.click();
    // System.out.println("button is clicked");
    // Thread.sleep(2000);
    // }
    // if (wait.until(ExpectedConditions.invisibilityOfElementLocated(locator))) {
    // System.out.println("Element is no longer visible, stopping clicks.");
    // break;
    // }
    // }catch(NoSuchElementException e){
    // System.out.println("Element not visible, stopping clicks.");
    // break;
    // }

    // }
    // }

    public static void clickUntilElementVisible(WebDriver driver, By locator, Integer maxIteration)
            throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Integer numIter = 0;
        while (numIter < maxIteration) {
            try {
                WebElement arrowButton = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

                if (arrowButton.isDisplayed()) {
                    arrowButton.click();
                    System.out.println("Button clicked in iteration " + numIter);
                    Thread.sleep(2000);
                }
                numIter++;

            } catch (NoSuchElementException e) {
                System.out.println("Element not visible, stopping clicks.");
                break;
            }
        }
    }
}
