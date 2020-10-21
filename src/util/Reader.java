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
//			reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("src\\������·��Ϣ.txt")), "UTF-8"));
//			reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("E:\\������·��Ϣ.txt")), "UTF-8"));
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(txtPath)), "UTF-8"));
			
			// �Ƚ��ļ��е����ݶ��� (��ΪҪ�ظ��õ�)
			ArrayList<String> txtLines = new ArrayList<String>();
			{  // ʹ������txtLineֻ�ڴ�������Ч  ��������for each����ʱȡͬ���ı�����
				String txtLine;
				while ((txtLine = reader.readLine()) != null) {
					txtLines.add(txtLine);
				}
			}
			
			// ��������վ��
			Map<String, BeanVertex> stations = new HashMap<>();
			for (String txtLine: txtLines) {
				String[] stationNames = txtLine.split(" ");
				String lineName = stationNames[0];
				for (int i=1; i<stationNames.length; i++) {  // 0��lineName
					if (!stations.containsKey(stationNames[i])) {  // map��û�� ��Ҫ���������
						BeanVertex v = new BeanVertex(stations.size(), stationNames[i]);
						v.getLine().add(lineName);
						stations.put(stationNames[i], v);
					} else {  // map���� ��Ҫ��һ��lineName
						stations.get(stationNames[i]).getLine().add(lineName);
					}
				}
				
			}
			
			
			// �������Ϣ ��վ��֮�����ϵ  �Լ�������(��·����)
			BeanEdge edge = new BeanEdge(stations.size());
//			int[][] edge = new int[stations.size()][stations.size()];  // ���ȵõ�����վ���֪��վ���size
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
			
			// ����ͼ
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
		// ��ȡ�ļ�
		Graph graph = readSubwayInfo("src\\������·��Ϣ.txt");
//		int[][] edge = graph.getEdge();
//		for (int i=0; i<edge.length; i++) {
//			for (int j=0; j<edge.length; j++) {
//				System.out.print(edge[i][j] + " ");
//			}
//			System.out.println();
//		}
		
		// Ѱ�����·��
//		int[] path = graph.dijkstra(0);
//		List<BeanVertex> stationPath = graph.getShortestPath("������վ", "Ӻ�͹�");
		BeanShortestPath shortestPath = graph.getShortestPath("������վ", "Ӻ�͹�");
		System.out.println(shortestPath.getStations().get(0).getStationName() + "\t" + shortestPath.getStations().get(0).getLine());
		for (int i=0; i<shortestPath.getLineNames().size(); i++) {
			System.out.println(shortestPath.getLineNames().get(i));
			System.out.println(shortestPath.getStations().get(i+1).getStationName() + "\t" + shortestPath.getStations().get(i+1).getLine());
		}
		System.out.println(shortestPath);
		// ���
	}

}
