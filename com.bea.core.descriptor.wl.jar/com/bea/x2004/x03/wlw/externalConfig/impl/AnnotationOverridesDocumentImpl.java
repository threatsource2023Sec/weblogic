package com.bea.x2004.x03.wlw.externalConfig.impl;

import com.bea.x2004.x03.wlw.externalConfig.AnnotationOverridesBean;
import com.bea.x2004.x03.wlw.externalConfig.AnnotationOverridesDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class AnnotationOverridesDocumentImpl extends XmlComplexContentImpl implements AnnotationOverridesDocument {
   private static final long serialVersionUID = 1L;
   private static final QName ANNOTATIONOVERRIDES$0 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "annotation-overrides");

   public AnnotationOverridesDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public AnnotationOverridesBean getAnnotationOverrides() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotationOverridesBean target = null;
         target = (AnnotationOverridesBean)this.get_store().find_element_user(ANNOTATIONOVERRIDES$0, 0);
         return target == null ? null : target;
      }
   }

   public void setAnnotationOverrides(AnnotationOverridesBean annotationOverrides) {
      this.generatedSetterHelperImpl(annotationOverrides, ANNOTATIONOVERRIDES$0, 0, (short)1);
   }

   public AnnotationOverridesBean addNewAnnotationOverrides() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotationOverridesBean target = null;
         target = (AnnotationOverridesBean)this.get_store().add_element_user(ANNOTATIONOVERRIDES$0);
         return target;
      }
   }
}
