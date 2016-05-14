package DialogManagementSystem;

import org.alicebot.ab.Chat;

public class Dialog extends Chat
{
    public Dialog(GoBot gobot, boolean doWrites) {
		super(gobot.getBot(), doWrites, "0");
	}
}
