package functions;

import java.util.ArrayList;
import java.util.Random;

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
	//	ArrayList<Integer> temp1 = bestSolution;
		//ArrayList <Integer>temp2 = randomSolutionGenerator();
		
		for(int i = startPMX;i<=stopPMX;i++){
		ArrayList<Integer> temp1 = bestSolution;
		ArrayList <Integer>temp2 = new pointMutation().generatedRandomChormo();
		int tempVal = (Integer) temp1.get(i);
		temp1.set(i, temp2.get(i));
		temp2.set(i,tempVal);
		
		if(fc1.calculateCost(temp1)<bestFitness){
			bestFitness = fc1.calculateCost(temp1);
			bestPathFound = fc1.pathFound;
			printResults(bestFitness,bestPathFound);
		}else if(fc1.calculateCost(temp2)<bestFitness){
			bestFitness = fc1.calculateCost(temp2);
			bestPathFound = fc1.pathFound;
			printResults(bestFitness,bestPathFound);
		}else if(fc1.calculateCost(temp2)<bestFitness && fc1.calculateCost(temp1)<bestFitness){
				if(fc1.calculateCost(temp1)<fc1.calculateCost(temp2)){
					bestFitness = fc1.calculateCost(temp1);
					bestPathFound = fc1.pathFound;
					printResults(bestFitness,bestPathFound);
				}else{
					bestFitness = fc1.calculateCost(temp2);
					bestPathFound = fc1.pathFound;
					printResults(bestFitness,bestPathFound);
				}
		}
			
			
		}
		
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
	public ArrayList <Integer> randomSolutionGenerator(){
		ArrayList tempList = new ArrayList();
		
		for(int i=2;i<CVRPData.NUM_NODES;i++){
			tempList.add(i);
		}
		
		return tempList;
	}
}
