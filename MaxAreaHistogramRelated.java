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

// 1856.
// time - O(n)
// space - O(n)
class Solution {
    public int maxSumMinProduct(int[] nums) {
        //edge
        if(nums == null || nums.length == 0)
        {
            return 0;
        }

        int mod = (int) 1e9 + 7;

        long result = Long.MIN_VALUE; //return value
        long[] runningSum = new long[nums.length]; //runningSum[i] = sum of all elements from 0 to i
        Stack<int[]> support = new Stack<>(); //keeps track of start of each sub array (index 1) and min in that sub array (index 0) that can be extended to right

        for(int i = 0; i < nums.length; i++)
        {
            //update running sum
            if(i == 0)
            {
                runningSum[i] = nums[i];
            }
            else
            {
                runningSum[i] += runningSum[i - 1] + nums[i];
            }

            int startIndex = i; //current subarray starts at i

            //check if current elements breaks any prev sub arays from extending to right
            while(!support.isEmpty() && support.peek()[0] > nums[i])
            {
                //prev sub array with that min can't be extended to right as smaller min (nums[i]) is found
                int[] prev = support.pop();
                int prevMin = prev[0];
                int prevEnd = i - 1; //i is not part of prev sub array
                int prevStart = prev[1];

                //sum of [i,j] = runningSum[j] - runningSum[i - 1]
                long prevSum = runningSum[prevEnd];
                if(prevStart > 0)
                {
                    prevSum -= runningSum[prevStart - 1];
                }

                long prevResult =  (prevMin * prevSum);

                //update max with prev
                result = Math.max(result, prevResult);

                //current sub array can be extended to left 
                startIndex = prevStart;
            }

            //current sub array will be computed in future iterations
            support.push(new int[]{nums[i], startIndex}); 
        }

        //there can still be subarrays extending all the way to end
        while(!support.isEmpty())
        {
            int[] current = support.pop();
            int currentMin = current[0];
            int currentEnd = nums.length - 1; //current extends till end of array
            int currentStart = current[1];

            //sum of [i,j] = runningSum[j] - runningSum[i - 1]
            long currentSum = runningSum[currentEnd];
            if(currentStart > 0)
            {
                currentSum -= runningSum[currentStart - 1];
            }
            
            long currentResult =  (currentSum * currentMin);
            result = Math.max(result, currentResult);
        }

        return (int) (result % mod);
    }
}

// 85.
// time - O(n*nm)
// space - O(n)
class Solution {
    public int maximalRectangle(char[][] matrix) {
        int result = 0; //return value

        //process all rows from top to end 
        int[] nums = new int[matrix[0].length];

        for(char[] row : matrix)
        {
            //copy each element
            for(int i = 0; i < row.length; i++)
            {
                //reset if 0
                if(row[i] == '0')
                {
                    nums[i] = 0;
                }
                //extend on top of prev
                else
                {
                    nums[i] += 1; 
                }
            }
            
            //get area for this row
            result = Math.max(result, findMaxAreaInHistogram(nums));
        }

        return result;
    }

    //returns max area in histogram
    //time - O(n)
    //space - O(n)
    private int findMaxAreaInHistogram(int[] nums)
    {
        Stack<int[]> support = new Stack<>(); //to keep track of bars that can be extended to right
        int maxArea = 0; //return value

        for(int i = 0; i < nums.length; i++)
        {
            int startIndex = i; //current bar starts at i
            //check if current bar stops any prev bars from extending to right
            while(!support.isEmpty() && support.peek()[0] >= nums[i])
            {
                int[] prev = support.pop(); //prev bar is shorter or equal and can't be extended to right
                int prevHeight = prev[0];
                int prevStart = prev[1];
                int prevEnd = i - 1;
                int prevArea = prevHeight * (prevEnd - prevStart + 1);

                maxArea = Math.max(maxArea, prevArea); //update result
                startIndex = prevStart; //current bar can be extended to left
            }

            support.push(new int[]{nums[i], startIndex});
        }

        //process bars extending till end
        while(!support.isEmpty())
        {
            int[] current = support.pop(); 
            int currentHeight = current[0];
            int currentStart = current[1];
            int currentEnd = nums.length - 1;
            int currentArea = currentHeight * (currentEnd - currentStart + 1);

            maxArea = Math.max(maxArea, currentArea); //update result
        }

        return maxArea;
    }
}
