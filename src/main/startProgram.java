package main;

import java.util.ArrayList;

import functions.fitnessCalculation;
import functions.nearestNeighbour;
import functions.operators;
import functions.operators2;

import misc.PMX;


public class startProgram {
	
	public static void main(String[] args){
	
		fitnessCalculation fc1 = new fitnessCalculation();
		nearestNeighbour nn1 = new nearestNeighbour();
		operators2 o1 = new operators2();
		operators o2 = new operators();
		ArrayList <Integer> bestPathFound = new ArrayList();
		double bestFitness = fc1.calculateCost(nn1.calculateInitialPopulation());
		bestPathFound = fc1.getPathFound();
	
		
	//	o1.doOperation();
//		o2.crossover(0.99);
//		
		
		
//		if(fc1.calculateCost(temp1)<bestFitness){
//		bestFitness = fc1.calculateCost(temp1);
//		bestPathFound = fc1.getPathFound();
//			}
		//System.out.println(count);
		
		
		System.out.println(bestFitness);
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

		
		
		
		
		
//		System.out.println("Fitness Cost "+bestFitnessCost);
//		for(int i=0;i<bestPathFound.size();i++){
//			if(i==0){
//				System.out.print(bestPathFound.get(i)+"->");
//			}else if(i==bestPathFound.size()-1){
//				System.out.println(bestPathFound.get(i));
//			}else if(bestPathFound.get(i)==bestPathFound.get(i+1)){
//				System.out.println(bestPathFound.get(i));
//			}else{
//				System.out.print(bestPathFound.get(i)+"->");
//			}
//		}
//		
//		
	}

}
