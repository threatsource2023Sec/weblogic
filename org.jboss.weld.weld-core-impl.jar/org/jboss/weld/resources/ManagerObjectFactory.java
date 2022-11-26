package org.jboss.weld.resources;

import java.io.File;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import org.jboss.weld.Container;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.logging.BeanManagerLogger;

public class ManagerObjectFactory implements ObjectFactory {
   public static final String FLAT_BEAN_DEPLOYMENT_ID = "flat";
   public static final String WEB_INF_CLASSES = "/WEB-INF/classes";
   public static final String WEB_INF_CLASSES_FILE_PATH;
   private final String contextId;

   public ManagerObjectFactory() {
      this("STATIC_INSTANCE");
   }

   public ManagerObjectFactory(String contextId) {
      this.contextId = contextId;
   }

   public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) throws Exception {
      if (!Container.available(this.contextId)) {
         throw BeanManagerLogger.LOG.cannotLocateBeanManager();
      } else {
         Iterator var5 = Container.instance(this.contextId).beanDeploymentArchives().entrySet().iterator();

         Map.Entry entry;
         BeanDeploymentArchive bda;
         do {
            if (!var5.hasNext()) {
               throw BeanManagerLogger.LOG.cannotLocateBeanManager();
            }

            entry = (Map.Entry)var5.next();
            bda = (BeanDeploymentArchive)entry.getKey();
         } while(!bda.getId().equals("flat") && !bda.getId().contains(WEB_INF_CLASSES_FILE_PATH) && !bda.getId().contains("/WEB-INF/classes"));

         return entry.getValue();
      }
   }

   static {
      WEB_INF_CLASSES_FILE_PATH = File.separatorChar + "WEB-INF" + File.separatorChar + "classes";
   }
}
