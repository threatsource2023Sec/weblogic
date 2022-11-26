package weblogic.i18n.tools;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Vector;

public final class MessageCatalogWriter {
   private static final boolean debug = false;

   public static void writeFormattedCatalog(String path, MessageCatalog catalog) throws IOException {
      writeCatalog(path, catalog, true);
   }

   public static void writeCatalog(String path, MessageCatalog catalog) throws IOException {
      writeCatalog(path, catalog, false);
   }

   private static void writeCatalog(String catalogPath, MessageCatalog catalog, boolean formatted) throws IOException {
      PrintWriter printWriter = null;

      try {
         printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(catalogPath), catalog.getOutputEncoding())));
         printWriter.print("<?xml version=\"1.0\" encoding=\"" + catalog.getOutputEncoding() + "\"?>\n");
         printWriter.print("<!DOCTYPE message_catalog PUBLIC \"weblogic-message-catalog-dtd\"  ");
         printWriter.print("\"http://www.bea.com/servers/wls810/dtd/msgcat.dtd\">\n");
         printWriter.print("<message_catalog\n");
         String[] attrNames = catalog.getAttributeNames();
         String[] attrs = catalog.getAttributes();
         if (attrNames.length != attrs.length) {
            throw new IOException("Fatal inconsistency in MessageCatalog attribute definitions.");
         }

         for(int i = 0; i < attrNames.length; ++i) {
            if (isInDtd(attrNames[i]) && attrs[i] != null) {
               printWriter.print("   " + attrNames[i] + "=\"" + attrs[i] + "\"\n");
            }
         }

         printWriter.print("   >\n");
         String comment = catalog.getComment();
         if (comment != null) {
            printWriter.print("<!--  " + comment + "-->\n");
         }

         writeMessagesToXML(catalog.getLogMessages(), printWriter, formatted);
         writeMessagesToXML(catalog.getMessages(), printWriter, formatted);
         writeMessagesToXML(catalog.getExceptions(), printWriter, formatted);
         printWriter.print("</message_catalog>\n");
      } finally {
         if (printWriter != null) {
            printWriter.flush();
            printWriter.close();
         }

      }

   }

   private static void writeMessagesToXML(Vector mssgs, PrintWriter printWriter, boolean formatted) {
      if (mssgs != null && mssgs.size() > 0) {
         Enumeration msgs = mssgs.elements();

         while(msgs.hasMoreElements()) {
            printWriter.print("\n");
            printWriter.print(((BasicMessage)msgs.nextElement()).toXML(formatted));
         }
      }

   }

   private static boolean isInDtd(String attrName) {
      return !attrName.equals("filename");
   }
}
