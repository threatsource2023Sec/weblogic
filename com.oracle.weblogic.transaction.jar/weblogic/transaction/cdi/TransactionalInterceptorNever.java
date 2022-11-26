package weblogic.transaction.cdi;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.transaction.InvalidTransactionException;
import javax.transaction.Transactional;
import javax.transaction.TransactionalException;
import javax.transaction.Transactional.TxType;
import weblogic.transaction.internal.TxDebug;

@Priority(200)
@Interceptor
@Transactional(TxType.NEVER)
public class TransactionalInterceptorNever extends TransactionalInterceptorBase {
   @AroundInvoke
   public Object transactional(InvocationContext ctx) throws Exception {
      if (TxDebug.JTACDI.isDebugEnabled()) {
         TxDebug.JTACDI.debug(this + "In NEVER TransactionalInterceptor");
      }

      if (this.isLifeCycleMethod(ctx)) {
         return this.proceed(ctx);
      } else {
         this.setTransactionalTransactionOperationsManger(true);

         Object var2;
         try {
            if (this.getTransactionManager().getTransaction() != null) {
               throw new TransactionalException("InvalidTransactionException thrown from TxType.NEVER transactional interceptor.", new InvalidTransactionException("Managed bean with Transactional annotation and TxType of NEVER called inside a transaction context"));
            }

            var2 = this.proceed(ctx);
         } finally {
            this.resetTransactionOperationsManager();
         }

         return var2;
      }
   }
}
