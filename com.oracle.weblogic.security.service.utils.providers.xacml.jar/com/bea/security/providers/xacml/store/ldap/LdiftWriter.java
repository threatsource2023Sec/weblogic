package com.bea.security.providers.xacml.store.ldap;

import com.bea.common.security.ProvidersLogger;
import com.bea.common.security.xacml.URISyntaxException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class LdiftWriter {
   private static final String LDIFT_OP = "-l";
   private static final String POLICY_OP = "-x";
   private static final String STAN_OP = "-s";
   private static final String HELP_OP = "-h";
   private static String entlFile = "";
   private static String xacmlFile = "";
   private static boolean isStandard = false;
   private static boolean isGenLdift = false;
   private static boolean isGenXml = false;

   private LdiftWriter() {
   }

   public static void main(String[] args) {
      if (args == null || args.length == 0) {
         printUsage(System.err);
         System.exit(1);
      }

      if (Arrays.asList(args).contains("-h")) {
         printUsage(System.out);
         System.exit(0);
      }

      try {
         getCmdlineParams(args);
         if (isGenXml) {
            writeXACMLPolicy(entlFile, xacmlFile, isStandard);
         } else {
            writeXACMLLdift(entlFile, xacmlFile, isStandard);
         }
      } catch (IllegalArgumentException var2) {
         System.err.println(var2.getMessage());
         printUsage(System.err);
         System.exit(1);
      } catch (EntlConversionException var3) {
         System.err.println(var3.getMessage());
         System.exit(1);
      }

   }

   private static void getCmdlineParams(String[] args) throws IllegalArgumentException {
      if (args.length >= 2 && args.length <= 4) {
         int len = args.length;
         xacmlFile = args[len - 1];
         entlFile = args[len - 2];

         for(int index = len - 3; index >= 0; --index) {
            if (args[index].equalsIgnoreCase("-x")) {
               if (isGenLdift) {
                  throw new IllegalArgumentException(ProvidersLogger.getIllegalCmdline());
               }

               isGenXml = true;
            } else if (args[index].equalsIgnoreCase("-l")) {
               if (isGenXml) {
                  throw new IllegalArgumentException(ProvidersLogger.getIllegalCmdline());
               }

               isGenLdift = true;
            } else {
               if (!args[index].equalsIgnoreCase("-s")) {
                  throw new IllegalArgumentException(ProvidersLogger.getIllegalCmdline());
               }

               isStandard = true;
            }
         }

      } else {
         throw new IllegalArgumentException(ProvidersLogger.getIllegalCmdline());
      }
   }

   public static void writeXACMLLdift(String entlFile, String xacmlFile, boolean isStandard) throws EntlConversionException {
      InputStream entlXml = null;
      OutputStream xacmlOutput = null;

      try {
         entlXml = new FileInputStream(entlFile);
         xacmlOutput = new FileOutputStream(xacmlFile);
         writeXACMLLdift((InputStream)entlXml, (OutputStream)xacmlOutput, isStandard);
      } catch (FileNotFoundException var13) {
         throw new EntlConversionException(var13);
      } finally {
         try {
            if (entlXml != null) {
               entlXml.close();
            }

            if (xacmlOutput != null) {
               xacmlOutput.close();
            }
         } catch (IOException var12) {
         }

      }

   }

   public static void writeXACMLLdift(InputStream entlXml, OutputStream xacmlOutput, boolean isStandard) throws EntlConversionException {
      try {
         XMLProcessor reader = new XMLProcessor(entlXml);
         List policies = reader.getConvertedPolicies();
         ConvertedEntlWriter writer = WriterFactory.create(true);
         writer.writeConvertedEntlPolicies(policies, xacmlOutput, isStandard);
      } catch (URISyntaxException var6) {
         throw new EntlConversionException(var6);
      } catch (SAXException var7) {
         throw new EntlConversionException(var7);
      } catch (ParserConfigurationException var8) {
         throw new EntlConversionException(var8);
      } catch (IOException var9) {
         throw new EntlConversionException(var9);
      }
   }

   private static void printUsage(PrintStream out) {
      out.println("NAME");
      out.println("  policygen - command line tool of converting entitlement file to XACML LDIFT file or XACML policy file");
      out.println("");
      out.println("SYNOPSIS");
      out.println("  print usage: policygen [-h]");
      out.println("  convert entitlement file: policygen [-s] [-l|-x] entlInputFile xacmlOutputFile");
      out.println("");
      out.println("DESCRIPTION");
      out.println("  -h: Tell the usage.");
      out.println("  -s: If it is specified, standard XACML policy will be generated in XACML LDIFT file or in XACML xml file, otherwise, entitlement XACML policy will be generated.");
      out.println("  -l: specifies that a XACML LDIFT file should be generated.");
      out.println("  -x: specifies that a XACML policy file should be generated.");
      out.println("  If no -l or -s specified, an XACML LDIFT file will be generated.");
      out.println("  entlInputFile: Specify the location of input entitlement xml file.");
      out.println("  xacmlOutputFile: Specify the location of output XACML file.");
   }

   public static void writeXACMLPolicy(String entlFile, String xacmlFile, boolean isStandard) throws EntlConversionException {
      InputStream entlXml = null;
      OutputStream xacmlOutput = null;

      try {
         entlXml = new FileInputStream(entlFile);
         xacmlOutput = new FileOutputStream(xacmlFile);
         writeXACMLPolicy((InputStream)entlXml, (OutputStream)xacmlOutput, isStandard);
      } catch (FileNotFoundException var13) {
         throw new EntlConversionException(var13);
      } finally {
         try {
            if (entlXml != null) {
               entlXml.close();
            }

            if (xacmlOutput != null) {
               xacmlOutput.close();
            }
         } catch (IOException var12) {
         }

      }

   }

   public static void writeXACMLPolicy(InputStream entlXml, OutputStream xacmlOutput, boolean isStandard) throws EntlConversionException {
      try {
         XMLProcessor reader = new XMLProcessor(entlXml);
         List policies = reader.getConvertedPolicies();
         ConvertedEntlWriter writer = WriterFactory.create(false);
         writer.writeConvertedEntlPolicies(policies, xacmlOutput, isStandard);
      } catch (URISyntaxException var6) {
         throw new EntlConversionException(var6);
      } catch (SAXException var7) {
         throw new EntlConversionException(var7);
      } catch (ParserConfigurationException var8) {
         throw new EntlConversionException(var8);
      } catch (IOException var9) {
         throw new EntlConversionException(var9);
      }
   }
}
