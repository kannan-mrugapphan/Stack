// 402.
// time - O(n)
// space - O(n)
class Solution {
    public String removeKdigits(String num, int k) {
        //observation - given a n numbers, n! n digit numbers can be formed from the given numbers
        //the smallest number is obtained by arranging the numbers in increasing order
        
        //eg 1 - 1432219 - from the plot, observe that 4, 3, 2 are the first 3(k) dips i.e drops from high to a lower value
        //so remove 4,3,2 to get 1219
        //eg 2 - 10200 - the 1st dip is at the 1st number itself, so remove 1 to get 0200 - remove preceeding 0 to get 200
        
        //stack is used to keep track of these dips
        
        //edge
        if(k >= num.length())
        {
            return "0"; //drop all numbers in num string
        }
        
        Stack<Integer> support = new Stack<>();
        for(int i = 0; i < num.length(); i++)
        {
            Integer current = num.charAt(i) - '0';
            //as long as current is smaller than stack top and k is +ve, pop from stack
            while(!support.isEmpty() && support.peek() > current && k > 0)
            {
                support.pop();
                k--;
            }
            //dont push preceeding zeros into stack
            if(support.isEmpty() && current == 0)
            {
                continue;
            }
            else
            {
                support.push(current);
            }
        }
        //in case of "112" with k = 1, stack will have all 1,1,2 as no current satisfies pop(), finally remove the largest(2) which is in the stack top
        while(k > 0)
        {
            support.pop();
            k--;
        }
        StringBuilder result = new StringBuilder(); //pop from the stack, append to result string, reverse it then return
        while(!support.isEmpty())
        {
            result.append(support.pop());
        }
        return (result.length() == 0) ? "0" : result.reverse().toString();
    }
}
