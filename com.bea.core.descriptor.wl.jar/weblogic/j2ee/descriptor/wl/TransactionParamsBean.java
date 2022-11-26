package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface TransactionParamsBean extends SettableBean {
   long getTransactionTimeout();

   void setTransactionTimeout(long var1) throws IllegalArgumentException;

   boolean isXAConnectionFactoryEnabled();

   void setXAConnectionFactoryEnabled(boolean var1) throws IllegalArgumentException;
}
