package com.oracle.weblogic.diagnostics.expressions.poller;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedDeque;
import weblogic.diagnostics.debug.DebugLogger;

public class CircularValuesBuffer extends ConcurrentLinkedDeque {
   private static final long serialVersionUID = -212596685939661226L;
   private int maxLength;
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsExpressionPollerBuffer");

   public CircularValuesBuffer(int max) {
      this.maxLength = max;
   }

   public void push(Object item) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Pushing item: " + item.toString());
      }

      super.push(item);
      this.checkMax();
   }

   public boolean add(Object e) {
      boolean ok = super.add(e);
      this.checkMax();
      return ok;
   }

   public void addFirst(Object e) {
      super.addFirst(e);
      this.checkMax();
   }

   public boolean addAll(Collection c) {
      boolean ok = super.addAll(c);
      this.checkMax();
      return ok;
   }

   public void setMaxElements(int max) {
      this.maxLength = max;
      this.checkMax();
   }

   public int getMaxElements() {
      return this.maxLength;
   }

   private void checkMax() {
      for(; this.size() > this.maxLength; this.removeLast()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Stack size exceeded, removing last item");
         }
      }

   }
}
