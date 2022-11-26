package weblogic.security.service;

import java.util.ArrayList;
import weblogic.security.services.AppContext;
import weblogic.security.services.AppContextElement;

public class AppContextHandler implements ContextHandler {
   private AppContext appContext = null;

   public static AppContextHandler getInstance(AppContext appCtx) {
      return appCtx == null ? null : new AppContextHandler(appCtx);
   }

   private AppContextHandler(AppContext appCtx) {
      this.appContext = appCtx;
   }

   public int size() {
      int size = 0;
      return this.appContext == null ? size : this.appContext.size();
   }

   public String[] getNames() {
      String[] contextNames = new String[0];
      return this.appContext == null ? contextNames : this.appContext.getNames();
   }

   public Object getValue(String name) {
      if (this.appContext == null) {
         return null;
      } else {
         AppContextElement appCtxElement = this.appContext.getElement(name);
         return appCtxElement == null ? null : appCtxElement.getValue();
      }
   }

   public ContextElement[] getValues(String[] names) {
      AppContextElement[] appCtxElements = null;
      ContextElement[] ctxElements = null;
      if (names == null) {
         return (ContextElement[])ctxElements;
      } else {
         appCtxElements = this.appContext.getElements(names);
         if (appCtxElements == null) {
            return (ContextElement[])ctxElements;
         } else {
            ArrayList listElements = new ArrayList();

            for(int i = 0; i < appCtxElements.length; ++i) {
               AppContextElement appCtxElement = appCtxElements[i];
               if (appCtxElement != null) {
                  listElements.add(new ContextElement(appCtxElement.getName(), appCtxElement.getValue()));
               }
            }

            ContextElement[] ctxElementArray = new ContextElement[listElements.size()];
            return (ContextElement[])((ContextElement[])listElements.toArray(ctxElementArray));
         }
      }
   }
}
