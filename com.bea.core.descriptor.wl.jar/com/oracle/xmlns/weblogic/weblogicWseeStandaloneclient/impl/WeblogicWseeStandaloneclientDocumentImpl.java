package com.oracle.xmlns.weblogic.weblogicWseeStandaloneclient.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicWseeStandaloneclient.WeblogicWseeStandaloneclientDocument;
import com.oracle.xmlns.weblogic.weblogicWseeStandaloneclient.WeblogicWseeStandaloneclientType;
import javax.xml.namespace.QName;

public class WeblogicWseeStandaloneclientDocumentImpl extends XmlComplexContentImpl implements WeblogicWseeStandaloneclientDocument {
   private static final long serialVersionUID = 1L;
   private static final QName WEBLOGICWSEESTANDALONECLIENT$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-wsee-standaloneclient", "weblogic-wsee-standaloneclient");

   public WeblogicWseeStandaloneclientDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public WeblogicWseeStandaloneclientType getWeblogicWseeStandaloneclient() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicWseeStandaloneclientType target = null;
         target = (WeblogicWseeStandaloneclientType)this.get_store().find_element_user(WEBLOGICWSEESTANDALONECLIENT$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWeblogicWseeStandaloneclient(WeblogicWseeStandaloneclientType weblogicWseeStandaloneclient) {
      this.generatedSetterHelperImpl(weblogicWseeStandaloneclient, WEBLOGICWSEESTANDALONECLIENT$0, 0, (short)1);
   }

   public WeblogicWseeStandaloneclientType addNewWeblogicWseeStandaloneclient() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicWseeStandaloneclientType target = null;
         target = (WeblogicWseeStandaloneclientType)this.get_store().add_element_user(WEBLOGICWSEESTANDALONECLIENT$0);
         return target;
      }
   }
}
