import controller.LoginUserController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import protocol.ServiceProxy;
import service.ServiceInterface;

import java.io.IOException;
import java.util.Properties;

public class StartClient extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Start client");
        Properties clientProperties = new Properties();
        try {
            clientProperties.load(StartClient.class.getResourceAsStream("/client.properties"));
            System.out.println("Properties set.");
            clientProperties.list(System.out);
        } catch (IOException e) {
            System.out.println("Cannot find client.properties " + e);
            return;
        }

        String defaultServer = "localhost";
        String serverIP = clientProperties.getProperty("server.host", defaultServer);
        int defaultPort = 55555;
        int serverPort = defaultPort;

        try {
            serverPort = Integer.parseInt(clientProperties.getProperty("server.port"));
        } catch (NumberFormatException nef) {
            System.err.println("Wrong  Port Number " + nef.getMessage());
            System.err.println("Using default port " + defaultPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        ServiceInterface service = new ServiceProxy(serverIP, serverPort);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("loginUser-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 660, 500);
        LoginUserController loginUserController = fxmlLoader.getController();
        loginUserController.setService(service);
        loginUserController.setStage(stage);
        stage.getIcons().add(new Image("icons/stage-picture.png"));
        stage.setTitle("Blood4Life");
        stage.setScene(scene);
        stage.show();
    }
}
