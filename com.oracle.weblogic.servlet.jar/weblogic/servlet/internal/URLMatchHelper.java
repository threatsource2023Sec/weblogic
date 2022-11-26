package weblogic.servlet.internal;

import java.util.Locale;
import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.MappingMatch;
import weblogic.utils.StringUtils;

final class URLMatchHelper {
   private static final boolean WIN_32;
   private final String pattern;
   private final ServletStubImpl servletStub;
   private final String servletPath;
   private final String extension;
   private final boolean defaultServlet;
   private final boolean exactPattern;
   private final boolean contextRoot;
   private final boolean isFileServlet;
   private final boolean isJspClassServlet;
   private final boolean isJspServlet;

   URLMatchHelper(String p, ServletStubImpl sstub) {
      this.pattern = p;
      this.servletStub = sstub;
      String className = this.servletStub.getClassName();
      this.isFileServlet = "weblogic.servlet.FileServlet".equals(className);
      this.isJspServlet = "weblogic.servlet.JSPServlet".equals(className) || "weblogic.servlet.JavelinJSPServlet".equals(className) || "weblogic.servlet.JavelinxJSPServlet".equals(className) || "oracle.jsp.runtimev2.JspServlet".equals(className);
      this.isJspClassServlet = "weblogic.servlet.JSPClassServlet".equals(className);
      if (this.pattern.startsWith("*.") && this.pattern.length() > 2) {
         this.servletPath = null;
         this.extension = this.pattern.substring(1);
         this.defaultServlet = false;
         this.exactPattern = false;
         this.contextRoot = false;
      } else if (this.pattern.equals("/")) {
         this.servletPath = null;
         this.extension = null;
         this.defaultServlet = true;
         this.exactPattern = true;
         this.contextRoot = false;
      } else if (this.pattern.equals("/*")) {
         this.servletPath = "";
         this.extension = null;
         this.defaultServlet = true;
         this.exactPattern = false;
         this.contextRoot = false;
      } else if (this.pattern.equals("/\"\"")) {
         this.servletPath = "";
         this.extension = null;
         this.defaultServlet = false;
         this.exactPattern = false;
         this.contextRoot = true;
      } else if (this.pattern.endsWith("/*")) {
         this.servletPath = this.pattern.substring(0, this.pattern.length() - 2);
         this.extension = null;
         this.defaultServlet = false;
         this.exactPattern = false;
         this.contextRoot = false;
      } else {
         this.servletPath = this.pattern;
         this.extension = null;
         this.defaultServlet = false;
         this.exactPattern = true;
         this.contextRoot = false;
      }

   }

   String getPattern() {
      return this.pattern;
   }

   ServletStubImpl getServletStub() {
      return this.servletStub;
   }

   boolean isDefaultServlet() {
      return this.defaultServlet;
   }

   boolean isIndexServlet() {
      return !this.defaultServlet && !this.isJspServlet || this.isFileServlet;
   }

   boolean isFileOrJspServlet() {
      return this.isJspServlet || this.isFileServlet || this.isJspClassServlet;
   }

   String getServletPath(String relUri) {
      if (!this.exactPattern && !this.isJspServlet && !this.isJspClassServlet) {
         if (this.defaultServlet) {
            return "";
         } else if (this.extension == null) {
            return this.servletPath;
         } else {
            int dot;
            if (WIN_32) {
               dot = StringUtils.lastIndexOfIgnoreCase(relUri, this.extension);
            } else {
               dot = relUri.lastIndexOf(this.extension);
            }

            int slash = relUri.indexOf(47, dot);
            return slash < 0 ? relUri : relUri.substring(0, slash);
         }
      } else {
         return relUri;
      }
   }

   HttpServletMapping getHttpServletMapping(String relUri) {
      HttpServletMapping mapping = null;
      String servletName = this.getServletStub().getServletName();
      if (this.contextRoot) {
         mapping = new MappingImpl("", "", MappingMatch.CONTEXT_ROOT, servletName);
         return mapping;
      } else if (this.defaultServlet) {
         mapping = new MappingImpl("", "/", MappingMatch.DEFAULT, servletName);
         return mapping;
      } else if (this.exactPattern) {
         mapping = new MappingImpl(this.pattern.substring(1), this.pattern, MappingMatch.EXACT, servletName);
         return mapping;
      } else if (this.extension != null) {
         int dot;
         if (WIN_32) {
            dot = StringUtils.lastIndexOfIgnoreCase(relUri, this.extension);
         } else {
            dot = relUri.lastIndexOf(this.extension);
         }

         mapping = new MappingImpl(relUri.substring(1, dot), this.pattern, MappingMatch.EXTENSION, servletName);
         return mapping;
      } else {
         mapping = new MappingImpl(relUri.equalsIgnoreCase(this.servletPath) ? "" : relUri.substring(this.servletPath.length() + 1), this.pattern, MappingMatch.PATH, servletName);
         return mapping;
      }
   }

   static {
      WIN_32 = System.getProperty("os.name", "unknown").toLowerCase(Locale.ENGLISH).indexOf("windows") >= 0;
   }

   private static class MappingImpl implements HttpServletMapping {
      private final String matchValue;
      private final String pattern;
      private final MappingMatch mappingType;
      private final String servletName;

      public MappingImpl(String matchValue, String pattern, MappingMatch mappingType, String servletName) {
         this.matchValue = matchValue;
         this.pattern = pattern;
         this.mappingType = mappingType;
         this.servletName = servletName;
      }

      public String getMatchValue() {
         return this.matchValue;
      }

      public String getPattern() {
         return this.pattern;
      }

      public MappingMatch getMappingMatch() {
         return this.mappingType;
      }

      public String getServletName() {
         return this.servletName;
      }
   }
}
