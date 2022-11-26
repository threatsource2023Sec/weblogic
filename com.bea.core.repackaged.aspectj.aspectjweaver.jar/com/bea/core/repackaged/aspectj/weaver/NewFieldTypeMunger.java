package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NewFieldTypeMunger extends ResolvedTypeMunger {
   public static final int VersionOne = 1;
   public static final int VersionTwo = 2;
   public int version = 1;

   public NewFieldTypeMunger(ResolvedMember signature, Set superMethodsCalled, List typeVariableAliases) {
      super(Field, signature);
      this.version = 2;
      this.typeVariableAliases = typeVariableAliases;
      signature.setAnnotatedElsewhere(true);
      this.setSuperMethodsCalled(superMethodsCalled);
   }

   public ResolvedMember getInitMethod(UnresolvedType aspectType) {
      return AjcMemberMaker.interFieldInitializer(this.signature, aspectType);
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      this.kind.write(s);
      this.signature.write(s);
      this.writeSuperMethodsCalled(s);
      this.writeSourceLocation(s);
      this.writeOutTypeAliases(s);
      s.writeInt(this.version);
   }

   public static ResolvedTypeMunger readField(VersionedDataInputStream s, ISourceContext context) throws IOException {
      ISourceLocation sloc = null;
      ResolvedMember fieldSignature = ResolvedMemberImpl.readResolvedMember(s, context);
      Set superMethodsCalled = readSuperMethodsCalled(s);
      sloc = readSourceLocation(s);
      List aliases = readInTypeAliases(s);
      NewFieldTypeMunger munger = new NewFieldTypeMunger(fieldSignature, superMethodsCalled, aliases);
      if (sloc != null) {
         munger.setSourceLocation(sloc);
      }

      if (s.getMajorVersion() >= 7) {
         munger.version = s.readInt();
      } else {
         munger.version = 1;
      }

      return munger;
   }

   public ResolvedMember getMatchingSyntheticMember(Member member, ResolvedType aspectType) {
      ResolvedType onType = aspectType.getWorld().resolve(this.getSignature().getDeclaringType());
      if (((ResolvedType)onType).isRawType()) {
         onType = ((ResolvedType)onType).getGenericType();
      }

      ResolvedMember ret = AjcMemberMaker.interFieldGetDispatcher(this.getSignature(), aspectType);
      if (ResolvedType.matches(ret, member)) {
         return this.getSignature();
      } else {
         ret = AjcMemberMaker.interFieldSetDispatcher(this.getSignature(), aspectType);
         if (ResolvedType.matches(ret, member)) {
            return this.getSignature();
         } else {
            ret = AjcMemberMaker.interFieldInterfaceGetter(this.getSignature(), (ResolvedType)onType, aspectType);
            if (ResolvedType.matches(ret, member)) {
               return this.getSignature();
            } else {
               ret = AjcMemberMaker.interFieldInterfaceSetter(this.getSignature(), (ResolvedType)onType, aspectType);
               return ResolvedType.matches(ret, member) ? this.getSignature() : super.getMatchingSyntheticMember(member, aspectType);
            }
         }
      }
   }

   public ResolvedTypeMunger parameterizedFor(ResolvedType target) {
      ResolvedType genericType = target;
      if (target.isRawType() || target.isParameterizedType()) {
         genericType = target.getGenericType();
      }

      ResolvedMember parameterizedSignature = null;
      if (target.isGenericType()) {
         TypeVariable[] vars = target.getTypeVariables();
         UnresolvedTypeVariableReferenceType[] varRefs = new UnresolvedTypeVariableReferenceType[vars.length];

         for(int i = 0; i < vars.length; ++i) {
            varRefs[i] = new UnresolvedTypeVariableReferenceType(vars[i]);
         }

         parameterizedSignature = this.getSignature().parameterizedWith(varRefs, (ResolvedType)genericType, true, this.typeVariableAliases);
      } else {
         parameterizedSignature = this.getSignature().parameterizedWith(target.getTypeParameters(), (ResolvedType)genericType, target.isParameterizedType(), this.typeVariableAliases);
      }

      NewFieldTypeMunger nftm = new NewFieldTypeMunger(parameterizedSignature, this.getSuperMethodsCalled(), this.typeVariableAliases);
      nftm.setDeclaredSignature(this.getSignature());
      nftm.setSourceLocation(this.getSourceLocation());
      return nftm;
   }

   public ResolvedTypeMunger parameterizeWith(Map m, World w) {
      ResolvedMember parameterizedSignature = this.getSignature().parameterizedWith(m, w);
      NewFieldTypeMunger nftm = new NewFieldTypeMunger(parameterizedSignature, this.getSuperMethodsCalled(), this.typeVariableAliases);
      nftm.setDeclaredSignature(this.getSignature());
      nftm.setSourceLocation(this.getSourceLocation());
      return nftm;
   }

   public boolean equals(Object other) {
      if (!(other instanceof NewFieldTypeMunger)) {
         return false;
      } else {
         boolean var10000;
         label55: {
            label49: {
               NewFieldTypeMunger o = (NewFieldTypeMunger)other;
               if (this.kind == null) {
                  if (o.kind != null) {
                     break label49;
                  }
               } else if (!this.kind.equals(o.kind)) {
                  break label49;
               }

               if (this.signature == null) {
                  if (o.signature != null) {
                     break label49;
                  }
               } else if (!this.signature.equals(o.signature)) {
                  break label49;
               }

               if (this.declaredSignature == null) {
                  if (o.declaredSignature != null) {
                     break label49;
                  }
               } else if (!this.declaredSignature.equals(o.declaredSignature)) {
                  break label49;
               }

               if (this.typeVariableAliases == null) {
                  if (o.typeVariableAliases == null) {
                     break label55;
                  }
               } else if (this.typeVariableAliases.equals(o.typeVariableAliases)) {
                  break label55;
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
      result = 37 * result + this.kind.hashCode();
      result = 37 * result + (this.signature == null ? 0 : this.signature.hashCode());
      result = 37 * result + (this.declaredSignature == null ? 0 : this.declaredSignature.hashCode());
      result = 37 * result + (this.typeVariableAliases == null ? 0 : this.typeVariableAliases.hashCode());
      return result;
   }
}
