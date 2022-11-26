package weblogic.xml.registry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.ConnectionSigner;
import weblogic.management.utils.ManagementHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.AssertionError;

public final class XMLRegistryDir {
   private static final boolean verbose = true;
   private boolean localDir = false;
   private String registryName;
   private URL adminFileServletURL;
   private static AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public XMLRegistryDir(String regName) {
      if (!ManagementService.getRuntimeAccess(KERNEL_ID).isAdminServer() || !ManagementService.getRuntimeAccess(KERNEL_ID).isAdminServerAvailable()) {
         try {
            this.adminFileServletURL = ManagementHelper.getURL();
         } catch (MalformedURLException var3) {
            throw new AssertionError(var3);
         }
      }

      this.registryName = regName;
   }

   public InputStream getEntity(String entityPath) throws XMLRegistryException {
      return this.isLocal() ? this.getLocalEntity(entityPath) : this.getRemoteEntity(entityPath);
   }

   boolean isLocal() {
      return this.adminFileServletURL == null;
   }

   private InputStream getLocalEntity(String entityPath) throws XMLRegistryException {
      DomainMBean domain = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain();
      String registryDirsPath = domain.getRootDirectory();
      File registryDir = new File(registryDirsPath, "xml/registries/" + this.registryName);
      if (!registryDir.exists()) {
         throw new XMLRegistryException("XML Registry directory " + registryDir.getAbsolutePath() + " not found");
      } else {
         File entityFile = new File(entityPath);
         if (!entityFile.isAbsolute()) {
            entityFile = new File(registryDir, entityPath);
         }

         if (!entityFile.exists()) {
            throw new XMLRegistryException("Entity " + entityPath + " not found in registry entity directory " + registryDir);
         } else {
            try {
               return new FileInputStream(entityFile);
            } catch (FileNotFoundException var7) {
               throw new AssertionError();
            }
         }
      }
   }

   private InputStream getRemoteEntity(String entityPath) throws XMLRegistryException {
      try {
         URLConnection co = this.adminFileServletURL.openConnection();
         HttpURLConnection conn = (HttpURLConnection)co;
         ConnectionSigner.signConnection(co, KERNEL_ID);
         conn.setRequestProperty("wl_request_type", "wl_xml_entity_request");
         conn.setRequestProperty("xml-registry-name", this.registryName);
         conn.setRequestProperty("xml-entity-path", entityPath);
         conn.setRequestProperty("Connection", "Close");
         return conn.getInputStream();
      } catch (IOException var4) {
         throw new XMLRegistryRemoteAccessException("Unable to open url: " + this.adminFileServletURL.toString(), var4);
      }
   }
}
