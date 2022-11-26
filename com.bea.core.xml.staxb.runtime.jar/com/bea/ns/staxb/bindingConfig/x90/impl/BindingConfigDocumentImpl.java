package com.bea.ns.staxb.bindingConfig.x90.impl;

import com.bea.ns.staxb.bindingConfig.x90.BindingConfigDocument;
import com.bea.ns.staxb.bindingConfig.x90.BindingTable;
import com.bea.ns.staxb.bindingConfig.x90.MappingTable;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class BindingConfigDocumentImpl extends XmlComplexContentImpl implements BindingConfigDocument {
   private static final long serialVersionUID = 1L;
   private static final QName BINDINGCONFIG$0 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "binding-config");

   public BindingConfigDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public BindingConfigDocument.BindingConfig getBindingConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BindingConfigDocument.BindingConfig target = null;
         target = (BindingConfigDocument.BindingConfig)this.get_store().find_element_user(BINDINGCONFIG$0, 0);
         return target == null ? null : target;
      }
   }

   public void setBindingConfig(BindingConfigDocument.BindingConfig bindingConfig) {
      this.generatedSetterHelperImpl(bindingConfig, BINDINGCONFIG$0, 0, (short)1);
   }

   public BindingConfigDocument.BindingConfig addNewBindingConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BindingConfigDocument.BindingConfig target = null;
         target = (BindingConfigDocument.BindingConfig)this.get_store().add_element_user(BINDINGCONFIG$0);
         return target;
      }
   }

   public static class BindingConfigImpl extends XmlComplexContentImpl implements BindingConfigDocument.BindingConfig {
      private static final long serialVersionUID = 1L;
      private static final QName BINDINGS$0 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "bindings");
      private static final QName XMLTOPOJO$2 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "xml-to-pojo");
      private static final QName XMLTOXMLOBJ$4 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "xml-to-xmlobj");
      private static final QName JAVATOXML$6 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "java-to-xml");
      private static final QName JAVATOELEMENT$8 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "java-to-element");

      public BindingConfigImpl(SchemaType sType) {
         super(sType);
      }

      public BindingTable getBindings() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            BindingTable target = null;
            target = (BindingTable)this.get_store().find_element_user(BINDINGS$0, 0);
            return target == null ? null : target;
         }
      }

      public void setBindings(BindingTable bindings) {
         this.generatedSetterHelperImpl(bindings, BINDINGS$0, 0, (short)1);
      }

      public BindingTable addNewBindings() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            BindingTable target = null;
            target = (BindingTable)this.get_store().add_element_user(BINDINGS$0);
            return target;
         }
      }

      public MappingTable getXmlToPojo() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            MappingTable target = null;
            target = (MappingTable)this.get_store().find_element_user(XMLTOPOJO$2, 0);
            return target == null ? null : target;
         }
      }

      public void setXmlToPojo(MappingTable xmlToPojo) {
         this.generatedSetterHelperImpl(xmlToPojo, XMLTOPOJO$2, 0, (short)1);
      }

      public MappingTable addNewXmlToPojo() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            MappingTable target = null;
            target = (MappingTable)this.get_store().add_element_user(XMLTOPOJO$2);
            return target;
         }
      }

      public MappingTable getXmlToXmlobj() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            MappingTable target = null;
            target = (MappingTable)this.get_store().find_element_user(XMLTOXMLOBJ$4, 0);
            return target == null ? null : target;
         }
      }

      public void setXmlToXmlobj(MappingTable xmlToXmlobj) {
         this.generatedSetterHelperImpl(xmlToXmlobj, XMLTOXMLOBJ$4, 0, (short)1);
      }

      public MappingTable addNewXmlToXmlobj() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            MappingTable target = null;
            target = (MappingTable)this.get_store().add_element_user(XMLTOXMLOBJ$4);
            return target;
         }
      }

      public MappingTable getJavaToXml() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            MappingTable target = null;
            target = (MappingTable)this.get_store().find_element_user(JAVATOXML$6, 0);
            return target == null ? null : target;
         }
      }

      public void setJavaToXml(MappingTable javaToXml) {
         this.generatedSetterHelperImpl(javaToXml, JAVATOXML$6, 0, (short)1);
      }

      public MappingTable addNewJavaToXml() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            MappingTable target = null;
            target = (MappingTable)this.get_store().add_element_user(JAVATOXML$6);
            return target;
         }
      }

      public MappingTable getJavaToElement() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            MappingTable target = null;
            target = (MappingTable)this.get_store().find_element_user(JAVATOELEMENT$8, 0);
            return target == null ? null : target;
         }
      }

      public void setJavaToElement(MappingTable javaToElement) {
         this.generatedSetterHelperImpl(javaToElement, JAVATOELEMENT$8, 0, (short)1);
      }

      public MappingTable addNewJavaToElement() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            MappingTable target = null;
            target = (MappingTable)this.get_store().add_element_user(JAVATOELEMENT$8);
            return target;
         }
      }
   }
}
