package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.PermissionsDocument;
import org.jcp.xmlns.xml.ns.javaee.PermissionsType;

public class PermissionsDocumentImpl extends XmlComplexContentImpl implements PermissionsDocument {
   private static final long serialVersionUID = 1L;
   private static final QName PERMISSIONS$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "permissions");

   public PermissionsDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public PermissionsType getPermissions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PermissionsType target = null;
         target = (PermissionsType)this.get_store().find_element_user(PERMISSIONS$0, 0);
         return target == null ? null : target;
      }
   }

   public void setPermissions(PermissionsType permissions) {
      this.generatedSetterHelperImpl(permissions, PERMISSIONS$0, 0, (short)1);
   }

   public PermissionsType addNewPermissions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PermissionsType target = null;
         target = (PermissionsType)this.get_store().add_element_user(PERMISSIONS$0);
         return target;
      }
   }
}
