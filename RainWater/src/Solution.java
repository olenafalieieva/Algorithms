import java.util.Stack;


public class Solution {
    public static int trap(int[] A) {  
	Stack<Integer> stack = new Stack<Integer>();  
	int sum = 0;  
	int pre = 0;  
	int i = -1;  
	while(++i < A.length){  

	    if(A[i]==0){
		pre = 0;
		continue;
		}  
	    while(!stack.isEmpty() && A[i] >= A[stack.peek()]){  
		int temp = A[stack.peek()];
		sum += (A[stack.peek()] - pre) * (i-stack.peek()-1);  
		pre = A[stack.pop()];  
	    }  
	    if(!stack.isEmpty()){  
		int temp2 = (i-stack.peek()-1);
		sum += (A[i] - pre) * (i-stack.peek()-1);  
		pre = A[i];  
	    }  
	    stack.push(i);  
	}  
	return sum;  
    }  
}
