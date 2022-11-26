package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.context.CompilationAndWeavingContext;
import java.util.Collections;
import java.util.List;

public class MissingResolvedTypeWithKnownSignature extends ResolvedType {
   private static ResolvedMember[] NO_MEMBERS = new ResolvedMember[0];
   private static ResolvedType[] NO_TYPES = new ResolvedType[0];
   private boolean issuedCantFindTypeError = false;
   private boolean issuedJoinPointWarning = false;
   private boolean issuedMissingInterfaceWarning = false;

   public MissingResolvedTypeWithKnownSignature(String signature, World world) {
      super(signature, world);
   }

   public boolean isMissing() {
      return true;
   }

   public MissingResolvedTypeWithKnownSignature(String signature, String signatureErasure, World world) {
      super(signature, signatureErasure, world);
   }

   public ResolvedMember[] getDeclaredFields() {
      this.raiseCantFindType("cantFindTypeFields");
      return NO_MEMBERS;
   }

   public ResolvedMember[] getDeclaredMethods() {
      this.raiseCantFindType("cantFindTypeMethods");
      return NO_MEMBERS;
   }

   public AnnotationAJ[] getAnnotations() {
      this.raiseCantFindType("cantFindTypeAnnotation");
      return AnnotationAJ.EMPTY_ARRAY;
   }

   public ResolvedType[] getDeclaredInterfaces() {
      this.raiseCantFindType("cantFindTypeInterfaces");
      return NO_TYPES;
   }

   public ResolvedMember[] getDeclaredPointcuts() {
      this.raiseCantFindType("cantFindTypePointcuts");
      return NO_MEMBERS;
   }

   public ResolvedType getSuperclass() {
      this.raiseCantFindType("cantFindTypeSuperclass");
      return ResolvedType.MISSING;
   }

   public int getModifiers() {
      this.raiseCantFindType("cantFindTypeModifiers");
      return 0;
   }

   public ISourceContext getSourceContext() {
      return new ISourceContext() {
         public ISourceLocation makeSourceLocation(IHasPosition position) {
            return null;
         }

         public ISourceLocation makeSourceLocation(int line, int offset) {
            return null;
         }

         public int getOffset() {
            return 0;
         }

         public void tidy() {
         }
      };
   }

   public boolean isAssignableFrom(ResolvedType other) {
      this.raiseCantFindType("cantFindTypeAssignable", other.getName());
      return false;
   }

   public boolean isAssignableFrom(ResolvedType other, boolean allowMissing) {
      return allowMissing ? false : this.isAssignableFrom(other);
   }

   public boolean isCoerceableFrom(ResolvedType other) {
      this.raiseCantFindType("cantFindTypeCoerceable", other.getName());
      return false;
   }

   public boolean hasAnnotation(UnresolvedType ofType) {
      this.raiseCantFindType("cantFindTypeAnnotation");
      return false;
   }

   public List getInterTypeMungers() {
      return Collections.EMPTY_LIST;
   }

   public List getInterTypeMungersIncludingSupers() {
      return Collections.EMPTY_LIST;
   }

   public List getInterTypeParentMungers() {
      return Collections.EMPTY_LIST;
   }

   public List getInterTypeParentMungersIncludingSupers() {
      return Collections.EMPTY_LIST;
   }

   protected void collectInterTypeMungers(List collector) {
   }

   public void raiseWarningOnJoinPointSignature(String signature) {
      if (!this.issuedJoinPointWarning) {
         String message = WeaverMessages.format("cantFindTypeJoinPoint", this.getName(), signature);
         message = message + "\n" + CompilationAndWeavingContext.getCurrentContext();
         this.world.getLint().cantFindTypeAffectingJoinPointMatch.signal(message, (ISourceLocation)null);
         this.issuedJoinPointWarning = true;
      }
   }

   public void raiseWarningOnMissingInterfaceWhilstFindingMethods() {
      if (!this.issuedMissingInterfaceWarning) {
         String message = WeaverMessages.format("cantFindTypeInterfaceMethods", this.getName(), this.signature);
         message = message + "\n" + CompilationAndWeavingContext.getCurrentContext();
         this.world.getLint().cantFindTypeAffectingJoinPointMatch.signal(message, (ISourceLocation)null);
         this.issuedMissingInterfaceWarning = true;
      }
   }

   private void raiseCantFindType(String key) {
      if (this.world.getLint().cantFindType.isEnabled()) {
         if (!this.issuedCantFindTypeError) {
            String message = WeaverMessages.format(key, this.getName());
            message = message + "\n" + CompilationAndWeavingContext.getCurrentContext();
            this.world.getLint().cantFindType.signal(message, (ISourceLocation)null);
            this.issuedCantFindTypeError = true;
         }
      }
   }

   private void raiseCantFindType(String key, String insert) {
      if (!this.issuedCantFindTypeError) {
         String message = WeaverMessages.format(key, this.getName(), insert);
         message = message + "\n" + CompilationAndWeavingContext.getCurrentContext();
         this.world.getLint().cantFindType.signal(message, (ISourceLocation)null);
         this.issuedCantFindTypeError = true;
      }
   }
}
