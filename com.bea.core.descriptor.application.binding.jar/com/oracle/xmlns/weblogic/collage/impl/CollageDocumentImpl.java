package com.oracle.xmlns.weblogic.collage.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.collage.CollageDocument;
import com.oracle.xmlns.weblogic.collage.CollageType;
import javax.xml.namespace.QName;

public class CollageDocumentImpl extends XmlComplexContentImpl implements CollageDocument {
   private static final long serialVersionUID = 1L;
   private static final QName COLLAGE$0 = new QName("http://xmlns.oracle.com/weblogic/collage", "collage");

   public CollageDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public CollageType getCollage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CollageType target = null;
         target = (CollageType)this.get_store().find_element_user(COLLAGE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setCollage(CollageType collage) {
      this.generatedSetterHelperImpl(collage, COLLAGE$0, 0, (short)1);
   }

   public CollageType addNewCollage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CollageType target = null;
         target = (CollageType)this.get_store().add_element_user(COLLAGE$0);
         return target;
      }
   }
}
