package com.sun.faces.facelets.compiler;

import com.sun.faces.facelets.tag.jsf.ComponentSupport;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.FaceletHandler;
import javax.faces.view.facelets.TextHandler;

public abstract class AbstractUIHandler implements FaceletHandler, TextHandler {
   public void addComponent(FaceletContext ctx, UIComponent parent, UIComponent c) {
      ComponentSupport.addComponent(ctx, parent, c);
   }
}
