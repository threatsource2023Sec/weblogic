package com.bea.core.repackaged.aspectj.weaver;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public abstract class AbstractAnnotationAJ implements AnnotationAJ {
   protected final ResolvedType type;
   private Set supportedTargets = null;

   public AbstractAnnotationAJ(ResolvedType type) {
      this.type = type;
   }

   public final ResolvedType getType() {
      return this.type;
   }

   public final String getTypeSignature() {
      return this.type.getSignature();
   }

   public final String getTypeName() {
      return this.type.getName();
   }

   public final boolean allowedOnAnnotationType() {
      this.ensureAtTargetInitialized();
      return this.supportedTargets.isEmpty() ? true : this.supportedTargets.contains("ANNOTATION_TYPE");
   }

   public final boolean allowedOnField() {
      this.ensureAtTargetInitialized();
      return this.supportedTargets.isEmpty() ? true : this.supportedTargets.contains("FIELD");
   }

   public final boolean allowedOnRegularType() {
      this.ensureAtTargetInitialized();
      return this.supportedTargets.isEmpty() ? true : this.supportedTargets.contains("TYPE");
   }

   public final void ensureAtTargetInitialized() {
      if (this.supportedTargets == null) {
         AnnotationAJ atTargetAnnotation = this.retrieveAnnotationOnAnnotation(UnresolvedType.AT_TARGET);
         if (atTargetAnnotation == null) {
            this.supportedTargets = Collections.emptySet();
         } else {
            this.supportedTargets = atTargetAnnotation.getTargets();
         }
      }

   }

   public final String getValidTargets() {
      StringBuffer sb = new StringBuffer();
      sb.append("{");
      Iterator iter = this.supportedTargets.iterator();

      while(iter.hasNext()) {
         String evalue = (String)iter.next();
         sb.append(evalue);
         if (iter.hasNext()) {
            sb.append(",");
         }
      }

      sb.append("}");
      return sb.toString();
   }

   public final boolean specifiesTarget() {
      this.ensureAtTargetInitialized();
      return !this.supportedTargets.isEmpty();
   }

   private final AnnotationAJ retrieveAnnotationOnAnnotation(UnresolvedType requiredAnnotationSignature) {
      AnnotationAJ[] annos = this.type.getAnnotations();

      for(int i = 0; i < annos.length; ++i) {
         AnnotationAJ a = annos[i];
         if (a.getTypeSignature().equals(requiredAnnotationSignature.getSignature())) {
            return annos[i];
         }
      }

      return null;
   }

   public abstract boolean isRuntimeVisible();

   public abstract Set getTargets();

   public abstract boolean hasNameValuePair(String var1, String var2);

   public abstract boolean hasNamedValue(String var1);

   public abstract String stringify();
}
