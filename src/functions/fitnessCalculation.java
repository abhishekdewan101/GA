package functions;

import java.util.ArrayList;

import main.CVRPData;

public class fitnessCalculation {
	ArrayList pathFound;
	
	
	public double calculateCost(ArrayList<Integer> pathFollowed){
		pathFound = new ArrayList();
		double fitnessCost=0.0;
		double numberOfTrucks = 0;
		double currentCapacity = CVRPData.VEHICLE_CAPACITY;
		ArrayList usedNodes = new ArrayList();
		
		for(int i=0;i<pathFollowed.size();i++){
			if(currentCapacity==CVRPData.VEHICLE_CAPACITY){
				fitnessCost = fitnessCost + CVRPData.getDistance(1,pathFollowed.get(i));
				currentCapacity = currentCapacity - CVRPData.getDemand(pathFollowed.get(i));
				pathFound.add(1);
				pathFound.add(pathFollowed.get(i));
			}else
			
			if((currentCapacity-CVRPData.getDemand(pathFollowed.get(i)))>=0){
				fitnessCost = fitnessCost + CVRPData.getDistance(pathFollowed.get(i-1),pathFollowed.get(i));
				currentCapacity = currentCapacity - CVRPData.getDemand(pathFollowed.get(i));
				pathFound.add(pathFollowed.get(i));
			}else
			
			if((currentCapacity-CVRPData.getDemand(pathFollowed.get(i)))<0){
				fitnessCost = fitnessCost + CVRPData.getDistance(pathFollowed.get(i-1),1);
				currentCapacity = CVRPData.VEHICLE_CAPACITY;
				pathFound.add(1);
				i--;
			}
			
		
		
		if(i==pathFollowed.size()-1){
			fitnessCost = fitnessCost + CVRPData.getDistance(1,pathFollowed.get(pathFollowed.size()-1));
			pathFound.add(1);
		}
		}
		return fitnessCost;
	}

	public ArrayList getPathFound() {
		return pathFound;
	}
}
