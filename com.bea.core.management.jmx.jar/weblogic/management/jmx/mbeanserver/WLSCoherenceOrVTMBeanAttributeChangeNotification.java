package weblogic.management.jmx.mbeanserver;

import java.util.Set;
import javax.management.MBeanInfo;
import weblogic.management.visibility.MBeanType;
import weblogic.management.visibility.WLSMBeanVisibility;

public class WLSCoherenceOrVTMBeanAttributeChangeNotification extends WLSMBeanAttributeChangeNotification {
   private static final long serialVersionUID = 12221L;
   private Set partitions;

   public Set getPartitions() {
      return this.partitions;
   }

   public WLSCoherenceOrVTMBeanAttributeChangeNotification(Object source, long sequenceNumber, long timeStamp, String msg, String attributeName, String attributeType, Object oldValue, Object newValue, WLSMBeanVisibility visibility, MBeanType mBeanType, boolean isAttributeVisible, MBeanInfo mBeanInfo, Set partitions) {
      super(source, sequenceNumber, timeStamp, msg, attributeName, attributeType, oldValue, newValue, visibility, mBeanType, mBeanInfo, isAttributeVisible);
      this.partitions = partitions;
      if (!mBeanType.equals(MBeanType.WLS_VIRTUAL_TARGET) && !mBeanType.equals(MBeanType.WLS_COHERENCE)) {
         throw new IllegalArgumentException("Only MBeanType.VIRTUAL_TARGET or MBeanType.Coherence is allowed for this class");
      }
   }

   public String toString() {
      return super.toString() + " :partitions = " + this.partitions;
   }
}
