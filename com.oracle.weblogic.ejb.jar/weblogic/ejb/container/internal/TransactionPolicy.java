package weblogic.ejb.container.internal;

import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.EJBTransactionRequiredException;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionRequiredLocalException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.SystemException;
import javax.transaction.TransactionRequiredException;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.logging.Loggable;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionManager;
import weblogic.utils.StackTraceUtilsClient;

public class TransactionPolicy implements Cloneable {
   private final String methodSignature;
   private final String txName;
   private final int selectForUpdate;
   private int isolationLevel;
   private TransactionAttributeType txAttribute;
   private final ClientViewDescriptor clientViewDesc;

   public TransactionPolicy(String methodSignature, int txAttribute, int isolationLevel, int selectForUpdate, ClientViewDescriptor clientViewDesc) {
      this.methodSignature = methodSignature;
      this.setTxAttribute(txAttribute);
      this.isolationLevel = isolationLevel;
      this.selectForUpdate = selectForUpdate;
      this.clientViewDesc = clientViewDesc;
      this.txName = "[EJB " + clientViewDesc.getBeanInfo().getBeanClassName() + "." + methodSignature + "]";
   }

   public TransactionAttributeType getTxAttribute() {
      return this.txAttribute;
   }

   public void setTxAttribute(int txAttribute) {
      this.txAttribute = this.convertTxAttribute(txAttribute);
   }

   public void setTxAttribute(TransactionAttributeType txAttribute) {
      this.txAttribute = txAttribute;
   }

   public void setIsolationLevel(int isolationLevel) {
      this.isolationLevel = isolationLevel;
   }

   public Transaction enforceTransactionPolicy(Transaction callerTx) throws InternalException {
      try {
         switch (this.txAttribute) {
            case NEVER:
               if (callerTx != null) {
                  Loggable log = EJBLogger.logTxNerverMethodCalledWithnInTxLoggable(this.methodSignature);
                  if (this.clientViewDesc.extendsRemote()) {
                     throw new EJBException(log.getMessage());
                  }

                  throw new RemoteException(log.getMessage());
               }

               return null;
            case NOT_SUPPORTED:
               try {
                  TransactionService.getTransactionManager().suspend();
               } catch (SystemException var4) {
                  EJBRuntimeUtils.throwInternalException("Error suspending tx:", var4);
               }

               return null;
            case SUPPORTS:
               if (callerTx != null) {
                  this.perhapsSetSelectForUpdate(callerTx);
               }

               return callerTx;
            case REQUIRED:
               if (callerTx == null) {
                  return this.startTransaction();
               }

               this.perhapsSetSelectForUpdate(callerTx);
               return callerTx;
            case REQUIRES_NEW:
               try {
                  TransactionService.getTransactionManager().suspend();
               } catch (SystemException var3) {
                  EJBRuntimeUtils.throwInternalException("Error suspending tx:", var3);
               }

               return this.startTransaction();
            case MANDATORY:
               if (callerTx == null) {
                  String message = "Method " + this.methodSignature + " is deployed as TX_MANDATORY, but it was called without a transaction.";
                  if (this.clientViewDesc.extendsRemote()) {
                     throw new TransactionRequiredException(message);
                  }

                  if (this.clientViewDesc.isBusinessView()) {
                     throw new EJBTransactionRequiredException(message);
                  }

                  throw new TransactionRequiredLocalException(message);
               }

               this.perhapsSetSelectForUpdate(callerTx);
               return callerTx;
            default:
               throw new AssertionError("Unknown TX Attribute: " + this.txAttribute);
         }
      } catch (Exception var5) {
         EJBRuntimeUtils.throwInternalException("Exception enforcing EJB transaction policy: ", var5);
         throw new AssertionError("should not reach");
      }
   }

   protected Transaction startTransaction() throws InvalidTransactionException {
      try {
         TransactionManager tms = TransactionService.getWeblogicTransactionManager();
         tms.begin(this.txName, this.getTxTimeoutSeconds());
         Transaction tx = (Transaction)tms.getTransaction();
         if (this.isolationLevel != -1) {
            tx.setProperty("ISOLATION LEVEL", this.isolationLevel);
         }

         this.perhapsSetSelectForUpdate(tx);
         return tx;
      } catch (Exception var3) {
         throw new InvalidTransactionException(EJBLogger.logCannotStartTransactionLoggable(var3.getMessage()).getMessageText());
      }
   }

   private void perhapsSetSelectForUpdate(Transaction tx) {
      if (this.selectForUpdate != 0) {
         tx.setProperty("SELECT_FOR_UPDATE", this.selectForUpdate);
      }

   }

   private int getTxTimeoutSeconds() {
      return this.clientViewDesc.getBeanInfo().getTransactionTimeoutSeconds();
   }

   private TransactionAttributeType convertTxAttribute(int txAttribute) {
      switch (txAttribute) {
         case 0:
            return TransactionAttributeType.NOT_SUPPORTED;
         case 1:
            return TransactionAttributeType.REQUIRED;
         case 2:
            return TransactionAttributeType.SUPPORTS;
         case 3:
            return TransactionAttributeType.REQUIRES_NEW;
         case 4:
            return TransactionAttributeType.MANDATORY;
         case 5:
            return TransactionAttributeType.NEVER;
         default:
            throw new IllegalArgumentException("Unknown tx attribute: " + txAttribute);
      }
   }

   public String toString() {
      StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
      sb.append(": Method: ").append(this.methodSignature);
      sb.append("TransactionAttribute: ").append(this.txAttribute);
      return sb.toString();
   }

   public Object clone() {
      try {
         return super.clone();
      } catch (CloneNotSupportedException var2) {
         throw new AssertionError("Unable to clone TransactionPolicy " + StackTraceUtilsClient.throwable2StackTrace(var2));
      }
   }
}
