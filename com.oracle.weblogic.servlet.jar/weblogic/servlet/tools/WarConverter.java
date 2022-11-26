package weblogic.servlet.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.ddconvert.ConvertCtx;
import weblogic.application.ddconvert.Converter;
import weblogic.application.ddconvert.DDConvertException;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.servlet.internal.WebAppDescriptor;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;

public class WarConverter implements Converter {
   private static final String STANDARD_DD;
   private static final String WEBLOGIC_DD;

   public void printStartMessage(String uri) {
      ConvertCtx.debug("START Converting web descriptors for module " + uri);
   }

   public void printEndMessage(String uri) {
      ConvertCtx.debug("END Converting web descriptors for module " + uri);
   }

   public void convertDDs(ConvertCtx ctx, VirtualJarFile vjar, File outputDir) throws DDConvertException {
      FileOutputStream stdDD = null;
      FileOutputStream wlDD = null;
      GenericClassLoader gcl = null;

      try {
         (new File(outputDir, "WEB-INF")).mkdirs();
         if (ctx.isVerbose()) {
            ConvertCtx.debug("Converting " + STANDARD_DD);
         }

         stdDD = new FileOutputStream(new File(outputDir, STANDARD_DD));
         gcl = ctx.newClassLoader(vjar);
         WebAppDescriptor wad = new WebAppDescriptor(ctx.getDescriptorManager(), gcl);
         WebAppBean wab = wad.getWebAppBean();
         ctx.getDescriptorManager().writeDescriptorBeanAsXML((DescriptorBean)wab, stdDD);
         WeblogicWebAppBean wlWeb = wad.getWeblogicWebAppBean();
         if (wlWeb != null) {
            if (ctx.isVerbose()) {
               ConvertCtx.debug("Converting " + WEBLOGIC_DD);
            }

            wlDD = new FileOutputStream(new File(outputDir, WEBLOGIC_DD));
            ctx.getDescriptorManager().writeDescriptorBeanAsXML((DescriptorBean)wlWeb, wlDD);
         }
      } catch (Exception var20) {
         throw new DDConvertException(var20);
      } finally {
         if (gcl != null) {
            gcl.close();
         }

         if (stdDD != null) {
            try {
               stdDD.close();
            } catch (IOException var19) {
            }
         }

         if (wlDD != null) {
            try {
               wlDD.close();
            } catch (IOException var18) {
            }
         }

      }

   }

   public void convertDDs(ConvertCtx ctx, ApplicationArchive archive, File outputDir) throws DDConvertException {
   }

   static {
      STANDARD_DD = "WEB-INF" + File.separator + "web.xml";
      WEBLOGIC_DD = "WEB-INF" + File.separator + "weblogic.xml";
   }
}
