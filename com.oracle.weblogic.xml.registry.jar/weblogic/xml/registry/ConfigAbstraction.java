package weblogic.xml.registry;

import java.beans.PropertyChangeListener;
import java.util.Enumeration;
import weblogic.j2ee.descriptor.wl.EntityMappingBean;
import weblogic.j2ee.descriptor.wl.XmlBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.XMLEntitySpecRegistryEntryMBean;
import weblogic.management.configuration.XMLParserSelectRegistryEntryMBean;
import weblogic.management.configuration.XMLRegistryEntryMBean;
import weblogic.management.configuration.XMLRegistryMBean;

class ConfigAbstraction {
   static ConfigAbstraction singularInstance = new ConfigAbstraction();

   static RegistryConfig getRegistryConfig(XMLRegistryMBean mBean) {
      return singularInstance.new RegistryMBeanConfigImpl(mBean);
   }

   static ParserSelectEntryConfig getParserSelectEntryConfig(XMLParserSelectRegistryEntryMBean mBean) {
      return singularInstance.new ParserSelectEntryMBeanConfigImpl(mBean);
   }

   static EntitySpecEntryConfig getEntitySpecEntryConfig(XMLEntitySpecRegistryEntryMBean mBean) {
      return singularInstance.new EntitySpecEntryMBeanConfigImpl(mBean);
   }

   static RegistryConfig getRegistryConfig(XmlBean xmlDD, String applicationName) {
      return singularInstance.new RegistryXMLDDConfigImpl(xmlDD, applicationName);
   }

   class EntityIterator extends ArrayIterator {
      EntityIterator(XMLRegistryEntryMBean[] array) {
         super(array, true);
      }

      public Object nextElement() {
         return this.isMBean ? ConfigAbstraction.this.new OldEntryMBeanConfigImpl((XMLRegistryEntryMBean)super.nextElement()) : null;
      }
   }

   class EntitySpecEntryIterator extends ArrayIterator {
      EntitySpecEntryIterator(XMLEntitySpecRegistryEntryMBean[] array) {
         super(array, true);
      }

      EntitySpecEntryIterator(EntityMappingBean[] array) {
         super(array, false);
      }

      public Object nextElement() {
         return this.isMBean ? ConfigAbstraction.this.new EntitySpecEntryMBeanConfigImpl((XMLEntitySpecRegistryEntryMBean)super.nextElement()) : ConfigAbstraction.this.new EntitySpecEntryDescriptorMBeanConfigImpl((EntityMappingBean)super.nextElement());
      }
   }

   class ParserSelectEntryIterator extends ArrayIterator {
      ParserSelectEntryIterator(XMLParserSelectRegistryEntryMBean[] array) {
         super(array, true);
      }

      public Object nextElement() {
         return this.isMBean ? ConfigAbstraction.this.new ParserSelectEntryMBeanConfigImpl((XMLParserSelectRegistryEntryMBean)super.nextElement()) : null;
      }
   }

   abstract class ArrayIterator implements Enumeration {
      boolean isMBean = true;
      Object[] array = null;
      int index = -1;

      ArrayIterator(Object[] array, boolean isMBean) {
         this.array = array;
         this.isMBean = isMBean;
      }

      public boolean hasMoreElements() {
         return this.index + 1 < this.array.length;
      }

      public Object nextElement() {
         ++this.index;
         return this.array[this.index];
      }
   }

   class RegistryXMLDDConfigImpl extends GenericConfigAppBasedImpl implements RegistryConfig {
      XmlBean xmlDD = null;

      RegistryXMLDDConfigImpl(XmlBean xmlDD, String applicationName) {
         super(applicationName);
         this.xmlDD = xmlDD;
      }

      public void addPropertyChangeListener(PropertyChangeListener listener) {
      }

      public void removePropertyChangeListener(PropertyChangeListener listener) {
      }

      public String getDocumentBuilderFactory() {
         return this.xmlDD.getParserFactory() == null ? null : this.xmlDD.getParserFactory().getDocumentBuilderFactory();
      }

      public String getTransformerFactory() {
         return this.xmlDD.getParserFactory() == null ? null : this.xmlDD.getParserFactory().getTransformerFactory();
      }

      public String getSAXParserFactory() {
         return this.xmlDD.getParserFactory() == null ? null : this.xmlDD.getParserFactory().getSaxparserFactory();
      }

      public String getXPathFactory() {
         return this.xmlDD.getParserFactory() == null ? null : this.xmlDD.getParserFactory().getXpathFactory();
      }

      public String getSchemaFactory() {
         return this.xmlDD.getParserFactory() == null ? null : this.xmlDD.getParserFactory().getSchemaFactory();
      }

      public String getXMLInputFactory() {
         return this.xmlDD.getParserFactory() == null ? null : this.xmlDD.getParserFactory().getXMLInputFactory();
      }

      public String getXMLOutputFactory() {
         return this.xmlDD.getParserFactory() == null ? null : this.xmlDD.getParserFactory().getXMLOutputFactory();
      }

      public String getXMLEventFactory() {
         return this.xmlDD.getParserFactory() == null ? null : this.xmlDD.getParserFactory().getXMLEventFactory();
      }

      public String getWhenToCache() {
         return null;
      }

      public String getHandleEntityInvalidation() {
         return null;
      }

      public Enumeration getParserSelectRegistryEntries() {
         return ConfigAbstraction.this.new ParserSelectEntryIterator((XMLParserSelectRegistryEntryMBean[])null);
      }

      public Enumeration getEntitySpecRegistryEntries() {
         return this.xmlDD.getEntityMappings() != null ? ConfigAbstraction.this.new EntitySpecEntryIterator(this.xmlDD.getEntityMappings()) : null;
      }

      public Enumeration getRegistryEntries() {
         return ConfigAbstraction.this.new EntityIterator((XMLRegistryEntryMBean[])null);
      }
   }

   abstract class GenericConfigAppBasedImpl {
      String applicationName;

      GenericConfigAppBasedImpl(String applicationName) {
         this.applicationName = applicationName;
      }

      public String toString() {
         return "ConfigAbstraction:" + this.applicationName;
      }

      public String getName() {
         return this.applicationName + ".XMLRegistry";
      }

      public void addPropertyChangeListener(PropertyChangeListener l) {
      }

      public void removePropertyChangeListener(PropertyChangeListener l) {
      }
   }

   class OldEntryMBeanConfigImpl extends GenericMBeanConfigImpl implements OldEntryConfig {
      XMLRegistryEntryMBean mBean = null;

      OldEntryMBeanConfigImpl(XMLRegistryEntryMBean mBean) {
         super(mBean);
         this.mBean = mBean;
      }

      public String getPublicId() {
         return this.mBean.getPublicId();
      }

      public String getSystemId() {
         return this.mBean.getSystemId();
      }

      public String getRootElementTag() {
         return this.mBean.getRootElementTag();
      }

      public String getDocumentBuilderFactory() {
         return this.mBean.getDocumentBuilderFactory();
      }

      public String getTransformerFactory() {
         return null;
      }

      public String getSAXParserFactory() {
         return this.mBean.getSAXParserFactory();
      }

      public String getParserClassName() {
         return this.mBean.getParserClassName();
      }

      public String getEntityPath() {
         return this.mBean.getEntityPath();
      }

      public String getXPathFactory() {
         return null;
      }

      public String getSchemaFactory() {
         return null;
      }

      public String getXMLInputFactory() {
         return null;
      }

      public String getXMLOutputFactory() {
         return null;
      }

      public String getXMLEventFactory() {
         return null;
      }
   }

   class EntitySpecEntryDescriptorMBeanConfigImpl implements EntitySpecEntryConfig {
      EntityMappingBean descMBean = null;

      EntitySpecEntryDescriptorMBeanConfigImpl(EntityMappingBean descMBean) {
         this.descMBean = descMBean;
      }

      public String getPublicId() {
         return this.descMBean.getPublicId();
      }

      public String getSystemId() {
         return this.descMBean.getSystemId();
      }

      public String getWhenToCache() {
         return this.descMBean.getWhenToCache();
      }

      public String getEntityURI() {
         return this.descMBean.getEntityUri();
      }

      public int getCacheTimeoutInterval() {
         return this.descMBean.getCacheTimeoutInterval();
      }

      public String getHandleEntityInvalidation() {
         return null;
      }

      public void addPropertyChangeListener(PropertyChangeListener listener) {
      }

      public void removePropertyChangeListener(PropertyChangeListener listener) {
      }
   }

   class EntitySpecEntryMBeanConfigImpl extends GenericMBeanConfigImpl implements EntitySpecEntryConfig {
      XMLEntitySpecRegistryEntryMBean mBean = null;

      EntitySpecEntryMBeanConfigImpl(XMLEntitySpecRegistryEntryMBean mBean) {
         super(mBean);
         this.mBean = mBean;
      }

      public String getPublicId() {
         return this.mBean.getPublicId();
      }

      public String getSystemId() {
         return this.mBean.getSystemId();
      }

      public String getWhenToCache() {
         return this.mBean.getWhenToCache();
      }

      public String getEntityURI() {
         return this.mBean.getEntityURI();
      }

      public int getCacheTimeoutInterval() {
         return this.mBean.getCacheTimeoutInterval();
      }

      public String getHandleEntityInvalidation() {
         return this.mBean.getHandleEntityInvalidation();
      }
   }

   class ParserSelectEntryMBeanConfigImpl extends GenericMBeanConfigImpl implements ParserSelectEntryConfig {
      XMLParserSelectRegistryEntryMBean mBean = null;

      ParserSelectEntryMBeanConfigImpl(XMLParserSelectRegistryEntryMBean mBean) {
         super(mBean);
         this.mBean = mBean;
      }

      public String getPublicId() {
         return this.mBean.getPublicId();
      }

      public String getSystemId() {
         return this.mBean.getSystemId();
      }

      public String getRootElementTag() {
         return this.mBean.getRootElementTag();
      }

      public String getDocumentBuilderFactory() {
         return this.mBean.getDocumentBuilderFactory();
      }

      public String getTransformerFactory() {
         return null;
      }

      public String getSAXParserFactory() {
         return this.mBean.getSAXParserFactory();
      }

      public String getParserClassName() {
         return this.mBean.getParserClassName();
      }

      public String getXPathFactory() {
         return null;
      }

      public String getSchemaFactory() {
         return null;
      }

      public String getXMLInputFactory() {
         return null;
      }

      public String getXMLOutputFactory() {
         return null;
      }

      public String getXMLEventFactory() {
         return null;
      }
   }

   class RegistryMBeanConfigImpl extends GenericMBeanConfigImpl implements RegistryConfig {
      XMLRegistryMBean mBean = null;

      RegistryMBeanConfigImpl(XMLRegistryMBean mBean) {
         super(mBean);
         this.mBean = mBean;
      }

      public String getDocumentBuilderFactory() {
         return this.mBean.getDocumentBuilderFactory();
      }

      public String getTransformerFactory() {
         return this.mBean.getTransformerFactory();
      }

      public String getSAXParserFactory() {
         return this.mBean.getSAXParserFactory();
      }

      public String getXPathFactory() {
         return this.mBean.getXpathFactory();
      }

      public String getSchemaFactory() {
         return this.mBean.getSchemaFactory();
      }

      public String getXMLInputFactory() {
         return this.mBean.getXMLInputFactory();
      }

      public String getXMLOutputFactory() {
         return this.mBean.getXMLOutputFactory();
      }

      public String getXMLEventFactory() {
         return this.mBean.getXMLEventFactory();
      }

      public String getWhenToCache() {
         return this.mBean.getWhenToCache();
      }

      public String getHandleEntityInvalidation() {
         return this.mBean.isHandleEntityInvalidation() ? "true" : "false";
      }

      public Enumeration getParserSelectRegistryEntries() {
         return ConfigAbstraction.this.new ParserSelectEntryIterator(this.mBean.getParserSelectRegistryEntries());
      }

      public Enumeration getEntitySpecRegistryEntries() {
         return ConfigAbstraction.this.new EntitySpecEntryIterator(this.mBean.getEntitySpecRegistryEntries());
      }

      public Enumeration getRegistryEntries() {
         return ConfigAbstraction.this.new EntityIterator(this.mBean.getRegistryEntries());
      }

      public String getName() {
         return this.mBean.getName();
      }
   }

   abstract class GenericMBeanConfigImpl {
      ConfigurationMBean configurationMBean = null;

      GenericMBeanConfigImpl(ConfigurationMBean configurationMBean) {
         this.configurationMBean = configurationMBean;
      }

      public void addPropertyChangeListener(PropertyChangeListener listener) {
         this.configurationMBean.addPropertyChangeListener(listener);
      }

      public void removePropertyChangeListener(PropertyChangeListener listener) {
         try {
            this.configurationMBean.removePropertyChangeListener(listener);
         } catch (Exception var3) {
         }

      }

      public String toString() {
         return this.configurationMBean.toString();
      }

      public ConfigurationMBean getMBean() {
         return this.configurationMBean;
      }
   }

   interface RegistryConfig extends JAXPConfig, CacheConfig {
      String getName();

      Enumeration getParserSelectRegistryEntries();

      Enumeration getEntitySpecRegistryEntries();

      Enumeration getRegistryEntries();
   }

   interface OldEntryConfig extends EntryConfig, JAXPConfig {
      String getRootElementTag();

      String getParserClassName();

      String getEntityPath();

      ConfigurationMBean getMBean();
   }

   interface EntitySpecEntryConfig extends EntryConfig, CacheConfig {
      String getEntityURI();

      int getCacheTimeoutInterval();
   }

   interface ParserSelectEntryConfig extends EntryConfig, JAXPConfig {
      String getRootElementTag();

      String getParserClassName();
   }

   interface CacheConfig extends GenericConfig {
      String getWhenToCache();

      String getHandleEntityInvalidation();
   }

   interface EntryConfig extends GenericConfig {
      String getPublicId();

      String getSystemId();
   }

   interface JAXPConfig extends GenericConfig {
      String getDocumentBuilderFactory();

      String getTransformerFactory();

      String getSAXParserFactory();

      String getXPathFactory();

      String getSchemaFactory();

      String getXMLInputFactory();

      String getXMLOutputFactory();

      String getXMLEventFactory();
   }

   interface GenericConfig {
      void addPropertyChangeListener(PropertyChangeListener var1);

      void removePropertyChangeListener(PropertyChangeListener var1);
   }
}
