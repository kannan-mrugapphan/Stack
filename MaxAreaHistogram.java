// 84.
// time - O(n)
// space - O(n)
class Solution {
    public int largestRectangleArea(int[] heights) {
        //edge
        if(heights == null || heights.length == 0)
        {
            return 0;
        }
        
        int maxArea = Integer.MIN_VALUE; //return value
        Stack<Integer> height = new Stack<>(); //2 stacks to keep track of unexplored heights and their starting positions
        Stack<Integer> position = new Stack<>();
        
        int current = 0; //iteration pointer
        while(current < heights.length)
        {
            //if stack is empty or stack top(height stack) is smaller than current height
            if(height.isEmpty() || heights[current] > height.peek())
            {
                height.push(heights[current]);
                position.push(current);
            }
            else //heights[current] <= height.peek()
            {   
                int prevPos = 0;
                int prevHt = 0;
                //as long as the condition heights[current] <= height.peek() is breached, pop and caluclate the respective area
                while(!height.isEmpty() && heights[current] <= height.peek())
                {
                    prevPos = position.pop();
                    prevHt = height.pop();
                    int prevArea = prevHt * (current - prevPos);
                    maxArea = Math.max(maxArea, prevArea);
                    
                }
                //push the current height and prev position
                height.push(heights[current]);
                position.push(prevPos);
            }
            current++;
        }
        
        //for remaining elements find area
        while(!height.isEmpty())
        {
            int prevPosition = position.pop();
            int popArea = height.pop() * (current - prevPosition);
            maxArea = Math.max(maxArea, popArea);
        }
        
        return maxArea;
    }
}
