package org.apache.taglibs.standard.extra.spath;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

public class SPathParser implements SPathParserConstants {
   public SPathParserTokenManager token_source;
   ASCII_UCodeESC_CharStream jj_input_stream;
   public Token token;
   public Token jj_nt;
   private int jj_ntk;
   private Token jj_scanpos;
   private Token jj_lastpos;
   private int jj_la;
   public boolean lookingAhead;
   private boolean jj_semLA;
   private int jj_gen;
   private final int[] jj_la1;
   private final int[] jj_la1_0;
   private final JJCalls[] jj_2_rtns;
   private boolean jj_rescan;
   private int jj_gc;
   private Vector jj_expentries;
   private int[] jj_expentry;
   private int jj_kind;
   private int[] jj_lasttokens;
   private int jj_endpos;

   public static void main(String[] args) throws ParseException {
      SPathParser parser = new SPathParser(System.in);
      Path p = parser.expression();
      List l = p.getSteps();
      System.out.println();
      if (p instanceof AbsolutePath) {
         System.out.println("Root: /");
      }

      for(int i = 0; i < l.size(); ++i) {
         Step s = (Step)l.get(i);
         System.out.print("Step: " + s.getName());
         if (s.isDepthUnlimited()) {
            System.out.print("(*)");
         }

         System.out.println();
      }

   }

   public SPathParser(String x) {
      this((Reader)(new StringReader(x)));
   }

   public final Path expression() throws ParseException {
      Object expr;
      if (this.jj_2_1(Integer.MAX_VALUE)) {
         expr = this.absolutePath();
         this.jj_consume_token(0);
      } else {
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 2:
            case 4:
            case 13:
            case 14:
               expr = this.relativePath();
               this.jj_consume_token(0);
               break;
            default:
               this.jj_la1[0] = this.jj_gen;
               this.jj_consume_token(-1);
               throw new ParseException();
         }
      }

      return (Path)expr;
   }

   public final AbsolutePath absolutePath() throws ParseException {
      this.jj_consume_token(13);
      RelativePath relPath = this.relativePath();
      return new AbsolutePath(relPath);
   }

   public final RelativePath relativePath() throws ParseException {
      RelativePath relPath = null;
      Step step = this.step();
      switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
         case 13:
            this.jj_consume_token(13);
            relPath = this.relativePath();
            break;
         default:
            this.jj_la1[1] = this.jj_gen;
      }

      return new RelativePath(step, relPath);
   }

   public final Step step() throws ParseException {
      Token slash = null;
      Vector pl = null;
      switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
         case 13:
            slash = this.jj_consume_token(13);
            break;
         default:
            this.jj_la1[2] = this.jj_gen;
      }

      String nt = this.nameTest();

      while(true) {
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 16:
               Predicate p = this.predicate();
               if (pl == null) {
                  pl = new Vector();
               }

               pl.add(p);
               break;
            default:
               this.jj_la1[3] = this.jj_gen;
               return new Step(slash != null, nt, pl);
         }
      }
   }

   public final String nameTest() throws ParseException {
      Token name;
      switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
         case 2:
            name = this.jj_consume_token(2);
            break;
         case 4:
            name = this.jj_consume_token(4);
            break;
         case 14:
            name = this.jj_consume_token(14);
            break;
         default:
            this.jj_la1[4] = this.jj_gen;
            this.jj_consume_token(-1);
            throw new ParseException();
      }

      return name.toString();
   }

   public final Predicate predicate() throws ParseException {
      this.jj_consume_token(16);
      Predicate p = this.attributePredicate();
      this.jj_consume_token(17);
      return p;
   }

   public final Predicate attributePredicate() throws ParseException {
      this.jj_consume_token(18);
      Token attname = this.jj_consume_token(2);
      this.jj_consume_token(19);
      Token target = this.jj_consume_token(1);
      return new AttributePredicate(attname.toString(), target.toString());
   }

   private final boolean jj_2_1(int xla) {
      this.jj_la = xla;
      this.jj_lastpos = this.jj_scanpos = this.token;
      boolean retval = !this.jj_3_1();
      this.jj_save(0, xla);
      return retval;
   }

   private final boolean jj_3R_13() {
      if (this.jj_scan_token(18)) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else if (this.jj_scan_token(2)) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else if (this.jj_scan_token(19)) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else if (this.jj_scan_token(1)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3_1() {
      if (this.jj_3R_2()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_10() {
      if (this.jj_scan_token(4)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_11() {
      if (this.jj_scan_token(2)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_2() {
      if (this.jj_scan_token(13)) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else if (this.jj_3R_3()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_12() {
      if (this.jj_scan_token(16)) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else if (this.jj_3R_13()) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else if (this.jj_scan_token(17)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_8() {
      if (this.jj_3R_12()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_5() {
      if (this.jj_scan_token(13)) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else if (this.jj_3R_3()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_6() {
      if (this.jj_scan_token(13)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_3() {
      if (this.jj_3R_4()) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else {
         Token xsp = this.jj_scanpos;
         if (this.jj_3R_5()) {
            this.jj_scanpos = xsp;
         } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }

         return false;
      }
   }

   private final boolean jj_3R_4() {
      Token xsp = this.jj_scanpos;
      if (this.jj_3R_6()) {
         this.jj_scanpos = xsp;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      }

      if (this.jj_3R_7()) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else {
         do {
            xsp = this.jj_scanpos;
            if (this.jj_3R_8()) {
               this.jj_scanpos = xsp;
               return false;
            }
         } while(this.jj_la != 0 || this.jj_scanpos != this.jj_lastpos);

         return false;
      }
   }

   private final boolean jj_3R_9() {
      if (this.jj_scan_token(14)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_7() {
      Token xsp = this.jj_scanpos;
      if (this.jj_3R_9()) {
         this.jj_scanpos = xsp;
         if (this.jj_3R_10()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_11()) {
               return true;
            }

            if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
               return false;
            }
         } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      }

      return false;
   }

   public SPathParser(InputStream stream) {
      this.lookingAhead = false;
      this.jj_la1 = new int[5];
      this.jj_la1_0 = new int[]{24596, 8192, 8192, 65536, 16404};
      this.jj_2_rtns = new JJCalls[1];
      this.jj_rescan = false;
      this.jj_gc = 0;
      this.jj_expentries = new Vector();
      this.jj_kind = -1;
      this.jj_lasttokens = new int[100];
      this.jj_input_stream = new ASCII_UCodeESC_CharStream(stream, 1, 1);
      this.token_source = new SPathParserTokenManager(this.jj_input_stream);
      this.token = new Token();
      this.jj_ntk = -1;
      this.jj_gen = 0;

      int i;
      for(i = 0; i < 5; ++i) {
         this.jj_la1[i] = -1;
      }

      for(i = 0; i < this.jj_2_rtns.length; ++i) {
         this.jj_2_rtns[i] = new JJCalls();
      }

   }

   public void ReInit(InputStream stream) {
      this.jj_input_stream.ReInit((InputStream)stream, 1, 1);
      this.token_source.ReInit(this.jj_input_stream);
      this.token = new Token();
      this.jj_ntk = -1;
      this.jj_gen = 0;

      int i;
      for(i = 0; i < 5; ++i) {
         this.jj_la1[i] = -1;
      }

      for(i = 0; i < this.jj_2_rtns.length; ++i) {
         this.jj_2_rtns[i] = new JJCalls();
      }

   }

   public SPathParser(Reader stream) {
      this.lookingAhead = false;
      this.jj_la1 = new int[5];
      this.jj_la1_0 = new int[]{24596, 8192, 8192, 65536, 16404};
      this.jj_2_rtns = new JJCalls[1];
      this.jj_rescan = false;
      this.jj_gc = 0;
      this.jj_expentries = new Vector();
      this.jj_kind = -1;
      this.jj_lasttokens = new int[100];
      this.jj_input_stream = new ASCII_UCodeESC_CharStream(stream, 1, 1);
      this.token_source = new SPathParserTokenManager(this.jj_input_stream);
      this.token = new Token();
      this.jj_ntk = -1;
      this.jj_gen = 0;

      int i;
      for(i = 0; i < 5; ++i) {
         this.jj_la1[i] = -1;
      }

      for(i = 0; i < this.jj_2_rtns.length; ++i) {
         this.jj_2_rtns[i] = new JJCalls();
      }

   }

   public void ReInit(Reader stream) {
      this.jj_input_stream.ReInit((Reader)stream, 1, 1);
      this.token_source.ReInit(this.jj_input_stream);
      this.token = new Token();
      this.jj_ntk = -1;
      this.jj_gen = 0;

      int i;
      for(i = 0; i < 5; ++i) {
         this.jj_la1[i] = -1;
      }

      for(i = 0; i < this.jj_2_rtns.length; ++i) {
         this.jj_2_rtns[i] = new JJCalls();
      }

   }

   public SPathParser(SPathParserTokenManager tm) {
      this.lookingAhead = false;
      this.jj_la1 = new int[5];
      this.jj_la1_0 = new int[]{24596, 8192, 8192, 65536, 16404};
      this.jj_2_rtns = new JJCalls[1];
      this.jj_rescan = false;
      this.jj_gc = 0;
      this.jj_expentries = new Vector();
      this.jj_kind = -1;
      this.jj_lasttokens = new int[100];
      this.token_source = tm;
      this.token = new Token();
      this.jj_ntk = -1;
      this.jj_gen = 0;

      int i;
      for(i = 0; i < 5; ++i) {
         this.jj_la1[i] = -1;
      }

      for(i = 0; i < this.jj_2_rtns.length; ++i) {
         this.jj_2_rtns[i] = new JJCalls();
      }

   }

   public void ReInit(SPathParserTokenManager tm) {
      this.token_source = tm;
      this.token = new Token();
      this.jj_ntk = -1;
      this.jj_gen = 0;

      int i;
      for(i = 0; i < 5; ++i) {
         this.jj_la1[i] = -1;
      }

      for(i = 0; i < this.jj_2_rtns.length; ++i) {
         this.jj_2_rtns[i] = new JJCalls();
      }

   }

   private final Token jj_consume_token(int kind) throws ParseException {
      Token oldToken;
      if ((oldToken = this.token).next != null) {
         this.token = this.token.next;
      } else {
         this.token = this.token.next = this.token_source.getNextToken();
      }

      this.jj_ntk = -1;
      if (this.token.kind != kind) {
         this.token = oldToken;
         this.jj_kind = kind;
         throw this.generateParseException();
      } else {
         ++this.jj_gen;
         if (++this.jj_gc > 100) {
            this.jj_gc = 0;

            for(int i = 0; i < this.jj_2_rtns.length; ++i) {
               for(JJCalls c = this.jj_2_rtns[i]; c != null; c = c.next) {
                  if (c.gen < this.jj_gen) {
                     c.first = null;
                  }
               }
            }
         }

         return this.token;
      }
   }

   private final boolean jj_scan_token(int kind) {
      if (this.jj_scanpos == this.jj_lastpos) {
         --this.jj_la;
         if (this.jj_scanpos.next == null) {
            this.jj_lastpos = this.jj_scanpos = this.jj_scanpos.next = this.token_source.getNextToken();
         } else {
            this.jj_lastpos = this.jj_scanpos = this.jj_scanpos.next;
         }
      } else {
         this.jj_scanpos = this.jj_scanpos.next;
      }

      if (this.jj_rescan) {
         int i = 0;

         Token tok;
         for(tok = this.token; tok != null && tok != this.jj_scanpos; tok = tok.next) {
            ++i;
         }

         if (tok != null) {
            this.jj_add_error_token(kind, i);
         }
      }

      return this.jj_scanpos.kind != kind;
   }

   public final Token getNextToken() {
      if (this.token.next != null) {
         this.token = this.token.next;
      } else {
         this.token = this.token.next = this.token_source.getNextToken();
      }

      this.jj_ntk = -1;
      ++this.jj_gen;
      return this.token;
   }

   public final Token getToken(int index) {
      Token t = this.lookingAhead ? this.jj_scanpos : this.token;

      for(int i = 0; i < index; ++i) {
         if (t.next != null) {
            t = t.next;
         } else {
            t = t.next = this.token_source.getNextToken();
         }
      }

      return t;
   }

   private final int jj_ntk() {
      return (this.jj_nt = this.token.next) == null ? (this.jj_ntk = (this.token.next = this.token_source.getNextToken()).kind) : (this.jj_ntk = this.jj_nt.kind);
   }

   private void jj_add_error_token(int kind, int pos) {
      if (pos < 100) {
         if (pos == this.jj_endpos + 1) {
            this.jj_lasttokens[this.jj_endpos++] = kind;
         } else if (this.jj_endpos != 0) {
            this.jj_expentry = new int[this.jj_endpos];

            for(int i = 0; i < this.jj_endpos; ++i) {
               this.jj_expentry[i] = this.jj_lasttokens[i];
            }

            boolean exists = false;
            Enumeration enum_ = this.jj_expentries.elements();

            label48:
            do {
               int[] oldentry;
               do {
                  if (!enum_.hasMoreElements()) {
                     break label48;
                  }

                  oldentry = (int[])((int[])enum_.nextElement());
               } while(oldentry.length != this.jj_expentry.length);

               exists = true;

               for(int i = 0; i < this.jj_expentry.length; ++i) {
                  if (oldentry[i] != this.jj_expentry[i]) {
                     exists = false;
                     break;
                  }
               }
            } while(!exists);

            if (!exists) {
               this.jj_expentries.addElement(this.jj_expentry);
            }

            if (pos != 0) {
               this.jj_lasttokens[(this.jj_endpos = pos) - 1] = kind;
            }
         }

      }
   }

   public final ParseException generateParseException() {
      this.jj_expentries.removeAllElements();
      boolean[] la1tokens = new boolean[20];

      int i;
      for(i = 0; i < 20; ++i) {
         la1tokens[i] = false;
      }

      if (this.jj_kind >= 0) {
         la1tokens[this.jj_kind] = true;
         this.jj_kind = -1;
      }

      int j;
      for(i = 0; i < 5; ++i) {
         if (this.jj_la1[i] == this.jj_gen) {
            for(j = 0; j < 32; ++j) {
               if ((this.jj_la1_0[i] & 1 << j) != 0) {
                  la1tokens[j] = true;
               }
            }
         }
      }

      for(i = 0; i < 20; ++i) {
         if (la1tokens[i]) {
            this.jj_expentry = new int[1];
            this.jj_expentry[0] = i;
            this.jj_expentries.addElement(this.jj_expentry);
         }
      }

      this.jj_endpos = 0;
      this.jj_rescan_token();
      this.jj_add_error_token(0, 0);
      int[][] exptokseq = new int[this.jj_expentries.size()][];

      for(j = 0; j < this.jj_expentries.size(); ++j) {
         exptokseq[j] = (int[])((int[])this.jj_expentries.elementAt(j));
      }

      return new ParseException(this.token, exptokseq, tokenImage);
   }

   public final void enable_tracing() {
   }

   public final void disable_tracing() {
   }

   private final void jj_rescan_token() {
      this.jj_rescan = true;

      for(int i = 0; i < 1; ++i) {
         JJCalls p = this.jj_2_rtns[i];

         do {
            if (p.gen > this.jj_gen) {
               this.jj_la = p.arg;
               this.jj_lastpos = this.jj_scanpos = p.first;
               switch (i) {
                  case 0:
                     this.jj_3_1();
               }
            }

            p = p.next;
         } while(p != null);
      }

      this.jj_rescan = false;
   }

   private final void jj_save(int index, int xla) {
      JJCalls p;
      for(p = this.jj_2_rtns[index]; p.gen > this.jj_gen; p = p.next) {
         if (p.next == null) {
            p = p.next = new JJCalls();
            break;
         }
      }

      p.gen = this.jj_gen + xla - this.jj_la;
      p.first = this.token;
      p.arg = xla;
   }

   static final class JJCalls {
      int gen;
      Token first;
      int arg;
      JJCalls next;
   }
}
