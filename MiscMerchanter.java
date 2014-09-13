package misc.scripts.merchanter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import misc.scripts.merchanter.util.ExchangeItem;
import misc.scripts.merchanter.util.Values;
import misc.scripts.merchanter.workers.Advertise;
import misc.scripts.merchanter.workers.BuyItems;

import org.hexbot.api.listeners.Paintable;
import org.hexbot.api.methods.Skills;
import org.hexbot.api.methods.input.Mouse;
import org.hexbot.api.util.Time;
import org.hexbot.api.wrapper.node.Item;
import org.hexbot.core.concurrent.script.Condition;
import org.hexbot.core.concurrent.script.Info;
import org.hexbot.core.concurrent.script.TaskScript;
import org.hexbot.core.concurrent.script.Type;

@Info(
		name = "MiscMerchanter",
		author = "Misc",
		description = "v1",
		type = Type.RUNECRAFTING
		)
public class MiscMerchanter extends TaskScript implements Paintable{

	public static long timeBeginTrade, timeStart, timeLastRemovePoint, timeNextRemovePoint, millis, seconds, minutes, hours;;
	public static int innerOrbitAngle = 0, innerOrbitAngle2 = 180, outerOrbitAngle = 90, outerOrbitAngle2 = 270;

	public static List<String> messages;
	public static List<ExchangeItem> exchangeItems;
	private static LinkedList<Point> points = null;

	public static boolean debugging, isBuyingItems, isSellingItems;

	public MiscMerchanter(){
		timeStart = System.currentTimeMillis();
		points = new LinkedList<Point>();
		messages = new ArrayList<String>();
		exchangeItems = new ArrayList<ExchangeItem>();
		exchangeItems.add(new ExchangeItem(Values.ID_LAW_RUNE, 250, 1000));
		exchangeItems.add(new ExchangeItem(Values.ID_DEATH_RUNE, 250, 1000));
		isBuyingItems = true;
		submit(new Advertise(), new BuyItems());
	}

	public static void waitFor(Condition condition, int timeout){
		for(int i = 0; i < timeout; i += 100){
			if(condition.validate())
				return;
			Time.sleep(100);
		}
	}

	private void updatePoints(){
		if(points.isEmpty()){
			points.add(0,Mouse.getLocation());
			timeNextRemovePoint = System.currentTimeMillis() + 500;
		}
		else if(!Mouse.getLocation().equals(points.get(0))){
			points.add(0,Mouse.getLocation());
		}
		if(!points.isEmpty() && System.currentTimeMillis() > timeNextRemovePoint){
			timeLastRemovePoint = timeNextRemovePoint;
			timeNextRemovePoint = System.currentTimeMillis() + 30;
			points.removeLast();
		}
	}

	private void drawPoints(Graphics g){
		if(points.size() > 1){
			Point prev = points.getFirst();
			for(Point p : points){
				if(!prev.equals(p))
					g.drawLine(prev.x, prev.y, p.x, p.y);
				prev = p;
			}
		}
	}

	private void updateOrbits(){
		if(innerOrbitAngle >= 360)
			innerOrbitAngle = 0;
		if(innerOrbitAngle2 >= 360)
			innerOrbitAngle2 = 0;
		if(outerOrbitAngle >= 360)
			outerOrbitAngle = 0;
		if(outerOrbitAngle2 >= 360)
			outerOrbitAngle2 = 0;
		innerOrbitAngle+=2;
		innerOrbitAngle2+=2;
		outerOrbitAngle-=2;
		outerOrbitAngle2-=2;
	}

	private void drawCursorOrbit(Graphics g){
		g.setColor(Color.green);
		g.drawArc(Mouse.getX() - 10, Mouse.getY() - 10, 20, 20, innerOrbitAngle, 135);
		g.drawArc(Mouse.getX() - 10, Mouse.getY() - 10, 20, 20, innerOrbitAngle2, 135);
		g.drawArc(Mouse.getX() - 15, Mouse.getY() - 15, 30, 30, outerOrbitAngle, 135);
		g.drawArc(Mouse.getX() - 15, Mouse.getY() - 15, 30, 30, outerOrbitAngle2, 135);

	}

	private void drawMouse(Graphics g){
		g.setColor(Color.red);
		g.fillRect(Mouse.getX() - 1, Mouse.getY() - 5, 2, 10);
		g.fillRect(Mouse.getX() - 5, Mouse.getY() - 1, 10, 2);
	}

	private void formatTime(){
		millis = System.currentTimeMillis() - timeStart;
		hours = millis / (1000 * 60 * 60);
		millis -= hours * (1000 * 60 * 60);
		minutes = millis / (1000 * 60);
		millis -= minutes * (1000 * 60);
		seconds = millis / 1000;
	}
	
	private void drawDebuggingInfo(Graphics g){
		g.setColor(Color.red);
		g.drawString("debugging", 410, 50);
	}

	@Override
	public void paint(Graphics g) {
		drawMouse(g);

		updatePoints();
		drawPoints(g);

		updateOrbits();
		drawCursorOrbit(g);
		
		g.setColor(Color.gray);
		g.fillRoundRect(350, 347, 147, 112, 10, 10);
		g.setColor(Color.BLACK);
		g.drawString("MiscMerchanter", 380, 360);
		formatTime();
		g.drawString("Runtime: " + hours +":"+ minutes + ":" + seconds, 355, 375);
	
		if(debugging){
			drawDebuggingInfo(g);
		}
	}

}
