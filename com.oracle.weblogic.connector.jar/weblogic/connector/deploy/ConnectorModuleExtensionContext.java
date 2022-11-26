package weblogic.connector.deploy;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.naming.Environment;
import weblogic.application.utils.BaseModuleExtensionContext;
import weblogic.application.utils.annotation.ClassInfoFinder;
import weblogic.connector.common.RAInstanceManager;
import weblogic.connector.external.AdminObjInfo;
import weblogic.connector.external.RAInfo;
import weblogic.j2ee.descriptor.ActivationSpecBean;
import weblogic.j2ee.descriptor.ConnectionDefinitionBean;
import weblogic.j2ee.descriptor.ConnectorBean;
import weblogic.j2ee.descriptor.InboundResourceAdapterBean;
import weblogic.j2ee.descriptor.MessageAdapterBean;
import weblogic.j2ee.descriptor.MessageListenerBean;
import weblogic.j2ee.descriptor.OutboundResourceAdapterBean;
import weblogic.j2ee.descriptor.ResourceAdapterBean;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.FileSource;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.classloaders.Source;

public class ConnectorModuleExtensionContext extends BaseModuleExtensionContext {
   private GenericClassLoader moduleClassLoader;
   private GenericClassLoader finderClassLoader;
   private RAInstanceManager raInstanceManager;
   private List classFinders;

   public ConnectorModuleExtensionContext(ApplicationContextInternal appCtx, ConnectorModule connectorModule, GenericClassLoader moduleClassLoader, GenericClassLoader finderClassLoader) {
      super(appCtx, appCtx.getModuleContext(connectorModule.getId()), connectorModule, (Environment)null);
      this.moduleClassLoader = moduleClassLoader;
      this.finderClassLoader = finderClassLoader;
      this.classFinders = connectorModule.getClassFinders();
   }

   public void setRAInstanceManager(RAInstanceManager raInstanceManager) {
      this.raInstanceManager = raInstanceManager;
   }

   public List getSources(String relativeURI) {
      List sources = new ArrayList();
      Enumeration e = this.getClassFinder().getSources(relativeURI);
      if (e != null) {
         while(e.hasMoreElements()) {
            Source s = (Source)e.nextElement();
            sources.add(s);
         }
      }

      return sources;
   }

   public Collection getBeanClassNames() {
      return Collections.emptySet();
   }

   public Set getAnnotatedClasses(boolean useTempClassLoader, Class... annos) {
      return Collections.emptySet();
   }

   public ClassInfoFinder getClassInfoFinder() {
      return ((ConnectorModule)this.getExtensibleModule()).getExplodedRar().getAnnotatedClassFinder();
   }

   public GenericClassLoader getConnectorClassLoader() {
      return this.finderClassLoader;
   }

   public RAInstanceManager getRaInstanceManager() {
      if (this.raInstanceManager == null) {
         throw new IllegalArgumentException("The ra instance manager is not set");
      } else {
         return this.raInstanceManager;
      }
   }

   public ClassFinder getClassFinder() {
      MultiClassFinder classFinder = new MultiClassFinder();
      if (this.raInstanceManager == null) {
         return ((ConnectorModule)this.getExtensibleModule()).getExplodedRar().getClassFinder();
      } else {
         if (this.classFinders != null) {
            Iterator var2 = this.classFinders.iterator();

            while(var2.hasNext()) {
               ClassFinder oneClassFinder = (ClassFinder)var2.next();
               classFinder.addFinder(oneClassFinder);
            }
         }

         return classFinder;
      }
   }

   public boolean excludeClass(Class clazz) {
      return this.excludeConnectorClassName(clazz.getName());
   }

   public Collection excludeConnectorClassNames(Collection classNames) {
      Collection nonExcludedClassNames = new ArrayList();
      Iterator var3 = classNames.iterator();

      while(var3.hasNext()) {
         String oneClassName = (String)var3.next();
         if (!this.excludeConnectorClassName(oneClassName)) {
            nonExcludedClassNames.add(oneClassName);
         }
      }

      return nonExcludedClassNames;
   }

   private boolean excludeConnectorClassName(String className) {
      RAInfo raInfo = this.getRaInstanceManager().getRAInfo();
      if (this.isConnectorClass(className, raInfo)) {
         return true;
      } else if (this.isActivation(className, raInfo)) {
         return true;
      } else if (this.isConnectionDefinition(className, raInfo)) {
         return true;
      } else if (this.isConnectionDefinitions(className, raInfo)) {
         return true;
      } else {
         return this.isAdminObject(className, raInfo);
      }
   }

   private boolean isConnectorClass(String className, RAInfo raInfo) {
      ConnectorBean connectorBean = raInfo.getConnectorBean();
      if (connectorBean != null) {
         ResourceAdapterBean resourceAdapterBean = connectorBean.getResourceAdapter();
         if (resourceAdapterBean != null) {
            String resourceAdapterClass = resourceAdapterBean.getResourceAdapterClass();
            if (resourceAdapterClass != null) {
               return className.equals(resourceAdapterClass);
            }
         }
      }

      return false;
   }

   private boolean isManagedConnectionFactoryClass(String className, RAInfo raInfo) {
      ConnectorBean connectorBean = raInfo.getConnectorBean();
      if (connectorBean != null) {
         ResourceAdapterBean resourceAdapterBean = connectorBean.getResourceAdapter();
         if (resourceAdapterBean != null) {
            OutboundResourceAdapterBean outboundResourceAdapterBean = resourceAdapterBean.getOutboundResourceAdapter();
            if (outboundResourceAdapterBean != null) {
               ConnectionDefinitionBean[] connectionDefinitionBeans = outboundResourceAdapterBean.getConnectionDefinitions();
               if (connectionDefinitionBeans != null) {
                  ConnectionDefinitionBean[] var7 = connectionDefinitionBeans;
                  int var8 = connectionDefinitionBeans.length;

                  for(int var9 = 0; var9 < var8; ++var9) {
                     ConnectionDefinitionBean oneBean = var7[var9];
                     String managedConnectionFactoryClass = oneBean.getManagedConnectionFactoryClass();
                     if (managedConnectionFactoryClass != null && className.equals(managedConnectionFactoryClass)) {
                        return true;
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   private boolean isConnectionDefinition(String className, RAInfo raInfo) {
      return this.isManagedConnectionFactoryClass(className, raInfo);
   }

   private boolean isConnectionDefinitions(String className, RAInfo raInfo) {
      return this.isManagedConnectionFactoryClass(className, raInfo);
   }

   private boolean isActivation(String className, RAInfo raInfo) {
      ConnectorBean connectorBean = raInfo.getConnectorBean();
      if (connectorBean != null) {
         ResourceAdapterBean resourceAdapterBean = connectorBean.getResourceAdapter();
         if (resourceAdapterBean != null) {
            InboundResourceAdapterBean inboundResourceAdapterBean = resourceAdapterBean.getInboundResourceAdapter();
            if (inboundResourceAdapterBean != null) {
               MessageAdapterBean messageAdapterBean = inboundResourceAdapterBean.getMessageAdapter();
               if (messageAdapterBean != null) {
                  MessageListenerBean[] messageListenerBeans = messageAdapterBean.getMessageListeners();
                  if (messageListenerBeans != null) {
                     MessageListenerBean[] var8 = messageListenerBeans;
                     int var9 = messageListenerBeans.length;

                     for(int var10 = 0; var10 < var9; ++var10) {
                        MessageListenerBean oneBean = var8[var10];
                        ActivationSpecBean activationSpecBean = oneBean.getActivationSpec();
                        if (activationSpecBean != null) {
                           String activationSpecClass = activationSpecBean.getActivationSpecClass();
                           if (activationSpecClass != null && className.equals(activationSpecClass)) {
                              return true;
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   private boolean isAdminObject(String className, RAInfo raInfo) {
      List adminObjects = raInfo.getAdminObjs();
      if (adminObjects != null) {
         Iterator var4 = adminObjects.iterator();

         while(var4.hasNext()) {
            AdminObjInfo oneAdminObject = (AdminObjInfo)var4.next();
            String adminObjClass = oneAdminObject.getAdminObjClass();
            if (adminObjClass != null && className.equals(adminObjClass)) {
               return true;
            }
         }
      }

      return false;
   }

   public String pathToClass(String path, Source source) {
      if (File.separator.equals("\\")) {
         path = path.replace('\\', '/');
      }

      int index = path.lastIndexOf("!");
      if (index >= 0) {
         path = path.substring(index + 1);
      } else if (source instanceof FileSource) {
         String parentDir = null;
         parentDir = ((FileSource)source).getCodeBase();
         if (parentDir != null && parentDir.length() > 0) {
            if (File.separator.equals("\\")) {
               parentDir = parentDir.replace('\\', '/');
            }

            index = path.indexOf(parentDir);
            if (index >= 0) {
               path = path.substring(parentDir.length() + index);
            }
         }
      }

      if (path.startsWith("/")) {
         path = path.substring(1);
      }

      index = path.indexOf(".class");
      if (index >= 0) {
         path = path.substring(0, index);
      }

      return path.replace("/", ".");
   }
}
