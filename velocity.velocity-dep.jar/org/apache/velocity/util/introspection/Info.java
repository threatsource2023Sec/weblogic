package org.apache.velocity.util.introspection;

public class Info {
   private int line;
   private int column;
   private String templateName;

   public Info(String tn, int l, int c) {
      this.templateName = tn;
      this.line = l;
      this.column = c;
   }

   private Info() {
   }

   public String getTemplateName() {
      return this.templateName;
   }

   public int getLine() {
      return this.line;
   }

   public int getColumn() {
      return this.column;
   }
}
