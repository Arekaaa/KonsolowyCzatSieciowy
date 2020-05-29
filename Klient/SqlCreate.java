import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlCreate implements Runnable {
	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {

			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
			Statement statement = connection.createStatement();
			statement.executeUpdate("CREATE DATABASE IF NOT EXISTS czat_sieciowy");
			statement.close();
			connection.close();
		} catch (SQLException cd) {
			cd.printStackTrace();
		}

		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/czat_sieciowy", "root",
					"");
			Statement statement = connection.createStatement();

			statement.executeUpdate("CREATE TABLE IF NOT EXISTS uzytkownik "
					+ "  (ID   INTEGER not NULL AUTO_INCREMENT," + "   imie VARCHAR(30) not NULL,"
					+ "   nazwaAdres VARCHAR(80) not NULL," + " PRIMARY KEY (ID))");
			statement.close();
			connection.close();
		} catch (SQLException ct) {
			ct.printStackTrace();
		}

	}

	public void createDb() {
		Thread t = new Thread(new SqlCreate());
		t.start();
	}
}