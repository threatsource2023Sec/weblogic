package com.bea.core.repackaged.springframework.beans.factory.xml;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import com.bea.core.repackaged.springframework.beans.factory.parsing.BeanComponentDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.io.support.ResourcePatternUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ResourceUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashSet;
import java.util.Set;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DefaultBeanDefinitionDocumentReader implements BeanDefinitionDocumentReader {
   public static final String BEAN_ELEMENT = "bean";
   public static final String NESTED_BEANS_ELEMENT = "beans";
   public static final String ALIAS_ELEMENT = "alias";
   public static final String NAME_ATTRIBUTE = "name";
   public static final String ALIAS_ATTRIBUTE = "alias";
   public static final String IMPORT_ELEMENT = "import";
   public static final String RESOURCE_ATTRIBUTE = "resource";
   public static final String PROFILE_ATTRIBUTE = "profile";
   protected final Log logger = LogFactory.getLog(this.getClass());
   @Nullable
   private XmlReaderContext readerContext;
   @Nullable
   private BeanDefinitionParserDelegate delegate;

   public void registerBeanDefinitions(Document doc, XmlReaderContext readerContext) {
      this.readerContext = readerContext;
      this.doRegisterBeanDefinitions(doc.getDocumentElement());
   }

   protected final XmlReaderContext getReaderContext() {
      Assert.state(this.readerContext != null, "No XmlReaderContext available");
      return this.readerContext;
   }

   @Nullable
   protected Object extractSource(Element ele) {
      return this.getReaderContext().extractSource(ele);
   }

   protected void doRegisterBeanDefinitions(Element root) {
      BeanDefinitionParserDelegate parent = this.delegate;
      this.delegate = this.createDelegate(this.getReaderContext(), root, parent);
      if (this.delegate.isDefaultNamespace((Node)root)) {
         String profileSpec = root.getAttribute("profile");
         if (StringUtils.hasText(profileSpec)) {
            String[] specifiedProfiles = StringUtils.tokenizeToStringArray(profileSpec, ",; ");
            if (!this.getReaderContext().getEnvironment().acceptsProfiles(specifiedProfiles)) {
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("Skipped XML bean definition file due to specified profiles [" + profileSpec + "] not matching: " + this.getReaderContext().getResource());
               }

               return;
            }
         }
      }

      this.preProcessXml(root);
      this.parseBeanDefinitions(root, this.delegate);
      this.postProcessXml(root);
      this.delegate = parent;
   }

   protected BeanDefinitionParserDelegate createDelegate(XmlReaderContext readerContext, Element root, @Nullable BeanDefinitionParserDelegate parentDelegate) {
      BeanDefinitionParserDelegate delegate = new BeanDefinitionParserDelegate(readerContext);
      delegate.initDefaults(root, parentDelegate);
      return delegate;
   }

   protected void parseBeanDefinitions(Element root, BeanDefinitionParserDelegate delegate) {
      if (delegate.isDefaultNamespace((Node)root)) {
         NodeList nl = root.getChildNodes();

         for(int i = 0; i < nl.getLength(); ++i) {
            Node node = nl.item(i);
            if (node instanceof Element) {
               Element ele = (Element)node;
               if (delegate.isDefaultNamespace((Node)ele)) {
                  this.parseDefaultElement(ele, delegate);
               } else {
                  delegate.parseCustomElement(ele);
               }
            }
         }
      } else {
         delegate.parseCustomElement(root);
      }

   }

   private void parseDefaultElement(Element ele, BeanDefinitionParserDelegate delegate) {
      if (delegate.nodeNameEquals(ele, "import")) {
         this.importBeanDefinitionResource(ele);
      } else if (delegate.nodeNameEquals(ele, "alias")) {
         this.processAliasRegistration(ele);
      } else if (delegate.nodeNameEquals(ele, "bean")) {
         this.processBeanDefinition(ele, delegate);
      } else if (delegate.nodeNameEquals(ele, "beans")) {
         this.doRegisterBeanDefinitions(ele);
      }

   }

   protected void importBeanDefinitionResource(Element ele) {
      String location = ele.getAttribute("resource");
      if (!StringUtils.hasText(location)) {
         this.getReaderContext().error("Resource location must not be empty", ele);
      } else {
         location = this.getReaderContext().getEnvironment().resolveRequiredPlaceholders(location);
         Set actualResources = new LinkedHashSet(4);
         boolean absoluteLocation = false;

         try {
            absoluteLocation = ResourcePatternUtils.isUrl(location) || ResourceUtils.toURI(location).isAbsolute();
         } catch (URISyntaxException var11) {
         }

         int importCount;
         if (absoluteLocation) {
            try {
               importCount = this.getReaderContext().getReader().loadBeanDefinitions(location, actualResources);
               if (this.logger.isTraceEnabled()) {
                  this.logger.trace("Imported " + importCount + " bean definitions from URL location [" + location + "]");
               }
            } catch (BeanDefinitionStoreException var10) {
               this.getReaderContext().error("Failed to import bean definitions from URL location [" + location + "]", ele, var10);
            }
         } else {
            try {
               Resource relativeResource = this.getReaderContext().getResource().createRelative(location);
               if (relativeResource.exists()) {
                  importCount = this.getReaderContext().getReader().loadBeanDefinitions(relativeResource);
                  actualResources.add(relativeResource);
               } else {
                  String baseLocation = this.getReaderContext().getResource().getURL().toString();
                  importCount = this.getReaderContext().getReader().loadBeanDefinitions(StringUtils.applyRelativePath(baseLocation, location), actualResources);
               }

               if (this.logger.isTraceEnabled()) {
                  this.logger.trace("Imported " + importCount + " bean definitions from relative location [" + location + "]");
               }
            } catch (IOException var8) {
               this.getReaderContext().error("Failed to resolve current resource location", ele, var8);
            } catch (BeanDefinitionStoreException var9) {
               this.getReaderContext().error("Failed to import bean definitions from relative location [" + location + "]", ele, var9);
            }
         }

         Resource[] actResArray = (Resource[])actualResources.toArray(new Resource[0]);
         this.getReaderContext().fireImportProcessed(location, actResArray, this.extractSource(ele));
      }
   }

   protected void processAliasRegistration(Element ele) {
      String name = ele.getAttribute("name");
      String alias = ele.getAttribute("alias");
      boolean valid = true;
      if (!StringUtils.hasText(name)) {
         this.getReaderContext().error("Name must not be empty", ele);
         valid = false;
      }

      if (!StringUtils.hasText(alias)) {
         this.getReaderContext().error("Alias must not be empty", ele);
         valid = false;
      }

      if (valid) {
         try {
            this.getReaderContext().getRegistry().registerAlias(name, alias);
         } catch (Exception var6) {
            this.getReaderContext().error("Failed to register alias '" + alias + "' for bean with name '" + name + "'", ele, var6);
         }

         this.getReaderContext().fireAliasRegistered(name, alias, this.extractSource(ele));
      }

   }

   protected void processBeanDefinition(Element ele, BeanDefinitionParserDelegate delegate) {
      BeanDefinitionHolder bdHolder = delegate.parseBeanDefinitionElement(ele);
      if (bdHolder != null) {
         bdHolder = delegate.decorateBeanDefinitionIfRequired(ele, bdHolder);

         try {
            BeanDefinitionReaderUtils.registerBeanDefinition(bdHolder, this.getReaderContext().getRegistry());
         } catch (BeanDefinitionStoreException var5) {
            this.getReaderContext().error("Failed to register bean definition with name '" + bdHolder.getBeanName() + "'", ele, var5);
         }

         this.getReaderContext().fireComponentRegistered(new BeanComponentDefinition(bdHolder));
      }

   }

   protected void preProcessXml(Element root) {
   }

   protected void postProcessXml(Element root) {
   }
}
