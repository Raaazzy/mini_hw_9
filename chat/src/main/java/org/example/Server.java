package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Server {
    static Vector<ClientHandler> users = new Vector<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        Socket socket;
        List<String> names = new ArrayList<>();
        while (true) {
            socket = serverSocket.accept();
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            Scanner scan = new Scanner(System.in);
            System.out.print("Enter username: ");
            String name = scan.next();
            names.add(name);
            ClientHandler handler = new ClientHandler(socket, name, input, output);
            Thread flow = new Thread(handler);
            System.out.println("User added");
            if (names.contains(name)) {
                users.add(handler);
                flow.start();
                names.add(name);
            }
        }
    }
}
