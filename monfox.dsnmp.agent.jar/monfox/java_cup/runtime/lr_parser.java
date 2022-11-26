package monfox.java_cup.runtime;

import java.util.Stack;
import monfox.toolkit.snmp.SnmpException;

public abstract class lr_parser {
   protected static final int _error_sync_size = 3;
   protected boolean _done_parsing = false;
   protected int tos;
   protected Symbol cur_token;
   protected Stack stack = new Stack();
   protected short[][] production_tab;
   protected short[][] action_tab;
   protected short[][] reduce_tab;
   protected Symbol[] lookahead;
   protected int lookahead_pos;

   protected int error_sync_size() {
      return 3;
   }

   public abstract short[][] production_table();

   public abstract short[][] action_table();

   public abstract short[][] reduce_table();

   public abstract int start_state();

   public abstract int start_production();

   public abstract int EOF_sym();

   public abstract int error_sym();

   public void done_parsing() {
      this._done_parsing = true;
   }

   public abstract Symbol do_action(int var1, lr_parser var2, Stack var3, int var4) throws Exception;

   public void user_init() throws Exception {
   }

   protected abstract void init_actions() throws Exception;

   public abstract Symbol scan() throws Exception;

   public void report_fatal_error(String var1, Object var2) throws Exception {
      this.done_parsing();
      this.report_error(var1, var2);
      throw new Exception(a("+k\t\u0005\u007fHx\u0002Ad\u001eo\u0015\u0002m\u001ae\n\u0002{\u001ao\u0011Kd\u001dyGGy\u001ae\u0015\nxA"));
   }

   public void report_error(String var1, Object var2) {
      boolean var3 = Symbol.a;
      System.err.print(var1);
      if (var2 instanceof Symbol) {
         if (((Symbol)var2).left != -1) {
            System.err.println(a("Hk\u0013\u0002h\u0000k\u0015Ch\u001co\u0015\u0002") + ((Symbol)var2).left + a("He\u0001\u0002b\u0006z\u0012V"));
            if (!var3) {
               return;
            }
         }

         System.err.println("");
         if (!var3) {
            return;
         }
      }

      System.err.println("");
   }

   public void syntax_error(Symbol var1) {
      this.report_error(a(";s\tVj\u0010*\u0002Py\u0007x"), var1);
   }

   public void unrecovered_syntax_error(Symbol var1) throws Exception {
      this.report_fatal_error(a("+e\u0012No\u0006-\u0013\u0002y\rz\u0006KyHk\tF+\u000be\tVb\u0006\u007f\u0002\u0002{\tx\u0014G"), var1);
   }

   protected final short get_action(int var1, int var2) {
      boolean var8 = Symbol.a;
      short[] var7 = this.action_tab[var1];
      int var10000;
      int var6;
      if (var7.length < 20) {
         var6 = 0;

         do {
            if (var6 >= var7.length) {
               var10000 = 0;
               return (short)var10000;
            }

            short var3 = var7[var6++];
            var10000 = var3;
            if (var8) {
               return (short)var10000;
            }

            if (var3 == var2 || var3 == -1) {
               return var7[var6];
            }

            ++var6;
         } while(!var8);
      }

      int var4 = 0;
      int var5 = (var7.length - 1) / 2 - 1;

      while(true) {
         if (var4 <= var5) {
            var6 = (var4 + var5) / 2;
            var10000 = var2;
            if (var8 || var8) {
               break;
            }

            if (var2 == var7[var6 * 2]) {
               return var7[var6 * 2 + 1];
            }

            if (var2 > var7[var6 * 2]) {
               var4 = var6 + 1;
               if (!var8) {
                  continue;
               }
            }

            var5 = var6 - 1;
            if (!var8) {
               continue;
            }
         }

         var10000 = var7[var7.length - 1];
         break;
      }

      return (short)var10000;
   }

   protected final short get_reduce(int var1, int var2) {
      boolean var6 = Symbol.a;
      short[] var4 = this.reduce_tab[var1];
      if (var4 == null) {
         return -1;
      } else {
         int var5 = 0;

         short var10000;
         while(true) {
            if (var5 < var4.length) {
               short var3 = var4[var5++];
               var10000 = var3;
               if (var6) {
                  break;
               }

               if (var3 == var2 || var3 == -1) {
                  return var4[var5];
               }

               ++var5;
               if (!var6) {
                  continue;
               }
            }

            var10000 = -1;
            break;
         }

         return var10000;
      }
   }

   public Symbol parse() throws Exception {
      boolean var6 = Symbol.a;
      Symbol var2 = null;
      this.production_tab = this.production_table();
      this.action_tab = this.action_table();
      this.reduce_tab = this.reduce_table();
      this.init_actions();
      this.user_init();
      this.cur_token = this.scan();
      this.stack.removeAllElements();
      this.stack.push(new Symbol(0, this.start_state()));
      this.tos = 0;
      this._done_parsing = false;

      while(!this._done_parsing) {
         short var1 = this.get_action(((Symbol)this.stack.peek()).parse_state, this.cur_token.sym);
         if (var1 > 0) {
            this.cur_token.parse_state = var1 - 1;
            this.stack.push(this.cur_token);
            ++this.tos;
            this.cur_token = this.scan();
            if (!var6) {
               continue;
            }
         }

         if (var1 < 0) {
            var2 = this.do_action(-var1 - 1, this, this.stack, this.tos);
            short var4 = this.production_tab[-var1 - 1][0];
            short var3 = this.production_tab[-var1 - 1][1];
            int var5 = 0;

            label49: {
               while(var5 < var3) {
                  this.stack.pop();
                  --this.tos;
                  ++var5;
                  if (var6) {
                     break label49;
                  }

                  if (var6) {
                     break;
                  }
               }

               var1 = this.get_reduce(((Symbol)this.stack.peek()).parse_state, var4);
               var2.parse_state = var1;
               this.stack.push(var2);
               ++this.tos;
            }

            if (!var6) {
               continue;
            }
         }

         if (var1 == 0) {
            this.syntax_error(this.cur_token);
            if (!this.error_recovery(false)) {
               this.unrecovered_syntax_error(this.cur_token);
               this.done_parsing();
               if (!var6) {
                  continue;
               }
            }

            var2 = (Symbol)this.stack.peek();
            if (var6) {
               break;
            }
         }
      }

      if (SnmpException.b) {
         Symbol.a = !var6;
      }

      return var2;
   }

   public void debug_message(String var1) {
      System.err.println(var1);
   }

   public void dump_stack() {
      boolean var2 = Symbol.a;
      if (this.stack == null) {
         this.debug_message(a("K*4Vj\u000baGF~\u0005zGPn\u0019\u007f\u0002Q\u007f\rnK\u0002i\u001d~GQ\u007f\ti\f\u0002b\u001b*\tWg\u0004"));
      } else {
         this.debug_message(a("U7Z\u001f6U7Z\u001f6U7Grj\u001ay\u0002\u0002X\u001ck\u0004I+,\u007f\nR+U7Z\u001f6U7Z\u001f6U7"));
         int var1 = 0;

         while(true) {
            if (var1 < this.stack.size()) {
               this.debug_message(a(";s\n@d\u00040G") + ((Symbol)this.stack.elementAt(var1)).sym + a("HY\u0013C\u007f\r0G") + ((Symbol)this.stack.elementAt(var1)).parse_state);
               ++var1;
               if (var2) {
                  break;
               }

               if (!var2) {
                  continue;
               }
            }

            this.debug_message(a("U7Z\u001f6U7Z\u001f6U7Z\u001f6U7Z\u001f6U7Z\u001f6U7Z\u001f6U7Z\u001f6U7Z\u001f6U7"));
            break;
         }

      }
   }

   public void debug_reduce(int var1, int var2, int var3) {
      this.debug_message(a("K*5Go\u001di\u0002\u0002|\u0001~\u000f\u0002{\u001ae\u0003\u0002(") + var1 + a("HQ)v6") + var2 + a("D*") + a(";PZ") + var3 + "]");
   }

   public void debug_shift(Symbol var1) {
      this.debug_message(a("K*4Jb\u000e~GWe\fo\u0015\u0002\u007f\rx\n\u0002(") + var1.sym + a("H~\b\u0002x\u001ck\u0013G+K") + var1.parse_state);
   }

   public Symbol debug_parse() throws Exception {
      boolean var6 = Symbol.a;
      Symbol var2 = null;
      this.production_tab = this.production_table();
      this.action_tab = this.action_table();
      this.reduce_tab = this.reduce_table();
      this.debug_message(a("K*.Lb\u001cc\u0006Nb\u0012c\tE+\u0018k\u0015Qn\u001a"));
      this.init_actions();
      this.user_init();
      this.cur_token = this.scan();
      this.debug_message(a("K*$Wy\u001ao\tV+;s\n@d\u0004*\u000eQ+K") + this.cur_token.sym);
      this.stack.removeAllElements();
      this.stack.push(new Symbol(0, this.start_state()));
      this.tos = 0;
      this._done_parsing = false;

      while(!this._done_parsing) {
         short var1 = this.get_action(((Symbol)this.stack.peek()).parse_state, this.cur_token.sym);
         if (var1 > 0) {
            this.cur_token.parse_state = var1 - 1;
            this.debug_shift(this.cur_token);
            this.stack.push(this.cur_token);
            ++this.tos;
            this.cur_token = this.scan();
            this.debug_message(a("K*$Wy\u001ao\tV+\u001ce\fGeHc\u0014\u0002") + this.cur_token);
            if (!var6) {
               continue;
            }

            SnmpException.b = !SnmpException.b;
         }

         if (var1 < 0) {
            var2 = this.do_action(-var1 - 1, this, this.stack, this.tos);
            short var4 = this.production_tab[-var1 - 1][0];
            short var3 = this.production_tab[-var1 - 1][1];
            this.debug_reduce(-var1 - 1, var4, var3);
            int var5 = 0;

            label47: {
               while(var5 < var3) {
                  this.stack.pop();
                  --this.tos;
                  ++var5;
                  if (var6) {
                     break label47;
                  }

                  if (var6) {
                     break;
                  }
               }

               var1 = this.get_reduce(((Symbol)this.stack.peek()).parse_state, var4);
               var2.parse_state = var1;
               this.stack.push(var2);
               ++this.tos;
               this.debug_message(a("K* M\u007f\u0007*\u0014Vj\u001coG\u0001") + var1);
            }

            if (!var6) {
               continue;
            }
         }

         if (var1 == 0) {
            this.syntax_error(this.cur_token);
            if (!this.error_recovery(true)) {
               this.unrecovered_syntax_error(this.cur_token);
               this.done_parsing();
               if (!var6) {
                  continue;
               }
            }

            var2 = (Symbol)this.stack.peek();
            if (var6) {
               break;
            }
         }
      }

      return var2;
   }

   protected boolean error_recovery(boolean var1) throws Exception {
      boolean var2 = Symbol.a;
      if (var1) {
         this.debug_message(a("K*&V\u007f\rg\u0017Vb\u0006mGGy\u001ae\u0015\u0002y\ri\bTn\u001as"));
      }

      if (!this.find_recovery_config(var1)) {
         if (var1) {
            this.debug_message(a("K*\"Py\u0007xGPn\u000be\u0011Gy\u0011*\u0001Cb\u0004y"));
         }

         return false;
      } else {
         this.read_lookahead();

         do {
            label52: {
               lr_parser var10000;
               String var10001;
               label58: {
                  if (var1) {
                     var10000 = this;
                     var10001 = a("K*3Pr\u0001d\u0000\u0002\u007f\u0007*\u0017Cy\u001boGCc\rk\u0003");
                     if (var2) {
                        break label58;
                     }

                     this.debug_message(var10001);
                  }

                  if (this.try_parse_ahead(var1) && !var2) {
                     break;
                  }

                  if (this.lookahead[0].sym == this.EOF_sym()) {
                     if (var1) {
                        this.debug_message(a("K*\"Py\u0007xGPn\u000be\u0011Gy\u0011*\u0001Cb\u0004yGC\u007fHO(d"));
                     }

                     return false;
                  }

                  if (!var1) {
                     break label52;
                  }

                  var10000 = this;
                  var10001 = a("K*$Me\u001b\u007f\nKe\u000f*4[f\ne\u000b\u0002(") + this.cur_err_token().sym;
               }

               var10000.debug_message(var10001);
            }

            this.restart_lookahead();
         } while(!var2);

         if (var1) {
            this.debug_message(a("K*7Cy\u001boJCc\rk\u0003\u0002d\u0003&GEd\u0001d\u0000\u0002i\ti\f\u0002\u007f\u0007*\tMy\u0005k\u000b\u0002{\tx\u0014G"));
         }

         this.parse_lookahead(var1);
         return true;
      }
   }

   protected boolean shift_under_error() {
      return this.get_action(((Symbol)this.stack.peek()).parse_state, this.error_sym()) > 0;
   }

   protected boolean find_recovery_config(boolean var1) {
      if (var1 != 0) {
         this.debug_message(a("K*!Ke\fc\tE+\u001ao\u0004M}\rx\u001e\u0002x\u001ck\u0013G+\u0007dGQ\u007f\ti\f"));
      }

      int var4 = ((Symbol)this.stack.peek()).right;
      int var5 = ((Symbol)this.stack.peek()).left;

      short var10000;
      while(true) {
         if (!this.shift_under_error()) {
            if (var1 != 0) {
               this.debug_message(a("K*7M{Hy\u0013Ch\u0003*\u0005[+\u0007d\u0002\u000e+\u001b~\u0006VnH}\u0006Q+K*") + ((Symbol)this.stack.peek()).parse_state);
            }

            var5 = ((Symbol)this.stack.pop()).left;
            --this.tos;
            if (!this.stack.empty()) {
               continue;
            }

            var10000 = var1;
            if (!Symbol.a) {
               if (var1 != 0) {
                  this.debug_message(a("K*)M+\u001ao\u0004M}\rx\u001e\u0002x\u001ck\u0013G+\u000ee\u0012LoHe\t\u0002x\u001ck\u0004I"));
               }

               return false;
            }
            break;
         }

         var10000 = this.get_action(((Symbol)this.stack.peek()).parse_state, this.error_sym());
         break;
      }

      short var3 = var10000;
      if (var1 != 0) {
         this.debug_message(a("K*5Gh\u0007|\u0002P+\u001b~\u0006VnHl\bWe\f*O\u0001") + ((Symbol)this.stack.peek()).parse_state + ")");
         this.debug_message(a("K*4Jb\u000e~\u000eLlHe\t\u0002n\u001ax\bP+\u001ceGQ\u007f\t~\u0002\u0002(") + (var3 - 1));
      }

      Symbol var2 = new Symbol(this.error_sym(), var5, var4);
      var2.parse_state = var3 - 1;
      this.stack.push(var2);
      ++this.tos;
      return true;
   }

   protected void read_lookahead() throws Exception {
      boolean var2 = Symbol.a;
      this.lookahead = new Symbol[this.error_sync_size()];
      int var1 = 0;

      while(true) {
         if (var1 < this.error_sync_size()) {
            this.lookahead[var1] = this.cur_token;
            this.cur_token = this.scan();
            ++var1;
            if (var2) {
               break;
            }

            if (!var2) {
               continue;
            }
         }

         this.lookahead_pos = 0;
         break;
      }

   }

   protected Symbol cur_err_token() {
      return this.lookahead[this.lookahead_pos];
   }

   protected boolean advance_lookahead() {
      ++this.lookahead_pos;
      return this.lookahead_pos < this.error_sync_size();
   }

   protected void restart_lookahead() throws Exception {
      boolean var2 = Symbol.a;
      int var1 = 1;

      while(true) {
         if (var1 < this.error_sync_size()) {
            this.lookahead[var1 - 1] = this.lookahead[var1];
            ++var1;
            if (var2) {
               break;
            }

            if (!var2) {
               continue;
            }
         }

         this.cur_token = this.scan();
         this.lookahead[this.error_sync_size() - 1] = this.cur_token;
         this.lookahead_pos = 0;
         break;
      }

   }

   protected boolean try_parse_ahead(boolean var1) throws Exception {
      boolean var7 = Symbol.a;
      virtual_parse_stack var5 = new virtual_parse_stack(this.stack);

      while(true) {
         label47:
         while(true) {
            short var2 = this.get_action(var5.top(), this.cur_err_token().sym);
            short var10000;
            if (var2 == 0) {
               var10000 = 0;
               if (!var7) {
                  return false;
               }
            } else {
               var10000 = var2;
            }

            do {
               if (var10000 <= 0) {
                  while(-var2 - 1 != this.start_production()) {
                     short var3 = this.production_tab[-var2 - 1][0];
                     short var4 = this.production_tab[-var2 - 1][1];
                     int var6 = 0;

                     do {
                        if (var6 >= var4) {
                           if (var1) {
                              this.debug_message(a("K*7Cy\u001boJCc\rk\u0003\u0002y\rn\u0012An\u001b0GJj\u0006n\u000bG+\u001bc\u001dG+U*") + var4 + a("Hf\u000fQ+U*D") + var3 + a("Hl\u0015MfHy\u0013C\u007f\r*D") + var5.top());
                           }

                           var5.push(this.get_reduce(var5.top(), var3));
                           break;
                        }

                        var5.pop();
                        ++var6;
                     } while(!var7 || !var7);

                     if (!var1) {
                        continue label47;
                     }

                     this.debug_message(a("K* M\u007f\u0007*\u0014Vj\u001coG\u0001") + var5.top());
                     if (!var7) {
                        continue label47;
                     }
                  }

                  if (var1) {
                     this.debug_message(a("K*7Cy\u001boJCc\rk\u0003\u0002j\u000bi\u0002R\u007f\u001b"));
                  }

                  return true;
               }

               var5.push(var2 - 1);
               if (var1) {
                  this.debug_message(a("K*7Cy\u001boJCc\rk\u0003\u0002x\u0000c\u0001VxHY\u001eOi\u0007fG\u0001") + this.cur_err_token().sym + a("Hc\tVdHy\u0013C\u007f\r*D") + (var2 - 1));
               }

               if (this.advance_lookahead()) {
                  continue label47;
               }

               var10000 = 1;
            } while(var7);

            return true;
         }
      }
   }

   protected void parse_lookahead(boolean var1) throws Exception {
      boolean var7 = Symbol.a;
      Symbol var3 = null;
      this.lookahead_pos = 0;
      if (var1) {
         this.debug_message(a("K*5G{\tx\u0014Ke\u000f*\u0014C}\rnGKe\u0018\u007f\u0013\u0002|\u0001~\u000f\u0002j\u000b~\u000eMe\u001b"));
         this.debug_message(a("K*$Wy\u001ao\tV+;s\n@d\u0004*\u000eQ+K") + this.cur_err_token().sym);
         this.debug_message(a("K*$Wy\u001ao\tV+\u001b~\u0006VnHc\u0014\u0002(") + ((Symbol)this.stack.peek()).parse_state);
      }

      short var2;
      do {
         while(true) {
            while(true) {
               if (this._done_parsing) {
                  return;
               }

               var2 = this.get_action(((Symbol)this.stack.peek()).parse_state, this.cur_err_token().sym);
               if (var2 <= 0) {
                  break;
               }

               this.cur_err_token().parse_state = var2 - 1;
               if (var1) {
                  this.debug_shift(this.cur_err_token());
               }

               this.stack.push(this.cur_err_token());
               ++this.tos;
               if (!this.advance_lookahead()) {
                  if (var1) {
                     this.debug_message(a("K*$Mf\u0018f\u0002Vn\f*\u0015G{\tx\u0014G"));
                  }

                  this.cur_token = this.scan();
                  return;
               }

               if (var1) {
                  this.debug_message(a("K*$Wy\u001ao\tV+;s\n@d\u0004*\u000eQ+K") + this.cur_err_token().sym);
                  if (var7) {
                     break;
                  }
               }
            }

            if (var2 >= 0) {
               break;
            }

            var3 = this.do_action(-var2 - 1, this, this.stack, this.tos);
            short var5 = this.production_tab[-var2 - 1][0];
            short var4 = this.production_tab[-var2 - 1][1];
            if (var1) {
               this.debug_reduce(-var2 - 1, var5, var4);
            }

            int var6 = 0;

            while(true) {
               if (var6 < var4) {
                  this.stack.pop();
                  --this.tos;
                  ++var6;
                  if (!var7 || !var7) {
                     continue;
                  }
                  break;
               }

               var2 = this.get_reduce(((Symbol)this.stack.peek()).parse_state, var5);
               var3.parse_state = var2;
               this.stack.push(var3);
               ++this.tos;
               break;
            }

            if (var1) {
               this.debug_message(a("K* M\u007f\u0007*\u0014Vj\u001coG\u0001") + var2);
               if (var7) {
                  break;
               }
            }
         }
      } while(var2 != 0);

      this.report_fatal_error(a(";s\tVj\u0010*\u0002Py\u0007x"), var3);
   }

   protected static short[][] unpackFromStrings(String[] var0) {
      boolean var8 = Symbol.a;
      StringBuffer var1 = new StringBuffer(var0[0]);
      int var2 = 1;

      while(true) {
         if (var2 < var0.length) {
            var1.append(var0[var2]);
            ++var2;
            if (!var8 || !var8) {
               continue;
            }
            break;
         }

         var2 = 0;
         break;
      }

      int var3 = var1.charAt(var2) << 16 | var1.charAt(var2 + 1);
      var2 += 2;
      short[][] var4 = new short[var3][];
      int var5 = 0;

      short[][] var10000;
      label36:
      while(true) {
         if (var5 >= var3) {
            var10000 = var4;
            break;
         }

         int var6 = var1.charAt(var2) << 16 | var1.charAt(var2 + 1);
         var2 += 2;
         var10000 = var4;
         if (var8) {
            break;
         }

         var4[var5] = new short[var6];
         int var7 = 0;

         while(var7 < var6) {
            var4[var5][var7] = (short)(var1.charAt(var2++) - 2);
            ++var7;
            if (var8) {
               continue label36;
            }

            if (var8) {
               break;
            }
         }

         ++var5;
         if (var8) {
         }
      }

      return var10000;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 104;
               break;
            case 1:
               var10003 = 10;
               break;
            case 2:
               var10003 = 103;
               break;
            case 3:
               var10003 = 34;
               break;
            default:
               var10003 = 11;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
