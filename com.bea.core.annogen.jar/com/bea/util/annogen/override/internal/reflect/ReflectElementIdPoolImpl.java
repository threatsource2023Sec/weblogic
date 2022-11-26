package com.bea.util.annogen.override.internal.reflect;

import com.bea.util.annogen.override.ElementId;
import com.bea.util.annogen.override.ReflectElementIdPool;
import com.bea.util.annogen.override.internal.ElementIdImpl;
import com.bea.util.annogen.view.internal.reflect.ReflectAnnogenTigerDelegate;
import com.bea.util.annogen.view.internal.reflect.ReflectIAE;
import com.bea.util.jam.provider.JamLogger;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectElementIdPoolImpl implements ReflectElementIdPool {
   private ReflectAnnogenTigerDelegate mRTiger;

   public ReflectElementIdPoolImpl(JamLogger logger) {
      this.mRTiger = ReflectAnnogenTigerDelegate.create(logger);
   }

   public ElementId getIdFor(Class clazz) {
      return ElementIdImpl.forClass(ReflectIAE.create(clazz, this.mRTiger), clazz.getName());
   }

   public ElementId getIdFor(Package pakkage) {
      return ElementIdImpl.forPackage(ReflectIAE.create(pakkage, this.mRTiger), pakkage.getName());
   }

   public ElementId getIdFor(Field field) {
      return ElementIdImpl.forField(ReflectIAE.create(field, this.mRTiger), field.getDeclaringClass().getName(), field.getName());
   }

   public ElementId getIdFor(Constructor ctor) {
      return ElementIdImpl.forConstructor(ReflectIAE.create(ctor, this.mRTiger), ctor.getDeclaringClass().getName(), this.getTypeNames(ctor.getParameterTypes()));
   }

   public ElementId getIdFor(Method method) {
      return ElementIdImpl.forMethod(ReflectIAE.create(method, this.mRTiger), method.getDeclaringClass().getName(), method.getName(), this.getTypeNames(method.getParameterTypes()));
   }

   public ElementId getIdFor(Method method, int paramNum) {
      return ElementIdImpl.forParameter(ReflectIAE.create(method, this.mRTiger), method.getDeclaringClass().getName(), method.getName(), this.getTypeNames(method.getParameterTypes()), paramNum);
   }

   public ElementId getIdFor(Constructor ctor, int paramNum) {
      return ElementIdImpl.forParameter(ReflectIAE.create(ctor, this.mRTiger), ctor.getDeclaringClass().getName(), ctor.getName(), this.getTypeNames(ctor.getParameterTypes()), paramNum);
   }

   private String[] getTypeNames(Class[] classes) {
      if (classes == null) {
         return null;
      } else {
         String[] out = new String[classes.length];

         for(int i = 0; i < out.length; ++i) {
            out[i] = classes[i].getName();
         }

         return out;
      }
   }
}
