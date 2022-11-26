package com.oracle.xmlns.weblogic.multiVersionState.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.multiVersionState.MultiVersionStateDocument;
import com.oracle.xmlns.weblogic.multiVersionState.MultiVersionStateType;
import javax.xml.namespace.QName;

public class MultiVersionStateDocumentImpl extends XmlComplexContentImpl implements MultiVersionStateDocument {
   private static final long serialVersionUID = 1L;
   private static final QName MULTIVERSIONSTATE$0 = new QName("http://xmlns.oracle.com/weblogic/multi-version-state", "multi-version-state");

   public MultiVersionStateDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public MultiVersionStateType getMultiVersionState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MultiVersionStateType target = null;
         target = (MultiVersionStateType)this.get_store().find_element_user(MULTIVERSIONSTATE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setMultiVersionState(MultiVersionStateType multiVersionState) {
      this.generatedSetterHelperImpl(multiVersionState, MULTIVERSIONSTATE$0, 0, (short)1);
   }

   public MultiVersionStateType addNewMultiVersionState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MultiVersionStateType target = null;
         target = (MultiVersionStateType)this.get_store().add_element_user(MULTIVERSIONSTATE$0);
         return target;
      }
   }
}
