package com.oracle.xmlns.weblogic.weblogicInterception.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicInterception.InterceptionType;
import com.oracle.xmlns.weblogic.weblogicInterception.WeblogicInterceptionDocument;
import javax.xml.namespace.QName;

public class WeblogicInterceptionDocumentImpl extends XmlComplexContentImpl implements WeblogicInterceptionDocument {
   private static final long serialVersionUID = 1L;
   private static final QName WEBLOGICINTERCEPTION$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-interception", "weblogic-interception");

   public WeblogicInterceptionDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public InterceptionType getWeblogicInterception() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InterceptionType target = null;
         target = (InterceptionType)this.get_store().find_element_user(WEBLOGICINTERCEPTION$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWeblogicInterception(InterceptionType weblogicInterception) {
      this.generatedSetterHelperImpl(weblogicInterception, WEBLOGICINTERCEPTION$0, 0, (short)1);
   }

   public InterceptionType addNewWeblogicInterception() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InterceptionType target = null;
         target = (InterceptionType)this.get_store().add_element_user(WEBLOGICINTERCEPTION$0);
         return target;
      }
   }
}
