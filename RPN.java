// 150.
// approach - traverse through tokens[], push numbers into stack, if operators are seen, pop top two numbers from stack, perform appropriate operation and push the result into stack - at the end pop the only number from stack and return
//time - O(n)
//space - O(n)
class Solution {
    public int evalRPN(String[] tokens) {
        //edge
        if(tokens == null || tokens.length == 0)
        {
            return 0;
        }
        
        Stack<Integer> support = new Stack<>();
        HashSet<String> operators = new HashSet<>();
        operators.add("+");
        operators.add("-");
        operators.add("*");
        operators.add("/");
        
        for(int i = 0; i < tokens.length; i++)
        {
            String current = tokens[i];
            if(!operators.contains(current)) 
            {
                //current token is a number
                support.push(Integer.parseInt(current));
            }
            else
            {
                int operand2 = support.pop();
                int operand1 = support.pop();
                //current is an operator
                if(current.equals("+"))
                {
                    support.push(operand1 + operand2);
                }
                else if(current.equals("-"))
                {
                    support.push(operand1 - operand2);
                }
                else if(current.equals("*"))
                {
                    support.push(operand1 * operand2);    
                }
                else //current = /
                {
                    support.push(operand1 / operand2);
                }
            }
        }
        
        return support.pop();
    }
}
