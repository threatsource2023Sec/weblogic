package weblogic.management.rest.lib.bean.utils;

import weblogic.management.rest.lib.utils.TransactionHelper;

public class BeanTransactionHelper extends TransactionHelper {
   public BeanTransactionHelper(InvocationContext ic) throws Exception {
      super(new BeanConfigurationManagerWrapper(ic), ic.request());
   }
}
