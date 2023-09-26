import static org.junit.Assert.assertEquals;
import org.junit.Test;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import org.grammaticalframework.pgf.PGF;
import org.grammaticalframework.pgf.*;
import java.io.FileNotFoundException;

public class MinibarTest {
    

    PGF pgf;

    @Test
    public void testGetGrammarFile() {
        try {
            pgf = PGF.readPGF("C:\\Users\\Neo Ramotlou\\Downloads\\Foods.pgf");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(pgf.getAbstractName(), "Foods");
        // Successfully runs if a grammar file was successfully addedd

        try {
            pgf = PGF.readPGF("C:/Users/27715/Downloads/Phrasebook.pgf");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(pgf.getAbstractName(), "Phrasebook");
        // Successfully runs if a grammar file was successfully addedd

    }
    
    @Test
    public void testGetFrom() {

        try{
        var m = new Minibar("C:\\Users\\Neo Ramotlou\\Downloads\\Foods.pgf", new TextFlow(),new HBox(),new TextField(), new VBox(), new Scene(null));
        assertEquals("Eng",m.getFrom());

    }catch(Exception e){ System.out.println(e);}  

    }

   
}
