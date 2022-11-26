package org.antlr.stringtemplate.language;

import antlr.CommonToken;
import java.util.List;

public class StringTemplateToken extends CommonToken {
   public List args;

   public StringTemplateToken() {
   }

   public StringTemplateToken(int type, String text) {
      super(type, text);
   }

   public StringTemplateToken(String text) {
      super(text);
   }

   public StringTemplateToken(int type, String text, List args) {
      super(type, text);
      this.args = args;
   }

   public String toString() {
      return super.toString() + "; args=" + this.args;
   }
}
