package node;
public class ScenicNode extends UniformNode{
	public static int tagNum;
	
	private double hourSpend;	// 游玩一个经典需要花的时间
	private double[] tagVec; 	// 每个景点在各个（目前是5个）标签上的评分(0~5)。
	/* 使用tagVec[]根据用户偏好计算权值:
	 * 根据用户的偏好自己写一个权值向量W (double[5]),
	 * 那么特定用户偏好下某个经典的价值就是 sigma(W[i]*tagVec[i])
	 */	
	
	public ScenicNode(NodeType type, int id, int cityID, String name, double unitPrice, Point pos, double hourSpend, double[] tagVec) {
		super(type, id, cityID, name, unitPrice, pos);
		this.hourSpend = hourSpend;
		this.tagVec = tagVec.clone();
	}
	
	public String toString() {
		String st = "[";
		for(int i = 0; i < tagVec.length; i++) {
			st = st + tagVec[i];
			if(i < tagVec.length - 1) 
				st += ',';
		}
		st += "]";
		return "["+super.toString()+','+hourSpend+','+st+"]\n";
	}
	
	public ScenicNode(ScenicNode rhs) {
		this(rhs.getType(), rhs.getID(), rhs.getCityID(), rhs.getName(), rhs.getUnitPrice(), rhs.getPos(), rhs.hourSpend, rhs.tagVec);
	}
	
	public double getHourSpend() {
		return this.hourSpend;
	}
	
	public double[] getTagVec() {
		return this.tagVec.clone();
	}
	
	public double getTag(int i) {
		return this.tagVec[i];
	}
	
	public double getSumTag(int i, int j) {
		return this.tagVec[i] + this.tagVec[j];
	}
	
	public double getTotalTag() {
		double totalTag = 0.0;
		for (int i = 0; i < tagVec.length; i++) {
			totalTag += tagVec[i];
		}
		return totalTag;
	}
}