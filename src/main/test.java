package main;

import java.util.ArrayList;
import java.util.Random;

import functions.fitnessCalculation;
import functions.nearestNeighbour;

public class test {

	public static void main(String[]args){
		fitnessCalculation fc1 = new fitnessCalculation();
		nearestNeighbour nn1 = new nearestNeighbour();
		double bestFitness = fc1.calculateCost(nn1.calculateInitialPopulation());
		ArrayList<Integer>bestPathFound = fc1.getPathFound();
		ArrayList<ArrayList>chromosomes = getCrossoverChromo(bestPathFound);
		Random r1 = new Random();
		for(int y=0;y<100000;y++){
		ArrayList<ArrayList>children = doCrossover(chromosomes.get(3),chromosomes.get(2),r1.nextInt(3));
		
		chromosomes.set(1, children.get(0));
		chromosomes.set(2, children.get(1));
		ArrayList<Integer> text = combineChildren(chromosomes);
		
		
		bestFitness = fc1.calculateCost(text);
		bestPathFound = fc1.getPathFound();
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
		}	
	}
	
	public static ArrayList<Integer> combineChildren(ArrayList<ArrayList> chromosome){
		ArrayList <Integer> tempArray = new ArrayList();
		
		for(int i=0;i<chromosome.size();i++){
			for(int y=0;y<chromosome.get(i).size();y++){
				tempArray.add((Integer) chromosome.get(i).get(y));
			}
		}
		
		for(int i=2;i<tempArray.size();i++){
			if(tempArray.contains(i)==false){
				System.out.println("sdas"+i);
			}
		}
		return tempArray;
	}
	
	public static ArrayList<ArrayList> doCrossover(ArrayList<Integer> father,ArrayList<Integer> mother,int crossPoint){
		ArrayList <ArrayList>tempArrayTo = new ArrayList();
		ArrayList <Integer> tempArray = new ArrayList();
		ArrayList <Integer> tempArray2 = new ArrayList();
		
		if(father.size()>mother.size()){
			for(int i=0;i<father.size();i++){
				if(i>=0 && i<crossPoint){
					tempArray.add((Integer) father.get(i));
					tempArray2.add((Integer)mother.get(i));
				}else if(i>=crossPoint&&i<father.size()){
					if(i<mother.size()){
					tempArray.add((Integer) mother.get(i));
					tempArray2.add((Integer) father.get(i));
					}else{
						tempArray2.add((Integer) father.get(i));
					}
				}
			}
		}else{
			for(int i=0;i<mother.size();i++){
				if(i>=0 && i<crossPoint){
					tempArray.add((Integer) mother.get(i));
					tempArray2.add((Integer)father.get(i));
				}else if(i>=crossPoint&&i<mother.size()){
					if(i<father.size()){
					tempArray.add((Integer) father.get(i));
					tempArray2.add((Integer) mother.get(i));
					}else{
						tempArray2.add((Integer) mother.get(i));
					}
				}
			}
		}

		
	
		tempArrayTo.add(tempArray2);
		tempArrayTo.add(tempArray);
	return tempArrayTo;	
	}
	
	
	
	
	private static ArrayList<ArrayList> getCrossoverChromo(ArrayList<Integer> tempArray2) {
		ArrayList <ArrayList>listToBePassed = new ArrayList<ArrayList>();
		ArrayList <Integer>tempSolution = new ArrayList<Integer>();
		for(int i=0;i<tempArray2.size();i++){
			if(i==0){
				
			}else if(i==tempArray2.size()-1){
					
			}else if(tempArray2.get(i)==tempArray2.get(i+1)){
				listToBePassed.add(tempSolution);
				tempSolution = new ArrayList <Integer> ();
				
			}else if(tempArray2.get(i)==tempArray2.get(i-1)){
				
			}else{
				tempSolution.add(tempArray2.get(i));
			}
			
			if(i==tempArray2.size()-1){
			listToBePassed.add(tempSolution);	
			}
		}
		
		return listToBePassed;
	}
	
}
