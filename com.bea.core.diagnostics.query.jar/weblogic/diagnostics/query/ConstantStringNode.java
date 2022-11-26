package weblogic.diagnostics.query;

import antlr.Token;

public final class ConstantStringNode extends AtomNode {
   public ConstantStringNode() {
      this.initNode();
   }

   public ConstantStringNode(Token tok) {
      super(tok);
      this.initNode();
   }

   private void initNode() {
      this.javaType = 0;
   }

   public Object getValue() throws UnknownVariableException {
      return this.getString();
   }

   public String getString() throws UnknownVariableException {
      return this.getText();
   }

   public int getIntValue() throws UnknownVariableException {
      throw new UnknownVariableException(DTF.getMismatchedNodeTypeMsg(this.getText(), Integer.TYPE.getName()));
   }

   public long getLongValue() throws UnknownVariableException {
      throw new UnknownVariableException(DTF.getMismatchedNodeTypeMsg(this.getText(), Long.TYPE.getName()));
   }

   public float getFloatValue() throws UnknownVariableException {
      throw new UnknownVariableException(DTF.getMismatchedNodeTypeMsg(this.getText(), Float.TYPE.getName()));
   }

   public double getDoubleValue() throws UnknownVariableException {
      throw new UnknownVariableException(DTF.getMismatchedNodeTypeMsg(this.getText(), Double.TYPE.getName()));
   }
}
