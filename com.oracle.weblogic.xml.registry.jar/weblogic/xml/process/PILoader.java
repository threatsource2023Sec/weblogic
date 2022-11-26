package weblogic.xml.process;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import weblogic.utils.AssertionError;
import weblogic.utils.StringUtils;

public final class PILoader extends PILoaderBase implements XMLProcessor, InProcessor {
   private static final boolean debug = false;
   private static final boolean verbose = false;
   private static final Map paths = new HashMap();
   private ProcessorDriver driver;
   private static final String publicId = "-//BEA Systems, Inc.//DTD XML Processing Instructions//EN";
   private static final String localDTDResourceName = "/weblogic/xml/process/xml-to-java.dtd";
   private ProcessingInstructions doc_p1;

   public ProcessorDriver getDriver() {
      return this.driver;
   }

   public PILoader() {
      this(true);
   }

   public PILoader(boolean validate) {
      this.driver = new ProcessorDriver(this, "-//BEA Systems, Inc.//DTD XML Processing Instructions//EN", "/weblogic/xml/process/xml-to-java.dtd", validate);
   }

   public void process(String xmlFilePath) throws IOException, XMLParsingException, XMLProcessingException {
      this.driver.process(xmlFilePath);
   }

   public void process(Reader xmlStream) throws IOException, XMLParsingException, XMLProcessingException {
      this.driver.process(xmlStream);
   }

   public void process(InputStream xmlInputStream) throws IOException, XMLParsingException, XMLProcessingException {
      this.driver.process(xmlInputStream);
   }

   public void process(File xmlFile) throws IOException, XMLParsingException, XMLProcessingException {
      this.driver.process(xmlFile);
   }

   public void preProc(ProcessingContext pctx) throws SAXProcessorException {
      String path = pctx.getPath();
      Integer id = (Integer)paths.get(path);
      if (id != null) {
         switch (id) {
            case 1:
               this.__pre_ProcParam(pctx);
               break;
            case 2:
               this.__pre_ProcPkg(pctx);
               break;
            case 3:
               this.__pre_ProcSuper(pctx);
               break;
            case 4:
               this.__pre_ProcClass(pctx);
               break;
            case 5:
               this.__pre_PubId(pctx);
               break;
            case 6:
               this.__pre_LocalDTD(pctx);
               break;
            case 7:
               this.__pre_DTDUrl(pctx);
               break;
            case 8:
            default:
               throw new AssertionError(id.toString());
            case 9:
               this.__pre_DocStart(pctx);
               break;
            case 10:
               this.__pre_DocStartBindObj(pctx);
               break;
            case 11:
               this.__pre_DocStartJava(pctx);
               break;
            case 12:
               this.__pre_DocEnd(pctx);
               break;
            case 13:
               this.__pre_DocEndBindObj(pctx);
               break;
            case 14:
               this.__pre_DocEndJava(pctx);
               break;
            case 15:
               this.__pre_ProcAction(pctx);
               break;
            case 16:
               this.__pre_ProcBindObj(pctx);
               break;
            case 17:
               this.__pre_ProcValidation(pctx);
               break;
            case 18:
               this.__pre_ProcJava(pctx);
         }

      }
   }

   public void postProc(ProcessingContext pctx) throws SAXProcessorException {
      String path = pctx.getPath();
      Integer id = (Integer)paths.get(path);
      if (id != null) {
         switch (id) {
            case 1:
               this.__post_ProcParam(pctx);
               break;
            case 2:
               this.__post_ProcPkg(pctx);
               break;
            case 3:
               this.__post_ProcSuper(pctx);
               break;
            case 4:
               this.__post_ProcClass(pctx);
               break;
            case 5:
               this.__post_PubId(pctx);
               break;
            case 6:
               this.__post_LocalDTD(pctx);
               break;
            case 7:
               this.__post_DTDUrl(pctx);
               break;
            case 8:
            default:
               throw new AssertionError(id.toString());
            case 9:
               this.__post_DocStart(pctx);
               break;
            case 10:
               this.__post_DocStartBindObj(pctx);
               break;
            case 11:
               this.__post_DocStartJava(pctx);
               break;
            case 12:
               this.__post_DocEnd(pctx);
               break;
            case 13:
               this.__post_DocEndBindObj(pctx);
               break;
            case 14:
               this.__post_DocEndJava(pctx);
               break;
            case 15:
               this.__post_ProcAction(pctx);
               break;
            case 16:
               this.__post_ProcBindObj(pctx);
               break;
            case 17:
               this.__post_ProcValidation(pctx);
               break;
            case 18:
               this.__post_ProcJava(pctx);
         }

      }
   }

   public ProcessingInstructions getProcessingInstructions() {
      return this.doc_p1;
   }

   private void __pre_ProcParam(ProcessingContext pctx) {
      this.doc_p1 = new ProcessingInstructions();
   }

   private void __post_ProcParam(ProcessingContext pctx) throws SAXProcessorException {
   }

   private void __pre_ProcPkg(ProcessingContext pctx) {
   }

   private void __post_ProcPkg(ProcessingContext pctx) throws SAXProcessorException {
      String v = Functions.value(pctx);
      this.doc_p1.setProcessorPackage(v);
   }

   private void __pre_ProcSuper(ProcessingContext pctx) {
   }

   private void __post_ProcSuper(ProcessingContext pctx) throws SAXProcessorException {
      String v = Functions.value(pctx);
      this.doc_p1.setProcessorSuperClass(v);
   }

   private void __pre_ProcClass(ProcessingContext pctx) {
   }

   private void __post_ProcClass(ProcessingContext pctx) throws SAXProcessorException {
      String v = Functions.value(pctx);
      this.doc_p1.setProcessorClass(v);
   }

   private void __pre_PubId(ProcessingContext pctx) {
   }

   private void __post_PubId(ProcessingContext pctx) throws SAXProcessorException {
      String v = Functions.value(pctx);
      this.doc_p1.setPublicId(v);
   }

   private void __pre_LocalDTD(ProcessingContext pctx) {
   }

   private void __post_LocalDTD(ProcessingContext pctx) throws SAXProcessorException {
      String v = Functions.value(pctx);
      this.doc_p1.setLocalDTDResourceName(v);

      try {
         InputStream dtdIn = this.getClass().getResourceAsStream(v);
         if (dtdIn == null) {
            throw new SAXProcessorException("Could not locate local DTD resource: " + v);
         } else {
            this.setDTD(dtdIn);
         }
      } catch (DTDParsingException var4) {
         var4.printStackTrace();
         throw new SAXProcessorException(var4);
      } catch (IOException var5) {
         throw new SAXProcessorException(var5);
      }
   }

   private void __pre_DTDUrl(ProcessingContext pctx) {
   }

   private void __post_DTDUrl(ProcessingContext pctx) throws SAXProcessorException {
      String v = Functions.value(pctx);
      this.doc_p1.setDtdURL(v);
   }

   private void __pre_DocStart(ProcessingContext pctx) {
      PAction a1 = new PAction();
      pctx.addBoundObject(a1, "a1");
   }

   private void __post_DocStart(ProcessingContext pctx) throws SAXProcessorException {
      PAction a1 = (PAction)pctx.getBoundObject("a1");
      this.doc_p1.setDocumentStartAction(a1);
   }

   private void __pre_DocStartBindObj(ProcessingContext pctx) {
   }

   private void __post_DocStartBindObj(ProcessingContext pctx) throws SAXProcessorException {
      String v = Functions.value(pctx);
      String attr_c1 = Functions.value(pctx, "@class");
      String attr_v1 = Functions.value(pctx, "@var-name");
      String attr_s1 = Functions.value(pctx, "@scope");
      String attr_n1 = Functions.value(pctx, "@initialize");
      PAction a1 = (PAction)pctx.getBoundObject("a1");
      Binding binding = new Binding();
      if (attr_c1 != null) {
         binding.setClassName(attr_c1);
      }

      if (attr_v1 != null) {
         binding.setVariableName(attr_v1);
      }

      if (attr_s1 != null) {
         binding.setHasDocumentScope("document".equals(attr_s1));
      }

      if (attr_n1 != null) {
         binding.setInitialize("true".equals(attr_n1));
      }

      a1.addBinding(binding);
   }

   private void __pre_DocStartJava(ProcessingContext pctx) {
   }

   private void __post_DocStartJava(ProcessingContext pctx) throws SAXProcessorException {
      String v = Functions.value(pctx);
      PAction a1 = (PAction)pctx.getBoundObject("a1");
      a1.setJavaCode(v);
   }

   private void __pre_DocEnd(ProcessingContext pctx) {
      PAction a1 = new PAction();
      pctx.addBoundObject(a1, "a1");
   }

   private void __post_DocEnd(ProcessingContext pctx) throws SAXProcessorException {
      PAction a1 = (PAction)pctx.getBoundObject("a1");
      this.doc_p1.setDocumentEndAction(a1);
   }

   private void __pre_DocEndBindObj(ProcessingContext pctx) {
   }

   private void __post_DocEndBindObj(ProcessingContext pctx) throws SAXProcessorException {
      String v = Functions.value(pctx);
      String attr_c1 = Functions.value(pctx, "@class");
      String attr_v1 = Functions.value(pctx, "@var-name");
      String attr_s1 = Functions.value(pctx, "@scope");
      String attr_n1 = Functions.value(pctx, "@initialize");
      PAction a1 = (PAction)pctx.getBoundObject("a1");
      Binding binding = new Binding();
      if (attr_c1 != null) {
         binding.setClassName(attr_c1);
      }

      if (attr_v1 != null) {
         binding.setVariableName(attr_v1);
      }

      if (attr_s1 != null) {
         binding.setHasDocumentScope("document".equals(attr_s1));
      }

      if (attr_n1 != null) {
         binding.setInitialize("true".equals(attr_n1));
      }

      a1.addBinding(binding);
   }

   private void __pre_DocEndJava(ProcessingContext pctx) {
   }

   private void __post_DocEndJava(ProcessingContext pctx) throws SAXProcessorException {
      String v = Functions.value(pctx);
      PAction a1 = (PAction)pctx.getBoundObject("a1");
      a1.setJavaCode(v);
   }

   private void __pre_ProcAction(ProcessingContext pctx) {
      PAction a1 = new PAction();
      pctx.addBoundObject(a1, "a1");
   }

   private void __post_ProcAction(ProcessingContext pctx) throws SAXProcessorException {
      String v = Functions.value(pctx);
      String attr_e1 = Functions.value(pctx, "@element");
      String attr_e2 = Functions.value(pctx, "@element-context");
      String attr_p1 = Functions.value(pctx, "@phase");
      PAction a1 = (PAction)pctx.getBoundObject("a1");
      if (!this.isInDTD(attr_e1)) {
         throw new SAXProcessorException("Element \"" + attr_e1 + "\" is not found in the DTD");
      } else {
         a1.setElementName(attr_e1);
         String[] paths = this.getPathsFromContext(attr_e1, attr_e2);
         if (paths != null && paths.length != 0) {
            if (paths != null) {
               a1.setPaths(paths);
            }

            if (attr_p1 != null) {
               a1.setIsStartAction("element-start".equals(attr_p1));
            }

            this.doc_p1.addProcessingAction(a1);
         } else {
            throw new SAXProcessorException("Element \"" + attr_e1 + "\" does not occur in context \"" + attr_e2 + "\" in the DTD");
         }
      }
   }

   private void __pre_ProcBindObj(ProcessingContext pctx) {
   }

   private void __post_ProcBindObj(ProcessingContext pctx) throws SAXProcessorException {
      String v = Functions.value(pctx);
      String attr_c1 = Functions.value(pctx, "@class");
      String attr_v1 = Functions.value(pctx, "@var-name");
      String attr_s1 = Functions.value(pctx, "@scope");
      String attr_n1 = Functions.value(pctx, "@initialize");
      PAction a1 = (PAction)pctx.getBoundObject("a1");
      Binding binding = new Binding();
      if (attr_c1 != null) {
         binding.setClassName(attr_c1);
      }

      if (attr_v1 != null) {
         binding.setVariableName(attr_v1);
      }

      if (attr_s1 != null) {
         binding.setHasDocumentScope("document".equals(attr_s1));
      }

      if (attr_n1 != null) {
         binding.setInitialize("true".equals(attr_n1));
      }

      a1.addBinding(binding);
   }

   private void __pre_ProcValidation(ProcessingContext pctx) {
   }

   private void __post_ProcValidation(ProcessingContext pctx) throws SAXProcessorException {
      String v = Functions.value(pctx);
      String attr_n1 = Functions.value(pctx, "@nullable");
      String attr_v1 = Functions.value(pctx, "@values");
      String attr_m1 = Functions.value(pctx, "@method");
      PAction a1 = (PAction)pctx.getBoundObject("a1");
      Validation vn = new Validation();
      if (attr_n1 != null) {
         vn.setIsNullable(attr_n1.equalsIgnoreCase("true"));
      }

      if (attr_v1 != null) {
         String[] valids = StringUtils.splitCompletely(attr_v1, "|");
         vn.addValidValues(valids);
      }

      if (attr_m1 != null) {
         vn.setMethodName(attr_m1);
      }

      a1.setValidation(vn);
   }

   private void __pre_ProcJava(ProcessingContext pctx) {
   }

   private void __post_ProcJava(ProcessingContext pctx) throws SAXProcessorException {
      String v = Functions.value(pctx);
      PAction a1 = (PAction)pctx.getBoundObject("a1");
      a1.setJavaCode(v);
   }

   public static void main(String[] args) throws Exception {
   }

   static {
      paths.put(".xml-to-java.processor-params.", new Integer(1));
      paths.put(".xml-to-java.processor-params.processor-package.", new Integer(2));
      paths.put(".xml-to-java.processor-params.processor-superclass.", new Integer(3));
      paths.put(".xml-to-java.processor-params.processor-class.", new Integer(4));
      paths.put(".xml-to-java.processor-params.xml-public-id.", new Integer(5));
      paths.put(".xml-to-java.processor-params.local-dtd-resource-name.", new Integer(6));
      paths.put(".xml-to-java.processor-params.dtd-url.", new Integer(7));
      paths.put(".xml-to-java.document-start-action.", new Integer(9));
      paths.put(".xml-to-java.document-start-action.declare-obj.", new Integer(10));
      paths.put(".xml-to-java.document-start-action.java.", new Integer(11));
      paths.put(".xml-to-java.document-end-action.", new Integer(12));
      paths.put(".xml-to-java.document-end-action.declare-obj.", new Integer(13));
      paths.put(".xml-to-java.document-end-action.java.", new Integer(14));
      paths.put(".xml-to-java.processing-action.", new Integer(15));
      paths.put(".xml-to-java.processing-action.declare-obj.", new Integer(16));
      paths.put(".xml-to-java.processing-action.validation.", new Integer(17));
      paths.put(".xml-to-java.processing-action.java.", new Integer(18));
   }
}
