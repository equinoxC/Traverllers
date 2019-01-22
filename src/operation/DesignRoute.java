package operation;

import java.util.ArrayList;

import node.City;
import node.HotelNode;
import node.ScenicNode;
import node.StationNode;
import node.forInput;

public class DesignRoute {

	private static final double Play_Time = 10.0;
	private static forInput fInput;
	private ArrayList<City> Cities;
	private int cityNum;
	private SelectNode selectNode;
	private StationNode startNode;
	private ArrayList<ScenicNode> planNode;
	private double playTime;
	private double totalExpense;
	private double maxCnt;
	private double velocity;
	private City departureCity;
	private ArrayList<ScenicNode> sceneSet;
	private HotelNode finalNode;
	private StationNode fromNode;
	
	public DesignRoute() throws Exception {
		fInput = new forInput();
		fInput.processExcel();
		Cities = fInput.getCities();
		cityNum = fInput.getCityNum();
		selectNode = new SelectNode();
		planNode = new ArrayList<ScenicNode>();
		playTime = Play_Time;
		totalExpense = 0.0;
		maxCnt = 4;
		velocity = 10;
		for (int i = 0; i < cityNum; i++) {
			City tmpCity = Cities.get(i);
			if (tmpCity.ScList.get(i).getCityID() == Integer.parseInt(selectNode.getDepartureCity())) {
				departureCity = tmpCity;
			}
		}
		sceneSet = new ArrayList<ScenicNode>();
	}
	
	public forInput getFInput() {
		return fInput;
	}
	
	public ArrayList<City> getCities() {
		return Cities;
	}
	
	public int getCityNum() {
		return cityNum;
	}
	
	public SelectNode getSelectNode() {
		return selectNode;
	}
	
	public StationNode getStartNode() {
		return startNode;
	}
	
	public ArrayList<ScenicNode> getPlanNode() {
		return planNode;
	}
	
	public void selectScenicNode(City tmpCity) {
		int[] compareTheme = new int[2];
		for (int j = 0; j < selectNode.getUserInput().getThemeNum().length; j++) {
			compareTheme[j] = selectNode.getUserInput().getThemeNum(j);
		}
		selectNode.chooseSceneNode(tmpCity, compareTheme[0], compareTheme[1]);
		for (int j = tmpCity.getScList().size()-1; j >= 5*selectNode.getUserInput().getTime(1); j--) {
			tmpCity.getScList().remove(j);
		}
		for (int j = 0; j < tmpCity.getScList().size(); j++) {
			sceneSet.add(tmpCity.getScList().get(j));
		}
	}
	
	public void setStartStation() {
		fromNode = null;
		for (int i = 0; i < cityNum; i++) {
			City fromCity = Cities.get(i);
			if (fromCity.ScList.get(i).getCityID() == Integer.parseInt(selectNode.getDepartureCity())) {
				for (StationNode tmpsn: fromCity.StList) {
					if (tmpsn.getID() == Integer.parseInt(selectNode.getUserInput().getDepartureStop())) {
						fromNode = tmpsn;
					}
				}
			}
		}
	}
	
	public void makePlan(City tmpCity, StationNode sn) {
		System.out.println("第1天：");
		
		ScenicNode lastNode = null;
		startNode = sn;
		selectNode.chooseFirstScenicNode(tmpCity, startNode);
		lastNode = tmpCity.getScList().get(0);
		planNode.add(lastNode);
		playTime = playTime - lastNode.getNodeDistance(startNode)/velocity - lastNode.getHourSpend();
		tmpCity.getScList().remove(lastNode);
		totalExpense = totalExpense + lastNode.getUnitPrice();
		maxCnt--;
		System.out.println("  " + fromNode.getName() + "-->" + startNode.getName());
		System.out.println("  " + startNode.getName() + "-->" + lastNode.getName());
		System.out.println("    路程：" + String.format("%.2f", lastNode.getNodeDistance(startNode)) + 
				"km  路程用时：" + String.format("%.2f", lastNode.getNodeDistance(startNode)/velocity) + 
				"h  游玩时间：" + lastNode.getHourSpend() + 
				"h  游玩费用：" + lastNode.getUnitPrice() + "元");
		
		chooseNode(tmpCity, lastNode, velocity);

		for (int j = 2; j <= selectNode.getUserInput().getTime(1); j++) {
			System.out.println("第"+j+"天：");
			playTime = Play_Time;
			maxCnt = 4;
			makePlan2(tmpCity, tmpCity.getHtList().get(0),velocity);
		}
		
		selectNode.chooseLeaveStationNode(tmpCity, finalNode);
		StationNode leave = null;
		String[] transportMethod = selectNode.getUserInput().getTransportation();
		for (int k = 0; k < transportMethod.length; k++) {
			if (transportMethod[k].equals("ship")) {
				transportMethod[k] = "NA_ST";
				System.out.println(k+":"+transportMethod[k]);
			} else if (transportMethod[k].equals("ral")) {
				transportMethod[k] = "RAL";
				System.out.println(k+":"+transportMethod[k]);
			} else if (transportMethod[k].equals("airplane")) {
				transportMethod[k] = "AIR";
				System.out.println(k+":"+transportMethod[k]);
			} else if (transportMethod[k].equals("bus")) {
				transportMethod[k] = "BUS";
				System.out.println(k+":"+transportMethod[k]);
			}
		}
		for (StationNode leaveNode: tmpCity.getStList()) {
			for (int k = 0; k < transportMethod.length; k++) {
				if (leaveNode.getStType().toString().equals(transportMethod[k])) {
					leave = leaveNode;
					break;
				}
			}
			if (leave != null) {
				break;
			}
		}
		System.out.println("  " + finalNode.getName() + "-->" + leave.getName());
		System.out.println("费用共计：" + totalExpense + "元");
	}
	
	public void makePlan2(City tmpCity, HotelNode startNode, double velocity) {
		selectNode.chooseFirstScenicNode(tmpCity, startNode);
		ScenicNode lastNode = tmpCity.getScList().get(0);
		planNode.add(lastNode);
		playTime = playTime - lastNode.getNodeDistance(startNode)/velocity - lastNode.getHourSpend();
		tmpCity.getScList().remove(lastNode);
		totalExpense = totalExpense + lastNode.getUnitPrice();
		maxCnt--;
		System.out.println("  " + startNode.getName() + "-->" + lastNode.getName());
		System.out.println("    路程：" + String.format("%.2f", lastNode.getNodeDistance(startNode)) + 
				"km  路程用时：" + String.format("%.2f", lastNode.getNodeDistance(startNode)/velocity) + 
				"h  游玩时间：" + lastNode.getHourSpend() + 
				"h  游玩费用：" + lastNode.getUnitPrice() + "元");
		chooseNode(tmpCity, lastNode, velocity);
	}
	
	public void chooseNode(City tmpCity, ScenicNode lastNode, double velocity) {
		do {
			selectNode.chooseNextScenicNode(tmpCity, lastNode);
			ScenicNode nextNode = tmpCity.getScList().get(0);
			if (lastNode.getNodeDistance(nextNode)/velocity + nextNode.getHourSpend() <= playTime) {
				planNode.add(nextNode);
				playTime = playTime - lastNode.getNodeDistance(nextNode)/velocity-nextNode.getHourSpend();
				tmpCity.getScList().remove(nextNode);
				totalExpense = totalExpense + nextNode.getUnitPrice();
				maxCnt--;
				System.out.println("  " + lastNode.getName() + "-->" + nextNode.getName());
				System.out.println("    路程：" + String.format("%.2f", lastNode.getNodeDistance(nextNode)) + 
						"km  路程用时：" + String.format("%.2f", lastNode.getNodeDistance(nextNode)/velocity) + 
						"h  游玩时间：" + nextNode.getHourSpend() + 
						"h  游玩费用：" + nextNode.getUnitPrice() + "元");
				lastNode = nextNode;
				if (maxCnt <= 0) {
					break;
				}
			} else {
				break;
			}
		} while (playTime > 0);
		
		while (playTime > 0) {
			selectNode.chooseEndScenicNode(tmpCity, lastNode);
			ScenicNode nextNode = tmpCity.getScList().get(0);
			if (lastNode.getNodeDistance(nextNode)/velocity + nextNode.getHourSpend() <= playTime) {
				planNode.add(nextNode);
				playTime = playTime - lastNode.getNodeDistance(nextNode)/velocity-nextNode.getHourSpend();
				tmpCity.getScList().remove(nextNode);
				totalExpense = totalExpense + nextNode.getUnitPrice();
				maxCnt--;
				System.out.println("  " + lastNode.getName() + "-->" + nextNode.getName());
				System.out.println("    路程：" + String.format("%.2f", lastNode.getNodeDistance(nextNode)) + 
						"km  路程用时：" + String.format("%.2f", lastNode.getNodeDistance(nextNode)/velocity) + 
						"h  游玩时间：" + nextNode.getHourSpend() + 
						"h  游玩费用：" + nextNode.getUnitPrice() + "元");
				lastNode = nextNode;
				if (maxCnt <= 0) {
					break;
				}
			} else {
				break;
			}
		}
		
		double lowerBound = selectNode.getUserInput().getLowerBound();
		double upperBound = selectNode.getUserInput().getUpperBound();
		for (int j = tmpCity.getHtList().size()-1; j >= 0; j--) {
			if (tmpCity.getHtList().get(j).getUnitPrice() > upperBound || tmpCity.getHtList().get(j).getUnitPrice() < lowerBound) {
				tmpCity.getHtList().remove(j);
			}
		}
		selectNode.chooseHotelNode(tmpCity, lastNode);
		totalExpense = totalExpense + tmpCity.getHtList().get(0).getUnitPrice();
		System.out.println("  " + lastNode.getName()+"-->住宿："+tmpCity.getHtList().get(0).getName());
		System.out.println("    路程：" + String.format("%.2f", lastNode.getNodeDistance(tmpCity.getHtList().get(0))) + 
				"km  路程用时：" + String.format("%.2f", lastNode.getNodeDistance(tmpCity.getHtList().get(0))/velocity) + 
				"h  住宿费用：" + tmpCity.getHtList().get(0).getUnitPrice() + "元");
		finalNode = tmpCity.getHtList().get(0);
	}
	
	public void chooseBestRoute() {
		for (int i = 0; i < cityNum; i++) {
			City tmpCity = Cities.get(i);
			if (tmpCity.ScList.get(i).getCityID() == Integer.parseInt(selectNode.chooseCity())) {
				selectScenicNode(tmpCity);
				chooseAvailableStation(departureCity, tmpCity);
				setStartStation();
				String[] transportMethod = selectNode.getUserInput().getTransportation();
				for (int j = 0; j < transportMethod.length; j++) {
					if (transportMethod[j].equals("ship")) {
						if (chooseSameTypeStation(tmpCity,"NA_ST").size() > 0 && chooseSameTypeStation(departureCity,"NA_ST").size() > 0) {
							System.out.println("****************************乘坐轮船：****************************");
							if (fromNode.getStType().toString().equals("NA_ST")) {
								makePlan(tmpCity, traverse(tmpCity, chooseSameTypeStation(tmpCity,"NA_ST")));
							} else {
								System.out.println("用户选择的出发车站不可乘坐轮船");
							}
							System.out.println("****************************************************************");
						} else {
							if (chooseSameTypeStation(tmpCity,"NA_ST").size() <= 0 && chooseSameTypeStation(departureCity,"NA_ST").size() > 0) {
								System.out.println("目的城市没有轮船");
							} else if (chooseSameTypeStation(tmpCity,"NA_ST").size() > 0 && chooseSameTypeStation(departureCity,"NA_ST").size() <= 0) {
								System.out.println("起点城市没有轮船");
							} else {
								System.out.println("起点城市和目的城市均没有轮船");
							}
						}
					} else if (transportMethod[j].equals("ral")) {
						if (chooseSameTypeStation(tmpCity,"RAL").size() > 0 && chooseSameTypeStation(departureCity,"RAL").size() > 0) {
							System.out.println("****************************乘坐火车：****************************");
							if (fromNode.getStType().toString().equals("RAL")) {
								makePlan(tmpCity, traverse(tmpCity, chooseSameTypeStation(tmpCity,"RAL")));
							} else {
								System.out.println("用户选择的出发车站不可乘坐火车");
							}
							System.out.println("****************************************************************");
						} else {
							if (chooseSameTypeStation(tmpCity,"RAL").size() <= 0 && chooseSameTypeStation(departureCity,"RAL").size() > 0) {
								System.out.println("目的城市没有铁路");
							} else if (chooseSameTypeStation(tmpCity,"RAL").size() > 0 && chooseSameTypeStation(departureCity,"RAL").size() <= 0) {
								System.out.println("起点城市没有铁路");
							} else {
								System.out.println("起点城市和目的城市均没有铁路");
							}
						}
					} else if (transportMethod[j].equals("airplane")){
						if (chooseSameTypeStation(tmpCity,"AIR").size() > 0 && chooseSameTypeStation(departureCity,"AIR").size() > 0) {
							System.out.println("****************************乘坐飞机：****************************");
							if (fromNode.getStType().toString().equals("AIR")) {
								makePlan(tmpCity, traverse(tmpCity, chooseSameTypeStation(tmpCity,"AIR")));
							} else {
								System.out.println("用户选择的出发车站不可乘坐飞机");
							}
							System.out.println("****************************************************************");
						} else {
							if (chooseSameTypeStation(tmpCity,"AIR").size() <= 0 && chooseSameTypeStation(departureCity,"AIR").size() > 0) {
								System.out.println("目的城市没有飞机");
							} else if (chooseSameTypeStation(tmpCity,"AIR").size() > 0 && chooseSameTypeStation(departureCity,"AIR").size() <= 0) {
								System.out.println("起点城市没有飞机");
							} else {
								System.out.println("起点城市和目的城市均没有飞机");
							}
						}
					} else if (transportMethod[j].equals("bus")) {
						if (chooseSameTypeStation(tmpCity,"BUS").size() > 0 && chooseSameTypeStation(departureCity,"BUS").size() > 0) {
							System.out.println("****************************乘坐大巴：****************************");
							if (fromNode.getStType().toString().equals("BUS")) {
								makePlan(tmpCity, traverse(tmpCity, chooseSameTypeStation(tmpCity,"BUS")));
							} else {
								System.out.println("用户选择的出发车站不可乘坐大巴");
							}
							System.out.println("****************************************************************");
						} else {
							if (chooseSameTypeStation(tmpCity,"BUS").size() <= 0 && chooseSameTypeStation(departureCity,"BUS").size() > 0) {
								System.out.println("目的城市没有大巴");
							} else if (chooseSameTypeStation(tmpCity,"BUS").size() > 0 && chooseSameTypeStation(departureCity,"BUS").size() <= 0) {
								System.out.println("起点城市没有大巴");
							} else {
								System.out.println("起点城市和目的城市均没有大巴");
							}
						}
					}
				}
			}
		}
	}
	
	public ArrayList<StationNode> chooseSameTypeStation(City desCity, String type) {
		ArrayList<StationNode> tmpStNode = new ArrayList<StationNode>();
		for (StationNode sn: desCity.getStList()) {
			if (sn.getStType().toString().equals(type)) {
				tmpStNode.add(sn);
			}
		}
		return tmpStNode;
	}
	
	public void deleteTypeStation(City desCity, String type) {
		for (StationNode sn: desCity.getStList()) {
			if (sn.getStType().toString().equals(type)) {
				desCity.getStList().remove(sn);
			}
		}
	}
	
	public void chooseAvailableStation(City departureCity, City tmpCity) {
		if (chooseSameTypeStation(tmpCity,"NA_ST").size() <= 0 || chooseSameTypeStation(departureCity,"NA_ST").size() <= 0) {
			deleteTypeStation(tmpCity, "NA_ST");
		}
		if (chooseSameTypeStation(tmpCity,"RAL").size() <= 0 || chooseSameTypeStation(departureCity,"RAL").size() <= 0) {
			deleteTypeStation(tmpCity, "RAL");
		}
		if (chooseSameTypeStation(tmpCity,"AIR").size() <= 0 || chooseSameTypeStation(departureCity,"AIR").size() <= 0) {
			deleteTypeStation(tmpCity, "AIR");
		}
		if (chooseSameTypeStation(tmpCity,"BUS").size() <= 0 || chooseSameTypeStation(departureCity,"BUS").size() <= 0) {
			deleteTypeStation(tmpCity, "BUS");
		}
	}
	
	public StationNode traverse(City tmpCity, ArrayList<StationNode> sameTypeStation) {
		double minimumExpense = Double.POSITIVE_INFINITY;
		StationNode tmpStNode = null;
		for (StationNode stationNode: sameTypeStation) {
			if (!tmpCity.getScList().isEmpty()) {
				tmpCity.getScList().clear();
			}
			for (int j = 0; j < sceneSet.size(); j++) {
				tmpCity.ScList.add(sceneSet.get(j));
			}
			makePlan(tmpCity, stationNode);
			System.out.println("=================================================================");
			if (totalExpense < minimumExpense) {
				minimumExpense = totalExpense;
				tmpStNode = stationNode;
			}
			totalExpense = 0;
		}
		if (!tmpCity.getScList().isEmpty()) {
			tmpCity.getScList().clear();
		}
		for (int j = 0; j < sceneSet.size(); j++) {
			tmpCity.ScList.add(sceneSet.get(j));
		}
		System.out.println("*****************************最优解：*****************************");
		return tmpStNode;
	}
	
}
