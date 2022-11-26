package com.bea.util.annogen.view;

import com.bea.util.annogen.override.AnnoOverrider;
import com.bea.util.annogen.view.internal.AnnoViewerParamsImpl;
import com.bea.util.annogen.view.internal.javadoc.JavadocAnnoViewerImpl;
import com.sun.javadoc.ExecutableMemberDoc;
import com.sun.javadoc.ProgramElementDoc;

public interface JavadocAnnoViewer {
   Object getAnnotation(Class var1, ProgramElementDoc var2);

   Object getAnnotation(Class var1, ExecutableMemberDoc var2, int var3);

   Object[] getAnnotations(ProgramElementDoc var1);

   Object[] getAnnotations(ExecutableMemberDoc var1, int var2);

   public static class Factory {
      public static JavadocAnnoViewer create(AnnoViewerParams params) {
         return new JavadocAnnoViewerImpl((AnnoViewerParamsImpl)params);
      }

      public static JavadocAnnoViewer create() {
         return new JavadocAnnoViewerImpl(new AnnoViewerParamsImpl());
      }

      public static JavadocAnnoViewer create(AnnoOverrider o) {
         AnnoViewerParamsImpl params = new AnnoViewerParamsImpl();
         params.addOverrider(o);
         return new JavadocAnnoViewerImpl(params);
      }
   }
}
