package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.DescriptionType;
import com.sun.java.xml.ns.j2Ee.FullyQualifiedClassType;
import com.sun.java.xml.ns.j2Ee.JndiNameType;
import com.sun.java.xml.ns.j2Ee.ResAuthType;
import com.sun.java.xml.ns.j2Ee.ResSharingScopeType;
import com.sun.java.xml.ns.j2Ee.ResourceRefType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ResourceRefTypeImpl extends XmlComplexContentImpl implements ResourceRefType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://java.sun.com/xml/ns/j2ee", "description");
   private static final QName RESREFNAME$2 = new QName("http://java.sun.com/xml/ns/j2ee", "res-ref-name");
   private static final QName RESTYPE$4 = new QName("http://java.sun.com/xml/ns/j2ee", "res-type");
   private static final QName RESAUTH$6 = new QName("http://java.sun.com/xml/ns/j2ee", "res-auth");
   private static final QName RESSHARINGSCOPE$8 = new QName("http://java.sun.com/xml/ns/j2ee", "res-sharing-scope");
   private static final QName ID$10 = new QName("", "id");

   public ResourceRefTypeImpl(SchemaType sType) {
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

   public JndiNameType getResRefName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().find_element_user(RESREFNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public void setResRefName(JndiNameType resRefName) {
      this.generatedSetterHelperImpl(resRefName, RESREFNAME$2, 0, (short)1);
   }

   public JndiNameType addNewResRefName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().add_element_user(RESREFNAME$2);
         return target;
      }
   }

   public FullyQualifiedClassType getResType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(RESTYPE$4, 0);
         return target == null ? null : target;
      }
   }

   public void setResType(FullyQualifiedClassType resType) {
      this.generatedSetterHelperImpl(resType, RESTYPE$4, 0, (short)1);
   }

   public FullyQualifiedClassType addNewResType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(RESTYPE$4);
         return target;
      }
   }

   public ResAuthType getResAuth() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResAuthType target = null;
         target = (ResAuthType)this.get_store().find_element_user(RESAUTH$6, 0);
         return target == null ? null : target;
      }
   }

   public void setResAuth(ResAuthType resAuth) {
      this.generatedSetterHelperImpl(resAuth, RESAUTH$6, 0, (short)1);
   }

   public ResAuthType addNewResAuth() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResAuthType target = null;
         target = (ResAuthType)this.get_store().add_element_user(RESAUTH$6);
         return target;
      }
   }

   public ResSharingScopeType getResSharingScope() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResSharingScopeType target = null;
         target = (ResSharingScopeType)this.get_store().find_element_user(RESSHARINGSCOPE$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetResSharingScope() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESSHARINGSCOPE$8) != 0;
      }
   }

   public void setResSharingScope(ResSharingScopeType resSharingScope) {
      this.generatedSetterHelperImpl(resSharingScope, RESSHARINGSCOPE$8, 0, (short)1);
   }

   public ResSharingScopeType addNewResSharingScope() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResSharingScopeType target = null;
         target = (ResSharingScopeType)this.get_store().add_element_user(RESSHARINGSCOPE$8);
         return target;
      }
   }

   public void unsetResSharingScope() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESSHARINGSCOPE$8, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$10);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$10);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$10) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$10);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$10);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$10);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$10);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$10);
      }
   }
}
