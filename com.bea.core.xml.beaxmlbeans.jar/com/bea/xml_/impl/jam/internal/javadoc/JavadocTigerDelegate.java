package com.bea.xml_.impl.jam.internal.javadoc;

import com.bea.xml_.impl.jam.internal.TigerDelegate;
import com.bea.xml_.impl.jam.internal.elements.ElementContext;
import com.bea.xml_.impl.jam.mutable.MAnnotatedElement;
import com.bea.xml_.impl.jam.mutable.MClass;
import com.bea.xml_.impl.jam.provider.JamLogger;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.ExecutableMemberDoc;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.ProgramElementDoc;

public abstract class JavadocTigerDelegate extends TigerDelegate {
   private static final String JAVADOC_DELEGATE_IMPL = "com.bea.xml_.impl.jam.internal.javadoc.JavadocTigerDelegateImpl_150";
   public static final String ANNOTATION_DEFAULTS_DISABLED_PROPERTY = "ANNOTATION_DEFAULTS_DISABLED_PROPERTY";

   public static JavadocTigerDelegate create(JamLogger logger) {
      if (!isTigerJavadocAvailable(logger)) {
         return null;
      } else {
         try {
            JavadocTigerDelegate out = (JavadocTigerDelegate)Class.forName("com.bea.xml_.impl.jam.internal.javadoc.JavadocTigerDelegateImpl_150").newInstance();
            out.init(logger);
            return out;
         } catch (ClassNotFoundException var2) {
            issue14BuildWarning(var2, logger);
         } catch (IllegalAccessException var3) {
            logger.error((Throwable)var3);
         } catch (InstantiationException var4) {
            logger.error((Throwable)var4);
         }

         return null;
      }
   }

   /** @deprecated */
   public static JavadocTigerDelegate create(ElementContext ctx) {
      if (!isTigerJavadocAvailable(ctx.getLogger())) {
         return null;
      } else {
         try {
            JavadocTigerDelegate out = (JavadocTigerDelegate)Class.forName("com.bea.xml_.impl.jam.internal.javadoc.JavadocTigerDelegateImpl_150").newInstance();
            out.init(ctx);
            return out;
         } catch (ClassNotFoundException var2) {
            ctx.getLogger().error((Throwable)var2);
         } catch (IllegalAccessException var3) {
            ctx.getLogger().error((Throwable)var3);
         } catch (InstantiationException var4) {
            ctx.getLogger().error((Throwable)var4);
         }

         return null;
      }
   }

   public abstract boolean isEnum(ClassDoc var1);

   public abstract void init(JamLogger var1);

   public abstract void populateAnnotationTypeIfNecessary(ClassDoc var1, MClass var2, JavadocClassBuilder var3);

   /** @deprecated */
   public abstract void extractAnnotations(MAnnotatedElement var1, ProgramElementDoc var2);

   /** @deprecated */
   public abstract void extractAnnotations(MAnnotatedElement var1, ExecutableMemberDoc var2, Parameter var3);
}
