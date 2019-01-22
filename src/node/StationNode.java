package node;

public class StationNode extends UniformNode {
	private StationType stType;	// 枚举类型，RAL铁路车站，AIR机场，BUS长途车站
	
	public StationNode(NodeType type, int id, int cityID, String name, double unitPrice, Point pos, StationType stType) {
		// aStation.unitPrice is currently UNUSED, just set to 0.
		super(type, id, cityID, name, unitPrice, pos);
		this.stType = stType;
	}
	
	public StationNode(StationNode rhs) {
		this(rhs.getType(), rhs.getID(), rhs.getCityID(), rhs.getName(), rhs.getUnitPrice(), rhs.getPos(), rhs.stType);
	}
	
	public String toString() {
		return "["+super.toString()+','+stType.toString()+"]\n";
	}
	
	public StationType getStType() {
		return this.stType;
	}
}