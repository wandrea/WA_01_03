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
    //rendezés méret szerint, mely paraméterezhető, hogy csökkenő vagy növekvő
  /*  public void listFilesInDir() {

        for (File file : listOfFiles) {
            System.out.println("name: " + file.getName() + " size: " + file.length() / 1024.0 + " kb");
        }
    }*/

    public void sortListFilesInDir(SortDirection sortDirection) {

        for (int i = 0; i < listOfFiles.length; i++) {
            if (sortDirection.equals(SortDirection.ASC)) {
                int j = i;
                while (j > 0 && listOfFiles[j].length() < listOfFiles[j - 1].length()) {
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

        }
        for (File file : listOfFiles) {
            System.out.println("name: " + file.getName() + " size: " + file.length() / 1024.0 + " kb");
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

    public void printFileNameContent(String fileName) {
        if (fileName.endsWith(".txt")) {
            File searchFile = new File(fileName);
            for (File file : listOfFiles) {

                if (file.getName().equals(searchFile.getName())) {

                    try (BufferedReader br = new BufferedReader(new FileReader(searchFile))) {
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
            }
        } else {
            System.out.println("Nem txt kiterjesztésű a beolvasni kívánt file");
        }

    }


    public void newFileAndContent() {
        Scanner sc = new Scanner(System.in);
        System.out.println("írja be a nevét a filenak: ");
        String newFileName = sc.next();
        System.out.println("írja le a file tartalmát: lezárás:EOF");
        sc.useDelimiter("EOF");
        String newFileContent = sc.next();
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(newFileName);
            fout.write(newFileContent.getBytes());

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        }

    }

    public void FileContentModify() {
        Scanner sc = new Scanner(System.in);
        System.out.println("írja be a nevét a filenak: ");
        String fileName = sc.next();
        for (File file : listOfFiles) {
            if (file.getName().equals(fileName)) {
                System.out.println("írja be a modosított tartalmat: lezárás:EOF");
                sc.useDelimiter("EOF");
                String newFileContent = sc.next();
                FileOutputStream fout = null;
                try {
                    fout = new FileOutputStream(file);
                    fout.write(newFileContent.getBytes());

                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } finally {
                    if (fout != null) {
                        try {
                            fout.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }


                }

            }
        }
    }

    public void fileDelete() {
        Scanner sc = new Scanner(System.in);
        System.out.println("írja be a nevét a filenak: ");
        String fileName = sc.next();
        for (File file : listOfFiles) {
            if (file.getName().equals(fileName)) {
                if (file.delete()) {
                    System.out.println("kitöröltük a filet");
                    ;
                } else {
                    System.out.println("nem sikerült törölni");
                }

            }
        }
    }

    public void biggestFileHalf() {
        sortListFilesInDir(SortDirection.DESC);
        for (File file : listOfFiles) {
            if (file.getName().endsWith(".txt")) {
                String biggestFileName = file.getAbsolutePath();
                FileInputStream fin = null;
                FileOutputStream fout1 = null;
                FileOutputStream fout2 = null;
                try {
                    fout1 = new FileOutputStream(biggestFileName.substring(0, biggestFileName.length() - 4) + "_1.txt");
                    fout2 = new FileOutputStream(biggestFileName.substring(0, biggestFileName.length() - 4) + "_2.txt");

                    fin = new FileInputStream(file);
                    int i;
                    int countByte = 0;
                    while ((i = fin.read()) != -1) {

                        if (countByte < (file.length() / 2)) {
                            fout1.write(i);
                        } else {
                            fout2.write(i);
                        }
                        countByte++;
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (fin != null) {
                        try {
                            fin.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fout1 != null) {
                        try {
                            fout1.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fout2 != null) {
                        try {
                            fout2.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }break;
        }
    }
}
