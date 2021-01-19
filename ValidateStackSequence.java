// 946.
// time - O(n)
// space - O(n)
class Solution {
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        //edge
        if(pushed.length != popped.length)
        {
            return false;
        }
        
        Stack<Integer> support = new Stack<>(); //simulates the operations
        int i = 0; //iterates over popped
        for(int num : pushed) //exhaust all elements in pushed
        {
            support.push(num); //push current
            while(!support.isEmpty() && support.peek() == popped[i])
            {
                //stack top is same as current in opped array
                //so simulate pop
                support.pop();
                i++; //go to next element in popped array
            }
        }
        
        if(i == popped.length) //popped is also exhausted
        {
            return true;
        }
        
        return false;
    }
}
