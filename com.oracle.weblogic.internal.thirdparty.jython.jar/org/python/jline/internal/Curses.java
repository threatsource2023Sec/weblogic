package org.python.jline.internal;

import java.io.IOException;
import java.io.Writer;
import java.util.Stack;

public class Curses {
   private static Object[] sv = new Object[26];
   private static Object[] dv = new Object[26];
   private static final int IFTE_NONE = 0;
   private static final int IFTE_IF = 1;
   private static final int IFTE_THEN = 2;
   private static final int IFTE_ELSE = 3;

   public static void tputs(Writer out, String str, Object... params) throws IOException {
      int index = 0;
      int length = str.length();
      int ifte = 0;
      boolean exec = true;
      Stack stack = new Stack();

      while(true) {
         while(index < length) {
            char ch = str.charAt(index++);
            switch (ch) {
               case '%':
                  ch = str.charAt(index++);
                  int v;
                  int v1;
                  switch (ch) {
                     case '!':
                        if (exec) {
                           v = toInteger(stack.pop());
                           stack.push(v == 0);
                        }
                        continue;
                     case '"':
                     case '#':
                     case '$':
                     case '(':
                     case ')':
                     case ',':
                     case '.':
                     case '0':
                     case '1':
                     case '2':
                     case '3':
                     case '4':
                     case '5':
                     case '6':
                     case '7':
                     case '8':
                     case '9':
                     case ':':
                     case '@':
                     case 'B':
                     case 'C':
                     case 'D':
                     case 'E':
                     case 'F':
                     case 'G':
                     case 'H':
                     case 'I':
                     case 'J':
                     case 'K':
                     case 'L':
                     case 'M':
                     case 'N':
                     case 'Q':
                     case 'R':
                     case 'S':
                     case 'T':
                     case 'U':
                     case 'V':
                     case 'W':
                     case 'X':
                     case 'Y':
                     case 'Z':
                     case '[':
                     case '\\':
                     case ']':
                     case '_':
                     case '`':
                     case 'a':
                     case 'b':
                     case 'c':
                     case 'f':
                     case 'h':
                     case 'j':
                     case 'k':
                     case 'n':
                     case 'o':
                     case 'q':
                     case 'r':
                     case 's':
                     case 'u':
                     case 'v':
                     case 'w':
                     case 'x':
                     case 'y':
                     case 'z':
                     case '}':
                     default:
                        throw new UnsupportedOperationException();
                     case '%':
                        if (exec) {
                           out.write(37);
                        }
                        continue;
                     case '&':
                        if (exec) {
                           v = toInteger(stack.pop());
                           v1 = toInteger(stack.pop());
                           stack.push(v1 & v);
                        }
                        continue;
                     case '\'':
                        ch = str.charAt(index++);
                        if (exec) {
                           stack.push(Integer.valueOf(ch));
                        }

                        ch = str.charAt(index++);
                        if (ch != '\'') {
                           throw new IllegalArgumentException();
                        }
                        continue;
                     case '*':
                        if (exec) {
                           v = toInteger(stack.pop());
                           v1 = toInteger(stack.pop());
                           stack.push(v1 * v);
                        }
                        continue;
                     case '+':
                        if (exec) {
                           v = toInteger(stack.pop());
                           v1 = toInteger(stack.pop());
                           stack.push(v1 + v);
                        }
                        continue;
                     case '-':
                        if (exec) {
                           v = toInteger(stack.pop());
                           v1 = toInteger(stack.pop());
                           stack.push(v1 - v);
                        }
                        continue;
                     case '/':
                        if (exec) {
                           v = toInteger(stack.pop());
                           v1 = toInteger(stack.pop());
                           stack.push(v1 / v);
                        }
                        continue;
                     case ';':
                        if (ifte != 0 && ifte != 1) {
                           ifte = 0;
                           exec = true;
                           continue;
                        }

                        throw new IllegalArgumentException();
                     case '<':
                        if (exec) {
                           v = toInteger(stack.pop());
                           v1 = toInteger(stack.pop());
                           stack.push(v1 < v);
                        }
                        continue;
                     case '=':
                        if (exec) {
                           v = toInteger(stack.pop());
                           v1 = toInteger(stack.pop());
                           stack.push(v1 == v);
                        }
                        continue;
                     case '>':
                        if (exec) {
                           v = toInteger(stack.pop());
                           v1 = toInteger(stack.pop());
                           stack.push(v1 > v);
                        }
                        continue;
                     case '?':
                        if (ifte != 0) {
                           throw new IllegalArgumentException();
                        }

                        ifte = 1;
                        continue;
                     case 'A':
                        if (exec) {
                           v = toInteger(stack.pop());
                           v1 = toInteger(stack.pop());
                           stack.push(v1 != 0 && v != 0);
                        }
                        continue;
                     case 'O':
                        if (exec) {
                           v = toInteger(stack.pop());
                           v1 = toInteger(stack.pop());
                           stack.push(v1 != 0 || v != 0);
                        }
                        continue;
                     case 'P':
                        ch = str.charAt(index++);
                        if (ch >= 'a' && ch <= 'z') {
                           if (exec) {
                              dv[ch - 97] = stack.pop();
                           }
                           continue;
                        } else {
                           if (ch >= 'A' && ch <= 'Z') {
                              if (exec) {
                                 sv[ch - 65] = stack.pop();
                              }
                              continue;
                           }

                           throw new IllegalArgumentException();
                        }
                     case '^':
                        if (exec) {
                           v = toInteger(stack.pop());
                           v1 = toInteger(stack.pop());
                           stack.push(v1 ^ v);
                        }
                        continue;
                     case 'd':
                        out.write(Integer.toString(toInteger(stack.pop())));
                        continue;
                     case 'e':
                        if (ifte != 2) {
                           throw new IllegalArgumentException();
                        }

                        ifte = 3;
                        exec = !exec;
                        continue;
                     case 'g':
                        ch = str.charAt(index++);
                        if (ch >= 'a' && ch <= 'z') {
                           if (exec) {
                              stack.push(dv[ch - 97]);
                           }
                           continue;
                        } else {
                           if (ch >= 'A' && ch <= 'Z') {
                              if (exec) {
                                 stack.push(sv[ch - 65]);
                              }
                              continue;
                           }

                           throw new IllegalArgumentException();
                        }
                     case 'i':
                        if (params.length >= 1) {
                           params[0] = toInteger(params[0]) + 1;
                        }

                        if (params.length >= 2) {
                           params[1] = toInteger(params[1]) + 1;
                        }
                        continue;
                     case 'l':
                        if (exec) {
                           stack.push(stack.pop().toString().length());
                        }
                        continue;
                     case 'm':
                        if (exec) {
                           v = toInteger(stack.pop());
                           v1 = toInteger(stack.pop());
                           stack.push(v1 % v);
                        }
                        continue;
                     case 'p':
                        ch = str.charAt(index++);
                        if (exec) {
                           stack.push(params[ch - 49]);
                        }
                        continue;
                     case 't':
                        if (ifte != 1 && ifte != 3) {
                           throw new IllegalArgumentException();
                        }

                        ifte = 2;
                        exec = toInteger(stack.pop()) != 0;
                        continue;
                     case '{':
                        int start = index;

                        while(str.charAt(index++) != '}') {
                        }

                        if (exec) {
                           v = Integer.valueOf(str.substring(start, index - 1));
                           stack.push(v);
                        }
                        continue;
                     case '|':
                        if (exec) {
                           v = toInteger(stack.pop());
                           v1 = toInteger(stack.pop());
                           stack.push(v1 | v);
                        }
                        continue;
                     case '~':
                        if (exec) {
                           v = toInteger(stack.pop());
                           stack.push(~v);
                        }
                        continue;
                  }
               case '\\':
                  ch = str.charAt(index++);
                  if (ch >= '0' && ch <= '9') {
                     throw new UnsupportedOperationException();
                  }

                  switch (ch) {
                     case ':':
                     case '\\':
                     case '^':
                        if (exec) {
                           out.write(ch);
                        }
                        continue;
                     case 'E':
                     case 'e':
                        if (exec) {
                           out.write(27);
                        }
                        continue;
                     case 'b':
                        if (exec) {
                           out.write(8);
                        }
                        continue;
                     case 'f':
                        if (exec) {
                           out.write(12);
                        }
                        continue;
                     case 'n':
                        out.write(10);
                        continue;
                     case 'r':
                        if (exec) {
                           out.write(13);
                        }
                        continue;
                     case 's':
                        if (exec) {
                           out.write(32);
                        }
                        continue;
                     case 't':
                        if (exec) {
                           out.write(9);
                        }
                        continue;
                     default:
                        throw new IllegalArgumentException();
                  }
               case '^':
                  ch = str.charAt(index++);
                  if (exec) {
                     out.write(ch - 64);
                  }
                  break;
               default:
                  if (exec) {
                     out.write(ch);
                  }
            }
         }

         return;
      }
   }

   private static int toInteger(Object pop) {
      if (pop instanceof Number) {
         return ((Number)pop).intValue();
      } else if (pop instanceof Boolean) {
         return (Boolean)pop ? 1 : 0;
      } else {
         return Integer.valueOf(pop.toString());
      }
   }
}
