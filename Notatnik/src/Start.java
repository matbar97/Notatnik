import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Start extends JFrame implements ActionListener {
	
	JMenuBar menuBar;
	JMenu mPliki, mPomoc;
	JMenuItem iNowy, iOtworz, iZapisz, iZapiszJako, iExit, iAutor;
	JTextArea notatnik;
	File plik;
	JScrollPane scroll;
	Start()
	{
		super("Notatnik");
		menuBar = new JMenuBar();
		mPliki = new JMenu("Pliki");
		mPomoc = new JMenu("Pomoc");
		iNowy = new JMenuItem("Nowy"); iNowy.addActionListener(this);
		iOtworz = new JMenuItem("Otwórz"); iOtworz.addActionListener(this);
		iZapisz = new JMenuItem("Zapisz"); iZapisz.addActionListener(this);
		iZapiszJako = new JMenuItem("Zapisz jako"); iZapiszJako.addActionListener(this);
		iExit = new JMenuItem("Zamknij"); iExit.addActionListener(this);
		iAutor = new JMenuItem("Autor"); iAutor.addActionListener(this);
		
		
		mPliki.add(iNowy); mPliki.add(iOtworz); mPliki.add(iZapisz); mPliki.add(iZapiszJako); 
		mPliki.addSeparator(); mPliki.add(iExit); 
		mPomoc.add(iAutor);
		
				
		menuBar.add(mPliki); 
		menuBar.add(mPomoc);
		
		notatnik = new JTextArea(); notatnik.setFont(new Font("Verdana", Font.ITALIC, 18));
		scroll = new JScrollPane(notatnik);
		add(scroll);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(menuBar);
		setSize(600,500);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	protected void otworz()
	{
		notatnik.setText("");
		JFileChooser fc = new JFileChooser();
		if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
		{
			plik = fc.getSelectedFile();
			try {
				Scanner scanner = new Scanner(plik);
				while (scanner.hasNext()) notatnik.append(scanner.nextLine() + "\n");
				scanner.close();
				this.setTitle(fc.getName(plik));

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void zapisz()
	{
		try
		{
			PrintWriter pw = new PrintWriter(plik);
			Scanner sc = new Scanner(notatnik.getText());
			while (sc.hasNext() ) pw.println(sc.nextLine());
			sc.close(); pw.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();		
		}
	}
	
	private void zapiszJako()
	{
		JFileChooser fc = new JFileChooser();
		fc.setSelectedFile(plik);
		if(fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) 
		{
			plik = fc.getSelectedFile();
			zapisz();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object cel = e.getSource();
		
		if(cel == iExit) System.exit(0);
		//Object JOptionPanel;
		if(cel == iAutor) JOptionPane.showMessageDialog(this, "Mateusz Bary³ka", "Autor", JOptionPane.INFORMATION_MESSAGE);
		if(cel == iOtworz) otworz();
		if(cel == iNowy) notatnik.setText(""); this.setTitle("Notatnik"); plik=null; 
		if(cel == iZapiszJako) zapiszJako();
		if(cel == iZapisz) 
		{
			if(plik == null) zapiszJako();
			else zapisz();
		}
		
	}
}
