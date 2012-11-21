package functions;

import java.util.ArrayList;

import main.CVRPData;

public class operators {
	
	
	
	public ArrayList<Integer> swap(int rate){
		nearestNeighbour nn1 = new nearestNeighbour();
		fitnessCalculation fc1 = new fitnessCalculation();
		ArrayList<Integer> tempArray = nn1.calculateInitialPopulation();
		int count =0;
		while(count<=rate){
		int firstSwapPoint = generateRandomPoint();
		int secondSwapPoint = generateRandomPoint();
		
		System.out.println(tempArray.size()+"	"+firstSwapPoint+"		"+secondSwapPoint);
		
		if(firstSwapPoint>secondSwapPoint){
			int tempVal = firstSwapPoint;
			firstSwapPoint = secondSwapPoint;
			secondSwapPoint = firstSwapPoint;
		}else if(firstSwapPoint == secondSwapPoint){
			secondSwapPoint = generateRandomPoint();
		}
		
		int tempArrayVal = tempArray.get(firstSwapPoint);
		tempArray.set(firstSwapPoint, tempArray.get(secondSwapPoint));
		tempArray.set(secondSwapPoint,tempArrayVal);
		count++;
		}
		return tempArray;
	}
	
	
	public ArrayList<Integer> crossover(double rate){
		ArrayList <Integer> text = null;
		nearestNeighbour nn1 = new nearestNeighbour();
		fitnessCalculation fc1 = new fitnessCalculation();
		double bestFitnessCost =fc1.calculateCost(nn1.calculateInitialPopulation());
		ArrayList <Integer> tempArray2 =fc1.getPathFound(); 
		
		ArrayList<ArrayList> chromosomes = getCrossoverChromo(tempArray2);
		ArrayList <ArrayList> currentBest = new ArrayList();
		double startPoint =110;
		double finalPoint = 0.0001;
		
		while(startPoint>=finalPoint){
		ArrayList <ArrayList> children = new ArrayList<ArrayList>();
		
		int firstPoint = generateRandomPoint(chromosomes.size());
		int secondPoint =generateRandomPoint(chromosomes.size());
		
		
		
		if(firstPoint == secondPoint ){
			secondPoint=firstPoint+1;
		}
		int crossoverPoint;
		if(chromosomes.get(firstPoint).size()<chromosomes.get(secondPoint).size()){
			crossoverPoint = generateRandomPoint(chromosomes.get(firstPoint).size());
			if(crossoverPoint == chromosomes.get(firstPoint).size()){
				crossoverPoint = crossoverPoint-1;
			}
		}else{
			crossoverPoint = generateRandomPoint(chromosomes.get(secondPoint).size());
			if(crossoverPoint == chromosomes.get(secondPoint).size()){
				crossoverPoint = crossoverPoint-1;
			}
		}
		
		children = doCrossover(chromosomes.get(firstPoint),chromosomes.get(secondPoint),crossoverPoint);
		
		chromosomes.set(firstPoint, children.get(0));
		chromosomes.set(secondPoint, children.get(1));
		
		
		text = combineChildren(chromosomes);
		
		if(fc1.calculateCost(text)<=bestFitnessCost){
			bestFitnessCost =fc1.calculateCost(text);
			currentBest = chromosomes;
			System.out.println(fc1.calculateCost(text));
			ArrayList bestPathFound = fc1.getPathFound();
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
		}else{
			System.out.println("-x-x-x-x-x-x-x-x-x-x FINDING -x-x-x-x-x-x-x-x-x");
			chromosomes = getCrossoverChromo(tempArray2);
		}
		

		startPoint -=rate;
		
		}
		text = combineChildren(currentBest);
		return text;
		
	}
	
	public ArrayList<ArrayList> doCrossover(ArrayList father,ArrayList mother,int crossPoint){
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
			
		tempArrayTo.add(tempArray);
		tempArrayTo.add(tempArray2);
	return tempArrayTo;	
	}
	
	
	
	public ArrayList<Integer> combineChildren(ArrayList<ArrayList> chromosome){
		ArrayList <Integer> tempArray = new ArrayList();
		
		for(int i=0;i<chromosome.size();i++){
			for(int y=0;y<chromosome.get(i).size();y++){
				tempArray.add((Integer) chromosome.get(i).get(y));
			}
		}
		
		for(int i=2;i<tempArray.size();i++){
			if(tempArray.contains(i)==false){
				System.out.println(i);
			}
		}
		return tempArray;
	}
	
	
	private ArrayList<ArrayList> getCrossoverChromo(ArrayList<Integer> tempArray2) {
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
	
	public int generateRandomPoint(){
		return 0+(int)Math.floor(Math.random()*(CVRPData.NUM_NODES-1));
	}
	
	public int generateRandomPoint(int limit){
		return 0+(int)Math.floor(Math.random()*(limit-1));
	}
}
