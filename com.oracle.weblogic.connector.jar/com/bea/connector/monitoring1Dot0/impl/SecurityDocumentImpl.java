package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.ResourceAdapterSecurityType;
import com.bea.connector.monitoring1Dot0.SecurityDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class SecurityDocumentImpl extends XmlComplexContentImpl implements SecurityDocument {
   private static final long serialVersionUID = 1L;
   private static final QName SECURITY$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "security");

   public SecurityDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public ResourceAdapterSecurityType getSecurity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceAdapterSecurityType target = null;
         target = (ResourceAdapterSecurityType)this.get_store().find_element_user(SECURITY$0, 0);
         return target == null ? null : target;
      }
   }

   public void setSecurity(ResourceAdapterSecurityType security) {
      this.generatedSetterHelperImpl(security, SECURITY$0, 0, (short)1);
   }

   public ResourceAdapterSecurityType addNewSecurity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceAdapterSecurityType target = null;
         target = (ResourceAdapterSecurityType)this.get_store().add_element_user(SECURITY$0);
         return target;
      }
   }
}
