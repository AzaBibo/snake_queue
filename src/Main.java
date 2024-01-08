import java.util.LinkedList;
import java.util.Queue;
import java.util.HashSet;
import java.util.Random;
import Pointer;

public class Snake {
	
	int[][] map = new int[22][10]; // initial map, which we will modify
	int[][] final_map = new int[22][10]; // final map
	int step = 220; // to find the least path 
	
	public static void printing(int[][] map) { // Printing the desk
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
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
		
		Queue<Queue_Try> q = new LinkedList<>();
		HashSet<String> hs = new HashSet<String>(); // to store previously visited coordinations
		
		q.add(new Pointer(hy, hx, 0, map)); // add the first coordinate to the queue

		while(!q.empty()) {
			Pointer temp = q.remove();

			for(int i = 0; i < 4; i++) {
				if((temp.x + dx[i] < 10 && temp.x + dx[i] >= 0) && (temp.y + dy[i] >= 0 && temp.y + dy[i] < 22) && map[temp.y + dy[i]][temp.x + dx[i]] != 3 ) {
					Pointer current = new Pointer(temp.y + dy[i], temp.x + dx[i], temp.steps + 1, map);

					if(map[current.y][current.x] == 4 && current.y == ty && current.x == tx) { // if the current is the target 
						if(current.steps < steps) {
							current.map[current.y][current.x] = 2;
							final_map = current.map;
							steps = current.steps;
						}
					}
					else if(!hs.contains(String.valueOf(current.y) + "-" + String.valueOf(current.x))) { // the current Pointer has not been visited before, and it's not the target 
						current.map[current.y][current.x] = 2;
						q.add(current);
						hs.add(String.valueOf(current.y) + "-" + String.valueOf(current.x)); // store the coordinates of the current node, mark it as "visited"
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		Snake engine = new Snake();
		
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
		engine.engine(8, 1, 4, 5, 5, 1, 6, 2, 7, 2);
		
		// Printing the final matrix
		System.out.println("\n===================\n");
		System.out.println("Printing the final matrix");
		printing(engine.final_map);
		System.out.println("\nStep: " + engine.step);
	}

}