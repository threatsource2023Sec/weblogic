package weblogic.diagnostics.runtimecontrol.internal;

import weblogic.diagnostics.descriptor.WLDFResourceBean;

public interface WLDFResourceRegistrationHandler {
   void registerWLDFResource(String var1, WLDFResourceBean var2);

   void unregisterWLDFResource(String var1, WLDFResourceBean var2);
}
