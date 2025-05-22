import java.util.List;
import org.ws4d.coap.core.enumerations.CoapMediaType;
import org.ws4d.coap.core.rest.BasicCoapResource;
import org.ws4d.coap.core.rest.CoapData;
import org.ws4d.coap.core.tools.Encoder;

public class Temp extends BasicCoapResource {
	private String value = "0";

	private Temp(String path, String value, CoapMediaType mediaType) {
		super(path, value, mediaType);
	}

	public Temp() {
		this("/temp", "0", CoapMediaType.text_plain); // 클라이언트가 temp 센서에 접근할 때는 /temp라는 주소를 사용해서 접근
	}

	@Override
	public synchronized CoapData get(List<String> query, List<CoapMediaType> mediaTypesAccepted) {
		return get(mediaTypesAccepted);
	}

	@Override
	public synchronized CoapData get(List<CoapMediaType> mediaTypesAccepted) {
		int sensing_data = (int) ((Math.random() * 10000 % 10)); // 실제 온도센서를 쓰는 게 아니므로 가상의 온도센서값을 랜덤한 값(1~9)으로 생성함
		this.value = Integer.toString(sensing_data); // this.value에 위의 값을 저장함
		return new CoapData(Encoder.StringToByte(this.value), CoapMediaType.text_plain); // this.value가 인자값으로 들어가므로 get
																							// 메시지를 받았을 때 랜덤으로 생성된 값이
																							// 리소스 데이터에 포함되어 응답 메시지로 전송됨
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
		return "Temperature";
	}

}