package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicConnector.TrueFalseType;
import com.oracle.xmlns.weblogic.weblogicConnector.UseAnonymousIdentityDocument;
import javax.xml.namespace.QName;

public class UseAnonymousIdentityDocumentImpl extends XmlComplexContentImpl implements UseAnonymousIdentityDocument {
   private static final long serialVersionUID = 1L;
   private static final QName USEANONYMOUSIDENTITY$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "use-anonymous-identity");

   public UseAnonymousIdentityDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public TrueFalseType getUseAnonymousIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(USEANONYMOUSIDENTITY$0, 0);
         return target == null ? null : target;
      }
   }

   public void setUseAnonymousIdentity(TrueFalseType useAnonymousIdentity) {
      this.generatedSetterHelperImpl(useAnonymousIdentity, USEANONYMOUSIDENTITY$0, 0, (short)1);
   }

   public TrueFalseType addNewUseAnonymousIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(USEANONYMOUSIDENTITY$0);
         return target;
      }
   }
}
