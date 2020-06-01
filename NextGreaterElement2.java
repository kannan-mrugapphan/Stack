// 503.
// obs - circular array, next greater element has to be stored in the result
// time - O(n)
// space - O(n)
class Solution {
    public int[] nextGreaterElements(int[] nums) {
        //edge
        if(nums == null || nums.length == 0)
        {
            return new int[0];
        }
        
        Stack<Integer> support = new Stack<>();
        int[] result = new int[nums.length];
        Arrays.fill(result, -1); //if no greater element, return array of -1s
        
        //circular array so process each element twice
        for(int x = 0; x < 2 * nums.length; x++)
        {
            int i = x % nums.length; 
            //as long as stack top is smaller than current, pop, update result
            while(!support.isEmpty() && nums[support.peek()] < nums[i])
            {
                int popped = support.pop();
                result[popped] = nums[i];
            }
            //push it if the element is not processed i.e result of this element is still -1
            if(result[i] == -1)
            {
                support.push(i);
            }
        }
        return result;
    }
}
