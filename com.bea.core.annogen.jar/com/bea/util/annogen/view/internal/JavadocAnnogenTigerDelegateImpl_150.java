package com.bea.util.annogen.view.internal;

import com.bea.util.annogen.override.AnnoBean;
import com.bea.util.annogen.override.AnnoBeanSet;
import com.bea.util.annogen.view.internal.javadoc.JavadocAnnogenTigerDelegate;
import com.bea.util.jam.provider.JamLogger;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationTypeElementDoc;
import com.sun.javadoc.AnnotationValue;
import com.sun.javadoc.ExecutableMemberDoc;
import com.sun.javadoc.ProgramElementDoc;

public final class JavadocAnnogenTigerDelegateImpl_150 extends JavadocAnnogenTigerDelegate {
   private JamLogger mLogger;

   public void init(JamLogger logger) {
      this.mLogger = logger;
   }

   public boolean extractAnnotations(AnnoBeanSet out, ProgramElementDoc src) {
      AnnotationDesc[] anns = src.annotations();
      if (anns != null && anns.length != 0) {
         for(int i = 0; i < anns.length; ++i) {
            Class annType = this.getClassFor(anns[i]);
            if (annType != null) {
               AnnoBean proxy = out.findOrCreateBeanFor(annType);
               if (proxy != null) {
                  this.copyValues(anns[i], proxy);
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean extractAnnotations(AnnoBeanSet out, ExecutableMemberDoc method, int paramNum) {
      throw new IllegalStateException("parameter annos NYI");
   }

   private void copyValues(AnnotationDesc src, AnnoBean dest) {
      AnnotationDesc.ElementValuePair[] values = src.elementValues();
      if (values != null && values.length != 0) {
         for(int i = 0; i < values.length; ++i) {
            AnnotationTypeElementDoc ated = values[i].element();
            String name = ated.name();
            AnnotationValue avalue = values[i].value();
            if (avalue != null) {
               Object value = avalue.value();
               if (value != null) {
                  if (value instanceof AnnotationDesc) {
                     Class nestedClass = this.getClassFor((AnnotationDesc)value);
                     if (nestedClass != null) {
                        AnnoBean nested = dest.createNestableBean(nestedClass);
                        if (nested != null) {
                           this.copyValues((AnnotationDesc)value, nested);
                           dest.setValue(name, nested);
                        }
                     }
                  } else {
                     if (value.getClass().isArray()) {
                        throw new IllegalStateException("arrays NYI");
                     }

                     dest.setValue(name, value);
                  }
               }
            }
         }

      }
   }

   private Class getClassFor(AnnotationDesc javadocAnn) {
      try {
         return Class.forName(javadocAnn.annotationType().qualifiedTypeName());
      } catch (ClassNotFoundException var3) {
         this.mLogger.error((Throwable)var3);
         return null;
      } catch (ClassCastException var4) {
         this.mLogger.error((Throwable)var4);
         return null;
      }
   }
}
