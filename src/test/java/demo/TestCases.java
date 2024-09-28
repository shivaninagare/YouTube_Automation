package demo;

//import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.Assert;
import java.time.Duration;
import java.util.logging.Level;
import java.util.List;
import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider { // Lets us read the data
        static ChromeDriver driver;

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */

        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */

        @Test
        public static void testCase01() throws InterruptedException{
        driver.get("https://www.youtube.com/");
        Thread.sleep(3000);
        String currentURL= driver.getCurrentUrl();
        Assert.assertEquals(currentURL,"https://www.youtube.com/","The URL is not\r\n" + //
                                "        correct");
        Wrappers.clickOnElement(driver,By.xpath("//a[text()='About']"));
        Thread.sleep(1000);
        System.out.println("clicked on element");

        List<WebElement> aboutText =
        driver.findElements(By.xpath("//p[contains(@class,'lb-font-color-text-primary')]"));
        for(WebElement element: aboutText){
        System.out.println(element.getText());
        }

        }

        @Test
        public static void testCase02() throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        driver.get("https://www.youtube.com/");
        Thread.sleep(3000);
        WebElement moviesElement =
        driver.findElement(By.xpath("//yt-formatted-string[text()='Movies']"));
        ((JavascriptExecutor)
        driver).executeScript("arguments[0].scrollIntoView(true);", moviesElement);
        moviesElement.click();
        Thread.sleep(5000);

        Wrappers.clickUntilElementVisible(driver, By.xpath("//span[contains(text(),\"Top selling\")]/ancestor::div[@id='dismissible']//div[@id='right-arrow']//button"),
        3);
        Thread.sleep(5000);
        WebElement moviewTypeElement = driver
        .findElement(By.xpath("(//*[@id='items']/ytd-grid-movie-renderer[16]/a/span)[1]"));
        String movieText = moviewTypeElement.getText();
        movieText = movieText.replaceAll("[^a-zA-Z]", "").trim();
        System.out.println("Movie Category: " + movieText);

        // Soft assert to check if the movie falls under "Animation", "Comedy", or
        // "Drama"
        softAssert.assertTrue(
        movieText.contains("Animation") || movieText.contains("Comedy")
        || movieText.contains("Drama"),
        "Movie category not found: " + movieText);

        // Check if the movie is marked as Mature (A)
        WebElement movieMarkElement = driver.findElement(By.xpath(
        "//*[@id=\"items\"]/ytd-grid-movie-renderer[16]/ytd-badge-supported-renderer/div[2]/p"));
        String markText = movieMarkElement.getText().trim();
        System.out.println("Maturity Mark: " + markText);
        if (markText.contains("U")) {
        softAssert.assertTrue(markText.contains("U"),
        "Movie is not marked as Mature (A), instead found: U ");
        } else {
        softAssert.fail("Movie is marked as Mature (A) " + markText);
        }

        // Assert all soft assertions
        softAssert.assertAll();
        }

        @Test
        public static void testCase03() throws InterruptedException {
                SoftAssert softAssert = new SoftAssert();
                driver.get("https://www.youtube.com/");
                Thread.sleep(3000);
                WebElement musicElement = driver.findElement(By.xpath("//yt-formatted-string[text()='Music']"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", musicElement);
                musicElement.click();
                Thread.sleep(5000);

                WebElement indiaElement = driver
                                .findElement(By.xpath("//span[text()=concat(\"India\", \"'s Biggest Hits\")]"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", indiaElement);

                Wrappers.clickUntilElementVisible(driver, By.xpath(
                                "//span[contains(text(),\"India's Biggest Hits\")]/ancestor::div[@id='dismissible']//div[@id='right-arrow']//button"),
                                3);

                Thread.sleep(5000);

                WebElement headerElement = driver.findElement(By.xpath(
                                "//span[contains(text(),\"India's Biggest Hits\")]/ancestor::div[@id='dismissible']//h3[normalize-space(text())='Bollywood Dance Hitlist']\r\n"
                                                + //
                                                ""));
                String headerText = headerElement.getText();
                System.out.println("header of music element is : " + headerText);

                WebElement trackElement = driver.findElement(By.xpath(
                                "//span[contains(text(),\"India's Biggest Hits\")]/ancestor::div[@id='dismissible']//h3[normalize-space(text())='Bollywood Dance Hitlist']/following::p[@id='video-count-text'][1]"));
                String TrackText = trackElement.getText();
                TrackText = TrackText.replaceAll("[^0-9]", "").trim();
                int trackCount = Integer.parseInt(TrackText);
                softAssert.assertTrue(trackCount <= 50, "Track count is greater than 50.");

                // Print the number of tracks for debugging
                System.out.println("Number of tracks listed: " + trackCount);

                // Finalize soft assertions
                softAssert.assertAll();

        }

        @Test
        public static void testCase04() throws InterruptedException {
                // SoftAssert softAssert = new SoftAssert();
                driver.get("https://www.youtube.com/");
                Thread.sleep(3000);
                WebElement newsElement = driver.findElement(By.xpath("//yt-formatted-string[text()='News']"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", newsElement);
                newsElement.click();
                Thread.sleep(5000);
                ((JavascriptExecutor) driver).executeScript("document.charset = 'UTF-8';");

                List<WebElement> latestNewsElements = driver.findElements(By.xpath(
                                "//span[contains(text(),\"Latest news posts\")]/ancestor::div[@id='dismissible']//div[@id='contents']/ytd-rich-item-renderer"));
                int totalLikes = 0;
                for (int i = 0; i < 3 && i < latestNewsElements.size(); i++) {
                        WebElement newsPost = latestNewsElements.get(i);
                        // List<WebElement> title =
                        // driver.findElements(By.xpath(".//div[@id='author']"));
                        // List<WebElement> body =
                        // driver.findElements(By.xpath(".//yt-formatted-string[@id='home-content-text']"));
                        // List<WebElement> count =
                        // driver.findElements(By.xpath(".//span[@id='vote-count-middle']"));
                        String title = newsPost.findElement(By.xpath(".//div[@id='author']")).getText(); // Adjusted
                                                                                                         // XPath for
                                                                                                         // the title
                        String body = newsPost.findElement(By.xpath(".//yt-formatted-string[@id='home-content-text']"))
                                        .getText(); // Adjusted XPath for the body

                        System.out.println("Title of news post " + (i + 1) + ": " + title);
                        System.out.println("Body of news post " + (i + 1) + ": " + body);

                        // Extract the number of likes using relative XPath from the newsPost
                        String likesText = newsPost.findElement(By.xpath(".//span[@id='vote-count-middle']")).getText(); // Adjusted

                        int likes = 0;
                        if (!likesText.isEmpty()) {
                                try {
                                        likes = Integer.parseInt(likesText.replaceAll("[^0-9]", "").trim());
                                } catch (NumberFormatException e) {
                                        likes = 0; // If parsing fails, assume 0 likes
                                }
                        }

                        // Add the current post's likes to the totalLikes
                        totalLikes += likes;

                        // Print the likes for the current iteration
                        System.out.println("Likes for news post " + (i + 1) + ": " + likes);
                }
                System.out.println("Total likes for the first three news posts: " + totalLikes);
        }

        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
        }

        // @AfterTest
        // public void endTest() {
        // driver.close();
        // driver.quit();

        // }
}