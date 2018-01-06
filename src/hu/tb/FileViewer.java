package hu.tb;

import sun.print.resources.serviceui_zh_CN;

import java.io.*;
import java.util.Scanner;

enum SortDirection {ASC, DESC};

public class FileViewer {

    private File folder;
    private File[] listOfFiles;
    private File lastPrintedFile;

    public FileViewer(File folder) {
        this.folder = folder;
        listOfFiles = folder.listFiles();
    }

    // file-ok listázása név + méret kb-ban
    //TODO rendezés méret szerint, mely paraméterezhető, hogy csökkenő vagy növekvő
  /*  public void listFilesInDir() {

        for (File file : listOfFiles) {
            System.out.println("name: " + file.getName() + " size: " + file.length() / 1024.0 + " kb");
        }
    }*/

	public void sortListFilesInDir (SortDirection sortDirection) {

		for (int i=0; i<listOfFiles.length;i++) {
			if (sortDirection.equals(SortDirection.ASC)) {
				int j=i;
				while ( j>0 && listOfFiles[j].length()<listOfFiles[j-1].length()) {
					File temp = listOfFiles[j - 1];
					listOfFiles[j - 1] = listOfFiles[j];
					listOfFiles[j] = temp;

					--j;
				}
			} else {
				int j = i;
				while (j > 0 && listOfFiles[j].length() > listOfFiles[j - 1].length()) {
					File temp = listOfFiles[j - 1];
					listOfFiles[j - 1] = listOfFiles[j];
					listOfFiles[j] = temp;
					--j;


				}

			}

		}for(File file : listOfFiles){
            System.out.println("name: " + file.getName() + " size: " + file.length() / 1024.0 +  " kb");
        }
	}



    public void printPreviousFileContent() {
        lastPrintedFile = searchPreviousTextFile();
        printFileContent(lastPrintedFile);
    }

    /**
     * Kiírja az első *.txt tartalmát console-ra
     */
    public void printFirstTextFile() {
        lastPrintedFile = searchFirstTextFile();
        printFileContent(lastPrintedFile);

    }

    public void printNextFileContent() {
        lastPrintedFile = searchNextTextFile();
        printFileContent(lastPrintedFile);
    }


    public void printFileContent(File file) {
        if (file == null) {
            System.err.println("vége a mappának, nincs több tartalma");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private File searchFirstTextFile() {
        for (File file : listOfFiles) {
            if (file.getName().endsWith("txt")) {
                return file;
            }
        }

        return null;
    }

    private File searchNextTextFile() {
        if (lastPrintedFile == null) {
            return searchFirstTextFile();
        }
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i] == lastPrintedFile) {
                for (int j = i + 1; j < listOfFiles.length; j++) {
                    if (listOfFiles[j].getName().endsWith("txt")) {
                        return listOfFiles[j];
                    }
                }
            }
        }

        return null;
    }

    private File searchPreviousTextFile() {
        if (lastPrintedFile == null) {
            return searchFirstTextFile();
        }
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i] == lastPrintedFile) {
                for (int j = i - 1; j >= 0; j--) {
                    if (listOfFiles[j].getName().endsWith("txt")) {
                        return listOfFiles[j];
                    }
                }
            }
        }

        return null;
    }


}
