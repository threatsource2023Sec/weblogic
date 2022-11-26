package com.bea.core.repackaged.aspectj.bridge;

import java.io.PrintWriter;

public interface IMessageHandler {
   IMessageHandler SYSTEM_ERR = new MessageWriter(new PrintWriter(System.err, true), true);
   IMessageHandler SYSTEM_OUT = new MessageWriter(new PrintWriter(System.out, true), false);
   IMessageHandler THROW = new IMessageHandler() {
      public boolean handleMessage(IMessage message) {
         if (message.getKind().compareTo(IMessage.ERROR) >= 0) {
            throw new AbortException(message);
         } else {
            return SYSTEM_OUT.handleMessage(message);
         }
      }

      public boolean isIgnoring(IMessage.Kind kind) {
         return false;
      }

      public void dontIgnore(IMessage.Kind kind) {
      }

      public void ignore(IMessage.Kind kind) {
      }
   };

   boolean handleMessage(IMessage var1) throws AbortException;

   boolean isIgnoring(IMessage.Kind var1);

   void dontIgnore(IMessage.Kind var1);

   void ignore(IMessage.Kind var1);
}
