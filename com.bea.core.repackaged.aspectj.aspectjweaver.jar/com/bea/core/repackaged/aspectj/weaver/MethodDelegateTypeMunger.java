package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.weaver.patterns.TypePattern;
import java.io.IOException;

public class MethodDelegateTypeMunger extends ResolvedTypeMunger {
   private final UnresolvedType aspect;
   private UnresolvedType fieldType;
   private final String implClassName;
   private final TypePattern typePattern;
   private String factoryMethodName;
   private String factoryMethodSignature;
   private int bitflags;
   private static final int REPLACING_EXISTING_METHOD = 1;
   private volatile int hashCode = 0;

   public MethodDelegateTypeMunger(ResolvedMember signature, UnresolvedType aspect, String implClassName, TypePattern typePattern) {
      super(MethodDelegate2, signature);
      this.aspect = aspect;
      this.typePattern = typePattern;
      this.implClassName = implClassName;
      this.factoryMethodName = "";
      this.factoryMethodSignature = "";
   }

   public MethodDelegateTypeMunger(ResolvedMember signature, UnresolvedType aspect, String implClassName, TypePattern typePattern, String factoryMethodName, String factoryMethodSignature) {
      super(MethodDelegate2, signature);
      this.aspect = aspect;
      this.typePattern = typePattern;
      this.implClassName = implClassName;
      this.factoryMethodName = factoryMethodName;
      this.factoryMethodSignature = factoryMethodSignature;
   }

   public boolean equals(Object other) {
      if (!(other instanceof MethodDelegateTypeMunger)) {
         return false;
      } else {
         boolean var10000;
         label71: {
            MethodDelegateTypeMunger o = (MethodDelegateTypeMunger)other;
            if (o.aspect == null) {
               if (this.aspect != null) {
                  break label71;
               }
            } else if (!this.aspect.equals(o.aspect)) {
               break label71;
            }

            if (o.typePattern == null) {
               if (this.typePattern != null) {
                  break label71;
               }
            } else if (!this.typePattern.equals(o.typePattern)) {
               break label71;
            }

            if (o.implClassName == null) {
               if (this.implClassName != null) {
                  break label71;
               }
            } else if (!this.implClassName.equals(o.implClassName)) {
               break label71;
            }

            if (o.fieldType == null) {
               if (this.fieldType != null) {
                  break label71;
               }
            } else if (!this.fieldType.equals(o.fieldType)) {
               break label71;
            }

            if (o.factoryMethodName == null) {
               if (this.factoryMethodName != null) {
                  break label71;
               }
            } else if (!this.factoryMethodName.equals(o.factoryMethodName)) {
               break label71;
            }

            if (o.factoryMethodSignature == null) {
               if (this.factoryMethodSignature != null) {
                  break label71;
               }
            } else if (!this.factoryMethodSignature.equals(o.factoryMethodSignature)) {
               break label71;
            }

            if (o.bitflags == this.bitflags) {
               var10000 = true;
               return var10000;
            }
         }

         var10000 = false;
         return var10000;
      }
   }

   public int hashCode() {
      if (this.hashCode == 0) {
         int result = 17;
         result = 37 * result + (this.aspect == null ? 0 : this.aspect.hashCode());
         result = 37 * result + (this.typePattern == null ? 0 : this.typePattern.hashCode());
         result = 37 * result + (this.implClassName == null ? 0 : this.implClassName.hashCode());
         result = 37 * result + (this.fieldType == null ? 0 : this.fieldType.hashCode());
         result = 37 * result + (this.factoryMethodName == null ? 0 : this.factoryMethodName.hashCode());
         result = 37 * result + (this.factoryMethodSignature == null ? 0 : this.factoryMethodSignature.hashCode());
         result = 37 * result + this.bitflags;
         this.hashCode = result;
      }

      return this.hashCode;
   }

   public ResolvedMember getDelegate(ResolvedType targetType) {
      return AjcMemberMaker.itdAtDeclareParentsField(targetType, this.fieldType, this.aspect);
   }

   public ResolvedMember getDelegateFactoryMethod(World w) {
      ResolvedType aspectType = w.resolve(this.aspect);
      ResolvedMember[] methods = aspectType.getDeclaredMethods();

      for(int i = 0; i < methods.length; ++i) {
         ResolvedMember rm = methods[i];
         if (rm.getName().equals(this.factoryMethodName) && rm.getSignature().equals(this.factoryMethodSignature)) {
            return rm;
         }
      }

      return null;
   }

   public String getImplClassName() {
      return this.implClassName;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      this.kind.write(s);
      this.signature.write(s);
      this.aspect.write(s);
      s.writeUTF(this.implClassName);
      this.typePattern.write(s);
      this.fieldType.write(s);
      s.writeUTF(this.factoryMethodName);
      s.writeUTF(this.factoryMethodSignature);
      s.writeInt(this.bitflags);
   }

   public static ResolvedTypeMunger readMethod(VersionedDataInputStream s, ISourceContext context, boolean isEnhanced) throws IOException {
      ResolvedMemberImpl signature = ResolvedMemberImpl.readResolvedMember(s, context);
      UnresolvedType aspect = UnresolvedType.read(s);
      String implClassName = s.readUTF();
      TypePattern tp = TypePattern.read(s, context);
      MethodDelegateTypeMunger typeMunger = new MethodDelegateTypeMunger(signature, aspect, implClassName, tp);
      UnresolvedType fieldType = null;
      if (isEnhanced) {
         fieldType = UnresolvedType.read(s);
      } else {
         fieldType = signature.getDeclaringType();
      }

      typeMunger.setFieldType(fieldType);
      if (isEnhanced) {
         typeMunger.factoryMethodName = s.readUTF();
         typeMunger.factoryMethodSignature = s.readUTF();
         typeMunger.bitflags = s.readInt();
      }

      return typeMunger;
   }

   public boolean matches(ResolvedType matchType, ResolvedType aspectType) {
      return !matchType.isEnum() && !matchType.isInterface() && !matchType.isAnnotation() ? this.typePattern.matchesStatically(matchType) : false;
   }

   public boolean changesPublicSignature() {
      return true;
   }

   public void setFieldType(UnresolvedType fieldType) {
      this.fieldType = fieldType;
   }

   public boolean specifiesDelegateFactoryMethod() {
      return this.factoryMethodName != null && this.factoryMethodName.length() != 0;
   }

   public String getFactoryMethodName() {
      return this.factoryMethodName;
   }

   public String getFactoryMethodSignature() {
      return this.factoryMethodSignature;
   }

   public UnresolvedType getAspect() {
      return this.aspect;
   }

   public boolean existsToSupportShadowMunging() {
      return true;
   }

   public void tagAsReplacingExistingMethod() {
      this.bitflags |= 1;
   }

   public boolean isReplacingExistingMethod() {
      return (this.bitflags & 1) != 0;
   }

   public static class FieldHostTypeMunger extends ResolvedTypeMunger {
      private final UnresolvedType aspect;
      private final TypePattern typePattern;

      public FieldHostTypeMunger(ResolvedMember field, UnresolvedType aspect, TypePattern typePattern) {
         super(FieldHost, field);
         this.aspect = aspect;
         this.typePattern = typePattern;
      }

      public boolean equals(Object other) {
         if (!(other instanceof FieldHostTypeMunger)) {
            return false;
         } else {
            boolean var10000;
            label38: {
               label27: {
                  FieldHostTypeMunger o = (FieldHostTypeMunger)other;
                  if (o.aspect == null) {
                     if (this.aspect != null) {
                        break label27;
                     }
                  } else if (!this.aspect.equals(o.aspect)) {
                     break label27;
                  }

                  if (o.typePattern == null) {
                     if (this.typePattern == null) {
                        break label38;
                     }
                  } else if (this.typePattern.equals(o.typePattern)) {
                     break label38;
                  }
               }

               var10000 = false;
               return var10000;
            }

            var10000 = true;
            return var10000;
         }
      }

      public int hashCode() {
         int result = 17;
         result = 37 * result + (this.aspect == null ? 0 : this.aspect.hashCode());
         result = 37 * result + (this.typePattern == null ? 0 : this.typePattern.hashCode());
         return result;
      }

      public void write(CompressingDataOutputStream s) throws IOException {
         this.kind.write(s);
         this.signature.write(s);
         this.aspect.write(s);
         this.typePattern.write(s);
      }

      public static ResolvedTypeMunger readFieldHost(VersionedDataInputStream s, ISourceContext context) throws IOException {
         ResolvedMemberImpl signature = ResolvedMemberImpl.readResolvedMember(s, context);
         UnresolvedType aspect = UnresolvedType.read(s);
         TypePattern tp = TypePattern.read(s, context);
         return new FieldHostTypeMunger(signature, aspect, tp);
      }

      public boolean matches(ResolvedType matchType, ResolvedType aspectType) {
         return !matchType.isEnum() && !matchType.isInterface() && !matchType.isAnnotation() ? this.typePattern.matchesStatically(matchType) : false;
      }

      public boolean changesPublicSignature() {
         return false;
      }

      public boolean existsToSupportShadowMunging() {
         return true;
      }
   }
}
