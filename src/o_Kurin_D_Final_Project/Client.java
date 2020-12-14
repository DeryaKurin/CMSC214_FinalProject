package o_Kurin_D_Final_Project;
	
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.net.Socket;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class Client extends Application {
	//Creating Stream objects for transferring data
    private DataInputStream fromServer = null;
    private DataOutputStream toServer = null;
    
    @Override
    public void start(Stage primaryStage) {
        BorderPane bp = new BorderPane();
        
        TextArea ta = new TextArea();
        bp.setCenter(new ScrollPane(ta));
        
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        
        final ComboBox medicineComboBox = new ComboBox();
        medicineComboBox.getItems().addAll(
            "Aspirin",
            "Acetaminophen",
            "Ibuprofen",
            "Valsartan",
            "Insulin Glargine",  
            "Rifaximin",
            "Lisinopril",
            "Amlodipine",
            "Guaifenesin",
            "Edoxaban"
        );

	    
	    
        Button btn = new Button("Search");
              
	    
	    
        Label lb1 = new Label("Medication name: ");

	    
	    gp.add(lb1, 0,1);
	    gp.add(medicineComboBox, 1,1);
	    
	    
	    gp.add(btn, 2,1);
	    gp.setHgap(20); 

	    
	    
	    bp.setPadding(new Insets(25, 25, 25, 25));
	    bp.setCenter(new ScrollPane(ta));
	    bp.setTop(gp);
	    
        
        Scene scene = new Scene(bp, 300, 200);
        primaryStage.setTitle("Final Project");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        try {
            Socket socket = new Socket("localhost", 8000);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException ex) {
            ta.appendText(ex.toString() + "\n");
        }
        
        btn.setOnAction(e -> {
            try {
            	//Getting the values from text fields
            	
                String medicine = (String) medicineComboBox.getValue();

                
                //Sending data to Server
                toServer.writeUTF(medicine);
                toServer.flush();
                
                //Read data from server 
                String medicalData = fromServer.readUTF();  
                //Set text for Client
                ta.appendText(medicalData);
              
            }
            catch (IOException ex) {
                System.err.println(ex);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
