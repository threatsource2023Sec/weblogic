package com.bea.util.annogen.view;

import com.bea.util.annogen.override.AnnoOverrider;
import com.bea.util.annogen.view.internal.AnnoViewerParamsImpl;

public interface AnnoViewerParams {
   void addOverrider(AnnoOverrider var1);

   void setClassLoader(ClassLoader var1);

   void setVerbose(Class var1);

   public static class Factory {
      public static AnnoViewerParams create() {
         return new AnnoViewerParamsImpl();
      }
   }
}
