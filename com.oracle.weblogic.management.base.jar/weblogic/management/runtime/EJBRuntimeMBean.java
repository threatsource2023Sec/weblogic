package weblogic.management.runtime;

public interface EJBRuntimeMBean extends RuntimeMBean {
   EJBTransactionRuntimeMBean getTransactionRuntime();

   String getEJBName();

   RuntimeMBean[] getResources();
}
