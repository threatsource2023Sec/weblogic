package weblogic.coherence.container.server;

import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import weblogic.application.ApplicationDescriptor;
import weblogic.application.Type;
import weblogic.application.library.ApplicationLibrary;
import weblogic.application.library.J2EELibraryReference;
import weblogic.application.library.Library;
import weblogic.application.library.LibraryContext;
import weblogic.application.library.LibraryData;
import weblogic.application.library.LibraryDefinition;
import weblogic.application.library.LibraryProcessingException;
import weblogic.application.utils.LibraryLoggingUtils;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicModuleBean;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.JarClassFinder;
import weblogic.utils.classloaders.MultiClassFinder;

public class GarLibraryDefinition extends LibraryDefinition implements Library, ApplicationLibrary {
   private static final Type TYPE;

   public GarLibraryDefinition(LibraryData libData) {
      super(libData, TYPE);
   }

   public void importLibrary(J2EELibraryReference libRef, LibraryContext ctx, MultiClassFinder libraryClassFinder, MultiClassFinder instanceAppLibClassFinder, MultiClassFinder shaerdAppLibClassFinder) throws LibraryProcessingException {
      LibraryLoggingUtils.checkNoContextRootSet(libRef, TYPE);
      String libName = libRef.getName();
      String path = libRef + "." + TYPE;

      try {
         ApplicationDescriptor ad = new ApplicationDescriptor();
         ApplicationDescriptor adEar = ctx.getApplicationDescriptor();
         WeblogicApplicationBean beanApp = ad.getWeblogicApplicationDescriptor();
         WeblogicModuleBean beanModule = beanApp.createModule();
         WeblogicModuleBean[] var12 = beanApp.getModules();
         int var13 = var12.length;

         for(int var14 = 0; var14 < var13; ++var14) {
            WeblogicModuleBean module = var12[var14];
            if (libName.equals(module.getName())) {
               throw new LibraryProcessingException("A module with the name " + libName + " is already present within the ear " + adEar.getApplicationDescriptor().getApplicationName());
            }
         }

         beanModule.setName(libName);
         beanModule.setPath(path);
         beanModule.setType(String.valueOf(TYPE));
         ad.updateWeblogicApplicationDescriptor(beanApp);
         LibraryLoggingUtils.mergeDescriptors(adEar, ad);
         ctx.notifyDescriptorUpdate();
      } catch (IOException var17) {
         LibraryLoggingUtils.errorMerging(var17);
      } catch (XMLStreamException var18) {
         LibraryLoggingUtils.errorMerging(var18);
      }

      try {
         ctx.registerLink(path, this.getLocation());
         ctx.addClassFinder(this.getClassFinder());
      } catch (IOException var16) {
         throw new LibraryProcessingException(var16);
      }
   }

   private ClassFinder getClassFinder() throws IOException {
      return new JarClassFinder(this.getLocation());
   }

   public void init() throws LibraryProcessingException {
      if (this.getAutoRef().length > 0) {
         throw new LibraryProcessingException("gar libraries may not be auto-ref");
      }
   }

   static {
      TYPE = Type.GAR;
   }
}
