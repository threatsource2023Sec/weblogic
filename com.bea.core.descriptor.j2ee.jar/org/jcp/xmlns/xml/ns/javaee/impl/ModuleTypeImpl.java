package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.ModuleType;
import org.jcp.xmlns.xml.ns.javaee.PathType;
import org.jcp.xmlns.xml.ns.javaee.WebType;

public class ModuleTypeImpl extends XmlComplexContentImpl implements ModuleType {
   private static final long serialVersionUID = 1L;
   private static final QName CONNECTOR$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "connector");
   private static final QName EJB$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "ejb");
   private static final QName JAVA$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "java");
   private static final QName WEB$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "web");
   private static final QName ALTDD$8 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "alt-dd");
   private static final QName ID$10 = new QName("", "id");

   public ModuleTypeImpl(SchemaType sType) {
      super(sType);
   }

   public PathType getConnector() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().find_element_user(CONNECTOR$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetConnector() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTOR$0) != 0;
      }
   }

   public void setConnector(PathType connector) {
      this.generatedSetterHelperImpl(connector, CONNECTOR$0, 0, (short)1);
   }

   public PathType addNewConnector() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().add_element_user(CONNECTOR$0);
         return target;
      }
   }

   public void unsetConnector() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTOR$0, 0);
      }
   }

   public PathType getEjb() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().find_element_user(EJB$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEjb() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EJB$2) != 0;
      }
   }

   public void setEjb(PathType ejb) {
      this.generatedSetterHelperImpl(ejb, EJB$2, 0, (short)1);
   }

   public PathType addNewEjb() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().add_element_user(EJB$2);
         return target;
      }
   }

   public void unsetEjb() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJB$2, 0);
      }
   }

   public PathType getJava() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().find_element_user(JAVA$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetJava() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JAVA$4) != 0;
      }
   }

   public void setJava(PathType java) {
      this.generatedSetterHelperImpl(java, JAVA$4, 0, (short)1);
   }

   public PathType addNewJava() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().add_element_user(JAVA$4);
         return target;
      }
   }

   public void unsetJava() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JAVA$4, 0);
      }
   }

   public WebType getWeb() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebType target = null;
         target = (WebType)this.get_store().find_element_user(WEB$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetWeb() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WEB$6) != 0;
      }
   }

   public void setWeb(WebType web) {
      this.generatedSetterHelperImpl(web, WEB$6, 0, (short)1);
   }

   public WebType addNewWeb() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebType target = null;
         target = (WebType)this.get_store().add_element_user(WEB$6);
         return target;
      }
   }

   public void unsetWeb() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WEB$6, 0);
      }
   }

   public PathType getAltDd() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().find_element_user(ALTDD$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAltDd() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ALTDD$8) != 0;
      }
   }

   public void setAltDd(PathType altDd) {
      this.generatedSetterHelperImpl(altDd, ALTDD$8, 0, (short)1);
   }

   public PathType addNewAltDd() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().add_element_user(ALTDD$8);
         return target;
      }
   }

   public void unsetAltDd() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ALTDD$8, 0);
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
