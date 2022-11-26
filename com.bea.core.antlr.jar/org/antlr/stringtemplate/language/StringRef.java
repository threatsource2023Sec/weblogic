package org.antlr.stringtemplate.language;

import java.io.IOException;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateWriter;

public class StringRef extends Expr {
   String str;

   public StringRef(StringTemplate enclosingTemplate, String str) {
      super(enclosingTemplate);
      this.str = str;
   }

   public int write(StringTemplate self, StringTemplateWriter out) throws IOException {
      if (this.str != null) {
         int n = out.write(this.str);
         return n;
      } else {
         return 0;
      }
   }

   public String toString() {
      return this.str != null ? this.str : "";
   }
}
