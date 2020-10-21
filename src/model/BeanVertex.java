package model;
import java.util.ArrayList;

public class BeanVertex {
	int id;
	String stationName;
	ArrayList<String> line = new ArrayList<String>();

	public BeanVertex(int id, String stationName) {
		super();
		this.id = id;
		this.stationName = stationName;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public ArrayList<String> getLine() {
		return line;
	}

	public void setLine(ArrayList<String> line) {
		this.line = line;
	}
	public void addLine(String lineNum) {
		this.line.add(lineNum);
	}
}
