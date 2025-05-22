import org.ws4d.coap.core.rest.CoapResourceServer;

public class CoAP_Server {
	private static CoAP_Server coapServer;
	private CoapResourceServer resourceServer;

	public static void main(String[] args) {
		coapServer = new CoAP_Server();
		coapServer.start();
	}

	public void start() {
		System.out.println("===Run Test Server ===");

		// create server
		if (this.resourceServer != null)
			this.resourceServer.stop();
		this.resourceServer = new CoapResourceServer();

		// initialize resource
		LED led = new LED();
		Temp temp = new Temp();
		Bit_price bit = new Bit_price();
		temp.setObservable(true); // temp 리소스에 observe 기능을 활성화시켜주는 코드, temp 리소스에 대해 observe 기능을 사용할 수 있게 됨
		bit.setObservable(true); // ✅ 필수 설정 추가!

		// add resource to server
		this.resourceServer.createResource(led);
		this.resourceServer.createResource(temp);
		this.resourceServer.createResource(bit);

		// run the server
		try {
			this.resourceServer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// observe 조건 - 5초에 한번씩 온도센서 값 자동 전달
		// temp 리소스를 어떻게 observe할지 정의함, 이번 시간에는 온도변화에 따라 값을 전달하는 것이 아니라, 5초에 한번씩 값만 전달하는
		// 기능을 구현해볼 것임
		while (true) {
			try {
				Thread.sleep(1000); // Thread를 사용할 때는 try/catch 문을 사용해야 함
			} catch (Exception e) {
			}
			temp.changed(); // temp를 observe하는 클라이언트에게 temp 데이터를 전송하는 기능, changed 실행 시 get메소드가 다시 실행되면서 데이터가
							// 생성되어 응답 메시지로 클라이언트에 전달
			bit.optional_changed();
		}
	}
}
