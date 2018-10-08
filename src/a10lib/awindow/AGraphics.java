package a10lib.awindow;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class AGraphics{

	private Graphics g;
	
	public AGraphics(Graphics g) {
		setGraphics(g);
	}
	
	public void drawCenteredText(String s, int x, int y) {
	    FontMetrics fm = g.getFontMetrics();
	    int n = x - (fm.stringWidth(s)/2) ;
	    int m = y - (fm.getHeight()/2);
	    g.drawString(s, n, m);
	}
	
	public int stringWidth(String str) {
		return g.getFontMetrics().stringWidth(str);
	}
	
	public int stringHeight() {
		return g.getFontMetrics().getHeight();
	}

	public void setGraphics(Graphics g) {
		this.g= g;
	}
	
	public Graphics getGraphics() {
		return g;
	}
	
	public void setColor(Color c) {
		g.setColor(c);
	}
	
	public void setColor(int b) {
		setColor(new Color(b));
	}
	
	public void setColor(int red,int green,int blue) {
		setColor(new Color(red,green,blue));
	}
	
	public void fillRect(int x,int y,int w,int h) {
		g.fillRect(x, y, w, h);
	}
	
	public void drawRect(int x,int y,int w,int h) {
		g.drawRect(x, y, w, h);
	}
	
	public void drawText(String txt,int x,int y) {
		g.drawString(txt, x, y);
	}
	
	public void drawImage(AImage img,int x,int y) {
		g.drawImage(img.getImage(),x,y,null);
	}
	
	public void setFontSize(int s) {
		setFont(new Font(getFont().getFontName(),Font.PLAIN,s));
	}
	
	public void setFont(Font f) {
		g.setFont(f);
	}
	
	public Font getFont() {
		return g.getFont();
	}
	
	@Override
	public boolean equals(Object o) {
		AGraphics ag = (AGraphics)o;
		if(ag.g.equals(g)) {
			return true;
		}
		return false;
	}
	
}
