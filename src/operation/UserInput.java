package operation;

import java.util.Scanner;

public class UserInput {

	// 速度单位km/h
	private static final double Ship_Velocity = 20;
	private static final double Railway_Velocity = 50;
	private static final double Airplane_Velocity = 900;
	private static final double Bus_Velocity = 30;
	private static UserInput userInput = null;
	private String departure; // 出发地
	private String departureStop; // 出发车站
	private String arrival; // 目的地
	private String[] theme; // 旅游主题
	private int[] themeNum;
	private String expense; // 旅游经济
	private double lowerBound; // 酒店价格下界
	private double upperBound; // 酒店价格上界
	private String[] transportation; // 出行方式
	private double[] transportVelocity;
	private int[] time; // 出行天数
	private String budget; // 预算

	public UserInput() {
		Scanner in = new Scanner(System.in);
		System.out.println("起始地：");
		departure = in.nextLine();

		System.out.println("目的地：");
		arrival = in.nextLine();
		
		System.out.println("出发车站：");
		departureStop = in.nextLine();

		System.out.println("旅游主题(culture,family,romantic,explore,view)：");
		String[] str = in.nextLine().split(" ");
		theme = new String[str.length];
		themeNum = new int[str.length];
		for (int i = 0; i < str.length; i++) {
			theme[i] = new String(str[i]);
			themeNum[i] = judgeThemeNum(theme[i]);
		}

		System.out.println("旅游经济(1-3)：");
		expense = in.nextLine();
		judgeExpense(expense);

		System.out.println("出行方式(ship,ral,airplane,bus)：");
		str = in.nextLine().split(" ");
		transportation = new String[str.length];
		transportVelocity = new double[str.length];
		for (int i = 0; i < str.length; i++) {
			transportation[i] = new String(str[i]);
			transportVelocity[i] = judgeTransport(transportation[i]);
		}

		System.out.println("出行天数：");
		String[] tmpTime = new String[2];
		tmpTime = in.nextLine().split("-");
		time = new int[2];
		time[0] = Integer.parseInt(tmpTime[0]);
		time[1] = Integer.parseInt(tmpTime[1]);

		System.out.println("预算：");
		budget = in.nextLine();
		in.close();
	}

	public String getDeparture() {
		return departure;
	}

	public String getArrival() {
		return arrival;
	}
	
	public String getDepartureStop() {
		return departureStop;
	}

	public String[] getTheme() {
		return theme;
	}
	
	public int[] getThemeNum() {
		return themeNum;
	}
	
	public int getThemeNum(int i) {
		return themeNum[i];
	}

	public String getExpense() {
		return expense;
	}
	
	public double getLowerBound() {
		return lowerBound;
	}
	
	public double getUpperBound() {
		return upperBound;
	}

	public String[] getTransportation() {
		return transportation;
	}
	
	public String getTransportation(int i) {
		return transportation[i];
	}

	public int[] getTime() {
		return time;
	}
	
	public int getTime(int i) {
		return time[i];
	}

	public String getBudget() {
		return budget;
	}

	public static UserInput getInstance() {
		if (userInput == null) {
			userInput = new UserInput();
		}
		return userInput;
	}

	public int judgeThemeNum(String theme) {
		switch (theme) {
		case "culture":
			return 0;
		case "family":
			return 1;
		case "romantic":
			return 2;
		case "explore":
			return 3;
		case "view":
			return 4;
		default:
			return 0;
		}
	}
	
	public void judgeExpense(String expense) {
		switch (expense) {
		case "1":
			lowerBound = 0.0;
			upperBound = 400.0;
			return;
		case "2":
			lowerBound = 400.0;
			upperBound = 800.0;
			return;
		case "3":
			lowerBound = 800.0;
			upperBound = 100000.0;
			return;
		}
	}
	
	public double judgeTransport(String transport) {
		switch (transport) {
		case "ship":
			return Ship_Velocity;
		case "ral":
			return Railway_Velocity;
		case "airplane":
			return Airplane_Velocity;
		case "bus":
			return Bus_Velocity;
		default:
			return 0;
		}
	}

}
