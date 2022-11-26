package weblogic.application.ddconvert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import weblogic.application.ApplicationDescriptor;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.utils.EarUtils;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.ModuleBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;

public class EarConverter implements Converter {
   private static final String STANDARD_DD;
   private static final String WEBLOGIC_DD;

   public void printStartMessage(String uri) {
      ConvertCtx.debug("START Converting descriptors for EAR " + uri);
   }

   public void printEndMessage(String uri) {
      ConvertCtx.debug("END Converting descriptors for EAR " + uri);
   }

   public void convertDDs(ConvertCtx ctx, ApplicationArchive archive, File outputDir) throws DDConvertException {
   }

   public void convertDDs(ConvertCtx ctx, VirtualJarFile vjar, File outputDir) throws DDConvertException {
      FileOutputStream appDD = null;
      FileOutputStream wlAppDD = null;
      GenericClassLoader gcl = null;

      try {
         (new File(outputDir, "META-INF")).mkdirs();
         appDD = new FileOutputStream(new File(outputDir, STANDARD_DD));
         if (ctx.isVerbose()) {
            ConvertCtx.debug("Converting " + STANDARD_DD);
         }

         gcl = ctx.newClassLoader(vjar);
         ApplicationDescriptor ad = new ApplicationDescriptor(ctx.getDescriptorManager(), gcl);
         ApplicationBean appXML = ad.getApplicationDescriptor();
         ctx.getDescriptorManager().writeDescriptorBeanAsXML((DescriptorBean)appXML, appDD);
         WeblogicApplicationBean wlXML = ad.getWeblogicApplicationDescriptor();
         if (wlXML != null) {
            if (ctx.isVerbose()) {
               ConvertCtx.debug("Converting " + WEBLOGIC_DD);
            }

            wlAppDD = new FileOutputStream(new File(outputDir, WEBLOGIC_DD));
            ctx.getDescriptorManager().writeDescriptorBeanAsXML((DescriptorBean)wlXML, wlAppDD);
         }

         this.convertModules(appXML.getModules(), ctx, outputDir);
      } catch (Exception var20) {
         var20.printStackTrace();
         throw new DDConvertException(var20);
      } finally {
         if (gcl != null) {
            gcl.close();
         }

         if (appDD != null) {
            try {
               appDD.close();
            } catch (IOException var19) {
            }
         }

         if (wlAppDD != null) {
            try {
               wlAppDD.close();
            } catch (IOException var18) {
            }
         }

      }

   }

   private void convertModules(ModuleBean[] modules, ConvertCtx ctx, File outputDir) throws DDConvertException, IOException {
      if (modules != null && modules.length != 0) {
         for(int i = 0; i < modules.length; ++i) {
            String uri = EarUtils.reallyGetModuleURI(modules[i]);
            VirtualJarFile vjar = null;

            try {
               vjar = ctx.getModuleVJF(uri);
               if (ctx.isVerbose()) {
                  ConvertCtx.debug("Looking for converters for module-uri " + uri);
               }

               Converter[] converters = ConverterFactoryManager.instance.findConverters(ctx, vjar);
               if (converters != null) {
                  Converter[] var8 = converters;
                  int var9 = converters.length;

                  for(int var10 = 0; var10 < var9; ++var10) {
                     Converter c = var8[var10];
                     File moduleDir = new File(outputDir, uri);
                     moduleDir.mkdirs();
                     if (!ctx.isQuiet()) {
                        c.printStartMessage(uri);
                     }

                     if (ctx.hasApplicationArchive()) {
                        c.convertDDs(ctx, ctx.getApplicationArchive().getApplicationArchive(uri), ctx.getOutputDir());
                        c.convertDDs(ctx, vjar, moduleDir);
                     } else {
                        c.convertDDs(ctx, vjar, moduleDir);
                     }

                     if (!ctx.isQuiet()) {
                        c.printEndMessage(uri);
                     }
                  }
               }
            } finally {
               if (vjar != null) {
                  try {
                     vjar.close();
                  } catch (IOException var18) {
                  }
               }

            }
         }

      }
   }

   static {
      STANDARD_DD = "META-INF" + File.separator + "application.xml";
      WEBLOGIC_DD = "META-INF" + File.separator + "weblogic-application.xml";
   }
}
