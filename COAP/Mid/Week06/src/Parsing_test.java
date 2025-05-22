import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Parsing_test {

    public static void main(String[] args) {
        // 경로 설정
    	System.setProperty("webdriver.chrome.driver", "./chromedriver-win64/chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://coinmarketcap.com/currencies/bitcoin/");
            Thread.sleep(3000);

            WebElement price = driver.findElement(By.cssSelector("span[data-test='text-cdp-price-display']"));
            System.out.println("비트코인 시세: " + price.getText());

        } catch (Exception e) {
            System.out.println("에러 발생: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}
