/*
 * Course: CS-1021-051
 * Fall 2018
 * Lab 9 - Final Project
 * Name: Brendan Ecker
 * Created: 08/20/18
 */
package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Sets up first window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Image Manipulator");
        primaryStage.setScene(new Scene(root, 400, 350));
        //sets up kernel window
        FXMLLoader kernel = new FXMLLoader(getClass().getResource("window2.fxml"));
        Parent root2 = kernel.load();
        Stage stage2 = new Stage();
        stage2.setScene(new Scene(root2, 300, 175));
        //The FXMLLoaders will create the instances of the controllers
        // for us. We need to get those instances
        Controller controller = loader.getController();
        FilterKernel kernelController = kernel.getController();
        kernelController.setFilterController(controller);
        //Kernel needs to access the second stage to
        // show the contents
        controller.setKernelStage(stage2);



        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
