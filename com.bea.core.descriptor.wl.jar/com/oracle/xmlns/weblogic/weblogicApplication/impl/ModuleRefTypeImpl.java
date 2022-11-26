package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicApplication.ModuleRefType;
import javax.xml.namespace.QName;

public class ModuleRefTypeImpl extends XmlComplexContentImpl implements ModuleRefType {
   private static final long serialVersionUID = 1L;
   private static final QName MODULEURI$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "module-uri");

   public ModuleRefTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getModuleUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MODULEURI$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetModuleUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MODULEURI$0, 0);
         return target;
      }
   }

   public void setModuleUri(String moduleUri) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MODULEURI$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MODULEURI$0);
         }

         target.setStringValue(moduleUri);
      }
   }

   public void xsetModuleUri(XmlString moduleUri) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MODULEURI$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MODULEURI$0);
         }

         target.set(moduleUri);
      }
   }
}
