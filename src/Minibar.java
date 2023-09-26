
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.grammaticalframework.pgf.*;
import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.util.Scanner ;



public class Minibar{

    private PGF grammar = null;
    private Map<String,Concr> langs ;
    private String abstractName ;
    private TextFlow suggestionTextFlow;
    private TextField inputText ;
    private String startCat ;
    private String from ;
    private int startCatCount ;
    private int countFrom  ;
    private HBox hboxInputs ;
    private int inputTextsIndex ;
    private VBox translations;
    private String to ;
    private String translateInput ;
    private ArrayList<String> randomList ;
    private String[] UnsortedStartCat = {"Float","Int","String"};
    private List<String> deleteList ;
    private String word;
    private ArrayList<String> suggestions;
    private String control;
    private TextField initailT ;
    private TextFlow initailTf ;
    private HBox initialHbox ;
    private Scene scene ;
    private  Node focusedNode;
    Map<String,ArrayList<String>> tranlastionSentence= new HashMap<String, ArrayList<String>>();
    
    

   

    public Minibar(String path,TextFlow suggestionTextFlow,HBox hboxInputs ,TextField inputText,VBox translations,Scene scene){
        try {
             this.grammar = PGF.readPGF(path); //Stores the pgf file in grammar
            abstractName = grammar.getAbstractName();// stores the name of the pgf file in abstractName
            langs = grammar.getLanguages() ; // Stores the languages supported by the pgf file in langs
            this.suggestionTextFlow = suggestionTextFlow; // Populates the sugestions box
            this.hboxInputs = hboxInputs ;
            this.inputText = inputText;
            startCat = grammar.getStartCat(); // Stores the start catergories supported by the pgf
            from  = "Eng";  
            startCatCount = 0; 
            countFrom =0 ;
            inputTextsIndex =0;
            this.translations = translations;
            to = "All";
            translateInput ="";
            randomList = new ArrayList<>();
            word ="";
            initailT = inputText;
            initailTf = suggestionTextFlow;
            initialHbox = hboxInputs;
            this.scene =scene;
            tranlastionSentence.put(inputText.getId(),new ArrayList<>());
                
        } catch (FileNotFoundException e) {
           e.printStackTrace();
        }

        
        
    
    }

    // Get & Set Methods
    public PGF getGrammar(){
        return grammar ;
    }

    public void setGrammar(PGF grammar){
        this.grammar = grammar;
    }

    public List<String> PopuplateStartCat(){
      
        return grammar.getCategories();
    }

    public void setStartCat(String x){
        startCat = x;

    }

    public String getStartcat(){
        return startCat;
    }

    public void setFrom(String x){
        from = x ;
    }

    public String getFrom(){
        return from;
    }

    public void IncrementStartCount(){
        startCatCount ++;
    }

    public int getStartCount(){
        return startCatCount;
    }
    public void IncrementFromCount(){
        countFrom ++;
    }

    public int getFromCount(){
        return countFrom;
    }

    public void setSuggestionTextFlow(TextFlow x){
        suggestionTextFlow =x;
    }
    public TextFlow getSuggestionTextFlow(){
        return suggestionTextFlow;
    }

    public int getInputTextIndex(){
        return inputTextsIndex;
    }
    public void ReSetInputText(){
        inputTextsIndex = 0;
    }
    public void setToLangs(String x){
        to=x;
    }
    public String getTo(){
        return to;
    }

    public void setTextFiled(TextField x){
        inputText =x;
    }
    public TextField getTextField(){
        return inputText;
    }
    public void setInputBox(HBox x){
        hboxInputs =x;
    }
    public HBox getInputBox(){
        return hboxInputs;
    }
    public ArrayList<String> getRandomList(){
        return randomList;
    }
    public void decreInputIndex(){
        inputTextsIndex--;
    }

    public String getInputs(){
        return translateInput;
    }
    public void setDeleteList(List<String> x){
        deleteList = x;
    }
    public List<String> getDeleteList(){
        return deleteList;
    }
    public ArrayList<String> getSuggestions(){
        return suggestions;
    }
    public String getControlFromFillsug(){
        return control;
    }
    public Map<String,ArrayList<String>> getTranslationS(){
        return tranlastionSentence;
    }
    public void setTranslationS(Map<String,ArrayList<String>> x){
        tranlastionSentence = x;
    }
   
    


    ///// end of get and set methods
    public void initDeleteList(String translateInput){
        String [] inputArr = translateInput.split(" ");
        List<String> inl = Arrays.asList(inputArr);
         deleteList = new ArrayList<>(inl);
    }

    public ArrayList<String> Populate_From_To(){
        Iterator<String> abstract_langs = langs.keySet().iterator(); 

        ArrayList<String> langs = new ArrayList<>();

        String lang;
        while(abstract_langs.hasNext()){
            lang = abstract_langs.next().substring(abstractName.length());
            langs.add(lang);
        }

        return langs;
    }

    //This methods fills the suggestion box 
    public void fillSuggestion(String startCat,String lang,String input,String prefix,String control){
        suggestionTextFlow.getChildren().clear();   
        this.control=control;
        if (startCat!=null && lang!=null){
            Concr concr = langs.get(abstractName+lang);

            try {
                    //Checks if the startcat choosen is supported
                    if (!Arrays.asList(UnsortedStartCat).contains(startCat)){
                        Iterable<TokenProb> tokenProbs = concr.complete(startCat,input,prefix);
    
                        suggestions = new ArrayList<String>();
                        for(TokenProb tb : tokenProbs){
                            if(!(suggestions.contains(tb.getToken()))){
                                suggestions.add(tb.getToken());    
                            }
                        
                        }
                        
                    

                         //reoreder alphabetical   
                        suggestions.sort(String::compareTo);

                        if (input!=""){
                            //if the input is not empty create an array that contains the previously entered inputs
                            suggestions = predictText(abstractName,suggestions);
                        }
                      
                     
                        if (control=="Clear"){
                            suggestionTextFlow.getChildren().clear();
                        }
                            
                            
                        if(control=="Random"){
                                randomList = suggestions;
                                
                        }else{ 
                                
                            if(suggestions.isEmpty() & control=="Random"){

                                translate(from,to,translateInput);
                                
                                
                            }else if (suggestions.isEmpty() & control!="Random"){
                                    
                                    
                            
                                    CreateCorpus(abstractName); // create text file 
                                    tranlastionSentence.get(inputText.getId()).add(translateInput);
                                    
                                    

                            }

                            for (int i=0;i<suggestions.size();i++){
                                Button text = new Button(suggestions.get(i));
                                suggestionTextFlow.getChildren().add(text);
                                text.setOnMouseClicked(event -> onSuggestionItemClick(event, hboxInputs));
                            }
                        }
                    }else{ 
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setHeaderText(startCat+" is no longer supported");
                        alert.setContentText("Please choose another option!");

                        // Display the alert
                        alert.showAndWait();
                    }
                

            } catch (ParseError e) {
                    System.out.println(e);
            }
        }
    }

    public void CreateCorpus(String fileName){
        

            try {
                
                FileWriter fw = new FileWriter("C:\\Users\\Neo Ramotlou\\OneDrive - University of Cape Town\\UCT_2023\\Minibar\\minibar\\"+fileName+".txt",true);
                //BufferedWriter bw = new BufferedWriter(fw);
                fw.write(translateInput+"\n");
                fw.close();
                } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public ArrayList<String> predictText(String fileName, ArrayList<String> suggestions){
          Map<String,ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
            ArrayList<String> predictList = new ArrayList<>();
            ArrayList<String>  joinedList = new ArrayList<>();
           


        try {
            File file = new File("C:\\Users\\Neo Ramotlou\\OneDrive - University of Cape Town\\UCT_2023\\Minibar\\minibar\\"+fileName+".txt");
            Scanner sc = new Scanner(file);
          

            while(sc.hasNextLine()){
                String[] arrLine = sc.nextLine().split(" ");
                

                for(int i=0;i<arrLine.length-1;i++){
                    
                    if(!(map.containsKey(arrLine[i]))){
                        map.put(arrLine[i],new ArrayList<>());
                        map.get(arrLine[i]).add(arrLine[i+1]);
                    }else{
                        map.get(arrLine[i]).add(arrLine[i+1]);
                    }
                        
                }
            }
         
            if (!(map.containsKey(word))){
                 sc.close();
                return suggestions;
            }else{
                if (map.get(word).addAll(suggestions)){
                 joinedList = map.get(word);
                for(String w : joinedList){
                    if(!(predictList.contains(w))){
                        predictList.add(w);    
                    }
                }
            }
           
        }
         
            sc.close();
            
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
      return predictList;

    }
     

    public void translate(String from , String To , String inputText){
    translations.getChildren().clear();  
    Concr concr = langs.get(abstractName+"Eng");
    //String inputText="that boring cheese is delicious"; Testing
    try {
        Iterator<ExprProb> exprProbs = concr.parse(startCat, inputText).iterator();

        while (exprProbs.hasNext()) {
            // get the parsed expression
            Expr expr = exprProbs.next().getExpr();
            // translating to every language available in langs
            Iterator<String> langNames = langs.keySet().iterator();
            System.out.println("translate "+abstractName+To);
            while (langNames.hasNext()) {
                String langName = langNames.next();// name of language
                if(To=="All"){
                Concr langGrammar = langs.get(langName);// gammar of the language
            
                TextField a = new TextField( langName.substring(abstractName.length()) + " : " + langGrammar.linearize(expr));
                a.setEditable(false);
                translations.getChildren().add(a);
                }else if(langName.equals(abstractName+To)){
                    Concr langGrammar = langs.get(langName);// gammar of the language
                    TextField a = new TextField( langName.substring(abstractName.length()) + " : " + langGrammar.linearize(expr));
                    a.setEditable(false);
                    translations.getChildren().add(a);
                }

            
            
            }

        }
        
    } catch (ParseError e) {
        System.out.println(e);
    }

    }

     @FXML
    public void onSuggestionItemClick(MouseEvent event, HBox hboxInputs) {
  
        inputText.requestFocus();
        Button clickedwordButton = (Button) event.getSource();
        String  i = clickedwordButton.getText();
        Text text = new Text(i);
        word = i;
        
        if (inputTextsIndex ==0)
        { translateInput = i;

        }else{translateInput = translateInput +" "+i;  }

        translate(from,to,translateInput);
        initDeleteList(translateInput);
        fillSuggestion(startCat, from ,translateInput, "","Clicked word");// Fill text flow based on button clicked
        hboxInputs.getChildren().add(inputTextsIndex,text);

        inputTextsIndex++; 
    
    }
     public void setSetenceFocus(){
         focusedNode = scene.getFocusOwner();
         if (focusedNode instanceof TextField){
            if(!focusedNode.getId().equals("input_text")){
                String textfiledId = focusedNode.getId();
            TextField focusedTextField = (TextField) focusedNode;
            String[] idNo = textfiledId.split("_");
            System.out.println("Id:"+idNo[1]);
            TextFlow correspondingTextFlow = (TextFlow) scene.lookup("#" + "textFlow_"+idNo[1]);
            HBox correspondingHBox = (HBox) scene.lookup("#" +"HBox_"+idNo[1]);
            setInputBox(correspondingHBox );
            setSuggestionTextFlow(correspondingTextFlow);
            setTextFiled(focusedTextField);
            }else{
                 setInputBox(initialHbox);
                 setSuggestionTextFlow(initailTf);
                setTextFiled(initailT);
            }
        }


    }
    




    
}
