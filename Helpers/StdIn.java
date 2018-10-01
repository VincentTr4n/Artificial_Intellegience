package Helpers;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class StdIn {
	final private int BUFFER_SIZE = 1 << 17;
	private DataInputStream din;
	private byte[] buffer;
	private int bufferPointer, bytesRead;

	public StdIn() {
		din = new DataInputStream(System.in);
		buffer = new byte[BUFFER_SIZE];
		bufferPointer = bytesRead = 0;
	}

	public StdIn(InputStream in) {
		try {
			din = new DataInputStream(in);
		} catch (Exception e) {
			throw new RuntimeException();
		}
		buffer = new byte[BUFFER_SIZE];
		bufferPointer = bytesRead = 0;
	}
	
	public StdIn(String fileName) {
		InputStream in;
		try {
			in = new FileInputStream(new File(fileName));
			din = new DataInputStream(in);
		} catch (Exception e) {
			throw new RuntimeException();
		}
		buffer = new byte[BUFFER_SIZE];
		bufferPointer = bytesRead = 0;
	}

	public String next() {
		int c;
		while ((c = read()) != -1 && (c == ' ' || c == '\n' || c == '\r'))
			;
		StringBuilder s = new StringBuilder();
		while (c != -1) {
			if (c == ' ' || c == '\n' || c == '\r')
				break;
			s.append((char) c);
			c = read();
		}
		return s.toString();
	}

	public String nextLine() {
		int c;
		while ((c = read()) != -1 && (c == ' ' || c == '\n' || c == '\r'))
			;
		StringBuilder s = new StringBuilder();
		while (c != -1) {
			if (c == '\n' || c == '\r')
				break;
			s.append((char) c);
			c = read();
		}
		return s.toString();
	}

	public int nextInt() {
		int ret = 0;
		byte c = read();
		while (c <= ' ')
			c = read();
		boolean neg = (c == '-');
		if (neg)
			c = read();
		do
			ret = ret * 10 + c - '0';
		while ((c = read()) >= '0' && c <= '9');

		if (neg)
			return -ret;
		return ret;
	}
	
	public int[][] readIntMatrix(int n, int m){
		int a[][] = new int[n][m];
		for(int i = 0; i < n; ++i)
			for(int j = 0; j < m; ++j) a[i][j] = nextInt();
		return a;
	}

	public int[] readIntArray(int n) {
		int[] ar = new int[n];
		for (int i = 0; i < n; ++i)
			ar[i] = nextInt();
		return ar;
	}

	public long nextLong() {
		long ret = 0;
		byte c = read();
		while (c <= ' ')
			c = read();
		boolean neg = (c == '-');
		if (neg)
			c = read();
		do
			ret = ret * 10 + c - '0';
		while ((c = read()) >= '0' && c <= '9');
		if (neg)
			return -ret;
		return ret;
	}

	public long[] readLongArray(int n) {
		long[] ar = new long[n];
		for (int i = 0; i < n; ++i)
			ar[i] = nextLong();
		return ar;
	}

	public double nextDouble() {
		double ret = 0, div = 1;
		byte c = read();
		while (c <= ' ')
			c = read();
		boolean neg = (c == '-');
		if (neg)
			c = read();
		do
			ret = ret * 10 + c - '0';
		while ((c = read()) >= '0' && c <= '9');
		if (c == '.')
			while ((c = read()) >= '0' && c <= '9')
				ret += (c - '0') / (div *= 10);
		if (neg)
			return -ret;
		return ret;
	}

	private void fillBuffer() throws IOException {
		bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
		if (bytesRead == -1)
			buffer[0] = -1;
	}

	private byte read() {
		try {
			if (bufferPointer == bytesRead)
				fillBuffer();
			return buffer[bufferPointer++];
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	public void close() throws IOException {
		if (din == null)
			return;
		din.close();
	}
}