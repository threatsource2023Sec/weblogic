package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlLong;
import com.oracle.xmlns.weblogic.weblogicJms.QuotaType;
import javax.xml.namespace.QName;

public class QuotaTypeImpl extends NamedEntityTypeImpl implements QuotaType {
   private static final long serialVersionUID = 1L;
   private static final QName BYTESMAXIMUM$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "bytes-maximum");
   private static final QName MESSAGESMAXIMUM$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "messages-maximum");
   private static final QName POLICY$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "policy");
   private static final QName SHARED$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "shared");

   public QuotaTypeImpl(SchemaType sType) {
      super(sType);
   }

   public long getBytesMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BYTESMAXIMUM$0, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetBytesMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(BYTESMAXIMUM$0, 0);
         return target;
      }
   }

   public boolean isSetBytesMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BYTESMAXIMUM$0) != 0;
      }
   }

   public void setBytesMaximum(long bytesMaximum) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BYTESMAXIMUM$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(BYTESMAXIMUM$0);
         }

         target.setLongValue(bytesMaximum);
      }
   }

   public void xsetBytesMaximum(XmlLong bytesMaximum) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(BYTESMAXIMUM$0, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(BYTESMAXIMUM$0);
         }

         target.set(bytesMaximum);
      }
   }

   public void unsetBytesMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BYTESMAXIMUM$0, 0);
      }
   }

   public long getMessagesMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MESSAGESMAXIMUM$2, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetMessagesMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(MESSAGESMAXIMUM$2, 0);
         return target;
      }
   }

   public boolean isSetMessagesMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGESMAXIMUM$2) != 0;
      }
   }

   public void setMessagesMaximum(long messagesMaximum) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MESSAGESMAXIMUM$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MESSAGESMAXIMUM$2);
         }

         target.setLongValue(messagesMaximum);
      }
   }

   public void xsetMessagesMaximum(XmlLong messagesMaximum) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(MESSAGESMAXIMUM$2, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(MESSAGESMAXIMUM$2);
         }

         target.set(messagesMaximum);
      }
   }

   public void unsetMessagesMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGESMAXIMUM$2, 0);
      }
   }

   public QuotaType.Policy.Enum getPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(POLICY$4, 0);
         return target == null ? null : (QuotaType.Policy.Enum)target.getEnumValue();
      }
   }

   public QuotaType.Policy xgetPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QuotaType.Policy target = null;
         target = (QuotaType.Policy)this.get_store().find_element_user(POLICY$4, 0);
         return target;
      }
   }

   public boolean isSetPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(POLICY$4) != 0;
      }
   }

   public void setPolicy(QuotaType.Policy.Enum policy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(POLICY$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(POLICY$4);
         }

         target.setEnumValue(policy);
      }
   }

   public void xsetPolicy(QuotaType.Policy policy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QuotaType.Policy target = null;
         target = (QuotaType.Policy)this.get_store().find_element_user(POLICY$4, 0);
         if (target == null) {
            target = (QuotaType.Policy)this.get_store().add_element_user(POLICY$4);
         }

         target.set(policy);
      }
   }

   public void unsetPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(POLICY$4, 0);
      }
   }

   public boolean getShared() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SHARED$6, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetShared() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SHARED$6, 0);
         return target;
      }
   }

   public boolean isSetShared() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SHARED$6) != 0;
      }
   }

   public void setShared(boolean shared) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SHARED$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SHARED$6);
         }

         target.setBooleanValue(shared);
      }
   }

   public void xsetShared(XmlBoolean shared) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SHARED$6, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SHARED$6);
         }

         target.set(shared);
      }
   }

   public void unsetShared() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SHARED$6, 0);
      }
   }

   public static class PolicyImpl extends JavaStringEnumerationHolderEx implements QuotaType.Policy {
      private static final long serialVersionUID = 1L;

      public PolicyImpl(SchemaType sType) {
         super(sType, false);
      }

      protected PolicyImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
