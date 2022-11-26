package org.apache.xmlbeans.impl.tool;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.xml.namespace.QName;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.impl.xb.xsdschema.FormChoice;
import org.apache.xmlbeans.impl.xb.xsdschema.IncludeDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.NamedAttributeGroup;
import org.apache.xmlbeans.impl.xb.xsdschema.NamedGroup;
import org.apache.xmlbeans.impl.xb.xsdschema.SchemaDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.TopLevelAttribute;
import org.apache.xmlbeans.impl.xb.xsdschema.TopLevelComplexType;
import org.apache.xmlbeans.impl.xb.xsdschema.TopLevelElement;
import org.apache.xmlbeans.impl.xb.xsdschema.TopLevelSimpleType;

public class FactorImports {
   public static void printUsage() {
      System.out.println("Refactors a directory of XSD files to remove name conflicts.");
      System.out.println("Usage: sfactor [-import common.xsd] [-out outputdir] inputdir");
      System.out.println("    -import common.xsd - The XSD file to contain redundant ");
      System.out.println("                         definitions for importing.");
      System.out.println("    -out outputdir - The directory into which to place XSD ");
      System.out.println("                     files resulting from refactoring, ");
      System.out.println("                     plus a commonly imported common.xsd.");
      System.out.println("    inputdir - The directory containing the XSD files with");
      System.out.println("               redundant definitions.");
      System.out.println("    -license - Print license information.");
      System.out.println();
   }

   public static void main(String[] args) throws Exception {
      Set flags = new HashSet();
      flags.add("h");
      flags.add("help");
      flags.add("usage");
      flags.add("license");
      flags.add("version");
      CommandLine cl = new CommandLine(args, flags, Arrays.asList("import", "out"));
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
         } else {
            args = cl.args();
            if (args.length != 1) {
               System.exit(0);
            } else {
               String commonName = cl.getOpt("import");
               if (commonName == null) {
                  commonName = "common.xsd";
               }

               String out = cl.getOpt("out");
               if (out == null) {
                  System.out.println("Using output directory 'out'");
                  out = "out";
               }

               File outdir = new File(out);
               File basedir = new File(args[0]);
               File[] files = cl.getFiles();
               Map schemaDocs = new HashMap();
               Set elementNames = new HashSet();
               Set attributeNames = new HashSet();
               Set typeNames = new HashSet();
               Set modelGroupNames = new HashSet();
               Set attrGroupNames = new HashSet();
               Set dupeElementNames = new HashSet();
               Set dupeAttributeNames = new HashSet();
               Set dupeTypeNames = new HashSet();
               Set dupeModelGroupNames = new HashSet();
               Set dupeAttrGroupNames = new HashSet();
               Set dupeNamespaces = new HashSet();

               int j;
               for(int i = 0; i < files.length; ++i) {
                  try {
                     SchemaDocument doc = SchemaDocument.Factory.parse(files[i]);
                     schemaDocs.put(doc, files[i]);
                     if (doc.getSchema().sizeOfImportArray() > 0 || doc.getSchema().sizeOfIncludeArray() > 0) {
                        System.out.println("warning: " + files[i] + " contains imports or includes that are being ignored.");
                     }

                     String targetNamespace = doc.getSchema().getTargetNamespace();
                     if (targetNamespace == null) {
                        targetNamespace = "";
                     }

                     TopLevelComplexType[] ct = doc.getSchema().getComplexTypeArray();

                     for(int j = 0; j < ct.length; ++j) {
                        noteName(ct[j].getName(), targetNamespace, typeNames, dupeTypeNames, dupeNamespaces);
                     }

                     TopLevelSimpleType[] st = doc.getSchema().getSimpleTypeArray();

                     for(int j = 0; j < st.length; ++j) {
                        noteName(st[j].getName(), targetNamespace, typeNames, dupeTypeNames, dupeNamespaces);
                     }

                     TopLevelElement[] el = doc.getSchema().getElementArray();

                     for(int j = 0; j < el.length; ++j) {
                        noteName(el[j].getName(), targetNamespace, elementNames, dupeElementNames, dupeNamespaces);
                     }

                     TopLevelAttribute[] at = doc.getSchema().getAttributeArray();

                     for(int j = 0; j < at.length; ++j) {
                        noteName(at[j].getName(), targetNamespace, attributeNames, dupeAttributeNames, dupeNamespaces);
                     }

                     NamedGroup[] gr = doc.getSchema().getGroupArray();

                     for(int j = 0; j < gr.length; ++j) {
                        noteName(gr[j].getName(), targetNamespace, modelGroupNames, dupeModelGroupNames, dupeNamespaces);
                     }

                     NamedAttributeGroup[] ag = doc.getSchema().getAttributeGroupArray();

                     for(j = 0; j < ag.length; ++j) {
                        noteName(ag[j].getName(), targetNamespace, attrGroupNames, dupeAttrGroupNames, dupeNamespaces);
                     }
                  } catch (XmlException var39) {
                     System.out.println("warning: " + files[i] + " is not a schema file - " + var39.getError().toString());
                  } catch (IOException var40) {
                     System.err.println("Unable to load " + files[i] + " - " + var40.getMessage());
                     System.exit(1);
                     return;
                  }
               }

               if (schemaDocs.size() == 0) {
                  System.out.println("No schema files found.");
                  System.exit(0);
               } else if (dupeTypeNames.size() + dupeElementNames.size() + dupeAttributeNames.size() + dupeModelGroupNames.size() + dupeAttrGroupNames.size() == 0) {
                  System.out.println("No duplicate names found.");
                  System.exit(0);
               } else {
                  Map commonDocs = new HashMap();
                  Map commonFiles = new HashMap();
                  int count = dupeNamespaces.size() == 1 ? 0 : 1;
                  Iterator i = dupeNamespaces.iterator();

                  while(i.hasNext()) {
                     String namespace = (String)i.next();
                     SchemaDocument commonDoc = SchemaDocument.Factory.parse("<xs:schema xmlns:xs='http://www.w3.org/2001/XMLSchema'/>");
                     if (namespace.length() > 0) {
                        commonDoc.getSchema().setTargetNamespace(namespace);
                     }

                     commonDoc.getSchema().setElementFormDefault(FormChoice.QUALIFIED);
                     commonDocs.put(namespace, commonDoc);
                     commonFiles.put(commonDoc, commonFileFor(commonName, namespace, count++, outdir));
                  }

                  i = schemaDocs.keySet().iterator();

                  SchemaDocument doc;
                  while(i.hasNext()) {
                     doc = (SchemaDocument)i.next();
                     String targetNamespace = doc.getSchema().getTargetNamespace();
                     if (targetNamespace == null) {
                        targetNamespace = "";
                     }

                     SchemaDocument commonDoc = (SchemaDocument)commonDocs.get(targetNamespace);
                     boolean needImport = false;
                     TopLevelComplexType[] ct = doc.getSchema().getComplexTypeArray();

                     for(j = ct.length - 1; j >= 0; --j) {
                        if (isDuplicate(ct[j].getName(), targetNamespace, dupeTypeNames)) {
                           if (isFirstDuplicate(ct[j].getName(), targetNamespace, typeNames, dupeTypeNames)) {
                              commonDoc.getSchema().addNewComplexType().set(ct[j]);
                           }

                           needImport = true;
                           doc.getSchema().removeComplexType(j);
                        }
                     }

                     TopLevelSimpleType[] st = doc.getSchema().getSimpleTypeArray();

                     for(int j = 0; j < st.length; ++j) {
                        if (isDuplicate(st[j].getName(), targetNamespace, dupeTypeNames)) {
                           if (isFirstDuplicate(st[j].getName(), targetNamespace, typeNames, dupeTypeNames)) {
                              commonDoc.getSchema().addNewSimpleType().set(st[j]);
                           }

                           needImport = true;
                           doc.getSchema().removeSimpleType(j);
                        }
                     }

                     TopLevelElement[] el = doc.getSchema().getElementArray();

                     for(int j = 0; j < el.length; ++j) {
                        if (isDuplicate(el[j].getName(), targetNamespace, dupeElementNames)) {
                           if (isFirstDuplicate(el[j].getName(), targetNamespace, elementNames, dupeElementNames)) {
                              commonDoc.getSchema().addNewElement().set(el[j]);
                           }

                           needImport = true;
                           doc.getSchema().removeElement(j);
                        }
                     }

                     TopLevelAttribute[] at = doc.getSchema().getAttributeArray();

                     for(int j = 0; j < at.length; ++j) {
                        if (isDuplicate(at[j].getName(), targetNamespace, dupeAttributeNames)) {
                           if (isFirstDuplicate(at[j].getName(), targetNamespace, attributeNames, dupeAttributeNames)) {
                              commonDoc.getSchema().addNewElement().set(at[j]);
                           }

                           needImport = true;
                           doc.getSchema().removeElement(j);
                        }
                     }

                     NamedGroup[] gr = doc.getSchema().getGroupArray();

                     for(int j = 0; j < gr.length; ++j) {
                        if (isDuplicate(gr[j].getName(), targetNamespace, dupeModelGroupNames)) {
                           if (isFirstDuplicate(gr[j].getName(), targetNamespace, modelGroupNames, dupeModelGroupNames)) {
                              commonDoc.getSchema().addNewElement().set(gr[j]);
                           }

                           needImport = true;
                           doc.getSchema().removeElement(j);
                        }
                     }

                     NamedAttributeGroup[] ag = doc.getSchema().getAttributeGroupArray();

                     for(int j = 0; j < ag.length; ++j) {
                        if (isDuplicate(ag[j].getName(), targetNamespace, dupeAttrGroupNames)) {
                           if (isFirstDuplicate(ag[j].getName(), targetNamespace, attrGroupNames, dupeAttrGroupNames)) {
                              commonDoc.getSchema().addNewElement().set(ag[j]);
                           }

                           needImport = true;
                           doc.getSchema().removeElement(j);
                        }
                     }

                     if (needImport) {
                        IncludeDocument.Include newInclude = doc.getSchema().addNewInclude();
                        File inputFile = (File)schemaDocs.get(doc);
                        File outputFile = outputFileFor(inputFile, basedir, outdir);
                        File commonFile = (File)commonFiles.get(commonDoc);
                        if (targetNamespace != null) {
                           newInclude.setSchemaLocation(relativeURIFor(outputFile, commonFile));
                        }
                     }
                  }

                  if (!outdir.isDirectory() && !outdir.mkdirs()) {
                     System.err.println("Unable to makedir " + outdir);
                     System.exit(1);
                  } else {
                     i = schemaDocs.keySet().iterator();

                     File outputFile;
                     while(i.hasNext()) {
                        doc = (SchemaDocument)i.next();
                        outputFile = (File)schemaDocs.get(doc);
                        File outputFile = outputFileFor(outputFile, basedir, outdir);
                        if (outputFile == null) {
                           System.out.println("Cannot copy " + outputFile);
                        } else {
                           doc.save(outputFile, (new XmlOptions()).setSavePrettyPrint().setSaveAggresiveNamespaces());
                        }
                     }

                     i = commonFiles.keySet().iterator();

                     while(i.hasNext()) {
                        doc = (SchemaDocument)i.next();
                        outputFile = (File)commonFiles.get(doc);
                        doc.save(outputFile, (new XmlOptions()).setSavePrettyPrint().setSaveAggresiveNamespaces());
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

   private static File outputFileFor(File file, File baseDir, File outdir) {
      URI base = baseDir.getAbsoluteFile().toURI();
      URI abs = file.getAbsoluteFile().toURI();
      URI rel = base.relativize(abs);
      if (rel.isAbsolute()) {
         System.out.println("Cannot relativize " + file);
         return null;
      } else {
         URI outbase = outdir.toURI();
         URI out = CodeGenUtil.resolve(outbase, rel);
         return new File(out);
      }
   }

   private static URI commonAncestor(URI first, URI second) {
      String firstStr = first.toString();
      String secondStr = second.toString();
      int len = firstStr.length();
      if (secondStr.length() < len) {
         len = secondStr.length();
      }

      int i;
      for(i = 0; i < len && firstStr.charAt(i) == secondStr.charAt(i); ++i) {
      }

      --i;
      if (i >= 0) {
         i = firstStr.lastIndexOf(47, i);
      }

      if (i < 0) {
         return null;
      } else {
         try {
            return new URI(firstStr.substring(0, i));
         } catch (URISyntaxException var7) {
            return null;
         }
      }
   }

   private static String relativeURIFor(File source, File target) {
      URI base = source.getAbsoluteFile().toURI();
      URI abs = target.getAbsoluteFile().toURI();
      URI commonBase = commonAncestor(base, abs);
      if (commonBase == null) {
         return abs.toString();
      } else {
         URI baserel = commonBase.relativize(base);
         URI targetrel = commonBase.relativize(abs);
         if (!baserel.isAbsolute() && !targetrel.isAbsolute()) {
            String prefix = "";
            String sourceRel = baserel.toString();

            for(int i = 0; i < sourceRel.length(); ++i) {
               i = sourceRel.indexOf(47, i);
               if (i < 0) {
                  break;
               }

               prefix = prefix + "../";
            }

            return prefix + targetrel.toString();
         } else {
            return abs.toString();
         }
      }
   }

   private static File commonFileFor(String commonName, String namespace, int i, File outdir) {
      String name = commonName;
      if (i > 0) {
         int index = commonName.lastIndexOf(46);
         if (index < 0) {
            index = commonName.length();
         }

         name = commonName.substring(0, index) + i + commonName.substring(index);
      }

      return new File(outdir, name);
   }

   private static void noteName(String name, String targetNamespace, Set seen, Set dupes, Set dupeNamespaces) {
      if (name != null) {
         QName qName = new QName(targetNamespace, name);
         if (seen.contains(qName)) {
            dupes.add(qName);
            dupeNamespaces.add(targetNamespace);
         } else {
            seen.add(qName);
         }

      }
   }

   private static boolean isFirstDuplicate(String name, String targetNamespace, Set notseen, Set dupes) {
      if (name == null) {
         return false;
      } else {
         QName qName = new QName(targetNamespace, name);
         if (dupes.contains(qName) && notseen.contains(qName)) {
            notseen.remove(qName);
            return true;
         } else {
            return false;
         }
      }
   }

   private static boolean isDuplicate(String name, String targetNamespace, Set dupes) {
      if (name == null) {
         return false;
      } else {
         QName qName = new QName(targetNamespace, name);
         return dupes.contains(qName);
      }
   }
}
