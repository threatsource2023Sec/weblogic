package weblogic.xml.babel.dtd;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.xml.sax.SAXException;
import weblogic.xml.babel.baseparser.Attribute;
import weblogic.xml.babel.baseparser.BaseParser;
import weblogic.xml.babel.baseparser.Element;
import weblogic.xml.babel.baseparser.ParseException;
import weblogic.xml.babel.baseparser.Space;
import weblogic.xml.babel.baseparser.StartElement;
import weblogic.xml.babel.baseparser.ValidatingBaseParser;
import weblogic.xml.babel.scanner.ScannerException;

public class DocumentTypeDefinition extends Element {
   private ElementDeclaration elementDecl = new ElementDeclaration();
   private ParsedEntityDeclaration peDecl = new ParsedEntityDeclaration();
   private GeneralEntityDeclaration geDecl = new GeneralEntityDeclaration();
   private AttributeListDeclaration attListDecl = new AttributeListDeclaration();
   private NotationDeclaration notationDecl = new NotationDeclaration();
   private Name name = new Name();
   private Space space = new Space();
   private ExternalID externalID = new ExternalID();
   private EntityTable parameterEntityTable = new EntityTable();
   private EntityTable internalEntityTable = new EntityTable();
   private EntityTable externalEntityTable = new EntityTable();
   private Map elementDeclarations = new HashMap();

   public void init() {
      super.init();
   }

   public boolean isMarkupDeclStarter(int tokenType) {
      switch (tokenType) {
         case 38:
         case 40:
         case 52:
         case 53:
         case 55:
            return true;
         default:
            return false;
      }
   }

   public Declaration parseMarkupDecl(BaseParser parser) throws IOException, ScannerException, ParseException {
      Object decl;
      switch (parser.getCurrentToken().tokenType) {
         case 38:
            decl = new ElementDeclaration();
            break;
         case 40:
            decl = new AttributeListDeclaration();
            break;
         case 52:
            decl = new GeneralEntityDeclaration();
            break;
         case 53:
            decl = new ParsedEntityDeclaration();
            break;
         case 55:
            decl = new NotationDeclaration();
            break;
         default:
            return null;
      }

      ((Declaration)decl).parse(parser);
      this.space.parse(parser);
      return (Declaration)decl;
   }

   public void parse(BaseParser parser) throws IOException, ScannerException, ParseException {
      this.init();
      this.space.parse(parser);
      parser.accept(24);
      this.space.parse(parser);
      this.name.parse(parser);
      this.space.parse(parser);
      if (ExternalID.checkStarters(parser.getCurrentToken().tokenType)) {
         this.externalID.parse(parser);
         ValidatingBaseParser newParser = new ValidatingBaseParser(parser);
         newParser.setExternalDTD();

         try {
            newParser.initDTD(parser.resolveEntity(this.externalID.getPubidLiteral(), this.externalID.getSystemLiteral()));
         } catch (SAXException var4) {
            throw new ParseException("Error resolving externalID " + this.externalID, parser.getLine(), parser.getCurrentToken());
         }

         this.parseDeclarations(newParser);
      }

      this.parseDeclarations(parser);
      parser.accept(58);
   }

   public void parseDeclarations(BaseParser parser) throws IOException, ScannerException, ParseException {
      this.space.parse(parser);

      while(true) {
         while(this.isMarkupDeclStarter(parser.getCurrentToken().tokenType) || parser.getCurrentToken().tokenType == 7 || parser.getCurrentToken().tokenType == 19) {
            if (parser.getCurrentToken().tokenType != 7 && parser.getCurrentToken().tokenType != 19) {
               Declaration decl = this.parseMarkupDecl(parser);
               if (decl.isElementDecl()) {
                  this.elementDeclarations.put(decl.getRawName(), decl);
               } else if (decl.isAttributeListDecl()) {
                  ElementDeclaration element = (ElementDeclaration)this.elementDeclarations.get(decl.getRawName());
                  if (element == null) {
                     element = new ElementDeclaration(decl.getRawName());
                     element.addAttributeList((AttributeListDeclaration)decl);
                     this.elementDeclarations.put(element.getRawName(), element);
                  }
               }
            } else {
               parser.accept();
            }
         }

         return;
      }
   }

   public void processAttributes(StartElement startElement) {
      this.addDefaultAttributes(startElement);
      this.setAttributeTypes(startElement);
   }

   public void setAttributeTypes(StartElement startElement) {
      ElementDeclaration element = (ElementDeclaration)this.elementDeclarations.get(startElement.getRawName());
      if (element != null) {
         Iterator attList = startElement.getAttributes().iterator();

         while(attList.hasNext()) {
            Attribute att = (Attribute)attList.next();
            AttributeDefinition def = element.getAttributeDefinition(att.getRawName());
            if (def != null) {
               att.setType(def.getType());
               if (def.getType() != 11) {
                  att.setValue(att.normalizeSpace(att.getValue()));
               }
            }
         }

      }
   }

   public void addDefaultAttributes(StartElement startElement) {
      ElementDeclaration element = (ElementDeclaration)this.elementDeclarations.get(startElement.getRawName());
      if (element != null) {
         if (element.hasDefaultAttributeDeclarations()) {
            Iterator attList = startElement.getAttributes().iterator();
            AttributeDefinition att;
            Iterator i;
            if (attList.hasNext()) {
               Set nameSet = new HashSet();

               while(attList.hasNext()) {
                  Attribute att = (Attribute)attList.next();
                  nameSet.add(att.getRawName());
               }

               i = element.getDefaultAttributes().iterator();

               while(i.hasNext()) {
                  att = (AttributeDefinition)i.next();
                  if (!nameSet.contains(att.getRawName())) {
                     startElement.addAttribute(att.createDefaultAttribute());
                  }
               }
            } else {
               i = element.getDefaultAttributes().iterator();

               while(i.hasNext()) {
                  att = (AttributeDefinition)i.next();
                  startElement.addAttribute(att.createDefaultAttribute());
               }
            }

         }
      }
   }

   public EntityTable getParameterEntityTable() {
      return this.parameterEntityTable;
   }

   public EntityTable getInternalEntityTable() {
      return this.internalEntityTable;
   }

   public EntityTable getExternalEntityTable() {
      return this.externalEntityTable;
   }

   public void setTables(EntityTable petable, EntityTable eetable, EntityTable ietable) {
      this.parameterEntityTable = petable;
      this.internalEntityTable = ietable;
      this.externalEntityTable = eetable;
   }
}
