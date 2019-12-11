package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientOneController implements Initializable  {

    private String clientMessage;
    private String serverMessage;
    private BufferedReader in;
    private BufferedReader stdIn;
    private PrintWriter out;

    @FXML
    TextArea textAreaClient;

    @FXML
    TextField textFieldClient;

    @FXML
    Button buttonClient;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Thread thread = new Thread(() ->
        {
            System.out.println("Thread running!");
            try {
                Socket clientSocket = new Socket("localhost", 7000);
                 out = new PrintWriter(clientSocket.getOutputStream(),true);
                 in =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                 while (true)
                 {
                     serverMessage ="[Server]: "+ in.readLine()+"\n";
                     textAreaClient.appendText(serverMessage);
                 }


            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        buttonClient.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                clientMessage = textFieldClient.getText();
                out.println("Client 1: "+clientMessage);
                textAreaClient.appendText("Me: "+clientMessage+"\n");
            }

        });

    }
//    public String sendMessage(String msg)
//    {
//        printWriter.println(msg);
//        String response = bufferedReader.readLine();
//        return response
//    }
}
