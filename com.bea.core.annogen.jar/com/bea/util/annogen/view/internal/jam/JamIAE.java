package com.bea.util.annogen.view.internal.jam;

import com.bea.util.annogen.override.AnnoBean;
import com.bea.util.annogen.override.AnnoBeanSet;
import com.bea.util.annogen.view.internal.IndigenousAnnoExtractor;
import com.bea.util.annogen.view.internal.NullIAE;
import com.bea.util.annogen.view.internal.javadoc.JavadocAnnogenTigerDelegate;
import com.bea.util.annogen.view.internal.javadoc.ParameterJavadocIAE;
import com.bea.util.annogen.view.internal.javadoc.ProgramElementJavadocIAE;
import com.bea.util.annogen.view.internal.reflect.ReflectAnnogenTigerDelegate;
import com.bea.util.annogen.view.internal.reflect.ReflectIAE;
import com.bea.util.jam.JAnnotatedElement;
import com.bea.util.jam.JAnnotation;
import com.bea.util.jam.JAnnotationValue;
import com.bea.util.jam.JInvokable;
import com.bea.util.jam.JParameter;
import com.bea.util.jam.JProperty;
import com.bea.util.jam.provider.JamLogger;
import com.sun.javadoc.ExecutableMemberDoc;
import com.sun.javadoc.ProgramElementDoc;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class JamIAE implements IndigenousAnnoExtractor {
   private JAnnotatedElement mElement;
   private JamLogger mLogger;

   public static IndigenousAnnoExtractor create(JAnnotatedElement element, JamLogger logger, ReflectAnnogenTigerDelegate rtiger, JavadocAnnogenTigerDelegate jtiger) {
      if (element == null) {
         throw new IllegalArgumentException("null element");
      } else {
         Object artifact = element.getArtifact();
         if (artifact == null) {
            return createForUnknownArtifact(element, logger);
         } else if (element instanceof JProperty) {
            throw new IllegalStateException("NYI");
         } else if (element instanceof JParameter) {
            JInvokable parent = (JInvokable)element.getParent();
            Object parentArt = parent.getArtifact();
            int num = getParameterNumber((JParameter)element);
            if (parentArt instanceof ExecutableMemberDoc) {
               return ParameterJavadocIAE.create((ExecutableMemberDoc)parentArt, num, jtiger);
            } else if (parentArt instanceof Method) {
               return ReflectIAE.create((Method)parentArt, num, rtiger);
            } else {
               return parentArt instanceof Constructor ? ReflectIAE.create((Constructor)parentArt, num, rtiger) : createForUnknownArtifact(element, logger);
            }
         } else if (artifact instanceof ProgramElementDoc) {
            return ProgramElementJavadocIAE.create((ProgramElementDoc)artifact, jtiger);
         } else if (artifact instanceof Class) {
            return ReflectIAE.create((Class)artifact, rtiger);
         } else if (artifact instanceof Package) {
            return ReflectIAE.create((Package)artifact, rtiger);
         } else if (artifact instanceof Method) {
            return ReflectIAE.create((Method)artifact, rtiger);
         } else if (artifact instanceof Constructor) {
            return ReflectIAE.create((Constructor)artifact, rtiger);
         } else {
            return artifact instanceof Field ? ReflectIAE.create((Field)artifact, rtiger) : createForUnknownArtifact(element, logger);
         }
      }
   }

   private static IndigenousAnnoExtractor createForUnknownArtifact(JAnnotatedElement element, JamLogger logger) {
      JAnnotation[] anns = element.getAnnotations();
      return (IndigenousAnnoExtractor)(anns != null && anns.length != 0 ? new JamIAE(element, logger) : NullIAE.getInstance());
   }

   private JamIAE(JAnnotatedElement element, JamLogger logger) {
      this.mElement = element;
      this.mLogger = logger;
   }

   public boolean extractIndigenousAnnotations(AnnoBeanSet out) {
      JAnnotation[] anns = this.mElement.getAnnotations();
      if (anns != null && anns.length != 0) {
         for(int i = 0; i < anns.length; ++i) {
            Class annoClass = this.getAnnotationTypeClass(anns[i]);
            if (annoClass != null) {
               AnnoBean bean = out.findOrCreateBeanFor(annoClass);
               if (bean != null) {
                  this.populate(anns[i], bean);
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private void populate(JAnnotation src, AnnoBean dest) {
      JAnnotationValue[] values = src.getValues();
      if (values != null && values.length != 0) {
         for(int i = 0; i < values.length; ++i) {
            dest.setValue(values[i].getName(), values[i].getValue());
         }

      }
   }

   private Class getAnnotationTypeClass(JAnnotation ann) {
      try {
         return Class.forName(ann.getQualifiedName());
      } catch (ClassNotFoundException var3) {
         return null;
      }
   }

   public static int getParameterNumber(JParameter param) {
      JParameter[] params = ((JInvokable)param.getParent()).getParameters();

      for(int i = 0; i < params.length; ++i) {
         if (param == params[i]) {
            return i;
         }
      }

      throw new IllegalStateException();
   }
}
