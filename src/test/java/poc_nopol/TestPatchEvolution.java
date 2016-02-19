package poc_nopol;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class TestPatchEvolution {

	@Test
	public void test() throws IOException {
		String cpClassFolder = "test-projects/target/classes";
		String cpTestFolder = "test-projects/target/test-classes";
		String srcClassFolder = "test-projects/src/main/java";
		String srcTestFolder = "test-projects/src/test/java";
		String destSrcTestFolder = "test-projects/destSrcTest";
		String destCpTestFolder = "test-projects/destCpTest";
		String newTestFolder = "test-projects/generated";
		String classPath = "junit-4.11.jar";
		boolean generateTest = false;

		//remove old java tests
		FileUtils.deleteDirectory(new File(destSrcTestFolder));
		FileUtils.deleteDirectory(new File(destCpTestFolder));
		
		Main.tryAllTests(cpClassFolder, cpTestFolder, srcClassFolder, srcTestFolder, destSrcTestFolder, destCpTestFolder, newTestFolder, classPath, generateTest);
		
		for(Map.Entry entry : Main.patches.entrySet()){
			System.out.println(entry.getValue()+" "+entry.getKey());
		}

		//check if we got the rights patches
		assertEquals(" CONDITIONAL number < nopol_example.NopolExample.this.value", Main.patches.get("basic"));
		assertEquals(" CONDITIONAL number < 1",Main.patches.get("test_nopol_example_generated_0"));
		assertEquals(null,Main.patches.get("test_nopol_example_generated_1"));
		
	}

}
