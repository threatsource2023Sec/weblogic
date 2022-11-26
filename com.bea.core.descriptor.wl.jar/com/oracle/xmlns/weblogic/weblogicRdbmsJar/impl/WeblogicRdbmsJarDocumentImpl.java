package com.oracle.xmlns.weblogic.weblogicRdbmsJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.WeblogicRdbmsJarDocument;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.WeblogicRdbmsJarType;
import javax.xml.namespace.QName;

public class WeblogicRdbmsJarDocumentImpl extends XmlComplexContentImpl implements WeblogicRdbmsJarDocument {
   private static final long serialVersionUID = 1L;
   private static final QName WEBLOGICRDBMSJAR$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "weblogic-rdbms-jar");

   public WeblogicRdbmsJarDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public WeblogicRdbmsJarType getWeblogicRdbmsJar() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicRdbmsJarType target = null;
         target = (WeblogicRdbmsJarType)this.get_store().find_element_user(WEBLOGICRDBMSJAR$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWeblogicRdbmsJar(WeblogicRdbmsJarType weblogicRdbmsJar) {
      this.generatedSetterHelperImpl(weblogicRdbmsJar, WEBLOGICRDBMSJAR$0, 0, (short)1);
   }

   public WeblogicRdbmsJarType addNewWeblogicRdbmsJar() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicRdbmsJarType target = null;
         target = (WeblogicRdbmsJarType)this.get_store().add_element_user(WEBLOGICRDBMSJAR$0);
         return target;
      }
   }
}
