import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends Applet implements MouseListener {

	private Square[][] Board;
	private int Row, Col, Player = 1, Count = 1, tRow, tCol, p1Count=0, p2Count=0;
	private Graphics m;
	private boolean[][] canMove;

	public void init() {
		canMove = new boolean[8][8];
		initializeBool();
		m = getGraphics();
		m.setColor(Color.cyan);
		Board = new Square[8][8];
		for(int i=0;i<Board.length;i++) 
			for(int j = 0;j<Board[0].length;j++)
				Board[i][j]=new Square();
		addMouseListener(this);
		for(int i=0;i<3;i++)
			for(int j=0;j<8;j++)
				if((i%2==0)&&(j%2==0)) 
					Board[i][j].setPlayer(1);
				else if((i==1)&&(j%2==1))
					Board[i][j].setPlayer(1);
		for(int i=5;i<8;i++)
			for(int j=0;j<8;j++)
				if((i%2==1)&&(j%2==1))
					Board[i][j].setPlayer(2);
				else if((i==6)&&(j%2==0))
					Board[i][j].setPlayer(2);
	}

	public void initializeBool() {
		for(int i=0;i<canMove.length;i++)
			for(int j=0;j<canMove.length;j++)
				canMove[i][j]=false;
	}

	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(50, 50, 400, 400);
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++) {
				g.setColor(Color.black);
				if((i%2==0)&&(j%2==0)) 
					g.fillRect(j*50+50, i*50+50, 50, 50);
				else if((i%2==1)&&(j%2==1))
					g.fillRect(j*50+50, i*50+50, 50, 50);
				if(Board[i][j].getPlayer()>0) {
					if(Board[i][j].getPlayer()==1) 
						g.setColor(Color.orange);
					else 
						g.setColor(Color.gray);
					g.fillOval(j*50+50, i*50+50, 50, 50); 
					if(Board[i][j].isKing()) {
						g.setColor(Color.red);
						g.fillOval(j*50+63, i*50+63, 25, 25);
					}
				}
			}
		g.setColor(Color.orange);
		for(int i=0;i<p1Count;i++) {
			g.drawArc(0, 605-i*20, 50, 35, 180, 180);
			g.drawArc(0, 625-i*20, 50, 35, 180, 180);
			if(i==p1Count-1)
				g.drawArc(0, 605-i*20, 50, 35, 0, 180);
			g.drawLine(0, 645-i*20, 0, 625-i*20);
			g.drawLine(50, 645-i*20, 50, 625-i*20);
		}
		g.setColor(Color.gray);
		for(int i=0;i<p2Count;i++) {
			g.drawArc(460, 605-i*20, 50, 35, 180, 180);
			g.drawArc(460, 625-i*20, 50, 35, 180, 180);
			if(i==p2Count-1)
				g.drawArc(460, 605-i*20, 50, 35, 0, 180);
			g.drawLine(460, 645-i*20, 460, 625-i*20);
			g.drawLine(510, 645-i*20, 510, 625-i*20);
		}
	}

	public int convertIt(int It) {
		return (It-50)/50; 
	}

	public void Moves() {
		m.setColor(Color.cyan);
		for(int i=0;i<canMove.length;i++)
			for(int j=0;j<canMove[0].length;j++) 
				if(canMove[i][j]) 
					m.fillOval(j*50+50, i*50+50, 50, 50);
	}

	public boolean Player1() {
		for(int i=0;i<Board.length;i++)
			for(int j=0;j<Board[0].length;j++)
				if(Board[i][j].getPlayer()==1)
					return false;
		return true;
	}

	public boolean Player2() {
		for(int i=0;i<Board.length;i++)
			for(int j=0;j<Board[0].length;j++)
				if(Board[i][j].getPlayer()==2)
					return false;
		return true;
	}

	public void checkMoves(int x, int y,int Player) {
		if(Player == 1)
			Player = 2;
		else
			Player = 1;
		initializeBool();
		if((Player == 1)||(Board[x][y].isKing())) {
			if((inBounds(x-1, y-1))&&(Board[x-1][y-1].getPlayer()==0))
				canMove[x-1][y-1] = true;
			if((inBounds(x-1, y+1))&&(Board[x-1][y+1].getPlayer()==0))
				canMove[x-1][y+1] = true;
		}
		if((Player == 2)||(Board[x][y].isKing())) {
			if((inBounds(x+1, y-1))&&(Board[x+1][y-1].getPlayer()==0))
				canMove[x+1][y-1] = true;
			if((inBounds(x+1, y+1))&&(Board[x+1][y+1].getPlayer()==0))
				canMove[x+1][y+1] = true;
		}
		Jumps(x,y,Player);
		Moves();
	}

	public void Jumps(int x, int y, int Player) {
		if((Player == 1)||(Board[x][y].isKing())) {
			if((inBounds(x-1,y-1))&&(Board[x-1][y-1].getPlayer()==Player)&&(inBounds(x-2,y-2))&&(Board[x-2][y-2].getPlayer()==0))
				canMove[x-2][y-2] = true;
			if((inBounds(x-1,y+1))&&(Board[x-1][y+1].getPlayer()==Player)&&(inBounds(x-2,y+2))&&(Board[x-2][y+2].getPlayer()==0))
				canMove[x-2][y+2] = true;
		}
		if((Player == 2)||(Board[x][y].isKing())) {
			if((inBounds(x+1,y-1))&&(Board[x+1][y-1].getPlayer()==Player)&&(inBounds(x+2,y-2))&&(Board[x+2][y-2].getPlayer()==0))
				canMove[x+2][y-2] = true;
			if((inBounds(x+1,y+1))&&(Board[x+1][y+1].getPlayer()==Player)&&(inBounds(x+2,y+2))&&(Board[x+2][y+2].getPlayer()==0))
				canMove[x+2][y+2] = true;
		}
	}

	public boolean move() {
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				if(canMove[i][j])
					return true;
		return false;
	}

	public boolean inBounds(int x, int y) {
		return ((x>=0)&&(x<8)&&(y>=0)&&(y<8));
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		Col = convertIt(arg0.getX());
		Row = convertIt(arg0.getY());
		if(arg0.isMetaDown()) {
			Col = Row = -1;
			Count = 1;
			repaint();
		}
		if(inBounds(Row,Col)) {
			if((Board[Row][Col].getPlayer() == Player)&&(Count == 1)) {
				checkMoves(Row,Col,Player);
				tRow = Row;
				tCol = Col;
				Count++;
			}
			else if((Board[Row][Col].getPlayer()==0)&&(Count==2)&&(canMove[Row][Col])) {
				Board[Row][Col].setPlayer(Board[tRow][tCol].getPlayer());
				Board[Row][Col].setKing(Board[tRow][tCol].isKing());
				Board[tRow][tCol]=new Square();
				for(int i=0;i<Board.length;i++) {
					if(Board[7][i].getPlayer() == 1) {
						if(!Board[7][i].isKing())
							if(p1Count>0)
								p1Count--;
						Board[7][i].setKing(true);
					}
					if(Board[0][i].getPlayer() == 2) {
						if(!Board[0][i].isKing())
							if(p2Count>0)
								p2Count--;
						Board[0][i].setKing(true);
					}
				}
				if(Math.abs(Row-tRow)>1) {
					Board[(Row+tRow)/2][(Col+tCol)/2].setPlayer(0);
					if(Player == 1) 
						p2Count++;
					else
						p1Count++;
					repaint();
					initializeBool();
					if(Player == 1)
						Jumps(Row,Col,2);
					else
						Jumps(Row,Col,1);
					if(move()) {
						tRow= Row;
						tCol=Col;
						Moves();
					}
					else {
						Count = 1;
						Player ++;
						if(Player == 3)
							Player = 1; 
					}
				}
				else {
					Count = 1;
					Player ++;
					if(Player == 3)
						Player = 1; 
					repaint();
					initializeBool();
				}
				if(Player1()) {
					removeMouseListener(this); 
					showStatus("Gray wins!");
				}
				else if(Player2()) {
					removeMouseListener(this); 
					showStatus("Orange wins!");
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}
}