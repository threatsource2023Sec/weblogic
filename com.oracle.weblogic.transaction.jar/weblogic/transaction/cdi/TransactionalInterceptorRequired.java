package weblogic.transaction.cdi;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.transaction.Transactional;
import javax.transaction.TransactionalException;
import javax.transaction.Transactional.TxType;
import weblogic.transaction.internal.TxDebug;

@Priority(200)
@Interceptor
@Transactional(TxType.REQUIRED)
public class TransactionalInterceptorRequired extends TransactionalInterceptorBase {
   @AroundInvoke
   public Object transactional(InvocationContext ctx) throws Exception {
      if (TxDebug.JTACDI.isDebugEnabled()) {
         TxDebug.JTACDI.debug(this + "In REQUIRED TransactionalInterceptor");
      }

      if (this.isLifeCycleMethod(ctx)) {
         return this.proceed(ctx);
      } else {
         this.setTransactionalTransactionOperationsManger(false);

         Object var26;
         try {
            boolean isTransactionStarted = false;
            if (this.getTransactionManager().getTransaction() == null) {
               if (TxDebug.JTACDI.isDebugEnabled()) {
                  TxDebug.JTACDI.debug(this + "Managed bean with Transactional annotation and TxType of REQUIRED called outside a transaction context.  Beginning a transaction...");
               }

               try {
                  this.getTransactionManager().begin();
               } catch (Exception var21) {
                  String messageString = "Managed bean with Transactional annotation and TxType of REQUIRED encountered exception during begin " + var21;
                  if (TxDebug.JTACDI.isDebugEnabled()) {
                     TxDebug.JTACDI.debug(this + "Managed bean with Transactional annotation and TxType of REQUIRED encountered exception during begin", var21);
                  }

                  throw new TransactionalException(messageString, var21);
               }

               isTransactionStarted = true;
            }

            Object proceed = null;
            boolean var20 = false;

            try {
               var20 = true;
               proceed = this.proceed(ctx);
               var20 = false;
            } finally {
               if (var20) {
                  if (isTransactionStarted) {
                     try {
                        if (this.getTransactionManager().getTransaction().getStatus() == 1) {
                           this.getTransactionManager().rollback();
                        } else {
                           this.getTransactionManager().commit();
                        }
                     } catch (Exception var22) {
                        String messageString = "Managed bean with Transactional annotation and TxType of REQUIRED encountered exception during commit " + var22;
                        if (TxDebug.JTACDI.isDebugEnabled()) {
                           TxDebug.JTACDI.debug(this + "Managed bean with Transactional annotation and TxType of REQUIRED encountered exception during commit", var22);
                        }

                        throw new TransactionalException(messageString, var22);
                     }
                  }

               }
            }

            if (isTransactionStarted) {
               try {
                  if (this.getTransactionManager().getTransaction().getStatus() == 1) {
                     this.getTransactionManager().rollback();
                  } else {
                     this.getTransactionManager().commit();
                  }
               } catch (Exception var24) {
                  String messageString = "Managed bean with Transactional annotation and TxType of REQUIRED encountered exception during commit " + var24;
                  if (TxDebug.JTACDI.isDebugEnabled()) {
                     TxDebug.JTACDI.debug(this + "Managed bean with Transactional annotation and TxType of REQUIRED encountered exception during commit", var24);
                  }

                  throw new TransactionalException(messageString, var24);
               }
            }

            var26 = proceed;
         } finally {
            this.resetTransactionOperationsManager();
         }

         return var26;
      }
   }
}
