package functions;

import java.util.ArrayList;
import java.util.Random;

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
	
	ArrayList <Integer> text = null;
	nearestNeighbour nn1 = new nearestNeighbour();
	fitnessCalculation fc1 = new fitnessCalculation();
	double bestFitnessCost =fc1.calculateCost(nn1.calculateInitialPopulation());
	ArrayList <Integer> tempArray2 =fc1.getPathFound();
	ArrayList<ArrayList> chromosomes = getCrossoverChromo(tempArray2);
	ArrayList <ArrayList> currentBest = chromosomes;
	
	public ArrayList<Integer> crossover(double rate){
	 
		double startPoint =110;
		double finalPoint = 0.0001;
		
		while(startPoint>=finalPoint){
		ArrayList <ArrayList> children = new ArrayList<ArrayList>();
		int[] crossoverParents = findFittestParents(chromosomes);
		int firstPoint = crossoverParents[0];//generateRandomPoint(chromosomes.size());
		int secondPoint =crossoverParents[1];//generateRandomPoint(chromosomes.size());
		
		
		
		if(firstPoint == secondPoint ){
			secondPoint=firstPoint+1;
		}
		
		int crossoverPoint;
		if(chromosomes.get(firstPoint).size()<chromosomes.get(secondPoint).size()){
			crossoverPoint =  generateRandomPoint(chromosomes.get(firstPoint).size());
			if(crossoverPoint == chromosomes.get(firstPoint).size()){
				crossoverPoint = crossoverPoint-1;
			}
		}else{
			crossoverPoint = generateRandomPoint(chromosomes.get(secondPoint).size());
			if(crossoverPoint == chromosomes.get(secondPoint).size()){
				crossoverPoint = crossoverPoint-1;
			}
		}
		
		children = improveFitness(doCrossover(chromosomes.get(firstPoint),chromosomes.get(secondPoint),crossoverPoint));
		
		chromosomes.set(firstPoint, children.get(0));
		chromosomes.set(secondPoint, children.get(1));
		
		
		text = combineChildren(chromosomes);
		
		if(fc1.calculateCost(text)<=bestFitnessCost||fc1.calculateCost(text)>bestFitnessCost){
			bestFitnessCost =fc1.calculateCost(text);
			
			System.out.println(fc1.calculateCost(text));
			ArrayList bestPathFound = fc1.getPathFound();
			//chromosomes = getCrossoverChromo(tempArray2);
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
			System.out.println(fc1.calculateCost(text));
			System.out.println("-x-x-x-x-x-x-x-x-x-x FINDING -x-x-x-x-x-x-x-x-x");
			chromosomes = currentBest;
		}
		

		startPoint -=rate;
		
		}
		text = combineChildren(currentBest);
		return text;
		
	}
	
	public ArrayList<ArrayList> improveFitness(ArrayList<ArrayList> children){
		ArrayList <ArrayList> improvedChild = new ArrayList<ArrayList>();
		
		for(int i=0;i<children.size();i++){
			ArrayList <Integer> tempChild = children.get(i);
			int [] improvedVersion = new int[tempChild.size()];
			int firstPlaceNumber = getMinDistanceNode(tempChild,1,false,0);
			int secondPlaceNumber = getMinDistanceNode(tempChild,1,true,firstPlaceNumber);
			
			improvedVersion[0] = firstPlaceNumber;
			improvedVersion[improvedVersion.length-1] = secondPlaceNumber;
			
			for(int y=0;y<improvedVersion.length;y++){
				if(y==0||y==improvedVersion.length-1){
				}else{
					improvedVersion[y] = getMinDistanceNode(tempChild,1,firstPlaceNumber,secondPlaceNumber,improvedVersion);
				}
			}
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for(int y=0;y<improvedVersion.length;y++){
				temp.add(improvedVersion[y]);
			}
			
			improvedChild.add(temp);
		}
		
		
		
		
		return improvedChild;
		
	}
	
	
	public int getMinDistanceNode(ArrayList<Integer>nodes,int fromNode,int flaggedNumber, int secondFlagNumber,int[]tempArray){
		int tempNode = 1;
		ArrayList <Double> distanceFromNode = new ArrayList();
		
		for(int i=0;i<nodes.size();i++){
			distanceFromNode.add(CVRPData.getDistance(fromNode,nodes.get(i)));
		}
		
		double minValue = Integer.MAX_VALUE;
		for(int i=0;i<distanceFromNode.size();i++){
			if(nodes.get(i)!=flaggedNumber){
				if(nodes.get(i)!=secondFlagNumber){
					if(doesItContain(tempArray,nodes.get(i))==false){
						if(distanceFromNode.get(i)<minValue){
								
							minValue = distanceFromNode.get(i);
							tempNode = nodes.get(i);
							
							}
			
						}
				}
			}
		}
		
		return tempNode;
	}
	
	
	
	public int getMinDistanceNode(ArrayList<Integer>nodes,int fromNode, boolean flag,int flaggedNumber){
		if(flag == false){
		int tempNode = 1;
		ArrayList <Double> distanceFromNode = new ArrayList();
		
		for(int i=0;i<nodes.size();i++){
			distanceFromNode.add(CVRPData.getDistance(fromNode,nodes.get(i)));
		}
		
		double minValue = Integer.MAX_VALUE;
		for(int i=0;i<distanceFromNode.size();i++){
			if(distanceFromNode.get(i)<minValue){
				minValue = distanceFromNode.get(i);
				tempNode = nodes.get(i);
			}
		}
		
		return tempNode;
		}else{
			
			int tempNode = 1;
			ArrayList <Double> distanceFromNode = new ArrayList();
			
			for(int i=0;i<nodes.size();i++){
				distanceFromNode.add(CVRPData.getDistance(fromNode,nodes.get(i)));
			}
			
			double minValue = Integer.MAX_VALUE;
			for(int i=0;i<distanceFromNode.size();i++){
				if(nodes.get(i)!=flaggedNumber){
				if(distanceFromNode.get(i)<minValue){
					minValue = distanceFromNode.get(i);
					tempNode = nodes.get(i);
				}
				}
			
			
			
			}
			return tempNode;
		}
	}
	public boolean doesItContain(int[]givenArray,int num){
		boolean flag = false;
		for(int i=0;i<givenArray.length;i++){
			if(givenArray[i]==num){
				flag = true;
			}
		}
		return flag;
	}
	
	public ArrayList<ArrayList> doCrossover(ArrayList<Integer> father,ArrayList<Integer> mother,int crossPoint){
		ArrayList <ArrayList>tempArrayTo = new ArrayList();
		ArrayList <Integer> tempArray = new ArrayList();
		ArrayList <Integer> tempArray2 = new ArrayList();
		double child1Cap=0;
		double child2Cap=0;
		
		for(int i=0;i<crossPoint;i++){
			child1Cap = child1Cap + CVRPData.getDemand(father.get(i));
			child2Cap = child2Cap + CVRPData.getDemand(mother.get(i));
		}
		
		double limitLeft1 = 220-(new fitnessCalculation().calculateCost(father));
		double limitLeft2 = 220-(new fitnessCalculation().calculateCost(mother));
		
	if((child1Cap<=(child2Cap+limitLeft2))&&((child2Cap<=(child1Cap+limitLeft1)))){	
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
	}else{
		tempArray = mother;
		tempArray2 = father;
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
				System.out.println("sdas"+i);
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
	
	
	public int[] findFittestParents(ArrayList<ArrayList> parents){
		int [] fittestParents = new int[2];
		ArrayList<Double> parentsFitness = new ArrayList<Double>();
		
		for(int i=0;i<parents.size();i++){
			parentsFitness.add(new fitnessCalculation().calculateCost(parents.get(i)));
		}
		
		double min = Integer.MAX_VALUE;
		
		for(int i=0;i<parentsFitness.size();i++){
			if(parentsFitness.get(i)<min){
				min = parentsFitness.get(i);
				fittestParents[0] = i;
			}
		}
		
//		double max1 = Integer.MIN_VALUE;
//		for(int i=0;i<parentsFitness.size();i++){
//		
//			if(parentsFitness.get(i)>max1){
//				max1 = parentsFitness.get(i);
//				fittestParents[0] = i;
//			
//		}
//	}		
		double max = Integer.MIN_VALUE;
		for(int i=0;i<parentsFitness.size();i++){
			if(i!=fittestParents[0]){
			if(parentsFitness.get(i)>max){
				max = parentsFitness.get(i);
				fittestParents[1] = i;
			}
		}
			}
		return fittestParents;
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
		Random r1 = new Random();
		return r1.nextInt(limit);
	}
	
	
}
