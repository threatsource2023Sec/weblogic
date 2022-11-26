package com.bea.core.repackaged.aspectj.weaver;

import java.util.Map;

public class BoundedReferenceType extends ReferenceType {
   public static final int UNBOUND = 0;
   public static final int EXTENDS = 1;
   public static final int SUPER = 2;
   public int kind;
   private ResolvedType lowerBound;
   private ResolvedType upperBound;
   protected ReferenceType[] additionalInterfaceBounds;

   public BoundedReferenceType(ReferenceType aBound, boolean isExtends, World world) {
      super((isExtends ? "+" : "-") + aBound.signature, aBound.signatureErasure, world);
      this.additionalInterfaceBounds = ReferenceType.EMPTY_ARRAY;
      if (isExtends) {
         this.kind = 1;
      } else {
         this.kind = 2;
      }

      if (isExtends) {
         this.upperBound = aBound;
      } else {
         this.lowerBound = aBound;
         this.upperBound = world.resolve(UnresolvedType.OBJECT);
      }

      this.setDelegate(new BoundedReferenceTypeDelegate((ReferenceType)this.getUpperBound()));
   }

   public BoundedReferenceType(ReferenceType aBound, boolean isExtends, World world, ReferenceType[] additionalInterfaces) {
      this(aBound, isExtends, world);
      this.additionalInterfaceBounds = additionalInterfaces;
   }

   protected BoundedReferenceType(String signature, String erasedSignature, World world) {
      super(signature, erasedSignature, world);
      this.additionalInterfaceBounds = ReferenceType.EMPTY_ARRAY;
      if (signature.equals("*")) {
         this.kind = 0;
         this.upperBound = world.resolve(UnresolvedType.OBJECT);
      } else {
         this.upperBound = world.resolve(forSignature(erasedSignature));
      }

      this.setDelegate(new BoundedReferenceTypeDelegate((ReferenceType)this.upperBound));
   }

   public BoundedReferenceType(World world) {
      super("*", "Ljava/lang/Object;", world);
      this.additionalInterfaceBounds = ReferenceType.EMPTY_ARRAY;
      this.kind = 0;
      this.upperBound = world.resolve(UnresolvedType.OBJECT);
      this.setDelegate(new BoundedReferenceTypeDelegate((ReferenceType)this.upperBound));
   }

   public UnresolvedType getUpperBound() {
      return this.upperBound;
   }

   public UnresolvedType getLowerBound() {
      return this.lowerBound;
   }

   public ReferenceType[] getAdditionalBounds() {
      return this.additionalInterfaceBounds;
   }

   public UnresolvedType parameterize(Map typeBindings) {
      if (this.kind == 0) {
         return this;
      } else {
         ReferenceType[] parameterizedAdditionalInterfaces = new ReferenceType[this.additionalInterfaceBounds == null ? 0 : this.additionalInterfaceBounds.length];

         for(int i = 0; i < parameterizedAdditionalInterfaces.length; ++i) {
            parameterizedAdditionalInterfaces[i] = (ReferenceType)this.additionalInterfaceBounds[i].parameterize(typeBindings);
         }

         return this.kind == 1 ? new BoundedReferenceType((ReferenceType)this.getUpperBound().parameterize(typeBindings), true, this.world, parameterizedAdditionalInterfaces) : new BoundedReferenceType((ReferenceType)this.getLowerBound().parameterize(typeBindings), false, this.world, parameterizedAdditionalInterfaces);
      }
   }

   public String getSignatureForAttribute() {
      StringBuilder ret = new StringBuilder();
      int i;
      if (this.kind == 2) {
         ret.append("-");
         ret.append(this.lowerBound.getSignatureForAttribute());

         for(i = 0; i < this.additionalInterfaceBounds.length; ++i) {
            ret.append(this.additionalInterfaceBounds[i].getSignatureForAttribute());
         }
      } else if (this.kind == 1) {
         ret.append("+");
         ret.append(this.upperBound.getSignatureForAttribute());

         for(i = 0; i < this.additionalInterfaceBounds.length; ++i) {
            ret.append(this.additionalInterfaceBounds[i].getSignatureForAttribute());
         }
      } else if (this.kind == 0) {
         ret.append("*");
      }

      return ret.toString();
   }

   public boolean hasLowerBound() {
      return this.lowerBound != null;
   }

   public boolean isExtends() {
      return this.kind == 1;
   }

   public boolean isSuper() {
      return this.kind == 2;
   }

   public boolean isUnbound() {
      return this.kind == 0;
   }

   public boolean alwaysMatches(ResolvedType aCandidateType) {
      if (this.isExtends()) {
         return ((ReferenceType)this.getUpperBound()).isAssignableFrom(aCandidateType);
      } else {
         return this.isSuper() ? aCandidateType.isAssignableFrom((ReferenceType)this.getLowerBound()) : true;
      }
   }

   public boolean canBeCoercedTo(ResolvedType aCandidateType) {
      if (this.alwaysMatches(aCandidateType)) {
         return true;
      } else if (aCandidateType.isGenericWildcard()) {
         BoundedReferenceType boundedRT = (BoundedReferenceType)aCandidateType;
         ResolvedType myUpperBound = (ResolvedType)this.getUpperBound();
         ResolvedType myLowerBound = (ResolvedType)this.getLowerBound();
         if (this.isExtends()) {
            if (boundedRT.isExtends()) {
               return myUpperBound.isAssignableFrom((ResolvedType)boundedRT.getUpperBound());
            } else if (boundedRT.isSuper()) {
               return myUpperBound == boundedRT.getLowerBound();
            } else {
               return true;
            }
         } else if (this.isSuper()) {
            if (boundedRT.isSuper()) {
               return ((ResolvedType)boundedRT.getLowerBound()).isAssignableFrom(myLowerBound);
            } else if (boundedRT.isExtends()) {
               return myLowerBound == boundedRT.getUpperBound();
            } else {
               return true;
            }
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public String getSimpleName() {
      if (!this.isExtends() && !this.isSuper()) {
         return "?";
      } else {
         return this.isExtends() ? "? extends " + this.getUpperBound().getSimpleName() : "? super " + this.getLowerBound().getSimpleName();
      }
   }

   public ResolvedType[] getDeclaredInterfaces() {
      ResolvedType[] interfaces = super.getDeclaredInterfaces();
      if (this.additionalInterfaceBounds.length > 0) {
         ResolvedType[] allInterfaces = new ResolvedType[interfaces.length + this.additionalInterfaceBounds.length];
         System.arraycopy(interfaces, 0, allInterfaces, 0, interfaces.length);
         System.arraycopy(this.additionalInterfaceBounds, 0, allInterfaces, interfaces.length, this.additionalInterfaceBounds.length);
         return allInterfaces;
      } else {
         return interfaces;
      }
   }

   public boolean isGenericWildcard() {
      return true;
   }
}
