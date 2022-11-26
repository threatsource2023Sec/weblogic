package org.antlr.stringtemplate.language;

import java.io.IOException;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateWriter;

public abstract class Expr {
   protected StringTemplate enclosingTemplate;
   protected String indentation = null;

   public Expr(StringTemplate enclosingTemplate) {
      this.enclosingTemplate = enclosingTemplate;
   }

   public abstract int write(StringTemplate var1, StringTemplateWriter var2) throws IOException;

   public StringTemplate getEnclosingTemplate() {
      return this.enclosingTemplate;
   }

   public String getIndentation() {
      return this.indentation;
   }

   public void setIndentation(String indentation) {
      this.indentation = indentation;
   }
}
