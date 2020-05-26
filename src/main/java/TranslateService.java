import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.language_translator.v3.model.TranslationResult;

import java.util.HashSet;
import java.util.Set;

public class TranslateService implements Page {

    private static final String Apikey = "KG1mjYk040N_3XnLQOdZkddfrkava-DU6U5OaiyETvbr";
    private static final String URL = "https://api.us-south.language-translator.watson.cloud.ibm.com/instances/47e63d6e-b7f2-4831-b5ea-7421d85f4a63";

    private TranslationGUI ui;
    private static LanguageTranslator languageTranslator;
    private static Set<String> modules;

    public static void setModules(){
        modules = new HashSet<>();
        modules.add("es-fr");
        modules.add("fr-es");
    }

    public static void setService(){
        IamAuthenticator authenticator = new IamAuthenticator(Apikey);
        languageTranslator = new LanguageTranslator("2018-05-01", authenticator);
        languageTranslator.setServiceUrl(URL);
    }

    public static boolean CheckModule(String[] modeList){
        if(modeList[0].equals("en") || modeList[1].equals("en") || modules.contains(modeList[0] + "-" + modeList[1])){
            return true;
        }else{
            return false;
        }
    }

    public static String Translate(String sentence, String mode){
        TranslateOptions translateOptions = new TranslateOptions.Builder().addText(sentence)
                .modelId(mode).build();
        TranslationResult result = languageTranslator.translate(translateOptions)
                .execute().getResult();
        return result.getTranslations().get(0).getTranslation();

    }

    public TranslateService(TranslationGUI gui){
        ui = gui;
        setService();
        setModules();
    }

    @Override
    public void start() {
        ui.cleanText();
        ui.addText("Please enter the message you want to translate");
    }

    @Override
    public void next() {
        String sentence = ui.getText();
        String[] modeList = ui.getTranslationMode();
        String translation = "";
        if(!modeList[0].equalsIgnoreCase(modeList[1])){
            if(CheckModule(modeList)){
                translation = Translate(sentence, modeList[0] + "-" + modeList[1]);
            }else{
                translation = "Sorry, we do not support this translation.";
            }
        }else{
            translation = sentence;
        }
        ui.setLeft(sentence);
        ui.setRight(translation);
    }

    @Override
    public void menu() {

    }

    @Override
    public void one() {

    }

    @Override
    public void two() {

    }

    @Override
    public void three() {

    }

    @Override
    public void four() {

    }
}
