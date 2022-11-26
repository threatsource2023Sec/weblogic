package com.bea.ns.staxb.bindingConfig.x90.impl;

import com.bea.ns.staxb.bindingConfig.x90.AsXmlType;
import com.bea.ns.staxb.bindingConfig.x90.SimpleType;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class SimpleTypeImpl extends BindingTypeImpl implements SimpleType {
   private static final long serialVersionUID = 1L;
   private static final QName ASXML$0 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "as-xml");

   public SimpleTypeImpl(SchemaType sType) {
      super(sType);
   }

   public AsXmlType getAsXml() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AsXmlType target = null;
         target = (AsXmlType)this.get_store().find_element_user(ASXML$0, 0);
         return target == null ? null : target;
      }
   }

   public void setAsXml(AsXmlType asXml) {
      this.generatedSetterHelperImpl(asXml, ASXML$0, 0, (short)1);
   }

   public AsXmlType addNewAsXml() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AsXmlType target = null;
         target = (AsXmlType)this.get_store().add_element_user(ASXML$0);
         return target;
      }
   }
}
