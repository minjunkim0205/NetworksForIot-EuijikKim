import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.ws4d.coap.core.enumerations.CoapMediaType;
import org.ws4d.coap.core.rest.BasicCoapResource;
import org.ws4d.coap.core.rest.CoapData;
import org.ws4d.coap.core.tools.Encoder;

public class Bit_price extends BasicCoapResource {
    private String value = "0";
    private WebDriver driver; // WebDriver를 필드로 유지

    public Bit_price() {
        super("/bitcoin", "0", CoapMediaType.text_plain);

        // 크롬드라이버 경로 설정
        System.setProperty("webdriver.chrome.driver", "./chromedriver-win64/chromedriver.exe");

        // 옵션 설정: 창 없이 실행 (headless 모드)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // 창 안 뜨게 함
        options.addArguments("--disable-gpu"); // GPU 사용 비활성화 (윈도우 최적화)
        options.addArguments("--no-sandbox");  // 리눅스 환경용 옵션

        // WebDriver 생성 (한 번만)
        this.driver = new ChromeDriver(options);
    }

    @Override
    public synchronized CoapData get(List<String> query, List<CoapMediaType> mediaTypesAccepted) {
        return get(mediaTypesAccepted);
    }

    @Override
    public synchronized CoapData get(List<CoapMediaType> mediaTypesAccepted) {
        String priceText = "0";

        try {
            // Coindesk에서 가격 가져오기
            driver.get("https://www.coindesk.com/price/bitcoin");

            Thread.sleep(3000); // 페이지 로딩 대기

            WebElement priceElement = driver.findElement(By.cssSelector("div.text-4xl.tabular-nums"));
            priceText = priceElement.getText(); // 예: "$77,786.77"

        } catch (Exception e) {
            System.out.println("에러 발생: " + e.getMessage());
        }

        this.value = priceText;
        return new CoapData(Encoder.StringToByte(this.value), CoapMediaType.text_plain);
    }
 // 시세만 가져오는 함수 (값 저장 안 함)
    private String fetchCurrentPrice() {
        String priceText = "0";

        try {
            driver.get("https://www.coindesk.com/price/bitcoin");
            Thread.sleep(3000);
            WebElement priceElement = driver.findElement(By.cssSelector("div.text-4xl.tabular-nums"));
            priceText = priceElement.getText(); // 예: "$77,786.77"
        } catch (Exception e) {
            System.out.println("시세 가져오기 실패: " + e.getMessage());
        }

        return priceText;
    }

    public synchronized void optional_changed() {
        String oldValue = this.value;
        String newValue = fetchCurrentPrice(); // 시세 가져오기

        if (!oldValue.equals(newValue)) {
            this.value = newValue;
            CoapData data = new CoapData(Encoder.StringToByte(this.value), CoapMediaType.text_plain);
            this.changed(data); // 클라이언트에 observe 응답 전송
            //this.changed();
        } else {
            System.out.println("⏳ 시세 변화 없음: " + this.value);
        }
    }

    @Override
    public synchronized boolean setValue(byte[] value) {
        this.value = Encoder.ByteToString(value);
        return true;
    }

    @Override
    public synchronized boolean post(byte[] data, CoapMediaType type) {
        return this.setValue(data);
    }

    @Override
    public synchronized boolean put(byte[] data, CoapMediaType type) {
        return this.setValue(data);
    }

    @Override
    public synchronized String getResourceType() {
        return "Bitcoin";
    }

    // 프로그램 종료 시 크롬도 종료
    public void shutdown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
