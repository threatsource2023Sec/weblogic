package com.bea.util.annogen.view.internal.jam;

import com.bea.util.annogen.override.ElementId;
import com.bea.util.annogen.override.JamElementIdPool;
import com.bea.util.annogen.view.JamAnnoViewer;
import com.bea.util.annogen.view.internal.AnnoViewerBase;
import com.bea.util.annogen.view.internal.AnnoViewerParamsImpl;
import com.bea.util.jam.JAnnotatedElement;
import com.bea.util.jam.JMethod;
import com.bea.util.jam.JProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JamAnnoViewerImpl extends AnnoViewerBase implements JamAnnoViewer {
   private JamElementIdPool mIdPool;

   public JamAnnoViewerImpl(AnnoViewerParamsImpl context) {
      super(context);
      this.mIdPool = JamElementIdPool.Factory.create(context.getLogger());
   }

   public Object getAnnotation(Class annotationType, JAnnotatedElement element) {
      return element instanceof JProperty ? this.getAnnotation(annotationType, (JProperty)element) : super.getAnnotation(annotationType, this.mIdPool.getIdFor(element));
   }

   public Object[] getAnnotations(JAnnotatedElement element) {
      return (Object[])(element instanceof JProperty ? this.getAnnotations((JProperty)element) : super.getAnnotations(this.mIdPool.getIdFor(element)));
   }

   private Object getAnnotation(Class annotationType, JProperty property) {
      JMethod g = property.getGetter();
      JMethod s = property.getSetter();
      ElementId gId = g == null ? null : this.mIdPool.getIdFor(property.getGetter());
      ElementId sId = s == null ? null : this.mIdPool.getIdFor(property.getSetter());
      Object gAnn = gId == null ? null : super.getAnnotation(annotationType, gId);
      Object sAnn = sId == null ? null : super.getAnnotation(annotationType, sId);
      if (gAnn != null) {
         if (sAnn != null) {
            this.mLogger.warning("Property '" + property.getQualifiedName() + "' has a " + annotationType.getName() + " annotation on both the getter and the setter.  Ignoring the one on the setter is being ignored.");
         }

         return gAnn;
      } else {
         return sAnn != null ? sAnn : null;
      }
   }

   private Object[] getAnnotations(JProperty property) {
      JMethod g = property.getGetter();
      JMethod s = property.getSetter();
      ElementId gId = g == null ? null : this.mIdPool.getIdFor(property.getGetter());
      ElementId sId = s == null ? null : this.mIdPool.getIdFor(property.getSetter());
      Object[] gAnn = gId == null ? null : super.getAnnotations(gId);
      Object[] sAnn = gId == null ? null : super.getAnnotations(sId);
      if (gAnn == null) {
         return sAnn;
      } else if (sAnn == null) {
         return gAnn;
      } else {
         List list = new ArrayList();
         list.addAll(Arrays.asList(gAnn));
         list.addAll(Arrays.asList(sAnn));
         Object[] out = new Object[list.size()];
         list.toArray(out);
         return out;
      }
   }
}
