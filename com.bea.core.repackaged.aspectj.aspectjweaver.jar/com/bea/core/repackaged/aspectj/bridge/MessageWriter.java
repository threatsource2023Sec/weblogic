package com.bea.core.repackaged.aspectj.bridge;

import java.io.PrintWriter;

public class MessageWriter implements IMessageHandler {
   protected PrintWriter writer;
   protected boolean abortOnFailure;

   public MessageWriter(PrintWriter writer, boolean abortOnFailure) {
      this.writer = null != writer ? writer : new PrintWriter(System.out);
      this.abortOnFailure = abortOnFailure;
   }

   public boolean handleMessage(IMessage message) throws AbortException {
      if (null != message && !this.isIgnoring(message.getKind())) {
         String result = this.render(message);
         if (null != result) {
            this.writer.println(result);
            this.writer.flush();
            if (this.abortOnFailure && (message.isFailed() || message.isAbort())) {
               throw new AbortException(message);
            }
         }
      }

      return true;
   }

   public boolean isIgnoring(IMessage.Kind kind) {
      return false;
   }

   public void dontIgnore(IMessage.Kind kind) {
   }

   public void ignore(IMessage.Kind kind) {
   }

   protected String render(IMessage message) {
      return message.toString();
   }
}
