package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInteger;
import com.bea.xml.XmlLong;
import com.sun.java.xml.ns.javaee.MultipartConfigType;
import com.sun.java.xml.ns.javaee.String;
import java.math.BigInteger;
import javax.xml.namespace.QName;

public class MultipartConfigTypeImpl extends XmlComplexContentImpl implements MultipartConfigType {
   private static final long serialVersionUID = 1L;
   private static final QName LOCATION$0 = new QName("http://java.sun.com/xml/ns/javaee", "location");
   private static final QName MAXFILESIZE$2 = new QName("http://java.sun.com/xml/ns/javaee", "max-file-size");
   private static final QName MAXREQUESTSIZE$4 = new QName("http://java.sun.com/xml/ns/javaee", "max-request-size");
   private static final QName FILESIZETHRESHOLD$6 = new QName("http://java.sun.com/xml/ns/javaee", "file-size-threshold");

   public MultipartConfigTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getLocation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(LOCATION$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLocation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOCATION$0) != 0;
      }
   }

   public void setLocation(String location) {
      this.generatedSetterHelperImpl(location, LOCATION$0, 0, (short)1);
   }

   public String addNewLocation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(LOCATION$0);
         return target;
      }
   }

   public void unsetLocation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOCATION$0, 0);
      }
   }

   public long getMaxFileSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXFILESIZE$2, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetMaxFileSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(MAXFILESIZE$2, 0);
         return target;
      }
   }

   public boolean isSetMaxFileSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXFILESIZE$2) != 0;
      }
   }

   public void setMaxFileSize(long maxFileSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXFILESIZE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXFILESIZE$2);
         }

         target.setLongValue(maxFileSize);
      }
   }

   public void xsetMaxFileSize(XmlLong maxFileSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(MAXFILESIZE$2, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(MAXFILESIZE$2);
         }

         target.set(maxFileSize);
      }
   }

   public void unsetMaxFileSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXFILESIZE$2, 0);
      }
   }

   public long getMaxRequestSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXREQUESTSIZE$4, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetMaxRequestSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(MAXREQUESTSIZE$4, 0);
         return target;
      }
   }

   public boolean isSetMaxRequestSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXREQUESTSIZE$4) != 0;
      }
   }

   public void setMaxRequestSize(long maxRequestSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXREQUESTSIZE$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXREQUESTSIZE$4);
         }

         target.setLongValue(maxRequestSize);
      }
   }

   public void xsetMaxRequestSize(XmlLong maxRequestSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(MAXREQUESTSIZE$4, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(MAXREQUESTSIZE$4);
         }

         target.set(maxRequestSize);
      }
   }

   public void unsetMaxRequestSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXREQUESTSIZE$4, 0);
      }
   }

   public BigInteger getFileSizeThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FILESIZETHRESHOLD$6, 0);
         return target == null ? null : target.getBigIntegerValue();
      }
   }

   public XmlInteger xgetFileSizeThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(FILESIZETHRESHOLD$6, 0);
         return target;
      }
   }

   public boolean isSetFileSizeThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FILESIZETHRESHOLD$6) != 0;
      }
   }

   public void setFileSizeThreshold(BigInteger fileSizeThreshold) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FILESIZETHRESHOLD$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FILESIZETHRESHOLD$6);
         }

         target.setBigIntegerValue(fileSizeThreshold);
      }
   }

   public void xsetFileSizeThreshold(XmlInteger fileSizeThreshold) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(FILESIZETHRESHOLD$6, 0);
         if (target == null) {
            target = (XmlInteger)this.get_store().add_element_user(FILESIZETHRESHOLD$6);
         }

         target.set(fileSizeThreshold);
      }
   }

   public void unsetFileSizeThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FILESIZETHRESHOLD$6, 0);
      }
   }
}
