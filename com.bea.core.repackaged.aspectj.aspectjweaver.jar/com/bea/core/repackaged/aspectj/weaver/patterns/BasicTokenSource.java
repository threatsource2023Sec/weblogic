package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import java.util.ArrayList;
import java.util.List;

public class BasicTokenSource implements ITokenSource {
   private int index = 0;
   private IToken[] tokens;
   private ISourceContext sourceContext;

   public BasicTokenSource(IToken[] tokens, ISourceContext sourceContext) {
      this.tokens = tokens;
      this.sourceContext = sourceContext;
   }

   public int getIndex() {
      return this.index;
   }

   public void setIndex(int newIndex) {
      this.index = newIndex;
   }

   public IToken next() {
      try {
         return this.tokens[this.index++];
      } catch (ArrayIndexOutOfBoundsException var2) {
         return IToken.EOF;
      }
   }

   public IToken peek() {
      try {
         return this.tokens[this.index];
      } catch (ArrayIndexOutOfBoundsException var2) {
         return IToken.EOF;
      }
   }

   public IToken peek(int offset) {
      try {
         return this.tokens[this.index + offset];
      } catch (ArrayIndexOutOfBoundsException var3) {
         return IToken.EOF;
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("[");

      for(int i = 0; i < this.tokens.length; ++i) {
         IToken t = this.tokens[i];
         if (t == null) {
            break;
         }

         if (i > 0) {
            buf.append(", ");
         }

         buf.append(t.toString());
      }

      buf.append("]");
      return buf.toString();
   }

   public static ITokenSource makeTokenSource(String input, ISourceContext context) {
      char[] chars = input.toCharArray();
      int i = 0;
      List tokens = new ArrayList();

      while(true) {
         while(true) {
            while(true) {
               while(i < chars.length) {
                  char ch = chars[i++];
                  char nextChar;
                  int nextChar2;
                  int start;
                  switch (ch) {
                     case '\t':
                     case '\n':
                     case '\r':
                     case ' ':
                        continue;
                     case '!':
                     case '(':
                     case ')':
                     case '*':
                     case '+':
                     case ',':
                     case ':':
                     case '<':
                     case '=':
                     case '>':
                     case '?':
                     case '@':
                     case '[':
                     case ']':
                        tokens.add(BasicToken.makeOperator(makeString(ch), i - 1, i - 1));
                        continue;
                     case '"':
                        for(nextChar2 = i - 1; i < chars.length && chars[i] != '"'; ++i) {
                        }

                        ++i;
                        tokens.add(BasicToken.makeLiteral(new String(chars, nextChar2 + 1, i - nextChar2 - 2), "string", nextChar2, i - 1));
                        continue;
                     case '&':
                        if (i + 1 <= chars.length && chars[i] != '&') {
                           tokens.add(BasicToken.makeOperator(makeString(ch), i - 1, i - 1));
                           continue;
                        }
                     case '|':
                        if (i == chars.length) {
                           throw new BCException("bad " + ch);
                        }

                        nextChar = chars[i++];
                        if (nextChar != ch) {
                           throw new RuntimeException("bad " + ch);
                        }

                        tokens.add(BasicToken.makeOperator(makeString(ch, 2), i - 2, i - 1));
                        continue;
                     case '.':
                        if (i + 2 <= chars.length) {
                           nextChar = chars[i];
                           nextChar2 = chars[i + 1];
                           if (ch == nextChar && ch == nextChar2) {
                              tokens.add(BasicToken.makeIdentifier("...", i - 1, i + 1));
                              i += 2;
                              continue;
                           }

                           tokens.add(BasicToken.makeOperator(makeString(ch), i - 1, i - 1));
                           continue;
                        }

                        tokens.add(BasicToken.makeOperator(makeString(ch), i - 1, i - 1));
                        continue;
                     default:
                        start = i - 1;
                  }

                  while(i < chars.length && Character.isJavaIdentifierPart(chars[i])) {
                     ++i;
                  }

                  tokens.add(BasicToken.makeIdentifier(new String(chars, start, i - start), start, i - 1));
               }

               return new BasicTokenSource((IToken[])((IToken[])tokens.toArray(new IToken[tokens.size()])), context);
            }
         }
      }
   }

   private static String makeString(char ch) {
      return Character.toString(ch);
   }

   private static String makeString(char ch, int count) {
      char[] chars = new char[count];

      for(int i = 0; i < count; ++i) {
         chars[i] = ch;
      }

      return new String(chars);
   }

   public ISourceContext getSourceContext() {
      return this.sourceContext;
   }

   public void setSourceContext(ISourceContext context) {
      this.sourceContext = context;
   }
}
