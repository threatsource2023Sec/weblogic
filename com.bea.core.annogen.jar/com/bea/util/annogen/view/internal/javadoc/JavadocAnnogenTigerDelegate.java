package com.bea.util.annogen.view.internal.javadoc;

import com.bea.util.annogen.override.AnnoBeanSet;
import com.bea.util.jam.internal.TigerDelegateHelper;
import com.bea.util.jam.provider.JamLogger;
import com.sun.javadoc.ExecutableMemberDoc;
import com.sun.javadoc.ProgramElementDoc;

public abstract class JavadocAnnogenTigerDelegate {
   private static final String IMPL = "com.bea.util.annogen.view.internal.JavadocAnnogenTigerDelegateImpl_150";

   public static JavadocAnnogenTigerDelegate create(JamLogger logger) {
      if (!TigerDelegateHelper.isTigerJavadocAvailable(logger)) {
         return null;
      } else {
         try {
            JavadocAnnogenTigerDelegate out = (JavadocAnnogenTigerDelegate)Class.forName("com.bea.util.annogen.view.internal.JavadocAnnogenTigerDelegateImpl_150").newInstance();
            out.init(logger);
            return out;
         } catch (ClassNotFoundException var2) {
            TigerDelegateHelper.issue14BuildWarning(var2, logger);
         } catch (IllegalAccessException var3) {
            logger.error((Throwable)var3);
         } catch (InstantiationException var4) {
            logger.error((Throwable)var4);
         }

         return null;
      }
   }

   public abstract void init(JamLogger var1);

   public abstract boolean extractAnnotations(AnnoBeanSet var1, ProgramElementDoc var2);

   public abstract boolean extractAnnotations(AnnoBeanSet var1, ExecutableMemberDoc var2, int var3);
}
