package com.oracle.weblogic.lifecycle.config.database;

import com.oracle.weblogic.lifecycle.LifecycleException;
import com.oracle.weblogic.lifecycle.config.PropagationManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.Populator;
import org.glassfish.hk2.api.PopulatorPostProcessor;
import org.glassfish.hk2.api.PostConstruct;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.configuration.api.ConfigurationUtilities;
import org.glassfish.hk2.configuration.hub.api.Hub;
import org.glassfish.hk2.configuration.hub.api.Instance;
import org.glassfish.hk2.configuration.hub.api.Type;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.glassfish.hk2.utilities.ClasspathDescriptorFileFinder;
import org.glassfish.hk2.utilities.DuplicatePostProcessor;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.xml.api.XmlHk2ConfigurationBean;
import org.jvnet.hk2.annotations.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import weblogic.utils.XXEUtils;

@Singleton
@Service(
   name = "XMLConfigPersistencePlugin"
)
public class XMLConfigPersistencePlugin implements LifecyclePersistencePlugin, PostConstruct {
   @Inject
   private Hub hub;
   @Inject
   private ServiceLocator configLocator;
   private Logger logger = Logger.getLogger("LifeCycle");
   private Document doc;
   private static final String[] rootChildren = new String[]{"runtimes", "environments", "tenants", "plugins"};
   File lifecycleConfigFile = null;

   public void postConstruct() {
      DynamicConfigurationService dcs = (DynamicConfigurationService)this.configLocator.getService(DynamicConfigurationService.class, new Annotation[0]);
      Populator populator = dcs.getPopulator();
      ClassLoader classLoader = ClassLoader.getSystemClassLoader();

      try {
         populator.populate(new ClasspathDescriptorFileFinder(classLoader), new PopulatorPostProcessor[]{new DuplicatePostProcessor()});
      } catch (Exception var5) {
         throw new RuntimeException(var5);
      }

      ServiceLocatorUtilities.enableLookupExceptions(this.configLocator);
      ConfigurationUtilities.enableConfigurationSystem(this.configLocator);
   }

   private void getInstances(String typeName, String parentTag, String childTag, String parentKey) throws Exception {
      Type type = this.hub.getCurrentDatabase().getType(typeName);
      if (type != null) {
         Map instances = type.getInstances();
         if (instances.size() != 0) {
            String parentValue;
            Element elem;
            for(Iterator var7 = instances.keySet().iterator(); var7.hasNext(); this.addChild(elem, parentTag, parentValue, parentKey)) {
               String key = (String)var7.next();
               Instance instance = (Instance)instances.get(key);
               parentValue = ConfigUtil.getParentValue(key);
               Map bean = (Map)instance.getBean();
               elem = this.doc.createElement(childTag);
               Iterator var13 = bean.keySet().iterator();

               while(var13.hasNext()) {
                  String attr = (String)var13.next();
                  Object attrValue = bean.get(attr);
                  if (attrValue != null && !attr.equalsIgnoreCase("properties") && attrValue instanceof String) {
                     elem.setAttribute(attr, attrValue.toString());
                  }
               }

               Object attrValue = bean.get("properties");
               if (attrValue != null) {
                  Map props = (Map)attrValue;
                  Iterator var20 = props.keySet().iterator();

                  while(var20.hasNext()) {
                     String propName = (String)var20.next();
                     Element propertyElement = this.doc.createElement("property");
                     propertyElement.setAttribute("name", propName);
                     propertyElement.setAttribute("value", props.get(propName).toString());
                     elem.appendChild(propertyElement);
                  }
               }
            }

         }
      }
   }

   private void addChild(Element elem, String parentTag, String parentValue, String parentKey) throws LifecycleException {
      NodeList parentList = this.doc.getElementsByTagName(parentTag);
      if (parentList.getLength() == 1) {
         parentList.item(0).appendChild(elem);
      } else if (parentValue != null && parentKey != null) {
         for(int i = 0; i < parentList.getLength(); ++i) {
            Node parentNode = parentList.item(i);
            Node parentKeyNode = parentNode.getAttributes().getNamedItem(parentKey);
            if (parentKeyNode != null && parentValue.equalsIgnoreCase(parentKeyNode.getNodeValue())) {
               parentNode.appendChild(elem);
               return;
            }
         }

         throw new LifecycleException("Cannot add ..." + elem.getTagName() + " TO " + parentKey + " " + parentValue);
      }
   }

   public void persist(List changes) throws Exception {
      if (changes.size() != 0) {
         DocumentBuilderFactory builderFactory = XXEUtils.createDocumentBuilderFactoryInstance();
         DocumentBuilder builder = builderFactory.newDocumentBuilder();
         this.doc = builder.newDocument();
         Element rootElement = this.doc.createElement("lifecycle-config");
         this.doc.appendChild(rootElement);
         String[] var5 = rootChildren;
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String rootChild = var5[var7];
            rootElement.appendChild(this.doc.createElement(rootChild));
         }

         this.getInstances("/lifecycle-config/runtimes/runtime", "runtimes", "runtime", (String)null);
         this.getInstances("/lifecycle-config/runtimes/runtime/partition", "runtime", "partition", "name");
         this.getInstances("/lifecycle-config/environments/environment", "environments", "environment", (String)null);
         this.getInstances("/lifecycle-config/environments/environment/partition-ref", "environment", "partition-ref", "name");
         this.getInstances("/lifecycle-config/environments/environment/association", "environment", "association", "name");
         this.getInstances("/lifecycle-config/tenants/tenant", "tenants", "tenant", (String)null);
         this.getInstances("/lifecycle-config/tenants/tenant/service", "tenant", "service", "name");
         this.getInstances("/lifecycle-config/tenants/tenant/service/pdb", "service", "pdb", "id");
         if (this.lifecycleConfigFile == null) {
            this.lifecycleConfigFile = ConfigUtil.getConfigFile();
         }

         this.save(new FileOutputStream(this.lifecycleConfigFile));
         PropagationManager pmgr = (PropagationManager)this.configLocator.getService(PropagationManager.class, new Annotation[0]);
         if (pmgr != null && pmgr.isEnabled()) {
            pmgr.propagate(this.lifecycleConfigFile);
         }

      }
   }

   public void load() throws Exception {
      Filter filter = BuilderHelper.createContractFilter(XmlHk2ConfigurationBean.class.getName());
      ServiceLocatorUtilities.removeFilter(this.configLocator, filter, true);
      this.lifecycleConfigFile = ConfigUtil.loadXML(this.hub, this.configLocator);
   }

   public void save(OutputStream out) throws IOException, TransformerException {
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer transformer = tf.newTransformer();
      transformer.setOutputProperty("omit-xml-declaration", "no");
      transformer.setOutputProperty("method", "xml");
      transformer.setOutputProperty("indent", "yes");
      transformer.setOutputProperty("encoding", "UTF-8");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
      transformer.transform(new DOMSource(this.doc), new StreamResult(new OutputStreamWriter(out, "UTF-8")));
   }
}
