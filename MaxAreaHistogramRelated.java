// 1793.
// brute force - find all sub arrays, while generating each sub array, keep track of min in it and compute its score, out of all scores return max score - time - O(n^2)
// time - O(n) - each element is inserted into and popped from stack once
// space - O(n) for array like [1,2,3,4,5]
class Solution {
    public int maximumScore(int[] nums, int k) {
        //edge
        if(nums == null || nums.length < k)
        {
            return 0; //nums[i,j] such that i <= k and j >= k i.e sub array must end at j 
        }

        //to keep track of min in each sub array (index 0) and start index of that sub array (index 1)
        Stack<int[]> support = new Stack<>(); 
        int maxScore = Integer.MIN_VALUE; //return value

        for(int i = 0; i < nums.length; i++)
        {
            int startIndex = i; //current subarray starts at index i

            //check if current element beats any prev minimums, if so those sub arrays end at current index i
            while(!support.isEmpty() && support.peek()[0] > nums[i])
            {
                //prev sub array with suport.peek()[0] as min and support.peek()[1] as start can't be extended to right as an even smaller number is found at i
                int[] prev = support.pop();
                int prevMinimum = prev[0];
                int prevStart = prev[1];
                int prevEnd = i - 1;

                //check if this subarray from prevStart to prevEnd is a good subarray
                if(prevStart <= k && k <= prevEnd)
                {
                    //update max score with score of current sub array
                    maxScore = Math.max(maxScore, prevMinimum * (prevEnd - prevStart + 1));
                }

                startIndex = prevStart; //current sub array can be extended to left as it is min of all elements in that range
            }

            //push current subarray with min as nums[i] and start as startIndex into stack
            support.push(new int[]{nums[i], startIndex});
        }

        //there can be subarrays extending to end, process them
        while(!support.isEmpty())
        {
            int[] current = support.pop();
            int currentMinimum = current[0];
            int currentStart = current[1];
            int currentEnd = nums.length - 1;

            //check if this subarray from currentStart to currentEnd is a good subarray
            if(currentStart <= k && k <= currentEnd)
            {
                //update max score with score of current sub array
                maxScore = Math.max(maxScore, currentMinimum * (currentEnd - currentStart + 1));
            }
        }

        return maxScore;
    }
}
