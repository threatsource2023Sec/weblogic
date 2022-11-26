package weblogic.apache.org.apache.log;

import java.io.ObjectStreamException;
import java.io.Serializable;

public final class LogEvent implements Serializable {
   private static final long START_TIME = System.currentTimeMillis();
   private String m_category;
   private String m_message;
   private Throwable m_throwable;
   private long m_time;
   private Priority m_priority;
   private ContextMap m_contextMap;
   /** @deprecated */
   private transient ContextStack m_contextStack;

   public final Priority getPriority() {
      return this.m_priority;
   }

   public final void setPriority(Priority priority) {
      this.m_priority = priority;
   }

   public final ContextMap getContextMap() {
      return this.m_contextMap;
   }

   public final void setContextMap(ContextMap contextMap) {
      this.m_contextMap = contextMap;
   }

   /** @deprecated */
   public final ContextStack getContextStack() {
      return this.m_contextStack;
   }

   /** @deprecated */
   public final void setContextStack(ContextStack contextStack) {
      this.m_contextStack = contextStack;
   }

   public final String getCategory() {
      return this.m_category;
   }

   public final String getMessage() {
      return this.m_message;
   }

   public final Throwable getThrowable() {
      return this.m_throwable;
   }

   public final long getTime() {
      return this.m_time;
   }

   public final long getRelativeTime() {
      return this.m_time - START_TIME;
   }

   public final void setCategory(String category) {
      this.m_category = category;
   }

   public final void setMessage(String message) {
      this.m_message = message;
   }

   public final void setThrowable(Throwable throwable) {
      this.m_throwable = throwable;
   }

   public final void setTime(long time) {
      this.m_time = time;
   }

   private Object readResolve() throws ObjectStreamException {
      if (null == this.m_category) {
         this.m_category = "";
      }

      if (null == this.m_message) {
         this.m_message = "";
      }

      String priorityName = "";
      if (null != this.m_priority) {
         priorityName = this.m_priority.getName();
      }

      this.m_priority = Priority.getPriorityForName(priorityName);
      return this;
   }
}
