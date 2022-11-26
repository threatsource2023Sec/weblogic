package org.python.antlr.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TokenRewriteStream extends CommonTokenStream {
   public static final String DEFAULT_PROGRAM_NAME = "default";
   public static final int PROGRAM_INIT_SIZE = 100;
   public static final int MIN_TOKEN_INDEX = 0;
   protected Map programs = null;
   protected Map lastRewriteTokenIndexes = null;

   public TokenRewriteStream() {
      this.init();
   }

   protected void init() {
      this.programs = new HashMap();
      this.programs.put("default", new ArrayList(100));
      this.lastRewriteTokenIndexes = new HashMap();
   }

   public TokenRewriteStream(TokenSource tokenSource) {
      super(tokenSource);
      this.init();
   }

   public TokenRewriteStream(TokenSource tokenSource, int channel) {
      super(tokenSource, channel);
      this.init();
   }

   public void rollback(int instructionIndex) {
      this.rollback("default", instructionIndex);
   }

   public void rollback(String programName, int instructionIndex) {
      List is = (List)this.programs.get(programName);
      if (is != null) {
         this.programs.put(programName, is.subList(0, instructionIndex));
      }

   }

   public void deleteProgram() {
      this.deleteProgram("default");
   }

   public void deleteProgram(String programName) {
      this.rollback(programName, 0);
   }

   public void insertAfter(Token t, Object text) {
      this.insertAfter("default", t, text);
   }

   public void insertAfter(int index, Object text) {
      this.insertAfter("default", index, text);
   }

   public void insertAfter(String programName, Token t, Object text) {
      this.insertAfter(programName, t.getTokenIndex(), text);
   }

   public void insertAfter(String programName, int index, Object text) {
      this.insertBefore(programName, index + 1, text);
   }

   public void insertBefore(Token t, Object text) {
      this.insertBefore("default", t, text);
   }

   public void insertBefore(int index, Object text) {
      this.insertBefore("default", index, text);
   }

   public void insertBefore(String programName, Token t, Object text) {
      this.insertBefore(programName, t.getTokenIndex(), text);
   }

   public void insertBefore(String programName, int index, Object text) {
      RewriteOperation op = new InsertBeforeOp(index, text);
      List rewrites = this.getProgram(programName);
      op.instructionIndex = rewrites.size();
      rewrites.add(op);
   }

   public void replace(int index, Object text) {
      this.replace("default", index, index, text);
   }

   public void replace(int from, int to, Object text) {
      this.replace("default", from, to, text);
   }

   public void replace(Token indexT, Object text) {
      this.replace("default", indexT, indexT, text);
   }

   public void replace(Token from, Token to, Object text) {
      this.replace("default", from, to, text);
   }

   public void replace(String programName, int from, int to, Object text) {
      if (from <= to && from >= 0 && to >= 0 && to < this.tokens.size()) {
         RewriteOperation op = new ReplaceOp(from, to, text);
         List rewrites = this.getProgram(programName);
         op.instructionIndex = rewrites.size();
         rewrites.add(op);
      } else {
         throw new IllegalArgumentException("replace: range invalid: " + from + ".." + to + "(size=" + this.tokens.size() + ")");
      }
   }

   public void replace(String programName, Token from, Token to, Object text) {
      this.replace(programName, from.getTokenIndex(), to.getTokenIndex(), text);
   }

   public void delete(int index) {
      this.delete("default", index, index);
   }

   public void delete(int from, int to) {
      this.delete("default", from, to);
   }

   public void delete(Token indexT) {
      this.delete("default", indexT, indexT);
   }

   public void delete(Token from, Token to) {
      this.delete("default", from, to);
   }

   public void delete(String programName, int from, int to) {
      this.replace(programName, from, to, (Object)null);
   }

   public void delete(String programName, Token from, Token to) {
      this.replace(programName, from, to, (Object)null);
   }

   public int getLastRewriteTokenIndex() {
      return this.getLastRewriteTokenIndex("default");
   }

   protected int getLastRewriteTokenIndex(String programName) {
      Integer I = (Integer)this.lastRewriteTokenIndexes.get(programName);
      return I == null ? -1 : I;
   }

   protected void setLastRewriteTokenIndex(String programName, int i) {
      this.lastRewriteTokenIndexes.put(programName, new Integer(i));
   }

   protected List getProgram(String name) {
      List is = (List)this.programs.get(name);
      if (is == null) {
         is = this.initializeProgram(name);
      }

      return is;
   }

   private List initializeProgram(String name) {
      List is = new ArrayList(100);
      this.programs.put(name, is);
      return is;
   }

   public String toOriginalString() {
      return this.toOriginalString(0, this.size() - 1);
   }

   public String toOriginalString(int start, int end) {
      StringBuffer buf = new StringBuffer();

      for(int i = start; i >= 0 && i <= end && i < this.tokens.size(); ++i) {
         buf.append(this.get(i).getText());
      }

      return buf.toString();
   }

   public String toString() {
      return this.toString(0, this.size() - 1);
   }

   public String toString(String programName) {
      return this.toString(programName, 0, this.size() - 1);
   }

   public String toString(int start, int end) {
      return this.toString("default", start, end);
   }

   public String toString(String programName, int start, int end) {
      List rewrites = (List)this.programs.get(programName);
      if (end > this.tokens.size() - 1) {
         end = this.tokens.size() - 1;
      }

      if (start < 0) {
         start = 0;
      }

      if (rewrites != null && rewrites.size() != 0) {
         StringBuffer buf = new StringBuffer();
         Map indexToOp = this.reduceToSingleOperationPerIndex(rewrites);
         int i = start;

         while(i <= end && i < this.tokens.size()) {
            RewriteOperation op = (RewriteOperation)indexToOp.get(new Integer(i));
            indexToOp.remove(new Integer(i));
            Token t = (Token)this.tokens.get(i);
            if (op == null) {
               buf.append(t.getText());
               ++i;
            } else {
               i = op.execute(buf);
            }
         }

         if (end == this.tokens.size() - 1) {
            Iterator it = indexToOp.values().iterator();

            while(it.hasNext()) {
               RewriteOperation op = (RewriteOperation)it.next();
               if (op.index >= this.tokens.size() - 1) {
                  buf.append(op.text);
               }
            }
         }

         return buf.toString();
      } else {
         return this.toOriginalString(start, end);
      }
   }

   protected Map reduceToSingleOperationPerIndex(List rewrites) {
      int i;
      RewriteOperation op;
      List prevInserts;
      int j;
      InsertBeforeOp prevIop;
      ReplaceOp rop;
      List prevReplaces;
      int j;
      for(i = 0; i < rewrites.size(); ++i) {
         op = (RewriteOperation)rewrites.get(i);
         if (op != null && op instanceof ReplaceOp) {
            ReplaceOp rop = (ReplaceOp)rewrites.get(i);
            prevInserts = this.getKindOfOps(rewrites, InsertBeforeOp.class, i);

            for(j = 0; j < prevInserts.size(); ++j) {
               prevIop = (InsertBeforeOp)prevInserts.get(j);
               if (prevIop.index >= rop.index && prevIop.index <= rop.lastIndex) {
                  rewrites.set(prevIop.instructionIndex, (Object)null);
               }
            }

            prevReplaces = this.getKindOfOps(rewrites, ReplaceOp.class, i);

            for(j = 0; j < prevReplaces.size(); ++j) {
               rop = (ReplaceOp)prevReplaces.get(j);
               if (rop.index >= rop.index && rop.lastIndex <= rop.lastIndex) {
                  rewrites.set(rop.instructionIndex, (Object)null);
               } else {
                  boolean disjoint = rop.lastIndex < rop.index || rop.index > rop.lastIndex;
                  boolean same = rop.index == rop.index && rop.lastIndex == rop.lastIndex;
                  if (!disjoint && !same) {
                     throw new IllegalArgumentException("replace op boundaries of " + rop + " overlap with previous " + rop);
                  }
               }
            }
         }
      }

      for(i = 0; i < rewrites.size(); ++i) {
         op = (RewriteOperation)rewrites.get(i);
         if (op != null && op instanceof InsertBeforeOp) {
            InsertBeforeOp iop = (InsertBeforeOp)rewrites.get(i);
            prevInserts = this.getKindOfOps(rewrites, InsertBeforeOp.class, i);

            for(j = 0; j < prevInserts.size(); ++j) {
               prevIop = (InsertBeforeOp)prevInserts.get(j);
               if (prevIop.index == iop.index) {
                  iop.text = this.catOpText(iop.text, prevIop.text);
                  rewrites.set(prevIop.instructionIndex, (Object)null);
               }
            }

            prevReplaces = this.getKindOfOps(rewrites, ReplaceOp.class, i);

            for(j = 0; j < prevReplaces.size(); ++j) {
               rop = (ReplaceOp)prevReplaces.get(j);
               if (iop.index == rop.index) {
                  rop.text = this.catOpText(iop.text, rop.text);
                  rewrites.set(i, (Object)null);
               } else if (iop.index >= rop.index && iop.index <= rop.lastIndex) {
                  throw new IllegalArgumentException("insert op " + iop + " within boundaries of previous " + rop);
               }
            }
         }
      }

      Map m = new HashMap();

      for(int i = 0; i < rewrites.size(); ++i) {
         RewriteOperation op = (RewriteOperation)rewrites.get(i);
         if (op != null) {
            if (m.get(new Integer(op.index)) != null) {
               throw new Error("should only be one op per index");
            }

            m.put(new Integer(op.index), op);
         }
      }

      return m;
   }

   protected String catOpText(Object a, Object b) {
      String x = "";
      String y = "";
      if (a != null) {
         x = a.toString();
      }

      if (b != null) {
         y = b.toString();
      }

      return x + y;
   }

   protected List getKindOfOps(List rewrites, Class kind) {
      return this.getKindOfOps(rewrites, kind, rewrites.size());
   }

   protected List getKindOfOps(List rewrites, Class kind, int before) {
      List ops = new ArrayList();

      for(int i = 0; i < before && i < rewrites.size(); ++i) {
         RewriteOperation op = (RewriteOperation)rewrites.get(i);
         if (op != null && op.getClass() == kind) {
            ops.add(op);
         }
      }

      return ops;
   }

   public String toDebugString() {
      return this.toDebugString(0, this.size() - 1);
   }

   public String toDebugString(int start, int end) {
      StringBuffer buf = new StringBuffer();

      for(int i = start; i >= 0 && i <= end && i < this.tokens.size(); ++i) {
         buf.append(this.get(i));
      }

      return buf.toString();
   }

   class DeleteOp extends ReplaceOp {
      public DeleteOp(int from, int to) {
         super(from, to, (Object)null);
      }

      public String toString() {
         return "<DeleteOp@" + this.index + ".." + this.lastIndex + ">";
      }
   }

   class ReplaceOp extends RewriteOperation {
      protected int lastIndex;

      public ReplaceOp(int from, int to, Object text) {
         super(from, text);
         this.lastIndex = to;
      }

      public int execute(StringBuffer buf) {
         if (this.text != null) {
            buf.append(this.text);
         }

         return this.lastIndex + 1;
      }

      public String toString() {
         return "<ReplaceOp@" + this.index + ".." + this.lastIndex + ":\"" + this.text + "\">";
      }
   }

   class InsertBeforeOp extends RewriteOperation {
      public InsertBeforeOp(int index, Object text) {
         super(index, text);
      }

      public int execute(StringBuffer buf) {
         buf.append(this.text);
         buf.append(((Token)TokenRewriteStream.this.tokens.get(this.index)).getText());
         return this.index + 1;
      }
   }

   class RewriteOperation {
      protected int instructionIndex;
      protected int index;
      protected Object text;

      protected RewriteOperation(int index, Object text) {
         this.index = index;
         this.text = text;
      }

      public int execute(StringBuffer buf) {
         return this.index;
      }

      public String toString() {
         String opName = this.getClass().getName();
         int $index = opName.indexOf(36);
         opName = opName.substring($index + 1, opName.length());
         return "<" + opName + "@" + this.index + ":\"" + this.text + "\">";
      }
   }
}
