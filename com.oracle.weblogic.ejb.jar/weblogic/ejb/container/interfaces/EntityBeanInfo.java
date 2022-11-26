package weblogic.ejb.container.interfaces;

import java.util.Collection;
import weblogic.ejb.container.persistence.spi.PersistenceManager;

public interface EntityBeanInfo extends ClientDrivenBeanInfo {
   int EXCLUSIVE_CONCURRENCY = 1;
   int DATABASE_CONCURRENCY = 2;
   int READONLY_EXCLUSIVE_CONCURRENCY = 4;
   int READONLY_CONCURRENCY = 5;
   int OPTIMISTIC_CONCURRENCY = 6;

   int getConcurrencyStrategy();

   boolean isUnknownPrimaryKey();

   Class getPrimaryKeyClass();

   String getPrimaryKeyClassName();

   boolean getIsBeanManagedPersistence();

   String getPersistenceUseIdentifier();

   String getPersistenceUseVersion();

   String getPersistenceUseStorage();

   boolean getBoxCarUpdates();

   String getIsModifiedMethodName();

   boolean isReentrant();

   boolean isReadOnlyWithSingleInstance();

   boolean isReadOnly();

   boolean isOptimistic();

   String getCacheName();

   int getEstimatedBeanSize();

   boolean getCacheBetweenTransactions();

   boolean getDisableReadyInstances();

   String getCategoryCmpField();

   void setCategoryCmpField(String var1);

   Collection getAllQueries();

   PersistenceManager getPersistenceManager();

   CMPInfo getCMPInfo();

   InvalidationBeanManager getInvalidationTargetBeanManager();

   String getInvalidationTargetEJBName();

   boolean isDynamicQueriesEnabled();

   int getInstanceLockOrder();

   int getMaxQueriesInCache();
}
