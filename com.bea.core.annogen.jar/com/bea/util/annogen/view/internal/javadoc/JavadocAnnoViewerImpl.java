package com.bea.util.annogen.view.internal.javadoc;

import com.bea.util.annogen.override.JavadocElementIdPool;
import com.bea.util.annogen.view.JavadocAnnoViewer;
import com.bea.util.annogen.view.internal.AnnoViewerBase;
import com.bea.util.annogen.view.internal.AnnoViewerParamsImpl;
import com.sun.javadoc.ExecutableMemberDoc;
import com.sun.javadoc.ProgramElementDoc;

public class JavadocAnnoViewerImpl extends AnnoViewerBase implements JavadocAnnoViewer {
   private JavadocElementIdPool mIdPool;

   public JavadocAnnoViewerImpl(AnnoViewerParamsImpl asp) {
      super(asp);
      this.mIdPool = JavadocElementIdPool.Factory.create(asp.getLogger());
   }

   public Object getAnnotation(Class annoClass, ProgramElementDoc ped) {
      return super.getAnnotation(annoClass, this.mIdPool.getIdFor(ped));
   }

   public Object[] getAnnotations(ProgramElementDoc ped) {
      return super.getAnnotations(this.mIdPool.getIdFor(ped));
   }

   public Object getAnnotation(Class annotationType, ExecutableMemberDoc ctorOrMethod, int paramNum) {
      return super.getAnnotation(annotationType, this.mIdPool.getIdFor(ctorOrMethod, paramNum));
   }

   public Object[] getAnnotations(ExecutableMemberDoc ctorOrMethod, int paramNum) {
      return super.getAnnotations(this.mIdPool.getIdFor(ctorOrMethod, paramNum));
   }
}
