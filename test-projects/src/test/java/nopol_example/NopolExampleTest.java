package nopol_example;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class NopolExampleTest {

	 @Test
	public void test_nopol_example_1() {
		 assertEquals(6,new NopolExample().minZero(6));
	}

	@Test
	public void test_nopol_example_2() {
		assertEquals(9,new NopolExample().minZero(9));
	}
	
	//failing test
	@Test
	public void test_nopol_example_3() {
		assertEquals(1,new NopolExample().minZero(1));
	}
	
	@Test
	public void test_nopol_example_4() {
		assertEquals(0,new NopolExample().minZero(-3));
	}

	@Test
	public void test_nopol_example_5() {
		assertEquals(0,new NopolExample().minZero(0));
	}


}
