import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        String gamePath = "C://Games//savegames";

        GameProgress gp1 = new GameProgress(100, 80, 3, 20);
        GameProgress gp2 = new GameProgress(90, 50, 6, 50);
        GameProgress gp3 = new GameProgress(150, 70, 4, 20);

        GameProgress[] gps = {gp1, gp2, gp3};
        for (int i = 0; i < gps.length; i++)
            saveGame(String.format("%s//data%d.save", gamePath, i), gps[i]);

        String zipPath = "C://Games//savegames//data.zip";
        String[] saves = new String[]{"C://Games//savegames//data0.save",
                "C://Games//savegames//data1.save",
                "C://Games//savegames//data2.save"
        };

        zipFiles(zipPath, saves);
    }

    public static void saveGame(String path, GameProgress gameProgress){
        File file;
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void zipFiles(String zipPath, String[] files){
        // архивируем сохранения
        try {
            ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath));
            for (String filePath : files) {
                File fileToZip = new File(filePath);
                FileInputStream fis = new FileInputStream(fileToZip);
                ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                zout.putNextEntry(zipEntry);

                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();
                zout.write(buffer);
            }
            zout.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        // удаляем лишние файлы
        for (String filePath : files ) {
            File fl =  new File(filePath);
            System.out.println(fl.delete());
        }
    }

}
