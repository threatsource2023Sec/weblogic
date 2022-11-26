package weblogic.diagnostics.query;

import antlr.Token;

public final class VariableNode extends AtomNode {
   private VariableResolver variableResolver;
   private QueryExecutionTrace queryExecutionTrace;
   private int variableIndex = -1;

   public VariableNode() {
   }

   public VariableNode(Token tok) {
      super(tok);
   }

   public void setVariableResolver(VariableResolver vr) {
      this.variableResolver = vr;
   }

   public void setQueryExecutionTrace(QueryExecutionTrace queryExecTrace) {
      this.queryExecutionTrace = queryExecTrace;
   }

   public void setVariableIndex(int index) {
      if (queryDebugLogger.isDebugEnabled()) {
         queryDebugLogger.debug("Setting index " + index + " for variable " + this.getText());
      }

      if (index >= 0) {
         this.variableIndex = index;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public Object getValue() throws UnknownVariableException {
      String nodeText = this.getText();
      Object value = null;
      if (this.variableResolver != null) {
         if (this.variableIndex < 0) {
            if (queryDebugLogger.isDebugEnabled()) {
               queryDebugLogger.debug("Resolving variable by name " + nodeText);
            }

            value = this.variableResolver.resolveVariable(nodeText);
         } else {
            if (queryDebugLogger.isDebugEnabled()) {
               queryDebugLogger.debug("Resolving variable by index " + nodeText);
            }

            value = this.variableResolver.resolveVariable(this.variableIndex);
         }

         if (this.queryExecutionTrace != null && value != null) {
            this.queryExecutionTrace.addEvaluatedVariable(nodeText, value);
         }

         return value;
      } else {
         throw new UnknownVariableException(DTF.getUnknownVariableMsg(nodeText));
      }
   }

   public String getString() throws UnknownVariableException {
      String nodeText = this.getText();
      if (this.variableResolver != null) {
         if (this.variableIndex < 0) {
            if (queryDebugLogger.isDebugEnabled()) {
               queryDebugLogger.debug("Resolving variable by name " + nodeText);
            }

            return this.variableResolver.resolveString(nodeText);
         } else {
            if (queryDebugLogger.isDebugEnabled()) {
               queryDebugLogger.debug("Resolving variable by index " + nodeText);
            }

            return this.variableResolver.resolveString(this.variableIndex);
         }
      } else {
         throw new UnknownVariableException(DTF.getUnknownVariableMsg(nodeText));
      }
   }

   public int getIntValue() throws UnknownVariableException {
      String nodeText = this.getText();
      if (this.variableResolver != null) {
         if (this.variableIndex < 0) {
            if (queryDebugLogger.isDebugEnabled()) {
               queryDebugLogger.debug("Resolving variable by name " + nodeText);
            }

            return this.variableResolver.resolveInteger(nodeText);
         } else {
            if (queryDebugLogger.isDebugEnabled()) {
               queryDebugLogger.debug("Resolving variable by index " + nodeText);
            }

            return this.variableResolver.resolveInteger(this.variableIndex);
         }
      } else {
         throw new UnknownVariableException(DTF.getUnknownVariableMsg(nodeText));
      }
   }

   public long getLongValue() throws UnknownVariableException {
      String nodeText = this.getText();
      if (this.variableResolver != null) {
         if (this.variableIndex < 0) {
            if (queryDebugLogger.isDebugEnabled()) {
               queryDebugLogger.debug("Resolving variable by name " + nodeText);
            }

            return this.variableResolver.resolveLong(nodeText);
         } else {
            if (queryDebugLogger.isDebugEnabled()) {
               queryDebugLogger.debug("Resolving variable by index " + nodeText);
            }

            return this.variableResolver.resolveLong(this.variableIndex);
         }
      } else {
         throw new UnknownVariableException(DTF.getUnknownVariableMsg(nodeText));
      }
   }

   public float getFloatValue() throws UnknownVariableException {
      String nodeText = this.getText();
      if (this.variableResolver != null) {
         if (this.variableIndex < 0) {
            if (queryDebugLogger.isDebugEnabled()) {
               queryDebugLogger.debug("Resolving variable by name " + nodeText);
            }

            return this.variableResolver.resolveFloat(nodeText);
         } else {
            if (queryDebugLogger.isDebugEnabled()) {
               queryDebugLogger.debug("Resolving variable by index " + nodeText);
            }

            return this.variableResolver.resolveFloat(this.variableIndex);
         }
      } else {
         throw new UnknownVariableException(DTF.getUnknownVariableMsg(nodeText));
      }
   }

   public double getDoubleValue() throws UnknownVariableException {
      String nodeText = this.getText();
      if (this.variableResolver != null) {
         if (this.variableIndex < 0) {
            if (queryDebugLogger.isDebugEnabled()) {
               queryDebugLogger.debug("Resolving variable by name " + nodeText);
            }

            return this.variableResolver.resolveDouble(nodeText);
         } else {
            if (queryDebugLogger.isDebugEnabled()) {
               queryDebugLogger.debug("Resolving variable by index " + nodeText);
            }

            return this.variableResolver.resolveDouble(this.variableIndex);
         }
      } else {
         throw new UnknownVariableException(DTF.getUnknownVariableMsg(nodeText));
      }
   }
}
