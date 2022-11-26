package weblogic.j2ee.dd.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import org.xml.sax.InputSource;
import weblogic.j2ee.dd.EJBModuleDescriptor;
import weblogic.j2ee.dd.JavaModuleDescriptor;
import weblogic.j2ee.dd.RoleDescriptor;
import weblogic.j2ee.dd.WebModuleDescriptor;
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

public final class J2EEDeploymentDescriptorLoader_J2EE12 extends J2EEDeploymentDescriptorLoader implements XMLProcessor, InProcessor {
   private static final boolean debug = true;
   private static final boolean verbose = true;
   private static final Map paths = new HashMap();
   private ProcessorDriver driver;
   private static final String publicId = "-//Sun Microsystems, Inc.//DTD J2EE Application 1.2//EN";
   private static final String localDTDResourceName = "/weblogic/j2ee/dd/xml/application_1_2.dtd";

   public ProcessorDriver getDriver() {
      return this.driver;
   }

   public J2EEDeploymentDescriptorLoader_J2EE12() {
      this(true);
   }

   public J2EEDeploymentDescriptorLoader_J2EE12(boolean var1) {
      this.driver = new ProcessorDriver(this, "-//Sun Microsystems, Inc.//DTD J2EE Application 1.2//EN", "/weblogic/j2ee/dd/xml/application_1_2.dtd", var1);
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

   private void __pre_141(ProcessingContext var1) {
   }

   private void __post_141(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      WebModuleDescriptor var3 = (WebModuleDescriptor)var1.getBoundObject("wd");
      var3.setContext(var2);
   }

   private void __pre_133(ProcessingContext var1) {
      RoleDescriptor var2 = new RoleDescriptor();
      var1.addBoundObject(var2, "rd");
   }

   private void __post_133(ProcessingContext var1) throws SAXProcessorException {
      RoleDescriptor var2 = (RoleDescriptor)var1.getBoundObject("rd");
      this.dd.addSecurityRole(var2);
   }

   private void __pre_140(ProcessingContext var1) {
   }

   private void __post_140(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      WebModuleDescriptor var3 = (WebModuleDescriptor)var1.getBoundObject("wd");
      var3.setWebURI(var2);
   }

   private void __pre_134(ProcessingContext var1) {
   }

   private void __post_134(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      RoleDescriptor var3 = (RoleDescriptor)var1.getBoundObject("rd");
      var3.setDescription(var2);
   }

   private void __pre_135(ProcessingContext var1) {
   }

   private void __post_135(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      RoleDescriptor var3 = (RoleDescriptor)var1.getBoundObject("rd");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1463801669](.application.security-role.role-name.) must be a non-empty string");
      } else {
         var3.setName(var2);
      }
   }

   private void __pre_128(ProcessingContext var1) {
   }

   private void __post_128(ProcessingContext var1) throws SAXProcessorException {
      this.dd.setVersion("1.2");
   }

   private void __pre_136(ProcessingContext var1) {
   }

   private void __post_136(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      super.getCurrentModuleContext().setAltDDURI(var2);
   }

   private void __pre_130(ProcessingContext var1) {
   }

   private void __post_130(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      this.dd.setDisplayName(var2);
   }

   private void __pre_129(ProcessingContext var1) {
   }

   private void __post_129(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      this.dd.setDescription(var2);
   }

   private void __pre_139(ProcessingContext var1) {
      WebModuleDescriptor var2 = new WebModuleDescriptor();
      var1.addBoundObject(var2, "wd");
   }

   private void __post_139(ProcessingContext var1) throws SAXProcessorException {
      WebModuleDescriptor var2 = (WebModuleDescriptor)var1.getBoundObject("wd");
      this.dd.addWebModule(var2);
      super.setCurrentModuleContext(var2);
   }

   private void __pre_138(ProcessingContext var1) {
      JavaModuleDescriptor var2 = new JavaModuleDescriptor();
      var1.addBoundObject(var2, "jd");
   }

   private void __post_138(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      JavaModuleDescriptor var3 = (JavaModuleDescriptor)var1.getBoundObject("jd");
      var3.setURI(var2);
      this.dd.addJavaModule(var3);
      super.setCurrentModuleContext(var3);
   }

   private void __pre_131(ProcessingContext var1) {
   }

   private void __post_131(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      this.dd.setSmallIconFileName(var2);
   }

   private void __pre_132(ProcessingContext var1) {
   }

   private void __post_132(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      this.dd.setLargeIconFileName(var2);
   }

   private void __pre_137(ProcessingContext var1) {
      EJBModuleDescriptor var2 = new EJBModuleDescriptor();
      var1.addBoundObject(var2, "ed");
   }

   private void __post_137(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      EJBModuleDescriptor var3 = (EJBModuleDescriptor)var1.getBoundObject("ed");
      var3.setURI(var2);
      this.dd.addEJBModule(var3);
      super.setCurrentModuleContext(var3);
   }

   public void addBoundObject(Object var1, String var2) {
      this.driver.currentNode().addBoundObject(var1, var2);
   }

   static {
      paths.put(".application.module.web.context-root.", new Integer(141));
      paths.put(".application.security-role.", new Integer(133));
      paths.put(".application.module.web.web-uri.", new Integer(140));
      paths.put(".application.security-role.description.", new Integer(134));
      paths.put(".application.security-role.role-name.", new Integer(135));
      paths.put(".application.", new Integer(128));
      paths.put(".application.module.alt-dd.", new Integer(136));
      paths.put(".application.display-name.", new Integer(130));
      paths.put(".application.description.", new Integer(129));
      paths.put(".application.module.web.", new Integer(139));
      paths.put(".application.module.java.", new Integer(138));
      paths.put(".application.icon.small-icon.", new Integer(131));
      paths.put(".application.icon.large-icon.", new Integer(132));
      paths.put(".application.module.ejb.", new Integer(137));
   }
}
