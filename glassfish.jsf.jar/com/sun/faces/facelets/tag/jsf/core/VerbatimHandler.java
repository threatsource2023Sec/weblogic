package com.sun.faces.facelets.tag.jsf.core;

import com.sun.faces.facelets.tag.TagHandlerImpl;
import java.util.Iterator;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TextHandler;

public final class VerbatimHandler extends ComponentHandler {
   public VerbatimHandler(ComponentConfig config) {
      super(config);
   }

   public void onComponentCreated(FaceletContext ctx, UIComponent c, UIComponent parent) {
      StringBuffer content = new StringBuffer();
      Iterator iter = TagHandlerImpl.findNextByType(this.nextHandler, TextHandler.class);

      while(iter.hasNext()) {
         TextHandler text = (TextHandler)iter.next();
         content.append(text.getText(ctx));
      }

      c.getAttributes().put("value", content.toString());
      c.getAttributes().put("escape", Boolean.FALSE);
      c.setTransient(true);
   }

   public void applyNextHandler(FaceletContext ctx, UIComponent c) {
   }
}
