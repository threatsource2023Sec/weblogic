package com.bea.core.repackaged.aspectj.lang.reflect;

import com.bea.core.repackaged.aspectj.internal.lang.reflect.AjTypeImpl;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public class AjTypeSystem {
   private static Map ajTypes = Collections.synchronizedMap(new WeakHashMap());

   public static AjType getAjType(Class fromClass) {
      WeakReference weakRefToAjType = (WeakReference)ajTypes.get(fromClass);
      AjTypeImpl theAjType;
      if (weakRefToAjType != null) {
         AjType theAjType = (AjType)weakRefToAjType.get();
         if (theAjType != null) {
            return theAjType;
         } else {
            theAjType = new AjTypeImpl(fromClass);
            ajTypes.put(fromClass, new WeakReference(theAjType));
            return theAjType;
         }
      } else {
         theAjType = new AjTypeImpl(fromClass);
         ajTypes.put(fromClass, new WeakReference(theAjType));
         return theAjType;
      }
   }
}
