
public class CourseSchedule {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
	boolean[] visited = new boolean[numCourses];
	int k = 0;
	return dfs(visited, prerequisites, k); 

	//return true;
    }

    private boolean dfs(boolean[] visited, int[][] prerequisites, int k) {
	int len = prerequisites.length;

	for (int i = k; i < len; i++) {

	    int v = prerequisites[i][0];
	    visited[v] = true;

	    int w = prerequisites[i][1];

	    if ((!visited[w]) && (find(prerequisites, w, i) != 0)) {
		k =  find(prerequisites, w, i);
		dfs(visited, prerequisites, k);
		
		
	    }
	     else if ((visited[w]) && (find(prerequisites, w, 0) != 0)) {
		return false;
	    } 
	}

	return true;
    }

    private boolean isLoop(int v, int w) {
	if (v == w) {
	    return true;
}
	return false;
	
    }

    private int find(int[][] prerequisites, int w, int i) {
	int len = prerequisites.length;
	for (int j = i + 1; j < len; j++) {
	    if (prerequisites[j][0] == w) {
		return j;
	    }
	}

	return 0;
    } 
}