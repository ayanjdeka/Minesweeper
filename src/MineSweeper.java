import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;


public class MineSweeper extends JPanel implements MouseListener, ActionListener{

    private Grid grid;
    private JToggleButton[][] array;
    private Tiles[][] tiles;

    private static final int BOX_SIZE = 50;

    private static final int INTERMEDIATE_LEVEL = 16;
    private static final int BEGINNER_LEVEL = 9;


    private static final int EXPERT_HEIGHT = 16;
 	private static final int EXPERT_WIDTH = 30;

 	int gridHeight;
 	int gridWidth;
 	int numMines;

 	JPanel scoreboard;
 	JPanel bigPanel;
 	JLabel flags;
 	JLabel time;
 	JButton face;


    int timePassed;
    String timeText;

    int amountOfFlagsLeft;

	JFrame frame;
	JMenuBar menuBar;

	JMenu game,icons,controls;

	JMenuItem beginner,intermediate,expert;

	JMenuItem defaultTheme, themeTwo, themeThree;

	JMenuItem instructions;

	int countOfClicks;
	boolean firstClick = true;

	Timer timer;
	boolean gameEnd=false;

	ImageIcon block;
	ImageIcon mine;
	ImageIcon flagged;
	ImageIcon empty;
	ImageIcon one;
	ImageIcon two;
	ImageIcon three;
	ImageIcon four;
	ImageIcon five;
	ImageIcon six;
	ImageIcon seven;
	ImageIcon eight;


    public MineSweeper(){
        grid = new Grid(BEGINNER_LEVEL, BEGINNER_LEVEL, 10);
        amountOfFlagsLeft = 10;
        UIManager.put("ToggleButton.select", Color.PINK);
    }

     public MineSweeper(Grid grid){
         this.grid=grid;

    }

	public void runCode(){

		frame = new JFrame("MineSweeper Program");


		menuBar = new JMenuBar();
		game = new JMenu("Game");
		icons = new JMenu("Icons");
		controls = new JMenu("Controls");

		beginner = new JMenuItem("Beginner");
		intermediate = new JMenuItem("Intermediate");
		expert = new JMenuItem("Expert");

		beginner.addActionListener(this);
		intermediate.addActionListener(this);
		expert.addActionListener(this);

		game.add(beginner);
		game.add(intermediate);
		game.add(expert);

		instructions = new JMenuItem("Intructions");
		controls.add(instructions);

		defaultTheme = new JMenuItem("The default theme");
		themeTwo = new JMenuItem("This is a different theme");
		themeThree = new JMenuItem("This is another different theme");

		icons.add(defaultTheme);
		icons.add(themeTwo);
		icons.add(themeThree);

		menuBar.add(game);
		menuBar.add(icons);
		menuBar.add(controls);

		amountOfFlagsLeft = grid.getMines();

		numMines = grid.getMines();
		gridWidth = grid.getWidth();
		gridHeight = grid.getHeight();

		scoreboard = new JPanel();
		scoreboard.setLayout(new GridLayout(1,3));

		flags = new JLabel("Flags: "+amountOfFlagsLeft);
		time = new JLabel("Time: ");
		face = new JButton();

		face.addActionListener(this);


		scoreboard.add(time);
		scoreboard.add(face);
		scoreboard.add(flags);

		bigPanel = new JPanel();
		bigPanel.setLayout(new GridLayout(
			2,1));
		bigPanel.add(menuBar);
		bigPanel.add(scoreboard);

		frame.add(bigPanel,BorderLayout.NORTH);



        this.setLayout(new GridLayout(grid.getWidth(), grid.getHeight()));

       frame.setSize(BOX_SIZE*grid.getHeight(),BOX_SIZE*grid.getWidth());

        block = new ImageIcon("block.png");

		block = new ImageIcon(
		block.getImage().getScaledInstance(
		frame.getWidth()/(grid.getWidth()),
		frame.getHeight()/(grid.getWidth()),
			Image.SCALE_SMOOTH));

        flagged = new ImageIcon("flagged.png");
		flagged = new ImageIcon(
		flagged.getImage().getScaledInstance(
		frame.getWidth()/(grid.getWidth()),
		frame.getHeight()/(grid.getWidth()),
			Image.SCALE_SMOOTH));

        mine = new ImageIcon("mine.png");
		mine = new ImageIcon(
		mine.getImage().getScaledInstance(
		frame.getWidth()/(grid.getWidth()),
		frame.getHeight()/(grid.getWidth()),
			Image.SCALE_SMOOTH));

        empty = new ImageIcon("empty.png");
		empty = new ImageIcon(
		empty.getImage().getScaledInstance(
		frame.getWidth()/(grid.getWidth()),
		frame.getHeight()/(grid.getWidth()),
			Image.SCALE_SMOOTH));

        one = new ImageIcon("one.png");
		one = new ImageIcon(
		one.getImage().getScaledInstance(
		frame.getWidth()/(grid.getWidth()),
		frame.getHeight()/(grid.getWidth()),
			Image.SCALE_SMOOTH));


        two = new ImageIcon("two.png");
		two = new ImageIcon(
		two.getImage().getScaledInstance(
		frame.getWidth()/(grid.getWidth()),
		frame.getHeight()/(grid.getWidth()),
			Image.SCALE_SMOOTH));

        three = new ImageIcon("three.png");
		three = new ImageIcon(
		three.getImage().getScaledInstance(
		frame.getWidth()/(grid.getWidth()),
		frame.getHeight()/(grid.getWidth()),
			Image.SCALE_SMOOTH));

        four = new ImageIcon("four.png");
		four = new ImageIcon(
		four.getImage().getScaledInstance(
		frame.getWidth()/(grid.getWidth()),
		frame.getHeight()/(grid.getWidth()),
			Image.SCALE_SMOOTH));

        five = new ImageIcon("five.png");
		five = new ImageIcon(
		five.getImage().getScaledInstance(
		frame.getWidth()/(grid.getWidth()),
		frame.getHeight()/(grid.getWidth()),
			Image.SCALE_SMOOTH));

        six = new ImageIcon("six.png");
		six = new ImageIcon(
		six.getImage().getScaledInstance(
		frame.getWidth()/(grid.getWidth()),
		frame.getHeight()/(grid.getWidth()),
			Image.SCALE_SMOOTH));


        seven = new ImageIcon("seven.png");
		seven = new ImageIcon(
		seven.getImage().getScaledInstance(
		frame.getWidth()/(grid.getWidth()),
		frame.getHeight()/(grid.getWidth()),
			Image.SCALE_SMOOTH));

        eight = new ImageIcon("eight.png");
		eight = new ImageIcon(
		eight.getImage().getScaledInstance(
		frame.getWidth()/(grid.getWidth()),
		frame.getHeight()/(grid.getWidth()),
			Image.SCALE_SMOOTH));



        grid.setTiles();
        tiles = grid.getTiles();
        array = new JToggleButton[tiles.length][tiles[0].length];
        for(int i=0;i<tiles.length;i++)
        {
            for(int j=0;j<tiles[0].length;j++)
            {
                array[i][j] = new JToggleButton();
                SwingUtilities.updateComponentTreeUI(array[i][j]);
                array[i][j].addMouseListener(this);
                array[i][j].setBorder(BorderFactory.createBevelBorder(1));
                array[i][j].setBackground(Color.BLUE);
                array[i][j].setOpaque(true);
                array[i][j].setIcon(block);
                this.add(array[i][j]);
            }
        }

		frame.add(this);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

    public void expand(int x, int y)
    {
        if(x<0 || y<0)
            return;
        else if(x>=grid.getWidth() || y>=grid.getHeight())
            return;
        else
        {
            if(array[x][y].isSelected())
                return;
            else
            {
                if(tiles[x][y].getNumber()<=0 && !tiles[x][y].getState().equals("Bomb") && !tiles[x][y].getState().equals("Bomb"))
                {
					array[x][y].setIcon(empty);
                    array[x][y].setSelected(true);
					expand(x - 1, y - 1);
			        expand(x - 1, y);
			        expand(x - 1, y + 1);
			        expand(x + 1, y);
			        expand(x, y - 1);
			        expand(x, y + 1);
			        expand(x + 1, y - 1);
			        expand(x + 1, y + 1);

                }
                else if(tiles[x][y].getNumber()>0 && !tiles[x][y].getState().equals("Bomb"))
                {
                    array[x][y].setSelected(true);

                    if(tiles[x][y].getNumber() == 1){
						array[x][y].setIcon(one);
					}

                    if(tiles[x][y].getNumber() == 2){
						array[x][y].setIcon(two);
					}

                    if(tiles[x][y].getNumber() == 3){
						array[x][y].setIcon(three);
					}

                    if(tiles[x][y].getNumber() == 4){
						array[x][y].setIcon(four);
					}

                    if(tiles[x][y].getNumber() == 5){
						array[x][y].setIcon(five);
					}

                    if(tiles[x][y].getNumber() == 6){
						array[x][y].setIcon(six);
					}

                    if(tiles[x][y].getNumber() == 7){
						array[x][y].setIcon(seven);
					}

                    if(tiles[x][y].getNumber() == 8){
						array[x][y].setIcon(eight);
					}
                    return;
                }
                else if(tiles[x][y].getState().equals("Bomb"))
                    return;
                else if(tiles[x][y].getState().equals("Flag"))
                    return;
            }
        }

    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == beginner){
            frame.setVisible(false);
            new MineSweeper(new Grid(BEGINNER_LEVEL, BEGINNER_LEVEL, 10)).runCode();
            amountOfFlagsLeft = 10;
        }
        if(e.getSource() == intermediate){
            frame.setVisible(false);
            new MineSweeper(new Grid(INTERMEDIATE_LEVEL, INTERMEDIATE_LEVEL, 40)).runCode();
            amountOfFlagsLeft = 40;
        }
        if(e.getSource() == expert){
            frame.setVisible(false);
            new MineSweeper(new Grid(16, 30, 99)).runCode();
            amountOfFlagsLeft = 99;
        }
        if(e.getSource() == face){
			if(gameEnd){
				frame.setVisible(false);
				gameEnd = false;
				new MineSweeper(new Grid(gridWidth,gridHeight,numMines)).runCode();
			}
		}
    }

	public void mouseExited(MouseEvent e){

	}

    public void mousePressed(MouseEvent e) {
    }
    public void mouseClicked(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) {
    }



    public void mouseReleased(MouseEvent e){
        for(int x=0;x<array.length;x++){
            for(int y=0;y<array[0].length;y++){
                if(e.getSource()==array[x][y]){
                    if(!array[x][y].isSelected()){
                        array[x][y].setSelected(true);
                        if(e.getButton()==e.BUTTON3){
                            tiles[x][y].setState("Flag");
                            array[x][y].setIcon(flagged);
                            amountOfFlagsLeft--;
                            flags.setText("Flags: "+amountOfFlagsLeft);
                        }
                    }
                    else{
                        array[x][y].setSelected(true);
                        if(firstClick){
                            grid.setNumbers(x, y);
                            startTimer();
                            if(e.getButton() == e.BUTTON3){
                                tiles[x][y].setState(tiles[x][y].getOriginalState());
                                array[x][y].setText("");
                                array[x][y].setIcon(block);
                                amountOfFlagsLeft++;
                                flags.setText("Flags: "+amountOfFlagsLeft);
                                array[x][y].setSelected(false);

                            }
                            else{
								array[x][y].setIcon(empty);
                                expand(x - 1, y - 1);
								expand(x - 1, y);
								expand(x - 1, y + 1);
								expand(x + 1, y);
								expand(x, y - 1);
								expand(x, y + 1);
								expand(x + 1, y - 1);
        						expand(x + 1, y + 1);
                            }
                            firstClick=false;
                        }
                        else
                        {
                            if(e.getButton() == e.BUTTON3){
                                if(tiles[x][y].getState().equals("Flag")) {
                                    tiles[x][y].setState(tiles[x][y].getOriginalState());
                                    array[x][y].setIcon(block);
                                    array[x][y].setText("");
                                    amountOfFlagsLeft++;
                                    flags.setText("Flags: "+amountOfFlagsLeft);
                                    array[x][y].setSelected(false);
                                }
                            }
                            else
                            {
                                if(tiles[x][y].getNumber()>0 && !tiles[x][y].getState().equals("Bomb")){

									array[x][y].setSelected(true);

                                    if(tiles[x][y].getNumber() == 1){
										array[x][y].setIcon(one);
									}

                                    if(tiles[x][y].getNumber() == 2){
										array[x][y].setIcon(two);
									}

                                    if(tiles[x][y].getNumber() == 3){
										array[x][y].setIcon(three);
									}

                                    if(tiles[x][y].getNumber() == 4){
										array[x][y].setIcon(four);
									}

                                    if(tiles[x][y].getNumber() == 5){
										array[x][y].setIcon(five);
									}

                                    if(tiles[x][y].getNumber() == 6){
										array[x][y].setIcon(six);
									}

                                    if(tiles[x][y].getNumber() == 7){
										array[x][y].setIcon(seven);
									}

                                    if(tiles[x][y].getNumber() == 8){
										array[x][y].setIcon(eight);
									}
                                }
                                else if(tiles[x][y].getState().equals("Bomb")){
									for(int i=0;i<array.length;i++){
   							         for(int j=0;j<array[0].length;j++){
   							             if(tiles[i][j].getOriginalState().equals("Bomb")){
   							                 array[i][j].setIcon(mine);
   							                 array[i][j].setSelected(true);
   							                 gameEnd = true;

   							             }
   							             array[i][j].setSelected(true);
   							         }
   							     }

                                }
                                else{
									array[x][y].setIcon(empty);
                                    expand(x - 1, y - 1);
									expand(x - 1, y);
									expand(x - 1, y + 1);
									expand(x + 1, y);
									expand(x, y - 1);
									expand(x, y + 1);
									expand(x + 1, y - 1);
        							expand(x + 1, y + 1);
                                }
                            }
                        }
                	}

            	}
            }
        }

        for(int i = 0;i<array.length;i++){
			for(int j = 0;j<array[0].length;j++){

				if(tiles[i][j].getState().equals("Bomb")){

					if(tiles[i][j].getState().equals("Flag")){
						if(tiles[i][j].getOriginalState().equals("Bomb")){
							System.out.println("Correct");
						}

					}
				}

			}
		}

        countOfClicks++;
    }





	public class Tiles{
	    private String state;
	    private int number;
	    private String originalState;
	    public Tiles(){

	    }
	    public void setState(String state){
	        this.state = state;
	        if(!getState().equals("Flag"))
	            originalState = state;
	    }
	    public String getState(){
	        return state;
	    }
	    public void setNumber(int number){
	        this.number = number;
	    }
	    public int getNumber(){
	        return number;
	    }
	    public String getOriginalState(){
	        return originalState;
	    }



	}


	public class Grid{
	    private int width=0;
	    private int height=0;
	    private Tiles[][] tiles;
	    private Tiles[][] original;
	    private int numOfMines=0;
	    public Grid(int width, int height, int numOfMines){
	        this.width = width;
	        this.height = height;
	        tiles = new Tiles[width][height];
	        this.numOfMines = numOfMines;
	    }
	    public void setTiles(){
	        for(int x=0;x<width;x++)
	        {
	            for(int y=0;y<height;y++)
	            {
	                tiles[x][y] = new Tiles();
	                tiles[x][y].setState("Empty");
	                tiles[x][y].setNumber(0);
	            }
	        }
	    }
	    private void setBombs(int x, int y){
	        tiles[x][y].setState("Empty");
	        while(numOfMines>0)
	        {
	            int r = (int)(Math.random()*(width));
	            int c = (int)(Math.random()*(height));
	            if(tiles[r][c].getState().equals("Empty") && r!=x && c!=y)
	            {
	                tiles[r][c].setState("Bomb");
	                tiles[r][c].setNumber(-1);
	                numOfMines--;
	            }
	        }
	        original = tiles;
	    }

	    public void setNumbers(int x, int y){
	        setBombs(x, y);
	        for (int i = 0; i < width; i++) {
	            for (int j = 0; j < height; j++) {
	                if (original[i][j].getState().equals("Bomb")) {
	                        for (int a = -1; a <= 1 ; a++) {
	                        	for (int b = -1; b <= 1; b++) {

	                        	    try {
	                        	        if (!original[i+a][j+b].getState().equals("Bomb")) {
	                        	            tiles[i+a][j+b].setNumber(tiles[i+a][j+b].getNumber()+1);
	                        	        }
	                        	    }
	                        	    catch (Exception e) {

	                        	    }
	                        	}
	                        	}
	                	}
	            	}
	            }
	    }
	    public void setWidth(int width){
	        this.width = width;
	    }
	    public void setHeight(int height){
	        this.height=height;
	    }
	    public int getWidth(){
	        return width;
	    }
	    public int getHeight(){
	        return height;
	    }
	    public void setMines(int numOfMines){
	        this.numOfMines=numOfMines;
	    }
	    public int getMines(){
			return numOfMines;
		}

	    public Tiles[][] getTiles(){
	        return tiles;
	    }
	}

    public void startTimer() {
        timer = new Timer();
        timer.schedule(new UpdateTimer(),0,1000);

	}


	public class UpdateTimer extends TimerTask {
		public void run() {
			if(!gameEnd){
				timePassed++;
				timeText = " "+timePassed;
				time.setText("Time: "+timePassed);

			}
		}
	}






    public static void main(String[] args)
    {
        MineSweeper app = new MineSweeper();
        app.runCode();

    }



}