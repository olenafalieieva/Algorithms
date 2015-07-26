import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class TestSolution {

    @Test
    public void test() { 
	int[] height = {4,2,3};
	    //{0,2,0}; 0
	    //{2, 0, 2}; 2
	int expected = 1;
	int actual = Solution.trap(height);
	assertEquals(expected, actual);
    }

}
