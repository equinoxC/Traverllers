package node;
import java.util.ArrayList;

public class City {
	// 这个类是一个城市的结点集合，其余的请看看其他类的描述
	public ArrayList< ScenicNode > ScList;
	public ArrayList< HotelNode > HtList;
	public ArrayList< StationNode> StList;
	
	
	public City(ArrayList< ScenicNode > ScList, ArrayList< HotelNode > HtList, ArrayList< StationNode > StList) {
		this.setScList(ScList);
		this.setHtList(HtList);
		this.setStList(StList);
	}
	
	public City() {
		ScList = new ArrayList< ScenicNode >();
		HtList = new ArrayList< HotelNode >();
		StList = new ArrayList< StationNode >();
	}
	
	public ArrayList< ScenicNode >getScList() {
		return this.ScList;
	}
	
	public ArrayList< HotelNode > getHtList() {
		return this.HtList;
	}
	
	public ArrayList< StationNode > getStList() {
		return this.StList;
	}
	
	public void setScList(ArrayList< ScenicNode > ScList) {
		this.ScList = ScList;
	}
	
	public void setHtList(ArrayList< HotelNode > HtList) {
		this.HtList = HtList;
	}
	
	public void setStList(ArrayList< StationNode > StList) {
		this.StList = StList;
	}
}