package weblogic.j2ee.dd.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import org.xml.sax.InputSource;
import weblogic.j2ee.dd.CustomModuleDescriptor;
import weblogic.j2ee.dd.WeblogicDeploymentDescriptor;
import weblogic.management.DeploymentException;
import weblogic.management.descriptors.application.weblogic.ClassloaderStructureMBeanImpl;
import weblogic.management.descriptors.application.weblogic.EjbMBeanImpl;
import weblogic.management.descriptors.application.weblogic.EntityCacheMBeanImpl;
import weblogic.management.descriptors.application.weblogic.EntityMappingMBeanImpl;
import weblogic.management.descriptors.application.weblogic.LibraryRefMBeanImpl;
import weblogic.management.descriptors.application.weblogic.ListenerMBeanImpl;
import weblogic.management.descriptors.application.weblogic.MaxCacheSizeMBeanImpl;
import weblogic.management.descriptors.application.weblogic.ModuleProviderMBeanImpl;
import weblogic.management.descriptors.application.weblogic.ModuleRefMBeanImpl;
import weblogic.management.descriptors.application.weblogic.ParserFactoryMBeanImpl;
import weblogic.management.descriptors.application.weblogic.SecurityMBeanImpl;
import weblogic.management.descriptors.application.weblogic.SecurityRoleAssignmentMBeanImpl;
import weblogic.management.descriptors.application.weblogic.ShutdownMBeanImpl;
import weblogic.management.descriptors.application.weblogic.StartupMBeanImpl;
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

public final class WebLogicApplication_3_0 extends WADDLoader implements XMLProcessor, InProcessor {
   private static final boolean debug = true;
   private static final boolean verbose = true;
   private static final Map paths = new HashMap();
   private ProcessorDriver driver;
   private static final String publicId = "-//BEA Systems, Inc.//DTD WebLogic Application 9.0.0//EN";
   private static final String localDTDResourceName = "/weblogic/j2ee/dd/xml/weblogic-application_3_0.dtd";

   public ProcessorDriver getDriver() {
      return this.driver;
   }

   public WebLogicApplication_3_0() {
      this(true);
   }

   public WebLogicApplication_3_0(boolean var1) {
      this.driver = new ProcessorDriver(this, "-//BEA Systems, Inc.//DTD WebLogic Application 9.0.0//EN", "/weblogic/j2ee/dd/xml/weblogic-application_3_0.dtd", var1);
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
            case 152:
               this.__pre_152(var1);
               break;
            case 153:
               this.__pre_153(var1);
               break;
            case 154:
               this.__pre_154(var1);
               break;
            case 155:
               this.__pre_155(var1);
               break;
            case 156:
               this.__pre_156(var1);
               break;
            case 157:
               this.__pre_157(var1);
               break;
            case 158:
               this.__pre_158(var1);
               break;
            case 159:
               this.__pre_159(var1);
               break;
            case 160:
               this.__pre_160(var1);
               break;
            case 161:
               this.__pre_161(var1);
               break;
            case 162:
               this.__pre_162(var1);
               break;
            case 163:
               this.__pre_163(var1);
               break;
            case 164:
               this.__pre_164(var1);
               break;
            case 165:
               this.__pre_165(var1);
               break;
            case 166:
               this.__pre_166(var1);
               break;
            case 167:
               this.__pre_167(var1);
               break;
            case 168:
               this.__pre_168(var1);
               break;
            case 169:
               this.__pre_169(var1);
               break;
            case 170:
               this.__pre_170(var1);
               break;
            case 171:
               this.__pre_171(var1);
               break;
            case 172:
               this.__pre_172(var1);
               break;
            case 173:
               this.__pre_173(var1);
               break;
            case 174:
               this.__pre_174(var1);
               break;
            case 175:
               this.__pre_175(var1);
               break;
            case 176:
               this.__pre_176(var1);
               break;
            case 177:
               this.__pre_177(var1);
               break;
            case 178:
               this.__pre_178(var1);
               break;
            case 179:
               this.__pre_179(var1);
               break;
            case 180:
               this.__pre_180(var1);
               break;
            case 181:
               this.__pre_181(var1);
               break;
            case 182:
               this.__pre_182(var1);
               break;
            case 183:
               this.__pre_183(var1);
               break;
            case 184:
               this.__pre_184(var1);
               break;
            case 185:
               this.__pre_185(var1);
               break;
            case 186:
               this.__pre_186(var1);
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
            case 152:
               this.__post_152(var1);
               break;
            case 153:
               this.__post_153(var1);
               break;
            case 154:
               this.__post_154(var1);
               break;
            case 155:
               this.__post_155(var1);
               break;
            case 156:
               this.__post_156(var1);
               break;
            case 157:
               this.__post_157(var1);
               break;
            case 158:
               this.__post_158(var1);
               break;
            case 159:
               this.__post_159(var1);
               break;
            case 160:
               this.__post_160(var1);
               break;
            case 161:
               this.__post_161(var1);
               break;
            case 162:
               this.__post_162(var1);
               break;
            case 163:
               this.__post_163(var1);
               break;
            case 164:
               this.__post_164(var1);
               break;
            case 165:
               this.__post_165(var1);
               break;
            case 166:
               this.__post_166(var1);
               break;
            case 167:
               this.__post_167(var1);
               break;
            case 168:
               this.__post_168(var1);
               break;
            case 169:
               this.__post_169(var1);
               break;
            case 170:
               this.__post_170(var1);
               break;
            case 171:
               this.__post_171(var1);
               break;
            case 172:
               this.__post_172(var1);
               break;
            case 173:
               this.__post_173(var1);
               break;
            case 174:
               this.__post_174(var1);
               break;
            case 175:
               this.__post_175(var1);
               break;
            case 176:
               this.__post_176(var1);
               break;
            case 177:
               this.__post_177(var1);
               break;
            case 178:
               this.__post_178(var1);
               break;
            case 179:
               this.__post_179(var1);
               break;
            case 180:
               this.__post_180(var1);
               break;
            case 181:
               this.__post_181(var1);
               break;
            case 182:
               this.__post_182(var1);
               break;
            case 183:
               this.__post_183(var1);
               break;
            case 184:
               this.__post_184(var1);
               break;
            case 185:
               this.__post_185(var1);
               break;
            case 186:
               this.__post_186(var1);
               break;
            default:
               throw new AssertionError(var3.toString());
         }

      }
   }

   private void __pre_169(ProcessingContext var1) {
   }

   private void __post_169(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      StartupMBeanImpl var3 = (StartupMBeanImpl)var1.getBoundObject("startup");
      WeblogicDeploymentDescriptor var4 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1323468230](.weblogic-application.startup.startup-class.) must be a non-empty string");
      } else {
         var3.setStartupClass(var2);
      }
   }

   private void __pre_155(ProcessingContext var1) {
      ClassloaderStructureMBeanImpl var2 = new ClassloaderStructureMBeanImpl();
      var1.addBoundObject(var2, "clNode1");
   }

   private void __post_155(ProcessingContext var1) throws SAXProcessorException {
      ClassloaderStructureMBeanImpl var2 = (ClassloaderStructureMBeanImpl)var1.getBoundObject("clNode1");
      WeblogicDeploymentDescriptor var3 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.setClassloaderStructure(var2);
   }

   private void __pre_160(ProcessingContext var1) {
      ModuleRefMBeanImpl var2 = new ModuleRefMBeanImpl();
      var1.addBoundObject(var2, "modRef2");
   }

   private void __post_160(ProcessingContext var1) throws SAXProcessorException {
      ModuleRefMBeanImpl var2 = (ModuleRefMBeanImpl)var1.getBoundObject("modRef2");
      ClassloaderStructureMBeanImpl var3 = (ClassloaderStructureMBeanImpl)var1.getBoundObject("clNode2");
      ClassloaderStructureMBeanImpl var4 = (ClassloaderStructureMBeanImpl)var1.getBoundObject("clNode1");
      WeblogicDeploymentDescriptor var5 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.addModuleRef(var2);
   }

   private void __pre_174(ProcessingContext var1) {
      ModuleProviderMBeanImpl var2 = new ModuleProviderMBeanImpl();
      var1.addBoundObject(var2, "moduleProvider");
   }

   private void __post_174(ProcessingContext var1) throws SAXProcessorException {
      ModuleProviderMBeanImpl var2 = (ModuleProviderMBeanImpl)var1.getBoundObject("moduleProvider");
      WeblogicDeploymentDescriptor var3 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.addModuleProvider(var2);
   }

   private void __pre_186(ProcessingContext var1) {
   }

   private void __post_186(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      LibraryRefMBeanImpl var3 = (LibraryRefMBeanImpl)var1.getBoundObject("library");
      WeblogicDeploymentDescriptor var4 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.setContextPath(var2);
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

   private void __pre_178(ProcessingContext var1) {
      CustomModuleDescriptor var2 = new CustomModuleDescriptor();
      var1.addBoundObject(var2, "custom");
   }

   private void __post_178(ProcessingContext var1) throws SAXProcessorException {
      CustomModuleDescriptor var2 = (CustomModuleDescriptor)var1.getBoundObject("custom");
      WeblogicDeploymentDescriptor var3 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.addModule(var2);
      super.setCurrentModuleContext(var2);
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
         throw new SAXValidationException("PAction[355629945](.weblogic-application.ejb.entity-cache.max-cache-size.megabytes.) must be a non-empty string");
      } else {
         try {
            this.validatePositiveInteger(var2);
         } catch (Exception var8) {
            throw new SAXValidationException("Path " + var1.getPath() + ": " + var8.getMessage());
         }

         var3.setMegabytes(Integer.parseInt(var2));
      }
   }

   private void __pre_168(ProcessingContext var1) {
      StartupMBeanImpl var2 = new StartupMBeanImpl();
      var1.addBoundObject(var2, "startup");
   }

   private void __post_168(ProcessingContext var1) throws SAXProcessorException {
      StartupMBeanImpl var2 = (StartupMBeanImpl)var1.getBoundObject("startup");
      WeblogicDeploymentDescriptor var3 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.addStartup(var2);
   }

   private void __pre_172(ProcessingContext var1) {
   }

   private void __post_172(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      ShutdownMBeanImpl var3 = (ShutdownMBeanImpl)var1.getBoundObject("shutdown");
      WeblogicDeploymentDescriptor var4 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1327763628](.weblogic-application.shutdown.shutdown-class.) must be a non-empty string");
      } else {
         var3.setShutdownClass(var2);
      }
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

   private void __pre_171(ProcessingContext var1) {
      ShutdownMBeanImpl var2 = new ShutdownMBeanImpl();
      var1.addBoundObject(var2, "shutdown");
   }

   private void __post_171(ProcessingContext var1) throws SAXProcessorException {
      ShutdownMBeanImpl var2 = (ShutdownMBeanImpl)var1.getBoundObject("shutdown");
      WeblogicDeploymentDescriptor var3 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.addShutdown(var2);
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

   private void __pre_175(ProcessingContext var1) {
   }

   private void __post_175(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      ModuleProviderMBeanImpl var3 = (ModuleProviderMBeanImpl)var1.getBoundObject("moduleProvider");
      WeblogicDeploymentDescriptor var4 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1915503092](.weblogic-application.module-provider.name.) must be a non-empty string");
      } else {
         var3.setName(var2);
      }
   }

   private void __pre_159(ProcessingContext var1) {
      ModuleRefMBeanImpl var2 = new ModuleRefMBeanImpl();
      var1.addBoundObject(var2, "modRef1");
   }

   private void __post_159(ProcessingContext var1) throws SAXProcessorException {
      ModuleRefMBeanImpl var2 = (ModuleRefMBeanImpl)var1.getBoundObject("modRef1");
      ClassloaderStructureMBeanImpl var3 = (ClassloaderStructureMBeanImpl)var1.getBoundObject("clNode1");
      WeblogicDeploymentDescriptor var4 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.addModuleRef(var2);
   }

   private void __pre_167(ProcessingContext var1) {
   }

   private void __post_167(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      ListenerMBeanImpl var3 = (ListenerMBeanImpl)var1.getBoundObject("listener");
      WeblogicDeploymentDescriptor var4 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[458209687](.weblogic-application.listener.listener-uri.) must be a non-empty string");
      } else {
         var3.setListenerUri(var2);
      }
   }

   private void __pre_184(ProcessingContext var1) {
   }

   private void __post_184(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      LibraryRefMBeanImpl var3 = (LibraryRefMBeanImpl)var1.getBoundObject("library");
      WeblogicDeploymentDescriptor var4 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.setImplementationVersion(var2);
   }

   private void __pre_181(ProcessingContext var1) {
      LibraryRefMBeanImpl var2 = new LibraryRefMBeanImpl();
      var1.addBoundObject(var2, "library");
   }

   private void __post_181(ProcessingContext var1) throws SAXProcessorException {
      LibraryRefMBeanImpl var2 = (LibraryRefMBeanImpl)var1.getBoundObject("library");
      WeblogicDeploymentDescriptor var3 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.addLibrary(var2);
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
         throw new SAXValidationException("PAction[233530418](.weblogic-application.xml.parser-factory.saxparser-factory.) must be a non-empty string");
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
         throw new SAXValidationException("PAction[683287027](.weblogic-application.ejb.entity-cache.max-cache-size.bytes.) must be a non-empty string");
      } else {
         try {
            this.validatePositiveInteger(var2);
         } catch (Exception var8) {
            throw new SAXValidationException("Path " + var1.getPath() + ": " + var8.getMessage());
         }

         var3.setBytes(Integer.parseInt(var2));
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
         throw new SAXValidationException("PAction[1766822961](.weblogic-application.ejb.entity-cache.max-beans-in-cache.) must be a non-empty string");
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
         throw new SAXValidationException("PAction[254413710](.weblogic-application.ejb.entity-cache.caching-strategy.) must be a non-empty string");
      } else {
         this.validateCachingStrategy(var3, var2);
         var3.setCachingStrategy(var2);
      }
   }

   private void __pre_152(ProcessingContext var1) {
      SecurityRoleAssignmentMBeanImpl var2 = new SecurityRoleAssignmentMBeanImpl();
      var1.addBoundObject(var2, "securityroleassignment");
   }

   private void __post_152(ProcessingContext var1) throws SAXProcessorException {
      SecurityRoleAssignmentMBeanImpl var2 = (SecurityRoleAssignmentMBeanImpl)var1.getBoundObject("securityroleassignment");
      SecurityMBeanImpl var3 = (SecurityMBeanImpl)var1.getBoundObject("security");
      WeblogicDeploymentDescriptor var4 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.addRoleAssignment(var2);
   }

   private void __pre_185(ProcessingContext var1) {
   }

   private void __post_185(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      LibraryRefMBeanImpl var3 = (LibraryRefMBeanImpl)var1.getBoundObject("library");
      WeblogicDeploymentDescriptor var4 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.setExactMatch(var2);
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

   private void __pre_156(ProcessingContext var1) {
      ClassloaderStructureMBeanImpl var2 = new ClassloaderStructureMBeanImpl();
      var1.addBoundObject(var2, "clNode2");
   }

   private void __post_156(ProcessingContext var1) throws SAXProcessorException {
      ClassloaderStructureMBeanImpl var2 = (ClassloaderStructureMBeanImpl)var1.getBoundObject("clNode2");
      ClassloaderStructureMBeanImpl var3 = (ClassloaderStructureMBeanImpl)var1.getBoundObject("clNode1");
      WeblogicDeploymentDescriptor var4 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.addClassloaderStructure(var2);
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

   private void __pre_176(ProcessingContext var1) {
   }

   private void __post_176(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      ModuleProviderMBeanImpl var3 = (ModuleProviderMBeanImpl)var1.getBoundObject("moduleProvider");
      WeblogicDeploymentDescriptor var4 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1496724653](.weblogic-application.module-provider.module-factory-class.) must be a non-empty string");
      } else {
         var3.setModuleFactoryClassName(var2);
      }
   }

   private void __pre_173(ProcessingContext var1) {
   }

   private void __post_173(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      ShutdownMBeanImpl var3 = (ShutdownMBeanImpl)var1.getBoundObject("shutdown");
      WeblogicDeploymentDescriptor var4 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[553264065](.weblogic-application.shutdown.shutdown-uri.) must be a non-empty string");
      } else {
         var3.setShutdownUri(var2);
      }
   }

   private void __pre_142(ProcessingContext var1) {
   }

   private void __post_142(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      ParserFactoryMBeanImpl var3 = (ParserFactoryMBeanImpl)var1.getBoundObject("parserfactory");
      XMLMBeanImpl var4 = (XMLMBeanImpl)var1.getBoundObject("xmlMBean");
      WeblogicDeploymentDescriptor var5 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[897697267](.weblogic-application.xml.parser-factory.transformer-factory.) must be a non-empty string");
      } else {
         var3.setTransformerFactory(var2);
      }
   }

   private void __pre_177(ProcessingContext var1) {
   }

   private void __post_177(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      WeblogicDeploymentDescriptor var3 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      super.getCurrentModuleContext().setAltDDURI(var2);
   }

   private void __pre_183(ProcessingContext var1) {
   }

   private void __post_183(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      LibraryRefMBeanImpl var3 = (LibraryRefMBeanImpl)var1.getBoundObject("library");
      WeblogicDeploymentDescriptor var4 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.setSpecificationVersion(var2);
   }

   private void __pre_149(ProcessingContext var1) {
   }

   private void __post_149(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      EntityMappingMBeanImpl var3 = (EntityMappingMBeanImpl)var1.getBoundObject("entityMapping");
      XMLMBeanImpl var4 = (XMLMBeanImpl)var1.getBoundObject("xmlMBean");
      WeblogicDeploymentDescriptor var5 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1789447862](.weblogic-application.xml.entity-mapping.cache-timeout-interval.) must be a non-empty string");
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

   private void __pre_182(ProcessingContext var1) {
   }

   private void __post_182(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      LibraryRefMBeanImpl var3 = (LibraryRefMBeanImpl)var1.getBoundObject("library");
      WeblogicDeploymentDescriptor var4 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[38997010](.weblogic-application.library-ref.library-name.) must be a non-empty string");
      } else {
         var3.setLibraryName(var2);
      }
   }

   private void __pre_179(ProcessingContext var1) {
   }

   private void __post_179(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      CustomModuleDescriptor var3 = (CustomModuleDescriptor)var1.getBoundObject("custom");
      WeblogicDeploymentDescriptor var4 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.setURI(var2);
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

   private void __pre_153(ProcessingContext var1) {
   }

   private void __post_153(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      SecurityRoleAssignmentMBeanImpl var3 = (SecurityRoleAssignmentMBeanImpl)var1.getBoundObject("securityroleassignment");
      SecurityMBeanImpl var4 = (SecurityMBeanImpl)var1.getBoundObject("security");
      WeblogicDeploymentDescriptor var5 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1942406066](.weblogic-application.security.security-role-assignment.role-name.) must be a non-empty string");
      } else {
         var3.setRoleName(var2);
      }
   }

   private void __pre_164(ProcessingContext var1) {
   }

   private void __post_164(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      ModuleRefMBeanImpl var3 = (ModuleRefMBeanImpl)var1.getBoundObject("modRef3");
      ClassloaderStructureMBeanImpl var4 = (ClassloaderStructureMBeanImpl)var1.getBoundObject("clNode3");
      ClassloaderStructureMBeanImpl var5 = (ClassloaderStructureMBeanImpl)var1.getBoundObject("clNode2");
      ClassloaderStructureMBeanImpl var6 = (ClassloaderStructureMBeanImpl)var1.getBoundObject("clNode1");
      WeblogicDeploymentDescriptor var7 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1213415012](.weblogic-application.classloader-structure.classloader-structure.classloader-structure.module-ref.module-uri.) must be a non-empty string");
      } else {
         var3.setModuleUri(var2);
      }
   }

   private void __pre_130(ProcessingContext var1) {
   }

   private void __post_130(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      EjbMBeanImpl var3 = (EjbMBeanImpl)var1.getBoundObject("ejbMBean");
      WeblogicDeploymentDescriptor var4 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1688376486](.weblogic-application.ejb.start-mdbs-with-application.) must be a non-empty string");
      } else if (!"true".equals(var2) && !"True".equals(var2) && !"false".equals(var2) && !"False".equals(var2)) {
         throw new SAXValidationException("PAction[1688376486](.weblogic-application.ejb.start-mdbs-with-application.) must be one of the values: true,True,false,False");
      } else {
         var3.setStartMdbsWithApplication("True".equalsIgnoreCase(var2));
      }
   }

   private void __pre_132(ProcessingContext var1) {
   }

   private void __post_132(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      EntityCacheMBeanImpl var3 = (EntityCacheMBeanImpl)var1.getBoundObject("entityCache");
      EjbMBeanImpl var4 = (EjbMBeanImpl)var1.getBoundObject("ejbMBean");
      WeblogicDeploymentDescriptor var5 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[2114664380](.weblogic-application.ejb.entity-cache.entity-cache-name.) must be a non-empty string");
      } else {
         var3.setEntityCacheName(var2);
      }
   }

   private void __pre_163(ProcessingContext var1) {
   }

   private void __post_163(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      ModuleRefMBeanImpl var3 = (ModuleRefMBeanImpl)var1.getBoundObject("modRef2");
      ClassloaderStructureMBeanImpl var4 = (ClassloaderStructureMBeanImpl)var1.getBoundObject("clNode2");
      ClassloaderStructureMBeanImpl var5 = (ClassloaderStructureMBeanImpl)var1.getBoundObject("clNode1");
      WeblogicDeploymentDescriptor var6 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[999661724](.weblogic-application.classloader-structure.classloader-structure.module-ref.module-uri.) must be a non-empty string");
      } else {
         var3.setModuleUri(var2);
      }
   }

   private void __pre_158(ProcessingContext var1) {
      ClassloaderStructureMBeanImpl var2 = new ClassloaderStructureMBeanImpl();
      var1.addBoundObject(var2, "clNode4");
   }

   private void __post_158(ProcessingContext var1) throws SAXProcessorException {
      ClassloaderStructureMBeanImpl var2 = (ClassloaderStructureMBeanImpl)var1.getBoundObject("clNode4");
      ClassloaderStructureMBeanImpl var3 = (ClassloaderStructureMBeanImpl)var1.getBoundObject("clNode3");
      ClassloaderStructureMBeanImpl var4 = (ClassloaderStructureMBeanImpl)var1.getBoundObject("clNode2");
      ClassloaderStructureMBeanImpl var5 = (ClassloaderStructureMBeanImpl)var1.getBoundObject("clNode1");
      WeblogicDeploymentDescriptor var6 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      String var7 = "classloader-structure element in weblogic-application.xml is nested too deeply. Nesting is restricted to 3 levels.";
      DeploymentException var8 = new DeploymentException(var7);
      throw new SAXProcessorException(var7, var8);
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

   private void __pre_157(ProcessingContext var1) {
      ClassloaderStructureMBeanImpl var2 = new ClassloaderStructureMBeanImpl();
      var1.addBoundObject(var2, "clNode3");
   }

   private void __post_157(ProcessingContext var1) throws SAXProcessorException {
      ClassloaderStructureMBeanImpl var2 = (ClassloaderStructureMBeanImpl)var1.getBoundObject("clNode3");
      ClassloaderStructureMBeanImpl var3 = (ClassloaderStructureMBeanImpl)var1.getBoundObject("clNode2");
      ClassloaderStructureMBeanImpl var4 = (ClassloaderStructureMBeanImpl)var1.getBoundObject("clNode1");
      WeblogicDeploymentDescriptor var5 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.addClassloaderStructure(var2);
   }

   private void __pre_144(ProcessingContext var1) {
   }

   private void __post_144(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      EntityMappingMBeanImpl var3 = (EntityMappingMBeanImpl)var1.getBoundObject("entityMapping");
      XMLMBeanImpl var4 = (XMLMBeanImpl)var1.getBoundObject("xmlMBean");
      WeblogicDeploymentDescriptor var5 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1793329556](.weblogic-application.xml.entity-mapping.entity-mapping-name.) must be a non-empty string");
      } else {
         var3.setEntityMappingName(var2);
      }
   }

   private void __pre_170(ProcessingContext var1) {
   }

   private void __post_170(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      StartupMBeanImpl var3 = (StartupMBeanImpl)var1.getBoundObject("startup");
      WeblogicDeploymentDescriptor var4 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[445884362](.weblogic-application.startup.startup-uri.) must be a non-empty string");
      } else {
         var3.setStartupUri(var2);
      }
   }

   private void __pre_180(ProcessingContext var1) {
   }

   private void __post_180(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      CustomModuleDescriptor var3 = (CustomModuleDescriptor)var1.getBoundObject("custom");
      WeblogicDeploymentDescriptor var4 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.setProviderName(var2);
   }

   private void __pre_141(ProcessingContext var1) {
   }

   private void __post_141(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      ParserFactoryMBeanImpl var3 = (ParserFactoryMBeanImpl)var1.getBoundObject("parserfactory");
      XMLMBeanImpl var4 = (XMLMBeanImpl)var1.getBoundObject("xmlMBean");
      WeblogicDeploymentDescriptor var5 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1031980531](.weblogic-application.xml.parser-factory.document-builder-factory.) must be a non-empty string");
      } else {
         var3.setDocumentBuilderFactory(var2);
      }
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

   private void __pre_148(ProcessingContext var1) {
   }

   private void __post_148(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      EntityMappingMBeanImpl var3 = (EntityMappingMBeanImpl)var1.getBoundObject("entityMapping");
      XMLMBeanImpl var4 = (XMLMBeanImpl)var1.getBoundObject("xmlMBean");
      WeblogicDeploymentDescriptor var5 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.setWhenToCache(var2);
   }

   private void __pre_166(ProcessingContext var1) {
   }

   private void __post_166(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      ListenerMBeanImpl var3 = (ListenerMBeanImpl)var1.getBoundObject("listener");
      WeblogicDeploymentDescriptor var4 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[721748895](.weblogic-application.listener.listener-class.) must be a non-empty string");
      } else {
         var3.setListenerClass(var2);
      }
   }

   private void __pre_154(ProcessingContext var1) {
   }

   private void __post_154(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      SecurityRoleAssignmentMBeanImpl var3 = (SecurityRoleAssignmentMBeanImpl)var1.getBoundObject("securityroleassignment");
      SecurityMBeanImpl var4 = (SecurityMBeanImpl)var1.getBoundObject("security");
      WeblogicDeploymentDescriptor var5 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1642534850](.weblogic-application.security.security-role-assignment.principal-name.) must be a non-empty string");
      } else {
         var3.addPrincipalName(var2);
      }
   }

   private void __pre_161(ProcessingContext var1) {
      ModuleRefMBeanImpl var2 = new ModuleRefMBeanImpl();
      var1.addBoundObject(var2, "modRef3");
   }

   private void __post_161(ProcessingContext var1) throws SAXProcessorException {
      ModuleRefMBeanImpl var2 = (ModuleRefMBeanImpl)var1.getBoundObject("modRef3");
      ClassloaderStructureMBeanImpl var3 = (ClassloaderStructureMBeanImpl)var1.getBoundObject("clNode3");
      ClassloaderStructureMBeanImpl var4 = (ClassloaderStructureMBeanImpl)var1.getBoundObject("clNode2");
      ClassloaderStructureMBeanImpl var5 = (ClassloaderStructureMBeanImpl)var1.getBoundObject("clNode1");
      WeblogicDeploymentDescriptor var6 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.addModuleRef(var2);
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

   private void __pre_162(ProcessingContext var1) {
   }

   private void __post_162(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      ModuleRefMBeanImpl var3 = (ModuleRefMBeanImpl)var1.getBoundObject("modRef1");
      ClassloaderStructureMBeanImpl var4 = (ClassloaderStructureMBeanImpl)var1.getBoundObject("clNode1");
      WeblogicDeploymentDescriptor var5 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1724731843](.weblogic-application.classloader-structure.module-ref.module-uri.) must be a non-empty string");
      } else {
         var3.setModuleUri(var2);
      }
   }

   private void __pre_165(ProcessingContext var1) {
      ListenerMBeanImpl var2 = new ListenerMBeanImpl();
      var1.addBoundObject(var2, "listener");
   }

   private void __post_165(ProcessingContext var1) throws SAXProcessorException {
      ListenerMBeanImpl var2 = (ListenerMBeanImpl)var1.getBoundObject("listener");
      WeblogicDeploymentDescriptor var3 = (WeblogicDeploymentDescriptor)var1.getBoundObject("wlApplication");
      var3.addListener(var2);
   }

   public void addBoundObject(Object var1, String var2) {
      this.driver.currentNode().addBoundObject(var1, var2);
   }

   static {
      paths.put(".weblogic-application.startup.startup-class.", new Integer(169));
      paths.put(".weblogic-application.classloader-structure.", new Integer(155));
      paths.put(".weblogic-application.classloader-structure.classloader-structure.module-ref.", new Integer(160));
      paths.put(".weblogic-application.module-provider.", new Integer(174));
      paths.put(".weblogic-application.library-ref.context-path.", new Integer(186));
      paths.put(".weblogic-application.xml.entity-mapping.", new Integer(143));
      paths.put(".weblogic-application.module.custom.", new Integer(178));
      paths.put(".weblogic-application.ejb.entity-cache.max-cache-size.megabytes.", new Integer(137));
      paths.put(".weblogic-application.startup.", new Integer(168));
      paths.put(".weblogic-application.shutdown.shutdown-class.", new Integer(172));
      paths.put(".weblogic-application.xml.entity-mapping.entity-uri.", new Integer(147));
      paths.put(".weblogic-application.shutdown.", new Integer(171));
      paths.put(".weblogic-application.xml.parser-factory.", new Integer(139));
      paths.put(".weblogic-application.xml.entity-mapping.system-id.", new Integer(146));
      paths.put(".weblogic-application.module-provider.name.", new Integer(175));
      paths.put(".weblogic-application.classloader-structure.module-ref.", new Integer(159));
      paths.put(".weblogic-application.listener.listener-uri.", new Integer(167));
      paths.put(".weblogic-application.library-ref.implementation-version.", new Integer(184));
      paths.put(".weblogic-application.library-ref.", new Integer(181));
      paths.put(".weblogic-application.security.", new Integer(150));
      paths.put(".weblogic-application.xml.parser-factory.saxparser-factory.", new Integer(140));
      paths.put(".weblogic-application.ejb.entity-cache.max-cache-size.bytes.", new Integer(136));
      paths.put(".weblogic-application.ejb.entity-cache.max-beans-in-cache.", new Integer(133));
      paths.put(".weblogic-application.ejb.entity-cache.caching-strategy.", new Integer(134));
      paths.put(".weblogic-application.security.security-role-assignment.", new Integer(152));
      paths.put(".weblogic-application.library-ref.exact-match.", new Integer(185));
      paths.put(".weblogic-application.xml.", new Integer(138));
      paths.put(".weblogic-application.classloader-structure.classloader-structure.", new Integer(156));
      paths.put(".weblogic-application.ejb.", new Integer(129));
      paths.put(".weblogic-application.module-provider.module-factory-class.", new Integer(176));
      paths.put(".weblogic-application.shutdown.shutdown-uri.", new Integer(173));
      paths.put(".weblogic-application.xml.parser-factory.transformer-factory.", new Integer(142));
      paths.put(".weblogic-application.module.alt-dd.", new Integer(177));
      paths.put(".weblogic-application.library-ref.specification-version.", new Integer(183));
      paths.put(".weblogic-application.xml.entity-mapping.cache-timeout-interval.", new Integer(149));
      paths.put(".weblogic-application.security.realm-name.", new Integer(151));
      paths.put(".weblogic-application.library-ref.library-name.", new Integer(182));
      paths.put(".weblogic-application.module.custom.uri.", new Integer(179));
      paths.put(".weblogic-application.ejb.entity-cache.", new Integer(131));
      paths.put(".weblogic-application.security.security-role-assignment.role-name.", new Integer(153));
      paths.put(".weblogic-application.classloader-structure.classloader-structure.classloader-structure.module-ref.module-uri.", new Integer(164));
      paths.put(".weblogic-application.ejb.start-mdbs-with-application.", new Integer(130));
      paths.put(".weblogic-application.ejb.entity-cache.entity-cache-name.", new Integer(132));
      paths.put(".weblogic-application.classloader-structure.classloader-structure.module-ref.module-uri.", new Integer(163));
      paths.put(".weblogic-application.classloader-structure.classloader-structure.classloader-structure.classloader-structure.", new Integer(158));
      paths.put(".weblogic-application.ejb.entity-cache.max-cache-size.", new Integer(135));
      paths.put(".weblogic-application.classloader-structure.classloader-structure.classloader-structure.", new Integer(157));
      paths.put(".weblogic-application.xml.entity-mapping.entity-mapping-name.", new Integer(144));
      paths.put(".weblogic-application.startup.startup-uri.", new Integer(170));
      paths.put(".weblogic-application.module.custom.provider-name.", new Integer(180));
      paths.put(".weblogic-application.xml.parser-factory.document-builder-factory.", new Integer(141));
      paths.put(".weblogic-application.", new Integer(128));
      paths.put(".weblogic-application.xml.entity-mapping.when-to-cache.", new Integer(148));
      paths.put(".weblogic-application.listener.listener-class.", new Integer(166));
      paths.put(".weblogic-application.security.security-role-assignment.principal-name.", new Integer(154));
      paths.put(".weblogic-application.classloader-structure.classloader-structure.classloader-structure.module-ref.", new Integer(161));
      paths.put(".weblogic-application.xml.entity-mapping.public-id.", new Integer(145));
      paths.put(".weblogic-application.classloader-structure.module-ref.module-uri.", new Integer(162));
      paths.put(".weblogic-application.listener.", new Integer(165));
   }
}
