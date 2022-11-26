package weblogic.connector.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.ddconvert.ConvertCtx;
import weblogic.application.ddconvert.Converter;
import weblogic.application.ddconvert.DDConvertException;
import weblogic.connector.configuration.ConnectorDescriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.ConnectorBean;
import weblogic.j2ee.descriptor.wl.WeblogicConnectorBean;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;

public final class RarConverter implements Converter {
   private static final String STANDARD_DD;
   private static final String WEBLOGIC_DD;

   public void printStartMessage(String uri) {
      ConvertCtx.debug("START Converting connector descriptors for module " + uri);
   }

   public void printEndMessage(String uri) {
      ConvertCtx.debug("END Converting connector descriptors for module " + uri);
   }

   public void convertDDs(ConvertCtx ctx, VirtualJarFile vjar, File outputDir) throws DDConvertException {
      FileOutputStream stdDD = null;
      GenericClassLoader gcl = null;

      try {
         gcl = ctx.newClassLoader(vjar);
         (new File(outputDir, "META-INF")).mkdirs();
         ConnectorDescriptor e = ConnectorDescriptor.buildDescriptor(ctx.getDescriptorManager(), gcl, false);
         WeblogicConnectorBean wl = e.getWeblogicConnectorBean();
         if (wl != null) {
            if (ctx.isVerbose()) {
               ConvertCtx.debug("Converting " + WEBLOGIC_DD);
            }

            this.convertWLRADD(ctx, wl, outputDir);
         }

         ConnectorBean std = e.getConnectorBean();
         if (std != null) {
            if (ctx.isVerbose()) {
               ConvertCtx.debug("Converting " + STANDARD_DD);
            }

            stdDD = new FileOutputStream(new File(outputDir, STANDARD_DD));
            ctx.getDescriptorManager().writeDescriptorBeanAsXML((DescriptorBean)std, stdDD);
         } else {
            ConvertCtx.debug("No ra.xml exists. Adapter may be a link-ref. Note that the adapter may still be deployed successfully as a 1.0 adapter without conversion.");
         }
      } catch (Exception var16) {
         throw new DDConvertException(var16);
      } finally {
         if (gcl != null) {
            gcl.close();
         }

         if (stdDD != null) {
            try {
               stdDD.close();
            } catch (IOException var15) {
            }
         }

      }

   }

   private void convertWLRADD(ConvertCtx ctx, WeblogicConnectorBean wl, File outputDir) throws DDConvertException {
      FileOutputStream wlDD = null;

      try {
         if (ctx.isVerbose()) {
            ConvertCtx.debug("Converting " + WEBLOGIC_DD);
         }

         wlDD = new FileOutputStream(new File(outputDir, WEBLOGIC_DD));
         ctx.getDescriptorManager().writeDescriptorBeanAsXML((DescriptorBean)wl, wlDD);
      } catch (Exception var13) {
         throw new DDConvertException(var13);
      } finally {
         if (wlDD != null) {
            try {
               wlDD.close();
            } catch (IOException var12) {
            }
         }

      }

   }

   public void convertDDs(ConvertCtx ctx, ApplicationArchive archive, File outputDir) throws DDConvertException {
   }

   static {
      STANDARD_DD = "META-INF" + File.separator + "ra.xml";
      WEBLOGIC_DD = "META-INF" + File.separator + "weblogic-ra.xml";
   }
}
