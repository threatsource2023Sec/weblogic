package com.bea.util.annogen.view;

import com.bea.util.annogen.override.AnnoOverrider;
import com.bea.util.annogen.view.internal.AnnoViewerParamsImpl;
import com.bea.util.annogen.view.internal.reflect.ReflectAnnoViewerImpl;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface ReflectAnnoViewer {
   Object getAnnotation(Class var1, Package var2);

   Object getAnnotation(Class var1, Class var2);

   Object getAnnotation(Class var1, Constructor var2);

   Object getAnnotation(Class var1, Field var2);

   Object getAnnotation(Class var1, Method var2);

   Object getAnnotation(Class var1, Method var2, int var3);

   Object getAnnotation(Class var1, Constructor var2, int var3);

   Object[] getAnnotations(Package var1);

   Object[] getAnnotations(Class var1);

   Object[] getAnnotations(Field var1);

   Object[] getAnnotations(Constructor var1);

   Object[] getAnnotations(Method var1);

   Object[] getAnnotations(Constructor var1, int var2);

   Object[] getAnnotations(Method var1, int var2);

   public static class Factory {
      public static ReflectAnnoViewer create(AnnoViewerParams params) {
         return new ReflectAnnoViewerImpl((AnnoViewerParamsImpl)params);
      }

      public static ReflectAnnoViewer create() {
         return new ReflectAnnoViewerImpl(new AnnoViewerParamsImpl());
      }

      public static ReflectAnnoViewer create(AnnoOverrider o) {
         AnnoViewerParamsImpl params = new AnnoViewerParamsImpl();
         params.addOverrider(o);
         return new ReflectAnnoViewerImpl(params);
      }
   }
}
