import java.util.List;

import org.ws4d.coap.core.enumerations.CoapMediaType;
import org.ws4d.coap.core.rest.BasicCoapResource;
import org.ws4d.coap.core.rest.CoapData;
import org.ws4d.coap.core.tools.Encoder;

public class TEMP extends BasicCoapResource{
	private String value = "0";


	private TEMP(String path, String value, CoapMediaType mediaType) {
		super(path, value, mediaType);
	}

	public TEMP() {
		this("/temp", "0", CoapMediaType.text_plain);
	}

	@Override
	public synchronized CoapData get(List<String> query, List<CoapMediaType> mediaTypesAccepted) {
		return get(mediaTypesAccepted);
	}
	
	@Override
	public synchronized CoapData get(List<CoapMediaType> mediaTypesAccepted) {
        int sensingData = (int)((Math.random()*10000%10));
        this.value = Integer.toString(sensingData);
        return new CoapData(Encoder.StringToByte(this.value), CoapMediaType.text_plain);
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
		return "TEMPERATURE";
	}

}