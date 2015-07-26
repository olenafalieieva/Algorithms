import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;


public class Test1 {

    @Test
    public void test() { 
	int[] height = {1,2,3};
	
	int[][] expected = {{1,2,3},{1,3,2},{2,1,3},{2,3,1},{3,1,2},{3,2,1}};
	List<List<Integer>> actual = Solution.permute(height);
	assertEquals(expected, actual);
    }

}
