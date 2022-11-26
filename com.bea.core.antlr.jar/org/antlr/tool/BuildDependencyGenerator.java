package org.antlr.tool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.antlr.Tool;
import org.antlr.codegen.CodeGenerator;
import org.antlr.misc.Utils;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class BuildDependencyGenerator {
   protected String grammarFileName;
   protected String tokenVocab;
   protected Tool tool;
   protected Grammar grammar;
   protected CodeGenerator generator;
   protected STGroup templates;

   public BuildDependencyGenerator(Tool tool, String grammarFileName) throws IOException {
      this.tool = tool;
      this.grammarFileName = grammarFileName;
      this.grammar = tool.getRootGrammar(grammarFileName);
      String language = (String)this.grammar.getOption("language");
      this.generator = new CodeGenerator(tool, this.grammar, language);
      this.generator.loadTemplates(language);
   }

   public List getGeneratedFileList() {
      List files = new ArrayList();
      File outputDir = this.tool.getOutputDirectory(this.grammarFileName);
      String escSpaces;
      if (outputDir.getName().equals(".")) {
         outputDir = outputDir.getParentFile();
      } else if (outputDir.getName().indexOf(32) >= 0) {
         escSpaces = Utils.replace(outputDir.toString(), " ", "\\ ");
         outputDir = new File(escSpaces);
      }

      escSpaces = this.generator.getRecognizerFileName(this.grammar.name, this.grammar.type);
      files.add(new File(outputDir, escSpaces));
      files.add(new File(this.tool.getOutputDirectory(), this.generator.getVocabFileName()));
      ST headerExtST = null;
      ST extST = this.generator.getTemplates().getInstanceOf("codeFileExtension");
      String suffix;
      String lexer;
      if (this.generator.getTemplates().isDefined("headerFile")) {
         headerExtST = this.generator.getTemplates().getInstanceOf("headerFileExtension");
         suffix = Grammar.grammarTypeToFileNameSuffix[this.grammar.type];
         lexer = this.grammar.name + suffix + headerExtST.render();
         files.add(new File(outputDir, lexer));
      }

      if (this.grammar.type == 4) {
         suffix = Grammar.grammarTypeToFileNameSuffix[1];
         lexer = this.grammar.name + suffix + extST.render();
         files.add(new File(outputDir, lexer));
         if (headerExtST != null) {
            String header = this.grammar.name + suffix + headerExtST.render();
            files.add(new File(outputDir, header));
         }
      }

      List imports = this.grammar.composite.getDelegates(this.grammar.composite.getRootGrammar());
      Iterator i$ = imports.iterator();

      while(i$.hasNext()) {
         Grammar g = (Grammar)i$.next();
         outputDir = this.tool.getOutputDirectory(g.getFileName());
         String fname = this.groomQualifiedFileName(outputDir.toString(), g.getRecognizerName() + extST.render());
         files.add(new File(fname));
      }

      return files.isEmpty() ? null : files;
   }

   public List getDependenciesFileList() {
      List files = this.getNonImportDependenciesFileList();
      List imports = this.grammar.composite.getDelegates(this.grammar.composite.getRootGrammar());
      Iterator i$ = imports.iterator();

      while(i$.hasNext()) {
         Grammar g = (Grammar)i$.next();
         String libdir = this.tool.getLibraryDirectory();
         String fileName = this.groomQualifiedFileName(libdir, g.fileName);
         files.add(new File(fileName));
      }

      return files.isEmpty() ? null : files;
   }

   public List getNonImportDependenciesFileList() {
      List files = new ArrayList();
      this.tokenVocab = (String)this.grammar.getOption("tokenVocab");
      if (this.tokenVocab != null) {
         File vocabFile = this.tool.getImportedVocabFile(this.tokenVocab);
         files.add(vocabFile);
      }

      return files;
   }

   public ST getDependencies() {
      this.loadDependencyTemplates();
      ST dependenciesST = this.templates.getInstanceOf("dependencies");
      dependenciesST.add("in", this.getDependenciesFileList());
      dependenciesST.add("out", this.getGeneratedFileList());
      dependenciesST.add("grammarFileName", this.grammar.fileName);
      return dependenciesST;
   }

   public void loadDependencyTemplates() {
      if (this.templates == null) {
         String fileName = "org/antlr/tool/templates/depend.stg";
         this.templates = new ToolSTGroupFile(fileName);
      }
   }

   public String getTokenVocab() {
      return this.tokenVocab;
   }

   public CodeGenerator getGenerator() {
      return this.generator;
   }

   public String groomQualifiedFileName(String outputDir, String fileName) {
      if (outputDir.equals(".")) {
         return fileName;
      } else if (outputDir.indexOf(32) >= 0) {
         String escSpaces = Utils.replace(outputDir.toString(), " ", "\\ ");
         return escSpaces + File.separator + fileName;
      } else {
         return outputDir + File.separator + fileName;
      }
   }
}
