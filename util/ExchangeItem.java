package misc.scripts.merchanter.util;

public class ExchangeItem {

	public ExchangeItem(int id, int price, int qty){
		_id = id;
		_price = price;
		_qty = qty;
	}
	
	private int _id;
	private int _price;
	private int _qty;//maximum quantity to trade
	
	public int getId(){
		return _id;
	}
	
	public void setId(int newValue){
		_id = newValue;
	}
	
	public int getPrice(){
		return _price;
	}
	
	public void setPrice(int newValue){
		_price = newValue;
	}
	
	public int getQty(){
		return _qty;
	}
	
	public void setQty(int newValue){
		_qty = newValue;
	}
	
}
