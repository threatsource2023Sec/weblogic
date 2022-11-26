package com.bea.xbean.tool;

import com.bea.xbean.validator.ValidatingXMLStreamReader;
import com.bea.xml.SchemaType;
import com.bea.xml.SchemaTypeLoader;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlError;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.xml.stream.Location;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class StreamInstanceValidator {
   private static final XMLInputFactory XML_INPUT_FACTORY = XMLInputFactory.newInstance();

   public static void printUsage() {
      System.out.println("Validates the specified instance against the specified schema.");
      System.out.println("A streaming validation useful for validating very large instance ");
      System.out.println("documents with less memory. Contrast with the validate tool.");
      System.out.println("Usage: svalidate [-dl] [-nopvr] [-noupa] [-license] schema.xsd instance.xml");
      System.out.println("Options:");
      System.out.println("    -dl - permit network downloads for imports and includes (default is off)");
      System.out.println("    -noupa - do not enforce the unique particle attribution rule");
      System.out.println("    -nopvr - do not enforce the particle valid (restriction) rule");
      System.out.println("    -license - prints license information");
   }

   public static void main(String[] args) {
      Set flags = new HashSet();
      flags.add("h");
      flags.add("help");
      flags.add("usage");
      flags.add("license");
      flags.add("version");
      flags.add("dl");
      flags.add("noupr");
      flags.add("noupa");
      CommandLine cl = new CommandLine(args, flags, Collections.EMPTY_SET);
      if (cl.getOpt("h") == null && cl.getOpt("help") == null && cl.getOpt("usage") == null && args.length >= 1) {
         String[] badopts = cl.getBadOpts();
         if (badopts.length > 0) {
            for(int i = 0; i < badopts.length; ++i) {
               System.out.println("Unrecognized option: " + badopts[i]);
            }

            printUsage();
            System.exit(0);
         } else if (cl.getOpt("license") != null) {
            CommandLine.printLicense();
            System.exit(0);
         } else if (cl.getOpt("version") != null) {
            CommandLine.printVersion();
            System.exit(0);
         } else if (cl.args().length == 0) {
            printUsage();
         } else {
            boolean dl = cl.getOpt("dl") != null;
            boolean nopvr = cl.getOpt("nopvr") != null;
            boolean noupa = cl.getOpt("noupa") != null;
            File[] schemaFiles = cl.filesEndingWith(".xsd");
            File[] instanceFiles = cl.filesEndingWith(".xml");
            File[] jarFiles = cl.filesEndingWith(".jar");
            List sdocs = new ArrayList();
            XmlOptions options = (new XmlOptions()).setLoadLineNumbers();

            for(int i = 0; i < schemaFiles.length; ++i) {
               try {
                  sdocs.add(XmlObject.Factory.parse(schemaFiles[i], options.setLoadMessageDigest()));
               } catch (Exception var18) {
                  System.err.println(schemaFiles[i] + " not loadable: " + var18);
               }
            }

            XmlObject[] schemas = (XmlObject[])((XmlObject[])sdocs.toArray(new XmlObject[0]));
            SchemaTypeLoader sLoader = null;
            Collection compErrors = new ArrayList();
            XmlOptions schemaOptions = new XmlOptions();
            schemaOptions.setErrorListener(compErrors);
            if (dl) {
               schemaOptions.setCompileDownloadUrls();
            }

            if (nopvr) {
               schemaOptions.setCompileNoPvrRule();
            }

            if (noupa) {
               schemaOptions.setCompileNoUpaRule();
            }

            if (jarFiles != null && jarFiles.length > 0) {
               sLoader = XmlBeans.typeLoaderForResource(XmlBeans.resourceLoaderForPath(jarFiles));
            }

            try {
               if (schemas != null && schemas.length > 0) {
                  sLoader = XmlBeans.compileXsd(schemas, (SchemaTypeLoader)sLoader, schemaOptions);
               }
            } catch (Exception var19) {
               if (compErrors.isEmpty() || !(var19 instanceof XmlException)) {
                  var19.printStackTrace(System.err);
               }

               System.out.println("Schema invalid");
               Iterator i = compErrors.iterator();

               while(i.hasNext()) {
                  System.out.println(i.next());
               }

               return;
            }

            validateFiles(instanceFiles, (SchemaTypeLoader)sLoader, options);
         }
      } else {
         printUsage();
         System.exit(0);
      }
   }

   public static void validateFiles(File[] instanceFiles, SchemaTypeLoader sLoader, XmlOptions options) {
      ValidatingXMLStreamReader vsr = new ValidatingXMLStreamReader();
      Collection errors = new ArrayList();

      for(int i = 0; i < instanceFiles.length; ++i) {
         File file = instanceFiles[i];
         String path = file.getPath();
         long time = 0L;
         errors.clear();

         try {
            FileInputStream fis = new FileInputStream(file);
            XMLStreamReader rdr = XML_INPUT_FACTORY.createXMLStreamReader(path, fis);

            while(!rdr.isStartElement()) {
               rdr.next();
            }

            time = System.currentTimeMillis();
            vsr.init(rdr, true, (SchemaType)null, sLoader, options, errors);

            while(vsr.hasNext()) {
               vsr.next();
            }

            time = System.currentTimeMillis() - time;
            vsr.close();
            fis.close();
         } catch (XMLStreamException var13) {
            Location loc = var13.getLocation();
            XmlError e = XmlError.forLocation(var13.getMessage(), path, loc.getLineNumber(), loc.getColumnNumber(), loc.getCharacterOffset());
            errors.add(e);
         } catch (Exception var14) {
            System.err.println("error for file: " + file + ": " + var14);
            var14.printStackTrace(System.err);
            continue;
         }

         if (errors.isEmpty()) {
            System.out.println(file + " valid. (" + time + " ms)");
         } else {
            System.out.println(file + " NOT valid (" + time + " ms):");
            Iterator it = errors.iterator();

            while(it.hasNext()) {
               XmlError err = (XmlError)it.next();
               System.out.println(stringFromError(err, path));
            }
         }
      }

   }

   private static String stringFromError(XmlError err, String path) {
      String s = XmlError.severityAsString(err.getSeverity()) + ": " + path + ":" + err.getLine() + ":" + err.getColumn() + " " + err.getMessage() + " ";
      return s;
   }
}
