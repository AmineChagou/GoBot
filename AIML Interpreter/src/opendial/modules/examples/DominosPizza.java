                                                            

package opendial.modules.examples;

import java.util.logging.*;
import java.util.Collection;

import opendial.DialogueState;
import opendial.DialogueSystem;
import opendial.Settings;
import opendial.domains.Domain;
import opendial.modules.Module;
import opendial.readers.XMLDomainReader;

/**

 */
public class DominosPizza implements Module {

	// logger
	public final static Logger log = Logger.getLogger("OpenDial");

	// the dialogue system
	DialogueSystem system;
    //DominosPizzaForm form;
    
	// whether the module is paused or active
	boolean paused = true;

	/**
	 * Creates a new instance of the flight-booking module
	 * 
	 * @param system the dialogue system to which the module should be attached
	 */
	public DominosPizza(DialogueSystem system) {
		this.system = system;
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
			if (action.equals("ActivateSellDomain")) {
				String userRequest = state.queryProb("u_u_c_request").getBest().toString();
				Domain domain;
				String domainFile    = Settings.getActiveBot().getSettings().domains.get("Sell");
				domain = XMLDomainReader.extractDomain(domainFile);
				log.info("Domain from " + domainFile + " successfully extracted");
				Settings.getActiveBot().changeDomain(domain);
				Settings.getActiveBot().addContent("u_u_c", userRequest);
			}else if (action.equals("ActivateChatDomain")) {
				Domain domain;
				String domainFile    = Settings.getActiveBot().getSettings().domains.get("Chat");
				domain = XMLDomainReader.extractDomain(domainFile);
				log.info("Domain from " + domainFile + " successfully extracted");
				Settings.getActiveBot().changeDomain(domain);
			}else if (action.equals("GetPrice")) {
				
				// extract items added to cart and derive prices from database
				
				int Price = 30;
				String newAction = "DisplayPrice(" + Price + ")";
				Settings.getActiveBot().addContent("a_m", newAction);
			}
			else if (action.equals("Order")) {

				
				// load Form and initialize with user information (delivery adress + payment information + items + phone number)
				// display form 
				String pizzaChoice = state.queryProb("choice").getBest().toString();
				String drinks =
						state.queryProb("Drinks").getBest().toString();
				String nbpersons = state.queryProb("NbPersons").getBest().toString();

				// In a real system, the system database should be modified here
				// to
				// actually perform the order. Here, we just print a small
				// message.
				String info = "Ordered: " + pizzaChoice + " pizza " + drinks
						+ " for " + nbpersons + " persons " ;
				log.fine(info);
			}else if (action.equals("Close")) {
				String newAction = "None";
				Settings.getActiveBot().addContent("a_m", newAction);		
				Settings.activeBot = "masterBot";
				//  Starting active bot 
				/*String domainFile    = Settings.getActiveBot().getSettings().domains.get("Chat");
				Domain domain = XMLDomainReader.extractDomain(domainFile);
				log.info("Domain from " + domainFile + " successfully extracted");
				Settings.getActiveBot().changeDomain(domain);
				Settings.getActiveBot().startSystem();*/
				newAction = "ResumeDialogue";
				Settings.getActiveBot().addContent("a_m", newAction);				
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
