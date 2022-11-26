package weblogic.apache.xerces.impl.xpath.regex;

import java.io.Serializable;
import java.text.CharacterIterator;
import java.util.Locale;
import java.util.Stack;
import weblogic.apache.xerces.util.IntStack;

public class RegularExpression implements Serializable {
   private static final long serialVersionUID = 6242499334195006401L;
   static final boolean DEBUG = false;
   String regex;
   int options;
   int nofparen;
   Token tokentree;
   boolean hasBackReferences;
   transient int minlength;
   transient Op operations;
   transient int numberOfClosures;
   transient Context context;
   transient RangeToken firstChar;
   transient String fixedString;
   transient int fixedStringOptions;
   transient BMPattern fixedStringTable;
   transient boolean fixedStringOnly;
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

   private synchronized void compile(Token var1) {
      if (this.operations == null) {
         this.numberOfClosures = 0;
         this.operations = this.compile(var1, (Op)null, false);
      }
   }

   private Op compile(Token var1, Op var2, boolean var3) {
      Object var4;
      switch (var1.type) {
         case 0:
            var4 = Op.createChar(var1.getChar());
            ((Op)var4).next = var2;
            break;
         case 1:
            var4 = var2;
            int var16;
            if (!var3) {
               for(var16 = var1.size() - 1; var16 >= 0; --var16) {
                  var4 = this.compile(var1.getChild(var16), (Op)var4, false);
               }

               return (Op)var4;
            } else {
               for(var16 = 0; var16 < var1.size(); ++var16) {
                  var4 = this.compile(var1.getChild(var16), (Op)var4, true);
               }

               return (Op)var4;
            }
         case 2:
            Op.UnionOp var5 = Op.createUnion(var1.size());

            for(int var6 = 0; var6 < var1.size(); ++var6) {
               var5.addElement(this.compile(var1.getChild(var6), var2, var3));
            }

            var4 = var5;
            break;
         case 3:
         case 9:
            Token var7 = var1.getChild(0);
            int var8 = var1.getMin();
            int var9 = var1.getMax();
            int var18;
            if (var8 >= 0 && var8 == var9) {
               var4 = var2;

               for(var18 = 0; var18 < var8; ++var18) {
                  var4 = this.compile(var7, (Op)var4, var3);
               }

               return (Op)var4;
            } else {
               if (var8 > 0 && var9 > 0) {
                  var9 -= var8;
               }

               if (var9 > 0) {
                  var4 = var2;

                  for(var18 = 0; var18 < var9; ++var18) {
                     Op.ChildOp var19 = Op.createQuestion(var1.type == 9);
                     var19.next = var2;
                     var19.setChild(this.compile(var7, (Op)var4, var3));
                     var4 = var19;
                  }
               } else {
                  Op.ChildOp var17;
                  if (var1.type == 9) {
                     var17 = Op.createNonGreedyClosure();
                  } else {
                     var17 = Op.createClosure(this.numberOfClosures++);
                  }

                  var17.next = var2;
                  var17.setChild(this.compile(var7, var17, var3));
                  var4 = var17;
               }

               if (var8 > 0) {
                  for(var18 = 0; var18 < var8; ++var18) {
                     var4 = this.compile(var7, (Op)var4, var3);
                  }
               }
               break;
            }
         case 4:
         case 5:
            var4 = Op.createRange(var1);
            ((Op)var4).next = var2;
            break;
         case 6:
            if (var1.getParenNumber() == 0) {
               var4 = this.compile(var1.getChild(0), var2, var3);
            } else {
               Op.CharOp var15;
               if (var3) {
                  var15 = Op.createCapture(var1.getParenNumber(), var2);
                  var2 = this.compile(var1.getChild(0), var15, var3);
                  var4 = Op.createCapture(-var1.getParenNumber(), var2);
               } else {
                  var15 = Op.createCapture(-var1.getParenNumber(), var2);
                  var2 = this.compile(var1.getChild(0), var15, var3);
                  var4 = Op.createCapture(var1.getParenNumber(), var2);
               }
            }
            break;
         case 7:
            var4 = var2;
            break;
         case 8:
            var4 = Op.createAnchor(var1.getChar());
            ((Op)var4).next = var2;
            break;
         case 10:
            var4 = Op.createString(var1.getString());
            ((Op)var4).next = var2;
            break;
         case 11:
            var4 = Op.createDot();
            ((Op)var4).next = var2;
            break;
         case 12:
            var4 = Op.createBackReference(var1.getReferenceNumber());
            ((Op)var4).next = var2;
            break;
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         default:
            throw new RuntimeException("Unknown token type: " + var1.type);
         case 20:
            var4 = Op.createLook(20, var2, this.compile(var1.getChild(0), (Op)null, false));
            break;
         case 21:
            var4 = Op.createLook(21, var2, this.compile(var1.getChild(0), (Op)null, false));
            break;
         case 22:
            var4 = Op.createLook(22, var2, this.compile(var1.getChild(0), (Op)null, true));
            break;
         case 23:
            var4 = Op.createLook(23, var2, this.compile(var1.getChild(0), (Op)null, true));
            break;
         case 24:
            var4 = Op.createIndependent(var2, this.compile(var1.getChild(0), (Op)null, var3));
            break;
         case 25:
            var4 = Op.createModifier(var2, this.compile(var1.getChild(0), (Op)null, var3), ((Token.ModifierToken)var1).getOptions(), ((Token.ModifierToken)var1).getOptionsMask());
            break;
         case 26:
            Token.ConditionToken var10 = (Token.ConditionToken)var1;
            int var11 = var10.refNumber;
            Op var12 = var10.condition == null ? null : this.compile(var10.condition, (Op)null, var3);
            Op var13 = this.compile(var10.yes, var2, var3);
            Op var14 = var10.no == null ? null : this.compile(var10.no, var2, var3);
            var4 = Op.createCondition(var2, var11, var12, var13, var14);
      }

      return (Op)var4;
   }

   public boolean matches(char[] var1) {
      return this.matches((char[])var1, 0, var1.length, (Match)null);
   }

   public boolean matches(char[] var1, int var2, int var3) {
      return this.matches(var1, var2, var3, (Match)null);
   }

   public boolean matches(char[] var1, Match var2) {
      return this.matches((char[])var1, 0, var1.length, var2);
   }

   public boolean matches(char[] var1, int var2, int var3, Match var4) {
      synchronized(this) {
         if (this.operations == null) {
            this.prepare();
         }

         if (this.context == null) {
            this.context = new Context();
         }
      }

      Context var6 = null;
      Context var7 = this.context;
      synchronized(var7) {
         var6 = this.context.inuse ? new Context() : this.context;
         var6.reset(var1, var2, var3, this.numberOfClosures);
      }

      if (var4 != null) {
         var4.setNumberOfGroups(this.nofparen);
         var4.setSource(var1);
      } else if (this.hasBackReferences) {
         var4 = new Match();
         var4.setNumberOfGroups(this.nofparen);
      }

      var6.match = var4;
      int var8;
      if (isSet(this.options, 512)) {
         var8 = this.match(var6, this.operations, var6.start, 1, this.options);
         if (var8 == var6.limit) {
            if (var6.match != null) {
               var6.match.setBeginning(0, var6.start);
               var6.match.setEnd(0, var8);
            }

            var6.setInUse(false);
            return true;
         } else {
            return false;
         }
      } else if (this.fixedStringOnly) {
         var8 = this.fixedStringTable.matches(var1, var6.start, var6.limit);
         if (var8 >= 0) {
            if (var6.match != null) {
               var6.match.setBeginning(0, var8);
               var6.match.setEnd(0, var8 + this.fixedString.length());
            }

            var6.setInUse(false);
            return true;
         } else {
            var6.setInUse(false);
            return false;
         }
      } else {
         if (this.fixedString != null) {
            var8 = this.fixedStringTable.matches(var1, var6.start, var6.limit);
            if (var8 < 0) {
               var6.setInUse(false);
               return false;
            }
         }

         var8 = var6.limit - this.minlength;
         int var10 = -1;
         int var9;
         if (this.operations != null && this.operations.type == 7 && this.operations.getChild().type == 0) {
            if (isSet(this.options, 4)) {
               var9 = var6.start;
               var10 = this.match(var6, this.operations, var6.start, 1, this.options);
            } else {
               boolean var15 = true;

               for(var9 = var6.start; var9 <= var8; ++var9) {
                  char var16 = var1[var9];
                  if (isEOLChar(var16)) {
                     var15 = true;
                  } else {
                     if (var15 && 0 <= (var10 = this.match(var6, this.operations, var9, 1, this.options))) {
                        break;
                     }

                     var15 = false;
                  }
               }
            }
         } else if (this.firstChar != null) {
            RangeToken var11 = this.firstChar;

            for(var9 = var6.start; var9 <= var8; ++var9) {
               int var12 = var1[var9];
               if (REUtil.isHighSurrogate(var12) && var9 + 1 < var6.limit) {
                  var12 = REUtil.composeFromSurrogates(var12, var1[var9 + 1]);
               }

               if (var11.match(var12) && 0 <= (var10 = this.match(var6, this.operations, var9, 1, this.options))) {
                  break;
               }
            }
         } else {
            for(var9 = var6.start; var9 <= var8 && 0 > (var10 = this.match(var6, this.operations, var9, 1, this.options)); ++var9) {
            }
         }

         if (var10 >= 0) {
            if (var6.match != null) {
               var6.match.setBeginning(0, var9);
               var6.match.setEnd(0, var10);
            }

            var6.setInUse(false);
            return true;
         } else {
            var6.setInUse(false);
            return false;
         }
      }
   }

   public boolean matches(String var1) {
      return this.matches((String)var1, 0, var1.length(), (Match)null);
   }

   public boolean matches(String var1, int var2, int var3) {
      return this.matches(var1, var2, var3, (Match)null);
   }

   public boolean matches(String var1, Match var2) {
      return this.matches((String)var1, 0, var1.length(), var2);
   }

   public boolean matches(String var1, int var2, int var3, Match var4) {
      synchronized(this) {
         if (this.operations == null) {
            this.prepare();
         }

         if (this.context == null) {
            this.context = new Context();
         }
      }

      Context var6 = null;
      Context var7 = this.context;
      synchronized(var7) {
         var6 = this.context.inuse ? new Context() : this.context;
         var6.reset(var1, var2, var3, this.numberOfClosures);
      }

      if (var4 != null) {
         var4.setNumberOfGroups(this.nofparen);
         var4.setSource(var1);
      } else if (this.hasBackReferences) {
         var4 = new Match();
         var4.setNumberOfGroups(this.nofparen);
      }

      var6.match = var4;
      int var8;
      if (isSet(this.options, 512)) {
         var8 = this.match(var6, this.operations, var6.start, 1, this.options);
         if (var8 == var6.limit) {
            if (var6.match != null) {
               var6.match.setBeginning(0, var6.start);
               var6.match.setEnd(0, var8);
            }

            var6.setInUse(false);
            return true;
         } else {
            return false;
         }
      } else if (this.fixedStringOnly) {
         var8 = this.fixedStringTable.matches(var1, var6.start, var6.limit);
         if (var8 >= 0) {
            if (var6.match != null) {
               var6.match.setBeginning(0, var8);
               var6.match.setEnd(0, var8 + this.fixedString.length());
            }

            var6.setInUse(false);
            return true;
         } else {
            var6.setInUse(false);
            return false;
         }
      } else {
         if (this.fixedString != null) {
            var8 = this.fixedStringTable.matches(var1, var6.start, var6.limit);
            if (var8 < 0) {
               var6.setInUse(false);
               return false;
            }
         }

         var8 = var6.limit - this.minlength;
         int var10 = -1;
         int var9;
         if (this.operations != null && this.operations.type == 7 && this.operations.getChild().type == 0) {
            if (isSet(this.options, 4)) {
               var9 = var6.start;
               var10 = this.match(var6, this.operations, var6.start, 1, this.options);
            } else {
               boolean var15 = true;

               for(var9 = var6.start; var9 <= var8; ++var9) {
                  char var16 = var1.charAt(var9);
                  if (isEOLChar(var16)) {
                     var15 = true;
                  } else {
                     if (var15 && 0 <= (var10 = this.match(var6, this.operations, var9, 1, this.options))) {
                        break;
                     }

                     var15 = false;
                  }
               }
            }
         } else if (this.firstChar != null) {
            RangeToken var11 = this.firstChar;

            for(var9 = var6.start; var9 <= var8; ++var9) {
               int var12 = var1.charAt(var9);
               if (REUtil.isHighSurrogate(var12) && var9 + 1 < var6.limit) {
                  var12 = REUtil.composeFromSurrogates(var12, var1.charAt(var9 + 1));
               }

               if (var11.match(var12) && 0 <= (var10 = this.match(var6, this.operations, var9, 1, this.options))) {
                  break;
               }
            }
         } else {
            for(var9 = var6.start; var9 <= var8 && 0 > (var10 = this.match(var6, this.operations, var9, 1, this.options)); ++var9) {
            }
         }

         if (var10 >= 0) {
            if (var6.match != null) {
               var6.match.setBeginning(0, var9);
               var6.match.setEnd(0, var10);
            }

            var6.setInUse(false);
            return true;
         } else {
            var6.setInUse(false);
            return false;
         }
      }
   }

   private int match(Context var1, Op var2, int var3, int var4, int var5) {
      ExpressionTarget var6 = var1.target;
      Stack var7 = new Stack();
      IntStack var8 = new IntStack();
      boolean var9 = isSet(var5, 2);
      boolean var10 = true;
      boolean var11 = false;

      while(true) {
         int var13;
         int var15;
         int var16;
         if (var2 != null && var3 <= var1.limit && var3 >= var1.start) {
            var15 = -1;
            switch (var2.type) {
               case 0:
                  var16 = var4 > 0 ? var3 : var3 - 1;
                  if (var16 < var1.limit && var16 >= 0) {
                     if (isSet(var5, 4)) {
                        if (REUtil.isHighSurrogate(var6.charAt(var16)) && var16 + var4 >= 0 && var16 + var4 < var1.limit) {
                           var16 += var4;
                        }
                     } else {
                        var13 = var6.charAt(var16);
                        if (REUtil.isHighSurrogate(var13) && var16 + var4 >= 0 && var16 + var4 < var1.limit) {
                           var16 += var4;
                           var13 = REUtil.composeFromSurrogates(var13, var6.charAt(var16));
                        }

                        if (isEOLChar(var13)) {
                           var11 = true;
                           break;
                        }
                     }

                     var3 = var4 > 0 ? var16 + 1 : var16;
                     var2 = var2.next;
                  } else {
                     var11 = true;
                  }
                  break;
               case 1:
                  var16 = var4 > 0 ? var3 : var3 - 1;
                  if (var16 < var1.limit && var16 >= 0 && this.matchChar(var2.getData(), var6.charAt(var16), var9)) {
                     var3 += var4;
                     var2 = var2.next;
                  } else {
                     var11 = true;
                  }
                  break;
               case 2:
               case 12:
               case 13:
               case 14:
               case 17:
               case 18:
               case 19:
               default:
                  throw new RuntimeException("Unknown operation type: " + var2.type);
               case 3:
               case 4:
                  var16 = var4 > 0 ? var3 : var3 - 1;
                  if (var16 < var1.limit && var16 >= 0) {
                     var13 = var6.charAt(var3);
                     if (REUtil.isHighSurrogate(var13) && var16 + var4 < var1.limit && var16 + var4 >= 0) {
                        var16 += var4;
                        var13 = REUtil.composeFromSurrogates(var13, var6.charAt(var16));
                     }

                     RangeToken var18 = var2.getToken();
                     if (!var18.match(var13)) {
                        var11 = true;
                     } else {
                        var3 = var4 > 0 ? var16 + 1 : var16;
                        var2 = var2.next;
                     }
                  } else {
                     var11 = true;
                  }
                  break;
               case 5:
                  if (!this.matchAnchor(var6, var2, var1, var3, var5)) {
                     var11 = true;
                  } else {
                     var2 = var2.next;
                  }
                  break;
               case 6:
                  String var17 = var2.getString();
                  var13 = var17.length();
                  if (var4 > 0) {
                     if (!var6.regionMatches(var9, var3, var1.limit, var17, var13)) {
                        var11 = true;
                        break;
                     }

                     var3 += var13;
                  } else {
                     if (!var6.regionMatches(var9, var3 - var13, var1.limit, var17, var13)) {
                        var11 = true;
                        break;
                     }

                     var3 -= var13;
                  }

                  var2 = var2.next;
                  break;
               case 7:
                  var16 = var2.getData();
                  if (var1.closureContexts[var16].contains(var3)) {
                     var11 = true;
                     break;
                  } else {
                     var1.closureContexts[var16].addOffset(var3);
                  }
               case 9:
                  var7.push(var2);
                  var8.push(var3);
                  var2 = var2.getChild();
                  break;
               case 8:
               case 10:
                  var7.push(var2);
                  var8.push(var3);
                  var2 = var2.next;
                  break;
               case 11:
                  if (var2.size() == 0) {
                     var11 = true;
                  } else {
                     var7.push(var2);
                     var8.push(0);
                     var8.push(var3);
                     var2 = var2.elementAt(0);
                  }
                  break;
               case 15:
                  var16 = var2.getData();
                  if (var1.match != null) {
                     if (var16 > 0) {
                        var8.push(var1.match.getBeginning(var16));
                        var1.match.setBeginning(var16, var3);
                     } else {
                        var13 = -var16;
                        var8.push(var1.match.getEnd(var13));
                        var1.match.setEnd(var13, var3);
                     }

                     var7.push(var2);
                     var8.push(var3);
                  }

                  var2 = var2.next;
                  break;
               case 16:
                  var16 = var2.getData();
                  if (var16 <= 0 || var16 >= this.nofparen) {
                     throw new RuntimeException("Internal Error: Reference number must be more than zero: " + var16);
                  }

                  if (var1.match.getBeginning(var16) >= 0 && var1.match.getEnd(var16) >= 0) {
                     var13 = var1.match.getBeginning(var16);
                     int var14 = var1.match.getEnd(var16) - var13;
                     if (var4 > 0) {
                        if (!var6.regionMatches(var9, var3, var1.limit, var13, var14)) {
                           var11 = true;
                           break;
                        }

                        var3 += var14;
                     } else {
                        if (!var6.regionMatches(var9, var3 - var14, var1.limit, var13, var14)) {
                           var11 = true;
                           break;
                        }

                        var3 -= var14;
                     }

                     var2 = var2.next;
                  } else {
                     var11 = true;
                  }
                  break;
               case 20:
               case 21:
               case 22:
               case 23:
                  var7.push(var2);
                  var8.push(var4);
                  var8.push(var3);
                  var4 = var2.type != 20 && var2.type != 21 ? -1 : 1;
                  var2 = var2.getChild();
                  break;
               case 24:
                  var7.push(var2);
                  var8.push(var3);
                  var2 = var2.getChild();
                  break;
               case 25:
                  var16 = var5 | var2.getData();
                  var16 &= ~var2.getData2();
                  var7.push(var2);
                  var8.push(var5);
                  var8.push(var3);
                  var5 = var16;
                  var2 = var2.getChild();
                  break;
               case 26:
                  Op.ConditionOp var12 = (Op.ConditionOp)var2;
                  if (var12.refNumber > 0) {
                     if (var12.refNumber >= this.nofparen) {
                        throw new RuntimeException("Internal Error: Reference number must be more than zero: " + var12.refNumber);
                     }

                     if (var1.match.getBeginning(var12.refNumber) >= 0 && var1.match.getEnd(var12.refNumber) >= 0) {
                        var2 = var12.yes;
                     } else if (var12.no != null) {
                        var2 = var12.no;
                     } else {
                        var2 = var12.next;
                     }
                  } else {
                     var7.push(var2);
                     var8.push(var3);
                     var2 = var12.condition;
                  }
            }
         } else {
            if (var2 != null) {
               var15 = -1;
            } else {
               var15 = isSet(var5, 512) && var3 != var1.limit ? -1 : var3;
            }

            var11 = true;
         }

         while(var11) {
            if (var7.isEmpty()) {
               return var15;
            }

            var2 = (Op)var7.pop();
            var3 = var8.pop();
            switch (var2.type) {
               case 7:
               case 9:
                  if (var15 < 0) {
                     var2 = var2.next;
                     var11 = false;
                  }
                  break;
               case 8:
               case 10:
                  if (var15 < 0) {
                     var2 = var2.getChild();
                     var11 = false;
                  }
                  break;
               case 11:
                  var16 = var8.pop();
                  if (var15 < 0) {
                     ++var16;
                     if (var16 < var2.size()) {
                        var7.push(var2);
                        var8.push(var16);
                        var8.push(var3);
                        var2 = var2.elementAt(var16);
                        var11 = false;
                     } else {
                        var15 = -1;
                     }
                  }
               case 12:
               case 13:
               case 14:
               case 16:
               case 17:
               case 18:
               case 19:
               default:
                  break;
               case 15:
                  var16 = var2.getData();
                  var13 = var8.pop();
                  if (var15 < 0) {
                     if (var16 > 0) {
                        var1.match.setBeginning(var16, var13);
                     } else {
                        var1.match.setEnd(-var16, var13);
                     }
                  }
                  break;
               case 20:
               case 22:
                  var4 = var8.pop();
                  if (0 <= var15) {
                     var2 = var2.next;
                     var11 = false;
                  }

                  var15 = -1;
                  break;
               case 21:
               case 23:
                  var4 = var8.pop();
                  if (0 > var15) {
                     var2 = var2.next;
                     var11 = false;
                  }

                  var15 = -1;
                  break;
               case 25:
                  var5 = var8.pop();
               case 24:
                  if (var15 >= 0) {
                     var3 = var15;
                     var2 = var2.next;
                     var11 = false;
                  }
                  break;
               case 26:
                  Op.ConditionOp var19 = (Op.ConditionOp)var2;
                  if (0 <= var15) {
                     var2 = var19.yes;
                  } else if (var19.no != null) {
                     var2 = var19.no;
                  } else {
                     var2 = var19.next;
                  }

                  var11 = false;
            }
         }
      }
   }

   private boolean matchChar(int var1, int var2, boolean var3) {
      return var3 ? matchIgnoreCase(var1, var2) : var1 == var2;
   }

   boolean matchAnchor(ExpressionTarget var1, Op var2, Context var3, int var4, int var5) {
      boolean var6 = false;
      int var7;
      switch (var2.getData()) {
         case 36:
            if (isSet(var5, 8)) {
               if (var4 == var3.limit || var4 < var3.limit && isEOLChar(var1.charAt(var4))) {
                  break;
               }

               return false;
            } else {
               if (var4 == var3.limit || var4 + 1 == var3.limit && isEOLChar(var1.charAt(var4)) || var4 + 2 == var3.limit && var1.charAt(var4) == '\r' && var1.charAt(var4 + 1) == '\n') {
                  break;
               }

               return false;
            }
         case 60:
            if (var3.length != 0 && var4 != var3.limit) {
               if (getWordType(var1, var3.start, var3.limit, var4, var5) == 1 && getPreviousWordType(var1, var3.start, var3.limit, var4, var5) == 2) {
                  break;
               }

               return false;
            }

            return false;
         case 62:
            if (var3.length != 0 && var4 != var3.start) {
               if (getWordType(var1, var3.start, var3.limit, var4, var5) == 2 && getPreviousWordType(var1, var3.start, var3.limit, var4, var5) == 1) {
                  break;
               }

               return false;
            }

            return false;
         case 64:
            if (var4 == var3.start || var4 > var3.start && isEOLChar(var1.charAt(var4 - 1))) {
               break;
            }

            return false;
         case 65:
            if (var4 != var3.start) {
               return false;
            }
            break;
         case 66:
            if (var3.length == 0) {
               var6 = true;
            } else {
               var7 = getWordType(var1, var3.start, var3.limit, var4, var5);
               var6 = var7 == 0 || var7 == getPreviousWordType(var1, var3.start, var3.limit, var4, var5);
            }

            if (!var6) {
               return false;
            }
            break;
         case 90:
            if (var4 == var3.limit || var4 + 1 == var3.limit && isEOLChar(var1.charAt(var4)) || var4 + 2 == var3.limit && var1.charAt(var4) == '\r' && var1.charAt(var4 + 1) == '\n') {
               break;
            }

            return false;
         case 94:
            if (isSet(var5, 8)) {
               if (var4 == var3.start || var4 > var3.start && var4 < var3.limit && isEOLChar(var1.charAt(var4 - 1))) {
                  break;
               }

               return false;
            }

            if (var4 != var3.start) {
               return false;
            }
            break;
         case 98:
            if (var3.length == 0) {
               return false;
            }

            var7 = getWordType(var1, var3.start, var3.limit, var4, var5);
            if (var7 == 0) {
               return false;
            }

            int var8 = getPreviousWordType(var1, var3.start, var3.limit, var4, var5);
            if (var7 == var8) {
               return false;
            }
            break;
         case 122:
            if (var4 != var3.limit) {
               return false;
            }
      }

      return true;
   }

   private static final int getPreviousWordType(ExpressionTarget var0, int var1, int var2, int var3, int var4) {
      --var3;

      int var5;
      for(var5 = getWordType(var0, var1, var2, var3, var4); var5 == 0; var5 = getWordType(var0, var1, var2, var3, var4)) {
         --var3;
      }

      return var5;
   }

   private static final int getWordType(ExpressionTarget var0, int var1, int var2, int var3, int var4) {
      return var3 >= var1 && var3 < var2 ? getWordType0(var0.charAt(var3), var4) : 2;
   }

   public boolean matches(CharacterIterator var1) {
      return this.matches(var1, (Match)null);
   }

   public boolean matches(CharacterIterator var1, Match var2) {
      int var3 = var1.getBeginIndex();
      int var4 = var1.getEndIndex();
      synchronized(this) {
         if (this.operations == null) {
            this.prepare();
         }

         if (this.context == null) {
            this.context = new Context();
         }
      }

      Context var6 = null;
      Context var7 = this.context;
      synchronized(var7) {
         var6 = this.context.inuse ? new Context() : this.context;
         var6.reset(var1, var3, var4, this.numberOfClosures);
      }

      if (var2 != null) {
         var2.setNumberOfGroups(this.nofparen);
         var2.setSource(var1);
      } else if (this.hasBackReferences) {
         var2 = new Match();
         var2.setNumberOfGroups(this.nofparen);
      }

      var6.match = var2;
      int var8;
      if (isSet(this.options, 512)) {
         var8 = this.match(var6, this.operations, var6.start, 1, this.options);
         if (var8 == var6.limit) {
            if (var6.match != null) {
               var6.match.setBeginning(0, var6.start);
               var6.match.setEnd(0, var8);
            }

            var6.setInUse(false);
            return true;
         } else {
            return false;
         }
      } else if (this.fixedStringOnly) {
         var8 = this.fixedStringTable.matches(var1, var6.start, var6.limit);
         if (var8 >= 0) {
            if (var6.match != null) {
               var6.match.setBeginning(0, var8);
               var6.match.setEnd(0, var8 + this.fixedString.length());
            }

            var6.setInUse(false);
            return true;
         } else {
            var6.setInUse(false);
            return false;
         }
      } else {
         if (this.fixedString != null) {
            var8 = this.fixedStringTable.matches(var1, var6.start, var6.limit);
            if (var8 < 0) {
               var6.setInUse(false);
               return false;
            }
         }

         var8 = var6.limit - this.minlength;
         int var10 = -1;
         int var9;
         if (this.operations != null && this.operations.type == 7 && this.operations.getChild().type == 0) {
            if (isSet(this.options, 4)) {
               var9 = var6.start;
               var10 = this.match(var6, this.operations, var6.start, 1, this.options);
            } else {
               boolean var15 = true;

               for(var9 = var6.start; var9 <= var8; ++var9) {
                  char var16 = var1.setIndex(var9);
                  if (isEOLChar(var16)) {
                     var15 = true;
                  } else {
                     if (var15 && 0 <= (var10 = this.match(var6, this.operations, var9, 1, this.options))) {
                        break;
                     }

                     var15 = false;
                  }
               }
            }
         } else if (this.firstChar != null) {
            RangeToken var11 = this.firstChar;

            for(var9 = var6.start; var9 <= var8; ++var9) {
               int var12 = var1.setIndex(var9);
               if (REUtil.isHighSurrogate(var12) && var9 + 1 < var6.limit) {
                  var12 = REUtil.composeFromSurrogates(var12, var1.setIndex(var9 + 1));
               }

               if (var11.match(var12) && 0 <= (var10 = this.match(var6, this.operations, var9, 1, this.options))) {
                  break;
               }
            }
         } else {
            for(var9 = var6.start; var9 <= var8 && 0 > (var10 = this.match(var6, this.operations, var9, 1, this.options)); ++var9) {
            }
         }

         if (var10 >= 0) {
            if (var6.match != null) {
               var6.match.setBeginning(0, var9);
               var6.match.setEnd(0, var10);
            }

            var6.setInUse(false);
            return true;
         } else {
            var6.setInUse(false);
            return false;
         }
      }
   }

   void prepare() {
      this.compile(this.tokentree);
      this.minlength = this.tokentree.getMinLength();
      this.firstChar = null;
      if (!isSet(this.options, 128) && !isSet(this.options, 512)) {
         RangeToken var1 = Token.createRange();
         int var2 = this.tokentree.analyzeFirstCharacter(var1, this.options);
         if (var2 == 1) {
            var1.compactRanges();
            this.firstChar = var1;
         }
      }

      if (this.operations != null && (this.operations.type == 6 || this.operations.type == 1) && this.operations.next == null) {
         this.fixedStringOnly = true;
         if (this.operations.type == 6) {
            this.fixedString = this.operations.getString();
         } else if (this.operations.getData() >= 65536) {
            this.fixedString = REUtil.decomposeToSurrogates(this.operations.getData());
         } else {
            char[] var4 = new char[]{(char)this.operations.getData()};
            this.fixedString = new String(var4);
         }

         this.fixedStringOptions = this.options;
         this.fixedStringTable = new BMPattern(this.fixedString, 256, isSet(this.fixedStringOptions, 2));
      } else if (!isSet(this.options, 256) && !isSet(this.options, 512)) {
         Token.FixedStringContainer var3 = new Token.FixedStringContainer();
         this.tokentree.findFixedString(var3, this.options);
         this.fixedString = var3.token == null ? null : var3.token.getString();
         this.fixedStringOptions = var3.options;
         if (this.fixedString != null && this.fixedString.length() < 2) {
            this.fixedString = null;
         }

         if (this.fixedString != null) {
            this.fixedStringTable = new BMPattern(this.fixedString, 256, isSet(this.fixedStringOptions, 2));
         }
      }

   }

   private static final boolean isSet(int var0, int var1) {
      return (var0 & var1) == var1;
   }

   public RegularExpression(String var1) throws ParseException {
      this(var1, (String)null);
   }

   public RegularExpression(String var1, String var2) throws ParseException {
      this.hasBackReferences = false;
      this.operations = null;
      this.context = null;
      this.firstChar = null;
      this.fixedString = null;
      this.fixedStringTable = null;
      this.fixedStringOnly = false;
      this.setPattern(var1, var2);
   }

   public RegularExpression(String var1, String var2, Locale var3) throws ParseException {
      this.hasBackReferences = false;
      this.operations = null;
      this.context = null;
      this.firstChar = null;
      this.fixedString = null;
      this.fixedStringTable = null;
      this.fixedStringOnly = false;
      this.setPattern(var1, var2, var3);
   }

   RegularExpression(String var1, Token var2, int var3, boolean var4, int var5) {
      this.hasBackReferences = false;
      this.operations = null;
      this.context = null;
      this.firstChar = null;
      this.fixedString = null;
      this.fixedStringTable = null;
      this.fixedStringOnly = false;
      this.regex = var1;
      this.tokentree = var2;
      this.nofparen = var3;
      this.options = var5;
      this.hasBackReferences = var4;
   }

   public void setPattern(String var1) throws ParseException {
      this.setPattern(var1, Locale.getDefault());
   }

   public void setPattern(String var1, Locale var2) throws ParseException {
      this.setPattern(var1, this.options, var2);
   }

   private void setPattern(String var1, int var2, Locale var3) throws ParseException {
      this.regex = var1;
      this.options = var2;
      Object var4 = isSet(this.options, 512) ? new ParserForXMLSchema(var3) : new RegexParser(var3);
      this.tokentree = ((RegexParser)var4).parse(this.regex, this.options);
      this.nofparen = ((RegexParser)var4).parennumber;
      this.hasBackReferences = ((RegexParser)var4).hasBackReferences;
      this.operations = null;
      this.context = null;
   }

   public void setPattern(String var1, String var2) throws ParseException {
      this.setPattern(var1, var2, Locale.getDefault());
   }

   public void setPattern(String var1, String var2, Locale var3) throws ParseException {
      this.setPattern(var1, REUtil.parseOptions(var2), var3);
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

   public boolean equals(Object var1) {
      if (var1 == null) {
         return false;
      } else if (!(var1 instanceof RegularExpression)) {
         return false;
      } else {
         RegularExpression var2 = (RegularExpression)var1;
         return this.regex.equals(var2.regex) && this.options == var2.options;
      }
   }

   boolean equals(String var1, int var2) {
      return this.regex.equals(var1) && this.options == var2;
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

   private static final boolean isEOLChar(int var0) {
      return var0 == 10 || var0 == 13 || var0 == 8232 || var0 == 8233;
   }

   private static final boolean isWordChar(int var0) {
      if (var0 == 95) {
         return true;
      } else if (var0 < 48) {
         return false;
      } else if (var0 > 122) {
         return false;
      } else if (var0 <= 57) {
         return true;
      } else if (var0 < 65) {
         return false;
      } else if (var0 <= 90) {
         return true;
      } else {
         return var0 >= 97;
      }
   }

   private static final boolean matchIgnoreCase(int var0, int var1) {
      if (var0 == var1) {
         return true;
      } else if (var0 <= 65535 && var1 <= 65535) {
         char var2 = Character.toUpperCase((char)var0);
         char var3 = Character.toUpperCase((char)var1);
         if (var2 == var3) {
            return true;
         } else {
            return Character.toLowerCase(var2) == Character.toLowerCase(var3);
         }
      } else {
         return false;
      }
   }

   static final class Context {
      int start;
      int limit;
      int length;
      Match match;
      boolean inuse = false;
      ClosureContext[] closureContexts;
      private StringTarget stringTarget;
      private CharArrayTarget charArrayTarget;
      private CharacterIteratorTarget characterIteratorTarget;
      ExpressionTarget target;

      private void resetCommon(int var1) {
         this.length = this.limit - this.start;
         this.setInUse(true);
         this.match = null;
         if (this.closureContexts == null || this.closureContexts.length != var1) {
            this.closureContexts = new ClosureContext[var1];
         }

         for(int var2 = 0; var2 < var1; ++var2) {
            if (this.closureContexts[var2] == null) {
               this.closureContexts[var2] = new ClosureContext();
            } else {
               this.closureContexts[var2].reset();
            }
         }

      }

      void reset(CharacterIterator var1, int var2, int var3, int var4) {
         if (this.characterIteratorTarget == null) {
            this.characterIteratorTarget = new CharacterIteratorTarget(var1);
         } else {
            this.characterIteratorTarget.resetTarget(var1);
         }

         this.target = this.characterIteratorTarget;
         this.start = var2;
         this.limit = var3;
         this.resetCommon(var4);
      }

      void reset(String var1, int var2, int var3, int var4) {
         if (this.stringTarget == null) {
            this.stringTarget = new StringTarget(var1);
         } else {
            this.stringTarget.resetTarget(var1);
         }

         this.target = this.stringTarget;
         this.start = var2;
         this.limit = var3;
         this.resetCommon(var4);
      }

      void reset(char[] var1, int var2, int var3, int var4) {
         if (this.charArrayTarget == null) {
            this.charArrayTarget = new CharArrayTarget(var1);
         } else {
            this.charArrayTarget.resetTarget(var1);
         }

         this.target = this.charArrayTarget;
         this.start = var2;
         this.limit = var3;
         this.resetCommon(var4);
      }

      synchronized void setInUse(boolean var1) {
         this.inuse = var1;
      }
   }

   static final class ClosureContext {
      int[] offsets = new int[4];
      int currentIndex = 0;

      boolean contains(int var1) {
         for(int var2 = 0; var2 < this.currentIndex; ++var2) {
            if (this.offsets[var2] == var1) {
               return true;
            }
         }

         return false;
      }

      void reset() {
         this.currentIndex = 0;
      }

      void addOffset(int var1) {
         if (this.currentIndex == this.offsets.length) {
            this.offsets = this.expandOffsets();
         }

         this.offsets[this.currentIndex++] = var1;
      }

      private int[] expandOffsets() {
         int var1 = this.offsets.length;
         int var2 = var1 << 1;
         int[] var3 = new int[var2];
         System.arraycopy(this.offsets, 0, var3, 0, this.currentIndex);
         return var3;
      }
   }

   static final class CharacterIteratorTarget extends ExpressionTarget {
      CharacterIterator target;

      CharacterIteratorTarget(CharacterIterator var1) {
         this.target = var1;
      }

      final void resetTarget(CharacterIterator var1) {
         this.target = var1;
      }

      final char charAt(int var1) {
         return this.target.setIndex(var1);
      }

      final boolean regionMatches(boolean var1, int var2, int var3, String var4, int var5) {
         if (var2 >= 0 && var3 - var2 >= var5) {
            return var1 ? this.regionMatchesIgnoreCase(var2, var3, var4, var5) : this.regionMatches(var2, var3, var4, var5);
         } else {
            return false;
         }
      }

      private final boolean regionMatches(int var1, int var2, String var3, int var4) {
         int var5 = 0;

         while(var4-- > 0) {
            if (this.target.setIndex(var1++) != var3.charAt(var5++)) {
               return false;
            }
         }

         return true;
      }

      private final boolean regionMatchesIgnoreCase(int var1, int var2, String var3, int var4) {
         int var5 = 0;

         while(var4-- > 0) {
            char var6 = this.target.setIndex(var1++);
            char var7 = var3.charAt(var5++);
            if (var6 != var7) {
               char var8 = Character.toUpperCase(var6);
               char var9 = Character.toUpperCase(var7);
               if (var8 != var9 && Character.toLowerCase(var8) != Character.toLowerCase(var9)) {
                  return false;
               }
            }
         }

         return true;
      }

      final boolean regionMatches(boolean var1, int var2, int var3, int var4, int var5) {
         if (var2 >= 0 && var3 - var2 >= var5) {
            return var1 ? this.regionMatchesIgnoreCase(var2, var3, var4, var5) : this.regionMatches(var2, var3, var4, var5);
         } else {
            return false;
         }
      }

      private final boolean regionMatches(int var1, int var2, int var3, int var4) {
         int var5 = var3;

         while(var4-- > 0) {
            if (this.target.setIndex(var1++) != this.target.setIndex(var5++)) {
               return false;
            }
         }

         return true;
      }

      private final boolean regionMatchesIgnoreCase(int var1, int var2, int var3, int var4) {
         int var5 = var3;

         while(var4-- > 0) {
            char var6 = this.target.setIndex(var1++);
            char var7 = this.target.setIndex(var5++);
            if (var6 != var7) {
               char var8 = Character.toUpperCase(var6);
               char var9 = Character.toUpperCase(var7);
               if (var8 != var9 && Character.toLowerCase(var8) != Character.toLowerCase(var9)) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   static final class CharArrayTarget extends ExpressionTarget {
      char[] target;

      CharArrayTarget(char[] var1) {
         this.target = var1;
      }

      final void resetTarget(char[] var1) {
         this.target = var1;
      }

      char charAt(int var1) {
         return this.target[var1];
      }

      final boolean regionMatches(boolean var1, int var2, int var3, String var4, int var5) {
         if (var2 >= 0 && var3 - var2 >= var5) {
            return var1 ? this.regionMatchesIgnoreCase(var2, var3, var4, var5) : this.regionMatches(var2, var3, var4, var5);
         } else {
            return false;
         }
      }

      private final boolean regionMatches(int var1, int var2, String var3, int var4) {
         int var5 = 0;

         while(var4-- > 0) {
            if (this.target[var1++] != var3.charAt(var5++)) {
               return false;
            }
         }

         return true;
      }

      private final boolean regionMatchesIgnoreCase(int var1, int var2, String var3, int var4) {
         int var5 = 0;

         while(var4-- > 0) {
            char var6 = this.target[var1++];
            char var7 = var3.charAt(var5++);
            if (var6 != var7) {
               char var8 = Character.toUpperCase(var6);
               char var9 = Character.toUpperCase(var7);
               if (var8 != var9 && Character.toLowerCase(var8) != Character.toLowerCase(var9)) {
                  return false;
               }
            }
         }

         return true;
      }

      final boolean regionMatches(boolean var1, int var2, int var3, int var4, int var5) {
         if (var2 >= 0 && var3 - var2 >= var5) {
            return var1 ? this.regionMatchesIgnoreCase(var2, var3, var4, var5) : this.regionMatches(var2, var3, var4, var5);
         } else {
            return false;
         }
      }

      private final boolean regionMatches(int var1, int var2, int var3, int var4) {
         int var5 = var3;

         while(var4-- > 0) {
            if (this.target[var1++] != this.target[var5++]) {
               return false;
            }
         }

         return true;
      }

      private final boolean regionMatchesIgnoreCase(int var1, int var2, int var3, int var4) {
         int var5 = var3;

         while(var4-- > 0) {
            char var6 = this.target[var1++];
            char var7 = this.target[var5++];
            if (var6 != var7) {
               char var8 = Character.toUpperCase(var6);
               char var9 = Character.toUpperCase(var7);
               if (var8 != var9 && Character.toLowerCase(var8) != Character.toLowerCase(var9)) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   static final class StringTarget extends ExpressionTarget {
      private String target;

      StringTarget(String var1) {
         this.target = var1;
      }

      final void resetTarget(String var1) {
         this.target = var1;
      }

      final char charAt(int var1) {
         return this.target.charAt(var1);
      }

      final boolean regionMatches(boolean var1, int var2, int var3, String var4, int var5) {
         if (var3 - var2 < var5) {
            return false;
         } else {
            return var1 ? this.target.regionMatches(true, var2, var4, 0, var5) : this.target.regionMatches(var2, var4, 0, var5);
         }
      }

      final boolean regionMatches(boolean var1, int var2, int var3, int var4, int var5) {
         if (var3 - var2 < var5) {
            return false;
         } else {
            return var1 ? this.target.regionMatches(true, var2, this.target, var4, var5) : this.target.regionMatches(var2, this.target, var4, var5);
         }
      }
   }

   abstract static class ExpressionTarget {
      abstract char charAt(int var1);

      abstract boolean regionMatches(boolean var1, int var2, int var3, String var4, int var5);

      abstract boolean regionMatches(boolean var1, int var2, int var3, int var4, int var5);
   }
}
