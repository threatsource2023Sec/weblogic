package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicApplication.DefaultResourcePrincipalType;
import com.sun.java.xml.ns.javaee.String;
import javax.xml.namespace.QName;

public class DefaultResourcePrincipalTypeImpl extends XmlComplexContentImpl implements DefaultResourcePrincipalType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "name");
   private static final QName PASSWORD$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "password");

   public DefaultResourcePrincipalTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(NAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setName(String name) {
      this.generatedSetterHelperImpl(name, NAME$0, 0, (short)1);
   }

   public String addNewName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(NAME$0);
         return target;
      }
   }

   public String getPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(PASSWORD$2, 0);
         return target == null ? null : target;
      }
   }

   public void setPassword(String password) {
      this.generatedSetterHelperImpl(password, PASSWORD$2, 0, (short)1);
   }

   public String addNewPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(PASSWORD$2);
         return target;
      }
   }
}
