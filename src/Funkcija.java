/*
 *
 *	Author: Nejc Saje
 *	nejc.saje@gmail.com
 *
 */

import ac.essex.graphing.plotting.ContinuousFunctionPlotter; 
import org.nfunk.jep.JEP;
import org.nfunk.jep.ParseException;

public class Funkcija extends ContinuousFunctionPlotter { 
	
	String expression;
	JEP jep;
	
	public Funkcija() {
		jep = new JEP();
		jep.addVariable("x", 0);
		jep.addStandardConstants();
		jep.addStandardFunctions();
	}
 
    public double getY(double x) { 
        jep.setVarValue("x", x);
        return jep.getValue();
    } 
 
    public void nastaviFunkcijo(String f) throws ParseException { 
    	jep.parseExpression(f);
    }
    
    public String getName() {
    	return "Generic function";
    }
 
} 

