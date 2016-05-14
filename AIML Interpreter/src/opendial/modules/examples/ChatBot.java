package opendial.modules.examples;

import java.util.logging.*;
import java.lang.reflect.Constructor;
import java.util.Collection;

import opendial.DialogueState;
import opendial.DialogueSystem;
import opendial.modules.Module;
import org.alicebot.ab.*;
public class ChatBot implements Module {

	// logger
	private Bot bot;
	private Chat chatSession;
	public Bot getBot(){
		return this.bot;
	}
	
	public final static Logger log = Logger.getLogger("OpenDial");

	// the dialogue system
	DialogueSystem system;

	// whether the module is paused or active
	boolean paused = true;

	/**
	 * Creates a new instance of the flight-booking module
	 * 
	 * @param system the dialogue system to which the module should be attached
	 */
	public ChatBot(DialogueSystem system,String name, String path, String action ){
		this.system = system;
		bot = new Bot(name,path,action);
		chatSession = new Chat(bot);
	}
	public ChatBot(DialogueSystem system){
		this.system = system;
		String botName = system.getSettings().botName;
		String botPath = system.getSettings().botPath;
		bot  	    = new Bot(botName, botPath, "chat");
		chatSession = new Chat(bot);
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
		if (updatedVars.contains("a_m") && state.hasChanceNode("a_m")) {
			String action = state.queryProb("a_m").getBest().toString();

			if (action.equals("Chat")) {
		        String userUtterance = this.system.getContent("u_u_c").getBest().toString();
		        String response = chatSession.multisentenceRespond(userUtterance);
				String newAction = "BotResponse(" + response + ")";
				system.addContent("a_m", newAction);
			}
	
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

}
