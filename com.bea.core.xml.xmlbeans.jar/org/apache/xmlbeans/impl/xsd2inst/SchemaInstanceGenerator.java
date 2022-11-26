package org.apache.xmlbeans.impl.xsd2inst;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SchemaTypeSystem;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.impl.tool.CommandLine;

public class SchemaInstanceGenerator {
   public static void printUsage() {
      System.out.println("Generates a document based on the given Schema file");
      System.out.println("having the given element as root.");
      System.out.println("The tool makes reasonable attempts to create a valid document,");
      System.out.println("but this is not always possible since, for example, ");
      System.out.println("there are schemas for which no valid instance document ");
      System.out.println("can be produced.");
      System.out.println("Usage: xsd2inst [flags] schema.xsd -name element_name");
      System.out.println("Flags:");
      System.out.println("    -name    the name of the root element");
      System.out.println("    -dl      enable network downloads for imports and includes");
      System.out.println("    -nopvr   disable particle valid (restriction) rule");
      System.out.println("    -noupa   disable unique particle attribution rule");
      System.out.println("    -license prints license information");
      System.out.println("    -version prints version information");
   }

   public static void main(String[] args) {
      Set flags = new HashSet();
      Set opts = new HashSet();
      flags.add("h");
      flags.add("help");
      flags.add("usage");
      flags.add("license");
      flags.add("version");
      flags.add("dl");
      flags.add("noupa");
      flags.add("nopvr");
      flags.add("partial");
      opts.add("name");
      CommandLine cl = new CommandLine(args, flags, opts);
      if (cl.getOpt("h") == null && cl.getOpt("help") == null && cl.getOpt("usage") == null) {
         String[] badOpts = cl.getBadOpts();
         if (badOpts.length > 0) {
            for(int i = 0; i < badOpts.length; ++i) {
               System.out.println("Unrecognized option: " + badOpts[i]);
            }

            printUsage();
         } else if (cl.getOpt("license") != null) {
            CommandLine.printLicense();
            System.exit(0);
         } else if (cl.getOpt("version") != null) {
            CommandLine.printVersion();
            System.exit(0);
         } else {
            boolean dl = cl.getOpt("dl") != null;
            boolean nopvr = cl.getOpt("nopvr") != null;
            boolean noupa = cl.getOpt("noupa") != null;
            File[] schemaFiles = cl.filesEndingWith(".xsd");
            String rootName = cl.getOpt("name");
            if (rootName == null) {
               System.out.println("Required option \"-name\" must be present");
            } else {
               List sdocs = new ArrayList();

               for(int i = 0; i < schemaFiles.length; ++i) {
                  try {
                     sdocs.add(XmlObject.Factory.parse(schemaFiles[i], (new XmlOptions()).setLoadLineNumbers().setLoadMessageDigest()));
                  } catch (Exception var14) {
                     System.err.println("Can not load schema file: " + schemaFiles[i] + ": ");
                     var14.printStackTrace();
                  }
               }

               XmlObject[] schemas = (XmlObject[])((XmlObject[])sdocs.toArray(new XmlObject[sdocs.size()]));
               Xsd2InstOptions options = new Xsd2InstOptions();
               options.setNetworkDownloads(dl);
               options.setNopvr(nopvr);
               options.setNoupa(noupa);
               String result = xsd2inst(schemas, rootName, options);
               System.out.println(result);
            }
         }
      } else {
         printUsage();
      }
   }

   public static String xsd2inst(String[] xsds, String rootName, Xsd2InstOptions options) throws XmlException, IOException {
      Reader[] instReaders = new Reader[xsds.length];

      for(int i = 0; i < xsds.length; ++i) {
         instReaders[i] = new StringReader(xsds[i]);
      }

      String res = xsd2inst(instReaders, rootName, options);
      return res;
   }

   public static String xsd2inst(Reader[] schemaReaders, String rootName, Xsd2InstOptions options) {
      List sdocs = new ArrayList();

      for(int i = 0; i < schemaReaders.length; ++i) {
         try {
            sdocs.add(XmlObject.Factory.parse(schemaReaders[i], (new XmlOptions()).setLoadLineNumbers().setLoadMessageDigest()));
         } catch (Exception var6) {
            System.err.println("Can not load schema reader: " + i + "  " + schemaReaders[i] + ": ");
            var6.printStackTrace();
         }
      }

      XmlObject[] schemas = (XmlObject[])((XmlObject[])sdocs.toArray(new XmlObject[sdocs.size()]));
      return xsd2inst(schemas, rootName, options);
   }

   public static String xsd2inst(XmlObject[] schemas, String rootName, Xsd2InstOptions options) {
      SchemaTypeSystem sts = null;
      if (schemas.length > 0) {
         Collection errors = new ArrayList();
         XmlOptions compileOptions = new XmlOptions();
         if (options.isNetworkDownloads()) {
            compileOptions.setCompileDownloadUrls();
         }

         if (options.isNopvr()) {
            compileOptions.setCompileNoPvrRule();
         }

         if (options.isNoupa()) {
            compileOptions.setCompileNoUpaRule();
         }

         try {
            sts = XmlBeans.compileXsd(schemas, XmlBeans.getBuiltinTypeSystem(), compileOptions);
         } catch (Exception var8) {
            if (errors.isEmpty() || !(var8 instanceof XmlException)) {
               var8.printStackTrace();
            }

            System.out.println("Schema compilation errors: ");
            Iterator i = errors.iterator();

            while(i.hasNext()) {
               System.out.println(i.next());
            }
         }
      }

      if (sts == null) {
         throw new RuntimeException("No Schemas to process.");
      } else {
         SchemaType[] globalElems = sts.documentTypes();
         SchemaType elem = null;

         for(int i = 0; i < globalElems.length; ++i) {
            if (rootName.equals(globalElems[i].getDocumentElementName().getLocalPart())) {
               elem = globalElems[i];
               break;
            }
         }

         if (elem == null) {
            throw new RuntimeException("Could not find a global element with name \"" + rootName + "\"");
         } else {
            String result = SampleXmlUtil.createSampleForType(elem);
            return result;
         }
      }
   }

   public static class Xsd2InstOptions {
      private boolean _downloads = false;
      private boolean _nopvr = false;
      private boolean _noupa = false;

      public boolean isNetworkDownloads() {
         return this._downloads;
      }

      public void setNetworkDownloads(boolean downloads) {
         this._downloads = downloads;
      }

      public boolean isNopvr() {
         return this._nopvr;
      }

      public void setNopvr(boolean nopvr) {
         this._nopvr = nopvr;
      }

      public boolean isNoupa() {
         return this._noupa;
      }

      public void setNoupa(boolean noupa) {
         this._noupa = noupa;
      }
   }
}
