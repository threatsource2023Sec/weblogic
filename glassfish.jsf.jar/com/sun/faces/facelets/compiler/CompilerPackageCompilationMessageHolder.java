package com.sun.faces.facelets.compiler;

interface CompilerPackageCompilationMessageHolder extends CompilationMessageHolder {
   CompilationManager getCurrentCompositeComponentCompilationManager();

   void setCurrentCompositeComponentCompilationManager(CompilationManager var1);
}
