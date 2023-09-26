import java.util.Optional;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextFlow;

public class Sentence{
    

    private int textFieldCount = 1; // Initialize a counter for TextField IDs
    private int textFlowCount = 1; // Initialize a counter for TextFlow IDs
    private int hboxCount = 1;
    private String textFieldId;// String ID
    private String textFlowId;// String ID
    private TextField deleteTextfield;
    private String deleteTextFlowId;
    private String deleteHboxId;

    private String HboxId ;
    private TextField newTextField;
    private TextFlow newTextFlow ;
    private HBox newHBox ;



    //Add
    public TextField getTextField(){
        // Create a new TextField
         newTextField = new TextField();
        newTextField.setPrefHeight(26); // Customize height as needed
        newTextField.setPrefWidth(150); // Customize width as needed
        textFieldId = "textField_" + textFieldCount; // Unique ID
        newTextField.setId(textFieldId);
 
        textFieldCount++;
        return newTextField;
    }
 
    public TextFlow getTextFlow(){
        // Create a new TextFlow
         newTextFlow = new TextFlow();
        newTextFlow.setPrefHeight(70); // Customize height as needed
        newTextFlow.setPrefWidth(899); // Customize width as needed
        newTextFlow.setStyle("-fx-border-style: dotted;");
        newTextFlow.setFocusTraversable(false);
        textFlowId = "textFlow_" + textFlowCount; // Unique ID
        newTextFlow.setId(textFlowId);

        textFlowCount++;
        return newTextFlow;
    
    }

    public HBox getHBox(){
        newHBox = new HBox();
        HboxId = "HBox_"+hboxCount;
        newHBox.setId(HboxId);
        newHBox.setPadding(new Insets(0,0,0,5));
        newHBox.setSpacing(5);
        hboxCount++;
        return newHBox;
        
    }


    public String getHboxId(){
        return HboxId;
    }
    
    public String getTextFieldId(){
        return textFieldId;

    }
    public String getTextFlowId(){
        return textFlowId;
    }


    // Delete 

    public void  setdeleteTextfield(TextField deleteTextField){
        this.deleteTextfield = deleteTextField;
    }

    public void setdeleteTextFlowId(){
        String textFieldId = deleteTextfield.getId();
    
        // Construct the ID of the corresponding TextFlow
        String textFlowId= "textFlow_" + textFieldId.split("_")[1];   
        // Find the corresponding TextFlow
        this.deleteTextFlowId = textFlowId;
    }

    public void setdeleteHboxId(){
        String textFieldId = deleteTextfield.getId();
    
        // Construct the ID of the corresponding TextFlow
        String HBoxId= "HBox_" + textFieldId.split("_")[1];   
        // Find the corresponding TextFlow
        this.deleteHboxId = HBoxId;
    }

    public TextField getdeleteTextfield(){
        return deleteTextfield;
    }

    public String getdeleteTextFlowId(){
        return deleteTextFlowId;
    }

    public String getdeleteHboxId(){
        return deleteHboxId;
    }

    @FXML
    public void deleteSentence(Node focusedNode){
            // Get the currently focused TextField
        if (focusedNode instanceof TextField) {
            TextField focusedTextField = (TextField) focusedNode;
            if(focusedTextField.getId().equals("input_text")){
                // Show a confirmation dialog
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Delete Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Cannot delete first sentence.");
                
                // Customize the style of the dialog
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("popup.css").toExternalForm());
                
                Optional<ButtonType> result = alert.showAndWait();
                
                // Check if the user clicked the OK button
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Do Not Perform the deletion
                
                }

            }else{
                // Show a confirmation dialog
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Delete Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete sentence?");
                
                // Customize the style of the dialog
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("popup.css").toExternalForm());
                
                Optional<ButtonType> result = alert.showAndWait();
                
                // Check if the user clicked the OK button
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Perform the deletion
                    deleteTextfield = focusedTextField;
                    setdeleteTextfield(deleteTextfield);
                    setdeleteTextFlowId();
                    setdeleteHboxId();

                }
            }
    
        }

    }
   
    

}