package weblogic.messaging.interception.internal;

import javax.jms.Message;
import javax.jms.TextMessage;
import javax.xml.rpc.handler.MessageContext;
import weblogic.jms.common.JMSMessageContext;
import weblogic.jms.common.MessageImpl;
import weblogic.messaging.interception.MIExceptionLogger;
import weblogic.messaging.interception.exceptions.InterceptionException;
import weblogic.messaging.interception.exceptions.InterceptionServiceException;
import weblogic.messaging.interception.exceptions.MessageContextException;
import weblogic.messaging.interception.interfaces.AssociationHandle;
import weblogic.messaging.interception.interfaces.AssociationInfo;
import weblogic.messaging.interception.interfaces.CarrierCallBack;
import weblogic.messaging.interception.interfaces.Processor;

public class Association implements AssociationHandle {
   private static final int PROCESS = 1;
   private static final int PROCESSONLY = 2;
   private static final int PROCESSASYNC = 3;
   private static final int PROCESSONLYASYNC = 4;
   private InterceptionPoint ip = null;
   private ProcessorWrapper pw = null;
   private boolean activated = false;
   private boolean removed = false;
   private long totalMessagesCount = 0L;
   private long continueMessagesCount = 0L;
   private long inProgressMessagesCount = 0L;
   private int depth;
   private AssociationInfoImpl info = null;
   private String ipType = null;
   private String[] ipName = null;
   private String pType = null;
   private String pName = null;

   Association(InterceptionPoint ip, ProcessorWrapper pw, boolean activated, int depth) throws InterceptionServiceException {
      this.ip = ip;
      this.ipType = ip.getType();
      this.ipName = ip.getName();
      this.pw = pw;
      this.pType = pw.getType();
      this.pName = pw.getName();
      this.activated = activated;
      this.depth = depth;
      this.info = new AssociationInfoImpl(this);
   }

   String getInternalName() {
      return this.ip.getInternalName();
   }

   String getIPType() {
      return this.ipType;
   }

   String[] getIPName() {
      return this.ipName;
   }

   String getPType() {
      return this.pType;
   }

   String getPName() {
      return this.pName;
   }

   synchronized void remove() throws InterceptionServiceException {
      if (this.removed) {
         throw new InterceptionServiceException(MIExceptionLogger.logRemoveAssociationAlreadyRemoveErrorLoggable("Association has been removed").getMessage());
      } else {
         this.removed = true;
         this.pw.removeAssociation(this);
         this.ip.removeAssociation();
         this.ip = null;
         this.pw = null;
      }
   }

   private boolean processInternal(MessageContext context, CarrierCallBack callBack, int type) throws InterceptionServiceException, InterceptionException, MessageContextException {
      Processor proc = null;
      this.adjust(context);
      synchronized(this) {
         if (this.removed) {
            return true;
         }

         ++this.totalMessagesCount;
         if (!this.activated) {
            ++this.continueMessagesCount;
            return true;
         }

         proc = this.pw.getProcessor();
         if (proc == null) {
            throw new InterceptionServiceException(MIExceptionLogger.logProcessProcessorNotFoundErrorLoggable("Processor not found").getMessage());
         }

         if (this.inProgressMessagesCount >= (long)this.depth) {
            throw new InterceptionServiceException(MIExceptionLogger.logProcessProcessorDepthExceededErrorLoggable("Processor has more intercepted message than " + this.depth + " outstanding").getMessage());
         }

         ++this.inProgressMessagesCount;
      }

      boolean result = false;
      boolean noException = false;
      Throwable t = null;
      InterceptionServiceException ise = null;
      boolean var24 = false;

      label406: {
         label407: {
            try {
               var24 = true;
               if (type == 1) {
                  result = proc.process(context, this.info);
                  noException = true;
                  var24 = false;
               } else {
                  InterceptionCallBackImpl ipCallBack;
                  if (type == 3) {
                     ipCallBack = new InterceptionCallBackImpl(callBack, this, false, context);
                     proc.processAsync(context, this.info, ipCallBack);
                     noException = true;
                     var24 = false;
                  } else if (type == 2) {
                     proc.processOnly(context, this.info);
                     noException = true;
                     var24 = false;
                  } else {
                     ipCallBack = new InterceptionCallBackImpl(callBack, this, true, context);
                     proc.processOnlyAsync(context, this.info, ipCallBack);
                     noException = true;
                     var24 = false;
                  }
               }
               break label406;
            } catch (RuntimeException var29) {
               t = var29;
               ise = this.pw.removeProcessor(proc, true);
               var24 = false;
            } catch (Error var30) {
               t = var30;
               ise = this.pw.removeProcessor(proc, true);
               var24 = false;
               break label407;
            } finally {
               if (var24) {
                  synchronized(this) {
                     if (!noException) {
                        --this.inProgressMessagesCount;
                     } else if (type == 1) {
                        --this.inProgressMessagesCount;
                        if (result) {
                           ++this.continueMessagesCount;
                        }
                     } else if (type == 2) {
                        --this.inProgressMessagesCount;
                        ++this.continueMessagesCount;
                     }
                  }

                  if (t != null) {
                     this.pw.removeProcessorWrapperIfNotUsed();
                     if (ise != null) {
                        throw ise;
                     }

                     if (t instanceof Error) {
                        throw new InterceptionServiceException(MIExceptionLogger.logProcessIllegalErrorLoggable("Processor throws illegal error").getMessage(), (Throwable)t);
                     }

                     throw new InterceptionServiceException(MIExceptionLogger.logProcessIllegalExceptionLoggable("Processor throws illegal runtime exception").getMessage(), (Throwable)t);
                  }

               }
            }

            synchronized(this) {
               if (!noException) {
                  --this.inProgressMessagesCount;
               } else if (type == 1) {
                  --this.inProgressMessagesCount;
                  if (result) {
                     ++this.continueMessagesCount;
                  }
               } else if (type == 2) {
                  --this.inProgressMessagesCount;
                  ++this.continueMessagesCount;
               }
            }

            if (t != null) {
               this.pw.removeProcessorWrapperIfNotUsed();
               if (ise != null) {
                  throw ise;
               }

               if (t instanceof Error) {
                  throw new InterceptionServiceException(MIExceptionLogger.logProcessIllegalErrorLoggable("Processor throws illegal error").getMessage(), (Throwable)t);
               }

               throw new InterceptionServiceException(MIExceptionLogger.logProcessIllegalExceptionLoggable("Processor throws illegal runtime exception").getMessage(), (Throwable)t);
            }

            return result;
         }

         synchronized(this) {
            if (!noException) {
               --this.inProgressMessagesCount;
            } else if (type == 1) {
               --this.inProgressMessagesCount;
               if (result) {
                  ++this.continueMessagesCount;
               }
            } else if (type == 2) {
               --this.inProgressMessagesCount;
               ++this.continueMessagesCount;
            }
         }

         if (t != null) {
            this.pw.removeProcessorWrapperIfNotUsed();
            if (ise != null) {
               throw ise;
            }

            if (t instanceof Error) {
               throw new InterceptionServiceException(MIExceptionLogger.logProcessIllegalErrorLoggable("Processor throws illegal error").getMessage(), (Throwable)t);
            }

            throw new InterceptionServiceException(MIExceptionLogger.logProcessIllegalExceptionLoggable("Processor throws illegal runtime exception").getMessage(), (Throwable)t);
         }

         return result;
      }

      synchronized(this) {
         if (!noException) {
            --this.inProgressMessagesCount;
         } else if (type == 1) {
            --this.inProgressMessagesCount;
            if (result) {
               ++this.continueMessagesCount;
            }
         } else if (type == 2) {
            --this.inProgressMessagesCount;
            ++this.continueMessagesCount;
         }
      }

      if (t != null) {
         this.pw.removeProcessorWrapperIfNotUsed();
         if (ise != null) {
            throw ise;
         } else if (t instanceof Error) {
            throw new InterceptionServiceException(MIExceptionLogger.logProcessIllegalErrorLoggable("Processor throws illegal error").getMessage(), (Throwable)t);
         } else {
            throw new InterceptionServiceException(MIExceptionLogger.logProcessIllegalExceptionLoggable("Processor throws illegal runtime exception").getMessage(), (Throwable)t);
         }
      } else {
         return result;
      }
   }

   boolean process(MessageContext context) throws InterceptionServiceException, InterceptionException, MessageContextException {
      return this.processInternal(context, (CarrierCallBack)null, 1);
   }

   void process(MessageContext context, CarrierCallBack callBack) throws InterceptionServiceException, InterceptionException, MessageContextException {
      this.processInternal(context, callBack, 3);
   }

   void processOnly(MessageContext context) throws InterceptionServiceException, InterceptionException, MessageContextException {
      this.processInternal(context, (CarrierCallBack)null, 2);
   }

   void processOnly(MessageContext context, CarrierCallBack callBack) throws InterceptionServiceException, InterceptionException, MessageContextException {
      this.processInternal(context, callBack, 4);
   }

   private void checkRemove() throws InterceptionServiceException {
      if (this.removed) {
         throw new InterceptionServiceException(MIExceptionLogger.logRemoveAssociationAlreadyRemoveErrorLoggable("Association has been removed").getMessage());
      }
   }

   String getInterceptionPointType() throws InterceptionServiceException {
      InterceptionPoint lip = null;
      synchronized(this) {
         this.checkRemove();
         lip = this.ip;
      }

      return lip.getType();
   }

   String[] getInterceptionPointName() throws InterceptionServiceException {
      InterceptionPoint lip = null;
      synchronized(this) {
         this.checkRemove();
         lip = this.ip;
      }

      return lip.getName();
   }

   String getProcessorType() throws InterceptionServiceException {
      ProcessorWrapper lpw = null;
      synchronized(this) {
         this.checkRemove();
         lpw = this.pw;
      }

      return lpw.getType();
   }

   String getProcessorName() throws InterceptionServiceException {
      ProcessorWrapper lpw = null;
      synchronized(this) {
         this.checkRemove();
         lpw = this.pw;
      }

      return lpw.getName();
   }

   public AssociationInfo getInfoInternal() {
      return this.info;
   }

   public synchronized AssociationInfo getAssociationInfo() throws InterceptionServiceException {
      this.checkRemove();
      return this.info;
   }

   synchronized ProcessorWrapper getProcessorWrapper() {
      return this.pw;
   }

   synchronized long getTotalMessagesCount() throws InterceptionServiceException {
      this.checkRemove();
      return this.totalMessagesCount;
   }

   synchronized long getContinueMessagesCount() throws InterceptionServiceException {
      this.checkRemove();
      return this.continueMessagesCount;
   }

   synchronized long getInProgressMessagesCount() throws InterceptionServiceException {
      this.checkRemove();
      return this.inProgressMessagesCount;
   }

   long getProcessorRegistrationTime() throws InterceptionServiceException {
      ProcessorWrapper lpw = null;
      synchronized(this) {
         this.checkRemove();
         lpw = this.pw;
      }

      return lpw.getRegistrationTime();
   }

   boolean hasProcessor() throws InterceptionServiceException {
      ProcessorWrapper lpw = null;
      synchronized(this) {
         this.checkRemove();
         lpw = this.pw;
      }

      return lpw.getProcessor() != null;
   }

   public synchronized void activate() throws InterceptionServiceException {
      this.checkRemove();
      this.activated = true;
   }

   public synchronized void deActivate() throws InterceptionServiceException {
      this.checkRemove();
      this.activated = false;
   }

   synchronized boolean isActivated() throws InterceptionServiceException {
      this.checkRemove();
      return this.activated;
   }

   boolean isProcessorShutdown() throws InterceptionServiceException {
      ProcessorWrapper lpw = null;
      synchronized(this) {
         this.checkRemove();
         lpw = this.pw;
      }

      return lpw.forcedShutdown();
   }

   synchronized void updateAsyncMeessagesCount(boolean continueOn) {
      if (continueOn) {
         ++this.continueMessagesCount;
      }

      --this.inProgressMessagesCount;
   }

   private void adjust(MessageContext ctx) {
      if (ctx instanceof JMSMessageContext) {
         JMSMessageContext jmsCtx = (JMSMessageContext)ctx;
         Message msg = jmsCtx.getMessage();
         if (msg instanceof TextMessage) {
            ((MessageImpl)msg).setBodyWritable();
            ((MessageImpl)msg).setPropertiesWritable(true);
         }
      }

   }
}
