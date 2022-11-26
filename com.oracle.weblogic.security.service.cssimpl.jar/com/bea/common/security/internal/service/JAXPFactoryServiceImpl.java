package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.service.JAXPFactoryService;
import com.bea.common.security.servicecfg.JAXPFactoryServiceConfig;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import weblogic.utils.XXEUtils;

public class JAXPFactoryServiceImpl implements JAXPFactoryService, ServiceLifecycleSpi {
   private LoggerSpi logger;
   private static final String STANDARD_DBF_CLASSNAME = "javax.xml.parsers.DocumentBuilderFactory";
   private static final String WLS_DBF_CLASSNAME = "weblogic.xml.jaxp.WebLogicDocumentBuilderFactory";
   private static final String STANDARD_TF_CLASSNAME = "javax.xml.transform.TransformerFactory";
   private static final String WLS_TF_CLASSNAME = "weblogic.xml.jaxp.WebLogicTransformerFactory";
   private Class documentBuilderFactoryClass = null;
   private Method documentBuilderFactoryNewInstance = null;
   private Constructor documentBuilderFactoryConstructor = null;
   private Class transformerFactoryClass = null;
   private Method transformerFactoryNewInstance = null;
   private Constructor transformerFactoryConstructor = null;
   private boolean callDBFNewInstance = true;
   private boolean callTFNewInstance = true;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.JAXPFactoryService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof JAXPFactoryServiceConfig) {
         JAXPFactoryServiceConfig myconfig = (JAXPFactoryServiceConfig)config;
         String documentBuilderFactoryClassName = myconfig.getDocumentBuilderFactoryClassName();
         if (documentBuilderFactoryClassName != null && !"javax.xml.parsers.DocumentBuilderFactory".equals(documentBuilderFactoryClassName)) {
            try {
               this.documentBuilderFactoryClass = Class.forName(documentBuilderFactoryClassName);
               if ("weblogic.xml.jaxp.WebLogicDocumentBuilderFactory".equals(documentBuilderFactoryClassName)) {
                  this.documentBuilderFactoryConstructor = this.documentBuilderFactoryClass.getConstructor((Class[])null);
               } else {
                  this.documentBuilderFactoryNewInstance = this.documentBuilderFactoryClass.getMethod("newInstance", (Class[])null);
               }
            } catch (ClassNotFoundException var11) {
               throw new ServiceInitializationException(var11);
            } catch (NoSuchMethodException var12) {
               throw new ServiceInitializationException(var12);
            }
         }

         String transformerFactoryClassName = myconfig.getTransformerFactoryClassName();
         if (transformerFactoryClassName != null && !"javax.xml.transform.TransformerFactory".equals(transformerFactoryClassName)) {
            try {
               this.transformerFactoryClass = Class.forName(transformerFactoryClassName);
               if ("weblogic.xml.jaxp.WebLogicTransformerFactory".equals(transformerFactoryClassName)) {
                  this.transformerFactoryConstructor = this.transformerFactoryClass.getConstructor((Class[])null);
               } else {
                  this.transformerFactoryNewInstance = this.transformerFactoryClass.getMethod("newInstance", (Class[])null);
               }
            } catch (ClassNotFoundException var9) {
               throw new ServiceInitializationException(var9);
            } catch (NoSuchMethodException var10) {
               throw new ServiceInitializationException(var10);
            }
         }

         return Delegator.getProxy((Class)JAXPFactoryService.class, this);
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getExpectedConfigurationNotSupplied(method, "JAXPFactoryServiceConfig"));
      }
   }

   public void shutdown() {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(this.getClass().getName() + ".shutdown");
      }

   }

   public DocumentBuilderFactory newDocumentBuilderFactory() {
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".newDocumentBuilderFactory";
      if (debug) {
         this.logger.debug(method);
      }

      if (this.documentBuilderFactoryClass == null) {
         if (debug) {
            this.logger.debug(method + " returns factory using standard newInstance");
         }

         return XXEUtils.createDocumentBuilderFactoryInstance();
      } else {
         try {
            if (this.documentBuilderFactoryNewInstance != null) {
               if (debug) {
                  this.logger.debug(method + " returns factory using static newInstance on named class");
               }

               return (DocumentBuilderFactory)this.documentBuilderFactoryNewInstance.invoke((Object)null, (Object[])null);
            }

            if (this.documentBuilderFactoryConstructor != null) {
               if (debug) {
                  this.logger.debug(method + " returns factory using non-standard default constructor");
               }

               return (DocumentBuilderFactory)this.documentBuilderFactoryConstructor.newInstance((Object[])null);
            }
         } catch (InvocationTargetException var4) {
            if (debug) {
               this.logger.debug(method + " failed with " + var4);
            }
         } catch (IllegalAccessException var5) {
            if (debug) {
               this.logger.debug(method + " failed with " + var5);
            }
         } catch (InstantiationException var6) {
            if (debug) {
               this.logger.debug(method + " failed with " + var6);
            }
         }

         return null;
      }
   }

   public TransformerFactory newTransformerFactory() {
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".newTransformerFactory";
      if (debug) {
         this.logger.debug(method);
      }

      if (this.transformerFactoryClass == null) {
         if (debug) {
            this.logger.debug(method + " returns factory using standard newInstance");
         }

         return TransformerFactory.newInstance();
      } else {
         try {
            if (this.transformerFactoryNewInstance != null) {
               if (debug) {
                  this.logger.debug(method + " returns factory using static newInstance on named class");
               }

               return (TransformerFactory)this.transformerFactoryNewInstance.invoke((Object)null, (Object[])null);
            }

            if (this.transformerFactoryConstructor != null) {
               if (debug) {
                  this.logger.debug(method + " returns factory using non-standard default constructor");
               }

               return (TransformerFactory)this.transformerFactoryConstructor.newInstance((Object[])null);
            }
         } catch (InvocationTargetException var4) {
            if (debug) {
               this.logger.debug(method + " failed with " + var4);
            }
         } catch (IllegalAccessException var5) {
            if (debug) {
               this.logger.debug(method + " failed with " + var5);
            }
         } catch (InstantiationException var6) {
            if (debug) {
               this.logger.debug(method + " failed with " + var6);
            }
         }

         return null;
      }
   }
}
