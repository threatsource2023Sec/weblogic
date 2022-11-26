package com.bea.util.annogen.override;

import com.bea.util.annogen.view.internal.AnnoViewerParamsImpl;
import com.bea.util.jam.provider.JamLogger;

public interface AnnoContext {
   Class getAnnobeanClassFor(Class var1) throws ClassNotFoundException;

   Class getJsr175ClassForAnnobeanClass(Class var1) throws ClassNotFoundException;

   JamLogger getLogger();

   ClassLoader getClassLoader();

   AnnoBean createAnnoBeanFor(Class var1);

   public static class Factory {
      public static AnnoContext newInstance() {
         return new AnnoViewerParamsImpl();
      }
   }
}
