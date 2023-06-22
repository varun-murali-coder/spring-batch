package com.cricket;

public class CricketScoreCalculator {

	public static void main(String[] args) {
int [] scores=new int[] {1,2,3,4,6,1,1,1,2,3,4,4,4,4,4,4,6,6};
int [] divBallsPerOver=divideBallsPerOver(scores.length);
if(checkIfAllInputValid(scores)) {
int totalScore=totalScoreCalculator(scores);
System.out.println("Total score is:"+totalScore);
for(int i=0;i<divBallsPerOver.length;i++) {
	if(checkIfIndexOfSix(divBallsPerOver[i])) {
		System.out.println("Over "+(i+1)+" score:"+eachOverScoreCalculator(divBallsPerOver[i],scores));
	}else {
		System.out.println("Over "+(i+1)+" still in progress only "+divBallsPerOver[i]%6+" balls bowled");
	}
}
}else {
	System.out.println("The input scores provided is not valid");
}
	}

	public static int eachOverScoreCalculator(int startIndex, int[] scores) {
		int runsScoredInOver=0;
		int endIndex=startIndex+6;
		if(checkIfIndexOfSix(startIndex)) {
			for(int i=startIndex;i<endIndex;i++) {
				runsScoredInOver+=scores[i];
			}
		}
		return runsScoredInOver;
	}

	public static int totalScoreCalculator(int[] scores) {
		int totalScore=0;
		for(int i=0;i<scores.length;i++) {
			totalScore+=scores[i];
		}
		return totalScore;
	}

	public static int[] divideBallsPerOver(int oversLength){
		int[] ballsDistPerOver=new int[findArrayLength(oversLength)];
		int startIndex=0;
		for(int i=0;i<findArrayLength(oversLength);i++) {
			ballsDistPerOver[i]=startIndex;
			if(checkIfIndexOfSix(oversLength))
				startIndex+=6;
			else {
				if(startIndex+6<=oversLength-6)
					startIndex+=6;
				else
					startIndex=startIndex+6+(oversLength%6);
					
			}
		}
		return ballsDistPerOver;
	}

	public static boolean checkIfIndexOfSix(int index) {
		// TODO Auto-generated method stub
		return index%6==0?true:false;
	}

	public static int findArrayLength(int inputLength) {
int arrayLength=inputLength/6;
if(inputLength%6!=0)
	arrayLength+=1;
		return arrayLength;
	}
	
	public static boolean checkIfAllInputValid(int [] input) {
		boolean isInputValid=true;
		for(int i=0;i<input.length;i++) {
			if(input[i]<0) {
				isInputValid=false;
				break;
			}
		}
		return isInputValid;
	}

}
