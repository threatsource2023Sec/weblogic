package com.bea.xml.stream.util;

public class ElementTypeNames {
   public static final String getEventTypeString(int eventType) {
      switch (eventType) {
         case 1:
            return "START_ELEMENT";
         case 2:
            return "END_ELEMENT";
         case 3:
            return "PROCESSING_INSTRUCTION";
         case 4:
            return "CHARACTERS";
         case 5:
            return "COMMENT";
         case 6:
            return "SPACE";
         case 7:
            return "START_DOCUMENT";
         case 8:
            return "END_DOCUMENT";
         case 9:
            return "ENTITY_REFERENCE";
         case 10:
            return "ATTRIBUTE";
         case 11:
            return "DTD";
         case 12:
            return "CDATA";
         case 13:
            return "NAMESPACE";
         default:
            return "UNKNOWN_EVENT_TYPE";
      }
   }

   public static int getEventType(String val) {
      if (val.equals("START_ELEMENT")) {
         return 1;
      } else if (val.equals("SPACE")) {
         return 6;
      } else if (val.equals("END_ELEMENT")) {
         return 2;
      } else if (val.equals("PROCESSING_INSTRUCTION")) {
         return 3;
      } else if (val.equals("CHARACTERS")) {
         return 4;
      } else if (val.equals("COMMENT")) {
         return 5;
      } else if (val.equals("START_DOCUMENT")) {
         return 7;
      } else if (val.equals("END_DOCUMENT")) {
         return 8;
      } else if (val.equals("ATTRIBUTE")) {
         return 10;
      } else if (val.equals("DTD")) {
         return 11;
      } else if (val.equals("CDATA")) {
         return 12;
      } else {
         return val.equals("NAMESPACE") ? 13 : -1;
      }
   }
}
