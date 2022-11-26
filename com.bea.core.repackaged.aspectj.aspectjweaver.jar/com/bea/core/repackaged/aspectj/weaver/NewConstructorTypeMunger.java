package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class NewConstructorTypeMunger extends ResolvedTypeMunger {
   private ResolvedMember syntheticConstructor;
   private ResolvedMember explicitConstructor;
   private volatile int hashCode = 0;

   public NewConstructorTypeMunger(ResolvedMember signature, ResolvedMember syntheticConstructor, ResolvedMember explicitConstructor, Set superMethodsCalled, List typeVariableAliases) {
      super(Constructor, signature);
      this.syntheticConstructor = syntheticConstructor;
      this.typeVariableAliases = typeVariableAliases;
      this.explicitConstructor = explicitConstructor;
      this.setSuperMethodsCalled(superMethodsCalled);
   }

   public boolean equals(Object other) {
      if (!(other instanceof NewConstructorTypeMunger)) {
         return false;
      } else {
         NewConstructorTypeMunger o = (NewConstructorTypeMunger)other;
         return (this.syntheticConstructor == null ? o.syntheticConstructor == null : this.syntheticConstructor.equals(o.syntheticConstructor)) & (this.explicitConstructor == null ? o.explicitConstructor == null : this.explicitConstructor.equals(o.explicitConstructor));
      }
   }

   public boolean equivalentTo(Object other) {
      if (!(other instanceof NewConstructorTypeMunger)) {
         return false;
      } else {
         NewConstructorTypeMunger o = (NewConstructorTypeMunger)other;
         return this.syntheticConstructor == null ? o.syntheticConstructor == null : this.syntheticConstructor.equals(o.syntheticConstructor);
      }
   }

   public int hashCode() {
      if (this.hashCode == 0) {
         int result = 17;
         result = 37 * result + (this.syntheticConstructor == null ? 0 : this.syntheticConstructor.hashCode());
         result = 37 * result + (this.explicitConstructor == null ? 0 : this.explicitConstructor.hashCode());
         this.hashCode = result;
      }

      return this.hashCode;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      this.kind.write(s);
      this.signature.write(s);
      this.syntheticConstructor.write(s);
      this.explicitConstructor.write(s);
      this.writeSuperMethodsCalled(s);
      this.writeSourceLocation(s);
      this.writeOutTypeAliases(s);
   }

   public static ResolvedTypeMunger readConstructor(VersionedDataInputStream s, ISourceContext context) throws IOException {
      ISourceLocation sloc = null;
      ResolvedMember sig = ResolvedMemberImpl.readResolvedMember(s, context);
      ResolvedMember syntheticCtor = ResolvedMemberImpl.readResolvedMember(s, context);
      ResolvedMember explicitCtor = ResolvedMemberImpl.readResolvedMember(s, context);
      Set superMethodsCalled = readSuperMethodsCalled(s);
      sloc = readSourceLocation(s);
      List typeVarAliases = readInTypeAliases(s);
      ResolvedTypeMunger munger = new NewConstructorTypeMunger(sig, syntheticCtor, explicitCtor, superMethodsCalled, typeVarAliases);
      if (sloc != null) {
         munger.setSourceLocation(sloc);
      }

      return munger;
   }

   public ResolvedMember getExplicitConstructor() {
      return this.explicitConstructor;
   }

   public ResolvedMember getSyntheticConstructor() {
      return this.syntheticConstructor;
   }

   public void setExplicitConstructor(ResolvedMember explicitConstructor) {
      this.explicitConstructor = explicitConstructor;
      this.hashCode = 0;
   }

   public ResolvedMember getMatchingSyntheticMember(Member member, ResolvedType aspectType) {
      ResolvedMember ret = this.getSyntheticConstructor();
      return ResolvedType.matches(ret, member) ? this.getSignature() : super.getMatchingSyntheticMember(member, aspectType);
   }

   public void check(World world) {
      if (this.getSignature().getDeclaringType().resolve(world).isAspect()) {
         world.showMessage(IMessage.ERROR, WeaverMessages.format("itdConsOnAspect"), this.getSignature().getSourceLocation(), (ISourceLocation)null);
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

      NewConstructorTypeMunger nctm = new NewConstructorTypeMunger(parameterizedSignature, this.syntheticConstructor, this.explicitConstructor, this.getSuperMethodsCalled(), this.typeVariableAliases);
      nctm.setSourceLocation(this.getSourceLocation());
      return nctm;
   }
}
