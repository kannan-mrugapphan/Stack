// 227.
// time - O(n)
// space - O(n) for the stack
class Solution {
    public int calculate(String s) {
        Stack<Integer> numbers = new Stack<>();
        int num = 0; //initially num = 0 and sign = +
        char prevSign = '+';
        int result = 0;
        for(int i = 0; i < s.length(); i++)
        {
            char current = s.charAt(i);
            if(Character.isDigit(current)) //when at number, update num
            {
                num = (num * 10) + (current - '0');
            }
            if((!Character.isDigit(current) && current != ' ') || i == s.length() - 1) 
            {
                //current char is an operator
                //if prev sign = '+' or '-' push +num or -num into stack accordingly (+s and -s should be processes at the end, so push the num with prev sign into stack) 
                //if prev sign = '*' or '/' process the prev sign with current num and stack's top, push the result into stack
                //update prev sign with current and num with 0
                //if at the last index, force push num into stack based on prev sign
                if(prevSign == '+')
                {
                    numbers.push(+num);
                }
                else if(prevSign == '-')
                {
                    numbers.push(-num);
                }
                else if(prevSign == '*')
                {
                    numbers.push(numbers.pop() * num);
                }
                else//(prevSign == '/')
                {
                    numbers.push(numbers.pop() / num);
                }
                prevSign = current;
                num = 0;
            }
        }
        while(!numbers.isEmpty()) //pop from stack and add to result
        {
            result += numbers.pop();
        }
        return result;
    }
}
