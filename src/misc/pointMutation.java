package misc;

import java.util.ArrayList;

import main.CVRPData;
import functions.fitnessCalculation;
public class pointMutation {
	ArrayList<Integer> a1;
	ArrayList<Integer> a2;
	public ArrayList <Integer> performSwap(ArrayList<Integer>parentChromo){
		ArrayList <Integer> tempArray = parentChromo;
		
		int  firstSwapPoint = (int) (0+(Math.random()*parentChromo.size()));
		
		int  secondSwapPoint = (int) (0+(Math.random()*parentChromo.size()));
		

		int tempNode = tempArray.get(firstSwapPoint);
		tempArray.set(firstSwapPoint, tempArray.get(secondSwapPoint));
		tempArray.set(secondSwapPoint, tempNode);
		return tempArray;
		
	}
	
	
	

	
	public ArrayList<Integer> getA1() {
		return a1;
	}


	public ArrayList<Integer> getA2() {
		return a2;
	}


	public ArrayList<Integer> generatedRandomChormo(){
		ArrayList <Integer> tempArray = new ArrayList();
		
		while(tempArray.size()<CVRPData.NUM_NODES-1){
			int tempval =(int)Math.floor(Math.random()*(CVRPData.NUM_NODES-1));
			int y = 2+tempval;
			if(!tempArray.contains(y)){
			tempArray.add(y);
		}
			}
		return tempArray;
	}
}
