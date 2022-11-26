package com.bea.core.repackaged.springframework.beans.factory.xml;

import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.parsing.ProblemReporter;
import com.bea.core.repackaged.springframework.beans.factory.parsing.ReaderContext;
import com.bea.core.repackaged.springframework.beans.factory.parsing.ReaderEventListener;
import com.bea.core.repackaged.springframework.beans.factory.parsing.SourceExtractor;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.core.env.Environment;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.io.ResourceLoader;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.StringReader;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class XmlReaderContext extends ReaderContext {
   private final XmlBeanDefinitionReader reader;
   private final NamespaceHandlerResolver namespaceHandlerResolver;

   public XmlReaderContext(Resource resource, ProblemReporter problemReporter, ReaderEventListener eventListener, SourceExtractor sourceExtractor, XmlBeanDefinitionReader reader, NamespaceHandlerResolver namespaceHandlerResolver) {
      super(resource, problemReporter, eventListener, sourceExtractor);
      this.reader = reader;
      this.namespaceHandlerResolver = namespaceHandlerResolver;
   }

   public final XmlBeanDefinitionReader getReader() {
      return this.reader;
   }

   public final BeanDefinitionRegistry getRegistry() {
      return this.reader.getRegistry();
   }

   @Nullable
   public final ResourceLoader getResourceLoader() {
      return this.reader.getResourceLoader();
   }

   @Nullable
   public final ClassLoader getBeanClassLoader() {
      return this.reader.getBeanClassLoader();
   }

   public final Environment getEnvironment() {
      return this.reader.getEnvironment();
   }

   public final NamespaceHandlerResolver getNamespaceHandlerResolver() {
      return this.namespaceHandlerResolver;
   }

   public String generateBeanName(BeanDefinition beanDefinition) {
      return this.reader.getBeanNameGenerator().generateBeanName(beanDefinition, this.getRegistry());
   }

   public String registerWithGeneratedName(BeanDefinition beanDefinition) {
      String generatedName = this.generateBeanName(beanDefinition);
      this.getRegistry().registerBeanDefinition(generatedName, beanDefinition);
      return generatedName;
   }

   public Document readDocumentFromString(String documentContent) {
      InputSource is = new InputSource(new StringReader(documentContent));

      try {
         return this.reader.doLoadDocument(is, this.getResource());
      } catch (Exception var4) {
         throw new BeanDefinitionStoreException("Failed to read XML document", var4);
      }
   }
}
