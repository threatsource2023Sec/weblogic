package weblogic.diagnostics.context;

import weblogic.workarea.WorkContext;

public interface DiagnosticContext extends WorkContext, DiagnosticContextConstants {
   String DIAGNOSTIC_CONTEXT_NAME = "weblogic.diagnostics.DiagnosticContext";
   int DEFAULT_LOG_LEVEL = -1;

   String getContextId();

   String getRID();

   int getLogLevel();

   void setLogLevel(int var1);

   void setDye(byte var1, boolean var2) throws InvalidDyeException;

   boolean isDyedWith(byte var1) throws InvalidDyeException;

   void setDyeVector(long var1);

   long getDyeVector();

   /** @deprecated */
   @Deprecated
   String getPayload();

   /** @deprecated */
   @Deprecated
   void setPayload(String var1);

   boolean isUnmarshalled();
}
