package com.octetstring.vde.schema;

import com.octetstring.vde.syntax.DirectoryString;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class SchemaXMLHandler extends DefaultHandler {
   private static final int DSML_OC = 0;
   private static final int DSML_AT = 1;
   private int currentType = 0;
   private static final int OP_IGNORE = 0;
   private static final int OP_NAME = 1;
   private static final int OP_DESC = 2;
   private static final int OP_OID = 3;
   private static final int OP_SYNTAX = 4;
   private static final int OP_SINGLEVAL = 5;
   private static final int OP_USERMOD = 6;
   private static final int OP_EQUALITY = 7;
   private static final int OP_ORDERING = 8;
   private static final int OP_SUBSTRING = 9;
   private int currentOp = 0;
   private ObjectClass currentOC = null;
   private AttributeType currentAT = null;

   public void characters(char[] ch, int start, int length) {
      String text = new String(ch, start, length);
      if (this.currentType == 0) {
         if (this.currentOp == 1) {
            this.currentOC.setName(new DirectoryString(text));
         } else if (this.currentOp == 2) {
            this.currentOC.setDescription(text);
         } else if (this.currentOp == 3) {
            this.currentOC.setOid(text);
         }
      } else if (this.currentType == 1) {
         if (this.currentOp == 1) {
            this.currentAT.setName(new DirectoryString(text));
         } else if (this.currentOp == 2) {
            this.currentAT.setDescription(text);
         } else if (this.currentOp == 3) {
            this.currentAT.setOid(text);
         } else if (this.currentOp == 7) {
            this.currentAT.setEqualityMatch(text);
         } else if (this.currentOp == 6) {
            if (text.equalsIgnoreCase("false")) {
               this.currentAT.setNoUserModification(true);
            }
         } else if (this.currentOp == 5) {
            if (text.equalsIgnoreCase("true")) {
               this.currentAT.setSingleValue(true);
            }
         } else if (this.currentOp == 4) {
            this.currentAT.setSyntax(text);
         } else if (this.currentOp == 8) {
            this.currentAT.setOrderingMatch(text);
         } else if (this.currentOp == 9) {
            this.currentAT.setSubstrMatch(text);
         }
      }

      this.currentOp = 0;
   }

   public void endElement(String scratch1, String scratch2, String name) {
      if (name.equals("dsml:class")) {
         SchemaChecker.getInstance().addObjectClass(this.currentOC);
      } else if (name.equals("dsml:attribute-type")) {
         SchemaChecker.getInstance().addAttributeType(this.currentAT);
      }

      this.currentOp = 0;
   }

   public void startElement(String scratch1, String scratch2, String name, Attributes atts) {
      String bound;
      String req;
      if (name.equals("dsml:class")) {
         this.currentType = 0;
         this.currentOC = new ObjectClass();
         bound = atts.getValue("superior");
         if (bound != null) {
            this.currentOC.setSuperior(new DirectoryString(bound.substring(1)));
         }

         req = atts.getValue("type");
         if (req != null) {
            if (req.equals("abstract")) {
               this.currentOC.setType(0);
            } else if (req.equals("auxilary")) {
               this.currentOC.setType(2);
            } else {
               this.currentOC.setType(1);
            }
         }
      } else if (name.equals("dsml:name")) {
         this.currentOp = 1;
      } else if (name.equals("dsml:description")) {
         this.currentOp = 2;
      } else if (name.equals("dsml:object-identifier")) {
         this.currentOp = 3;
      } else if (name.equals("dsml:attribute")) {
         if (this.currentType == 0) {
            bound = atts.getValue("ref");
            req = atts.getValue("required");
            if (req != null && bound != null && req.equals("true")) {
               this.currentOC.addMust(new DirectoryString(bound.substring(1)));
            } else if (bound != null) {
               this.currentOC.addMay(new DirectoryString(bound.substring(1)));
            }
         }
      } else if (name.equals("dsml:attribute-type")) {
         this.currentType = 1;
         this.currentAT = new AttributeType();
         bound = atts.getValue("superior");
         if (bound != null) {
            this.currentAT.setSuperior(new DirectoryString(bound.substring(1)));
         }
      } else if (name.equals("dsml:name")) {
         if (this.currentType == 1) {
            this.currentOp = 1;
         }
      } else if (name.equals("dsml:description")) {
         if (this.currentType == 1) {
            this.currentOp = 2;
         }
      } else if (name.equals("dsml:syntax")) {
         if (this.currentType == 1) {
            this.currentOp = 4;
            bound = atts.getValue("bound");
            if (bound != null) {
               this.currentAT.setBound(new Integer(bound));
            }
         }
      } else if (name.equals("dsml:object-identifier")) {
         if (this.currentType == 1) {
            this.currentOp = 3;
         }
      } else if (name.equals("dsml:single-value")) {
         if (this.currentType == 1) {
            this.currentOp = 5;
         }
      } else if (name.equals("dsml:user-modification")) {
         if (this.currentType == 1) {
            this.currentOp = 6;
         }
      } else if (name.equals("dsml:equality")) {
         if (this.currentType == 1) {
            this.currentOp = 7;
         }
      } else if (name.equals("dsml:ordering")) {
         if (this.currentType == 1) {
            this.currentOp = 8;
         }
      } else if (name.equals("dsml:substring") && this.currentType == 1) {
         this.currentOp = 9;
      }

   }
}
