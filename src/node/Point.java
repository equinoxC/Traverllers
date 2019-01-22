package node;

import operation.Distance;

public class Point {
	private double xpos;
	private double ypos;
	private Distance distance = new Distance();
	
	public Point(double xpos, double ypos) {
		this.setPos(xpos, ypos);
	}
	
	public Point(Point rhs) {
		this(rhs.xpos, rhs.ypos);
	}
	
	public Point(String str) {
		char[] temp = str.toCharArray();
		boolean first = true;
		String xpos="", ypos="";
		
		for(int i = 0; i < temp.length; i++) {
			if(temp[i]>='0'&&temp[i]<='9'||temp[i]=='.') {
				if(first) xpos += temp[i];
				else ypos += temp[i];
			}
			else if(temp[i]==',') {
				first = false;
			}
		}
		
		this.xpos = Double.valueOf(xpos);
		this.ypos = Double.valueOf(ypos);
	}
	
	public String toString() {
		return "[Xpos="+this.xpos+",Ypos="+this.ypos+"]";
	}
	
	public Point getPoint() {
		return new Point(this);
	}
	
	public double getXpos() {
		return this.xpos;
	}
	
	public void setXpos(double xpos) {
		this.xpos = xpos;
	}
	
	public double getYpos() {
		return this.ypos;
	}
	
	public void setYpos(double ypos) {
		this.ypos = ypos;
	}
	
	public void setPos(double xpos, double ypos) {
		this.setXpos(xpos);
		this.setYpos(ypos);
	}
	
	public void setPos(Point rhs) {
		this.setPos(rhs.xpos, rhs.ypos);
	}
	
	public double getDistance(Point rhs) {
		return (distance.getDistance(this.xpos, this.ypos, rhs.xpos, rhs.ypos));
	}
}