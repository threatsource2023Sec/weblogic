package net.shibboleth.utilities.java.support.xml;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.annotation.constraint.NullableElements;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.component.AbstractInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@ThreadSafe
public class BasicParserPool extends AbstractInitializableComponent implements ParserPool {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(BasicParserPool.class);
   @Nullable
   private String securityManagerAttributeName;
   private DocumentBuilderFactory builderFactory;
   @Nonnull
   @NotEmpty
   private final Stack builderPool = new Stack();
   private int maxPoolSize = 5;
   @Nonnull
   private Map builderAttributes = Collections.emptyMap();
   private boolean coalescing = true;
   private boolean expandEntityReferences = false;
   @Nonnull
   private Map builderFeatures = this.buildDefaultFeatures();
   private boolean ignoreComments = true;
   private boolean ignoreElementContentWhitespace = true;
   private boolean namespaceAware = true;
   private Schema schema = null;
   private boolean dtdValidating = false;
   private boolean xincludeAware = false;
   private EntityResolver entityResolver;
   private ErrorHandler errorHandler;

   public BasicParserPool() {
      this.errorHandler = new LoggingErrorHandler(this.log);
   }

   @Nonnull
   public DocumentBuilder getBuilder() throws XMLParserException {
      this.checkInitializedNotDestroyed();
      DocumentBuilder builder = null;
      synchronized(this.builderPool) {
         while(builder == null && !this.builderPool.isEmpty()) {
            builder = (DocumentBuilder)((SoftReference)this.builderPool.pop()).get();
         }
      }

      if (builder == null) {
         builder = this.createBuilder();
      }

      if (builder != null) {
         this.prepareBuilder(builder);
         return new DocumentBuilderProxy(builder, this);
      } else {
         throw new XMLParserException("Unable to obtain a DocumentBuilder");
      }
   }

   public void returnBuilder(@Nullable DocumentBuilder builder) {
      this.checkInitializedNotDestroyed();
      if (builder != null && builder instanceof DocumentBuilderProxy) {
         DocumentBuilderProxy proxiedBuilder = (DocumentBuilderProxy)builder;
         if (proxiedBuilder.getOwningPool() == this) {
            synchronized(proxiedBuilder) {
               if (proxiedBuilder.isReturned()) {
                  return;
               }

               proxiedBuilder.setReturned(true);
            }

            DocumentBuilder unwrappedBuilder = proxiedBuilder.getProxiedBuilder();
            unwrappedBuilder.reset();
            SoftReference builderReference = new SoftReference(unwrappedBuilder);
            synchronized(this.builderPool) {
               if (this.builderPool.size() < this.maxPoolSize) {
                  this.builderPool.push(builderReference);
               }

            }
         }
      }
   }

   @Nonnull
   public Document newDocument() throws XMLParserException {
      this.checkInitializedNotDestroyed();
      DocumentBuilder builder = null;

      Document document;
      try {
         builder = this.getBuilder();
         document = builder.newDocument();
      } finally {
         this.returnBuilder(builder);
      }

      if (document == null) {
         throw new XMLParserException("DocumentBuilder returned a null Document");
      } else {
         return document;
      }
   }

   @Nonnull
   public Document parse(@Nonnull InputStream input) throws XMLParserException {
      this.checkInitializedNotDestroyed();
      Constraint.isNotNull(input, "Input stream can not be null");
      DocumentBuilder builder = this.getBuilder();

      Document var4;
      try {
         Document document = builder.parse(input);
         if (document == null) {
            throw new XMLParserException("DocumentBuilder parsed a null Document");
         }

         var4 = document;
      } catch (SAXException var9) {
         throw new XMLParserException("Unable to parse inputstream, it contained invalid XML", var9);
      } catch (IOException var10) {
         throw new XMLParserException("Unable to read data from input stream", var10);
      } finally {
         this.returnBuilder(builder);
      }

      return var4;
   }

   @Nonnull
   public Document parse(@Nonnull Reader input) throws XMLParserException {
      this.checkInitializedNotDestroyed();
      Constraint.isNotNull(input, "Input reader can not be null");
      DocumentBuilder builder = this.getBuilder();

      Document var4;
      try {
         Document document = builder.parse(new InputSource(input));
         if (document == null) {
            throw new XMLParserException("DocumentBuilder parsed a null Document");
         }

         var4 = document;
      } catch (SAXException var9) {
         throw new XMLParserException("Invalid XML", var9);
      } catch (IOException var10) {
         throw new XMLParserException("Unable to read XML from input stream", var10);
      } finally {
         this.returnBuilder(builder);
      }

      return var4;
   }

   public void setSecurityManagerAttributeName(@Nullable String name) {
      this.checkNotInitializedNotDestroyed();
      this.securityManagerAttributeName = StringSupport.trimOrNull(name);
   }

   public int getMaxPoolSize() {
      return this.maxPoolSize;
   }

   public void setMaxPoolSize(int newSize) {
      this.checkNotInitializedNotDestroyed();
      this.maxPoolSize = (int)Constraint.isGreaterThan(0L, (long)newSize, "New maximum pool size must be greater than 0");
   }

   @Nonnull
   @NonnullElements
   public Map getBuilderAttributes() {
      return Collections.unmodifiableMap(this.builderAttributes);
   }

   public void setBuilderAttributes(@Nullable @NullableElements Map newAttributes) {
      this.checkNotInitializedNotDestroyed();
      if (newAttributes == null) {
         this.builderAttributes = Collections.emptyMap();
      } else {
         this.builderAttributes = new HashMap(Maps.filterKeys(newAttributes, Predicates.notNull()));
      }

   }

   public boolean isCoalescing() {
      return this.coalescing;
   }

   public void setCoalescing(boolean isCoalescing) {
      this.checkNotInitializedNotDestroyed();
      this.coalescing = isCoalescing;
   }

   public boolean isExpandEntityReferences() {
      return this.expandEntityReferences;
   }

   public void setExpandEntityReferences(boolean expand) {
      this.checkNotInitializedNotDestroyed();
      this.expandEntityReferences = expand;
   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   public Map getBuilderFeatures() {
      return this.builderFeatures;
   }

   public void setBuilderFeatures(@Nullable @NullableElements Map newFeatures) {
      this.checkNotInitializedNotDestroyed();
      if (newFeatures == null) {
         this.builderFeatures = Collections.emptyMap();
      } else {
         this.builderFeatures = ImmutableMap.copyOf(Maps.filterKeys(newFeatures, Predicates.notNull()));
      }

   }

   public boolean isIgnoreComments() {
      return this.ignoreComments;
   }

   public void setIgnoreComments(boolean ignore) {
      this.checkNotInitializedNotDestroyed();
      this.ignoreComments = ignore;
   }

   public boolean isIgnoreElementContentWhitespace() {
      return this.ignoreElementContentWhitespace;
   }

   public void setIgnoreElementContentWhitespace(boolean ignore) {
      this.checkNotInitializedNotDestroyed();
      this.ignoreElementContentWhitespace = ignore;
   }

   public boolean isNamespaceAware() {
      return this.namespaceAware;
   }

   public void setNamespaceAware(boolean isNamespaceAware) {
      this.checkNotInitializedNotDestroyed();
      this.namespaceAware = isNamespaceAware;
   }

   @Nullable
   public Schema getSchema() {
      return this.schema;
   }

   public void setSchema(@Nullable Schema newSchema) {
      this.checkNotInitializedNotDestroyed();
      this.schema = newSchema;
      if (this.schema != null) {
         this.setNamespaceAware(true);
         this.builderAttributes.remove("http://java.sun.com/xml/jaxp/properties/schemaSource");
         this.builderAttributes.remove("http://java.sun.com/xml/jaxp/properties/schemaLanguage");
      }

   }

   @Nullable
   public EntityResolver getEntityResolver() {
      return this.entityResolver;
   }

   public void setEntityResolver(@Nullable EntityResolver resolver) {
      this.checkNotInitializedNotDestroyed();
      this.entityResolver = resolver;
   }

   @Nonnull
   public ErrorHandler getErrorHandler() {
      return this.errorHandler;
   }

   public void setErrorHandler(@Nonnull ErrorHandler handler) {
      this.checkNotInitializedNotDestroyed();
      this.errorHandler = (ErrorHandler)Constraint.isNotNull(handler, "ErrorHandler may not be null");
   }

   public boolean isDTDValidating() {
      return this.dtdValidating;
   }

   public void setDTDValidating(boolean isValidating) {
      this.checkNotInitializedNotDestroyed();
      this.dtdValidating = isValidating;
   }

   public boolean isXincludeAware() {
      return this.xincludeAware;
   }

   public void setXincludeAware(boolean isXIncludeAware) {
      this.checkNotInitializedNotDestroyed();
      this.xincludeAware = isXIncludeAware;
   }

   protected int getPoolSize() {
      return this.builderPool.size();
   }

   @Nonnull
   protected DocumentBuilder createBuilder() throws XMLParserException {
      this.checkInitializedNotDestroyed();

      try {
         DocumentBuilder builder = this.builderFactory.newDocumentBuilder();
         return builder;
      } catch (ParserConfigurationException var2) {
         this.log.debug("Unable to create new document builder", var2);
         throw new XMLParserException("Unable to create new document builder", var2);
      }
   }

   private void prepareBuilder(@Nonnull DocumentBuilder builder) {
      if (this.entityResolver != null) {
         builder.setEntityResolver(this.entityResolver);
      }

      builder.setErrorHandler(this.errorHandler);
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();

      try {
         DocumentBuilderFactory newFactory = DocumentBuilderFactory.newInstance();
         Iterator i$ = this.builderAttributes.entrySet().iterator();

         Map.Entry feature;
         while(i$.hasNext()) {
            feature = (Map.Entry)i$.next();
            newFactory.setAttribute((String)feature.getKey(), feature.getValue());
         }

         i$ = this.builderFeatures.entrySet().iterator();

         while(i$.hasNext()) {
            feature = (Map.Entry)i$.next();
            if (feature.getKey() != null) {
               newFactory.setFeature((String)feature.getKey(), (Boolean)feature.getValue());
            }
         }

         newFactory.setCoalescing(this.coalescing);
         newFactory.setExpandEntityReferences(this.expandEntityReferences);
         newFactory.setIgnoringComments(this.ignoreComments);
         newFactory.setIgnoringElementContentWhitespace(this.ignoreElementContentWhitespace);
         newFactory.setNamespaceAware(this.namespaceAware);
         newFactory.setSchema(this.schema);
         newFactory.setValidating(this.dtdValidating);
         newFactory.setXIncludeAware(this.xincludeAware);
         this.builderFactory = newFactory;
         if (this.securityManagerAttributeName != null) {
            Object securityManager = this.builderFactory.getAttribute(this.securityManagerAttributeName);
            if (securityManager != null) {
               this.log.info("XMLSecurityManager of type '{}' is installed", securityManager.getClass().getName());
            } else {
               this.log.warn("No XMLSecurityManager installed, system may be vulnerable to XML processing vulnerabilities");
            }
         }

      } catch (ParserConfigurationException var4) {
         throw new ComponentInitializationException("Unable to configure builder factory", var4);
      }
   }

   protected void doDestroy() {
      this.builderPool.clear();
      super.doDestroy();
   }

   private void checkInitializedNotDestroyed() {
      ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
   }

   private void checkNotInitializedNotDestroyed() {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
   }

   protected Map buildDefaultFeatures() {
      HashMap features = new HashMap();
      features.put("http://javax.xml.XMLConstants/feature/secure-processing", true);
      features.put("http://apache.org/xml/features/disallow-doctype-decl", true);
      return features;
   }

   protected class DocumentBuilderProxy extends DocumentBuilder {
      private final DocumentBuilder builder;
      private final ParserPool owningPool;
      private boolean returned;

      public DocumentBuilderProxy(DocumentBuilder target, BasicParserPool owner) {
         this.owningPool = owner;
         this.builder = target;
         this.returned = false;
      }

      public DOMImplementation getDOMImplementation() {
         this.checkValidState();
         return this.builder.getDOMImplementation();
      }

      public Schema getSchema() {
         this.checkValidState();
         return this.builder.getSchema();
      }

      public boolean isNamespaceAware() {
         this.checkValidState();
         return this.builder.isNamespaceAware();
      }

      public boolean isValidating() {
         this.checkValidState();
         return this.builder.isValidating();
      }

      public boolean isXIncludeAware() {
         this.checkValidState();
         return this.builder.isXIncludeAware();
      }

      public Document newDocument() {
         this.checkValidState();
         return this.builder.newDocument();
      }

      public Document parse(File f) throws SAXException, IOException {
         this.checkValidState();
         return this.builder.parse(f);
      }

      public Document parse(InputSource is) throws SAXException, IOException {
         this.checkValidState();
         return this.builder.parse(is);
      }

      public Document parse(InputStream is) throws SAXException, IOException {
         this.checkValidState();
         return this.builder.parse(is);
      }

      public Document parse(InputStream is, String systemId) throws SAXException, IOException {
         this.checkValidState();
         return this.builder.parse(is, systemId);
      }

      public Document parse(String uri) throws SAXException, IOException {
         this.checkValidState();
         return this.builder.parse(uri);
      }

      public void reset() {
      }

      public void setEntityResolver(EntityResolver er) {
         this.checkValidState();
      }

      public void setErrorHandler(ErrorHandler eh) {
         this.checkValidState();
      }

      protected ParserPool getOwningPool() {
         return this.owningPool;
      }

      protected DocumentBuilder getProxiedBuilder() {
         return this.builder;
      }

      protected boolean isReturned() {
         return this.returned;
      }

      protected void setReturned(boolean isReturned) {
         this.returned = isReturned;
      }

      protected void checkValidState() {
         if (this.isReturned()) {
            throw new IllegalStateException("DocumentBuilderProxy has already been returned to its owning pool");
         }
      }

      protected void finalize() throws Throwable {
         super.finalize();
         this.owningPool.returnBuilder(this);
      }
   }
}
