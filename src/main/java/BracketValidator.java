import java.util.Stack;

class BracketsValidator {

    private Stack<Character> stack = new Stack<Character>();

    private boolean isOpeningBracket(char bracket) {
        return "({[".indexOf(bracket) != -1;
    }

    private boolean isClosingBracket(char bracket) {
        return ")}]".indexOf(bracket) != -1;
    }

    private boolean isPair(char opening, char closing) {
        return opening == '(' && closing == ')' || opening == '['
                && closing == ']' || opening == '{' && closing == '}';
    }

    public boolean validate(String input) {
        for (char c : input.toCharArray()) {
            if (isClosingBracket(c) && stack.isEmpty()) {
                return false;
            }
            if (isOpeningBracket(c)) {
                stack.push(c);
            }
            if (isClosingBracket(c)) {
                if (isPair(stack.peek(), c)) {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

}