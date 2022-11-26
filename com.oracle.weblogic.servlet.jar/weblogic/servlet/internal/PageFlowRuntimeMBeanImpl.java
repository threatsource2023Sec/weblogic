package weblogic.servlet.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementException;
import weblogic.management.runtime.PageFlowActionRuntimeMBean;
import weblogic.management.runtime.PageFlowRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.utils.AssertionError;

public class PageFlowRuntimeMBeanImpl extends RuntimeMBeanDelegate implements PageFlowRuntimeMBean {
   private static final long serialVersionUID = 1L;
   private String _className;
   private HashMap _actions;
   private long _lastResetTime;
   private long _createCount;
   private long _destroyCount;
   private int _numExceptionsToKeep;
   private LinkedList _reportedExceptions;
   private DebugLogger _logger;

   public PageFlowRuntimeMBeanImpl(String serverName, String httpServerName, String className, RuntimeMBean parent) throws ManagementException {
      super(className, parent);
      this._className = className;
      this._actions = new HashMap();
      this._reportedExceptions = new LinkedList();
      this._numExceptionsToKeep = 5;
      this._logger = PageFlowsRuntimeMBeanImpl.logger;
   }

   public PageFlowRuntimeMBeanImpl() throws ManagementException {
      throw new AssertionError("Public constructor provided only for JMX compliance.");
   }

   public void reset() {
      this._lastResetTime = System.currentTimeMillis();
      this._createCount = 0L;
      this._destroyCount = 0L;
      if (this._actions.size() != 0) {
         Iterator iter = this._actions.values().iterator();

         while(iter.hasNext()) {
            PageFlowActionRuntimeMBeanImpl actionStats = (PageFlowActionRuntimeMBeanImpl)iter.next();
            actionStats.reset();
         }

      }
   }

   public String getClassName() {
      return this._className;
   }

   public PageFlowActionRuntimeMBean[] getActions() {
      PageFlowActionRuntimeMBean[] result = new PageFlowActionRuntimeMBean[this._actions.size()];
      this._actions.values().toArray(result);
      return result;
   }

   public PageFlowActionRuntimeMBean getAction(String actionName) {
      return (PageFlowActionRuntimeMBean)this._actions.get(actionName);
   }

   public void setActions(String[] actions) {
      assert actions != null;

      for(int i = 0; i < actions.length; ++i) {
         String actionName = actions[i];

         try {
            PageFlowActionRuntimeMBeanImpl currentStats = new PageFlowActionRuntimeMBeanImpl(actionName, this);
            this._actions.put(actionName, currentStats);
         } catch (ManagementException var6) {
            this._logger.debug("Unable to create MBean for " + actionName, var6);
         }
      }

   }

   public void reportCreate() {
      ++this._createCount;
   }

   public void reportDestroy() {
      ++this._destroyCount;
   }

   public void setNumExceptionsToKeep(int num) {
      this._numExceptionsToKeep = num;
      Iterator iter = this._actions.values().iterator();

      while(iter.hasNext()) {
         PageFlowActionRuntimeMBeanImpl currentStats = (PageFlowActionRuntimeMBeanImpl)iter.next();
         if (currentStats != null) {
            currentStats.setNumExceptionsToKeep(num);
         }
      }

   }

   public void reportSuccess(String actionName, long totalTime) {
      PageFlowActionRuntimeMBeanImpl action = (PageFlowActionRuntimeMBeanImpl)this.getAction(actionName);
      if (action != null) {
         action.reportSuccess(totalTime);
      }

   }

   public void reportException(String actionName, Throwable t) {
      this.reportException(actionName, new PageFlowErrorImpl(t));
   }

   public void reportException(String actionName, PageFlowErrorImpl error) {
      PageFlowActionRuntimeMBeanImpl action = (PageFlowActionRuntimeMBeanImpl)this.getAction(actionName);
      if (action != null && error != null) {
         action.reportException(error);
      }

   }

   public void reportExceptionHandled(String actionName, Throwable t, long totalTime) {
      PageFlowActionRuntimeMBeanImpl action = (PageFlowActionRuntimeMBeanImpl)this.getAction(actionName);
      if (action != null) {
         action.reportHandledException(t, totalTime);
      }

   }

   public long getCreateCount() {
      return this._createCount;
   }

   public long getDestroyCount() {
      return this._destroyCount;
   }

   public long getLastResetTime() {
      return this._lastResetTime;
   }
}
