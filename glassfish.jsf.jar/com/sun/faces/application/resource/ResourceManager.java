package com.sun.faces.application.resource;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Stream;
import javax.faces.application.ProjectStage;
import javax.faces.application.ResourceVisitOption;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public class ResourceManager {
   private static final Logger LOGGER;
   private static final Pattern CONFIG_MIMETYPE_PATTERN;
   private FaceletWebappResourceHelper faceletWebappResourceHelper = new FaceletWebappResourceHelper();
   private ResourceHelper webappResourceHelper = new WebappResourceHelper();
   private ClasspathResourceHelper classpathResourceHelper = new ClasspathResourceHelper();
   private ResourceCache cache;
   private List compressableTypes;
   private ReentrantLock lock = new ReentrantLock();

   public ResourceManager(ResourceCache cache) {
      this.cache = cache;
      Map throwAwayMap = new HashMap();
      this.initCompressableTypes(throwAwayMap);
   }

   public ResourceManager(Map appMap, ResourceCache cache) {
      this.cache = cache;
      this.initCompressableTypes(appMap);
   }

   public ResourceInfo findResource(String libraryName, String resourceName, String contentType, FacesContext ctx) {
      return this.findResource(libraryName, resourceName, contentType, false, ctx);
   }

   public ResourceInfo findViewResource(String resourceName, String contentType, FacesContext facesContext) {
      String localePrefix = this.getLocalePrefix(facesContext);
      List contracts = this.getResourceLibraryContracts(facesContext);
      ResourceInfo info = this.getFromCache(resourceName, (String)null, localePrefix, contracts);
      if (info == null) {
         if (this.isCompressable(contentType, facesContext)) {
            info = this.findResourceCompressed((String)null, resourceName, true, localePrefix, contracts, facesContext);
         } else {
            info = this.findResourceNonCompressed((String)null, resourceName, true, localePrefix, contracts, facesContext);
         }
      }

      return info;
   }

   public ResourceInfo findResource(String libraryName, String resourceName, String contentType, boolean isViewResource, FacesContext ctx) {
      String localePrefix = this.getLocalePrefix(ctx);
      List contracts = this.getResourceLibraryContracts(ctx);
      ResourceInfo info = this.getFromCache(resourceName, libraryName, localePrefix, contracts);
      if (info == null) {
         if (this.isCompressable(contentType, ctx)) {
            info = this.findResourceCompressed(libraryName, resourceName, isViewResource, localePrefix, contracts, ctx);
         } else {
            info = this.findResourceNonCompressed(libraryName, resourceName, isViewResource, localePrefix, contracts, ctx);
         }
      }

      return info;
   }

   public Stream getViewResources(FacesContext facesContext, String path, int maxDepth, ResourceVisitOption... options) {
      return this.faceletWebappResourceHelper.getViewResources(facesContext, path, maxDepth, options);
   }

   private ResourceInfo findResourceCompressed(String libraryName, String resourceName, boolean isViewResource, String localePrefix, List contracts, FacesContext ctx) {
      ResourceInfo info = null;
      this.lock.lock();

      try {
         info = this.getFromCache(resourceName, libraryName, localePrefix, contracts);
         if (info == null) {
            info = this.doLookup(libraryName, resourceName, localePrefix, true, isViewResource, contracts, ctx);
            if (info != null) {
               this.addToCache(info, contracts);
            }
         }
      } finally {
         this.lock.unlock();
      }

      return info;
   }

   private ResourceInfo findResourceNonCompressed(String libraryName, String resourceName, boolean isViewResource, String localePrefix, List contracts, FacesContext ctx) {
      ResourceInfo info = this.doLookup(libraryName, resourceName, localePrefix, false, isViewResource, contracts, ctx);
      if (info == null && contracts != null) {
         info = this.doLookup(this.libraryNameFromContracts(libraryName, contracts), resourceName, localePrefix, false, isViewResource, contracts, ctx);
      }

      if (info != null && !info.isDoNotCache()) {
         this.addToCache(info, contracts);
      }

      return info;
   }

   private String libraryNameFromContracts(String libraryName, List contracts) {
      Iterator var3 = contracts.iterator();

      String contract;
      do {
         if (!var3.hasNext()) {
            return libraryName;
         }

         contract = (String)var3.next();
      } while(!contract.equals(libraryName));

      return null;
   }

   private ResourceInfo doLookup(String libraryName, String resourceName, String localePrefix, boolean compressable, boolean isViewResource, List contracts, FacesContext ctx) {
      Iterator var8 = contracts.iterator();

      ResourceInfo info;
      do {
         if (!var8.hasNext()) {
            return this.getResourceInfo(libraryName, resourceName, localePrefix, (String)null, compressable, isViewResource, ctx, (LibraryInfo)null);
         }

         String contract = (String)var8.next();
         info = this.getResourceInfo(libraryName, resourceName, localePrefix, contract, compressable, isViewResource, ctx, (LibraryInfo)null);
      } while(info == null);

      return info;
   }

   private ResourceInfo getResourceInfo(String libraryName, String resourceName, String localePrefix, String contract, boolean compressable, boolean isViewResource, FacesContext ctx, LibraryInfo library) {
      if (libraryName != null && !nameContainsForbiddenSequence(libraryName)) {
         library = this.findLibrary(libraryName, localePrefix, contract, ctx);
         if (library == null && localePrefix != null) {
            library = this.findLibrary(libraryName, (String)null, contract, ctx);
         }

         if (library == null) {
            library = this.findLibraryOnClasspathWithZipDirectoryEntryScan(libraryName, localePrefix, contract, ctx, false);
            if (library == null && localePrefix != null) {
               library = this.findLibraryOnClasspathWithZipDirectoryEntryScan(libraryName, (String)null, contract, ctx, false);
            }

            if (library == null) {
               return null;
            }
         }
      } else if (nameContainsForbiddenSequence(libraryName)) {
         return null;
      }

      String resName = this.trimLeadingSlash(resourceName);
      if (!nameContainsForbiddenSequence(resName) && (isViewResource || !resName.startsWith("WEB-INF"))) {
         ResourceInfo info = this.findResource(library, resourceName, localePrefix, compressable, isViewResource, ctx);
         if (info == null && localePrefix != null) {
            info = this.findResource(library, resourceName, (String)null, compressable, isViewResource, ctx);
         }

         if (info == null && library != null && library.getHelper() instanceof WebappResourceHelper) {
            LibraryInfo altLibrary = this.classpathResourceHelper.findLibrary(libraryName, localePrefix, contract, ctx);
            if (altLibrary != null) {
               VersionInfo originalVersion = library.getVersion();
               VersionInfo altVersion = altLibrary.getVersion();
               if (originalVersion == null && altVersion == null) {
                  library = altLibrary;
               } else if (originalVersion == null && altVersion != null) {
                  library = null;
               } else if (originalVersion != null && altVersion == null) {
                  library = null;
               } else if (originalVersion.compareTo(altVersion) == 0) {
                  library = altLibrary;
               }
            }

            if (library != null) {
               info = this.findResource(library, resourceName, localePrefix, compressable, isViewResource, ctx);
               if (info == null && localePrefix != null) {
                  info = this.findResource(library, resourceName, (String)null, compressable, isViewResource, ctx);
               }
            }
         }

         return info;
      } else {
         return null;
      }
   }

   private String trimLeadingSlash(String s) {
      return s.charAt(0) == '/' ? s.substring(1) : s;
   }

   static boolean nameContainsForbiddenSequence(String name) {
      boolean result = false;
      if (name != null) {
         name = name.toLowerCase();
         result = name.startsWith(".") || name.contains("../") || name.contains("..\\") || name.startsWith("/") || name.startsWith("\\") || name.endsWith("/") || name.contains("..%2f") || name.contains("..%5c") || name.startsWith("%2f") || name.startsWith("%5c") || name.endsWith("%2f") || name.contains("..\\u002f") || name.contains("..\\u005c") || name.startsWith("\\u002f") || name.startsWith("\\u005c") || name.endsWith("\\u002f");
      }

      return result;
   }

   private ResourceInfo getFromCache(String name, String library, String localePrefix, List contracts) {
      return this.cache == null ? null : this.cache.get(name, library, localePrefix, contracts);
   }

   private void addToCache(ResourceInfo info, List contracts) {
      if (this.cache != null) {
         this.cache.add(info, contracts);
      }
   }

   LibraryInfo findLibrary(String libraryName, String localePrefix, String contract, FacesContext ctx) {
      LibraryInfo library = this.webappResourceHelper.findLibrary(libraryName, localePrefix, contract, ctx);
      if (library == null) {
         library = this.classpathResourceHelper.findLibrary(libraryName, localePrefix, contract, ctx);
      }

      if (library == null && contract == null) {
         library = this.faceletWebappResourceHelper.findLibrary(libraryName, localePrefix, contract, ctx);
      }

      return library;
   }

   LibraryInfo findLibraryOnClasspathWithZipDirectoryEntryScan(String libraryName, String localePrefix, String contract, FacesContext ctx, boolean forceScan) {
      return this.classpathResourceHelper.findLibraryWithZipDirectoryEntryScan(libraryName, localePrefix, contract, ctx, forceScan);
   }

   private ResourceInfo findResource(LibraryInfo library, String resourceName, String localePrefix, boolean compressable, boolean skipToFaceletResourceHelper, FacesContext ctx) {
      if (library != null) {
         return library.getHelper().findResource(library, resourceName, localePrefix, compressable, ctx);
      } else {
         ResourceInfo resource = null;
         if (!skipToFaceletResourceHelper) {
            resource = this.webappResourceHelper.findResource((LibraryInfo)null, resourceName, localePrefix, compressable, ctx);
         }

         if (resource == null && !skipToFaceletResourceHelper) {
            resource = this.classpathResourceHelper.findResource((LibraryInfo)null, resourceName, localePrefix, compressable, ctx);
         }

         if (resource == null) {
            resource = this.faceletWebappResourceHelper.findResource(library, resourceName, localePrefix, compressable, ctx);
         }

         return resource;
      }
   }

   ResourceInfo findResource(String resourceId) {
      String libraryName = null;
      String resourceName = null;
      int end = false;
      int start = false;
      int end;
      if (-1 != (end = resourceId.lastIndexOf("/"))) {
         resourceName = resourceId.substring(end + 1);
         int start;
         if (-1 != (start = resourceId.lastIndexOf("/", end - 1))) {
            libraryName = resourceId.substring(start + 1, end);
         } else {
            libraryName = resourceId.substring(0, end);
         }
      }

      FacesContext context = FacesContext.getCurrentInstance();
      LibraryInfo info = this.findLibrary(libraryName, (String)null, (String)null, context);
      ResourceInfo resourceInfo = this.findResource(info, resourceName, libraryName, true, false, context);
      return resourceInfo;
   }

   private String getLocalePrefix(FacesContext context) {
      String localePrefix = null;
      localePrefix = (String)context.getExternalContext().getRequestParameterMap().get("loc");
      if (localePrefix != null && !nameContainsForbiddenSequence(localePrefix)) {
         return localePrefix;
      } else {
         localePrefix = null;
         String appBundleName = context.getApplication().getMessageBundle();
         if (null != appBundleName) {
            Locale locale = null;
            if (context.getViewRoot() != null) {
               locale = context.getViewRoot().getLocale();
            } else {
               locale = context.getApplication().getViewHandler().calculateLocale(context);
            }

            try {
               ResourceBundle appBundle = ResourceBundle.getBundle(appBundleName, locale, Util.getCurrentLoader(ResourceManager.class));
               localePrefix = appBundle.getString("javax.faces.resource.localePrefix");
            } catch (MissingResourceException var6) {
               if (LOGGER.isLoggable(Level.FINEST)) {
                  LOGGER.log(Level.FINEST, "Ignoring missing resource", var6);
               }
            }
         }

         return localePrefix;
      }
   }

   private List getResourceLibraryContracts(FacesContext context) {
      UIViewRoot viewRoot = context.getViewRoot();
      if (viewRoot == null) {
         if (context.getApplication().getResourceHandler().isResourceRequest(context)) {
            String param = (String)context.getExternalContext().getRequestParameterMap().get("con");
            if (!nameContainsForbiddenSequence(param) && param != null && param.trim().length() > 0) {
               return Arrays.asList(param);
            }
         }

         return Collections.emptyList();
      } else {
         return context.getResourceLibraryContracts();
      }
   }

   private boolean isCompressable(String contentType, FacesContext ctx) {
      if (contentType != null && !ctx.isProjectStage(ProjectStage.Development)) {
         if (this.compressableTypes != null && !this.compressableTypes.isEmpty()) {
            Iterator var3 = this.compressableTypes.iterator();

            while(var3.hasNext()) {
               Pattern p = (Pattern)var3.next();
               boolean matches = p.matcher(contentType).matches();
               if (matches) {
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private void initCompressableTypes(Map appMap) {
      WebConfiguration config = WebConfiguration.getInstance();
      String value = config.getOptionValue(WebConfiguration.WebContextInitParameter.CompressableMimeTypes);
      if (value != null && value.length() > 0) {
         String[] values = Util.split(appMap, value, ",");
         if (values != null) {
            String[] var5 = values;
            int var6 = values.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               String s = var5[var7];
               String pattern = s.trim();
               if (this.isPatternValid(pattern)) {
                  if (pattern.endsWith("/*")) {
                     pattern = pattern.substring(0, pattern.indexOf("/*"));
                     pattern = pattern + "/[a-z0-9.-]*";
                  }

                  if (this.compressableTypes == null) {
                     this.compressableTypes = new ArrayList(values.length);
                  }

                  try {
                     this.compressableTypes.add(Pattern.compile(pattern));
                  } catch (PatternSyntaxException var11) {
                     if (LOGGER.isLoggable(Level.WARNING)) {
                        LOGGER.log(Level.WARNING, "jsf.resource.mime.type.configration.invalid", new Object[]{pattern, var11.getPattern()});
                     }
                  }
               }
            }
         }
      }

   }

   private boolean isPatternValid(String input) {
      return CONFIG_MIMETYPE_PATTERN.matcher(input).matches();
   }

   static {
      LOGGER = FacesLogger.RESOURCE.getLogger();
      CONFIG_MIMETYPE_PATTERN = Pattern.compile("[a-z-]*/[a-z0-9.\\*-]*");
   }
}
