package com.sun.faces.facelets.tag;

import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.Metadata;

final class MetadataImpl extends Metadata {
   private final Metadata[] mappers;
   private final int size;

   public MetadataImpl(Metadata[] mappers) {
      this.mappers = mappers;
      this.size = mappers.length;
   }

   public void applyMetadata(FaceletContext ctx, Object instance) {
      for(int i = 0; i < this.size; ++i) {
         this.mappers[i].applyMetadata(ctx, instance);
      }

   }
}
