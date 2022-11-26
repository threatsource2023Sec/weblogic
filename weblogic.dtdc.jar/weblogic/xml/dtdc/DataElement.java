package weblogic.xml.dtdc;

import java.io.IOException;
import java.io.PrintWriter;
import org.xml.sax.AttributeList;

public class DataElement implements GeneratedElement {
   private String value;

   public DataElement(String value) {
      this.value = value;
   }

   public String getValue() {
      return this.value;
   }

   public void initialize(String name, AttributeList attrList) {
   }

   public void toXML(PrintWriter writer, int depth) throws IOException {
      this.toXML(writer);
   }

   public void toXML(PrintWriter writer) throws IOException {
      writer.print(this.value);
   }

   public boolean isEmpty() {
      return true;
   }

   public String getElementName() {
      return "CDATA";
   }

   public boolean equals(Object other) {
      if (other instanceof DataElement) {
         DataElement that = (DataElement)other;
         if (that.getValue().trim().equals(this.getValue().trim())) {
            return true;
         }
      }

      return false;
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.value == null ? 0 : this.value.hashCode());
      return result;
   }
}
