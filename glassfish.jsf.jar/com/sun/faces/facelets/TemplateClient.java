package com.sun.faces.facelets;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;

public interface TemplateClient {
   boolean apply(FaceletContext var1, UIComponent var2, String var3) throws IOException;
}
