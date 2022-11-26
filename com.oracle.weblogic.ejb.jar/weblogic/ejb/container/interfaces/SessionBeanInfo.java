package weblogic.ejb.container.interfaces;

import java.lang.reflect.Method;
import java.util.Map;
import weblogic.ejb.container.internal.AsyncInvocationManager;

public interface SessionBeanInfo extends ClientDrivenBeanInfo, weblogic.ejb.spi.SessionBeanInfo {
   boolean hasBusinessLocals();

   Map getBusinessJNDINames();

   String getCustomJndiName(Class var1);

   Class getGeneratedLocalBusinessImplClass(Class var1);

   Class getGeneratedNoIntfViewImplClass();

   boolean hasBusinessRemotes();

   Class getGeneratedRemoteBusinessIntfClass(Class var1);

   Class getGeneratedRemoteBusinessImplClass(Class var1);

   boolean hasAsyncMethods();

   boolean isAsyncMethod(Method var1);

   AsyncInvocationManager getAsyncInvocationManager();

   boolean isEndpointView();

   boolean isLocalClientView(Class var1);

   String getEjbCreateInitMethodName(Method var1);
}
