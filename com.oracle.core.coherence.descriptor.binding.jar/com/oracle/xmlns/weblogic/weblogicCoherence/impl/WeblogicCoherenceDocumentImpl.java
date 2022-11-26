package com.oracle.xmlns.weblogic.weblogicCoherence.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicCoherence.WeblogicCoherenceDocument;
import com.oracle.xmlns.weblogic.weblogicCoherence.WeblogicCoherenceType;
import javax.xml.namespace.QName;

public class WeblogicCoherenceDocumentImpl extends XmlComplexContentImpl implements WeblogicCoherenceDocument {
   private static final long serialVersionUID = 1L;
   private static final QName WEBLOGICCOHERENCE$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "weblogic-coherence");

   public WeblogicCoherenceDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public WeblogicCoherenceType getWeblogicCoherence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicCoherenceType target = null;
         target = (WeblogicCoherenceType)this.get_store().find_element_user(WEBLOGICCOHERENCE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWeblogicCoherence(WeblogicCoherenceType weblogicCoherence) {
      this.generatedSetterHelperImpl(weblogicCoherence, WEBLOGICCOHERENCE$0, 0, (short)1);
   }

   public WeblogicCoherenceType addNewWeblogicCoherence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicCoherenceType target = null;
         target = (WeblogicCoherenceType)this.get_store().add_element_user(WEBLOGICCOHERENCE$0);
         return target;
      }
   }
}
