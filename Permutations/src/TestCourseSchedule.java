import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class TestCourseSchedule {

    @Test

    public void test() { 
	int[][] prerequisites = {{1,0},{0,1}};
	//{{0,1},{0,2},{1,2},{2,0}}; false
	//	{{1,0},{2,1}}; true
	int numCourses = 3;
	boolean expected = false;
	CourseSchedule cs = new CourseSchedule(); 

	boolean actual = cs.canFinish(numCourses, prerequisites);
	assertEquals(expected, actual);
    }
}
