package com.bea.util.annogen.view.internal;

import com.bea.util.annogen.override.AnnoBean;
import com.bea.util.annogen.override.AnnoBeanSet;
import com.bea.util.annogen.override.AnnoContext;
import com.bea.util.annogen.override.AnnoOverrider;
import com.bea.util.annogen.override.ElementId;
import com.bea.util.annogen.override.internal.AnnoBeanSetImpl;
import com.bea.util.annogen.override.internal.CompositeAnnoOverrider;
import com.bea.util.annogen.override.internal.ElementIdImpl;
import com.bea.util.jam.provider.JamLogger;

public abstract class AnnoViewerBase {
   private AnnoContext mContext;
   private AnnoOverrider mOverrider;
   protected JamLogger mLogger;

   public AnnoViewerBase(AnnoViewerParamsImpl asp) {
      if (asp == null) {
         throw new IllegalArgumentException("null asp");
      } else {
         AnnoOverrider[] pps = asp.getOverriders();
         if (pps != null && pps.length != 0) {
            if (pps.length == 1) {
               this.mOverrider = pps[0];
            } else {
               this.mOverrider = new CompositeAnnoOverrider(pps);
            }
         } else {
            this.mOverrider = null;
         }

         if (this.mOverrider != null) {
            this.mOverrider.init(asp);
         }

         this.mLogger = asp.getLogger();
         this.mContext = asp;
      }
   }

   public AnnoBean[] getAnnotations(ElementId id) {
      if (id == null) {
         throw new IllegalArgumentException("null id");
      } else {
         AnnoBeanSet apsi = new AnnoBeanSetImpl(this.mContext);
         this.getIndigenousAnnotations(id, apsi);
         if (this.mOverrider != null) {
            this.mOverrider.modifyAnnos(id, apsi);
         }

         return apsi.getAll();
      }
   }

   public AnnoBean getAnnotation(Class what, ElementId where) {
      Class beanClass;
      try {
         beanClass = this.mContext.getAnnobeanClassFor(what);
      } catch (ClassNotFoundException var6) {
         this.mLogger.verbose((Throwable)var6, this);
         return null;
      }

      AnnoBean[] annos = this.getAnnotations(where);

      for(int i = 0; i < annos.length; ++i) {
         if (beanClass.isAssignableFrom(annos[i].getClass())) {
            return annos[i];
         }
      }

      return null;
   }

   private void getIndigenousAnnotations(ElementId id, AnnoBeanSet out) {
      if (id == null) {
         throw new IllegalArgumentException("null id");
      } else if (out == null) {
         throw new IllegalArgumentException("null out");
      } else {
         IndigenousAnnoExtractor iae = ((ElementIdImpl)id).getIAE();
         if (iae == null) {
            throw new IllegalStateException();
         } else {
            iae.extractIndigenousAnnotations(out);
         }
      }
   }
}
