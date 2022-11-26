package antlr.preprocessor;

import antlr.CodeGenerator;
import antlr.collections.impl.IndexedVector;
import java.io.IOException;
import java.util.Enumeration;

class Grammar {
   protected String name;
   protected String fileName;
   protected String superGrammar;
   protected String type;
   protected IndexedVector rules;
   protected IndexedVector options;
   protected String tokenSection;
   protected String preambleAction;
   protected String memberAction;
   protected Hierarchy hier;
   protected boolean predefined = false;
   protected boolean alreadyExpanded = false;
   protected boolean specifiedVocabulary = false;
   protected String superClass = null;
   protected String importVocab = null;
   protected String exportVocab = null;
   protected antlr.Tool antlrTool;

   public Grammar(antlr.Tool var1, String var2, String var3, IndexedVector var4) {
      this.name = var2;
      this.superGrammar = var3;
      this.rules = var4;
      this.antlrTool = var1;
   }

   public void addOption(Option var1) {
      if (this.options == null) {
         this.options = new IndexedVector();
      }

      this.options.appendElement(var1.getName(), var1);
   }

   public void addRule(Rule var1) {
      this.rules.appendElement(var1.getName(), var1);
   }

   public void expandInPlace() {
      if (!this.alreadyExpanded) {
         Grammar var1 = this.getSuperGrammar();
         if (var1 != null) {
            if (this.exportVocab == null) {
               this.exportVocab = this.getName();
            }

            if (!var1.isPredefined()) {
               var1.expandInPlace();
               this.alreadyExpanded = true;
               GrammarFile var2 = this.hier.getFile(this.getFileName());
               var2.setExpanded(true);
               IndexedVector var3 = var1.getRules();
               Enumeration var4 = var3.elements();

               while(var4.hasMoreElements()) {
                  Rule var5 = (Rule)var4.nextElement();
                  this.inherit(var5, var1);
               }

               IndexedVector var12 = var1.getOptions();
               if (var12 != null) {
                  Enumeration var13 = var12.elements();

                  while(var13.hasMoreElements()) {
                     Option var6 = (Option)var13.nextElement();
                     this.inherit(var6, var1);
                  }
               }

               if (this.options != null && this.options.getElement("importVocab") == null || this.options == null) {
                  Option var14 = new Option("importVocab", var1.exportVocab + ";", this);
                  this.addOption(var14);
                  String var15 = var1.getFileName();
                  String var7 = this.antlrTool.pathToFile(var15);
                  String var8 = var7 + var1.exportVocab + CodeGenerator.TokenTypesFileSuffix + CodeGenerator.TokenTypesFileExt;
                  String var9 = this.antlrTool.fileMinusPath(var8);
                  if (!var7.equals("." + System.getProperty("file.separator"))) {
                     try {
                        this.antlrTool.copyFile(var8, var9);
                     } catch (IOException var11) {
                        this.antlrTool.toolError("cannot find/copy importVocab file " + var8);
                        return;
                     }
                  }
               }

               this.inherit(var1.memberAction, var1);
            }
         }
      }
   }

   public String getFileName() {
      return this.fileName;
   }

   public String getName() {
      return this.name;
   }

   public IndexedVector getOptions() {
      return this.options;
   }

   public IndexedVector getRules() {
      return this.rules;
   }

   public Grammar getSuperGrammar() {
      if (this.superGrammar == null) {
         return null;
      } else {
         Grammar var1 = this.hier.getGrammar(this.superGrammar);
         return var1;
      }
   }

   public String getSuperGrammarName() {
      return this.superGrammar;
   }

   public String getType() {
      return this.type;
   }

   public void inherit(Option var1, Grammar var2) {
      if (!var1.getName().equals("importVocab") && !var1.getName().equals("exportVocab")) {
         Option var3 = null;
         if (this.options != null) {
            var3 = (Option)this.options.getElement(var1.getName());
         }

         if (var3 == null) {
            this.addOption(var1);
         }

      }
   }

   public void inherit(Rule var1, Grammar var2) {
      Rule var3 = (Rule)this.rules.getElement(var1.getName());
      if (var3 != null) {
         if (!var3.sameSignature(var1)) {
            this.antlrTool.warning("rule " + this.getName() + "." + var3.getName() + " has different signature than " + var2.getName() + "." + var3.getName());
         }
      } else {
         this.addRule(var1);
      }

   }

   public void inherit(String var1, Grammar var2) {
      if (this.memberAction == null) {
         if (var1 != null) {
            this.memberAction = var1;
         }

      }
   }

   public boolean isPredefined() {
      return this.predefined;
   }

   public void setFileName(String var1) {
      this.fileName = var1;
   }

   public void setHierarchy(Hierarchy var1) {
      this.hier = var1;
   }

   public void setMemberAction(String var1) {
      this.memberAction = var1;
   }

   public void setOptions(IndexedVector var1) {
      this.options = var1;
   }

   public void setPreambleAction(String var1) {
      this.preambleAction = var1;
   }

   public void setPredefined(boolean var1) {
      this.predefined = var1;
   }

   public void setTokenSection(String var1) {
      this.tokenSection = var1;
   }

   public void setType(String var1) {
      this.type = var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer(10000);
      if (this.preambleAction != null) {
         var1.append(this.preambleAction);
      }

      if (this.superGrammar == null) {
         return "class " + this.name + ";";
      } else {
         if (this.superClass != null) {
            var1.append("class " + this.name + " extends " + this.superClass + ";");
         } else {
            var1.append("class " + this.name + " extends " + this.type + ";");
         }

         var1.append(System.getProperty("line.separator") + System.getProperty("line.separator"));
         if (this.options != null) {
            var1.append(Hierarchy.optionsToString(this.options));
         }

         if (this.tokenSection != null) {
            var1.append(this.tokenSection + "\n");
         }

         if (this.memberAction != null) {
            var1.append(this.memberAction + System.getProperty("line.separator"));
         }

         for(int var2 = 0; var2 < this.rules.size(); ++var2) {
            Rule var3 = (Rule)this.rules.elementAt(var2);
            if (!this.getName().equals(var3.enclosingGrammar.getName())) {
               var1.append("// inherited from grammar " + var3.enclosingGrammar.getName() + System.getProperty("line.separator"));
            }

            var1.append(var3 + System.getProperty("line.separator") + System.getProperty("line.separator"));
         }

         return var1.toString();
      }
   }
}
