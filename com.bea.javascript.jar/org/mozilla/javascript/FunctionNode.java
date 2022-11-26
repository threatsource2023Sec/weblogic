package org.mozilla.javascript;

public class FunctionNode extends Node {
   public static final byte FUNCTION_STATEMENT = 1;
   public static final byte FUNCTION_EXPRESSION = 2;
   public static final byte FUNCTION_EXPRESSION_STATEMENT = 3;
   protected VariableTable itsVariableTable = new VariableTable();
   protected boolean itsNeedsActivation;
   protected boolean itsCheckThis;
   protected byte itsFunctionType;

   public FunctionNode(String var1, Node var2, Node var3) {
      super(110, var2, var3, (Object)var1);
   }

   public boolean getCheckThis() {
      return this.itsCheckThis;
   }

   public String getFunctionName() {
      return this.getString();
   }

   public byte getFunctionType() {
      return this.itsFunctionType;
   }

   public VariableTable getVariableTable() {
      return this.itsVariableTable;
   }

   public boolean requiresActivation() {
      return this.itsNeedsActivation;
   }

   public void setCheckThis(boolean var1) {
      this.itsCheckThis = var1;
   }

   public void setFunctionType(byte var1) {
      this.itsFunctionType = var1;
   }

   public boolean setRequiresActivation(boolean var1) {
      return this.itsNeedsActivation = var1;
   }
}
