package leetcode;


// dfs 
public class CourseSchedule {

    public boolean canFinish(int numCourses, int[][] precourse) {
	int[] visited = new int[numCourses];
	//	Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
	//	for (int j = 0; j < numCourses; j++) {
	//	    for (int i = 0; i < precourse.length; i++) {
	//		if 
	//	    }
	//	    map.put(precourse[i][0], precourse[i][1]);
	//	}
	return dfs(precourse,  visited, 0);

    }

    private boolean dfs(int[][] precourse, int[] visited, int j) {
	int len = precourse.length;
	for (int i = j; i < len; i++) {

	    int v = precourse[i][0];
	    int w = precourse[i][1];

	    if (visited[w] != 1) {
		j = find(precourse, w, i);
		if ((j != -1) && (j < len)) {
		    dfs(precourse,  visited, j);
		    visited[w] = 2;
		} else if ((j == -1) && (j < len)) {
		    visited[w] = 2;
		} 
		j = find(precourse, v, i);
		if ((j == -1) && (j < len)) {
		    visited[v] = 2; 
		} 
	    } else if (visited[w] == 1) {
		return false;
	    }

	    if (visited[v] != 2) {
		visited[v] = 1;
	    }
	}

	return true;
    }

    private int find(int[][] prerequisites, int w, int i) {
	int len = prerequisites.length;
	for (int j = i + 1; j < len; j++) {
	    if (prerequisites[j][0] == w) {
		return j;
	    }
	}
	return -1;
    }
}