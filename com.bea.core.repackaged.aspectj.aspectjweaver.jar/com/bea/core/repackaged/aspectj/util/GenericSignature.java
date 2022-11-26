package com.bea.core.repackaged.aspectj.util;

public class GenericSignature {
   public static class TypeArgument {
      public boolean isWildcard = false;
      public boolean isPlus = false;
      public boolean isMinus = false;
      public FieldTypeSignature signature;

      public TypeArgument() {
         this.isWildcard = true;
      }

      public TypeArgument(boolean plus, boolean minus, FieldTypeSignature aSig) {
         this.isPlus = plus;
         this.isMinus = minus;
         this.signature = aSig;
      }

      public String toString() {
         if (this.isWildcard) {
            return "*";
         } else {
            StringBuffer sb = new StringBuffer();
            if (this.isPlus) {
               sb.append("+");
            }

            if (this.isMinus) {
               sb.append("-");
            }

            sb.append(this.signature.toString());
            return sb.toString();
         }
      }
   }

   public static class SimpleClassTypeSignature {
      public String identifier;
      public TypeArgument[] typeArguments;

      public SimpleClassTypeSignature(String identifier) {
         this.identifier = identifier;
         this.typeArguments = new TypeArgument[0];
      }

      public SimpleClassTypeSignature(String identifier, TypeArgument[] args) {
         this.identifier = identifier;
         this.typeArguments = args;
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append(this.identifier);
         if (this.typeArguments.length > 0) {
            sb.append("<");

            for(int i = 0; i < this.typeArguments.length; ++i) {
               sb.append(this.typeArguments[i].toString());
            }

            sb.append(">");
         }

         return sb.toString();
      }
   }

   public static class ArrayTypeSignature extends FieldTypeSignature {
      public TypeSignature typeSig;

      public ArrayTypeSignature(TypeSignature aTypeSig) {
         this.typeSig = aTypeSig;
      }

      public boolean isArrayTypeSignature() {
         return true;
      }

      public String toString() {
         return "[" + this.typeSig.toString();
      }
   }

   public static class TypeVariableSignature extends FieldTypeSignature {
      public String typeVariableName;

      public TypeVariableSignature(String typeVarToken) {
         this.typeVariableName = typeVarToken.substring(1);
      }

      public boolean isTypeVariableSignature() {
         return true;
      }

      public String toString() {
         return "T" + this.typeVariableName + ";";
      }
   }

   public static class ClassTypeSignature extends FieldTypeSignature {
      public static final ClassTypeSignature[] NONE = new ClassTypeSignature[0];
      public String classSignature;
      public SimpleClassTypeSignature outerType;
      public SimpleClassTypeSignature[] nestedTypes;

      public ClassTypeSignature(String sig, String identifier) {
         this.classSignature = sig;
         this.outerType = new SimpleClassTypeSignature(identifier);
         this.nestedTypes = new SimpleClassTypeSignature[0];
      }

      public ClassTypeSignature(String sig, SimpleClassTypeSignature outer, SimpleClassTypeSignature[] inners) {
         this.classSignature = sig;
         this.outerType = outer;
         this.nestedTypes = inners;
      }

      public boolean isClassTypeSignature() {
         return true;
      }

      public String toString() {
         return this.classSignature;
      }
   }

   public abstract static class FieldTypeSignature extends TypeSignature {
      public boolean isClassTypeSignature() {
         return false;
      }

      public boolean isTypeVariableSignature() {
         return false;
      }

      public boolean isArrayTypeSignature() {
         return false;
      }
   }

   public static class BaseTypeSignature extends TypeSignature {
      private final String sig;

      public BaseTypeSignature(String aPrimitiveType) {
         this.sig = aPrimitiveType;
      }

      public boolean isBaseType() {
         return true;
      }

      public String toString() {
         return this.sig;
      }
   }

   public abstract static class TypeSignature {
      public boolean isBaseType() {
         return false;
      }
   }

   public static class FormalTypeParameter {
      public static final FormalTypeParameter[] NONE = new FormalTypeParameter[0];
      public String identifier;
      public FieldTypeSignature classBound;
      public FieldTypeSignature[] interfaceBounds;

      public String toString() {
         StringBuffer ret = new StringBuffer();
         ret.append("T");
         ret.append(this.identifier);
         ret.append(":");
         ret.append(this.classBound.toString());

         for(int i = 0; i < this.interfaceBounds.length; ++i) {
            ret.append(":");
            ret.append(this.interfaceBounds[i].toString());
         }

         return ret.toString();
      }
   }

   public static class MethodTypeSignature {
      public FormalTypeParameter[] formalTypeParameters = new FormalTypeParameter[0];
      public TypeSignature[] parameters = new TypeSignature[0];
      public TypeSignature returnType;
      public FieldTypeSignature[] throwsSignatures = new FieldTypeSignature[0];

      public MethodTypeSignature(FormalTypeParameter[] aFormalParameterList, TypeSignature[] aParameterList, TypeSignature aReturnType, FieldTypeSignature[] aThrowsSignatureList) {
         this.formalTypeParameters = aFormalParameterList;
         this.parameters = aParameterList;
         this.returnType = aReturnType;
         this.throwsSignatures = aThrowsSignatureList;
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         int i;
         if (this.formalTypeParameters.length > 0) {
            sb.append("<");

            for(i = 0; i < this.formalTypeParameters.length; ++i) {
               sb.append(this.formalTypeParameters[i].toString());
            }

            sb.append(">");
         }

         sb.append("(");

         for(i = 0; i < this.parameters.length; ++i) {
            sb.append(this.parameters[i].toString());
         }

         sb.append(")");
         sb.append(this.returnType.toString());

         for(i = 0; i < this.throwsSignatures.length; ++i) {
            sb.append("^");
            sb.append(this.throwsSignatures[i].toString());
         }

         return sb.toString();
      }
   }

   public static class ClassSignature {
      public FormalTypeParameter[] formalTypeParameters;
      public ClassTypeSignature superclassSignature;
      public ClassTypeSignature[] superInterfaceSignatures;

      public ClassSignature() {
         this.formalTypeParameters = GenericSignature.FormalTypeParameter.NONE;
         this.superInterfaceSignatures = GenericSignature.ClassTypeSignature.NONE;
      }

      public String toString() {
         StringBuffer ret = new StringBuffer();
         ret.append(this.formalTypeParameters.toString());
         ret.append(this.superclassSignature.toString());

         for(int i = 0; i < this.superInterfaceSignatures.length; ++i) {
            ret.append(this.superInterfaceSignatures[i].toString());
         }

         return ret.toString();
      }
   }
}
