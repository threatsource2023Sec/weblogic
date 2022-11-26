package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.AnnotationGen;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.ElementValue;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.EnumElementValue;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.NameValuePair;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.SimpleElementValue;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Instruction;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionFactory;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionList;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Type;
import com.bea.core.repackaged.aspectj.weaver.AnnotationAJ;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import java.util.Iterator;
import java.util.List;

class AnnotationAccessFieldVar extends BcelVar {
   private AnnotationAccessVar annoAccessor;
   private ResolvedType annoFieldOfInterest;
   private String name;
   private int elementValueType;

   public AnnotationAccessFieldVar(AnnotationAccessVar aav, ResolvedType annoFieldOfInterest, String name) {
      super(annoFieldOfInterest, 0);
      this.annoAccessor = aav;
      this.name = name;
      String sig = annoFieldOfInterest.getSignature();
      if (sig.length() == 1) {
         switch (sig.charAt(0)) {
            case 'I':
               this.elementValueType = 73;
               break;
            default:
               throw new IllegalStateException(sig);
         }
      } else if (sig.equals("Ljava/lang/String;")) {
         this.elementValueType = 115;
      } else {
         if (!annoFieldOfInterest.isEnum()) {
            throw new IllegalStateException(sig);
         }

         this.elementValueType = 101;
      }

      this.annoFieldOfInterest = annoFieldOfInterest;
   }

   public void appendLoadAndConvert(InstructionList il, InstructionFactory fact, ResolvedType toType) {
      if (this.annoAccessor.getKind() == Shadow.MethodExecution) {
         String annotationOfInterestSignature = this.annoAccessor.getType().getSignature();
         Member holder = this.annoAccessor.getMember();
         AnnotationAJ[] annos = holder.getAnnotations();
         AnnotationAJ[] arr$ = annos;
         int len$ = annos.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            AnnotationAJ anno = arr$[i$];
            AnnotationGen annotation = ((BcelAnnotation)anno).getBcelAnnotation();
            boolean foundValueInAnnotationUsage = false;
            if (annotation.getTypeSignature().equals(annotationOfInterestSignature)) {
               ResolvedMember[] annotationFields = toType.getWorld().resolve(UnresolvedType.forSignature(annotation.getTypeSignature())).getDeclaredMethods();
               int countOfType = 0;
               ResolvedMember[] arr$ = annotationFields;
               int len$ = annotationFields.length;

               int len$;
               for(len$ = 0; len$ < len$; ++len$) {
                  ResolvedMember annotationField = arr$[len$];
                  if (annotationField.getType().equals(this.annoFieldOfInterest)) {
                     ++countOfType;
                  }
               }

               List nvps = annotation.getValues();
               Iterator i$ = nvps.iterator();

               String svalue;
               label89:
               while(true) {
                  NameValuePair nvp;
                  do {
                     if (!i$.hasNext()) {
                        break label89;
                     }

                     nvp = (NameValuePair)i$.next();
                  } while(countOfType > 1 && !nvp.getNameString().equals(this.name));

                  ElementValue o = nvp.getValue();
                  if (o.getElementValueType() == this.elementValueType) {
                     if (o instanceof EnumElementValue) {
                        EnumElementValue v = (EnumElementValue)o;
                        svalue = v.getEnumTypeString();
                        ResolvedType rt = toType.getWorld().resolve(UnresolvedType.forSignature(svalue));
                        if (rt.equals(toType)) {
                           il.append((Instruction)fact.createGetStatic(rt.getName(), v.getEnumValueString(), Type.getType(rt.getSignature())));
                           foundValueInAnnotationUsage = true;
                        }
                     } else if (o instanceof SimpleElementValue) {
                        SimpleElementValue v = (SimpleElementValue)o;
                        switch (v.getElementValueType()) {
                           case 73:
                              il.append(fact.createConstant(v.getValueInt()));
                              foundValueInAnnotationUsage = true;
                              break;
                           case 115:
                              il.append(fact.createConstant(v.getValueString()));
                              foundValueInAnnotationUsage = true;
                              break;
                           default:
                              throw new IllegalStateException("NYI: Unsupported annotation value binding for " + o);
                        }
                     }

                     if (foundValueInAnnotationUsage) {
                        break;
                     }
                  }
               }

               if (!foundValueInAnnotationUsage) {
                  ResolvedMember[] arr$ = annotationFields;
                  len$ = annotationFields.length;

                  for(int i$ = 0; i$ < len$; ++i$) {
                     ResolvedMember annotationField = arr$[i$];
                     if ((countOfType <= 1 || annotationField.getName().equals(this.name)) && annotationField.getType().getSignature().equals(this.annoFieldOfInterest.getSignature())) {
                        if (annotationField.getType().getSignature().equals("I")) {
                           int ivalue = Integer.parseInt(annotationField.getAnnotationDefaultValue());
                           il.append(fact.createConstant(ivalue));
                           foundValueInAnnotationUsage = true;
                        } else if (annotationField.getType().getSignature().equals("Ljava/lang/String;")) {
                           svalue = annotationField.getAnnotationDefaultValue();
                           il.append(fact.createConstant(svalue));
                           foundValueInAnnotationUsage = true;
                        } else {
                           svalue = annotationField.getAnnotationDefaultValue();
                           String typename = svalue.substring(0, svalue.lastIndexOf(59) + 1);
                           String field = svalue.substring(svalue.lastIndexOf(59) + 1);
                           ResolvedType rt = toType.getWorld().resolve(UnresolvedType.forSignature(typename));
                           il.append((Instruction)fact.createGetStatic(rt.getName(), field, Type.getType(rt.getSignature())));
                           foundValueInAnnotationUsage = true;
                        }
                        break;
                     }
                  }
               }
            }

            if (foundValueInAnnotationUsage) {
               break;
            }
         }

      }
   }

   public void insertLoad(InstructionList il, InstructionFactory fact) {
      if (this.annoAccessor.getKind() == Shadow.MethodExecution) {
         this.appendLoadAndConvert(il, fact, this.annoFieldOfInterest);
      }
   }

   public String toString() {
      return super.toString();
   }
}
