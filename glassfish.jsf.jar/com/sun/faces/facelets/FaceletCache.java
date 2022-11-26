package com.sun.faces.facelets;

import java.io.IOException;
import java.net.URL;

/** @deprecated */
@Deprecated
public abstract class FaceletCache {
   private InstanceFactory _faceletFactory;
   private InstanceFactory _metafaceletFactory;

   public abstract Object getFacelet(URL var1) throws IOException;

   public abstract boolean isFaceletCached(URL var1);

   public abstract Object getMetadataFacelet(URL var1) throws IOException;

   public abstract boolean isMetadataFaceletCached(URL var1);

   public final void init(InstanceFactory faceletFactory, InstanceFactory metafaceletFactory) {
      this._faceletFactory = faceletFactory;
      this._metafaceletFactory = metafaceletFactory;
   }

   protected final InstanceFactory getFaceletInstanceFactory() {
      return this._faceletFactory;
   }

   protected final InstanceFactory getMetadataFaceletInstanceFactory() {
      return this._metafaceletFactory;
   }

   /** @deprecated */
   @Deprecated
   public interface InstanceFactory {
      Object newInstance(URL var1) throws IOException;
   }
}
