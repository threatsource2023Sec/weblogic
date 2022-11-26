package com.sun.faces.application.resource;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.config.WebConfiguration;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.faces.FacesException;
import javax.faces.application.ResourceVisitOption;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.flow.Flow;

public class FaceletWebappResourceHelper extends ResourceHelper {
   private static final String[] RESTRICTED_DIRECTORIES = new String[]{"/WEB-INF/", "/META-INF/"};
   private final String webAppContractsDirectory;
   private final String[] configuredExtensions;

   public FaceletWebappResourceHelper() {
      WebConfiguration webConfig = WebConfiguration.getInstance();
      this.webAppContractsDirectory = webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.WebAppContractsDirectory);
      this.configuredExtensions = webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.FaceletsSuffix, " ");
   }

   public boolean equals(Object obj) {
      return obj instanceof FaceletWebappResourceHelper;
   }

   public int hashCode() {
      return 3;
   }

   public LibraryInfo findLibrary(String libraryName, String localePrefix, String contract, FacesContext ctx) {
      return null;
   }

   public ResourceInfo findResource(LibraryInfo library, String resourceName, String localePrefix, boolean compressable, FacesContext ctx) {
      if (localePrefix != null) {
         return null;
      } else {
         FaceletResourceInfo result = null;

         try {
            List contracts = ctx.getResourceLibraryContracts();
            ContractInfo[] outContract = new ContractInfo[1];
            boolean[] outDoNotCache = new boolean[1];
            URL url = null;
            if (library == null && !contracts.isEmpty()) {
               url = this.findResourceInfoConsideringContracts(ctx, resourceName, outContract, contracts);
            }

            if (url == null) {
               url = Resource.getResourceUrl(ctx, this.createPath(library, resourceName));
            }

            if (url == null) {
               url = this.findResourceUrlConsideringFlows(resourceName, outDoNotCache);
            }

            if (url != null) {
               result = new FaceletResourceInfo(outContract[0], resourceName, (VersionInfo)null, this, url);
               result.setDoNotCache(outDoNotCache[0]);
            }

            return result;
         } catch (IOException var11) {
            throw new FacesException(var11);
         }
      }
   }

   public Stream getViewResources(FacesContext facesContext, String path, int maxDepth, ResourceVisitOption... options) {
      return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new ResourcePathsIterator(path, maxDepth, this.configuredExtensions, getRestrictedDirectories(options), facesContext.getExternalContext()), 1), false);
   }

   private static String[] getRestrictedDirectories(ResourceVisitOption... options) {
      ResourceVisitOption[] var1 = options;
      int var2 = options.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         ResourceVisitOption option = var1[var3];
         if (option == ResourceVisitOption.TOP_LEVEL_VIEWS_ONLY) {
            return RESTRICTED_DIRECTORIES;
         }
      }

      return null;
   }

   private String createPath(LibraryInfo library, String resourceName) {
      String path = resourceName;
      if (library != null) {
         path = library.getPath() + "/" + resourceName;
      } else if (resourceName.charAt(0) != '/') {
         path = "/" + resourceName;
      }

      return path;
   }

   private URL findResourceInfoConsideringContracts(FacesContext ctx, String baseResourceName, ContractInfo[] outContract, List contracts) throws MalformedURLException {
      URL url = null;
      Iterator var7 = contracts.iterator();

      while(var7.hasNext()) {
         String contract = (String)var7.next();
         String resourceName;
         if (baseResourceName.startsWith("/")) {
            resourceName = this.webAppContractsDirectory + "/" + contract + baseResourceName;
         } else {
            resourceName = this.webAppContractsDirectory + "/" + contract + "/" + baseResourceName;
         }

         url = Resource.getResourceUrl(ctx, resourceName);
         if (url != null) {
            outContract[0] = new ContractInfo(contract);
            break;
         }

         if (baseResourceName.startsWith("/")) {
            resourceName = WebConfiguration.META_INF_CONTRACTS_DIR + "/" + contract + baseResourceName;
         } else {
            resourceName = WebConfiguration.META_INF_CONTRACTS_DIR + "/" + contract + "/" + baseResourceName;
         }

         url = Util.getCurrentLoader(this).getResource(resourceName);
         if (url != null) {
            outContract[0] = new ContractInfo(contract);
            break;
         }
      }

      return url;
   }

   private URL findResourceUrlConsideringFlows(String resourceName, boolean[] outDoNotCache) throws IOException {
      URL url = null;
      ClassLoader cl = Util.getCurrentLoader(this);
      Enumeration matches = cl.getResources("META-INF/flows" + resourceName);

      try {
         url = (URL)matches.nextElement();
      } catch (NoSuchElementException var12) {
         url = null;
      }

      if (url != null && matches.hasMoreElements()) {
         boolean keepGoing = true;
         FacesContext context = FacesContext.getCurrentInstance();
         Flow currentFlow = context.getApplication().getFlowHandler().getCurrentFlow(context);

         do {
            if (currentFlow != null && 0 < currentFlow.getDefiningDocumentId().length()) {
               String definingDocumentId = currentFlow.getDefiningDocumentId();
               ExternalContext extContext = context.getExternalContext();
               ApplicationAssociate associate = ApplicationAssociate.getInstance(extContext);
               if (associate.urlIsRelatedToDefiningDocumentInJar(url, definingDocumentId)) {
                  keepGoing = false;
                  outDoNotCache[0] = true;
               } else if (matches.hasMoreElements()) {
                  url = (URL)matches.nextElement();
               } else {
                  keepGoing = false;
               }
            } else {
               keepGoing = false;
            }
         } while(keepGoing);
      }

      return url;
   }

   public String getBaseResourcePath() {
      return "";
   }

   public String getBaseContractsPath() {
      return this.webAppContractsDirectory;
   }

   protected InputStream getNonCompressedInputStream(ResourceInfo info, FacesContext ctx) throws IOException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public URL getURL(ResourceInfo resource, FacesContext ctx) {
      return ((FaceletResourceInfo)resource).getUrl();
   }
}
