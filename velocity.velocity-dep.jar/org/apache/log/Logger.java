package org.apache.log;

public class Logger {
   public static final char CATEGORY_SEPARATOR = '.';
   private final ErrorHandler m_errorHandler;
   private final Logger m_parent;
   private final String m_category;
   private Logger[] m_children;
   private LogTarget[] m_logTargets;
   private boolean m_logTargetsForceSet;
   private Priority m_priority;
   private boolean m_priorityForceSet;
   private boolean m_additivity;

   Logger(ErrorHandler errorHandler, String category, LogTarget[] logTargets, Logger parent) {
      this.m_errorHandler = errorHandler;
      this.m_category = category;
      this.m_logTargets = logTargets;
      this.m_parent = parent;
      if (null == this.m_logTargets) {
         this.unsetLogTargets();
      }

      this.unsetPriority();
   }

   public final boolean isDebugEnabled() {
      return this.getPriority().isLowerOrEqual(Priority.DEBUG);
   }

   public final void debug(String message, Throwable throwable) {
      if (this.isDebugEnabled()) {
         this.output(Priority.DEBUG, message, throwable);
      }

   }

   public final void debug(String message) {
      if (this.isDebugEnabled()) {
         this.output(Priority.DEBUG, message, (Throwable)null);
      }

   }

   public final boolean isInfoEnabled() {
      return this.getPriority().isLowerOrEqual(Priority.INFO);
   }

   public final void info(String message, Throwable throwable) {
      if (this.isInfoEnabled()) {
         this.output(Priority.INFO, message, throwable);
      }

   }

   public final void info(String message) {
      if (this.isInfoEnabled()) {
         this.output(Priority.INFO, message, (Throwable)null);
      }

   }

   public final boolean isWarnEnabled() {
      return this.getPriority().isLowerOrEqual(Priority.WARN);
   }

   public final void warn(String message, Throwable throwable) {
      if (this.isWarnEnabled()) {
         this.output(Priority.WARN, message, throwable);
      }

   }

   public final void warn(String message) {
      if (this.isWarnEnabled()) {
         this.output(Priority.WARN, message, (Throwable)null);
      }

   }

   public final boolean isErrorEnabled() {
      return this.getPriority().isLowerOrEqual(Priority.ERROR);
   }

   public final void error(String message, Throwable throwable) {
      if (this.isErrorEnabled()) {
         this.output(Priority.ERROR, message, throwable);
      }

   }

   public final void error(String message) {
      if (this.isErrorEnabled()) {
         this.output(Priority.ERROR, message, (Throwable)null);
      }

   }

   public final boolean isFatalErrorEnabled() {
      return this.getPriority().isLowerOrEqual(Priority.FATAL_ERROR);
   }

   public final void fatalError(String message, Throwable throwable) {
      if (this.isFatalErrorEnabled()) {
         this.output(Priority.FATAL_ERROR, message, throwable);
      }

   }

   public final void fatalError(String message) {
      if (this.isFatalErrorEnabled()) {
         this.output(Priority.FATAL_ERROR, message, (Throwable)null);
      }

   }

   public final void setAdditivity(boolean additivity) {
      this.m_additivity = additivity;
   }

   public final boolean isPriorityEnabled(Priority priority) {
      return this.getPriority().isLowerOrEqual(priority);
   }

   public final void log(Priority priority, String message, Throwable throwable) {
      if (this.getPriority().isLowerOrEqual(priority)) {
         this.output(priority, message, throwable);
      }

   }

   public final void log(Priority priority, String message) {
      this.log(priority, message, (Throwable)null);
   }

   public synchronized void setPriority(Priority priority) {
      this.m_priority = priority;
      this.m_priorityForceSet = true;
      this.resetChildPriorities(false);
   }

   public synchronized void unsetPriority() {
      this.unsetPriority(false);
   }

   public synchronized void unsetPriority(boolean recursive) {
      if (null != this.m_parent) {
         this.m_priority = this.m_parent.getPriority();
      } else {
         this.m_priority = Priority.DEBUG;
      }

      this.m_priorityForceSet = false;
      this.resetChildPriorities(recursive);
   }

   public synchronized void setLogTargets(LogTarget[] logTargets) {
      this.m_logTargets = logTargets;
      this.setupErrorHandlers();
      this.m_logTargetsForceSet = true;
      this.resetChildLogTargets(false);
   }

   public synchronized void unsetLogTargets() {
      this.unsetLogTargets(false);
   }

   public synchronized void unsetLogTargets(boolean recursive) {
      if (null != this.m_parent) {
         this.m_logTargets = this.m_parent.safeGetLogTargets();
      } else {
         this.m_logTargets = null;
      }

      this.m_logTargetsForceSet = false;
      this.resetChildLogTargets(recursive);
   }

   public synchronized Logger[] getChildren() {
      if (null == this.m_children) {
         return new Logger[0];
      } else {
         Logger[] children = new Logger[this.m_children.length];

         for(int i = 0; i < children.length; ++i) {
            children[i] = this.m_children[i];
         }

         return children;
      }
   }

   public synchronized Logger getChildLogger(String subCategory) throws IllegalArgumentException {
      int end = subCategory.indexOf(46);
      String nextCategory = null;
      String remainder = null;
      if (-1 == end) {
         nextCategory = subCategory;
      } else {
         if (end == 0) {
            throw new IllegalArgumentException("Logger categories MUST not have empty elements");
         }

         nextCategory = subCategory.substring(0, end);
         remainder = subCategory.substring(end + 1);
      }

      String category = null;
      if (this.m_category.equals("")) {
         category = nextCategory;
      } else {
         category = this.m_category + '.' + nextCategory;
      }

      if (null != this.m_children) {
         for(int i = 0; i < this.m_children.length; ++i) {
            if (this.m_children[i].getCategory().equals(category)) {
               if (null == remainder) {
                  return this.m_children[i];
               }

               return this.m_children[i].getChildLogger(remainder);
            }
         }
      }

      Logger child = new Logger(this.m_errorHandler, category, (LogTarget[])null, this);
      if (null == this.m_children) {
         this.m_children = new Logger[]{child};
      } else {
         Logger[] children = new Logger[this.m_children.length + 1];
         System.arraycopy(this.m_children, 0, children, 0, this.m_children.length);
         children[this.m_children.length] = child;
         this.m_children = children;
      }

      return null == remainder ? child : child.getChildLogger(remainder);
   }

   /** @deprecated */
   public final Priority getPriority() {
      return this.m_priority;
   }

   /** @deprecated */
   public final String getCategory() {
      return this.m_category;
   }

   /** @deprecated */
   public LogTarget[] getLogTargets() {
      return new LogTarget[0];
   }

   private final void output(Priority priority, String message, Throwable throwable) {
      LogEvent event = new LogEvent();
      event.setCategory(this.m_category);
      event.setContextStack(ContextStack.getCurrentContext(false));
      event.setContextMap(ContextMap.getCurrentContext(false));
      if (null != message) {
         event.setMessage(message);
      } else {
         event.setMessage("");
      }

      event.setThrowable(throwable);
      event.setPriority(priority);
      event.setTime(System.currentTimeMillis());
      this.output(event);
   }

   private final void output(LogEvent event) {
      LogTarget[] targets = this.m_logTargets;
      if (null == targets) {
         String message = "LogTarget is null for category '" + this.m_category + "'";
         this.m_errorHandler.error(message, (Throwable)null, event);
      } else if (!this.m_additivity) {
         this.fireEvent(event, targets);
      } else {
         if (this.m_logTargetsForceSet) {
            this.fireEvent(event, targets);
         }

         if (null != this.m_parent) {
            this.m_parent.output(event);
         }
      }

   }

   private final void fireEvent(LogEvent event, LogTarget[] targets) {
      for(int i = 0; i < targets.length; ++i) {
         targets[i].processEvent(event);
      }

   }

   private synchronized void resetChildPriorities(boolean recursive) {
      if (null != this.m_children) {
         Logger[] children = this.m_children;

         for(int i = 0; i < children.length; ++i) {
            children[i].resetPriority(recursive);
         }

      }
   }

   private synchronized void resetPriority(boolean recursive) {
      if (recursive) {
         this.m_priorityForceSet = false;
      } else if (this.m_priorityForceSet) {
         return;
      }

      this.m_priority = this.m_parent.getPriority();
      this.resetChildPriorities(recursive);
   }

   private synchronized LogTarget[] safeGetLogTargets() {
      if (null == this.m_logTargets) {
         return null == this.m_parent ? new LogTarget[0] : this.m_parent.safeGetLogTargets();
      } else {
         LogTarget[] logTargets = new LogTarget[this.m_logTargets.length];

         for(int i = 0; i < logTargets.length; ++i) {
            logTargets[i] = this.m_logTargets[i];
         }

         return logTargets;
      }
   }

   private synchronized void resetChildLogTargets(boolean recursive) {
      if (null != this.m_children) {
         for(int i = 0; i < this.m_children.length; ++i) {
            this.m_children[i].resetLogTargets(recursive);
         }

      }
   }

   private synchronized void setupErrorHandlers() {
      if (null != this.m_logTargets) {
         for(int i = 0; i < this.m_logTargets.length; ++i) {
            LogTarget target = this.m_logTargets[i];
            if (target instanceof ErrorAware) {
               ((ErrorAware)target).setErrorHandler(this.m_errorHandler);
            }
         }

      }
   }

   private synchronized void resetLogTargets(boolean recursive) {
      if (recursive) {
         this.m_logTargetsForceSet = false;
      } else if (this.m_logTargetsForceSet) {
         return;
      }

      this.m_logTargets = this.m_parent.safeGetLogTargets();
      this.resetChildLogTargets(recursive);
   }
}
