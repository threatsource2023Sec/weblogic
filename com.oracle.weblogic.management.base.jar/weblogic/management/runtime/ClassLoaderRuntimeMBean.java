package weblogic.management.runtime;

public interface ClassLoaderRuntimeMBean extends RuntimeMBean {
   long getLoadClassTime();

   long getLoadClassCount();

   long getFindClassTime();

   long getFindClassCount();

   long getDefineClassTime();

   long getDefineClassCount();

   long getResourceTime();

   long getResourceCount();

   long getParentDelegationTime();

   long getParentDelegationCount();

   long getIndexingTime();

   long getBeforeIndexingLoadClassTime();

   long getBeforeIndexingLoadClassCount();

   long getBeforeIndexingFindClassTime();

   long getBeforeIndexingFindClassCount();

   long getBeforeIndexingResourceTime();

   long getBeforeIndexingResourceCount();

   long getDuringIndexingLoadClassTime();

   long getDuringIndexingLoadClassCount();

   long getDuringIndexingFindClassTime();

   long getDuringIndexingFindClassCount();

   long getDuringIndexingResourceTime();

   long getDuringIndexingResourceCount();

   long getAfterIndexingLoadClassTime();

   long getAfterIndexingLoadClassCount();

   long getAfterIndexingFindClassTime();

   long getAfterIndexingFindClassCount();

   long getAfterIndexingResourceTime();

   long getAfterIndexingResourceCount();
}
