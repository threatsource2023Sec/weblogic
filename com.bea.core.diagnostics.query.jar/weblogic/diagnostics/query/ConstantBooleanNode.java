package weblogic.diagnostics.query;

import antlr.Token;

public final class ConstantBooleanNode extends AtomNode {
   private Boolean value;

   public ConstantBooleanNode() {
      this.value = Boolean.FALSE;
      this.initNode();
   }

   public ConstantBooleanNode(Token tok) {
      super(tok);
      this.value = Boolean.FALSE;
      this.initNode();
   }

   private void initNode() {
      this.javaType = 5;
      this.value = Boolean.valueOf(this.getText());
   }

   public Object getValue() throws UnknownVariableException {
      return this.value;
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
