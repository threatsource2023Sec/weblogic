package weblogic.ejb.container.persistence;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import org.xml.sax.InputSource;
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

public final class WeblogicPersistenceVendorProcessor extends PersistenceVendorProcessor implements XMLProcessor, InProcessor {
   private static final boolean debug = true;
   private static final boolean verbose = true;
   private static final Map paths = new HashMap();
   private ProcessorDriver driver;
   private static final String publicId = "-//BEA Systems, Inc.//DTD WebLogic 6.0.0 Persistence Vendor//EN";
   private static final String localDTDResourceName = "/weblogic/ejb/container/persistence/spi/weblogic-persistence-vendor.dtd";

   public ProcessorDriver getDriver() {
      return this.driver;
   }

   public WeblogicPersistenceVendorProcessor() {
      this(true);
   }

   public WeblogicPersistenceVendorProcessor(boolean var1) {
      this.driver = new ProcessorDriver(this, "-//BEA Systems, Inc.//DTD WebLogic 6.0.0 Persistence Vendor//EN", "/weblogic/ejb/container/persistence/spi/weblogic-persistence-vendor.dtd", var1);
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
            default:
               throw new AssertionError(var3.toString());
         }

      }
   }

   private void __pre_131(ProcessingContext var1) {
   }

   private void __post_131(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      PersistenceType var3 = (PersistenceType)var1.getBoundObject("curType");
      PersistenceVendor var4 = (PersistenceVendor)var1.getBoundObject("vendor");
      var3.setIdentifier(var2);
   }

   private void __pre_136(ProcessingContext var1) {
   }

   private void __post_136(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      PersistenceType var3 = (PersistenceType)var1.getBoundObject("curType");
      PersistenceVendor var4 = (PersistenceVendor)var1.getBoundObject("vendor");
      var3.setPersistenceManagerClassName(var2);
   }

   private void __pre_132(ProcessingContext var1) {
   }

   private void __post_132(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      PersistenceType var3 = (PersistenceType)var1.getBoundObject("curType");
      PersistenceVendor var4 = (PersistenceVendor)var1.getBoundObject("vendor");
      var3.setVersion(var2);
   }

   private void __pre_140(ProcessingContext var1) {
   }

   private void __post_140(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      PersistenceType var3 = (PersistenceType)var1.getBoundObject("curType");
      PersistenceVendor var4 = (PersistenceVendor)var1.getBoundObject("vendor");
      var3.setJarDeploymentClassName(var2);
   }

   private void __pre_135(ProcessingContext var1) {
   }

   private void __post_135(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      PersistenceType var3 = (PersistenceType)var1.getBoundObject("curType");
      PersistenceVendor var4 = (PersistenceVendor)var1.getBoundObject("vendor");
      var3.setCmpDeployerClassName(var2);
   }

   private void __pre_129(ProcessingContext var1) {
   }

   private void __post_129(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      PersistenceVendor var3 = (PersistenceVendor)var1.getBoundObject("vendor");
      var3.setName(var2);
   }

   private void __pre_133(ProcessingContext var1) {
   }

   private void __post_133(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      PersistenceType var3 = (PersistenceType)var1.getBoundObject("curType");
      PersistenceVendor var4 = (PersistenceVendor)var1.getBoundObject("vendor");
      var3.setWeblogicVersion(var2);
   }

   private void __pre_139(ProcessingContext var1) {
   }

   private void __post_139(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      PersistenceType var3 = (PersistenceType)var1.getBoundObject("curType");
      PersistenceVendor var4 = (PersistenceVendor)var1.getBoundObject("vendor");
      var3.setReadonlyManagerClassName(var2);
   }

   private void __pre_141(ProcessingContext var1) {
   }

   private void __post_141(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      PersistenceType var3 = (PersistenceType)var1.getBoundObject("curType");
      PersistenceVendor var4 = (PersistenceVendor)var1.getBoundObject("vendor");
      var3.setCodeGeneratorClassName(var2);
   }

   private void __pre_134(ProcessingContext var1) {
   }

   private void __post_134(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      PersistenceType var3 = (PersistenceType)var1.getBoundObject("curType");
      PersistenceVendor var4 = (PersistenceVendor)var1.getBoundObject("vendor");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1130478920](.weblogic-persistence-vendor.weblogic-persistence-type.cmp-version.) must be a non-empty string");
      } else if (!"1.x".equals(var2) && !"2.x".equals(var2)) {
         throw new SAXValidationException("PAction[1130478920](.weblogic-persistence-vendor.weblogic-persistence-type.cmp-version.) must be one of the values: 1.x,2.x");
      } else {
         var3.setCmpVersion(var2);
      }
   }

   private void __pre_138(ProcessingContext var1) {
   }

   private void __post_138(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      PersistenceType var3 = (PersistenceType)var1.getBoundObject("curType");
      PersistenceVendor var4 = (PersistenceVendor)var1.getBoundObject("vendor");
      var3.setDatabaseManagerClassName(var2);
   }

   private void __pre_137(ProcessingContext var1) {
   }

   private void __post_137(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      PersistenceType var3 = (PersistenceType)var1.getBoundObject("curType");
      PersistenceVendor var4 = (PersistenceVendor)var1.getBoundObject("vendor");
      var3.setExclusiveManagerClassName(var2);
   }

   private void __pre_130(ProcessingContext var1) {
      PersistenceType var2 = new PersistenceType();
      var1.addBoundObject(var2, "curType");
   }

   private void __post_130(ProcessingContext var1) throws SAXProcessorException {
      PersistenceType var2 = (PersistenceType)var1.getBoundObject("curType");
      PersistenceVendor var3 = (PersistenceVendor)var1.getBoundObject("vendor");
      var2.setVendor(var3);
      this.addType(var2);
   }

   private void __pre_128(ProcessingContext var1) {
      PersistenceVendor var2 = new PersistenceVendor();
      var1.addBoundObject(var2, "vendor");
   }

   private void __post_128(ProcessingContext var1) throws SAXProcessorException {
      PersistenceVendor var2 = (PersistenceVendor)var1.getBoundObject("vendor");
   }

   public void addBoundObject(Object var1, String var2) {
      this.driver.currentNode().addBoundObject(var1, var2);
   }

   static {
      paths.put(".weblogic-persistence-vendor.weblogic-persistence-type.persistence-type-identifier.", new Integer(131));
      paths.put(".weblogic-persistence-vendor.weblogic-persistence-type.class-information.persistence-manager-class.", new Integer(136));
      paths.put(".weblogic-persistence-vendor.weblogic-persistence-type.persistence-type-version.", new Integer(132));
      paths.put(".weblogic-persistence-vendor.weblogic-persistence-type.class-information.jar-deployment-class.", new Integer(140));
      paths.put(".weblogic-persistence-vendor.weblogic-persistence-type.class-information.cmp-deployer-class.", new Integer(135));
      paths.put(".weblogic-persistence-vendor.vendor-name.", new Integer(129));
      paths.put(".weblogic-persistence-vendor.weblogic-persistence-type.weblogic-major-version.", new Integer(133));
      paths.put(".weblogic-persistence-vendor.weblogic-persistence-type.class-information.readonly-concurrency-bean-manager-class.", new Integer(139));
      paths.put(".weblogic-persistence-vendor.weblogic-persistence-type.class-information.code-generator-class.", new Integer(141));
      paths.put(".weblogic-persistence-vendor.weblogic-persistence-type.cmp-version.", new Integer(134));
      paths.put(".weblogic-persistence-vendor.weblogic-persistence-type.class-information.database-concurrency-bean-manager-class.", new Integer(138));
      paths.put(".weblogic-persistence-vendor.weblogic-persistence-type.class-information.exclusive-concurrency-bean-manager-class.", new Integer(137));
      paths.put(".weblogic-persistence-vendor.weblogic-persistence-type.", new Integer(130));
      paths.put(".weblogic-persistence-vendor.", new Integer(128));
   }
}
