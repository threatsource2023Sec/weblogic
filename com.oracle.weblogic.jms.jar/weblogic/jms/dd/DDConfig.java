package weblogic.jms.dd;

public interface DDConfig {
   String getName();

   int getType();

   String getSAFExportPolicy();

   String getUnitOfOrderRouting();

   String getJNDIName();

   int getLoadBalancingPolicyAsInt();

   String getApplicationName();

   String getEARModuleName();

   String getReferenceName();

   int getForwardDelay();

   boolean getResetDeliveryCountOnForward();

   boolean isDefaultUnitOfOrder();
}
