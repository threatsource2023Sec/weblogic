package weblogic.validation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.XXEUtils;

public class ValidationContext {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugValidation");
   private boolean isJar;
   private boolean isZip;
   private String resourceDir;
   private static final String META_INF = "/META-INF/";
   private static final String WEB_INF = "/WEB-INF/";
   private URL url;
   private boolean isStateValid = true;
   private Boolean hasValidDescriptor;
   private Set constraintMappingResources = new HashSet();

   private ValidationContext(URL anUrl) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Creating ValidationContext for Url:" + anUrl);
      }

      if (anUrl != null) {
         this.url = anUrl;
         this.isJar = this.url.getProtocol().equalsIgnoreCase("jar");
         String p = this.url.getPath();
         if (!p.endsWith("validation.xml")) {
            this.invalidURL("URL does not point to a validation.xml.");
         }

         if (this.isJar) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("ValidationContext is reading descriptor from a jar.");
            }

            String[] parts = p.split("!");
            if (parts.length != 2 || !parts[0].startsWith("file:")) {
               this.invalidURL("Invalid jar url.");
            }

            if (!(new File(parts[0].substring(5))).exists()) {
               this.invalidURL("The jar does not exist.");
            }

            this.saveResourceDir(p.substring(p.lastIndexOf(33) + 1));
         } else if (this.url.getProtocol().equalsIgnoreCase("zip")) {
            this.isZip = true;
            this.saveResourceDir(p.substring(p.lastIndexOf(33) + 1));
         } else {
            File f = new File(p);
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("ValidationContext is reading descriptor from a file.");
            }

            if (!f.exists()) {
               this.invalidURL("The file, " + p + ", does not exist.");
            }

            if (!f.isFile()) {
               this.invalidURL("The url does not point to a file.");
            }

            String regex = "/";
            String[] parts = p.split(regex);
            int partCount = parts.length;
            if (partCount < 2) {
               this.invalidURL("validation.xml must be in a META-INF or WEB-INF directory.");
            }

            this.saveResourceDir(parts[partCount - 2] + "/" + parts[partCount - 1]);
         }
      }

   }

   protected static ValidationContext getNewValidationContextForUrl(URL url) {
      ValidationContext vc = new ValidationContext(url);
      vc.checkIsValid();
      if (debugLogger.isDebugEnabled()) {
         if (vc.hasValidDescriptor()) {
            debugLogger.debug("Returning a valid new validation context for url, " + url);
         } else {
            debugLogger.debug("Returning an invalid validation context for url, " + url);
         }
      }

      return vc;
   }

   private void checkIsValid() {
      if (!this.isStateValid) {
         this.hasValidDescriptor = true;
      }

   }

   private void saveResourceDir(String p) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("ValidationContext is saving resource " + p);
      }

      String[] parts = p.split("/");
      if (parts.length != 2 && (parts.length != 3 || parts[0].length() != 0)) {
         this.invalidURL("validation.xml must be accessible as a META-INF or WEB-INF resource.");
      } else {
         int idx = parts.length - 2;
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("ValidationContext Resource name parts: " + parts[0] + ", " + parts[1]);
         }

         if (parts[idx].equals("META-INF")) {
            this.resourceDir = "/META-INF/";
         } else if (parts[idx].equals("WEB-INF")) {
            this.resourceDir = "/WEB-INF/";
         } else {
            this.invalidURL("validation.xml must be accessible as a META-INF or WEB-INF resource.");
         }
      }

   }

   private void invalidURL(String reason) {
      this.isStateValid = false;
      ValidationLogger.errorUnableToProcessURL(this.url, reason);
      throw new IllegalArgumentException("Invalid URL, " + this.url + ", because " + reason);
   }

   public URL getURLForPath(String resourcePath) {
      assert this.hasValidDescriptor() && this.isStateValid : "It should not be possible to get an instance in this state";

      return this.url != null && this.isStateValid && this.hasValidDescriptor() ? this.buildURLForPath(resourcePath) : null;
   }

   public InputStream openStreamForPath(String resourcePath) throws IOException {
      URL inputStream;
      if (this.isZip && this.url != null) {
         if (resourcePath.endsWith("META-INF/validation.xml")) {
            return this.url.openStream();
         } else {
            inputStream = null;
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            InputStream inputStream;
            if (cl != null) {
               inputStream = cl.getResourceAsStream(resourcePath);
               if (inputStream == null) {
                  cl = ValidationContext.class.getClassLoader();
                  inputStream = cl.getResourceAsStream(resourcePath);
               }
            } else {
               cl = ValidationContext.class.getClassLoader();
               inputStream = cl.getResourceAsStream(resourcePath);
            }

            return inputStream;
         }
      } else {
         inputStream = this.getURLForPath(resourcePath);
         return inputStream == null ? null : inputStream.openStream();
      }
   }

   private URL buildURLForPath(String resourcePath) {
      resourcePath = resourcePath.substring(resourcePath.lastIndexOf(47) + 1);

      try {
         return new URL(this.url, this.isJar ? this.resourceDir + resourcePath : resourcePath);
      } catch (MalformedURLException var3) {
         ValidationLogger.errorUnableToProcessURLWith(this.url, this.isJar, this.resourceDir, resourcePath);
         throw new RuntimeException("Unable to build URL corresponding to resource " + resourcePath, var3);
      }
   }

   private void addConstraintMappingResource(String resourceName) {
      this.constraintMappingResources.add(resourceName);
   }

   public boolean hasConstraintMappingResource(String resourceName) {
      return this.constraintMappingResources.contains(resourceName);
   }

   public boolean hasValidDescriptor() {
      if (this.hasValidDescriptor == null) {
         if (this.url == null) {
            this.hasValidDescriptor = true;
         } else {
            this.hasValidDescriptor = false;
            URL valDescrUrl = this.buildURLForPath("META-INF/validation.xml");
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("ValidationContext is valid!");
            }

            SAXParserFactory spf = XXEUtils.createSAXParserFactoryInstance();

            try {
               SAXParser sp = spf.newSAXParser();
               final boolean[] foundValidationConfig = new boolean[]{false};
               sp.parse(valDescrUrl.openStream(), new DefaultHandler() {
                  String tempVal;

                  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                     this.tempVal = null;
                     if ("validation-config".equals(qName)) {
                        foundValidationConfig[0] = true;
                     }

                  }

                  public void characters(char[] ch, int start, int length) throws SAXException {
                     this.tempVal = new String(ch, start, length);
                  }

                  public void endElement(String uri, String localName, String qName) throws SAXException {
                     if ("constraint-mapping".equals(qName)) {
                        ValidationContext.this.addConstraintMappingResource(this.tempVal);
                     }

                  }
               });
               this.hasValidDescriptor = foundValidationConfig[0];
            } catch (IOException var5) {
               ValidationLogger.errorUnableToProcessURLDueToException(valDescrUrl, var5);
            } catch (Exception var6) {
               var6.printStackTrace();
            }

            if (!this.hasValidDescriptor) {
               ValidationLogger.warningUnableToParseDescriptor(valDescrUrl);
            }
         }
      }

      return this.hasValidDescriptor;
   }
}
