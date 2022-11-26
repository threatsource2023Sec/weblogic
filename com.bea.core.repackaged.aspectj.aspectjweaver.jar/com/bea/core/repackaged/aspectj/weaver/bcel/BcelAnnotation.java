package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.AnnotationGen;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.ArrayElementValue;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.ElementValue;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.EnumElementValue;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.NameValuePair;
import com.bea.core.repackaged.aspectj.weaver.AbstractAnnotationAJ;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BcelAnnotation extends AbstractAnnotationAJ {
   private final AnnotationGen bcelAnnotation;

   public BcelAnnotation(AnnotationGen theBcelAnnotation, World world) {
      super(UnresolvedType.forSignature(theBcelAnnotation.getTypeSignature()).resolve(world));
      this.bcelAnnotation = theBcelAnnotation;
   }

   public BcelAnnotation(AnnotationGen theBcelAnnotation, ResolvedType resolvedAnnotationType) {
      super(resolvedAnnotationType);
      this.bcelAnnotation = theBcelAnnotation;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      List nvPairs = this.bcelAnnotation.getValues();
      sb.append("Anno[" + this.getTypeSignature() + " " + (this.isRuntimeVisible() ? "rVis" : "rInvis"));
      if (nvPairs.size() > 0) {
         sb.append(" ");
         int i = 0;

         for(Iterator i$ = nvPairs.iterator(); i$.hasNext(); ++i) {
            NameValuePair element = (NameValuePair)i$.next();
            if (i > 0) {
               sb.append(',');
            }

            sb.append(element.getNameString()).append("=").append(element.getValue().toString());
         }
      }

      sb.append("]");
      return sb.toString();
   }

   public Set getTargets() {
      if (!this.type.equals(UnresolvedType.AT_TARGET)) {
         return Collections.emptySet();
      } else {
         List values = this.bcelAnnotation.getValues();
         NameValuePair envp = (NameValuePair)values.get(0);
         ArrayElementValue aev = (ArrayElementValue)envp.getValue();
         ElementValue[] evs = aev.getElementValuesArray();
         Set targets = new HashSet();

         for(int i = 0; i < evs.length; ++i) {
            EnumElementValue ev = (EnumElementValue)evs[i];
            targets.add(ev.getEnumValueString());
         }

         return targets;
      }
   }

   public boolean hasNameValuePair(String name, String value) {
      return this.bcelAnnotation.hasNameValuePair(name, value);
   }

   public boolean hasNamedValue(String name) {
      return this.bcelAnnotation.hasNamedValue(name);
   }

   public String stringify() {
      StringBuffer sb = new StringBuffer();
      sb.append("@").append(this.type.getClassName());
      List values = this.bcelAnnotation.getValues();
      if (values != null && values.size() != 0) {
         sb.append("(");
         Iterator i$ = values.iterator();

         while(i$.hasNext()) {
            NameValuePair nvPair = (NameValuePair)i$.next();
            sb.append(nvPair.getNameString()).append("=").append(nvPair.getValue().stringifyValue());
         }

         sb.append(")");
      }

      return sb.toString();
   }

   public boolean isRuntimeVisible() {
      return this.bcelAnnotation.isRuntimeVisible();
   }

   public AnnotationGen getBcelAnnotation() {
      return this.bcelAnnotation;
   }

   public String getStringFormOfValue(String name) {
      List annotationValues = this.bcelAnnotation.getValues();
      if (annotationValues != null && annotationValues.size() != 0) {
         Iterator i$ = annotationValues.iterator();

         NameValuePair nvPair;
         do {
            if (!i$.hasNext()) {
               return null;
            }

            nvPair = (NameValuePair)i$.next();
         } while(!nvPair.getNameString().equals(name));

         return nvPair.getValue().stringifyValue();
      } else {
         return null;
      }
   }
}
