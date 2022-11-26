package weblogic.xml.dtdc;

import com.ibm.xml.parser.DTD;
import com.ibm.xml.parser.ElementDecl;
import weblogic.utils.compiler.CodeGenerator;

public class ElementOutput extends CodeGenerator.Output {
   private ElementDecl elementDecl;
   private DTD dtd;

   ElementOutput(String outputFile, String packageName, String template, ElementDecl elementDecl, DTD dtd) {
      super(outputFile, template, packageName);
      this.elementDecl = elementDecl;
      this.dtd = dtd;
   }

   public ElementDecl getElementDecl() {
      return this.elementDecl;
   }

   public DTD getDTD() {
      return this.dtd;
   }

   public boolean equals(Object object) {
      if (object != null && object instanceof ElementOutput) {
         ElementOutput output = (ElementOutput)object;
         return output.getOutputFile().equals(this.getOutputFile()) && output.getPackage().equals(this.getPackage());
      } else {
         return false;
      }
   }
}
