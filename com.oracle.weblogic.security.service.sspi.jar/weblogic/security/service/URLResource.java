package weblogic.security.service;

import com.bea.common.security.SecurityLogger;
import weblogic.security.spi.Resource;

public final class URLResource extends ResourceBase {
   private static final String[] KEYS = new String[]{"application", "contextPath", "uri", "httpMethod", "transportType"};
   private static final int REPEATING_FIELD_INDEX = 3;
   private static final int REPEATING_FIELD_TERMINATING_INDEX = 2;
   private static final Resource TOP = new URLResource((String[])null, 0, (String)null, (String)null, false);
   private String suffix;
   private String httpMethod;
   private boolean URIUnchanged;
   private static boolean doMappingToLowerCase = false;
   private static boolean enforceStrictURLPattern = true;

   public URLResource(String application, String contextPathArg, String uriArg, String httpMethod, String transportType) {
      this(application, contextPathArg, uriArg, httpMethod, transportType, LegacyWebAppFilesCaseInsensitiveManager.getWebAppFilesCaseInsensitive());
   }

   public URLResource(String application, String contextPathArg, String uriArg, String httpMethod, String transportType, boolean areWebAppFilesCaseInsensitive) {
      this.suffix = null;
      this.httpMethod = null;
      this.URIUnchanged = true;
      if (application != null && application.length() == 0) {
         throw new InvalidParameterException(SecurityLogger.getEmptyResourceKeyString("URL", KEYS[0]));
      } else {
         String uri = uriArg;
         String contextPath = contextPathArg;
         doMappingToLowerCase = areWebAppFilesCaseInsensitive;
         enforceStrictURLPattern = LegacyEnforceStrictURLPatternManager.getEnforceStrictURLPattern();
         if (contextPathArg != null && doMappingToLowerCase) {
            contextPath = contextPathArg.toLowerCase();
         }

         if (uriArg != null) {
            if (doMappingToLowerCase) {
               uri = uriArg.toLowerCase();
            }

            int finalSlash = uri.lastIndexOf("/");
            if (finalSlash == -1) {
               finalSlash = 1;
            }

            int suffixIndex = uri.lastIndexOf(".");
            if (suffixIndex != -1 && suffixIndex > finalSlash) {
               this.suffix = "*" + uri.substring(suffixIndex);
            }

            if (!uri.startsWith("/") && !uri.startsWith("*.")) {
               uri = "/" + uri;
            }
         }

         String[] vals = new String[]{application, contextPath, uri, httpMethod, transportType};
         this.init(vals, 5, 0L, httpMethod, this.suffix, true);
      }
   }

   private URLResource(String[] values, int length, String httpMethod, String suffix, boolean URIUnchanged) {
      this.suffix = null;
      this.httpMethod = null;
      this.URIUnchanged = true;
      this.init(values, length, 0L, httpMethod, suffix, URIUnchanged);
   }

   protected void init(String[] values, int len, long seed, String httpMethod, String suffix, boolean URIUnchanged) {
      this.suffix = suffix;
      this.httpMethod = httpMethod;
      this.URIUnchanged = URIUnchanged;
      seed += (URIUnchanged ? 31L : -31L) * (long)((httpMethod == null ? 0 : httpMethod.hashCode() + 1) + (suffix == null ? 0 : suffix.hashCode() + 3));
      super.init(values, len, seed);
   }

   public String getType() {
      return "<url>";
   }

   private Resource makeParentBackwardCompatible() {
      switch (this.length) {
         case 0:
            return null;
         case 1:
            return new ApplicationResource(this.values[0], TOP);
         case 2:
         default:
            return new URLResource(this.values, this.length - 1, this.httpMethod, this.suffix, this.URIUnchanged);
         case 3:
            String uri = this.values[2];
            if (uri.startsWith("*.")) {
               return this.urlResourceWithNewURI("/*", this.httpMethod, (String)null);
            } else {
               int finalSlash = uri.lastIndexOf("/");
               if (uri.endsWith("/")) {
                  return this.urlResourceWithNewURI(uri.substring(0, finalSlash) + "/*", this.httpMethod, (String)null);
               } else {
                  if (finalSlash != -1 && (uri.endsWith("/") || uri.endsWith("/*"))) {
                     finalSlash = uri.lastIndexOf("/", finalSlash - 1);
                  } else if (this.URIUnchanged && uri != null) {
                     return this.urlResourceWithNewURI(uri + "/*", this.httpMethod, this.suffix, false);
                  }

                  if (finalSlash == 0) {
                     return this.suffix != null ? this.urlResourceWithNewURI(this.suffix, this.httpMethod, (String)null) : this.urlResourceWithNewURI("/*", this.httpMethod, (String)null);
                  } else if (finalSlash != -1) {
                     String newURI = uri.substring(0, finalSlash) + "/*";
                     return this.urlResourceWithNewURI(newURI, this.httpMethod, this.suffix);
                  } else {
                     return new URLResource(this.values, 2, this.httpMethod, this.suffix, false);
                  }
               }
            }
      }
   }

   protected Resource makeParent() {
      if (!enforceStrictURLPattern) {
         return this.makeParentBackwardCompatible();
      } else {
         switch (this.length) {
            case 0:
               return null;
            case 1:
               return new ApplicationResource(this.values[0], TOP);
            case 2:
            default:
               return new URLResource(this.values, this.length - 1, this.httpMethod, this.suffix, this.URIUnchanged);
            case 3:
               String uri = this.values[2];
               if (uri.equals("/")) {
                  return new URLResource(this.values, 2, this.httpMethod, this.suffix, false);
               } else if (uri.startsWith("*.")) {
                  return this.urlResourceWithNewURI("/", this.httpMethod, (String)null);
               } else if (uri.equals("/*")) {
                  return this.suffix != null ? this.urlResourceWithNewURI(this.suffix, this.httpMethod, (String)null) : this.urlResourceWithNewURI("/", this.httpMethod, (String)null);
               } else {
                  int finalSlash = uri.lastIndexOf("/");
                  if (uri.endsWith("/")) {
                     return this.urlResourceWithNewURI(uri.substring(0, finalSlash) + "/*", this.httpMethod, (String)null);
                  } else {
                     if (finalSlash == -1 || !uri.endsWith("/") && !uri.endsWith("/*")) {
                        if (this.URIUnchanged && uri != null) {
                           return this.urlResourceWithNewURI(uri + "/*", this.httpMethod, this.suffix, false);
                        }
                     } else {
                        finalSlash = uri.lastIndexOf("/", finalSlash - 1);
                     }

                     if (finalSlash == 0) {
                        return this.urlResourceWithNewURI("/*", this.httpMethod, this.suffix);
                     } else if (finalSlash != -1) {
                        String newURI = uri.substring(0, finalSlash) + "/*";
                        return this.urlResourceWithNewURI(newURI, this.httpMethod, this.suffix);
                     } else {
                        return new URLResource(this.values, 2, this.httpMethod, this.suffix, false);
                     }
                  }
               }
         }
      }
   }

   private URLResource urlResourceWithNewURI(String newURI, String httpMethod, String newSuffix, boolean URIUnchanged) {
      int size = 3;
      if (httpMethod != null) {
         size = 4;
      }

      return new URLResource(new String[]{this.values[0], this.values[1], newURI, httpMethod}, size, httpMethod, newSuffix, URIUnchanged);
   }

   private URLResource urlResourceWithNewURI(String newURI, String httpMethod, String newSuffix) {
      return this.urlResourceWithNewURI(newURI, httpMethod, newSuffix, this.URIUnchanged);
   }

   public String[] getKeys() {
      return KEYS;
   }

   public int getFieldType(String fieldName) {
      return fieldName.equals("uri") ? 2 : 1;
   }

   public int getRepeatingFieldIndex() {
      return 3;
   }

   public int getRepeatingFieldTerminatingIndex() {
      return 2;
   }

   public String getApplicationName() {
      return this.length > 0 ? this.values[0] : null;
   }

   public String getContextPath() {
      return this.length > 1 ? this.values[1] : null;
   }

   public String getURI() {
      return this.length > 2 ? this.values[2] : null;
   }

   public String getHttpMethod() {
      return this.length > 3 ? this.values[3] : null;
   }

   public String getTransportType() {
      return this.length > 4 ? this.values[4] : null;
   }

   public boolean mappingToLowerCase() {
      return doMappingToLowerCase;
   }

   public boolean equals(Object obj) {
      if (!super.equals(obj)) {
         return false;
      } else {
         URLResource r = (URLResource)obj;
         return this.URIUnchanged == r.URIUnchanged && (this.httpMethod == null && r.httpMethod == null || this.httpMethod != null && this.httpMethod.equals(r.httpMethod)) && (this.suffix == null && r.suffix == null || this.suffix != null && this.suffix.equals(r.suffix));
      }
   }
}
