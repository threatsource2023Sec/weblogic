package com.sun.faces.facelets.tag.jsf.html;

import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.MetaRuleset;

public class HtmlComponentHandler extends ComponentHandler {
   public HtmlComponentHandler(ComponentConfig config) {
      super(config);
   }

   protected MetaRuleset createMetaRuleset(Class type) {
      return super.createMetaRuleset(type).alias("class", "styleClass");
   }
}
