package model;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class BeanShortestPath {
	List<BeanVertex> stations;
	List<String> lineNames;
	
	/**
	 * 
	 */
	public BeanShortestPath() {
		super();
		stations = new ArrayList<>();
		lineNames = new ArrayList<>();
	}
	public List<BeanVertex> getStations() {
		return stations;
	}
	public void setStations(List<BeanVertex> stations) {
		this.stations = stations;
	}
	public List<String> getLineNames() {
		return lineNames;
	}
	public void setLineNames(List<String> lineNames) {
		this.lineNames = lineNames;
	}
	@Override
	public String toString() {
//		System.out.println(shortestPath.getStations().get(0).getStationName() + "\t" + shortestPath.getStations().get(0).getLine());
//		for (int i=0; i<shortestPath.getLineNames().size(); i++) {
//			System.out.println(shortestPath.getLineNames().get(i));
//			System.out.println(shortestPath.getStations().get(i+1).getStationName() + "\t" + shortestPath.getStations().get(i+1).getLine());
//		}
		String ret = new String();
		Set<String> lines = new LinkedHashSet<>(lineNames);
		
		ret += stations.get(0).getStationName() + " --> ";
		boolean isFirst = true;
		for (String line: lines) {
			if (isFirst) {
				ret += line;
				isFirst = false;
			} else {
				ret += " --> ";
				ret += stations.get(lineNames.indexOf(line)).getStationName();
				ret += " --> ";
				ret += line;
			}
		}
		ret += " --> " + stations.get(stations.size()-1).getStationName();
		return ret;
	}
	
	
}
