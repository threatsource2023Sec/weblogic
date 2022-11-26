package antlr.preprocessor;

import antlr.collections.impl.IndexedVector;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

public class GrammarFile {
   protected String fileName;
   protected String headerAction = "";
   protected IndexedVector options;
   protected IndexedVector grammars;
   protected boolean expanded = false;
   protected antlr.Tool tool;

   public GrammarFile(antlr.Tool var1, String var2) {
      this.fileName = var2;
      this.grammars = new IndexedVector();
      this.tool = var1;
   }

   public void addGrammar(Grammar var1) {
      this.grammars.appendElement(var1.getName(), var1);
   }

   public void generateExpandedFile() throws IOException {
      if (this.expanded) {
         String var1 = this.nameForExpandedGrammarFile(this.getName());
         PrintWriter var2 = this.tool.openOutputFile(var1);
         var2.println(this.toString());
         var2.close();
      }
   }

   public IndexedVector getGrammars() {
      return this.grammars;
   }

   public String getName() {
      return this.fileName;
   }

   public String nameForExpandedGrammarFile(String var1) {
      return this.expanded ? "expanded" + this.tool.fileMinusPath(var1) : var1;
   }

   public void setExpanded(boolean var1) {
      this.expanded = var1;
   }

   public void addHeaderAction(String var1) {
      this.headerAction = this.headerAction + var1 + System.getProperty("line.separator");
   }

   public void setOptions(IndexedVector var1) {
      this.options = var1;
   }

   public String toString() {
      String var1 = this.headerAction == null ? "" : this.headerAction;
      String var2 = this.options == null ? "" : Hierarchy.optionsToString(this.options);
      StringBuffer var3 = new StringBuffer(10000);
      var3.append(var1);
      var3.append(var2);
      Enumeration var4 = this.grammars.elements();

      while(var4.hasMoreElements()) {
         Grammar var5 = (Grammar)var4.nextElement();
         var3.append(var5.toString());
      }

      return var3.toString();
   }
}
