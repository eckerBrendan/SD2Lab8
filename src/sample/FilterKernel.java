/*
 * Course: CS-1021-051
 * Fall 2018
 * Lab 9 - Final Project
 * Name: Brendan Ecker
 * Created: 08/20/18
 */
package sample;

import edu.msoe.cs1021.ImageUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class FilterKernel {
    @FXML
    TextField text1;
    @FXML
    TextField text2;
    @FXML
    TextField text3;
    @FXML
    TextField text4;
    @FXML
    TextField text5;
    @FXML
    TextField text6;
    @FXML
    TextField text7;
    @FXML
    TextField text8;
    @FXML
    TextField text9;
    Controller con;
    private double[] kernel;
    private double[] blurKernel = new double[]{0.0, 1.0/9, 0.0,
            1.0/9, 5.0/9, 1.0/9,
            0.0, 1.0/9, 0.0};
    private double[] sharpenKernel = new double[]{0.0, -1.0, 0.0,
                                        -1.0, 5.0, -1.0,
                                         0.0, -1.0, 0.0};
    Stage stage = new Stage();
    public void setFilterController(Controller con){
        this.con = con;
    }

    @FXML
    public void blur(ActionEvent e){
            text1.setText("0");
            text2.setText("1");
            text3.setText("0");
            text4.setText("1");
            text5.setText("5");
            text6.setText("1");
            text7.setText("0");
            text8.setText("1");
            text9.setText("0");

        kernel = blurKernel;
    }

    @FXML
    public void sharpen(ActionEvent e){
            text1.setText("0");
            text2.setText("-1");
            text3.setText("0");
            text4.setText("-1");
            text5.setText("5");
            text6.setText("-1");
            text7.setText("0");
            text8.setText("-1");
            text9.setText("0");

            kernel = sharpenKernel;
    }

    @FXML
    public void apply(ActionEvent e){
        if ((kernel != blurKernel || kernel != sharpenKernel)){
            double topLeft = Double.parseDouble(text1.getText());
            double topMid = Double.parseDouble(text2.getText());
            double topRight = Double.parseDouble(text3.getText());
            double midLeft = Double.parseDouble(text4.getText());
            double midMid = Double.parseDouble(text5.getText());
            double midRight = Double.parseDouble(text6.getText());
            double botLeft = Double.parseDouble(text7.getText());
            double botMid = Double.parseDouble(text8.getText());
            double botRight = Double.parseDouble(text9.getText());
            double sum = 0;
            for (int i = 1; i < kernel.length; i++){
                sum = sum + kernel[i];
            }
            if (sum>9){
                kernel = new double[]{topLeft/9, topMid/9, topRight/9,
                                      midLeft/9, midMid/9, midRight/9,
                                      botLeft/9, botMid/9, botRight/9};
            }else if (sum>1){
                kernel = new double[]{topLeft, topMid, topRight,
                                      midLeft, midMid, midRight,
                                      botLeft, botMid, botRight};
            }
        }
        Image in = con.getImage();
        con.setImage(ImageUtil.convolve(in, kernel));
    }
}
