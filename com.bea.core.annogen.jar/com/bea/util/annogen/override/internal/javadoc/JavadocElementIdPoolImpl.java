package com.bea.util.annogen.override.internal.javadoc;

import com.bea.util.annogen.override.ElementId;
import com.bea.util.annogen.override.JavadocElementIdPool;
import com.bea.util.annogen.override.internal.ElementIdImpl;
import com.bea.util.annogen.view.internal.IndigenousAnnoExtractor;
import com.bea.util.annogen.view.internal.javadoc.JavadocAnnogenTigerDelegate;
import com.bea.util.annogen.view.internal.javadoc.ParameterJavadocIAE;
import com.bea.util.annogen.view.internal.javadoc.ProgramElementJavadocIAE;
import com.bea.util.jam.provider.JamLogger;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.ConstructorDoc;
import com.sun.javadoc.ExecutableMemberDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.ProgramElementDoc;

public class JavadocElementIdPoolImpl implements JavadocElementIdPool {
   private JavadocAnnogenTigerDelegate mJTiger;

   public JavadocElementIdPoolImpl(JamLogger logger) {
      this.mJTiger = JavadocAnnogenTigerDelegate.create(logger);
   }

   public ElementId getIdFor(ProgramElementDoc ped) {
      if (ped == null) {
         throw new IllegalArgumentException("null ped");
      } else {
         IndigenousAnnoExtractor iae = ProgramElementJavadocIAE.create(ped, this.mJTiger);
         if (ped instanceof PackageDoc) {
            return ElementIdImpl.forPackage(iae, ped.name());
         } else if (ped instanceof ClassDoc) {
            return ElementIdImpl.forClass(iae, ped.name());
         } else if (ped instanceof FieldDoc) {
            return ElementIdImpl.forField(iae, ped.containingClass().name(), ped.name());
         } else if (ped instanceof ConstructorDoc) {
            return ElementIdImpl.forConstructor(iae, ped.containingClass().name(), this.getSignature((ConstructorDoc)ped));
         } else if (ped instanceof MethodDoc) {
            return ElementIdImpl.forMethod(iae, ped.containingClass().name(), ped.name(), this.getSignature((MethodDoc)ped));
         } else {
            throw new IllegalStateException(ped.getClass().getName());
         }
      }
   }

   public ElementId getIdFor(ExecutableMemberDoc emd, int paramNum) {
      if (emd == null) {
         throw new IllegalArgumentException("null emd");
      } else {
         IndigenousAnnoExtractor iae = ParameterJavadocIAE.create(emd, paramNum, this.mJTiger);
         return ElementIdImpl.forParameter(iae, emd.containingClass().name(), emd.name(), this.getSignature(emd), paramNum);
      }
   }

   private String[] getSignature(ExecutableMemberDoc emd) {
      if (emd == null) {
         throw new IllegalArgumentException("null emd");
      } else {
         Parameter[] params = emd.parameters();
         if (params != null && params.length != 0) {
            String[] out = new String[params.length];

            for(int i = 0; i < out.length; ++i) {
               out[i] = params[i].name();
            }

            return out;
         } else {
            return new String[0];
         }
      }
   }
}
