package util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.BeanEdge;
import model.BeanShortestPath;
import model.BeanVertex;

public class Graph {
	
	private BeanEdge edges;
	private Map<String, BeanVertex> vertexes;
	
	/**
	 * @param edges
	 * @param vertexes
	 */
	public Graph(BeanEdge edges, Map<String, BeanVertex> vertexes) {
		super();
		this.edges = edges;
		this.vertexes = vertexes;
	}
	

	public int[] dijkstra(int startVertexIndex) {
		boolean[] collected = new boolean[edges.getEdgeValues().length];
		int[] dist = new int[edges.getEdgeValues().length];
		int[] path = new int[edges.getEdgeValues().length];
		
		// 初始化
		for (int i=0; i<edges.getEdgeValues().length; i++) {
			if (i==startVertexIndex) {  // 等于起始点
				collected[i] = true;
				dist[i] = 0;
				path[i] = i;
			} else {  // 不是起始点
				collected[i] = false;
				if (edges.getEdgeValues()[startVertexIndex][i] > 0) {  // 起始点能到
					dist[i] = edges.getEdgeValues()[startVertexIndex][i];
					path[i] = startVertexIndex;
				} else {
					dist[i] = Integer.MAX_VALUE;
					path[i] = -1;
				}
			}
			
		}
		
		while (true) {
			int minDistIndex = getMinVertex(collected, dist);  // 未收录顶点中 collected=false 的dist最小者
			if (minDistIndex == -1) {
				break;
			}
			collected[minDistIndex] = true;
			
			for (int i=0; i<edges.getEdgeValues().length; i++) {  // minVertex的每个邻接点
				if (edges.getEdgeValues()[minDistIndex][i]>0 && collected[i] == false) {
					dist[i] = dist[minDistIndex] + edges.getEdgeValues()[minDistIndex][i];
					path[i] = minDistIndex;
				}
			}
		}
		
		return path;
	}

	private int getMinVertex(boolean[] collected, int[] dist) {
		int minDist = Integer.MAX_VALUE;
		int minDistIndex = -1;
		for (int i=0; i<edges.getEdgeValues().length; i++) {
			if (collected[i] == false && dist[i]<minDist) {
				minDist = dist[i];
				minDistIndex = i;
			}
		}
		
		return minDistIndex;
	}
	
	public BeanShortestPath getShortestPath(String startStationName, String endStationName) {
		int[] path = dijkstra(vertexes.get(startStationName).getId());
		
		Map<Integer, BeanVertex> id2StationName = new HashMap<>();  // 再搞一个id到结点的映射
		id2StationName = getId2StationName();
		
		BeanShortestPath shortestPath = new BeanShortestPath();
		int stationId = vertexes.get(endStationName).getId();
		while (stationId != vertexes.get(startStationName).getId()) {
			shortestPath.getStations().add(id2StationName.get(stationId));  // 根据id找名字找站点
			shortestPath.getLineNames().add(edges.getEdgeNames()[stationId][path[stationId]]);  // 当前站到 上一站 的名字
			stationId = path[stationId];
		}
		shortestPath.getStations().add(id2StationName.get(stationId));  // 加入起点
		Collections.reverse(shortestPath.getStations());
		Collections.reverse(shortestPath.getLineNames());
		return shortestPath;
	}
	
	private Map<Integer, BeanVertex> getId2StationName() {
		Map<Integer, BeanVertex> id2StationName = new HashMap<>();
		for (BeanVertex v: vertexes.values()) {
			id2StationName.put(v.getId(), v);
		}
		return id2StationName;
	}
	
	
	
	public boolean isInGraph(String stationName) {
		return this.vertexes.containsKey(stationName);
		
	}

	public Map<String, BeanVertex> getVertexes() {
		return vertexes;
	}

	public BeanEdge getEdges() {
		return edges;
	}


	public void setEdges(BeanEdge edges) {
		this.edges = edges;
	}


	public void setVertexes(Map<String, BeanVertex> vertexes) {
		this.vertexes = vertexes;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		int[][] e = {{0,-1,10,-1,30,100},
//					{-1,0,5,-1,-1,-1},
//					{-1,-1,0,50,-1,-1},
//					{-1,-1,-1,0,-1,10},
//					{-1,-1,-1,20,0,60},
//					{-1,-1,-1,-1,-1,0}};
////		String[] s = new String[6];
//		Vertex[] v = new Vertex[6];
//		for (int i=0; i<e.length; i++) {
//			v[i] = new Vertex(i, "v"+i);
//		}
//		Graph graph = new Graph(e, v);
//		int[] path = graph.dijkstra(0);
//		for (int i=0; i<6; i++) {
//			System.out.println(path[i]);
//		}
		BeanEdge edge = new BeanEdge(11);
		int[][] edgeValues = edge.getEdgeValues();
		edgeValues[0][1] = 1;
		edgeValues[1][0] = 1;
		edgeValues[1][2] = 1;
		edgeValues[2][1] = 1;
		edgeValues[2][3] = 1;
		edgeValues[3][2] = 1;
		edgeValues[1][4] = 1;
		edgeValues[4][1] = 1;
		edgeValues[4][5] = 1;
		edgeValues[5][4] = 1;
		edgeValues[3][6] = 1;
		edgeValues[6][3] = 1;
		edgeValues[5][6] = 1;
		edgeValues[6][5] = 1;
		edgeValues[6][7] = 1;
		edgeValues[7][6] = 1;
		edgeValues[0][8] = 1;
		edgeValues[8][0] = 1;
		edgeValues[8][9] = 1;
		edgeValues[9][8] = 1;
		edgeValues[9][10] = 1;
		edgeValues[10][9] = 1;
		edgeValues[10][3] = 1;
		edgeValues[3][10] = 1;
		
//		for (int i=0; i<edgeValues.length; i++) {
//			for (int j=0; j<edgeValues.length; j++) {
//				System.out.print(edgeValues[i][j] + " ");
//			}
//			System.out.println();
//		}
		Map<String, BeanVertex> v = new HashMap<>();
//		BeanVertex[] v = new BeanVertex[11];
		for (int i=0; i<edgeValues.length; i++) {
			v.put("v"+i, new BeanVertex(i, "v"+i));
//			v[i] = new BeanVertex(i, "v"+i);
		}
		Graph g = new Graph(edge, v);
		
//		System.out.println(g.dijkstra(0));
		List<BeanVertex> sp = g.getShortestPath("v0", "v7").getStations();
		for (int i=0; i<sp.size(); i++) {
			System.out.println(sp.get(i).getStationName());
		}
		System.out.print(g.isInGraph("v0"));
	}
}
