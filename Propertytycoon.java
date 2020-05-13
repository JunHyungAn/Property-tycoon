/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;


import javax.swing.*;
import javax.swing.border.*;
import javax.accessibility.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;

public class Propertytycoon extends JPanel implements ActionListener {
	public Object[] options;
	public Object choice;
	private JLayeredPane board;
	private JPanel tools, dice;
	private JLabel image, die1, die2;
	public static final JLabel money1 = new JLabel();
	public static final JLabel money2 = new JLabel();
	public static final JLabel money3 = new JLabel();
	public static final JLabel money4 = new JLabel();
	private JButton roll, done, buyProperty, addHouse, sellHouse, getOutOfJail;
	private JTextArea info;
	private JScrollPane scroll;
	private Dimension d = new Dimension(200, 15);
	private JLabel[] players;
	private ImageIcon icon;
	public static final Color[] colors = new Color[] { new Color(250, 0, 0), new Color(0, 250, 0), new Color(0, 0, 250),
			new Color(150, 150, 0) ,new Color(153,102,0), new Color(102,0,153)};
	public static final Point[] mod = new Point[] { new Point(0, 0), new Point(0, 35), new Point(35, 0),
			new Point(35, 35), new Point(65,0), new Point(0,55) };
	public int numberOfPlayers;
	public static final int[][] locs = { { 810, 810 }, { 708, 815 }, { 636, 815 }, { 565, 815 }, { 493, 815 },
			{ 420, 815 }, { 347, 815 }, { 272, 815 }, { 200, 815 }, { 127, 815 }, { 15, 815 }, { 15, 710 }, { 15, 637 },
			{ 15, 564 }, { 15, 492 }, { 15, 420 }, { 15, 347 }, { 15, 273 }, { 15, 200 }, { 15, 127 }, { 15, 30 },
			{ 126, 22 }, { 199, 22 }, { 272, 22 }, { 345, 22 }, { 418, 22 }, { 491, 22 }, { 564, 22 }, { 637, 22 },
			{ 710, 22 }, { 800, 22 }, { 812, 125 }, { 812, 198 }, { 812, 271 }, { 812, 345 }, { 812, 419 },
			{ 812, 492 }, { 812, 565 }, { 812, 638 }, { 812, 711 } };
	private Player current;
	public Boolean isDone;
	private Boolean hasRolled;
	private Die die = new Die(6);
	private Property[] spaces;
	private ArrayList<Player> thePlayers;

	public Propertytycoon() {
		super();
		numberOfPlayers = 0;
		isDone = false;
		setLayout(new FlowLayout());

		
		icon = new ImageIcon(getClass().getResource("rsz_proptycoonpic.jpg"));
		image = new JLabel(icon);
		image.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());

		board = new JLayeredPane();
		board.setPreferredSize(new Dimension(900, 900));
		board.setBorder(BorderFactory.createTitledBorder("Property Tycoon"));// watch out
		board.setOpaque(true);
		board.add(image, 2, 0);

		createButtons();

		add(board);
		add(tools);

	}

	// inputs number of players
	public void inputNumPlayers(int num) {
		numberOfPlayers = num;
	}

	// Creates the Button panel
	private void createButtons() {
		tools = new JPanel();
		tools.setLayout(new BoxLayout(tools, BoxLayout.Y_AXIS));

		dice = new JPanel(new FlowLayout());
		die1 = new JLabel();
		die1.setSize(200, 100);
		die2 = new JLabel();
		die2.setSize(200, 100);
		dice.add(die1);
		dice.add(die2);

		roll = new JButton("Roll");
		roll.setSize(d);
		roll.addActionListener(this);

		done = new JButton("Done");
		done.setSize(d);
		done.addActionListener(this);

		buyProperty = new JButton("Buy Property");
		buyProperty.setSize(d);
		buyProperty.addActionListener(this);

		addHouse = new JButton("Add House");
		addHouse.setSize(d);
		addHouse.addActionListener(this);

		sellHouse = new JButton("Sell House");
		sellHouse.setSize(d);
		sellHouse.addActionListener(this);

		getOutOfJail = new JButton("Get Out Of Jail");
		getOutOfJail.setSize(d);
		getOutOfJail.addActionListener(this);

		createScrollingText();

		tools.add(dice);
		tools.add(roll);
		tools.add(buyProperty);
		tools.add(addHouse);
		tools.add(sellHouse);
		tools.add(getOutOfJail);
		tools.add(done);
		tools.add(Box.createVerticalStrut(40));
		tools.add(scroll);
		tools.add(money1);
		tools.add(money2);
		tools.add(money3);
		tools.add(money4);
	}

	// Creates the scrolling text box
	public void createScrollingText() {
		info = new JTextArea(5, 20);
		info.setSize(180, 400);
		info.setLineWrap(true);
		scroll = new JScrollPane(info);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		info.setText("Welcome to property tycoon!");
	}

	// Puts the array as an instance field
	public void fillSpaces(Property[] p) {
		spaces = p;
	}

	// Fills players into an array and creates the markers
	public void fillPlayers() {
		JLabel[] p = new JLabel[numberOfPlayers];
		for (int i = 0; i < p.length; i++)
			p[i] = createPlayerMarker(i + 1, colors[i], mod[i]);
		players = p;
		for (JLabel e : players)
			board.add(e, 3, 1);
	}

	// Puts the player array list as an instance field
	private void enterPlayers(ArrayList<Player> playersIn) {
		thePlayers = playersIn;
	}

	// Sets Player p as their turn
	public void updateMoney() {
		for (Player p : thePlayers) {
			if (p.getNum() == 1)
				money1.setText(p.getName() + ": " + (new Integer(p.getMoney())).toString());
			if (p.getNum() == 2)
				money2.setText(p.getName() + ": " + (new Integer(p.getMoney())).toString());
			if (p.getNum() == 3)
				money3.setText(p.getName() + ": " + (new Integer(p.getMoney())).toString());
			if (p.getNum() == 4)
				money4.setText(p.getName() + ": " + (new Integer(p.getMoney())).toString());
		}
	}

	// Sets the player whose current turn it is
	public void setCurrentPlayer(Player p) {
		hasRolled = false;
		isDone = false;
		current = p;
		
	}

	// returns true if the player can end their turn
	public boolean isPlayerDone() {
		return isDone;
	}

	// Creates a rectangular player marker
	// FIND A WAY TO INCORPORATE NUMBER
	private JLabel createPlayerMarker(int number, Color color, Point mod) {
		JLabel label = new JLabel();
		label.setVerticalAlignment(JLabel.TOP);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setOpaque(true);
		label.setBackground(color);
		label.setBorder(BorderFactory.createLineBorder(Color.black));
		label.setBounds(locs[0][0] + mod.x, locs[0][1] + mod.y, 30, 30);
		return label;
	}

	// Removes a player from the game
	// Removes player from the game
	public void removePlayer(Player p) {
		board.remove(players[p.getNum()]);
	}

	// Add text to the scrolling info window
	public void addText(String text) {
		info.setText(info.getText() + "\n" + text);
	}

	// moves a player to a target location
	///// TRY ANIMATION WITH A LOOP
	/*
	 * public void movePlayer(Player p, int location) {
	 * players[p.getPlayerNumber()].setLocation(locs[location][0],
	 * locs[location][1]); }
	 */
	public void movePlayer(int player, int location) {
		JLabel thePlayer = players[player - 1];
		thePlayer.setLocation(locs[location][0] + mod[player].x, locs[location][1] + mod[player].y);
	}

	// Roll the dice and pay property fees
	// Roll the dice and pay fees
	private void roll() {
		hasRolled = true;
		if (!current.isJailed()) {
			int roll1 = die.cast();
			int roll2 = die.cast();
			die1.setText((new Integer(roll1)).toString() + "-");
			die2.setText((new Integer(roll2)).toString());
			movePlayer(current.getNum(), current.move(roll1 + roll2));
			// Property he/she lands on affects the player
			Property p = spaces[current.getLoc()];
			addText(current.getName() + " landed on " + p.getName());
			if (!(p.getOwner() == null) && !(p.getOwner().equals(current)))
				addText(current.getName() + p.getText());
			p.affect(current);
			updateMoney();
			movePlayer(current.getNum(), current.getLoc());
			if (roll1 == roll2) {
				current.incrementDoubles();
				if (current.isJailed()) {
					movePlayer(current.getNum(), current.getLoc());
					addText("Too Fast! Go to jail");
				} else {
					addText("You rolled Doubles!  Roll again");
					hasRolled = false;
				}
			}
		} else if (current.isJailed()) {
			int roll1 = die.cast();
			int roll2 = die.cast();
			if (roll1 == roll2) {
				movePlayer(current.getNum(), current.move(roll1 + roll2));
				current.release();
				addText(current.getName() + "has been released!");
				Property p = spaces[current.getLoc()];
				addText(current.getName() + " landed on " + p.getName());
				if (!(p.getOwner().equals(current)))
					addText(current.getName() + p.getText());
				p.affect(current);
				movePlayer(current.getNum(), current.getLoc());
				hasRolled = false;
			} else {
				current.addTime();
				if (current.getTime() == 3) {
					movePlayer(current.getNum(), current.move(roll1 + roll2));
					current.release();
					current.pay(50);
					addText(current.getName() + "has been released!");
					Property p = spaces[current.getLoc()];
					addText(current.getName() + " landed on " + p.getName());
					if (!(p.getOwner() == null) && !(p.getOwner().equals(current)))
						addText(current.getName() + p.getText());
					p.affect(current);
					updateMoney();
					movePlayer(current.getNum(), current.getLoc());
				} else
					addText(current.getName() + " is still in jail");
			}
			updateMoney();
		}
	}

	// Button Presses
	// performs actions for buttons
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == roll) {
			if (!hasRolled)
				roll();
			else
				addText("Cheater!");
		}
		if (event.getSource() == done) {
			if (hasRolled) {
				isDone = true;
				int next = (current.getNum()) % thePlayers.size();
				setCurrentPlayer(thePlayers.get(next));
				info.append("\nIts " + thePlayers.get(next).getName() + " turn now");
			} else
				addText("You're not done, roll the dice.");
		}
		if (event.getSource() == addHouse) {
			if (current.buildableProps().length > 0) {
				Street[] props = current.buildableProps();
				options = new Object[(props.length + 1)];
				for (int i = 0; i < options.length - 1; i++)
					options[i] = props[i].getName();
				options[options.length - 1] = "Done";
				choice = JOptionPane.showOptionDialog(null, "On which properties?", "", JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				if (!choice.equals("Done")) {
					int chosen = Arrays.binarySearch(options, choice);
					props[chosen].addHouse();
				}
				updateMoney();
			} else
				addText("You have no properties");
		}
		if (event.getSource() == sellHouse) {

		}
		if (event.getSource() == buyProperty) {
			Property p = spaces[current.getLoc()];
			if (p.getOwner() == null) {
				current.get(p);
				current.pay(p.getCost());
				p.setOwner(current);
				addText(current.getName() + " bought " + p.getName() + " for " + p.getCost() + ".");
			} else
				addText("You can't buy that!");
			updateMoney();
		}
		if (event.getSource() == getOutOfJail) {
			if (current.isJailed() && current.hasJailCard()) {
				current.release();
				addText(current.getName() + " has been released from Jail.");
			} else
				addText("You have no get out of jail free card!");

		}
	}

	// Run!
	public static void main(String Lawl[]) {
		

		Street med = new Street("Crapper Str", "PURPLE", 1, 2, 10, 30, 90, 160, 250, 50, 60);
		Street bal = new Street("Gangsters Paradise", "PURPLE", 2, 4, 20, 60, 180, 320, 450, 50, 60);
		Street ori = new Street("Weeping Angel", "LIGHT_BLUE", 1, 6, 30, 90, 270, 400, 550, 50, 100);
		Street ver = new Street("Potts Avenue", "LIGHT_BLUE", 2, 6, 30, 90, 270, 400, 550, 50, 100);
		Street con = new Street("Nardole Drive", "LIGHT_BLUE", 3, 8, 40, 100, 300, 450, 600, 50, 100);
		Street cha = new Street("Skywalker Drive", "PINK", 1, 10, 50, 150, 450, 625, 750, 100, 140);
		Street sta = new Street("Wookie Hole", "PINK", 2, 10, 50, 150, 450, 625, 750, 100, 140);
		Street vir = new Street("Rey Lane", "PINK", 3, 12, 60, 180, 500, 700, 900, 100, 160);
		Street jam = new Street("Cooper Drive", "ORANGE", 1, 14, 70, 200, 550, 750, 950, 100, 180);
		Street ten = new Street("Wolowitz Street", "ORANGE", 2, 14, 70, 200, 550, 750, 950, 100, 180);
		Street york = new Street("Penny Lane", "ORANGE", 3, 16, 80, 220, 600, 800, 1000, 100, 200);
		Street ken = new Street("Yue Fei Square", "RED", 1, 18, 90, 250, 700, 875, 1050, 150, 220);
		Street ind = new Street("Mulan Rouge", "RED", 2, 18, 90, 250, 700, 875, 1050, 150, 220);
		Street ill = new Street("Han Xin Gardens", "RED", 3, 20, 100, 300, 750, 925, 1100, 150, 240);
		Street atl = new Street("Kirk Close", "YELLOW", 1, 22, 110, 330, 800, 975, 1150, 150, 260);
		Street ven = new Street("Picard Avenue", "YELLOW", 2, 22, 110, 330, 800, 975, 1150, 150, 260);
		Street mar = new Street("Crusher Creek", "YELLOW", 3, 24, 120, 360, 850, 1025, 1200, 150, 280);
		Street pac = new Street("Sirat Mews", "GREEN", 1, 26, 130, 390, 900, 1100, 1275, 200, 300);
		Street car = new Street("Ghenkis Crescent", "GREEN", 2, 26, 130, 390, 900, 1100, 1275, 200, 300);
		Street pen = new Street("Ibis Close", "GREEN", 3, 28, 150, 450, 1000, 1200, 1400, 200, 320);
		Street par = new Street("Hawking Way", "BLUE", 1, 35, 175, 500, 1100, 1300, 1500, 200, 350);
		Street boa = new Street("Turing Heights", "BLUE", 2, 50, 200, 600, 1400, 1700, 2000, 200, 400);
		Railroad rea = new Railroad("Brighton Station");
		Railroad penn = new Railroad("Hove Station");
		Railroad bo = new Railroad("Falmer Station");
		Railroad sho = new Railroad("Lewes Station");
		Utility ele = new Utility("Tesla Power Co");
		Utility wat = new Utility("Edison Water");
		Space go = new Space("Go", 200);
		Space incTax = new Space("Income Tax", -200);
		Space luxTax = new Space("Super  Tax", -100);
		Space free = new Space("Free Parking", 500);
		Space jail = new Space("Jail", 0);
		GoToJail gtg = new GoToJail();
		CardStack oppknocks = new CardStack(new Card[] { new Card("\nBank pays you divided of 50\n"),new Card("\nyou have won a lip sync battle. Collect 100\n"), 
                                                                  new Card("\nAdvance to Turing Heights\n"),new Card("\nAdvance to Han Xin Gardens.If you pass GO, collect 200\n"), 
                                                                  new Card("\nFined 15 for speeding\n"),new Card("\nPay university fees of 150\n"), new Card("\nTake a trip to Hove Station. If you pass GO collect 200\n"),
                                                                  new Card("\nLoan matures,collect 150\n"),new Card("\nYou are assesed for repairs, 40/house, 115/hotel\n"),new Card("\nAdvance to GO\n"),
                                                                  new Card("\nYou are assessed for repairs, 25/house,100/hotel\n"),new Card("G\no back 3 spaces\n"),new Card("\nAdvance to Skywalker Drive. If you pass GO collect 200\n"),
                                                                  new Card("\no to jail. Do not pass GO, do not collect 200\n"),new Card("\nDrunk in charge of a skateboard. Fine 20\n"), new Card("\net out of jail free\n") });
                CardStack potluck= new CardStack(new Card[]{  new Card("\nYou inherit 100\n"), new Card("\nYou have won 2nd prize in a beauty contest, collect 200\n"),new Card("\nGo back to Crapper Street\n"),new Card("\nStudent loan refund. Collect 20\n"),
                                                              new Card("\nBank error in your favour. Collect 200\n"),new Card("\nPay bill for text books of 100\n"),new Card("\nMega late night taxi bill pay 50\n"),new Card("\nAdvance to GO\n"),
                                                              new Card("\nFrom sale of Bitcoin you get 50\n"),new Card("\nPay a 10 fine or take opportunity knocks\n"),new Card("\nPay insurance fee of 50\n"),new Card("\nSavings bond matures,collect 100\n"),
                                                              new Card("\nGo to jail. Do not pass GO, do not collect 200\n"),new Card("\nReceived interest on shares of 25\n"), new Card("\nIts your birthday. Collect 10 from each player\n"), 
                                                              new Card("\nGet out of jail free\n")});
                        
		Property[] spaces = { go, med, /* cc */ potluck, bal, incTax, rea, ori, /* chance */oppknocks, ver, con, jail, cha,
				ele, sta, vir, penn, jam, /* cc */potluck, ten, york, free, ken, /* chance */oppknocks, ind, ill, bo, atl,
				ven, wat, mar, gtg, pac, car, /* cc */potluck, pen, sho, /* chance */oppknocks, par, luxTax, boa };
                
                
		JFrame frame = new JFrame("Property Tycoon");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		Propertytycoon game = new Propertytycoon();
		game.setOpaque(true); // content panes must be opaque
		frame.setContentPane(game);

		// Display the window.
		frame.pack();
		frame.setVisible(true);

		// Choose number of players.
                    String nums = JOptionPane.showInputDialog("Please input number of players(minimum 2 players) : ");
                    int num = Integer.parseInt(nums);
                    if (num < 2){
                        nums = JOptionPane.showInputDialog("* Please input minimum 2 players * : ");
                        num = Integer.parseInt(nums);
                        }
                    else if (num >= 7){
                        nums = JOptionPane.showInputDialog("* Maximum 6 players can be, Please input again * : ");
                        num = Integer.parseInt(nums);
                    }
                    
                    ArrayList<Player> playersIn = new ArrayList<Player>();
                    for(int i = 1; i <= num; i++){
                        String name = JOptionPane.showInputDialog("Player" + i + " enter your name.");
                        Player player = new Player(name, i);
                        playersIn.add(player);
                    }
                         
                game.inputNumPlayers(playersIn.size());
		game.fillPlayers();
		game.fillSpaces(spaces);
		game.enterPlayers(playersIn);
		game.updateMoney();
		int turn = 0;

		for (int i = 1; i > 0; i++) {
			game.setCurrentPlayer(playersIn.get(turn));
			game.addText("It is " + game.current.getName() + "'s turn.");
			while (!game.isPlayerDone()) {
				if (game.current.getMoney() < 0) {
					playersIn.remove(turn);
					turn--;
					game.addText(game.current.getName() + " is bankrupt and removed from the game.");
					game.isDone = true;
				}
			}
			game.current.reset();
			game.updateMoney();
			if (turn < playersIn.size())
				turn++;
			if (turn >= playersIn.size())
				turn = 0;
			if (playersIn.size() == 1)
				i = -1;

		}
		// Player current moves

		// When there's only one player left, prints that that player wins.
		Player winner = playersIn.get(0);
		JOptionPane.showMessageDialog(null, winner.getName() + "wins");
               

		
                        
		

	}
        
        

}
