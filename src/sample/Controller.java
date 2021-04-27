package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Controller {
    Socket socket;
    DataOutputStream out;
    @FXML
    TextArea textArea;
    @FXML
    TextField textField;
    @FXML
    private void send(){
        String text = textField.getText();
        try {
            out.writeUTF(text);
            textField.clear();
            textField.requestFocus();
            textArea.appendText("Вы: "+text+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void connect(){
        try {
            socket = new Socket("213.139.209.109",8188);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        String response = null; // Читаем ответ сервера
                        try {
                            response = in.readUTF();
                            textArea.appendText(response+"\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}