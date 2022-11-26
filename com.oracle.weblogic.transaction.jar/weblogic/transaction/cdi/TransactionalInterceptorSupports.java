package weblogic.transaction.cdi;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import weblogic.transaction.internal.TxDebug;

@Priority(200)
@Interceptor
@Transactional(TxType.SUPPORTS)
public class TransactionalInterceptorSupports extends TransactionalInterceptorBase {
   @AroundInvoke
   public Object transactional(InvocationContext ctx) throws Exception {
      if (TxDebug.JTACDI.isDebugEnabled()) {
         TxDebug.JTACDI.debug(this + "In SUPPORTS TransactionalInterceptor");
      }

      if (this.isLifeCycleMethod(ctx)) {
         return this.proceed(ctx);
      } else {
         this.setTransactionalTransactionOperationsManger(false);

         Object var2;
         try {
            var2 = this.proceed(ctx);
         } finally {
            this.resetTransactionOperationsManager();
         }

         return var2;
      }
   }
}
