package com.octetstring.vde.schema;

import com.octetstring.nls.Messages;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.ServerConfig;
import java.io.File;
import java.io.FileInputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class InitSchema {
   public void init() {
      SAXParser parser = null;

      try {
         SAXParserFactory spf = FactoryInstanceHelper.getSAXParserInstance();
         spf.setNamespaceAware(true);
         parser = spf.newSAXParser();
         SchemaXMLHandler handler = new SchemaXMLHandler();
         String schemafname = (String)ServerConfig.getInstance().get("vde.schema.std");
         String ihome = System.getProperty(Messages.getString("vde.home_1"));
         String fullname = null;
         File file = new File(schemafname);
         if (!file.isAbsolute() && ihome != null) {
            fullname = ihome + Messages.getString("/_2") + schemafname;
         } else {
            fullname = schemafname;
         }

         SchemaChecker.getInstance().setSchemaFilename(fullname);
         FileInputStream fis = new FileInputStream(fullname);
         parser.parse(fis, handler, fullname);
      } catch (Exception var9) {
         Logger.getInstance().printStackTrace(var9);
         Logger.getInstance().log(0, this, Messages.getString("Failed_to_Parse_Schema___3") + var9.getMessage());
      }

   }
}
