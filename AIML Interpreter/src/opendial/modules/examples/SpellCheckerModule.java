package opendial.modules.examples;
import java.io.*;
import java.util.*;
import java.lang.reflect.Constructor;
import java.util.Collection;

import opendial.DialogueState;
import opendial.DialogueSystem;
import opendial.Settings;
import opendial.modules.Module;
import com.swabunga.spell.event.*;
import com.swabunga.spell.engine.*;
import com.swabunga.spell.engine.Word;

public class SpellCheckerModule implements Module,SpellCheckListener {


	private final String DICTIONARY_FILE = System.getProperty("user.dir") + "/jazzy/resources/english.0";
	private final String string1 = "This is a sample test string with no misspellings.";   // first test string
	private final String string2 = "Viagra will make your male m'ember larger";            // second test string

	private SpellChecker spellChecker;
	private List<String> misspelledWords;


	// the dialogue system
	DialogueSystem system;

	// whether the module is paused or active
	boolean paused = true;


	public SpellCheckerModule(DialogueSystem system){
		this.system = system;
		createDictionary();
	    spellChecker.addSpellCheckListener(this);

	}
	/**
	 * Starts the module.
	 */
	@Override
	public void start() {
		paused = false;
	}

	/**
	 * Checks whether the updated variables contains the system action and (if yes)
	 * whether the system action value is "FindOffer" or "Book". If the value is
	 * "FindOffer", checks the price of the order (faked here to 179 or 299 EUR) and
	 * adds the new action "MakeOffer(price)" to the dialogue state. If the value is
	 * "Book", simply write down the order on the system output.
	 * 
	 * @param state the current dialogue state
	 * @param updatedVars the updated variables in the state
	 */
	@Override
	public void trigger(DialogueState state, Collection<String> updatedVars) {
		if (updatedVars.contains("u_u")) {
              
		        String userUtterance =  Settings.getActiveBot().getContent("u_u").getBest().toString();     
			    misspelledWords = new ArrayList<String>();
		        String userUtteranceCorrected=getCorrectedLine(userUtterance);
		        Settings.getActiveBot().addContent("u_u_c", userUtterance);
		        String userUtterance2 = Settings.getActiveBot().getContent("u_u").getBest().toString();     
		        System.out.println(userUtteranceCorrected);
				
		}
		
	

	    
	    

	}

	/**
	 * Pauses the module.
	 * 
	 * @param toPause whether to pause the module or not
	 */
	@Override
	public void pause(boolean toPause) {
		paused = toPause;
	}

	/**
	 * Returns whether the module is currently running or not.
	 * 
	 * @return whether the module is running or not.
	 */
	@Override
	public boolean isRunning() {
		return !paused;
	}
	  private void createDictionary()
	  {

	    File dict = new File(DICTIONARY_FILE);
	    try
	    {
	      spellChecker = new SpellChecker(new SpellDictionaryHashMap(dict));
	    }
	    catch (FileNotFoundException e)
	    {
	      System.err.println("Dictionary File '" + dict + "' not found! Quitting. " + e);
	      System.exit(1);
	    }
	    catch (IOException ex)
	    {
	      System.err.println("IOException occurred while trying to read the dictionary file: " + ex);
	      System.exit(2);
	    }
	  }
	  public List<String> getSuggestions(String misspelledWord){
		   
		  @SuppressWarnings("unchecked")
		  List<Word> suggestion_ = spellChecker.getSuggestions(misspelledWord, 0);
		  List<String> suggestions = new ArrayList<String>();
		  for (Word suggestion : suggestion_){
		   suggestions.add(suggestion.getWord());
		  }
		   
		  return suggestions;
		 }
	  public String getCorrectedLine(String line){
		  List<String> misSpelledWords = getMisspelledWords(line);
		   if(misSpelledWords!= null)
		   {
			for (String misSpelledWord : misSpelledWords){
				List<String> suggestions = getSuggestions(misSpelledWord);
				if (suggestions.size() == 0)
				   continue;
				String bestSuggestion = suggestions.get(0);
				line = line.replace(misSpelledWord, bestSuggestion);
			}
		   }
		  return line;
		 }
	  public List<String> getMisspelledWords(String text) {
		  StringWordTokenizer texTok = new StringWordTokenizer(text,new TeXWordFinder());
		  spellChecker.checkSpelling(texTok);
		  return misspelledWords;
		 }
	@Override
	 public void spellingError(SpellCheckEvent event) {
		  event.ignoreWord(true);
		  misspelledWords.add(event.getInvalidWord());
	}
		 

	 



}
