package com.bea.core.repackaged.aspectj.weaver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class StandardAnnotation extends AbstractAnnotationAJ {
   private final boolean isRuntimeVisible;
   private List nvPairs = null;

   public StandardAnnotation(ResolvedType type, boolean isRuntimeVisible) {
      super(type);
      this.isRuntimeVisible = isRuntimeVisible;
   }

   public boolean isRuntimeVisible() {
      return this.isRuntimeVisible;
   }

   public String stringify() {
      StringBuffer sb = new StringBuffer();
      sb.append("@").append(this.type.getClassName());
      if (this.hasNameValuePairs()) {
         sb.append("(");
         Iterator i$ = this.nvPairs.iterator();

         while(i$.hasNext()) {
            AnnotationNameValuePair nvPair = (AnnotationNameValuePair)i$.next();
            sb.append(nvPair.stringify());
         }

         sb.append(")");
      }

      return sb.toString();
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("Anno[" + this.getTypeSignature() + " " + (this.isRuntimeVisible ? "rVis" : "rInvis"));
      if (this.nvPairs != null) {
         sb.append(" ");
         Iterator iter = this.nvPairs.iterator();

         while(iter.hasNext()) {
            AnnotationNameValuePair element = (AnnotationNameValuePair)iter.next();
            sb.append(element.toString());
            if (iter.hasNext()) {
               sb.append(",");
            }
         }
      }

      sb.append("]");
      return sb.toString();
   }

   public boolean hasNamedValue(String n) {
      if (this.nvPairs == null) {
         return false;
      } else {
         for(int i = 0; i < this.nvPairs.size(); ++i) {
            AnnotationNameValuePair pair = (AnnotationNameValuePair)this.nvPairs.get(i);
            if (pair.getName().equals(n)) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean hasNameValuePair(String n, String v) {
      if (this.nvPairs == null) {
         return false;
      } else {
         for(int i = 0; i < this.nvPairs.size(); ++i) {
            AnnotationNameValuePair pair = (AnnotationNameValuePair)this.nvPairs.get(i);
            if (pair.getName().equals(n) && pair.getValue().stringify().equals(v)) {
               return true;
            }
         }

         return false;
      }
   }

   public Set getTargets() {
      if (!this.type.equals(UnresolvedType.AT_TARGET)) {
         return Collections.emptySet();
      } else {
         AnnotationNameValuePair nvp = (AnnotationNameValuePair)this.nvPairs.get(0);
         ArrayAnnotationValue aav = (ArrayAnnotationValue)nvp.getValue();
         AnnotationValue[] avs = aav.getValues();
         Set targets = new HashSet();

         for(int i = 0; i < avs.length; ++i) {
            EnumAnnotationValue value = (EnumAnnotationValue)avs[i];
            targets.add(value.getValue());
         }

         return targets;
      }
   }

   public List getNameValuePairs() {
      return this.nvPairs;
   }

   public boolean hasNameValuePairs() {
      return this.nvPairs != null && this.nvPairs.size() != 0;
   }

   public void addNameValuePair(AnnotationNameValuePair pair) {
      if (this.nvPairs == null) {
         this.nvPairs = new ArrayList();
      }

      this.nvPairs.add(pair);
   }

   public String getStringFormOfValue(String name) {
      if (this.hasNameValuePairs()) {
         Iterator i$ = this.nvPairs.iterator();

         while(i$.hasNext()) {
            AnnotationNameValuePair nvPair = (AnnotationNameValuePair)i$.next();
            if (nvPair.getName().equals(name)) {
               return nvPair.getValue().stringify();
            }
         }
      }

      return null;
   }
}
