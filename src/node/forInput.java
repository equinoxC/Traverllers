package node;

import java.io.*;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;

public class forInput {
	static XSSFRow row;
	static XSSFSheet sheet;
	
	static int sheetNum;
	static int rowNum;
	static int cellNum;
	
	public static int getSheetNum(XSSFWorkbook workbook) {
		int ret = 0;
		Iterator< Sheet > sheetIterator = workbook.sheetIterator();
		while(sheetIterator.hasNext()) {
			sheetIterator.next();
			ret++;
		}
//		System.out.println(ret);
		return ret;
	}
	
	public static int getRowNum(XSSFSheet sheet) {
		int ret = 0;
		Iterator< Row > rowIterator = sheet.rowIterator();
		rowIterator.next();
		while(rowIterator.hasNext()) {
			rowIterator.next();
			ret++;
		}
		return ret;
	}
	
	public static int getCellNum(XSSFRow row) {
		int ret = 0;
		Iterator< Cell > cellIterator = row.cellIterator();
		while(cellIterator.hasNext()) {
			cellIterator.next();
			ret++;
		}
		return ret;
	}
	static ArrayList< City > Cities;
	static int cityNum;
	
	public ArrayList<City> getCities() {
		return Cities;
	}
	
	public int getCityNum() {
		return cityNum;
	}
	
	public static NodeType judgeNodeType(XSSFRow row0) throws Exception{
		Iterator< Cell > cellIterator = row0.cellIterator();
		while(cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			if(cell.getCellType() == CellType.STRING) {
				String cellName = cell.getStringCellValue();
				if(cellName.equals("星级")) {
					return NodeType.HOTEL;
				}
				else if(cellName.equals("平均时间") || cellName.equals("景点名") || cellName.equals("景点id")) {
					return NodeType.SCENIC;
				}
				else if(cellName.equals("车站类型")) {
					return NodeType.STATION;
				}
			}
		}
		return NodeType.NA_NODE;
	}
	
	public static StationType judgeStationType(String str) {
		if(str.equals("air_st")) 
			return StationType.AIR;
		else if(str.equals("ral_st"))
			return StationType.RAL;
		else if(str.equals("bus_st"))
			return StationType.BUS;
		else return StationType.NA_ST;
	}
	
	public static void readToNode(XSSFRow row0, ArrayList<UniformNode> unil, NodeType nodet) throws Exception {
		int id=0, cityID=0;
		String name="", pos="";
		double unitPrice=0.0;
		
		switch (nodet) {
			case HOTEL:
				id = (int)(row0.getCell(0).getNumericCellValue());
				name = row0.getCell(1).getStringCellValue();
				cityID = (int)(row0.getCell(2).getNumericCellValue());
				int level = (int)(row0.getCell(3).getNumericCellValue());
				pos = row0.getCell(4).getStringCellValue();
				unitPrice = row0.getCell(5).getNumericCellValue();
				
				unil.add(new HotelNode(nodet, id, cityID, name, unitPrice, new Point(pos), level));
				break;
			case SCENIC:
				int tagNum = cellNum - 6;
				//System.out.println(tagNum);
				ScenicNode.tagNum = tagNum;
				
				id = (int)(row0.getCell(0).getNumericCellValue());
				name = row0.getCell(1).getStringCellValue();
				cityID = (int)(row0.getCell(2).getNumericCellValue());
				pos = row0.getCell(3).getStringCellValue();
				unitPrice = row0.getCell(4).getNumericCellValue();
				double hourSpend = row0.getCell(5).getNumericCellValue();
				
				double[] tagVec =  new double[tagNum];
				for(int i = 0; i < tagNum; i++) {
					tagVec[i] = row0.getCell(6+i).getNumericCellValue();
				}
				
				unil.add(new ScenicNode(nodet, id, cityID, name, unitPrice, new Point(pos), hourSpend, tagVec));
				break;
			case STATION:
				StationType stType;
				id = (int)(row0.getCell(0).getNumericCellValue());
				cityID = (int)(row0.getCell(1).getNumericCellValue());
				stType = judgeStationType(row0.getCell(2).getStringCellValue());
				pos = row0.getCell(3).getStringCellValue();
				name = row0.getCell(4).getStringCellValue();
				
				unil.add(new StationNode(nodet, id, cityID, name, 0.0, new Point(pos), stType));
				break;
			case NA_NODE:
				break;
		}
	}
	 
	public static void processExcel() throws Exception {
		final String srcname = "ExampleXlsx/算法基础数据.xlsx";
		FileInputStream fIP = new FileInputStream(new File(srcname));
		
		XSSFWorkbook workbook = new XSSFWorkbook(fIP);
		//sheetNum = workbook.getNumberOfSheets();
		sheetNum = getSheetNum(workbook);
		cityNum = sheetNum/3;
		
//		System.out.println(""+sheetNum+","+cityNum);
		Iterator< Sheet > sheetIterator = workbook.sheetIterator();
		
		//ArrayList< ScenicNode > ScList;
		//ArrayList< HotelNode > HtList;
		//ArrayList< StationNode > StList;
		
		Cities = new ArrayList< City >(cityNum);
		for(int i = 0; i < cityNum; i++) {
			Cities.add(new City());
		}
		
		int curSheet = 0;
		while(sheetIterator.hasNext()) {
			sheet = (XSSFSheet) sheetIterator.next();
			//rowNum = sheet.getLastRowNum()+1;
			rowNum = getRowNum(sheet);
			
			Iterator< Row > rowIterator = sheet.rowIterator();
			
			NodeType nodet=NodeType.NA_NODE;
			if(rowIterator.hasNext()) {
				row = (XSSFRow) rowIterator.next();
				cellNum = getCellNum(row);
				nodet = judgeNodeType(row);
			}
			
			//UniformNode[] UnList = new UniformNode[rowNum + 1];
			ArrayList<UniformNode> UnList = new ArrayList<UniformNode>();
			while(rowIterator.hasNext()) {
				row = (XSSFRow) rowIterator.next();
				readToNode(row, UnList, nodet);		
			}
			
			UnList.trimToSize();
			City tmpCity = Cities.get(curSheet % cityNum);
			switch (nodet) {
				case SCENIC:
					for(int i = 0; i < UnList.size(); i++)
						tmpCity.ScList.add((ScenicNode)UnList.get(i));
					break;
				case HOTEL:
					for(int i = 0; i < UnList.size(); i++)
						tmpCity.HtList.add((HotelNode)UnList.get(i));
					break;
				case STATION:
					for(int i = 0; i < UnList.size(); i++)
						tmpCity.StList.add((StationNode)UnList.get(i));
					break;
				default:
					break;
			}
			curSheet++;
		}
		workbook.close();
	}
	
	// 需要的操作在main()里面
	public static void main(String[] args) throws Exception {
		processExcel();
		
		// 测试一下Excel输入有没有问题
		File file = new File("test.txt");
		PrintStream ps = new PrintStream(new FileOutputStream(file));
		
		for(int i = 0; i < cityNum; i++) {
			City tmpCity = Cities.get(i);
			ps.println(tmpCity.ScList.size());
			for(int j = 0; j < tmpCity.ScList.size(); j++) {
				ps.println(tmpCity.ScList.get(j).toString());
			}
			ps.println("--------");
			ps.println(tmpCity.HtList.size());
			for(int j = 0; j < tmpCity.HtList.size(); j++) {
				ps.println(tmpCity.HtList.get(j).toString());
			}
			ps.println("--------");
			ps.println(tmpCity.StList.size());
			for(int j = 0; j < tmpCity.StList.size(); j++) {
				ps.println(tmpCity.StList.get(j).toString());
			}
			ps.println("__________________________________________________");
		}
		ps.close();
		// 下面开始写算法
		// Start your algorithm HERE!

	}
} 