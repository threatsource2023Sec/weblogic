package antlr;

import antlr.ASdebug.ASDebugStream;
import antlr.ASdebug.IASDebugStream;
import antlr.ASdebug.TokenOffsetInfo;
import antlr.collections.impl.BitSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TokenStreamRewriteEngine implements TokenStream, IASDebugStream {
   public static final int MIN_TOKEN_INDEX = 0;
   public static final String DEFAULT_PROGRAM_NAME = "default";
   public static final int PROGRAM_INIT_SIZE = 100;
   protected List tokens;
   protected Map programs;
   protected Map lastRewriteTokenIndexes;
   protected int index;
   protected TokenStream stream;
   protected BitSet discardMask;

   public TokenStreamRewriteEngine(TokenStream var1) {
      this(var1, 1000);
   }

   public TokenStreamRewriteEngine(TokenStream var1, int var2) {
      this.programs = null;
      this.lastRewriteTokenIndexes = null;
      this.index = 0;
      this.discardMask = new BitSet();
      this.stream = var1;
      this.tokens = new ArrayList(var2);
      this.programs = new HashMap();
      this.programs.put("default", new ArrayList(100));
      this.lastRewriteTokenIndexes = new HashMap();
   }

   public Token nextToken() throws TokenStreamException {
      TokenWithIndex var1;
      do {
         var1 = (TokenWithIndex)this.stream.nextToken();
         if (var1 != null) {
            var1.setIndex(this.index);
            if (var1.getType() != 1) {
               this.tokens.add(var1);
            }

            ++this.index;
         }
      } while(var1 != null && this.discardMask.member(var1.getType()));

      return var1;
   }

   public void rollback(int var1) {
      this.rollback("default", var1);
   }

   public void rollback(String var1, int var2) {
      List var3 = (List)this.programs.get(var1);
      if (var3 != null) {
         this.programs.put(var1, var3.subList(0, var2));
      }

   }

   public void deleteProgram() {
      this.deleteProgram("default");
   }

   public void deleteProgram(String var1) {
      this.rollback(var1, 0);
   }

   protected void addToSortedRewriteList(RewriteOperation var1) {
      this.addToSortedRewriteList("default", var1);
   }

   protected void addToSortedRewriteList(String var1, RewriteOperation var2) {
      List var3 = this.getProgram(var1);
      Comparator var4 = new Comparator() {
         public int compare(Object var1, Object var2) {
            RewriteOperation var3 = (RewriteOperation)var1;
            RewriteOperation var4 = (RewriteOperation)var2;
            if (var3.index < var4.index) {
               return -1;
            } else {
               return var3.index > var4.index ? 1 : 0;
            }
         }
      };
      int var5 = Collections.binarySearch(var3, var2, var4);
      if (var5 < 0) {
         var3.add(-var5 - 1, var2);
      } else {
         while(true) {
            if (var5 >= 0) {
               RewriteOperation var6 = (RewriteOperation)var3.get(var5);
               if (var6.index >= var2.index) {
                  --var5;
                  continue;
               }
            }

            ++var5;
            if (var2 instanceof ReplaceOp) {
               boolean var9 = false;

               int var7;
               for(var7 = var5; var7 < var3.size(); ++var7) {
                  RewriteOperation var8 = (RewriteOperation)var3.get(var5);
                  if (var8.index != var2.index) {
                     break;
                  }

                  if (var8 instanceof ReplaceOp) {
                     var3.set(var5, var2);
                     var9 = true;
                     break;
                  }
               }

               if (!var9) {
                  var3.add(var7, var2);
               }
            } else {
               var3.add(var5, var2);
            }
            break;
         }
      }

   }

   public void insertAfter(Token var1, String var2) {
      this.insertAfter("default", var1, var2);
   }

   public void insertAfter(int var1, String var2) {
      this.insertAfter("default", var1, var2);
   }

   public void insertAfter(String var1, Token var2, String var3) {
      this.insertAfter(var1, ((TokenWithIndex)var2).getIndex(), var3);
   }

   public void insertAfter(String var1, int var2, String var3) {
      this.insertBefore(var1, var2 + 1, var3);
   }

   public void insertBefore(Token var1, String var2) {
      this.insertBefore("default", var1, var2);
   }

   public void insertBefore(int var1, String var2) {
      this.insertBefore("default", var1, var2);
   }

   public void insertBefore(String var1, Token var2, String var3) {
      this.insertBefore(var1, ((TokenWithIndex)var2).getIndex(), var3);
   }

   public void insertBefore(String var1, int var2, String var3) {
      this.addToSortedRewriteList(var1, new InsertBeforeOp(var2, var3));
   }

   public void replace(int var1, String var2) {
      this.replace("default", var1, var1, var2);
   }

   public void replace(int var1, int var2, String var3) {
      this.replace("default", var1, var2, var3);
   }

   public void replace(Token var1, String var2) {
      this.replace("default", var1, var1, var2);
   }

   public void replace(Token var1, Token var2, String var3) {
      this.replace("default", var1, var2, var3);
   }

   public void replace(String var1, int var2, int var3, String var4) {
      this.addToSortedRewriteList(new ReplaceOp(var2, var3, var4));
   }

   public void replace(String var1, Token var2, Token var3, String var4) {
      this.replace(var1, ((TokenWithIndex)var2).getIndex(), ((TokenWithIndex)var3).getIndex(), var4);
   }

   public void delete(int var1) {
      this.delete("default", var1, var1);
   }

   public void delete(int var1, int var2) {
      this.delete("default", var1, var2);
   }

   public void delete(Token var1) {
      this.delete("default", var1, var1);
   }

   public void delete(Token var1, Token var2) {
      this.delete("default", var1, var2);
   }

   public void delete(String var1, int var2, int var3) {
      this.replace(var1, var2, var3, (String)null);
   }

   public void delete(String var1, Token var2, Token var3) {
      this.replace(var1, var2, var3, (String)null);
   }

   public void discard(int var1) {
      this.discardMask.add(var1);
   }

   public TokenWithIndex getToken(int var1) {
      return (TokenWithIndex)this.tokens.get(var1);
   }

   public int getTokenStreamSize() {
      return this.tokens.size();
   }

   public String toOriginalString() {
      return this.toOriginalString(0, this.getTokenStreamSize() - 1);
   }

   public String toOriginalString(int var1, int var2) {
      StringBuffer var3 = new StringBuffer();

      for(int var4 = var1; var4 >= 0 && var4 <= var2 && var4 < this.tokens.size(); ++var4) {
         var3.append(this.getToken(var4).getText());
      }

      return var3.toString();
   }

   public String toString() {
      return this.toString(0, this.getTokenStreamSize() - 1);
   }

   public String toString(String var1) {
      return this.toString(var1, 0, this.getTokenStreamSize() - 1);
   }

   public String toString(int var1, int var2) {
      return this.toString("default", var1, var2);
   }

   public String toString(String var1, int var2, int var3) {
      List var4 = (List)this.programs.get(var1);
      if (var4 != null && var4.size() != 0) {
         StringBuffer var5 = new StringBuffer();
         int var6 = 0;
         int var7 = var2;

         while(var7 >= 0 && var7 <= var3 && var7 < this.tokens.size()) {
            if (var6 < var4.size()) {
               RewriteOperation var8 = (RewriteOperation)var4.get(var6);

               while(var8.index < var7 && var6 < var4.size()) {
                  ++var6;
                  if (var6 < var4.size()) {
                     var8 = (RewriteOperation)var4.get(var6);
                  }
               }

               while(var7 == var8.index && var6 < var4.size()) {
                  var7 = var8.execute(var5);
                  ++var6;
                  if (var6 < var4.size()) {
                     var8 = (RewriteOperation)var4.get(var6);
                  }
               }
            }

            if (var7 <= var3) {
               var5.append(this.getToken(var7).getText());
               ++var7;
            }
         }

         for(int var10 = var6; var10 < var4.size(); ++var10) {
            RewriteOperation var9 = (RewriteOperation)var4.get(var10);
            if (var9.index >= this.size()) {
               var9.execute(var5);
            }
         }

         return var5.toString();
      } else {
         return this.toOriginalString(var2, var3);
      }
   }

   public String toDebugString() {
      return this.toDebugString(0, this.getTokenStreamSize() - 1);
   }

   public String toDebugString(int var1, int var2) {
      StringBuffer var3 = new StringBuffer();

      for(int var4 = var1; var4 >= 0 && var4 <= var2 && var4 < this.tokens.size(); ++var4) {
         var3.append(this.getToken(var4));
      }

      return var3.toString();
   }

   public int getLastRewriteTokenIndex() {
      return this.getLastRewriteTokenIndex("default");
   }

   protected int getLastRewriteTokenIndex(String var1) {
      Integer var2 = (Integer)this.lastRewriteTokenIndexes.get(var1);
      return var2 == null ? -1 : var2;
   }

   protected void setLastRewriteTokenIndex(String var1, int var2) {
      this.lastRewriteTokenIndexes.put(var1, new Integer(var2));
   }

   protected List getProgram(String var1) {
      List var2 = (List)this.programs.get(var1);
      if (var2 == null) {
         var2 = this.initializeProgram(var1);
      }

      return var2;
   }

   private List initializeProgram(String var1) {
      ArrayList var2 = new ArrayList(100);
      this.programs.put(var1, var2);
      return var2;
   }

   public int size() {
      return this.tokens.size();
   }

   public int index() {
      return this.index;
   }

   public String getEntireText() {
      return ASDebugStream.getEntireText(this.stream);
   }

   public TokenOffsetInfo getOffsetInfo(Token var1) {
      return ASDebugStream.getOffsetInfo(this.stream, var1);
   }

   static class DeleteOp extends ReplaceOp {
      public DeleteOp(int var1, int var2) {
         super(var1, var2, (String)null);
      }
   }

   static class ReplaceOp extends RewriteOperation {
      protected int lastIndex;

      public ReplaceOp(int var1, int var2, String var3) {
         super(var1, var3);
         this.lastIndex = var2;
      }

      public int execute(StringBuffer var1) {
         if (this.text != null) {
            var1.append(this.text);
         }

         return this.lastIndex + 1;
      }
   }

   static class InsertBeforeOp extends RewriteOperation {
      public InsertBeforeOp(int var1, String var2) {
         super(var1, var2);
      }

      public int execute(StringBuffer var1) {
         var1.append(this.text);
         return this.index;
      }
   }

   static class RewriteOperation {
      protected int index;
      protected String text;

      protected RewriteOperation(int var1, String var2) {
         this.index = var1;
         this.text = var2;
      }

      public int execute(StringBuffer var1) {
         return this.index;
      }

      public String toString() {
         String var1 = this.getClass().getName();
         int var2 = var1.indexOf(36);
         var1 = var1.substring(var2 + 1, var1.length());
         return var1 + "@" + this.index + '"' + this.text + '"';
      }
   }
}
