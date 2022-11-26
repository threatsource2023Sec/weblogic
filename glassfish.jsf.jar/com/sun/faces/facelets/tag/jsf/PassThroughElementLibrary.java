package com.sun.faces.facelets.tag.jsf;

import com.sun.faces.facelets.tag.AbstractTagLibrary;

public final class PassThroughElementLibrary extends AbstractTagLibrary {
   public static final String Namespace = "http://xmlns.jcp.org/jsf";
   public static final PassThroughElementLibrary Instance = new PassThroughElementLibrary();

   public PassThroughElementLibrary() {
      super("http://xmlns.jcp.org/jsf");
      this.addComponent("element", "javax.faces.Panel", "javax.faces.passthrough.Element", PassThroughElementComponentHandler.class);
   }
}
