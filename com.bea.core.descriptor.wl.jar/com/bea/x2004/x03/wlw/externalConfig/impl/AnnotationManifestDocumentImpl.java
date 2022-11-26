package com.bea.x2004.x03.wlw.externalConfig.impl;

import com.bea.x2004.x03.wlw.externalConfig.AnnotationManifestBean;
import com.bea.x2004.x03.wlw.externalConfig.AnnotationManifestDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class AnnotationManifestDocumentImpl extends XmlComplexContentImpl implements AnnotationManifestDocument {
   private static final long serialVersionUID = 1L;
   private static final QName ANNOTATIONMANIFEST$0 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "annotation-manifest");

   public AnnotationManifestDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public AnnotationManifestBean getAnnotationManifest() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotationManifestBean target = null;
         target = (AnnotationManifestBean)this.get_store().find_element_user(ANNOTATIONMANIFEST$0, 0);
         return target == null ? null : target;
      }
   }

   public void setAnnotationManifest(AnnotationManifestBean annotationManifest) {
      this.generatedSetterHelperImpl(annotationManifest, ANNOTATIONMANIFEST$0, 0, (short)1);
   }

   public AnnotationManifestBean addNewAnnotationManifest() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotationManifestBean target = null;
         target = (AnnotationManifestBean)this.get_store().add_element_user(ANNOTATIONMANIFEST$0);
         return target;
      }
   }
}
