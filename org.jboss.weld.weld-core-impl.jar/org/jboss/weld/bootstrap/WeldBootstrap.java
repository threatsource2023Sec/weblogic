package org.jboss.weld.bootstrap;

import java.net.URL;
import javax.enterprise.inject.spi.Extension;
import org.jboss.weld.bootstrap.api.Bootstrap;
import org.jboss.weld.bootstrap.api.CDI11Bootstrap;
import org.jboss.weld.bootstrap.api.Environment;
import org.jboss.weld.bootstrap.api.TypeDiscoveryConfiguration;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.bootstrap.spi.BeansXml;
import org.jboss.weld.bootstrap.spi.Deployment;
import org.jboss.weld.config.SystemPropertiesConfiguration;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.ServiceLoader;
import org.jboss.weld.xml.BeansXmlParser;
import org.jboss.weld.xml.BeansXmlStreamParser;
import org.jboss.weld.xml.BeansXmlValidator;

public class WeldBootstrap implements CDI11Bootstrap {
   private WeldStartup weldStartup = new WeldStartup();
   private WeldRuntime weldRuntime;
   private final BeansXmlValidator beansXmlValidator;

   public WeldBootstrap() {
      this.beansXmlValidator = SystemPropertiesConfiguration.INSTANCE.isXmlValidationDisabled() ? null : new BeansXmlValidator();
   }

   public synchronized TypeDiscoveryConfiguration startExtensions(Iterable extensions) {
      return this.weldStartup.startExtensions(extensions);
   }

   public synchronized Bootstrap startContainer(Environment environment, Deployment deployment) {
      return this.startContainer("STATIC_INSTANCE", environment, deployment);
   }

   public synchronized Bootstrap startContainer(String contextId, Environment environment, Deployment deployment) {
      this.weldRuntime = this.weldStartup.startContainer(contextId, environment, deployment);
      return this;
   }

   public synchronized Bootstrap startInitialization() {
      this.checkInitializationNotAlreadyEnded();
      this.weldStartup.startInitialization();
      return this;
   }

   public synchronized Bootstrap deployBeans() {
      this.checkInitializationNotAlreadyEnded();
      this.weldStartup.deployBeans();
      return this;
   }

   public synchronized Bootstrap validateBeans() {
      this.checkInitializationNotAlreadyEnded();
      this.weldStartup.validateBeans();
      return this;
   }

   public synchronized Bootstrap endInitialization() {
      if (this.weldStartup != null) {
         this.weldStartup.endInitialization();
         this.weldStartup = null;
      }

      return this;
   }

   public synchronized BeanManagerImpl getManager(BeanDeploymentArchive beanDeploymentArchive) {
      return this.weldRuntime == null ? null : this.weldRuntime.getManager(beanDeploymentArchive);
   }

   public synchronized void shutdown() {
      if (this.weldRuntime != null) {
         this.weldRuntime.shutdown();
         this.weldRuntime = null;
      }

   }

   public BeansXml parse(Iterable urls) {
      return this.parse(urls, false);
   }

   public BeansXml parse(Iterable urls, boolean removeDuplicates) {
      return BeansXmlParser.merge(urls, this::parse, removeDuplicates);
   }

   public BeansXml parse(URL url) {
      if (this.beansXmlValidator != null) {
         this.beansXmlValidator.validate(url);
      }

      return (new BeansXmlStreamParser(url)).parse();
   }

   public Iterable loadExtensions(ClassLoader classLoader) {
      return ServiceLoader.load(Extension.class, classLoader);
   }

   private void checkInitializationNotAlreadyEnded() {
      if (this.weldStartup == null) {
         throw BootstrapLogger.LOG.callingBootstrapMethodAfterContainerHasBeenInitialized();
      }
   }
}
