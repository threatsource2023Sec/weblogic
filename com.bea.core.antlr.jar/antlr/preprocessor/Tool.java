package antlr.preprocessor;

import antlr.collections.impl.Vector;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;

public class Tool {
   protected Hierarchy theHierarchy;
   protected String grammarFileName;
   protected String[] args;
   protected int nargs;
   protected Vector grammars;
   protected antlr.Tool antlrTool;

   public Tool(antlr.Tool var1, String[] var2) {
      this.antlrTool = var1;
      this.processArguments(var2);
   }

   public static void main(String[] var0) {
      antlr.Tool var1 = new antlr.Tool();
      Tool var2 = new Tool(var1, var0);
      var2.preprocess();
      String[] var3 = var2.preprocessedArgList();

      for(int var4 = 0; var4 < var3.length; ++var4) {
         System.out.print(" " + var3[var4]);
      }

      System.out.println();
   }

   public boolean preprocess() {
      if (this.grammarFileName == null) {
         this.antlrTool.toolError("no grammar file specified");
         return false;
      } else {
         if (this.grammars != null) {
            this.theHierarchy = new Hierarchy(this.antlrTool);
            Enumeration var1 = this.grammars.elements();

            while(var1.hasMoreElements()) {
               String var2 = (String)var1.nextElement();

               try {
                  this.theHierarchy.readGrammarFile(var2);
               } catch (FileNotFoundException var6) {
                  this.antlrTool.toolError("file " + var2 + " not found");
                  return false;
               }
            }
         }

         boolean var7 = this.theHierarchy.verifyThatHierarchyIsComplete();
         if (!var7) {
            return false;
         } else {
            this.theHierarchy.expandGrammarsInFile(this.grammarFileName);
            GrammarFile var8 = this.theHierarchy.getFile(this.grammarFileName);
            String var3 = var8.nameForExpandedGrammarFile(this.grammarFileName);
            if (var3.equals(this.grammarFileName)) {
               this.args[this.nargs++] = this.grammarFileName;
            } else {
               try {
                  var8.generateExpandedFile();
                  this.args[this.nargs++] = this.antlrTool.getOutputDirectory() + System.getProperty("file.separator") + var3;
               } catch (IOException var5) {
                  this.antlrTool.toolError("cannot write expanded grammar file " + var3);
                  return false;
               }
            }

            return true;
         }
      }
   }

   public String[] preprocessedArgList() {
      String[] var1 = new String[this.nargs];
      System.arraycopy(this.args, 0, var1, 0, this.nargs);
      this.args = var1;
      return this.args;
   }

   private void processArguments(String[] var1) {
      this.nargs = 0;
      this.args = new String[var1.length];

      for(int var2 = 0; var2 < var1.length; ++var2) {
         if (var1[var2].length() == 0) {
            this.antlrTool.warning("Zero length argument ignoring...");
         } else if (var1[var2].equals("-glib")) {
            if (File.separator.equals("\\") && var1[var2].indexOf(47) != -1) {
               this.antlrTool.warning("-glib cannot deal with '/' on a PC: use '\\'; ignoring...");
            } else {
               antlr.Tool var10001 = this.antlrTool;
               this.grammars = antlr.Tool.parseSeparatedList(var1[var2 + 1], ';');
               ++var2;
            }
         } else if (var1[var2].equals("-o")) {
            this.args[this.nargs++] = var1[var2];
            if (var2 + 1 >= var1.length) {
               this.antlrTool.error("missing output directory with -o option; ignoring");
            } else {
               ++var2;
               this.args[this.nargs++] = var1[var2];
               this.antlrTool.setOutputDirectory(var1[var2]);
            }
         } else if (var1[var2].charAt(0) == '-') {
            this.args[this.nargs++] = var1[var2];
         } else {
            this.grammarFileName = var1[var2];
            if (this.grammars == null) {
               this.grammars = new Vector(10);
            }

            this.grammars.appendElement(this.grammarFileName);
            if (var2 + 1 < var1.length) {
               this.antlrTool.warning("grammar file must be last; ignoring other arguments...");
               break;
            }
         }
      }

   }
}
