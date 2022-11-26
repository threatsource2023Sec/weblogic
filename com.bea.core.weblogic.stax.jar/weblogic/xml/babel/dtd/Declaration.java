package weblogic.xml.babel.dtd;

import weblogic.xml.babel.baseparser.Element;

public abstract class Declaration extends Element {
   public static final int ELEMENTDECL = 10;
   public static final int ATTRIBUTELISTDECL = 11;
   public static final int NOTATIONDECL = 12;
   public static final int EXTERNALID = 13;
   public static final int PEDECL = 14;
   public static final int GEDECL = 15;
   static final String[] typeArray = new String[]{"ELEMENTDECL", "ATTRIBUTELISTDECL", "NOTATIONDECL", "EXTERNALID", "PEDECL", "GEDECL"};

   public static final String getType(int name) {
      return typeArray[name - 10];
   }

   public boolean isElementDecl() {
      return 10 == this.type;
   }

   public boolean isAttributeListDecl() {
      return 11 == this.type;
   }

   public boolean isNotationDecl() {
      return 12 == this.type;
   }

   public boolean isExternalId() {
      return 13 == this.type;
   }

   public boolean isPEDecl() {
      return 14 == this.type;
   }

   public boolean isGEDecl() {
      return 15 == this.type;
   }
}
