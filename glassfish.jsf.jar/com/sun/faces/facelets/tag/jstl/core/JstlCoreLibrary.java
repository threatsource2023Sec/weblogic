package com.sun.faces.facelets.tag.jstl.core;

import com.sun.faces.facelets.tag.AbstractTagLibrary;

public final class JstlCoreLibrary extends AbstractTagLibrary {
   public static final String Namespace = "http://java.sun.com/jsp/jstl/core";
   public static final String IncorrectNamespace = "http://java.sun.com/jstl/core";
   public static final String XMLNSNamespace = "http://xmlns.jcp.org/jsp/jstl/core";

   public JstlCoreLibrary() {
      super("http://java.sun.com/jsp/jstl/core");
      this.addTagHandler("if", IfHandler.class);
      this.addTagHandler("forEach", ForEachHandler.class);
      this.addTagHandler("catch", CatchHandler.class);
      this.addTagHandler("choose", ChooseHandler.class);
      this.addTagHandler("when", ChooseWhenHandler.class);
      this.addTagHandler("otherwise", ChooseOtherwiseHandler.class);
      this.addTagHandler("set", SetHandler.class);
   }

   public JstlCoreLibrary(String namespace) {
      super(namespace);
      this.addTagHandler("if", IfHandler.class);
      this.addTagHandler("forEach", ForEachHandler.class);
      this.addTagHandler("catch", CatchHandler.class);
      this.addTagHandler("choose", ChooseHandler.class);
      this.addTagHandler("when", ChooseWhenHandler.class);
      this.addTagHandler("otherwise", ChooseOtherwiseHandler.class);
      this.addTagHandler("set", SetHandler.class);
   }
}
