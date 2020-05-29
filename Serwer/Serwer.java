import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class Serwer {

	private ArrayList klientArrayList; // lista klient�w
	PrintWriter printWriter; 

	// uruchomienie programu
	public static void main(String[] argsv) {
		Serwer s = new Serwer();
		s.startSerwer();

	}

	// uruchomienie serwera
	public void startSerwer() { 
		klientArrayList = new ArrayList();

		try { // nas�uchiwanie i odbieranie komunikatu od klienta

			ServerSocket serverSocket = new ServerSocket(4500); // uruchomienie us�ugi nas�uchiwania na porcie 4500

			while (true) { // serwer dzia�a ca�y czas
				Socket socket = serverSocket.accept(); // akceptowanie wszystkich po��cze� przychodzacych na port
				String klientAddress = socket.getInetAddress().getHostAddress();
				System.out.println("Nowe po��czenie przychodz�ce o adresie: " + klientAddress);
				printWriter = new PrintWriter(socket.getOutputStream()); 
																			
				klientArrayList.add(printWriter); // dodanie klienta do listy

				
				Thread t = new Thread(new SerwerKlient(socket));
				t.start();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// w�tek rozsy�aj�cy wiadomo�ci do klient�w
	class SerwerKlient implements Runnable {

		
		Socket socket; 
		BufferedReader bufferedReader; 

		
		public SerwerKlient(Socket socketKlient) { // odczyt wej�cia
			try {
				System.out.println("Po��czony.");
				socket = socketKlient;
				bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Odczyt wiadomo�ci od klient�w
																										
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

		@Override
		public void run() {
			String str;
			PrintWriter pw = null;

			try {
				while ((str = bufferedReader.readLine()) != null) { 
					System.out.println("Odebrano >> " + str);

					Iterator it = klientArrayList.iterator(); // iterowanie po li�cie klient�w
					while (it.hasNext()) { 					
						pw = (PrintWriter) it.next(); 
						pw.println(str); 
						pw.flush(); 
					}
				}

			} catch (Exception e) {

			}

		}
	}

}
