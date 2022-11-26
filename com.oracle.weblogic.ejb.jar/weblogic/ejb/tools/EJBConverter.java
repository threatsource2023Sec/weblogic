package weblogic.ejb.tools;

import java.io.File;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.ddconvert.ConvertCtx;
import weblogic.application.ddconvert.Converter;
import weblogic.application.ddconvert.DDConvertException;
import weblogic.ejb.container.metadata.EJBDescriptorBeanUtils;
import weblogic.ejb.container.metadata.EjbDescriptorReaderImpl;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.ejb.spi.EjbDescriptorFactory;
import weblogic.utils.jars.VirtualJarFile;
import weblogic.xml.process.ProcessorFactory;

public final class EJBConverter implements Converter {
   private final ModuleType type;
   private final String descRoot;

   EJBConverter(ModuleType type) {
      this.type = type;
      if (type == ModuleType.EJB) {
         this.descRoot = "META-INF";
      } else {
         this.descRoot = "WEB-INF";
      }

   }

   public void printStartMessage(String uri) {
      ConvertCtx.debug("START Converting EJB descriptors for module " + uri);
   }

   public void printEndMessage(String uri) {
      ConvertCtx.debug("END Converting EJB descriptors for module " + uri);
   }

   public void convertDDs(ConvertCtx ctx, VirtualJarFile vjar, File outputDir) throws DDConvertException {
      try {
         EjbDescriptorReaderImpl reader = (EjbDescriptorReaderImpl)EjbDescriptorFactory.getEjbDescriptorReader();
         EjbDescriptorBean ejbDescriptor = reader.parseEjbDescriptorsForConverter(ctx.getDescriptorManager(), vjar, ModuleType.EJB);
         if (ejbDescriptor != null) {
            if (ctx.isVerbose()) {
               ConvertCtx.debug("Converting ejb-jar.xml");
            }

            (new File(outputDir, this.descRoot)).mkdirs();
            if (ejbDescriptor.getWeblogicEjbJarBean() != null) {
               if (ctx.isVerbose()) {
                  ConvertCtx.debug("Converting weblogic-ejb-jar.xml");
               }

               if (this.type == ModuleType.EJB) {
                  EJBDescriptorBeanUtils.loadWeblogicRDBMSJarBeans(ejbDescriptor, vjar, new ProcessorFactory(), false);
               }
            }

            ejbDescriptor.persistToDirectory(new File(outputDir.getAbsolutePath()), this.descRoot);
         }
      } catch (Exception var6) {
         throw new DDConvertException(var6);
      }
   }

   public void convertDDs(ConvertCtx ctx, ApplicationArchive archive, File outputDir) throws DDConvertException {
   }
}
