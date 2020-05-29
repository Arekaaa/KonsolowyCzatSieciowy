import java.util.Scanner;

public class Regulamin implements Runnable {
	Powitanie p = new Powitanie();
	private String zgoda;

        @Override
        public void run() {
            System.out.println("--------------------------------------------------------------------------------------------------------------");
            System.out.println("Regulamin czatu internetowego: ");
            System.out.println("1. U�ytkownik czatu ma obowi�zek przedstawi� si� swoim imieniem lub nickiem.");
            System.out.println("2. Na czacie nie mo�na obra�a� innych u�ytkownik�w.");
            System.out.println("3. Nale�y zachowywa� si� kulturalnie, nie namawia� do przemocy oraz nie u�ywa� niecenzuralnego s�ownictwa.");
            System.out.println("4. Szanowa� pogl�dy polityczne i religijne innych u�ytkownik�w oraz nie narzuca� w�asnych pogl�d�w");
            System.out.println("5. Zabraniane jest u�ywanie spamu w celu utrudnienia komunikacji innym.");
            System.out.println("6. Nie wolno reklamowa� innych stron internetowych, je�li administrator nie wyrazi na to zgody.");
            System.out.println("--------------------------------------------------------------------------------------------------------------\n");
            
		Scanner sc1 = new Scanner(System.in);
		System.out.println("Akceptujesz regulamin czatu ? U�yj litery T/t do akceptacji");
		System.out.println("T/t - Tak");
		System.out.println("Inna litera - Nie");
		zgoda = sc1.nextLine();

		try {

			if (zgoda.equalsIgnoreCase("t")) {
				System.out.println("Regulamin zatwierdzony! Przej�cie dalej...");
				Thread.sleep(1000);
				/*
				 * for (int i = 0; i<5; i++){ // mechanizm porz�dkowania konsoli. Dla
				 * zwi�kszenia czytelno�ci System.out.println(" "); }
				 */
				p.powitanie(); // Start ekranu startowego czatu
			}

			else {
				System.out.println("Brak zatwierdzenia regulaminu.");
				System.out.println("Opuszczanie czatu....");
				Thread.sleep(2000);
				sc1.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void regulamin() {

		Thread t = new Thread(new Regulamin());
		t.start();

	}

}
