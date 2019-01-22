package operation;

import java.util.Collections;
import java.util.Comparator;

import node.City;
import node.HotelNode;
import node.ScenicNode;
import node.StationNode;

public class SelectNode {

	private UserInput userInput;
	
	public SelectNode() {
		userInput = UserInput.getInstance();
	}
	
	public UserInput getUserInput() {
		return userInput;
	}
	
	/**
	 * 0.得到出发城市
	 */
	public String getDepartureCity() {
		return userInput.getDeparture();
	}
	
	/**
	 * 1.根据目的地选定旅游城市
	 */
	public String chooseCity() {
		return userInput.getArrival();
	}
	
	/**
	 * 2.按照旅游主题筛选景点
	 */
	public void chooseSceneNode(City city, int themeNum1, int themeNum2) {
		Comparator<ScenicNode> compareSumTheme = new Comparator<ScenicNode>() {
			@Override
			public int compare(ScenicNode o1, ScenicNode o2) {
				if (o1.getSumTag(themeNum1, themeNum2) < o2.getSumTag(themeNum1, themeNum2)) {
					return 1;
				} else if (o1.getSumTag(themeNum1, themeNum2) > o2.getSumTag(themeNum1, themeNum2)) {
					return -1;
				} else {
					return 0;
				}
				
			}
		};
		
		Comparator<ScenicNode> compareTheme1 = new Comparator<ScenicNode>() {
			@Override
			public int compare(ScenicNode o1, ScenicNode o2) {
				if (o1.getTag(themeNum1) < o2.getTag(themeNum1)) {
					return 1;
				} else if (o1.getTag(themeNum1) > o2.getTag(themeNum1)) {
					return -1;
				} else {
					return 0;
				}
				
			}
		};
		
		Comparator<ScenicNode> compareTheme2 = new Comparator<ScenicNode>() {
			@Override
			public int compare(ScenicNode o1, ScenicNode o2) {
				if (o1.getTag(themeNum2) < o2.getTag(themeNum2)) {
					return 1;
				} else if (o1.getTag(themeNum2) > o2.getTag(themeNum2)) {
					return -1;
				} else {
					return 0;
				}
				
			}
		};
		
		Comparator<ScenicNode> compareTotalTheme = new Comparator<ScenicNode>() {
			@Override
			public int compare(ScenicNode o1, ScenicNode o2) {
				if (o1.getTotalTag() < o2.getTotalTag()) {
					return 1;
				} else if (o1.getTotalTag() > o2.getTotalTag()) {
					return -1;
				} else {
					return 0;
				}
				
			}
		};
		
		Collections.sort(city.getScList(), compareSumTheme.thenComparing(compareTheme1).thenComparing(compareTheme2).thenComparing(compareTotalTheme));
	}
	
	/**
	 * 3.选择从某车站出发的第一个景点
	 */
	public void chooseFirstScenicNode(City city, StationNode startNode) {
		Comparator<ScenicNode> compareDistance = new Comparator<ScenicNode>() {
			@Override
			public int compare(ScenicNode o1, ScenicNode o2) {
				if (o1.getNodeDistance(startNode) > o2.getNodeDistance(startNode)) {
					return 1;
				} else if (o1.getNodeDistance(startNode) < o2.getNodeDistance(startNode)) {
					return -1;
				} else {
					return 0;
				}
				
			}
		};
		Collections.sort(city.getScList(), compareDistance);
	}
	
	/**
	 * 4.选择从某车站出发的第一个景点
	 */
	public void chooseFirstScenicNode(City city, HotelNode startNode) {
		Comparator<ScenicNode> compareDistance = new Comparator<ScenicNode>() {
			@Override
			public int compare(ScenicNode o1, ScenicNode o2) {
				if (o1.getNodeDistance(startNode) > o2.getNodeDistance(startNode)) {
					return 1;
				} else if (o1.getNodeDistance(startNode) < o2.getNodeDistance(startNode)) {
					return -1;
				} else {
					return 0;
				}
				
			}
		};
		Collections.sort(city.getScList(), compareDistance);
	}
	
	/**
	 * 5.从某一景点出发到另一景点
	 */
	public void chooseNextScenicNode(City city, ScenicNode lastNode) {
		Comparator<ScenicNode> compareDistance = new Comparator<ScenicNode>() {
			@Override
			public int compare(ScenicNode o1, ScenicNode o2) {
				if (o1.getNodeDistance(lastNode) > o2.getNodeDistance(lastNode)) {
					return 1;
				} else if (o1.getNodeDistance(lastNode) < o2.getNodeDistance(lastNode)) {
					return -1;
				} else {
					return 0;
				}
				
			}
		};
		Collections.sort(city.getScList(), compareDistance);
	}
	
	/**
	 * 6.选择备选景点
	 */
	public void chooseEndScenicNode(City city, ScenicNode lastNode) {
		Comparator<ScenicNode> compareTime = new Comparator<ScenicNode>() {
			@Override
			public int compare(ScenicNode o1, ScenicNode o2) {
				if (o1.getHourSpend() > o2.getHourSpend()) {
					return 1;
				} else if (o1.getHourSpend() < o2.getHourSpend()) {
					return -1;
				} else {
					return 0;
				}
				
			}
		};
		
		Comparator<ScenicNode> compareDistance = new Comparator<ScenicNode>() {
			@Override
			public int compare(ScenicNode o1, ScenicNode o2) {
				if (o1.getNodeDistance(lastNode) > o2.getNodeDistance(lastNode)) {
					return 1;
				} else if (o1.getNodeDistance(lastNode) < o2.getNodeDistance(lastNode)) {
					return -1;
				} else {
					return 0;
				}
				
			}
		};
		Collections.sort(city.getScList(), compareTime.thenComparing(compareDistance));
	}
	
	/**
	 * 7.按照当日结束地点选酒店
	 */
	public void chooseHotelNode(City city, ScenicNode endNode) {
		Comparator<HotelNode> comparePrice = new Comparator<HotelNode>() {
			@Override
			public int compare(HotelNode o1, HotelNode o2) {
				if (o1.getUnitPrice() > o2.getUnitPrice()) {
					return 1;
				} else if (o1.getUnitPrice() < o2.getUnitPrice()) {
					return -1;
				} else {
					return 0;
				}
				
			}
		};
		
		Comparator<HotelNode> compareDistance = new Comparator<HotelNode>() {
			@Override
			public int compare(HotelNode o1, HotelNode o2) {
				if (o1.getNodeDistance(endNode) > o2.getNodeDistance(endNode)) {
					return 1;
				} else if (o1.getNodeDistance(endNode) < o2.getNodeDistance(endNode)) {
					return -1;
				} else {
					return 0;
				}
				
			}
		};
		Collections.sort(city.getHtList(), compareDistance.thenComparing(comparePrice));
	}
	
	/**
	 * 8.选择从最后一天的酒店出发到最近到车站
	 */
	public void chooseLeaveStationNode(City city, HotelNode endNode) {
		Comparator<StationNode> compareDistance = new Comparator<StationNode>() {
			@Override
			public int compare(StationNode o1, StationNode o2) {
				if (o1.getNodeDistance(endNode) > o2.getNodeDistance(endNode)) {
					return 1;
				} else if (o1.getNodeDistance(endNode) < o2.getNodeDistance(endNode)) {
					return -1;
				} else {
					return 0;
				}
				
			}
		};
		Collections.sort(city.getStList(), compareDistance);
	}
	
}
