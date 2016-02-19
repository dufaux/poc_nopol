package nopol_example;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class NopolExampleGeneratedTest {

	//test ajouté par evosuite
	@Test
	public void test_nopol_example_generated_0(){
		NopolExample ne = new NopolExample();
		ne.setValue(10);
		assertEquals(10,ne.getValue());
		assertEquals(6,ne.minZero(6));
	}

	//test ajouté par evosuite (qui foire le patch) fige le bug
	@Test
	public void test_nopol_example_generated_1(){
		NopolExample ne = new NopolExample();
		assertEquals(0,ne.minZero(1));	
	}


}
