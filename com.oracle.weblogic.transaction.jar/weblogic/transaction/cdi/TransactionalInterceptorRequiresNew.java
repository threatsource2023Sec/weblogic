package weblogic.transaction.cdi;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.transaction.Transaction;
import javax.transaction.Transactional;
import javax.transaction.TransactionalException;
import javax.transaction.Transactional.TxType;
import weblogic.transaction.internal.TxDebug;

@Priority(200)
@Interceptor
@Transactional(TxType.REQUIRES_NEW)
public class TransactionalInterceptorRequiresNew extends TransactionalInterceptorBase {
   @AroundInvoke
   public Object transactional(InvocationContext ctx) throws Exception {
      if (TxDebug.JTACDI.isDebugEnabled()) {
         TxDebug.JTACDI.debug(this + "In REQUIRES_NEW TransactionalInterceptor");
      }

      if (this.isLifeCycleMethod(ctx)) {
         return this.proceed(ctx);
      } else {
         this.setTransactionalTransactionOperationsManger(false);

         Object var32;
         try {
            Transaction suspendedTransaction = null;
            if (this.getTransactionManager().getTransaction() != null) {
               if (TxDebug.JTACDI.isDebugEnabled()) {
                  TxDebug.JTACDI.debug(this + "Managed bean with Transactional annotation and TxType of REQUIRES_NEW called inside a transaction context.  Suspending before beginning a transaction...");
               }

               suspendedTransaction = this.getTransactionManager().suspend();
            }

            try {
               this.getTransactionManager().begin();
            } catch (Exception var25) {
               String messageString = "Managed bean with Transactional annotation and TxType of REQUIRES_NEW encountered exception during begin " + var25;
               if (TxDebug.JTACDI.isDebugEnabled()) {
                  TxDebug.JTACDI.debug(this + "Managed bean with Transactional annotation and TxType of REQUIRES_NEW encountered exception during begin", var25);
               }

               throw new TransactionalException(messageString, var25);
            }

            Object proceed = null;
            boolean var24 = false;

            try {
               var24 = true;
               proceed = this.proceed(ctx);
               var24 = false;
            } finally {
               if (var24) {
                  String messageString;
                  try {
                     if (this.getTransactionManager().getTransaction().getStatus() == 1) {
                        this.getTransactionManager().rollback();
                     } else {
                        this.getTransactionManager().commit();
                     }
                  } catch (Exception var26) {
                     messageString = "Managed bean with Transactional annotation and TxType of REQUIRES_NEW encountered exception during commit " + var26;
                     if (TxDebug.JTACDI.isDebugEnabled()) {
                        TxDebug.JTACDI.debug(this + "Managed bean with Transactional annotation and TxType of REQUIRES_NEW encountered exception during commit", var26);
                     }

                     throw new TransactionalException(messageString, var26);
                  }

                  if (suspendedTransaction != null) {
                     try {
                        this.getTransactionManager().resume(suspendedTransaction);
                     } catch (Exception var27) {
                        messageString = "Managed bean with Transactional annotation and TxType of REQUIRED encountered exception during resume " + var27;
                        if (TxDebug.JTACDI.isDebugEnabled()) {
                           TxDebug.JTACDI.debug(this + "Managed bean with Transactional annotation and TxType of REQUIRED encountered exception during resume", var27);
                        }

                        throw new TransactionalException(messageString, var27);
                     }
                  }

               }
            }

            String messageString;
            try {
               if (this.getTransactionManager().getTransaction().getStatus() == 1) {
                  this.getTransactionManager().rollback();
               } else {
                  this.getTransactionManager().commit();
               }
            } catch (Exception var30) {
               messageString = "Managed bean with Transactional annotation and TxType of REQUIRES_NEW encountered exception during commit " + var30;
               if (TxDebug.JTACDI.isDebugEnabled()) {
                  TxDebug.JTACDI.debug(this + "Managed bean with Transactional annotation and TxType of REQUIRES_NEW encountered exception during commit", var30);
               }

               throw new TransactionalException(messageString, var30);
            }

            if (suspendedTransaction != null) {
               try {
                  this.getTransactionManager().resume(suspendedTransaction);
               } catch (Exception var29) {
                  messageString = "Managed bean with Transactional annotation and TxType of REQUIRED encountered exception during resume " + var29;
                  if (TxDebug.JTACDI.isDebugEnabled()) {
                     TxDebug.JTACDI.debug(this + "Managed bean with Transactional annotation and TxType of REQUIRED encountered exception during resume", var29);
                  }

                  throw new TransactionalException(messageString, var29);
               }
            }

            var32 = proceed;
         } finally {
            this.resetTransactionOperationsManager();
         }

         return var32;
      }
   }
}
