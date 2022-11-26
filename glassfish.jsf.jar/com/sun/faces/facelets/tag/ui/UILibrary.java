package com.sun.faces.facelets.tag.ui;

import com.sun.faces.facelets.tag.AbstractTagLibrary;

public final class UILibrary extends AbstractTagLibrary {
   public static final String Namespace = "http://java.sun.com/jsf/facelets";
   public static final String XMLNSNamespace = "http://xmlns.jcp.org/jsf/facelets";
   public static final UILibrary Instance = new UILibrary();

   public UILibrary() {
      this("http://java.sun.com/jsf/facelets");
   }

   public UILibrary(String namespace) {
      super(namespace);
      this.addTagHandler("include", IncludeHandler.class);
      this.addTagHandler("composition", CompositionHandler.class);
      this.addComponent("component", "facelets.ui.ComponentRef", (String)null, ComponentRefHandler.class);
      this.addComponent("fragment", "facelets.ui.ComponentRef", (String)null, ComponentRefHandler.class);
      this.addTagHandler("define", DefineHandler.class);
      this.addTagHandler("insert", InsertHandler.class);
      this.addTagHandler("param", ParamHandler.class);
      this.addTagHandler("decorate", DecorateHandler.class);
      this.addComponent("repeat", "facelets.ui.Repeat", (String)null, RepeatHandler.class);
      this.addComponent("debug", "facelets.ui.Debug", (String)null);
   }
}
