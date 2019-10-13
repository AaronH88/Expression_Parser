package exercise;

/* 
 * Java implementation of the Shunting-yard algorithm
*/ 
import java.util.Stack; 

public class EvaluateString 
{ 
 public static int evaluate(String expression) 
 { 
     char[] charIn = expression.toCharArray();

     Stack<Integer> numbers = new Stack<Integer>(); 

     Stack<Character> ops = new Stack<Character>(); 

     for (int i = 0; i < charIn.length; i++) 
     { 
         if (charIn[i] == ' ') 
             continue; 

         if (charIn[i] >= '0' && charIn[i] <= '9') 
         { 
             StringBuffer sbuf = new StringBuffer();
             
             while (i < charIn.length && charIn[i] >= '0' && charIn[i] <= '9') 
                 sbuf.append(charIn[i++]); 
             
             numbers.push(Integer.parseInt(sbuf.toString())); 
         }
         else if (charIn[i] == '(') 
             ops.push(charIn[i]);
         else if (charIn[i] == ')') 
         { 
             while (ops.peek() != '(') 
               numbers.push(doMath(ops.pop(), numbers.pop(), numbers.pop())); 
             
             ops.pop(); 
         } 
         else if (charIn[i] == '+' || charIn[i] == '-' || 
                  charIn[i] == '*' || charIn[i] == '/') 
         { 
             while (!ops.empty() && orderOfExec(charIn[i], ops.peek())) 
               numbers.push(doMath(ops.pop(), numbers.pop(), numbers.pop())); 

             ops.push(charIn[i]); 
         } 
     } 

     while (!ops.empty()) 
         numbers.push(doMath(ops.pop(), numbers.pop(), numbers.pop())); 

     return numbers.pop(); 
 } 

 public static boolean orderOfExec(char op1, char op2) 
 { 
     if (op2 == '(' || op2 == ')') 
         return false; 
     if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) 
         return false; 
     else
         return true; 
 } 
 
 public static int doMath(char operator, int b, int a) 
 { 
     switch (operator) 
     { 
     case '+': 
         return a + b; 
     case '-': 
         return a - b; 
     case '*': 
         return a * b; 
     case '/': 
         if (b == 0) 
             throw new
             UnsupportedOperationException("Cannot divide by zero"); 
         return a / b; 
     } 
     return 0; 
 } 

 public static void main(String[] args) 
 { 
	 System.out.println(EvaluateString.evaluate("( 1 + 2 ) * ( 3 + ( 4 * 5 ) )"));
 } 
}