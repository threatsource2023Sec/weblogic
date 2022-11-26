package com.bea.core.repackaged.springframework.beans.factory.xml;

import com.bea.core.repackaged.springframework.beans.BeanUtils;
import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import com.bea.core.repackaged.springframework.beans.factory.parsing.EmptyReaderEventListener;
import com.bea.core.repackaged.springframework.beans.factory.parsing.FailFastProblemReporter;
import com.bea.core.repackaged.springframework.beans.factory.parsing.NullSourceExtractor;
import com.bea.core.repackaged.springframework.beans.factory.parsing.ProblemReporter;
import com.bea.core.repackaged.springframework.beans.factory.parsing.ReaderEventListener;
import com.bea.core.repackaged.springframework.beans.factory.parsing.SourceExtractor;
import com.bea.core.repackaged.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.core.Constants;
import com.bea.core.repackaged.springframework.core.NamedThreadLocal;
import com.bea.core.repackaged.springframework.core.io.DescriptiveResource;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.io.ResourceLoader;
import com.bea.core.repackaged.springframework.core.io.support.EncodedResource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.xml.SimpleSaxErrorHandler;
import com.bea.core.repackaged.springframework.util.xml.XmlValidationModeDetector;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
   public static final int VALIDATION_NONE = 0;
   public static final int VALIDATION_AUTO = 1;
   public static final int VALIDATION_DTD = 2;
   public static final int VALIDATION_XSD = 3;
   private static final Constants constants = new Constants(XmlBeanDefinitionReader.class);
   private int validationMode = 1;
   private boolean namespaceAware = false;
   private Class documentReaderClass = DefaultBeanDefinitionDocumentReader.class;
   private ProblemReporter problemReporter = new FailFastProblemReporter();
   private ReaderEventListener eventListener = new EmptyReaderEventListener();
   private SourceExtractor sourceExtractor = new NullSourceExtractor();
   @Nullable
   private NamespaceHandlerResolver namespaceHandlerResolver;
   private DocumentLoader documentLoader = new DefaultDocumentLoader();
   @Nullable
   private EntityResolver entityResolver;
   private ErrorHandler errorHandler;
   private final XmlValidationModeDetector validationModeDetector;
   private final ThreadLocal resourcesCurrentlyBeingLoaded;

   public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
      super(registry);
      this.errorHandler = new SimpleSaxErrorHandler(this.logger);
      this.validationModeDetector = new XmlValidationModeDetector();
      this.resourcesCurrentlyBeingLoaded = new NamedThreadLocal("XML bean definition resources currently being loaded");
   }

   public void setValidating(boolean validating) {
      this.validationMode = validating ? 1 : 0;
      this.namespaceAware = !validating;
   }

   public void setValidationModeName(String validationModeName) {
      this.setValidationMode(constants.asNumber(validationModeName).intValue());
   }

   public void setValidationMode(int validationMode) {
      this.validationMode = validationMode;
   }

   public int getValidationMode() {
      return this.validationMode;
   }

   public void setNamespaceAware(boolean namespaceAware) {
      this.namespaceAware = namespaceAware;
   }

   public boolean isNamespaceAware() {
      return this.namespaceAware;
   }

   public void setProblemReporter(@Nullable ProblemReporter problemReporter) {
      this.problemReporter = (ProblemReporter)(problemReporter != null ? problemReporter : new FailFastProblemReporter());
   }

   public void setEventListener(@Nullable ReaderEventListener eventListener) {
      this.eventListener = (ReaderEventListener)(eventListener != null ? eventListener : new EmptyReaderEventListener());
   }

   public void setSourceExtractor(@Nullable SourceExtractor sourceExtractor) {
      this.sourceExtractor = (SourceExtractor)(sourceExtractor != null ? sourceExtractor : new NullSourceExtractor());
   }

   public void setNamespaceHandlerResolver(@Nullable NamespaceHandlerResolver namespaceHandlerResolver) {
      this.namespaceHandlerResolver = namespaceHandlerResolver;
   }

   public void setDocumentLoader(@Nullable DocumentLoader documentLoader) {
      this.documentLoader = (DocumentLoader)(documentLoader != null ? documentLoader : new DefaultDocumentLoader());
   }

   public void setEntityResolver(@Nullable EntityResolver entityResolver) {
      this.entityResolver = entityResolver;
   }

   protected EntityResolver getEntityResolver() {
      if (this.entityResolver == null) {
         ResourceLoader resourceLoader = this.getResourceLoader();
         if (resourceLoader != null) {
            this.entityResolver = new ResourceEntityResolver(resourceLoader);
         } else {
            this.entityResolver = new DelegatingEntityResolver(this.getBeanClassLoader());
         }
      }

      return this.entityResolver;
   }

   public void setErrorHandler(ErrorHandler errorHandler) {
      this.errorHandler = errorHandler;
   }

   public void setDocumentReaderClass(Class documentReaderClass) {
      this.documentReaderClass = documentReaderClass;
   }

   public int loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException {
      return this.loadBeanDefinitions(new EncodedResource(resource));
   }

   public int loadBeanDefinitions(EncodedResource encodedResource) throws BeanDefinitionStoreException {
      Assert.notNull(encodedResource, (String)"EncodedResource must not be null");
      if (this.logger.isTraceEnabled()) {
         this.logger.trace("Loading XML bean definitions from " + encodedResource);
      }

      Set currentResources = (Set)this.resourcesCurrentlyBeingLoaded.get();
      if (currentResources == null) {
         currentResources = new HashSet(4);
         this.resourcesCurrentlyBeingLoaded.set(currentResources);
      }

      if (!((Set)currentResources).add(encodedResource)) {
         throw new BeanDefinitionStoreException("Detected cyclic loading of " + encodedResource + " - check your import definitions!");
      } else {
         int var5;
         try {
            InputStream inputStream = encodedResource.getResource().getInputStream();

            try {
               InputSource inputSource = new InputSource(inputStream);
               if (encodedResource.getEncoding() != null) {
                  inputSource.setEncoding(encodedResource.getEncoding());
               }

               var5 = this.doLoadBeanDefinitions(inputSource, encodedResource.getResource());
            } finally {
               inputStream.close();
            }
         } catch (IOException var15) {
            throw new BeanDefinitionStoreException("IOException parsing XML document from " + encodedResource.getResource(), var15);
         } finally {
            ((Set)currentResources).remove(encodedResource);
            if (((Set)currentResources).isEmpty()) {
               this.resourcesCurrentlyBeingLoaded.remove();
            }

         }

         return var5;
      }
   }

   public int loadBeanDefinitions(InputSource inputSource) throws BeanDefinitionStoreException {
      return this.loadBeanDefinitions(inputSource, "resource loaded through SAX InputSource");
   }

   public int loadBeanDefinitions(InputSource inputSource, @Nullable String resourceDescription) throws BeanDefinitionStoreException {
      return this.doLoadBeanDefinitions(inputSource, new DescriptiveResource(resourceDescription));
   }

   protected int doLoadBeanDefinitions(InputSource inputSource, Resource resource) throws BeanDefinitionStoreException {
      try {
         Document doc = this.doLoadDocument(inputSource, resource);
         int count = this.registerBeanDefinitions(doc, resource);
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Loaded " + count + " bean definitions from " + resource);
         }

         return count;
      } catch (BeanDefinitionStoreException var5) {
         throw var5;
      } catch (SAXParseException var6) {
         throw new XmlBeanDefinitionStoreException(resource.getDescription(), "Line " + var6.getLineNumber() + " in XML document from " + resource + " is invalid", var6);
      } catch (SAXException var7) {
         throw new XmlBeanDefinitionStoreException(resource.getDescription(), "XML document from " + resource + " is invalid", var7);
      } catch (ParserConfigurationException var8) {
         throw new BeanDefinitionStoreException(resource.getDescription(), "Parser configuration exception parsing XML from " + resource, var8);
      } catch (IOException var9) {
         throw new BeanDefinitionStoreException(resource.getDescription(), "IOException parsing XML document from " + resource, var9);
      } catch (Throwable var10) {
         throw new BeanDefinitionStoreException(resource.getDescription(), "Unexpected exception parsing XML document from " + resource, var10);
      }
   }

   protected Document doLoadDocument(InputSource inputSource, Resource resource) throws Exception {
      return this.documentLoader.loadDocument(inputSource, this.getEntityResolver(), this.errorHandler, this.getValidationModeForResource(resource), this.isNamespaceAware());
   }

   protected int getValidationModeForResource(Resource resource) {
      int validationModeToUse = this.getValidationMode();
      if (validationModeToUse != 1) {
         return validationModeToUse;
      } else {
         int detectedMode = this.detectValidationMode(resource);
         return detectedMode != 1 ? detectedMode : 3;
      }
   }

   protected int detectValidationMode(Resource resource) {
      if (resource.isOpen()) {
         throw new BeanDefinitionStoreException("Passed-in Resource [" + resource + "] contains an open stream: cannot determine validation mode automatically. Either pass in a Resource that is able to create fresh streams, or explicitly specify the validationMode on your XmlBeanDefinitionReader instance.");
      } else {
         InputStream inputStream;
         try {
            inputStream = resource.getInputStream();
         } catch (IOException var5) {
            throw new BeanDefinitionStoreException("Unable to determine validation mode for [" + resource + "]: cannot open InputStream. Did you attempt to load directly from a SAX InputSource without specifying the validationMode on your XmlBeanDefinitionReader instance?", var5);
         }

         try {
            return this.validationModeDetector.detectValidationMode(inputStream);
         } catch (IOException var4) {
            throw new BeanDefinitionStoreException("Unable to determine validation mode for [" + resource + "]: an error occurred whilst reading from the InputStream.", var4);
         }
      }
   }

   public int registerBeanDefinitions(Document doc, Resource resource) throws BeanDefinitionStoreException {
      BeanDefinitionDocumentReader documentReader = this.createBeanDefinitionDocumentReader();
      int countBefore = this.getRegistry().getBeanDefinitionCount();
      documentReader.registerBeanDefinitions(doc, this.createReaderContext(resource));
      return this.getRegistry().getBeanDefinitionCount() - countBefore;
   }

   protected BeanDefinitionDocumentReader createBeanDefinitionDocumentReader() {
      return (BeanDefinitionDocumentReader)BeanUtils.instantiateClass(this.documentReaderClass);
   }

   public XmlReaderContext createReaderContext(Resource resource) {
      return new XmlReaderContext(resource, this.problemReporter, this.eventListener, this.sourceExtractor, this, this.getNamespaceHandlerResolver());
   }

   public NamespaceHandlerResolver getNamespaceHandlerResolver() {
      if (this.namespaceHandlerResolver == null) {
         this.namespaceHandlerResolver = this.createDefaultNamespaceHandlerResolver();
      }

      return this.namespaceHandlerResolver;
   }

   protected NamespaceHandlerResolver createDefaultNamespaceHandlerResolver() {
      ClassLoader cl = this.getResourceLoader() != null ? this.getResourceLoader().getClassLoader() : this.getBeanClassLoader();
      return new DefaultNamespaceHandlerResolver(cl);
   }
}
