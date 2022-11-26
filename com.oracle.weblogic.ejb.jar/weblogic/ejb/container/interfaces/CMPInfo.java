package weblogic.ejb.container.interfaces;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import weblogic.ejb.container.persistence.PersistenceType;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.ejb.container.persistence.spi.CMPDeployer;
import weblogic.ejb.container.persistence.spi.Relationships;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.utils.Getopt2;
import weblogic.utils.jars.VirtualJarFile;

public interface CMPInfo {
   void setup(File var1, Getopt2 var2, VirtualJarFile var3) throws WLDeploymentException;

   boolean uses20CMP();

   String getGeneratedBeanClassName();

   Collection getAllContainerManagedFieldNames();

   Collection getAllQueries();

   boolean hasContainerManagedFields();

   String getCMPrimaryKeyFieldName();

   String getCMPVersion();

   String getAbstractSchemaName();

   boolean findersLoadBean();

   String getPersistenceUseIdentifier();

   String getPersistenceUseVersion();

   String getPersistenceUseStorage();

   Relationships getRelationships();

   CMPBeanDescriptor getCMPBeanDescriptor(String var1);

   Map getBeanMap();

   Map getAllBeanMap();

   PersistenceType getPersistenceType();

   Class getGeneratedBeanClass() throws ClassNotFoundException;

   CMPDeployer getDeployer();

   Collection getAllEJBEntityReferences();

   void setupParentBeanManagers();

   void setupMNBeanManagers();

   void setCycleExists();

   boolean isQueryCachingEnabled(Method var1);

   String getQueryCachingEnabledFinderIndex(Method var1);

   int getMaxQueriesInCache();

   boolean isEnableEagerRefresh(Method var1);
}
