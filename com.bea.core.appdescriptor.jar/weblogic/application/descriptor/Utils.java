package weblogic.application.descriptor;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

public class Utils implements XMLStreamConstants {
   public static String type2Str(int t, XMLStreamReader reader) {
      switch (t) {
         case 1:
            return "START_ELEMENT[" + reader.getLocalName() + "]";
         case 2:
            return "END_ELEMENT[" + reader.getLocalName() + "]";
         case 3:
            return "PROCESSING_INSTRUCTION";
         case 4:
            return "CHARACTERS: [" + new String(reader.getTextCharacters()) + "]";
         case 5:
            return "COMMENT";
         case 6:
            return "SPACE[6]";
         case 7:
            return "START_DOCUMENT[7]";
         case 8:
            return "END_DOCUMENT[8]";
         case 9:
            return "ENTITY_REFERENCE[9]";
         case 10:
            return "ATTRIBUTE";
         case 11:
            return "DTD";
         case 12:
            return "CDATA[12]";
         case 13:
            return "NAMESPACE[13]";
         case 14:
            return "NOTATION_DECLARATION";
         case 15:
            return "ENTITY_DECLARATION";
         default:
            throw new AssertionError("Unexpected type " + t);
      }
   }
}
