package DialogManagementSystem;
import opendial.DialogueState;
import opendial.DialogueSystem;
import opendial.Settings;
import opendial.bn.BNetwork;
import opendial.bn.distribs.CategoricalTable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import opendial.bn.BNetwork;
import opendial.bn.distribs.CategoricalTable;
import opendial.bn.distribs.IndependentDistribution;
import opendial.bn.distribs.MultivariateDistribution;
import opendial.bn.distribs.ProbDistribution;
import opendial.bn.values.Value;
import opendial.datastructs.Assignment;
import opendial.datastructs.SpeechData;
import opendial.domains.Domain;
import opendial.domains.Model;
import opendial.gui.GUIFrame;
import opendial.gui.TextOnlyInterface;
import opendial.modules.AudioModule;
import opendial.modules.DialogueImporter;
import opendial.modules.DialogueRecorder;
import opendial.modules.ForwardPlanner;
import opendial.modules.Module;
import opendial.modules.RemoteConnector;
import opendial.modules.simulation.Simulator;
import opendial.readers.XMLDomainReader;
import opendial.readers.XMLDialogueReader;
import org.alicebot.ab.*;

import java.io.*;
import java.util.HashMap;


public class GoBot extends DialogueSystem {
	//Bot 
	private Bot bot;
	public Bot getBot(){
		return this.bot;
	}
    public GoBot(Domain domain,String name, String path, String action)
    {
    	super(domain);
    	bot = new Bot(name,path,action);
    	
    }
    public GoBot(String domainFile,String name, String path, String action)
    {
    	super(domainFile);
    	bot = new Bot(name,path,action);
    	
    }
	
}

