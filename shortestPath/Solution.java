package shortestPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import Helpers.StdIn;

public class Solution {
	public static HashMap<String, Integer> map = new HashMap<>();
	public static String cities[];
	public static int n;
	public static int a[][];
	public static int dis[];
	public static int parent[];
	
	public static void Uniform_Search(int s, int e) {
		PriorityQueue<Node> q = new PriorityQueue<>();
		q.add(new Node(0, s, new ArrayList<>()));
		
		while(!q.isEmpty()) {
			Node cur = q.poll();
			int d = cur.W;
			int u = cur.X;
			ArrayList<Integer> list = cur.path;
			
			if(u == e) {
				System.out.println("OK");
				System.out.print(cities[s]);
				for(int x : list) System.out.print(" -> " + cities[x]);
				System.out.println(" || Total cost : " + d);
				return;
			}
			
			for(int v = 1; v <= n; ++v) if(a[u][v] > 0) {
				ArrayList<Integer> tmp = new ArrayList<>();
				tmp.addAll(list);
				tmp.add(v);
				q.add(new Node(a[u][v] + d, v, tmp));
			}
		}
	}
	
	public static void Greendy_Search(int s, int e) {
		boolean vis[] = new boolean[n + 1];
		PriorityQueue<Node> q = new PriorityQueue<>();
		q.add(new Node(dis[s], s, new ArrayList<>()));
		vis[s] = true;
		
		while(!q.isEmpty()) {
			Node cur = q.poll();
			int u = cur.X;
			ArrayList<Integer> list = cur.path;
			
			if(u == e) {
				System.out.println("OK");
				System.out.print(cities[s]);
				int total = 0;
				for(int x : list) {
					System.out.print(" -> " + cities[x]);
					total += a[s][x];
					s = x;
				}
				System.out.println(" || Total cost : " + total);
				return;
			}
			
			for(int v = 1; v <= n; ++v) if(a[u][v] > 0 && !vis[v]) {
				ArrayList<Integer> tmp = new ArrayList<>();
				tmp.addAll(list);
				tmp.add(v);
				vis[v] = true;
				q.add(new Node(dis[v], v, tmp));
			}
		}
	}
	
	public static void A_Star_Search(int s, int e) {
		boolean vis[] = new boolean[n + 1];
		PriorityQueue<Node> q = new PriorityQueue<>();
		q.add(new Node(dis[s], s, new ArrayList<>()));
		vis[s] = true;
		
		while(!q.isEmpty()) {
			Node cur = q.poll();
			int u = cur.X;
			ArrayList<Integer> list = cur.path;
			
			if(u == e) {
				System.out.println("OK");
				System.out.print(cities[s]);
				int total = 0;
				for(int x : list) {
					System.out.print(" -> " + cities[x]);
					total += a[s][x];
					s = x;
				}
				System.out.println(" || Total cost : " + total);
				return;
			}
			
			for(int v = 1; v <= n; ++v) if(a[u][v] > 0 && !vis[v]) {
				ArrayList<Integer> tmp = new ArrayList<>();
				tmp.addAll(list);
				tmp.add(v);
				vis[v] = true;
				q.add(new Node(dis[v] + a[u][v], v, tmp));
			}
		}
	}

	public static void main(String[] args) throws Exception{
		init();
		String start = "Arad";
		String end = "Bucharest";
		Uniform_Search(map.get(start), map.get(end));
		Greendy_Search(map.get(start), map.get(end));
		A_Star_Search(map.get(start), map.get(end));
	}
	
	public static void printPath(int cur) {
		if(parent[cur] == 0) return;
		System.out.println(cur);
		printPath(parent[cur]);
	}
	
	public static class Node implements Comparable<Node>{
		public int W;
		public int X;
		public ArrayList<Integer> path;
		public Node() {}
		public Node(int w, int x, ArrayList<Integer> list) {
			W = w;
			X = x;
			path = list; 
		}
		
		@Override
		public int compareTo(Node o) {
			return W - o.W;
		}
	}

	public static void init() {
		StdIn in = new StdIn("src/shortestPath/in.txt");
		n = in.nextInt();
		a = new int[n + 1][n + 1];
		cities = new String[n + 1];
		dis = new int[n + 1];
		for(int i = 0; i < n; ++i) {
			int id = in.nextInt();
			String s = in.next();
			int d = in.nextInt();
			map.put(s, id);
			cities[id] = s;
			dis[id] = d;
		}
		int m = in.nextInt();
		for(int i = 0; i < m; ++i) {
			String s1 = in.next();
			String s2 = in.next();
			int d = in.nextInt();
			int u = map.get(s1);
			int v = map.get(s2);
			a[u][v] = d;
			a[v][u] = d;
		}
	}
}
