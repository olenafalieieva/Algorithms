package leetcode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestDFS {
    
    @Test
    public void test() {
	int[][] prerequisites = {{1,0},{2,6},{1,7},{5,1},{6,4},{7,0},{0,5},{5,1},{6,4}};
	    //{{1,0},{2,3},{3,0}}; true
	    //{{0,2},{2,3},{1,3},{1,2},{1,0}}; false
	    //{{0,3},{3,1},{3,2},{2,1}}; true
	    //{{0,2},{2,3},{1,3},{1,2},{1,0}}; false
	//{{0,1},{0,2},{1,2},{2,0}}; false
	//	{{1,0},{2,1}}; true
	int numCourses = 8;
	boolean expected = false;
	CourseSchedule cs = new CourseSchedule(); 

	boolean actual = cs.canFinish(numCourses, prerequisites);
	assertEquals(expected, actual);
 
    }
}