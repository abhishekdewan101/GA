package misc;

import java.util.ArrayList;
import java.util.Random;

import functions.fitnessCalculation;
import functions.nearestNeighbour;

import main.CVRPData;

public class PMX {
	nearestNeighbour nn1 = new nearestNeighbour();
	fitnessCalculation fc1 = new fitnessCalculation();
	
	public void performPMX(){
		ArrayList <Integer> bestSolution = nn1.calculateInitialPopulation();
		double bestFitness = fc1.calculateCost(bestSolution);
		
		ArrayList <Integer> bestPathFound = new ArrayList();
		
		int startPMX = randomGenerator(bestSolution.size());
		int stopPMX = randomGenerator(bestSolution.size());
		System.out.println(startPMX+"  "+stopPMX);
		
		for(int i = startPMX;i<=stopPMX;i++){
		ArrayList<Integer> temp1 = bestSolution;
		int child[] = new int[temp1.size()];
		ArrayList <Integer>temp2 = new pointMutation().generatedRandomChormo();
		int valueToBeAdded;
		int valueToBeSearched;
		
		for(int y=0;y<child.length;y++){
			child[y]=-1;
		}
		
		for(int y=startPMX;y<stopPMX;y++){
			child[y]=temp1.get(y);
		}
		boolean inBetween;
		boolean terminateWhile =true;
		for(int y=startPMX;y<stopPMX;y++){
			if(child[y]!=temp2.get(y)){
				valueToBeAdded = temp2.get(y);
				valueToBeSearched = temp1.get(y);
				while(terminateWhile == true){
				for(int z=0;z<temp2.size();z++){
					inBetween =false;
					if(temp2.get(z)==valueToBeSearched){
						inBetween = inBetween(z,startPMX,stopPMX);
					}	
					if(inBetween==true){
						valueToBeSearched = temp1.get(z);
					}else{
						
					}
				}
				}
				
			}
		}
		 
		
		
		
			
			
		}
		
	}
	
	
	
	
	private void checkinBetween(int valueofParent1) {
		// TODO Auto-generated method stub
		
	}




	public int randomGenerator(int given){
		Random r1 = new Random();
		return r1.nextInt(given);
	}

	public void printResults(double bestFitnessCost,ArrayList bestPathFound){
		System.out.println("Fitness Cost "+bestFitnessCost);
		for(int i=0;i<bestPathFound.size();i++){
			if(i==0){
				System.out.print(bestPathFound.get(i)+"->");
			}else if(i==bestPathFound.size()-1){
				System.out.println(bestPathFound.get(i));
			}else if(bestPathFound.get(i)==bestPathFound.get(i+1)){
				System.out.println(bestPathFound.get(i));
			}else{
				System.out.print(bestPathFound.get(i)+"->");
			}
		}
	}
	
	public boolean inBetween(int number,int from,int to){
		boolean inBetween = false;
		
		for(int i=from;i<to;i++){
			if(i==number){
				inBetween = true;
			}
		}
		
		
		return inBetween;
	}
	public ArrayList <Integer> randomSolutionGenerator(){
		ArrayList tempList = new ArrayList();
		
		for(int i=2;i<CVRPData.NUM_NODES;i++){
			tempList.add(i);
		}
		
		return tempList;
	}
}
