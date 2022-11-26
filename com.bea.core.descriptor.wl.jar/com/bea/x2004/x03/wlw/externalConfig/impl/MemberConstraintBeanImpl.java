package com.bea.x2004.x03.wlw.externalConfig.impl;

import com.bea.x2004.x03.wlw.externalConfig.MemberConstraintBean;
import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class MemberConstraintBeanImpl extends XmlComplexContentImpl implements MemberConstraintBean {
   private static final long serialVersionUID = 1L;
   private static final QName CONSTRAINTTYPE$0 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "constraint-type");
   private static final QName MAXLENGTH$2 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "max-length");
   private static final QName MINVALUE$4 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "min-value");
   private static final QName MAXVALUE$6 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "max-value");
   private static final QName SCALE$8 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "scale");

   public MemberConstraintBeanImpl(SchemaType sType) {
      super(sType);
   }

   public MemberConstraintBean.ConstraintType.Enum getConstraintType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONSTRAINTTYPE$0, 0);
         return target == null ? null : (MemberConstraintBean.ConstraintType.Enum)target.getEnumValue();
      }
   }

   public MemberConstraintBean.ConstraintType xgetConstraintType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MemberConstraintBean.ConstraintType target = null;
         target = (MemberConstraintBean.ConstraintType)this.get_store().find_element_user(CONSTRAINTTYPE$0, 0);
         return target;
      }
   }

   public void setConstraintType(MemberConstraintBean.ConstraintType.Enum constraintType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONSTRAINTTYPE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONSTRAINTTYPE$0);
         }

         target.setEnumValue(constraintType);
      }
   }

   public void xsetConstraintType(MemberConstraintBean.ConstraintType constraintType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MemberConstraintBean.ConstraintType target = null;
         target = (MemberConstraintBean.ConstraintType)this.get_store().find_element_user(CONSTRAINTTYPE$0, 0);
         if (target == null) {
            target = (MemberConstraintBean.ConstraintType)this.get_store().add_element_user(CONSTRAINTTYPE$0);
         }

         target.set(constraintType);
      }
   }

   public String getMaxLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXLENGTH$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetMaxLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MAXLENGTH$2, 0);
         return target;
      }
   }

   public boolean isSetMaxLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXLENGTH$2) != 0;
      }
   }

   public void setMaxLength(String maxLength) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXLENGTH$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXLENGTH$2);
         }

         target.setStringValue(maxLength);
      }
   }

   public void xsetMaxLength(XmlString maxLength) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MAXLENGTH$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MAXLENGTH$2);
         }

         target.set(maxLength);
      }
   }

   public void unsetMaxLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXLENGTH$2, 0);
      }
   }

   public String getMinValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MINVALUE$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetMinValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MINVALUE$4, 0);
         return target;
      }
   }

   public boolean isSetMinValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MINVALUE$4) != 0;
      }
   }

   public void setMinValue(String minValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MINVALUE$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MINVALUE$4);
         }

         target.setStringValue(minValue);
      }
   }

   public void xsetMinValue(XmlString minValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MINVALUE$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MINVALUE$4);
         }

         target.set(minValue);
      }
   }

   public void unsetMinValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MINVALUE$4, 0);
      }
   }

   public String getMaxValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXVALUE$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetMaxValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MAXVALUE$6, 0);
         return target;
      }
   }

   public boolean isSetMaxValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXVALUE$6) != 0;
      }
   }

   public void setMaxValue(String maxValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXVALUE$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXVALUE$6);
         }

         target.setStringValue(maxValue);
      }
   }

   public void xsetMaxValue(XmlString maxValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MAXVALUE$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MAXVALUE$6);
         }

         target.set(maxValue);
      }
   }

   public void unsetMaxValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXVALUE$6, 0);
      }
   }

   public int getScale() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SCALE$8, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetScale() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(SCALE$8, 0);
         return target;
      }
   }

   public boolean isSetScale() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SCALE$8) != 0;
      }
   }

   public void setScale(int scale) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SCALE$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SCALE$8);
         }

         target.setIntValue(scale);
      }
   }

   public void xsetScale(XmlInt scale) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(SCALE$8, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(SCALE$8);
         }

         target.set(scale);
      }
   }

   public void unsetScale() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SCALE$8, 0);
      }
   }

   public static class ConstraintTypeImpl extends JavaStringEnumerationHolderEx implements MemberConstraintBean.ConstraintType {
      private static final long serialVersionUID = 1L;

      public ConstraintTypeImpl(SchemaType sType) {
         super(sType, false);
      }

      protected ConstraintTypeImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
