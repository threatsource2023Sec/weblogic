package weblogic.j2ee.descriptor.wl;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class ParserFactoryBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ParserFactoryBean beanTreeNode;

   public ParserFactoryBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ParserFactoryBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
   }

   private void initXpaths() throws ConfigurationException {
      List xlist = new ArrayList();
      this.xpaths = (String[])((String[])xlist.toArray(new String[0]));
   }

   private void customInit() throws ConfigurationException {
   }

   public DConfigBean createDConfigBean(DDBean ddb, DConfigBean parent) throws ConfigurationException {
      return null;
   }

   public String keyPropertyValue() {
      return null;
   }

   public void initKeyPropertyValue(String value) {
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getSaxparserFactory() {
      return this.beanTreeNode.getSaxparserFactory();
   }

   public void setSaxparserFactory(String value) {
      this.beanTreeNode.setSaxparserFactory(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SaxparserFactory", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDocumentBuilderFactory() {
      return this.beanTreeNode.getDocumentBuilderFactory();
   }

   public void setDocumentBuilderFactory(String value) {
      this.beanTreeNode.setDocumentBuilderFactory(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DocumentBuilderFactory", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getTransformerFactory() {
      return this.beanTreeNode.getTransformerFactory();
   }

   public void setTransformerFactory(String value) {
      this.beanTreeNode.setTransformerFactory(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TransformerFactory", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getXpathFactory() {
      return this.beanTreeNode.getXpathFactory();
   }

   public void setXpathFactory(String value) {
      this.beanTreeNode.setXpathFactory(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "XpathFactory", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSchemaFactory() {
      return this.beanTreeNode.getSchemaFactory();
   }

   public void setSchemaFactory(String value) {
      this.beanTreeNode.setSchemaFactory(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SchemaFactory", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getXMLInputFactory() {
      return this.beanTreeNode.getXMLInputFactory();
   }

   public void setXMLInputFactory(String value) {
      this.beanTreeNode.setXMLInputFactory(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "XMLInputFactory", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getXMLOutputFactory() {
      return this.beanTreeNode.getXMLOutputFactory();
   }

   public void setXMLOutputFactory(String value) {
      this.beanTreeNode.setXMLOutputFactory(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "XMLOutputFactory", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getXMLEventFactory() {
      return this.beanTreeNode.getXMLEventFactory();
   }

   public void setXMLEventFactory(String value) {
      this.beanTreeNode.setXMLEventFactory(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "XMLEventFactory", (Object)null, (Object)null));
      this.setModified(true);
   }
}
