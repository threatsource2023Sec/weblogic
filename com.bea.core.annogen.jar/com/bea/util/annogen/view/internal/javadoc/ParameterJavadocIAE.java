package com.bea.util.annogen.view.internal.javadoc;

import com.bea.util.annogen.override.AnnoBeanSet;
import com.bea.util.annogen.view.internal.IndigenousAnnoExtractor;
import com.bea.util.annogen.view.internal.NullIAE;
import com.sun.javadoc.ExecutableMemberDoc;

public final class ParameterJavadocIAE implements IndigenousAnnoExtractor {
   private ExecutableMemberDoc mEMD;
   private int mParamNum;
   private JavadocAnnogenTigerDelegate mTigerDelegate;

   public static IndigenousAnnoExtractor create(ExecutableMemberDoc emd, int paramNum, JavadocAnnogenTigerDelegate tiger) {
      return (IndigenousAnnoExtractor)(tiger == null ? NullIAE.getInstance() : new ParameterJavadocIAE(emd, paramNum, tiger));
   }

   private ParameterJavadocIAE(ExecutableMemberDoc emd, int paramNum, JavadocAnnogenTigerDelegate tiger) {
      if (emd == null) {
         throw new IllegalArgumentException("null ped");
      } else if (tiger == null) {
         throw new IllegalArgumentException("null tiger");
      } else if (paramNum < 0) {
         throw new IllegalArgumentException("invalid paramNum " + paramNum);
      } else {
         this.mTigerDelegate = tiger;
         this.mEMD = emd;
         this.mParamNum = paramNum;
      }
   }

   public boolean extractIndigenousAnnotations(AnnoBeanSet out) {
      return this.mTigerDelegate.extractAnnotations(out, this.mEMD, this.mParamNum);
   }
}
