package com.sun.faces.facelets.tag.composite;

import javax.faces.view.facelets.TagConfig;

public class ActionSource2AttachedObjectTargetHandler extends AttachedObjectTargetHandler {
   public ActionSource2AttachedObjectTargetHandler(TagConfig config) {
      super(config);
   }

   AttachedObjectTargetImpl newAttachedObjectTargetImpl() {
      return new ActionSource2AttachedObjectTargetImpl();
   }
}
