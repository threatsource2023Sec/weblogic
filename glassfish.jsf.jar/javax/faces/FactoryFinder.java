package javax.faces;

public final class FactoryFinder {
   public static final String APPLICATION_FACTORY = "javax.faces.application.ApplicationFactory";
   public static final String CLIENT_WINDOW_FACTORY = "javax.faces.lifecycle.ClientWindowFactory";
   public static final String EXCEPTION_HANDLER_FACTORY = "javax.faces.context.ExceptionHandlerFactory";
   public static final String EXTERNAL_CONTEXT_FACTORY = "javax.faces.context.ExternalContextFactory";
   public static final String FACES_CONTEXT_FACTORY = "javax.faces.context.FacesContextFactory";
   public static final String FACELET_CACHE_FACTORY = "javax.faces.view.facelets.FaceletCacheFactory";
   public static final String FLASH_FACTORY = "javax.faces.context.FlashFactory";
   public static final String FLOW_HANDLER_FACTORY = "javax.faces.flow.FlowHandlerFactory";
   public static final String PARTIAL_VIEW_CONTEXT_FACTORY = "javax.faces.context.PartialViewContextFactory";
   public static final String VISIT_CONTEXT_FACTORY = "javax.faces.component.visit.VisitContextFactory";
   public static final String LIFECYCLE_FACTORY = "javax.faces.lifecycle.LifecycleFactory";
   public static final String RENDER_KIT_FACTORY = "javax.faces.render.RenderKitFactory";
   public static final String VIEW_DECLARATION_LANGUAGE_FACTORY = "javax.faces.view.ViewDeclarationLanguageFactory";
   public static final String TAG_HANDLER_DELEGATE_FACTORY = "javax.faces.view.facelets.TagHandlerDelegateFactory";
   public static final String SEARCH_EXPRESSION_CONTEXT_FACTORY = "javax.faces.component.search.SearchExpressionContextFactory";
   static final CurrentThreadToServletContext FACTORIES_CACHE = new CurrentThreadToServletContext();

   FactoryFinder() {
   }

   public static Object getFactory(String factoryName) throws FacesException {
      FactoryFinderInstance factoryFinder;
      if ("com.sun.faces.ServletContextFacesContextFactory".equals(factoryName)) {
         factoryFinder = FACTORIES_CACHE.getFactoryFinder(false);
      } else {
         factoryFinder = FACTORIES_CACHE.getFactoryFinder();
      }

      return factoryFinder != null ? factoryFinder.getFactory(factoryName) : null;
   }

   public static void setFactory(String factoryName, String implName) {
      FACTORIES_CACHE.getFactoryFinder().addFactory(factoryName, implName);
   }

   public static void releaseFactories() throws FacesException {
      synchronized(FACTORIES_CACHE) {
         if (!FACTORIES_CACHE.factoryFinderMap.isEmpty()) {
            FACTORIES_CACHE.getFactoryFinder().releaseFactories();
         }

         FACTORIES_CACHE.removeFactoryFinder();
      }
   }

   private static void reInitializeFactoryManager() {
      FACTORIES_CACHE.resetSpecialInitializationCaseFlags();
   }
}
