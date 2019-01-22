package node;
public class UniformNode {
	// 这只是个父类，方便操作用的。
	
	private NodeType type;	// 枚举类型，SCENIC旅游景点，HOTEL酒店结点，STATION车站结点
	private int id;			// excel上第一列的 景点id
	private int cityID;		// excel上的 城市id
	private String name; 	// 节点名字
	private double unitPrice;	// 使用该节点需要的价格（景点的票价，酒店的单价，车站暂时没有给我价格信息预留为0）
	private Point pos;		// 经纬度坐标
	
	public UniformNode(NodeType type, int id, int cityID, String name, double unitPrice, Point pos) {
		this.type = type;
		this.id = id;
		this.cityID = cityID;
		this.name = name;
		this.unitPrice = unitPrice;
		this.pos = new Point(pos);
	}
	
	public String toString() {
		return "["+this.id+','+this.cityID+','+this.name+','+this.unitPrice+','+this.pos+"]";
	}
	
	public UniformNode(UniformNode rhs) {
		this(rhs.type, rhs.id, rhs.cityID, rhs.name, rhs.unitPrice, rhs.pos);
	}
	
	// 一系列getter-setter函数
	public NodeType getType() {
		return this.type;
	}
	
	public int getID() {
		return this.id;
	}
	
	public int getCityID() {
		return this.cityID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public double getUnitPrice() {
		return this.unitPrice;
	}
	
	public Point getPos() {
		return new Point(this.pos);
	}
	
	// 用于计算任何两个结点之间的距离
	public double getNodeDistance(UniformNode rhs) {
		return this.pos.getDistance(rhs.pos);
	}
}