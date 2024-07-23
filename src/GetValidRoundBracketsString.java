import java.util.Stack;

/**
 * @author : lzp
 */
public class GetValidRoundBracketsString {

    public boolean checkValid(String s) {
        Stack<Character> stack = new Stack<>();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == '(') {
                stack.push(c);
            } else if(c == ')') {
                if(stack.isEmpty()) {
                    // invalid, ¶îÍâÈ±ÉÙ×óÀ¨ºÅ
                    return false;
                } else {
                    stack.pop();
                }
            }
        }
        return stack.isEmpty();
    }

    public String getValid(String s) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == '(') {
                stack.push(c);
                result.append("(");
            } else if(c == ')') {
                if(stack.isEmpty()) {
                    // invalid, ¶îÍâÈ±ÉÙ×óÀ¨ºÅ
                    result.append("(");
                } else {
                    stack.pop();
                }
                result.append(")");
            }
        }
        // invalid£¬ È±ÉÙÓÒÀ¨ºÅ
        while(!stack.isEmpty()) {
            stack.pop();
            result.append(")");
        }
        return result.toString();
    }

    public static void main(String[] args) {
        GetValidRoundBracketsString demo = new GetValidRoundBracketsString();
        System.out.println(demo.checkValid("()((("));
        System.out.println(demo.getValid("()((("));

        System.out.println(demo.checkValid("()))"));
        System.out.println(demo.getValid("()))"));

        System.out.println(demo.checkValid("()"));
        System.out.println(demo.getValid("()"));
    }
}
