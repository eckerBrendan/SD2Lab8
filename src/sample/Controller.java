/*
 * Course: CS-1021-051
 * Fall 2018
 * Lab 9 - Final Project
 * Name: Brendan Ecker
 * Created: 08/20/18
 */
package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.nio.file.Path;


public class Controller {
    @FXML
    Stage stage;
    @FXML
    ImageView image;
    @FXML
    Button showFilter;
    private ImageIO iO = new ImageIO();
    private Path imgFile;
    private Image orgImage;
    private FilterKernel kern;
    private Transformable negative = (y, c) -> new Color(1 - c.getRed(), 1 - c.getGreen(), 1 - c.getBlue(), 1);
    private Transformable greyScale = (y, c) -> new Color((c.getRed() * 0.2126 + c.getGreen() * 0.7152 + c.getBlue() * 0.0722),
            (c.getRed() * 0.2126 + c.getGreen() * 0.7152 + c.getBlue() * 0.0722), (c.getRed() * 0.2126 + c.getGreen() * 0.7152 + c.getBlue() * 0.0722), 1);
    private Transformable red = (y, c) -> new Color(c.getRed(), 0, 0, 1);
    private Transformable redGray = (y,c) -> { final double grey = c.getRed() * 0.2126 + c.getGreen() * 0.7152 + c.getBlue() * 0.0722;
    if ( y%2 == 0){
        return new Color(grey, grey, grey, 1);
    }else{
        return new Color(c.getRed(),0,0,1);
    }
    };

    Stage kernelStage;


    public void setKernelStage(Stage stage) {
        this.kernelStage = stage;
    }

    public void openResponse(ActionEvent e) {

        FileChooser file = new FileChooser();
        file.setTitle("Choose an image");
        file.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.gif", "*.png", "*.tiff", "*.msoe", "*.bmsoe"));
        imgFile = Path.of(file.showOpenDialog(stage).toURI());
        String imgFileString = imgFile.toString();
        int periodIndex = imgFileString.indexOf(".");
        String subImgFile = imgFileString.substring(periodIndex);
        if (subImgFile.equals(".gif") || subImgFile.equals(".jpg") || subImgFile.equals(".png") || subImgFile.equals(".tiff") || subImgFile.equals(".msoe") || subImgFile.equals(".bmsoe")) {
            Image img = iO.read(imgFile);
            image.setImage(img);
            orgImage = image.getImage();
        } else {
            Alert notSupported = new Alert(Alert.AlertType.ERROR);
            notSupported.setTitle("Not Supported");
            notSupported.setHeaderText("This File is not supported");
            notSupported.setContentText("The file you choose is not a gif, jpg, png, tiff, or msoe.");
            notSupported.showAndWait();
        }
    }


    public void saveResponse(ActionEvent e) {
        Image img = image.getImage();
        FileChooser file = new FileChooser();
        File fileLocation = file.showSaveDialog(stage);
        if (fileLocation != null) {
            ImageIO.write(img, fileLocation.toPath());
        }
    }


    public void negativeResponse(ActionEvent e) {
        Image img = image.getImage();
        Image negativeImg = transformImage(img, negative);
        //Previous negative response

      /*  for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                Color org = pix.getColor(x, y);
                double negativeRed = (1 - org.getRed());
                double negativeGreen = (1 - org.getGreen());
                double negativeBlue = (1 - org.getBlue());

                Color negativeColor = Color.color(negativeRed, negativeGreen, negativeBlue);
                pixOut.setColor(x, y, negativeColor);
            }
        }*/
        image.setImage(negativeImg);
    }


    public void greyScaleResponse(ActionEvent e) {
        Image img = image.getImage();
        Image greyImg = transformImage(img, greyScale);

        // Previous greyScaleResponse

        /*for (int x = 0; x < img.getWidth(); x ++){
            for(int y = 0; y< img.getHeight(); y++){
                Color org = pix.getColor(x,y);
                double gR = (0.2126 * org.getRed());
                double gG = (0.7152 * org.getGreen());
                double gB = (0.0722 * org.getBlue());
                Color greyImgColor = Color.color((gR + gG+ gB), (gR + gG+ gB),(gR + gG+ gB));
                pixOut.setColor(x,y,greyImgColor);
            }
        }*/
        image.setImage(greyImg);
    }

    public void redResponse(ActionEvent e) {
        Image img = image.getImage();
        Image redImg = transformImage(img,red);
        /*for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                Color org = pix.getColor(x, y);
                Color redImgColor = Color.color(org.getRed(), 0, 0);
                pixout.setColor(x, y, redImgColor);
            }
        }*/
        image.setImage(redImg);
    }

    public void greyRedResponse(ActionEvent e) {
        Image img = image.getImage();
        Image redGreyImg = transformImage(img, redGray);


       /* for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                if (y % 2 == 0) {
                    for (int i = 0; i < img.getWidth(); i++) {
                        Color org = pix.getColor(i, y);
                        double gR = (0.2126 * org.getRed());
                        double gG = (0.7152 * org.getGreen());
                        double gB = (0.0722 * org.getBlue());
                        Color greyImgColor = Color.color((gR + gG + gB), (gR + gG + gB), (gR + gG + gB));
                        pixOut.setColor(i, y, greyImgColor);
                    }
                } else if (!(y % 2 == 0)) {
                    for (int i = 0; i < img.getWidth(); i++) {
                        Color org = pix.getColor(i, y);
                        Color redImgColor = Color.color(org.getRed(), 0, 0);
                        pixOut.setColor(i, y, redImgColor);
                    }
                }
            }
        }*/
                image.setImage(redGreyImg);
    }

    public void reloadResponse(ActionEvent e) {
        image.setImage(orgImage);
    }

    public void showFilter(ActionEvent e) {
        if(showFilter.getText().equals("Show Filter")) {
            kernelStage.show();
            showFilter.setText("Hide Filter");
        }else if(showFilter.getText().equals("Hide Filter")){
            kernelStage.hide();
            showFilter.setText("Show Filter");
        }

    }

    public void setImage(Image image){
        this.image.setImage(image);
    }

    public Image getImage(){
        return image.getImage();
    }

    private static Image transformImage(Image image, Transformable transformable){
        WritableImage outImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
        PixelReader in = image.getPixelReader();
        PixelWriter out = outImage.getPixelWriter();

        for (int x = 0; x < image.getWidth(); x++){
            for (int y = 0; y < image.getHeight(); y++){
                Color cIn = in.getColor(x,y);
                Color c = transformable.apply(y,cIn);
                out.setColor(x,y,c);
            }
        }
        return outImage;
    }
}
