package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.DescriptionType;
import com.sun.java.xml.ns.j2Ee.EjbLinkType;
import com.sun.java.xml.ns.j2Ee.EjbRefNameType;
import com.sun.java.xml.ns.j2Ee.EjbRefType;
import com.sun.java.xml.ns.j2Ee.EjbRefTypeType;
import com.sun.java.xml.ns.j2Ee.HomeType;
import com.sun.java.xml.ns.j2Ee.RemoteType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class EjbRefTypeImpl extends XmlComplexContentImpl implements EjbRefType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://java.sun.com/xml/ns/j2ee", "description");
   private static final QName EJBREFNAME$2 = new QName("http://java.sun.com/xml/ns/j2ee", "ejb-ref-name");
   private static final QName EJBREFTYPE$4 = new QName("http://java.sun.com/xml/ns/j2ee", "ejb-ref-type");
   private static final QName HOME$6 = new QName("http://java.sun.com/xml/ns/j2ee", "home");
   private static final QName REMOTE$8 = new QName("http://java.sun.com/xml/ns/j2ee", "remote");
   private static final QName EJBLINK$10 = new QName("http://java.sun.com/xml/ns/j2ee", "ejb-link");
   private static final QName ID$12 = new QName("", "id");

   public EjbRefTypeImpl(SchemaType sType) {
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

   public EjbRefNameType getEjbRefName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefNameType target = null;
         target = (EjbRefNameType)this.get_store().find_element_user(EJBREFNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public void setEjbRefName(EjbRefNameType ejbRefName) {
      this.generatedSetterHelperImpl(ejbRefName, EJBREFNAME$2, 0, (short)1);
   }

   public EjbRefNameType addNewEjbRefName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefNameType target = null;
         target = (EjbRefNameType)this.get_store().add_element_user(EJBREFNAME$2);
         return target;
      }
   }

   public EjbRefTypeType getEjbRefType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefTypeType target = null;
         target = (EjbRefTypeType)this.get_store().find_element_user(EJBREFTYPE$4, 0);
         return target == null ? null : target;
      }
   }

   public void setEjbRefType(EjbRefTypeType ejbRefType) {
      this.generatedSetterHelperImpl(ejbRefType, EJBREFTYPE$4, 0, (short)1);
   }

   public EjbRefTypeType addNewEjbRefType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefTypeType target = null;
         target = (EjbRefTypeType)this.get_store().add_element_user(EJBREFTYPE$4);
         return target;
      }
   }

   public HomeType getHome() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HomeType target = null;
         target = (HomeType)this.get_store().find_element_user(HOME$6, 0);
         return target == null ? null : target;
      }
   }

   public void setHome(HomeType home) {
      this.generatedSetterHelperImpl(home, HOME$6, 0, (short)1);
   }

   public HomeType addNewHome() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HomeType target = null;
         target = (HomeType)this.get_store().add_element_user(HOME$6);
         return target;
      }
   }

   public RemoteType getRemote() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RemoteType target = null;
         target = (RemoteType)this.get_store().find_element_user(REMOTE$8, 0);
         return target == null ? null : target;
      }
   }

   public void setRemote(RemoteType remote) {
      this.generatedSetterHelperImpl(remote, REMOTE$8, 0, (short)1);
   }

   public RemoteType addNewRemote() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RemoteType target = null;
         target = (RemoteType)this.get_store().add_element_user(REMOTE$8);
         return target;
      }
   }

   public EjbLinkType getEjbLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLinkType target = null;
         target = (EjbLinkType)this.get_store().find_element_user(EJBLINK$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEjbLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EJBLINK$10) != 0;
      }
   }

   public void setEjbLink(EjbLinkType ejbLink) {
      this.generatedSetterHelperImpl(ejbLink, EJBLINK$10, 0, (short)1);
   }

   public EjbLinkType addNewEjbLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLinkType target = null;
         target = (EjbLinkType)this.get_store().add_element_user(EJBLINK$10);
         return target;
      }
   }

   public void unsetEjbLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBLINK$10, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$12);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$12);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$12) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$12);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$12);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$12);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$12);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$12);
      }
   }
}
