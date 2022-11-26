package antlr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

class ImportVocabTokenManager extends SimpleTokenManager implements Cloneable {
   private String filename;
   protected Grammar grammar;

   ImportVocabTokenManager(Grammar var1, String var2, String var3, Tool var4) {
      super(var3, var4);
      this.grammar = var1;
      this.filename = var2;
      File var5 = new File(this.filename);
      if (!var5.exists()) {
         var5 = new File(this.antlrTool.getOutputDirectory(), this.filename);
         if (!var5.exists()) {
            this.antlrTool.panic("Cannot find importVocab file '" + this.filename + "'");
         }
      }

      this.setReadOnly(true);

      try {
         BufferedReader var6 = new BufferedReader(new FileReader(var5));
         ANTLRTokdefLexer var7 = new ANTLRTokdefLexer(var6);
         ANTLRTokdefParser var8 = new ANTLRTokdefParser(var7);
         var8.setTool(this.antlrTool);
         var8.setFilename(this.filename);
         var8.file(this);
      } catch (FileNotFoundException var9) {
         this.antlrTool.panic("Cannot find importVocab file '" + this.filename + "'");
      } catch (RecognitionException var10) {
         this.antlrTool.panic("Error parsing importVocab file '" + this.filename + "': " + var10.toString());
      } catch (TokenStreamException var11) {
         this.antlrTool.panic("Error reading importVocab file '" + this.filename + "'");
      }

   }

   public Object clone() {
      ImportVocabTokenManager var1 = (ImportVocabTokenManager)super.clone();
      var1.filename = this.filename;
      var1.grammar = this.grammar;
      return var1;
   }

   public void define(TokenSymbol var1) {
      super.define(var1);
   }

   public void define(String var1, int var2) {
      Object var3 = null;
      if (var1.startsWith("\"")) {
         var3 = new StringLiteralSymbol(var1);
      } else {
         var3 = new TokenSymbol(var1);
      }

      ((TokenSymbol)var3).setTokenType(var2);
      super.define((TokenSymbol)var3);
      this.maxToken = var2 + 1 > this.maxToken ? var2 + 1 : this.maxToken;
   }

   public boolean isReadOnly() {
      return this.readOnly;
   }

   public int nextTokenType() {
      return super.nextTokenType();
   }
}
