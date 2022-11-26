package antlr;

import antlr.collections.impl.BitSet;
import antlr.collections.impl.Vector;

public class Lookahead implements Cloneable {
   BitSet fset;
   String cycle;
   BitSet epsilonDepth;
   boolean hasEpsilon;

   public Lookahead() {
      this.hasEpsilon = false;
      this.fset = new BitSet();
   }

   public Lookahead(BitSet var1) {
      this.hasEpsilon = false;
      this.fset = var1;
   }

   public Lookahead(String var1) {
      this();
      this.cycle = var1;
   }

   public Object clone() {
      Lookahead var1 = null;

      try {
         var1 = (Lookahead)super.clone();
         var1.fset = (BitSet)this.fset.clone();
         var1.cycle = this.cycle;
         if (this.epsilonDepth != null) {
            var1.epsilonDepth = (BitSet)this.epsilonDepth.clone();
         }

         return var1;
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }
   }

   public void combineWith(Lookahead var1) {
      if (this.cycle == null) {
         this.cycle = var1.cycle;
      }

      if (var1.containsEpsilon()) {
         this.hasEpsilon = true;
      }

      if (this.epsilonDepth != null) {
         if (var1.epsilonDepth != null) {
            this.epsilonDepth.orInPlace(var1.epsilonDepth);
         }
      } else if (var1.epsilonDepth != null) {
         this.epsilonDepth = (BitSet)var1.epsilonDepth.clone();
      }

      this.fset.orInPlace(var1.fset);
   }

   public boolean containsEpsilon() {
      return this.hasEpsilon;
   }

   public Lookahead intersection(Lookahead var1) {
      Lookahead var2 = new Lookahead(this.fset.and(var1.fset));
      if (this.hasEpsilon && var1.hasEpsilon) {
         var2.setEpsilon();
      }

      return var2;
   }

   public boolean nil() {
      return this.fset.nil() && !this.hasEpsilon;
   }

   public static Lookahead of(int var0) {
      Lookahead var1 = new Lookahead();
      var1.fset.add(var0);
      return var1;
   }

   public void resetEpsilon() {
      this.hasEpsilon = false;
   }

   public void setEpsilon() {
      this.hasEpsilon = true;
   }

   public String toString() {
      String var1 = "";
      String var3 = "";
      String var4 = "";
      String var2 = this.fset.toString(",");
      if (this.containsEpsilon()) {
         var1 = "+<epsilon>";
      }

      if (this.cycle != null) {
         var3 = "; FOLLOW(" + this.cycle + ")";
      }

      if (this.epsilonDepth != null) {
         var4 = "; depths=" + this.epsilonDepth.toString(",");
      }

      return var2 + var1 + var3 + var4;
   }

   public String toString(String var1, CharFormatter var2) {
      String var3 = "";
      String var5 = "";
      String var6 = "";
      String var4 = this.fset.toString(var1, var2);
      if (this.containsEpsilon()) {
         var3 = "+<epsilon>";
      }

      if (this.cycle != null) {
         var5 = "; FOLLOW(" + this.cycle + ")";
      }

      if (this.epsilonDepth != null) {
         var6 = "; depths=" + this.epsilonDepth.toString(",");
      }

      return var4 + var3 + var5 + var6;
   }

   public String toString(String var1, CharFormatter var2, Grammar var3) {
      return var3 instanceof LexerGrammar ? this.toString(var1, var2) : this.toString(var1, var3.tokenManager.getVocabulary());
   }

   public String toString(String var1, Vector var2) {
      String var4 = "";
      String var5 = "";
      String var3 = this.fset.toString(var1, var2);
      if (this.cycle != null) {
         var4 = "; FOLLOW(" + this.cycle + ")";
      }

      if (this.epsilonDepth != null) {
         var5 = "; depths=" + this.epsilonDepth.toString(",");
      }

      return var3 + var4 + var5;
   }
}
