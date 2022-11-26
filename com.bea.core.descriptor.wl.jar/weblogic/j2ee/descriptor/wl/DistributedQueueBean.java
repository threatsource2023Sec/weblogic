package weblogic.j2ee.descriptor.wl;

/** @deprecated */
@Deprecated
public interface DistributedQueueBean extends DistributedDestinationBean {
   DistributedDestinationMemberBean[] getDistributedQueueMembers();

   DistributedDestinationMemberBean createDistributedQueueMember(String var1);

   void destroyDistributedQueueMember(DistributedDestinationMemberBean var1);

   DistributedDestinationMemberBean lookupDistributedQueueMember(String var1);

   int getForwardDelay();

   void setForwardDelay(int var1) throws IllegalArgumentException;

   boolean getResetDeliveryCountOnForward();

   void setResetDeliveryCountOnForward(boolean var1) throws IllegalArgumentException;
}
