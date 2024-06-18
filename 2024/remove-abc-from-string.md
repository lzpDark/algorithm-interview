
#### 原始题目描述
```Java
public class Main {
    /**
     * 去掉字符串中的‘b'或者连续的’ac‘
     *  比如：
     *  'aacbd' - 'ad'
     *  'aabcd' -> 'ad'
     *  'aaabbccc' -> ''
     *  'aadc' -> 'aadc'
     */
    public static void main(String[] args) {
        //Scanner scanner = new Scanner(System.in);
        //String str = scanner.next();
        System.out.println("Hello world!");
    }
}

```
#### 提交代码
```Java
public class Main {
    private static List<String> loadInput() {
        return List.of(
                "aacbd",
                "aabcd",
                "aaabbccc",
                "aadc"
        );
    }
    private static List<String> expectedList() {
        return List.of(
                "ad",
                "ad",
                "",
                "aadc"
        );
    }
    private static String removeAC(String str) {
        // input in stack
        Stack<Character>stack = new Stack<>();
        int length = str.length();
        for(int i = 0; i < length; i++) {
            if(str.charAt(i) == 'b') {
                continue;
            } else if(str.charAt(i) == 'c') {
                // remove top 'c'/'a' only top is 'a'
                if(stack.peek() == 'a') {
                    stack.pop();
                } else {
                    stack.push('c');
                }
            } else {
                stack.push(str.charAt(i));
            }
        }
        // output
        StringBuilder res = new StringBuilder();
        while(!stack.isEmpty()) {
            res.append(stack.pop());
        }
        // reverse res
        res.reverse();
        return res.toString();
    }
    public static void main(String[] args) {
        List<String> strList = loadInput();
        List<String> expects = expectedList();
        for(int i = 0; i < strList.size(); i++) {
            String res = removeAC(strList.get(i));
            if(!expects.get(i).equals(res)) {
                System.out.print("error" + strList.get(i));
            }
        }
    }
}
```