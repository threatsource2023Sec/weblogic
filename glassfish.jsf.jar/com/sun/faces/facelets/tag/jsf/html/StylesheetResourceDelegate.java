package com.sun.faces.facelets.tag.jsf.html;

import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;

public class StylesheetResourceDelegate extends ComponentResourceDelegate {
   public StylesheetResourceDelegate(ComponentHandler owner) {
      super(owner);
   }

   protected String getLocationTarget(FaceletContext ctx) {
      return "head";
   }
}
