package weblogic.j2ee.descriptor.wl;

public interface SAFImportedDestinationsBean extends TargetableBean {
   SAFQueueBean[] getSAFQueues();

   SAFQueueBean createSAFQueue(String var1);

   void destroySAFQueue(SAFQueueBean var1);

   SAFQueueBean lookupSAFQueue(String var1);

   SAFTopicBean[] getSAFTopics();

   SAFTopicBean createSAFTopic(String var1);

   void destroySAFTopic(SAFTopicBean var1);

   SAFTopicBean lookupSAFTopic(String var1);

   String getJNDIPrefix();

   void setJNDIPrefix(String var1) throws IllegalArgumentException;

   SAFRemoteContextBean getSAFRemoteContext();

   void setSAFRemoteContext(SAFRemoteContextBean var1) throws IllegalArgumentException;

   SAFErrorHandlingBean getSAFErrorHandling();

   void setSAFErrorHandling(SAFErrorHandlingBean var1) throws IllegalArgumentException;

   long getTimeToLiveDefault();

   void setTimeToLiveDefault(long var1) throws IllegalArgumentException;

   boolean isUseSAFTimeToLiveDefault();

   void setUseSAFTimeToLiveDefault(boolean var1) throws IllegalArgumentException;

   String getUnitOfOrderRouting();

   void setUnitOfOrderRouting(String var1) throws IllegalArgumentException;

   MessageLoggingParamsBean getMessageLoggingParams();

   String getExactlyOnceLoadBalancingPolicy();

   void setExactlyOnceLoadBalancingPolicy(String var1) throws IllegalArgumentException;
}
