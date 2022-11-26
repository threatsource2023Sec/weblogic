package com.sun.faces.facelets.tag.composite;

import javax.faces.view.facelets.TagConfig;

public class ValueHolderAttachedObjectTargetHandler extends AttachedObjectTargetHandler {
   public ValueHolderAttachedObjectTargetHandler(TagConfig config) {
      super(config);
   }

   AttachedObjectTargetImpl newAttachedObjectTargetImpl() {
      return new ValueHolderAttachedObjectTargetImpl();
   }
}
