package org.python.antlr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.python.antlr.runtime.CommonToken;
import org.python.antlr.runtime.CommonTokenStream;
import org.python.antlr.runtime.Token;
import org.python.antlr.runtime.TokenSource;
import org.python.core.Py;

public class PythonTokenSource implements TokenSource {
   public static final int MAX_INDENTS = 100;
   public static final int FIRST_CHAR_POSITION = 0;
   int[] indentStack;
   int sp;
   Vector tokens;
   CommonTokenStream stream;
   int lastTokenAddedIndex;
   String filename;
   boolean inSingle;

   public PythonTokenSource(PythonLexer lexer) {
      this.indentStack = new int[100];
      this.sp = -1;
      this.tokens = new Vector();
      this.lastTokenAddedIndex = -1;
   }

   public PythonTokenSource(CommonTokenStream stream, String filename) {
      this(stream, filename, false);
   }

   public PythonTokenSource(CommonTokenStream stream, String filename, boolean single) {
      this.indentStack = new int[100];
      this.sp = -1;
      this.tokens = new Vector();
      this.lastTokenAddedIndex = -1;
      this.stream = stream;
      this.filename = filename;
      this.inSingle = single;
      this.push(0);
   }

   public Token nextToken() {
      if (this.tokens.size() > 0) {
         Token t = (Token)this.tokens.firstElement();
         this.tokens.removeElementAt(0);
         return t;
      } else {
         this.insertImaginaryIndentDedentTokens();
         return this.nextToken();
      }
   }

   private void generateNewline(Token t) {
      CommonToken newline = new CommonToken(7, "\n");
      newline.setLine(t.getLine());
      newline.setCharPositionInLine(t.getCharPositionInLine());
      this.tokens.addElement(newline);
   }

   private void handleEOF(CommonToken eof, CommonToken prev) {
      if (prev != null) {
         eof.setStartIndex(prev.getStopIndex());
         eof.setStopIndex(prev.getStopIndex());
         eof.setLine(prev.getLine());
         eof.setCharPositionInLine(prev.getCharPositionInLine());
      }

   }

   protected void insertImaginaryIndentDedentTokens() {
      Token t = this.stream.LT(1);
      this.stream.consume();
      Token newline;
      if (t.getType() == -1) {
         newline = this.stream.LT(-1);
         this.handleEOF((CommonToken)t, (CommonToken)newline);
         if (!this.inSingle) {
            if (newline == null) {
               this.generateNewline(t);
            } else if (newline.getType() == 8) {
               this.handleDedents(-1, (CommonToken)t);
               this.generateNewline(t);
            } else if (newline.getType() != 7) {
               this.generateNewline(t);
               this.handleDedents(-1, (CommonToken)t);
            }
         } else {
            this.handleDedents(-1, (CommonToken)t);
         }

         this.enqueue(t);
      } else if (t.getType() == 7) {
         this.enqueueHiddens(t);
         this.tokens.addElement(t);
         newline = t;
         t = this.stream.LT(1);
         this.stream.consume();
         List commentedNewlines = this.enqueueHiddens(t);
         int cpos = t.getCharPositionInLine();
         if (t.getType() == -1) {
            this.handleEOF((CommonToken)t, (CommonToken)newline);
            cpos = -1;
         } else if (t.getType() == 8) {
            Token next = this.stream.LT(1);
            if (next != null && next.getType() == -1) {
               this.stream.consume();
               return;
            }

            cpos = t.getText().length();
         }

         int lastIndent = this.peek();
         if (cpos > lastIndent) {
            this.handleIndents(cpos, (CommonToken)t);
         } else if (cpos < lastIndent) {
            this.handleDedents(cpos, (CommonToken)t);
         }

         if (t.getType() == -1 && this.inSingle) {
            String newlines = newline.getText();

            for(int i = 1; i < newlines.length(); ++i) {
               this.generateNewline(newline);
            }

            Iterator var10 = commentedNewlines.iterator();

            while(var10.hasNext()) {
               Token c = (Token)var10.next();
               this.generateNewline(c);
            }
         }

         if (t.getType() != 8) {
            this.tokens.addElement(t);
         }
      } else {
         this.enqueue(t);
      }

   }

   private void enqueue(Token t) {
      this.enqueueHiddens(t);
      this.tokens.addElement(t);
   }

   private List enqueueHiddens(Token t) {
      List newlines = new ArrayList();
      if (this.inSingle && t.getType() == -1) {
         int k = 1;

         label41:
         while(true) {
            while(true) {
               if (this.stream.size() <= this.lastTokenAddedIndex + k) {
                  break label41;
               }

               Token hidden = this.stream.get(this.lastTokenAddedIndex + k);
               if (hidden.getType() == 96) {
                  String text = hidden.getText();
                  int i = text.indexOf("\n");

                  for(i = text.indexOf("\n", i + 1); i != -1; i = text.indexOf("\n", i + 1)) {
                     newlines.add(hidden);
                     ++this.lastTokenAddedIndex;
                  }

                  ++k;
               } else {
                  if (hidden.getType() == 7) {
                     this.generateNewline(hidden);
                     ++this.lastTokenAddedIndex;
                     break label41;
                  }

                  if (hidden.getType() != 8) {
                     break label41;
                  }

                  ++k;
               }
            }
         }
      }

      List hiddenTokens = this.stream.getTokens(this.lastTokenAddedIndex + 1, t.getTokenIndex() - 1);
      if (hiddenTokens != null) {
         this.tokens.addAll(hiddenTokens);
      }

      this.lastTokenAddedIndex = t.getTokenIndex();
      return newlines;
   }

   private void handleIndents(int cpos, CommonToken t) {
      this.push(cpos);
      CommonToken indent = new CommonToken(4, "");
      indent.setCharPositionInLine(t.getCharPositionInLine());
      indent.setLine(t.getLine());
      indent.setStartIndex(t.getStartIndex() - 1);
      indent.setStopIndex(t.getStartIndex() - 1);
      this.tokens.addElement(indent);
   }

   private void handleDedents(int cpos, CommonToken t) {
      int prevIndex = this.findPreviousIndent(cpos, t);

      for(int d = this.sp - 1; d >= prevIndex; --d) {
         CommonToken dedent = new CommonToken(5, "");
         dedent.setCharPositionInLine(t.getCharPositionInLine());
         dedent.setLine(t.getLine());
         dedent.setStartIndex(t.getStartIndex() - 1);
         dedent.setStopIndex(t.getStartIndex() - 1);
         this.tokens.addElement(dedent);
      }

      this.sp = prevIndex;
   }

   protected void push(int i) {
      if (this.sp >= 100) {
         throw new IllegalStateException("stack overflow");
      } else {
         ++this.sp;
         this.indentStack[this.sp] = i;
      }
   }

   protected int pop() {
      if (this.sp < 0) {
         throw new IllegalStateException("stack underflow");
      } else {
         int top = this.indentStack[this.sp];
         --this.sp;
         return top;
      }
   }

   protected int peek() {
      return this.indentStack[this.sp];
   }

   protected int findPreviousIndent(int i, Token t) {
      for(int j = this.sp - 1; j >= 0; --j) {
         if (this.indentStack[j] == i) {
            return j;
         }
      }

      if (i != -1 && i != -2) {
         ParseException p = new ParseException("unindent does not match any outer indentation level", t.getLine(), t.getCharPositionInLine());
         p.setType(Py.IndentationError);
         throw p;
      } else {
         return 0;
      }
   }

   public String stackString() {
      StringBuffer buf = new StringBuffer();

      for(int j = this.sp; j >= 0; --j) {
         buf.append(" ");
         buf.append(this.indentStack[j]);
      }

      return buf.toString();
   }

   public String getSourceName() {
      return this.filename;
   }
}
