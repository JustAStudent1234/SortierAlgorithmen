package SortierAlgorithmen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GUI

{

	// Frame Components
	public JFrame frame;
	public JTextField[][] field_arr;
	public JButton QuicksortButton;
	public JButton MergeSortButton;
	public JButton BubbleSortButton;
	public JButton InsertionSortButton;
	public JButton RandomAuffüllenButton;
	public JButton ClearButton;
	public JButton SelectionSortButton;
	public JButton SchnellsterAlgoButton;
	public JButton MergeVsInsertButton;
	JLabel label;					//Zeigt Algorithmus an 
	JLabel label1;					//Gibt dem nutzer eine Beschreibung für den slider 
	JLabel label3;					//Zeigt Laufzeit des Algorithmus an
	JLabel label4;					//gibt an bei welchem Index wir in der inneren Schleife sind
	JLabel label5;					//gibt an bei weclhem Index wir bei der äußeren Schleife sind.
	JSlider AnimationsSpeedSlider;	

	int[] Ergebnis;					//Stellt in zwei Funktionen das Ergebnis da, welches dann von der Gui übernommen werden soll.
	int GrößeArray;					//GrößeArray ist die Eingabe des Nutzers, wie groß das zu sortierende Array sein soll.
	int TatsächlicheGrößeArray; 	//realk ist die tatsächliche Größe des dann in der Gui erstellten Arrays, nämlich GrößeArray aufgerundet auf die nächste Zahl%30=0					
	int SPEED = 120;				//Die Gui wird alle 120millisekunden erneut aktualisiert, durch slider veränderbar.						
	int Grenze;						//Hilfsvariable, um eindimensionale Arrays in 2d Arrays zu konvertieren. 			
	int Grenze1;					//ebenfalls eine Hilfsvariable, um eindimensionale Arrays in 2d Arrays zu konvertieren.


	
	//Es wird ein PopUp Fenster aufgerufen, welches abfragt wie groß dein zu sortierendes Array sein soll.
	public GUI() {
		int selected;
		try {
		String selected1 = JOptionPane.showInputDialog("Wählen sie die Größe des zu sotierenden Arrays");
		 if(selected1==null) {
			 return;
		 }
		 selected = Integer.parseInt(selected1);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getStackTrace());
			return;
		}
		try {
			setGUI1(selected);
		} catch (matrixException e) {
			JOptionPane.showMessageDialog(null, "Die ArrayLänge darf 500 nicht überschreiten!");
			GUI c = new GUI();
		}
	}
	
	void setGUI1(int k) throws matrixException {
		Grenze = -1;
		Grenze1 = -1;
		if (k > 510) {
			matrixException a = new matrixException();
			throw a;
		}

		this.GrößeArray = k;
		int n = (k - (k % 30)) / 30;
		int l = 30;
		int h = n + 1;
		if (k % 30 == 0) {
			l = 30;
			h = h - 1;
		}

		this.TatsächlicheGrößeArray = h * l;
		setGUI2(h, l);
	}

	// Frame setzen
	void setGUI2(int h, int l) {

		field_arr = new JTextField[h][l];

		frame = new JFrame("Sortieralgorithmen");

		for (int i = 0; i < h; i++) {
			for (int j = 0; j < l; j++) {
				field_arr[i][j] = new JTextField();
				field_arr[i][j].setBounds(11 + j * 24, 50 + i * 21, 24, 20);
				Font font1 = new Font("SansSerif", Font.BOLD, 13);
				field_arr[i][j].setFont(font1);
				field_arr[i][j].setHorizontalAlignment(SwingConstants.CENTER);

				frame.add(field_arr[i][j]); //
			}

			//oben wurde bereits erklärt was die Variablen machen sollen.
			label = new JLabel("Algorithmus:");
			label.setBounds(15, 15, 200, 15);
			Font font1 = new Font("SansSerif", Font.BOLD, 13);
			label.setFont(font1);
			label1 = new JLabel("Animation:");
			label1.setBounds(530, 15, 90, 15);
			label1.setFont(font1);
			label3 = new JLabel("Laufzeit:");
			label3.setBounds(220, 15, 200, 15);
			label3.setFont(font1);
			label4 = new JLabel("i=_____");
			label4.setBounds(420, 15, 50, 15);
			label4.setFont(font1);
			AnimationsSpeedSlider = new JSlider(JSlider.HORIZONTAL, 60, 300, 120);
			AnimationsSpeedSlider.setBounds(610, 15, 100, 15);
			label5 = new JLabel("j=_____");
			label5.setBounds(475, 15, 50, 15);
			label5.setFont(font1);

			AnimationsSpeedSlider.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent arg0) {
					int value = AnimationsSpeedSlider.getValue();
					SPEED = value;
				}

			});

			QuicksortButton = new JButton("QUICKSORT");
			QuicksortButton.setBounds(50, 420, 130, 40);
			QuicksortButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					quicksort();
				}
			});

			MergeSortButton = new JButton("MERGE-SORT");
			MergeSortButton.setBounds(50, 470, 130, 40);
			MergeSortButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					mergeSort();

				}
			});

			BubbleSortButton = new JButton("BUBBLE-SORT");
			BubbleSortButton.setBounds(200, 450, 150, 35);
			BubbleSortButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					BUBBLE(doubletosolo(field_arr));
					label3.setText("Laufzeit: O(n)=n^2");
					label.setText("Algorithmus: BUBBLE-SORT");
				}
			});

			ClearButton = new JButton("CLEAR");
			ClearButton.setBounds(370, 470, 155, 40);
			ClearButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					clear();

				}
			});

			InsertionSortButton = new JButton("INSERTION-SORT");
			InsertionSortButton.setBounds(200, 485, 150, 35);
			InsertionSortButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					insertionsort(doubletosolo(field_arr));

					label3.setText("Laufzeit: O(n)=n^2");
					label.setText("Algorithmus: INSERTION-SORT");

				}
			});

			RandomAuffüllenButton = new JButton("RANDOM-AUFFÜLLEN");
			RandomAuffüllenButton.setBounds(370, 420, 155, 40);
			RandomAuffüllenButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					Werkseinstellungen();
					Ergebnis = new int[TatsächlicheGrößeArray];
					ArrayList<Integer> Liste = new ArrayList<Integer>();
					for (int i = 0; i < TatsächlicheGrößeArray; i++) {
						Liste.add(i);
					}

					for (int i = 0; i < TatsächlicheGrößeArray; i++) {

						Random random = new Random();

						int f = random.nextInt(TatsächlicheGrößeArray);

						if (Liste.contains(f)) {
							Ergebnis[i] = f;
							Liste.remove((Object) f);
						} else {
							i--;
						}
					}
					for (int i = 0; i < h; i++) {
						for (int j = 0; j < l; j++) {
							set_element(i, j, Ergebnis[i * 30 + j]);
						}
					}

				}
			});

			SelectionSortButton = new JButton("SELECTION-SORT");
			SelectionSortButton.setBounds(200, 420, 150, 35);
			SelectionSortButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					selectionsort(doubletosolo(field_arr));
					label3.setText("Laufzeit: O(n)=n^2");
					label.setText("Algorithmus: SELECTION-SORT");

				}

			});

			SchnellsterAlgoButton = new JButton("SchnellsterAlgo");
			SchnellsterAlgoButton.setBounds(550, 420, 140, 40);
			SchnellsterAlgoButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					String Algo = Sortieralgorithmen.schnellsterAlgo(doubletosolo(field_arr));

					JOptionPane.showMessageDialog(null, Algo, "SortierAlgorithmen", JOptionPane.INFORMATION_MESSAGE);
					if (Algo.contains("INSERTION-SORT: ")) {
						insertionsort(doubletosolo(field_arr));
						label3.setText("Laufzeit: O(n)=n^2");
						label.setText("Algorithmus: INSERTION-SORT");

					} else if (Algo.contains("BUBBLE-SORT: ")) {
						BUBBLE(doubletosolo(field_arr));
						label3.setText("Laufzeit: O(n)=n^2");
						label.setText("Algorithmus: BUBBLE-SORT");
					} else if (Algo.contains("MERGE-SORT: ")) {
						mergeSort();
					} else if (Algo.contains("SELECTION-SORT: ")) {
						selectionsort(doubletosolo(field_arr));
						label3.setText("Laufzeit: O(n)=n^2");
						label.setText("Algorithmus: SELECTION-SORT");
					} else if (Algo.contains("QUICK-SORT: ")) {
						quicksort();
						label3.setText("Laufzeit: O(n)=n^2");
						label.setText("Algorithmus: QUICK-SORT");
					}

				}
			});

			MergeVsInsertButton = new JButton("MERGE-VS-INSERT");
			MergeVsInsertButton.setBounds(550, 470, 140, 40);
			MergeVsInsertButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null, "Bei der Arraygröße von " + Sortieralgorithmen.MergeVSInsert()
							+ " war bei einem zufällig sortiertem array, \n Merge-Sort zum ersten mal schneller als Insertion-Sort",
							"Sortieralgorithmen", JOptionPane.INFORMATION_MESSAGE);

				}

			});

			// Fügt buttons hinzu
			frame.add(QuicksortButton);
			frame.add(MergeSortButton);
			frame.add(BubbleSortButton);
			frame.add(InsertionSortButton);
			frame.add(RandomAuffüllenButton);
			frame.add(ClearButton);
			frame.add(SelectionSortButton);
			frame.add(SchnellsterAlgoButton);
			frame.add(MergeVsInsertButton);
			frame.add(label);
			frame.add(label1);
			frame.add(label3);
			frame.add(label4);
			frame.add(label5);
			frame.add(AnimationsSpeedSlider);
		}

		//frame-Größe
		frame.setSize(750, 500);

		//Zentriert frame.
		center();
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	
	// Methode gibt element an der stelle i,j zurück oder 0 wenn leer.
	int get_element(int i, int j) {
		String txt = field_arr[i][j].getText();
		if (txt.isEmpty())

			return 0;

		return Integer.parseInt(txt.trim());
	}

	// Methode setzt die Zahl val bei Element i,j ein. 
	void set_element(int i, int j, int val) {
		field_arr[i][j].setText(String.valueOf(val));
	}

	// Methode macht das Array leer.
	void clear() {
		for (int i = 0; i < field_arr.length; i++) {
			for (int j = 0; j < field_arr[0].length; j++) {
				field_arr[i][j].setText("");
				field_arr[i][j].setBackground(Color.white);
			}
		}
		Werkseinstellungen();

	}

	// setzt ein array a in der Gui als einen JTextFiled[][] um.
	public void solotodouble(int[] a) {

		int m = 0;
		for (int i = 0; i < TatsächlicheGrößeArray; i++) {
			if (i == 30 * (m + 1)) {
				m = m + 1;
			}
			field_arr[m][i % 30].setText(Integer.toString(a[i]));
		}
	}

	//kriegt ein 2d JTextFieldArray als Parameter und gibt das dazu Kompatible in[] array zurück
	public int[] doubletosolo(JTextField[][] q) {
		Ergebnis = new int[TatsächlicheGrößeArray];
		int m = 0;
		for (int i = 0; i < TatsächlicheGrößeArray; i++) {
			if (i == 30 * (m + 1)) {
				m++;
			}
			if (!(q[m][i % 30].getText().equals(""))) {
				Ergebnis[i] = Integer.parseInt(q[m][i % 30].getText());
			} else {
				Ergebnis[i] = Integer.MAX_VALUE;
			}
		}
		return Ergebnis;
	}

	//setzt alle labels, Hilfsvariablen und die Farben der Felder auf ihre Ausgangswerte zurück.
	public void Werkseinstellungen() {
		label.setText("Algorithmus:");
		label1.setText("Animation:");
		label3.setText("Laufzeit:");
		label4.setText("i=_____");
		label5.setText("j=_____");
		Grenze = -1;
		Grenze1 = -1;
		Farbe();
	}

	//Zentriert das frame.
	void center() {

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dimension.width / 2 - frame.getWidth() / 2, dimension.height / 2 - frame.getHeight() / 2);
		frame.setSize(750, 550);
	}


	// Die Animation der äußeren Schleife von BubbleSort.
	public void BUBBLE(int[] a) {

		int j = SPEED * (a.length + 1);

		SwingUtilities.invokeLater(() -> {

			Timer timer = new Timer(j, new ActionListener() {

				int i = 0;

				public void actionPerformed(ActionEvent event) {

					if (i % 30 == 0) {
						Grenze++;
					}

					BUBBLE1(a, i);

					//Bei der letzten Schleife soll alles auf den Werkszustand zuückgestzt werden.
					if (i == a.length - 1) {
						Werkseinstellungen();
						((Timer) event.getSource()).stop();
						JOptionPane.showMessageDialog(null, "Sortiert");
					} else {
						i++;
					}
				}

			});
			timer.setInitialDelay(0);
			timer.start();
		});
	}

	// Die Animation der inneren Schleife von BubbleSort
	public void BUBBLE1(int[] a, int j) {

		SwingUtilities.invokeLater(() -> {

			Timer timer = new Timer(SPEED, new ActionListener() {

				int i = a.length - 1;
				int m = -1;

				public void actionPerformed(ActionEvent event) {
					int p = j;
					
					//Diese Schleife fungiert wie ein Pinsel und soll die grauen Felder wieder weis färben.
					for (int n = 0; n < field_arr.length; n++) {
						for (int f = 0; f < field_arr[0].length; f++) {
							field_arr[n][f].setBackground(Color.WHITE);
						}
					}

					//Färbt grün weiter bei Zeilenumbruch
					if (Grenze > 0) {
						for (int i = 0; i < Grenze; i++) {
							for (int j = 0; j < field_arr[0].length; j++) {
								field_arr[i][j].setBackground(Color.GREEN);

							}
						}
					}
					
					//Färbt in der entsprächenden Zeile grün
					for (int j = 0; j < p % 30; j++) {
						field_arr[Grenze][j % 30].setBackground(Color.GREEN);
					}

					//wenn j=i ist wird die Methode beendet, dies symbolisiert die Abbruchbedingung der inneren Schleife in BubbleSort.
					if (j == i) {
						((Timer) event.getSource()).stop();
						return;
					} else {
						if (((a.length - 1) - i) % 30 == 0) {
							m++;
						}
						//swap bedingung von BubbleSort.
						if (a[i] <= a[i - 1]) {
							int c = a[i];
							a[i] = a[i - 1];
							a[i - 1] = c;
							
							label4.setText("i= " + i);
							label5.setText("j= " + j);
							solotodouble(a);
						//Färbt die zu tauschenden Felder Grau.
							field_arr[(field_arr.length) - m - 1][(i - 1) % 30].setBackground(Color.LIGHT_GRAY);
							field_arr[(field_arr.length) - m - 1][i % 30].setBackground(Color.LIGHT_GRAY);
							LeereFelderMax();

						} else {
// 					Übergangszahlen Grau färben.				
							field_arr[(field_arr.length) - m - 1][i % 30].setBackground(Color.LIGHT_GRAY);
						}
						i--;

					}

				}

			});
			timer.setInitialDelay(0);
			timer.start();

		});
	}


	// Animation der aüßeren Schleife von insertionSort.
	public void insertionsort(int[] a) {

		SwingUtilities.invokeLater(() -> {

			Timer timer = new Timer(SPEED, new ActionListener() {

				int j = 1;

				public void actionPerformed(ActionEvent event) {

					//Bei jedem Zeilenumbruch soll sich Grenze1 um 1 erhöhen, gibt also an bei welcher Spalte wir aktuell sind.
					if (((j - 1) % 30) == 0) {
						Grenze1++;
					}

			 
					int p = j;
					
					//Ferbt alle Felder grün die bereits sortiert sind.
					for (int j = 0; j < (p - 1) % 30; j++) {
						field_arr[Grenze1][j % 30].setBackground(Color.GREEN);
						field_arr[Grenze1][(j + 1) % 30].setBackground(Color.GREEN);
						field_arr[Grenze1][(j + 2) % 30].setBackground(Color.GREEN);
					}

					//bei der letzten Schleife soll alle wieder auf Werkseinstellungen zurück gesetzt werden, siehe funktion werkseinstellungen.
					if (j == a.length - 1) {
						Werkseinstellungen();
						((Timer) event.getSource()).stop();
						JOptionPane.showMessageDialog(null, "Sortiert");
					}

					int key = a[j];
					int i = j - 1;

					label5.setText("j= " + i);
					
					//das eigentliche Insertion Sort
					while (i >= 0 && a[i] > key) {
						a[i + 1] = a[i];

						i = i - 1;
					}
					a[i + 1] = key;
					solotodouble(a);
					
					//Leere Felder bleiben weis.
					Weismacher();
					j++;

				}
			});
			timer.setInitialDelay(0);
			timer.start();
		});

	}

	public void selectionsort(int[] a) {

		SwingUtilities.invokeLater(() -> {

			Timer timer = new Timer(SPEED, new ActionListener() {

				int i = 0;

				public void actionPerformed(ActionEvent event) {

					//Wenn letzt schleife, dann auf Werkseinstellungen zurücksetzen, siehe Methode Werkseinstellungen.
					if (i == a.length - 1) {
						Werkseinstellungen();
						((Timer) event.getSource()).stop();
						JOptionPane.showMessageDialog(null, "Sortiert");
					}

					int k = i;
					for (int j = i + 1; j < a.length; j++) {

						label5.setText("j= " + i);

						if (a[j] < a[k]) {
							k = j;
						}
					}
					//Färbt den bereits sortierten bereich Grau.
					field_arr[Sortieralgorithmen.get_index(i)][i % 30].setBackground(Color.LIGHT_GRAY);
					int n = a[i];
					a[i] = a[k];
					a[k] = n;
					solotodouble(a);
					//Leere Felder weis machen. 
					Weismacher();
					i++;
				}

			});
			timer.start();

		});

	}

	//Wendet den mergeSort-Algorithmus an. 
	void mergeSort() {
		label3.setText("Laufzeit: O(n)=nlog(n)");
		label.setText("Algorithmus: MERGE-SORT");

		int[] array = Sortieralgorithmen.mergeSort(doubletosolo(field_arr));
		solotodouble(array);

		LeereFelderMax();

	}

	//Wendet den quicksort-Algorithmus an.
	void quicksort() {

		label3.setText("Laufzeit: O(n)=nlog(n)");
		label.setText("Algorithmus: QUICK-SORT");

		Quicksort quicksort = new Quicksort(doubletosolo(field_arr));
		int[] array = quicksort.sort(0, doubletosolo(field_arr).length - 1);
		solotodouble(array);

		LeereFelderMax();
	}

	//setzt die Farbe auf weis zurück.
	void Farbe() {
		for (int i = 0; i < field_arr.length; i++) {
			for (int j = 0; j < field_arr[0].length; j++) {
				field_arr[i][j].setBackground(Color.WHITE);

			}
		}
	}
	
	//Leere Felder weis machen.
	void Weismacher() {
		for (int ä = 0; ä < field_arr.length; ä++) {
			for (int ü = 0; ü < field_arr[0].length; ü++) {
				if (get_element(ä, ü) == Integer.MAX_VALUE) {

					field_arr[ä][ü].setText("");
					field_arr[ä][ü].setBackground(Color.WHITE);

				}
			}
		}
	}
	
	//Leere Felder sollen nach hinten, werden also als max-Int angesehen.
	void LeereFelderMax() {
		for (int ä = 0; ä < field_arr.length; ä++) {
			for (int ü = 0; ü < field_arr[0].length; ü++) {
				if (get_element(ä, ü) == Integer.MAX_VALUE) {

					field_arr[ä][ü].setText("");

				}
			}
		}
	}
	

}
