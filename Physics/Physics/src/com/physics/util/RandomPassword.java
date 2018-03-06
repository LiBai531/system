package com.physics.util;

import java.util.Random;

public class RandomPassword {
	public static String getRandomPassword(){
		int[] numbers = getRandomIntarray();
		StringBuilder randomPassword = new StringBuilder("0123456789");
		
		randomPassword.replace(numbers[0],numbers[0]+1,getRandomSpecailChar() );
		randomPassword.replace(numbers[1],numbers[1]+1,getRandomSpecailChar() );
		randomPassword.replace(numbers[2],numbers[2]+1,getRandomChar() );
		randomPassword.replace(numbers[3],numbers[3]+1,getRandomChar() );
		randomPassword.replace(numbers[4],numbers[4]+1,getRandomChar() );
		randomPassword.replace(numbers[5],numbers[5]+1,getRandomChar() );
		randomPassword.replace(numbers[6],numbers[6]+1,getRandomInt() );
		randomPassword.replace(numbers[7],numbers[7]+1,getRandomInt() );
		randomPassword.replace(numbers[8],numbers[8]+1,getRandomInt() );
		randomPassword.replace(numbers[9],numbers[9]+1,getRandomInt() );
		
		return randomPassword.toString();
	}
	
	private static int[] getRandomIntarray(){
		int[] numbers = {0,1,2,3,4,5,6,7,8,9};
		int index,t;
		for(int i = 0 ;i < numbers.length ;i++){
		   index = new Random().nextInt(3);
		   t = numbers[i];
		   numbers[i] = numbers[index];
		   numbers[index] = t;
		 }
		return numbers;
	}
	
	private static String getRandomSpecailChar(){
		String sc = "@#$%";
		int index = new Random().nextInt(4);
		return sc.substring(index, index+1);
	}
	
	private  static String getRandomChar(){
		String sc = "qwertyuioplkjhgfdsazxcvbnm";
		int index = new Random().nextInt(26);
		return sc.substring(index, index+1);
	}
	
	private static String getRandomInt(){
		String sc = "0123456789";
		int index = new Random().nextInt(10);
		return sc.substring(index, index+1);
	}
}
