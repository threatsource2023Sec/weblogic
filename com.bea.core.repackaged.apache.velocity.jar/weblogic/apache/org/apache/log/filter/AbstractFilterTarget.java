package weblogic.apache.org.apache.log.filter;

import weblogic.apache.org.apache.log.FilterTarget;
import weblogic.apache.org.apache.log.LogEvent;
import weblogic.apache.org.apache.log.LogTarget;

public abstract class AbstractFilterTarget implements FilterTarget, LogTarget {
   private LogTarget[] m_targets;

   public void addTarget(LogTarget target) {
      if (null == this.m_targets) {
         this.m_targets = new LogTarget[]{target};
      } else {
         LogTarget[] oldTargets = this.m_targets;
         this.m_targets = new LogTarget[oldTargets.length + 1];
         System.arraycopy(oldTargets, 0, this.m_targets, 0, oldTargets.length);
         this.m_targets[this.m_targets.length - 1] = target;
      }

   }

   protected abstract boolean filter(LogEvent var1);

   public void processEvent(LogEvent event) {
      if (null != this.m_targets && !this.filter(event)) {
         for(int i = 0; i < this.m_targets.length; ++i) {
            this.m_targets[i].processEvent(event);
         }

      }
   }
}
