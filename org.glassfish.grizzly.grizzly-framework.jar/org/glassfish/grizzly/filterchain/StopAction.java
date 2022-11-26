package org.glassfish.grizzly.filterchain;

import org.glassfish.grizzly.Appender;

final class StopAction extends AbstractNextAction {
   static final int TYPE = 1;
   private Appender appender;
   private Object incompleteChunk;

   StopAction() {
      super(1);
   }

   public Object getIncompleteChunk() {
      return this.incompleteChunk;
   }

   public Appender getAppender() {
      return this.appender;
   }

   public void setIncompleteChunk(Object incompleteChunk, Appender appender) {
      this.incompleteChunk = incompleteChunk;
      this.appender = appender;
   }

   void reset() {
      this.incompleteChunk = null;
      this.appender = null;
   }
}
