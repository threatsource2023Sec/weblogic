package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NewMethodTypeMunger extends ResolvedTypeMunger {
   public NewMethodTypeMunger(ResolvedMember signature, Set superMethodsCalled, List typeVariableAliases) {
      super(Method, signature);
      this.typeVariableAliases = typeVariableAliases;
      this.setSuperMethodsCalled(superMethodsCalled);
   }

   public ResolvedMember getInterMethodBody(UnresolvedType aspectType) {
      return AjcMemberMaker.interMethodBody(this.signature, aspectType);
   }

   public ResolvedMember getDeclaredInterMethodBody(UnresolvedType aspectType, World w) {
      if (this.declaredSignature != null) {
         ResolvedMember rm = this.declaredSignature.parameterizedWith((UnresolvedType[])null, this.signature.getDeclaringType().resolve(w), false, this.getTypeVariableAliases());
         return AjcMemberMaker.interMethodBody(rm, aspectType);
      } else {
         return AjcMemberMaker.interMethodBody(this.signature, aspectType);
      }
   }

   public ResolvedMember getDeclaredInterMethodDispatcher(UnresolvedType aspectType, World w) {
      if (this.declaredSignature != null) {
         ResolvedMember rm = this.declaredSignature.parameterizedWith((UnresolvedType[])null, this.signature.getDeclaringType().resolve(w), false, this.getTypeVariableAliases());
         return AjcMemberMaker.interMethodDispatcher(rm, aspectType);
      } else {
         return AjcMemberMaker.interMethodDispatcher(this.signature, aspectType);
      }
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      this.kind.write(s);
      this.signature.write(s);
      this.writeSuperMethodsCalled(s);
      this.writeSourceLocation(s);
      this.writeOutTypeAliases(s);
   }

   public static ResolvedTypeMunger readMethod(VersionedDataInputStream s, ISourceContext context) throws IOException {
      ISourceLocation sloc = null;
      ResolvedMemberImpl rmImpl = ResolvedMemberImpl.readResolvedMember(s, context);
      Set superMethodsCalled = readSuperMethodsCalled(s);
      sloc = readSourceLocation(s);
      List typeVarAliases = readInTypeAliases(s);
      ResolvedTypeMunger munger = new NewMethodTypeMunger(rmImpl, superMethodsCalled, typeVarAliases);
      if (sloc != null) {
         munger.setSourceLocation(sloc);
      }

      return munger;
   }

   public ResolvedMember getMatchingSyntheticMember(Member member, ResolvedType aspectType) {
      ResolvedMember ret = AjcMemberMaker.interMethodDispatcher(this.getSignature(), aspectType);
      return ResolvedType.matches(ret, member) ? this.getSignature() : super.getMatchingSyntheticMember(member, aspectType);
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

      NewMethodTypeMunger nmtm = new NewMethodTypeMunger(parameterizedSignature, this.getSuperMethodsCalled(), this.typeVariableAliases);
      nmtm.setDeclaredSignature(this.getSignature());
      nmtm.setSourceLocation(this.getSourceLocation());
      return nmtm;
   }

   public boolean equals(Object other) {
      if (!(other instanceof NewMethodTypeMunger)) {
         return false;
      } else {
         boolean var10000;
         label55: {
            label49: {
               NewMethodTypeMunger o = (NewMethodTypeMunger)other;
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

   public ResolvedTypeMunger parameterizeWith(Map m, World w) {
      ResolvedMember parameterizedSignature = this.getSignature().parameterizedWith(m, w);
      NewMethodTypeMunger nmtm = new NewMethodTypeMunger(parameterizedSignature, this.getSuperMethodsCalled(), this.typeVariableAliases);
      nmtm.setDeclaredSignature(this.getSignature());
      nmtm.setSourceLocation(this.getSourceLocation());
      return nmtm;
   }
}
