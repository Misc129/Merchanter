package misc.scripts.merchanter.util;

import java.util.List;

import org.hexbot.api.methods.helper.Trade.TradeItem;
import org.hexbot.api.methods.interactable.Npcs;
import org.hexbot.api.methods.interactable.Players;
import org.hexbot.api.methods.node.Widgets;
import org.hexbot.api.util.Filter;
import org.hexbot.api.wrapper.interactable.Npc;
import org.hexbot.api.wrapper.interactable.Player;
import org.hexbot.api.wrapper.node.WidgetComponent;

public class Util {

	public static final int ID_WIDGET_CHATBOX = 137;
	public static final int ID_COMPONENT_CHATBOX = 2;
	public static final int ID_COMPONENT_CHATLINE = 0;
	//14 is the lowest, 2 is the highest
	public static final int[] IDS_COMPONENT_CHATLINE = {14,12,10,8,6,4,2};
	
	public static Player getNearestPlayer(final String name){
		Player[] players = Players.getLoaded(new Filter<Player>(){
			@Override
			public boolean accept(Player arg0) {
				return arg0.getName().equals(name);
			}});
		return players != null && players.length > 0 ? players[0] : null;
	}
	
	
	
//	public static boolean tradeItemsEqual(TradeItem[] a, TradeItem[] b){
//		if(a == null) return b == null;
//		if(b == null) return a == null;
//		if(a.length != b.length)
//			return false;
//		for(int i = 0; i < a.length; i++){
//			if(a[i].getId() != b[i].getId() || a[i].getStackSize() != b[i].getStackSize()){
//				return false;
//			}
//		}
//		return true;
//	}
	
}
