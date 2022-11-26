package weblogic.xml.process;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.xml.sax.InputSource;
import weblogic.utils.AssertionError;
import weblogic.utils.Debug;

public final class GILoader extends GILoaderBase implements XMLProcessor, InProcessor {
   private static final boolean debug = true;
   private static final boolean verbose = true;
   private static final Map paths = new HashMap();
   private ProcessorDriver driver;
   private static final String publicId = "-//BEA Systems, Inc.//DTD XML Generating Instructions//EN";
   private static final String localDTDResourceName = "/weblogic/xml/process/java-to-xml.dtd";

   public ProcessorDriver getDriver() {
      return this.driver;
   }

   public GILoader() {
      this(true);
   }

   public GILoader(boolean var1) {
      this.driver = new ProcessorDriver(this, "-//BEA Systems, Inc.//DTD XML Generating Instructions//EN", "/weblogic/xml/process/java-to-xml.dtd", var1);
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
            default:
               throw new AssertionError(var3.toString());
         }

      }
   }

   private void __pre_138(ProcessingContext var1) {
      ArrayList var2 = new ArrayList();
      var1.addBoundObject(var2, "args");
   }

   private void __post_138(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1, " @element ");
      String var3 = Functions.value(var1, " @from-var ");
      String var4 = Functions.value(var1, " @element-context ");
      ArrayList var5 = (ArrayList)var1.getBoundObject("args");
      GAction var6 = (GAction)var1.getBoundObject("gi");
      ArrayList var7 = (ArrayList)var1.getBoundObject("params");
      WriteXmlFunctionRef var8 = new WriteXmlFunctionRef(var2, var4);
      if (var3 != null) {
         var8.setFromVar(var3);
      }

      var8.setArgs(var5);
      var6.addCodeFragment(var8);
   }

   private void __pre_137(ProcessingContext var1) {
   }

   private void __post_137(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1, "@from-var");
      String var3 = Functions.value(var1, "@name");
      GAction var4 = (GAction)var1.getBoundObject("gi");
      ArrayList var5 = (ArrayList)var1.getBoundObject("params");
      SetAttrValFunctionRef var6 = new SetAttrValFunctionRef(var3);
      if (var2 != null) {
         var6.setFromVar(var2);
      }

      var4.addCodeFragment(var6);
   }

   private void __pre_139(ProcessingContext var1) {
   }

   private void __post_139(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      ArrayList var3 = (ArrayList)var1.getBoundObject("args");
      GAction var4 = (GAction)var1.getBoundObject("gi");
      ArrayList var5 = (ArrayList)var1.getBoundObject("params");
      var3.add(var2);
   }

   private void __pre_130(ProcessingContext var1) {
   }

   private void __post_130(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      this.instrux.setProcessorClass(var2);
   }

   private void __pre_136(ProcessingContext var1) {
   }

   private void __post_136(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1, "@from-var");
      String var3 = Functions.value(var1);
      GAction var4 = (GAction)var1.getBoundObject("gi");
      ArrayList var5 = (ArrayList)var1.getBoundObject("params");
      WriteTextFunctionRef var6 = new WriteTextFunctionRef(var3);
      if (var2 != null) {
         var6.setFromVar(var2);
      }

      var4.addCodeFragment(var6);
   }

   private void __pre_135(ProcessingContext var1) {
   }

   private void __post_135(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      GAction var3 = (GAction)var1.getBoundObject("gi");
      ArrayList var4 = (ArrayList)var1.getBoundObject("params");
      var3.addCodeFragment(var2);
   }

   private void __pre_133(ProcessingContext var1) {
      GAction var2 = new GAction();
      var1.addBoundObject(var2, "gi");
      ArrayList var3 = new ArrayList();
      var1.addBoundObject(var3, "params");
   }

   private void __post_133(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1, "@element-context");
      String var3 = Functions.value(var1, "@element");
      GAction var4 = (GAction)var1.getBoundObject("gi");
      ArrayList var5 = (ArrayList)var1.getBoundObject("params");
      var4.setElementName(var3);
      var4.setElementContext(var2);
      var4.setParams(var5);
      this.instrux.addGeneratingAction(var4);
   }

   private void __pre_129(ProcessingContext var1) {
   }

   private void __post_129(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      this.instrux.setProcessorSuperClass(var2);
   }

   private void __pre_131(ProcessingContext var1) {
   }

   private void __post_131(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      this.instrux.setPublicId(var2);
   }

   private void __pre_128(ProcessingContext var1) {
   }

   private void __post_128(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      this.instrux.setProcessorPackage(var2);
   }

   private void __pre_132(ProcessingContext var1) {
   }

   private void __post_132(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      this.instrux.setDtdURL(var2);
   }

   private void __pre_134(ProcessingContext var1) {
   }

   private void __post_134(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1, "@class");
      String var3 = Functions.value(var1, "@name");
      GAction var4 = (GAction)var1.getBoundObject("gi");
      ArrayList var5 = (ArrayList)var1.getBoundObject("params");
      String var6 = var3;
      String var7 = var2;

      try {
         Class var8 = Class.forName(var7);
         var5.add(new GAction.Param(var6, var8));
      } catch (ClassNotFoundException var9) {
         throw new SAXValidationException("Invalid class " + var2 + "; could not be loaded");
      }
   }

   public void addBoundObject(Object var1, String var2) {
      this.driver.currentNode().addBoundObject(var1, var2);
   }

   static {
      paths.put(".java-to-xml.generate-element.java.write-xml.", new Integer(138));
      paths.put(".java-to-xml.generate-element.java.set-attribute-value.", new Integer(137));
      paths.put(".java-to-xml.generate-element.java.write-xml.param.", new Integer(139));
      paths.put(".java-to-xml.processor-params.processor-class.", new Integer(130));
      paths.put(".java-to-xml.generate-element.java.write-text.", new Integer(136));
      paths.put(".java-to-xml.generate-element.java.#text.", new Integer(135));
      paths.put(".java-to-xml.generate-element.", new Integer(133));
      paths.put(".java-to-xml.processor-params.processor-superclass.", new Integer(129));
      paths.put(".java-to-xml.processor-params.xml-public-id.", new Integer(131));
      paths.put(".java-to-xml.processor-params.processor-package.", new Integer(128));
      paths.put(".java-to-xml.processor-params.dtd-url.", new Integer(132));
      paths.put(".java-to-xml.generate-element.declare-param.", new Integer(134));
   }
}
