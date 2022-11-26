package com.bea.staxb.buildtime.internal.mbean;

import com.bea.staxb.buildtime.Java2Schema;
import com.bea.staxb.buildtime.internal.tylar.TylarWriter;
import com.bea.util.annogen.view.JamAnnoViewer;
import com.bea.util.annogen.view.JamAnnoViewer.Factory;
import com.bea.util.jam.JClass;

public class MBeanJava2Schema extends Java2Schema {
   private JavadocTagProcessor mTagProcessor = null;

   public MBeanJava2Schema() {
      super.setGenerateDocumentation(false);
   }

   public void addClassToBind(JClass c) {
      super.addClassToBind(c, false);
      if (this.mTagProcessor == null) {
         this.mTagProcessor = new JavadocTagProcessor();
      }

      this.mTagProcessor.translateTagsFor(c);
   }

   protected void internalBind(TylarWriter writer) {
      this.setOrderPropertiesBySource(true);
      if (this.mTagProcessor != null) {
         JamAnnoViewer jav = Factory.create(this.mTagProcessor.getOverrides());
         super.setAnnoViewer(jav);
      }

      super.internalBind(writer);
   }

   public void setAnnoViewer(JamAnnoViewer jav) {
      throw new IllegalStateException("not supported.");
   }
}
