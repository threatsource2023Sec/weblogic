package antlr.preprocessor;

import antlr.collections.impl.IndexedVector;
import java.util.Enumeration;

class Rule {
   protected String name;
   protected String block;
   protected String args;
   protected String returnValue;
   protected String throwsSpec;
   protected String initAction;
   protected IndexedVector options;
   protected String visibility;
   protected Grammar enclosingGrammar;
   protected boolean bang = false;

   public Rule(String var1, String var2, IndexedVector var3, Grammar var4) {
      this.name = var1;
      this.block = var2;
      this.options = var3;
      this.setEnclosingGrammar(var4);
   }

   public String getArgs() {
      return this.args;
   }

   public boolean getBang() {
      return this.bang;
   }

   public String getName() {
      return this.name;
   }

   public String getReturnValue() {
      return this.returnValue;
   }

   public String getVisibility() {
      return this.visibility;
   }

   public boolean narrowerVisibility(Rule var1) {
      if (this.visibility.equals("public")) {
         return !var1.equals("public");
      } else if (this.visibility.equals("protected")) {
         return var1.equals("private");
      } else {
         return this.visibility.equals("private") ? false : false;
      }
   }

   public boolean sameSignature(Rule var1) {
      boolean var2 = true;
      boolean var3 = true;
      boolean var4 = true;
      var2 = this.name.equals(var1.getName());
      if (this.args != null) {
         var3 = this.args.equals(var1.getArgs());
      }

      if (this.returnValue != null) {
         var4 = this.returnValue.equals(var1.getReturnValue());
      }

      return var2 && var3 && var4;
   }

   public void setArgs(String var1) {
      this.args = var1;
   }

   public void setBang() {
      this.bang = true;
   }

   public void setEnclosingGrammar(Grammar var1) {
      this.enclosingGrammar = var1;
   }

   public void setInitAction(String var1) {
      this.initAction = var1;
   }

   public void setOptions(IndexedVector var1) {
      this.options = var1;
   }

   public void setReturnValue(String var1) {
      this.returnValue = var1;
   }

   public void setThrowsSpec(String var1) {
      this.throwsSpec = var1;
   }

   public void setVisibility(String var1) {
      this.visibility = var1;
   }

   public String toString() {
      String var1 = "";
      String var2 = this.returnValue == null ? "" : "returns " + this.returnValue;
      String var3 = this.args == null ? "" : this.args;
      String var4 = this.getBang() ? "!" : "";
      var1 = var1 + (this.visibility == null ? "" : this.visibility + " ");
      var1 = var1 + this.name + var4 + var3 + " " + var2 + this.throwsSpec;
      if (this.options != null) {
         var1 = var1 + System.getProperty("line.separator") + "options {" + System.getProperty("line.separator");

         for(Enumeration var5 = this.options.elements(); var5.hasMoreElements(); var1 = var1 + (Option)var5.nextElement() + System.getProperty("line.separator")) {
         }

         var1 = var1 + "}" + System.getProperty("line.separator");
      }

      if (this.initAction != null) {
         var1 = var1 + this.initAction + System.getProperty("line.separator");
      }

      var1 = var1 + this.block;
      return var1;
   }
}
