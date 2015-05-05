import java.util.HashSet;


public class Problem1 {
	public char[][] generate (int m, int n){
		char[][] result = new char[m][n];
		for (int i = 0; i < m; i++){
			for (int j = 0; j < n; j++){
				if (Math.random() >= 0.5)
					result[i][j] = '1';
				else
					result[i][j] = '0';
			}
		}
		return result;
	}
	
	public int count(char[][] grid) {
		if (grid.length == 0 || grid[0].length == 0)
			return 0;
		int row = grid.length;
		int column = grid[0].length;
		int length = row * column;
        int[] result = new int[length];
        for (int i = 0; i < length; i++){
        	if (grid[i/column][i - i/column * column] == '1')
        		result[i] = i;
        	else
        		result[i] = length;
        }
        
        System.out.println();
        for (int i = 0; i < length; i++){
        	int x = i/column;
        	int y = i - x * column;
        	if (i >= column && grid[x][y] == grid[x - 1][y] && grid[x][y] == '1'){
        		int root1 = findroot(result, i); 
        		int root2 = findroot(result, i - column);
        		result[root1] = root2;
        		result[i] = root2;
        	}
        	if (y != 0 && grid[x][y] == grid[x][y - 1] && grid[x][y] == '1'){
        		int root1 = findroot(result, i); 
        		int root2 = findroot(result, i - 1);
        		result[root1] = root2;
        		result[i] = root2;
        	}
        }
        
        
        for (int i = 0; i < length; i++){
        	int x = i/column;
        	int y = i - x * column;
        	if (grid[x][y] == '1')
        		result[i] = findroot(result, i);       	
        }
        
        HashSet<Integer> h = new HashSet<Integer>();
        for (int i = 0; i < length; i++){
        	if (result[i] < length)
        		h.add(result[i]);
        }
        return h.size();
    }
	
	public int findroot (int[] a, int n){
		int result = n;
		while (result != a[result]){
			result = a[result];
		}
		return result;
	}
	
	public static void main(String[] args){
		Problem1 a = new Problem1();
		System.out.println(a.count(a.generate(10, 10)));
	}
}
