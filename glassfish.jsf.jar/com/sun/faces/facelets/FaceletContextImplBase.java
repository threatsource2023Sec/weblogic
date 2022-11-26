package com.sun.faces.facelets;

import java.io.IOException;
import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.FaceletException;

public abstract class FaceletContextImplBase extends FaceletContext {
   public abstract void pushClient(TemplateClient var1);

   public abstract void popClient(TemplateClient var1);

   public abstract void extendClient(TemplateClient var1);

   public abstract boolean includeDefinition(UIComponent var1, String var2) throws IOException, FaceletException, FacesException, ELException;
}
