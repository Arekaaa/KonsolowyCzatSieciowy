
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
			// Nawi�zywanie po��czenia z baz�
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/czat_sieciowy", "root",""); 
			if (connection != null) {
				System.out.println("Po��czono do bazy!");
				System.out.println("Historia pod��czonych u�ytkownik�w:\n");
			}

			Statement statement = connection.createStatement(); // Stworzenie klasy Statement

			ResultSet result = statement.executeQuery("select * from uzytkownik"); // Tworzenie zapytania
			System.out.println("ID                   Imie                             nazwaAdres\n");
			while (result.next()) {// Odczytywanie wynik�w w p�tli.

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
