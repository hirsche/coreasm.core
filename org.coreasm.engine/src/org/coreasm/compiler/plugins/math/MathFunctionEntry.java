package org.coreasm.compiler.plugins.math;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.coreasm.compiler.CompilerOptions;
import org.coreasm.compiler.CoreASMCompiler;
import org.coreasm.compiler.classlibrary.LibraryEntry;
import org.coreasm.compiler.codefragment.CodeFragment;
import org.coreasm.compiler.codefragment.CodeFragmentException;
import org.coreasm.compiler.exception.LibraryEntryException;

/**
 * Represents a mathematical function generated by the math plugin
 * @author Markus Brenner
 *
 */
public class MathFunctionEntry implements LibraryEntry {
	private String name;
	private CodeFragment body;
	
	/**
	 * Initializes the function entry
	 * @param name The name of the function
	 * @param body The code body of the function
	 */
	public MathFunctionEntry(String name, CodeFragment body){
		this.name = name;
		this.body = body;
	}

	@Override
	public void writeFile() throws LibraryEntryException {
		CompilerOptions options = CoreASMCompiler.getEngine().getOptions();
		File directory = new File(options.tempDirectory + File.separator + "plugins" + File.separator + "MathPlugin");
		File file = new File(directory, name + ".java");
		
		if(file.exists()) throw new LibraryEntryException(new Exception("file already exists"));
		
		BufferedWriter bw = null;
		
		directory.mkdirs();
		try {
			file.createNewFile();
		
			bw = new BufferedWriter(new FileWriter(file));
		
			bw.write(this.generate());
		} 
		catch (IOException e) {
			throw new LibraryEntryException(e);
		} catch (CodeFragmentException e) {
			throw new LibraryEntryException(e);
		} 
		finally{
			try{
				bw.close();
			}
			catch(IOException e){
			}
		}
	}

	private String generate() throws CodeFragmentException{
		String result = "package plugins.MathPlugin;\n"
				+ "import CompilerRuntime.*;\n"
				+ "import java.util.List;\n"
				+ "import plugins.NumberPlugin.NumberElement;\n"
				+ "public class " + name + " extends MathFunction{\n";
		
		result += body.generateCode();
		
		result += "}\n";
	
		return result;
	}

	@Override
	public String getFullName() {
		return "plugins.MathPlugin." + name;
	}

}
