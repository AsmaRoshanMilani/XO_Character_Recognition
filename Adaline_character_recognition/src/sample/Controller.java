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

    @FXML
    void xClicked(ActionEvent event) {
        inputs[25] = 1;

    }

    @FXML
    void yClicked(ActionEvent event) {
        inputs[25] = -1;
    }

    int numberOfFiles = 0;
    String fileData = " ";
    String st="";
    int[] inputs = new int[26];
    int digit = 0;
    int[][] algoInput;
    int[] target;
    ArrayList<Button> buttons;
    float alpha=0.1f;
    float bias=0.0f;
    float [] y;
    float E=2;
    float [] weight=new float[25];
    int sigma=0;
    int loopIndex=0;

    public void initialize() {
        File directory =new File("D:\\adaline_recognition\\src\\data\\");
        numberOfFiles = directory.list().length;
        algoInput = new int[26][numberOfFiles];
        target = new int[numberOfFiles];
        y= new float[numberOfFiles];
        for(int t=0;t<25;t++) {
            weight[t]=0;
        }
        for(int t=0;t<numberOfFiles;t++) {
            y[t]=0;
        }
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


        st=" ";
        try {
            File directory =new File("D:\\adaline_recognition\\src\\data\\");
            numberOfFiles = directory.list().length;

            for (int j =0 ; j<26; j++){
                st=st+inputs[j]+"@";
            }
            st = st.substring(1,st.length());
            File file = new File("D:\\adaline_recognition\\src\\data\\"+"data"+numberOfFiles+".txt");
            FileWriter writer = new FileWriter(file);
            writer.write(st);
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void adalineAlgorithm(int[][] algoInput,int[] target) {
        File directory =new File("D:\\adaline_recognition\\src\\data\\");
        numberOfFiles = directory.list().length;
        while(E>1.5) {
            E=0;
            for(int i=0;i<numberOfFiles;i++) {
                y[i]=bias+(algoInput[0][i]*weight[0])+(algoInput[1][i]*weight[1]);
                bias=bias+(target[i]-y[i])*alpha;
                for(int j=0;j<25;j++) {
                    weight[j]=weight[j]+(target[i]-y[i])*algoInput[j][i]*alpha;
                }

            }
            for(int i=0;i<numberOfFiles;i++) {
                y[i]=bias+(algoInput[0][i]*weight[0])+(algoInput[1][i]*weight[1]);
                E=E+((target[i]-y[i])*(target[i]-y[i]));
            }
           loopIndex++;
            if(loopIndex>1000);
            break;
        }


        for(int i=0;i<25;i++) {
            sigma= (int) (sigma+(weight[i]*inputs[i]));
        }
        sigma= (int) (sigma+bias);

        if(sigma>=0) {
            out_Lbl.setText("x");
            out_Lbl.setStyle("-fx-font-size: 20");
        }
        else
            out_Lbl.setText("O");
            out_Lbl.setStyle("-fx-font-size: 20");


    }




    public void recognize(MouseEvent mouseEvent) {

        try {
            File directory =new File("D:\\adaline_recognition\\src\\data\\");
            numberOfFiles = directory.list().length;
            for (int k = 0; k < numberOfFiles; k++) {
                File myObj = new File("D:\\adaline_recognition\\src\\data\\" + "data" +k+ ".txt");
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
        adalineAlgorithm(algoInput,target);
    }

}
