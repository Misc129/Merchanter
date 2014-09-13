package misc.scripts.merchanter.util;

import java.util.List;

import org.hexbot.api.methods.node.Widgets;
import org.hexbot.api.wrapper.node.Item;
import org.hexbot.api.wrapper.node.WidgetComponent;

public class Trading {

	public static final int ID_WIDGET_CHATBOX = 137;
	public static final int ID_COMPONENT_CHATBOX = 2;
	public static final int ID_COMPONENT_CHATLINE = 0;
	
	public static final int WIDGET = 335;
	public static final int COMPONENT_ACCEPT_FIRSTSCREEN = 17;
	public static final int COMPONENT_DECLINE_FIRSTSCREEN = 18;
	
	//children of COMPONENT_MY_ITEMS and COMPONENT_TRADER_ITEMS \
	//are the items indexes [0,27] correspond to the inventory 
	//in order left to right top to bottom
	public static final int COMPONENT_MY_ITEMS = 48;
	public static final int COMPONENT_TRADER_ITEMS = 50;
	
	
	public static boolean hasTrade(){
		WidgetComponent w = Widgets.getChild(ID_WIDGET_CHATBOX, ID_COMPONENT_CHATBOX);
		if(w != null){
			w = w.getChild(ID_COMPONENT_CHATLINE);
			return w.getText().contains("wishes to trade");
		}
		return false;
	}
	
	public static boolean acceptTrade(){
		WidgetComponent w = Widgets.getChild(ID_WIDGET_CHATBOX, ID_COMPONENT_CHATBOX);
		if(w != null)
			w = w.getChild(ID_COMPONENT_CHATLINE);
		if(w != null)
			return w.interact("Accept trade");
		return false;
	}
	
	public static boolean isFirstScreenOpen(){
		//TODO
		return false;
	}
	
	public static boolean isSecondScreenOpen(){
		//TODO
		return false;
	}
	
	public static List<Item> getMyOffer(){
		//TODO
		return null;
	}
	
	public static List<Item> getTraderOffer(){
		//TODO
		return null;
	}
	
}
