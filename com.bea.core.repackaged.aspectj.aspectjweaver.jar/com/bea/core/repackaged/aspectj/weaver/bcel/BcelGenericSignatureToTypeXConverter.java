package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.util.GenericSignature;
import com.bea.core.repackaged.aspectj.weaver.BoundedReferenceType;
import com.bea.core.repackaged.aspectj.weaver.ReferenceType;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.TypeFactory;
import com.bea.core.repackaged.aspectj.weaver.TypeVariable;
import com.bea.core.repackaged.aspectj.weaver.TypeVariableReferenceType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.tools.Trace;
import com.bea.core.repackaged.aspectj.weaver.tools.TraceFactory;
import java.util.HashMap;
import java.util.Map;

public class BcelGenericSignatureToTypeXConverter {
   private static Trace trace = TraceFactory.getTraceFactory().getTrace(BcelGenericSignatureToTypeXConverter.class);

   public static ResolvedType classTypeSignature2TypeX(GenericSignature.ClassTypeSignature aClassTypeSignature, GenericSignature.FormalTypeParameter[] typeParams, World world) throws GenericSignatureFormatException {
      Map typeMap = new HashMap();
      ResolvedType ret = classTypeSignature2TypeX(aClassTypeSignature, typeParams, world, typeMap);
      fixUpCircularDependencies(ret, typeMap);
      return ret;
   }

   private static ResolvedType classTypeSignature2TypeX(GenericSignature.ClassTypeSignature aClassTypeSignature, GenericSignature.FormalTypeParameter[] typeParams, World world, Map inProgressTypeVariableResolutions) throws GenericSignatureFormatException {
      StringBuffer sig = new StringBuffer();
      sig.append(aClassTypeSignature.outerType.identifier.replace(';', ' ').trim());

      for(int i = 0; i < aClassTypeSignature.nestedTypes.length; ++i) {
         sig.append("$");
         sig.append(aClassTypeSignature.nestedTypes[i].identifier.replace(';', ' ').trim());
      }

      sig.append(";");
      GenericSignature.SimpleClassTypeSignature innerType = aClassTypeSignature.outerType;
      if (aClassTypeSignature.nestedTypes.length > 0) {
         innerType = aClassTypeSignature.nestedTypes[aClassTypeSignature.nestedTypes.length - 1];
      }

      if (innerType.typeArguments.length <= 0) {
         return world.resolve(UnresolvedType.forSignature(sig.toString()));
      } else {
         ResolvedType theBaseType = UnresolvedType.forSignature(sig.toString()).resolve(world);
         if (!theBaseType.isGenericType() && !theBaseType.isRawType()) {
            if (trace.isTraceEnabled()) {
               trace.event("classTypeSignature2TypeX: this type is not a generic type:", (Object)null, (Object[])(new Object[]{theBaseType}));
            }

            return theBaseType;
         } else {
            ResolvedType[] typeArgumentTypes = new ResolvedType[innerType.typeArguments.length];

            for(int i = 0; i < typeArgumentTypes.length; ++i) {
               typeArgumentTypes[i] = typeArgument2TypeX(innerType.typeArguments[i], typeParams, world, inProgressTypeVariableResolutions);
            }

            return TypeFactory.createParameterizedType(theBaseType, typeArgumentTypes, world);
         }
      }
   }

   public static ResolvedType fieldTypeSignature2TypeX(GenericSignature.FieldTypeSignature aFieldTypeSignature, GenericSignature.FormalTypeParameter[] typeParams, World world) throws GenericSignatureFormatException {
      Map typeMap = new HashMap();
      ResolvedType ret = fieldTypeSignature2TypeX(aFieldTypeSignature, typeParams, world, typeMap);
      fixUpCircularDependencies(ret, typeMap);
      return ret;
   }

   private static ResolvedType fieldTypeSignature2TypeX(GenericSignature.FieldTypeSignature aFieldTypeSignature, GenericSignature.FormalTypeParameter[] typeParams, World world, Map inProgressTypeVariableResolutions) throws GenericSignatureFormatException {
      if (aFieldTypeSignature.isClassTypeSignature()) {
         return classTypeSignature2TypeX((GenericSignature.ClassTypeSignature)aFieldTypeSignature, typeParams, world, inProgressTypeVariableResolutions);
      } else if (!aFieldTypeSignature.isArrayTypeSignature()) {
         if (aFieldTypeSignature.isTypeVariableSignature()) {
            ResolvedType rtx = typeVariableSignature2TypeX((GenericSignature.TypeVariableSignature)aFieldTypeSignature, typeParams, world, inProgressTypeVariableResolutions);
            return rtx;
         } else {
            throw new GenericSignatureFormatException("Cant understand field type signature: " + aFieldTypeSignature);
         }
      } else {
         int dims = 0;

         Object ats;
         for(ats = aFieldTypeSignature; ats instanceof GenericSignature.ArrayTypeSignature; ats = ((GenericSignature.ArrayTypeSignature)ats).typeSig) {
            ++dims;
         }

         return world.resolve(UnresolvedType.makeArray(typeSignature2TypeX((GenericSignature.TypeSignature)ats, typeParams, world, inProgressTypeVariableResolutions), dims));
      }
   }

   public static TypeVariable formalTypeParameter2TypeVariable(GenericSignature.FormalTypeParameter aFormalTypeParameter, GenericSignature.FormalTypeParameter[] typeParams, World world) throws GenericSignatureFormatException {
      Map typeMap = new HashMap();
      return formalTypeParameter2TypeVariable(aFormalTypeParameter, typeParams, world, typeMap);
   }

   private static TypeVariable formalTypeParameter2TypeVariable(GenericSignature.FormalTypeParameter aFormalTypeParameter, GenericSignature.FormalTypeParameter[] typeParams, World world, Map inProgressTypeVariableResolutions) throws GenericSignatureFormatException {
      UnresolvedType upperBound = fieldTypeSignature2TypeX(aFormalTypeParameter.classBound, typeParams, world, inProgressTypeVariableResolutions);
      UnresolvedType[] ifBounds = new UnresolvedType[aFormalTypeParameter.interfaceBounds.length];

      for(int i = 0; i < ifBounds.length; ++i) {
         ifBounds[i] = fieldTypeSignature2TypeX(aFormalTypeParameter.interfaceBounds[i], typeParams, world, inProgressTypeVariableResolutions);
      }

      return new TypeVariable(aFormalTypeParameter.identifier, upperBound, ifBounds);
   }

   private static ResolvedType typeArgument2TypeX(GenericSignature.TypeArgument aTypeArgument, GenericSignature.FormalTypeParameter[] typeParams, World world, Map inProgressTypeVariableResolutions) throws GenericSignatureFormatException {
      if (aTypeArgument.isWildcard) {
         return UnresolvedType.SOMETHING.resolve(world);
      } else {
         ResolvedType bound;
         ResolvedType resolvedBound;
         ReferenceType rBound;
         if (aTypeArgument.isMinus) {
            bound = fieldTypeSignature2TypeX(aTypeArgument.signature, typeParams, world, inProgressTypeVariableResolutions);
            resolvedBound = world.resolve((UnresolvedType)bound);
            if (resolvedBound.isMissing()) {
               world.getLint().cantFindType.signal("Unable to find type (for bound): " + resolvedBound.getName(), (ISourceLocation)null);
               resolvedBound = world.resolve(UnresolvedType.OBJECT);
            }

            rBound = (ReferenceType)resolvedBound;
            return new BoundedReferenceType(rBound, false, world);
         } else if (aTypeArgument.isPlus) {
            bound = fieldTypeSignature2TypeX(aTypeArgument.signature, typeParams, world, inProgressTypeVariableResolutions);
            resolvedBound = world.resolve((UnresolvedType)bound);
            if (resolvedBound.isMissing()) {
               world.getLint().cantFindType.signal("Unable to find type (for bound): " + resolvedBound.getName(), (ISourceLocation)null);
               resolvedBound = world.resolve(UnresolvedType.OBJECT);
            }

            rBound = (ReferenceType)resolvedBound;
            return new BoundedReferenceType(rBound, true, world);
         } else {
            return fieldTypeSignature2TypeX(aTypeArgument.signature, typeParams, world, inProgressTypeVariableResolutions);
         }
      }
   }

   public static ResolvedType typeSignature2TypeX(GenericSignature.TypeSignature aTypeSig, GenericSignature.FormalTypeParameter[] typeParams, World world) throws GenericSignatureFormatException {
      Map typeMap = new HashMap();
      ResolvedType ret = typeSignature2TypeX(aTypeSig, typeParams, world, typeMap);
      fixUpCircularDependencies(ret, typeMap);
      return ret;
   }

   private static ResolvedType typeSignature2TypeX(GenericSignature.TypeSignature aTypeSig, GenericSignature.FormalTypeParameter[] typeParams, World world, Map inProgressTypeVariableResolutions) throws GenericSignatureFormatException {
      return aTypeSig.isBaseType() ? world.resolve(UnresolvedType.forSignature(((GenericSignature.BaseTypeSignature)aTypeSig).toString())) : fieldTypeSignature2TypeX((GenericSignature.FieldTypeSignature)aTypeSig, typeParams, world, inProgressTypeVariableResolutions);
   }

   private static ResolvedType typeVariableSignature2TypeX(GenericSignature.TypeVariableSignature aTypeVarSig, GenericSignature.FormalTypeParameter[] typeParams, World world, Map inProgressTypeVariableResolutions) throws GenericSignatureFormatException {
      GenericSignature.FormalTypeParameter typeVarBounds = null;

      for(int i = 0; i < typeParams.length; ++i) {
         if (typeParams[i].identifier.equals(aTypeVarSig.typeVariableName)) {
            typeVarBounds = typeParams[i];
            break;
         }
      }

      if (typeVarBounds == null) {
         return new TypeVariableReferenceType(new TypeVariable(aTypeVarSig.typeVariableName), world);
      } else if (inProgressTypeVariableResolutions.containsKey(typeVarBounds)) {
         return (ResolvedType)inProgressTypeVariableResolutions.get(typeVarBounds);
      } else {
         inProgressTypeVariableResolutions.put(typeVarBounds, new FTPHolder(typeVarBounds, world));
         ReferenceType ret = new TypeVariableReferenceType(formalTypeParameter2TypeVariable(typeVarBounds, typeParams, world, inProgressTypeVariableResolutions), world);
         inProgressTypeVariableResolutions.put(typeVarBounds, ret);
         return ret;
      }
   }

   private static void fixUpCircularDependencies(ResolvedType aTypeX, Map typeVariableResolutions) {
      if (aTypeX instanceof ReferenceType) {
         ReferenceType rt = (ReferenceType)aTypeX;
         TypeVariable[] typeVars = rt.getTypeVariables();
         if (typeVars != null) {
            for(int i = 0; i < typeVars.length; ++i) {
               if (typeVars[i].getUpperBound() instanceof FTPHolder) {
                  GenericSignature.FormalTypeParameter key = ((FTPHolder)typeVars[i].getUpperBound()).ftpToBeSubstituted;
                  typeVars[i].setUpperBound((UnresolvedType)typeVariableResolutions.get(key));
               }
            }
         }

      }
   }

   public static class GenericSignatureFormatException extends Exception {
      public GenericSignatureFormatException(String explanation) {
         super(explanation);
      }
   }

   private static class FTPHolder extends ReferenceType {
      public GenericSignature.FormalTypeParameter ftpToBeSubstituted;

      public FTPHolder(GenericSignature.FormalTypeParameter ftp, World world) {
         super("Ljava/lang/Object;", world);
         this.ftpToBeSubstituted = ftp;
      }

      public String toString() {
         return "placeholder for TypeVariable of " + this.ftpToBeSubstituted.toString();
      }

      public ResolvedType resolve(World world) {
         return this;
      }

      public boolean isCacheable() {
         return false;
      }
   }
}
