 package com.cricket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CricketScoreCalculatorTests {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testMain() {
		assertTrue(true);
	}
	@Test
	public void testFindArrayLengthNotDivisibleBySix() {
		int arrayLength=CricketScoreCalculator.findArrayLength(14);
		assertEquals(3,arrayLength);
	}
	@Test
	public void testFindArrayLength() {
		int arrayLength=CricketScoreCalculator.findArrayLength(12);
		assertEquals(2,arrayLength);
	}
	
	@Test
	public void testCheckIfAllInputValid() {
		int[] scores=new int[] {1,2,3};
		boolean result=CricketScoreCalculator.checkIfAllInputValid(scores);
		assertTrue(result); 
	}
	
	@Test
	public void testCheckIfAllInputValidFalse() {
		int[] scores=new int[] {1,-2,3};
		boolean result=CricketScoreCalculator.checkIfAllInputValid(scores);
		assertFalse(result); 
	}
	
	@Test
	public void testCheckIfIndexOfSix() {
		assertTrue(CricketScoreCalculator.checkIfIndexOfSix(12));
	}
	@Test
	public void testCheckIfIndexOfSixNo() {
		assertFalse(CricketScoreCalculator.checkIfIndexOfSix(13));
	}
	
	@Test
	public void testEachOverScoreCalculator() {
		int[] scores=new int [] {1,1,1,1,1,1};
		assertEquals(6,CricketScoreCalculator.eachOverScoreCalculator(0, scores));
	}
	
	@Test
	public void testEachOverScoreCalculatorIfOverNotComplete() {
		int[] scores=new int [] {1,1,1,1,1,1,6};
		assertEquals(0,CricketScoreCalculator.eachOverScoreCalculator(7, scores));
	}
	
	@Test
	public void testTotalScoreCalculator() {
		int[] scores=new int [] {1,1,1,1,1,1,6};
		assertEquals(12,CricketScoreCalculator.totalScoreCalculator(scores));
	}
	
	@Test
	public void testDivideBallsPerOver() {
		assertEquals(0,CricketScoreCalculator.divideBallsPerOver(12)[0]);
		assertEquals(6,CricketScoreCalculator.divideBallsPerOver(12)[1]);

	}
	@Test
	public void testDivideBallsPerOverIfOverNotComplete() {
		assertEquals(0,CricketScoreCalculator.divideBallsPerOver(14)[0]);
		assertEquals(6,CricketScoreCalculator.divideBallsPerOver(14)[1]);
		assertEquals(14,CricketScoreCalculator.divideBallsPerOver(14)[2]);

	}

}
