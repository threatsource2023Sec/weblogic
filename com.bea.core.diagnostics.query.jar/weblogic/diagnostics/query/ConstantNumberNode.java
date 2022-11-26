package weblogic.diagnostics.query;

import antlr.Token;

public final class ConstantNumberNode extends AtomNode {
   private Number cache = new Double(Double.NaN);

   public ConstantNumberNode() {
      this.initNumberCache();
   }

   public ConstantNumberNode(Token tok) {
      super(tok);
      this.initNumberCache();
   }

   public Object getValue() throws UnknownVariableException {
      return this.cache;
   }

   public String getString() throws UnknownVariableException {
      return this.getText();
   }

   public int getIntValue() throws UnknownVariableException {
      return this.cache.intValue();
   }

   public long getLongValue() throws UnknownVariableException {
      return this.cache.longValue();
   }

   public float getFloatValue() throws UnknownVariableException {
      return this.cache.floatValue();
   }

   public double getDoubleValue() throws UnknownVariableException {
      return this.cache.doubleValue();
   }

   private void initNumberCache() {
      String nodeText = this.getText();
      int len = nodeText.length();
      char c = nodeText.charAt(len - 1);
      if ((c == 'F' || c == 'f') && len > 1) {
         this.cache = Float.valueOf(nodeText.substring(0, len - 1));
         this.javaType = 3;
      } else if ((c == 'L' || c == 'l') && len > 1) {
         this.cache = Long.valueOf(nodeText.substring(0, len - 1));
         this.javaType = 2;
      } else if (nodeText.indexOf(".") < 0) {
         this.cache = Integer.valueOf(nodeText);
         this.javaType = 1;
      } else {
         this.cache = Double.valueOf(nodeText);
         this.javaType = 4;
      }

   }
}
