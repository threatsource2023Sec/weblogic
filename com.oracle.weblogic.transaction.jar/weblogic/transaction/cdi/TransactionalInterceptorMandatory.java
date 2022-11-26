package weblogic.transaction.cdi;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.transaction.TransactionRequiredException;
import javax.transaction.Transactional;
import javax.transaction.TransactionalException;
import javax.transaction.Transactional.TxType;
import weblogic.transaction.internal.TxDebug;

@Priority(200)
@Interceptor
@Transactional(TxType.MANDATORY)
public class TransactionalInterceptorMandatory extends TransactionalInterceptorBase {
   @AroundInvoke
   public Object transactional(InvocationContext ctx) throws Exception {
      if (TxDebug.JTACDI.isDebugEnabled()) {
         TxDebug.JTACDI.debug(this + "In MANDATORY TransactionalInterceptor");
      }

      if (this.isLifeCycleMethod(ctx)) {
         return this.proceed(ctx);
      } else {
         this.setTransactionalTransactionOperationsManger(false);

         Object var2;
         try {
            if (this.getTransactionManager().getTransaction() == null) {
               throw new TransactionalException("TransactionRequiredException thrown from TxType.MANDATORY transactional interceptor.", new TransactionRequiredException("Managed bean with Transactional annotation and TxType of MANDATORY called outside of a transaction context"));
            }

            var2 = this.proceed(ctx);
         } finally {
            this.resetTransactionOperationsManager();
         }

         return var2;
      }
   }
}
