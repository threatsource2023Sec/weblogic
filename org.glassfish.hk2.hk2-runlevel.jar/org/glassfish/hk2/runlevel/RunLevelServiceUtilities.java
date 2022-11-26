package org.glassfish.hk2.runlevel;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import org.glassfish.hk2.api.AnnotationLiteral;
import org.glassfish.hk2.api.DuplicateServiceException;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.runlevel.internal.AsyncRunLevelContext;
import org.glassfish.hk2.runlevel.internal.RunLevelControllerImpl;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

public class RunLevelServiceUtilities {
   public static void enableRunLevelService(ServiceLocator locator) {
      if (locator.getService(RunLevelContext.class, new Annotation[0]) == null) {
         try {
            ServiceLocatorUtilities.addClasses(locator, true, new Class[]{RunLevelContext.class, AsyncRunLevelContext.class, RunLevelControllerImpl.class});
         } catch (MultiException var2) {
            if (!isDupException(var2)) {
               throw var2;
            }
         }

      }
   }

   private static boolean isDupException(MultiException me) {
      boolean atLeastOne = false;
      Iterator var2 = me.getErrors().iterator();

      Throwable error;
      do {
         if (!var2.hasNext()) {
            return atLeastOne;
         }

         error = (Throwable)var2.next();
         atLeastOne = true;
      } while(error instanceof DuplicateServiceException);

      return false;
   }

   public static RunLevel getRunLevelAnnotation(int value) {
      return getRunLevelAnnotation(value, 1);
   }

   public static RunLevel getRunLevelAnnotation(int value, int mode) {
      return new RunLevelImpl(value, mode);
   }

   private static class RunLevelImpl extends AnnotationLiteral implements RunLevel {
      private static final long serialVersionUID = -359213687920354669L;
      private final int value;
      private final int mode;

      private RunLevelImpl(int value, int mode) {
         this.value = value;
         this.mode = mode;
      }

      public int value() {
         return this.value;
      }

      public int mode() {
         return this.mode;
      }

      // $FF: synthetic method
      RunLevelImpl(int x0, int x1, Object x2) {
         this(x0, x1);
      }
   }
}
