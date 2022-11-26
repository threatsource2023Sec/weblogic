package com.bea.core.repackaged.springframework.aop.aspectj;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.aspectj.bridge.AbortException;
import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.IMessageHandler;

public class AspectJWeaverMessageHandler implements IMessageHandler {
   private static final String AJ_ID = "[AspectJ] ";
   private static final Log logger = LogFactory.getLog("AspectJ Weaver");

   public boolean handleMessage(IMessage message) throws AbortException {
      IMessage.Kind messageKind = message.getKind();
      if (messageKind == IMessage.DEBUG) {
         if (logger.isDebugEnabled()) {
            logger.debug(this.makeMessageFor(message));
            return true;
         }
      } else if (messageKind != IMessage.INFO && messageKind != IMessage.WEAVEINFO) {
         if (messageKind == IMessage.WARNING) {
            if (logger.isWarnEnabled()) {
               logger.warn(this.makeMessageFor(message));
               return true;
            }
         } else if (messageKind == IMessage.ERROR) {
            if (logger.isErrorEnabled()) {
               logger.error(this.makeMessageFor(message));
               return true;
            }
         } else if (messageKind == IMessage.ABORT && logger.isFatalEnabled()) {
            logger.fatal(this.makeMessageFor(message));
            return true;
         }
      } else if (logger.isInfoEnabled()) {
         logger.info(this.makeMessageFor(message));
         return true;
      }

      return false;
   }

   private String makeMessageFor(IMessage aMessage) {
      return "[AspectJ] " + aMessage.getMessage();
   }

   public boolean isIgnoring(IMessage.Kind messageKind) {
      return false;
   }

   public void dontIgnore(IMessage.Kind messageKind) {
   }

   public void ignore(IMessage.Kind kind) {
   }
}
