package weblogic.diagnostics.query;

import antlr.Token;
import java.util.HashSet;
import java.util.Iterator;
import weblogic.diagnostics.debug.DebugLogger;

public final class SetNode extends AtomNode {
   private static DebugLogger queryDebugLogger = DebugLogger.getDebugLogger("DebugDiagnosticQuery");
   private HashSet set = new HashSet();
   private boolean setInitialized = false;

   public SetNode() {
      if (queryDebugLogger.isDebugEnabled()) {
         queryDebugLogger.debug("Inside default constructor");
      }

   }

   public SetNode(Token tok) {
      super(tok);
   }

   public void initialize(Token tok) {
      super.initialize(tok);
      this.javaType = 0;
      if (queryDebugLogger.isDebugEnabled()) {
         queryDebugLogger.debug("Inside initialize(Token tok)");
      }

   }

   private void initializeSet() throws UnknownVariableException {
      AtomNode first = (AtomNode)this.getFirstChild();
      AtomNode next = null;
      if (first != null) {
         if (queryDebugLogger.isDebugEnabled()) {
            queryDebugLogger.debug("Adding first child " + first.getText());
         }

         this.set.add(first.getValue());
         next = (AtomNode)first.getNextSibling();
      }

      if (queryDebugLogger.isDebugEnabled()) {
         queryDebugLogger.debug("Initializing set from " + this.toStringTree());
      }

      while(next != null) {
         if (queryDebugLogger.isDebugEnabled()) {
            queryDebugLogger.debug("Adding next child " + next.getText());
         }

         this.set.add(next.getValue());
         next = (AtomNode)next.getNextSibling();
      }

   }

   public Object getValue() throws UnknownVariableException {
      if (!this.setInitialized) {
         this.initializeSet();
      }

      return this.set;
   }

   public String getString() throws UnknownVariableException {
      StringBuffer buf = new StringBuffer();
      Iterator it = this.set.iterator();
      if (it.hasNext()) {
         buf.append(it.next());
      }

      while(it.hasNext()) {
         buf.append(",");
         buf.append(it.next());
      }

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
