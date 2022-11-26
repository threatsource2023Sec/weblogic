package weblogic.j2ee.dd;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import weblogic.application.ApplicationFileManager;
import weblogic.j2ee.J2EELogger;
import weblogic.management.descriptors.application.WebModuleMBean;
import weblogic.utils.io.XMLWriter;
import weblogic.utils.jars.VirtualJarFile;

public final class WebModuleDescriptor extends ModuleDescriptor implements WebModuleMBean {
   private static final long serialVersionUID = -4976877915175897268L;
   private static final boolean debug = false;
   private String context;

   public WebModuleDescriptor() {
   }

   public WebModuleDescriptor(String uri, String context) {
      super(uri);
      this.context = context;
   }

   public void setContext(String context) {
      String old = this.context;
      this.context = context;
      this.checkChange("context", old, context);
   }

   public String getContext() {
      return this.context;
   }

   public void setWebURI(String uri) {
      this.setURI(uri);
   }

   public void setModuleURI(String uri) {
      this.setURI(uri);
   }

   public String getModuleURI() {
      return this.getURI();
   }

   public void setContextRoot(String context) {
      this.setContext(context);
   }

   public String getContextRoot() {
      return this.getContext();
   }

   public String getModuleKey() {
      return this.getModuleURI() + "_" + this.getContextRoot();
   }

   public void toXML(XMLWriter x) {
      x.println("<module>");
      x.incrIndent();
      x.println("<web>");
      x.incrIndent();
      x.println("<web-uri>" + this.getURI() + "</web-uri>");
      if (this.getContext() != null) {
         x.println("<context-root>" + this.getContext() + "</context-root>");
      }

      x.decrIndent();
      x.println("</web>");
      if (this.getAltDDURI() != null) {
         x.println("<alt-dd>" + this.getAltDDURI() + "</alt-dd>");
      }

      x.decrIndent();
      x.println("</module>");
   }

   public String getAdminMBeanType(ApplicationFileManager afm) {
      return this.isWebService(afm) ? "WebServiceComponent" : "WebAppComponent";
   }

   private boolean isWebService(ApplicationFileManager afm) {
      VirtualJarFile vjf = null;

      boolean var4;
      try {
         vjf = afm.getVirtualJarFile();
         if (!vjf.isDirectory()) {
            if (vjf.getEntry(this.getModuleURI() + "/" + "WEB-INF/web-services.xml") != null) {
               boolean var48 = true;
               return var48;
            }

            ZipEntry warEntry = vjf.getEntry(this.getModuleURI());
            if (warEntry != null) {
               InputStream is = vjf.getJarFile().getInputStream(warEntry);
               if (is == null) {
                  boolean var50 = false;
                  return var50;
               }

               ZipInputStream zis = new ZipInputStream(is);

               try {
                  for(ZipEntry ze = zis.getNextEntry(); ze != null; ze = zis.getNextEntry()) {
                     if (ze.getName().equals("WEB-INF/web-services.xml")) {
                        boolean var7 = true;
                        return var7;
                     }
                  }
               } finally {
                  is.close();
                  zis.close();
               }
            }

            var4 = false;
            return var4;
         }

         VirtualJarFile moduleVJF = null;

         try {
            moduleVJF = afm.getVirtualJarFile(this.getURI());
            var4 = moduleVJF.getEntry("WEB-INF/web-services.xml") != null;
         } finally {
            if (moduleVJF != null) {
               try {
                  moduleVJF.close();
               } catch (Exception var42) {
               }
            }

         }
      } catch (IOException var45) {
         J2EELogger.logErrorCheckingWebService("can't read WEB-INF/web-services.xml", var45);
         var4 = false;
         return var4;
      } finally {
         try {
            if (vjf != null) {
               vjf.close();
            }
         } catch (IOException var41) {
         }

      }

      return var4;
   }
}
