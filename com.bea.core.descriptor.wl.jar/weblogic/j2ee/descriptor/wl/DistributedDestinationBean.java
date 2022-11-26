package weblogic.j2ee.descriptor.wl;

/** @deprecated */
@Deprecated
public interface DistributedDestinationBean extends NamedEntityBean {
   String getJNDIName();

   void setJNDIName(String var1) throws IllegalArgumentException;

   String getLoadBalancingPolicy();

   void setLoadBalancingPolicy(String var1) throws IllegalArgumentException;

   String getUnitOfOrderRouting();

   void setUnitOfOrderRouting(String var1) throws IllegalArgumentException;

   String getSAFExportPolicy();

   void setSAFExportPolicy(String var1);
}
