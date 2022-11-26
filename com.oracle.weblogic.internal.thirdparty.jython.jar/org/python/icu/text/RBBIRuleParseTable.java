package org.python.icu.text;

class RBBIRuleParseTable {
   static final short doCheckVarDef = 1;
   static final short doDotAny = 2;
   static final short doEndAssign = 3;
   static final short doEndOfRule = 4;
   static final short doEndVariableName = 5;
   static final short doExit = 6;
   static final short doExprCatOperator = 7;
   static final short doExprFinished = 8;
   static final short doExprOrOperator = 9;
   static final short doExprRParen = 10;
   static final short doExprStart = 11;
   static final short doLParen = 12;
   static final short doNOP = 13;
   static final short doNoChain = 14;
   static final short doOptionEnd = 15;
   static final short doOptionStart = 16;
   static final short doReverseDir = 17;
   static final short doRuleChar = 18;
   static final short doRuleError = 19;
   static final short doRuleErrorAssignExpr = 20;
   static final short doScanUnicodeSet = 21;
   static final short doSlash = 22;
   static final short doStartAssign = 23;
   static final short doStartTagValue = 24;
   static final short doStartVariableName = 25;
   static final short doTagDigit = 26;
   static final short doTagExpectedError = 27;
   static final short doTagValue = 28;
   static final short doUnaryOpPlus = 29;
   static final short doUnaryOpQuestion = 30;
   static final short doUnaryOpStar = 31;
   static final short doVariableNameExpectedErr = 32;
   static final short kRuleSet_default = 255;
   static final short kRuleSet_digit_char = 128;
   static final short kRuleSet_eof = 252;
   static final short kRuleSet_escaped = 254;
   static final short kRuleSet_name_char = 129;
   static final short kRuleSet_name_start_char = 130;
   static final short kRuleSet_rule_char = 131;
   static final short kRuleSet_white_space = 132;
   static RBBIRuleTableElement[] gRuleParseStateTable = new RBBIRuleTableElement[]{new RBBIRuleTableElement((short)13, 0, 0, 0, true, (String)null), new RBBIRuleTableElement((short)11, 254, 29, 9, false, "start"), new RBBIRuleTableElement((short)13, 132, 1, 0, true, (String)null), new RBBIRuleTableElement((short)14, 94, 12, 9, true, (String)null), new RBBIRuleTableElement((short)11, 36, 88, 98, false, (String)null), new RBBIRuleTableElement((short)13, 33, 19, 0, true, (String)null), new RBBIRuleTableElement((short)13, 59, 1, 0, true, (String)null), new RBBIRuleTableElement((short)13, 252, 0, 0, false, (String)null), new RBBIRuleTableElement((short)11, 255, 29, 9, false, (String)null), new RBBIRuleTableElement((short)4, 59, 1, 0, true, "break-rule-end"), new RBBIRuleTableElement((short)13, 132, 9, 0, true, (String)null), new RBBIRuleTableElement((short)19, 255, 103, 0, false, (String)null), new RBBIRuleTableElement((short)11, 254, 29, 0, false, "start-after-caret"), new RBBIRuleTableElement((short)13, 132, 12, 0, true, (String)null), new RBBIRuleTableElement((short)19, 94, 103, 0, false, (String)null), new RBBIRuleTableElement((short)11, 36, 88, 37, false, (String)null), new RBBIRuleTableElement((short)19, 59, 103, 0, false, (String)null), new RBBIRuleTableElement((short)19, 252, 103, 0, false, (String)null), new RBBIRuleTableElement((short)11, 255, 29, 0, false, (String)null), new RBBIRuleTableElement((short)13, 33, 21, 0, true, "rev-option"), new RBBIRuleTableElement((short)17, 255, 28, 9, false, (String)null), new RBBIRuleTableElement((short)16, 130, 23, 0, true, "option-scan1"), new RBBIRuleTableElement((short)19, 255, 103, 0, false, (String)null), new RBBIRuleTableElement((short)13, 129, 23, 0, true, "option-scan2"), new RBBIRuleTableElement((short)15, 255, 25, 0, false, (String)null), new RBBIRuleTableElement((short)13, 59, 1, 0, true, "option-scan3"), new RBBIRuleTableElement((short)13, 132, 25, 0, true, (String)null), new RBBIRuleTableElement((short)19, 255, 103, 0, false, (String)null), new RBBIRuleTableElement((short)11, 255, 29, 9, false, "reverse-rule"), new RBBIRuleTableElement((short)18, 254, 38, 0, true, "term"), new RBBIRuleTableElement((short)13, 132, 29, 0, true, (String)null), new RBBIRuleTableElement((short)18, 131, 38, 0, true, (String)null), new RBBIRuleTableElement((short)13, 91, 94, 38, false, (String)null), new RBBIRuleTableElement((short)12, 40, 29, 38, true, (String)null), new RBBIRuleTableElement((short)13, 36, 88, 37, false, (String)null), new RBBIRuleTableElement((short)2, 46, 38, 0, true, (String)null), new RBBIRuleTableElement((short)19, 255, 103, 0, false, (String)null), new RBBIRuleTableElement((short)1, 255, 38, 0, false, "term-var-ref"), new RBBIRuleTableElement((short)13, 132, 38, 0, true, "expr-mod"), new RBBIRuleTableElement((short)31, 42, 43, 0, true, (String)null), new RBBIRuleTableElement((short)29, 43, 43, 0, true, (String)null), new RBBIRuleTableElement((short)30, 63, 43, 0, true, (String)null), new RBBIRuleTableElement((short)13, 255, 43, 0, false, (String)null), new RBBIRuleTableElement((short)7, 254, 29, 0, false, "expr-cont"), new RBBIRuleTableElement((short)13, 132, 43, 0, true, (String)null), new RBBIRuleTableElement((short)7, 131, 29, 0, false, (String)null), new RBBIRuleTableElement((short)7, 91, 29, 0, false, (String)null), new RBBIRuleTableElement((short)7, 40, 29, 0, false, (String)null), new RBBIRuleTableElement((short)7, 36, 29, 0, false, (String)null), new RBBIRuleTableElement((short)7, 46, 29, 0, false, (String)null), new RBBIRuleTableElement((short)7, 47, 55, 0, false, (String)null), new RBBIRuleTableElement((short)7, 123, 67, 0, true, (String)null), new RBBIRuleTableElement((short)9, 124, 29, 0, true, (String)null), new RBBIRuleTableElement((short)10, 41, 255, 0, true, (String)null), new RBBIRuleTableElement((short)8, 255, 255, 0, false, (String)null), new RBBIRuleTableElement((short)22, 47, 57, 0, true, "look-ahead"), new RBBIRuleTableElement((short)13, 255, 103, 0, false, (String)null), new RBBIRuleTableElement((short)7, 254, 29, 0, false, "expr-cont-no-slash"), new RBBIRuleTableElement((short)13, 132, 43, 0, true, (String)null), new RBBIRuleTableElement((short)7, 131, 29, 0, false, (String)null), new RBBIRuleTableElement((short)7, 91, 29, 0, false, (String)null), new RBBIRuleTableElement((short)7, 40, 29, 0, false, (String)null), new RBBIRuleTableElement((short)7, 36, 29, 0, false, (String)null), new RBBIRuleTableElement((short)7, 46, 29, 0, false, (String)null), new RBBIRuleTableElement((short)9, 124, 29, 0, true, (String)null), new RBBIRuleTableElement((short)10, 41, 255, 0, true, (String)null), new RBBIRuleTableElement((short)8, 255, 255, 0, false, (String)null), new RBBIRuleTableElement((short)13, 132, 67, 0, true, "tag-open"), new RBBIRuleTableElement((short)24, 128, 70, 0, false, (String)null), new RBBIRuleTableElement((short)27, 255, 103, 0, false, (String)null), new RBBIRuleTableElement((short)13, 132, 74, 0, true, "tag-value"), new RBBIRuleTableElement((short)13, 125, 74, 0, false, (String)null), new RBBIRuleTableElement((short)26, 128, 70, 0, true, (String)null), new RBBIRuleTableElement((short)27, 255, 103, 0, false, (String)null), new RBBIRuleTableElement((short)13, 132, 74, 0, true, "tag-close"), new RBBIRuleTableElement((short)28, 125, 77, 0, true, (String)null), new RBBIRuleTableElement((short)27, 255, 103, 0, false, (String)null), new RBBIRuleTableElement((short)7, 254, 29, 0, false, "expr-cont-no-tag"), new RBBIRuleTableElement((short)13, 132, 77, 0, true, (String)null), new RBBIRuleTableElement((short)7, 131, 29, 0, false, (String)null), new RBBIRuleTableElement((short)7, 91, 29, 0, false, (String)null), new RBBIRuleTableElement((short)7, 40, 29, 0, false, (String)null), new RBBIRuleTableElement((short)7, 36, 29, 0, false, (String)null), new RBBIRuleTableElement((short)7, 46, 29, 0, false, (String)null), new RBBIRuleTableElement((short)7, 47, 55, 0, false, (String)null), new RBBIRuleTableElement((short)9, 124, 29, 0, true, (String)null), new RBBIRuleTableElement((short)10, 41, 255, 0, true, (String)null), new RBBIRuleTableElement((short)8, 255, 255, 0, false, (String)null), new RBBIRuleTableElement((short)25, 36, 90, 0, true, "scan-var-name"), new RBBIRuleTableElement((short)13, 255, 103, 0, false, (String)null), new RBBIRuleTableElement((short)13, 130, 92, 0, true, "scan-var-start"), new RBBIRuleTableElement((short)32, 255, 103, 0, false, (String)null), new RBBIRuleTableElement((short)13, 129, 92, 0, true, "scan-var-body"), new RBBIRuleTableElement((short)5, 255, 255, 0, false, (String)null), new RBBIRuleTableElement((short)21, 91, 255, 0, true, "scan-unicode-set"), new RBBIRuleTableElement((short)21, 112, 255, 0, true, (String)null), new RBBIRuleTableElement((short)21, 80, 255, 0, true, (String)null), new RBBIRuleTableElement((short)13, 255, 103, 0, false, (String)null), new RBBIRuleTableElement((short)13, 132, 98, 0, true, "assign-or-rule"), new RBBIRuleTableElement((short)23, 61, 29, 101, true, (String)null), new RBBIRuleTableElement((short)13, 255, 37, 9, false, (String)null), new RBBIRuleTableElement((short)3, 59, 1, 0, true, "assign-end"), new RBBIRuleTableElement((short)20, 255, 103, 0, false, (String)null), new RBBIRuleTableElement((short)6, 255, 103, 0, true, "errorDeath")};

   static class RBBIRuleTableElement {
      short fAction;
      short fCharClass;
      short fNextState;
      short fPushState;
      boolean fNextChar;
      String fStateName;

      RBBIRuleTableElement(short a, int cc, int ns, int ps, boolean nc, String sn) {
         this.fAction = a;
         this.fCharClass = (short)cc;
         this.fNextState = (short)ns;
         this.fPushState = (short)ps;
         this.fNextChar = nc;
         this.fStateName = sn;
      }
   }
}
