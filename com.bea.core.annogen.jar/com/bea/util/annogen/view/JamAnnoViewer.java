package com.bea.util.annogen.view;

import com.bea.util.annogen.override.AnnoOverrider;
import com.bea.util.annogen.view.internal.AnnoViewerParamsImpl;
import com.bea.util.annogen.view.internal.jam.JamAnnoViewerImpl;
import com.bea.util.jam.JAnnotatedElement;

public interface JamAnnoViewer {
   Object getAnnotation(Class var1, JAnnotatedElement var2);

   Object[] getAnnotations(JAnnotatedElement var1);

   public static class Factory {
      public static JamAnnoViewer create(AnnoViewerParams params) {
         return new JamAnnoViewerImpl((AnnoViewerParamsImpl)params);
      }

      public static JamAnnoViewer create() {
         return new JamAnnoViewerImpl(new AnnoViewerParamsImpl());
      }

      public static JamAnnoViewer create(AnnoOverrider o) {
         AnnoViewerParamsImpl params = new AnnoViewerParamsImpl();
         params.addOverrider(o);
         return new JamAnnoViewerImpl(params);
      }
   }
}
