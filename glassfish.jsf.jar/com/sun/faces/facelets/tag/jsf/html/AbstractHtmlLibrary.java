package com.sun.faces.facelets.tag.jsf.html;

import com.sun.faces.facelets.tag.AbstractTagLibrary;

public abstract class AbstractHtmlLibrary extends AbstractTagLibrary {
   public AbstractHtmlLibrary(String namespace) {
      super(namespace);
   }

   public void addHtmlComponent(String name, String componentType, String rendererType) {
      super.addComponent(name, componentType, rendererType, HtmlComponentHandler.class);
   }
}
