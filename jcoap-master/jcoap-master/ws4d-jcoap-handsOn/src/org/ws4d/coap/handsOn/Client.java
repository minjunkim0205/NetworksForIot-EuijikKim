package org.ws4d.coap.handsOn;

import java.net.InetAddress;

import org.ws4d.coap.core.CoapClient;
import org.ws4d.coap.core.CoapConstants;
import org.ws4d.coap.core.connection.BasicCoapChannelManager;
import org.ws4d.coap.core.connection.api.CoapChannelManager;
import org.ws4d.coap.core.connection.api.CoapClientChannel;
import org.ws4d.coap.core.enumerations.CoapMediaType;
import org.ws4d.coap.core.enumerations.CoapRequestCode;
import org.ws4d.coap.core.messages.api.CoapRequest;
import org.ws4d.coap.core.messages.api.CoapResponse;

public class Client implements CoapClient {

	// TODO 4.1: Skip for now! In the 2nd step we change this to false.
	/** Shall the program terminate after the first response? */
	private boolean exitAfterResponse = true;

	/** A manager to keep track of our connections */
	private CoapChannelManager channelManager;

	/** The target where we want to send our request to */
	private CoapClientChannel clientChannel;

	/** The method containing the creation of our requests */
	public void start(String serverAddress, int serverPort) {

		/* FIXME 2.1: Get the ChannelManager instance */
//		this.channelManager = BasicCoapChannelManager.getInstance();

		this.clientChannel = null;

		try {
			/* FIXME 2.2: Create a channel to the server */
//			this.clientChannel = this.channelManager.connect(this, InetAddress.getByName(serverAddress), serverPort);

			/* Make sure that the channel is not null */
			if (this.clientChannel == null) {
				System.err.println("Connect failed: clientChannel is null!");
				System.exit(-1);
			}
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(-1);
		}

		/* Create an request and prepare parameters */

		/* FIXME 2.3: What kind of request do we want to send? */
//		CoapRequestCode requestCode = CoapRequestCode.<CTRL><SPACE>;
		
		// do we want an acknowledgement?
		boolean reliable = true;

		/* FIXME 2.4: Create the request */
//		CoapRequest request = this.clientChannel.createRequest(reliable, requestCode);

		/* FIXME 2.5: Set the resource path */
//		request.setUriPath(???);

		/* TODO 4.2: Skip for now! In the 2nd step we add the observe option here 
		 * Tip: the Parameter sequenceNumber is recommended to be 0 
		 * */
//		 request.set<CTRL><SPACE>;
		
		/* For further exploration: other interesting options */
		// request.setPayload(payload); // for PUT and POST operations you need to provide some payload
		// request.setContentType(CoapMediaType.text_plain); // to define the media type of the payload
		// request.setUriQuery(query); // query parameters; known from e.g.: http://server.domain/resource?query=parameter
		// request.addAccept(CoapMediaType.text_plain); // only accept certain media types as response; you can add multiple
		// request.setProxyUri(proxyUri); // send this request through a proxy 

		/* FIXME 2.6: Send your message */
//		this.clientChannel.sendMessage(request);
	}

	/* *************************************************************************
	 * Some callback functions that are invoked by the jCoAP-Framework
	 * ************************************************************************/

	@Override
	public void onConnectionFailed(CoapClientChannel channel, boolean notReachable, boolean resetByServer) {
		System.err.println("Connection Failed");
		System.exit(-1);
	}

	@Override
	public void onResponse(CoapClientChannel channel, CoapResponse response) {
		// We just print out the response
		if (response.getPayload() != null) {
			/* TODO 5.2: Skip for now! In the 3rd step we replace this behavior.*/
			System.out.println("Response: " + response.toString() + " Payload: " + new String(response.getPayload()));
		} else {
			System.out.println("Response: " + response.toString());
		}
		if (this.exitAfterResponse) {
			System.out.println("=== STOP  Client ===");
			System.exit(0);
		} 
	}

	/* ignore me, I'm only for multicast communication */
	@Override
	public void onMCResponse(CoapClientChannel channel, CoapResponse response, InetAddress srcAddress, int srcPort) {
		/* do nothing */
	}

	/* *************************************************************************
	 * Last but not least the main method to get things running 
	 * ************************************************************************/

	public static void main(String[] args) {
		Client coapClient = new Client();
		System.out.println("=== START Client ===");
		coapClient.start("127.0.0.1", CoapConstants.COAP_DEFAULT_PORT);
	}
}
