package com.bea.util.jam.internal.javadoc;

import com.bea.util.jam.internal.TigerDelegateHelper;
import com.bea.util.jam.internal.elements.ElementContext;
import com.bea.util.jam.mutable.MAnnotatedElement;
import com.bea.util.jam.mutable.MClass;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.ExecutableMemberDoc;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.ProgramElementDoc;

public abstract class JavadocTigerDelegate {
   private static final String JAVADOC_DELEGATE_IMPL = "com.bea.util.jam.internal.javadoc.JavadocTigerDelegateImpl_150";

   public static JavadocTigerDelegate create(ElementContext ctx) {
      if (!TigerDelegateHelper.isTigerJavadocAvailable(ctx.getLogger())) {
         return null;
      } else {
         try {
            JavadocTigerDelegate out = (JavadocTigerDelegate)Class.forName("com.bea.util.jam.internal.javadoc.JavadocTigerDelegateImpl_150").newInstance();
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

   public abstract void init(ElementContext var1);

   public abstract void populateAnnotationTypeIfNecessary(ClassDoc var1, MClass var2, JavadocClassBuilder var3);

   /** @deprecated */
   @Deprecated
   public abstract void extractAnnotations(MAnnotatedElement var1, ProgramElementDoc var2);

   /** @deprecated */
   @Deprecated
   public abstract void extractAnnotations(MAnnotatedElement var1, ExecutableMemberDoc var2, Parameter var3);
}
