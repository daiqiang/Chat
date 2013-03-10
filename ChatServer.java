import java.net.*;
import java.util.*;
import java.io.*;

public class ChatServer {
	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}
}

class Server {
	private ArrayList<Client> clients = new ArrayList<Client>();
	final int PORT = 18889;

	public void start() {
		ServerSocket ss = null;

		try {
			ss = new ServerSocket(PORT);
			// started = true;
			while (true) {
				Client c = new Client(ss.accept());
				System.out.println(clients.size() + " client logined");// 试用输出
				Thread thread = new Thread(c);
				thread.start();
				clients.add(c);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void send(String str) {
		for (int i = 0; i < clients.size(); i++) {
			PrintWriter pw;
			try {
				pw = new PrintWriter(clients.get(i).getSocket()
						.getOutputStream());
				pw.println(str);
				pw.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private class Client implements Runnable {
		private Socket s = null;
		private String line = null;
		private BufferedReader br = null;

		public Socket getSocket() {
			return s;
		}

		Client(Socket s) {
			this.s = s;
			try {
				br = new BufferedReader(new InputStreamReader(
						s.getInputStream()));
				// pw = new PrintWriter(s.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			while (s.isConnected()) {
				try {
					line = br.readLine();
					if (line == null) {
						s.close();
						return;
					}
					System.out.println(line);// 输出到控制台，调试使用。
					// /for (int i = 0; i < clients.size(); i++) {
					// Client c = clients.get(i);
					send(line);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}