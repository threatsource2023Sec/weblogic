package weblogic.jaxrs.server.extension;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import java.util.List;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.ext.Provider;
import weblogic.descriptor.Descriptor;
import weblogic.servlet.internal.WebBaseModuleExtensionContext;

public final class DeployHelper {
   public static final Class[] JAXRS_ANNOTATIONS = new Class[]{ApplicationPath.class, Path.class, Provider.class};
   public static final String[] JAXRS_HANDLE_TYPES;

   public static boolean isJaxRsApplication(WebBaseModuleExtensionContext context) {
      return isJaxRsApplication(context.getTemporaryClassLoader(), context);
   }

   public static boolean isJaxRsApplication(ClassLoader loader, WebBaseModuleExtensionContext context) {
      return !context.getWebAppHelper().getHandlesImpls(loader, JAXRS_HANDLE_TYPES).isEmpty();
   }

   private DeployHelper() {
   }

   static boolean isServletVersion2x(Descriptor descriptor) {
      return descriptor.getOriginalVersionInfo().startsWith("2");
   }

   static {
      List handles = Lists.newArrayList(JAXRS_ANNOTATIONS);
      handles.add(Application.class);
      JAXRS_HANDLE_TYPES = (String[])Lists.transform(handles, new Function() {
         public String apply(Class clazz) {
            return clazz.getName();
         }
      }).toArray(new String[handles.size()]);
   }
}
