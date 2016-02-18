package poc_nopol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.IOUtils;

import spoon.Launcher;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtAnnotationType;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.support.reflect.declaration.CtAnnotationTypeImpl;



public class Main {

	public final static char fileSeparator = '/';
	public final static String outputsDir = "outputs";
	public final static String evoOutput = "generatedTests";
	
	public static void main(String[] args) throws IOException, ParseException {
		
		String typeOfBug = null;
		String numberOfBug = null;
		String bugFolder = null;
		String cpClassFolder = null;
		String cpTestFolder = null;
		String destSrcTestDir = null;
		String newTestDir = null;
		String classPath = null;
		
        Options options = new Options();
        options.addOption("typeOfBug", true, "type of bug (Chart, Math, Lang or Time");
        options.addOption("numberOfBug", true, "number of bug (ex: 85 for Math85)");
        options.addOption("bugFolder", true, "bug folder (ex: bugs/Math_85)");
        options.addOption("cpClassFolder", true, "classes files (ex: target/classes");
        options.addOption("cpTestFolder", true, "classes test files (ex: target/test-classes)");
        options.addOption("destSrcTestDir", true, "java files (ex: src/test)");
        options.addOption("newTestDir", true, "location of news tests files");
        options.addOption("classPath", true, "all classes required for the news tests (ex: cpclassfolder, junit, hamcrest, evosuite)");
        
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        
        if (cmd.getOptionValue("typeOfBug") != null) {
        	typeOfBug = cmd.getOptionValue("typeOfBug");
        }
        
        if (cmd.getOptionValue("numberOfBug") != null) {
        	numberOfBug = cmd.getOptionValue("numberOfBug");
        }
        
        if (cmd.getOptionValue("bugFolder") != null) {
        	bugFolder = cmd.getOptionValue("bugFolder");
        }
        
        if (cmd.getOptionValue("cpClassFolder") != null) {
        	cpClassFolder = cmd.getOptionValue("cpClassFolder");
        }
        
        if (cmd.getOptionValue("cpTestFolder") != null) {
        	cpTestFolder = cmd.getOptionValue("cpTestFolder");
        }
        
        if (cmd.getOptionValue("destSrcTestDir") != null) {
        	destSrcTestDir = cmd.getOptionValue("destSrcTestDir");
        }
        
        if (cmd.getOptionValue("newTestDir") != null) {
        	newTestDir = cmd.getOptionValue("newTestDir");
        }
        
        if (cmd.getOptionValue("classPath") != null) {
        	classPath = cmd.getOptionValue("classPath");
        }
        
        if(!cmd.hasOption("typeOfBug") || !cmd.hasOption("numberOfBug") || !cmd.hasOption("bugFolder") ||
        		!cmd.hasOption("cpClassFolder") || !cmd.hasOption("cpTestFolder") || !cmd.hasOption("destSrcTestDir") ||
        		!cmd.hasOption("newTestDir") || !cmd.hasOption("classPath")) {
    		HelpFormatter formatter = new HelpFormatter();
    		formatter.printHelp( "list des parametres", options );
    		System.exit(0);
    	}
        
        tryAllTests(typeOfBug, numberOfBug, bugFolder, cpClassFolder, cpTestFolder, destSrcTestDir, newTestDir, classPath);

	}

	
	/**
	 * this method analyze all java class in generatedTestDir and return paths composed of package + class
	 * @param generatedTestDir
	 * @param classPath
	 * @return
	 */
	public static List<String> getNewTestsFilesPaths(String generatedTestDir, String classPath){
		List<String> testsFiles = new ArrayList<String>();
		System.out.println("--------------------------------------------------");
		System.out.println(" ##### Search tests files path ##### ");
		Launcher spoon = new Launcher();
		spoon.getEnvironment().setAutoImports(true);
		//TestSelectionProcessor = new TestSelectionProcessor(listProc);
		System.out.println(classPath);
		spoon.run(new String[]{"-i",generatedTestDir,"--source-classpath",classPath,"--compile"});

		//getannotatedMethod.. could be better
		for(CtType<?> clazz : spoon.getFactory().Class().getAll()){
			String filePath = clazz.getPackage().toString().replace('.', fileSeparator)+"/"+clazz.getSimpleName();
			System.out.println("[FOUND] "+filePath);
			testsFiles.add(filePath);
		}
		
		return testsFiles;
	}
	
	
	/**
	 * this method analyze all java class in generatedTestDir and return a list of all Junit method
	 * @param generatedTestDir
	 * @param classPath
	 * @return
	 */
	public static List<CtMethod> getNewTestsMethods(String generatedTestDir, String classPath){
		List<CtMethod>  testsMethods = new ArrayList<CtMethod>();
		
		System.out.println("--------------------------------------------------");
		System.out.println(" ##### Search tests methods ##### ");
		Launcher spoon = new Launcher();
		spoon.getEnvironment().setAutoImports(true);
		//TestSelectionProcessor = new TestSelectionProcessor(listProc);
		spoon.run(new String[]{"-i",generatedTestDir,"--source-classpath",classPath,"--compile"});

		//getannotatedMethod.. could be better
		for(CtType<?> clazz : spoon.getFactory().Class().getAll()){
			methodLoop:
				for(CtMethod<?> method : clazz.getAllMethods()){
					for(CtAnnotation<? extends Annotation> annotation : method.getAnnotations()){
						if(annotation.getSignature().equals("@org.junit.Test")){
							System.out.println("[FOUND] "+method.getSignature());
							testsMethods.add(method);
							continue methodLoop;
						}
					}
				}
		}
		
		return testsMethods;
	}

	/**
	 * 
	 * @param testFolder
	 * @param cpClassFolder
	 * @param cpTestFolder
	 * @param testFiles
	 */
	public static void compileTests(String testFolder, String cpClassFolder, String cpTestFolder, List<String> testFiles){

		List<String> cmd = new ArrayList<String>();
		cmd.add("./compile_evo.sh");
		cmd.add(testFolder);
		cmd.add(cpClassFolder);
		cmd.add(cpTestFolder);
		cmd.addAll(testFiles);
		
		System.out.println("./compile_evo.sh "+testFolder+" "+cpClassFolder+" "+cpTestFolder+" "+testFiles);

		ProcessBuilder pb = new ProcessBuilder(cmd);
		Map<String, String> env = pb.environment();
		Process p = null;
		try {
			p = pb.start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param typeOfBug
	 * @param numberOfBug
	 * @param bugFolder
	 * @param numberOfTurn
	 * @return
	 * @throws PatchNotFoundException 
	 */
	public static String NopolPatchGeneration(String typeOfBug, String numberOfBug, String bugFolder, int numberOfTurn) throws PatchNotFoundException{
		
		
		List<String> cmd = new ArrayList<String>();
		cmd.add("./nopol_launcher.sh");
		cmd.add(typeOfBug);
		cmd.add(numberOfBug);
		cmd.add(bugFolder);
		
		
		ProcessBuilder pb = new ProcessBuilder(cmd);
		Map<String, String> env = pb.environment();
		Process p = null;
		try {
			//pb.inheritIO(); //doesnt work caus maven http://stackoverflow.com/questions/17525441/redirect-i-o-of-subprocess-in-java-why-doesnt-processbuilder-inheritio-work
			p = pb.start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		StringBuilder builder = new StringBuilder();
		String line = null;
		try {
			while ( (line = reader.readLine()) != null) {
				builder.append(line);
				builder.append(System.getProperty("line.separator"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String result = builder.toString();
		
		try{
			new File(outputsDir+fileSeparator+typeOfBug+"_"+numberOfBug).mkdirs();
			File file = new File(outputsDir+fileSeparator+typeOfBug+"_"+numberOfBug+fileSeparator+numberOfTurn+".txt");
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(result);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(!result.contains("----PATCH FOUND----")){
			throw new PatchNotFoundException();
		}
		return result;
	}
	
	
	/**
	 * 
	 * @param typeOfBug
	 * @param numberOfBug
	 * @param bugFolder
	 * @param cpClassFolder
	 * @param cpTestFolder
	 * @param destSrcTestDir
	 * @param newTestDir
	 * @param classPath
	 */
	public static void tryAllTests(String typeOfBug, String numberOfBug, String bugFolder, 
			String cpClassFolder, String cpTestFolder, String destSrcTestDir, String newTestDir, 
			String classPath){
		
		String result = null;
		String className = null;
		List<CtMethod>  keptMethods = new ArrayList<CtMethod>();
		List<String> testsFiles = getNewTestsFilesPaths(newTestDir, classPath);
		List<CtMethod> testsMethods = getNewTestsMethods(newTestDir, classPath);

		System.out.println("--------------------------------------------------");
		System.out.println(" ##### launch nopol without new tests ##### ");
		try {
			result = NopolPatchGeneration(typeOfBug, numberOfBug, bugFolder, -1);
		} catch (PatchNotFoundException e) {
			System.out.println("No patch found without new tests");
			return;
		}
		
		System.out.println("patch found, let's try with evosuite");
		className = extractClassPathFromNopolStdOut(result);
		System.out.println(" #### run EvoSuite on "+className+" #### ");
		generateEvoSuiteTests(classPath, className);
		/*System.out.println("###########################################");
		System.out.println("######## start to try each methods ########");
		System.out.println("###########################################");
		List<String> filesToCompile = new ArrayList<String>();
		int numberOfTurn = 0;
		for(CtMethod method : testsMethods){
			System.out.println("--------------------------------------------------");
			System.out.println("# TEST METHOD : "+method.getSignature());
			System.out.println("--------------------------------------------------");

			System.out.println("### Remove EvoSuite ");
			filesToCompile = new ArrayList<String>();
			Launcher spoonLauncher = new Launcher();
			spoonLauncher.addProcessor(new TestSelectionProcessor(method, keptMethods));
			spoonLauncher.addProcessor(new RemoveEvosuiteEffectsProcessor());
			spoonLauncher.run(new String[]{"-i",newTestDir,"--source-classpath",classPath,"-o",destSrcTestDir,"--compile","-d",cpTestFolder});
			for(String testPath : testsFiles){
				//System.out.println("------ "+destSrcTestDir+fileSeparator+testPath+".java");
				filesToCompile.add(destSrcTestDir+fileSeparator+testPath+".java");
			}
			compileTests(destSrcTestDir, cpClassFolder, cpTestFolder, filesToCompile);
			

			System.out.println("### Launch Nopol");
			patchFound = NopolPatchGeneration(typeOfBug, numberOfBug, bugFolder, numberOfTurn);
			if(patchFound){
				System.out.println("### ----- PATCH FOUND -----");
				System.out.println("### METHOD KEPT : "+method.getSignature());
				keptMethods.add(method);
			}else{
				System.out.println("### ----- NO PATCH FOUND -----");
				System.out.println("### METHOD REMOVED : "+method.getSignature());
			}
			numberOfTurn++;
		}*/
	}
	
	
	public static String generateEvoSuiteTests(String classPath, String className){
		List<String> cmd = new ArrayList<String>();
		cmd.add("java");
		cmd.add("-jar");
		cmd.add("evosuite-1.0.2.jar");
		cmd.add("-class");
		cmd.add(className);
		cmd.add("-projectCP");
		cmd.add(classPath);
		cmd.add("-generateSuite");
		cmd.add("-Dsearch_budget=30");
		cmd.add("-Dstopping_condition=MaxTime");
		cmd.add("-Dno_runtime_dependency=true");
		cmd.add("-Dtest_dir="+evoOutput);
		
		ProcessBuilder pb = new ProcessBuilder(cmd);
		System.out.println("ABCDEF== "+pb.command());
		Map<String, String> env = pb.environment();
		Process p = null;
		try {
			//pb.inheritIO(); //doesnt work caus maven http://stackoverflow.com/questions/17525441/redirect-i-o-of-subprocess-in-java-why-doesnt-processbuilder-inheritio-work
			p = pb.start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		StringBuilder builder = new StringBuilder();
		String line = null;
		try {
			while ( (line = reader.readLine()) != null) {
				builder.append(line);
				builder.append(System.getProperty("line.separator"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String result = builder.toString();
		
		try{
			File file = new File("evoOutput.txt");
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(result);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return evoOutput;
	}
	
	public static String extractClassPathFromNopolStdOut(String stdOut){
		//extract file path of patch.
		String output = stdOut.split("----PATCH FOUND----")[1].replace("\n", "").replace("\r", "");
    	String[] datas = output.split(":");
    	return datas[0];
	}
	
}
