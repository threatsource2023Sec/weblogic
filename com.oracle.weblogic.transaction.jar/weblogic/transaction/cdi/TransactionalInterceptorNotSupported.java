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
@Transactional(TxType.NOT_SUPPORTED)
public class TransactionalInterceptorNotSupported extends TransactionalInterceptorBase {
   @AroundInvoke
   public Object transactional(InvocationContext ctx) throws Exception {
      if (TxDebug.JTACDI.isDebugEnabled()) {
         TxDebug.JTACDI.debug(this + "In NOT_SUPPORTED TransactionalInterceptor");
      }

      if (this.isLifeCycleMethod(ctx)) {
         return this.proceed(ctx);
      } else {
         this.setTransactionalTransactionOperationsManger(true);

         Object var26;
         try {
            Transaction transaction = null;
            if (this.getTransactionManager().getTransaction() != null) {
               if (TxDebug.JTACDI.isDebugEnabled()) {
                  TxDebug.JTACDI.debug(this + "Managed bean with Transactional annotation and TxType of NOT_SUPPORTED called inside a transaction context. Suspending transaction...");
               }

               try {
                  transaction = this.getTransactionManager().suspend();
               } catch (Exception var23) {
                  String messageString = "Managed bean with Transactional annotation and TxType of NOT_SUPPORTED called inside a transaction context.  Suspending transaction failed due to " + var23;
                  if (TxDebug.JTACDI.isDebugEnabled()) {
                     TxDebug.JTACDI.debug(this + "Managed bean with Transactional annotation and TxType of NOT_SUPPORTED called inside a transaction context. Suspending transaction failed due to", var23);
                  }

                  throw new TransactionalException(messageString, var23);
               }
            }

            Object proceed = null;
            boolean var20 = false;

            try {
               var20 = true;
               proceed = this.proceed(ctx);
               var20 = false;
            } finally {
               if (var20) {
                  if (transaction != null) {
                     try {
                        this.getTransactionManager().resume(transaction);
                     } catch (Exception var21) {
                        String messageString = "Managed bean with Transactional annotation and TxType of NOT_SUPPORTED encountered exception during resume " + var21;
                        throw new TransactionalException(messageString, var21);
                     }
                  }

               }
            }

            if (transaction != null) {
               try {
                  this.getTransactionManager().resume(transaction);
               } catch (Exception var22) {
                  String messageString = "Managed bean with Transactional annotation and TxType of NOT_SUPPORTED encountered exception during resume " + var22;
                  throw new TransactionalException(messageString, var22);
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
