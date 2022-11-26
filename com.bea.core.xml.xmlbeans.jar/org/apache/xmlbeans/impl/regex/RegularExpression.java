package org.apache.xmlbeans.impl.regex;

import java.io.Serializable;
import java.text.CharacterIterator;

public class RegularExpression implements Serializable {
   static final boolean DEBUG = false;
   String regex;
   int options;
   int nofparen;
   Token tokentree;
   boolean hasBackReferences = false;
   transient int minlength;
   transient Op operations = null;
   transient int numberOfClosures;
   transient Context context = null;
   transient RangeToken firstChar = null;
   transient String fixedString = null;
   transient int fixedStringOptions;
   transient BMPattern fixedStringTable = null;
   transient boolean fixedStringOnly = false;
   static final int IGNORE_CASE = 2;
   static final int SINGLE_LINE = 4;
   static final int MULTIPLE_LINES = 8;
   static final int EXTENDED_COMMENT = 16;
   static final int USE_UNICODE_CATEGORY = 32;
   static final int UNICODE_WORD_BOUNDARY = 64;
   static final int PROHIBIT_HEAD_CHARACTER_OPTIMIZATION = 128;
   static final int PROHIBIT_FIXED_STRING_OPTIMIZATION = 256;
   static final int XMLSCHEMA_MODE = 512;
   static final int SPECIAL_COMMA = 1024;
   private static final int WT_IGNORE = 0;
   private static final int WT_LETTER = 1;
   private static final int WT_OTHER = 2;
   static final int LINE_FEED = 10;
   static final int CARRIAGE_RETURN = 13;
   static final int LINE_SEPARATOR = 8232;
   static final int PARAGRAPH_SEPARATOR = 8233;

   private synchronized void compile(Token tok) {
      if (this.operations == null) {
         this.numberOfClosures = 0;
         this.operations = this.compile(tok, (Op)null, false);
      }
   }

   private Op compile(Token tok, Op next, boolean reverse) {
      Object ret;
      switch (tok.type) {
         case 0:
            ret = Op.createChar(tok.getChar());
            ((Op)ret).next = next;
            break;
         case 1:
            ret = next;
            int i;
            if (!reverse) {
               for(i = tok.size() - 1; i >= 0; --i) {
                  ret = this.compile(tok.getChild(i), (Op)ret, false);
               }

               return (Op)ret;
            } else {
               for(i = 0; i < tok.size(); ++i) {
                  ret = this.compile(tok.getChild(i), (Op)ret, true);
               }

               return (Op)ret;
            }
         case 2:
            Op.UnionOp uni = Op.createUnion(tok.size());

            for(int i = 0; i < tok.size(); ++i) {
               uni.addElement(this.compile(tok.getChild(i), next, reverse));
            }

            ret = uni;
            break;
         case 3:
         case 9:
            Token child = tok.getChild(0);
            int min = tok.getMin();
            int max = tok.getMax();
            int i;
            if (min >= 0 && min == max) {
               ret = next;

               for(i = 0; i < min; ++i) {
                  ret = this.compile(child, (Op)ret, reverse);
               }

               return (Op)ret;
            } else {
               if (min > 0 && max > 0) {
                  max -= min;
               }

               if (max > 0) {
                  ret = next;

                  for(i = 0; i < max; ++i) {
                     Op.ChildOp q = Op.createQuestion(tok.type == 9);
                     q.next = next;
                     q.setChild(this.compile(child, (Op)ret, reverse));
                     ret = q;
                  }
               } else {
                  Op.ChildOp op;
                  if (tok.type == 9) {
                     op = Op.createNonGreedyClosure();
                  } else if (child.getMinLength() == 0) {
                     op = Op.createClosure(this.numberOfClosures++);
                  } else {
                     op = Op.createClosure(-1);
                  }

                  op.next = next;
                  op.setChild(this.compile(child, op, reverse));
                  ret = op;
               }

               if (min > 0) {
                  for(i = 0; i < min; ++i) {
                     ret = this.compile(child, (Op)ret, reverse);
                  }
               }
               break;
            }
         case 4:
         case 5:
            ret = Op.createRange(tok);
            ((Op)ret).next = next;
            break;
         case 6:
            if (tok.getParenNumber() == 0) {
               ret = this.compile(tok.getChild(0), next, reverse);
            } else {
               Op.CharOp next;
               if (reverse) {
                  next = Op.createCapture(tok.getParenNumber(), next);
                  next = this.compile(tok.getChild(0), next, reverse);
                  ret = Op.createCapture(-tok.getParenNumber(), next);
               } else {
                  next = Op.createCapture(-tok.getParenNumber(), next);
                  next = this.compile(tok.getChild(0), next, reverse);
                  ret = Op.createCapture(tok.getParenNumber(), next);
               }
            }
            break;
         case 7:
            ret = next;
            break;
         case 8:
            ret = Op.createAnchor(tok.getChar());
            ((Op)ret).next = next;
            break;
         case 10:
            ret = Op.createString(tok.getString());
            ((Op)ret).next = next;
            break;
         case 11:
            ret = Op.createDot();
            ((Op)ret).next = next;
            break;
         case 12:
            ret = Op.createBackReference(tok.getReferenceNumber());
            ((Op)ret).next = next;
            break;
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         default:
            throw new RuntimeException("Unknown token type: " + tok.type);
         case 20:
            ret = Op.createLook(20, next, this.compile(tok.getChild(0), (Op)null, false));
            break;
         case 21:
            ret = Op.createLook(21, next, this.compile(tok.getChild(0), (Op)null, false));
            break;
         case 22:
            ret = Op.createLook(22, next, this.compile(tok.getChild(0), (Op)null, true));
            break;
         case 23:
            ret = Op.createLook(23, next, this.compile(tok.getChild(0), (Op)null, true));
            break;
         case 24:
            ret = Op.createIndependent(next, this.compile(tok.getChild(0), (Op)null, reverse));
            break;
         case 25:
            ret = Op.createModifier(next, this.compile(tok.getChild(0), (Op)null, reverse), ((Token.ModifierToken)tok).getOptions(), ((Token.ModifierToken)tok).getOptionsMask());
            break;
         case 26:
            Token.ConditionToken ctok = (Token.ConditionToken)tok;
            int ref = ctok.refNumber;
            Op condition = ctok.condition == null ? null : this.compile(ctok.condition, (Op)null, reverse);
            Op yes = this.compile(ctok.yes, next, reverse);
            Op no = ctok.no == null ? null : this.compile(ctok.no, next, reverse);
            ret = Op.createCondition(next, ref, condition, yes, no);
      }

      return (Op)ret;
   }

   public boolean matches(char[] target) {
      return this.matches((char[])target, 0, target.length, (Match)null);
   }

   public boolean matches(char[] target, int start, int end) {
      return this.matches(target, start, end, (Match)null);
   }

   public boolean matches(char[] target, Match match) {
      return this.matches((char[])target, 0, target.length, match);
   }

   public boolean matches(char[] target, int start, int end, Match match) {
      synchronized(this) {
         if (this.operations == null) {
            this.prepare();
         }

         if (this.context == null) {
            this.context = new Context();
         }
      }

      Context con = null;
      synchronized(this.context) {
         con = this.context.inuse ? new Context() : this.context;
         con.reset(target, start, end, this.numberOfClosures);
      }

      if (match != null) {
         match.setNumberOfGroups(this.nofparen);
         match.setSource(target);
      } else if (this.hasBackReferences) {
         match = new Match();
         match.setNumberOfGroups(this.nofparen);
      }

      con.match = match;
      int limit;
      if (isSet(this.options, 512)) {
         limit = this.matchCharArray(con, this.operations, con.start, 1, this.options);
         if (limit == con.limit) {
            if (con.match != null) {
               con.match.setBeginning(0, con.start);
               con.match.setEnd(0, limit);
            }

            con.inuse = false;
            return true;
         } else {
            return false;
         }
      } else if (this.fixedStringOnly) {
         limit = this.fixedStringTable.matches(target, con.start, con.limit);
         if (limit >= 0) {
            if (con.match != null) {
               con.match.setBeginning(0, limit);
               con.match.setEnd(0, limit + this.fixedString.length());
            }

            con.inuse = false;
            return true;
         } else {
            con.inuse = false;
            return false;
         }
      } else {
         if (this.fixedString != null) {
            limit = this.fixedStringTable.matches(target, con.start, con.limit);
            if (limit < 0) {
               con.inuse = false;
               return false;
            }
         }

         limit = con.limit - this.minlength;
         int matchEnd = -1;
         int matchStart;
         if (this.operations != null && this.operations.type == 7 && this.operations.getChild().type == 0) {
            if (isSet(this.options, 4)) {
               matchStart = con.start;
               matchEnd = this.matchCharArray(con, this.operations, con.start, 1, this.options);
            } else {
               boolean previousIsEOL = true;

               for(matchStart = con.start; matchStart <= limit; ++matchStart) {
                  int ch = target[matchStart];
                  if (isEOLChar(ch)) {
                     previousIsEOL = true;
                  } else {
                     if (previousIsEOL && 0 <= (matchEnd = this.matchCharArray(con, this.operations, matchStart, 1, this.options))) {
                        break;
                     }

                     previousIsEOL = false;
                  }
               }
            }
         } else if (this.firstChar != null) {
            RangeToken range = this.firstChar;
            int ch;
            if (isSet(this.options, 2)) {
               range = this.firstChar.getCaseInsensitiveToken();

               for(matchStart = con.start; matchStart <= limit; ++matchStart) {
                  ch = target[matchStart];
                  if (REUtil.isHighSurrogate(ch) && matchStart + 1 < con.limit) {
                     ch = REUtil.composeFromSurrogates(ch, target[matchStart + 1]);
                     if (!range.match(ch)) {
                        continue;
                     }
                  } else if (!range.match(ch)) {
                     char ch1 = Character.toUpperCase((char)ch);
                     if (!range.match(ch1) && !range.match(Character.toLowerCase(ch1))) {
                        continue;
                     }
                  }

                  if (0 <= (matchEnd = this.matchCharArray(con, this.operations, matchStart, 1, this.options))) {
                     break;
                  }
               }
            } else {
               for(matchStart = con.start; matchStart <= limit; ++matchStart) {
                  ch = target[matchStart];
                  if (REUtil.isHighSurrogate(ch) && matchStart + 1 < con.limit) {
                     ch = REUtil.composeFromSurrogates(ch, target[matchStart + 1]);
                  }

                  if (range.match(ch) && 0 <= (matchEnd = this.matchCharArray(con, this.operations, matchStart, 1, this.options))) {
                     break;
                  }
               }
            }
         } else {
            for(matchStart = con.start; matchStart <= limit && 0 > (matchEnd = this.matchCharArray(con, this.operations, matchStart, 1, this.options)); ++matchStart) {
            }
         }

         if (matchEnd >= 0) {
            if (con.match != null) {
               con.match.setBeginning(0, matchStart);
               con.match.setEnd(0, matchEnd);
            }

            con.inuse = false;
            return true;
         } else {
            con.inuse = false;
            return false;
         }
      }
   }

   private int matchCharArray(Context con, Op op, int offset, int dx, int opts) {
      char[] target = con.charTarget;

      while(op != null) {
         if (offset > con.limit || offset < con.start) {
            return -1;
         }

         int ch;
         int ch;
         int o2;
         int literallen;
         switch (op.type) {
            case 0:
               if (dx > 0) {
                  if (offset >= con.limit) {
                     return -1;
                  }

                  ch = target[offset];
                  if (isSet(opts, 4)) {
                     if (REUtil.isHighSurrogate(ch) && offset + 1 < con.limit) {
                        ++offset;
                     }
                  } else {
                     if (REUtil.isHighSurrogate(ch) && offset + 1 < con.limit) {
                        ++offset;
                        ch = REUtil.composeFromSurrogates(ch, target[offset]);
                     }

                     if (isEOLChar(ch)) {
                        return -1;
                     }
                  }

                  ++offset;
               } else {
                  ch = offset - 1;
                  if (ch >= con.limit || ch < 0) {
                     return -1;
                  }

                  ch = target[ch];
                  if (isSet(opts, 4)) {
                     if (REUtil.isLowSurrogate(ch) && ch - 1 >= 0) {
                        --ch;
                     }
                  } else {
                     if (REUtil.isLowSurrogate(ch) && ch - 1 >= 0) {
                        --ch;
                        ch = REUtil.composeFromSurrogates(target[ch], ch);
                     }

                     if (!isEOLChar(ch)) {
                        return -1;
                     }
                  }

                  offset = ch;
               }

               op = op.next;
               break;
            case 1:
               if (isSet(opts, 2)) {
                  ch = op.getData();
                  if (dx > 0) {
                     if (offset >= con.limit || !matchIgnoreCase(ch, target[offset])) {
                        return -1;
                     }

                     ++offset;
                  } else {
                     ch = offset - 1;
                     if (ch >= con.limit || ch < 0 || !matchIgnoreCase(ch, target[ch])) {
                        return -1;
                     }

                     offset = ch;
                  }
               } else {
                  ch = op.getData();
                  if (dx > 0) {
                     if (offset >= con.limit || ch != target[offset]) {
                        return -1;
                     }

                     ++offset;
                  } else {
                     ch = offset - 1;
                     if (ch >= con.limit || ch < 0 || ch != target[ch]) {
                        return -1;
                     }

                     offset = ch;
                  }
               }

               op = op.next;
               break;
            case 2:
            case 12:
            case 13:
            case 14:
            case 17:
            case 18:
            case 19:
            default:
               throw new RuntimeException("Unknown operation type: " + op.type);
            case 3:
            case 4:
               if (dx > 0) {
                  if (offset >= con.limit) {
                     return -1;
                  }

                  ch = target[offset];
                  if (REUtil.isHighSurrogate(ch) && offset + 1 < con.limit) {
                     ++offset;
                     ch = REUtil.composeFromSurrogates(ch, target[offset]);
                  }

                  RangeToken tok = op.getToken();
                  if (isSet(opts, 2)) {
                     tok = tok.getCaseInsensitiveToken();
                     if (!tok.match(ch)) {
                        if (ch >= 65536) {
                           return -1;
                        }

                        char uch;
                        if (!tok.match(uch = Character.toUpperCase((char)ch)) && !tok.match(Character.toLowerCase(uch))) {
                           return -1;
                        }
                     }
                  } else if (!tok.match(ch)) {
                     return -1;
                  }

                  ++offset;
               } else {
                  ch = offset - 1;
                  if (ch >= con.limit || ch < 0) {
                     return -1;
                  }

                  ch = target[ch];
                  if (REUtil.isLowSurrogate(ch) && ch - 1 >= 0) {
                     --ch;
                     ch = REUtil.composeFromSurrogates(target[ch], ch);
                  }

                  RangeToken tok = op.getToken();
                  if (isSet(opts, 2)) {
                     tok = tok.getCaseInsensitiveToken();
                     if (!tok.match(ch)) {
                        if (ch >= 65536) {
                           return -1;
                        }

                        char uch;
                        if (!tok.match(uch = Character.toUpperCase((char)ch)) && !tok.match(Character.toLowerCase(uch))) {
                           return -1;
                        }
                     }
                  } else if (!tok.match(ch)) {
                     return -1;
                  }

                  offset = ch;
               }

               op = op.next;
               break;
            case 5:
               boolean go = false;
               switch (op.getData()) {
                  case 36:
                     if (isSet(opts, 8)) {
                        if (offset == con.limit || offset < con.limit && isEOLChar(target[offset])) {
                           break;
                        }

                        return -1;
                     } else {
                        if (offset == con.limit || offset + 1 == con.limit && isEOLChar(target[offset]) || offset + 2 == con.limit && target[offset] == '\r' && target[offset + 1] == '\n') {
                           break;
                        }

                        return -1;
                     }
                  case 60:
                     if (con.length != 0 && offset != con.limit) {
                        if (getWordType(target, con.start, con.limit, offset, opts) == 1 && getPreviousWordType(target, con.start, con.limit, offset, opts) == 2) {
                           break;
                        }

                        return -1;
                     }

                     return -1;
                  case 62:
                     if (con.length != 0 && offset != con.start) {
                        if (getWordType(target, con.start, con.limit, offset, opts) == 2 && getPreviousWordType(target, con.start, con.limit, offset, opts) == 1) {
                           break;
                        }

                        return -1;
                     }

                     return -1;
                  case 64:
                     if (offset == con.start || offset > con.start && isEOLChar(target[offset - 1])) {
                        break;
                     }

                     return -1;
                  case 65:
                     if (offset != con.start) {
                        return -1;
                     }
                     break;
                  case 66:
                     if (con.length == 0) {
                        go = true;
                     } else {
                        ch = getWordType(target, con.start, con.limit, offset, opts);
                        go = ch == 0 || ch == getPreviousWordType(target, con.start, con.limit, offset, opts);
                     }

                     if (!go) {
                        return -1;
                     }
                     break;
                  case 90:
                     if (offset == con.limit || offset + 1 == con.limit && isEOLChar(target[offset]) || offset + 2 == con.limit && target[offset] == '\r' && target[offset + 1] == '\n') {
                        break;
                     }

                     return -1;
                  case 94:
                     if (isSet(opts, 8)) {
                        if (offset == con.start || offset > con.start && isEOLChar(target[offset - 1])) {
                           break;
                        }

                        return -1;
                     }

                     if (offset != con.start) {
                        return -1;
                     }
                     break;
                  case 98:
                     if (con.length == 0) {
                        return -1;
                     }

                     ch = getWordType(target, con.start, con.limit, offset, opts);
                     if (ch == 0) {
                        return -1;
                     }

                     o2 = getPreviousWordType(target, con.start, con.limit, offset, opts);
                     if (ch == o2) {
                        return -1;
                     }
                     break;
                  case 122:
                     if (offset != con.limit) {
                        return -1;
                     }
               }

               op = op.next;
               break;
            case 6:
               String literal = op.getString();
               o2 = literal.length();
               if (!isSet(opts, 2)) {
                  if (dx > 0) {
                     if (!regionMatches(target, offset, con.limit, literal, o2)) {
                        return -1;
                     }

                     offset += o2;
                  } else {
                     if (!regionMatches(target, offset - o2, con.limit, literal, o2)) {
                        return -1;
                     }

                     offset -= o2;
                  }
               } else if (dx > 0) {
                  if (!regionMatchesIgnoreCase(target, offset, con.limit, literal, o2)) {
                     return -1;
                  }

                  offset += o2;
               } else {
                  if (!regionMatchesIgnoreCase(target, offset - o2, con.limit, literal, o2)) {
                     return -1;
                  }

                  offset -= o2;
               }

               op = op.next;
               break;
            case 7:
               ch = op.getData();
               if (ch >= 0) {
                  o2 = con.offsets[ch];
                  if (o2 >= 0 && o2 == offset) {
                     con.offsets[ch] = -1;
                     op = op.next;
                     break;
                  }

                  con.offsets[ch] = offset;
               }

               o2 = this.matchCharArray(con, op.getChild(), offset, dx, opts);
               if (ch >= 0) {
                  con.offsets[ch] = -1;
               }

               if (o2 >= 0) {
                  return o2;
               }

               op = op.next;
               break;
            case 8:
            case 10:
               ch = this.matchCharArray(con, op.next, offset, dx, opts);
               if (ch >= 0) {
                  return ch;
               }

               op = op.getChild();
               break;
            case 9:
               ch = this.matchCharArray(con, op.getChild(), offset, dx, opts);
               if (ch >= 0) {
                  return ch;
               }

               op = op.next;
               break;
            case 11:
               for(ch = 0; ch < op.size(); ++ch) {
                  o2 = this.matchCharArray(con, op.elementAt(ch), offset, dx, opts);
                  if (o2 >= 0) {
                     return o2;
                  }
               }

               return -1;
            case 15:
               ch = op.getData();
               if (con.match != null && ch > 0) {
                  o2 = con.match.getBeginning(ch);
                  con.match.setBeginning(ch, offset);
                  literallen = this.matchCharArray(con, op.next, offset, dx, opts);
                  if (literallen < 0) {
                     con.match.setBeginning(ch, o2);
                  }

                  return literallen;
               }

               if (con.match != null && ch < 0) {
                  o2 = -ch;
                  literallen = con.match.getEnd(o2);
                  con.match.setEnd(o2, offset);
                  int ret = this.matchCharArray(con, op.next, offset, dx, opts);
                  if (ret < 0) {
                     con.match.setEnd(o2, literallen);
                  }

                  return ret;
               }

               op = op.next;
               break;
            case 16:
               ch = op.getData();
               if (ch > 0 && ch < this.nofparen) {
                  if (con.match.getBeginning(ch) >= 0 && con.match.getEnd(ch) >= 0) {
                     o2 = con.match.getBeginning(ch);
                     literallen = con.match.getEnd(ch) - o2;
                     if (!isSet(opts, 2)) {
                        if (dx > 0) {
                           if (!regionMatches(target, offset, con.limit, o2, literallen)) {
                              return -1;
                           }

                           offset += literallen;
                        } else {
                           if (!regionMatches(target, offset - literallen, con.limit, o2, literallen)) {
                              return -1;
                           }

                           offset -= literallen;
                        }
                     } else if (dx > 0) {
                        if (!regionMatchesIgnoreCase(target, offset, con.limit, o2, literallen)) {
                           return -1;
                        }

                        offset += literallen;
                     } else {
                        if (!regionMatchesIgnoreCase(target, offset - literallen, con.limit, o2, literallen)) {
                           return -1;
                        }

                        offset -= literallen;
                     }

                     op = op.next;
                     break;
                  }

                  return -1;
               }

               throw new RuntimeException("Internal Error: Reference number must be more than zero: " + ch);
            case 20:
               if (0 > this.matchCharArray(con, op.getChild(), offset, 1, opts)) {
                  return -1;
               }

               op = op.next;
               break;
            case 21:
               if (0 <= this.matchCharArray(con, op.getChild(), offset, 1, opts)) {
                  return -1;
               }

               op = op.next;
               break;
            case 22:
               if (0 > this.matchCharArray(con, op.getChild(), offset, -1, opts)) {
                  return -1;
               }

               op = op.next;
               break;
            case 23:
               if (0 <= this.matchCharArray(con, op.getChild(), offset, -1, opts)) {
                  return -1;
               }

               op = op.next;
               break;
            case 24:
               o2 = this.matchCharArray(con, op.getChild(), offset, dx, opts);
               if (o2 < 0) {
                  return o2;
               }

               offset = o2;
               op = op.next;
               break;
            case 25:
               o2 = opts | op.getData();
               o2 &= ~op.getData2();
               literallen = this.matchCharArray(con, op.getChild(), offset, dx, o2);
               if (literallen < 0) {
                  return literallen;
               }

               offset = literallen;
               op = op.next;
               break;
            case 26:
               Op.ConditionOp cop = (Op.ConditionOp)op;
               boolean matchp = false;
               if (cop.refNumber > 0) {
                  if (cop.refNumber >= this.nofparen) {
                     throw new RuntimeException("Internal Error: Reference number must be more than zero: " + cop.refNumber);
                  }

                  matchp = con.match.getBeginning(cop.refNumber) >= 0 && con.match.getEnd(cop.refNumber) >= 0;
               } else {
                  matchp = 0 <= this.matchCharArray(con, cop.condition, offset, dx, opts);
               }

               if (matchp) {
                  op = cop.yes;
               } else if (cop.no != null) {
                  op = cop.no;
               } else {
                  op = cop.next;
               }
         }
      }

      return isSet(opts, 512) && offset != con.limit ? -1 : offset;
   }

   private static final int getPreviousWordType(char[] target, int begin, int end, int offset, int opts) {
      --offset;

      int ret;
      for(ret = getWordType(target, begin, end, offset, opts); ret == 0; ret = getWordType(target, begin, end, offset, opts)) {
         --offset;
      }

      return ret;
   }

   private static final int getWordType(char[] target, int begin, int end, int offset, int opts) {
      return offset >= begin && offset < end ? getWordType0(target[offset], opts) : 2;
   }

   private static final boolean regionMatches(char[] target, int offset, int limit, String part, int partlen) {
      if (offset < 0) {
         return false;
      } else if (limit - offset < partlen) {
         return false;
      } else {
         int i = 0;

         do {
            if (partlen-- <= 0) {
               return true;
            }
         } while(target[offset++] == part.charAt(i++));

         return false;
      }
   }

   private static final boolean regionMatches(char[] target, int offset, int limit, int offset2, int partlen) {
      if (offset < 0) {
         return false;
      } else if (limit - offset < partlen) {
         return false;
      } else {
         int i = offset2;

         do {
            if (partlen-- <= 0) {
               return true;
            }
         } while(target[offset++] == target[i++]);

         return false;
      }
   }

   private static final boolean regionMatchesIgnoreCase(char[] target, int offset, int limit, String part, int partlen) {
      if (offset < 0) {
         return false;
      } else if (limit - offset < partlen) {
         return false;
      } else {
         int i = 0;

         while(partlen-- > 0) {
            char ch1 = target[offset++];
            char ch2 = part.charAt(i++);
            if (ch1 != ch2) {
               char uch1 = Character.toUpperCase(ch1);
               char uch2 = Character.toUpperCase(ch2);
               if (uch1 != uch2 && Character.toLowerCase(uch1) != Character.toLowerCase(uch2)) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   private static final boolean regionMatchesIgnoreCase(char[] target, int offset, int limit, int offset2, int partlen) {
      if (offset < 0) {
         return false;
      } else if (limit - offset < partlen) {
         return false;
      } else {
         int i = offset2;

         while(partlen-- > 0) {
            char ch1 = target[offset++];
            char ch2 = target[i++];
            if (ch1 != ch2) {
               char uch1 = Character.toUpperCase(ch1);
               char uch2 = Character.toUpperCase(ch2);
               if (uch1 != uch2 && Character.toLowerCase(uch1) != Character.toLowerCase(uch2)) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public boolean matches(String target) {
      return this.matches((String)target, 0, target.length(), (Match)null);
   }

   public boolean matches(String target, int start, int end) {
      return this.matches(target, start, end, (Match)null);
   }

   public boolean matches(String target, Match match) {
      return this.matches((String)target, 0, target.length(), match);
   }

   public boolean matches(String target, int start, int end, Match match) {
      synchronized(this) {
         if (this.operations == null) {
            this.prepare();
         }

         if (this.context == null) {
            this.context = new Context();
         }
      }

      Context con = null;
      synchronized(this.context) {
         con = this.context.inuse ? new Context() : this.context;
         con.reset(target, start, end, this.numberOfClosures);
      }

      if (match != null) {
         match.setNumberOfGroups(this.nofparen);
         match.setSource(target);
      } else if (this.hasBackReferences) {
         match = new Match();
         match.setNumberOfGroups(this.nofparen);
      }

      con.match = match;
      int limit;
      if (isSet(this.options, 512)) {
         limit = this.matchString(con, this.operations, con.start, 1, this.options);
         if (limit == con.limit) {
            if (con.match != null) {
               con.match.setBeginning(0, con.start);
               con.match.setEnd(0, limit);
            }

            con.inuse = false;
            return true;
         } else {
            return false;
         }
      } else if (this.fixedStringOnly) {
         limit = this.fixedStringTable.matches(target, con.start, con.limit);
         if (limit >= 0) {
            if (con.match != null) {
               con.match.setBeginning(0, limit);
               con.match.setEnd(0, limit + this.fixedString.length());
            }

            con.inuse = false;
            return true;
         } else {
            con.inuse = false;
            return false;
         }
      } else {
         if (this.fixedString != null) {
            limit = this.fixedStringTable.matches(target, con.start, con.limit);
            if (limit < 0) {
               con.inuse = false;
               return false;
            }
         }

         limit = con.limit - this.minlength;
         int matchEnd = -1;
         int matchStart;
         if (this.operations != null && this.operations.type == 7 && this.operations.getChild().type == 0) {
            if (isSet(this.options, 4)) {
               matchStart = con.start;
               matchEnd = this.matchString(con, this.operations, con.start, 1, this.options);
            } else {
               boolean previousIsEOL = true;

               for(matchStart = con.start; matchStart <= limit; ++matchStart) {
                  int ch = target.charAt(matchStart);
                  if (isEOLChar(ch)) {
                     previousIsEOL = true;
                  } else {
                     if (previousIsEOL && 0 <= (matchEnd = this.matchString(con, this.operations, matchStart, 1, this.options))) {
                        break;
                     }

                     previousIsEOL = false;
                  }
               }
            }
         } else if (this.firstChar != null) {
            RangeToken range = this.firstChar;
            int ch;
            if (isSet(this.options, 2)) {
               range = this.firstChar.getCaseInsensitiveToken();

               for(matchStart = con.start; matchStart <= limit; ++matchStart) {
                  ch = target.charAt(matchStart);
                  if (REUtil.isHighSurrogate(ch) && matchStart + 1 < con.limit) {
                     ch = REUtil.composeFromSurrogates(ch, target.charAt(matchStart + 1));
                     if (!range.match(ch)) {
                        continue;
                     }
                  } else if (!range.match(ch)) {
                     char ch1 = Character.toUpperCase((char)ch);
                     if (!range.match(ch1) && !range.match(Character.toLowerCase(ch1))) {
                        continue;
                     }
                  }

                  if (0 <= (matchEnd = this.matchString(con, this.operations, matchStart, 1, this.options))) {
                     break;
                  }
               }
            } else {
               for(matchStart = con.start; matchStart <= limit; ++matchStart) {
                  ch = target.charAt(matchStart);
                  if (REUtil.isHighSurrogate(ch) && matchStart + 1 < con.limit) {
                     ch = REUtil.composeFromSurrogates(ch, target.charAt(matchStart + 1));
                  }

                  if (range.match(ch) && 0 <= (matchEnd = this.matchString(con, this.operations, matchStart, 1, this.options))) {
                     break;
                  }
               }
            }
         } else {
            for(matchStart = con.start; matchStart <= limit && 0 > (matchEnd = this.matchString(con, this.operations, matchStart, 1, this.options)); ++matchStart) {
            }
         }

         if (matchEnd >= 0) {
            if (con.match != null) {
               con.match.setBeginning(0, matchStart);
               con.match.setEnd(0, matchEnd);
            }

            con.inuse = false;
            return true;
         } else {
            con.inuse = false;
            return false;
         }
      }
   }

   private int matchString(Context con, Op op, int offset, int dx, int opts) {
      String target = con.strTarget;

      while(op != null) {
         if (offset > con.limit || offset < con.start) {
            return -1;
         }

         int ch;
         int ch;
         int o2;
         int literallen;
         switch (op.type) {
            case 0:
               if (dx > 0) {
                  if (offset >= con.limit) {
                     return -1;
                  }

                  ch = target.charAt(offset);
                  if (isSet(opts, 4)) {
                     if (REUtil.isHighSurrogate(ch) && offset + 1 < con.limit) {
                        ++offset;
                     }
                  } else {
                     if (REUtil.isHighSurrogate(ch) && offset + 1 < con.limit) {
                        ++offset;
                        ch = REUtil.composeFromSurrogates(ch, target.charAt(offset));
                     }

                     if (isEOLChar(ch)) {
                        return -1;
                     }
                  }

                  ++offset;
               } else {
                  ch = offset - 1;
                  if (ch >= con.limit || ch < 0) {
                     return -1;
                  }

                  ch = target.charAt(ch);
                  if (isSet(opts, 4)) {
                     if (REUtil.isLowSurrogate(ch) && ch - 1 >= 0) {
                        --ch;
                     }
                  } else {
                     if (REUtil.isLowSurrogate(ch) && ch - 1 >= 0) {
                        --ch;
                        ch = REUtil.composeFromSurrogates(target.charAt(ch), ch);
                     }

                     if (!isEOLChar(ch)) {
                        return -1;
                     }
                  }

                  offset = ch;
               }

               op = op.next;
               break;
            case 1:
               if (isSet(opts, 2)) {
                  ch = op.getData();
                  if (dx > 0) {
                     if (offset >= con.limit || !matchIgnoreCase(ch, target.charAt(offset))) {
                        return -1;
                     }

                     ++offset;
                  } else {
                     ch = offset - 1;
                     if (ch >= con.limit || ch < 0 || !matchIgnoreCase(ch, target.charAt(ch))) {
                        return -1;
                     }

                     offset = ch;
                  }
               } else {
                  ch = op.getData();
                  if (dx > 0) {
                     if (offset >= con.limit || ch != target.charAt(offset)) {
                        return -1;
                     }

                     ++offset;
                  } else {
                     ch = offset - 1;
                     if (ch >= con.limit || ch < 0 || ch != target.charAt(ch)) {
                        return -1;
                     }

                     offset = ch;
                  }
               }

               op = op.next;
               break;
            case 2:
            case 12:
            case 13:
            case 14:
            case 17:
            case 18:
            case 19:
            default:
               throw new RuntimeException("Unknown operation type: " + op.type);
            case 3:
            case 4:
               if (dx > 0) {
                  if (offset >= con.limit) {
                     return -1;
                  }

                  ch = target.charAt(offset);
                  if (REUtil.isHighSurrogate(ch) && offset + 1 < con.limit) {
                     ++offset;
                     ch = REUtil.composeFromSurrogates(ch, target.charAt(offset));
                  }

                  RangeToken tok = op.getToken();
                  if (isSet(opts, 2)) {
                     tok = tok.getCaseInsensitiveToken();
                     if (!tok.match(ch)) {
                        if (ch >= 65536) {
                           return -1;
                        }

                        char uch;
                        if (!tok.match(uch = Character.toUpperCase((char)ch)) && !tok.match(Character.toLowerCase(uch))) {
                           return -1;
                        }
                     }
                  } else if (!tok.match(ch)) {
                     return -1;
                  }

                  ++offset;
               } else {
                  ch = offset - 1;
                  if (ch >= con.limit || ch < 0) {
                     return -1;
                  }

                  ch = target.charAt(ch);
                  if (REUtil.isLowSurrogate(ch) && ch - 1 >= 0) {
                     --ch;
                     ch = REUtil.composeFromSurrogates(target.charAt(ch), ch);
                  }

                  RangeToken tok = op.getToken();
                  if (isSet(opts, 2)) {
                     tok = tok.getCaseInsensitiveToken();
                     if (!tok.match(ch)) {
                        if (ch >= 65536) {
                           return -1;
                        }

                        char uch;
                        if (!tok.match(uch = Character.toUpperCase((char)ch)) && !tok.match(Character.toLowerCase(uch))) {
                           return -1;
                        }
                     }
                  } else if (!tok.match(ch)) {
                     return -1;
                  }

                  offset = ch;
               }

               op = op.next;
               break;
            case 5:
               boolean go = false;
               switch (op.getData()) {
                  case 36:
                     if (isSet(opts, 8)) {
                        if (offset == con.limit || offset < con.limit && isEOLChar(target.charAt(offset))) {
                           break;
                        }

                        return -1;
                     } else {
                        if (offset == con.limit || offset + 1 == con.limit && isEOLChar(target.charAt(offset)) || offset + 2 == con.limit && target.charAt(offset) == '\r' && target.charAt(offset + 1) == '\n') {
                           break;
                        }

                        return -1;
                     }
                  case 60:
                     if (con.length != 0 && offset != con.limit) {
                        if (getWordType(target, con.start, con.limit, offset, opts) == 1 && getPreviousWordType(target, con.start, con.limit, offset, opts) == 2) {
                           break;
                        }

                        return -1;
                     }

                     return -1;
                  case 62:
                     if (con.length != 0 && offset != con.start) {
                        if (getWordType(target, con.start, con.limit, offset, opts) == 2 && getPreviousWordType(target, con.start, con.limit, offset, opts) == 1) {
                           break;
                        }

                        return -1;
                     }

                     return -1;
                  case 64:
                     if (offset == con.start || offset > con.start && isEOLChar(target.charAt(offset - 1))) {
                        break;
                     }

                     return -1;
                  case 65:
                     if (offset != con.start) {
                        return -1;
                     }
                     break;
                  case 66:
                     if (con.length == 0) {
                        go = true;
                     } else {
                        ch = getWordType(target, con.start, con.limit, offset, opts);
                        go = ch == 0 || ch == getPreviousWordType(target, con.start, con.limit, offset, opts);
                     }

                     if (!go) {
                        return -1;
                     }
                     break;
                  case 90:
                     if (offset == con.limit || offset + 1 == con.limit && isEOLChar(target.charAt(offset)) || offset + 2 == con.limit && target.charAt(offset) == '\r' && target.charAt(offset + 1) == '\n') {
                        break;
                     }

                     return -1;
                  case 94:
                     if (isSet(opts, 8)) {
                        if (offset == con.start || offset > con.start && isEOLChar(target.charAt(offset - 1))) {
                           break;
                        }

                        return -1;
                     }

                     if (offset != con.start) {
                        return -1;
                     }
                     break;
                  case 98:
                     if (con.length == 0) {
                        return -1;
                     }

                     ch = getWordType(target, con.start, con.limit, offset, opts);
                     if (ch == 0) {
                        return -1;
                     }

                     o2 = getPreviousWordType(target, con.start, con.limit, offset, opts);
                     if (ch == o2) {
                        return -1;
                     }
                     break;
                  case 122:
                     if (offset != con.limit) {
                        return -1;
                     }
               }

               op = op.next;
               break;
            case 6:
               String literal = op.getString();
               o2 = literal.length();
               if (!isSet(opts, 2)) {
                  if (dx > 0) {
                     if (!regionMatches(target, offset, con.limit, literal, o2)) {
                        return -1;
                     }

                     offset += o2;
                  } else {
                     if (!regionMatches(target, offset - o2, con.limit, literal, o2)) {
                        return -1;
                     }

                     offset -= o2;
                  }
               } else if (dx > 0) {
                  if (!regionMatchesIgnoreCase(target, offset, con.limit, literal, o2)) {
                     return -1;
                  }

                  offset += o2;
               } else {
                  if (!regionMatchesIgnoreCase(target, offset - o2, con.limit, literal, o2)) {
                     return -1;
                  }

                  offset -= o2;
               }

               op = op.next;
               break;
            case 7:
               ch = op.getData();
               if (ch >= 0) {
                  o2 = con.offsets[ch];
                  if (o2 >= 0 && o2 == offset) {
                     con.offsets[ch] = -1;
                     op = op.next;
                     break;
                  }

                  con.offsets[ch] = offset;
               }

               o2 = this.matchString(con, op.getChild(), offset, dx, opts);
               if (ch >= 0) {
                  con.offsets[ch] = -1;
               }

               if (o2 >= 0) {
                  return o2;
               }

               op = op.next;
               break;
            case 8:
            case 10:
               ch = this.matchString(con, op.next, offset, dx, opts);
               if (ch >= 0) {
                  return ch;
               }

               op = op.getChild();
               break;
            case 9:
               ch = this.matchString(con, op.getChild(), offset, dx, opts);
               if (ch >= 0) {
                  return ch;
               }

               op = op.next;
               break;
            case 11:
               for(ch = 0; ch < op.size(); ++ch) {
                  o2 = this.matchString(con, op.elementAt(ch), offset, dx, opts);
                  if (o2 >= 0) {
                     return o2;
                  }
               }

               return -1;
            case 15:
               ch = op.getData();
               if (con.match != null && ch > 0) {
                  o2 = con.match.getBeginning(ch);
                  con.match.setBeginning(ch, offset);
                  literallen = this.matchString(con, op.next, offset, dx, opts);
                  if (literallen < 0) {
                     con.match.setBeginning(ch, o2);
                  }

                  return literallen;
               }

               if (con.match != null && ch < 0) {
                  o2 = -ch;
                  literallen = con.match.getEnd(o2);
                  con.match.setEnd(o2, offset);
                  int ret = this.matchString(con, op.next, offset, dx, opts);
                  if (ret < 0) {
                     con.match.setEnd(o2, literallen);
                  }

                  return ret;
               }

               op = op.next;
               break;
            case 16:
               ch = op.getData();
               if (ch > 0 && ch < this.nofparen) {
                  if (con.match.getBeginning(ch) >= 0 && con.match.getEnd(ch) >= 0) {
                     o2 = con.match.getBeginning(ch);
                     literallen = con.match.getEnd(ch) - o2;
                     if (!isSet(opts, 2)) {
                        if (dx > 0) {
                           if (!regionMatches(target, offset, con.limit, o2, literallen)) {
                              return -1;
                           }

                           offset += literallen;
                        } else {
                           if (!regionMatches(target, offset - literallen, con.limit, o2, literallen)) {
                              return -1;
                           }

                           offset -= literallen;
                        }
                     } else if (dx > 0) {
                        if (!regionMatchesIgnoreCase(target, offset, con.limit, o2, literallen)) {
                           return -1;
                        }

                        offset += literallen;
                     } else {
                        if (!regionMatchesIgnoreCase(target, offset - literallen, con.limit, o2, literallen)) {
                           return -1;
                        }

                        offset -= literallen;
                     }

                     op = op.next;
                     break;
                  }

                  return -1;
               }

               throw new RuntimeException("Internal Error: Reference number must be more than zero: " + ch);
            case 20:
               if (0 > this.matchString(con, op.getChild(), offset, 1, opts)) {
                  return -1;
               }

               op = op.next;
               break;
            case 21:
               if (0 <= this.matchString(con, op.getChild(), offset, 1, opts)) {
                  return -1;
               }

               op = op.next;
               break;
            case 22:
               if (0 > this.matchString(con, op.getChild(), offset, -1, opts)) {
                  return -1;
               }

               op = op.next;
               break;
            case 23:
               if (0 <= this.matchString(con, op.getChild(), offset, -1, opts)) {
                  return -1;
               }

               op = op.next;
               break;
            case 24:
               o2 = this.matchString(con, op.getChild(), offset, dx, opts);
               if (o2 < 0) {
                  return o2;
               }

               offset = o2;
               op = op.next;
               break;
            case 25:
               o2 = opts | op.getData();
               o2 &= ~op.getData2();
               literallen = this.matchString(con, op.getChild(), offset, dx, o2);
               if (literallen < 0) {
                  return literallen;
               }

               offset = literallen;
               op = op.next;
               break;
            case 26:
               Op.ConditionOp cop = (Op.ConditionOp)op;
               boolean matchp = false;
               if (cop.refNumber > 0) {
                  if (cop.refNumber >= this.nofparen) {
                     throw new RuntimeException("Internal Error: Reference number must be more than zero: " + cop.refNumber);
                  }

                  matchp = con.match.getBeginning(cop.refNumber) >= 0 && con.match.getEnd(cop.refNumber) >= 0;
               } else {
                  matchp = 0 <= this.matchString(con, cop.condition, offset, dx, opts);
               }

               if (matchp) {
                  op = cop.yes;
               } else if (cop.no != null) {
                  op = cop.no;
               } else {
                  op = cop.next;
               }
         }
      }

      return isSet(opts, 512) && offset != con.limit ? -1 : offset;
   }

   private static final int getPreviousWordType(String target, int begin, int end, int offset, int opts) {
      --offset;

      int ret;
      for(ret = getWordType(target, begin, end, offset, opts); ret == 0; ret = getWordType(target, begin, end, offset, opts)) {
         --offset;
      }

      return ret;
   }

   private static final int getWordType(String target, int begin, int end, int offset, int opts) {
      return offset >= begin && offset < end ? getWordType0(target.charAt(offset), opts) : 2;
   }

   private static final boolean regionMatches(String text, int offset, int limit, String part, int partlen) {
      return limit - offset < partlen ? false : text.regionMatches(offset, part, 0, partlen);
   }

   private static final boolean regionMatches(String text, int offset, int limit, int offset2, int partlen) {
      return limit - offset < partlen ? false : text.regionMatches(offset, text, offset2, partlen);
   }

   private static final boolean regionMatchesIgnoreCase(String text, int offset, int limit, String part, int partlen) {
      return text.regionMatches(true, offset, part, 0, partlen);
   }

   private static final boolean regionMatchesIgnoreCase(String text, int offset, int limit, int offset2, int partlen) {
      return limit - offset < partlen ? false : text.regionMatches(true, offset, text, offset2, partlen);
   }

   public boolean matches(CharacterIterator target) {
      return this.matches(target, (Match)null);
   }

   public boolean matches(CharacterIterator target, Match match) {
      int start = target.getBeginIndex();
      int end = target.getEndIndex();
      synchronized(this) {
         if (this.operations == null) {
            this.prepare();
         }

         if (this.context == null) {
            this.context = new Context();
         }
      }

      Context con = null;
      synchronized(this.context) {
         con = this.context.inuse ? new Context() : this.context;
         con.reset(target, start, end, this.numberOfClosures);
      }

      if (match != null) {
         match.setNumberOfGroups(this.nofparen);
         match.setSource(target);
      } else if (this.hasBackReferences) {
         match = new Match();
         match.setNumberOfGroups(this.nofparen);
      }

      con.match = match;
      int limit;
      if (isSet(this.options, 512)) {
         limit = this.matchCharacterIterator(con, this.operations, con.start, 1, this.options);
         if (limit == con.limit) {
            if (con.match != null) {
               con.match.setBeginning(0, con.start);
               con.match.setEnd(0, limit);
            }

            con.inuse = false;
            return true;
         } else {
            return false;
         }
      } else if (this.fixedStringOnly) {
         limit = this.fixedStringTable.matches(target, con.start, con.limit);
         if (limit >= 0) {
            if (con.match != null) {
               con.match.setBeginning(0, limit);
               con.match.setEnd(0, limit + this.fixedString.length());
            }

            con.inuse = false;
            return true;
         } else {
            con.inuse = false;
            return false;
         }
      } else {
         if (this.fixedString != null) {
            limit = this.fixedStringTable.matches(target, con.start, con.limit);
            if (limit < 0) {
               con.inuse = false;
               return false;
            }
         }

         limit = con.limit - this.minlength;
         int matchEnd = -1;
         int matchStart;
         if (this.operations != null && this.operations.type == 7 && this.operations.getChild().type == 0) {
            if (isSet(this.options, 4)) {
               matchStart = con.start;
               matchEnd = this.matchCharacterIterator(con, this.operations, con.start, 1, this.options);
            } else {
               boolean previousIsEOL = true;

               for(matchStart = con.start; matchStart <= limit; ++matchStart) {
                  int ch = target.setIndex(matchStart);
                  if (isEOLChar(ch)) {
                     previousIsEOL = true;
                  } else {
                     if (previousIsEOL && 0 <= (matchEnd = this.matchCharacterIterator(con, this.operations, matchStart, 1, this.options))) {
                        break;
                     }

                     previousIsEOL = false;
                  }
               }
            }
         } else if (this.firstChar != null) {
            RangeToken range = this.firstChar;
            int ch;
            if (isSet(this.options, 2)) {
               range = this.firstChar.getCaseInsensitiveToken();

               for(matchStart = con.start; matchStart <= limit; ++matchStart) {
                  ch = target.setIndex(matchStart);
                  if (REUtil.isHighSurrogate(ch) && matchStart + 1 < con.limit) {
                     ch = REUtil.composeFromSurrogates(ch, target.setIndex(matchStart + 1));
                     if (!range.match(ch)) {
                        continue;
                     }
                  } else if (!range.match(ch)) {
                     char ch1 = Character.toUpperCase((char)ch);
                     if (!range.match(ch1) && !range.match(Character.toLowerCase(ch1))) {
                        continue;
                     }
                  }

                  if (0 <= (matchEnd = this.matchCharacterIterator(con, this.operations, matchStart, 1, this.options))) {
                     break;
                  }
               }
            } else {
               for(matchStart = con.start; matchStart <= limit; ++matchStart) {
                  ch = target.setIndex(matchStart);
                  if (REUtil.isHighSurrogate(ch) && matchStart + 1 < con.limit) {
                     ch = REUtil.composeFromSurrogates(ch, target.setIndex(matchStart + 1));
                  }

                  if (range.match(ch) && 0 <= (matchEnd = this.matchCharacterIterator(con, this.operations, matchStart, 1, this.options))) {
                     break;
                  }
               }
            }
         } else {
            for(matchStart = con.start; matchStart <= limit && 0 > (matchEnd = this.matchCharacterIterator(con, this.operations, matchStart, 1, this.options)); ++matchStart) {
            }
         }

         if (matchEnd >= 0) {
            if (con.match != null) {
               con.match.setBeginning(0, matchStart);
               con.match.setEnd(0, matchEnd);
            }

            con.inuse = false;
            return true;
         } else {
            con.inuse = false;
            return false;
         }
      }
   }

   private int matchCharacterIterator(Context con, Op op, int offset, int dx, int opts) {
      CharacterIterator target = con.ciTarget;

      while(op != null) {
         if (offset > con.limit || offset < con.start) {
            return -1;
         }

         int ch;
         int ch;
         int o2;
         int literallen;
         switch (op.type) {
            case 0:
               if (dx > 0) {
                  if (offset >= con.limit) {
                     return -1;
                  }

                  ch = target.setIndex(offset);
                  if (isSet(opts, 4)) {
                     if (REUtil.isHighSurrogate(ch) && offset + 1 < con.limit) {
                        ++offset;
                     }
                  } else {
                     if (REUtil.isHighSurrogate(ch) && offset + 1 < con.limit) {
                        ++offset;
                        ch = REUtil.composeFromSurrogates(ch, target.setIndex(offset));
                     }

                     if (isEOLChar(ch)) {
                        return -1;
                     }
                  }

                  ++offset;
               } else {
                  ch = offset - 1;
                  if (ch >= con.limit || ch < 0) {
                     return -1;
                  }

                  ch = target.setIndex(ch);
                  if (isSet(opts, 4)) {
                     if (REUtil.isLowSurrogate(ch) && ch - 1 >= 0) {
                        --ch;
                     }
                  } else {
                     if (REUtil.isLowSurrogate(ch) && ch - 1 >= 0) {
                        --ch;
                        ch = REUtil.composeFromSurrogates(target.setIndex(ch), ch);
                     }

                     if (!isEOLChar(ch)) {
                        return -1;
                     }
                  }

                  offset = ch;
               }

               op = op.next;
               break;
            case 1:
               if (isSet(opts, 2)) {
                  ch = op.getData();
                  if (dx > 0) {
                     if (offset >= con.limit || !matchIgnoreCase(ch, target.setIndex(offset))) {
                        return -1;
                     }

                     ++offset;
                  } else {
                     ch = offset - 1;
                     if (ch >= con.limit || ch < 0 || !matchIgnoreCase(ch, target.setIndex(ch))) {
                        return -1;
                     }

                     offset = ch;
                  }
               } else {
                  ch = op.getData();
                  if (dx > 0) {
                     if (offset >= con.limit || ch != target.setIndex(offset)) {
                        return -1;
                     }

                     ++offset;
                  } else {
                     ch = offset - 1;
                     if (ch >= con.limit || ch < 0 || ch != target.setIndex(ch)) {
                        return -1;
                     }

                     offset = ch;
                  }
               }

               op = op.next;
               break;
            case 2:
            case 12:
            case 13:
            case 14:
            case 17:
            case 18:
            case 19:
            default:
               throw new RuntimeException("Unknown operation type: " + op.type);
            case 3:
            case 4:
               if (dx > 0) {
                  if (offset >= con.limit) {
                     return -1;
                  }

                  ch = target.setIndex(offset);
                  if (REUtil.isHighSurrogate(ch) && offset + 1 < con.limit) {
                     ++offset;
                     ch = REUtil.composeFromSurrogates(ch, target.setIndex(offset));
                  }

                  RangeToken tok = op.getToken();
                  if (isSet(opts, 2)) {
                     tok = tok.getCaseInsensitiveToken();
                     if (!tok.match(ch)) {
                        if (ch >= 65536) {
                           return -1;
                        }

                        char uch;
                        if (!tok.match(uch = Character.toUpperCase((char)ch)) && !tok.match(Character.toLowerCase(uch))) {
                           return -1;
                        }
                     }
                  } else if (!tok.match(ch)) {
                     return -1;
                  }

                  ++offset;
               } else {
                  ch = offset - 1;
                  if (ch >= con.limit || ch < 0) {
                     return -1;
                  }

                  ch = target.setIndex(ch);
                  if (REUtil.isLowSurrogate(ch) && ch - 1 >= 0) {
                     --ch;
                     ch = REUtil.composeFromSurrogates(target.setIndex(ch), ch);
                  }

                  RangeToken tok = op.getToken();
                  if (isSet(opts, 2)) {
                     tok = tok.getCaseInsensitiveToken();
                     if (!tok.match(ch)) {
                        if (ch >= 65536) {
                           return -1;
                        }

                        char uch;
                        if (!tok.match(uch = Character.toUpperCase((char)ch)) && !tok.match(Character.toLowerCase(uch))) {
                           return -1;
                        }
                     }
                  } else if (!tok.match(ch)) {
                     return -1;
                  }

                  offset = ch;
               }

               op = op.next;
               break;
            case 5:
               boolean go = false;
               switch (op.getData()) {
                  case 36:
                     if (isSet(opts, 8)) {
                        if (offset == con.limit || offset < con.limit && isEOLChar(target.setIndex(offset))) {
                           break;
                        }

                        return -1;
                     } else {
                        if (offset == con.limit || offset + 1 == con.limit && isEOLChar(target.setIndex(offset)) || offset + 2 == con.limit && target.setIndex(offset) == '\r' && target.setIndex(offset + 1) == '\n') {
                           break;
                        }

                        return -1;
                     }
                  case 60:
                     if (con.length != 0 && offset != con.limit) {
                        if (getWordType(target, con.start, con.limit, offset, opts) == 1 && getPreviousWordType(target, con.start, con.limit, offset, opts) == 2) {
                           break;
                        }

                        return -1;
                     }

                     return -1;
                  case 62:
                     if (con.length != 0 && offset != con.start) {
                        if (getWordType(target, con.start, con.limit, offset, opts) == 2 && getPreviousWordType(target, con.start, con.limit, offset, opts) == 1) {
                           break;
                        }

                        return -1;
                     }

                     return -1;
                  case 64:
                     if (offset == con.start || offset > con.start && isEOLChar(target.setIndex(offset - 1))) {
                        break;
                     }

                     return -1;
                  case 65:
                     if (offset != con.start) {
                        return -1;
                     }
                     break;
                  case 66:
                     if (con.length == 0) {
                        go = true;
                     } else {
                        ch = getWordType(target, con.start, con.limit, offset, opts);
                        go = ch == 0 || ch == getPreviousWordType(target, con.start, con.limit, offset, opts);
                     }

                     if (!go) {
                        return -1;
                     }
                     break;
                  case 90:
                     if (offset == con.limit || offset + 1 == con.limit && isEOLChar(target.setIndex(offset)) || offset + 2 == con.limit && target.setIndex(offset) == '\r' && target.setIndex(offset + 1) == '\n') {
                        break;
                     }

                     return -1;
                  case 94:
                     if (isSet(opts, 8)) {
                        if (offset == con.start || offset > con.start && isEOLChar(target.setIndex(offset - 1))) {
                           break;
                        }

                        return -1;
                     }

                     if (offset != con.start) {
                        return -1;
                     }
                     break;
                  case 98:
                     if (con.length == 0) {
                        return -1;
                     }

                     ch = getWordType(target, con.start, con.limit, offset, opts);
                     if (ch == 0) {
                        return -1;
                     }

                     o2 = getPreviousWordType(target, con.start, con.limit, offset, opts);
                     if (ch == o2) {
                        return -1;
                     }
                     break;
                  case 122:
                     if (offset != con.limit) {
                        return -1;
                     }
               }

               op = op.next;
               break;
            case 6:
               String literal = op.getString();
               o2 = literal.length();
               if (!isSet(opts, 2)) {
                  if (dx > 0) {
                     if (!regionMatches(target, offset, con.limit, literal, o2)) {
                        return -1;
                     }

                     offset += o2;
                  } else {
                     if (!regionMatches(target, offset - o2, con.limit, literal, o2)) {
                        return -1;
                     }

                     offset -= o2;
                  }
               } else if (dx > 0) {
                  if (!regionMatchesIgnoreCase(target, offset, con.limit, literal, o2)) {
                     return -1;
                  }

                  offset += o2;
               } else {
                  if (!regionMatchesIgnoreCase(target, offset - o2, con.limit, literal, o2)) {
                     return -1;
                  }

                  offset -= o2;
               }

               op = op.next;
               break;
            case 7:
               ch = op.getData();
               if (ch >= 0) {
                  o2 = con.offsets[ch];
                  if (o2 >= 0 && o2 == offset) {
                     con.offsets[ch] = -1;
                     op = op.next;
                     break;
                  }

                  con.offsets[ch] = offset;
               }

               o2 = this.matchCharacterIterator(con, op.getChild(), offset, dx, opts);
               if (ch >= 0) {
                  con.offsets[ch] = -1;
               }

               if (o2 >= 0) {
                  return o2;
               }

               op = op.next;
               break;
            case 8:
            case 10:
               ch = this.matchCharacterIterator(con, op.next, offset, dx, opts);
               if (ch >= 0) {
                  return ch;
               }

               op = op.getChild();
               break;
            case 9:
               ch = this.matchCharacterIterator(con, op.getChild(), offset, dx, opts);
               if (ch >= 0) {
                  return ch;
               }

               op = op.next;
               break;
            case 11:
               for(ch = 0; ch < op.size(); ++ch) {
                  o2 = this.matchCharacterIterator(con, op.elementAt(ch), offset, dx, opts);
                  if (o2 >= 0) {
                     return o2;
                  }
               }

               return -1;
            case 15:
               ch = op.getData();
               if (con.match != null && ch > 0) {
                  o2 = con.match.getBeginning(ch);
                  con.match.setBeginning(ch, offset);
                  literallen = this.matchCharacterIterator(con, op.next, offset, dx, opts);
                  if (literallen < 0) {
                     con.match.setBeginning(ch, o2);
                  }

                  return literallen;
               }

               if (con.match != null && ch < 0) {
                  o2 = -ch;
                  literallen = con.match.getEnd(o2);
                  con.match.setEnd(o2, offset);
                  int ret = this.matchCharacterIterator(con, op.next, offset, dx, opts);
                  if (ret < 0) {
                     con.match.setEnd(o2, literallen);
                  }

                  return ret;
               }

               op = op.next;
               break;
            case 16:
               ch = op.getData();
               if (ch > 0 && ch < this.nofparen) {
                  if (con.match.getBeginning(ch) >= 0 && con.match.getEnd(ch) >= 0) {
                     o2 = con.match.getBeginning(ch);
                     literallen = con.match.getEnd(ch) - o2;
                     if (!isSet(opts, 2)) {
                        if (dx > 0) {
                           if (!regionMatches(target, offset, con.limit, o2, literallen)) {
                              return -1;
                           }

                           offset += literallen;
                        } else {
                           if (!regionMatches(target, offset - literallen, con.limit, o2, literallen)) {
                              return -1;
                           }

                           offset -= literallen;
                        }
                     } else if (dx > 0) {
                        if (!regionMatchesIgnoreCase(target, offset, con.limit, o2, literallen)) {
                           return -1;
                        }

                        offset += literallen;
                     } else {
                        if (!regionMatchesIgnoreCase(target, offset - literallen, con.limit, o2, literallen)) {
                           return -1;
                        }

                        offset -= literallen;
                     }

                     op = op.next;
                     break;
                  }

                  return -1;
               }

               throw new RuntimeException("Internal Error: Reference number must be more than zero: " + ch);
            case 20:
               if (0 > this.matchCharacterIterator(con, op.getChild(), offset, 1, opts)) {
                  return -1;
               }

               op = op.next;
               break;
            case 21:
               if (0 <= this.matchCharacterIterator(con, op.getChild(), offset, 1, opts)) {
                  return -1;
               }

               op = op.next;
               break;
            case 22:
               if (0 > this.matchCharacterIterator(con, op.getChild(), offset, -1, opts)) {
                  return -1;
               }

               op = op.next;
               break;
            case 23:
               if (0 <= this.matchCharacterIterator(con, op.getChild(), offset, -1, opts)) {
                  return -1;
               }

               op = op.next;
               break;
            case 24:
               o2 = this.matchCharacterIterator(con, op.getChild(), offset, dx, opts);
               if (o2 < 0) {
                  return o2;
               }

               offset = o2;
               op = op.next;
               break;
            case 25:
               o2 = opts | op.getData();
               o2 &= ~op.getData2();
               literallen = this.matchCharacterIterator(con, op.getChild(), offset, dx, o2);
               if (literallen < 0) {
                  return literallen;
               }

               offset = literallen;
               op = op.next;
               break;
            case 26:
               Op.ConditionOp cop = (Op.ConditionOp)op;
               boolean matchp = false;
               if (cop.refNumber > 0) {
                  if (cop.refNumber >= this.nofparen) {
                     throw new RuntimeException("Internal Error: Reference number must be more than zero: " + cop.refNumber);
                  }

                  matchp = con.match.getBeginning(cop.refNumber) >= 0 && con.match.getEnd(cop.refNumber) >= 0;
               } else {
                  matchp = 0 <= this.matchCharacterIterator(con, cop.condition, offset, dx, opts);
               }

               if (matchp) {
                  op = cop.yes;
               } else if (cop.no != null) {
                  op = cop.no;
               } else {
                  op = cop.next;
               }
         }
      }

      return isSet(opts, 512) && offset != con.limit ? -1 : offset;
   }

   private static final int getPreviousWordType(CharacterIterator target, int begin, int end, int offset, int opts) {
      --offset;

      int ret;
      for(ret = getWordType(target, begin, end, offset, opts); ret == 0; ret = getWordType(target, begin, end, offset, opts)) {
         --offset;
      }

      return ret;
   }

   private static final int getWordType(CharacterIterator target, int begin, int end, int offset, int opts) {
      return offset >= begin && offset < end ? getWordType0(target.setIndex(offset), opts) : 2;
   }

   private static final boolean regionMatches(CharacterIterator target, int offset, int limit, String part, int partlen) {
      if (offset < 0) {
         return false;
      } else if (limit - offset < partlen) {
         return false;
      } else {
         int i = 0;

         do {
            if (partlen-- <= 0) {
               return true;
            }
         } while(target.setIndex(offset++) == part.charAt(i++));

         return false;
      }
   }

   private static final boolean regionMatches(CharacterIterator target, int offset, int limit, int offset2, int partlen) {
      if (offset < 0) {
         return false;
      } else if (limit - offset < partlen) {
         return false;
      } else {
         int i = offset2;

         do {
            if (partlen-- <= 0) {
               return true;
            }
         } while(target.setIndex(offset++) == target.setIndex(i++));

         return false;
      }
   }

   private static final boolean regionMatchesIgnoreCase(CharacterIterator target, int offset, int limit, String part, int partlen) {
      if (offset < 0) {
         return false;
      } else if (limit - offset < partlen) {
         return false;
      } else {
         int i = 0;

         while(partlen-- > 0) {
            char ch1 = target.setIndex(offset++);
            char ch2 = part.charAt(i++);
            if (ch1 != ch2) {
               char uch1 = Character.toUpperCase(ch1);
               char uch2 = Character.toUpperCase(ch2);
               if (uch1 != uch2 && Character.toLowerCase(uch1) != Character.toLowerCase(uch2)) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   private static final boolean regionMatchesIgnoreCase(CharacterIterator target, int offset, int limit, int offset2, int partlen) {
      if (offset < 0) {
         return false;
      } else if (limit - offset < partlen) {
         return false;
      } else {
         int i = offset2;

         while(partlen-- > 0) {
            char ch1 = target.setIndex(offset++);
            char ch2 = target.setIndex(i++);
            if (ch1 != ch2) {
               char uch1 = Character.toUpperCase(ch1);
               char uch2 = Character.toUpperCase(ch2);
               if (uch1 != uch2 && Character.toLowerCase(uch1) != Character.toLowerCase(uch2)) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   void prepare() {
      this.compile(this.tokentree);
      this.minlength = this.tokentree.getMinLength();
      this.firstChar = null;
      if (!isSet(this.options, 128) && !isSet(this.options, 512)) {
         RangeToken firstChar = Token.createRange();
         int fresult = this.tokentree.analyzeFirstCharacter(firstChar, this.options);
         if (fresult == 1) {
            firstChar.compactRanges();
            this.firstChar = firstChar;
         }
      }

      if (this.operations != null && (this.operations.type == 6 || this.operations.type == 1) && this.operations.next == null) {
         this.fixedStringOnly = true;
         if (this.operations.type == 6) {
            this.fixedString = this.operations.getString();
         } else if (this.operations.getData() >= 65536) {
            this.fixedString = REUtil.decomposeToSurrogates(this.operations.getData());
         } else {
            char[] ac = new char[]{(char)this.operations.getData()};
            this.fixedString = new String(ac);
         }

         this.fixedStringOptions = this.options;
         this.fixedStringTable = new BMPattern(this.fixedString, 256, isSet(this.fixedStringOptions, 2));
      } else if (!isSet(this.options, 256) && !isSet(this.options, 512)) {
         Token.FixedStringContainer container = new Token.FixedStringContainer();
         this.tokentree.findFixedString(container, this.options);
         this.fixedString = container.token == null ? null : container.token.getString();
         this.fixedStringOptions = container.options;
         if (this.fixedString != null && this.fixedString.length() < 2) {
            this.fixedString = null;
         }

         if (this.fixedString != null) {
            this.fixedStringTable = new BMPattern(this.fixedString, 256, isSet(this.fixedStringOptions, 2));
         }
      }

   }

   private static final boolean isSet(int options, int flag) {
      return (options & flag) == flag;
   }

   public RegularExpression(String regex) throws ParseException {
      this.setPattern(regex, (String)null);
   }

   public RegularExpression(String regex, String options) throws ParseException {
      this.setPattern(regex, options);
   }

   RegularExpression(String regex, Token tok, int parens, boolean hasBackReferences, int options) {
      this.regex = regex;
      this.tokentree = tok;
      this.nofparen = parens;
      this.options = options;
      this.hasBackReferences = hasBackReferences;
   }

   public void setPattern(String newPattern) throws ParseException {
      this.setPattern(newPattern, this.options);
   }

   private void setPattern(String newPattern, int options) throws ParseException {
      this.regex = newPattern;
      this.options = options;
      RegexParser rp = isSet(this.options, 512) ? new ParserForXMLSchema() : new RegexParser();
      this.tokentree = ((RegexParser)rp).parse(this.regex, this.options);
      this.nofparen = ((RegexParser)rp).parennumber;
      this.hasBackReferences = ((RegexParser)rp).hasBackReferences;
      this.operations = null;
      this.context = null;
   }

   public void setPattern(String newPattern, String options) throws ParseException {
      this.setPattern(newPattern, REUtil.parseOptions(options));
   }

   public String getPattern() {
      return this.regex;
   }

   public String toString() {
      return this.tokentree.toString(this.options);
   }

   public String getOptions() {
      return REUtil.createOptionString(this.options);
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (!(obj instanceof RegularExpression)) {
         return false;
      } else {
         RegularExpression r = (RegularExpression)obj;
         return this.regex.equals(r.regex) && this.options == r.options;
      }
   }

   boolean equals(String pattern, int options) {
      return this.regex.equals(pattern) && this.options == options;
   }

   public int hashCode() {
      return (this.regex + "/" + this.getOptions()).hashCode();
   }

   public int getNumberOfGroups() {
      return this.nofparen;
   }

   private static final int getWordType0(char var0, int var1) {
      // $FF: Couldn't be decompiled
   }

   private static final boolean isEOLChar(int ch) {
      return ch == 10 || ch == 13 || ch == 8232 || ch == 8233;
   }

   private static final boolean isWordChar(int ch) {
      if (ch == 95) {
         return true;
      } else if (ch < 48) {
         return false;
      } else if (ch > 122) {
         return false;
      } else if (ch <= 57) {
         return true;
      } else if (ch < 65) {
         return false;
      } else if (ch <= 90) {
         return true;
      } else {
         return ch >= 97;
      }
   }

   private static final boolean matchIgnoreCase(int chardata, int ch) {
      if (chardata == ch) {
         return true;
      } else if (chardata <= 65535 && ch <= 65535) {
         char uch1 = Character.toUpperCase((char)chardata);
         char uch2 = Character.toUpperCase((char)ch);
         if (uch1 == uch2) {
            return true;
         } else {
            return Character.toLowerCase(uch1) == Character.toLowerCase(uch2);
         }
      } else {
         return false;
      }
   }

   static final class Context {
      CharacterIterator ciTarget;
      String strTarget;
      char[] charTarget;
      int start;
      int limit;
      int length;
      Match match;
      boolean inuse = false;
      int[] offsets;

      private void resetCommon(int nofclosures) {
         this.length = this.limit - this.start;
         this.inuse = true;
         this.match = null;
         if (this.offsets == null || this.offsets.length != nofclosures) {
            this.offsets = new int[nofclosures];
         }

         for(int i = 0; i < nofclosures; ++i) {
            this.offsets[i] = -1;
         }

      }

      void reset(CharacterIterator target, int start, int limit, int nofclosures) {
         this.ciTarget = target;
         this.start = start;
         this.limit = limit;
         this.resetCommon(nofclosures);
      }

      void reset(String target, int start, int limit, int nofclosures) {
         this.strTarget = target;
         this.start = start;
         this.limit = limit;
         this.resetCommon(nofclosures);
      }

      void reset(char[] target, int start, int limit, int nofclosures) {
         this.charTarget = target;
         this.start = start;
         this.limit = limit;
         this.resetCommon(nofclosures);
      }
   }
}
