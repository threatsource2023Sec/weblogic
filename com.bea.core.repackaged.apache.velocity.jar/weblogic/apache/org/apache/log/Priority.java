package weblogic.apache.org.apache.log;

import java.io.ObjectStreamException;
import java.io.Serializable;

public final class Priority implements Serializable {
   public static final Priority DEBUG = new Priority("DEBUG", 5);
   public static final Priority INFO = new Priority("INFO", 10);
   public static final Priority WARN = new Priority("WARN", 15);
   public static final Priority ERROR = new Priority("ERROR", 20);
   public static final Priority FATAL_ERROR = new Priority("FATAL_ERROR", 25);
   private final String m_name;
   private final int m_priority;

   public static Priority getPriorityForName(String priority) {
      if (DEBUG.getName().equals(priority)) {
         return DEBUG;
      } else if (INFO.getName().equals(priority)) {
         return INFO;
      } else if (WARN.getName().equals(priority)) {
         return WARN;
      } else if (ERROR.getName().equals(priority)) {
         return ERROR;
      } else {
         return FATAL_ERROR.getName().equals(priority) ? FATAL_ERROR : DEBUG;
      }
   }

   private Priority(String name, int priority) {
      this.m_name = name;
      this.m_priority = priority;
   }

   public String toString() {
      return "Priority[" + this.getName() + "/" + this.getValue() + "]";
   }

   public int getValue() {
      return this.m_priority;
   }

   public String getName() {
      return this.m_name;
   }

   public boolean isGreater(Priority other) {
      return this.m_priority > other.getValue();
   }

   public boolean isLower(Priority other) {
      return this.m_priority < other.getValue();
   }

   public boolean isLowerOrEqual(Priority other) {
      return this.m_priority <= other.getValue();
   }

   private Object readResolve() throws ObjectStreamException {
      return getPriorityForName(this.m_name);
   }
}
