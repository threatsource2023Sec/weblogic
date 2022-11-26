package com.bea.core.repackaged.aspectj.weaver.loadtime;

import com.bea.core.repackaged.aspectj.bridge.AbortException;
import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.IMessageHandler;

public class DefaultMessageHandler implements IMessageHandler {
   boolean isVerbose = false;
   boolean isDebug = false;
   boolean showWeaveInfo = false;
   boolean showWarn = true;

   public boolean handleMessage(IMessage message) throws AbortException {
      return this.isIgnoring(message.getKind()) ? false : SYSTEM_ERR.handleMessage(message);
   }

   public boolean isIgnoring(IMessage.Kind kind) {
      if (kind.equals(IMessage.WEAVEINFO)) {
         return !this.showWeaveInfo;
      } else if (kind.isSameOrLessThan(IMessage.INFO)) {
         return !this.isVerbose;
      } else if (kind.isSameOrLessThan(IMessage.DEBUG)) {
         return !this.isDebug;
      } else {
         return !this.showWarn;
      }
   }

   public void dontIgnore(IMessage.Kind kind) {
      if (kind.equals(IMessage.WEAVEINFO)) {
         this.showWeaveInfo = true;
      } else if (kind.equals(IMessage.DEBUG)) {
         this.isVerbose = true;
      } else if (kind.equals(IMessage.WARNING)) {
         this.showWarn = false;
      }

   }

   public void ignore(IMessage.Kind kind) {
      if (kind.equals(IMessage.WEAVEINFO)) {
         this.showWeaveInfo = false;
      } else if (kind.equals(IMessage.DEBUG)) {
         this.isVerbose = false;
      } else if (kind.equals(IMessage.WARNING)) {
         this.showWarn = true;
      }

   }
}
