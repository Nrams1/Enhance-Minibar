import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;


public class Controller implements Initializable {


    @FXML
    private AnchorPane MainPane;
 

    @FXML
    private Button addSentence;

    @FXML
    private Button clearButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextFlow textflow;

    @FXML
    private ComboBox<String> fromDropDown;

    @FXML
    private Button randomButton;

    @FXML
    private TextField input_text;

    @FXML
    private TextField sentence1;

    @FXML
    private ComboBox<String> startcatDropDown;

    @FXML
    private HBox suggestinBox;

    @FXML
    private ListView<String> suggestinBox1;


    @FXML
    private AnchorPane translation;

    @FXML
    private TitledPane transltionScrollablepanel;

    @FXML
    private Button uploadButton;

    @FXML
    private VBox wordVBox;

    @FXML
    private Button deleteSentence;


    @FXML
    private Button add_button;

    @FXML
    private Button clear_button;

    @FXML
    private Button delete_button;

    @FXML
    private ChoiceBox<String> from_CB;

    @FXML
    private Text from_text;


    @FXML
    private Button upload_button;

    @FXML
    private Button random_button;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ChoiceBox<String> startCat_CB;

    @FXML
    private Text startCat_text;

    @FXML
    private ChoiceBox<String> to_CB;

    @FXML
    private VBox translations;

    @FXML
    private HBox text_section;
    
    @FXML
    private ImageView image;


    
    public Minibar minibar;
    private Scene scene;
    private  String Rtext ="" ;
    private ArrayList<String> sent;
    private Random rand ;
    private int randomCount =0;
    Sentence sentencepanel = new Sentence();
  




    @FXML
    public void setAppScene(Scene scene){
        this.scene = scene;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    upload_button.setOnAction(this::getGrammarFile);
        
    }

    public void getGrammarFile(ActionEvent event){

        try {
            

            // Everytime a new file is uploaded , we clear our translation and texfields
            clear(); // clear Hbox
            translations.getChildren().clear(); // clear translations


            // Load File 
            ExtensionFilter extension = new ExtensionFilter("PGF files", "*.pgf");
            FileChooser fileChooser =new FileChooser();
            fileChooser.getExtensionFilters().add(extension);
            File selectedFile  = fileChooser.showOpenDialog(null);
                    
            if(!(selectedFile==null)){
                upload_button.setText(selectedFile.getName());
                // Minibar is created using Grammar file , TextFlow ,TextSection , Input Text
                minibar = new Minibar(selectedFile.getPath(),textflow,text_section,input_text,translations,scene);
            }
            
            // Populate choice boxes 
            startCat_CB.getItems().clear();
            startCat_CB.getItems().addAll(minibar.PopuplateStartCat());
            from_CB.getItems().clear();
            to_CB.getItems().clear();
            to_CB.getItems().add("All");
            ArrayList<String> langs = minibar.Populate_From_To();
            from_CB.getItems().addAll(langs);
            to_CB.getItems().addAll(langs);

            
            //Default values
            startCat_CB.setValue(minibar.getGrammar().getStartCat()); 
            from_CB.setValue("Eng");
            to_CB.setValue("All");
                    
        } catch (Exception e) { System.out.println("File Not Loaded");}
                
    }
    // used to clear 
     public void clear(){
        input_text.requestFocus();
        text_section.getChildren().clear();
        input_text.clear();
        text_section.getChildren().add(input_text);
    }




    // Getter Methods
    public ChoiceBox<String> getStartCat_CB(){
        return startCat_CB;
    }

    public ChoiceBox<String> getFrom_CB(){
        return from_CB;
    }

    public ChoiceBox<String> getTo_CB(){
        return to_CB ;
    }

    public TextField getInputText(){
        return input_text;
    }

    public Button getRandomBtn(){
        return random_button;
    }
    public String getRandomText(){
        return Rtext;
    }
   


    @FXML
    private void addSentenceButtonClick(ActionEvent event) {
        // Add the TextField and TextFlow to the VBox
        
        TextFlow textflow = sentencepanel.getTextFlow();
        HBox hbox = sentencepanel.getHBox();
        TextField textfield = sentencepanel.getTextField();
        hbox.getChildren().add(textfield);
        ///adding textfield focus
        wordVBox.getChildren().addAll(hbox,textflow);
        input_text = textfield ; 
        minibar.setTextFiled(textfield);
        minibar.setSuggestionTextFlow(textflow);
        minibar.setInputBox(hbox);
        minibar.ReSetInputText(); // reset input text to zero
        //textfield.requestFocus(); // Add sentence textfield as focus node
        minibar.fillSuggestion(minibar.getStartcat(),minibar.getFrom(),"","","");
        scrollPane.setVvalue(1.0);
        minibar.setSetenceFocus();
        minibar.getTranslationS().put(minibar.getTextField().getId(),new ArrayList<>());
        

    }
    @FXML
    private void deleteSentenceButtonClick(ActionEvent event) {
        Node focusedNode = scene.getFocusOwner();
        sentencepanel.deleteSentence(focusedNode);
        TextFlow correspondingTextFlow = (TextFlow) scene.lookup("#" + sentencepanel.getdeleteTextFlowId());
        HBox correspondingHBox = (HBox) scene.lookup("#" + sentencepanel.getdeleteHboxId());
        wordVBox.getChildren().removeAll(correspondingHBox, correspondingTextFlow);
    }


    @FXML
    private void deleteButtonClicked(ActionEvent event) {
        Node focusNode =  scene.getFocusOwner();
        String inputs = "" ;
    

        if(focusNode instanceof TextField){
            if (input_text.getText()!=""){
                 input_text.setText(""+input_text.getText().substring(0, input_text.getText().length() - 1));
                 
            } else{ 
            text_section.getChildren().remove(minibar.getInputTextIndex()-1);
            List<String> iniList = minibar.getDeleteList();
            int indexOfLastItem = iniList.size()-1;
            
          
            iniList.remove(indexOfLastItem);
            minibar.setDeleteList(iniList);
            

            for(int i =0;iniList.size()>i; ++i)
            {
                if (i==0){inputs = iniList.get(i); }else{
                    inputs = inputs +" "+ iniList.get(i);
                }
                
            }
         
            minibar.fillSuggestion(minibar.getStartcat(),minibar.getFrom(),inputs,"","");
            minibar.decreInputIndex();
        }

        }
    }




    @FXML
    private void clearButtonClicked(ActionEvent event) {

            minibar.setSetenceFocus();
            minibar.getInputBox().getChildren().clear();
            minibar.getTextField().clear();
            minibar.getSuggestionTextFlow().getChildren().clear();
            minibar.getInputBox().getChildren().add(input_text);
            minibar.setSuggestionTextFlow(minibar.getSuggestionTextFlow());
            minibar.fillSuggestion(minibar.getStartcat(), minibar.getFrom(),"", "", "Clear");
            minibar.ReSetInputText();
            
       

       
    }

    @FXML
    private void randomButtonClicked(ActionEvent event) {
        minibar.getInputBox().getChildren().clear();
        String sc = minibar.getStartcat();
        String f = minibar.getFrom();
        int count=0;
      
        try{
            do {
                count++;
                minibar.fillSuggestion(sc,f,Rtext,"","Random");
                sent = minibar.getRandomList();
                if(!(sent.isEmpty())){
                    getRandom();
                }
                

            } while (!(sent.isEmpty()) && count<10 );

            
                String[] a = Rtext.split(" ");
         
            for (int i=0;i<a.length;i++){
                Text text = new Text(a[i]);
                minibar.getInputBox().getChildren().add(i,text);
            }
                minibar.getInputBox().getChildren().add(minibar.getTextField());
                minibar.translate(minibar.getFrom(),minibar.getTo(), Rtext);
                minibar.getTranslationS().get(minibar.getTextField().getId()).add(Rtext);
    
                Rtext="";
                randomCount=0;
        
        }catch (Exception e){System.out.println(e);}
        
    
    }
        //Get Random Words from predicted words
        public void getRandom() {

            rand = new Random();
            
                int  x = rand.nextInt(sent.size());
    
            
            if (randomCount==0){
                Rtext = sent.get(x);
                    randomCount++;
                }else{ Rtext = Rtext + " " + sent.get(x); randomCount++ ;}
            

        

        }

    @FXML
    private void Info_button(ActionEvent event) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Minibar");
        alert.setHeaderText("About Minibar");
        alert.setContentText(" Version : 1.0 \n Date : 2023-09-25 \n Java : 17.0.8.7 \n Visual Studio Code :1.82.2 ");

        // Display the alert
        alert.showAndWait();

    }






}


