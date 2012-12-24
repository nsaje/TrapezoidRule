/*
 *
 *	Author: Nejc Saje
 *	nejc.saje@gmail.com
 *
 */

import java.awt.Graphics;

import ac.essex.graphing.plotting.Graph;
import ac.essex.graphing.plotting.Plotter;


public class TrapezPlotter extends Plotter {
	
	private double a, b, h;
	private Funkcija f;
	
	public TrapezPlotter(double a, double b, double h, Funkcija f) {
		this.a = a;
		this.b = b;
		this.h = h;
		this.f = f;
	}

	public void setA(double a) { this.a = a; }
	public void setB(double b) { this.b = b; }
	public void setH(double h) { this.h = h; }
	
	public double getA() { return a; }
	public double getB() { return b; }
	public double getH() { return h; }
	
	@Override
	public String getName() {
		return "TrapezPlotter";
	}

	@Override
	public void plot(Graph p, Graphics g, int chartWidth, int chartHeight) {
		/*for (double x=a; x<=b-h; x+=h) {
			p.drawTrapez(g, x, f.getY(x), x+h, f.getY(x+h));
		}*/
		double x = a;
		int i = 0;
		
		while (true) {
			if (x+h>b) {
				p.drawTrapez(g, x, f.getY(x), b, f.getY(b), i%2);
				break;
			}
			p.drawTrapez(g, x, f.getY(x), x+h, f.getY(x+h), i%2);
			x+=h; i++;
		}
	}

}
