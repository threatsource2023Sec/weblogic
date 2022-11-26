package org.jboss.weld.module.jsf;

import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.util.ApiAbstraction;

public class JsfApiAbstraction extends ApiAbstraction implements Service {
   public final Class FACES_CONTEXT = this.classForName("javax.faces.context.FacesContext");
   public final Class BEHAVIOR_CLASS = this.classForName("javax.faces.component.behavior.Behavior");
   public final double MINIMUM_API_VERSION;
   private static final String FACES_CONTEXT_CLASS_NAME = "javax.faces.context.FacesContext";
   private static final String BEHAVIOR_CLASS_NAME = "javax.faces.component.behavior.Behavior";
   private static final double COMMON_VERSION = 2.0;
   private static final double OLDER_VERSION = 1.2;

   public JsfApiAbstraction(ResourceLoader resourceLoader) {
      super(resourceLoader);
      if (this.BEHAVIOR_CLASS.getName().equals("javax.faces.component.behavior.Behavior")) {
         this.MINIMUM_API_VERSION = 2.0;
      } else {
         this.MINIMUM_API_VERSION = 1.2;
      }

   }

   public boolean isApiVersionCompatibleWith(double version) {
      return this.MINIMUM_API_VERSION >= version;
   }

   public void cleanup() {
   }
}
