package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.WeblogicEjbJarDocument;
import com.oracle.xmlns.weblogic.weblogicEjbJar.WeblogicEjbJarType;
import javax.xml.namespace.QName;

public class WeblogicEjbJarDocumentImpl extends XmlComplexContentImpl implements WeblogicEjbJarDocument {
   private static final long serialVersionUID = 1L;
   private static final QName WEBLOGICEJBJAR$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "weblogic-ejb-jar");

   public WeblogicEjbJarDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public WeblogicEjbJarType getWeblogicEjbJar() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicEjbJarType target = null;
         target = (WeblogicEjbJarType)this.get_store().find_element_user(WEBLOGICEJBJAR$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWeblogicEjbJar(WeblogicEjbJarType weblogicEjbJar) {
      this.generatedSetterHelperImpl(weblogicEjbJar, WEBLOGICEJBJAR$0, 0, (short)1);
   }

   public WeblogicEjbJarType addNewWeblogicEjbJar() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicEjbJarType target = null;
         target = (WeblogicEjbJarType)this.get_store().add_element_user(WEBLOGICEJBJAR$0);
         return target;
      }
   }
}
