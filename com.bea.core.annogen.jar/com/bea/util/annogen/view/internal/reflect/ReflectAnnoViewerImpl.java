package com.bea.util.annogen.view.internal.reflect;

import com.bea.util.annogen.override.ReflectElementIdPool;
import com.bea.util.annogen.view.ReflectAnnoViewer;
import com.bea.util.annogen.view.internal.AnnoViewerBase;
import com.bea.util.annogen.view.internal.AnnoViewerParamsImpl;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectAnnoViewerImpl extends AnnoViewerBase implements ReflectAnnoViewer {
   private ReflectElementIdPool mIdPool;

   public ReflectAnnoViewerImpl(AnnoViewerParamsImpl asp) {
      super(asp);
      this.mIdPool = ReflectElementIdPool.Factory.create(asp.getLogger());
   }

   public Object getAnnotation(Class annoClass, Package x) {
      return super.getAnnotation(annoClass, this.mIdPool.getIdFor(x));
   }

   public Object getAnnotation(Class annoClass, Class x) {
      return super.getAnnotation(annoClass, this.mIdPool.getIdFor(x));
   }

   public Object getAnnotation(Class annoClass, Constructor x) {
      return super.getAnnotation(annoClass, this.mIdPool.getIdFor(x));
   }

   public Object getAnnotation(Class annoClass, Field x) {
      return super.getAnnotation(annoClass, this.mIdPool.getIdFor(x));
   }

   public Object getAnnotation(Class annoClass, Method x) {
      return super.getAnnotation(annoClass, this.mIdPool.getIdFor(x));
   }

   public Object getAnnotation(Class annoClass, Constructor x, int pnum) {
      return super.getAnnotation(annoClass, this.mIdPool.getIdFor(x, pnum));
   }

   public Object getAnnotation(Class annoClass, Method x, int pnum) {
      return super.getAnnotation(annoClass, this.mIdPool.getIdFor(x, pnum));
   }

   public Object[] getAnnotations(Package x) {
      return super.getAnnotations(this.mIdPool.getIdFor(x));
   }

   public Object[] getAnnotations(Class x) {
      return super.getAnnotations(this.mIdPool.getIdFor(x));
   }

   public Object[] getAnnotations(Field x) {
      return super.getAnnotations(this.mIdPool.getIdFor(x));
   }

   public Object[] getAnnotations(Constructor x) {
      return super.getAnnotations(this.mIdPool.getIdFor(x));
   }

   public Object[] getAnnotations(Method x) {
      return super.getAnnotations(this.mIdPool.getIdFor(x));
   }

   public Object[] getAnnotations(Constructor x, int paramNum) {
      return super.getAnnotations(this.mIdPool.getIdFor(x, paramNum));
   }

   public Object[] getAnnotations(Method x, int paramNum) {
      return super.getAnnotations(this.mIdPool.getIdFor(x, paramNum));
   }
}
