// 692.
// time - O(n)
// space - O(n)
class Solution {
    public int calPoints(String[] ops) {
        //edge
        if(ops == null || ops.length == 0)
        {
            return 0;
        }
        
        Stack<Integer> support = new Stack<>();
        int result = 0;
        
        for(String operand : ops)
        {
            if(operand.equals("+"))
            {
                //current round score = sum of two previous round scores
                int lastRound = support.pop();
                int secondLastRound = support.pop();
                int currentRound = lastRound + secondLastRound;
                result += currentRound;
                support.push(secondLastRound);
                support.push(lastRound);
                support.push(currentRound);
            }
            else if(operand.equals("D"))
            {
                //current round score = 2 * last round score
                int lastRound = support.pop();
                int currentRound = lastRound * 2;
                result += currentRound;
                support.push(lastRound);
                support.push(currentRound);
            }
            else if(operand.equals("C"))
            {
                //remove last round score
                int lastRound = support.pop();
                result -= lastRound;
            }
            else //operand is a number
            {
                int currentRound = Integer.parseInt(operand);
                result += currentRound;
                support.push(currentRound);
            }
        }
        
        return result;
    }
}
