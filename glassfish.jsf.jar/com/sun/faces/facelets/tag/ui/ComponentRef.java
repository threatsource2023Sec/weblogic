package com.sun.faces.facelets.tag.ui;

import javax.faces.component.UIComponentBase;

public final class ComponentRef extends UIComponentBase {
   public static final String COMPONENT_TYPE = "facelets.ui.ComponentRef";
   public static final String COMPONENT_FAMILY = "facelets";

   public String getFamily() {
      return "facelets";
   }
}
