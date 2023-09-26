
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class App extends Application {
    Controller controller;
  
    @Override
    public void start(Stage primaryStage)  {

        try {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("LandingPage.fxml"));
        AnchorPane root = new AnchorPane();
        loader.setRoot(root);
        loader.load();
        Scene scene = new Scene(root);
       

        // Pass the scene to the controller
        Controller controller = loader.getController();
        controller.setAppScene(scene);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Mininbar");
        controller.setAppScene(scene);
        
        //listeners 
        controller.getStartCat_CB().getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> { 
            controller.minibar.setStartCat(newValue);
            controller.minibar.IncrementStartCount();
            if(controller.minibar.getStartCount()!=1){
            controller.minibar.fillSuggestion(newValue,controller.minibar.getFrom(),"","","");}else{
                controller.minibar.fillSuggestion(controller.minibar.getStartcat(),controller.minibar.getFrom(),"","",""); 
            }});
        
            controller.getFrom_CB().getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
        controller.minibar.IncrementFromCount();
        if (controller.minibar.getFromCount()!=1) {
            controller.minibar.setFrom(newValue);
            controller.minibar.fillSuggestion(controller.minibar.getStartcat() ,newValue,"","","");
        }});

        controller.getTo_CB().getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            controller.minibar.setToLangs(newValue);
            if (controller.minibar.getSuggestions().isEmpty() & controller.minibar.getControlFromFillsug()!="Random"){
                controller.minibar.translate("Eng", newValue,controller.minibar.getInputs() );
            }else if(controller.minibar.getSuggestions().isEmpty() & controller.minibar.getControlFromFillsug()=="Random"){
                
                controller.minibar.translate("Eng", newValue,controller.getRandomText() );
            }
        });

        

        controller.getInputText().textProperty().addListener((obs, oldText, newText) -> {
            controller.minibar.setSetenceFocus();
            controller.minibar.fillSuggestion(controller.minibar.getStartcat() ,controller.minibar.getFrom(),"",newText,"");
            
        
        });


        } catch (Exception e) {
             System.out.println(e);
        }
       
    
    } 

    public Controller getController(){
        return controller;
    }
   

    public static void main(String[] args) {
        launch(args);
    }
}
