package weblogic.ejb.container.interfaces;

import java.util.Map;
import weblogic.application.naming.PersistenceUnitRegistry;
import weblogic.ejb.container.deployer.StatefulTimeoutConfiguration;

public interface StatefulSessionBeanInfo extends SessionBeanInfo, weblogic.ejb.spi.StatefulSessionBeanInfo {
   int REPLICATION_NONE = 1;
   int REPLICATION_MEMORY = 2;

   int getReplicationType();

   boolean isReplicated();

   boolean implementsSessionSynchronization();

   Map getSessionSyncMethodMapping();

   String getSwapDirectoryName();

   long getIdleTimeoutMS();

   long getSessionTimeoutMS();

   StatefulTimeoutConfiguration getStatefulTimeoutConfiguration();

   boolean isStatefulTimeoutConfigured();

   boolean isAllowRemoveDuringTx();

   boolean getPassivateDuringReplication();

   boolean getCalculateDeltaUsingReflection();

   boolean containsExtendedPersistenceContextRefs();

   void setPersistenceUnitRegistry(PersistenceUnitRegistry var1);

   weblogic.ejb.container.internal.MethodDescriptor getPostConstructMethodDescriptor();

   weblogic.ejb.container.internal.MethodDescriptor getPreDestroyMethodDescriptor();

   weblogic.ejb.container.internal.MethodDescriptor getPostActivateMethodDescriptor();

   weblogic.ejb.container.internal.MethodDescriptor getPrePassivateMethodDescriptor();
}
