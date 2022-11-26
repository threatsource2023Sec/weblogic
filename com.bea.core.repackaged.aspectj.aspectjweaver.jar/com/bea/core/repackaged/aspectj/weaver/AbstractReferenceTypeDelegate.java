package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.util.GenericSignature;
import com.bea.core.repackaged.aspectj.util.GenericSignatureParser;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractReferenceTypeDelegate implements ReferenceTypeDelegate {
   private String sourcefilename = "<Unknown>";
   private ISourceContext sourceContext;
   protected boolean exposedToWeaver;
   protected ReferenceType resolvedTypeX;
   protected GenericSignature.ClassSignature cachedGenericClassTypeSignature;
   public static final String UNKNOWN_SOURCE_FILE = "<Unknown>";

   public AbstractReferenceTypeDelegate(ReferenceType resolvedTypeX, boolean exposedToWeaver) {
      this.sourceContext = SourceContextImpl.UNKNOWN_SOURCE_CONTEXT;
      this.resolvedTypeX = resolvedTypeX;
      this.exposedToWeaver = exposedToWeaver;
   }

   public final boolean isClass() {
      return !this.isAspect() && !this.isInterface();
   }

   public boolean isCacheable() {
      return false;
   }

   public boolean doesNotExposeShadowMungers() {
      return false;
   }

   public boolean isExposedToWeaver() {
      return this.exposedToWeaver;
   }

   public ReferenceType getResolvedTypeX() {
      return this.resolvedTypeX;
   }

   public final String getSourcefilename() {
      return this.sourcefilename;
   }

   public final void setSourcefilename(String sourceFileName) {
      this.sourcefilename = sourceFileName;
      if (sourceFileName != null && sourceFileName.equals("<Unknown>")) {
         this.sourcefilename = "Type '" + this.getResolvedTypeX().getName() + "' (no debug info available)";
      } else {
         String pname = this.getResolvedTypeX().getPackageName();
         if (pname != null) {
            this.sourcefilename = pname.replace('.', '/') + '/' + sourceFileName;
         }
      }

      if (this.sourcefilename != null && this.sourceContext instanceof SourceContextImpl) {
         ((SourceContextImpl)this.sourceContext).setSourceFileName(this.sourcefilename);
      }

   }

   public ISourceLocation getSourceLocation() {
      return this.getSourceContext().makeSourceLocation(0, 0);
   }

   public ISourceContext getSourceContext() {
      return this.sourceContext;
   }

   public void setSourceContext(ISourceContext isc) {
      this.sourceContext = isc;
   }

   public GenericSignature.ClassSignature getGenericClassTypeSignature() {
      if (this.cachedGenericClassTypeSignature == null) {
         String sig = this.getDeclaredGenericSignature();
         if (sig != null) {
            GenericSignatureParser parser = new GenericSignatureParser();
            this.cachedGenericClassTypeSignature = parser.parseAsClassSignature(sig);
         }
      }

      return this.cachedGenericClassTypeSignature;
   }

   protected GenericSignature.FormalTypeParameter[] getFormalTypeParametersFromOuterClass() {
      List typeParameters = new ArrayList();
      ResolvedType outerClassType = this.getOuterClass();
      if (!(outerClassType instanceof ReferenceType)) {
         if (outerClassType == null) {
            return GenericSignature.FormalTypeParameter.NONE;
         } else {
            throw new BCException("Whilst processing type '" + this.resolvedTypeX.getSignature() + "' - cannot cast the outer type to a reference type.  Signature=" + outerClassType.getSignature() + " toString()=" + outerClassType.toString() + " class=" + outerClassType.getClassName());
         }
      } else {
         ReferenceType outer = (ReferenceType)outerClassType;
         ReferenceTypeDelegate outerDelegate = outer.getDelegate();
         AbstractReferenceTypeDelegate outerObjectType = (AbstractReferenceTypeDelegate)outerDelegate;
         int i;
         if (outerObjectType.isNested()) {
            GenericSignature.FormalTypeParameter[] parentParams = outerObjectType.getFormalTypeParametersFromOuterClass();

            for(i = 0; i < parentParams.length; ++i) {
               typeParameters.add(parentParams[i]);
            }
         }

         GenericSignature.ClassSignature outerSig = outerObjectType.getGenericClassTypeSignature();
         if (outerSig != null) {
            for(i = 0; i < outerSig.formalTypeParameters.length; ++i) {
               typeParameters.add(outerSig.formalTypeParameters[i]);
            }
         }

         GenericSignature.FormalTypeParameter[] ret = new GenericSignature.FormalTypeParameter[typeParameters.size()];
         typeParameters.toArray(ret);
         return ret;
      }
   }

   public boolean copySourceContext() {
      return true;
   }

   public int getCompilerVersion() {
      return AjAttribute.WeaverVersionInfo.getCurrentWeaverMajorVersion();
   }

   public void ensureConsistent() {
   }

   public boolean isWeavable() {
      return false;
   }

   public boolean hasBeenWoven() {
      return false;
   }
}
