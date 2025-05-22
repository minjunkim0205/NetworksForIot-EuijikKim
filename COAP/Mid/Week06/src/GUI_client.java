import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.ws4d.coap.core.CoapClient;
import org.ws4d.coap.core.CoapConstants;
import org.ws4d.coap.core.connection.BasicCoapChannelManager;
import org.ws4d.coap.core.connection.api.CoapChannelManager;
import org.ws4d.coap.core.connection.api.CoapClientChannel;
import org.ws4d.coap.core.enumerations.CoapMediaType;
import org.ws4d.coap.core.enumerations.CoapRequestCode;
import org.ws4d.coap.core.messages.api.CoapRequest;
import org.ws4d.coap.core.messages.api.CoapResponse;
import org.ws4d.coap.core.rest.CoapData;
import org.ws4d.coap.core.tools.Encoder;

public class GUI_client extends JFrame implements CoapClient{
	private static final boolean exitAfterResponse = false;
	JButton btn_get = new JButton("GET");
	JButton btn_post = new JButton("POST");
	JButton btn_put = new JButton("PUT");
	JButton btn_delete = new JButton("DELETE");
	JButton btn_obsget = new JButton("Observe GET");
	
	JLabel path_label = new JLabel("Path");
	JTextArea path_text = new JTextArea("/.well-known/core", 1,1);//��ũ�ѹ� ����
	JLabel payload_label = new JLabel("Payload");
	JTextArea payload_text = new JTextArea("", 1,1);//��ũ�ѹ� ����
	JTextArea display_text = new JTextArea();
	JScrollPane display_text_jp  = new JScrollPane(display_text);
	JLabel display_label = new JLabel("Display");
	
	//Swing 컴포넌트들을 생성해주고 초기화
	CoapClientChannel clientChannel = null;
	
	public GUI_client (String serverAddress, int serverPort) {
		//���� ����
		super("IoT network GUI client");
		//���̾ƿ� ����
		this.setLayout(null);
		String sAddress = serverAddress;
		int sPort = serverPort;

		CoapChannelManager channelManager = BasicCoapChannelManager.getInstance();

		try {
			clientChannel = channelManager.connect(this, InetAddress.getByName(sAddress), sPort);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		if (null == clientChannel) {
			return;
		}

		//btn
		btn_get.setBounds(20, 670, 100, 50);
		btn_put.setBounds(130, 670, 100, 50);
		btn_post.setBounds(240, 670, 100, 50);
		btn_delete.setBounds(350, 670, 100, 50);
		btn_obsget.setBounds(460, 670, 130, 50);
		
		// 버튼을 눌렀을 때 실행되는 동작
		btn_get.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String path = path_text.getText();	//서버에서 어떤 리소스를 요청할 것인지 path에 적고 해당 값을 가져옴
				String payload = payload_text.getText();	//전달하고자 하는 데이터를 payload에 적고 해당 값을 가져옴
				CoapRequest request = clientChannel.createRequest(CoapRequestCode.GET, path, true);		//request 메시지 객체 생성(메시지 종류, path(어떤 리소스인지), con인지 non인지)
				request.setPayload(new CoapData(payload, CoapMediaType.text_plain));	//request 메시지에 페이로드를 coapdata 형식으로 넣어줌(텍스트 형식)
				displayRequest(request);
				clientChannel.sendMessage(request);
			}
		});
		
		btn_put.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String path = path_text.getText();
				String payload = payload_text.getText();
				CoapRequest request = clientChannel.createRequest(CoapRequestCode.PUT, path, true);		//put만 달라짐
				request.setPayload(new CoapData(payload, CoapMediaType.text_plain));
				displayRequest(request);
				clientChannel.sendMessage(request);
			}
		});
		
		btn_post.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String path = path_text.getText();
				String payload = payload_text.getText();
				CoapRequest request = clientChannel.createRequest(CoapRequestCode.POST, path, true);		
				request.setPayload(new CoapData(payload, CoapMediaType.text_plain));
				displayRequest(request);
				clientChannel.sendMessage(request);
			}
		});
		
		
		btn_delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String path = path_text.getText();
				String payload = payload_text.getText();
				CoapRequest request = clientChannel.createRequest(CoapRequestCode.PUT, path, true);		
				displayRequest(request);
				clientChannel.sendMessage(request);
			}
		});
		
		btn_obsget.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String path = path_text.getText();
				String payload = payload_text.getText();
				CoapRequest request = clientChannel.createRequest(CoapRequestCode.GET, path, true);		
				request.setObserveOption(0);
				displayRequest(request);
				clientChannel.sendMessage(request);
			}
		});	

		payload_label.setBounds(20, 570, 350, 30);
		payload_text.setBounds(20, 600, 440, 30);
		payload_text.setFont(new Font("arian", Font.BOLD, 15));
		
		path_label.setBounds(20, 500, 350, 30);
		path_text.setBounds(20, 530, 440, 30);
		path_text.setFont(new Font("arian", Font.BOLD, 15));
		
		display_label.setBounds(20, 10, 100, 20);
		display_text.setLineWrap(true);
		display_text.setFont(new Font("arian", Font.BOLD, 15));
		display_text_jp.setBounds(20, 40, 740, 430);
		
				
		this.add(btn_get);
		this.add(btn_post);
		this.add(btn_put);
		this.add(btn_delete);
		this.add(btn_obsget);
		this.add(path_text);
		this.add(path_label);
		this.add(payload_label);
		this.add(payload_text);
		this.add(display_text_jp);
		this.add(display_label);

		//������ ũ�� ����	
		this.setSize(800, 800);

		//������ ���̱�
		this.setVisible(true);

		//swing���� �ִ� X��ư Ŭ���� ����
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void onConnectionFailed(CoapClientChannel channel, boolean notReachable, boolean resetByServer) {
		System.out.println("Connection Failed");
		System.exit(-1);
	}

	//클라이언트가 서버로부터 회신을 받으면 어떻게 동작할 것인지 정의한 메소드(전달받은 메시지를 display에 출력해주는 동작)
	@Override
	public void onResponse(CoapClientChannel channel, CoapResponse response) {
		if (response.getPayload() != null) {
			display_text.append(
					"Response: " + response.toString() + " payload: " + Encoder.ByteToString(response.getPayload()));
			display_text.setCaretPosition(display_text.getDocument().getLength());  
		} else {
			display_text.append("Response: " + response.toString());
			display_text.setCaretPosition(display_text.getDocument().getLength());
		}
		if (GUI_client.exitAfterResponse) {
			display_text.append("===END===");
			System.exit(0);
		}
		display_text.append(System.lineSeparator());
		display_text.append("*");
		display_text.append(System.lineSeparator());
	}

	@Override
	public void onMCResponse(CoapClientChannel channel, CoapResponse response, InetAddress srcAddress, int srcPort) {
		// TODO Auto-generated method stub
	}
	
	//버튼을 눌러서 요청을 보낼 때 어떤 요청이 갔는지 display에 출력해주는 메소드
	private void displayRequest(CoapRequest request){
		if(request.getPayload() != null){
			display_text.append("Request: "+request.toString() + " payload: " + Encoder.ByteToString(request.getPayload()) + " resource: " + request.getUriPath());
			display_text.setCaretPosition(display_text.getDocument().getLength());  
		} 
		else{
			display_text.append("Request: "+request.toString() + " resource: " + request.getUriPath());
			display_text.setCaretPosition(display_text.getDocument().getLength());  
		}
		display_text.append(System.lineSeparator());
		display_text.append("*");
		display_text.append(System.lineSeparator());
	}
	

	public static void main(String[] args){
		//������ ����
		GUI_client gui = new GUI_client("127.0.0.1", CoapConstants.COAP_DEFAULT_PORT);
	}
	
	
}