package weblogic.management.mbeans.custom;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBean;
import weblogic.management.configuration.DeterminerCandidateResourceInfoVBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.JTAClusterMBean;
import weblogic.management.configuration.JTAMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class JTAMBeanCustomizer extends ConfigurationMBeanCustomizer {
   static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public JTAMBeanCustomizer(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public DeterminerCandidateResourceInfoVBean[] getDeterminerCandidateResourceInfoList() {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      DomainMBean domainMBean = runtimeAccess.getDomain();
      String[] currentDeterminers = null;
      if (this.getMbean() instanceof JTAClusterMBean) {
         currentDeterminers = ((JTAClusterMBean)this.getMbean()).getDeterminers();
      } else {
         currentDeterminers = ((JTAMBean)this.getMbean()).getDeterminers();
      }

      ArrayList determinerCandidateResourceInfoList = new ArrayList();
      if (Arrays.asList(currentDeterminers).contains("WebLogic_JMS")) {
         determinerCandidateResourceInfoList.add(new DeterminerCandidateResourceInfoVBeanImpl("WebLogic JMS", "WebLogic_JMS", "jms", true));
      } else {
         determinerCandidateResourceInfoList.add(new DeterminerCandidateResourceInfoVBeanImpl("WebLogic JMS", "WebLogic_JMS", "jms", false));
      }

      JDBCSystemResourceMBean[] resources = domainMBean.getJDBCSystemResources();
      DeterminerCandidateResourceInfoVBeanImpl[] determinerCandidateResourceInfoVBeanImpl;
      if (resources == null) {
         determinerCandidateResourceInfoVBeanImpl = new DeterminerCandidateResourceInfoVBeanImpl[determinerCandidateResourceInfoList.size()];
         return (DeterminerCandidateResourceInfoVBean[])determinerCandidateResourceInfoList.toArray(determinerCandidateResourceInfoVBeanImpl);
      } else {
         for(int j = 0; j < resources.length; ++j) {
            JDBCSystemResourceMBean jdbcSystemResourceMBean = resources[j];
            JDBCDataSourceBean dataSourceBean = jdbcSystemResourceMBean.getJDBCResource();
            if (dataSourceBean != null) {
               JDBCDataSourceParamsBean params = dataSourceBean.getJDBCDataSourceParams();
               if (params != null) {
                  String protocol = params.getGlobalTransactionsProtocol();
                  if ("TwoPhaseCommit".equalsIgnoreCase(protocol)) {
                     String internalName = jdbcSystemResourceMBean.getName() + "_" + domainMBean.getName();
                     DeterminerCandidateResourceInfoVBeanImpl determinerCandidateResourceInfo = new DeterminerCandidateResourceInfoVBeanImpl();
                     determinerCandidateResourceInfo.setInternalName(internalName);
                     determinerCandidateResourceInfo.setDisplayName(jdbcSystemResourceMBean.getName());
                     determinerCandidateResourceInfo.setResourceType("datasource");
                     if (Arrays.asList(currentDeterminers).contains(internalName)) {
                        determinerCandidateResourceInfo.setIsDeterminer(true);
                     } else {
                        determinerCandidateResourceInfo.setIsDeterminer(false);
                     }

                     determinerCandidateResourceInfoList.add(determinerCandidateResourceInfo);
                  }
               }
            }
         }

         determinerCandidateResourceInfoVBeanImpl = new DeterminerCandidateResourceInfoVBeanImpl[determinerCandidateResourceInfoList.size()];
         return (DeterminerCandidateResourceInfoVBean[])determinerCandidateResourceInfoList.toArray(determinerCandidateResourceInfoVBeanImpl);
      }
   }
}
