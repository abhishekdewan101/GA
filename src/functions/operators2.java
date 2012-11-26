package functions;
import java.util.*;

import main.CVRPData;


public class operators2 {
	
	double startPoint = 110.0;
	double endPoint = 0.1;
	double rateOfDecay = 0.99;
	int numberOfCycles = 100;
	int count =0;
	
	nearestNeighbour nn1 = new nearestNeighbour();
	fitnessCalculation fc1 = new fitnessCalculation();
	ArrayList<int[]>workChromosomes;
	ArrayList<int[]>bestChromosomes;
	int[] nowSolution = new int[CVRPData.NUM_NODES-1];//now
	int[] workSolution = new int[CVRPData.NUM_NODES-1];//working
	int[] bestSolution = new int[CVRPData.NUM_NODES-1];//best
	
	double nowSolutionCost,workSolutionCost,bestSolutionCost;

	public void doOperation(){
		
		boolean foundSolution = false;
		boolean changeSolution = false;
		int acceptance = 0;
		int length = CVRPData.NUM_NODES-1;
		double iterator = startPoint;
		
		nn1.calculateInitialPopulation();
		System.arraycopy((nn1.getPathInArray()), 0,nowSolution, 0,length);
		nowSolutionCost = fc1.getFitnessWithArray(nowSolution);
		bestSolutionCost = nowSolutionCost;
		System.arraycopy(nowSolution, 0, workSolution, 0, length);
		
		while(iterator>endPoint){
			count =0;
			for(int i=0;i<numberOfCycles;i++){
				changeSolution = false;
				//createChange(); /// do something to the working solution
				doCrossover();
				workSolutionCost = getFitness();
				
				if(workSolutionCost<=nowSolutionCost){
					changeSolution = true;
				}else{
					double prob = new Random().nextDouble();
					double changeInDistance = workSolutionCost - nowSolutionCost;
					double calculatedValue = Math.exp(-changeInDistance/iterator);
					if(calculatedValue>prob){
						count++;
						changeSolution = true;
					}
				}
				
				if(changeSolution == true){
					changeSolution = false;
					nowSolutionCost = workSolutionCost;
					System.arraycopy(workSolution, 0, nowSolution, 0, length);
					if(nowSolutionCost<bestSolutionCost){
						bestSolutionCost = nowSolutionCost;
						bestChromosomes = workChromosomes;
						System.arraycopy(nowSolution, 0, bestSolution, 0, length);
						foundSolution = true;
					}
				}else{
					System.arraycopy(nowSolution, 0, workSolution, 0, length);
				}
				System.out.println("Now Solution Cost="+nowSolutionCost);
				System.out.println("Work Solution Cost="+workSolutionCost);
				System.out.println("Best Solution Cost="+bestSolutionCost);
				System.out.println();
			}
			
			if(foundSolution==true){
				//System.out.println("Best Solution Cost="+bestSolutionCost);
				printResults(bestChromosomes);
			}
			iterator *= rateOfDecay;
		}
	}

	private void printResults(){
		for(int i=0;i<workChromosomes.size();i++){
			int[] tempArray = workChromosomes.get(i);
			System.out.print(1+"->");
			for(int y=0;y<tempArray.length;y++){
				System.out.print(tempArray[y]+"->");
			}
			System.out.print(1);
			System.out.println();	
		}
	}
	
	
	private void printResults(ArrayList<int[]> solutions){
		for(int i=0;i<solutions.size();i++){
			int[] tempArray = solutions.get(i);
			System.out.print(1+"->");
			for(int y=0;y<tempArray.length;y++){
				System.out.print(tempArray[y]+"->");
			}
			System.out.print(1);
			System.out.println();	
		}
	}
	
	private int[] bestPathFound(){
		ArrayList<Integer>bestPath = new ArrayList<Integer>();
		
		return null;
		
	}
	 
	private double getFitness() {
		double fitness=0;
		
		for(int i=0;i<workChromosomes.size();i++){
			int[] tempArray = workChromosomes.get(i);
			double tempFitness = 0.0;
			for(int y=0;y<tempArray.length;y++){
				if(y==0){
					tempFitness = tempFitness + CVRPData.getDistance(1, tempArray[y]);
				}else if(y==tempArray.length-1){
					tempFitness = tempFitness + CVRPData.getDistance(tempArray[y-1],tempArray[y]);
					tempFitness = tempFitness + CVRPData.getDistance(tempArray[y], 1);
				}else{
					tempFitness = tempFitness + CVRPData.getDistance(tempArray[y-1],tempArray[y]);
				}
			}
			fitness = fitness + tempFitness;
		}
		
		return fitness;
	}

	private int[] findParents(ArrayList<int[]>chromosomes) {
		double[]fitnessCostPerParent = new double[chromosomes.size()];
		int[]parents = new int[2];
		
		for(int i=0;i<chromosomes.size();i++){
			int[] tempArray = chromosomes.get(i);
			double tempFitness = 0.0;
			for(int y=0;y<tempArray.length;y++){
				if(y==0){
					tempFitness = tempFitness + CVRPData.getDistance(1, tempArray[y]);
				}else if(y==tempArray.length-1){
					tempFitness = tempFitness + CVRPData.getDistance(tempArray[y-1],tempArray[y]);
					tempFitness = tempFitness + CVRPData.getDistance(tempArray[y], 1);
				}else{
					tempFitness = tempFitness + CVRPData.getDistance(tempArray[y-1],tempArray[y]);
				}
			}
			fitnessCostPerParent[i] = tempFitness;
		}
		
		double min = Integer.MAX_VALUE;
		
		for(int i=0;i<fitnessCostPerParent.length;i++){
			if(fitnessCostPerParent[i]<min){
				min = fitnessCostPerParent[i];
				parents[0] = i;
			}
		}
		
//		double max = Integer.MIN_VALUE;
//		
//		for(int i=0;i<fitnessCostPerParent.length;i++){
//			if(fitnessCostPerParent[i]>max){
//				max = fitnessCostPerParent[i];
//				parents[1] = i;
//			}
//		}
		min = Integer.MAX_VALUE;
		
		for(int i=0;i<fitnessCostPerParent.length;i++){
			if(doesItContain(parents,i)==false){
			if(fitnessCostPerParent[i]<min){
				min = fitnessCostPerParent[i];
				parents[0] = i;
			}}
		}
		
		
		return parents;
	}
	private void createChange() {
		
		int swapPoint1 = new Random().nextInt(workSolution.length);
		int swapPoint2 = 5;//new Random().nextInt(workSolution.length);
		
		int temp = workSolution[swapPoint1];
		workSolution[swapPoint1] = workSolution[swapPoint2];
		workSolution[swapPoint2] = temp;

	}
	
	public int calculateRemainingCapacity(int[] parent){
		int remainingCapacity=0;
		
		for(int i=0;i<parent.length;i++){
			remainingCapacity = remainingCapacity + CVRPData.getDemand(parent[i]);
		}
		return remainingCapacity;
	}
	
	public void doCrossover(){
		int [] bestPathFound = fc1.getPathFoundAsArray();
		
		ArrayList<int[]> chromosomes = createChromosomes(bestPathFound);
		int [] parents = findParents(chromosomes);
		int firstParent = parents[0];			//new Random().nextInt(chromosomes.size());
		int secondParent =parents[1];			//new Random().nextInt(chromosomes.size());
		int crossoverPoint;
		
		
		if(firstParent==secondParent){
			secondParent = secondParent-firstParent+1;
		}
		int[] parent1 = chromosomes.get(firstParent);
		int[] parent2 = chromosomes.get(secondParent);
		
		if(parent1.length>parent2.length){
			crossoverPoint = new Random().nextInt(chromosomes.get(secondParent).length);
			}else{
				crossoverPoint = new Random().nextInt(chromosomes.get(firstParent).length);
			}
		
		
		
		int parent1Capacity =0;
		int parent2Capacity =0;
		int secondParentCrossoverPoint=0;
		int count=0;
		ArrayList<Integer>child1 = new ArrayList<Integer>();
		ArrayList<Integer>child2 = new ArrayList<Integer>();
		
		for(int i=0;i<crossoverPoint;i++){
			parent1Capacity = parent1Capacity + CVRPData.getDemand(parent1[i]);
		}
		
		for(int i=0;i<parent2.length;i++){
			parent2Capacity = parent2Capacity + CVRPData.getDemand(parent2[i]);
			secondParentCrossoverPoint = i;
			if(parent2Capacity > parent1Capacity){
				break;
			}
			
		}
	
		
		
			for(int i=0;i<crossoverPoint;i++){
				child1.add(parent1[i]);
			}
			
			for(int i=secondParentCrossoverPoint;i<parent2.length;i++){
				child1.add(parent2[i]);
			}
			
			
			
			for(int i=crossoverPoint;i<parent1.length;i++){
				child2.add(parent1[i]);
			}
			
			for(int i=0;i<secondParentCrossoverPoint;i++){
				child2.add(parent2[i]);
			}
			
		
		
		chromosomes.set(firstParent,bestChromosome(toArray(child1)));
		chromosomes.set(secondParent,bestChromosome(toArray(child2)));
		workChromosomes = new ArrayList<int[]>();
		for(int i=0;i<chromosomes.size();i++){
			if(chromosomes.get(i)!=null){
			workChromosomes.add(chromosomes.get(i));
		}
			}
		changeWork(chromosomes);
	}
	
	
	
	

	private int[] bestChromosome(int[] array) {
		int [] temp = new int[array.length];
		double[] distance = new double[temp.length];
		
		for(int i=0;i<distance.length;i++){
			distance[i] = CVRPData.getDistance(1, array[i]);
		}
		
		double min = Integer.MAX_VALUE;
		int minPosition1 = 0,minPosition2 = 0;
		for(int i=0;i<distance.length;i++){
			if(distance[i]<min){
				min = distance[i];
				minPosition1 = i;
			}
		}
		min = Integer.MAX_VALUE;
		for(int i=0;i<distance.length;i++){
			if(i!=minPosition1){
			if(distance[i]<min){
				min = distance[i];
				minPosition2 = i;
			}
			}
		}
		
		for(int i=0;i<temp.length;i++){
			if(i==0){
				temp[i] = array[minPosition1];
			}else if(i==temp.length-1){
				temp[i] = array[minPosition2];
			}else{
				temp[i] = calcualteNearestNode(array,minPosition1,minPosition2,temp[i-1],temp);
			}
		}
		
		return temp;
	}

	private int calcualteNearestNode(int[] array, int minPosition1,int minPosition2, int from,int[] temp) {
		int nodePosition =0;
		double distances [] = new double[array.length];
		
		for(int i=0;i<array.length;i++){
			if(i==minPosition1||i==minPosition2 || doesItContain(temp,array[i])==true){
			distances[i] = Integer.MAX_VALUE;	
			}else{
			distances[i] = CVRPData.getDistance(from, array[i]);
		}
			}
		
		double min = Integer.MAX_VALUE;
		
		for(int i=0;i<distances.length;i++){
			if(distances[i]<min){
				min = distances[i];
				nodePosition = array[i];
			}
		}
		
		
		return nodePosition;
	}

	private boolean doesItContain(int[] temp, int number) {
		boolean contains = false;
		
		for(int i=0;i<temp.length;i++){
			if(temp[i]==number){
				contains = true;
			}
		}
		
		return contains;
	}

	private void changeWork(ArrayList<int[]> chromosomes) {
		int[] tempArray = new int[CVRPData.NUM_NODES-1];
		int count = 0;
		for(int i=0;i<chromosomes.size();i++){
			if(chromosomes.get(i)!=null){
			int [] tempArray2 = chromosomes.get(i);
			for(int y=0;y<tempArray2.length;y++){
				if(count<CVRPData.NUM_NODES-1){
				tempArray[count] = tempArray2[y];
				count++;}
			}
		}
		}
		System.arraycopy(tempArray, 0, workSolution, 0, tempArray.length);
		
	}

	public int[] toArray(ArrayList<Integer>givenArray){
		int[] tempArray = new int[givenArray.size()];
		
		for(int i=0;i<givenArray.size();i++){
			if(givenArray.get(i)!=null){
			tempArray[i] = givenArray.get(i);
		}
		}	
		return tempArray;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<int[]>createChromosomes(int[] bestPathFound){
		ArrayList<int[]> tempList = new ArrayList<int[]>();
		ArrayList<Integer>tempArray = new ArrayList<Integer>();
		for(int i=0;i<bestPathFound.length;i++){
			if(i==0){
				
			}else if(i==bestPathFound.length-1){
				
			}else if(bestPathFound[i]==bestPathFound[i+1]){
				tempList.add(toArray(tempArray));
				tempArray = new ArrayList<Integer>();
			}else if(bestPathFound[i]==bestPathFound[i-1]){}else{
				tempArray.add(bestPathFound[i]);
			}
			if(i==bestPathFound.length-1){
				tempList.add(toArray(tempArray));
			}
		}	
		
		return tempList;
	}
	
	public int[] createRandomPopulation(){
		int[] tempArray = new int[CVRPData.NUM_NODES-1];
		int count =0;
		for(int i=2;i<77;i++){
			tempArray[count] = i;
			count++;
		}
		
		return tempArray;
		
	}
	
	public static void main(String[] args){
		operators2 o2 = new operators2();
		o2.doOperation();
	}
	
	
}
