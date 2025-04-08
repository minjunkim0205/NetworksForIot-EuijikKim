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
		if (this.resourceServer != null)	this.resourceServer.stop();
		this.resourceServer = new CoapResourceServer();


		// initialize resource
		LED led = new LED();
		TEMP temp = new TEMP();
		temp.setObservable(true);
		
		// add resource to server
		this.resourceServer.createResource(led);
		this.resourceServer.createResource(temp);
		temp.registerServerListener(resourceServer);
		
		// run the server
		try {
			this.resourceServer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		while(true) {
			try {
				Thread.sleep(5000);
			}
			catch(Exception e) {
				// Handle excption
			}
			temp.changed();
		}
	}
}

