package weblogic.diagnostics.debug;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Tag;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.StringTokenizer;

public class DebugCategoryParser {
   private static final String TAG_NAME = "oldDebugCategory";
   private static final String OUTPUT_FILE_OPTION = "-outputFile";
   private static final String BOOLEAN_TYPE_NAME = "boolean";
   private static boolean verbose = false;
   private static Properties props = new Properties();

   public static boolean start(RootDoc rootDoc) throws IOException {
      ClassDoc[] classDocs = rootDoc.classes();
      if (classDocs == null) {
         return false;
      } else {
         String[][] options = rootDoc.options();
         String filePath = null;
         int i;
         if (options != null) {
            for(i = 0; i < options.length; ++i) {
               if (options[i][0].equals("-outputFile")) {
                  filePath = options[i][1];
               } else if (options[i][0].equals("-verbose")) {
                  verbose = true;
               }
            }
         }

         rootDoc.printNotice("Started building the DebugCategory mapping...");

         for(i = 0; i < classDocs.length; ++i) {
            if (!processClass(rootDoc, classDocs[i])) {
               return false;
            }
         }

         rootDoc.printNotice("The DebugCategory mapping will be written to " + filePath);
         FileOutputStream fos = new FileOutputStream(filePath);
         props.store(fos, "Map of DebugLogger to DebugCategory names");
         rootDoc.printNotice("DebugCategory mapping generated successfully");
         return true;
      }
   }

   public static int optionLength(String option) {
      if (option.equals("-outputFile")) {
         return 2;
      } else if (option.equals("-d")) {
         return 2;
      } else if (option.equals("-doctitle")) {
         return 2;
      } else {
         return option.equals("-windowtitle") ? 2 : 0;
      }
   }

   private static boolean processClass(RootDoc rootDoc, ClassDoc classDoc) {
      rootDoc.printNotice("DebugScopesParser will process class " + classDoc.qualifiedTypeName());
      MethodDoc[] methodDocs = classDoc.methods();
      if (methodDocs == null) {
         return false;
      } else {
         for(int i = 0; i < methodDocs.length; ++i) {
            MethodDoc methodDoc = methodDocs[i];
            String methodName = methodDoc.name();
            if (verbose) {
               rootDoc.printNotice("Processing method " + methodName);
            }

            String attributeName;
            String attributeType;
            if (methodName.startsWith("get")) {
               attributeName = methodName.substring(3);
               attributeType = methodDoc.returnType().typeName();
            } else if (methodName.startsWith("is")) {
               attributeName = methodName.substring(2);
               attributeType = methodDoc.returnType().typeName();
            } else {
               if (!methodName.startsWith("set")) {
                  if (verbose) {
                     rootDoc.printNotice("The method " + methodName + " does not define an MBean attribute");
                  }
                  continue;
               }

               attributeName = methodName.substring(3);
               Parameter[] params = methodDoc.parameters();
               if (params == null || params.length == 0) {
                  continue;
               }

               Parameter param = params[0];
               if (param == null) {
                  continue;
               }

               attributeType = param.typeName();
            }

            if (attributeType.equals("boolean")) {
               if (verbose) {
                  rootDoc.printNotice("Processing attribute name " + attributeName + " of type " + attributeType);
               }

               Tag[] tags = methodDoc.tags("oldDebugCategory");
               if (tags != null && tags.length != 0) {
                  for(int j = 0; j < tags.length; ++j) {
                     Tag tag = tags[j];
                     StringTokenizer tk = new StringTokenizer(tag.text().trim());
                     if (tk.hasMoreTokens()) {
                        String category = tk.nextToken();
                        String previousCategory = props.getProperty(attributeName);
                        if (previousCategory == null) {
                           props.put(attributeName, category);
                        } else {
                           props.put(attributeName, previousCategory + "," + category);
                        }

                        if (verbose) {
                           rootDoc.printNotice("Mapping attribute " + attributeName + " to the Category " + category);
                        }
                     }
                  }
               }
            }
         }

         return true;
      }
   }
}
