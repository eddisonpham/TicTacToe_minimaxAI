import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
public class TTT extends JPanel implements ActionListener
{
	JButton buttons[][] = new JButton[3][3];
	String winner = "n";
	JLabel winnerText;
	public TTT ()
	{
		
		setBackground(Color.orange);
		JLabel title = new JLabel ("Tic Tac Toe");
		title.setFont (new Font ("Jokerman", Font.BOLD, 30));
		title.setForeground (Color.red);
		winnerText = new JLabel("Click on a square to place an X:");
		JButton reset = new JButton("Reset");
		reset.setForeground(Color.yellow);
		reset.setBackground(Color.blue);
		reset.setActionCommand("reset");
		reset.addActionListener(this);
		add (title);
		add(winnerText);
		for (int i =0;i<3;i++) {
			for (int j =0;j<3;j++) {
				buttons[i][j] = new JButton("");
				buttons[i][j].setPreferredSize(new Dimension(60,40));
				buttons[i][j].setBackground(Color.white);
				buttons[i][j].setActionCommand(i+""+j);
				buttons[i][j].addActionListener(this);
				add(buttons[i][j]);
			}
		}
		add(reset);
		botMove();
	}
	public void actionPerformed (ActionEvent e)
	{
		if (e.getActionCommand().equals("reset")) {
			reset();
		}else {
			for (int i =0;i<3;i++) {
				for (int j =0;j<3;j++) {
					if (e.getActionCommand().equals(i+""+j)&&winner.equals("n")) {
						buttons[i][j].setText("X");
						buttons[i][j].setForeground(new Color(0,255,255));
						buttons[i][j].setBackground(Color.blue);
						buttons[i][j].setEnabled(false);
						winner = checkWinner();
						if (winner.equals("n")) {
							botMove();
							winner=checkWinner();
						}else {
							setWinnerText();
						}
					}else if(!winner.equals("n")) {
						setWinnerText();
					}
				}
			}
		}
	}
	public void reset() {
		winner = "n";
		for (int i=0;i<3;i++) {
			for (int j=0;j<3;j++) {
				buttons[i][j].setEnabled(true);
				buttons[i][j].setText("");
				buttons[i][j].setBackground(Color.white);
				winnerText.setText("Click on a square to place an X:");
			}
		}
		botMove();
	}
	public void setWinnerText() {
		if (checkWinner().equals("t")) {
			winnerText.setText("                           You tied.                          ");
		}else {
			winnerText.setText((winner.equals("O")?"The computer won, you LOSE.    ":"  The computer lost, you WIN.      "));
		}
		for (int i=0;i<3;i++) {
			for (int j=0;j<3;j++) {
				buttons[i][j].setEnabled(false);
			}
		}
	}
	public boolean checkThree(String a, String b, String c) {
		return a.equals(b)&&a.equals(c)&&!a.equals("");
	}
	public String checkWinner() {
		//row check
		for (int i =0;i<3;i++) {
			if (checkThree(buttons[i][0].getText(),buttons[i][1].getText(),buttons[i][2].getText())) {
				return buttons[i][0].getText();
			}
		}
		//col
		for (int i =0;i<3;i++) {
			if (checkThree(buttons[0][i].getText(),buttons[1][i].getText(),buttons[2][i].getText())) {
				return buttons[0][i].getText();
			}
		}
		//diagonals
		if (buttons[0][0].getText().equals(buttons[1][1].getText())&&buttons[0][0].getText().equals(buttons[2][2].getText())&&!buttons[0][0].getText().equals("")) {
			return buttons[0][0].getText();
		}
		if (buttons[0][2].getText().equals(buttons[1][1].getText())&&buttons[0][2].getText().equals(buttons[2][0].getText())&&!buttons[0][2].getText().equals("")) {
			return buttons[0][2].getText();
		}
		for (int i =0;i<3;i++) {
			for (int j =0;j<3;j++) {
				if (buttons[i][j].getText().equals("")) {
					return "n";
				}
			}
		}
		return "t";
	}
	public void botMove() {
		int bestScore = Integer.MIN_VALUE;
		int movei=0;
		int movej=0;
		for (int i =0;i<3;i++) {
			for (int j =0;j<3;j++) {
				if (buttons[i][j].getText().equals("")) {
					buttons[i][j].setText("O");
					int score = minimax(false);
					buttons[i][j].setText("");
					if (score>bestScore) {
						bestScore= score;
						movei=i;
						movej=j;
					}
				}
			}
		}
		buttons[movei][movej].setText("O");
		buttons[movei][movej].setForeground(new Color(255,255,0));
		buttons[movei][movej].setBackground(Color.red);
		buttons[movei][movej].setEnabled(false);
	}
		public int minimax(boolean maxPlayer) {

			
			String results = checkWinner();
			if (!results.equals("n")) {
			   if (results.equals("X")) {
				   return -1;
			   }else if (results.equals("O")) {
				   return 1;
			   } else if (results.equals("t")) {
				   return 0;
			   }
			}
			int max = Integer.MIN_VALUE;
			int min = Integer.MAX_VALUE;
			if (maxPlayer) {
				
				for (int i =0;i<3;i++) {
					for (int j =0;j<3;j++) {
						if (buttons[i][j].getText().equals("")) {
							buttons[i][j].setText("O");
							int score = minimax(false);
							buttons[i][j].setText("");
							max = Math.max(score, max);
						}
					}
				}
				return max;
			}else{
				for (int i =0;i<3;i++) {
					for (int j =0;j<3;j++) {
						if (buttons[i][j].getText().equals("")) {
							buttons[i][j].setText("X");
							int score = minimax(true);
							buttons[i][j].setText("");
							min = Math.min(score, min);
							
						}
					}
				}
				return min;
			}
		}	

	protected static ImageIcon createImageIcon (String path)
	{
		java.net.URL imgURL = TTT.class.getResource (path);
		if (imgURL != null)
			return new ImageIcon (imgURL);
		else
			return null;
	}
	public static void main (String[] args)
	{
		JFrame.setDefaultLookAndFeelDecorated (true);
		//Create and set up the window.
		JFrame frame = new JFrame ("TTT");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		//Create and set up the content pane.
		JComponent newContentPane = new TTT ();
		newContentPane.setOpaque (true);
		frame.setContentPane (newContentPane);
		frame.setSize (250, 300);
		frame.setVisible (true);
	}
}
