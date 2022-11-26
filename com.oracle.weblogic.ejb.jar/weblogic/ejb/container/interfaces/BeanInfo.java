package weblogic.ejb.container.interfaces;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.naming.Environment;
import weblogic.ejb.container.compliance.EJBCheckerFactory;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.ejb.spi.EJBValidationInfo;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.interfaces.PrincipalNotFoundException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.j2ee.descriptor.EnterpriseBeanBean;
import weblogic.j2ee.descriptor.PersistenceContextRefBean;
import weblogic.j2ee.descriptor.PersistenceUnitRefBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean;
import weblogic.security.acl.internal.AuthenticatedSubject;

public interface BeanInfo extends weblogic.ejb.spi.BeanInfo, EJBValidationInfo {
   DeploymentInfo getDeploymentInfo();

   ComponentInvocationContext getCIC();

   ManagedInvocationContext setCIC();

   int getTransactionTimeoutSeconds();

   String getDispatchPolicy();

   boolean getStickToFirstServer();

   int getRemoteClientTimeout();

   /** @deprecated */
   @Deprecated
   String getIsIdenticalKey();

   String getFullyQualifiedName();

   String getDisplayName();

   String getBeanClassName();

   Collection getAllEJBReferences();

   Collection getAllEJBLocalReferences();

   Iterator getAllMethodInfosIterator();

   Collection getAllTimerMethodInfos();

   MethodInfo getTimerMethodInfo(String var1);

   weblogic.ejb.container.internal.MethodDescriptor getEjbTimeoutMethodDescriptor();

   weblogic.ejb.container.internal.MethodDescriptor getAutomaticTimerMethodDescriptor(String var1);

   Map getAllEJBReferenceJNDINames();

   Map getAllEJBLocalReferenceJNDINames();

   Collection getAllResourceReferences();

   Collection getAllResourceEnvReferences();

   Collection getAllWlResourceReferences();

   Collection getAllWlResourceEnvReferences();

   Collection getAllMessageDestinationReferences();

   Collection getAllSecurityRoleReferences();

   SecurityRoleReference getSecurityRoleReference(String var1);

   PersistenceContextRefBean[] getPersistenceContextRefs();

   PersistenceUnitRefBean[] getPersistenceUnitRefs();

   ClassLoader getClassLoader();

   ClassLoader getModuleClassLoader();

   boolean useCallByReference();

   String getNetworkAccessPoint();

   boolean getClientsOnSameServer();

   CachingDescriptor getCachingDescriptor();

   IIOPSecurityDescriptor getIIOPSecurityDescriptor();

   boolean isWarningDisabled(String var1);

   String getRunAsPrincipalName();

   AuthenticatedSubject getRunAsSubject() throws PrincipalNotFoundException;

   String getCreateAsPrincipalName();

   String getRemoveAsPrincipalName();

   String getPassivateAsPrincipalName();

   void assignDefaultTXAttributesIfNecessary(int var1);

   boolean usesBeanManagedTx();

   void setEjbComponentCreator(EjbComponentCreator var1);

   EjbComponentCreator getEjbComponentCreator();

   boolean isEJB30();

   boolean isTimerDriven();

   boolean isClusteredTimers();

   Method getTimeoutMethod();

   String getTimerStoreName();

   Collection getAutomaticTimerMethods();

   /** @deprecated */
   @Deprecated
   Map getAutomaticTimerMDs();

   BeanManager getBeanManager();

   void setupBeanManager(EJBRuntimeHolder var1);

   void init() throws ClassNotFoundException, WLDeploymentException;

   void prepare(Environment var1) throws WLDeploymentException;

   void activate(Map var1, Map var2) throws WLDeploymentException;

   void onUndeploy();

   void updateImplClassLoader() throws WLDeploymentException;

   void setTransactionTimeoutSeconds(int var1, boolean var2);

   void unprepare();

   void addBeanUpdateListener(WeblogicEnterpriseBeanBean var1, EjbDescriptorBean var2, ApplicationContextInternal var3);

   void removeBeanUpdateListener(WeblogicEnterpriseBeanBean var1, EjbDescriptorBean var2, ApplicationContextInternal var3);

   EJBCheckerFactory getEJBCheckerFactory(DeploymentInfo var1);

   boolean isUsingJTAConfigTimeout();

   boolean isVersionGreaterThan30();

   EnterpriseBeanBean getEnterpriseBeanBean();

   WeblogicEnterpriseBeanBean getWeblogicEnterpriseBeanBean();

   void perhapsStartTimerManager();

   void clearInjectors();
}
