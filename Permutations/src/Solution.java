import java.util.ArrayList;
import java.util.List;
//A permutation can be obtained by selecting an element in the given set and recursively permuting the remaining elements

public class Solution {
    public static List<List<Integer>> permute(int[] nums) {
	List<List<Integer>> list = new ArrayList<>(); 
	permute(nums, 0, list);

	return list;
    }

    static void permute(int[] nums, int i, List<List<Integer>> list) {         
	int j = 0;
	int len = nums.length;

	for (j = i; j < len; j++) {
	    swap(nums, i, j);
	    permute(nums, i + 1, list);
	    swap(nums, i, j);
	}
	
	if ((i == j) && (j == len)) { //write to result list, when recursion bottom is reached
	    List<Integer> part = toList(nums);
	    list.add(part);
	}
    }   

    private static void swap(int[] A, int i, int j){
	int temp =  A[i];
	A[i] = A[j];
	A[j] = temp;
    }

    private static List toList (int[] nums) {
	List<Integer> result = new ArrayList<>();
	int len = nums.length;
	for (int i = 0; i < len; i++) {
	    result.add(nums[i]);
	}

	return result;
    }
}
