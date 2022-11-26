package weblogic.diagnostics.debug;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Tag;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class DebugScopeParser {
   private static final String DEBUG_SCOPE_TAG_NAME = "debugScope";
   private static final String EXCLUDE_DEBUG_TAG = "excludeFromDebugScope";
   private static final String BOOLEAN_TYPE_NAME = "boolean";
   private static boolean verbose = false;
   private static HashSet excludedAttrs = new HashSet();

   public static boolean start(RootDoc rootDoc) throws InvalidDebugScopeException, IOException {
      ClassDoc[] classDocs = rootDoc.classes();
      if (classDocs == null) {
         return false;
      } else {
         String[][] options = rootDoc.options();
         String filePath = null;
         if (options != null) {
            for(int i = 0; i < options.length; ++i) {
               if (options[i][0].equals("-debugScopeOutput")) {
                  filePath = options[i][1];
               } else if (options[i][0].equals("-verbose")) {
                  verbose = true;
               }
            }
         }

         rootDoc.printNotice("Started building the DebugScope tree...");
         DebugScopeTree.setVerbose(verbose);
         DebugScopeTree tree = new DebugScopeTree();
         Set processedAttributes = new HashSet();

         for(int i = 0; i < classDocs.length; ++i) {
            if (!processClass(rootDoc, classDocs[i], tree, processedAttributes)) {
               return false;
            }
         }

         rootDoc.printNotice("The DebugScope tree will be written to " + filePath);
         ObjectOutputStream oos = null;

         try {
            FileOutputStream fos = new FileOutputStream(filePath);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(tree);
            rootDoc.printNotice("DebugScope tree generated successfully");
         } finally {
            if (oos != null) {
               oos.flush();
               oos.close();
            }

         }

         return true;
      }
   }

   public static int optionLength(String option) {
      if (option.equals("-debugScopeOutput")) {
         return 2;
      } else if (option.equals("-d")) {
         return 2;
      } else if (option.equals("-doctitle")) {
         return 2;
      } else {
         return option.equals("-windowtitle") ? 2 : 0;
      }
   }

   private static boolean processClass(RootDoc rootDoc, ClassDoc classDoc, DebugScopeTree tree, Set processedAttributes) throws InvalidDebugScopeException {
      rootDoc.printNotice("DebugScopesParser will process class " + classDoc.qualifiedTypeName());
      MethodDoc[] methodDocs = classDoc.methods();
      if (methodDocs == null) {
         String msg = "There are no javadocs for any method in the class " + classDoc.qualifiedTypeName();
         rootDoc.printError(msg);
         throw new InvalidDebugScopeException(msg);
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

            if (attributeType.equals("boolean") && !excludedAttrs.contains(attributeName)) {
               if (verbose) {
                  rootDoc.printNotice("Processing attribute name " + attributeName + " of type " + attributeType);
               }

               Tag[] tags = methodDoc.tags("excludeFromDebugScope");
               if (tags != null && tags.length > 0) {
                  excludedAttrs.add(attributeName);
                  if (verbose) {
                     rootDoc.printNotice("Excluding attribute " + attributeName + " from DebugScopes.");
                  }
               } else {
                  tags = methodDoc.tags("debugScope");
                  if (tags != null && tags.length != 0) {
                     for(int j = 0; j < tags.length; ++j) {
                        Tag tag = tags[j];
                        StringTokenizer tk = new StringTokenizer(tag.text().trim());
                        String debugScope = "default";
                        if (tk.hasMoreTokens()) {
                           debugScope = tk.nextToken();
                        }

                        if (verbose) {
                           rootDoc.printNotice("Adding attribute " + attributeName + " to the DebugScope " + debugScope);
                        }

                        tree.addDebugAttributeToDebugScopeTree(debugScope, attributeName, processedAttributes);
                     }
                  } else {
                     tree.addDebugAttributeToDebugScopeTree("default", attributeName, processedAttributes);
                  }
               }
            }
         }

         return true;
      }
   }
}
