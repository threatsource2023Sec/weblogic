package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.WebservicesDocument;
import org.jcp.xmlns.xml.ns.javaee.WebservicesType;

public class WebservicesDocumentImpl extends XmlComplexContentImpl implements WebservicesDocument {
   private static final long serialVersionUID = 1L;
   private static final QName WEBSERVICES$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "webservices");

   public WebservicesDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public WebservicesType getWebservices() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebservicesType target = null;
         target = (WebservicesType)this.get_store().find_element_user(WEBSERVICES$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWebservices(WebservicesType webservices) {
      this.generatedSetterHelperImpl(webservices, WEBSERVICES$0, 0, (short)1);
   }

   public WebservicesType addNewWebservices() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebservicesType target = null;
         target = (WebservicesType)this.get_store().add_element_user(WEBSERVICES$0);
         return target;
      }
   }
}
