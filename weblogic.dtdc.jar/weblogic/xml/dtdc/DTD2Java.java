package weblogic.xml.dtdc;

import com.ibm.xml.parser.AttDef;
import com.ibm.xml.parser.DTD;
import com.ibm.xml.parser.ElementDecl;
import com.ibm.xml.parser.InsertableElement;
import com.ibm.xml.parser.Parser;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import weblogic.utils.Getopt2;
import weblogic.utils.compiler.BadOutputException;
import weblogic.utils.compiler.CodeGenerationException;
import weblogic.utils.compiler.CodeGenerator;

public class DTD2Java extends CodeGenerator {
   private static final boolean debug = false;
   private static final boolean verbose = false;
   private static final String EOL = System.getProperty("line.separator");
   static final String PACKAGE = "package";
   private ElementOutput currentOutput;
   private Vector outputs;
   private DTD dtd;
   private String packageName;
   private AttDef currentAttribute;
   private String currentSubElement;

   public DTD2Java(Getopt2 opts) {
      super(opts);
      opts.addOption("package", "weblogic.xml.objects", "Name of the base package for objects");
   }

   protected void extractOptionValues(Getopt2 opts) {
      this.packageName = opts.getOption("package", "weblogic.xml.objects");
   }

   public Enumeration outputs(Object[] inputs) throws Exception {
      String[] filenames = (String[])((String[])inputs);
      this.outputs = new Vector();

      for(int i = 0; i < filenames.length; ++i) {
         File f = new File((new File(filenames[i])).getAbsolutePath());
         InputStream is = new FileInputStream(f);
         Parser parse = new Parser(f.getParentFile().toURL().toString());
         DTD dtd = parse.readDTDStream(is);
         Enumeration elementDeclartions = dtd.getElementDeclarations();

         while(elementDeclartions.hasMoreElements()) {
            ElementDecl elementDecl = (ElementDecl)elementDeclartions.nextElement();
            String name = elementDecl.getName();
            String packageName = this.packageName;
            packageName = packageName + NameMangler.getpackage(name);
            this.outputs.addElement(new ElementOutput(NameMangler.upcase(NameMangler.depackage(name)) + ".java", packageName, "element.j", elementDecl, dtd));
         }
      }

      return this.outputs.elements();
   }

   protected void prepare(CodeGenerator.Output output) throws BadOutputException {
      this.currentOutput = (ElementOutput)output;
   }

   public ElementDecl getElement() {
      return this.currentOutput.getElementDecl();
   }

   public DTD getDTD() {
      return this.currentOutput.getDTD();
   }

   public String element_realname() {
      return this.getElement().getName();
   }

   public String element_name() {
      return NameMangler.depackage(this.getElement().getName());
   }

   public String element_class_name() {
      return NameMangler.upcase(this.element_name());
   }

   public String package_name() {
      return this.packageName;
   }

   public String is_empty() {
      return this.getElement().getContentType() == 1 ? "true" : "false";
   }

   public String attributes() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      Enumeration attributes = this.getDTD().getAttributeDeclarations(this.getElement().getName());

      while(attributes.hasMoreElements()) {
         this.currentAttribute = (AttDef)attributes.nextElement();
         sb.append(this.parse(this.getProductionRule("attribute")));
      }

      return sb.toString();
   }

   public String set_attributes() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      Enumeration attributes = this.getDTD().getAttributeDeclarations(this.getElement().getName());

      while(attributes.hasMoreElements()) {
         this.currentAttribute = (AttDef)attributes.nextElement();
         sb.append(this.parse(this.getProductionRule("set_attribute")));
      }

      return sb.toString();
   }

   public String attribute_realname() {
      return this.currentAttribute.getName();
   }

   public String attribute_name() {
      return NameMangler.depackage(this.currentAttribute.getName());
   }

   public String attribute_varname() {
      return (this.attribute_name() + "Value").replace(':', '_').replace('-', '_');
   }

   public String attribute_default() {
      int type = this.currentAttribute.getDefaultType();
      return type != 1 && type != -1 ? "" : this.currentAttribute.getDefaultStringValue();
   }

   public String attribute_value_getter() {
      return "get" + NameMangler.upcase(this.attribute_name()) + "Attribute";
   }

   public String attribute_value_setter() {
      return "set" + NameMangler.upcase(this.attribute_name()) + "Attribute";
   }

   public String subelements() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      Hashtable ht = this.getDTD().prepareTable(this.getElement().getName());
      Enumeration insertableElementsNames = ht.keys();

      while(insertableElementsNames.hasMoreElements()) {
         InsertableElement ie = (InsertableElement)ht.get(insertableElementsNames.nextElement());
         if (this.getDTD().getElementDeclaration(ie.name) != null) {
            this.currentSubElement = NameMangler.depackage(ie.name);
            sb.append(this.parse(this.getProductionRule("subelement")));
         }
      }

      return sb.toString();
   }

   public String sub_element_name() {
      return NameMangler.depackage(this.currentSubElement);
   }

   public String sub_element_varname() {
      return this.sub_element_name() + "SubElements";
   }

   public String sub_elements_getter() {
      return this.sub_element_getter() + "s";
   }

   public String sub_element_class_name() {
      return NameMangler.upcase(this.sub_element_name());
   }

   public String sub_element_getter() {
      return "get" + NameMangler.upcase(this.sub_element_name()) + "Element";
   }

   public String sub_element_adder() {
      return "add" + NameMangler.upcase(this.sub_element_name()) + "Element";
   }
}
