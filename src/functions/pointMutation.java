package functions;

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
	
	
	public void performPMX(ArrayList<Integer>parentChromo1,ArrayList<Integer>parentChromo2){
		a1 = new ArrayList<Integer>();
		a2 = new ArrayList<Integer>();
		int  swapPoint = (int) (0+(Math.floor(Math.random()*40)));
		int  swapPoint2 = (int) (0+(Math.ceil(Math.random()*40)));
		System.out.println(swapPoint+"   "+swapPoint2);
		
		
		for(int i=0;i<parentChromo1.size();i++){
			if(swapPoint<swapPoint2){
			if(i<=swapPoint){
				a1.add(parentChromo1.get(i));
				a2.add(parentChromo2.get(i));
			}else if(i>swapPoint && i<=swapPoint2){
				a1.add(parentChromo2.get(i));
				a2.add(parentChromo1.get(i));
			}else{
				a1.add(parentChromo1.get(i));
				a2.add(parentChromo2.get(i));
			}
			}else{
				if(i<=swapPoint2){
					a1.add(parentChromo1.get(i));
					a2.add(parentChromo2.get(i));
				}else if(i>swapPoint2 && i<=swapPoint){
					a1.add(parentChromo2.get(i));
					a2.add(parentChromo1.get(i));
				}else{
					a1.add(parentChromo1.get(i));
					a2.add(parentChromo2.get(i));
				}
			}
		}
		
		fitnessCalculation fc1 = new fitnessCalculation();
		System.out.println(fc1.calculateCost(a1)+"    "+fc1.calculateCost(a2));	
		
	}

	
	public ArrayList<Integer> getA1() {
		return a1;
	}


	public ArrayList<Integer> getA2() {
		return a2;
	}


	public ArrayList<Integer> generatedRandomChormo(){
		ArrayList <Integer> tempArray = new ArrayList();
		
		while(tempArray.size()<CVRPData.NUM_NODES){
			int y = 1+(int)(Math.floor((Math.random()*CVRPData.NUM_NODES-1)));
			if(!tempArray.contains(y)){
			tempArray.add(y);
		}
			}
		return tempArray;
	}
}
