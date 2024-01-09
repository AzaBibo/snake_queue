import java.util.LinkedList;
import java.util.Queue;
import java.util.HashSet;
import java.util.Random;

public class Main {
	
	int[][] map = new int[22][10]; // initial map, which we will modify
	int step = 220; // to find the least path 
	int total_paths = 0;
	
	public static void printing(int[][] map) { // Printing the desk
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static int[][] deepCopy(int[][] original) {
		if (original == null) {
			return null;
		}
	
		int[][] copy = new int[original.length][];
		for (int i = 0; i < original.length; i++) {
			// Manual deep copy for each subarray (row)
			copy[i] = new int[original[i].length];
			System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
		}
	
		return copy;
	}
	
	
	public void engine(int tx, int ty, int hx, int hy, int ox1, int oy1, int ox2, int oy2, int ox3, int oy3) {
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = 0;
			}
		}

		
		map[ty][tx] = 4; // target
		map[hy][hx] = 1; // worm head
		map[oy1][ox1] = 3; // obstacle 1
		map[oy2][ox2] = 3; // obstacle 2
		map[oy3][ox3] = 3; // obstacle 3
		
		// Printing the initial matrix
		System.out.println("Printing the initial matrix");
		printing(map);

		int[] dx = {0, 0, 1, -1};
		int[] dy = {1, -1, 0, 0};
		
		Queue<Pointer> q = new LinkedList<>();
		HashSet<String> hs = new HashSet<String>(); // to store previously visited coordinations
		int[][] final_map = null; // final map
		
		q.add(new Pointer(hy, hx, 0, map)); // add the first coordinate to the queue

		while(!q.isEmpty()) {
			Pointer temp = q.poll();

			for(int i = 0; i < 4; i++) {
				if((temp.x + dx[i] < 10 && temp.x + dx[i] >= 0) && (temp.y + dy[i] >= 0 && temp.y + dy[i] < 22) && map[temp.y + dy[i]][temp.x + dx[i]] != 3 ) {
					Pointer current = new Pointer(temp.y + dy[i], temp.x + dx[i], temp.steps + 1, deepCopy(temp.desk));

					if(current.desk[current.y][current.x] == 4 && current.y == ty && current.x == tx) { // if the current is the target 
						total_paths++;
						if(current.steps < step) {
							final_map = deepCopy(current.desk);
							step = current.steps;
						}
					}
					else if(!hs.contains(String.valueOf(current.y) + "-" + String.valueOf(current.x))) { // the current Pointer has not been visited before, and it's not the target 
						if(current.desk[current.y][current.x] != 1) current.desk[current.y][current.x] = 2;
						q.add(current);
						hs.add(String.valueOf(current.y) + "-" + String.valueOf(current.x)); // store the coordinates of the current node, mark it as "visited"
					}
				}
			}
		}	
				
		// Printing the final matrix
		System.out.println("\n===================\n");
		System.out.println("Printing the final matrix");
		printing(final_map);
		System.out.println("\nTotal number of paths: " + total_paths);
		System.out.println("Shortest one took " + step + " steps");
	}

	public static void main(String[] args) {
		Main engine = new Main();
		
		Random rand = new Random();
		int tx = rand.nextInt(0, 10), ty = rand.nextInt(0, 22);
		int hx, hy;
		
		while(true) {
			hx = rand.nextInt(0, 10);
			hy = rand.nextInt(0, 22);
			
			if((hy != ty && hx != tx)) break;
		} 
		
		int ox1, oy1, ox2, oy2, ox3, oy3;
		while(true) {
			ox1 = rand.nextInt(0, 10);
			oy1 = rand.nextInt(0, 20);

			if(oy1 != ty && oy1 == hy && ox1 != tx && ox1 != hx) break;
		} 
		
		while(true) {
			ox2 = rand.nextInt(0, 10);
			oy2 = rand.nextInt(0, 20);

			if(oy2 != ty && oy2 == hy && ox2 != tx && ox2 != hx) break;
		} 
		
		while(true) {
			ox3 = rand.nextInt(0, 10);
			oy3 = rand.nextInt(0, 20);
		
			if(oy3 != ty && oy3 != hy && ox3 != tx && ox3 != hx) break;
		} 
		
		//engine.engine(tx, ty, hx, hy, ox1, oy1, ox2, oy2, ox3, oy3);
		engine.engine(8, 1, 4, 5, 8, 2, 9, 2, 6, 1);
	}
}