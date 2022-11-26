package weblogic.apache.org.apache.log.filter;

import weblogic.apache.org.apache.log.LogEvent;
import weblogic.apache.org.apache.log.Priority;

public class PriorityFilter extends AbstractFilterTarget {
   private Priority m_priority;

   public PriorityFilter(Priority priority) {
      this.m_priority = priority;
   }

   public void setPriority(Priority priority) {
      this.m_priority = priority;
   }

   protected boolean filter(LogEvent event) {
      return this.m_priority.isGreater(event.getPriority());
   }
}
