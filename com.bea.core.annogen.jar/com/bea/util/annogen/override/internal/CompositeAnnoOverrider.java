package com.bea.util.annogen.override.internal;

import com.bea.util.annogen.override.AnnoBeanSet;
import com.bea.util.annogen.override.AnnoContext;
import com.bea.util.annogen.override.AnnoOverrider;
import com.bea.util.annogen.override.ElementId;

public class CompositeAnnoOverrider implements AnnoOverrider {
   private AnnoOverrider[] mPops;

   public CompositeAnnoOverrider(AnnoOverrider[] pops) {
      if (pops == null) {
         throw new IllegalArgumentException("null pops");
      } else {
         this.mPops = pops;
      }
   }

   public void init(AnnoContext pc) {
      for(int i = 0; i < this.mPops.length; ++i) {
         this.mPops[i].init(pc);
      }

   }

   public void modifyAnnos(ElementId id, AnnoBeanSet currentAnnos) {
      for(int i = 0; i < this.mPops.length; ++i) {
         this.mPops[i].modifyAnnos(id, currentAnnos);
      }

   }
}
