package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

class ClientHandler implements Runnable {
    private final String name;
    final DataInputStream input;
    final DataOutputStream output;
    Socket socket;
    boolean exist;

    public ClientHandler(Socket socket, String name, DataInputStream input, DataOutputStream output) {
        this.input = input;
        this.output = output;
        this.name = name;
        this.socket = socket;
        this.exist = true;
    }

    @Override
    public void run() {
        String received;
        while (true) {
            try {
                received = input.readUTF();
                System.out.println(received);
                StringTokenizer stringTokenizer = new StringTokenizer(received, "/");
                String textToSend = stringTokenizer.nextToken();
                String recipient = stringTokenizer.nextToken();
                for (ClientHandler user : Server.users) {
                    if (user.name.equals(recipient) && user.exist) {
                        user.output.writeUTF(this.name + ": " + textToSend);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
