package weblogic.servlet.internal;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import weblogic.application.io.DescriptorFinder;
import weblogic.application.utils.CompositeWebAppFinder;
import weblogic.utils.StringUtils;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.Source;
import weblogic.utils.enumerations.EmptyEnumerator;
import weblogic.utils.jars.VirtualJarFile;

public class WarDefinition {
   private String uri;
   private VirtualJarFile vjf;
   private boolean useOriginalJars;
   private String splitDirectoryClassPath;
   private File cacheLocationDir = null;

   public WarDefinition() {
   }

   public WarDefinition(String uri, VirtualJarFile vjf, boolean useOriginalJars, String[] splitDirWebInfoClasses, File cacheLocationDir) {
      this.setUri(uri);
      this.setVirtualJarFile(vjf);
      this.setUseOriginalJars(useOriginalJars);
      this.configureSplitDirectory(splitDirWebInfoClasses);
      this.cacheLocationDir = cacheLocationDir;
   }

   public void setUri(String uri) {
      this.uri = uri;
   }

   public void setVirtualJarFile(VirtualJarFile vjf) {
      this.vjf = vjf;
   }

   public void configureSplitDirectory(String[] splitDirWebInfoClasses) {
      if (splitDirWebInfoClasses != null && splitDirWebInfoClasses.length != 0) {
         this.splitDirectoryClassPath = StringUtils.join(splitDirWebInfoClasses, File.pathSeparator);
      }
   }

   public void setUseOriginalJars(boolean useOriginalJars) {
      this.useOriginalJars = useOriginalJars;
   }

   private WarExtensionProcessor getProcessor(War war) throws IOException {
      WarExtensionProcessor[] processors = new WarExtensionProcessor[]{new ConsoleExtensionProcessor(war), new BeaExtensionProcessor(war), new ManagementServicesExtensionProcessor(war)};
      WarExtensionProcessor[] var3 = processors;
      int var4 = processors.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         WarExtensionProcessor processor = var3[var5];
         if (processor.isSupport()) {
            return processor;
         }
      }

      return null;
   }

   public War extract(File extractDir, StaleProber reloadProber) throws IOException {
      War war = new War(this.uri, extractDir, this.vjf, this.useOriginalJars, reloadProber, this.cacheLocationDir);
      if (this.splitDirectoryClassPath != null) {
         CompositeWebAppFinder warClassFinder = (CompositeWebAppFinder)war.getClassFinder();
         ClassFinder splitWebAppClassfinder = new ClasspathClassFinder2(this.splitDirectoryClassPath);
         warClassFinder.addFinder(splitWebAppClassfinder);
         warClassFinder.addFinder(new SplitWebInfoClassesDescriptorFinder(this.uri, splitWebAppClassfinder));
         war.setSplitDirectoryClasspath(this.splitDirectoryClassPath);
      }

      WarExtensionProcessor processor = this.getProcessor(war);
      if (processor != null) {
         List extensions = processor.getExtensions();
         Iterator var6 = extensions.iterator();

         while(var6.hasNext()) {
            WarExtension extension = (WarExtension)var6.next();
            war.registerExtension(extension);
         }
      }

      return war;
   }

   private static class SplitWebInfoClassesDescriptorFinder extends DescriptorFinder {
      private static String WEB_INF_CLASSES = "/WEB-INF/classes";
      private final String prefix;
      private final ClassFinder delegate;

      public SplitWebInfoClassesDescriptorFinder(String uri, ClassFinder delegate) throws IOException {
         super(uri, delegate);
         this.prefix = uri + "#" + WEB_INF_CLASSES;
         this.delegate = delegate;
      }

      public Source getSource(String name) {
         return name != null && name.startsWith(this.prefix) ? this.delegate.getSource(this.removePrefix(name)) : null;
      }

      public Enumeration getSources(String name) {
         return (Enumeration)(name != null && name.startsWith(this.prefix) ? this.delegate.getSources(this.removePrefix(name)) : new EmptyEnumerator());
      }

      private String removePrefix(String name) {
         return name.substring(this.prefix.length(), name.length());
      }
   }
}
