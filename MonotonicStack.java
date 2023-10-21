// 503.
class Solution {
    public int[] nextGreaterElements(int[] nums) {
        //edge
        if(nums == null || nums.length == 0)
        {
            return new int[0];
        }

        int[] result = new int[nums.length]; //return list
        Arrays.fill(result, -1); //if nge is not available for an element then it is defaulted to -1
        Stack<Integer> support = new Stack<>();

        for(int step = 1; step <= 2; step++)
        {
            //each element is inserted into and popped from stack atmost once
            //time - O(n) 
            for(int i = 0; i < nums.length; i++)
            {
                //check if current element at i is the nge for any previous element
                while(!support.isEmpty() && nums[support.peek()] < nums[i])
                {
                    //nums[i] is nge to element at support.peek()
                    result[support.peek()] = nums[i];

                    //stack's top result is computed and could be removed
                    support.pop();
                }

                //nge for current element at i will be computed in future iterations
                support.push(i);
            }

            //the last elements for which nge is not found in 1st step will remain in stack and computed by in the next step which accounts for circular nature of array
        }
        
        return result;
    }
}

// 739.
// time - O(n) - each element is pushed into and popped from stack atmost once
// space - O(n) for stack consider case where no warmer day exists for any day
class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        //edge
        if(temperatures == null || temperatures.length == 0)
        {
            return new int[0];
        }

        //result[i] is the number of wait days for next warmer day to the ith day
        int[] result = new int[temperatures.length]; 
        Stack<Integer> support = new Stack<>();

        for(int i = 0; i < temperatures.length; i++)
        {
            //check if the current day is the answer to any previous day
            while(!support.isEmpty() && temperatures[support.peek()] < temperatures[i])
            {
                //ith day is warmer than the day at stack top
                result[support.peek()] = i - support.peek(); //range from [stack top, i)

                //the warmer day fro stack top is computed
                support.pop();
            }

            //the next warmer day for the current day will be computed in future iteration
            support.push(i);
        }

        return result;
    }
}

// 1762.
// time - O(n) - each element is pushed into and popped from stack atmost once
// space - O(n) for stack - consider case where all buildings have ocean view [10,9,8,7,6,5[
class Solution {
    public int[] findBuildings(int[] heights) {
        //edge
        if(heights == null || heights.length == 0)
        {
            return new int[0];
        }

        Stack<Integer> support = new Stack<>();
        for(int i = 0; i < heights.length; i++)
        {
            //check if the current building i blocks the view of any previous building
            while(!support.isEmpty() && heights[support.peek()] <= heights[i])
            {
                //current building i blocks the view of building at stack top because it is taller than or has same height as the prev
                support.pop(); //this building won't have an ocean view
            }

            //building i's view is based on the buildings to right - will be processed in future iterations
            support.push(i);
        }

        //all the buildings in stack have no buildings to right with larger or equal height and have ocean view (buildings are in stack from 0 to n- 1 from bottom to top)
        int[] result = new int[support.size()];
        int index = result.length - 1;
        while(!support.isEmpty())
        {
            result[index--] = support.pop();
        }

        return result;
    }
}

// 456.
// time - O(n) - each element is inserted into and popped from stack only once
// space - O(n) for minimum array and O(n) for stack
class Solution {
    public boolean find132pattern(int[] nums) {
        //edge
        if(nums == null || nums.length < 3)
        {
            return false; //atleast 3 elements needed
        }

        //minimum[i] = the minimum element in nums[0, i]
        int[] minimum = new int[nums.length];
        Stack<Integer> support = new Stack<>();

        //i < j < k and nums[i] < nums[j] > nums[k] and nums[k] > nums[i]
        //consider each element as the last element of the triplet
        for(int k = 0; k < nums.length; k++)
        {
            //update minimum till k
            if(k == 0)
            {
                minimum[k] = nums[k]; //minimum in nums[0,0] is nums[0]
            }
            else
            {
                //if current is smaller than minimum in nums[0, current - 1]
                if(nums[k] < minimum[k - 1])
                {
                    minimum[k] = nums[k]; //minimum in nums[0, current] is current
                }
                else
                {
                    minimum[k] = minimum[k - 1]; //else retain the prev minimum as it is smaller than current
                }
            }
            
            //j is the previous greater element of k
            while(!support.isEmpty() && nums[support.peek()] <= nums[k])
            {
                //stack top can't be j as it is smaller than or equal to k
                support.pop();
            }

            //stack's top if not empty is larger than k
            if(!support.isEmpty())
            {
                //prev greater element of k is found and set to j
                int j = nums[support.peek()];

                //i (1st element in triplet) is smallest from nums[0, j]
                int i = minimum[support.peek()];
                if(i < j && nums[k] > i)
                {
                    return true; 
                }
            }

            //consider current k as a j for future elements
            support.push(k);
        }

        return false;
    }
}

// 84.
// time - O(n) - each bar is inserted into and removed from stack once
// space - O(n) for stack - scenario in case [9,8,7,6,5]
class Solution {
    public int largestRectangleArea(int[] heights) {
        //edge
        if(heights == null)
        {
            return 0;
        }

        int maxArea = 0; //return value
        Stack<int[]> support = new Stack<>(); //keeps tracks of the bars that can be extended to right and their start indices

        for(int i = 0; i < heights.length; i++)
        {
            int startIndex = i; //current bar starts from index i
            //check if this bar stops any previous bars from extending to right
            while(!support.isEmpty() && support.peek()[0] > heights[i])
            {
                //current bar is shorter than bar at stack top and prev bar can't be extended to right
                int[] prevBar = support.pop();
                int prevHeight = prevBar[0];
                int prevWidth = i - prevBar[1]; //prev bar ranges from [prev start, i)

                maxArea = Math.max(maxArea, prevHeight * prevWidth); //update max area based on prev bar area

                //current bar is shorter than prev bar and it can be extended to left till prevBar start
                startIndex = prevBar[1];
            }

            //current bar starts at startIndex and can be extended to right, so push to stack
            support.push(new int[]{heights[i], startIndex});
        }

        //some bars may remain in stack (meaning they can be extended from their start till end)
        while(!support.isEmpty())
        {
            int[] current = support.pop();
            int currentHeight = current[0];
            int currentWidth = heights.length - current[1]; //current bar ranges from [start, end)
            maxArea = Math.max(maxArea, currentHeight * currentWidth); //update max area based on current bar area
        }

        return maxArea;
    }
}
