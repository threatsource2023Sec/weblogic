package weblogic.servlet.internal;

import java.io.IOException;

public interface RequestCallback {
   String getIncludeURI();

   void reportJSPTranslationFailure(String var1, String var2) throws IOException;

   void reportJSPCompilationFailure(String var1, String var2) throws IOException;
}
