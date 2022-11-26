package weblogic.ejb.container.persistence.spi;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;
import javax.naming.Name;
import weblogic.ejb.spi.EjbDescriptorBean;

public interface CMPBeanDescriptor {
   String getAbstractSchemaName();

   String getEJBName();

   Class getHomeClass();

   String getHomeInterfaceName();

   Class getHomeInterfaceClass();

   Class getLocalHomeClass();

   String getLocalHomeInterfaceName();

   Class getLocalHomeInterfaceClass();

   Class getBeanClass();

   String getGeneratedBeanClassName();

   String getGeneratedBeanInterfaceName();

   Class getRemoteClass();

   String getRemoteInterfaceName();

   Class getRemoteInterfaceClass();

   Name getJNDIName();

   Class getLocalClass();

   String getLocalInterfaceName();

   Class getLocalInterfaceClass();

   Class getJavaClass();

   Name getLocalJNDIName();

   boolean getCacheBetweenTransactions();

   boolean getBoxCarUpdates();

   boolean hasComplexPrimaryKey();

   Class getPrimaryKeyClass();

   String getPrimaryKeyClassName();

   Set getPrimaryKeyFieldNames();

   Set getCMFieldNames();

   Class getFieldClass(String var1);

   boolean getIsReentrant();

   String getIsModifiedMethodName();

   boolean getFindersLoadBean();

   int getTransactionTimeoutSeconds();

   Collection getAllQueries();

   ClassLoader getClassLoader();

   boolean hasLocalClientView();

   boolean hasRemoteClientView();

   boolean isReadOnly();

   boolean isOptimistic();

   int getConcurrencyStrategy();

   Method getGetterMethod(Class var1, String var2);

   Method getSetterMethod(Class var1, String var2);

   EjbDescriptorBean getEjbDescriptorBean();

   boolean isBeanClassAbstract();

   void setPrimaryKeyField(String var1);

   boolean getIsDynamicQueriesEnabled();

   int getMaxQueriesInCache();
}
