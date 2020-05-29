import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Klient {

	public static final int PORT = 4500;
	// public static final String IP = "127.0.0.1" LOCALHOST
	public static final String IP = "192.168.100.8";

	BufferedReader bufferedReader; 
	private String imie;

	// start programu
	public static void main(String[] argsv) {

		Regulamin r = new Regulamin();
		SqlSelect select = new SqlSelect();
		SqlCreate create = new SqlCreate();

		int decyzja;
		System.out.println("Funkcje programu:");
		System.out.println("1. Do³¹czenie do czatu sieciowego.");
		System.out.println("2. Przegl¹danie historii pod³¹czonych u¿ytkowników.");
		Scanner sc2 = new Scanner(System.in);
		System.out.print("Wybierz funkcjê:");
		decyzja = sc2.nextInt();

		if (decyzja == 1) {
			create.createDb(); // Utworzenie wstêpnej tabeli dla u¿ytkowników
			r.regulamin(); // Start klienta
		} else if (decyzja == 2) {
			select.showRecords(); // Przegl¹danie historii po³¹czonych nicków
			decyzja = sc2.nextInt();
			if (decyzja == 1) {
				r.regulamin();

			}
		} else {
			System.out.println("B³êdny wybór!");
			sc2.close();
		}

	}

	// uruchomienie klienta

	public void startKlient() { // wysy³anie komunikatu do serwera - w¹tek g³ówny
		Scanner sc = new Scanner(System.in);
		System.out.print("Podaj imiê: ");
		imie = sc.nextLine();

		// ---------------------------------------------------

		try { // Dopisanie do bazy danych uzytkownika

			InetAddress adres = InetAddress.getLocalHost();
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/czat_sieciowy", "root",
					"");
			Statement statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO uzytkownik " + "VALUES (NULL,'" + imie + "','" + adres + "')");
			statement.close();
			connection.close();
		} catch (SQLException exc) {
			exc.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		// -----------------------------------------------------

		try { // nawi¹zywanie po³¹czenia, wysy³anie i odbieranie tekstu

			Socket socket = new Socket(IP, PORT);
			System.out.println("Pod³¹czono do " + socket);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream()); // Wysy³anie komunikatu
			
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Odbiór komunikatu

			Thread t = new Thread(new Odbiorca());
			t.start();

			while (true) {
				String str = sc.nextLine();
				if (!str.equalsIgnoreCase("q")) {
					printWriter.println(imie + ": " + str);
					printWriter.flush();
				} else { // wychodzenie z czatu przez klienta
					printWriter.println(imie + " rozlaczyl/a sie");
					printWriter.flush();
					printWriter.close();
					sc.close();
					socket.close();
				}
			}

		} catch (Exception e) {

		}

	}

	// odbiór komunikatu od serwera w¹tek drugi
	class Odbiorca implements Runnable {

		@Override
		public void run() {
			String tekst;
			try {
				while ((tekst = bufferedReader.readLine()) != null) { // Jeœli tekst odczytany nie jest pusty
					String subString[] = tekst.split(":"); 
															
					if (!subString[0].equals(imie)) { 
														
						System.out.println(tekst);
					}
				}
			} catch (Exception e) {
				System.out.println("Po³¹czenie zosta³o zakoñczone.");
			}
		}
	}

}
