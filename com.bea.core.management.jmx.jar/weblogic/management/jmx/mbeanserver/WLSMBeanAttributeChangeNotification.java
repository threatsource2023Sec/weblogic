package weblogic.management.jmx.mbeanserver;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanInfo;
import weblogic.management.visibility.MBeanType;
import weblogic.management.visibility.WLSMBeanVisibility;

public class WLSMBeanAttributeChangeNotification extends AttributeChangeNotification {
   private static final long serialVersionUID = 12221L;
   private WLSMBeanVisibility visibility;
   private MBeanType mBeanType;
   private boolean isAttributeVisible;
   private MBeanInfo mBeanInfo;

   public boolean isAttributeVisible() {
      return this.isAttributeVisible;
   }

   public MBeanType getMBeanType() {
      return this.mBeanType;
   }

   public WLSMBeanVisibility getVisibility() {
      return this.visibility;
   }

   public MBeanInfo getMBeanInfo() {
      return this.mBeanInfo;
   }

   public WLSMBeanAttributeChangeNotification(Object source, long sequenceNumber, long timeStamp, String msg, String attributeName, String attributeType, Object oldValue, Object newValue, WLSMBeanVisibility visibility, MBeanType mBeanType, MBeanInfo mBeanInfo, boolean isAttributeVisible) {
      super(source, sequenceNumber, timeStamp, msg, attributeName, attributeType, oldValue, newValue);
      this.visibility = WLSMBeanVisibility.NONE;
      this.mBeanType = MBeanType.OTHER;
      this.isAttributeVisible = false;
      this.visibility = visibility;
      this.isAttributeVisible = isAttributeVisible;
      this.mBeanType = mBeanType;
      this.mBeanInfo = mBeanInfo;
   }

   public String toString() {
      return super.toString() + " :visibility = " + this.visibility + " :mbeanType = " + this.mBeanType + " :Attribute = " + this.getAttributeName() + " :isAttributeVisible = " + this.isAttributeVisible;
   }
}
