package org.antlr.tool;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class GrammarSerializerFoo {
   protected DataOutputStream out;
   protected String filename;
   protected Grammar g;
   protected ByteArrayOutputStream altBuf;
   protected int numElementsInAlt = 0;

   public GrammarSerializerFoo(Grammar g) {
      this.g = g;
   }

   public void open(String filename) throws IOException {
      this.filename = filename;
      FileOutputStream fos = new FileOutputStream(filename);
      BufferedOutputStream bos = new BufferedOutputStream(fos);
      this.out = new DataOutputStream(bos);
      this.writeString(this.out, "$ANTLR");
      this.out.writeByte(1);
   }

   public void close() throws IOException {
      if (this.out != null) {
         this.out.close();
      }

      this.out = null;
   }

   public void grammar(int grammarTokenType, String name) {
      try {
         this.out.writeShort(this.g.getRules().size());
      } catch (IOException var4) {
         ErrorManager.error(1, (Object)this.filename);
      }

   }

   public void rule(String name) {
      try {
         this.out.writeByte(82);
         this.writeString(this.out, name);
      } catch (IOException var3) {
         ErrorManager.error(1, (Object)this.filename);
      }

   }

   public void endRule() {
      try {
         this.out.writeByte(46);
      } catch (IOException var2) {
         ErrorManager.error(1, (Object)this.filename);
      }

   }

   public void block(int nalts) {
      try {
         this.out.writeByte(66);
         this.out.writeShort(nalts);
      } catch (IOException var3) {
         ErrorManager.error(1, (Object)this.filename);
      }

   }

   public void alt(GrammarAST alt) {
      this.numElementsInAlt = 0;

      try {
         this.out.writeByte(65);
      } catch (IOException var3) {
         ErrorManager.error(1, (Object)this.filename);
      }

   }

   public void endAlt() {
      try {
         this.out.writeByte(59);
      } catch (IOException var2) {
         ErrorManager.error(1, (Object)this.filename);
      }

   }

   public void ruleRef(GrammarAST t) {
      ++this.numElementsInAlt;

      try {
         this.out.writeByte(114);
         this.out.writeShort(this.g.getRuleIndex(t.getText()));
      } catch (IOException var3) {
         ErrorManager.error(1, (Object)this.filename);
      }

   }

   public void token(GrammarAST t) {
      ++this.numElementsInAlt;

      try {
         this.out.writeByte(116);
         int ttype = this.g.getTokenType(t.getText());
         this.out.writeShort(ttype);
      } catch (IOException var3) {
         ErrorManager.error(1, (Object)this.filename);
      }

   }

   public void charLiteral(GrammarAST t) {
      ++this.numElementsInAlt;

      try {
         if (this.g.type != 1) {
            this.out.writeByte(116);
            int ttype = this.g.getTokenType(t.getText());
            this.out.writeShort(ttype);
         }
      } catch (IOException var3) {
         ErrorManager.error(1, (Object)this.filename);
      }

   }

   public void wildcard(GrammarAST t) {
      ++this.numElementsInAlt;

      try {
         this.out.writeByte(119);
      } catch (IOException var3) {
         ErrorManager.error(1, (Object)this.filename);
      }

   }

   public void range() {
      ++this.numElementsInAlt;

      try {
         this.out.writeByte(45);
      } catch (IOException var2) {
         ErrorManager.error(1, (Object)this.filename);
      }

   }

   public void not() {
      try {
         this.out.writeByte(126);
      } catch (IOException var2) {
         ErrorManager.error(1, (Object)this.filename);
      }

   }

   public void writeString(DataOutputStream out, String s) throws IOException {
      out.writeBytes(s);
      out.writeByte(59);
   }
}
