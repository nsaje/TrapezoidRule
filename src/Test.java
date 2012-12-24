import org.nfunk.jep.*;


public class Test {

	public static void main(String[] args) {

		JEP jep = new JEP();
		jep.addStandardConstants();
		jep.addStandardFunctions();
		jep.addVariable("x", 10);
		jep.parseExpression("sin(x)");
		System.out.println(jep.getValue());
		
	}

}
