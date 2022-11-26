package com.sun.faces.facelets.compiler;

import javax.faces.view.facelets.FaceletHandler;

final class RemoveUnit extends CompilationUnit {
   public RemoveUnit() {
   }

   public void addChild(CompilationUnit unit) {
   }

   public FaceletHandler createFaceletHandler() {
      return LEAF;
   }
}
