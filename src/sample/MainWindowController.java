package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    Button serverButton;

    @FXML
    Button clientOneButton;

    @FXML
    Button clientTwoButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createNewWindow(serverButton,"view/ServerWindow.fxml","Server");
        createNewWindow(clientOneButton,"view/ClientOneWindow.fxml","ClientOne");
        createNewWindow(clientTwoButton,"view/ClientTwoWindow.fxml","ClientTwo");


    }
    public void createNewWindow(Button button, String fxmlName, String title)
    {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource(fxmlName));

                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Stage stage = new Stage();
                stage.setTitle(title);
                stage.setScene(scene);
                stage.show();
            }
        });
    }

}
