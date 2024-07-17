package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Controller {

    @FXML
    private Button button_1;

    @FXML
    private Button button_10;

    @FXML
    private Button button_11;

    @FXML
    private Button button_12;

    @FXML
    private Button button_13;

    @FXML
    private Button button_14;

    @FXML
    private Button button_15;

    @FXML
    private Button button_16;

    @FXML
    private Button button_17;

    @FXML
    private Button button_18;

    @FXML
    private Button button_19;

    @FXML
    private Button button_2;

    @FXML
    private Button button_20;

    @FXML
    private Button button_21;

    @FXML
    private Button button_22;

    @FXML
    private Button button_23;

    @FXML
    private Button button_24;

    @FXML
    private Button button_25;

    @FXML
    private Button button_3;

    @FXML
    private Button button_4;

    @FXML
    private Button button_5;

    @FXML
    private Button button_6;

    @FXML
    private Button button_7;

    @FXML
    private Button button_8;

    @FXML
    private Button button_9;

    @FXML
    private Button button_train;

    @FXML
    private Button button_x;

    @FXML
    private Button button_y;

    @FXML
    private Label out_Lbl;

    int alpha=1;
    int bias=0;
    int sigma=0;
    float theta=0.2f;
    int restart=1;
    int yin;
    int y=0;
    int[] weight = new int[25];

    @FXML
    void xClicked(ActionEvent event) {
        inputs[25] = 1;

    }

    @FXML
    void yClicked(ActionEvent event) {

        inputs[25] = -1;
    }


    int[][] algoInput ;
    int[] target ;
    String fileData = " ";
    String st="";
    int[] inputs = new int[26];
    int digit = 0;
    int numberOfFiles = 0;

    ArrayList<Button> buttons;

    public void initialize() {
        File directory =new File("D:\\perceptron_recognition\\src\\data\\");
        numberOfFiles = directory.list().length;
        algoInput = new int[26][numberOfFiles];
        target = new int[numberOfFiles];
        for (int j = 0; j < 26; j++) {
            inputs[j] = -1;
        }
        buttons = new ArrayList<>(Arrays.asList(button_1, button_2, button_3, button_4, button_5, button_6, button_7
                , button_8, button_9, button_10, button_11, button_12, button_13, button_14, button_15, button_16, button_17, button_18
                , button_19, button_20, button_21, button_22, button_23, button_24, button_25));
        buttons.forEach(button -> {
            setupButton(button);

        });
    }

    private void setupButton(Button button) {
        button.setOnMouseClicked(mouseEvent -> {
            if (button.getStyle().compareTo("-fx-background-color: #000000") == 0) {
                button.setStyle("-fx-background-color: #null");
                digit = Integer.parseInt(button.getText());
                inputs[digit] = -1;
            } else {
                button.setStyle("-fx-background-color: #000000");
                digit = Integer.parseInt(button.getText());
                inputs[digit] = 1;

            }

        });
    }

    @FXML
    void trainClicked(ActionEvent event) throws IOException {
        File directory =new File("D:\\perceptron_recognition\\src\\data\\");
        numberOfFiles = directory.list().length;
        st=" ";
        try {

            for (int j =0 ; j<26; j++){
                st=st+inputs[j]+"@";
            }
            st = st.substring(1,st.length());
            File file = new File("D:\\perceptron_recognition\\src\\data\\"+"data"+numberOfFiles+".txt");
            FileWriter writer = new FileWriter(file);
            writer.write(st);
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void perceptronAlgorithm(int[][] algoInput,int[] target) {

        for (int j = 0; j < 25; j++) {
            weight[j] = 0;
        }
        while(restart==1) {
            restart = 0;
            for (int i = 0; i < numberOfFiles; i++) {
                yin = bias + (algoInput[0][i] * weight[0]) + (algoInput[1][i] * weight[1]);
                if (yin > theta)
                    y = 1;
                if (yin <= theta && yin >= (-1) * theta)
                    y = 0;
                if (yin < (-1) * theta)
                    y = -1;
                restart = 0;
                if (y - target[i] == 0)
                {
                    restart = 0;
                }
                else
                    restart = 1;

                for (int j = 0; j < 25; j++)
                {
                    weight[j] = weight[j] + (target[i] * algoInput[j][i] * alpha);
                }

                bias = bias + (target[i] * alpha);
            }
        }
        for(int i=0;i<25;i++) {
            sigma=sigma+weight[i]*inputs[i];
        }
        sigma=sigma+bias;

        if(sigma>theta) {
            
              out_Lbl.setText("x");
              out_Lbl.setStyle("-fx-font-size: 27");
        }
        else

            out_Lbl.setText("O");
            out_Lbl.setStyle("-fx-font-size: 27");

    }




    public void recognize(MouseEvent mouseEvent) {

        try {
            File directory =new File("D:\\perceptron_recognition\\src\\data\\");
            numberOfFiles = directory.list().length;
            for (int k = 0; k < numberOfFiles; k++) {
                File myObj = new File("D:\\perceptron_recognition\\src\\data\\" + "data" +k+ ".txt");
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    fileData = myReader.nextLine();

                }

                 fileData= fileData.substring(0,fileData.length()-1);
                 String fileSplitedData[] = fileData.split("@",26);

               for (int m = 0; m <26; m++) {
                   algoInput[m][k] = Integer.parseInt(fileSplitedData[m]);
                }
                target[k] = Integer.parseInt(fileSplitedData[25]);

                myReader.close();
            }

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        perceptronAlgorithm(algoInput,target);
    }

}
