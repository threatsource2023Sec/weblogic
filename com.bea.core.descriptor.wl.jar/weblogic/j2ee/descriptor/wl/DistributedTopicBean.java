package weblogic.j2ee.descriptor.wl;

/** @deprecated */
@Deprecated
public interface DistributedTopicBean extends DistributedDestinationBean {
   DistributedDestinationMemberBean[] getDistributedTopicMembers();

   DistributedDestinationMemberBean createDistributedTopicMember(String var1);

   void destroyDistributedTopicMember(DistributedDestinationMemberBean var1);

   DistributedDestinationMemberBean lookupDistributedTopicMember(String var1);
}
