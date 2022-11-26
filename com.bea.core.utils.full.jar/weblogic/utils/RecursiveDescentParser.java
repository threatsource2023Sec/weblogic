package weblogic.utils;

import java.util.EmptyStackException;

public abstract class RecursiveDescentParser {
   public static final boolean debug = false;
   protected char[] buf;
   protected int idx;
   protected int peek;
   private final IndexStack idxStack = new IndexStack();
   protected String sval;
   protected Object oval;
   protected int tok_type;
   protected boolean skipWhiteSpace = false;

   public void setSkipWhiteSpace(boolean b) {
      this.skipWhiteSpace = b;
   }

   protected final void startMatch() {
      if (this.skipWhiteSpace) {
         this.skipWhiteSpace = false;
         this.skipWhiteSpace();
         this.skipWhiteSpace = true;
      }

      this.idxStack.push(this.idx);
   }

   protected final void startMatch(String s) {
      if (this.skipWhiteSpace) {
         this.skipWhiteSpace = false;
         this.skipWhiteSpace();
         this.skipWhiteSpace = true;
      }

      this.idxStack.push(this.idx);
   }

   protected String capture(int start, int end) {
      return new String(this.buf, start, end - start);
   }

   protected final boolean success() {
      int start = this.idxStack.pop();
      this.sval = new String(this.buf, start, this.peek - start);
      this.idx = this.peek;
      return true;
   }

   protected final boolean success(String s) {
      this.idxStack.pop();
      this.sval = s;
      this.idx = this.peek;
      return true;
   }

   protected final boolean failure() {
      this.idx = this.idxStack.pop();
      this.peek = this.idx;
      return false;
   }

   protected final boolean match(char c) {
      this.startMatch();
      if (this.peek == this.buf.length) {
         return this.failure();
      } else {
         return this.buf[this.peek++] == c ? this.success() : this.failure();
      }
   }

   protected final boolean notMatch(char c) {
      this.startMatch();
      if (this.peek == this.buf.length) {
         return this.failure();
      } else {
         return this.buf[this.peek++] != c ? this.success() : this.failure();
      }
   }

   protected final boolean matchIgnoreCase(char c) {
      this.startMatch();
      if (this.peek == this.buf.length) {
         return this.failure();
      } else {
         return this.lc(this.buf[this.peek++]) == this.lc(c) ? this.success() : this.failure();
      }
   }

   protected final boolean match(String s) {
      this.startMatch();
      int len = s.length();
      if (this.peek + len >= this.buf.length) {
         return this.failure();
      } else {
         if (this.buf[this.peek] == s.charAt(0) && this.buf.length > this.peek + len) {
            String inBuf = new String(this.buf, this.peek, len);
            if (inBuf.equals(s)) {
               this.peek += len;
               return this.success();
            }
         }

         return this.failure();
      }
   }

   protected final boolean matchIgnoreCase(String s) {
      this.startMatch();
      int len = s.length();
      if (this.peek + len >= this.buf.length) {
         return this.failure();
      } else {
         if (this.lc(this.buf[this.peek]) == this.lc(s.charAt(0)) && this.buf.length > this.peek + len) {
            String inBuf = new String(this.buf, this.peek, len);
            if (inBuf.equalsIgnoreCase(s)) {
               this.peek += len;
               return this.success();
            }
         }

         return this.failure();
      }
   }

   protected final boolean lookingAt(String s) {
      int len = s.length();
      if (this.peek + len >= this.buf.length) {
         return false;
      } else {
         if (this.buf[this.peek] == s.charAt(0) && this.buf.length > this.peek + len) {
            String inBuf = new String(this.buf, this.peek, len);
            if (inBuf.equals(s)) {
               return true;
            }
         }

         return false;
      }
   }

   protected final boolean lookingAtIgnoreCase(String s) {
      int len = s.length();
      if (this.peek + len >= this.buf.length) {
         return false;
      } else {
         if (this.lc(this.buf[this.peek]) == this.lc(s.charAt(0)) && this.buf.length > this.peek + len) {
            String inBuf = new String(this.buf, this.peek, len);
            if (inBuf.equalsIgnoreCase(s)) {
               return true;
            }
         }

         return false;
      }
   }

   protected final int peek() {
      return this.buf[this.peek];
   }

   protected final boolean eof() {
      if (this.skipWhiteSpace) {
         this.skipWhiteSpace = false;
         this.skipWhiteSpace();
         this.skipWhiteSpace = true;
      }

      return this.idx == this.buf.length;
   }

   protected void skipWhiteSpace() {
      while(true) {
         if (this.peek < this.buf.length) {
            switch (this.buf[this.peek]) {
               case '\t':
               case '\n':
               case '\r':
               case ' ':
                  ++this.peek;
                  continue;
            }
         }

         this.idx = this.peek;
         return;
      }
   }

   private String niceChar(char c) {
      switch (c) {
         case '\t':
            return "\\t";
         case '\n':
            return "\\n";
         case '\r':
            return "\\r";
         case ' ':
            return "SPC";
         default:
            return String.valueOf(c);
      }
   }

   private char lc(char c) {
      return Character.toLowerCase(c);
   }

   protected final void say(String s) {
      int level = this.idxStack.depth();

      for(int i = 0; i < level; ++i) {
         System.out.print("| ");
      }

      System.out.println(s);
   }

   private static final class IndexStack {
      private int[] stack;
      private int sp;

      IndexStack() {
         this(10);
      }

      IndexStack(int depth) {
         this.sp = 0;
         this.stack = new int[depth];
      }

      public void push(int num) {
         try {
            this.stack[this.sp] = num;
         } catch (ArrayIndexOutOfBoundsException var5) {
            int len = this.stack.length;
            int[] newstack = new int[len * 2];
            System.arraycopy(this.stack, 0, newstack, 0, len);
            this.stack = newstack;
            this.stack[this.sp] = num;
         }

         ++this.sp;
      }

      public int pop() throws EmptyStackException {
         try {
            return this.stack[--this.sp];
         } catch (ArrayIndexOutOfBoundsException var2) {
            throw new EmptyStackException();
         }
      }

      public int depth() {
         return this.sp;
      }

      public String toString() {
         StringBuffer sb = new StringBuffer("stack: ");

         for(int i = 0; i < this.sp; ++i) {
            sb.append(this.stack[i]).append(",");
         }

         return sb.toString().substring(0, sb.length() - 1);
      }
   }
}
