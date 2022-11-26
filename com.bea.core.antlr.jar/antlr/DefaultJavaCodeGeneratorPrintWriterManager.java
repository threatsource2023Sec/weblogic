package antlr;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class DefaultJavaCodeGeneratorPrintWriterManager implements JavaCodeGeneratorPrintWriterManager {
   private Grammar grammar;
   private PrintWriterWithSMAP smapOutput;
   private PrintWriter currentOutput;
   private Tool tool;
   private Map sourceMaps = new HashMap();
   private String currentFileName;

   public PrintWriter setupOutput(Tool var1, Grammar var2) throws IOException {
      return this.setupOutput(var1, var2, (String)null);
   }

   public PrintWriter setupOutput(Tool var1, String var2) throws IOException {
      return this.setupOutput(var1, (Grammar)null, var2);
   }

   public PrintWriter setupOutput(Tool var1, Grammar var2, String var3) throws IOException {
      this.tool = var1;
      this.grammar = var2;
      if (var3 == null) {
         var3 = var2.getClassName();
      }

      this.smapOutput = new PrintWriterWithSMAP(var1.openOutputFile(var3 + ".java"));
      this.currentFileName = var3 + ".java";
      this.currentOutput = this.smapOutput;
      return this.currentOutput;
   }

   public void startMapping(int var1) {
      this.smapOutput.startMapping(var1);
   }

   public void startSingleSourceLineMapping(int var1) {
      this.smapOutput.startSingleSourceLineMapping(var1);
   }

   public void endMapping() {
      this.smapOutput.endMapping();
   }

   public void finishOutput() throws IOException {
      this.currentOutput.close();
      if (this.grammar != null) {
         PrintWriter var1 = this.tool.openOutputFile(this.grammar.getClassName() + ".smap");
         String var2 = this.grammar.getFilename();
         var2 = var2.replace('\\', '/');
         int var3 = var2.lastIndexOf(47);
         if (var3 != -1) {
            var2 = var2.substring(var3 + 1);
         }

         this.smapOutput.dump(var1, this.grammar.getClassName(), var2);
         this.sourceMaps.put(this.currentFileName, this.smapOutput.getSourceMap());
      }

      this.currentOutput = null;
   }

   public Map getSourceMaps() {
      return this.sourceMaps;
   }

   public int getCurrentOutputLine() {
      return this.smapOutput.getCurrentOutputLine();
   }
}
