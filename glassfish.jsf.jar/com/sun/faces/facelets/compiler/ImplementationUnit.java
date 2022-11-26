package com.sun.faces.facelets.compiler;

import com.sun.faces.facelets.tag.TagLibrary;
import javax.faces.view.facelets.Tag;

class ImplementationUnit extends TrimmedTagUnit {
   public ImplementationUnit(TagLibrary library, String namespace, String name, Tag tag, String id) {
      super(library, namespace, name, tag, id);
   }

   protected void finishNotify(CompilationManager manager) {
      ((CompilerPackageCompilationMessageHolder)manager.getCompilationMessageHolder()).setCurrentCompositeComponentCompilationManager((CompilationManager)null);
      super.finishNotify(manager);
   }

   protected void startNotify(CompilationManager manager) {
      super.startNotify(manager);
      ((CompilerPackageCompilationMessageHolder)manager.getCompilationMessageHolder()).setCurrentCompositeComponentCompilationManager(manager);
   }
}
