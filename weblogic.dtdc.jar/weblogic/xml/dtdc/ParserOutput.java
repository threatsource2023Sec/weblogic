package weblogic.xml.dtdc;

import com.ibm.xml.parser.DTD;
import weblogic.utils.compiler.CodeGenerator;

public class ParserOutput extends CodeGenerator.Output {
   private DTD dtd;
   private String dtdname;

   ParserOutput(String outputFile, String packageName, String template, DTD dtd, String dtdname) {
      super(outputFile, template, packageName);
      this.dtd = dtd;
      this.dtdname = dtdname;
   }

   public DTD getDTD() {
      return this.dtd;
   }

   public String getDTDName() {
      return this.dtdname;
   }

   public boolean equals(Object object) {
      if (object != null && object instanceof ParserOutput) {
         ParserOutput output = (ParserOutput)object;
         return output.getDTD().equals(this.getDTD()) && output.getDTDName().equals(this.getDTDName());
      } else {
         return false;
      }
   }
}
