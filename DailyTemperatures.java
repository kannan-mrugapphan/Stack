// 739.
// brute force - for every element, find a larger element in the right, update the result to get a run time of O(n^2)
// stack - stack of indices -> stack top always has the min so far -> all elements in stack are not processed
//time - O(n)
//space - O(n)

class Solution {
    public int[] dailyTemperatures(int[] T) {
        //edge
        if(T == null || T.length == 0)
        {
            return new int[0];
        }
        
        Stack<Integer> support = new Stack<>();
        int[] result = new int[T.length];
        for(int i = 0; i < T.length; i++)
        {
            //as long as stack top is smaller than current, pop, udate result
            while(!support.isEmpty() && T[support.peek()] < T[i])
            {
                int index = support.pop();
                result[index] = i - index;
            }
            //push current into stack
            support.push(i);
        }
        return result;
    }
}
