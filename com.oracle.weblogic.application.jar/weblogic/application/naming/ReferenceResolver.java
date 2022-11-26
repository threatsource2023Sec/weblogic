package weblogic.application.naming;

import weblogic.application.ApplicationContextInternal;
import weblogic.application.compiler.ToolsContext;

public interface ReferenceResolver {
   Object get();

   void resolve(ApplicationContextInternal var1) throws ReferenceResolutionException;

   void resolve(ToolsContext var1) throws ReferenceResolutionException;
}
