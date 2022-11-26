package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.DescriptionType;
import org.jcp.xmlns.xml.ns.javaee.DisplayNameType;
import org.jcp.xmlns.xml.ns.javaee.IconType;
import org.jcp.xmlns.xml.ns.javaee.PathType;
import org.jcp.xmlns.xml.ns.javaee.PortComponentType;
import org.jcp.xmlns.xml.ns.javaee.String;
import org.jcp.xmlns.xml.ns.javaee.WebserviceDescriptionType;

public class WebserviceDescriptionTypeImpl extends XmlComplexContentImpl implements WebserviceDescriptionType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "description");
   private static final QName DISPLAYNAME$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "display-name");
   private static final QName ICON$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "icon");
   private static final QName WEBSERVICEDESCRIPTIONNAME$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "webservice-description-name");
   private static final QName WSDLFILE$8 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "wsdl-file");
   private static final QName JAXRPCMAPPINGFILE$10 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "jaxrpc-mapping-file");
   private static final QName PORTCOMPONENT$12 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "port-component");
   private static final QName ID$14 = new QName("", "id");

   public WebserviceDescriptionTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DescriptionType getDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().find_element_user(DESCRIPTION$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESCRIPTION$0) != 0;
      }
   }

   public void setDescription(DescriptionType description) {
      this.generatedSetterHelperImpl(description, DESCRIPTION$0, 0, (short)1);
   }

   public DescriptionType addNewDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().add_element_user(DESCRIPTION$0);
         return target;
      }
   }

   public void unsetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$0, 0);
      }
   }

   public DisplayNameType getDisplayName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().find_element_user(DISPLAYNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDisplayName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISPLAYNAME$2) != 0;
      }
   }

   public void setDisplayName(DisplayNameType displayName) {
      this.generatedSetterHelperImpl(displayName, DISPLAYNAME$2, 0, (short)1);
   }

   public DisplayNameType addNewDisplayName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().add_element_user(DISPLAYNAME$2);
         return target;
      }
   }

   public void unsetDisplayName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISPLAYNAME$2, 0);
      }
   }

   public IconType getIcon() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().find_element_user(ICON$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetIcon() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ICON$4) != 0;
      }
   }

   public void setIcon(IconType icon) {
      this.generatedSetterHelperImpl(icon, ICON$4, 0, (short)1);
   }

   public IconType addNewIcon() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().add_element_user(ICON$4);
         return target;
      }
   }

   public void unsetIcon() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ICON$4, 0);
      }
   }

   public String getWebserviceDescriptionName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(WEBSERVICEDESCRIPTIONNAME$6, 0);
         return target == null ? null : target;
      }
   }

   public void setWebserviceDescriptionName(String webserviceDescriptionName) {
      this.generatedSetterHelperImpl(webserviceDescriptionName, WEBSERVICEDESCRIPTIONNAME$6, 0, (short)1);
   }

   public String addNewWebserviceDescriptionName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(WEBSERVICEDESCRIPTIONNAME$6);
         return target;
      }
   }

   public PathType getWsdlFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().find_element_user(WSDLFILE$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetWsdlFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WSDLFILE$8) != 0;
      }
   }

   public void setWsdlFile(PathType wsdlFile) {
      this.generatedSetterHelperImpl(wsdlFile, WSDLFILE$8, 0, (short)1);
   }

   public PathType addNewWsdlFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().add_element_user(WSDLFILE$8);
         return target;
      }
   }

   public void unsetWsdlFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WSDLFILE$8, 0);
      }
   }

   public PathType getJaxrpcMappingFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().find_element_user(JAXRPCMAPPINGFILE$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetJaxrpcMappingFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JAXRPCMAPPINGFILE$10) != 0;
      }
   }

   public void setJaxrpcMappingFile(PathType jaxrpcMappingFile) {
      this.generatedSetterHelperImpl(jaxrpcMappingFile, JAXRPCMAPPINGFILE$10, 0, (short)1);
   }

   public PathType addNewJaxrpcMappingFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().add_element_user(JAXRPCMAPPINGFILE$10);
         return target;
      }
   }

   public void unsetJaxrpcMappingFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JAXRPCMAPPINGFILE$10, 0);
      }
   }

   public PortComponentType[] getPortComponentArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PORTCOMPONENT$12, targetList);
         PortComponentType[] result = new PortComponentType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PortComponentType getPortComponentArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PortComponentType target = null;
         target = (PortComponentType)this.get_store().find_element_user(PORTCOMPONENT$12, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPortComponentArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PORTCOMPONENT$12);
      }
   }

   public void setPortComponentArray(PortComponentType[] portComponentArray) {
      this.check_orphaned();
      this.arraySetterHelper(portComponentArray, PORTCOMPONENT$12);
   }

   public void setPortComponentArray(int i, PortComponentType portComponent) {
      this.generatedSetterHelperImpl(portComponent, PORTCOMPONENT$12, i, (short)2);
   }

   public PortComponentType insertNewPortComponent(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PortComponentType target = null;
         target = (PortComponentType)this.get_store().insert_element_user(PORTCOMPONENT$12, i);
         return target;
      }
   }

   public PortComponentType addNewPortComponent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PortComponentType target = null;
         target = (PortComponentType)this.get_store().add_element_user(PORTCOMPONENT$12);
         return target;
      }
   }

   public void removePortComponent(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PORTCOMPONENT$12, i);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$14);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$14);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$14) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$14);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$14);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$14);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$14);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$14);
      }
   }
}
