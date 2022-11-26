package weblogic.servlet.internal;

import java.util.LinkedList;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementException;
import weblogic.management.runtime.PageFlowActionRuntimeMBean;
import weblogic.management.runtime.PageFlowError;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public final class PageFlowActionRuntimeMBeanImpl extends RuntimeMBeanDelegate implements PageFlowActionRuntimeMBean {
   private static final long serialVersionUID = 1L;
   private String _actionName;
   private long _successCount;
   private long _exceptionCount;
   private long _handledExceptionCount;
   private long _totalSuccessDispatchTime;
   private long _minSuccessDispatchTime;
   private long _maxSuccessDispatchTime;
   private long _totalHandledExceptionDispatchTime;
   private long _minHandledExceptionDispatchTime;
   private long _maxHandledExceptionDispatchTime;
   private int _numExceptionsToKeep;
   private LinkedList _exceptionList;
   private DebugLogger _logger;
   private static int MAX_NUM_EXCEPTIONS = 5;

   public PageFlowActionRuntimeMBeanImpl(String actionName, RuntimeMBean parent) throws ManagementException {
      super(actionName, parent);
      this._actionName = actionName;
      this._exceptionList = new LinkedList();
      this._numExceptionsToKeep = MAX_NUM_EXCEPTIONS;
      this._logger = PageFlowsRuntimeMBeanImpl.logger;
   }

   public void reset() {
      synchronized(this) {
         this._successCount = 0L;
         this._exceptionCount = 0L;
         this._handledExceptionCount = 0L;
         this._totalSuccessDispatchTime = 0L;
         this._minSuccessDispatchTime = 0L;
         this._maxSuccessDispatchTime = 0L;
         this._totalHandledExceptionDispatchTime = 0L;
         this._minHandledExceptionDispatchTime = 0L;
         this._maxHandledExceptionDispatchTime = 0L;
         this._exceptionList.clear();
      }
   }

   public String getActionName() {
      return this._actionName;
   }

   public long getSuccessCount() {
      return this._successCount;
   }

   public long getExceptionCount() {
      return this._exceptionCount;
   }

   public long getHandledExceptionCount() {
      return this._handledExceptionCount;
   }

   public long getSuccessDispatchTimeTotal() {
      return this._totalSuccessDispatchTime;
   }

   public long getSuccessDispatchTimeHigh() {
      return this._maxSuccessDispatchTime;
   }

   public long getSuccessDispatchTimeLow() {
      return this._minSuccessDispatchTime;
   }

   public long getSuccessDispatchTimeAverage() {
      return this._successCount > 0L ? this._totalSuccessDispatchTime / this._successCount : 0L;
   }

   public long getHandledExceptionDispatchTimeTotal() {
      return this._totalHandledExceptionDispatchTime;
   }

   public long getHandledExceptionDispatchTimeHigh() {
      return this._maxHandledExceptionDispatchTime;
   }

   public long getHandledExceptionDispatchTimeLow() {
      return this._minHandledExceptionDispatchTime;
   }

   public long getHandledExceptionDispatchTimeAverage() {
      return this._handledExceptionCount > 0L ? this._totalHandledExceptionDispatchTime / this._handledExceptionCount : 0L;
   }

   void reportSuccess(long dispatchTime) {
      ++this._successCount;
      this._totalSuccessDispatchTime += dispatchTime;
      if (dispatchTime < this._minSuccessDispatchTime) {
         this._minSuccessDispatchTime = dispatchTime;
      } else if (dispatchTime > this._maxSuccessDispatchTime) {
         this._maxSuccessDispatchTime = dispatchTime;
         if (this._minSuccessDispatchTime == 0L) {
            this._minSuccessDispatchTime = dispatchTime;
         }
      }

   }

   void reportException(PageFlowErrorImpl error) {
      ++this._exceptionCount;
      this.enqueueException(error);
   }

   void reportHandledException(Throwable t, long timeTaken) {
      ++this._handledExceptionCount;
      this._totalHandledExceptionDispatchTime += timeTaken;
      if (this._minHandledExceptionDispatchTime == 0L) {
         this._minHandledExceptionDispatchTime = timeTaken;
      } else if (timeTaken < this._minHandledExceptionDispatchTime) {
         this._minHandledExceptionDispatchTime = timeTaken;
      }

      if (timeTaken > this._maxHandledExceptionDispatchTime) {
         this._maxHandledExceptionDispatchTime = timeTaken;
      }

   }

   public PageFlowError[] getLastExceptions() {
      synchronized(this._exceptionList) {
         PageFlowError[] result = new PageFlowError[this._exceptionList.size()];
         this._exceptionList.toArray(result);
         return result;
      }
   }

   void setNumExceptionsToKeep(int numToKeep) {
      this._numExceptionsToKeep = numToKeep;
      if (numToKeep == 0) {
         this._exceptionList.clear();
      }

   }

   private void enqueueException(PageFlowErrorImpl error) {
      if (error != null && this._numExceptionsToKeep > 0) {
         synchronized(this._exceptionList) {
            while(this._exceptionList.size() >= this._numExceptionsToKeep) {
               this._exceptionList.removeFirst();
            }

            this._exceptionList.addLast(error);
         }
      }

   }
}
