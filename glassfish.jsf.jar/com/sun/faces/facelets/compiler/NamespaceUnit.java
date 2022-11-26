package com.sun.faces.facelets.compiler;

import com.sun.faces.facelets.tag.TagLibrary;
import java.util.HashMap;
import java.util.Map;
import javax.faces.view.facelets.FaceletHandler;

final class NamespaceUnit extends CompilationUnit {
   private final Map ns = new HashMap();
   private final TagLibrary library;

   public NamespaceUnit(TagLibrary library) {
      this.library = library;
   }

   public FaceletHandler createFaceletHandler() {
      FaceletHandler next = this.getNextFaceletHandler();
      return new NamespaceHandler(next, this.library, this.ns);
   }

   public void setNamespace(String prefix, String uri) {
      this.ns.put(prefix, uri);
   }

   public void addChild(CompilationUnit unit) {
      super.addChild(unit);
   }
}
