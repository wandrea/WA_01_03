package hu.tb;

import java.util.Scanner;
import java.io.*;

//refactor to singleton
public class Menu {

	private static Menu INSTANCE;

	private FileViewer fileViewer;


	private Menu() {
		fileViewer = new FileViewer(new File("d:\\HELIXLAB_TANFOLYAM\\JAVA\\9ora"));
	}

	public static Menu getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Menu();
		}
		return INSTANCE;
	}

	/*public Menu() {
        fileViewer = new FileViewer(new File("C:\\Users\\Admin\\Desktop\\resources"));
	}*/

	//Console-os menü megvalósítása
	public void run() {

		int flag = -1;

		while (flag != 0) {
			printMenu();
			System.out.println("Menü pont kiválasztása: ");
			Scanner scanner = new Scanner(System.in);
			flag = scanner.nextInt();

			switch (flag) {
				case 1:
					fileViewer.sortListFilesInDir(SortDirection.DESC);

					break;
				case 2:
					fileViewer.printFirstTextFile();
					break;
				case 3:
					fileViewer.printNextFileContent();
					break;
				case 4:
					fileViewer.printPreviousFileContent();
					break;
				case 5:
					System.out.println("Adja meg a kiiratni kívánt file nevét: ");
					String fileName = scanner.next();
					fileViewer.printFileNameContent(fileName);
					break;
				case 6:
					fileViewer.newFileAndContent();
					break;
				case 7:
					fileViewer.FileContentModify();
					break;
				case 8:
					fileViewer.fileDelete();
					break;

				case 9:
					fileViewer.biggestFileHalf();
					break;
			}
		}
		System.out.println("\n");


	}

	//  választható menüpontok
	//file-ok listázása 1
	//  - 1. *.txt tartalmának a megnyitása(console-ra írása) 2
	//  Következő *.txt tartalmának a megnyitása(console-ra írása)
	// Előző *.txt tartalmának a megnyitása(console-ra írása)
	// Név alapján *.txt tartalmának a megnyitása(console-ra írása)
	// file létrehozás név + tartalom
	// file tartalom módosítás név alapján
	//  file törlése
	//  legnagyobb méretű file megkeresése, tartalmának elfelezése és 2 külön file-ba írása
	//  Kilépés

	private void printMenu() {
		System.out.println("**********Menu***********");
		System.out.println("**File-ok listázása(1)***");
		System.out.println("** 1 txt file kiírása(2)*");
		System.out.println("** 2 txt file kiírása(3)*");
		System.out.println("** előző txt file kiírása(4)*");
		System.out.println("** nev alapján kiiratás(5)*");
		System.out.println("**uj file létrehozása(6)*");
		System.out.println("**tartalom modosítása(7)*");
		System.out.println("**file törles(8)*");
		System.out.println("**file felezés(9)*");
		System.out.println("*******Kilépés (0)******");
	}
}
