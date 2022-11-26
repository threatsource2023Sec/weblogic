package weblogic.servlet.jsp;

import javax.servlet.jsp.PageContext;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.internal.WebComponentCreator;

public class DependencyInjectionHelper {
   public static void inject(PageContext context, Object obj) {
      if (obj != null) {
         WebComponentCreator creator = getWebComponentCreator(context);
         if (creator.needDependencyInjection(obj)) {
            creator.inject(obj);
            creator.notifyPostConstruct(obj);
         }

      }
   }

   public static void preDestroy(PageContext context, Object obj) {
      if (obj != null) {
         WebComponentCreator creator = getWebComponentCreator(context);
         if (creator.needDependencyInjection(obj)) {
            creator.notifyPreDestroy(obj);
         }

      }
   }

   private static WebComponentCreator getWebComponentCreator(PageContext context) {
      WebAppServletContext wsc = (WebAppServletContext)context.getServletContext();
      return wsc.getComponentCreator();
   }
}
