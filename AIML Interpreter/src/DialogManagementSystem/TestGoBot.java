package DialogManagementSystem;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.ChatTest;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;

import opendial.DialogueSystem;
import opendial.Settings;
import opendial.domains.Domain;
import opendial.gui.TextOnlyInterface;
import opendial.modules.simulation.Simulator;
import opendial.readers.XMLDomainReader;

public class TestGoBot {
	  public static void testDialog (GoBot gobot, boolean doWrites, boolean traceMode) {
	        Dialog dialogSession = new Dialog(gobot, doWrites);
	        // Chat chatSession = new Chat(bot, doWrites, userProfile);
	        gobot.getBot().brain.nodeStats();
	        MagicBooleans.trace_mode = traceMode;
	        String textLine="";
	        while (true) {
	            textLine = IOUtils.readInputTextLine("Human");
	            if (textLine == null || textLine.length() < 1)  textLine = MagicStrings.null_input;
	            if (textLine.equals("q")) System.exit(0);
	            else if (textLine.equals("wq")) {
	            	gobot.getBot().writeQuit();
	                System.exit(0);
	            }
	            else {
	                String request = textLine;
	                if (MagicBooleans.trace_mode) System.out.println("STATE="+request+":THAT="+dialogSession.thatHistory.get(0).get(0)+":TOPIC="+dialogSession.predicates.get("topic"));
	                String response = dialogSession.multisentenceRespond(request);
	                while (response.contains("&lt;")) response = response.replace("&lt;","<");
	                while (response.contains("&gt;")) response = response.replace("&gt;",">");
	                IOUtils.writeOutputTextLine("Robot", response);
	                //System.out.println("Learn graph:");
	                //bot.learnGraph.printgraph();
	                /*
	        		DialogueSystem system = new DialogueSystem();
	        		String domainFile = System.getProperty("domain");
	        		String dialogueFile = System.getProperty("dialogue");
	        		String simulatorFile = System.getProperty("simulator");

	        		system.getSettings().fillSettings(System.getProperties());
	        		if (domainFile != null) {
	        			Domain domain;
	        			try {
	        				domain = XMLDomainReader.extractDomain(domainFile);

	        			}
	        			catch (RuntimeException e) {
	        				system.displayComment("Cannot load domain: " + e);
	        				e.printStackTrace();
	        				domain = XMLDomainReader.extractEmptyDomain(domainFile);
	        			}
	        			system.changeDomain(domain);
	        		}
	        		if (dialogueFile != null) {
	        			system.importDialogue(dialogueFile);
	        		}
	        		if (simulatorFile != null) {
	        			Simulator simulator = new Simulator(system,
	        					XMLDomainReader.extractDomain(simulatorFile));

	        			system.attachModule(simulator);
	        		}
	        		Settings settings = system.getSettings();
	        		system.changeSettings(settings);

	        		if (!settings.showGUI) {
	        			system.attachModule(new TextOnlyInterface(system));
	        		}

	        		system.startSystem();
					*/
	            }
	        }
	    }
}
