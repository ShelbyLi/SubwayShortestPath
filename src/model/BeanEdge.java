package model;

public class BeanEdge {
	int[][] edgeValues;
	String[][] edgeNames;
	int length;
	
	
	
	/**
	 * @param length
	 */
	public BeanEdge(int length) {
		super();
		this.length = length;
		edgeValues = new int[length][length];
		edgeNames = new String[length][length];
	}
	
	public int[][] getEdgeValues() {
		return edgeValues;
	}
	public void setEdgeValues(int[][] edgeValues) {
		this.edgeValues = edgeValues;
	}
	public String[][] getEdgeNames() {
		return edgeNames;
	}
	public void setEdgeNames(String[][] edgeNames) {
		this.edgeNames = edgeNames;
	}
	
	
}
