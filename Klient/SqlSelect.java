
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlSelect implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {
			// Nawi¹zywanie po³¹czenia z baz¹
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/czat_sieciowy", "root",""); 
			if (connection != null) {
				System.out.println("Po³¹czono do bazy!");
				System.out.println("Historia pod³¹czonych u¿ytkowników:\n");
			}

			Statement statement = connection.createStatement(); // Stworzenie klasy Statement

			ResultSet result = statement.executeQuery("select * from uzytkownik"); // Tworzenie zapytania
			System.out.println("ID                   Imie                             nazwaAdres\n");
			while (result.next()) {// Odczytywanie wyników w pêtli.

				System.out.println(result.getInt("ID") + "                    " + result.getString("imie")
						+ "                 " + result.getString("nazwaAdres"));
			}
			result.close();
			statement.close();
			connection.close();
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
	}

	public void showRecords() {

		Thread t = new Thread(new SqlSelect());
		t.start();

	}
}
