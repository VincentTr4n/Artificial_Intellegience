package BaiToanQuaSong;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Solution {
	
    public static HashMap<State, State> map1 = new HashMap<>();
    public static HashMap<State, State> map2 = new HashMap<>();
    public static HashMap<State, State> map3 = new HashMap<>();
    public static HashMap<State, State> map4 = new HashMap<>();
    public static HashMap<State, Integer> vis = new HashMap<>();
    public static State NULL = new State(0, 0, 0);
    
    static int[] dx = {2 , 0 , 1 , 0 , 1 };
    static int[] dy = {0 , 2 , 1 , 1 , 0 };

    public static boolean check(State st) {
        if (st.getA() != 0 && st.getB() != 0) { 
            if (st.getA() >= st.getB()) return true;
        } else if (st.getA() == 0 || st.getB() == 0) return true;
        return false;
    }
    
    public static boolean checkAll(State st1, State st2) {
        return (check(st2) && check(st1) && st1.getA() >= 0 && st1.getB() >= 0 && st2.getA() >= 0 && st2.getB() >= 0);
    }
    
    public static void bfs(State begin, State end) {
    	Queue<State> queue = new LinkedList<State>();
    	Queue<State> pairs = new LinkedList<State>();
    	
    	queue.add(begin);
    	pairs.add(NULL);
    	
    	map1.put(begin, NULL);
    	
    	while(!queue.isEmpty()) { 
    		State s1 = queue.poll();
    		State s2 = pairs.poll();
    		
    		if(s1.equals(end)) {
    			System.out.println("OK");
    			return;
    		}
    		
    		int c = s1.getC();
    		
    		for(int i = 0; i < dx.length; ++i) {
    			State tmp1 = new State(s2.getA() + dx[i], s2.getB() + dy[i], c == 0 ? 1 : 0); 
                State tmp2 = new State(s1.getA() - dx[i], s1.getB() - dy[i], c); 
                
                if(checkAll(tmp1, tmp2) && !map1.containsKey(tmp1)) {
                	queue.add(tmp1); pairs.add(tmp2);
                	map1.put(tmp1, s1);
                }
    		}
    	}
    }
    
    public static void dfs(State cur, State pre, State end) {
    	if(cur.equals(end)) System.out.println("OK");
    	vis.put(cur, 1);
    	int c = cur.getC();
    	for(int i = 0; i < dx.length; ++i) {
			State tmp1 = new State(pre.getA() + dx[i], pre.getB() + dy[i], c == 0 ? 1 : 0); 
            State tmp2 = new State(cur.getA() - dx[i], cur.getB() - dy[i], c); 
            if(checkAll(tmp1, tmp2) && !vis.containsKey(tmp1)) {
            	map2.put(tmp1, cur);
            	dfs(tmp1, tmp2, end);
            }
		}
    }
    
    public static void bfs_priority(State begin, State end) {
    	PriorityQueue<MyData> queue = new PriorityQueue<>();
    	queue.add(new MyData(begin, NULL));
    	map3.put(begin, NULL);
    	
    	while(!queue.isEmpty()) {
    		MyData data = queue.poll();
    		State s1 = data.state;
    		State s2 = data.pair;
    		
    		if(s1.equals(end)) {
    			System.out.println("OK");
    			return;
    		}
    		
    		int c = s1.getC();
    		
    		for(int i = 0; i < dx.length; ++i) {
    			State tmp1 = new State(s2.getA() + dx[i], s2.getB() + dy[i], c == 0 ? 1 : 0); 
                State tmp2 = new State(s1.getA() - dx[i], s1.getB() - dy[i], c); 
                
                if(checkAll(tmp1, tmp2) && !map3.containsKey(tmp1)) {
                	queue.add(new MyData(tmp1, tmp2)); 
                	map3.put(tmp1, s1);
                }
    		}
    	}
    }
    
    public static void hill_climb(State begin, State end) {
    	Stack<MyData> st = new Stack<>();
    	st.push(new MyData(begin, NULL));
    	map4.put(begin, NULL);
    	
    	while(!st.isEmpty()) {
    		MyData data = st.pop();
    		State s1 = data.state;
    		State s2 = data.pair;
    		
    		if(s1.equals(end)) {
    			System.out.println("OK");
    			return;
    		}
    		
    		int c = s1.getC();
    		
    		List<MyData> list = new ArrayList<MyData>();
    		for(int i = 0; i < dx.length; ++i) {
    			State tmp1 = new State(s2.getA() + dx[i], s2.getB() + dy[i], c == 0 ? 1 : 0); 
                State tmp2 = new State(s1.getA() - dx[i], s1.getB() - dy[i], c); 
                
                if(checkAll(tmp1, tmp2) && !map4.containsKey(tmp1)) {
                	list.add(new MyData(tmp1, tmp2)); 
                	map4.put(tmp1, s1);
                }
    		}
    		
    		Collections.sort(list);
    		Collections.reverse(list);
    		for(MyData item : list) st.push(item);
    	}
    }
    
    public static void printSolution(State cur, HashMap<State, State> map) {
    	if(!map.containsKey(cur)) return;
    	if(map.get(cur) == null) return;
    	printSolution(map.get(cur), map);
    	System.out.println(cur.toString());
    }
    

    public static void main(String[] args) {
    	State begin = new State(3, 3, 1);
    	State end = new State(3, 3, 0);
        run_bfs(begin, end);
        run_dfs(begin, end);
        run_bfs_priority(begin, end);
        run_hill_climb(begin, end);
    }
    
    public static void run_bfs(State begin, State end) {
    	long s = System.currentTimeMillis();
    	bfs(begin, end);
    	printSolution(end, map1);
    	System.out.println((System.currentTimeMillis() - s) / 1000d + "s");
    }
    
    public static void run_dfs(State begin, State end) {
    	long s = System.currentTimeMillis();
    	map2.put(begin, NULL);
    	dfs(begin, NULL, end);
    	printSolution(end, map2);
    	System.out.println((System.currentTimeMillis() - s) / 1000d + "s");
    }
    
    public static void run_bfs_priority(State begin, State end) {
    	long s = System.currentTimeMillis();
    	bfs_priority(begin, end);
    	printSolution(end, map3);
    	System.out.println((System.currentTimeMillis() - s) / 1000d + "s");
    }
    
    public static void run_hill_climb(State begin, State end) {
    	long s = System.currentTimeMillis();
    	hill_climb(begin, end);
    	printSolution(end, map4);
    	System.out.println((System.currentTimeMillis() - s) / 1000d + "s");
    }
    
    public static class MyData implements Comparable<MyData>{
    	public int h;
    	public State state;
    	public State pair;
    	public MyData() {}
    	public MyData(State cur, State p) {
    		state = cur;
    		pair = p;
    		h = cur.getVal();
    	}
		@Override
		public int compareTo(MyData o) {
			return h - o.h;
		}
    }
    
    public static class State {
        private int a;
        private int b;
        private int c;

        public State() {
        }


        public State(int A, int B, int C) {
            this.a = A;
            this.b = B;
            this.c = C;
        }

        public int getA() {
            return a;
        }

        public void setA(int T) {
            this.a = T;
        }

        public int getB() {
            return b;
        }

        public void setB(int B) {
            this.b = B;
        }

        public int getC() {
            return c;
        }

        public void setC(int C) {
            this.c = C;
        }
        
        public int getVal() {
        	int c = getC();
        	int a = c == 1 ? getA() : 3 - getA();
        	int b = c == 1 ? getB() : 3 - getB();
        	return a + b;
        }
        
        @Override
        public int hashCode() {
        	return this.a * 23 + this.b * 31 + this.c * 37;
        }

        @Override
        public boolean equals(Object obj) {
            if(this == obj) {
                return true;
            } else if(obj == null) {
                return false;
            }else if(obj instanceof State ) {
                State st = (State) obj;
                if(st.getC() == c && st.getB() == b && st.getA() == a) {
                    return true;
                }
            }
            return false;
        }
       
        
        @Override
        public String toString() {
        	return "( " + (c == 0 ? 3 - a : a) + " , " + (c == 0 ? 3 - b : b) + " , " + c + " )";
        }
    }
}