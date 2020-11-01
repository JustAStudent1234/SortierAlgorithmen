package SortierAlgorithmen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
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
	public JButton jbutton;
	public JButton jbutton2;
	public JButton jbutton3;
	public JButton jbutton4;
	public JButton jbutton5;
	public JButton jbutton6;
	public JButton jbutton7;
	public JButton jbutton8;
	public JButton jbutton9;
	JLabel label;
	JLabel label1;
	JLabel label2;
	JLabel label3;
	JLabel label4;
	JLabel label5;
	JSlider slider;

	int[] Ergebnis;
	int k;
	int realK; // in diesem Fall 9, weil wir von einer 3x3 matrix ausgehen
	long Zeit;
	int SPEED = 120;
	int[] d;
	int Grenze;
	int Grenze1;

	GUI(int k) throws matrixException {
		Grenze = -1;
		Grenze1 = -1;
		if (k > 510) {
			matrixException a = new matrixException();
			throw a;
		}

		this.k = k;
		int n = (k - (k % 30)) / 30;
		int l = 30;
		int h = n + 1;
		if (k % 30 == 0) {
			l = 30;
			h = h - 1;
		}

		this.realK = h * l;
		setGUI(h, l);
	}

	// Frame setzen
	void setGUI(int h, int l) {

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

			label = new JLabel("Algorithmus:");
			label.setBounds(15, 15, 200, 15);
			Font font1 = new Font("SansSerif", Font.BOLD, 13);
			label.setFont(font1);
			label1 = new JLabel("Animation:");
			label1.setBounds(530, 15, 90, 15);
			label1.setFont(font1);
			label2 = new JLabel();
			label2.setFont(font1);
			label2.setOpaque(true);
			label2.setBounds(610, 15, 80, 15);
			label3 = new JLabel("Laufzeit:");
			label3.setBounds(220, 15, 200, 15);
			label3.setFont(font1);
			label4 = new JLabel("i=_____");
			label4.setBounds(420, 15, 50, 15);
			label.setFont(font1);
			slider = new JSlider(JSlider.HORIZONTAL, 60, 300, 120);
			slider.setBounds(610, 15, 100, 15);
			label5 = new JLabel("j=_____");
			label5.setBounds(475, 15, 50, 15);
			label5.setFont(font1);

			slider.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent arg0) {
					int value = slider.getValue();
					SPEED = value;
				}

			});

			jbutton = new JButton("QUICKSORT");
			jbutton.setBounds(50, 420, 130, 40);
			jbutton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					quicksort();
				}
			});

			jbutton2 = new JButton("MERGE-SORT");
			jbutton2.setBounds(50, 470, 130, 40);
			jbutton2.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					mergeSort();

				}
			});

			jbutton3 = new JButton("BUBBLE-SORT");
			jbutton3.setBounds(200, 450, 150, 35);
			jbutton3.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					BUBBLE(doubletosolo(field_arr));
					label3.setText("Laufzeit: O(n)=n^2");
					label.setText("Algorithmus: BUBBLE-SORT");
				}
			});

			jbutton6 = new JButton("CLEAR");
			jbutton6.setBounds(370, 470, 155, 40);
			jbutton6.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					clear();

				}
			});

			jbutton4 = new JButton("INSERTION-SORT");
			jbutton4.setBounds(200, 485, 150, 35);
			jbutton4.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					insertionsort(doubletosolo(field_arr));

					label3.setText("Laufzeit: O(n)=n^2");
					label.setText("Algorithmus: INSERTION-SORT");

				}
			});

			jbutton5 = new JButton("RANDOM-AUFFÜLLEN");
			jbutton5.setBounds(370, 420, 155, 40);
			jbutton5.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					Werkseinstellungen();
					Ergebnis = new int[realK];
					ArrayList<Integer> Liste = new ArrayList<Integer>();
					for (int i = 0; i < realK; i++) {
						Liste.add(i);
					}

					for (int i = 0; i < realK; i++) {

						Random random = new Random();

						int f = random.nextInt(realK);

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

			jbutton7 = new JButton("SELECTION-SORT");
			jbutton7.setBounds(200, 420, 150, 35);
			jbutton7.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					selectionsort(doubletosolo(field_arr));
					label3.setText("Laufzeit: O(n)=n^2");
					label.setText("Algorithmus: SELECTION-SORT");

				}

			});

			jbutton8 = new JButton("SchnellsterAlgo");
			jbutton8.setBounds(550, 420, 140, 40);
			jbutton8.addActionListener(new ActionListener() {

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

			jbutton9 = new JButton("MERGE-VS-INSERT");
			jbutton9.setBounds(550, 470, 140, 40);
			jbutton9.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null, "Bei der Arraygröße von " + Sortieralgorithmen.MergeVSInsert()
							+ " war bei einem zufällig sortiertem array, \n Merge-Sort zum ersten mal schneller als Insertion-Sort",
							"Sortieralgorithmen", JOptionPane.INFORMATION_MESSAGE);

				}

			});

			// Adding the button
			frame.add(jbutton);
			frame.add(jbutton2);
			frame.add(jbutton3);
			frame.add(jbutton4);
			frame.add(jbutton5);
			frame.add(jbutton6);
			frame.add(jbutton7);
			frame.add(jbutton8);
			frame.add(jbutton9);
			frame.add(label);
			frame.add(label1);
			frame.add(label2);
			frame.add(label3);
			frame.add(label4);
			frame.add(label5);
			frame.add(slider);
		}

		// Setting the size of frame
		frame.setSize(750, 500);
		// Centering the frame
		center();
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	// Method to return the element present in particular box if not return 0
	int get_element(int i, int j) {
		String txt = field_arr[i][j].getText();
		if (txt.isEmpty())

			return 0;

		return Integer.parseInt(txt.trim());
	}

	// Method to set element in particular box
	void set_element(int i, int j, int val) {
		field_arr[i][j].setText(String.valueOf(val));
	}

	// Method to clear the box
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
		for (int i = 0; i < realK; i++) {
			if (i == 30 * (m + 1)) {
				m = m + 1;
			}
			field_arr[m][i % 30].setText(Integer.toString(a[i]));
		}
	}

	public int[] doubletosolo(JTextField[][] q) {
		Ergebnis = new int[realK];
		int m = 0;
		for (int i = 0; i < realK; i++) {
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

	void center() {

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dimension.width / 2 - frame.getWidth() / 2, dimension.height / 2 - frame.getHeight() / 2);
		frame.setSize(750, 550);
	}

// public void bubble() {
//	 label3.setText("Laufzeit: O(n)=n^2");
//		label.setText("Algorithmus: BUBBLE-SORT");
//		
//		long n=System.nanoTime();
//		int[] array = Sortieralgorithmen.BubbleSort(doubletosolo(field_arr));
////		System.out.print(java.util.Arrays.toString(array));		
//			solotodouble(array);
//			
//			for(int i=0; i<field_arr.length;i++) {
//				for(int j=0; j<field_arr[0].length;j++) {
//			if(get_element(i,j)==Integer.MAX_VALUE) {
//				field_arr[i][j].setText("");
//			}
//				}
//			}
//		long q=System.nanoTime();
//		long res=q-n;
//		
//		label2.setText("  "+res);
//		   if(Zeit>res) {
//			   label2.setBackground(Color.green);
//		   }else {
//			   Color a = new Color(255,0,0);
//			   a.brighter();
//			   a.brighter();
//			   a.brighter();
//			   a.brighter();
//			   label2.setBackground(a);
//		   }
//		   Zeit = res;
//	
// }

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
					for (int n = 0; n < field_arr.length; n++) {
						for (int f = 0; f < field_arr[0].length; f++) {
							field_arr[n][f].setBackground(Color.WHITE);
						}
					}

					if (Grenze > 0) {
						for (int i = 0; i < Grenze; i++) {
							for (int j = 0; j < field_arr[0].length; j++) {
								field_arr[i][j].setBackground(Color.GREEN);

							}
						}
					}
					for (int j = 0; j < p % 30; j++) {
						field_arr[Grenze][j % 30].setBackground(Color.GREEN);
					}

					if (j == i) {
						((Timer) event.getSource()).stop();
						d = Arrays.copyOf(a, a.length);
						return;
					} else {
						if (((a.length - 1) - i) % 30 == 0) {
							m++;
						}

						if (a[i] <= a[i - 1]) {
							int c = a[i];
							a[i] = a[i - 1];
							a[i - 1] = c;
							label4.setText("i= " + i);
							label5.setText("j= " + j);
							solotodouble(a);
							field_arr[(field_arr.length) - m - 1][(i - 1) % 30].setBackground(Color.LIGHT_GRAY);
							field_arr[(field_arr.length) - m - 1][i % 30].setBackground(Color.LIGHT_GRAY);
							for (int ä = 0; ä < field_arr.length; ä++) {
								for (int ü = 0; ü < field_arr[0].length; ü++) {
									if (get_element(ä, ü) == Integer.MAX_VALUE) {

										field_arr[ä][ü].setText("");

									}
								}
							}

						} else {
// 					Übergangszahlen färben: 				
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

// 	public void INSERTIONSORT() {
// 		
// 		label3.setText("Laufzeit: O(n)=n^2");
//		label.setText("Algorithmus: INSERTION-SORT");
//		
//		
//		long n=System.nanoTime();
//		int[] array = Sortieralgorithmen.InsertionSort(doubletosolo(field_arr));
////		System.out.print(java.util.Arrays.toString(array));		
//			solotodouble(array);
//			
//			for(int i=0; i<field_arr.length;i++) {
//				for(int j=0; j<field_arr[0].length;j++) {
//			if(get_element(i,j)==Integer.MAX_VALUE) {
//				field_arr[i][j].setText("");
//			}
//				}
//			}
//		long q=System.nanoTime();
//		long res=q-n;
//		
//	
//		label2.setText("  "+res);
//		   if(Zeit>res) {
//			   label2.setBackground(Color.green);
//		   }else {
//			   Color a = new Color(255,0,0);
//			   a.brighter();
//			   a.brighter();
//			   a.brighter();
//			   a.brighter();
//			   label2.setBackground(a);
//		   }
//		   Zeit = res;
//	   
//	}   

	// Animation der aüßeren Schleife von insertionSort.
	public void insertionsort(int[] a) {

		SwingUtilities.invokeLater(() -> {

			Timer timer = new Timer(SPEED, new ActionListener() {

				int j = 1;

				public void actionPerformed(ActionEvent event) {

					if (((j - 1) % 30) == 0) {
						Grenze1++;
					}

					int p = j;
					for (int n = 0; n < field_arr.length; n++) {
						for (int f = 0; f < field_arr[0].length; f++) {
							field_arr[n][f].setBackground(Color.WHITE);
						}
					}

					if (Grenze1 > 0) {
						for (int i = 0; i < Grenze1; i++) {
							for (int j = 0; j < field_arr[0].length; j++) {
								field_arr[i][j].setBackground(Color.GREEN);

							}
						}
					}

					for (int j = 0; j < (p - 1) % 30; j++) {
						field_arr[Grenze1][j % 30].setBackground(Color.GREEN);
						field_arr[Grenze1][(j + 1) % 30].setBackground(Color.GREEN);
						field_arr[Grenze1][(j + 2) % 30].setBackground(Color.GREEN);
					}

					if (j == a.length - 1) {

						Werkseinstellungen();
						((Timer) event.getSource()).stop();
						JOptionPane.showMessageDialog(null, "Sortiert");
					}

					int key = a[j];
					int i = j - 1;

					label5.setText("j= " + i);
					while (i >= 0 && a[i] > key) {
						a[i + 1] = a[i];

						i = i - 1;
					}
					a[i + 1] = key;
					solotodouble(a);

					for (int ä = 0; ä < field_arr.length; ä++) {
						for (int ü = 0; ü < field_arr[0].length; ü++) {
							if (get_element(ä, ü) == Integer.MAX_VALUE) {

								field_arr[ä][ü].setText("");
								field_arr[ä][ü].setBackground(Color.WHITE);

							}
						}
					}
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

					if (i == a.length - 1) {

						Werkseinstellungen();
						((Timer) event.getSource()).stop();
						JOptionPane.showMessageDialog(null, "Sortiert");
					}

					int k = i;
					for (int j = i + 1; j < a.length; j++) {

//						label4.setText("i= "+j);
						label5.setText("j= " + i);

						if (a[j] < a[k]) {
							k = j;
						}
					}
					field_arr[Sortieralgorithmen.get_index(i)][i % 30].setBackground(Color.LIGHT_GRAY);
					int n = a[i];
					a[i] = a[k];
					a[k] = n;
					solotodouble(a);
					for (int ä = 0; ä < field_arr.length; ä++) {
						for (int ü = 0; ü < field_arr[0].length; ü++) {
							if (get_element(ä, ü) == Integer.MAX_VALUE) {

								field_arr[ä][ü].setText("");
								field_arr[ä][ü].setBackground(Color.WHITE);

							}
						}
					}
					i++;
				}

			});
			timer.start();

		});

	}

//	public void SELECTIONSORT() {
//	
//	   label3.setText("Laufzeit: O(n)=n^2");
//	   label.setText("Algorithmus: SELECTION-SORT");
//		
//		long n=System.nanoTime();
//		int[] array = Sortieralgorithmen.SelectionSort(doubletosolo(field_arr));
////		System.out.print(java.util.Arrays.toString(array));		
//			solotodouble(array);
//			
//			for(int i=0; i<field_arr.length;i++) {
//				for(int j=0; j<field_arr[0].length;j++) {
//			if(get_element(i,j)==Integer.MAX_VALUE) {
//				field_arr[i][j].setText("");
//			}
//				}
//			}
//		long q=System.nanoTime();
//		long res=q-n;
//		
//	
//		label2.setText("  "+res);
//		   if(Zeit>res) {
//			   label2.setBackground(Color.green);
//		   }else {
//			   Color a = new Color(255,0,0);
//			   a.brighter();
//			   a.brighter();
//			   a.brighter();
//			   a.brighter();
//			   label2.setBackground(a);
//		   }
//		   Zeit = res;
//	
//	}
//	

	void mergeSort() {
		label3.setText("Laufzeit: O(n)=nlog(n)");
		label.setText("Algorithmus: MERGE-SORT");

		int[] array = Sortieralgorithmen.mergeSort(doubletosolo(field_arr));
		solotodouble(array);

		for (int i = 0; i < field_arr.length; i++) {
			for (int j = 0; j < field_arr[0].length; j++) {
				if (get_element(i, j) == Integer.MAX_VALUE) {
					field_arr[i][j].setText("");
				}
			}
		}

	}

	void quicksort() {

		label3.setText("Laufzeit: O(n)=nlog(n)");
		label.setText("Algorithmus: QUICK-SORT");

		Quicksort quicksort = new Quicksort(doubletosolo(field_arr));
		int[] array = quicksort.sort(0, doubletosolo(field_arr).length - 1);
		solotodouble(array);

		for (int i = 0; i < field_arr.length; i++) {
			for (int j = 0; j < field_arr[0].length; j++) {
				if (get_element(i, j) == Integer.MAX_VALUE) {
					field_arr[i][j].setText("");
				}
			}
		}
	}

	void Farbe() {
		for (int i = 0; i < field_arr.length; i++) {
			for (int j = 0; j < field_arr[0].length; j++) {
				field_arr[i][j].setBackground(Color.WHITE);

			}
		}
	}

}
