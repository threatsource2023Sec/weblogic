package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface SingletonServiceMBean extends SingletonServiceBaseMBean {
   String getClassName();

   void setClassName(String var1) throws InvalidAttributeValueException;

   ServerMBean[] getConstrainedCandidateServers();

   void setConstrainedCandidateServers(ServerMBean[] var1) throws InvalidAttributeValueException;

   boolean addConstrainedCandidateServer(ServerMBean var1) throws InvalidAttributeValueException;

   boolean removeConstrainedCandidateServer(ServerMBean var1) throws InvalidAttributeValueException;

   ClusterMBean getCluster();

   void setCluster(ClusterMBean var1);

   ServerMBean[] getAllCandidateServers();

   void setAllCandidateServers(ServerMBean[] var1);

   boolean isCandidate(ServerMBean var1);
}
