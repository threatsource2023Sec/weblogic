package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.SecurityPermissionDocument;
import com.bea.connector.monitoring1Dot0.SecurityPermissionType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class SecurityPermissionDocumentImpl extends XmlComplexContentImpl implements SecurityPermissionDocument {
   private static final long serialVersionUID = 1L;
   private static final QName SECURITYPERMISSION$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "security-permission");

   public SecurityPermissionDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public SecurityPermissionType getSecurityPermission() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityPermissionType target = null;
         target = (SecurityPermissionType)this.get_store().find_element_user(SECURITYPERMISSION$0, 0);
         return target == null ? null : target;
      }
   }

   public void setSecurityPermission(SecurityPermissionType securityPermission) {
      this.generatedSetterHelperImpl(securityPermission, SECURITYPERMISSION$0, 0, (short)1);
   }

   public SecurityPermissionType addNewSecurityPermission() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityPermissionType target = null;
         target = (SecurityPermissionType)this.get_store().add_element_user(SECURITYPERMISSION$0);
         return target;
      }
   }
}
