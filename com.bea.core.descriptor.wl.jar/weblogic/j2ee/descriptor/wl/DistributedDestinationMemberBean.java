package weblogic.j2ee.descriptor.wl;

/** @deprecated */
@Deprecated
public interface DistributedDestinationMemberBean extends NamedEntityBean {
   int getWeight();

   void setWeight(int var1) throws IllegalArgumentException;

   /** @deprecated */
   @Deprecated
   String getPhysicalDestinationName();

   /** @deprecated */
   @Deprecated
   void setPhysicalDestinationName(String var1) throws IllegalArgumentException;
}
