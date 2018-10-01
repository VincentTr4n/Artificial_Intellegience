package GameTheory;

import java.util.ArrayList;
import java.util.List;

public class DodgemBasic {

	public static void main(String[] args) throws Exception{
		int a[][] = { { 1, 0, 0 },
					  { 1, 0, 0 },
					  { 0, 2, 2 }};
		
		Game begin = new Game(a, 1);
		
		while(begin.gameOver() == 0) {
			Game res = RunMinMax(begin, 5);
			System.out.println(res);
			begin = res;
		}
		
		System.out.println(begin.gameOver() == 1 ? "Den thang" : "Trang thang");
	}
	
	static Game RunMinMax(Game game, int dept) {
		Game res = null;
		Game tmp1 = null;
		
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		int val = game.player == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
		
		System.out.print(game.getPlayer() + "co diem : [");
		List<int[]> list = game.getLocal(game.player);
		
		for(int x[] : list) {
			int move[][] = game.player == 1 ? Game.MOVE_BLACK : Game.MOVE_WHITE;
			for(int i = 0; i < 3; ++i) {
				
				Game tmp = new Game(game.a, game.player);
				int x1 = x[0], y1 = x[1], x2 = x1 + move[i][0], y2 = y1 + move[i][1];
				
				if(tmp.move(x1, y1, x2, y2)) {
					tmp.player = game.player == 1 ? 2 : 1;
					tmp1 = new Game(tmp.a, tmp.player);
					
					int sr = game.player == 1 ? MinValue(tmp, dept - 1, alpha, beta) : MaxValue(tmp, dept - 1, alpha, beta);
					System.out.print(sr + " ");
					
					if(game.player == 1 && sr <= val) {
						val = sr;
						res = tmp1;
					}
					else if(game.player == 2 && sr >= val) {
						val = sr;
						res = tmp1;
					}
				}
			}
		}
		System.out.println(" ]");
		System.out.println(game.getPlayer() + "chon diem toi uu la : " + val);
		return res;
	}
	
	static int MinValue(Game game, int dept, int alpha, int beta) {
		if(game.gameOver() != 0 || dept == 0) return game.get();
		
		int min = Integer.MAX_VALUE;
		
		List<int[]> list = game.getLocal(game.player);
		for(int x[] : list) {
			int move[][] = game.player == 1 ? Game.MOVE_BLACK : Game.MOVE_WHITE;
			
			for(int i = 0; i < 3; ++i) {
				Game tmp = new Game(game.a, game.player);
				int x1 = x[0], y1 = x[1], x2 = x1 + move[i][0], y2 = y1 + move[i][1];
				
				if(tmp.move(x1, y1, x2, y2)) {
					tmp.player = game.player == 1 ? 2 : 1;
					min = Math.min(min, MaxValue(tmp, dept - 1, alpha, beta));
					//if(val < min) min = val;
					if(beta < min) beta = min;
					if(alpha >= beta) break;
				}
			}
		}
		return min;
	}
	
	static int MaxValue(Game game, int dept, int alpha, int beta) {
		if(game.gameOver() != 0 || dept == 0) return game.get();
		
		int max = Integer.MIN_VALUE;
		
		List<int[]> list = game.getLocal(game.player);
		for(int x[] : list) {
			int move[][] = game.player == 1 ? Game.MOVE_BLACK : Game.MOVE_WHITE;
			
			for(int i = 0; i < 3; ++i) {
				Game tmp = new Game(game.a, game.player);
				int x1 = x[0], y1 = x[1], x2 = x1 + move[i][0], y2 = y1 + move[i][1];
				
				if(tmp.move(x1, y1, x2, y2)) {
					tmp.player = game.player == 1 ? 2 : 1;
					max = Math.max(max, MinValue(tmp, dept - 1, alpha, beta));
					//if(val > max) max = val;
					if(alpha > max) alpha = max;
					if(alpha >= beta) break;
				}
			}
		}
		return max;
	}
	
	static class Game{
		public int a[][] = new int[3][3];
		public int player;		// 1 la den, 2 la trang
		
		// Black can move UP, RIGHT, DOWN
		// White can move LEFT, UP, RIGHT
		static int MOVE_BLACK[][] = { { 0, 1 }, { -1, 0 }, { 1, 0 } };
		static int MOVE_WHITE[][] = { { -1, 0 }, { 0, 1 }, { 0, -1 } };
		
		static int DATA_BLACK[][] = { { -10, -25, -40 }, { -5, -20, -35 }, { 0, -15, -30 } };
		static int DATA_WHITE[][] = { { 30, 35, 40 }, { 15, 20, 25 }, { 0, 5, 10 } };
		
		public Game(int p) { player = p; };
		public Game(int b[][], int p) {
			for(int i = 0; i < 3; ++i) for(int j = 0; j < 3; ++j) a[i][j] = b[i][j];
			player = p;
		}
		
		public boolean canMove(int x, int y) {
			if(canGetOut(x, y)) return true;
			if(player == 1) {
				boolean ok = false;
				for(int i = 0; i < 3; ++i) {
					int x1 = x + MOVE_BLACK[i][0];
					int y1 = y + MOVE_BLACK[i][1];
					if(x1 >= 0 && x1 < 3 && y1 >= 0 && y1 < 3 && a[x1][y1] == 0) ok = true;
				}
				return ok;
			}
			else {
				boolean ok = false;
				for(int i = 0; i < 3; ++i) {
					int x1 = x + MOVE_WHITE[i][0];
					int y1 = y + MOVE_WHITE[i][1];
					if(x1 >= 0 && x1 < 3 && y1 >= 0 && y1 < 3 && a[x1][y1] == 0) ok = true;
				}
				return ok;
			}
		}
		
		public boolean move(int x1, int y1, int x2, int y2) {
			if(canGetOut(x1, y1)) {
				a[x1][y1] = 0;
				return true;
			}
			
			if(x2 < 0 || y2 >= 3 || x2 >= 3 || y2 < 0) return false;
			if(a[x2][y2] == 0) {
				a[x2][y2] = a[x1][y1];
				a[x1][y1] = 0;
				return true;
			}
			return false;
		}
		
		public boolean canGetOut(int x, int y) {
			if(player == 1) return y == 2 && (x >= 0 && x < 3);
			return x == 0 && (y >= 0 && y < 3);
		}
		
		public List<int []> getLocal(int u){
			List<int []> list = new ArrayList<>();
			for(int i = 0; i < 3; ++i)
				for(int j = 0; j < 3; ++j) if(a[i][j] == u) {
					int x[] = new int[2];
					x[0] = i;
					x[1] = j;
					list.add(x);
				}
			return list;
		}
		
		// 1 hoac 2 la win, 0 la chua ket thuc
		public int gameOver() {
			List<int[]> list = getLocal(player);
			List<int[]> list1 = getLocal(player == 1 ? 2 : 1);
			if(list.size() == 0) return player;
			if(list1.size() == 0) return player == 1 ? 2 : 1;
			boolean ok = false;
			for(int x[] : list) if(canMove(x[0], x[1])) ok = true;
			if(!ok) return player == 1 ? 2 : 1;
			return 0;
		}
		
		public int get() {
			int s = gameOver();
			if(s != 0) return s == 1 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
			int total = 0;
			for(int i = 0; i < 3; ++i)
				for(int j = 0; j < 3; ++j) {
					if(a[i][j] == 1) total += DATA_BLACK[i][j] + getPointBlock(i, j, 1);
					else if(a[i][j] == 2) total += DATA_WHITE[i][j] + getPointBlock(i, j, 2);
				}
			
			return total;
		}
		
		public int getPointBlock(int x, int y, int s) {
			int res = 0;
			for(int k = 1; k <= 2; ++k) {
				int dx = s == 1 ? 0 : -1;
				int dy = s == 1 ? 1 : 0;
				int x1 = x + k * dx, y1 = y + k * dy;
				if(x1 < 0 || x1 >= 3 || y1 < 0 || y1 >= 3 || a[x1][y1] == 0) continue;
				if(a[x1][y1] != s) res += k == 1 ? 40 : 30;	
			}
			return s == 1 ? res : -res;
		}
		
		public String getPlayer() {
			return player == 1 ? "Den " : "Trang ";
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < 3; ++i) {
				String s = "";
				for(int j = 0; j < 3; ++j) s += (a[i][j] == 1) ? "X " : (a[i][j] == 2) ? "O " : "- ";
				s.trim();
				sb.append(s + "\n");
			}
			return sb.toString();
		}
	}
}
