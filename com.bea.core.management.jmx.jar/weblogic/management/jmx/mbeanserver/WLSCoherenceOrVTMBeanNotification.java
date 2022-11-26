package weblogic.management.jmx.mbeanserver;

import java.util.HashSet;
import java.util.Set;
import javax.management.MBeanInfo;
import javax.management.ObjectName;
import weblogic.management.visibility.MBeanType;
import weblogic.management.visibility.WLSMBeanVisibility;

public class WLSCoherenceOrVTMBeanNotification extends WLSMBeanNotification {
   private static final long serialVersionUID = 12221L;
   private Set partitions = new HashSet();

   public Set getPartitions() {
      return this.partitions;
   }

   public WLSCoherenceOrVTMBeanNotification(String type, Object source, long sequenceNumber, ObjectName objectName, WLSMBeanVisibility visibility, MBeanType mBeanType, MBeanInfo mBeanInfo, Set partitions) {
      super(type, source, sequenceNumber, objectName, visibility, mBeanType, mBeanInfo);
      this.partitions = partitions;
      if (!mBeanType.equals(MBeanType.WLS_VIRTUAL_TARGET) && !mBeanType.equals(MBeanType.WLS_COHERENCE)) {
         throw new IllegalArgumentException("Only MBeanType.WLS_VIRTUAL_TARGET or MBeanType.Coherence is allowed for this class");
      }
   }

   public String toString() {
      return super.toString() + " partitions :" + this.partitions;
   }
}
