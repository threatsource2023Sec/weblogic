package weblogic.utils.io;

import java.io.PrintWriter;
import java.io.Writer;

public final class IndentingPrintWriter extends PrintWriter {
   private static final String DEFAULT_INDENTATION = "  ";
   private final String indent;
   private int indentLevel;

   public IndentingPrintWriter(Writer w, String s) {
      super(w);
      this.indent = s;
      this.indentLevel = 0;
   }

   public int getIndentLevel() {
      return this.indentLevel;
   }

   public void setIndentLevel(int i) {
      this.indentLevel = i;
   }

   public void pushIndentLevel() {
      ++this.indentLevel;
   }

   public void popIndentLevel() {
      --this.indentLevel;
   }

   public void println() {
      super.println();

      for(int i = 0; i < this.indentLevel; ++i) {
         this.print(this.indent);
      }

   }
}
