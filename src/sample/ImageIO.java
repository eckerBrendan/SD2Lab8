/*
 * Course: CS-1021-051
 * Fall 2018
 * Lab 9 - Final Project
 * Name: Brendan Ecker
 * Created: 08/20/18
 */
package sample;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.Scanner;

import static edu.msoe.cs1021.ImageUtil.readImage;
import static edu.msoe.cs1021.ImageUtil.writeImage;

public class ImageIO {


    public Image read(Path path) {
        Image newImage = null;
        String string = path.toString();
        if ((string.toLowerCase().endsWith(".msoe"))) {
            newImage = readMSOE(path);
        } else if ((string.toLowerCase().endsWith(".bmsoe"))) {
            newImage = readBMSOE(path);
        } else {
            try {
                newImage = readImage(path);

            } catch (IOException e) {
                Alert ioException = new Alert(Alert.AlertType.ERROR);
                ioException.setTitle("IOException");
                ioException.setHeaderText("Something went wrong with reading the file!");
                ioException.setContentText("The file you chose was not a supported file to open.");
            }
        }
        return newImage;
    }

    public static boolean write(Image image, Path path) {
        boolean written = false;
        String pathString = path.toString();
        int periodIndex = pathString.indexOf(".");
        String subPath = pathString.substring(periodIndex);
        if (subPath.equals(".msoe")) {
            written = writeMSOE(image, path);
        } else if (subPath.equals(".bmsoe")) {
            written = writeBMSOE(image, path);
        } else {
            try {
                writeImage(path, image);
                written = true;
            } catch (FileNotFoundException e) {
                Alert noFile = new Alert(Alert.AlertType.ERROR);
                noFile.setTitle("FileNotFound Exception");
                noFile.setHeaderText("Problem making file");
                noFile.setContentText("There was a problem when making a file with this name.");
            } catch (IOException e) {
                Alert ioException = new Alert(Alert.AlertType.ERROR);
                ioException.setTitle("IO Exception");
                ioException.setHeaderText("Opening Problem");
                ioException.setContentText("There was a problem opening this file.");
            }
        }
        return written;
    }

    private Image readMSOE(Path path) {
        WritableImage msoeImg = null;
        try {
            Scanner in = new Scanner(path);
            in.nextLine();
            msoeImg = new WritableImage(in.nextInt(), in.nextInt());
            in.nextLine();
            PixelWriter pixOut = msoeImg.getPixelWriter();
            for (int y = 0; y < msoeImg.getHeight(); y++) {
                for (int x = 0; x < msoeImg.getWidth(); x++) {
                    Color newColor = Color.web(in.next());
                    pixOut.setColor(x, y, newColor);
                }
            }
        } catch (IOException e) {
            Alert ioException = new Alert(Alert.AlertType.ERROR);
            ioException.setTitle("Something Went Wrong");
            ioException.setHeaderText("Something Went Wrong");
            ioException.setContentText("There was a problem will opening this file.");
        } catch (InputMismatchException e) {
            Alert inputException = new Alert(Alert.AlertType.ERROR);
            inputException.setTitle("Input Mismatch Exception");
            inputException.setHeaderText("");
        }


        return msoeImg;
    }

    private static boolean writeMSOE(Image image, Path path) {
        boolean written = false;
        PixelReader in = image.getPixelReader();
        PrintWriter out = null;
        try {
            FileWriter fw = new FileWriter(path.toString());
            out = new PrintWriter(fw);
            out.println("MSOE");
            out.print((int) image.getWidth() + " " + (int) image.getHeight());
            out.println();

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    Color color = in.getColor(x, y);
                    out.print(toRGBCode(color) + " ");
                }
                out.println();
            }
            written = true;
        } catch (IOException e) {
            Alert iOException = new Alert(Alert.AlertType.ERROR);
            iOException.setTitle("IO Exception");
            iOException.setHeaderText("There was a problem");
            iOException.setContentText("There was a problem writing this file");
            iOException.showAndWait();
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return written;
    }


    public static String toRGBCode(Color color) {
        return String.format("#%02x%02x%02x",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    public Image readBMSOE(Path path) {
        DataInputStream dataStream = null;
        WritableImage outImg = null;
        PixelWriter pixOut;
        try {
            InputStream inStream = Files.newInputStream(path);
            dataStream = new DataInputStream(inStream);

            char B = (char) dataStream.readByte();
            char M = (char) dataStream.readByte();
            char S = (char) dataStream.readByte();
            char O = (char) dataStream.readByte();
            char E = (char) dataStream.readByte();


            int width = dataStream.readInt();
            int height = dataStream.readInt();


            outImg = new WritableImage(width, height);
            pixOut = outImg.getPixelWriter();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int color = dataStream.readInt();
                    pixOut.setColor(x, y, intToColor(color));
                }
            }

        } catch (IOException e) {
            Alert ioException = new Alert(Alert.AlertType.ERROR);
            ioException.setTitle("IO Exception");
            ioException.setContentText("There was a problem opening the stream");
            ioException.showAndWait();
        } finally {
            if (dataStream != null) {
                try {
                    dataStream.close();
                } catch (IOException e) {
                    Alert closeFile = new Alert(Alert.AlertType.ERROR);
                    closeFile.setTitle("IO Exception");
                    closeFile.setContentText("It has failed to close the stream");
                    closeFile.showAndWait();
                }
            }
        }
        return outImg;
    }


    public static boolean writeBMSOE(Image image, Path path) {
        boolean written = false;
        PixelReader in = image.getPixelReader();
        DataOutputStream out = null;
        try {
            OutputStream os = Files.newOutputStream(path);
            out = new DataOutputStream(os);
            out.writeBytes ("b");
            out.writeBytes ("m");
            out.writeBytes ("s");
            out.writeBytes ("o");
            out.writeBytes ("e");
            out.writeInt((int)image.getWidth());
            out.writeInt((int)image.getHeight());

            for (int y = 0; y < image.getHeight(); y++){
                for (int x = 0; x < image.getWidth(); x++){
                    Color colorOut = in.getColor(x,y);
                    int color = colorToInt(colorOut);
                    out.writeInt(color);
                }
            }
            written = false;
        } catch (IOException e) {
            Alert closeFile = new Alert(Alert.AlertType.ERROR);
            closeFile.setTitle("IO Exception");
            closeFile.setContentText("It has failed to open the stream");
            closeFile.showAndWait();
        }finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    Alert closeFile = new Alert(Alert.AlertType.ERROR);
                    closeFile.setTitle("IO Exception");
                    closeFile.setContentText("It has failed to close the stream");
                    closeFile.showAndWait();
                }
            }
        }
        return written;
    }

    private static Color intToColor(int color) {
        double red = ((color >> 16) & 0x000000FF) / 255.0;
        double green = ((color >> 8) & 0x000000FF) / 255.0;
        double blue = (color & 0x000000FF) / 255.0;
        double alpha = ((color >> 24) & 0x000000FF) / 255.0;
        return new Color(red, green, blue, alpha);
    }

    private static int colorToInt(Color color) {
        int red = ((int) (color.getRed() * 255)) & 0x000000FF;
        int green = ((int) (color.getGreen() * 255)) & 0x000000FF;
        int blue = ((int) (color.getBlue() * 255)) & 0x000000FF;
        int alpha = ((int) (color.getOpacity() * 255)) & 0x000000FF;
        return (alpha << 24) + (red << 16) + (green << 8) + blue;
    }
}
