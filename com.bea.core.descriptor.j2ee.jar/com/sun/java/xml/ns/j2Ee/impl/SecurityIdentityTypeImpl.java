package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.DescriptionType;
import com.sun.java.xml.ns.j2Ee.EmptyType;
import com.sun.java.xml.ns.j2Ee.RunAsType;
import com.sun.java.xml.ns.j2Ee.SecurityIdentityType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class SecurityIdentityTypeImpl extends XmlComplexContentImpl implements SecurityIdentityType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://java.sun.com/xml/ns/j2ee", "description");
   private static final QName USECALLERIDENTITY$2 = new QName("http://java.sun.com/xml/ns/j2ee", "use-caller-identity");
   private static final QName RUNAS$4 = new QName("http://java.sun.com/xml/ns/j2ee", "run-as");
   private static final QName ID$6 = new QName("", "id");

   public SecurityIdentityTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DescriptionType[] getDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DESCRIPTION$0, targetList);
         DescriptionType[] result = new DescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DescriptionType getDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().find_element_user(DESCRIPTION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESCRIPTION$0);
      }
   }

   public void setDescriptionArray(DescriptionType[] descriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(descriptionArray, DESCRIPTION$0);
   }

   public void setDescriptionArray(int i, DescriptionType description) {
      this.generatedSetterHelperImpl(description, DESCRIPTION$0, i, (short)2);
   }

   public DescriptionType insertNewDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().insert_element_user(DESCRIPTION$0, i);
         return target;
      }
   }

   public DescriptionType addNewDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().add_element_user(DESCRIPTION$0);
         return target;
      }
   }

   public void removeDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$0, i);
      }
   }

   public EmptyType getUseCallerIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmptyType target = null;
         target = (EmptyType)this.get_store().find_element_user(USECALLERIDENTITY$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetUseCallerIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USECALLERIDENTITY$2) != 0;
      }
   }

   public void setUseCallerIdentity(EmptyType useCallerIdentity) {
      this.generatedSetterHelperImpl(useCallerIdentity, USECALLERIDENTITY$2, 0, (short)1);
   }

   public EmptyType addNewUseCallerIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmptyType target = null;
         target = (EmptyType)this.get_store().add_element_user(USECALLERIDENTITY$2);
         return target;
      }
   }

   public void unsetUseCallerIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USECALLERIDENTITY$2, 0);
      }
   }

   public RunAsType getRunAs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RunAsType target = null;
         target = (RunAsType)this.get_store().find_element_user(RUNAS$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRunAs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RUNAS$4) != 0;
      }
   }

   public void setRunAs(RunAsType runAs) {
      this.generatedSetterHelperImpl(runAs, RUNAS$4, 0, (short)1);
   }

   public RunAsType addNewRunAs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RunAsType target = null;
         target = (RunAsType)this.get_store().add_element_user(RUNAS$4);
         return target;
      }
   }

   public void unsetRunAs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RUNAS$4, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$6) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$6);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$6);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$6);
      }
   }
}
