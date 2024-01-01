

// Level 2 - Task 2 - File Encryption/Decryption


import javax.crypto.*;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class FileHnadling {

    public void createFile() {

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter File name::");
            String fname = scanner.next();
            File object = new File(fname);
            if (object.createNewFile()) {
                System.out.println("File is Created -"+object.getName());
            }
            else {
                System.out.println("File is already exists");
            }

        } catch (Exception e) {
         
            System.out.println(e);
        }
    }

    public void writeFile() {
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter File name::");
            String fname = scanner.next();
            FileWriter object = new FileWriter(fname);
            System.out.println("Enter data::");
            String data = scanner.next();
            object.write(data);
            object.close();
            System.out.println("File Wrote Succcessfully..");
        } catch (Exception e) {
         
            System.out.println(e);
        }
    }


    public void openFile() {
        try{
            Scanner scanner = new Scanner(System.in);
           System.out.println("Enter File name::");
           String fname = scanner.next();
           File object = new File(fname);
           Scanner reader = new Scanner(object);

           while (reader.hasNextLine()) {
               String data = reader.nextLine();
               System.out.println(data);
           }
           reader.close();

       } catch (Exception e) {
        
           System.out.println(e);
       }
   }

    private static Key generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }


    private static void encryptFile(String inputFile, String outputFile, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] inputBytes = Files.readAllBytes(Paths.get(inputFile));
        byte[] encryptedBytes = cipher.doFinal(inputBytes);

        Files.write(Paths.get(outputFile), encryptedBytes, StandardOpenOption.CREATE);
    }

    private static void decryptFile(String inputFile, String outputFile, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] encryptedBytes = Files.readAllBytes(Paths.get(inputFile));
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        Files.write(Paths.get(outputFile), decryptedBytes, StandardOpenOption.CREATE);
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            FileHnadling obj = new FileHnadling();

            // Generate a key
            Key secretKey = generateSecretKey();

            // create a file
            System.out.println("Create a File.....");
            obj.createFile();

            // write a file
            System.out.println("Write a File.....");
            obj.writeFile();

            // open a file
            System.out.println("Open a File.....");
            obj.openFile();

            // File paths for Encrypting File
            System.out.println("Enter Input File for Encrypting::");
            String inputFile = scanner.next();

            String encryptedFile = "encrypted.txt";
            System.out.println("Encrypted File Name -"+ encryptedFile);

            // Encrypt file
            encryptFile(inputFile, encryptedFile, secretKey);

            // open a file
            System.out.println("Open a Encrypted File.....");
            obj.openFile();

            // File paths for Decrypting File
            System.out.println("Enter Encrypted File name for Decrypting::");
            String InencryptedFile = scanner.next();

            String decryptedFile = "decrypted.txt";
            System.out.println("Decrypted File Name -"+ decryptedFile);

            // Decrypt file
            decryptFile(InencryptedFile, decryptedFile, secretKey);

             // open a file
             System.out.println("Open a Decrypted File.....");
            obj.openFile();

            System.out.println("Encryption and decryption completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}