package com.oracle.pitchfork.intercept.internal;

import com.oracle.pitchfork.intercept.InterceptorExclusion;
import com.oracle.pitchfork.intercept.InterceptorExclusionManager;
import com.oracle.pitchfork.interfaces.intercept.InterceptorMetadataI;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class InterceptorExclusionManagerImpl implements InterceptorExclusionManager {
   private final Map exclusions = new HashMap();

   public Map getExclusions() {
      return this.exclusions;
   }

   public void setExcludeClassInterceptors(AnnotatedElement methodOrClass) {
      InterceptorExclusion exclude = (InterceptorExclusion)this.exclusions.get(methodOrClass);
      if (exclude == null) {
         exclude = new InterceptorExclusion();
      }

      exclude.setExcludeClassInterceptors(true);
      this.exclusions.put(methodOrClass, exclude);
   }

   public void setExcludeDefaultInterceptors(AnnotatedElement methodOrClass) {
      InterceptorExclusion exclude = (InterceptorExclusion)this.exclusions.get(methodOrClass);
      if (exclude == null) {
         exclude = new InterceptorExclusion();
      }

      exclude.setExcludeDefaultInterceptors(true);
      this.exclusions.put(methodOrClass, exclude);
   }

   public boolean isExcluded(Method method, Class targetClass, InterceptorMetadataI im) {
      InterceptorExclusion exclusion = (InterceptorExclusion)this.exclusions.get(method);
      if (exclusion == null) {
         exclusion = (InterceptorExclusion)this.exclusions.get(targetClass);
      }

      return exclusion != null && (this.isClassExcluded(exclusion, im) || this.isDefaultExcluded(exclusion, im));
   }

   private boolean isClassExcluded(InterceptorExclusion exclusion, InterceptorMetadataI im) {
      return exclusion.isExcludeClassInterceptors() && im.isClassInterceptor();
   }

   private boolean isDefaultExcluded(InterceptorExclusion exclusion, InterceptorMetadataI im) {
      return exclusion.isExcludeDefaultInterceptors() && im.isDefaultInterceptor();
   }
}
