// 71.
// time - O(n)
// space - O(number of folfers in o/p path) for stack
class Solution {
    public String simplifyPath(String path) {
        //edge
        if(path == null || path.length() == 0)
        {
            return path;
        }

        path = path + "/"; //path doesnot end with trailing /

        Stack<String> directories = new Stack<>(); //to keep track of directory or file names in path string

        String currentComponent = ""; //build the current componenet (file name or directory name or ..) that comes with /.../

        //1st char in string is starting / and can be ignored
        for(int i = 1; i < path.length(); i++)
        {
            //until a / is found, contruct the current component
            if(path.charAt(i) != '/')
            {
                currentComponent += path.charAt(i);
            }
            else
            {
                //current component is obtained
                if(currentComponent.equals(".."))
                {
                    //go 1 directory back
                    if(!directories.isEmpty())
                    {
                        directories.pop();
                    }
                }
                else if(currentComponent.equals(".") || currentComponent.length() == 0)
                {
                    currentComponent = ""; //reset and continue
                    continue; //no processing needed
                }
                else
                {
                    //current component is a valid file/folder
                    directories.push(currentComponent);
                }

                //reset current component
                currentComponent = "";
            }
        }

        //build result
        String[] folders = new String[directories.size()];
        int index = folders.length - 1;

        while(!directories.isEmpty())
        {
            folders[index--] = directories.pop();
        }

        StringBuilder result = new StringBuilder();
        result.append("/");

        for(String folder : folders)
        {
            result.append(folder);
            result.append("/");
        }

        //remove last / if length is above 1
        if(result.length() > 1)
        {
            result.deleteCharAt(result.length() - 1);    
        }
        
        return result.toString();
    }
}
