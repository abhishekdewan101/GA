package functions;


import java.util.ArrayList;
import main.CVRPData;
public class nearestNeighbour{
	
	public ArrayList<Integer> calculateInitialPopulation(){
		ArrayList <Integer> pathFollowed = new ArrayList();
		int currentNode = 1;
		int nodeWithMinDistance = 1;
		while(pathFollowed.size()<=(CVRPData.NUM_NODES-1)){
			
			
			double distancesArray[] = new double[(CVRPData.NUM_NODES)+1];
			distancesArray = calculateDistances(currentNode);
			nodeWithMinDistance = getNodeWithMinDist(distancesArray,pathFollowed);
			currentNode = nodeWithMinDistance;
			pathFollowed.add(nodeWithMinDistance);
			
		}
		pathFollowed.remove(pathFollowed.lastIndexOf(1));
		return pathFollowed;
		
		
	}

	private  int getNodeWithMinDist(double[] distancesArray,	ArrayList<Integer> pathFollowed) {
		int tempNode = 1;
		double tempCount = Integer.MAX_VALUE;
		
		for(int i=0;i<distancesArray.length;i++){
			if(pathFollowed.contains(i)== false){
				if(distancesArray[i]<tempCount){
					if(i != 1){
					tempCount = distancesArray[i];
					tempNode = i;
					}
				}
			}
		}
		
		return tempNode;
	}

	private  double[] calculateDistances(int currentNode) {
		double[] distanceArray = new double[(CVRPData.NUM_NODES)+1];
			
		for(int i=0;i<distanceArray.length;i++){
			if(i==0){
				distanceArray[i] = Integer.MAX_VALUE;
			}else if(i == currentNode){
				distanceArray[i]= Integer.MAX_VALUE;
			}else{
				distanceArray[i] = CVRPData.getDistance(currentNode, i);
			}
		}
		
		return distanceArray;
	}
}