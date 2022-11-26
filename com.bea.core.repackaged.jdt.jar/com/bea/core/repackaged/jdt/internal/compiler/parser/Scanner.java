package com.bea.core.repackaged.jdt.internal.compiler.parser;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.core.compiler.InvalidInputException;
import com.bea.core.repackaged.jdt.internal.compiler.CompilationResult;
import com.bea.core.repackaged.jdt.internal.compiler.DefaultErrorHandlingPolicies;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Statement;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.problem.DefaultProblemFactory;
import com.bea.core.repackaged.jdt.internal.compiler.problem.ProblemReporter;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;

public class Scanner implements TerminalTokens {
   public long sourceLevel;
   public long complianceLevel;
   public boolean useAssertAsAnIndentifier;
   public boolean containsAssertKeyword;
   public boolean useEnumAsAnIndentifier;
   public boolean recordLineSeparator;
   public char currentCharacter;
   public int startPosition;
   public int currentPosition;
   public int initialPosition;
   public int eofPosition;
   public boolean skipComments;
   public boolean tokenizeComments;
   public boolean tokenizeWhiteSpace;
   public char[] source;
   public char[] withoutUnicodeBuffer;
   public int withoutUnicodePtr;
   public boolean unicodeAsBackSlash;
   public boolean scanningFloatLiteral;
   public static final int COMMENT_ARRAYS_SIZE = 30;
   public int[] commentStops;
   public int[] commentStarts;
   public int[] commentTagStarts;
   public int commentPtr;
   public int lastCommentLinePosition;
   public char[][] foundTaskTags;
   public char[][] foundTaskMessages;
   public char[][] foundTaskPriorities;
   public int[][] foundTaskPositions;
   public int foundTaskCount;
   public char[][] taskTags;
   public char[][] taskPriorities;
   public boolean isTaskCaseSensitive;
   public boolean diet;
   public int[] lineEnds;
   public int linePtr;
   public boolean wasAcr;
   public boolean fakeInModule;
   boolean inCase;
   protected ScanContext scanContext;
   protected boolean insideModuleInfo;
   public static final String END_OF_SOURCE = "End_Of_Source";
   public static final String INVALID_HEXA = "Invalid_Hexa_Literal";
   public static final String INVALID_OCTAL = "Invalid_Octal_Literal";
   public static final String INVALID_CHARACTER_CONSTANT = "Invalid_Character_Constant";
   public static final String INVALID_ESCAPE = "Invalid_Escape";
   public static final String INVALID_INPUT = "Invalid_Input";
   public static final String INVALID_UNICODE_ESCAPE = "Invalid_Unicode_Escape";
   public static final String INVALID_FLOAT = "Invalid_Float_Literal";
   public static final String INVALID_LOW_SURROGATE = "Invalid_Low_Surrogate";
   public static final String INVALID_HIGH_SURROGATE = "Invalid_High_Surrogate";
   public static final String NULL_SOURCE_STRING = "Null_Source_String";
   public static final String UNTERMINATED_STRING = "Unterminated_String";
   public static final String UNTERMINATED_COMMENT = "Unterminated_Comment";
   public static final String INVALID_CHAR_IN_STRING = "Invalid_Char_In_String";
   public static final String INVALID_DIGIT = "Invalid_Digit";
   private static final int[] EMPTY_LINE_ENDS;
   public static final String INVALID_BINARY = "Invalid_Binary_Literal";
   public static final String BINARY_LITERAL_NOT_BELOW_17 = "Binary_Literal_Not_Below_17";
   public static final String ILLEGAL_HEXA_LITERAL = "Illegal_Hexa_Literal";
   public static final String INVALID_UNDERSCORE = "Invalid_Underscore";
   public static final String UNDERSCORES_IN_LITERALS_NOT_BELOW_17 = "Underscores_In_Literals_Not_Below_17";
   static final char[] charArray_a;
   static final char[] charArray_b;
   static final char[] charArray_c;
   static final char[] charArray_d;
   static final char[] charArray_e;
   static final char[] charArray_f;
   static final char[] charArray_g;
   static final char[] charArray_h;
   static final char[] charArray_i;
   static final char[] charArray_j;
   static final char[] charArray_k;
   static final char[] charArray_l;
   static final char[] charArray_m;
   static final char[] charArray_n;
   static final char[] charArray_o;
   static final char[] charArray_p;
   static final char[] charArray_q;
   static final char[] charArray_r;
   static final char[] charArray_s;
   static final char[] charArray_t;
   static final char[] charArray_u;
   static final char[] charArray_v;
   static final char[] charArray_w;
   static final char[] charArray_x;
   static final char[] charArray_y;
   static final char[] charArray_z;
   static final char[] initCharArray;
   static final int TableSize = 30;
   static final int InternalTableSize = 6;
   public static final int OptimizedLength = 7;
   public final char[][][][] charArray_length;
   public static final char[] TAG_PREFIX;
   public static final int TAG_PREFIX_LENGTH;
   public static final char TAG_POSTFIX = '$';
   public static final int TAG_POSTFIX_LENGTH = 1;
   public static final char[] IDENTITY_COMPARISON_TAG;
   public boolean[] validIdentityComparisonLines;
   public boolean checkUninternedIdentityComparison;
   private NLSTag[] nlsTags;
   protected int nlsTagsPtr;
   public boolean checkNonExternalizedStringLiterals;
   protected int lastPosition;
   public boolean returnOnlyGreater;
   int newEntry2;
   int newEntry3;
   int newEntry4;
   int newEntry5;
   int newEntry6;
   public boolean insideRecovery;
   int[] lookBack;
   protected int nextToken;
   private VanguardScanner vanguardScanner;
   private VanguardParser vanguardParser;
   ConflictedParser activeParser;
   private boolean consumingEllipsisAnnotations;
   public static final int RoundBracket = 0;
   public static final int SquareBracket = 1;
   public static final int CurlyBracket = 2;
   public static final int BracketKinds = 3;
   public static final int LOW_SURROGATE_MIN_VALUE = 56320;
   public static final int HIGH_SURROGATE_MIN_VALUE = 55296;
   public static final int HIGH_SURROGATE_MAX_VALUE = 56319;
   public static final int LOW_SURROGATE_MAX_VALUE = 57343;

   static {
      EMPTY_LINE_ENDS = Util.EMPTY_INT_ARRAY;
      charArray_a = new char[]{'a'};
      charArray_b = new char[]{'b'};
      charArray_c = new char[]{'c'};
      charArray_d = new char[]{'d'};
      charArray_e = new char[]{'e'};
      charArray_f = new char[]{'f'};
      charArray_g = new char[]{'g'};
      charArray_h = new char[]{'h'};
      charArray_i = new char[]{'i'};
      charArray_j = new char[]{'j'};
      charArray_k = new char[]{'k'};
      charArray_l = new char[]{'l'};
      charArray_m = new char[]{'m'};
      charArray_n = new char[]{'n'};
      charArray_o = new char[]{'o'};
      charArray_p = new char[]{'p'};
      charArray_q = new char[]{'q'};
      charArray_r = new char[]{'r'};
      charArray_s = new char[]{'s'};
      charArray_t = new char[]{'t'};
      charArray_u = new char[]{'u'};
      charArray_v = new char[]{'v'};
      charArray_w = new char[]{'w'};
      charArray_x = new char[]{'x'};
      charArray_y = new char[]{'y'};
      charArray_z = new char[]{'z'};
      initCharArray = new char[6];
      TAG_PREFIX = "//$NON-NLS-".toCharArray();
      TAG_PREFIX_LENGTH = TAG_PREFIX.length;
      IDENTITY_COMPARISON_TAG = "//$IDENTITY-COMPARISON$".toCharArray();
   }

   public Scanner() {
      this(false, false, false, 3080192L, (char[][])null, (char[][])null, true);
   }

   public Scanner(boolean tokenizeComments, boolean tokenizeWhiteSpace, boolean checkNonExternalizedStringLiterals, long sourceLevel, long complianceLevel, char[][] taskTags, char[][] taskPriorities, boolean isTaskCaseSensitive) {
      this.useAssertAsAnIndentifier = false;
      this.containsAssertKeyword = false;
      this.useEnumAsAnIndentifier = false;
      this.recordLineSeparator = false;
      this.skipComments = false;
      this.tokenizeComments = false;
      this.tokenizeWhiteSpace = false;
      this.unicodeAsBackSlash = false;
      this.scanningFloatLiteral = false;
      this.commentStops = new int[30];
      this.commentStarts = new int[30];
      this.commentTagStarts = new int[30];
      this.commentPtr = -1;
      this.lastCommentLinePosition = -1;
      this.foundTaskTags = null;
      this.foundTaskPriorities = null;
      this.foundTaskCount = 0;
      this.taskTags = null;
      this.taskPriorities = null;
      this.isTaskCaseSensitive = true;
      this.diet = false;
      this.lineEnds = new int[250];
      this.linePtr = -1;
      this.wasAcr = false;
      this.fakeInModule = false;
      this.inCase = false;
      this.scanContext = null;
      this.insideModuleInfo = false;
      this.charArray_length = new char[7][30][6][];
      this.nlsTags = null;
      this.returnOnlyGreater = false;

      int taskTagsLength;
      int length;
      int taskPrioritiesLength;
      for(taskTagsLength = 0; taskTagsLength < 6; ++taskTagsLength) {
         for(length = 0; length < 30; ++length) {
            for(taskPrioritiesLength = 0; taskPrioritiesLength < 6; ++taskPrioritiesLength) {
               this.charArray_length[taskTagsLength][length][taskPrioritiesLength] = initCharArray;
            }
         }
      }

      this.newEntry2 = 0;
      this.newEntry3 = 0;
      this.newEntry4 = 0;
      this.newEntry5 = 0;
      this.newEntry6 = 0;
      this.insideRecovery = false;
      this.lookBack = new int[2];
      this.nextToken = 0;
      this.activeParser = null;
      this.consumingEllipsisAnnotations = false;
      this.eofPosition = Integer.MAX_VALUE;
      this.tokenizeComments = tokenizeComments;
      this.tokenizeWhiteSpace = tokenizeWhiteSpace;
      this.sourceLevel = sourceLevel;
      this.lookBack[0] = this.lookBack[1] = this.nextToken = 0;
      this.consumingEllipsisAnnotations = false;
      this.complianceLevel = complianceLevel;
      this.checkNonExternalizedStringLiterals = checkNonExternalizedStringLiterals;
      if (taskTags != null) {
         taskTagsLength = taskTags.length;
         length = taskTagsLength;
         if (taskPriorities == null) {
            Util.reverseQuickSort(taskTags, 0, taskTagsLength - 1);
         } else {
            taskPrioritiesLength = taskPriorities.length;
            if (taskPrioritiesLength != taskTagsLength) {
               if (taskPrioritiesLength > taskTagsLength) {
                  System.arraycopy(taskPriorities, 0, taskPriorities = new char[taskTagsLength][], 0, taskTagsLength);
               } else {
                  System.arraycopy(taskTags, 0, taskTags = new char[taskPrioritiesLength][], 0, taskPrioritiesLength);
                  length = taskPrioritiesLength;
               }
            }

            int[] initialIndexes = new int[length];

            for(int i = 0; i < length; initialIndexes[i] = i++) {
            }

            Util.reverseQuickSort(taskTags, 0, length - 1, initialIndexes);
            char[][] temp = new char[length][];

            for(int i = 0; i < length; ++i) {
               temp[i] = taskPriorities[initialIndexes[i]];
            }

            this.taskPriorities = temp;
         }

         this.taskTags = taskTags;
         this.isTaskCaseSensitive = isTaskCaseSensitive;
      }

   }

   public Scanner(boolean tokenizeComments, boolean tokenizeWhiteSpace, boolean checkNonExternalizedStringLiterals, long sourceLevel, char[][] taskTags, char[][] taskPriorities, boolean isTaskCaseSensitive) {
      this(tokenizeComments, tokenizeWhiteSpace, checkNonExternalizedStringLiterals, sourceLevel, sourceLevel, taskTags, taskPriorities, isTaskCaseSensitive);
   }

   public final boolean atEnd() {
      return this.eofPosition <= this.currentPosition;
   }

   public void checkTaskTag(int commentStart, int commentEnd) throws InvalidInputException {
      char[] src = this.source;
      if (this.foundTaskCount <= 0 || this.foundTaskPositions[this.foundTaskCount - 1][0] < commentStart) {
         int foundTaskIndex = this.foundTaskCount;
         char previous = src[commentStart + 1];

         int j;
         int end;
         int j;
         for(int i = commentStart + 2; i < commentEnd && i < this.eofPosition; ++i) {
            char[] tag = null;
            char[] priority = null;
            if (previous != '@') {
               label187:
               for(j = 0; j < this.taskTags.length; ++j) {
                  char[] tag = this.taskTags[j];
                  end = tag.length;
                  if (end != 0 && (!ScannerHelper.isJavaIdentifierStart(this.complianceLevel, tag[0]) || !ScannerHelper.isJavaIdentifierPart(this.complianceLevel, previous))) {
                     for(int t = 0; t < end; ++t) {
                        int x = i + t;
                        char tc;
                        if (x >= this.eofPosition || x >= commentEnd || (j = src[i + t]) != (tc = tag[t]) && (this.isTaskCaseSensitive || ScannerHelper.toLowerCase((char)j) != ScannerHelper.toLowerCase(tc))) {
                           continue label187;
                        }
                     }

                     if (i + end >= commentEnd || !ScannerHelper.isJavaIdentifierPart(this.complianceLevel, src[i + end - 1]) || !ScannerHelper.isJavaIdentifierPart(this.complianceLevel, src[i + end])) {
                        if (this.foundTaskTags == null) {
                           this.foundTaskTags = new char[5][];
                           this.foundTaskMessages = new char[5][];
                           this.foundTaskPriorities = new char[5][];
                           this.foundTaskPositions = new int[5][];
                        } else if (this.foundTaskCount == this.foundTaskTags.length) {
                           System.arraycopy(this.foundTaskTags, 0, this.foundTaskTags = new char[this.foundTaskCount * 2][], 0, this.foundTaskCount);
                           System.arraycopy(this.foundTaskMessages, 0, this.foundTaskMessages = new char[this.foundTaskCount * 2][], 0, this.foundTaskCount);
                           System.arraycopy(this.foundTaskPriorities, 0, this.foundTaskPriorities = new char[this.foundTaskCount * 2][], 0, this.foundTaskCount);
                           System.arraycopy(this.foundTaskPositions, 0, this.foundTaskPositions = new int[this.foundTaskCount * 2][], 0, this.foundTaskCount);
                        }

                        char[] priority = this.taskPriorities != null && j < this.taskPriorities.length ? this.taskPriorities[j] : null;
                        this.foundTaskTags[this.foundTaskCount] = tag;
                        this.foundTaskPriorities[this.foundTaskCount] = priority;
                        this.foundTaskPositions[this.foundTaskCount] = new int[]{i, i + end - 1};
                        this.foundTaskMessages[this.foundTaskCount] = CharOperation.NO_CHAR;
                        ++this.foundTaskCount;
                        i += end - 1;
                        break;
                     }
                  }
               }
            }

            previous = src[i];
         }

         boolean containsEmptyTask = false;

         int i;
         int msgStart;
         for(i = foundTaskIndex; i < this.foundTaskCount; ++i) {
            msgStart = this.foundTaskPositions[i][0] + this.foundTaskTags[i].length;
            j = i + 1 < this.foundTaskCount ? this.foundTaskPositions[i + 1][0] - 1 : commentEnd - 1;
            if (j < msgStart) {
               j = msgStart;
            }

            end = -1;

            for(j = msgStart; j < j; ++j) {
               char c;
               if ((c = src[j]) == '\n' || c == '\r') {
                  end = j - 1;
                  break;
               }
            }

            if (end == -1) {
               for(j = j; j > msgStart; --j) {
                  if (src[j] == '*') {
                     end = j - 1;
                     break;
                  }
               }

               if (end == -1) {
                  end = j;
               }
            }

            if (msgStart == end) {
               containsEmptyTask = true;
            } else {
               while(CharOperation.isWhitespace(src[end]) && msgStart <= end) {
                  --end;
               }

               this.foundTaskPositions[i][1] = end;
               j = end - msgStart + 1;
               char[] message = new char[j];
               System.arraycopy(src, msgStart, message, 0, j);
               this.foundTaskMessages[i] = message;
            }
         }

         if (containsEmptyTask) {
            i = foundTaskIndex;

            for(msgStart = this.foundTaskCount; i < msgStart; ++i) {
               if (this.foundTaskMessages[i].length == 0) {
                  for(j = i + 1; j < msgStart; ++j) {
                     if (this.foundTaskMessages[j].length != 0) {
                        this.foundTaskMessages[i] = this.foundTaskMessages[j];
                        this.foundTaskPositions[i][1] = this.foundTaskPositions[j][1];
                        break;
                     }
                  }
               }
            }
         }

      }
   }

   public char[] getCurrentIdentifierSource() {
      if (this.withoutUnicodePtr != 0) {
         char[] result = new char[this.withoutUnicodePtr];
         System.arraycopy(this.withoutUnicodeBuffer, 1, result, 0, this.withoutUnicodePtr);
         return result;
      } else {
         int length = this.currentPosition - this.startPosition;
         if (length == this.eofPosition) {
            return this.source;
         } else {
            switch (length) {
               case 1:
                  return this.optimizedCurrentTokenSource1();
               case 2:
                  return this.optimizedCurrentTokenSource2();
               case 3:
                  return this.optimizedCurrentTokenSource3();
               case 4:
                  return this.optimizedCurrentTokenSource4();
               case 5:
                  return this.optimizedCurrentTokenSource5();
               case 6:
                  return this.optimizedCurrentTokenSource6();
               default:
                  char[] result = new char[length];
                  System.arraycopy(this.source, this.startPosition, result, 0, length);
                  return result;
            }
         }
      }
   }

   public int getCurrentTokenEndPosition() {
      return this.currentPosition - 1;
   }

   public char[] getCurrentTokenSource() {
      char[] result;
      if (this.withoutUnicodePtr != 0) {
         System.arraycopy(this.withoutUnicodeBuffer, 1, result = new char[this.withoutUnicodePtr], 0, this.withoutUnicodePtr);
      } else {
         int length;
         System.arraycopy(this.source, this.startPosition, result = new char[length = this.currentPosition - this.startPosition], 0, length);
      }

      return result;
   }

   public final String getCurrentTokenString() {
      return this.withoutUnicodePtr != 0 ? new String(this.withoutUnicodeBuffer, 1, this.withoutUnicodePtr) : new String(this.source, this.startPosition, this.currentPosition - this.startPosition);
   }

   public char[] getCurrentTokenSourceString() {
      char[] result;
      if (this.withoutUnicodePtr != 0) {
         System.arraycopy(this.withoutUnicodeBuffer, 2, result = new char[this.withoutUnicodePtr - 2], 0, this.withoutUnicodePtr - 2);
      } else {
         int length;
         System.arraycopy(this.source, this.startPosition + 1, result = new char[length = this.currentPosition - this.startPosition - 2], 0, length);
      }

      return result;
   }

   public final String getCurrentStringLiteral() {
      return this.withoutUnicodePtr != 0 ? new String(this.withoutUnicodeBuffer, 2, this.withoutUnicodePtr - 2) : new String(this.source, this.startPosition + 1, this.currentPosition - this.startPosition - 2);
   }

   public final char[] getRawTokenSource() {
      int length = this.currentPosition - this.startPosition;
      char[] tokenSource = new char[length];
      System.arraycopy(this.source, this.startPosition, tokenSource, 0, length);
      return tokenSource;
   }

   public final char[] getRawTokenSourceEnd() {
      int length = this.eofPosition - this.currentPosition - 1;
      char[] sourceEnd = new char[length];
      System.arraycopy(this.source, this.currentPosition, sourceEnd, 0, length);
      return sourceEnd;
   }

   public int getCurrentTokenStartPosition() {
      return this.startPosition;
   }

   public final int getLineEnd(int lineNumber) {
      if (this.lineEnds != null && this.linePtr != -1) {
         if (lineNumber > this.lineEnds.length + 1) {
            return -1;
         } else if (lineNumber <= 0) {
            return -1;
         } else {
            return lineNumber == this.lineEnds.length + 1 ? this.eofPosition : this.lineEnds[lineNumber - 1];
         }
      } else {
         return -1;
      }
   }

   public final int[] getLineEnds() {
      if (this.linePtr == -1) {
         return EMPTY_LINE_ENDS;
      } else {
         int[] copy;
         System.arraycopy(this.lineEnds, 0, copy = new int[this.linePtr + 1], 0, this.linePtr + 1);
         return copy;
      }
   }

   public final int getLineStart(int lineNumber) {
      if (this.lineEnds != null && this.linePtr != -1) {
         if (lineNumber > this.lineEnds.length + 1) {
            return -1;
         } else if (lineNumber <= 0) {
            return -1;
         } else {
            return lineNumber == 1 ? this.initialPosition : this.lineEnds[lineNumber - 2] + 1;
         }
      } else {
         return -1;
      }
   }

   public final int getNextChar() {
      try {
         if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
            this.getNextUnicodeChar();
         } else {
            this.unicodeAsBackSlash = false;
            if (this.withoutUnicodePtr != 0) {
               this.unicodeStore();
            }
         }

         return this.currentCharacter;
      } catch (IndexOutOfBoundsException var1) {
         return -1;
      } catch (InvalidInputException var2) {
         return -1;
      }
   }

   public final int getNextCharWithBoundChecks() {
      if (this.currentPosition >= this.eofPosition) {
         return -1;
      } else {
         this.currentCharacter = this.source[this.currentPosition++];
         if (this.currentPosition >= this.eofPosition) {
            this.unicodeAsBackSlash = false;
            if (this.withoutUnicodePtr != 0) {
               this.unicodeStore();
            }

            return this.currentCharacter;
         } else {
            if (this.currentCharacter == '\\' && this.source[this.currentPosition] == 'u') {
               try {
                  this.getNextUnicodeChar();
               } catch (InvalidInputException var1) {
                  return -1;
               }
            } else {
               this.unicodeAsBackSlash = false;
               if (this.withoutUnicodePtr != 0) {
                  this.unicodeStore();
               }
            }

            return this.currentCharacter;
         }
      }
   }

   public final boolean getNextChar(char testedChar) {
      if (this.currentPosition >= this.eofPosition) {
         this.unicodeAsBackSlash = false;
         return false;
      } else {
         int temp = this.currentPosition;

         try {
            if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
               this.getNextUnicodeChar();
               if (this.currentCharacter != testedChar) {
                  this.currentPosition = temp;
                  --this.withoutUnicodePtr;
                  return false;
               } else {
                  return true;
               }
            } else if (this.currentCharacter != testedChar) {
               this.currentPosition = temp;
               return false;
            } else {
               this.unicodeAsBackSlash = false;
               if (this.withoutUnicodePtr != 0) {
                  this.unicodeStore();
               }

               return true;
            }
         } catch (IndexOutOfBoundsException var3) {
            this.unicodeAsBackSlash = false;
            this.currentPosition = temp;
            return false;
         } catch (InvalidInputException var4) {
            this.unicodeAsBackSlash = false;
            this.currentPosition = temp;
            return false;
         }
      }
   }

   public final int getNextChar(char testedChar1, char testedChar2) {
      if (this.currentPosition >= this.eofPosition) {
         return -1;
      } else {
         int temp = this.currentPosition;

         try {
            if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
               this.getNextUnicodeChar();
               byte result;
               if (this.currentCharacter == testedChar1) {
                  result = 0;
               } else if (this.currentCharacter == testedChar2) {
                  result = 1;
               } else {
                  this.currentPosition = temp;
                  --this.withoutUnicodePtr;
                  result = -1;
               }

               return result;
            } else {
               byte result;
               if (this.currentCharacter == testedChar1) {
                  result = 0;
               } else {
                  if (this.currentCharacter != testedChar2) {
                     this.currentPosition = temp;
                     return -1;
                  }

                  result = 1;
               }

               if (this.withoutUnicodePtr != 0) {
                  this.unicodeStore();
               }

               return result;
            }
         } catch (IndexOutOfBoundsException var5) {
            this.currentPosition = temp;
            return -1;
         } catch (InvalidInputException var6) {
            this.currentPosition = temp;
            return -1;
         }
      }
   }

   private final void consumeDigits(int radix) throws InvalidInputException {
      this.consumeDigits(radix, false);
   }

   private final void consumeDigits(int radix, boolean expectingDigitFirst) throws InvalidInputException {
      switch (this.consumeDigits0(radix, 1, 2, expectingDigitFirst)) {
         case 1:
            if (this.sourceLevel < 3342336L) {
               throw new InvalidInputException("Underscores_In_Literals_Not_Below_17");
            }
         default:
            return;
         case 2:
            if (this.sourceLevel < 3342336L) {
               throw new InvalidInputException("Underscores_In_Literals_Not_Below_17");
            } else {
               throw new InvalidInputException("Invalid_Underscore");
            }
      }
   }

   private final int consumeDigits0(int radix, int usingUnderscore, int invalidPosition, boolean expectingDigitFirst) throws InvalidInputException {
      int kind = 0;
      if (this.getNextChar('_')) {
         if (expectingDigitFirst) {
            return invalidPosition;
         }

         kind = usingUnderscore;

         while(this.getNextChar('_')) {
         }
      }

      if (!this.getNextCharAsDigit(radix)) {
         return kind == usingUnderscore ? invalidPosition : kind;
      } else {
         while(this.getNextCharAsDigit(radix)) {
         }

         int kind2 = this.consumeDigits0(radix, usingUnderscore, invalidPosition, false);
         return kind2 == 0 ? kind : kind2;
      }
   }

   public final boolean getNextCharAsDigit() throws InvalidInputException {
      if (this.currentPosition >= this.eofPosition) {
         return false;
      } else {
         int temp = this.currentPosition;

         try {
            if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
               this.getNextUnicodeChar();
               if (!ScannerHelper.isDigit(this.currentCharacter)) {
                  this.currentPosition = temp;
                  --this.withoutUnicodePtr;
                  return false;
               } else {
                  return true;
               }
            } else if (!ScannerHelper.isDigit(this.currentCharacter)) {
               this.currentPosition = temp;
               return false;
            } else {
               if (this.withoutUnicodePtr != 0) {
                  this.unicodeStore();
               }

               return true;
            }
         } catch (IndexOutOfBoundsException var2) {
            this.currentPosition = temp;
            return false;
         } catch (InvalidInputException var3) {
            this.currentPosition = temp;
            return false;
         }
      }
   }

   public final boolean getNextCharAsDigit(int radix) {
      if (this.currentPosition >= this.eofPosition) {
         return false;
      } else {
         int temp = this.currentPosition;

         try {
            if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
               this.getNextUnicodeChar();
               if (ScannerHelper.digit(this.currentCharacter, radix) == -1) {
                  this.currentPosition = temp;
                  --this.withoutUnicodePtr;
                  return false;
               } else {
                  return true;
               }
            } else if (ScannerHelper.digit(this.currentCharacter, radix) == -1) {
               this.currentPosition = temp;
               return false;
            } else {
               if (this.withoutUnicodePtr != 0) {
                  this.unicodeStore();
               }

               return true;
            }
         } catch (IndexOutOfBoundsException var3) {
            this.currentPosition = temp;
            return false;
         } catch (InvalidInputException var4) {
            this.currentPosition = temp;
            return false;
         }
      }
   }

   public boolean getNextCharAsJavaIdentifierPartWithBoundCheck() {
      int pos = this.currentPosition;
      if (pos >= this.eofPosition) {
         return false;
      } else {
         int temp2 = this.withoutUnicodePtr;

         try {
            boolean unicode = false;
            this.currentCharacter = this.source[this.currentPosition++];
            if (this.currentPosition < this.eofPosition && this.currentCharacter == '\\' && this.source[this.currentPosition] == 'u') {
               this.getNextUnicodeChar();
               unicode = true;
            }

            char c = this.currentCharacter;
            boolean isJavaIdentifierPart = false;
            if (c >= '\ud800' && c <= '\udbff') {
               if (this.complianceLevel < 3211264L) {
                  this.currentPosition = pos;
                  this.withoutUnicodePtr = temp2;
                  return false;
               }

               char low = (char)this.getNextCharWithBoundChecks();
               if (low < '\udc00' || low > '\udfff') {
                  this.currentPosition = pos;
                  this.withoutUnicodePtr = temp2;
                  return false;
               }

               isJavaIdentifierPart = ScannerHelper.isJavaIdentifierPart(this.complianceLevel, c, low);
            } else {
               if (c >= '\udc00' && c <= '\udfff') {
                  this.currentPosition = pos;
                  this.withoutUnicodePtr = temp2;
                  return false;
               }

               isJavaIdentifierPart = ScannerHelper.isJavaIdentifierPart(this.complianceLevel, c);
            }

            if (unicode) {
               if (!isJavaIdentifierPart) {
                  this.currentPosition = pos;
                  this.withoutUnicodePtr = temp2;
                  return false;
               } else {
                  return true;
               }
            } else if (!isJavaIdentifierPart) {
               this.currentPosition = pos;
               return false;
            } else {
               if (this.withoutUnicodePtr != 0) {
                  this.unicodeStore();
               }

               return true;
            }
         } catch (InvalidInputException var7) {
            this.currentPosition = pos;
            this.withoutUnicodePtr = temp2;
            return false;
         }
      }
   }

   public boolean getNextCharAsJavaIdentifierPart() {
      int pos;
      if ((pos = this.currentPosition) >= this.eofPosition) {
         return false;
      } else {
         int temp2 = this.withoutUnicodePtr;

         try {
            boolean unicode = false;
            if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
               this.getNextUnicodeChar();
               unicode = true;
            }

            char c = this.currentCharacter;
            boolean isJavaIdentifierPart = false;
            if (c >= '\ud800' && c <= '\udbff') {
               if (this.complianceLevel < 3211264L) {
                  this.currentPosition = pos;
                  this.withoutUnicodePtr = temp2;
                  return false;
               }

               char low = (char)this.getNextChar();
               if (low < '\udc00' || low > '\udfff') {
                  this.currentPosition = pos;
                  this.withoutUnicodePtr = temp2;
                  return false;
               }

               isJavaIdentifierPart = ScannerHelper.isJavaIdentifierPart(this.complianceLevel, c, low);
            } else {
               if (c >= '\udc00' && c <= '\udfff') {
                  this.currentPosition = pos;
                  this.withoutUnicodePtr = temp2;
                  return false;
               }

               isJavaIdentifierPart = ScannerHelper.isJavaIdentifierPart(this.complianceLevel, c);
            }

            if (unicode) {
               if (!isJavaIdentifierPart) {
                  this.currentPosition = pos;
                  this.withoutUnicodePtr = temp2;
                  return false;
               } else {
                  return true;
               }
            } else if (!isJavaIdentifierPart) {
               this.currentPosition = pos;
               return false;
            } else {
               if (this.withoutUnicodePtr != 0) {
                  this.unicodeStore();
               }

               return true;
            }
         } catch (IndexOutOfBoundsException var7) {
            this.currentPosition = pos;
            this.withoutUnicodePtr = temp2;
            return false;
         } catch (InvalidInputException var8) {
            this.currentPosition = pos;
            this.withoutUnicodePtr = temp2;
            return false;
         }
      }
   }

   public int scanIdentifier() throws InvalidInputException {
      int whiteStart = false;
      this.withoutUnicodePtr = 0;
      int whiteStart = this.currentPosition;
      boolean hasWhiteSpaces = false;
      boolean checkIfUnicode = false;

      boolean isWhiteSpace;
      int offset;
      int unicodePtr;
      do {
         unicodePtr = this.withoutUnicodePtr;
         offset = this.currentPosition;
         this.startPosition = this.currentPosition;
         if (this.currentPosition >= this.eofPosition) {
            if (this.tokenizeWhiteSpace && whiteStart != this.currentPosition - 1) {
               --this.currentPosition;
               this.startPosition = whiteStart;
               return 1000;
            }

            return 61;
         }

         this.currentCharacter = this.source[this.currentPosition++];
         checkIfUnicode = this.currentPosition < this.eofPosition && this.currentCharacter == '\\' && this.source[this.currentPosition] == 'u';
         if (checkIfUnicode) {
            isWhiteSpace = this.jumpOverUnicodeWhiteSpace();
            offset = this.currentPosition - offset;
         } else {
            offset = this.currentPosition - offset;
            switch (this.currentCharacter) {
               case '\t':
               case '\n':
               case '\f':
               case '\r':
               case ' ':
                  isWhiteSpace = true;
                  break;
               default:
                  isWhiteSpace = false;
            }
         }

         if (isWhiteSpace) {
            hasWhiteSpaces = true;
         }
      } while(isWhiteSpace);

      if (hasWhiteSpaces) {
         if (this.tokenizeWhiteSpace) {
            this.currentPosition -= offset;
            this.startPosition = whiteStart;
            if (checkIfUnicode) {
               this.withoutUnicodePtr = unicodePtr;
            }

            return 1000;
         }

         if (checkIfUnicode) {
            this.withoutUnicodePtr = 0;
            this.unicodeStore();
         } else {
            this.withoutUnicodePtr = 0;
         }
      }

      char c = this.currentCharacter;
      if (c < 128) {
         if ((ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[c] & 64) != 0) {
            return this.scanIdentifierOrKeywordWithBoundCheck();
         } else {
            return 129;
         }
      } else {
         boolean isJavaIdStart;
         if (c >= '\ud800' && c <= '\udbff') {
            if (this.complianceLevel < 3211264L) {
               throw new InvalidInputException("Invalid_Unicode_Escape");
            }

            char low = (char)this.getNextCharWithBoundChecks();
            if (low < '\udc00' || low > '\udfff') {
               throw new InvalidInputException("Invalid_Low_Surrogate");
            }

            isJavaIdStart = ScannerHelper.isJavaIdentifierStart(this.complianceLevel, c, low);
         } else {
            if (c >= '\udc00' && c <= '\udfff') {
               if (this.complianceLevel < 3211264L) {
                  throw new InvalidInputException("Invalid_Unicode_Escape");
               }

               throw new InvalidInputException("Invalid_High_Surrogate");
            }

            isJavaIdStart = ScannerHelper.isJavaIdentifierStart(this.complianceLevel, c);
         }

         if (isJavaIdStart) {
            return this.scanIdentifierOrKeywordWithBoundCheck();
         } else {
            return 129;
         }
      }
   }

   public void ungetToken(int unambiguousToken) {
      if (this.nextToken != 0) {
         throw new ArrayIndexOutOfBoundsException("Single cell array overflow");
      } else {
         this.nextToken = unambiguousToken;
      }
   }

   private void updateCase(int token) {
      if (token == 101) {
         this.inCase = true;
      }

      if (token == 62 || token == 98) {
         this.inCase = false;
      }

   }

   public int getNextToken() throws InvalidInputException {
      int token;
      if (this.nextToken != 0) {
         token = this.nextToken;
         this.nextToken = 0;
         return token;
      } else {
         if (this.scanContext == null) {
            this.scanContext = this.isInModuleDeclaration() ? Scanner.ScanContext.EXPECTING_KEYWORD : Scanner.ScanContext.INACTIVE;
         }

         token = this.getNextToken0();
         if (this.areRestrictedModuleKeywordsActive()) {
            if (isRestrictedKeyword(token)) {
               token = this.disambiguatedRestrictedKeyword(token);
            }

            this.updateScanContext(token);
         }

         if (this.activeParser == null) {
            return token;
         } else {
            if (token != 23 && token != 11 && token != 37 && token != 98) {
               if (token == 122) {
                  this.consumingEllipsisAnnotations = false;
               }
            } else {
               token = this.disambiguatedToken(token);
            }

            this.lookBack[0] = this.lookBack[1];
            this.lookBack[1] = token;
            this.updateCase(token);
            return token;
         }
      }
   }

   protected int getNextToken0() throws InvalidInputException {
      this.wasAcr = false;
      if (this.diet) {
         this.jumpOverMethodBody();
         this.diet = false;
         return this.currentPosition > this.eofPosition ? 61 : 33;
      } else {
         int whiteStart = 0;

         try {
            while(true) {
               this.withoutUnicodePtr = 0;
               whiteStart = this.currentPosition;
               boolean hasWhiteSpaces = false;
               boolean checkIfUnicode = false;

               boolean isWhiteSpace;
               int offset;
               int unicodePtr;
               do {
                  unicodePtr = this.withoutUnicodePtr;
                  offset = this.currentPosition;
                  this.startPosition = this.currentPosition;

                  try {
                     checkIfUnicode = (this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u';
                  } catch (IndexOutOfBoundsException var18) {
                     if (this.tokenizeWhiteSpace && whiteStart != this.currentPosition - 1) {
                        --this.currentPosition;
                        this.startPosition = whiteStart;
                        return 1000;
                     }

                     if (this.currentPosition > this.eofPosition) {
                        return 61;
                     }
                  }

                  if (this.currentPosition > this.eofPosition) {
                     if (this.tokenizeWhiteSpace && whiteStart != this.currentPosition - 1) {
                        --this.currentPosition;
                        this.startPosition = whiteStart;
                        return 1000;
                     }

                     return 61;
                  }

                  if (checkIfUnicode) {
                     isWhiteSpace = this.jumpOverUnicodeWhiteSpace();
                     offset = this.currentPosition - offset;
                  } else {
                     offset = this.currentPosition - offset;
                     if ((this.currentCharacter == '\r' || this.currentCharacter == '\n') && this.recordLineSeparator) {
                        this.pushLineSeparator();
                     }

                     switch (this.currentCharacter) {
                        case '\t':
                        case '\n':
                        case '\f':
                        case '\r':
                        case ' ':
                           isWhiteSpace = true;
                           break;
                        default:
                           isWhiteSpace = false;
                     }
                  }

                  if (isWhiteSpace) {
                     hasWhiteSpaces = true;
                  }
               } while(isWhiteSpace);

               if (hasWhiteSpaces) {
                  if (this.tokenizeWhiteSpace) {
                     this.currentPosition -= offset;
                     this.startPosition = whiteStart;
                     if (checkIfUnicode) {
                        this.withoutUnicodePtr = unicodePtr;
                     }

                     return 1000;
                  }

                  if (checkIfUnicode) {
                     this.withoutUnicodePtr = 0;
                     this.unicodeStore();
                  } else {
                     this.withoutUnicodePtr = 0;
                  }
               }

               int lookAhead;
               boolean isJavadoc;
               int start;
               switch (this.currentCharacter) {
                  case '\u001a':
                     if (this.atEnd()) {
                        return 61;
                     }

                     throw new InvalidInputException("Ctrl-Z");
                  case '!':
                     if (this.getNextChar('=')) {
                        return 20;
                     }

                     return 63;
                  case '"':
                     try {
                        this.unicodeAsBackSlash = false;
                        boolean isUnicode = false;
                        if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                           this.getNextUnicodeChar();
                           isUnicode = true;
                        } else if (this.withoutUnicodePtr != 0) {
                           this.unicodeStore();
                        }

                        while(this.currentCharacter != '"') {
                           if (this.currentPosition >= this.eofPosition) {
                              throw new InvalidInputException("Unterminated_String");
                           }

                           if (this.currentCharacter == '\n' || this.currentCharacter == '\r') {
                              if (isUnicode) {
                                 start = this.currentPosition;

                                 for(int lookAhead = 0; lookAhead < 50; ++lookAhead) {
                                    if (this.currentPosition >= this.eofPosition) {
                                       this.currentPosition = start;
                                       break;
                                    }

                                    if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                                       isUnicode = true;
                                       this.getNextUnicodeChar();
                                    } else {
                                       isUnicode = false;
                                    }

                                    if (!isUnicode && this.currentCharacter == '\n') {
                                       --this.currentPosition;
                                       break;
                                    }

                                    if (this.currentCharacter == '"') {
                                       throw new InvalidInputException("Invalid_Char_In_String");
                                    }
                                 }
                              } else {
                                 --this.currentPosition;
                              }

                              throw new InvalidInputException("Invalid_Char_In_String");
                           }

                           if (this.currentCharacter == '\\') {
                              if (this.unicodeAsBackSlash) {
                                 --this.withoutUnicodePtr;
                                 this.unicodeAsBackSlash = false;
                                 if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                                    this.getNextUnicodeChar();
                                    isUnicode = true;
                                    --this.withoutUnicodePtr;
                                 } else {
                                    isUnicode = false;
                                 }
                              } else {
                                 if (this.withoutUnicodePtr == 0) {
                                    this.unicodeInitializeBuffer(this.currentPosition - this.startPosition);
                                 }

                                 --this.withoutUnicodePtr;
                                 this.currentCharacter = this.source[this.currentPosition++];
                              }

                              this.scanEscapeCharacter();
                              if (this.withoutUnicodePtr != 0) {
                                 this.unicodeStore();
                              }
                           }

                           this.unicodeAsBackSlash = false;
                           if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                              this.getNextUnicodeChar();
                              isUnicode = true;
                           } else {
                              isUnicode = false;
                              if (this.withoutUnicodePtr != 0) {
                                 this.unicodeStore();
                              }
                           }
                        }

                        return 46;
                     } catch (IndexOutOfBoundsException var16) {
                        --this.currentPosition;
                        throw new InvalidInputException("Unterminated_String");
                     } catch (InvalidInputException var17) {
                        if (var17.getMessage().equals("Invalid_Escape")) {
                           for(start = 0; start < 50 && this.currentPosition + start != this.eofPosition && this.source[this.currentPosition + start] != '\n'; ++start) {
                              if (this.source[this.currentPosition + start] == '"') {
                                 this.currentPosition += start + 1;
                                 break;
                              }
                           }
                        }

                        throw var17;
                     }
                  case '%':
                     if (this.getNextChar('=')) {
                        return 94;
                     }

                     return 9;
                  case '&':
                     if ((lookAhead = this.getNextChar('&', '=')) == 0) {
                        return 30;
                     }

                     if (lookAhead > 0) {
                        return 91;
                     }

                     return 21;
                  case '\'':
                     if ((lookAhead = this.getNextChar('\n', '\r')) == 0) {
                        throw new InvalidInputException("Invalid_Character_Constant");
                     }

                     if (lookAhead > 0) {
                        for(start = 0; start < 3 && this.currentPosition + start != this.eofPosition && this.source[this.currentPosition + start] != '\n'; ++start) {
                           if (this.source[this.currentPosition + start] == '\'') {
                              this.currentPosition += start + 1;
                              break;
                           }
                        }

                        throw new InvalidInputException("Invalid_Character_Constant");
                     }

                     if (this.getNextChar('\'')) {
                        for(lookAhead = 0; lookAhead < 3 && this.currentPosition + lookAhead != this.eofPosition && this.source[this.currentPosition + lookAhead] != '\n'; ++lookAhead) {
                           if (this.source[this.currentPosition + lookAhead] == '\'') {
                              this.currentPosition += lookAhead + 1;
                              break;
                           }
                        }

                        throw new InvalidInputException("Invalid_Character_Constant");
                     }

                     if (this.getNextChar('\\')) {
                        if (this.unicodeAsBackSlash) {
                           this.unicodeAsBackSlash = false;
                           if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                              this.getNextUnicodeChar();
                           } else if (this.withoutUnicodePtr != 0) {
                              this.unicodeStore();
                           }
                        } else {
                           this.currentCharacter = this.source[this.currentPosition++];
                        }

                        this.scanEscapeCharacter();
                     } else {
                        this.unicodeAsBackSlash = false;
                        checkIfUnicode = false;

                        try {
                           checkIfUnicode = (this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u';
                        } catch (IndexOutOfBoundsException var15) {
                           --this.currentPosition;
                           throw new InvalidInputException("Invalid_Character_Constant");
                        }

                        if (checkIfUnicode) {
                           this.getNextUnicodeChar();
                        } else if (this.withoutUnicodePtr != 0) {
                           this.unicodeStore();
                        }
                     }

                     if (this.getNextChar('\'')) {
                        return 45;
                     }

                     for(lookAhead = 0; lookAhead < 20 && this.currentPosition + lookAhead != this.eofPosition && this.source[this.currentPosition + lookAhead] != '\n'; ++lookAhead) {
                        if (this.source[this.currentPosition + lookAhead] == '\'') {
                           this.currentPosition += lookAhead + 1;
                           break;
                        }
                     }

                     throw new InvalidInputException("Invalid_Character_Constant");
                  case '(':
                     return 23;
                  case ')':
                     return 25;
                  case '*':
                     if (this.getNextChar('=')) {
                        return 89;
                     }

                     return 8;
                  case '+':
                     if ((lookAhead = this.getNextChar('+', '=')) == 0) {
                        return 2;
                     }

                     if (lookAhead > 0) {
                        return 87;
                     }

                     return 4;
                  case ',':
                     return 32;
                  case '-':
                     if ((lookAhead = this.getNextChar('-', '=')) == 0) {
                        return 3;
                     }

                     if (lookAhead > 0) {
                        return 88;
                     }

                     if (this.getNextChar('>')) {
                        return 98;
                     }

                     return 5;
                  case '.':
                     if (this.getNextCharAsDigit()) {
                        return this.scanNumber(true);
                     }

                     int temp = this.currentPosition;
                     if (this.getNextChar('.')) {
                        if (this.getNextChar('.')) {
                           return 122;
                        }

                        this.currentPosition = temp;
                        return 1;
                     }

                     this.currentPosition = temp;
                     return 1;
                  case '/':
                     if (this.skipComments) {
                        return this.getNextChar('=') ? 90 : 10;
                     }

                     lookAhead = this.getNextChar('/', '*');
                     if (lookAhead == 0) {
                        this.lastCommentLinePosition = this.currentPosition;

                        try {
                           if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                              this.getNextUnicodeChar();
                           }

                           if (this.currentCharacter == '\\' && this.source[this.currentPosition] == '\\') {
                              ++this.currentPosition;
                           }

                           isJavadoc = false;

                           while(this.currentCharacter != '\r' && this.currentCharacter != '\n') {
                              if (this.currentPosition >= this.eofPosition) {
                                 this.lastCommentLinePosition = this.currentPosition++;
                                 throw new IndexOutOfBoundsException();
                              }

                              this.lastCommentLinePosition = this.currentPosition;
                              isJavadoc = false;
                              if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                                 this.getNextUnicodeChar();
                                 isJavadoc = true;
                              }

                              if (this.currentCharacter == '\\' && this.source[this.currentPosition] == '\\') {
                                 ++this.currentPosition;
                              }
                           }

                           if (this.currentCharacter == '\r' && this.eofPosition > this.currentPosition) {
                              if (this.source[this.currentPosition] == '\n') {
                                 ++this.currentPosition;
                                 this.currentCharacter = '\n';
                              } else if (this.source[this.currentPosition] == '\\' && this.source[this.currentPosition + 1] == 'u') {
                                 this.getNextUnicodeChar();
                                 isJavadoc = true;
                              }
                           }

                           this.recordComment(1001);
                           if (this.taskTags != null) {
                              this.checkTaskTag(this.startPosition, this.currentPosition);
                           }

                           if (this.currentCharacter == '\r' || this.currentCharacter == '\n') {
                              if ((this.checkNonExternalizedStringLiterals || this.checkUninternedIdentityComparison) && this.lastPosition < this.currentPosition) {
                                 this.parseTags();
                              }

                              if (this.recordLineSeparator) {
                                 if (isJavadoc) {
                                    this.pushUnicodeLineSeparator();
                                 } else {
                                    this.pushLineSeparator();
                                 }
                              }
                           }

                           if (this.tokenizeComments) {
                              return 1001;
                           }
                        } catch (IndexOutOfBoundsException var19) {
                           --this.currentPosition;
                           this.recordComment(1001);
                           if (this.taskTags != null) {
                              this.checkTaskTag(this.startPosition, this.currentPosition);
                           }

                           if ((this.checkNonExternalizedStringLiterals || this.checkUninternedIdentityComparison) && this.lastPosition < this.currentPosition) {
                              this.parseTags();
                           }

                           if (this.tokenizeComments) {
                              return 1001;
                           }

                           ++this.currentPosition;
                        }
                     } else {
                        if (lookAhead <= 0) {
                           return this.getNextChar('=') ? 90 : 10;
                        }

                        try {
                           isJavadoc = false;
                           boolean star = false;
                           boolean isUnicode = false;
                           this.unicodeAsBackSlash = false;
                           if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                              this.getNextUnicodeChar();
                              isUnicode = true;
                           } else {
                              isUnicode = false;
                              if (this.withoutUnicodePtr != 0) {
                                 this.unicodeStore();
                              }
                           }

                           if (this.currentCharacter == '*') {
                              isJavadoc = true;
                              star = true;
                           }

                           if ((this.currentCharacter == '\r' || this.currentCharacter == '\n') && this.recordLineSeparator) {
                              if (isUnicode) {
                                 this.pushUnicodeLineSeparator();
                              } else {
                                 this.pushLineSeparator();
                              }
                           }

                           isUnicode = false;
                           int previous = this.currentPosition;
                           if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                              this.getNextUnicodeChar();
                              isUnicode = true;
                           } else {
                              isUnicode = false;
                           }

                           if (this.currentCharacter == '\\' && this.source[this.currentPosition] == '\\') {
                              ++this.currentPosition;
                           }

                           if (this.currentCharacter == '/') {
                              isJavadoc = false;
                           }

                           int firstTag = 0;

                           while(this.currentCharacter != '/' || !star) {
                              if (this.currentPosition >= this.eofPosition) {
                                 throw new InvalidInputException("Unterminated_Comment");
                              }

                              if ((this.currentCharacter == '\r' || this.currentCharacter == '\n') && this.recordLineSeparator) {
                                 if (isUnicode) {
                                    this.pushUnicodeLineSeparator();
                                 } else {
                                    this.pushLineSeparator();
                                 }
                              }

                              switch (this.currentCharacter) {
                                 case '*':
                                    star = true;
                                    break;
                                 case '@':
                                    if (firstTag == 0 && this.isFirstTag()) {
                                       firstTag = previous;
                                    }
                                 default:
                                    star = false;
                              }

                              previous = this.currentPosition;
                              if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                                 this.getNextUnicodeChar();
                                 isUnicode = true;
                              } else {
                                 isUnicode = false;
                              }

                              if (this.currentCharacter == '\\' && this.source[this.currentPosition] == '\\') {
                                 ++this.currentPosition;
                              }
                           }

                           int token = isJavadoc ? 1003 : 1002;
                           this.recordComment(token);
                           this.commentTagStarts[this.commentPtr] = firstTag;
                           if (this.taskTags != null) {
                              this.checkTaskTag(this.startPosition, this.currentPosition);
                           }

                           if (this.tokenizeComments) {
                              return token;
                           }
                        } catch (IndexOutOfBoundsException var20) {
                           --this.currentPosition;
                           throw new InvalidInputException("Unterminated_Comment");
                        }
                     }
                     break;
                  case ':':
                     if (this.getNextChar(':')) {
                        return 7;
                     }

                     return 62;
                  case ';':
                     return 26;
                  case '<':
                     if ((lookAhead = this.getNextChar('=', '<')) == 0) {
                        return 12;
                     }

                     if (lookAhead > 0) {
                        if (this.getNextChar('=')) {
                           return 95;
                        }

                        return 18;
                     }

                     return 11;
                  case '=':
                     if (this.getNextChar('=')) {
                        return 19;
                     }

                     return 72;
                  case '>':
                     if (this.returnOnlyGreater) {
                        return 15;
                     }

                     if ((lookAhead = this.getNextChar('=', '>')) == 0) {
                        return 13;
                     }

                     if (lookAhead > 0) {
                        if ((lookAhead = this.getNextChar('=', '>')) == 0) {
                           return 96;
                        }

                        if (lookAhead > 0) {
                           if (this.getNextChar('=')) {
                              return 97;
                           }

                           return 16;
                        }

                        return 14;
                     }

                     return 15;
                  case '?':
                     return 29;
                  case '@':
                     return 37;
                  case '[':
                     return 6;
                  case ']':
                     return 66;
                  case '^':
                     if (this.getNextChar('=')) {
                        return 93;
                     }

                     return 24;
                  case '{':
                     return 49;
                  case '|':
                     if ((lookAhead = this.getNextChar('|', '=')) == 0) {
                        return 31;
                     }

                     if (lookAhead > 0) {
                        return 92;
                     }

                     return 28;
                  case '}':
                     return 33;
                  case '~':
                     return 64;
                  default:
                     char c = this.currentCharacter;
                     if (c < 128) {
                        if ((ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[c] & 64) != 0) {
                           return this.scanIdentifierOrKeyword();
                        }

                        if ((ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[c] & 4) != 0) {
                           return this.scanNumber(false);
                        }

                        return 129;
                     }

                     if (c >= '\ud800' && c <= '\udbff') {
                        if (this.complianceLevel < 3211264L) {
                           throw new InvalidInputException("Invalid_Unicode_Escape");
                        }

                        char low = (char)this.getNextChar();
                        if (low < '\udc00' || low > '\udfff') {
                           throw new InvalidInputException("Invalid_Low_Surrogate");
                        }

                        isJavadoc = ScannerHelper.isJavaIdentifierStart(this.complianceLevel, c, low);
                     } else {
                        if (c >= '\udc00' && c <= '\udfff') {
                           if (this.complianceLevel < 3211264L) {
                              throw new InvalidInputException("Invalid_Unicode_Escape");
                           }

                           throw new InvalidInputException("Invalid_High_Surrogate");
                        }

                        isJavadoc = ScannerHelper.isJavaIdentifierStart(this.complianceLevel, c);
                     }

                     if (isJavadoc) {
                        return this.scanIdentifierOrKeyword();
                     }

                     if (ScannerHelper.isDigit(this.currentCharacter)) {
                        return this.scanNumber(false);
                     }

                     return 129;
               }
            }
         } catch (IndexOutOfBoundsException var21) {
            if (this.tokenizeWhiteSpace && whiteStart != this.currentPosition - 1) {
               --this.currentPosition;
               this.startPosition = whiteStart;
               return 1000;
            } else {
               return 61;
            }
         }
      }
   }

   public void getNextUnicodeChar() throws InvalidInputException {
      int c1 = false;
      int c2 = false;
      int c3 = false;
      int c4 = false;
      int unicodeSize = 6;
      ++this.currentPosition;
      if (this.currentPosition >= this.eofPosition) {
         --this.currentPosition;
         throw new InvalidInputException("Invalid_Unicode_Escape");
      } else {
         while(this.source[this.currentPosition] == 'u') {
            ++this.currentPosition;
            if (this.currentPosition >= this.eofPosition) {
               --this.currentPosition;
               throw new InvalidInputException("Invalid_Unicode_Escape");
            }

            ++unicodeSize;
         }

         if (this.currentPosition + 4 > this.eofPosition) {
            this.currentPosition += this.eofPosition - this.currentPosition;
            throw new InvalidInputException("Invalid_Unicode_Escape");
         } else {
            int c1;
            int c2;
            int c3;
            int c4;
            if ((c1 = ScannerHelper.getHexadecimalValue(this.source[this.currentPosition++])) <= 15 && c1 >= 0 && (c2 = ScannerHelper.getHexadecimalValue(this.source[this.currentPosition++])) <= 15 && c2 >= 0 && (c3 = ScannerHelper.getHexadecimalValue(this.source[this.currentPosition++])) <= 15 && c3 >= 0 && (c4 = ScannerHelper.getHexadecimalValue(this.source[this.currentPosition++])) <= 15 && c4 >= 0) {
               this.currentCharacter = (char)(((c1 * 16 + c2) * 16 + c3) * 16 + c4);
               if (this.withoutUnicodePtr == 0) {
                  this.unicodeInitializeBuffer(this.currentPosition - unicodeSize - this.startPosition);
               }

               this.unicodeStore();
               this.unicodeAsBackSlash = this.currentCharacter == '\\';
            } else {
               throw new InvalidInputException("Invalid_Unicode_Escape");
            }
         }
      }
   }

   public NLSTag[] getNLSTags() {
      int length = this.nlsTagsPtr;
      if (length != 0) {
         NLSTag[] result = new NLSTag[length];
         System.arraycopy(this.nlsTags, 0, result, 0, length);
         this.nlsTagsPtr = 0;
         return result;
      } else {
         return null;
      }
   }

   public boolean[] getIdentityComparisonLines() {
      boolean[] retVal = this.validIdentityComparisonLines;
      this.validIdentityComparisonLines = null;
      return retVal;
   }

   public char[] getSource() {
      return this.source;
   }

   protected boolean isFirstTag() {
      return true;
   }

   public final void jumpOverMethodBody() {
      this.wasAcr = false;
      int found = 1;

      try {
         label432:
         do {
            label430:
            while(true) {
               this.withoutUnicodePtr = 0;

               boolean isWhiteSpace;
               do {
                  this.startPosition = this.currentPosition;
                  if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                     isWhiteSpace = this.jumpOverUnicodeWhiteSpace();
                  } else {
                     if (this.recordLineSeparator && (this.currentCharacter == '\r' || this.currentCharacter == '\n')) {
                        this.pushLineSeparator();
                     }

                     isWhiteSpace = CharOperation.isWhitespace(this.currentCharacter);
                  }
               } while(isWhiteSpace);

               boolean isJavadoc;
               switch (this.currentCharacter) {
                  case '"':
                     try {
                        try {
                           this.unicodeAsBackSlash = false;
                           if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                              this.getNextUnicodeChar();
                           } else if (this.withoutUnicodePtr != 0) {
                              this.unicodeStore();
                           }
                        } catch (InvalidInputException var15) {
                        }

                        while(true) {
                           if (this.currentCharacter == '"') {
                              continue label430;
                           }

                           if (this.currentPosition >= this.eofPosition) {
                              return;
                           }

                           if (this.currentCharacter == '\r') {
                              if (this.source[this.currentPosition] == '\n') {
                                 ++this.currentPosition;
                              }
                              continue label430;
                           }

                           if (this.currentCharacter == '\n') {
                              continue label430;
                           }

                           if (this.currentCharacter == '\\') {
                              try {
                                 if (this.unicodeAsBackSlash) {
                                    this.unicodeAsBackSlash = false;
                                    if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                                       this.getNextUnicodeChar();
                                    } else if (this.withoutUnicodePtr != 0) {
                                       this.unicodeStore();
                                    }
                                 } else {
                                    this.currentCharacter = this.source[this.currentPosition++];
                                 }

                                 this.scanEscapeCharacter();
                              } catch (InvalidInputException var13) {
                              }
                           }

                           try {
                              this.unicodeAsBackSlash = false;
                              if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                                 this.getNextUnicodeChar();
                              } else if (this.withoutUnicodePtr != 0) {
                                 this.unicodeStore();
                              }
                           } catch (InvalidInputException var14) {
                           }
                        }
                     } catch (IndexOutOfBoundsException var16) {
                        return;
                     }
                  case '\'':
                     boolean test = this.getNextChar('\\');
                     if (test) {
                        try {
                           if (this.unicodeAsBackSlash) {
                              this.unicodeAsBackSlash = false;
                              if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                                 this.getNextUnicodeChar();
                              } else if (this.withoutUnicodePtr != 0) {
                                 this.unicodeStore();
                              }
                           } else {
                              this.currentCharacter = this.source[this.currentPosition++];
                           }

                           this.scanEscapeCharacter();
                        } catch (InvalidInputException var12) {
                        }
                     } else {
                        try {
                           this.unicodeAsBackSlash = false;
                           if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                              this.getNextUnicodeChar();
                           } else if (this.withoutUnicodePtr != 0) {
                              this.unicodeStore();
                           }
                        } catch (InvalidInputException var11) {
                        }
                     }

                     this.getNextChar('\'');
                     break;
                  case '/':
                     int test;
                     if ((test = this.getNextChar('/', '*')) == 0) {
                        try {
                           this.lastCommentLinePosition = this.currentPosition;
                           if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                              this.getNextUnicodeChar();
                           }

                           if (this.currentCharacter == '\\' && this.source[this.currentPosition] == '\\') {
                              ++this.currentPosition;
                           }

                           isJavadoc = false;

                           while(this.currentCharacter != '\r' && this.currentCharacter != '\n') {
                              if (this.currentPosition >= this.eofPosition) {
                                 this.lastCommentLinePosition = this.currentPosition++;
                                 throw new IndexOutOfBoundsException();
                              }

                              this.lastCommentLinePosition = this.currentPosition;
                              isJavadoc = false;
                              if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                                 isJavadoc = true;
                                 this.getNextUnicodeChar();
                              }

                              if (this.currentCharacter == '\\' && this.source[this.currentPosition] == '\\') {
                                 ++this.currentPosition;
                              }
                           }

                           if (this.currentCharacter == '\r' && this.eofPosition > this.currentPosition) {
                              if (this.source[this.currentPosition] == '\n') {
                                 ++this.currentPosition;
                                 this.currentCharacter = '\n';
                              } else if (this.source[this.currentPosition] == '\\' && this.source[this.currentPosition + 1] == 'u') {
                                 isJavadoc = true;
                                 this.getNextUnicodeChar();
                              }
                           }

                           this.recordComment(1001);
                           if (!this.recordLineSeparator || this.currentCharacter != '\r' && this.currentCharacter != '\n') {
                              continue;
                           }

                           if ((this.checkNonExternalizedStringLiterals || this.checkUninternedIdentityComparison) && this.lastPosition < this.currentPosition) {
                              this.parseTags();
                           }

                           if (this.recordLineSeparator) {
                              if (isJavadoc) {
                                 this.pushUnicodeLineSeparator();
                              } else {
                                 this.pushLineSeparator();
                              }
                           }
                        } catch (IndexOutOfBoundsException var9) {
                           --this.currentPosition;
                           this.recordComment(1001);
                           if ((this.checkNonExternalizedStringLiterals || this.checkUninternedIdentityComparison) && this.lastPosition < this.currentPosition) {
                              this.parseTags();
                           }

                           if (!this.tokenizeComments) {
                              ++this.currentPosition;
                           }
                        }
                     } else {
                        if (test <= 0) {
                           continue;
                        }

                        isJavadoc = false;

                        try {
                           boolean star = false;
                           boolean isUnicode = false;
                           this.unicodeAsBackSlash = false;
                           if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                              this.getNextUnicodeChar();
                              isUnicode = true;
                           } else {
                              isUnicode = false;
                              if (this.withoutUnicodePtr != 0) {
                                 this.unicodeStore();
                              }
                           }

                           if (this.currentCharacter == '*') {
                              isJavadoc = true;
                              star = true;
                           }

                           if ((this.currentCharacter == '\r' || this.currentCharacter == '\n') && this.recordLineSeparator) {
                              if (isUnicode) {
                                 this.pushUnicodeLineSeparator();
                              } else {
                                 this.pushLineSeparator();
                              }
                           }

                           isUnicode = false;
                           int previous = this.currentPosition;
                           if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                              this.getNextUnicodeChar();
                              isUnicode = true;
                           } else {
                              isUnicode = false;
                           }

                           if (this.currentCharacter == '\\' && this.source[this.currentPosition] == '\\') {
                              ++this.currentPosition;
                           }

                           if (this.currentCharacter == '/') {
                              isJavadoc = false;
                           }

                           int firstTag = 0;

                           while(this.currentCharacter != '/' || !star) {
                              if (this.currentPosition >= this.eofPosition) {
                                 return;
                              }

                              if ((this.currentCharacter == '\r' || this.currentCharacter == '\n') && this.recordLineSeparator) {
                                 if (isUnicode) {
                                    this.pushUnicodeLineSeparator();
                                 } else {
                                    this.pushLineSeparator();
                                 }
                              }

                              switch (this.currentCharacter) {
                                 case '*':
                                    star = true;
                                    break;
                                 case '@':
                                    if (firstTag == 0 && this.isFirstTag()) {
                                       firstTag = previous;
                                    }
                                 default:
                                    star = false;
                              }

                              previous = this.currentPosition;
                              if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                                 this.getNextUnicodeChar();
                                 isUnicode = true;
                              } else {
                                 isUnicode = false;
                              }

                              if (this.currentCharacter == '\\' && this.source[this.currentPosition] == '\\') {
                                 ++this.currentPosition;
                              }
                           }

                           this.recordComment(isJavadoc ? 1003 : 1002);
                           this.commentTagStarts[this.commentPtr] = firstTag;
                        } catch (IndexOutOfBoundsException var10) {
                           return;
                        }
                     }
                     break;
                  case '{':
                     ++found;
                     break;
                  case '}':
                     --found;
                     continue label432;
                  default:
                     try {
                        char c = this.currentCharacter;
                        if (c < 128) {
                           if ((ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[c] & 64) != 0) {
                              this.scanIdentifierOrKeyword();
                           } else if ((ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[c] & 4) != 0) {
                              this.scanNumber(false);
                           }
                        } else {
                           if (c >= '\ud800' && c <= '\udbff') {
                              if (this.complianceLevel < 3211264L) {
                                 throw new InvalidInputException("Invalid_Unicode_Escape");
                              }

                              char low = (char)this.getNextChar();
                              if (low < '\udc00' || low > '\udfff') {
                                 continue;
                              }

                              isJavadoc = ScannerHelper.isJavaIdentifierStart(this.complianceLevel, c, low);
                           } else {
                              if (c >= '\udc00' && c <= '\udfff') {
                                 continue;
                              }

                              isJavadoc = ScannerHelper.isJavaIdentifierStart(this.complianceLevel, c);
                           }

                           if (isJavadoc) {
                              this.scanIdentifierOrKeyword();
                           }
                        }
                     } catch (InvalidInputException var17) {
                     }
               }
            }
         } while(found != 0);

         return;
      } catch (IndexOutOfBoundsException var18) {
      } catch (InvalidInputException var19) {
      }

   }

   public final boolean jumpOverUnicodeWhiteSpace() throws InvalidInputException {
      this.wasAcr = false;
      this.getNextUnicodeChar();
      return CharOperation.isWhitespace(this.currentCharacter);
   }

   final char[] optimizedCurrentTokenSource1() {
      char charOne = this.source[this.startPosition];
      switch (charOne) {
         case 'a':
            return charArray_a;
         case 'b':
            return charArray_b;
         case 'c':
            return charArray_c;
         case 'd':
            return charArray_d;
         case 'e':
            return charArray_e;
         case 'f':
            return charArray_f;
         case 'g':
            return charArray_g;
         case 'h':
            return charArray_h;
         case 'i':
            return charArray_i;
         case 'j':
            return charArray_j;
         case 'k':
            return charArray_k;
         case 'l':
            return charArray_l;
         case 'm':
            return charArray_m;
         case 'n':
            return charArray_n;
         case 'o':
            return charArray_o;
         case 'p':
            return charArray_p;
         case 'q':
            return charArray_q;
         case 'r':
            return charArray_r;
         case 's':
            return charArray_s;
         case 't':
            return charArray_t;
         case 'u':
            return charArray_u;
         case 'v':
            return charArray_v;
         case 'w':
            return charArray_w;
         case 'x':
            return charArray_x;
         case 'y':
            return charArray_y;
         case 'z':
            return charArray_z;
         default:
            return new char[]{charOne};
      }
   }

   final char[] optimizedCurrentTokenSource2() {
      char[] src = this.source;
      int start = this.startPosition;
      char c0;
      char c1;
      int hash = (((c0 = src[start]) << 6) + (c1 = src[start + 1])) % 30;
      char[][] table = this.charArray_length[0][hash];
      int i = this.newEntry2;

      char[] charArray;
      do {
         ++i;
         if (i >= 6) {
            i = -1;
            int max = this.newEntry2;

            char[] charArray;
            do {
               ++i;
               if (i > max) {
                  ++max;
                  if (max >= 6) {
                     max = 0;
                  }

                  System.arraycopy(src, start, charArray = new char[2], 0, 2);
                  return table[this.newEntry2 = max] = charArray;
               }

               charArray = table[i];
            } while(c0 != charArray[0] || c1 != charArray[1]);

            return charArray;
         }

         charArray = table[i];
      } while(c0 != charArray[0] || c1 != charArray[1]);

      return charArray;
   }

   final char[] optimizedCurrentTokenSource3() {
      char[] src = this.source;
      int start = this.startPosition;
      char c1 = src[start + 1];
      char c0;
      char c2;
      int hash = (((c0 = src[start]) << 6) + (c2 = src[start + 2])) % 30;
      char[][] table = this.charArray_length[1][hash];
      int i = this.newEntry3;

      char[] charArray;
      do {
         ++i;
         if (i >= 6) {
            i = -1;
            int max = this.newEntry3;

            char[] charArray;
            do {
               ++i;
               if (i > max) {
                  ++max;
                  if (max >= 6) {
                     max = 0;
                  }

                  System.arraycopy(src, start, charArray = new char[3], 0, 3);
                  return table[this.newEntry3 = max] = charArray;
               }

               charArray = table[i];
            } while(c0 != charArray[0] || c1 != charArray[1] || c2 != charArray[2]);

            return charArray;
         }

         charArray = table[i];
      } while(c0 != charArray[0] || c1 != charArray[1] || c2 != charArray[2]);

      return charArray;
   }

   final char[] optimizedCurrentTokenSource4() {
      char[] src = this.source;
      int start = this.startPosition;
      char c1 = src[start + 1];
      char c3 = src[start + 3];
      char c0;
      char c2;
      int hash = (((c0 = src[start]) << 6) + (c2 = src[start + 2])) % 30;
      char[][] table = this.charArray_length[2][hash];
      int i = this.newEntry4;

      char[] charArray;
      do {
         ++i;
         if (i >= 6) {
            i = -1;
            int max = this.newEntry4;

            char[] charArray;
            do {
               ++i;
               if (i > max) {
                  ++max;
                  if (max >= 6) {
                     max = 0;
                  }

                  System.arraycopy(src, start, charArray = new char[4], 0, 4);
                  return table[this.newEntry4 = max] = charArray;
               }

               charArray = table[i];
            } while(c0 != charArray[0] || c1 != charArray[1] || c2 != charArray[2] || c3 != charArray[3]);

            return charArray;
         }

         charArray = table[i];
      } while(c0 != charArray[0] || c1 != charArray[1] || c2 != charArray[2] || c3 != charArray[3]);

      return charArray;
   }

   final char[] optimizedCurrentTokenSource5() {
      char[] src = this.source;
      int start = this.startPosition;
      char c1 = src[start + 1];
      char c3 = src[start + 3];
      char c0;
      char c2;
      char c4;
      int hash = (((c0 = src[start]) << 12) + ((c2 = src[start + 2]) << 6) + (c4 = src[start + 4])) % 30;
      char[][] table = this.charArray_length[3][hash];
      int i = this.newEntry5;

      char[] charArray;
      do {
         ++i;
         if (i >= 6) {
            i = -1;
            int max = this.newEntry5;

            char[] charArray;
            do {
               ++i;
               if (i > max) {
                  ++max;
                  if (max >= 6) {
                     max = 0;
                  }

                  System.arraycopy(src, start, charArray = new char[5], 0, 5);
                  return table[this.newEntry5 = max] = charArray;
               }

               charArray = table[i];
            } while(c0 != charArray[0] || c1 != charArray[1] || c2 != charArray[2] || c3 != charArray[3] || c4 != charArray[4]);

            return charArray;
         }

         charArray = table[i];
      } while(c0 != charArray[0] || c1 != charArray[1] || c2 != charArray[2] || c3 != charArray[3] || c4 != charArray[4]);

      return charArray;
   }

   final char[] optimizedCurrentTokenSource6() {
      char[] src = this.source;
      int start = this.startPosition;
      char c1 = src[start + 1];
      char c3 = src[start + 3];
      char c5 = src[start + 5];
      char c0;
      char c2;
      char c4;
      int hash = (((c0 = src[start]) << 12) + ((c2 = src[start + 2]) << 6) + (c4 = src[start + 4])) % 30;
      char[][] table = this.charArray_length[4][hash];
      int i = this.newEntry6;

      char[] charArray;
      do {
         ++i;
         if (i >= 6) {
            i = -1;
            int max = this.newEntry6;

            char[] charArray;
            do {
               ++i;
               if (i > max) {
                  ++max;
                  if (max >= 6) {
                     max = 0;
                  }

                  System.arraycopy(src, start, charArray = new char[6], 0, 6);
                  return table[this.newEntry6 = max] = charArray;
               }

               charArray = table[i];
            } while(c0 != charArray[0] || c1 != charArray[1] || c2 != charArray[2] || c3 != charArray[3] || c4 != charArray[4] || c5 != charArray[5]);

            return charArray;
         }

         charArray = table[i];
      } while(c0 != charArray[0] || c1 != charArray[1] || c2 != charArray[2] || c3 != charArray[3] || c4 != charArray[4] || c5 != charArray[5]);

      return charArray;
   }

   public boolean isInModuleDeclaration() {
      return this.fakeInModule || this.insideModuleInfo || this.activeParser != null && this.activeParser.isParsingModuleDeclaration();
   }

   protected boolean areRestrictedModuleKeywordsActive() {
      return this.scanContext != null && this.scanContext != Scanner.ScanContext.INACTIVE;
   }

   void updateScanContext(int token) {
      switch (token) {
         case 1:
         case 27:
         case 32:
         case 37:
         case 106:
         case 113:
         case 116:
         case 117:
         case 118:
         case 119:
         case 121:
         case 124:
         case 125:
            this.scanContext = Scanner.ScanContext.EXPECTING_IDENTIFIER;
            break;
         case 22:
            this.scanContext = Scanner.ScanContext.EXPECTING_KEYWORD;
            break;
         case 25:
         case 26:
         case 33:
            this.scanContext = Scanner.ScanContext.EXPECTING_KEYWORD;
            break;
         case 49:
            this.scanContext = Scanner.ScanContext.EXPECTING_KEYWORD;
            break;
         case 114:
            this.scanContext = Scanner.ScanContext.EXPECTING_KEYWORD;
            break;
         case 115:
            this.scanContext = Scanner.ScanContext.AFTER_REQUIRES;
      }

   }

   private void parseTags() {
      int position = 0;
      int currentStartPosition = this.startPosition;
      int currentLinePtr = this.linePtr;
      if (currentLinePtr >= 0) {
         position = this.lineEnds[currentLinePtr] + 1;
      }

      while(ScannerHelper.isWhitespace(this.source[position])) {
         ++position;
      }

      if (currentStartPosition != position) {
         char[] s = null;
         int sourceEnd = this.currentPosition;
         int sourceStart = currentStartPosition;
         int sourceDelta = 0;
         char[] s;
         if (this.withoutUnicodePtr != 0) {
            System.arraycopy(this.withoutUnicodeBuffer, 1, s = new char[this.withoutUnicodePtr], 0, this.withoutUnicodePtr);
            sourceEnd = this.withoutUnicodePtr;
            sourceStart = 1;
            sourceDelta = currentStartPosition;
         } else {
            s = this.source;
         }

         int pos;
         int start;
         int end;
         if (this.checkNonExternalizedStringLiterals && (pos = CharOperation.indexOf(TAG_PREFIX, s, true, sourceStart, sourceEnd)) != -1) {
            if (this.nlsTags == null) {
               this.nlsTags = new NLSTag[10];
               this.nlsTagsPtr = 0;
            }

            for(; pos != -1; pos = CharOperation.indexOf(TAG_PREFIX, s, true, end, sourceEnd)) {
               start = pos + TAG_PREFIX_LENGTH;
               end = CharOperation.indexOf('$', s, start, sourceEnd);
               if (end != -1) {
                  NLSTag currentTag = null;
                  int currentLine = currentLinePtr + 1;

                  try {
                     currentTag = new NLSTag(pos + sourceDelta, end + sourceDelta, currentLine, this.extractInt(s, start, end));
                  } catch (NumberFormatException var13) {
                     currentTag = new NLSTag(pos + sourceDelta, end + sourceDelta, currentLine, -1);
                  }

                  if (this.nlsTagsPtr == this.nlsTags.length) {
                     System.arraycopy(this.nlsTags, 0, this.nlsTags = new NLSTag[this.nlsTagsPtr + 10], 0, this.nlsTagsPtr);
                  }

                  this.nlsTags[this.nlsTagsPtr++] = currentTag;
               } else {
                  end = start;
               }
            }
         }

         if (this.checkUninternedIdentityComparison && CharOperation.indexOf(IDENTITY_COMPARISON_TAG, s, true, sourceStart, sourceEnd) != -1) {
            if (this.validIdentityComparisonLines == null) {
               this.validIdentityComparisonLines = new boolean[0];
            }

            start = currentLinePtr + 1;
            end = this.validIdentityComparisonLines.length;
            System.arraycopy(this.validIdentityComparisonLines, 0, this.validIdentityComparisonLines = new boolean[start + 1], 0, end);
            this.validIdentityComparisonLines[start] = true;
         }

      }
   }

   private int extractInt(char[] array, int start, int end) {
      int value = 0;

      for(int i = start; i < end; ++i) {
         char currentChar = array[i];
         int digit = false;
         byte digit;
         switch (currentChar) {
            case '0':
               digit = 0;
               break;
            case '1':
               digit = 1;
               break;
            case '2':
               digit = 2;
               break;
            case '3':
               digit = 3;
               break;
            case '4':
               digit = 4;
               break;
            case '5':
               digit = 5;
               break;
            case '6':
               digit = 6;
               break;
            case '7':
               digit = 7;
               break;
            case '8':
               digit = 8;
               break;
            case '9':
               digit = 9;
               break;
            default:
               throw new NumberFormatException();
         }

         value *= 10;
         if (digit < 0) {
            throw new NumberFormatException();
         }

         value += digit;
      }

      return value;
   }

   public final void pushLineSeparator() {
      int separatorPos;
      int length;
      if (this.currentCharacter == '\r') {
         separatorPos = this.currentPosition - 1;
         if (this.linePtr >= 0 && this.lineEnds[this.linePtr] >= separatorPos) {
            return;
         }

         length = this.lineEnds.length;
         if (++this.linePtr >= length) {
            System.arraycopy(this.lineEnds, 0, this.lineEnds = new int[2 * length + 250], 0, length);
         }

         this.lineEnds[this.linePtr] = separatorPos;

         try {
            if (this.source[this.currentPosition] == '\n') {
               this.lineEnds[this.linePtr] = this.currentPosition++;
               this.wasAcr = false;
            } else {
               this.wasAcr = true;
            }
         } catch (IndexOutOfBoundsException var3) {
            this.wasAcr = true;
         }
      } else if (this.currentCharacter == '\n') {
         if (this.wasAcr && this.lineEnds[this.linePtr] == this.currentPosition - 2) {
            this.lineEnds[this.linePtr] = this.currentPosition - 1;
         } else {
            separatorPos = this.currentPosition - 1;
            if (this.linePtr >= 0 && this.lineEnds[this.linePtr] >= separatorPos) {
               return;
            }

            length = this.lineEnds.length;
            if (++this.linePtr >= length) {
               System.arraycopy(this.lineEnds, 0, this.lineEnds = new int[2 * length + 250], 0, length);
            }

            this.lineEnds[this.linePtr] = separatorPos;
         }

         this.wasAcr = false;
      }

   }

   public final void pushUnicodeLineSeparator() {
      if (this.currentCharacter == '\r') {
         if (this.source[this.currentPosition] == '\n') {
            this.wasAcr = false;
         } else {
            this.wasAcr = true;
         }
      } else if (this.currentCharacter == '\n') {
         this.wasAcr = false;
      }

   }

   public void recordComment(int token) {
      int commentStart = this.startPosition;
      int stopPosition = this.currentPosition;
      switch (token) {
         case 1001:
            commentStart = -this.startPosition;
            stopPosition = -this.lastCommentLinePosition;
            break;
         case 1002:
            stopPosition = -this.currentPosition;
      }

      int length = this.commentStops.length;
      if (++this.commentPtr >= length) {
         int newLength = length + 300;
         System.arraycopy(this.commentStops, 0, this.commentStops = new int[newLength], 0, length);
         System.arraycopy(this.commentStarts, 0, this.commentStarts = new int[newLength], 0, length);
         System.arraycopy(this.commentTagStarts, 0, this.commentTagStarts = new int[newLength], 0, length);
      }

      this.commentStops[this.commentPtr] = stopPosition;
      this.commentStarts[this.commentPtr] = commentStart;
   }

   public void resetTo(int begin, int end) {
      this.resetTo(begin, end, this.isInModuleDeclaration());
   }

   public void resetTo(int begin, int end, boolean isModuleInfo) {
      this.resetTo(begin, end, isModuleInfo, (ScanContext)null);
   }

   public void resetTo(int begin, int end, boolean isModuleInfo, ScanContext context) {
      this.diet = false;
      this.initialPosition = this.startPosition = this.currentPosition = begin;
      if (this.source != null && this.source.length < end) {
         this.eofPosition = this.source.length;
      } else {
         this.eofPosition = end < Integer.MAX_VALUE ? end + 1 : end;
      }

      this.commentPtr = -1;
      this.foundTaskCount = 0;
      this.lookBack[0] = this.lookBack[1] = this.nextToken = 0;
      this.consumingEllipsisAnnotations = false;
      this.insideModuleInfo = isModuleInfo;
      this.scanContext = context == null ? this.getScanContext(begin) : context;
   }

   private ScanContext getScanContext(int begin) {
      if (!this.isInModuleDeclaration()) {
         return Scanner.ScanContext.INACTIVE;
      } else if (begin == 0) {
         return Scanner.ScanContext.EXPECTING_KEYWORD;
      } else {
         CompilerOptions options = new CompilerOptions();
         options.complianceLevel = this.complianceLevel;
         options.sourceLevel = this.sourceLevel;
         ScanContextDetector parser = new ScanContextDetector(options);
         return parser.getScanContext(this.source, begin - 1);
      }
   }

   protected final void scanEscapeCharacter() throws InvalidInputException {
      switch (this.currentCharacter) {
         case '"':
            this.currentCharacter = '"';
            break;
         case '\'':
            this.currentCharacter = '\'';
            break;
         case '\\':
            this.currentCharacter = '\\';
            break;
         case 'b':
            this.currentCharacter = '\b';
            break;
         case 'f':
            this.currentCharacter = '\f';
            break;
         case 'n':
            this.currentCharacter = '\n';
            break;
         case 'r':
            this.currentCharacter = '\r';
            break;
         case 't':
            this.currentCharacter = '\t';
            break;
         default:
            int number = ScannerHelper.getHexadecimalValue(this.currentCharacter);
            if (number < 0 || number > 7) {
               throw new InvalidInputException("Invalid_Escape");
            }

            boolean zeroToThreeNot = number > 3;
            if (ScannerHelper.isDigit(this.currentCharacter = this.source[this.currentPosition++])) {
               int digit = ScannerHelper.getHexadecimalValue(this.currentCharacter);
               if (digit >= 0 && digit <= 7) {
                  number = number * 8 + digit;
                  if (ScannerHelper.isDigit(this.currentCharacter = this.source[this.currentPosition++])) {
                     if (zeroToThreeNot) {
                        --this.currentPosition;
                     } else {
                        digit = ScannerHelper.getHexadecimalValue(this.currentCharacter);
                        if (digit >= 0 && digit <= 7) {
                           number = number * 8 + digit;
                        } else {
                           --this.currentPosition;
                        }
                     }
                  } else {
                     --this.currentPosition;
                  }
               } else {
                  --this.currentPosition;
               }
            } else {
               --this.currentPosition;
            }

            if (number > 255) {
               throw new InvalidInputException("Invalid_Escape");
            }

            this.currentCharacter = (char)number;
      }

   }

   public int scanIdentifierOrKeywordWithBoundCheck() {
      this.useAssertAsAnIndentifier = false;
      this.useEnumAsAnIndentifier = false;
      char[] src = this.source;
      int length = this.eofPosition;

      int index;
      label47:
      while((index = this.currentPosition) < length) {
         char c = src[index];
         if (c < 128) {
            if ((ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[c] & 60) != 0) {
               if (this.withoutUnicodePtr != 0) {
                  this.currentCharacter = c;
                  this.unicodeStore();
               }

               ++this.currentPosition;
               continue;
            }

            if ((ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[c] & 258) != 0) {
               this.currentCharacter = c;
               break;
            } else {
               while(true) {
                  if (!this.getNextCharAsJavaIdentifierPartWithBoundCheck()) {
                     break label47;
                  }
               }
            }
         }

         while(true) {
            if (!this.getNextCharAsJavaIdentifierPartWithBoundCheck()) {
               break label47;
            }
         }
      }

      char[] data;
      if (this.withoutUnicodePtr == 0) {
         if ((length = this.currentPosition - this.startPosition) == 1) {
            return 22;
         }

         data = this.source;
         index = this.startPosition;
      } else {
         if ((length = this.withoutUnicodePtr) == 1) {
            return 22;
         }

         data = this.withoutUnicodeBuffer;
         index = 1;
      }

      return this.internalScanIdentifierOrKeyword(index, length, data);
   }

   public int scanIdentifierOrKeyword() {
      this.useAssertAsAnIndentifier = false;
      this.useEnumAsAnIndentifier = false;
      char[] src = this.source;
      int length = this.eofPosition;

      int index;
      label47:
      while((index = this.currentPosition) < length) {
         char c = src[index];
         if (c < 128) {
            if ((ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[c] & 60) != 0) {
               if (this.withoutUnicodePtr != 0) {
                  this.currentCharacter = c;
                  this.unicodeStore();
               }

               ++this.currentPosition;
               continue;
            }

            if ((ScannerHelper.OBVIOUS_IDENT_CHAR_NATURES[c] & 258) != 0) {
               this.currentCharacter = c;
               break;
            } else {
               while(true) {
                  if (!this.getNextCharAsJavaIdentifierPart()) {
                     break label47;
                  }
               }
            }
         }

         while(true) {
            if (!this.getNextCharAsJavaIdentifierPart()) {
               break label47;
            }
         }
      }

      char[] data;
      if (this.withoutUnicodePtr == 0) {
         if ((length = this.currentPosition - this.startPosition) == 1) {
            return 22;
         }

         data = this.source;
         index = this.startPosition;
      } else {
         if ((length = this.withoutUnicodePtr) == 1) {
            return 22;
         }

         data = this.withoutUnicodeBuffer;
         index = 1;
      }

      return this.internalScanIdentifierOrKeyword(index, length, data);
   }

   private int internalScanIdentifierOrKeyword(int index, int length, char[] data) {
      switch (data[index]) {
         case 'a':
            switch (length) {
               case 6:
                  ++index;
                  if (data[index] == 's') {
                     ++index;
                     if (data[index] == 's') {
                        ++index;
                        if (data[index] == 'e') {
                           ++index;
                           if (data[index] == 'r') {
                              ++index;
                              if (data[index] == 't') {
                                 if (this.sourceLevel >= 3145728L) {
                                    this.containsAssertKeyword = true;
                                    return 76;
                                 }

                                 this.useAssertAsAnIndentifier = true;
                                 return 22;
                              }
                           }
                        }
                     }
                  }

                  return 22;
               case 7:
               default:
                  return 22;
               case 8:
                  ++index;
                  if (data[index] == 'b') {
                     ++index;
                     if (data[index] == 's') {
                        ++index;
                        if (data[index] == 't') {
                           ++index;
                           if (data[index] == 'r') {
                              ++index;
                              if (data[index] == 'a') {
                                 ++index;
                                 if (data[index] == 'c') {
                                    ++index;
                                    if (data[index] == 't') {
                                       return 52;
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }

                  return 22;
            }
         case 'b':
            switch (length) {
               case 4:
                  ++index;
                  if (data[index] == 'y') {
                     ++index;
                     if (data[index] == 't') {
                        ++index;
                        if (data[index] == 'e') {
                           return 100;
                        }
                     }
                  }

                  return 22;
               case 5:
                  ++index;
                  if (data[index] == 'r') {
                     ++index;
                     if (data[index] == 'e') {
                        ++index;
                        if (data[index] == 'a') {
                           ++index;
                           if (data[index] == 'k') {
                              return 77;
                           }
                        }
                     }
                  }

                  return 22;
               case 6:
               default:
                  return 22;
               case 7:
                  ++index;
                  if (data[index] == 'o') {
                     ++index;
                     if (data[index] == 'o') {
                        ++index;
                        if (data[index] == 'l') {
                           ++index;
                           if (data[index] == 'e') {
                              ++index;
                              if (data[index] == 'a') {
                                 ++index;
                                 if (data[index] == 'n') {
                                    return 99;
                                 }
                              }
                           }
                        }
                     }
                  }

                  return 22;
            }
         case 'c':
            switch (length) {
               case 4:
                  ++index;
                  if (data[index] == 'a') {
                     ++index;
                     if (data[index] == 's') {
                        ++index;
                        if (data[index] == 'e') {
                           return 101;
                        }
                     }

                     return 22;
                  } else {
                     if (data[index] == 'h') {
                        ++index;
                        if (data[index] == 'a') {
                           ++index;
                           if (data[index] == 'r') {
                              return 103;
                           }
                        }
                     }

                     return 22;
                  }
               case 5:
                  ++index;
                  if (data[index] == 'a') {
                     ++index;
                     if (data[index] == 't') {
                        ++index;
                        if (data[index] == 'c') {
                           ++index;
                           if (data[index] == 'h') {
                              return 102;
                           }
                        }
                     }

                     return 22;
                  } else if (data[index] == 'l') {
                     ++index;
                     if (data[index] == 'a') {
                        ++index;
                        if (data[index] == 's') {
                           ++index;
                           if (data[index] == 's') {
                              return 67;
                           }
                        }
                     }

                     return 22;
                  } else {
                     if (data[index] == 'o') {
                        ++index;
                        if (data[index] == 'n') {
                           ++index;
                           if (data[index] == 's') {
                              ++index;
                              if (data[index] == 't') {
                                 return 127;
                              }
                           }
                        }
                     }

                     return 22;
                  }
               case 6:
               case 7:
               default:
                  return 22;
               case 8:
                  ++index;
                  if (data[index] == 'o') {
                     ++index;
                     if (data[index] == 'n') {
                        ++index;
                        if (data[index] == 't') {
                           ++index;
                           if (data[index] == 'i') {
                              ++index;
                              if (data[index] == 'n') {
                                 ++index;
                                 if (data[index] == 'u') {
                                    ++index;
                                    if (data[index] == 'e') {
                                       return 78;
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }

                  return 22;
            }
         case 'd':
            switch (length) {
               case 2:
                  ++index;
                  if (data[index] == 'o') {
                     return 79;
                  }

                  return 22;
               case 3:
               case 4:
               case 5:
               default:
                  return 22;
               case 6:
                  ++index;
                  if (data[index] == 'o') {
                     ++index;
                     if (data[index] == 'u') {
                        ++index;
                        if (data[index] == 'b') {
                           ++index;
                           if (data[index] == 'l') {
                              ++index;
                              if (data[index] == 'e') {
                                 return 104;
                              }
                           }
                        }
                     }
                  }

                  return 22;
               case 7:
                  ++index;
                  if (data[index] == 'e') {
                     ++index;
                     if (data[index] == 'f') {
                        ++index;
                        if (data[index] == 'a') {
                           ++index;
                           if (data[index] == 'u') {
                              ++index;
                              if (data[index] == 'l') {
                                 ++index;
                                 if (data[index] == 't') {
                                    return 73;
                                 }
                              }
                           }
                        }
                     }
                  }

                  return 22;
            }
         case 'e':
            switch (length) {
               case 4:
                  ++index;
                  if (data[index] == 'l') {
                     ++index;
                     if (data[index] == 's') {
                        ++index;
                        if (data[index] == 'e') {
                           return 112;
                        }
                     }

                     return 22;
                  } else {
                     if (data[index] == 'n') {
                        ++index;
                        if (data[index] == 'u') {
                           ++index;
                           if (data[index] == 'm') {
                              if (this.sourceLevel >= 3211264L) {
                                 return 71;
                              }

                              this.useEnumAsAnIndentifier = true;
                              return 22;
                           }
                        }
                     }

                     return 22;
                  }
               case 5:
               case 6:
               default:
                  return 22;
               case 7:
                  ++index;
                  if (data[index] == 'x') {
                     ++index;
                     if (data[index] == 't') {
                        ++index;
                        if (data[index] == 'e') {
                           ++index;
                           if (data[index] == 'n') {
                              ++index;
                              if (data[index] == 'd') {
                                 ++index;
                                 if (data[index] == 's') {
                                    return 86;
                                 }
                              }
                           }
                        }
                     }

                     if (this.areRestrictedModuleKeywordsActive() && data[index] == 'p') {
                        ++index;
                        if (data[index] == 'o') {
                           ++index;
                           if (data[index] == 'r') {
                              ++index;
                              if (data[index] == 't') {
                                 ++index;
                                 if (data[index] == 's') {
                                    return 116;
                                 }
                              }
                           }
                        }
                     }

                     return 22;
                  }

                  return 22;
            }
         case 'f':
            switch (length) {
               case 3:
                  ++index;
                  if (data[index] == 'o') {
                     ++index;
                     if (data[index] == 'r') {
                        return 80;
                     }
                  }

                  return 22;
               case 4:
               case 6:
               default:
                  return 22;
               case 5:
                  ++index;
                  if (data[index] == 'i') {
                     ++index;
                     if (data[index] == 'n') {
                        ++index;
                        if (data[index] == 'a') {
                           ++index;
                           if (data[index] == 'l') {
                              return 53;
                           }
                        }
                     }

                     return 22;
                  } else if (data[index] == 'l') {
                     ++index;
                     if (data[index] == 'o') {
                        ++index;
                        if (data[index] == 'a') {
                           ++index;
                           if (data[index] == 't') {
                              return 105;
                           }
                        }
                     }

                     return 22;
                  } else {
                     if (data[index] == 'a') {
                        ++index;
                        if (data[index] == 'l') {
                           ++index;
                           if (data[index] == 's') {
                              ++index;
                              if (data[index] == 'e') {
                                 return 38;
                              }
                           }
                        }
                     }

                     return 22;
                  }
               case 7:
                  ++index;
                  if (data[index] == 'i') {
                     ++index;
                     if (data[index] == 'n') {
                        ++index;
                        if (data[index] == 'a') {
                           ++index;
                           if (data[index] == 'l') {
                              ++index;
                              if (data[index] == 'l') {
                                 ++index;
                                 if (data[index] == 'y') {
                                    return 111;
                                 }
                              }
                           }
                        }
                     }
                  }

                  return 22;
            }
         case 'g':
            if (length == 4) {
               ++index;
               if (data[index] == 'o') {
                  ++index;
                  if (data[index] == 't') {
                     ++index;
                     if (data[index] == 'o') {
                        return 128;
                     }
                  }
               }
            }

            return 22;
         case 'h':
         case 'j':
         case 'k':
         case 'q':
         default:
            return 22;
         case 'i':
            switch (length) {
               case 2:
                  ++index;
                  if (data[index] == 'f') {
                     return 81;
                  }

                  return 22;
               case 3:
                  ++index;
                  if (data[index] == 'n') {
                     ++index;
                     if (data[index] == 't') {
                        return 107;
                     }
                  }

                  return 22;
               case 4:
               case 5:
               case 7:
               case 8:
               default:
                  return 22;
               case 6:
                  ++index;
                  if (data[index] == 'm') {
                     ++index;
                     if (data[index] == 'p') {
                        ++index;
                        if (data[index] == 'o') {
                           ++index;
                           if (data[index] == 'r') {
                              ++index;
                              if (data[index] == 't') {
                                 return 106;
                              }
                           }
                        }
                     }
                  }

                  return 22;
               case 9:
                  ++index;
                  if (data[index] == 'n') {
                     ++index;
                     if (data[index] == 't') {
                        ++index;
                        if (data[index] == 'e') {
                           ++index;
                           if (data[index] == 'r') {
                              ++index;
                              if (data[index] == 'f') {
                                 ++index;
                                 if (data[index] == 'a') {
                                    ++index;
                                    if (data[index] == 'c') {
                                       ++index;
                                       if (data[index] == 'e') {
                                          return 70;
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }

                  return 22;
               case 10:
                  ++index;
                  if (data[index] == 'm') {
                     ++index;
                     if (data[index] == 'p') {
                        ++index;
                        if (data[index] == 'l') {
                           ++index;
                           if (data[index] == 'e') {
                              ++index;
                              if (data[index] == 'm') {
                                 ++index;
                                 if (data[index] == 'e') {
                                    ++index;
                                    if (data[index] == 'n') {
                                       ++index;
                                       if (data[index] == 't') {
                                          ++index;
                                          if (data[index] == 's') {
                                             return 123;
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }

                     return 22;
                  } else {
                     if (data[index] == 'n') {
                        ++index;
                        if (data[index] == 's') {
                           ++index;
                           if (data[index] == 't') {
                              ++index;
                              if (data[index] == 'a') {
                                 ++index;
                                 if (data[index] == 'n') {
                                    ++index;
                                    if (data[index] == 'c') {
                                       ++index;
                                       if (data[index] == 'e') {
                                          ++index;
                                          if (data[index] == 'o') {
                                             ++index;
                                             if (data[index] == 'f') {
                                                return 17;
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }

                     return 22;
                  }
            }
         case 'l':
            if (length == 4) {
               ++index;
               if (data[index] == 'o') {
                  ++index;
                  if (data[index] == 'n') {
                     ++index;
                     if (data[index] == 'g') {
                        return 108;
                     }
                  }
               }
            }

            return 22;
         case 'm':
            switch (length) {
               case 6:
                  if (this.areRestrictedModuleKeywordsActive()) {
                     ++index;
                     if (data[index] == 'o') {
                        ++index;
                        if (data[index] == 'd') {
                           ++index;
                           if (data[index] == 'u') {
                              ++index;
                              if (data[index] == 'l') {
                                 ++index;
                                 if (data[index] == 'e') {
                                    return 113;
                                 }
                              }
                           }
                        }
                     }
                  }

                  return 22;
               default:
                  return 22;
            }
         case 'n':
            switch (length) {
               case 3:
                  ++index;
                  if (data[index] == 'e') {
                     ++index;
                     if (data[index] == 'w') {
                        return 36;
                     }
                  }

                  return 22;
               case 4:
                  ++index;
                  if (data[index] == 'u') {
                     ++index;
                     if (data[index] == 'l') {
                        ++index;
                        if (data[index] == 'l') {
                           return 39;
                        }
                     }
                  }

                  return 22;
               case 5:
               default:
                  return 22;
               case 6:
                  ++index;
                  if (data[index] == 'a') {
                     ++index;
                     if (data[index] == 't') {
                        ++index;
                        if (data[index] == 'i') {
                           ++index;
                           if (data[index] == 'v') {
                              ++index;
                              if (data[index] == 'e') {
                                 return 54;
                              }
                           }
                        }
                     }
                  }

                  return 22;
            }
         case 'o':
            switch (length) {
               case 4:
                  if (this.areRestrictedModuleKeywordsActive()) {
                     ++index;
                     if (data[index] == 'p') {
                        ++index;
                        if (data[index] == 'e') {
                           ++index;
                           if (data[index] == 'n') {
                              return 114;
                           }
                        }
                     }
                  }

                  return 22;
               case 5:
                  if (this.areRestrictedModuleKeywordsActive()) {
                     ++index;
                     if (data[index] == 'p') {
                        ++index;
                        if (data[index] == 'e') {
                           ++index;
                           if (data[index] == 'n') {
                              ++index;
                              if (data[index] == 's') {
                                 return 117;
                              }
                           }
                        }
                     }
                  }

                  return 22;
               default:
                  return 22;
            }
         case 'p':
            switch (length) {
               case 6:
                  ++index;
                  if (data[index] == 'u') {
                     ++index;
                     if (data[index] == 'b') {
                        ++index;
                        if (data[index] == 'l') {
                           ++index;
                           if (data[index] == 'i') {
                              ++index;
                              if (data[index] == 'c') {
                                 return 57;
                              }
                           }
                        }
                     }
                  }

                  return 22;
               case 7:
                  ++index;
                  if (data[index] == 'a') {
                     ++index;
                     if (data[index] == 'c') {
                        ++index;
                        if (data[index] == 'k') {
                           ++index;
                           if (data[index] == 'a') {
                              ++index;
                              if (data[index] == 'g') {
                                 ++index;
                                 if (data[index] == 'e') {
                                    return 85;
                                 }
                              }
                           }
                        }
                     }

                     return 22;
                  } else {
                     if (data[index] == 'r') {
                        ++index;
                        if (data[index] == 'i') {
                           ++index;
                           if (data[index] == 'v') {
                              ++index;
                              if (data[index] == 'a') {
                                 ++index;
                                 if (data[index] == 't') {
                                    ++index;
                                    if (data[index] == 'e') {
                                       return 55;
                                    }
                                 }
                              }
                           }
                        }
                     }

                     return 22;
                  }
               case 8:
                  if (this.areRestrictedModuleKeywordsActive()) {
                     ++index;
                     if (data[index] == 'r') {
                        ++index;
                        if (data[index] == 'o') {
                           ++index;
                           if (data[index] == 'v') {
                              ++index;
                              if (data[index] == 'i') {
                                 ++index;
                                 if (data[index] == 'd') {
                                    ++index;
                                    if (data[index] == 'e') {
                                       ++index;
                                       if (data[index] == 's') {
                                          return 119;
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }

                  return 22;
               case 9:
                  ++index;
                  if (data[index] == 'r') {
                     ++index;
                     if (data[index] == 'o') {
                        ++index;
                        if (data[index] == 't') {
                           ++index;
                           if (data[index] == 'e') {
                              ++index;
                              if (data[index] == 'c') {
                                 ++index;
                                 if (data[index] == 't') {
                                    ++index;
                                    if (data[index] == 'e') {
                                       ++index;
                                       if (data[index] == 'd') {
                                          return 56;
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }

                  return 22;
               default:
                  return 22;
            }
         case 'r':
            switch (length) {
               case 6:
                  ++index;
                  if (data[index] == 'e') {
                     ++index;
                     if (data[index] == 't') {
                        ++index;
                        if (data[index] == 'u') {
                           ++index;
                           if (data[index] == 'r') {
                              ++index;
                              if (data[index] == 'n') {
                                 return 82;
                              }
                           }
                        }
                     }
                  }

                  return 22;
               case 7:
               default:
                  return 22;
               case 8:
                  if (this.areRestrictedModuleKeywordsActive()) {
                     ++index;
                     if (data[index] == 'e') {
                        ++index;
                        if (data[index] == 'q') {
                           ++index;
                           if (data[index] == 'u') {
                              ++index;
                              if (data[index] == 'i') {
                                 ++index;
                                 if (data[index] == 'r') {
                                    ++index;
                                    if (data[index] == 'e') {
                                       ++index;
                                       if (data[index] == 's') {
                                          return 115;
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }

                  return 22;
            }
         case 's':
            switch (length) {
               case 5:
                  ++index;
                  if (data[index] == 'h') {
                     ++index;
                     if (data[index] == 'o') {
                        ++index;
                        if (data[index] == 'r') {
                           ++index;
                           if (data[index] == 't') {
                              return 109;
                           }
                        }
                     }

                     return 22;
                  } else {
                     if (data[index] == 'u') {
                        ++index;
                        if (data[index] == 'p') {
                           ++index;
                           if (data[index] == 'e') {
                              ++index;
                              if (data[index] == 'r') {
                                 return 34;
                              }
                           }
                        }
                     }

                     return 22;
                  }
               case 6:
                  ++index;
                  if (data[index] == 't') {
                     ++index;
                     if (data[index] == 'a') {
                        ++index;
                        if (data[index] == 't') {
                           ++index;
                           if (data[index] == 'i') {
                              ++index;
                              if (data[index] == 'c') {
                                 return 48;
                              }
                           }
                        }
                     }

                     return 22;
                  } else {
                     if (data[index] == 'w') {
                        ++index;
                        if (data[index] == 'i') {
                           ++index;
                           if (data[index] == 't') {
                              ++index;
                              if (data[index] == 'c') {
                                 ++index;
                                 if (data[index] == 'h') {
                                    return 51;
                                 }
                              }
                           }
                        }
                     }

                     return 22;
                  }
               case 7:
               case 9:
               case 10:
               case 11:
               default:
                  return 22;
               case 8:
                  ++index;
                  if (data[index] == 't') {
                     ++index;
                     if (data[index] == 'r') {
                        ++index;
                        if (data[index] == 'i') {
                           ++index;
                           if (data[index] == 'c') {
                              ++index;
                              if (data[index] == 't') {
                                 ++index;
                                 if (data[index] == 'f') {
                                    ++index;
                                    if (data[index] == 'p') {
                                       return 58;
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }

                  return 22;
               case 12:
                  ++index;
                  if (data[index] == 'y') {
                     ++index;
                     if (data[index] == 'n') {
                        ++index;
                        if (data[index] == 'c') {
                           ++index;
                           if (data[index] == 'h') {
                              ++index;
                              if (data[index] == 'r') {
                                 ++index;
                                 if (data[index] == 'o') {
                                    ++index;
                                    if (data[index] == 'n') {
                                       ++index;
                                       if (data[index] == 'i') {
                                          ++index;
                                          if (data[index] == 'z') {
                                             ++index;
                                             if (data[index] == 'e') {
                                                ++index;
                                                if (data[index] == 'd') {
                                                   return 50;
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }

                  return 22;
            }
         case 't':
            switch (length) {
               case 2:
                  if (this.areRestrictedModuleKeywordsActive()) {
                     ++index;
                     if (data[index] == 'o') {
                        return 124;
                     }
                  }

                  return 22;
               case 3:
                  ++index;
                  if (data[index] == 'r') {
                     ++index;
                     if (data[index] == 'y') {
                        return 83;
                     }
                  }

                  return 22;
               case 4:
                  ++index;
                  if (data[index] == 'h') {
                     ++index;
                     if (data[index] == 'i') {
                        ++index;
                        if (data[index] == 's') {
                           return 35;
                        }
                     }

                     return 22;
                  } else {
                     if (data[index] == 'r') {
                        ++index;
                        if (data[index] == 'u') {
                           ++index;
                           if (data[index] == 'e') {
                              return 40;
                           }
                        }
                     }

                     return 22;
                  }
               case 5:
                  ++index;
                  if (data[index] == 'h') {
                     ++index;
                     if (data[index] == 'r') {
                        ++index;
                        if (data[index] == 'o') {
                           ++index;
                           if (data[index] == 'w') {
                              return 74;
                           }
                        }
                     }
                  }

                  return 22;
               case 6:
                  ++index;
                  if (data[index] == 'h') {
                     ++index;
                     if (data[index] == 'r') {
                        ++index;
                        if (data[index] == 'o') {
                           ++index;
                           if (data[index] == 'w') {
                              ++index;
                              if (data[index] == 's') {
                                 return 120;
                              }
                           }
                        }
                     }
                  }

                  return 22;
               case 7:
               case 8:
               default:
                  return 22;
               case 9:
                  ++index;
                  if (data[index] == 'r') {
                     ++index;
                     if (data[index] == 'a') {
                        ++index;
                        if (data[index] == 'n') {
                           ++index;
                           if (data[index] == 's') {
                              ++index;
                              if (data[index] == 'i') {
                                 ++index;
                                 if (data[index] == 'e') {
                                    ++index;
                                    if (data[index] == 'n') {
                                       ++index;
                                       if (data[index] == 't') {
                                          return 59;
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }

                  return 22;
               case 10:
                  if (this.areRestrictedModuleKeywordsActive()) {
                     ++index;
                     if (data[index] == 'r') {
                        ++index;
                        if (data[index] == 'a') {
                           ++index;
                           if (data[index] == 'n') {
                              ++index;
                              if (data[index] == 's') {
                                 ++index;
                                 if (data[index] == 'i') {
                                    ++index;
                                    if (data[index] == 't') {
                                       ++index;
                                       if (data[index] == 'i') {
                                          ++index;
                                          if (data[index] == 'v') {
                                             ++index;
                                             if (data[index] == 'e') {
                                                return 121;
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }

                  return 22;
            }
         case 'u':
            switch (length) {
               case 4:
                  if (this.areRestrictedModuleKeywordsActive()) {
                     ++index;
                     if (data[index] == 's') {
                        ++index;
                        if (data[index] == 'e') {
                           ++index;
                           if (data[index] == 's') {
                              return 118;
                           }
                        }
                     }
                  }

                  return 22;
               default:
                  return 22;
            }
         case 'v':
            switch (length) {
               case 4:
                  ++index;
                  if (data[index] == 'o') {
                     ++index;
                     if (data[index] == 'i') {
                        ++index;
                        if (data[index] == 'd') {
                           return 110;
                        }
                     }
                  }

                  return 22;
               case 5:
               case 6:
               case 7:
               default:
                  return 22;
               case 8:
                  ++index;
                  if (data[index] == 'o') {
                     ++index;
                     if (data[index] == 'l') {
                        ++index;
                        if (data[index] == 'a') {
                           ++index;
                           if (data[index] == 't') {
                              ++index;
                              if (data[index] == 'i') {
                                 ++index;
                                 if (data[index] == 'l') {
                                    ++index;
                                    if (data[index] == 'e') {
                                       return 60;
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }

                  return 22;
            }
         case 'w':
            switch (length) {
               case 4:
                  if (this.areRestrictedModuleKeywordsActive()) {
                     ++index;
                     if (data[index] == 'i') {
                        ++index;
                        if (data[index] == 't') {
                           ++index;
                           if (data[index] == 'h') {
                              return 125;
                           }
                        }
                     }
                  }

                  return 22;
               case 5:
                  ++index;
                  if (data[index] == 'h') {
                     ++index;
                     if (data[index] == 'i') {
                        ++index;
                        if (data[index] == 'l') {
                           ++index;
                           if (data[index] == 'e') {
                              return 75;
                           }
                        }
                     }
                  }

                  return 22;
               default:
                  return 22;
            }
      }
   }

   public int scanNumber(boolean dotPrefix) throws InvalidInputException {
      boolean floating = dotPrefix;
      if (!dotPrefix && this.currentCharacter == '0') {
         int end;
         int start;
         if (this.getNextChar('x', 'X') >= 0) {
            start = this.currentPosition;
            this.consumeDigits(16, true);
            end = this.currentPosition;
            if (this.getNextChar('l', 'L') >= 0) {
               if (end == start) {
                  throw new InvalidInputException("Invalid_Hexa_Literal");
               }

               return 42;
            }

            if (this.getNextChar('.')) {
               boolean hasNoDigitsBeforeDot = end == start;
               start = this.currentPosition;
               this.consumeDigits(16, true);
               end = this.currentPosition;
               if (hasNoDigitsBeforeDot && end == start) {
                  if (this.sourceLevel < 3211264L) {
                     throw new InvalidInputException("Illegal_Hexa_Literal");
                  }

                  throw new InvalidInputException("Invalid_Hexa_Literal");
               }

               if (this.getNextChar('p', 'P') < 0) {
                  if (this.sourceLevel < 3211264L) {
                     throw new InvalidInputException("Illegal_Hexa_Literal");
                  }

                  throw new InvalidInputException("Invalid_Hexa_Literal");
               }

               this.unicodeAsBackSlash = false;
               if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                  this.getNextUnicodeChar();
               } else if (this.withoutUnicodePtr != 0) {
                  this.unicodeStore();
               }

               if (this.currentCharacter == '-' || this.currentCharacter == '+') {
                  this.unicodeAsBackSlash = false;
                  if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                     this.getNextUnicodeChar();
                  } else if (this.withoutUnicodePtr != 0) {
                     this.unicodeStore();
                  }
               }

               if (!ScannerHelper.isDigit(this.currentCharacter)) {
                  if (this.sourceLevel < 3211264L) {
                     throw new InvalidInputException("Illegal_Hexa_Literal");
                  }

                  if (this.currentCharacter == '_') {
                     this.consumeDigits(10);
                     throw new InvalidInputException("Invalid_Underscore");
                  }

                  throw new InvalidInputException("Invalid_Hexa_Literal");
               }

               this.consumeDigits(10);
               if (this.getNextChar('f', 'F') >= 0) {
                  if (this.sourceLevel < 3211264L) {
                     throw new InvalidInputException("Illegal_Hexa_Literal");
                  }

                  return 43;
               }

               if (this.getNextChar('d', 'D') >= 0) {
                  if (this.sourceLevel < 3211264L) {
                     throw new InvalidInputException("Illegal_Hexa_Literal");
                  }

                  return 44;
               }

               if (this.getNextChar('l', 'L') >= 0) {
                  if (this.sourceLevel < 3211264L) {
                     throw new InvalidInputException("Illegal_Hexa_Literal");
                  }

                  throw new InvalidInputException("Invalid_Hexa_Literal");
               }

               if (this.sourceLevel < 3211264L) {
                  throw new InvalidInputException("Illegal_Hexa_Literal");
               }

               return 44;
            }

            if (this.getNextChar('p', 'P') >= 0) {
               if (end == start) {
                  if (this.sourceLevel < 3211264L) {
                     throw new InvalidInputException("Illegal_Hexa_Literal");
                  }

                  throw new InvalidInputException("Invalid_Hexa_Literal");
               }

               this.unicodeAsBackSlash = false;
               if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                  this.getNextUnicodeChar();
               } else if (this.withoutUnicodePtr != 0) {
                  this.unicodeStore();
               }

               if (this.currentCharacter == '-' || this.currentCharacter == '+') {
                  this.unicodeAsBackSlash = false;
                  if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                     this.getNextUnicodeChar();
                  } else if (this.withoutUnicodePtr != 0) {
                     this.unicodeStore();
                  }
               }

               if (!ScannerHelper.isDigit(this.currentCharacter)) {
                  if (this.sourceLevel < 3211264L) {
                     throw new InvalidInputException("Illegal_Hexa_Literal");
                  }

                  if (this.currentCharacter == '_') {
                     this.consumeDigits(10);
                     throw new InvalidInputException("Invalid_Underscore");
                  }

                  throw new InvalidInputException("Invalid_Float_Literal");
               }

               this.consumeDigits(10);
               if (this.getNextChar('f', 'F') >= 0) {
                  if (this.sourceLevel < 3211264L) {
                     throw new InvalidInputException("Illegal_Hexa_Literal");
                  }

                  return 43;
               }

               if (this.getNextChar('d', 'D') >= 0) {
                  if (this.sourceLevel < 3211264L) {
                     throw new InvalidInputException("Illegal_Hexa_Literal");
                  }

                  return 44;
               }

               if (this.getNextChar('l', 'L') >= 0) {
                  if (this.sourceLevel < 3211264L) {
                     throw new InvalidInputException("Illegal_Hexa_Literal");
                  }

                  throw new InvalidInputException("Invalid_Hexa_Literal");
               }

               if (this.sourceLevel < 3211264L) {
                  throw new InvalidInputException("Illegal_Hexa_Literal");
               }

               return 44;
            }

            if (end == start) {
               throw new InvalidInputException("Invalid_Hexa_Literal");
            }

            return 41;
         }

         if (this.getNextChar('b', 'B') >= 0) {
            start = this.currentPosition;
            this.consumeDigits(2, true);
            end = this.currentPosition;
            if (end == start) {
               if (this.sourceLevel < 3342336L) {
                  throw new InvalidInputException("Binary_Literal_Not_Below_17");
               }

               throw new InvalidInputException("Invalid_Binary_Literal");
            }

            if (this.getNextChar('l', 'L') >= 0) {
               if (this.sourceLevel < 3342336L) {
                  throw new InvalidInputException("Binary_Literal_Not_Below_17");
               }

               return 42;
            }

            if (this.sourceLevel < 3342336L) {
               throw new InvalidInputException("Binary_Literal_Not_Below_17");
            }

            return 41;
         }

         if (this.getNextCharAsDigit()) {
            this.consumeDigits(10);
            if (this.getNextChar('l', 'L') >= 0) {
               return 42;
            }

            if (this.getNextChar('f', 'F') >= 0) {
               return 43;
            }

            if (this.getNextChar('d', 'D') >= 0) {
               return 44;
            }

            boolean isInteger = true;
            if (this.getNextChar('.')) {
               isInteger = false;
               this.consumeDigits(10);
            }

            if (this.getNextChar('e', 'E') >= 0) {
               isInteger = false;
               this.unicodeAsBackSlash = false;
               if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                  this.getNextUnicodeChar();
               } else if (this.withoutUnicodePtr != 0) {
                  this.unicodeStore();
               }

               if (this.currentCharacter == '-' || this.currentCharacter == '+') {
                  this.unicodeAsBackSlash = false;
                  if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                     this.getNextUnicodeChar();
                  } else if (this.withoutUnicodePtr != 0) {
                     this.unicodeStore();
                  }
               }

               if (!ScannerHelper.isDigit(this.currentCharacter)) {
                  if (this.currentCharacter == '_') {
                     this.consumeDigits(10);
                     throw new InvalidInputException("Invalid_Underscore");
                  }

                  throw new InvalidInputException("Invalid_Float_Literal");
               }

               this.consumeDigits(10);
            }

            if (this.getNextChar('f', 'F') >= 0) {
               return 43;
            }

            if (this.getNextChar('d', 'D') < 0 && isInteger) {
               return 41;
            }

            return 44;
         }
      }

      this.consumeDigits(10);
      if (!dotPrefix && this.getNextChar('l', 'L') >= 0) {
         return 42;
      } else {
         if (!dotPrefix && this.getNextChar('.')) {
            this.consumeDigits(10, true);
            floating = true;
         }

         if (this.getNextChar('e', 'E') >= 0) {
            floating = true;
            this.unicodeAsBackSlash = false;
            if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
               this.getNextUnicodeChar();
            } else if (this.withoutUnicodePtr != 0) {
               this.unicodeStore();
            }

            if (this.currentCharacter == '-' || this.currentCharacter == '+') {
               this.unicodeAsBackSlash = false;
               if ((this.currentCharacter = this.source[this.currentPosition++]) == '\\' && this.source[this.currentPosition] == 'u') {
                  this.getNextUnicodeChar();
               } else if (this.withoutUnicodePtr != 0) {
                  this.unicodeStore();
               }
            }

            if (!ScannerHelper.isDigit(this.currentCharacter)) {
               if (this.currentCharacter == '_') {
                  this.consumeDigits(10);
                  throw new InvalidInputException("Invalid_Underscore");
               }

               throw new InvalidInputException("Invalid_Float_Literal");
            }

            this.consumeDigits(10);
         }

         if (this.getNextChar('d', 'D') >= 0) {
            return 44;
         } else if (this.getNextChar('f', 'F') >= 0) {
            return 43;
         } else {
            return floating ? 44 : 41;
         }
      }
   }

   public final int getLineNumber(int position) {
      return Util.getLineNumber(position, this.lineEnds, 0, this.linePtr);
   }

   public final void setSource(char[] sourceString) {
      int sourceLength;
      if (sourceString == null) {
         this.source = CharOperation.NO_CHAR;
         sourceLength = 0;
      } else {
         this.source = sourceString;
         sourceLength = sourceString.length;
      }

      this.startPosition = -1;
      this.eofPosition = sourceLength;
      this.initialPosition = this.currentPosition = 0;
      this.containsAssertKeyword = false;
      this.linePtr = -1;
      this.scanContext = null;
      this.insideModuleInfo = false;
   }

   public final void setSource(char[] contents, CompilationResult compilationResult) {
      if (contents == null) {
         char[] cuContents = compilationResult.compilationUnit.getContents();
         this.setSource(cuContents);
      } else {
         this.setSource(contents);
      }

      int[] lineSeparatorPositions = compilationResult.lineSeparatorPositions;
      if (lineSeparatorPositions != null) {
         this.lineEnds = lineSeparatorPositions;
         this.linePtr = lineSeparatorPositions.length - 1;
      }

   }

   public final void setSource(CompilationResult compilationResult) {
      this.setSource((char[])null, compilationResult);
   }

   public String toString() {
      if (this.startPosition == this.eofPosition) {
         return "EOF\n\n" + new String(this.source);
      } else if (this.currentPosition > this.eofPosition) {
         return "behind the EOF\n\n" + new String(this.source);
      } else if (this.currentPosition <= 0) {
         return "NOT started!\n\n" + (this.source != null ? new String(this.source) : "");
      } else {
         StringBuffer buffer = new StringBuffer();
         int middleLength;
         if (this.startPosition < 1000) {
            buffer.append(this.source, 0, this.startPosition);
         } else {
            buffer.append("<source beginning>\n...\n");
            middleLength = Util.getLineNumber(this.startPosition - 1000, this.lineEnds, 0, this.linePtr);
            int lineStart = this.getLineStart(middleLength);
            buffer.append(this.source, lineStart, this.startPosition - lineStart);
         }

         buffer.append("\n===============================\nStarts here -->");
         middleLength = this.currentPosition - 1 - this.startPosition + 1;
         if (middleLength > -1) {
            buffer.append(this.source, this.startPosition, middleLength);
         }

         if (this.nextToken != 0) {
            buffer.append("<-- Ends here [in pipeline " + this.toStringAction(this.nextToken) + "]\n===============================\n");
         } else {
            buffer.append("<-- Ends here\n===============================\n");
         }

         buffer.append(this.source, this.currentPosition - 1 + 1, this.eofPosition - (this.currentPosition - 1) - 1);
         return buffer.toString();
      }
   }

   public String toStringAction(int act) {
      switch (act) {
         case 1:
            return ".";
         case 2:
            return "++";
         case 3:
            return "--";
         case 4:
            return "+";
         case 5:
            return "-";
         case 6:
            return "[";
         case 7:
            return "::";
         case 8:
            return "*";
         case 9:
            return "%";
         case 10:
            return "/";
         case 11:
            return "<";
         case 12:
            return "<=";
         case 13:
            return ">=";
         case 14:
            return ">>";
         case 15:
            return ">";
         case 16:
            return ">>>";
         case 17:
            return "instanceof";
         case 18:
            return "<<";
         case 19:
            return "==";
         case 20:
            return "!=";
         case 21:
            return "&";
         case 22:
            return "Identifier(" + new String(this.getCurrentTokenSource()) + ")";
         case 23:
            return "(";
         case 24:
            return "^";
         case 25:
            return ")";
         case 26:
            return ";";
         case 28:
            return "|";
         case 29:
            return "?";
         case 30:
            return "&&";
         case 31:
            return "||";
         case 32:
            return ",";
         case 33:
            return "}";
         case 34:
            return "super";
         case 35:
            return "this";
         case 36:
            return "new";
         case 38:
            return "false";
         case 39:
            return "null";
         case 40:
            return "true";
         case 41:
            return "Integer(" + new String(this.getCurrentTokenSource()) + ")";
         case 42:
            return "Long(" + new String(this.getCurrentTokenSource()) + ")";
         case 43:
            return "Float(" + new String(this.getCurrentTokenSource()) + ")";
         case 44:
            return "Double(" + new String(this.getCurrentTokenSource()) + ")";
         case 45:
            return "Char(" + new String(this.getCurrentTokenSource()) + ")";
         case 46:
            return "String(" + new String(this.getCurrentTokenSource()) + ")";
         case 48:
            return "static";
         case 49:
            return "{";
         case 50:
            return "synchronized";
         case 51:
            return "switch";
         case 52:
            return "abstract";
         case 53:
            return "final";
         case 54:
            return "native";
         case 55:
            return "private";
         case 56:
            return "protected";
         case 57:
            return "public";
         case 59:
            return "transient";
         case 60:
            return "volatile";
         case 61:
            return "EOF";
         case 62:
            return ":";
         case 63:
            return "!";
         case 64:
            return "~";
         case 66:
            return "]";
         case 67:
            return "class";
         case 70:
            return "interface";
         case 72:
            return "=";
         case 73:
            return "default";
         case 74:
            return "throw";
         case 75:
            return "while";
         case 77:
            return "break";
         case 78:
            return "continue";
         case 79:
            return "do";
         case 80:
            return "for";
         case 81:
            return "if";
         case 82:
            return "return";
         case 83:
            return "try";
         case 85:
            return "package";
         case 86:
            return "extends";
         case 87:
            return "+=";
         case 88:
            return "-=";
         case 89:
            return "*=";
         case 90:
            return "/=";
         case 91:
            return "&=";
         case 92:
            return "|=";
         case 93:
            return "^=";
         case 94:
            return "%=";
         case 95:
            return "<<=";
         case 96:
            return ">>=";
         case 97:
            return ">>>=";
         case 98:
            return "->";
         case 99:
            return "boolean";
         case 100:
            return "byte";
         case 101:
            return "case";
         case 102:
            return "catch";
         case 103:
            return "char";
         case 104:
            return "double";
         case 105:
            return "float";
         case 106:
            return "import";
         case 107:
            return "int";
         case 108:
            return "long";
         case 109:
            return "short";
         case 110:
            return "void";
         case 111:
            return "finally";
         case 112:
            return "else";
         case 113:
            return "module";
         case 115:
            return "requires";
         case 116:
            return "exports";
         case 120:
            return "throws";
         case 123:
            return "implements";
         case 1000:
            return "white_space(" + new String(this.getCurrentTokenSource()) + ")";
         default:
            return "not-a-token";
      }
   }

   public void unicodeInitializeBuffer(int length) {
      this.withoutUnicodePtr = length;
      if (this.withoutUnicodeBuffer == null) {
         this.withoutUnicodeBuffer = new char[length + 11];
      }

      int bLength = this.withoutUnicodeBuffer.length;
      if (1 + length >= bLength) {
         System.arraycopy(this.withoutUnicodeBuffer, 0, this.withoutUnicodeBuffer = new char[length + 11], 0, bLength);
      }

      System.arraycopy(this.source, this.startPosition, this.withoutUnicodeBuffer, 1, length);
   }

   public void unicodeStore() {
      int pos = ++this.withoutUnicodePtr;
      if (this.withoutUnicodeBuffer == null) {
         this.withoutUnicodeBuffer = new char[10];
      }

      int length = this.withoutUnicodeBuffer.length;
      if (pos == length) {
         System.arraycopy(this.withoutUnicodeBuffer, 0, this.withoutUnicodeBuffer = new char[length * 2], 0, length);
      }

      this.withoutUnicodeBuffer[pos] = this.currentCharacter;
   }

   public void unicodeStore(char character) {
      int pos = ++this.withoutUnicodePtr;
      if (this.withoutUnicodeBuffer == null) {
         this.withoutUnicodeBuffer = new char[10];
      }

      int length = this.withoutUnicodeBuffer.length;
      if (pos == length) {
         System.arraycopy(this.withoutUnicodeBuffer, 0, this.withoutUnicodeBuffer = new char[length * 2], 0, length);
      }

      this.withoutUnicodeBuffer[pos] = character;
   }

   public static boolean isIdentifier(int token) {
      return token == 22;
   }

   public static boolean isLiteral(int token) {
      switch (token) {
         case 41:
         case 42:
         case 43:
         case 44:
         case 45:
         case 46:
            return true;
         default:
            return false;
      }
   }

   public static boolean isKeyword(int token) {
      switch (token) {
         case 17:
         case 34:
         case 35:
         case 36:
         case 38:
         case 39:
         case 40:
         case 48:
         case 50:
         case 51:
         case 52:
         case 53:
         case 54:
         case 55:
         case 56:
         case 57:
         case 58:
         case 59:
         case 60:
         case 67:
         case 70:
         case 73:
         case 74:
         case 75:
         case 76:
         case 77:
         case 78:
         case 79:
         case 80:
         case 81:
         case 82:
         case 83:
         case 85:
         case 86:
         case 99:
         case 100:
         case 101:
         case 102:
         case 103:
         case 104:
         case 105:
         case 106:
         case 107:
         case 108:
         case 109:
         case 110:
         case 111:
         case 112:
         case 120:
         case 123:
            return true;
         case 18:
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 37:
         case 41:
         case 42:
         case 43:
         case 44:
         case 45:
         case 46:
         case 47:
         case 49:
         case 61:
         case 62:
         case 63:
         case 64:
         case 65:
         case 66:
         case 68:
         case 69:
         case 71:
         case 72:
         case 84:
         case 87:
         case 88:
         case 89:
         case 90:
         case 91:
         case 92:
         case 93:
         case 94:
         case 95:
         case 96:
         case 97:
         case 98:
         case 113:
         case 114:
         case 115:
         case 116:
         case 117:
         case 118:
         case 119:
         case 121:
         case 122:
         default:
            return false;
      }
   }

   private VanguardParser getVanguardParser() {
      if (this.vanguardParser == null) {
         this.vanguardScanner = new VanguardScanner(this.sourceLevel, this.complianceLevel);
         this.vanguardParser = new VanguardParser(this.vanguardScanner);
         this.vanguardScanner.setActiveParser(this.vanguardParser);
      }

      this.vanguardScanner.setSource(this.source);
      this.vanguardScanner.resetTo(this.startPosition, this.eofPosition - 1, this.isInModuleDeclaration(), this.scanContext);
      return this.vanguardParser;
   }

   protected final boolean maybeAtLambdaOrCast() {
      switch (this.lookBack[1]) {
         case 22:
         case 34:
         case 35:
         case 50:
         case 51:
         case 75:
         case 80:
         case 81:
         case 83:
         case 102:
            return false;
         default:
            return this.activeParser.atConflictScenario(23);
      }
   }

   protected final boolean maybeAtReferenceExpression() {
      switch (this.lookBack[1]) {
         case 22:
            switch (this.lookBack[0]) {
               case 11:
               case 14:
               case 15:
               case 17:
               case 21:
               case 26:
               case 33:
               case 34:
               case 36:
               case 37:
               case 48:
               case 52:
               case 53:
               case 55:
               case 56:
               case 57:
               case 67:
               case 70:
               case 71:
               case 86:
               case 120:
               case 123:
                  return false;
            }
         case 0:
            return this.activeParser.atConflictScenario(11);
         default:
            return false;
      }
   }

   private final boolean maybeAtEllipsisAnnotationsStart() {
      if (this.consumingEllipsisAnnotations) {
         return false;
      } else {
         switch (this.lookBack[1]) {
            case 1:
            case 11:
            case 17:
            case 21:
            case 32:
            case 34:
            case 36:
            case 49:
            case 86:
            case 120:
            case 123:
               return false;
            default:
               return true;
         }
      }
   }

   protected final boolean atTypeAnnotation() {
      return !this.activeParser.atConflictScenario(37);
   }

   public void setActiveParser(ConflictedParser parser) {
      this.activeParser = parser;
      this.lookBack[0] = this.lookBack[1] = 0;
      if (parser != null) {
         this.insideModuleInfo = parser.isParsingModuleDeclaration();
      }

   }

   public static boolean isRestrictedKeyword(int token) {
      switch (token) {
         case 113:
         case 114:
         case 115:
         case 116:
         case 117:
         case 118:
         case 119:
         case 121:
         case 124:
         case 125:
            return true;
         case 120:
         case 122:
         case 123:
         default:
            return false;
      }
   }

   int disambiguatedRestrictedKeyword(int restrictedKeywordToken) {
      int token = restrictedKeywordToken;
      if (this.scanContext == Scanner.ScanContext.EXPECTING_IDENTIFIER) {
         return 22;
      } else {
         switch (restrictedKeywordToken) {
            case 113:
            case 114:
            case 115:
            case 116:
            case 117:
            case 118:
            case 119:
            case 124:
            case 125:
               if (this.scanContext != Scanner.ScanContext.EXPECTING_KEYWORD) {
                  token = 22;
               }
            case 120:
            case 122:
            case 123:
            default:
               break;
            case 121:
               if (this.scanContext != Scanner.ScanContext.AFTER_REQUIRES) {
                  token = 22;
               } else {
                  this.getVanguardParser();
                  this.vanguardScanner.resetTo(this.currentPosition, this.eofPosition - 1, true, Scanner.ScanContext.EXPECTING_IDENTIFIER);

                  try {
                     int lookAhead = this.vanguardScanner.getNextToken();
                     if (lookAhead == 26) {
                        token = 22;
                     }
                  } catch (InvalidInputException var4) {
                  }
               }
         }

         return token;
      }
   }

   int disambiguatedToken(int token) {
      VanguardParser parser = this.getVanguardParser();
      if (token == 98 && this.inCase) {
         this.nextToken = 98;
         this.inCase = false;
         return 69;
      } else {
         if (token == 23 && this.maybeAtLambdaOrCast()) {
            if (parser.parse(Scanner.Goal.LambdaParameterListGoal)) {
               this.nextToken = 23;
               return 47;
            }

            this.vanguardScanner.resetTo(this.startPosition, this.eofPosition - 1);
            if (parser.parse(Scanner.Goal.IntersectionCastGoal)) {
               this.nextToken = 23;
               return 65;
            }
         } else if (token == 11 && this.maybeAtReferenceExpression()) {
            if (parser.parse(Scanner.Goal.ReferenceExpressionGoal)) {
               this.nextToken = 11;
               return 84;
            }
         } else if (token == 37 && this.atTypeAnnotation()) {
            token = 27;
            if (this.maybeAtEllipsisAnnotationsStart() && parser.parse(Scanner.Goal.VarargTypeAnnotationGoal)) {
               this.consumingEllipsisAnnotations = true;
               this.nextToken = 27;
               return 126;
            }
         }

         return token;
      }
   }

   protected boolean isAtAssistIdentifier() {
      return false;
   }

   public int fastForward(Statement unused) {
      while(true) {
         int token;
         try {
            token = this.getNextToken();
         } catch (InvalidInputException var3) {
            return 61;
         }

         switch (token) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 24:
            case 25:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 68:
            case 69:
            case 72:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            case 102:
            case 106:
            default:
               break;
            case 22:
               if (this.isAtAssistIdentifier()) {
                  return token;
               }
            case 2:
            case 3:
            case 11:
            case 23:
            case 27:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 67:
            case 70:
            case 71:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 99:
            case 100:
            case 101:
            case 103:
            case 104:
            case 105:
            case 107:
            case 108:
            case 109:
            case 110:
               if (!this.getVanguardParser().parse(Scanner.Goal.BlockStatementoptGoal)) {
                  break;
               }

               return token;
            case 26:
            case 61:
               return token;
            case 33:
               this.ungetToken(token);
               return 26;
         }
      }
   }

   protected int getNextNotFakedToken() throws InvalidInputException {
      return this.getNextToken();
   }

   private static class Goal {
      int first;
      int[] follow;
      int rule;
      static int LambdaParameterListRule = 0;
      static int IntersectionCastRule = 0;
      static int ReferenceExpressionRule = 0;
      static int VarargTypeAnnotationsRule = 0;
      static int BlockStatementoptRule = 0;
      static Goal LambdaParameterListGoal;
      static Goal IntersectionCastGoal;
      static Goal VarargTypeAnnotationGoal;
      static Goal ReferenceExpressionGoal;
      static Goal BlockStatementoptGoal;

      static {
         for(int i = 1; i <= 867; ++i) {
            if ("ParenthesizedLambdaParameterList".equals(Parser.name[Parser.non_terminal_index[Parser.lhs[i]]])) {
               LambdaParameterListRule = i;
            } else if ("ParenthesizedCastNameAndBounds".equals(Parser.name[Parser.non_terminal_index[Parser.lhs[i]]])) {
               IntersectionCastRule = i;
            } else if ("ReferenceExpressionTypeArgumentsAndTrunk".equals(Parser.name[Parser.non_terminal_index[Parser.lhs[i]]])) {
               ReferenceExpressionRule = i;
            } else if ("TypeAnnotations".equals(Parser.name[Parser.non_terminal_index[Parser.lhs[i]]])) {
               VarargTypeAnnotationsRule = i;
            } else if ("BlockStatementopt".equals(Parser.name[Parser.non_terminal_index[Parser.lhs[i]]])) {
               BlockStatementoptRule = i;
            }
         }

         LambdaParameterListGoal = new Goal(98, new int[]{98}, LambdaParameterListRule);
         IntersectionCastGoal = new Goal(23, followSetOfCast(), IntersectionCastRule);
         VarargTypeAnnotationGoal = new Goal(37, new int[]{122}, VarargTypeAnnotationsRule);
         ReferenceExpressionGoal = new Goal(11, new int[]{7}, ReferenceExpressionRule);
         BlockStatementoptGoal = new Goal(49, new int[0], BlockStatementoptRule);
      }

      Goal(int first, int[] follow, int rule) {
         this.first = first;
         this.follow = follow;
         this.rule = rule;
      }

      boolean hasBeenReached(int act, int token) {
         if (act == this.rule) {
            int length = this.follow.length;
            if (length == 0) {
               return true;
            }

            for(int i = 0; i < length; ++i) {
               if (this.follow[i] == token) {
                  return true;
               }
            }
         }

         return false;
      }

      private static int[] followSetOfCast() {
         return new int[]{22, 36, 34, 35, 38, 40, 39, 41, 42, 43, 44, 45, 46, 63, 64, 23};
      }
   }

   static enum ScanContext {
      EXPECTING_KEYWORD,
      EXPECTING_IDENTIFIER,
      AFTER_REQUIRES,
      INACTIVE;
   }

   private class ScanContextDetector extends VanguardParser {
      ScanContextDetector(CompilerOptions options) {
         super(new ProblemReporter(DefaultErrorHandlingPolicies.ignoreAllProblems(), options, new DefaultProblemFactory()));
         this.problemReporter.options.performStatementsRecovery = false;
         this.reportSyntaxErrorIsRequired = false;
         this.reportOnlyOneSyntaxError = false;
      }

      public void initializeScanner() {
         this.scanner = new Scanner(false, false, false, this.options.sourceLevel, this.options.complianceLevel, this.options.taskTags, this.options.taskPriorities, this.options.isTaskCaseSensitive) {
            void updateScanContext(int token) {
               if (token != 61) {
                  super.updateScanContext(token);
               }

            }
         };
         this.scanner.recordLineSeparator = false;
         this.scanner.setActiveParser(this);
      }

      public boolean isParsingModuleDeclaration() {
         return true;
      }

      public ScanContext getScanContext(char[] src, int begin) {
         this.scanner.setSource(src);
         this.scanner.resetTo(0, begin);
         this.goForCompilationUnit();
         Goal goal = new Goal(2, (int[])null, 0) {
            boolean hasBeenReached(int act, int token) {
               return token == 61;
            }
         };
         this.parse(goal);
         return this.scanner.scanContext;
      }
   }

   private static class VanguardParser extends Parser {
      public static final boolean SUCCESS = true;
      public static final boolean FAILURE = false;
      Goal currentGoal;

      public VanguardParser(VanguardScanner scanner) {
         this.scanner = scanner;
      }

      public VanguardParser(ProblemReporter reporter) {
         super(reporter, false);
      }

      protected boolean parse(Goal goal) {
         this.currentGoal = goal;

         try {
            int act = 1124;
            this.stateStackTop = -1;
            this.currentToken = goal.first;

            label173:
            while(true) {
               while(true) {
                  int stackLength = this.stack.length;
                  if (++this.stateStackTop >= stackLength) {
                     System.arraycopy(this.stack, 0, this.stack = new int[stackLength + 255], 0, stackLength);
                  }

                  this.stack[this.stateStackTop] = act;
                  act = Parser.tAction(act, this.currentToken);
                  if (act == 16966) {
                     return false;
                  }

                  if (act <= 867) {
                     --this.stateStackTop;
                     break;
                  }

                  if (act > 16966) {
                     this.unstackedAct = act;

                     try {
                        this.currentToken = this.scanner.getNextToken();
                     } finally {
                        this.unstackedAct = 16966;
                     }

                     act -= 16966;
                     break;
                  }

                  if (act >= 16965) {
                     return false;
                  }

                  this.unstackedAct = act;

                  try {
                     this.currentToken = this.scanner.getNextToken();
                  } finally {
                     this.unstackedAct = 16966;
                  }
               }

               while(!goal.hasBeenReached(act, this.currentToken)) {
                  this.stateStackTop -= Parser.rhs[act] - 1;
                  act = Parser.ntAction(this.stack[this.stateStackTop], Parser.lhs[act]);
                  if (act > 867) {
                     continue label173;
                  }
               }

               return true;
            }
         } catch (Exception var13) {
            return false;
         }
      }

      public String toString() {
         return "\n\n\n----------------Scanner--------------\n" + this.scanner.toString();
      }
   }

   private static final class VanguardScanner extends Scanner {
      public VanguardScanner(long sourceLevel, long complianceLevel) {
         super(false, false, false, sourceLevel, complianceLevel, (char[][])null, (char[][])null, false);
      }

      public int getNextToken() throws InvalidInputException {
         int token;
         if (this.nextToken != 0) {
            token = this.nextToken;
            this.nextToken = 0;
            return token;
         } else {
            if (this.scanContext == null) {
               this.scanContext = this.isInModuleDeclaration() ? Scanner.ScanContext.EXPECTING_KEYWORD : Scanner.ScanContext.INACTIVE;
            }

            token = this.getNextToken0();
            if (this.areRestrictedModuleKeywordsActive()) {
               if (isRestrictedKeyword(token)) {
                  token = this.disambiguatedRestrictedKeyword(token);
               }

               this.updateScanContext(token);
            }

            if (token == 37 && this.atTypeAnnotation()) {
               if (((VanguardParser)this.activeParser).currentGoal == Scanner.Goal.LambdaParameterListGoal) {
                  token = this.disambiguatedToken(token);
               } else {
                  token = 27;
               }
            }

            return token == 61 ? 0 : token;
         }
      }
   }
}
