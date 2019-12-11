package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ServerController implements Initializable {
    private String clientMessage;
    private String serverMessage;
    private PrintWriter out;
    private BufferedReader in;
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private List<PrintWriter> printWriterList;

    @FXML
    TextArea textAreaServer;

    @FXML
    TextField textFieldServer;

    @FXML
    Button buttonServer;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        printWriterList = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(7000);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while (true) {
                    try {
                        clientSocket = serverSocket.accept();
                        textAreaServer.appendText("Someone connect with me!\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //new ServerThread(clientSocket, textAreaServer).start();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                out = new PrintWriter(clientSocket.getOutputStream(), true);
                                printWriterList.add(out);
                                System.out.println(printWriterList);
                                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                            try {
                                clientMessage=in.readLine();
                                while(true){

                                    textAreaServer.appendText(clientMessage+"\n");
                                    clientMessage=in.readLine();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        }).start();
        buttonServer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                serverMessage = textFieldServer.getText();
                for (int i =0; i<printWriterList.size(); i++) {
                    printWriterList.get(i).println(serverMessage);
                }
                textAreaServer.appendText("[Server]: "+serverMessage+"\n");
            }

        });

    }
}
