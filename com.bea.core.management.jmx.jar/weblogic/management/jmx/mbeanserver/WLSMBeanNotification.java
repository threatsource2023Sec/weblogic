package weblogic.management.jmx.mbeanserver;

import javax.management.MBeanInfo;
import javax.management.MBeanServerNotification;
import javax.management.ObjectName;
import weblogic.management.visibility.MBeanType;
import weblogic.management.visibility.WLSMBeanVisibility;

public class WLSMBeanNotification extends MBeanServerNotification {
   private static final long serialVersionUID = 12221L;
   private WLSMBeanVisibility visibility;
   private MBeanType mBeanType;
   private MBeanInfo mBeanInfo;

   public MBeanType getMBeanType() {
      return this.mBeanType;
   }

   public WLSMBeanVisibility getVisibility() {
      return this.visibility;
   }

   public MBeanInfo getMBeanInfo() {
      return this.mBeanInfo;
   }

   public WLSMBeanNotification(String type, Object source, long sequenceNumber, ObjectName objectName, WLSMBeanVisibility visibility, MBeanType mBeanType, MBeanInfo mBeanInfo) {
      super(type, source, sequenceNumber, objectName);
      this.visibility = WLSMBeanVisibility.NONE;
      this.mBeanType = MBeanType.OTHER;
      this.visibility = visibility;
      this.mBeanType = mBeanType;
      this.mBeanInfo = mBeanInfo;
   }

   public String toString() {
      return super.toString() + " Visibility : " + this.visibility + " mBeanType :" + this.mBeanType;
   }
}
