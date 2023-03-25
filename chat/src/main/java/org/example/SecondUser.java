package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class SecondUser {
    final static int port = 1234;

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        InetAddress ip = InetAddress.getByName("localhost");
        Socket socket = new Socket(ip, port);
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        Thread sendText = new Thread(() -> {
            while (true) {
                String text = scan.nextLine();
                try {
                    output.writeUTF(text);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread readText = new Thread(() -> {
            while (true) {
                try {
                    String text = input.readUTF();
                    System.out.println(text);
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        });
        sendText.start();
        readText.start();
    }
}