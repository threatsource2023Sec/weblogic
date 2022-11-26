package com.bea.util.annogen.view.internal.javadoc;

import com.bea.util.annogen.override.AnnoBeanSet;
import com.bea.util.annogen.view.internal.IndigenousAnnoExtractor;
import com.bea.util.annogen.view.internal.NullIAE;
import com.sun.javadoc.ProgramElementDoc;

public final class ProgramElementJavadocIAE implements IndigenousAnnoExtractor {
   private ProgramElementDoc mPed;
   private JavadocAnnogenTigerDelegate mTigerDelegate;

   public static IndigenousAnnoExtractor create(ProgramElementDoc ped, JavadocAnnogenTigerDelegate tiger) {
      return (IndigenousAnnoExtractor)(tiger == null ? NullIAE.getInstance() : new ProgramElementJavadocIAE(ped, tiger));
   }

   private ProgramElementJavadocIAE(ProgramElementDoc ped, JavadocAnnogenTigerDelegate tiger) {
      if (ped == null) {
         throw new IllegalArgumentException("null ped");
      } else if (tiger == null) {
         throw new IllegalArgumentException("null tiger");
      } else {
         this.mTigerDelegate = tiger;
         this.mPed = ped;
      }
   }

   public boolean extractIndigenousAnnotations(AnnoBeanSet out) {
      return this.mTigerDelegate.extractAnnotations(out, this.mPed);
   }
}
