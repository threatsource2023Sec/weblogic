package kodo.kernel.jdoql;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;

class FilterTokenizer {
   public static final int TT_EOF = -1;
   public static final int TT_EOL = 10;
   public static final int TT_NUMBER = -2;
   public static final int TT_WORD = -3;
   private static final int TT_NOTHING = -4;
   private static final int NEED_CHAR = Integer.MAX_VALUE;
   private static final int SKIP_LF = 2147483646;
   private static final byte CT_WHITESPACE = 1;
   private static final byte CT_DIGIT = 2;
   private static final byte CT_ALPHA = 4;
   private static final byte CT_QUOTE = 8;
   private static final byte CT_COMMENT = 16;
   private static final byte NTYPE_UNKNOWN = 0;
   private static final byte NTYPE_INT = 1;
   private static final byte NTYPE_LONG = 2;
   private static final byte NTYPE_FLOAT = 3;
   private static final byte NTYPE_DOUBLE = 4;
   public int ttype = -4;
   public String sval;
   public Number nval;
   private Reader reader = null;
   private char[] buf = new char[20];
   private int peekc = Integer.MAX_VALUE;
   private byte[] ctype = new byte[256];
   private LinkedList nextWords = null;
   private boolean cached = false;
   private int pushedBackType = -4;
   private String pushedBackSval = null;

   public FilterTokenizer(Reader r) {
      this.reader = r;
      this.wordChars(97, 122);
      this.wordChars(65, 90);
      this.wordChars(160, 255);
      this.wordChars(95, 95);
      this.wordChars(58, 58);
      this.whitespaceChars(0, 32);
      this.quoteChar(34);
      this.quoteChar(39);
      this.ordinaryChar(47);
      this.ordinaryChar(37);
      this.ordinaryChar(36);
      this.ordinaryChar(44);
      this.parseNumbers();
   }

   public int nextToken() throws IOException {
      if (this.nextWords != null && !this.nextWords.isEmpty()) {
         this.sval = (String)this.nextWords.removeFirst();
         if (".".equals(this.sval)) {
            this.ttype = 46;
         } else {
            this.ttype = -3;
         }

         this.cached = true;
         return this.ttype;
      } else {
         this.cached = false;
         if (this.pushedBackType != -4) {
            this.ttype = this.pushedBackType;
            this.sval = this.pushedBackSval;
            this.pushedBackType = -4;
            this.pushedBackSval = null;
            return this.ttype;
         } else {
            byte[] ct = this.ctype;
            this.sval = null;
            int c = this.peekc;
            if (c < 0) {
               c = Integer.MAX_VALUE;
            }

            if (c == 2147483646) {
               c = this.reader.read();
               if (c < 0) {
                  return this.ttype = -1;
               }

               if (c == 10) {
                  c = Integer.MAX_VALUE;
               }
            }

            if (c == Integer.MAX_VALUE) {
               c = this.reader.read();
               if (c < 0) {
                  return this.ttype = -1;
               }
            }

            this.ttype = c;
            this.peekc = Integer.MAX_VALUE;

            byte ctype;
            for(ctype = c < 256 ? ct[c] : 4; (ctype & 1) != 0; ctype = c < 256 ? ct[c] : 4) {
               if (c == 13) {
                  c = this.reader.read();
                  if (c == 10) {
                     c = this.reader.read();
                  }
               } else {
                  c = this.reader.read();
               }

               if (c < 0) {
                  return this.ttype = -1;
               }
            }

            if ((ctype & 2) != 0) {
               boolean neg = false;
               int seendot = false;
               if (c != 45) {
                  if (c == 46) {
                     c = this.reader.read();
                     if (c < 48 || c > 57) {
                        this.peekc = c;
                        return this.ttype = 46;
                     }

                     seendot = true;
                  }
               } else {
                  c = this.reader.read();
                  if (c != 46 && (c < 48 || c > 57)) {
                     this.peekc = c;
                     return this.ttype = 45;
                  }

                  neg = true;
               }

               StringBuffer pre = null;
               StringBuffer post = null;
               int ntype = 0;

               while(true) {
                  if (c == 46 && !seendot) {
                     seendot = true;
                  } else {
                     if (48 > c || c > 57) {
                        switch (c) {
                           case 68:
                           case 100:
                              ntype = 4;
                              c = this.reader.read();
                              break;
                           case 70:
                           case 102:
                              ntype = 3;
                              c = this.reader.read();
                              break;
                           case 76:
                           case 108:
                              ntype = 2;
                              c = this.reader.read();
                        }

                        this.peekc = c;
                        if (ntype == 0) {
                           if (!seendot) {
                              if (pre.length() < 10) {
                                 ntype = 1;
                              } else if (pre.length() == 10) {
                                 if (pre.charAt(0) >= '2') {
                                    ntype = 2;
                                 } else {
                                    ntype = 1;
                                 }
                              } else {
                                 ntype = 2;
                              }
                           } else {
                              ntype = 4;
                           }
                        }

                        if (seendot && post != null) {
                           if (pre == null) {
                              pre = new StringBuffer(post.length() + 3);
                              if (neg) {
                                 pre.append('-');
                              }

                              pre.append(0);
                           } else if (neg) {
                              pre.insert(0, '-');
                           }

                           pre.append('.').append(post);
                        } else if (neg) {
                           pre.insert(0, '-');
                        }

                        switch (ntype) {
                           case 1:
                              this.nval = new Integer(pre.toString());
                              break;
                           case 2:
                              this.nval = new Long(pre.toString());
                              break;
                           case 3:
                              this.nval = new Float(pre.toString());
                              break;
                           case 4:
                              this.nval = new Double(pre.toString());
                        }

                        return this.ttype = -2;
                     }

                     if (!seendot) {
                        if (pre == null) {
                           pre = new StringBuffer(9);
                        }

                        pre.append((char)c);
                     } else {
                        if (post == null) {
                           post = new StringBuffer(5);
                        }

                        post.append((char)c);
                     }
                  }

                  c = this.reader.read();
               }
            } else {
               int i;
               if ((ctype & 4) != 0) {
                  i = 0;

                  do {
                     this.addCharToBuf(c, i++);
                     c = this.reader.read();
                     ctype = c < 0 ? 1 : (c < 256 ? ct[c] : 4);
                  } while((ctype & 6) != 0);

                  this.peekc = c;
                  this.sval = String.copyValueOf(this.buf, 0, i);
                  return this.ttype = -3;
               } else if ((ctype & 8) != 0) {
                  this.ttype = c;
                  i = 0;

                  int d;
                  for(d = this.reader.read(); d >= 0 && d != this.ttype && d != 10 && d != 13; this.addCharToBuf(c, i++)) {
                     if (d == 92) {
                        c = this.reader.read();
                        int first = c;
                        if (c >= 48 && c <= 55) {
                           c -= 48;
                           int c2 = this.reader.read();
                           if (48 <= c2 && c2 <= 55) {
                              c = (c << 3) + (c2 - 48);
                              c2 = this.reader.read();
                              if (48 <= c2 && c2 <= 55 && first <= 51) {
                                 c = (c << 3) + (c2 - 48);
                                 d = this.reader.read();
                              } else {
                                 d = c2;
                              }
                           } else {
                              d = c2;
                           }
                        } else {
                           switch (c) {
                              case 97:
                                 c = 7;
                                 break;
                              case 98:
                                 c = 8;
                              case 99:
                              case 100:
                              case 101:
                              case 103:
                              case 104:
                              case 105:
                              case 106:
                              case 107:
                              case 108:
                              case 109:
                              case 111:
                              case 112:
                              case 113:
                              case 115:
                              case 117:
                              default:
                                 break;
                              case 102:
                                 c = 12;
                                 break;
                              case 110:
                                 c = 10;
                                 break;
                              case 114:
                                 c = 13;
                                 break;
                              case 116:
                                 c = 9;
                                 break;
                              case 118:
                                 c = 11;
                           }

                           d = this.reader.read();
                        }
                     } else {
                        c = d;
                        d = this.reader.read();
                     }
                  }

                  this.peekc = d == this.ttype ? Integer.MAX_VALUE : d;
                  this.sval = String.copyValueOf(this.buf, 0, i);
                  return this.ttype;
               } else if ((ctype & 16) == 0) {
                  return this.ttype = c;
               } else {
                  while((c = this.reader.read()) != 10 && c != 13 && c >= 0) {
                  }

                  this.peekc = c;
                  return this.nextToken();
               }
            }
         }
      }
   }

   public void pushBack() {
      if (this.cached) {
         this.nextWords.addFirst(this.sval);
      } else if (this.ttype != -4) {
         this.pushedBackType = this.ttype;
         this.pushedBackSval = this.sval;
      }

   }

   public void lookaheadWord(String s) {
      if (this.nextWords == null) {
         this.nextWords = new LinkedList();
      }

      this.nextWords.add(s);
   }

   private void wordChars(int low, int hi) {
      if (low < 0) {
         low = 0;
      }

      if (hi >= this.ctype.length) {
         hi = this.ctype.length - 1;
      }

      while(low <= hi) {
         byte[] var10000 = this.ctype;
         int var10001 = low++;
         var10000[var10001] = (byte)(var10000[var10001] | 4);
      }

   }

   private void whitespaceChars(int low, int hi) {
      if (low < 0) {
         low = 0;
      }

      if (hi >= this.ctype.length) {
         hi = this.ctype.length - 1;
      }

      while(low <= hi) {
         byte[] var10000 = this.ctype;
         int var10001 = low++;
         var10000[var10001] = (byte)(var10000[var10001] | 1);
      }

   }

   private void ordinaryChar(int ch) {
      if (ch >= 0 && ch < this.ctype.length) {
         this.ctype[ch] = 0;
      }

   }

   private void quoteChar(int ch) {
      if (ch >= 0 && ch < this.ctype.length) {
         this.ctype[ch] = 8;
      }

   }

   private void parseNumbers() {
      byte[] var10000;
      for(int i = 48; i <= 57; ++i) {
         var10000 = this.ctype;
         var10000[i] = (byte)(var10000[i] | 2);
      }

      var10000 = this.ctype;
      var10000[46] = (byte)(var10000[46] | 2);
      var10000 = this.ctype;
      var10000[45] = (byte)(var10000[45] | 2);
   }

   public String toString() {
      String ret;
      switch (this.ttype) {
         case -4:
            ret = "NOTHING";
            break;
         case -3:
            ret = this.sval;
            break;
         case -2:
            ret = "n=" + this.nval;
            break;
         case -1:
            ret = "EOF";
            break;
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         default:
            if (this.ttype < 256 && (this.ctype[this.ttype] & 8) != 0) {
               ret = this.sval;
            } else {
               char[] s = new char[3];
               s[0] = s[2] = '\'';
               s[1] = (char)this.ttype;
               ret = new String(s);
            }
            break;
         case 10:
            ret = "EOL";
      }

      return "Token[" + ret + "]";
   }

   private void addCharToBuf(int c, int pos) {
      if (pos >= this.buf.length) {
         char[] nb = new char[this.buf.length * 2];
         System.arraycopy(this.buf, 0, nb, 0, this.buf.length);
         this.buf = nb;
      }

      this.buf[pos] = (char)c;
   }
}
