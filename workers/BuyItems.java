package misc.scripts.merchanter.workers;

import misc.scripts.merchanter.MiscMerchanter;
import misc.scripts.merchanter.util.ExchangeItem;
import misc.scripts.merchanter.util.Util;
import misc.scripts.merchanter.util.Values;

import org.hexbot.api.methods.helper.Trade;
import org.hexbot.api.methods.helper.Trade.TradeItem;
import org.hexbot.api.methods.input.Keyboard;
import org.hexbot.api.methods.node.Inventory;
import org.hexbot.api.util.Time;
import org.hexbot.api.wrapper.node.InventoryItem;
import org.hexbot.core.concurrent.script.Condition;
import org.hexbot.core.concurrent.script.Worker;

public class BuyItems extends Worker{

	//private boolean playerIdle;
	
	private static final long TIMEOUT_INTERVAL = 20000;//milliseconds

	@Override
	public void run() {
		System.out.println("Buy items");
		//if player has not made any offer and timeout has passed
		if(System.currentTimeMillis() - MiscMerchanter.timeBeginTrade > TIMEOUT_INTERVAL){
			Trade.declineFirst();
			Trade.declineSecond();
		}
		
		waitForOffer();

		int offerAmount = 0;
		for(TradeItem t : Trade.getTraderItems()){
			offerAmount += getGPValue(t);
		}

		offerGP(offerAmount);
		MiscMerchanter.waitFor(new Condition(){
			@Override
			public boolean validate() {
				// TODO Auto-generated method stub
				return isValidTrade();
			}}, 5000);
		Time.sleep(800,1200);
		/*
		 * if first trade window and valid trade
		 * 	accept first trade window
		 * 
		 * if second trade window and valid trade
		 * 	accept second trade window
		 */
	}

	@Override
	public boolean validate() {
		//playerIdle = true;
		MiscMerchanter.timeBeginTrade = System.currentTimeMillis();
		return Trade.isOpen() && MiscMerchanter.isBuyingItems;
	}
	
	public boolean isValidTrade(){
		TradeItem[] traderItems = Trade.getTraderItems();
		if(traderItems == null || traderItems.length == 0)
			return false;
		int tradeValue = 0;
		for(TradeItem traderItem : Trade.getTraderItems()){
			tradeValue += getGPValue(traderItem);
		}
		return tradeValue == getCurrentGPOffer();
	}
	
	public int getCurrentGPOffer(){
		int result = 0;
		for(TradeItem item : Trade.getMyItems()){
			if(item.getId() == Values.ID_GP){
				result = item.getStackSize();
			}
		}
		return result;
	}

	//wait for trader's item offer to change for 5s at a time (a change resets 5s timer)
	//TODO set a 1 to 2 minute timeout just incase
	public void waitForOffer(){
		TradeItem[] prevState = Trade.getTraderItems();
		int i = 0;
		//wait 5s for players trade offer to change
		while(i < 5000){
			TradeItem[] state = Trade.getTraderItems();
			if(!Util.tradeItemsEqual(prevState, state)){
				//if changed, reset 5s timer
				prevState = state;
				i = 0;
			}
			Time.sleep(100);
			i += 100;
		}
	}
	
	//offer the GP value into trade screen (minus what is already offered)
	private void offerGP(int amount){
		amount = amount - getCurrentGPOffer();
		InventoryItem coins = Inventory.getItem(Values.ID_GP);
		if(coins.interact("Offer-x")){
			Time.sleep(1500,2000);
			Keyboard.sendKeys(""+amount);
		}
	}

	public int getGPValue(TradeItem t){
		if(t == null)
			return 0;
		for(ExchangeItem e : MiscMerchanter.exchangeItems){
			if(e.getId() == t.getId()){
				return t.getStackSize() * e.getPrice();
			}
		}
		return 0;
	}

}
