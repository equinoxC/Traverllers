package node;
public class HotelNode extends UniformNode {
	private int level;	// 宾馆星级
	
	public HotelNode(NodeType type, int id, int cityID, String name, double unitPrice, Point pos, int level) {
		super(type, id, cityID, name, unitPrice, pos);
		this.level = level;
	}
	
	public HotelNode(HotelNode rhs) {
		this(rhs.getType(), rhs.getID(), rhs.getCityID(), rhs.getName(), rhs.getUnitPrice(), rhs.getPos(), rhs.level);
	}
	
	public String toString() {
		return "["+super.toString()+','+level+"]\n";
	}
	
	public int getLevel() {
		return this.level;
	}
}
