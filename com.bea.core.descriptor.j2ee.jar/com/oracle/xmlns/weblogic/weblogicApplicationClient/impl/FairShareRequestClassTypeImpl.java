package com.oracle.xmlns.weblogic.weblogicApplicationClient.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicApplicationClient.FairShareRequestClassType;
import com.sun.java.xml.ns.javaee.XsdIntegerType;
import com.sun.java.xml.ns.javaee.XsdStringType;
import javax.xml.namespace.QName;

public class FairShareRequestClassTypeImpl extends XmlComplexContentImpl implements FairShareRequestClassType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "name");
   private static final QName FAIRSHARE$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "fair-share");
   private static final QName ID$4 = new QName("", "id");

   public FairShareRequestClassTypeImpl(SchemaType sType) {
      super(sType);
   }

   public XsdStringType getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(NAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setName(XsdStringType name) {
      this.generatedSetterHelperImpl(name, NAME$0, 0, (short)1);
   }

   public XsdStringType addNewName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(NAME$0);
         return target;
      }
   }

   public XsdIntegerType getFairShare() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(FAIRSHARE$2, 0);
         return target == null ? null : target;
      }
   }

   public void setFairShare(XsdIntegerType fairShare) {
      this.generatedSetterHelperImpl(fairShare, FAIRSHARE$2, 0, (short)1);
   }

   public XsdIntegerType addNewFairShare() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(FAIRSHARE$2);
         return target;
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$4) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$4);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$4);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$4);
      }
   }
}
