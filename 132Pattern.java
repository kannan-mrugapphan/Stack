// 456.
// i,j,k need not be contiguous
//time - O(n)
// space - O(n)
class Solution {
    public boolean find132pattern(int[] nums) {
        //brute force - 3 nested for loops for i,j,k check if nums[i] < nums[k] < nums[j] for any i,j,k -> O(n^3)
        
        //brute force - 2 nested for loops for j,k, find j,k such that nums[k] < nums[j] -> find i between 0 and j - 1 such that nums[i] < nums[k] -> best possible i is the minimum number in nums[] from 0,..j - 1 -> O(n^2)
        
        //approach - 1 loop to find j, check if minimum in nums[] between 0,..j - 1 is less than nums[j] -> if so an i such that nums[i] < nums[j] and i < j is found -> find k from j + ,.., n such that nums[i] < nums[k] < nums[j] -> best possible answer is max in nums[] from j + 1,...,n -> O(n)
        
        //edge
        if(nums == null || nums.length == 0)
        {
            return false;
        }
        
        int[] minimum = new int[nums.length]; //each index i tracks min in nums[] from 0 to i
        minimum[0] = nums[0];
        for(int i = 1; i < nums.length; i++)
        {
            minimum[i] = Math.min(minimum[i - 1], nums[i]);
        }
        
        Stack<Integer> maximum = new Stack<>();
        for(int j = nums.length - 1; j > 0; j--)
        {
            int numsI = minimum[j - 1]; //minimum number from 0 to j - 1
            //as long as stack top is smaller than nums[j]
            while(!maximum.isEmpty() && nums[maximum.peek()] < nums[j])
            {
                if(numsI < nums[maximum.peek()] && nums[maximum.peek()] < nums[j])
                {
                    System.out.println("i : " + numsI + " j : " + nums[j] + " k : " + nums[maximum.peek()]);
                    return true;
                }
                maximum.pop(); //if condition not satisfied, current stack top cant be k, pop and check again
            }
            maximum.push(j);  //push when while breaks
        }
        return false;
    }
}
