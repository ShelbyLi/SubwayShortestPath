package util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.BeanEdge;
import model.BeanShortestPath;
import model.BeanVertex;

public class Reader {

	public static Graph readSubwayInfo(String txtPath) {
		Graph graph = null;
		BufferedReader reader;
		try {
//			reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("src\\地铁线路信息.txt")), "UTF-8"));
//			reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("E:\\地铁线路信息.txt")), "UTF-8"));
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(txtPath)), "UTF-8"));
			
			// 先将文件中的数据读入 (因为要重复用到)
			ArrayList<String> txtLines = new ArrayList<String>();
			{  // 使变量名txtLine只在此区域生效  便于下面for each遍历时取同样的变量名
				String txtLine;
				while ((txtLine = reader.readLine()) != null) {
					txtLines.add(txtLine);
				}
			}
			
			// 保存所有站点
			Map<String, BeanVertex> stations = new HashMap<>();
			for (String txtLine: txtLines) {
				String[] stationNames = txtLine.split(" ");
				String lineName = stationNames[0];
				for (int i=1; i<stationNames.length; i++) {  // 0是lineName
					if (!stations.containsKey(stationNames[i])) {  // map中没有 需要创建后加入
						BeanVertex v = new BeanVertex(stations.size(), stationNames[i]);
						v.getLine().add(lineName);
						stations.put(stationNames[i], v);
					} else {  // map中有 需要加一个lineName
						stations.get(stationNames[i]).getLine().add(lineName);
					}
				}
				
			}
			
			
			// 保存边信息 即站点之间的联系  以及边名称(线路名称)
			BeanEdge edge = new BeanEdge(stations.size());
//			int[][] edge = new int[stations.size()][stations.size()];  // 得先得到所有站点才知道站点的size
//			String[][] edgeName = new String[stations.size()][stations.size()];
			for (String txtLine: txtLines) {
				String[] stationNames = txtLine.split(" ");
				String lineName = stationNames[0];
				for (int i=1; i<stationNames.length-1; i++) {
					BeanVertex station1 = stations.get(stationNames[i]);
					BeanVertex station2 = stations.get(stationNames[i+1]);
					edge.getEdgeValues()[station1.getId()][station2.getId()] = 1;
					edge.getEdgeValues()[station2.getId()][station1.getId()] = 1;
					edge.getEdgeNames()[station1.getId()][station2.getId()] = lineName;
					edge.getEdgeNames()[station2.getId()][station1.getId()] = lineName;
				}
			}
			
			// 构建图
			graph = new Graph(edge, stations);
		} catch (UnsupportedEncodingException | FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return graph;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 读取文件
		Graph graph = readSubwayInfo("src\\地铁线路信息.txt");
//		int[][] edge = graph.getEdge();
//		for (int i=0; i<edge.length; i++) {
//			for (int j=0; j<edge.length; j++) {
//				System.out.print(edge[i][j] + " ");
//			}
//			System.out.println();
//		}
		
		// 寻找最短路径
//		int[] path = graph.dijkstra(0);
//		List<BeanVertex> stationPath = graph.getShortestPath("北京南站", "雍和宫");
		BeanShortestPath shortestPath = graph.getShortestPath("北京南站", "雍和宫");
		System.out.println(shortestPath.getStations().get(0).getStationName() + "\t" + shortestPath.getStations().get(0).getLine());
		for (int i=0; i<shortestPath.getLineNames().size(); i++) {
			System.out.println(shortestPath.getLineNames().get(i));
			System.out.println(shortestPath.getStations().get(i+1).getStationName() + "\t" + shortestPath.getStations().get(i+1).getLine());
		}
		System.out.println(shortestPath);
		// 输出
	}

}
