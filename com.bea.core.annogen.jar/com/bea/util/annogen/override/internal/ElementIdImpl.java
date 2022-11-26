package com.bea.util.annogen.override.internal;

import com.bea.util.annogen.override.ElementId;
import com.bea.util.annogen.view.internal.IndigenousAnnoExtractor;
import java.io.StringWriter;

public class ElementIdImpl implements ElementId {
   private String mToString = null;
   private IndigenousAnnoExtractor mIAE;
   private String mName;
   private String mContainingClass = null;
   private int mType = -1;
   private int mParamNum = -1;
   private String[] mSignature = null;

   public static ElementIdImpl forPackage(IndigenousAnnoExtractor iae, String packageName) {
      ElementIdImpl id = new ElementIdImpl();
      id.setType(0);
      id.setIAE(iae);
      id.setName(packageName);
      return id;
   }

   public static ElementIdImpl forClass(IndigenousAnnoExtractor iae, String classname) {
      ElementIdImpl id = new ElementIdImpl();
      id.setType(1);
      id.setIAE(iae);
      id.setName(classname);
      return id;
   }

   public static ElementIdImpl forField(IndigenousAnnoExtractor iae, String contClass, String name) {
      ElementIdImpl id = new ElementIdImpl();
      id.setType(2);
      id.setIAE(iae);
      id.setName(name);
      id.setContainingClass(contClass);
      return id;
   }

   public static ElementIdImpl forConstructor(IndigenousAnnoExtractor iae, String containingClass, String[] signature) {
      ElementIdImpl id = new ElementIdImpl();
      id.setType(4);
      id.setIAE(iae);
      id.setName(containingClass.substring(containingClass.lastIndexOf(46) + 1));
      id.setContainingClass(containingClass);
      id.setSignature(signature);
      return id;
   }

   public static ElementIdImpl forMethod(IndigenousAnnoExtractor iae, String containingClass, String name, String[] signature) {
      ElementIdImpl id = new ElementIdImpl();
      id.setType(3);
      id.setIAE(iae);
      id.setName(name);
      id.setContainingClass(containingClass);
      id.setSignature(signature);
      return id;
   }

   public static ElementIdImpl forParameter(IndigenousAnnoExtractor iae, String containingClass, String methodName, String[] signature, int paramNum) {
      ElementIdImpl id = new ElementIdImpl();
      id.setType(5);
      id.setIAE(iae);
      id.setName(methodName);
      id.setContainingClass(containingClass);
      id.setSignature(signature);
      id.setParamNum(paramNum);
      return id;
   }

   protected ElementIdImpl() {
   }

   public IndigenousAnnoExtractor getIAE() {
      return this.mIAE;
   }

   public int getType() {
      return this.mType;
   }

   public String getName() {
      return this.mName;
   }

   public String getContainingClass() {
      return this.mContainingClass;
   }

   public String[] getSignature() {
      return this.mSignature;
   }

   public int getParameterNumber() {
      return this.mParamNum;
   }

   public int hashCode() {
      return this.toString().hashCode();
   }

   public boolean equals(Object o) {
      return o instanceof ElementId && this.toString().equals(o.toString());
   }

   public String toString() {
      if (this.mToString == null) {
         this.mToString = this.createToString();
      }

      return this.mToString;
   }

   protected void setIAE(IndigenousAnnoExtractor iae) {
      if (iae == null) {
         throw new IllegalArgumentException("null iae");
      } else {
         this.mIAE = iae;
      }
   }

   protected void setName(String name) {
      if (name == null) {
         throw new IllegalArgumentException("null name");
      } else {
         this.mName = name;
      }
   }

   protected void setContainingClass(String cc) {
      if (cc == null) {
         throw new IllegalArgumentException("null cc");
      } else {
         this.mContainingClass = cc;
      }
   }

   protected void setSignature(String[] sig) {
      this.mSignature = sig == null ? new String[0] : sig;
   }

   protected void setType(int type) {
      this.mType = type;
   }

   protected void setParamNum(int pnum) {
      if (pnum < 0) {
         throw new IllegalArgumentException("invalid pnum " + pnum);
      } else {
         this.mParamNum = pnum;
      }
   }

   private String createToString() {
      switch (this.getType()) {
         case 0:
         case 1:
            return this.getName();
         case 2:
         case 3:
         case 4:
         case 5:
            StringWriter out = new StringWriter();
            out.write(this.getContainingClass());
            out.write(46);
            out.write(this.getName());
            if (this.getType() == 2) {
               return out.toString();
            } else {
               out.write(40);
               String[] sig = this.getSignature();
               if (sig != null && sig.length > 0) {
                  int i = 0;

                  while(true) {
                     out.write(sig[i++]);
                     if (i == sig.length) {
                        break;
                     }

                     out.write(44);
                  }
               }

               out.write(41);
               if (this.getType() != 5) {
                  return out.toString();
               }

               out.write(91);
               out.write(this.getParameterNumber());
               out.write(93);
               return out.toString();
            }
         default:
            throw new IllegalStateException();
      }
   }
}
