package com.bea.util.annogen.override.internal;

import com.bea.util.annogen.override.AnnoBean;
import com.bea.util.annogen.override.AnnoBeanSet;
import com.bea.util.annogen.override.AnnoContext;
import com.bea.util.annogen.override.ElementId;
import com.bea.util.annogen.override.StoredAnnoOverrider;
import java.util.HashMap;
import java.util.Map;

public class StoredAnnoOverriderImpl implements StoredAnnoOverrider {
   private Map mId2APS = new HashMap();
   private AnnoContext mContext;

   public StoredAnnoOverriderImpl(AnnoContext ctx) {
      if (ctx == null) {
         throw new IllegalArgumentException("null ctx");
      } else {
         this.mContext = ctx;
      }
   }

   public StoredAnnoOverriderImpl() {
      this.mContext = AnnoContext.Factory.newInstance();
   }

   public AnnoBeanSet findOrCreateStoredAnnoSetFor(ElementId id) {
      AnnoBeanSet out = (AnnoBeanSet)this.mId2APS.get(id);
      if (out == null) {
         out = new AnnoBeanSetImpl(this.mContext);
         this.mId2APS.put(id, out);
      }

      return (AnnoBeanSet)out;
   }

   public void init(AnnoContext ctx) {
      this.mContext = ctx;
   }

   public void modifyAnnos(ElementId id, AnnoBeanSet currentAnnos) {
      AnnoBeanSet stored = (AnnoBeanSet)this.mId2APS.get(id);
      if (stored != null) {
         AnnoBean[] proxies = stored.getAll();
         if (proxies != null && proxies.length != 0) {
            for(int i = 0; i < proxies.length; ++i) {
               currentAnnos.put(proxies[i]);
            }

         }
      }
   }
}
