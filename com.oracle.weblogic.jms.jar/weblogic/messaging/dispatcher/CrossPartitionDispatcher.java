package weblogic.messaging.dispatcher;

import javax.jms.JMSException;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.jms.dispatcher.DispatcherAdapter;

public final class CrossPartitionDispatcher extends DispatcherAdapter implements PartitionAware {
   final DispatcherImpl dispatcherImpl;

   public CrossPartitionDispatcher(DispatcherImpl dispatcherImpl) {
      super(dispatcherImpl, dispatcherImpl.getDispatcherPartition4rmic());
      this.dispatcherImpl = dispatcherImpl;
   }

   private ManagedInvocationContext setupBefore(Request request) {
      Object pushed = this.dispatcherImpl.getDispatcherPartition4rmic().pushComponentInvocationContext();
      this.dispatcherImpl.giveRequestResource(request);
      return (ManagedInvocationContext)pushed;
   }

   public void dispatchNoReply(Request request) throws JMSException {
      ManagedInvocationContext mic = this.setupBefore(request);
      Throwable var3 = null;

      try {
         super.dispatchNoReply(request);
      } catch (Throwable var12) {
         var3 = var12;
         throw var12;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var11) {
                  var3.addSuppressed(var11);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   public void dispatchNoReplyWithId(Request request, int id) throws JMSException {
      ManagedInvocationContext mic = this.setupBefore(request);
      Throwable var4 = null;

      try {
         super.dispatchNoReplyWithId(request, id);
      } catch (Throwable var13) {
         var4 = var13;
         throw var13;
      } finally {
         if (mic != null) {
            if (var4 != null) {
               try {
                  mic.close();
               } catch (Throwable var12) {
                  var4.addSuppressed(var12);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   public Response dispatchSync(Request request) throws JMSException {
      ManagedInvocationContext mic = this.setupBefore(request);
      Throwable var3 = null;

      Response var4;
      try {
         var4 = super.dispatchSync(request);
      } catch (Throwable var13) {
         var3 = var13;
         throw var13;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var12) {
                  var3.addSuppressed(var12);
               }
            } else {
               mic.close();
            }
         }

      }

      return var4;
   }

   public Response dispatchSyncTran(Request request) throws JMSException {
      ManagedInvocationContext mic = this.setupBefore(request);
      Throwable var3 = null;

      Response var4;
      try {
         var4 = super.dispatchSyncTran(request);
      } catch (Throwable var13) {
         var3 = var13;
         throw var13;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var12) {
                  var3.addSuppressed(var12);
               }
            } else {
               mic.close();
            }
         }

      }

      return var4;
   }

   public Response dispatchSyncNoTran(Request request) throws JMSException {
      ManagedInvocationContext mic = this.setupBefore(request);
      Throwable var3 = null;

      Response var4;
      try {
         var4 = super.dispatchSyncNoTran(request);
      } catch (Throwable var13) {
         var3 = var13;
         throw var13;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var12) {
                  var3.addSuppressed(var12);
               }
            } else {
               mic.close();
            }
         }

      }

      return var4;
   }

   public Response dispatchSyncNoTranWithId(Request request, int id) throws JMSException {
      ManagedInvocationContext mic = this.setupBefore(request);
      Throwable var4 = null;

      Response var5;
      try {
         var5 = super.dispatchSyncNoTranWithId(request, id);
      } catch (Throwable var14) {
         var4 = var14;
         throw var14;
      } finally {
         if (mic != null) {
            if (var4 != null) {
               try {
                  mic.close();
               } catch (Throwable var13) {
                  var4.addSuppressed(var13);
               }
            } else {
               mic.close();
            }
         }

      }

      return var5;
   }

   public void dispatchAsync(Request request) throws DispatcherException {
      ManagedInvocationContext mic = this.setupBefore(request);
      Throwable var3 = null;

      try {
         super.dispatchAsync(request);
      } catch (Throwable var12) {
         var3 = var12;
         throw var12;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var11) {
                  var3.addSuppressed(var11);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   public void dispatchAsyncWithId(Request request, int id) throws DispatcherException {
      ManagedInvocationContext mic = this.setupBefore(request);
      Throwable var4 = null;

      try {
         super.dispatchAsyncWithId(request, id);
      } catch (Throwable var13) {
         var4 = var13;
         throw var13;
      } finally {
         if (mic != null) {
            if (var4 != null) {
               try {
                  mic.close();
               } catch (Throwable var12) {
                  var4.addSuppressed(var12);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   public String getPartitionId() {
      return this.dispatcherImpl.getPartitionId();
   }

   public String getPartitionName() {
      return this.dispatcherImpl.getPartitionName();
   }

   public String getConnectionPartitionName() {
      return this.dispatcherImpl.getConnectionPartitionName();
   }
}
