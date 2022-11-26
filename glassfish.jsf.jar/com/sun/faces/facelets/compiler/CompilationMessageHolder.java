package com.sun.faces.facelets.compiler;

import java.util.List;
import javax.faces.context.FacesContext;

public interface CompilationMessageHolder {
   List getNamespacePrefixMessages(FacesContext var1, String var2);

   void removeNamespacePrefixMessages(String var1);

   void processCompilationMessages(FacesContext var1);
}
