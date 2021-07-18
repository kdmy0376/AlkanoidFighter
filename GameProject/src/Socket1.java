import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Socket1 
{
	InetAddress inetAddr = null;
	Socket socket = null;
	
	public Socket1(Object obj, String ipAddress)
	{
		GameFrame gFrame = (GameFrame)obj;
		try
		{
			inetAddr = InetAddress.getByName(ipAddress);
			socket = new Socket(inetAddr, 10000);
			
			JOptionPane.showMessageDialog(gFrame, "���� �����Ͽ����ϴ�.",
					"����", JOptionPane.PLAIN_MESSAGE);
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(gFrame, "���� ���� �Ұ�",
						"����", JOptionPane.PLAIN_MESSAGE);			
		}
		
	}
}
