package misc.scripts.merchanter.workers;

import misc.scripts.merchanter.MiscMerchanter;
import misc.scripts.merchanter.util.Trading;
import misc.scripts.merchanter.util.Util;

import org.hexbot.api.methods.input.Keyboard;
import org.hexbot.api.methods.interactable.Players;
import org.hexbot.api.methods.helper.Trade;
import org.hexbot.api.util.Filter;
import org.hexbot.api.util.Time;
import org.hexbot.api.wrapper.interactable.Player;
import org.hexbot.core.concurrent.script.Worker;

public class Advertise extends Worker{

	
	
	@Override
	public void run() {
		System.out.println("Advertise");
//		String traderName = null;
//		Player player = null;
//		if(traderName != null){
//			player = Util.getNearestPlayer(traderName);
//		}
//		if(player != null){
//			player.interact("Trade");
//			return;
//		}
		if(Trading.hasTrade() && Trading.acceptTrade()){
			MiscMerchanter.timeBeginTrade = System.currentTimeMillis();
			return;
		}
		for(String message : MiscMerchanter.messages){
			Keyboard.sendKeys(message, true);
			Time.sleep(500);
		}
	}

	@Override
	public boolean validate() {
		return !Trade.isOpen();
	}

}
