package com.FlashCards;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;

import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ListIterator;  

public class FlashCardsBuilder {

	
	private JTextArea questions;
	private JTextArea answers;
	private ArrayList<FlashCard> cardList;
	private JFrame frame; 
	private ListIterator<FlashCard> prevItr; 
	private FlashCard currentCard; 
	private JList displayList; 
	private DefaultListModel<String> dL; 
	private JButton clear; 
	private JButton clearAll; 
	//invokes when next button is clicked 
	 class NextCardListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			//create a flasCard
			FlashCard card = new FlashCard(questions.getText(), answers.getText());
			dL.addElement("Question: " + questions.getText());
			dL.addElement("Answer: "+answers.getText());
			dL.toString();
			cardList.add(card);
			
//			displayList.addlement(card);
			clearCard();
			System.out.println(cardList.size());
			
			
		}

	}
	 class PrevCardListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			prevItr = cardList.listIterator();
			
			currentCard = prevItr.previous();
			questions.setText(currentCard.getQuestions());
			answers.setText(currentCard.getAnswers());
			
		}
		 
	 }

		private void clearCard() {
			// TODO Auto-generated method stub
			questions.setText("");
			answers.setText("");
			questions.requestFocus();
		}
		
		class ClearActionListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				clearCard();

			}
			
		}
		class ClearAllActionListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				questions.setText("");
				answers.setText("");
				questions.requestFocus();
				
				cardList.clear();
				dL.clear();
				displayList.clearSelection();
				
			}
			
			
		}
	 
	 class NewMenuItemListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("new Menu");

		}
		 
	 }
	 
	 class SavedMenuItemListener implements ActionListener{
		 @Override
		 public void actionPerformed(ActionEvent e) {
			FlashCard card = new FlashCard(questions.getText(), answers.getText());
			cardList.add(card);
			
			
			JFileChooser fileSave = new JFileChooser();
			fileSave.showSaveDialog(frame);
			saveFile(fileSave.getSelectedFile());
			

			 
		 }
	 }
	 
	 private void saveFile(File selectedFile) {
		 try {
			 BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
			 //easier way to loop through our FlashCard
			 Iterator<FlashCard> cardIterator = cardList.iterator();
			 
			 while(cardIterator.hasNext()) {
				 FlashCard  card = (FlashCard) cardIterator.next(); //cast just to make sure
				 writer.write(card.getQuestions() + "/"); //to separate question and answers add /
				 writer.write(card.getAnswers() + "\n"); //go on the next line for next set of answer and question

				 
			 }
			 writer.close();
			 
			 
			  
		 }catch(Exception e) {
			 System.out.println("Writing on card failed");   
		 }
	 }
	
	public FlashCardsBuilder() {
		//we will build user interface in here 
		
		frame = new JFrame("Flash Cards");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // enables exits 
		
		//create a JPanel to hold everything 
		JPanel mainPanel = new JPanel();
		
		//create fonts 
		Font greatFont = new Font("Helvetica Neue", Font.BOLD, 21);
		
		questions= new JTextArea(10, 20); //create jTextarea for questions with 6 rows and 15 col
		//if question is to long set it to the next line 
		questions.setLineWrap(true); //makes it fit on questions JtextArea
		questions.setWrapStyleWord(true);
		questions.setFont(greatFont);
		
		//create scroll for questions textAreas
		JScrollPane Qscroll = new JScrollPane(questions);
		Qscroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		Qscroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		//Create textAreas for answer
		answers = new JTextArea(10,20); 
		answers.setLineWrap(true);
		answers.setWrapStyleWord(true);
		answers.setFont(greatFont);
		
		
		
		//create a  JScrollpane
		
		JScrollPane Ascroll = new JScrollPane(answers); //will allow the answer text area to scroll
		Ascroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		Ascroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		cardList  = new ArrayList<FlashCard>();
		
		//create next card button 
		 
		JButton nextButton = new JButton("Next");
		clear = new JButton("Clear");
		clearAll = new JButton("Start Over");
//		JButton prevButton = new JButton("Previous");
		
		//create some labels
		JLabel questionLabel = new JLabel("Question");
		JLabel answerLabel = new  JLabel("Answer");
		
		dL = new DefaultListModel<>();
		
		displayList = new JList<>(dL);
//		displayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		displayList.setVisibleRowCount(10);
		
		JScrollPane listScrollPane = new JScrollPane(displayList);
		listScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		listScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		clear.addActionListener(new ClearActionListener());
		
		clearAll.addActionListener(new ClearAllActionListener());
		//add components to mainPanel
		mainPanel.add(questionLabel); //add questions label 
		mainPanel.add(Qscroll);
		mainPanel.add(answerLabel);
		mainPanel.add(Ascroll);
	
//		mainPanel.add(prevButton);  
		mainPanel.add(nextButton);
		mainPanel.add(clear);
		mainPanel.add(clearAll);
		mainPanel.add(listScrollPane);
//		mainPanel.add(displayList);
		Color cl = new Color(189,173,173);
		mainPanel.setBackground(cl);
		
	
		
		//add action listeners to out buttons 
		
		nextButton.addActionListener(new NextCardListener());
//		prevButton.addActionListener(new PrevCardListener());
		clear.addActionListener(new ClearActionListener());
		
		clearAll.addActionListener(new ClearAllActionListener());
		//Add menu bar 
		JMenuBar menuBar = new JMenuBar();
		//lets now create the menu items 
		JMenu fileMenu = new JMenu("Options");
		JMenuItem newMenuItem = new JMenuItem("New");
		JMenuItem saveMenuItem = new JMenuItem("Save");
		fileMenu.add(newMenuItem);
		fileMenu.add(saveMenuItem);
		menuBar.add(fileMenu);
		
		//add event listeners for the menu items

		newMenuItem.addActionListener(new NewMenuItemListener());
		saveMenuItem.addActionListener(new SavedMenuItemListener());
	
		
		
		
		
		//add it to its appropriate location
		frame.setJMenuBar(menuBar);
		
		
		//add to the frame
		//get out Frame , get the content panel then add the main panel
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(1200, 550);
		frame.setResizable(false);
		frame.setVisible(true);
		
		
		
		
		 
		 
				
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new FlashCardsBuilder(); //calls the constructor 
			}
			
		});

	}
	
	

}
