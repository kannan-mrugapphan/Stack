// 1249.
// time - O(n)
// space - O(n) for stack
class Solution {
    public String minRemoveToMakeValid(String s) {
        //edge
        if(s == null || s.length() == 0)
        {
            return s; //i/p string is already empty and valid
        }

        Stack<Integer> support = new Stack<>(); //keeps track of indices to be deleted from s
        for(int i = 0; i < s.length(); i++)
        {
            char current = s.charAt(i); //get the current char
            if(Character.isLetter(current))
            {
                continue; //letters doesn't affect the validity of s
            }
            else if(current == '(')
            {
                //push into support stack so it can be nullified by future close paranthesis
                support.push(i);
            }
            else
            {
                //close bracket
                //check if there is a matching open bracket
                if(!support.isEmpty() && s.charAt(support.peek()) == '(')
                {
                    support.pop(); //corresponding open bracket found, so both can be retained
                }
                else
                {
                    //no corresponding open bracket found, should be deleted
                    support.push(i);
                }
            }
        }

        //build result string excluding letters from stack
        StringBuilder result = new StringBuilder();
        for(int i = s.length() - 1; i >= 0; i--)
        {
            //if i has to be deleted
            if(!support.isEmpty() && support.peek() == i)
            {
                support.pop(); 
                continue;
            }
            else
            {
                result.append(s.charAt(i));
            }
        }

        return result.reverse().toString();
    }
}
