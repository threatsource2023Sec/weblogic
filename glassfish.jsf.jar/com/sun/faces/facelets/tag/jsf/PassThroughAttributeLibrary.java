package com.sun.faces.facelets.tag.jsf;

import com.sun.faces.facelets.tag.AbstractTagLibrary;

public final class PassThroughAttributeLibrary extends AbstractTagLibrary {
   public static final String Namespace = "http://xmlns.jcp.org/jsf/passthrough";
   public static final PassThroughAttributeLibrary Instance = new PassThroughAttributeLibrary();

   public PassThroughAttributeLibrary() {
      super("http://xmlns.jcp.org/jsf/passthrough");
   }
}
