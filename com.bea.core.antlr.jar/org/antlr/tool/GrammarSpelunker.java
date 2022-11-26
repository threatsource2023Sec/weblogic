package org.antlr.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class GrammarSpelunker {
   protected String grammarFileName;
   protected String token;
   protected Scanner scanner;
   protected String grammarModifier;
   protected String grammarName;
   protected String tokenVocab;
   protected String language = "Java";
   protected String inputDirectory;
   protected List importedGrammars;

   public GrammarSpelunker(String inputDirectory, String grammarFileName) {
      this.inputDirectory = inputDirectory;
      this.grammarFileName = grammarFileName;
   }

   void consume() throws IOException {
      this.token = this.scanner.nextToken();
   }

   protected void match(String expecting) throws IOException {
      if (this.token.equals(expecting)) {
         this.consume();
      } else {
         throw new Error("Error parsing " + this.grammarFileName + ": '" + this.token + "' not expected '" + expecting + "'");
      }
   }

   public void parse() throws IOException {
      Reader r = new FileReader((this.inputDirectory != null ? this.inputDirectory + File.separator : "") + this.grammarFileName);
      BufferedReader br = new BufferedReader(r);

      try {
         this.scanner = new Scanner(br);
         this.consume();
         this.grammarHeader();

         while(this.token != null && !this.token.equals("@") && !this.token.equals(":") && !this.token.equals("import") && !this.token.equals("options")) {
            this.consume();
         }

         if (this.token.equals("options")) {
            this.options();
         }

         while(this.token != null && !this.token.equals("@") && !this.token.equals(":") && !this.token.equals("import")) {
            this.consume();
         }

         if (this.token.equals("import")) {
            this.imports();
         }
      } finally {
         if (br != null) {
            br.close();
         }

      }

   }

   protected void grammarHeader() throws IOException {
      if (this.token != null) {
         if (this.token.equals("tree") || this.token.equals("parser") || this.token.equals("lexer")) {
            this.grammarModifier = this.token;
            this.consume();
         }

         this.match("grammar");
         this.grammarName = this.token;
         this.consume();
      }
   }

   protected void options() throws IOException {
      this.match("options");
      this.match("{");

      while(this.token != null && !this.token.equals("}")) {
         String name = this.token;
         this.consume();
         String value = this.token;
         this.consume();
         this.match(";");
         if (name.equals("tokenVocab")) {
            this.tokenVocab = value;
         }

         if (name.equals("language")) {
            this.language = value;
         }
      }

      this.match("}");
   }

   protected void imports() throws IOException {
      this.match("import");
      this.importedGrammars = new ArrayList();

      while(this.token != null && !this.token.equals(";")) {
         this.importedGrammars.add(this.token);
         this.consume();
      }

      this.match(";");
      if (this.importedGrammars.isEmpty()) {
         this.importedGrammars = null;
      }

   }

   public String getGrammarModifier() {
      return this.grammarModifier;
   }

   public String getGrammarName() {
      return this.grammarName;
   }

   public String getTokenVocab() {
      return this.tokenVocab;
   }

   public String getLanguage() {
      return this.language;
   }

   public List getImportedGrammars() {
      return this.importedGrammars;
   }

   public static void main(String[] args) throws IOException {
      GrammarSpelunker g = new GrammarSpelunker(".", args[0]);
      g.parse();
      System.out.println(g.grammarModifier + " grammar " + g.grammarName);
      System.out.println("language=" + g.language);
      System.out.println("tokenVocab=" + g.tokenVocab);
      System.out.println("imports=" + g.importedGrammars);
   }

   public static class Scanner {
      public static final int EOF = -1;
      Reader input;
      int c;

      public Scanner(Reader input) throws IOException {
         this.input = input;
         this.consume();
      }

      boolean isDIGIT() {
         return this.c >= 48 && this.c <= 57;
      }

      boolean isID_START() {
         return this.c >= 97 && this.c <= 122 || this.c >= 65 && this.c <= 90;
      }

      boolean isID_LETTER() {
         return this.isID_START() || this.c >= 48 && this.c <= 57 || this.c == 95;
      }

      void consume() throws IOException {
         this.c = this.input.read();
      }

      public String nextToken() throws IOException {
         while(this.c != -1) {
            switch (this.c) {
               case 39:
                  return this.STRING();
               case 47:
                  this.COMMENT();
                  break;
               case 58:
                  this.consume();
                  return ":";
               case 59:
                  this.consume();
                  return ";";
               case 64:
                  this.consume();
                  return "@";
               case 123:
                  this.consume();
                  return "{";
               case 125:
                  this.consume();
                  return "}";
               default:
                  if (this.isID_START()) {
                     return this.ID();
                  }

                  if (this.isDIGIT()) {
                     return this.INT();
                  }

                  this.consume();
            }
         }

         return null;
      }

      String ID() throws IOException {
         StringBuilder buf = new StringBuilder();

         while(this.c != -1 && this.isID_LETTER()) {
            buf.append((char)this.c);
            this.consume();
         }

         return buf.toString();
      }

      String INT() throws IOException {
         StringBuilder buf = new StringBuilder();

         while(this.c != -1 && this.isDIGIT()) {
            buf.append((char)this.c);
            this.consume();
         }

         return buf.toString();
      }

      String STRING() throws IOException {
         StringBuilder buf = new StringBuilder();
         this.consume();

         while(this.c != -1 && this.c != 39) {
            if (this.c == 92) {
               buf.append((char)this.c);
               this.consume();
            }

            buf.append((char)this.c);
            this.consume();
         }

         this.consume();
         return buf.toString();
      }

      void COMMENT() throws IOException {
         if (this.c == 47) {
            this.consume();
            if (this.c == 42) {
               this.consume();

               do {
                  while(this.c != 42) {
                     while(this.c != -1 && this.c != 42) {
                        this.consume();
                     }
                  }

                  this.consume();
               } while(this.c != 47);

               this.consume();
            } else if (this.c == 47) {
               while(this.c != -1 && this.c != 10) {
                  this.consume();
               }
            }
         }

      }
   }
}
