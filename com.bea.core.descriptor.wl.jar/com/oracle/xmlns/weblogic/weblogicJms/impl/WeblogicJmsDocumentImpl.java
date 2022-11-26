package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicJms.JmsType;
import com.oracle.xmlns.weblogic.weblogicJms.WeblogicJmsDocument;
import javax.xml.namespace.QName;

public class WeblogicJmsDocumentImpl extends XmlComplexContentImpl implements WeblogicJmsDocument {
   private static final long serialVersionUID = 1L;
   private static final QName WEBLOGICJMS$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "weblogic-jms");

   public WeblogicJmsDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public JmsType getWeblogicJms() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsType target = null;
         target = (JmsType)this.get_store().find_element_user(WEBLOGICJMS$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWeblogicJms(JmsType weblogicJms) {
      this.generatedSetterHelperImpl(weblogicJms, WEBLOGICJMS$0, 0, (short)1);
   }

   public JmsType addNewWeblogicJms() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsType target = null;
         target = (JmsType)this.get_store().add_element_user(WEBLOGICJMS$0);
         return target;
      }
   }
}
