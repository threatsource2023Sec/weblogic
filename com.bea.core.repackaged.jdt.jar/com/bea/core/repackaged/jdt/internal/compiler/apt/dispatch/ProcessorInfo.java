package com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch;

import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.processing.Processor;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

public class ProcessorInfo {
   final Processor _processor;
   final Set _supportedOptions;
   final SourceVersion _supportedSourceVersion;
   private final Pattern _supportedAnnotationTypesPattern;
   private final boolean _supportsStar;
   private boolean _hasBeenCalled;

   public ProcessorInfo(Processor p) {
      this._processor = p;
      this._hasBeenCalled = false;
      this._supportedSourceVersion = p.getSupportedSourceVersion();
      this._supportedOptions = p.getSupportedOptions();
      Set supportedAnnotationTypes = p.getSupportedAnnotationTypes();
      boolean supportsStar = false;
      if (supportedAnnotationTypes != null && !supportedAnnotationTypes.isEmpty()) {
         StringBuilder regex = new StringBuilder();
         Iterator iName = supportedAnnotationTypes.iterator();

         while(true) {
            String name = (String)iName.next();
            supportsStar |= "*".equals(name);
            String escapedName1 = name.replace(".", "\\.");
            String escapedName2 = escapedName1.replace("*", ".*");
            regex.append(escapedName2);
            if (!iName.hasNext()) {
               this._supportedAnnotationTypesPattern = Pattern.compile(regex.toString());
               break;
            }

            regex.append('|');
         }
      } else {
         this._supportedAnnotationTypesPattern = null;
      }

      this._supportsStar = supportsStar;
   }

   public boolean computeSupportedAnnotations(Set annotations, Set result) {
      if (annotations != null && !annotations.isEmpty() && this._supportedAnnotationTypesPattern != null) {
         Iterator var4 = annotations.iterator();

         while(var4.hasNext()) {
            TypeElement annotation = (TypeElement)var4.next();
            Matcher matcher = this._supportedAnnotationTypesPattern.matcher(annotation.getQualifiedName().toString());
            if (matcher.matches()) {
               result.add(annotation);
            }
         }
      }

      boolean call = this._hasBeenCalled || this._supportsStar || !result.isEmpty();
      this._hasBeenCalled |= call;
      return call;
   }

   public boolean supportsStar() {
      return this._supportsStar;
   }

   public void reset() {
      this._hasBeenCalled = false;
   }

   public int hashCode() {
      return this._processor.getClass().hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         ProcessorInfo other = (ProcessorInfo)obj;
         return this._processor.getClass().equals(other._processor.getClass());
      }
   }

   public String toString() {
      return this._processor.getClass().getName();
   }

   public String getSupportedAnnotationTypesAsString() {
      StringBuilder sb = new StringBuilder();
      sb.append('[');
      Iterator iAnnots = this._processor.getSupportedAnnotationTypes().iterator();
      boolean hasNext = iAnnots.hasNext();

      while(hasNext) {
         sb.append((String)iAnnots.next());
         hasNext = iAnnots.hasNext();
         if (hasNext) {
            sb.append(',');
         }
      }

      sb.append(']');
      return sb.toString();
   }
}
