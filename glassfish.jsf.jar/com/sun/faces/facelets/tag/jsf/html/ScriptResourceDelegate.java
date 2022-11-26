package com.sun.faces.facelets.tag.jsf.html;

import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;

public class ScriptResourceDelegate extends ComponentResourceDelegate {
   public ScriptResourceDelegate(ComponentHandler owner) {
      super(owner);
   }

   protected String getLocationTarget(FaceletContext ctx) {
      TagAttribute attr = this.getAttribute("target");
      return attr != null ? attr.getValue(ctx) : null;
   }
}
