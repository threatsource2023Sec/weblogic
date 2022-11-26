package weblogic.ejb.container.interfaces;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;
import javax.naming.Name;
import javax.naming.NamingException;
import weblogic.ejb.container.dd.ClusteringDescriptor;
import weblogic.ejb.container.deployer.EjbJndiBinder;

public interface ClientDrivenBeanInfo extends BeanInfo, weblogic.ejb.spi.ClientDrivenBeanInfo {
   EjbJndiBinder getJndiBinder() throws NamingException;

   Name getJNDIName();

   String getJNDINameAsString();

   Set getPortableJNDINames();

   Name getLocalJNDIName();

   String getLocalJNDINameAsString();

   Set getLocalPortableJNDINames();

   String getGeneratedBeanClassName();

   String getGeneratedBeanInterfaceName();

   Class getGeneratedBeanInterface();

   Class getHomeClass();

   Class getLocalHomeClass();

   Class getWebserviceObjectClass();

   Class getHomeInterfaceClass();

   Class getLocalHomeInterfaceClass();

   Class getRemoteClass();

   Class getLocalClass();

   String getRemoteInterfaceName();

   String getLocalInterfaceName();

   String getServiceEndpointName();

   Class getRemoteInterfaceClass();

   Class getLocalInterfaceClass();

   Class getServiceEndpointClass();

   MethodInfo getRemoteMethodInfo(String var1);

   MethodInfo getRemoteMethodInfo(String var1, String[] var2);

   MethodInfo getRemoteMethodInfo(Method var1);

   MethodInfo getHomeMethodInfo(String var1);

   MethodInfo getHomeMethodInfo(String var1, String[] var2);

   MethodInfo getHomeMethodInfo(Method var1);

   MethodInfo getCallbackMethodInfo(String var1);

   MethodInfo getCallbackMethodInfo(Method var1);

   Collection getAllRemoteMethodInfos();

   Collection getAllHomeMethodInfos();

   Collection getAllLocalMethodInfos();

   Collection getAllLocalHomeMethodInfos();

   Collection getAllWebserviceMethodInfos();

   Collection getAllCallbackMethodInfos();

   ClusteringDescriptor getClusteringDescriptor();

   BaseEJBRemoteHomeIntf getRemoteHome();

   BaseEJBLocalHomeIntf getLocalHome();

   boolean hasDeclaredRemoteHome();

   boolean hasDeclaredLocalHome();

   boolean hasRemoteClientView();

   boolean hasLocalClientView();

   boolean hasWebserviceClientView();

   String getGeneratedJndiNameForHome();

   String getGeneratedJndiNameFor(String var1);
}
