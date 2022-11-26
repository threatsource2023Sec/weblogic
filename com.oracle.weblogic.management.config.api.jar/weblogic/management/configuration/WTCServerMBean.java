package weblogic.management.configuration;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InvalidAttributeValueException;

public interface WTCServerMBean extends DeploymentMBean {
   WTCLocalTuxDomMBean[] getWTCLocalTuxDoms();

   WTCLocalTuxDomMBean createWTCLocalTuxDom(String var1);

   void destroyWTCLocalTuxDom(WTCLocalTuxDomMBean var1);

   WTCLocalTuxDomMBean lookupWTCLocalTuxDom(String var1);

   /** @deprecated */
   @Deprecated
   WTCLocalTuxDomMBean[] getLocalTuxDoms();

   WTCRemoteTuxDomMBean[] getWTCRemoteTuxDoms();

   WTCRemoteTuxDomMBean createWTCRemoteTuxDom(String var1);

   void destroyWTCRemoteTuxDom(WTCRemoteTuxDomMBean var1);

   WTCRemoteTuxDomMBean lookupWTCRemoteTuxDom(String var1);

   /** @deprecated */
   @Deprecated
   WTCRemoteTuxDomMBean[] getRemoteTuxDoms();

   WTCExportMBean[] getWTCExports();

   WTCExportMBean createWTCExport(String var1);

   void destroyWTCExport(WTCExportMBean var1);

   WTCExportMBean lookupWTCExport(String var1);

   /** @deprecated */
   @Deprecated
   WTCExportMBean[] getExports();

   WTCImportMBean[] getWTCImports();

   WTCImportMBean createWTCImport(String var1);

   void destroyWTCImport(WTCImportMBean var1);

   WTCImportMBean lookupWTCImport(String var1);

   /** @deprecated */
   @Deprecated
   WTCImportMBean[] getImports();

   WTCPasswordMBean[] getWTCPasswords();

   WTCPasswordMBean createWTCPassword(String var1);

   void destroyWTCPassword(WTCPasswordMBean var1);

   WTCPasswordMBean lookupWTCPassword(String var1);

   /** @deprecated */
   @Deprecated
   WTCPasswordMBean[] getPasswords();

   WTCResourcesMBean getWTCResources();

   /** @deprecated */
   @Deprecated
   WTCResourcesMBean getResources();

   WTCResourcesMBean createWTCResources(String var1) throws InstanceAlreadyExistsException;

   void destroyWTCResources(WTCResourcesMBean var1);

   WTCtBridgeGlobalMBean getWTCtBridgeGlobal();

   WTCtBridgeGlobalMBean createWTCtBridgeGlobal() throws InstanceAlreadyExistsException;

   void destroyWTCtBridgeGlobal();

   /** @deprecated */
   @Deprecated
   WTCResourcesMBean getResource();

   /** @deprecated */
   @Deprecated
   void setResource(WTCResourcesMBean var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   WTCtBridgeGlobalMBean gettBridgeGlobal();

   /** @deprecated */
   @Deprecated
   void settBridgeGlobal(WTCtBridgeGlobalMBean var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   WTCtBridgeRedirectMBean[] gettBridgeRedirects();

   WTCtBridgeRedirectMBean[] getWTCtBridgeRedirects();

   WTCtBridgeRedirectMBean createWTCtBridgeRedirect(String var1);

   void destroyWTCtBridgeRedirect(WTCtBridgeRedirectMBean var1);

   WTCtBridgeRedirectMBean lookupWTCtBridgeRedirect(String var1);
}
