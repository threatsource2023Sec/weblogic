package com.sun.faces.facelets.tag.composite;

import javax.faces.view.facelets.TagConfig;

public class EditableValueHolderAttachedObjectTargetHandler extends AttachedObjectTargetHandler {
   public EditableValueHolderAttachedObjectTargetHandler(TagConfig config) {
      super(config);
   }

   AttachedObjectTargetImpl newAttachedObjectTargetImpl() {
      return new EditableValueHolderAttachedObjectTargetImpl();
   }
}
