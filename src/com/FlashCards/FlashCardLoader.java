package com.FlashCards;
import javax.swing.*;
import java.util.*;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ListIterator;  


public class FlashCardLoader {
	
	
	private JTextArea display; 
	private JTextArea answers; 
	private ArrayList<FlashCard> cardList; 
	private ListIterator<FlashCard> cardItr; 
	private FlashCard currentCard; 
	private JFrame frame;
	private boolean isShowAnswer;
	private JButton showAnswer;
	private boolean hasPrev; 
	private JButton prev; 
	private JLabel label; 
	

	
	
	
	class NextCardListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(isShowAnswer) {
				display.setText(currentCard.getAnswers());
				label.setText("Answer");
				showAnswer.setText("Next Card");

//				showAnswer.setText("Next Card");
//				answers.setText(currentCard.getAnswers());
				isShowAnswer = false; 
			}else {
				if(cardItr.hasNext()) {
					showNextCard();
				}else {
					display.setText("No more FlashCards left");
					showAnswer.setText("Next Card");
					label.setText("Answer");
					showAnswer.setEnabled(false);
				}
			}
			
			
		}
		
	}
	
	class PrevCardListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			
			// TODO Auto-generated method stub
			if(hasPrev) {
				display.setText(currentCard.getQuestions());
				showAnswer.setText("Show Answer");
				isShowAnswer= false; 
				hasPrev = false;
				
			}else {
				if(cardItr.hasPrevious()) {
					showPrevCard();
				}else {
//					showNextCard();
//					showAnswer.setText("Next Card");
					label.setText("Question");
					showAnswer.setText("Show Answer");
//					prev.setEnabled(false);
				}
			}
			
		}
		
	}

	class OpenMenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JFileChooser fileOpen = new JFileChooser();
			fileOpen.showOpenDialog(frame);
			loadFile(fileOpen.getSelectedFile());
		}
		
	}
	
	private void loadFile(File selectedFile) {
		
		cardList = new ArrayList<FlashCard>(); //instantiate cardList
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
			String line = null; //add each line to line
			
			while((line= reader.readLine()) != null) {
				makeCard(line); 
			}
			
			
		}catch(Exception e) {
			System.out.println("Loading file failed");
		}
		cardItr = cardList.listIterator();
		showNextCard(); 
		
	}
	
	private void showPrevCard() {
		

	
			currentCard = cardItr.previous(); 
			display.setText(currentCard.getQuestions());
			showAnswer.setText("Show Answer");
			label.setText("Question");
			hasPrev = true;
			isShowAnswer= true;
//		
		
	}
	
	private void showNextCard() {
		currentCard = (FlashCard)cardItr.next();
		
		display.setText(currentCard.getQuestions());
		showAnswer.setText("Show Answer");
		label.setText("Question");

//		display.setText(currentCard.getAnswers());

		isShowAnswer= true;
		 
	}
	
	private void makeCard(String lineToParse) {
		//split the line with /
		
		StringTokenizer result = new StringTokenizer(lineToParse, "/");
		if(result.hasMoreTokens()) {
			FlashCard card= new FlashCard(result.nextToken(), result.nextToken());
			cardList.add(card); //add it to our cardList 
//			System.out.println("Card is made");
		}
		

		//can also approach this with string array as we can split 
		
		
	}
	
	public FlashCardLoader() {
		
		//Build UI
		frame = new JFrame("FlashCard Loader");
		
		JPanel mainPanel = new JPanel();
		Font displayFont = new Font("Helvetica Neue", Font.BOLD,22 );
		 
		label = new JLabel("Question");
		mainPanel.add(label);
		display = new JTextArea(10,20); 
		display.setFont(displayFont);
		//introduce scrolling to the display 
		JScrollPane displayScroll = new JScrollPane(display);
		displayScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		displayScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		//create answer textarea
		answers = new JTextArea(10,20);
		//add scrolling 
		JScrollPane answerScroll = new JScrollPane(answers);
		answerScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		answerScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		showAnswer = new JButton("ShowAnswer");
		
//		JButton next = new JButton("Next");
		prev = new JButton("Prev");
//		
		
		//Add menu Bar
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("Options");
		JMenuItem loadMenuItem = new JMenuItem("Load questions");
		loadMenuItem.addActionListener(new OpenMenuListener());
		mainPanel.add(displayScroll);
//		mainPanel.add(answerScroll);
		mainPanel.add(showAnswer);
		mainPanel.add(prev);
		
		showAnswer.addActionListener(new NextCardListener());
		prev.addActionListener(new PrevCardListener() );
		fileMenu.add(loadMenuItem);
		menuBar.add(fileMenu);
		
		
		frame.setJMenuBar(menuBar);
		
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(500,600);
		frame.setVisible(true);
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new FlashCardLoader();
			}
		});
	}

}
