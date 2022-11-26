package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.FullyQualifiedClassType;
import com.sun.java.xml.ns.j2Ee.PackageMappingType;
import com.sun.java.xml.ns.j2Ee.XsdAnyURIType;
import javax.xml.namespace.QName;

public class PackageMappingTypeImpl extends XmlComplexContentImpl implements PackageMappingType {
   private static final long serialVersionUID = 1L;
   private static final QName PACKAGETYPE$0 = new QName("http://java.sun.com/xml/ns/j2ee", "package-type");
   private static final QName NAMESPACEURI$2 = new QName("http://java.sun.com/xml/ns/j2ee", "namespaceURI");
   private static final QName ID$4 = new QName("", "id");

   public PackageMappingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public FullyQualifiedClassType getPackageType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(PACKAGETYPE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setPackageType(FullyQualifiedClassType packageType) {
      this.generatedSetterHelperImpl(packageType, PACKAGETYPE$0, 0, (short)1);
   }

   public FullyQualifiedClassType addNewPackageType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(PACKAGETYPE$0);
         return target;
      }
   }

   public XsdAnyURIType getNamespaceURI() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdAnyURIType target = null;
         target = (XsdAnyURIType)this.get_store().find_element_user(NAMESPACEURI$2, 0);
         return target == null ? null : target;
      }
   }

   public void setNamespaceURI(XsdAnyURIType namespaceURI) {
      this.generatedSetterHelperImpl(namespaceURI, NAMESPACEURI$2, 0, (short)1);
   }

   public XsdAnyURIType addNewNamespaceURI() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdAnyURIType target = null;
         target = (XsdAnyURIType)this.get_store().add_element_user(NAMESPACEURI$2);
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
