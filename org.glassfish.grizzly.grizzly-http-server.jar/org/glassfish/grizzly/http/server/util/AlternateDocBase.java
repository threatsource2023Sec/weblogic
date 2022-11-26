package org.glassfish.grizzly.http.server.util;

import java.util.List;
import org.glassfish.grizzly.http.server.naming.DirContext;

public class AlternateDocBase {
   private String pattern;
   private UrlPatternType patternType;
   private int patternSlashCount;
   private String docBase;
   private String basePath;
   private String patternSuffix;
   private String wildcardPath;
   private DirContext resources;
   private DirContext webappResources;

   public void setUrlPattern(String urlPattern) {
      this.pattern = urlPattern;
      this.patternSlashCount = getSlashCount(urlPattern);
      if (urlPattern.endsWith("/*")) {
         this.patternType = AlternateDocBase.UrlPatternType.WILDCARD;
         this.wildcardPath = urlPattern.substring(0, urlPattern.length() - 1);
      } else if (urlPattern.startsWith("*.")) {
         this.patternType = AlternateDocBase.UrlPatternType.EXTENSION;
         this.patternSuffix = urlPattern.substring(1);
      } else {
         this.patternType = AlternateDocBase.UrlPatternType.EXACT;
      }

   }

   public String getUrlPattern() {
      return this.pattern;
   }

   public UrlPatternType getUrlPatternType() {
      return this.patternType;
   }

   public int getUrlPatternSlashCount() {
      return this.patternSlashCount;
   }

   public String getUrlPatternSuffix() {
      return this.patternSuffix;
   }

   public String getUrlPatternWildcardPath() {
      return this.wildcardPath;
   }

   public void setDocBase(String docBase) {
      this.docBase = docBase;
   }

   public String getDocBase() {
      return this.docBase;
   }

   public void setBasePath(String basePath) {
      this.basePath = basePath;
   }

   public String getBasePath() {
      return this.basePath;
   }

   public void setResources(DirContext resources) {
      this.resources = resources;
   }

   public DirContext getResources() {
      return this.resources;
   }

   public void setWebappResources(DirContext webappResources) {
      this.webappResources = webappResources;
   }

   public DirContext getWebappResources() {
      return this.webappResources;
   }

   public static AlternateDocBase findMatch(String path, List alternateDocBases) {
      if (alternateDocBases == null) {
         return null;
      } else {
         AlternateDocBase match = null;
         AlternateDocBase wildcardMatch = null;
         AlternateDocBase extensionMatch = null;
         int maxSlashCountMatch = 0;
         int pathSlashCount = getSlashCount(path);

         for(int i = 0; i < alternateDocBases.size(); ++i) {
            AlternateDocBase alternateDocBase = (AlternateDocBase)alternateDocBases.get(i);
            String pattern = alternateDocBase.getUrlPattern();
            int patternSlashCount = alternateDocBase.getUrlPatternSlashCount();
            UrlPatternType type = alternateDocBase.getUrlPatternType();
            String wildcardPath = alternateDocBase.getUrlPatternWildcardPath();
            if (type == AlternateDocBase.UrlPatternType.EXACT && path.equals(pattern)) {
               match = alternateDocBase;
               break;
            }

            if (type == AlternateDocBase.UrlPatternType.WILDCARD && pathSlashCount >= patternSlashCount && patternSlashCount > maxSlashCountMatch && path.startsWith(wildcardPath)) {
               wildcardMatch = alternateDocBase;
               maxSlashCountMatch = patternSlashCount;
            } else if (type == AlternateDocBase.UrlPatternType.EXTENSION && path.endsWith(alternateDocBase.getUrlPatternSuffix())) {
               extensionMatch = alternateDocBase;
            }
         }

         if (match == null) {
            if (wildcardMatch != null) {
               match = wildcardMatch;
            } else {
               match = extensionMatch;
            }
         }

         return match;
      }
   }

   private static int getSlashCount(String s) {
      int count = 0;
      if (s != null) {
         for(int index = s.indexOf(47); index >= 0; index = s.indexOf(47, index + 1)) {
            ++count;
         }
      }

      return count;
   }

   static enum UrlPatternType {
      EXACT,
      WILDCARD,
      EXTENSION;
   }
}
