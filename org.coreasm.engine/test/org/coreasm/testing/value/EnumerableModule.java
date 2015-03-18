package org.coreasm.testing.value;

import org.coreasm.testing.TestingHelperModule;

public class EnumerableModule extends TestingHelperModule{
	public EnumerableModule(){
		moduleFile = "Enumerable.mod";
	}
	
	@Override
	public String modifyCode(String code){
		return code.replace("CompilerRuntime.Enumerable", "EnumerableMock");
	}
}