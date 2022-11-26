package com.bea.core.repackaged.aspectj.internal.lang.reflect;

import com.bea.core.repackaged.aspectj.lang.reflect.AjType;
import com.bea.core.repackaged.aspectj.lang.reflect.DeclareAnnotation;
import com.bea.core.repackaged.aspectj.lang.reflect.SignaturePattern;
import com.bea.core.repackaged.aspectj.lang.reflect.TypePattern;
import java.lang.annotation.Annotation;

public class DeclareAnnotationImpl implements DeclareAnnotation {
   private Annotation theAnnotation;
   private String annText;
   private AjType declaringType;
   private DeclareAnnotation.Kind kind;
   private TypePattern typePattern;
   private SignaturePattern signaturePattern;

   public DeclareAnnotationImpl(AjType declaring, String kindString, String pattern, Annotation ann, String annText) {
      this.declaringType = declaring;
      if (kindString.equals("at_type")) {
         this.kind = DeclareAnnotation.Kind.Type;
      } else if (kindString.equals("at_field")) {
         this.kind = DeclareAnnotation.Kind.Field;
      } else if (kindString.equals("at_method")) {
         this.kind = DeclareAnnotation.Kind.Method;
      } else {
         if (!kindString.equals("at_constructor")) {
            throw new IllegalStateException("Unknown declare annotation kind: " + kindString);
         }

         this.kind = DeclareAnnotation.Kind.Constructor;
      }

      if (this.kind == DeclareAnnotation.Kind.Type) {
         this.typePattern = new TypePatternImpl(pattern);
      } else {
         this.signaturePattern = new SignaturePatternImpl(pattern);
      }

      this.theAnnotation = ann;
      this.annText = annText;
   }

   public AjType getDeclaringType() {
      return this.declaringType;
   }

   public DeclareAnnotation.Kind getKind() {
      return this.kind;
   }

   public SignaturePattern getSignaturePattern() {
      return this.signaturePattern;
   }

   public TypePattern getTypePattern() {
      return this.typePattern;
   }

   public Annotation getAnnotation() {
      return this.theAnnotation;
   }

   public String getAnnotationAsText() {
      return this.annText;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("declare @");
      switch (this.getKind()) {
         case Type:
            sb.append("type : ");
            sb.append(this.getTypePattern().asString());
            break;
         case Method:
            sb.append("method : ");
            sb.append(this.getSignaturePattern().asString());
            break;
         case Field:
            sb.append("field : ");
            sb.append(this.getSignaturePattern().asString());
            break;
         case Constructor:
            sb.append("constructor : ");
            sb.append(this.getSignaturePattern().asString());
      }

      sb.append(" : ");
      sb.append(this.getAnnotationAsText());
      return sb.toString();
   }
}
