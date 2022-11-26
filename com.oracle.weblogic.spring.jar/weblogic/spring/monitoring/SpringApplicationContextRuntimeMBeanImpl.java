package weblogic.spring.monitoring;

import java.util.HashMap;
import java.util.Set;
import weblogic.management.ManagementException;
import weblogic.management.runtime.SpringApplicationContextRuntimeMBean;
import weblogic.spring.SpringLogger;
import weblogic.spring.monitoring.utils.AbstractApplicationContextDelegator;

public class SpringApplicationContextRuntimeMBeanImpl extends SpringBaseRuntimeMBeanImpl implements SpringApplicationContextRuntimeMBean {
   private AbstractApplicationContextDelegator appCtxDelegator;
   private String parentContext;
   private long startupDate;
   private Object beanFactory;
   private long numberOfPrototypeBeansCreated;
   private double elapsedTimesPrototypeBeanCreation;
   private long numberOfSingletonBeansCreated;
   private double elapsedTimesSingletonBeanCreation;
   private long refreshCount;
   private long refreshFailedCount;
   private double elapsedTimesRefresh;
   private double elapsedTimesGetBean;
   private long getBeanExecutions;
   private long getBeanFailedExecutions;
   private double elapsedTimesGetBeansOfType;
   private long getBeansOfTypeExecutions;
   private long getBeansOfTypeFailedExecutions;
   private double elapsedTimesGetBeanNamesForType;
   private long getBeanNamesForTypeExecutions;
   private long getBeanNamesForTypeFailedExecutions;
   private HashMap customScopes;

   public SpringApplicationContextRuntimeMBeanImpl(String name, Object applicationContext) throws ManagementException {
      super(applicationContext, applicationContext, name, false);
      this.appCtxDelegator = new AbstractApplicationContextDelegator(applicationContext);
      this.parentContext = this.appCtxDelegator.getParentContext();
      this.startupDate = this.appCtxDelegator.getStartupDate();
      this.beanFactory = this.appCtxDelegator.getBeanFactory();
      this.customScopes = new HashMap();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("SpringApplicationContextRuntimeMBeanImpl(" + name + ")");
      }

   }

   public String getDisplayName() {
      return this.getApplicationContextDisplayName();
   }

   public String getParentContext() {
      return this.parentContext;
   }

   public long getStartupDate() {
      return this.startupDate;
   }

   public Object getBeanFactory() {
      return this.beanFactory;
   }

   public synchronized double getAveragePrototypeBeanCreationTime() {
      return this.numberOfPrototypeBeansCreated == 0L ? 0.0 : this.elapsedTimesPrototypeBeanCreation / 1000000.0 / (double)this.numberOfPrototypeBeansCreated;
   }

   public synchronized long getPrototypeBeansCreatedCount() {
      return this.numberOfPrototypeBeansCreated;
   }

   public synchronized double getAverageSingletonBeanCreationTime() {
      return this.numberOfSingletonBeansCreated == 0L ? 0.0 : this.elapsedTimesSingletonBeanCreation / 1000000.0 / (double)this.numberOfSingletonBeansCreated;
   }

   public synchronized long getSingletonBeansCreatedCount() {
      return this.numberOfSingletonBeansCreated;
   }

   public String[] getCustomScopeNames() {
      if (this.customScopes != null && !this.customScopes.isEmpty()) {
         Set keys = this.customScopes.keySet();
         return (String[])keys.toArray(new String[keys.size()]);
      } else {
         return null;
      }
   }

   public double getAverageCustomScopeBeanCreationTime(String scopeName) throws IllegalArgumentException {
      if (scopeName != null && scopeName.length() != 0) {
         CustomScopeInfo info = (CustomScopeInfo)this.customScopes.get(scopeName);
         if (info != null) {
            return info.numberOfCustomScopeBeansCreated == 0L ? 0.0 : info.elapsedTimesCustomScopeBeanCreation / 1000000.0 / (double)info.numberOfCustomScopeBeansCreated;
         } else {
            throw new IllegalArgumentException(SpringLogger.getUnregisteredScopeName(scopeName));
         }
      } else {
         throw new IllegalArgumentException(SpringLogger.getUnregisteredScopeName(scopeName));
      }
   }

   public long getCustomScopeBeansCreatedCount(String scopeName) throws IllegalArgumentException {
      if (scopeName != null && scopeName.length() != 0) {
         CustomScopeInfo info = (CustomScopeInfo)this.customScopes.get(scopeName);
         if (info != null) {
            return info.numberOfCustomScopeBeansCreated;
         } else {
            throw new IllegalArgumentException(SpringLogger.getUnregisteredScopeName(scopeName));
         }
      } else {
         throw new IllegalArgumentException(SpringLogger.getUnregisteredScopeName(scopeName));
      }
   }

   public synchronized double getAverageRefreshTime() {
      return this.refreshCount == 0L ? 0.0 : this.elapsedTimesRefresh / 1000000.0 / (double)this.refreshCount;
   }

   public synchronized long getRefreshCount() {
      return this.refreshCount;
   }

   public synchronized long getRefreshFailedCount() {
      return this.refreshFailedCount;
   }

   public synchronized double getAverageGetBeanTime() {
      return this.getBeanExecutions == 0L ? 0.0 : this.elapsedTimesGetBean / 1000000.0 / (double)this.getBeanExecutions;
   }

   public synchronized long getGetBeanCount() {
      return this.getBeanExecutions;
   }

   public synchronized long getGetBeanFailedCount() {
      return this.getBeanFailedExecutions;
   }

   public synchronized double getAverageGetBeansOfTypeTime() {
      return this.getBeansOfTypeExecutions == 0L ? 0.0 : this.elapsedTimesGetBeansOfType / 1000000.0 / (double)this.getBeansOfTypeExecutions;
   }

   public synchronized long getGetBeansOfTypeCount() {
      return this.getBeansOfTypeExecutions;
   }

   public synchronized long getGetBeansOfTypeFailedCount() {
      return this.getBeansOfTypeFailedExecutions;
   }

   public synchronized double getAverageGetBeanNamesForTypeTime() {
      return this.getBeanNamesForTypeExecutions == 0L ? 0.0 : this.elapsedTimesGetBeanNamesForType / 1000000.0 / (double)this.getBeanNamesForTypeExecutions;
   }

   public synchronized long getGetBeanNamesForTypeCount() {
      return this.getBeanNamesForTypeExecutions;
   }

   public synchronized long getGetBeanNamesForTypeFailedCount() {
      return this.getBeanNamesForTypeFailedExecutions;
   }

   public synchronized void addPrototypeBeanCreation(long elapsedTimeNanos) {
      ++this.numberOfPrototypeBeansCreated;
      this.elapsedTimesPrototypeBeanCreation += (double)elapsedTimeNanos;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("SpringApplicationContextRuntimeMBeanImpl.addPrototypeBeanCreation() : " + this.name);
      }

   }

   public synchronized void addSingletonBeanCreation(long elapsedTimeNanos) {
      ++this.numberOfSingletonBeansCreated;
      this.elapsedTimesSingletonBeanCreation += (double)elapsedTimeNanos;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("SpringApplicationContextRuntimeMBeanImpl.addSingletonBeanCreation() : " + this.name);
      }

   }

   public synchronized void addCustomScope(String scopeName) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("SpringApplicationContextRuntimeMBeanImpl.addCustomScope(" + scopeName + ") : " + this.name);
      }

      if (scopeName != null && scopeName.length() != 0) {
         CustomScopeInfo info = (CustomScopeInfo)this.customScopes.get(scopeName);
         if (info == null) {
            info = new CustomScopeInfo();
            this.customScopes.put(scopeName, info);
         }

      } else {
         throw new IllegalArgumentException();
      }
   }

   public synchronized void addCustomScopeBeanCreation(String scopeName, long elapsedTimeNanos) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("SpringApplicationContextRuntimeMBeanImpl.addCustomScopeBeanCreation(" + scopeName + ") : " + this.name);
      }

      if (scopeName != null && scopeName.length() != 0) {
         CustomScopeInfo info = (CustomScopeInfo)this.customScopes.get(scopeName);
         if (info == null) {
            info = new CustomScopeInfo();
            this.customScopes.put(scopeName, info);
         }

         ++info.numberOfCustomScopeBeansCreated;
         info.elapsedTimesCustomScopeBeanCreation += (double)elapsedTimeNanos;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public synchronized void addRefresh(boolean successful, long elapsedTimeNanos) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("SpringApplicationContextRuntimeMBeanImpl.addRefresh() : " + this.name);
      }

      ++this.refreshCount;
      if (!successful) {
         ++this.refreshFailedCount;
      }

      this.elapsedTimesRefresh += (double)elapsedTimeNanos;
   }

   public synchronized void addGetBeanExecution(boolean successful, long elapsedTimeNanos) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("SpringApplicationContextRuntimeMBeanImpl.addGetBeanExecution() : " + this.name);
      }

      ++this.getBeanExecutions;
      if (!successful) {
         ++this.getBeanFailedExecutions;
      }

      this.elapsedTimesGetBean += (double)elapsedTimeNanos;
   }

   public synchronized void addGetBeansOfTypeExecution(boolean successful, long elapsedTimeNanos) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("SpringApplicationContextRuntimeMBeanImpl.addGetBeansOfTypeExecution() : " + this.name);
      }

      ++this.getBeansOfTypeExecutions;
      if (!successful) {
         ++this.getBeansOfTypeFailedExecutions;
      }

      this.elapsedTimesGetBeansOfType += (double)elapsedTimeNanos;
   }

   public synchronized void addGetBeanNamesForTypeExecution(boolean successful, long elapsedTimeNanos) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("SpringApplicationContextRuntimeMBeanImpl.addGetBeanNamesForTypeExecution() : " + this.name);
      }

      ++this.getBeanNamesForTypeExecutions;
      if (!successful) {
         ++this.getBeanNamesForTypeFailedExecutions;
      }

      this.elapsedTimesGetBeanNamesForType += (double)elapsedTimeNanos;
   }

   public synchronized void updateBeanFactory() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("SpringApplicationContextRuntimeMBeanImpl.resetBeanFactoryStats() : " + this.name);
      }

      this.beanFactory = this.appCtxDelegator.getBeanFactory();
      this.numberOfPrototypeBeansCreated = 0L;
      this.elapsedTimesPrototypeBeanCreation = 0.0;
      this.numberOfSingletonBeansCreated = 0L;
      this.elapsedTimesSingletonBeanCreation = 0.0;
      this.elapsedTimesGetBean = 0.0;
      this.getBeanExecutions = 0L;
      this.getBeanFailedExecutions = 0L;
      this.elapsedTimesGetBeansOfType = 0.0;
      this.getBeansOfTypeExecutions = 0L;
      this.getBeansOfTypeFailedExecutions = 0L;
      this.elapsedTimesGetBeanNamesForType = 0.0;
      this.getBeanNamesForTypeExecutions = 0L;
      this.getBeanNamesForTypeFailedExecutions = 0L;
      if (this.customScopes != null) {
         this.customScopes.clear();
         this.customScopes = null;
      }

   }

   private static class CustomScopeInfo {
      public long numberOfCustomScopeBeansCreated;
      public double elapsedTimesCustomScopeBeanCreation;

      private CustomScopeInfo() {
      }

      // $FF: synthetic method
      CustomScopeInfo(Object x0) {
         this();
      }
   }
}
