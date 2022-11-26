package weblogic.ejb.container.interfaces;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.security.jacc.PolicyConfiguration;
import org.jboss.weld.bootstrap.spi.BeanDiscoveryMode;
import weblogic.ejb.container.persistence.spi.Relationships;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.j2ee.descriptor.MessageDestinationBean;
import weblogic.j2ee.descriptor.wl.MessageDestinationDescriptorBean;
import weblogic.j2ee.injection.PitchforkContext;
import weblogic.security.jacc.RoleMapper;
import weblogic.utils.classloaders.GenericClassLoader;

public interface DeploymentInfo extends weblogic.ejb.spi.DeploymentInfo {
   /** @deprecated */
   @Deprecated
   String getApplicationName();

   String getApplicationId();

   String getModuleId();

   String getModuleURI();

   GenericClassLoader getModuleClassLoader();

   String getJACCPolicyContextId();

   PolicyConfiguration getJACCPolicyConfig();

   URL getJACCCodeSourceURL();

   RoleMapper getJACCRoleMapper();

   /** @deprecated */
   @Deprecated
   String getJarFileName();

   Map getApplicationExceptions();

   ExceptionInfo getExceptionInfo(Method var1, Throwable var2);

   String getClientJarFileName();

   boolean isDynamicQueriesEnabled();

   Collection getBeanInfos();

   Collection getMessageDrivenBeanInfos();

   Collection getEntityBeanInfos();

   Collection getSessionBeanInfos();

   BeanInfo getBeanInfo(String var1);

   String getRunAsRoleAssignment(String var1);

   SecurityRoleMapping getDeploymentRoles();

   Relationships getRelationships();

   MessageDestinationDescriptorBean[] getMessageDestinationDescriptors();

   MessageDestinationBean[] getMessageDestinations();

   boolean isEnableBeanClassRedeploy();

   boolean isWarningDisabled(String var1);

   EjbDescriptorBean getEjbDescriptorBean();

   List getInterceptorBeans(String var1);

   PitchforkContext getPitchforkContext();

   boolean isEar();

   /** @deprecated */
   @Deprecated
   String getEJBCompMBeanName();

   boolean isCDIEnabled();

   BeanDiscoveryMode getBeanDiscoveryMode();

   boolean isAnyAuthUserRoleDefinedInDD();

   String getLogString();
}
