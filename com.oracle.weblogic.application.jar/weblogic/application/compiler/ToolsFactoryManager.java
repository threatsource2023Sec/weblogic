package weblogic.application.compiler;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.compiler.utils.DefaultToolsEnvironment;
import weblogic.application.utils.AppSupportDeclarations;
import weblogic.j2ee.descriptor.ModuleBean;
import weblogic.j2ee.descriptor.wl.WeblogicModuleBean;

public class ToolsFactoryManager {
   private static FactoryManager compilerFactoryManager = new FactoryManager();
   private static FactoryManager mergerFactoryManager = new FactoryManager();
   private static FactoryManager moduleFactoryManager = new FactoryManager();
   private static FactoryManager wlmoduleFactoryManager = new FactoryManager();
   private static FactoryManager standaloneModuleFactoriesManager = new FactoryManager();
   private static FactoryManager standaloneAAModuleFactoriesManager = new FactoryManager();
   private static Map extensionFactories = new HashMap();
   private static Map moduleExtensionFactories = new HashMap();
   private static ToolsEnvironment environment = new DefaultToolsEnvironment();

   public static void addCompilerFactory(Factory factory) {
      compilerFactoryManager.addFactory(factory);
   }

   public static Compiler createCompiler(CompilerCtx ctx) throws FactoryException {
      return (Compiler)compilerFactoryManager.create(ctx);
   }

   public static void addMergerFactory(Factory factory) {
      mergerFactoryManager.addFactory(factory);
   }

   public static Merger createMerger(CompilerCtx ctx) throws FactoryException {
      return (Merger)mergerFactoryManager.create(ctx);
   }

   public static void addModuleFactory(ToolsModuleFactory factory) {
      moduleFactoryManager.addFactory(factory);
      AppSupportDeclarations.instance.register(factory);
   }

   public static ToolsModule createModule(ModuleBean bean) throws FactoryException {
      return (ToolsModule)moduleFactoryManager.create(bean);
   }

   public static void addWLModuleFactory(WebLogicToolsModuleFactory factory) {
      wlmoduleFactoryManager.addFactory(factory);
   }

   public static ToolsModule createWLModule(WeblogicModuleBean bean) throws FactoryException {
      return (ToolsModule)wlmoduleFactoryManager.create(bean);
   }

   public static void addStandaloneModuleFactory(StandaloneToolsModuleFactory factory) {
      standaloneModuleFactoriesManager.addFactory(factory);
      standaloneAAModuleFactoriesManager.addFactory(new WrapperFactory(factory));
   }

   public static ToolsModule createStandaloneModule(File standaloneModule, ApplicationArchive archive) throws FactoryException {
      try {
         return (ToolsModule)standaloneModuleFactoriesManager.create(standaloneModule);
      } catch (NoClaimsFactoryException var3) {
         if (archive == null) {
            throw var3;
         } else {
            return (ToolsModule)standaloneAAModuleFactoriesManager.create(archive);
         }
      }
   }

   public static void addExtensionFactory(ToolsExtensionFactory factory) {
      String name = factory.getClass().getName();
      if (!extensionFactories.containsKey(name)) {
         extensionFactories.put(name, factory);
      }

   }

   public static List createToolsExtensions(CompilerCtx ctx) {
      List extensions = new ArrayList();
      Iterator var2 = extensionFactories.values().iterator();

      while(var2.hasNext()) {
         ToolsExtensionFactory factory = (ToolsExtensionFactory)var2.next();
         ToolsExtension extension = factory.createExtension(ctx);
         if (extension != null) {
            extensions.add(extension);
         }
      }

      return extensions;
   }

   public static void addModuleExtensionFactory(String extensibleModuleType, ToolsModuleExtensionFactory factory) {
      if (!moduleExtensionFactories.containsKey(extensibleModuleType)) {
         moduleExtensionFactories.put(extensibleModuleType, new ArrayList());
      }

      ((List)moduleExtensionFactories.get(extensibleModuleType)).add(factory);
      AppSupportDeclarations.instance.register(factory);
   }

   public static Iterator getModuleExtensionFactories(String extensibleModuleType) {
      return moduleExtensionFactories.containsKey(extensibleModuleType) ? ((List)moduleExtensionFactories.get(extensibleModuleType)).iterator() : Collections.emptyList().iterator();
   }

   public static void setToolsEnvironment(ToolsEnvironment env) {
      environment = env;
   }

   public static ToolsEnvironment getToolsEnvironment() {
      return environment;
   }

   static {
      addCompilerFactory(new SingleModuleCompilerFactory());
      addMergerFactory(new SCACompilerFactory());
      addMergerFactory(new SingleModuleMergerFactory());
   }

   private static class WrapperFactory implements Factory {
      private final StandaloneToolsModuleFactory delegate;

      private WrapperFactory(StandaloneToolsModuleFactory delegate) {
         this.delegate = delegate;
      }

      public Boolean claim(ApplicationArchive archive) {
         return this.delegate.claim(archive);
      }

      public Boolean claim(ApplicationArchive archive, List allClaimants) {
         return this.delegate.claim(archive, allClaimants);
      }

      public ToolsModule create(ApplicationArchive archive) {
         return this.delegate.create(archive);
      }

      // $FF: synthetic method
      WrapperFactory(StandaloneToolsModuleFactory x0, Object x1) {
         this(x0);
      }
   }
}
