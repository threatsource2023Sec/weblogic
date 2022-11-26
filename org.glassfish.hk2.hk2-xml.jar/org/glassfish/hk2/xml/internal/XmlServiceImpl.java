package org.glassfish.hk2.xml.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.xml.stream.XMLStreamReader;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.IterableProvider;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.Self;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.TwoPhaseResource;
import org.glassfish.hk2.api.Visibility;
import org.glassfish.hk2.configuration.hub.api.Hub;
import org.glassfish.hk2.configuration.hub.api.WriteableBeanDatabase;
import org.glassfish.hk2.utilities.cache.CacheUtilities;
import org.glassfish.hk2.utilities.cache.WeakCARCache;
import org.glassfish.hk2.utilities.reflection.ClassReflectionHelper;
import org.glassfish.hk2.utilities.reflection.Logger;
import org.glassfish.hk2.utilities.reflection.internal.ClassReflectionHelperImpl;
import org.glassfish.hk2.xml.api.XmlHubCommitMessage;
import org.glassfish.hk2.xml.api.XmlRootHandle;
import org.glassfish.hk2.xml.api.XmlService;
import org.glassfish.hk2.xml.jaxb.internal.BaseHK2JAXBBean;
import org.glassfish.hk2.xml.spi.PreGenerationRequirement;
import org.glassfish.hk2.xml.spi.XmlServiceParser;

@Singleton
@Visibility(DescriptorVisibility.LOCAL)
public class XmlServiceImpl implements XmlService {
   public static final boolean DEBUG_PARSING = (Boolean)AccessController.doPrivileged(new PrivilegedAction() {
      public Boolean run() {
         return Boolean.parseBoolean(System.getProperty("org.jvnet.hk2.xmlservice.parser.stream", "false"));
      }
   });
   @Inject
   private ServiceLocator serviceLocator;
   @Inject
   private DynamicConfigurationService dynamicConfigurationService;
   @Inject
   private Hub hub;
   @Inject
   private IterableProvider parser;
   private final ClassReflectionHelper classReflectionHelper = new ClassReflectionHelperImpl();
   private final JAUtilities jaUtilities;
   @Inject
   @Self
   private ActiveDescriptor selfDescriptor;
   private final WeakCARCache packageNamespaceCache;

   public XmlServiceImpl() {
      this.jaUtilities = new JAUtilities(this.classReflectionHelper);
      this.packageNamespaceCache = CacheUtilities.createWeakCARCache(new PackageToNamespaceComputable(), 20, true);
   }

   public XmlRootHandle unmarshal(URI uri, Class jaxbAnnotatedClassOrInterface) {
      return this.unmarshal(uri, jaxbAnnotatedClassOrInterface, true, true);
   }

   public XmlRootHandle unmarshal(InputStream input, Class jaxbAnnotatedInterface) {
      return this.unmarshal(input, jaxbAnnotatedInterface, true, true);
   }

   public XmlRootHandle unmarshal(URI uri, Class jaxbAnnotatedInterface, boolean advertiseInRegistry, boolean advertiseInHub) {
      return this.unmarshal((URI)uri, jaxbAnnotatedInterface, advertiseInRegistry, advertiseInHub, (Map)null);
   }

   public XmlRootHandle unmarshal(URI uri, Class jaxbAnnotatedInterface, boolean advertiseInRegistry, boolean advertiseInHub, Map options) {
      if (uri != null && jaxbAnnotatedInterface != null) {
         if (!jaxbAnnotatedInterface.isInterface()) {
            throw new IllegalArgumentException("Only an interface can be given to unmarshall: " + jaxbAnnotatedInterface.getName());
         } else {
            XmlServiceParser localParser = (XmlServiceParser)this.parser.named(this.selfDescriptor.getName()).get();
            if (localParser == null) {
               throw new IllegalStateException("There is no XmlServiceParser implementation");
            } else {
               try {
                  boolean generateAll = PreGenerationRequirement.MUST_PREGENERATE.equals(localParser.getPreGenerationRequirement());
                  this.jaUtilities.convertRootAndLeaves(jaxbAnnotatedInterface, generateAll);
                  ModelImpl model = this.jaUtilities.getModel(jaxbAnnotatedInterface);
                  return this.unmarshallClass(uri, (InputStream)null, model, localParser, (XMLStreamReader)null, advertiseInRegistry, advertiseInHub, options);
               } catch (RuntimeException var9) {
                  throw var9;
               } catch (Throwable var10) {
                  throw new MultiException(var10);
               }
            }
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   public XmlRootHandle unmarshal(XMLStreamReader reader, Class jaxbAnnotatedInterface, boolean advertiseInRegistry, boolean advertiseInHub) {
      return this.unmarshal((XMLStreamReader)reader, jaxbAnnotatedInterface, advertiseInRegistry, advertiseInHub, (Map)null);
   }

   public XmlRootHandle unmarshal(XMLStreamReader reader, Class jaxbAnnotatedInterface, boolean advertiseInRegistry, boolean advertiseInHub, Map options) {
      if (reader != null && jaxbAnnotatedInterface != null) {
         if (!jaxbAnnotatedInterface.isInterface()) {
            throw new IllegalArgumentException("Only an interface can be given to unmarshall: " + jaxbAnnotatedInterface.getName());
         } else {
            try {
               this.jaUtilities.convertRootAndLeaves(jaxbAnnotatedInterface, false);
               ModelImpl model = this.jaUtilities.getModel(jaxbAnnotatedInterface);
               return this.unmarshallClass((URI)null, (InputStream)null, model, (XmlServiceParser)null, reader, advertiseInRegistry, advertiseInHub, options);
            } catch (RuntimeException var7) {
               throw var7;
            } catch (Throwable var8) {
               throw new MultiException(var8);
            }
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   public XmlRootHandle unmarshal(InputStream input, Class jaxbAnnotatedInterface, boolean advertiseInRegistry, boolean advertiseInHub) {
      return this.unmarshal((InputStream)input, jaxbAnnotatedInterface, advertiseInRegistry, advertiseInHub, (Map)null);
   }

   public XmlRootHandle unmarshal(InputStream input, Class jaxbAnnotatedInterface, boolean advertiseInRegistry, boolean advertiseInHub, Map options) {
      if (input != null && jaxbAnnotatedInterface != null) {
         if (!jaxbAnnotatedInterface.isInterface()) {
            throw new IllegalArgumentException("Only an interface can be given to unmarshall: " + jaxbAnnotatedInterface.getName());
         } else {
            XmlServiceParser localParser = (XmlServiceParser)this.parser.named(this.selfDescriptor.getName()).get();
            if (localParser == null) {
               throw new IllegalStateException("There is no XmlServiceParser implementation");
            } else {
               try {
                  this.jaUtilities.convertRootAndLeaves(jaxbAnnotatedInterface, false);
                  ModelImpl model = this.jaUtilities.getModel(jaxbAnnotatedInterface);
                  return this.unmarshallClass((URI)null, input, model, localParser, (XMLStreamReader)null, advertiseInRegistry, advertiseInHub, options);
               } catch (RuntimeException var8) {
                  throw var8;
               } catch (Throwable var9) {
                  throw new MultiException(var9);
               }
            }
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   private XmlRootHandle unmarshallClass(URI uri, InputStream inputStream, ModelImpl model, XmlServiceParser localParser, XMLStreamReader reader, boolean advertise, boolean advertiseInHub, Map options) throws Exception {
      long elapsedUpToJAXB = 0L;
      if (JAUtilities.DEBUG_GENERATION_TIMING) {
         elapsedUpToJAXB = System.currentTimeMillis();
      }

      Hk2JAXBUnmarshallerListener listener = new Hk2JAXBUnmarshallerListener(this.jaUtilities, this.classReflectionHelper);
      long jaxbUnmarshallElapsedTime = 0L;
      if (JAUtilities.DEBUG_GENERATION_TIMING) {
         jaxbUnmarshallElapsedTime = System.currentTimeMillis();
         elapsedUpToJAXB = jaxbUnmarshallElapsedTime - elapsedUpToJAXB;
         Logger.getLogger().debug("Time up to parsing " + uri + " is " + elapsedUpToJAXB + " milliseconds");
      }

      Object root;
      if (localParser != null) {
         if (uri != null) {
            root = localParser.parseRoot(model, (URI)uri, listener, options);
         } else {
            root = localParser.parseRoot(model, (InputStream)inputStream, listener, options);
         }
      } else {
         root = XmlStreamImpl.parseRoot(this, model, reader, listener);
      }

      long elapsedJAXBToAdvertisement = 0L;
      if (JAUtilities.DEBUG_GENERATION_TIMING) {
         elapsedJAXBToAdvertisement = System.currentTimeMillis();
         jaxbUnmarshallElapsedTime = elapsedJAXBToAdvertisement - jaxbUnmarshallElapsedTime;
         Logger.getLogger().debug("Time parsing " + uri + " is " + jaxbUnmarshallElapsedTime + " milliseconds , now with " + this.jaUtilities.getNumGenerated() + " proxies generated and " + this.jaUtilities.getNumPreGenerated() + " pre generated proxies loaded");
      }

      DynamicChangeInfo changeControl = new DynamicChangeInfo(this.jaUtilities, this.hub, advertiseInHub, this, this.dynamicConfigurationService, advertise, this.serviceLocator);
      XmlRootHandleImpl retVal = new XmlRootHandleImpl(this, this.hub, root, model, uri, advertise, advertiseInHub, changeControl);
      changeControl.setRoot(retVal);
      Iterator var19 = listener.getAllBeans().iterator();

      while(var19.hasNext()) {
         BaseHK2JAXBBean base = (BaseHK2JAXBBean)var19.next();
         String instanceName = Utilities.createInstanceName(base);
         base._setInstanceName(instanceName);
         base._setDynamicChangeInfo(retVal, changeControl);
         if (DEBUG_PARSING) {
            Logger.getLogger().debug("XmlServiceDebug found bean " + base);
         }
      }

      if (DEBUG_PARSING) {
         Logger.getLogger().debug("XmlServiceDebug after parsing all beans in " + uri);
      }

      long elapsedPreAdvertisement = 0L;
      if (JAUtilities.DEBUG_GENERATION_TIMING) {
         elapsedPreAdvertisement = System.currentTimeMillis();
         elapsedJAXBToAdvertisement = elapsedPreAdvertisement - elapsedJAXBToAdvertisement;
         Logger.getLogger().debug("Time from parsing to PreAdvertisement " + uri + " is " + elapsedJAXBToAdvertisement + " milliseconds");
      }

      DynamicConfiguration config = advertise ? this.dynamicConfigurationService.createDynamicConfiguration() : null;
      WriteableBeanDatabase wdb = advertiseInHub ? this.hub.getWriteableDatabaseCopy() : null;
      boolean attachedTransaction = false;
      if (config != null && wdb != null) {
         attachedTransaction = true;
         wdb.setCommitMessage(new XmlHubCommitMessage() {
         });
         config.registerTwoPhaseResources(new TwoPhaseResource[]{wdb.getTwoPhaseResource()});
      }

      LinkedList allBeans = listener.getAllBeans();
      List addedDescriptors = new ArrayList(allBeans.size());
      Iterator var26 = allBeans.iterator();

      while(var26.hasNext()) {
         BaseHK2JAXBBean bean = (BaseHK2JAXBBean)var26.next();
         ActiveDescriptor added = Utilities.advertise(wdb, config, bean);
         if (added != null) {
            addedDescriptors.add(added);
         }
      }

      long elapsedHK2Advertisement = 0L;
      if (JAUtilities.DEBUG_GENERATION_TIMING) {
         elapsedHK2Advertisement = System.currentTimeMillis();
         elapsedPreAdvertisement = elapsedHK2Advertisement - elapsedPreAdvertisement;
         Logger.getLogger().debug("Time from JAXB to PreAdvertisement " + uri + " is " + elapsedPreAdvertisement + " milliseconds");
      }

      if (config != null) {
         config.commit();
      }

      long elapsedHubAdvertisement = 0L;
      if (JAUtilities.DEBUG_GENERATION_TIMING) {
         elapsedHubAdvertisement = System.currentTimeMillis();
         elapsedHK2Advertisement = elapsedHubAdvertisement - elapsedHK2Advertisement;
         Logger.getLogger().debug("Time to advertise " + uri + " in HK2 is " + elapsedHK2Advertisement + " milliseconds");
      }

      if (wdb != null && !attachedTransaction) {
         wdb.commit(new XmlHubCommitMessage() {
         });
      }

      Iterator var30 = addedDescriptors.iterator();

      while(var30.hasNext()) {
         ActiveDescriptor ad = (ActiveDescriptor)var30.next();
         this.serviceLocator.getServiceHandle(ad).getService();
      }

      if (JAUtilities.DEBUG_GENERATION_TIMING) {
         elapsedHubAdvertisement = System.currentTimeMillis() - elapsedHubAdvertisement;
         Logger.getLogger().debug("Time to advertise " + uri + " in Hub is " + elapsedHubAdvertisement + " milliseconds");
      }

      return retVal;
   }

   public XmlRootHandle createEmptyHandle(Class jaxbAnnotatedInterface, boolean advertiseInRegistry, boolean advertiseInHub) {
      if (!jaxbAnnotatedInterface.isInterface()) {
         throw new IllegalArgumentException("Only an interface can be given to unmarshall: " + jaxbAnnotatedInterface.getName());
      } else {
         try {
            this.jaUtilities.convertRootAndLeaves(jaxbAnnotatedInterface, true);
            ModelImpl model = this.jaUtilities.getModel(jaxbAnnotatedInterface);
            DynamicChangeInfo change = new DynamicChangeInfo(this.jaUtilities, this.hub, advertiseInHub, this, this.dynamicConfigurationService, advertiseInRegistry, this.serviceLocator);
            XmlRootHandleImpl retVal = new XmlRootHandleImpl(this, this.hub, (Object)null, model, (URI)null, advertiseInRegistry, advertiseInHub, change);
            change.setRoot(retVal);
            return retVal;
         } catch (RuntimeException var7) {
            throw var7;
         } catch (Exception var8) {
            throw new MultiException(var8);
         }
      }
   }

   public XmlRootHandle createEmptyHandle(Class jaxbAnnotationInterface) {
      return this.createEmptyHandle(jaxbAnnotationInterface, true, true);
   }

   public Object createBean(Class beanInterface) {
      if (!beanInterface.isInterface()) {
         throw new IllegalArgumentException("Only an interface can be given to unmarshall: " + beanInterface.getName());
      } else {
         this.jaUtilities.convertRootAndLeaves(beanInterface, true);
         ModelImpl model = this.jaUtilities.getModel(beanInterface);
         Object retVal = Utilities.createBean(model.getProxyAsClass());
         BaseHK2JAXBBean base = (BaseHK2JAXBBean)retVal;
         base._setClassReflectionHelper(this.classReflectionHelper);
         base._setActive();
         return retVal;
      }
   }

   public void marshal(OutputStream outputStream, XmlRootHandle rootHandle) throws IOException {
      this.marshal(outputStream, rootHandle, (Map)null);
   }

   public void marshal(OutputStream outputStream, XmlRootHandle rootHandle, Map options) throws IOException {
      XmlServiceParser localParser = (XmlServiceParser)this.parser.named(this.selfDescriptor.getName()).get();
      if (localParser == null) {
         throw new IllegalStateException("There is no XmlServiceParser implementation");
      } else {
         XmlRootHandleImpl impl = (XmlRootHandleImpl)rootHandle;
         DynamicChangeInfo changeControl = impl.getChangeInfo();
         if (changeControl == null) {
            throw new IllegalStateException("May not marshal an unfinished rootHandle");
         } else {
            changeControl.getReadLock().lock();

            try {
               localParser.marshal(outputStream, rootHandle, options);
            } finally {
               changeControl.getReadLock().unlock();
            }

         }
      }
   }

   public ClassReflectionHelper getClassReflectionHelper() {
      return this.classReflectionHelper;
   }

   DynamicConfigurationService getDynamicConfigurationService() {
      return this.dynamicConfigurationService;
   }

   public ServiceLocator getServiceLocator() {
      return this.serviceLocator;
   }

   public XmlServiceParser getParser() {
      return (XmlServiceParser)this.parser.named(this.selfDescriptor.getName()).get();
   }

   public Map getPackageNamespace(Class clazz) {
      Package p = clazz.getPackage();
      return (Map)this.packageNamespaceCache.compute(p);
   }

   @PreDestroy
   private void preDestroy() {
      this.packageNamespaceCache.clear();
   }
}
