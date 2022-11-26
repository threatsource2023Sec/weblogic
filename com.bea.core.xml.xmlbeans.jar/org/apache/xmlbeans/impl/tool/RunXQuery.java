package org.apache.xmlbeans.impl.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

public class RunXQuery {
   public static void printUsage() {
      System.out.println("Run an XQuery against an XML instance");
      System.out.println("Usage:");
      System.out.println("xquery [-verbose] [-pretty] [-q <query> | -qf query.xq] [file.xml]*");
      System.out.println(" -q <query> to specify a query on the command-line");
      System.out.println(" -qf <query> to specify a file containing a query");
      System.out.println(" -pretty pretty-prints the results");
      System.out.println(" -license prints license information");
      System.out.println(" the query is run on each XML file specified");
      System.out.println("");
   }

   public static void main(String[] args) throws Exception {
      Set flags = new HashSet();
      flags.add("h");
      flags.add("help");
      flags.add("usage");
      flags.add("license");
      flags.add("version");
      flags.add("verbose");
      flags.add("pretty");
      CommandLine cl = new CommandLine(args, flags, Arrays.asList("q", "qf"));
      if (cl.getOpt("h") == null && cl.getOpt("help") == null && cl.getOpt("usage") == null) {
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
         } else {
            args = cl.args();
            if (args.length == 0) {
               printUsage();
               System.exit(0);
            } else {
               boolean verbose = cl.getOpt("verbose") != null;
               boolean pretty = cl.getOpt("pretty") != null;
               String query = cl.getOpt("q");
               String queryfile = cl.getOpt("qf");
               if (query == null && queryfile == null) {
                  System.err.println("No query specified");
                  System.exit(0);
               } else if (query != null && queryfile != null) {
                  System.err.println("Specify -qf or -q, not both.");
                  System.exit(0);
               } else {
                  StringBuffer result;
                  int ch;
                  try {
                     if (queryfile != null) {
                        File queryFile = new File(queryfile);
                        FileInputStream is = new FileInputStream(queryFile);
                        InputStreamReader r = new InputStreamReader(is);
                        result = new StringBuffer();

                        while(true) {
                           ch = r.read();
                           if (ch < 0) {
                              r.close();
                              is.close();
                              query = result.toString();
                              break;
                           }

                           result.append((char)ch);
                        }
                     }
                  } catch (Throwable var17) {
                     System.err.println("Cannot read query file: " + var17.getMessage());
                     System.exit(1);
                     return;
                  }

                  if (verbose) {
                     System.out.println("Compile Query:");
                     System.out.println(query);
                     System.out.println();
                  }

                  try {
                     query = XmlBeans.compileQuery(query);
                  } catch (Exception var15) {
                     System.err.println("Error compiling query: " + var15.getMessage());
                     System.exit(1);
                     return;
                  }

                  File[] files = cl.getFiles();

                  for(int i = 0; i < files.length; ++i) {
                     XmlObject x;
                     try {
                        if (verbose) {
                           InputStream is = new FileInputStream(files[i]);

                           while(true) {
                              ch = is.read();
                              if (ch < 0) {
                                 is.close();
                                 System.out.println();
                                 break;
                              }

                              System.out.write(ch);
                           }
                        }

                        x = XmlObject.Factory.parse(files[i]);
                     } catch (Throwable var16) {
                        System.err.println("Error parsing instance: " + var16.getMessage());
                        System.exit(1);
                        return;
                     }

                     if (verbose) {
                        System.out.println("Executing Query...");
                        System.err.println();
                     }

                     result = null;

                     XmlObject[] result;
                     try {
                        result = x.execQuery(query);
                     } catch (Throwable var14) {
                        System.err.println("Error executing query: " + var14.getMessage());
                        System.exit(1);
                        return;
                     }

                     if (verbose) {
                        System.out.println("Query Result:");
                     }

                     XmlOptions opts = new XmlOptions();
                     opts.setSaveOuter();
                     if (pretty) {
                        opts.setSavePrettyPrint();
                     }

                     for(int j = 0; j < result.length; ++j) {
                        result[j].save(System.out, opts);
                        System.out.println();
                     }
                  }

               }
            }
         }
      } else {
         printUsage();
         System.exit(0);
      }
   }
}
