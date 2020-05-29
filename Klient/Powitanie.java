public class Powitanie implements Runnable {

    Klient k = new Klient();
    @Override
    public void run() {
        System.out.println("---------------------------------------------------------------------------------------------------");
        System.out.println("-Witaj na czacie internetowym.");
        System.out.println("-Za chwilê uzyskasz dostêp do rozmowy z innymi u¿ytkownikami online. Nie zapomnij siê przywitaæ.");
        System.out.println("-¯yczymy udanej rozmowy!!");
        System.out.println("-Wyjœcie z czatu za pomoc¹ litery 'Q/q' ");
        System.out.println("---------------------------------------------------------------------------------------------------");
	}

	public void powitanie() {

		Thread t = new Thread(new Powitanie());
		t.start();
		try {
			Thread.sleep(1500);
			k.startKlient(); // Start klienta
		} catch (Exception e) {

		}

	}
}
