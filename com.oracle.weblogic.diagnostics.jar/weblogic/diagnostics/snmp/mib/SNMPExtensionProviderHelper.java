package weblogic.diagnostics.snmp.mib;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.jars.ManifestManager;

public final class SNMPExtensionProviderHelper {
   public static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugSNMPExtensionProvider");

   public static List discoverSNMPAgentExtensionProviders() {
      List extensionProviders = new ArrayList();
      Set discoveredImpls = new HashSet();
      ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
      Iterator iterator = ManifestManager.getServices(SNMPExtensionProvider.class, systemClassLoader).iterator();

      while(iterator.hasNext()) {
         SNMPExtensionProvider snmpExtensionProvider = (SNMPExtensionProvider)iterator.next();
         if (snmpExtensionProvider != null) {
            String className = snmpExtensionProvider.getClass().getName();
            if (discoveredImpls.contains(className)) {
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("SNMPExtensionProvider implementation class " + className + " already discovered");
               }
            } else {
               discoveredImpls.add(className);
               extensionProviders.add(snmpExtensionProvider);
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("SNMPExtensionProvider implementation class " + className + " discovered");
               }
            }
         }
      }

      return extensionProviders;
   }
}
