package weblogic.j2ee.dd.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import org.xml.sax.InputSource;
import weblogic.j2ee.dd.WeblogicDeploymentDescriptor;
import weblogic.management.descriptors.application.weblogic.EjbMBeanImpl;
import weblogic.management.descriptors.application.weblogic.EntityCacheMBeanImpl;
import weblogic.management.descriptors.application.weblogic.EntityMappingMBeanImpl;
import weblogic.management.descriptors.application.weblogic.MaxCacheSizeMBeanImpl;
import weblogic.management.descriptors.application.weblogic.ParserFactoryMBeanImpl;
import weblogic.management.descriptors.application.weblogic.SecurityMBeanImpl;
import weblogic.management.descriptors.application.weblogic.XMLMBeanImpl;
import weblogic.utils.AssertionError;
import weblogic.utils.Debug;
import weblogic.xml.process.Functions;
import weblogic.xml.process.InProcessor;
import weblogic.xml.process.ProcessingContext;
import weblogic.xml.process.ProcessorDriver;
import weblogic.xml.process.SAXProcessorException;
import weblogic.xml.process.SAXValidationException;
import weblogic.xml.process.XMLParsingException;
import weblogic.xml.process.XMLProcessingException;
import weblogic.xml.process.XMLProcessor;

public final class WebLogicApplication_1_0 extends WADDLoader implements XMLProcessor, InProcessor {
   private static final boolean debug = true;
   private static final boolean verbose = true;
   private static final Map paths = new HashMap();
   private ProcessorDriver driver;
   private static final String publicId = "-//BEA Systems, Inc.//DTD WebLogic Application 7.0.0//EN";
   private static final String localDTDResourceName = "/weblogic/j2ee/dd/xml/weblogic-application_1_0.dtd";

   public ProcessorDriver getDriver() {
      return this.driver;
   }

   public WebLogicApplication_1_0() {
      this(true);
   }

   public WebLogicApplication_1_0(boolean var1) {
      this.driver = new ProcessorDriver(this, "-//BEA Systems, Inc.//DTD WebLogic Application 7.0.0//EN", "/weblogic/j2ee/dd/xml/weblogic-application_1_0.dtd", var1);
   }

   public void process(String var1) throws IOException, XMLParsingException, XMLProcessingException {
      this.driver.process(var1);
   }

   public void process(File var1) throws IOException, XMLParsingException, XMLProcessingException {
      this.driver.process(var1);
   }

   public void process(Reader var1) throws IOException, XMLParsingException, XMLProcessingException {
      this.driver.process(var1);
   }

   public void process(InputSource var1) throws IOException, XMLParsingException, XMLProcessingException {
      this.driver.process(var1);
   }

   public void process(InputStream var1) throws IOException, XMLParsingException, XMLProcessingException {
      this.driver.process(var1);
   }

   public void preProc(ProcessingContext var1) throws SAXProcessorException {
      Debug.assertion(var1 != null);
      String var2 = var1.getPath();
      Debug.assertion(var2 != null);
      Integer var3 = (Integer)paths.get(var2);
      if (var3 != null) {
         switch (var3) {
            case 128:
               this.__pre_128(var1);
               break;
            case 129:
               this.__pre_129(var1);
               break;
            case 130:
               this.__pre_130(var1);
               break;
            case 131:
               this.__pre_131(var1);
               break;
            case 132:
               this.__pre_132(var1);
               break;
            case 133:
               this.__pre_133(var1);
               break;
            case 134:
               this.__pre_134(var1);
               break;
            case 135:
               this.__pre_135(var1);
               break;
            case 136:
               this.__pre_136(var1);
               break;
            case 137:
               this.__pre_137(var1);
               break;
            case 138:
               this.__pre_138(var1);
               break;
            case 139:
               this.__pre_139(var1);
               break;
            case 140:
               this.__pre_140(var1);
               break;
            case 141:
               this.__pre_141(var1);
               break;
            case 142:
               this.__pre_142(var1);
               break;
            case 143:
               this.__pre_143(var1);
               break;
            case 144:
               this.__pre_144(var1);
               break;
            case 145:
               this.__pre_145(var1);
               break;
            case 146:
               this.__pre_146(var1);
               break;
            case 147:
               this.__pre_147(var1);
               break;
            case 148:
               this.__pre_148(var1);
               break;
            case 149:
               this.__pre_149(var1);
               break;
            case 150:
               this.__pre_150(var1);
               break;
            case 151:
               this.__pre_151(var1);
               break;
            default:
               throw new AssertionError(var3.toString());
         }

      }
   }

   public void postProc(ProcessingContext var1) throws SAXProcessorException {
      Debug.assertion(var1 != null);
      String var2 = var1.getPath();
      Debug.assertion(var2 != null);
      Integer var3 = (Integer)paths.get(var2);
      if (var3 != null) {
         switch (var3) {
            case 128:
               this.__post_128(var1);
               break;
            case 129:
               this.__post_129(var1);
               break;
            case 130:
               this.__post_130(var1);
               break;
            case 131:
               this.__post_131(var1);
               break;
            case 132:
               this.__post_132(var1);
               break;
            case 133:
               this.__post_133(var1);
               break;
            case 134:
               this.__post_134(var1);
               break;
            case 135:
               this.__post_135(var1);
               break;
            case 136:
               this.__post_136(var1);
               break;
            case 137:
               this.__post_137(var1);
               break;
            case 138:
               this.__post_138(var1);
               break;
            case 139:
               this.__post_139(var1);
               break;
            case 140:
               this.__post_140(var1);
               break;
            case 141:
               this.__post_141(var1);
               break;
            case 142:
               this.__post_142(var1);
               break;
            case 143:
               this.__post_143(var1);
               break;
            case 144:
               this.__post_144(var1);
               break;
            case 145:
               this.__post_145(var1);
               break;
            case 146:
               this.__post_146(var1);
               break;
            case 147:
               this.__post_147(var1);
               break;
            case 148:
               this.__post_148(var1);
               break;
            case 149:
               this.__post_149(var1);
               break;
            case 150:
               this.__post_150(var1);
               break;
            case 151:
               this.__post_151(var1);
               break;
            default:
               throw new AssertionError(var3.toString());
         }

      }
   }

   private void __pre_144(ProcessingContext var1) {
   }

   private void __post_144(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      EntityMappingMBeanImpl var3 = (EntityMappingMBeanImpl)var1.getBoundObject("entityMapping");
      XMLMBeanImpl var4 = (XMLMBeanImpl)var1.getBoundObject("xmlMBean");
      WeblogicDeploymentDescriptor var5 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1323468230](.weblogic-application.xml.entity-mapping.entity-mapping-name.) must be a non-empty string");
      } else {
         var3.setEntityMappingName(var2);
      }
   }

   private void __pre_150(ProcessingContext var1) {
      SecurityMBeanImpl var2 = new SecurityMBeanImpl();
      var1.addBoundObject(var2, "security");
   }

   private void __post_150(ProcessingContext var1) throws SAXProcessorException {
      SecurityMBeanImpl var2 = (SecurityMBeanImpl)var1.getBoundObject("security");
      WeblogicDeploymentDescriptor var3 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.setSecurity(var2);
   }

   private void __pre_140(ProcessingContext var1) {
   }

   private void __post_140(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      ParserFactoryMBeanImpl var3 = (ParserFactoryMBeanImpl)var1.getBoundObject("parserfactory");
      XMLMBeanImpl var4 = (XMLMBeanImpl)var1.getBoundObject("xmlMBean");
      WeblogicDeploymentDescriptor var5 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[355629945](.weblogic-application.xml.parser-factory.saxparser-factory.) must be a non-empty string");
      } else {
         var3.setSaxparserFactory(var2);
      }
   }

   private void __pre_136(ProcessingContext var1) {
   }

   private void __post_136(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      MaxCacheSizeMBeanImpl var3 = (MaxCacheSizeMBeanImpl)var1.getBoundObject("maxCacheSize");
      EntityCacheMBeanImpl var4 = (EntityCacheMBeanImpl)var1.getBoundObject("entityCache");
      EjbMBeanImpl var5 = (EjbMBeanImpl)var1.getBoundObject("ejbMBean");
      WeblogicDeploymentDescriptor var6 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1327763628](.weblogic-application.ejb.entity-cache.max-cache-size.bytes.) must be a non-empty string");
      } else {
         try {
            this.validatePositiveInteger(var2);
         } catch (Exception var8) {
            throw new SAXValidationException("Path " + var1.getPath() + ": " + var8.getMessage());
         }

         var3.setBytes(Integer.parseInt(var2));
      }
   }

   private void __pre_131(ProcessingContext var1) {
      EntityCacheMBeanImpl var2 = new EntityCacheMBeanImpl();
      var1.addBoundObject(var2, "entityCache");
   }

   private void __post_131(ProcessingContext var1) throws SAXProcessorException {
      EntityCacheMBeanImpl var2 = (EntityCacheMBeanImpl)var1.getBoundObject("entityCache");
      EjbMBeanImpl var3 = (EjbMBeanImpl)var1.getBoundObject("ejbMBean");
      WeblogicDeploymentDescriptor var4 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.addEntityCache(var2);
   }

   private void __pre_141(ProcessingContext var1) {
   }

   private void __post_141(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      ParserFactoryMBeanImpl var3 = (ParserFactoryMBeanImpl)var1.getBoundObject("parserfactory");
      XMLMBeanImpl var4 = (XMLMBeanImpl)var1.getBoundObject("xmlMBean");
      WeblogicDeploymentDescriptor var5 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1915503092](.weblogic-application.xml.parser-factory.document-builder-factory.) must be a non-empty string");
      } else {
         var3.setDocumentBuilderFactory(var2);
      }
   }

   private void __pre_133(ProcessingContext var1) {
   }

   private void __post_133(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      EntityCacheMBeanImpl var3 = (EntityCacheMBeanImpl)var1.getBoundObject("entityCache");
      EjbMBeanImpl var4 = (EjbMBeanImpl)var1.getBoundObject("ejbMBean");
      WeblogicDeploymentDescriptor var5 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1535128843](.weblogic-application.ejb.entity-cache.max-beans-in-cache.) must be a non-empty string");
      } else {
         try {
            this.validateIntegerGreaterThanZero(var2);
         } catch (Exception var7) {
            throw new SAXValidationException("Path " + var1.getPath() + ": " + var7.getMessage());
         }

         var3.setMaxBeansInCache(Integer.parseInt(var2));
      }
   }

   private void __pre_134(ProcessingContext var1) {
   }

   private void __post_134(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      EntityCacheMBeanImpl var3 = (EntityCacheMBeanImpl)var1.getBoundObject("entityCache");
      EjbMBeanImpl var4 = (EjbMBeanImpl)var1.getBoundObject("ejbMBean");
      WeblogicDeploymentDescriptor var5 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1567581361](.weblogic-application.ejb.entity-cache.caching-strategy.) must be a non-empty string");
      } else {
         this.validateCachingStrategy(var3, var2);
         var3.setCachingStrategy(var2);
      }
   }

   private void __pre_143(ProcessingContext var1) {
      EntityMappingMBeanImpl var2 = new EntityMappingMBeanImpl();
      var1.addBoundObject(var2, "entityMapping");
   }

   private void __post_143(ProcessingContext var1) throws SAXProcessorException {
      EntityMappingMBeanImpl var2 = (EntityMappingMBeanImpl)var1.getBoundObject("entityMapping");
      XMLMBeanImpl var3 = (XMLMBeanImpl)var1.getBoundObject("xmlMBean");
      WeblogicDeploymentDescriptor var4 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.addEntityMapping(var2);
   }

   private void __pre_128(ProcessingContext var1) {
      WeblogicDeploymentDescriptor var2 = new WeblogicDeploymentDescriptor();
      var1.addBoundObject(var2, "wlApplication");
   }

   private void __post_128(ProcessingContext var1) throws SAXProcessorException {
      WeblogicDeploymentDescriptor var2 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (this.applicationDescriptor != null) {
         this.applicationDescriptor.setWeblogicApplicationDescriptor(var2);
      }

   }

   private void __pre_137(ProcessingContext var1) {
   }

   private void __post_137(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      MaxCacheSizeMBeanImpl var3 = (MaxCacheSizeMBeanImpl)var1.getBoundObject("maxCacheSize");
      EntityCacheMBeanImpl var4 = (EntityCacheMBeanImpl)var1.getBoundObject("entityCache");
      EjbMBeanImpl var5 = (EjbMBeanImpl)var1.getBoundObject("ejbMBean");
      WeblogicDeploymentDescriptor var6 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[849460928](.weblogic-application.ejb.entity-cache.max-cache-size.megabytes.) must be a non-empty string");
      } else {
         try {
            this.validatePositiveInteger(var2);
         } catch (Exception var8) {
            throw new SAXValidationException("Path " + var1.getPath() + ": " + var8.getMessage());
         }

         var3.setMegabytes(Integer.parseInt(var2));
      }
   }

   private void __pre_148(ProcessingContext var1) {
   }

   private void __post_148(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      EntityMappingMBeanImpl var3 = (EntityMappingMBeanImpl)var1.getBoundObject("entityMapping");
      XMLMBeanImpl var4 = (XMLMBeanImpl)var1.getBoundObject("xmlMBean");
      WeblogicDeploymentDescriptor var5 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.setWhenToCache(var2);
   }

   private void __pre_130(ProcessingContext var1) {
   }

   private void __post_130(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      EjbMBeanImpl var3 = (EjbMBeanImpl)var1.getBoundObject("ejbMBean");
      WeblogicDeploymentDescriptor var4 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[580024961](.weblogic-application.ejb.start-mdbs-with-application.) must be a non-empty string");
      } else if (!"true".equals(var2) && !"True".equals(var2) && !"false".equals(var2) && !"False".equals(var2)) {
         throw new SAXValidationException("PAction[580024961](.weblogic-application.ejb.start-mdbs-with-application.) must be one of the values: true,True,false,False");
      } else {
         var3.setStartMdbsWithApplication("True".equalsIgnoreCase(var2));
      }
   }

   private void __pre_138(ProcessingContext var1) {
      XMLMBeanImpl var2 = new XMLMBeanImpl();
      var1.addBoundObject(var2, "xmlMBean");
   }

   private void __post_138(ProcessingContext var1) throws SAXProcessorException {
      XMLMBeanImpl var2 = (XMLMBeanImpl)var1.getBoundObject("xmlMBean");
      WeblogicDeploymentDescriptor var3 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.setXML(var2);
   }

   private void __pre_147(ProcessingContext var1) {
   }

   private void __post_147(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      EntityMappingMBeanImpl var3 = (EntityMappingMBeanImpl)var1.getBoundObject("entityMapping");
      XMLMBeanImpl var4 = (XMLMBeanImpl)var1.getBoundObject("xmlMBean");
      WeblogicDeploymentDescriptor var5 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.setEntityURI(var2);
   }

   private void __pre_132(ProcessingContext var1) {
   }

   private void __post_132(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      EntityCacheMBeanImpl var3 = (EntityCacheMBeanImpl)var1.getBoundObject("entityCache");
      EjbMBeanImpl var4 = (EjbMBeanImpl)var1.getBoundObject("ejbMBean");
      WeblogicDeploymentDescriptor var5 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1766822961](.weblogic-application.ejb.entity-cache.entity-cache-name.) must be a non-empty string");
      } else {
         var3.setEntityCacheName(var2);
      }
   }

   private void __pre_139(ProcessingContext var1) {
      ParserFactoryMBeanImpl var2 = new ParserFactoryMBeanImpl();
      var1.addBoundObject(var2, "parserfactory");
   }

   private void __post_139(ProcessingContext var1) throws SAXProcessorException {
      ParserFactoryMBeanImpl var2 = (ParserFactoryMBeanImpl)var1.getBoundObject("parserfactory");
      XMLMBeanImpl var3 = (XMLMBeanImpl)var1.getBoundObject("xmlMBean");
      WeblogicDeploymentDescriptor var4 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.setParserFactory(var2);
   }

   private void __pre_146(ProcessingContext var1) {
   }

   private void __post_146(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      EntityMappingMBeanImpl var3 = (EntityMappingMBeanImpl)var1.getBoundObject("entityMapping");
      XMLMBeanImpl var4 = (XMLMBeanImpl)var1.getBoundObject("xmlMBean");
      WeblogicDeploymentDescriptor var5 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.setSystemId(var2);
   }

   private void __pre_129(ProcessingContext var1) {
      EjbMBeanImpl var2 = new EjbMBeanImpl();
      var1.addBoundObject(var2, "ejbMBean");
   }

   private void __post_129(ProcessingContext var1) throws SAXProcessorException {
      EjbMBeanImpl var2 = (EjbMBeanImpl)var1.getBoundObject("ejbMBean");
      WeblogicDeploymentDescriptor var3 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.setEjb(var2);
   }

   private void __pre_145(ProcessingContext var1) {
   }

   private void __post_145(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      EntityMappingMBeanImpl var3 = (EntityMappingMBeanImpl)var1.getBoundObject("entityMapping");
      XMLMBeanImpl var4 = (XMLMBeanImpl)var1.getBoundObject("xmlMBean");
      WeblogicDeploymentDescriptor var5 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.setPublicId(var2);
   }

   private void __pre_135(ProcessingContext var1) {
      MaxCacheSizeMBeanImpl var2 = new MaxCacheSizeMBeanImpl();
      var1.addBoundObject(var2, "maxCacheSize");
   }

   private void __post_135(ProcessingContext var1) throws SAXProcessorException {
      MaxCacheSizeMBeanImpl var2 = (MaxCacheSizeMBeanImpl)var1.getBoundObject("maxCacheSize");
      EntityCacheMBeanImpl var3 = (EntityCacheMBeanImpl)var1.getBoundObject("entityCache");
      EjbMBeanImpl var4 = (EjbMBeanImpl)var1.getBoundObject("ejbMBean");
      WeblogicDeploymentDescriptor var5 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.setMaxCacheSize(var2);
   }

   private void __pre_142(ProcessingContext var1) {
   }

   private void __post_142(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      ParserFactoryMBeanImpl var3 = (ParserFactoryMBeanImpl)var1.getBoundObject("parserfactory");
      XMLMBeanImpl var4 = (XMLMBeanImpl)var1.getBoundObject("xmlMBean");
      WeblogicDeploymentDescriptor var5 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1496724653](.weblogic-application.xml.parser-factory.transformer-factory.) must be a non-empty string");
      } else {
         var3.setTransformerFactory(var2);
      }
   }

   private void __pre_149(ProcessingContext var1) {
   }

   private void __post_149(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      EntityMappingMBeanImpl var3 = (EntityMappingMBeanImpl)var1.getBoundObject("entityMapping");
      XMLMBeanImpl var4 = (XMLMBeanImpl)var1.getBoundObject("xmlMBean");
      WeblogicDeploymentDescriptor var5 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[553264065](.weblogic-application.xml.entity-mapping.cache-timeout-interval.) must be a non-empty string");
      } else {
         try {
            this.validatePositiveInteger(var2);
         } catch (Exception var7) {
            throw new SAXValidationException("Path " + var1.getPath() + ": " + var7.getMessage());
         }

         var3.setCacheTimeoutInterval(Integer.parseInt(var2));
      }
   }

   private void __pre_151(ProcessingContext var1) {
   }

   private void __post_151(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      SecurityMBeanImpl var3 = (SecurityMBeanImpl)var1.getBoundObject("security");
      WeblogicDeploymentDescriptor var4 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.setRealmName(var2);
   }

   public void addBoundObject(Object var1, String var2) {
      this.driver.currentNode().addBoundObject(var1, var2);
   }

   static {
      paths.put(".weblogic-application.xml.entity-mapping.entity-mapping-name.", new Integer(144));
      paths.put(".weblogic-application.security.", new Integer(150));
      paths.put(".weblogic-application.xml.parser-factory.saxparser-factory.", new Integer(140));
      paths.put(".weblogic-application.ejb.entity-cache.max-cache-size.bytes.", new Integer(136));
      paths.put(".weblogic-application.ejb.entity-cache.", new Integer(131));
      paths.put(".weblogic-application.xml.parser-factory.document-builder-factory.", new Integer(141));
      paths.put(".weblogic-application.ejb.entity-cache.max-beans-in-cache.", new Integer(133));
      paths.put(".weblogic-application.ejb.entity-cache.caching-strategy.", new Integer(134));
      paths.put(".weblogic-application.xml.entity-mapping.", new Integer(143));
      paths.put(".weblogic-application.", new Integer(128));
      paths.put(".weblogic-application.ejb.entity-cache.max-cache-size.megabytes.", new Integer(137));
      paths.put(".weblogic-application.xml.entity-mapping.when-to-cache.", new Integer(148));
      paths.put(".weblogic-application.ejb.start-mdbs-with-application.", new Integer(130));
      paths.put(".weblogic-application.xml.", new Integer(138));
      paths.put(".weblogic-application.xml.entity-mapping.entity-uri.", new Integer(147));
      paths.put(".weblogic-application.ejb.entity-cache.entity-cache-name.", new Integer(132));
      paths.put(".weblogic-application.xml.parser-factory.", new Integer(139));
      paths.put(".weblogic-application.xml.entity-mapping.system-id.", new Integer(146));
      paths.put(".weblogic-application.ejb.", new Integer(129));
      paths.put(".weblogic-application.xml.entity-mapping.public-id.", new Integer(145));
      paths.put(".weblogic-application.ejb.entity-cache.max-cache-size.", new Integer(135));
      paths.put(".weblogic-application.xml.parser-factory.transformer-factory.", new Integer(142));
      paths.put(".weblogic-application.xml.entity-mapping.cache-timeout-interval.", new Integer(149));
      paths.put(".weblogic-application.security.realm-name.", new Integer(151));
   }
}
