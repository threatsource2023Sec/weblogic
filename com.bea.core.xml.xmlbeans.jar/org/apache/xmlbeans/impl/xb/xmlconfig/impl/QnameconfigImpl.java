package org.apache.xmlbeans.impl.xb.xmlconfig.impl;

import java.util.List;
import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlQName;
import org.apache.xmlbeans.XmlString;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xmlconfig.Qnameconfig;
import org.apache.xmlbeans.impl.xb.xmlconfig.Qnametargetlist;

public class QnameconfigImpl extends XmlComplexContentImpl implements Qnameconfig {
   private static final QName NAME$0 = new QName("", "name");
   private static final QName JAVANAME$2 = new QName("", "javaname");
   private static final QName TARGET$4 = new QName("", "target");

   public QnameconfigImpl(SchemaType sType) {
      super(sType);
   }

   public QName getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$0);
         return target == null ? null : target.getQNameValue();
      }
   }

   public XmlQName xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_attribute_user(NAME$0);
         return target;
      }
   }

   public boolean isSetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(NAME$0) != null;
      }
   }

   public void setName(QName name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(NAME$0);
         }

         target.setQNameValue(name);
      }
   }

   public void xsetName(XmlQName name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_attribute_user(NAME$0);
         if (target == null) {
            target = (XmlQName)this.get_store().add_attribute_user(NAME$0);
         }

         target.set(name);
      }
   }

   public void unsetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(NAME$0);
      }
   }

   public String getJavaname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(JAVANAME$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetJavaname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(JAVANAME$2);
         return target;
      }
   }

   public boolean isSetJavaname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(JAVANAME$2) != null;
      }
   }

   public void setJavaname(String javaname) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(JAVANAME$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(JAVANAME$2);
         }

         target.setStringValue(javaname);
      }
   }

   public void xsetJavaname(XmlString javaname) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(JAVANAME$2);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(JAVANAME$2);
         }

         target.set(javaname);
      }
   }

   public void unsetJavaname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(JAVANAME$2);
      }
   }

   public List getTarget() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(TARGET$4);
         if (target == null) {
            target = (SimpleValue)this.get_default_attribute_value(TARGET$4);
         }

         return target == null ? null : target.getListValue();
      }
   }

   public Qnametargetlist xgetTarget() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Qnametargetlist target = null;
         target = (Qnametargetlist)this.get_store().find_attribute_user(TARGET$4);
         if (target == null) {
            target = (Qnametargetlist)this.get_default_attribute_value(TARGET$4);
         }

         return target;
      }
   }

   public boolean isSetTarget() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(TARGET$4) != null;
      }
   }

   public void setTarget(List targetValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(TARGET$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(TARGET$4);
         }

         target.setListValue(targetValue);
      }
   }

   public void xsetTarget(Qnametargetlist targetValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Qnametargetlist target = null;
         target = (Qnametargetlist)this.get_store().find_attribute_user(TARGET$4);
         if (target == null) {
            target = (Qnametargetlist)this.get_store().add_attribute_user(TARGET$4);
         }

         target.set(targetValue);
      }
   }

   public void unsetTarget() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(TARGET$4);
      }
   }
}
