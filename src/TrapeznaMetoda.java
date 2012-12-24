import ac.essex.graphing.swing.*;
import ac.essex.graphing.plotting.Graph; 
import ac.essex.graphing.plotting.PlotSettings; 
import ac.essex.graphing.swing.InteractiveGraphPanel;
 
import javax.swing.*; 
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;

import java.awt.*; 
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter; 
import java.awt.event.WindowEvent; 
import java.awt.event.ActionListener; 
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
 

public class TrapeznaMetoda extends JFrame implements ActionListener, FocusListener, ChangeListener, SettingsUpdateListener { 
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 6880523354158178692L;
    protected JTextField meriloOsi, funkcija, od, doo, dh;
    protected JButton ponastavi;
    protected JLabel rezultat;
    protected JSlider slider;
    protected Funkcija f;
    protected TrapezPlotter tp;
    protected GraphPanel graphPanel; 
    private double a, b, h;
 
    public TrapeznaMetoda(Graph graph) {
    	a = 0; b = 2; h = 1;
    	
    	f = new Funkcija();
    	tp = new TrapezPlotter(a, b, h, f);
    	try {f.nastaviFunkcijo("x^2");} catch (Exception e) {}
    	graph.functions.add(f);
    	graph.functions.add(tp);
 
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new GridBagLayout());
        toolbar.setBorder(BorderFactory.createTitledBorder("Nastavitve"));
        
        JPanel toolbar2 = new JPanel();
        toolbar2.setLayout(new GridBagLayout());

        JPanel toolbar3 = new JPanel();
        toolbar3.setLayout(new GridBagLayout());
        toolbar3.setBorder(BorderFactory.createTitledBorder("Koordinatni sistem"));
        
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx=2;
        c.ipady=2;
        c.insets=new Insets(2, 2, 2, 2);
        
        funkcija = new JTextField("x^2");
        funkcija.addActionListener(this);
        funkcija.addFocusListener(this);
        funkcija.setColumns(15);
        
        od = new JTextField("0");
        od.addActionListener(this);
        od.addFocusListener(this);
        od.setColumns(2);
        
        doo = new JTextField("2");
        doo.addActionListener(this);
        doo.addFocusListener(this);
        doo.setColumns(2);
        
        dh = new JTextField("1.000");
        dh.addActionListener(this);
        dh.addFocusListener(this);
        dh.setColumns(4);
        
        slider = new JSlider(JSlider.HORIZONTAL, 1, 2000, 1000);
        slider.addChangeListener(this);
        slider.setPaintLabels(true);
        slider.setPaintTicks(false);
        slider.setPreferredSize(new Dimension(100,20));
        
        c.gridx=0;c.gridy=0; toolbar.add(new JLabel(" f(x) ="), c);
        c.gridx=1;c.gridy=0;c.gridwidth=4; toolbar.add(funkcija, c); c.gridwidth=1;
        c.gridx=0;c.gridy=1;c.anchor=GridBagConstraints.LINE_END; toolbar.add(new JLabel("Od"), c);
        c.gridx=1;c.gridy=1;c.anchor=GridBagConstraints.LINE_START; toolbar.add(od, c);
        c.gridx=2;c.gridy=1; toolbar.add(new JLabel("do"), c);
        c.gridx=3;c.gridy=1; toolbar.add(doo, c);
        c.gridx=0;c.gridy=2;c.anchor=GridBagConstraints.LINE_END; toolbar.add(new JLabel("h ="), c);
        c.gridx=1;c.gridy=2; toolbar.add(dh, c);
        c.gridx=2;c.gridy=2;c.gridwidth=4; toolbar.add(slider, c); c.gridwidth=1;
        
        
        JLabel label = new JLabel("S = ");
        label.setFont(new Font("Serif", Font.ITALIC, 48));
        rezultat = new JLabel(calculate());
        rezultat.setFont(new Font("Serif", Font.ITALIC, 48));
        toolbar2.add(label);
        toolbar2.add(rezultat);
        
        meriloOsi = new JTextField(String.valueOf(graph.plotSettings.getGridSpacingX()));
        meriloOsi.addActionListener(this);
        meriloOsi.addFocusListener(this);
        meriloOsi.setColumns(3);
        
        ponastavi = new JButton("Ponastavi pogled");
        ponastavi.addActionListener(this);
        
        c.gridx=0;c.gridy=0; toolbar3.add(new JLabel("Merilo: "), c);
        c.gridx=1;c.gridy=0; toolbar3.add(meriloOsi, c);
        c.gridx=0;c.gridy=1;c.gridwidth=2; toolbar3.add(ponastavi, c);
 
        // add the panel to the middle of the BorderLayout, it will fill the window. 
        graphPanel = new InteractiveGraphPanel(this); 
 
        // Make sure Java Exits when the close button is clicked 
        addWindowListener(new WindowAdapter() { 
            public void windowClosing(WindowEvent e) { 
                System.exit(0); 
            } 
        } 
        ); 
 
        // Add the toolbar and graph to the frame 
        Container container = getContentPane(); 
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBorder(new EmptyBorder(0,20,20,20));
        bottom.add(toolbar, BorderLayout.WEST);
        bottom.add(toolbar2, BorderLayout.CENTER);
        bottom.add(toolbar3, BorderLayout.EAST);
        container.add(bottom, BorderLayout.SOUTH);
        container.add(graphPanel, BorderLayout.CENTER);
 
        setTitle("Trapezna metoda"); 
 
        setVisible(true); 
 
        graphPanel.setGraph(graph); 
 
    } 
 
    private String calculate() {
    	a = tp.getA(); b = tp.getB(); h = tp.getH();
    	double sum = f.getY(a)+f.getY(b)/2;
    	for (double i=a+h; i<b; i+=h) {
    		sum += f.getY(i);
    	}
    	BigDecimal rezultat = new BigDecimal(sum*h);
    	rezultat = rezultat.setScale(5, BigDecimal.ROUND_HALF_UP);
    	System.out.println(rezultat.doubleValue());
    	return String.valueOf(rezultat.doubleValue());
    }
 
    public void graphUpdated(PlotSettings settings) {
    	//settings.setGridSpacingX(settings.getRangeX()/10);
    	//settings.setGridSpacingY(settings.getRangeY()/10);
    } 

    
    public static void main(String[] args) {
    	PlotSettings settings = new PlotSettings();
    	settings.setGridSpacingX(1.0);
    	settings.setGridSpacingY(1.0);
    	settings.setNumberFormatter(new DecimalFormat("0.0"));
    	settings.setPlotColor(Color.BLUE);
    	settings.setMaxY(8);
    	settings.setMinY(-2);
    	
    	Graph graph = new Graph(settings);
    	graph.functions.clear();
    	
    	try{
    	    javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	} catch (Exception exc){
    	     exc.printStackTrace();
    	}
    	
    	TrapeznaMetoda tm = new TrapeznaMetoda(graph);
    	tm.setSize(800, 640);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) { 
 
        if (e.getSource() == meriloOsi) {
        	System.out.println("meriloOsi");
        	try {
	            Graph g = graphPanel.getGraph();
	            g.plotSettings.setGridSpacingX(Double.parseDouble(meriloOsi.getText()));
	            g.plotSettings.setGridSpacingY(Double.parseDouble(meriloOsi.getText()));
        	} catch (NumberFormatException ex) {        		
        	}
        } else if (e.getSource() == funkcija) {
        	System.out.println("funkcija");
        	try {
        		f.nastaviFunkcijo(funkcija.getText());
        	} catch (Exception ex) {
        	}
        } else if (e.getSource() == ponastavi) {
        	Graph g = graphPanel.getGraph();
        	g.plotSettings.setMaxX(5);
        	g.plotSettings.setMaxY(8);
        	g.plotSettings.setMinX(-5);
        	g.plotSettings.setMinY(-2);
        	g.plotSettings.setGridSpacingX(1.0);
        	g.plotSettings.setGridSpacingY(1.0);
        } else if (e.getSource() == od) {
        	tp.setA(Double.parseDouble(od.getText()));
        } else if (e.getSource() == doo) {
        	tp.setB(Double.parseDouble(doo.getText()));
        } else if (e.getSource() == dh) {
        	tp.setH(Double.parseDouble(dh.getText()));
        }
        rezultat.setText(calculate());
        graphPanel.repaint();
    }

	@Override
	public void focusGained(FocusEvent e) {
		((JTextComponent) e.getSource()).selectAll();
	}


	@Override
	public void focusLost(FocusEvent e) {
		actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, "focus lost"));
	}


	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == slider) {
			dh.setText(String.valueOf(slider.getValue()/1000.0));
			actionPerformed(new ActionEvent(dh, ActionEvent.ACTION_PERFORMED, ""));
		}
	}
 
} 

