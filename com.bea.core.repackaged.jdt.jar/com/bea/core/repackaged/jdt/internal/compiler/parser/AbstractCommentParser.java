package com.bea.core.repackaged.jdt.internal.compiler.parser;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.core.compiler.InvalidInputException;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommentParser implements JavadocTagConstants {
   public static final int COMPIL_PARSER = 1;
   public static final int DOM_PARSER = 2;
   public static final int SELECTION_PARSER = 4;
   public static final int COMPLETION_PARSER = 8;
   public static final int SOURCE_PARSER = 16;
   public static final int FORMATTER_COMMENT_PARSER = 32;
   protected static final int PARSER_KIND = 255;
   protected static final int TEXT_PARSE = 256;
   protected static final int TEXT_VERIF = 512;
   protected static final int QUALIFIED_NAME_RECOVERY = 1;
   protected static final int ARGUMENT_RECOVERY = 2;
   protected static final int ARGUMENT_TYPE_RECOVERY = 3;
   protected static final int EMPTY_ARGUMENT_RECOVERY = 4;
   public Scanner scanner;
   public char[] source;
   protected Parser sourceParser;
   private int currentTokenType = -1;
   public boolean checkDocComment = false;
   public boolean setJavadocPositions = false;
   public boolean reportProblems;
   protected long complianceLevel;
   protected long sourceLevel;
   protected long[] inheritedPositions;
   protected int inheritedPositionsPtr;
   private static final int INHERITED_POSITIONS_ARRAY_INCREMENT = 4;
   protected boolean deprecated;
   protected Object returnStatement;
   protected int javadocStart;
   protected int javadocEnd;
   protected int javadocTextStart;
   protected int javadocTextEnd = -1;
   protected int firstTagPosition;
   protected int index;
   protected int lineEnd;
   protected int tokenPreviousPosition;
   protected int lastIdentifierEndPosition;
   protected int starPosition;
   protected int textStart;
   protected int memberStart;
   protected int tagSourceStart;
   protected int tagSourceEnd;
   protected int inlineTagStart;
   protected int[] lineEnds;
   protected boolean lineStarted = false;
   protected boolean inlineTagStarted = false;
   protected boolean abort = false;
   protected int kind;
   protected int tagValue = 0;
   protected int lastBlockTagValue = 0;
   private int linePtr;
   private int lastLinePtr;
   protected int identifierPtr;
   protected char[][] identifierStack;
   protected int identifierLengthPtr;
   protected int[] identifierLengthStack;
   protected long[] identifierPositionStack;
   protected static final int AST_STACK_INCREMENT = 10;
   protected int astPtr;
   protected Object[] astStack;
   protected int astLengthPtr;
   protected int[] astLengthStack;

   protected AbstractCommentParser(Parser sourceParser) {
      this.sourceParser = sourceParser;
      this.scanner = new Scanner(false, false, false, 3080192L, (char[][])null, (char[][])null, true);
      this.identifierStack = new char[20][];
      this.identifierPositionStack = new long[20];
      this.identifierLengthStack = new int[10];
      this.astStack = new Object[30];
      this.astLengthStack = new int[20];
      this.reportProblems = sourceParser != null;
      if (sourceParser != null) {
         this.checkDocComment = this.sourceParser.options.docCommentSupport;
         this.sourceLevel = this.sourceParser.options.sourceLevel;
         this.scanner.sourceLevel = this.sourceLevel;
         this.complianceLevel = this.sourceParser.options.complianceLevel;
      }

   }

   protected boolean commentParse() {
      boolean validComment = true;

      try {
         this.astLengthPtr = -1;
         this.astPtr = -1;
         this.identifierPtr = -1;
         this.currentTokenType = -1;
         this.setInlineTagStarted(false);
         this.inlineTagStart = -1;
         this.lineStarted = false;
         this.returnStatement = null;
         this.inheritedPositions = null;
         this.lastBlockTagValue = 0;
         this.deprecated = false;
         this.lastLinePtr = this.getLineNumber(this.javadocEnd);
         this.textStart = -1;
         this.abort = false;
         char previousChar = false;
         int invalidTagLineEnd = -1;
         int invalidInlineTagLineEnd = -1;
         boolean lineHasStar = true;
         boolean verifText = (this.kind & 512) != 0;
         boolean isDomParser = (this.kind & 2) != 0;
         boolean isFormatterParser = (this.kind & 32) != 0;
         int lastStarPosition = -1;
         this.linePtr = this.getLineNumber(this.firstTagPosition);
         int realStart = this.linePtr == 1 ? this.javadocStart : this.scanner.getLineEnd(this.linePtr - 1) + 1;
         if (realStart < this.javadocStart) {
            realStart = this.javadocStart;
         }

         this.scanner.resetTo(realStart, this.javadocEnd);
         this.index = realStart;
         if (realStart == this.javadocStart) {
            this.readChar();
            this.readChar();
         }

         int previousPosition = this.index;
         char nextCharacter = 0;
         if (realStart == this.javadocStart) {
            for(nextCharacter = this.readChar(); this.peekChar() == '*'; nextCharacter = this.readChar()) {
            }

            this.javadocTextStart = this.index;
         }

         this.lineEnd = this.linePtr == this.lastLinePtr ? this.javadocEnd : this.scanner.getLineEnd(this.linePtr) - 1;
         this.javadocTextEnd = this.javadocEnd - 2;
         boolean considerTagAsPlainText = false;
         int openingBraces = 0;
         int textEndPosition = -1;

         while(true) {
            int initialIndex;
            while(!this.abort && this.index < this.javadocEnd) {
               previousPosition = this.index;
               char previousChar = nextCharacter;
               if (this.index > this.lineEnd + 1) {
                  this.updateLineEnd();
               }

               if (this.currentTokenType < 0) {
                  nextCharacter = this.readChar();
               } else {
                  previousPosition = this.scanner.getCurrentTokenStartPosition();
                  switch (this.currentTokenType) {
                     case 8:
                        nextCharacter = '*';
                        break;
                     case 33:
                        nextCharacter = '}';
                        break;
                     default:
                        nextCharacter = this.scanner.currentCharacter;
                  }

                  this.consumeToken();
               }

               switch (nextCharacter) {
                  case '\t':
                  case '\f':
                  case ' ':
                     if (isFormatterParser) {
                        if (!ScannerHelper.isWhitespace(previousChar)) {
                           textEndPosition = previousPosition;
                        }
                     } else if (this.lineStarted && isDomParser) {
                        textEndPosition = this.index;
                     }
                     continue;
                  case '\n':
                  case '\r':
                     if (this.lineStarted) {
                        if (isFormatterParser && !ScannerHelper.isWhitespace(previousChar)) {
                           textEndPosition = previousPosition;
                        }

                        if (this.textStart != -1 && this.textStart < textEndPosition) {
                           this.pushText(this.textStart, textEndPosition);
                        }
                     }

                     this.lineStarted = false;
                     lineHasStar = false;
                     this.textStart = -1;
                     continue;
                  case '*':
                     lastStarPosition = previousPosition;
                     if (previousChar == '*') {
                        continue;
                     }

                     this.starPosition = previousPosition;
                     if (!isDomParser && !isFormatterParser) {
                        continue;
                     }

                     if (lineHasStar) {
                        this.lineStarted = true;
                        if (this.textStart == -1) {
                           this.textStart = previousPosition;
                           if (this.index <= this.javadocTextEnd) {
                              textEndPosition = this.index;
                           }
                        }
                     }

                     if (!this.lineStarted) {
                        lineHasStar = true;
                     }
                     continue;
                  case '/':
                     if (previousChar == '*') {
                        continue;
                     }
                     break;
                  case '@':
                     if (considerTagAsPlainText) {
                        if (!this.lineStarted) {
                           if (openingBraces > 0 && this.reportProblems) {
                              this.sourceParser.problemReporter().javadocUnterminatedInlineTag(this.inlineTagStart, invalidInlineTagLineEnd);
                           }

                           considerTagAsPlainText = false;
                           this.inlineTagStarted = false;
                           openingBraces = 0;
                        }
                     } else if (this.lineStarted && previousChar != '{') {
                        textEndPosition = this.index;
                        if (verifText && this.tagValue == 3 && this.returnStatement != null) {
                           this.refreshReturnStatement();
                        } else if (isFormatterParser && this.textStart == -1) {
                           this.textStart = previousPosition;
                        }
                     } else {
                        if (this.inlineTagStarted) {
                           this.setInlineTagStarted(false);
                           if (this.reportProblems) {
                              initialIndex = previousPosition < invalidInlineTagLineEnd ? previousPosition : invalidInlineTagLineEnd;
                              this.sourceParser.problemReporter().javadocUnterminatedInlineTag(this.inlineTagStart, initialIndex);
                           }

                           validComment = false;
                           if (this.textStart != -1 && this.textStart < textEndPosition) {
                              this.pushText(this.textStart, textEndPosition);
                           }

                           if (isDomParser || isFormatterParser) {
                              this.refreshInlineTagPosition(textEndPosition);
                           }
                        }

                        if (previousChar == '{') {
                           if (this.textStart != -1 && this.textStart < textEndPosition) {
                              this.pushText(this.textStart, textEndPosition);
                           }

                           this.setInlineTagStarted(true);
                           invalidInlineTagLineEnd = this.lineEnd;
                        } else if (this.textStart != -1 && this.textStart < invalidTagLineEnd) {
                           this.pushText(this.textStart, invalidTagLineEnd);
                        }

                        this.scanner.resetTo(this.index, this.javadocEnd);
                        this.currentTokenType = -1;

                        try {
                           if (!this.parseTag(previousPosition)) {
                              validComment = false;
                              if (isDomParser) {
                                 this.createTag();
                              }

                              this.textStart = this.tagSourceEnd + 1;
                              invalidTagLineEnd = this.lineEnd;
                              textEndPosition = this.index;
                           }

                           if (!isFormatterParser && (this.tagValue == 19 || this.tagValue == 18)) {
                              considerTagAsPlainText = true;
                              ++openingBraces;
                           }
                        } catch (InvalidInputException var17) {
                           this.consumeToken();
                        }
                     }

                     this.lineStarted = true;
                     continue;
                  case '{':
                     if (verifText && this.tagValue == 3 && this.returnStatement != null) {
                        this.refreshReturnStatement();
                     }

                     if (considerTagAsPlainText) {
                        ++openingBraces;
                     } else if (this.inlineTagStarted) {
                        this.setInlineTagStarted(false);
                        if (this.reportProblems) {
                           initialIndex = previousPosition < invalidInlineTagLineEnd ? previousPosition : invalidInlineTagLineEnd;
                           this.sourceParser.problemReporter().javadocUnterminatedInlineTag(this.inlineTagStart, initialIndex);
                        }

                        if (this.lineStarted && this.textStart != -1 && this.textStart < textEndPosition) {
                           this.pushText(this.textStart, textEndPosition);
                        }

                        this.refreshInlineTagPosition(textEndPosition);
                        textEndPosition = this.index;
                     } else if (this.peekChar() != '@') {
                        if (this.textStart == -1) {
                           this.textStart = previousPosition;
                        }

                        textEndPosition = this.index;
                     }

                     if (!this.lineStarted) {
                        this.textStart = previousPosition;
                     }

                     this.lineStarted = true;
                     if (!considerTagAsPlainText) {
                        this.inlineTagStart = previousPosition;
                     }
                     continue;
                  case '}':
                     if (verifText && this.tagValue == 3 && this.returnStatement != null) {
                        this.refreshReturnStatement();
                     }

                     if (considerTagAsPlainText) {
                        invalidInlineTagLineEnd = this.lineEnd;
                        --openingBraces;
                        if (openingBraces == 0) {
                           considerTagAsPlainText = false;
                        }
                     }

                     if (this.inlineTagStarted) {
                        textEndPosition = this.index - 1;
                        if (!considerTagAsPlainText) {
                           if (this.lineStarted && this.textStart != -1 && this.textStart < textEndPosition) {
                              this.pushText(this.textStart, textEndPosition);
                           }

                           this.refreshInlineTagPosition(previousPosition);
                        }

                        if (!isFormatterParser && !considerTagAsPlainText) {
                           this.textStart = this.index;
                        }

                        this.setInlineTagStarted(false);
                     } else if (!this.lineStarted) {
                        this.textStart = previousPosition;
                     }

                     this.lineStarted = true;
                     textEndPosition = this.index;
                     continue;
               }

               if (isFormatterParser && nextCharacter == '<') {
                  initialIndex = this.index;
                  this.scanner.resetTo(this.index, this.javadocEnd);
                  if (!ScannerHelper.isWhitespace(previousChar)) {
                     textEndPosition = previousPosition;
                  }

                  if (this.parseHtmlTag(previousPosition, textEndPosition)) {
                     continue;
                  }

                  if (this.abort) {
                     return false;
                  }

                  this.scanner.currentPosition = initialIndex;
                  this.index = initialIndex;
               }

               if (verifText && this.tagValue == 3 && this.returnStatement != null) {
                  this.refreshReturnStatement();
               }

               if (!this.lineStarted || this.textStart == -1) {
                  this.textStart = previousPosition;
               }

               this.lineStarted = true;
               textEndPosition = this.index;
            }

            this.javadocTextEnd = this.starPosition - 1;
            if (!this.inlineTagStarted && !considerTagAsPlainText) {
               if (this.lineStarted && this.textStart != -1 && this.textStart <= textEndPosition && (this.textStart < this.starPosition || this.starPosition == lastStarPosition)) {
                  this.pushText(this.textStart, textEndPosition);
               }
            } else {
               if (this.reportProblems) {
                  initialIndex = this.javadocTextEnd < invalidInlineTagLineEnd ? this.javadocTextEnd : invalidInlineTagLineEnd;
                  if (this.index >= this.javadocEnd) {
                     initialIndex = invalidInlineTagLineEnd;
                  }

                  this.sourceParser.problemReporter().javadocUnterminatedInlineTag(this.inlineTagStart, initialIndex);
               }

               if (this.lineStarted && this.textStart != -1 && this.textStart < textEndPosition) {
                  this.pushText(this.textStart, textEndPosition);
               }

               this.refreshInlineTagPosition(textEndPosition);
               this.setInlineTagStarted(false);
            }

            this.updateDocComment();
            break;
         }
      } catch (Exception var18) {
         validComment = false;
      }

      return validComment;
   }

   protected void consumeToken() {
      this.currentTokenType = -1;
      this.updateLineEnd();
   }

   protected abstract Object createArgumentReference(char[] var1, int var2, boolean var3, Object var4, long[] var5, long var6) throws InvalidInputException;

   protected boolean createFakeReference(int start) {
      return true;
   }

   protected abstract Object createFieldReference(Object var1) throws InvalidInputException;

   protected abstract Object createMethodReference(Object var1, List var2) throws InvalidInputException;

   protected Object createReturnStatement() {
      return null;
   }

   protected abstract void createTag();

   protected abstract Object createTypeReference(int var1);

   private int getIndexPosition() {
      return this.index > this.lineEnd ? this.lineEnd : this.index - 1;
   }

   private int getLineNumber(int position) {
      if (this.scanner.linePtr != -1) {
         return Util.getLineNumber(position, this.scanner.lineEnds, 0, this.scanner.linePtr);
      } else {
         return this.lineEnds == null ? 1 : Util.getLineNumber(position, this.lineEnds, 0, this.lineEnds.length - 1);
      }
   }

   private int getTokenEndPosition() {
      return this.scanner.getCurrentTokenEndPosition() > this.lineEnd ? this.lineEnd : this.scanner.getCurrentTokenEndPosition();
   }

   protected int getCurrentTokenType() {
      return this.currentTokenType;
   }

   protected Object parseArguments(Object receiver) throws InvalidInputException {
      int modulo = 0;
      int iToken = 0;
      char[] argName = null;
      List arguments = new ArrayList(10);
      int start = this.scanner.getCurrentTokenStartPosition();
      Object typeRef = null;
      int dim = false;
      boolean isVarargs = false;
      long[] dimPositions = new long[20];
      char[] name = null;
      long argNamePos = -1L;

      while(this.index < this.scanner.eofPosition) {
         try {
            typeRef = this.parseQualifiedName(false);
            if (this.abort) {
               return null;
            }
         } catch (InvalidInputException var17) {
            break;
         }

         boolean firstArg = modulo == 0;
         if (firstArg) {
            if (iToken != 0) {
               break;
            }
         } else if (iToken % modulo != 0) {
            break;
         }

         int token;
         if (typeRef == null) {
            if (firstArg && this.currentTokenType == 25) {
               if (!this.verifySpaceOrEndComment()) {
                  token = this.starPosition == -1 ? this.lineEnd : this.starPosition;
                  if (this.source[token] == '\n') {
                     --token;
                  }

                  if (this.reportProblems) {
                     this.sourceParser.problemReporter().javadocMalformedSeeReference(start, token);
                  }

                  return null;
               }

               this.lineStarted = true;
               return this.createMethodReference(receiver, (List)null);
            }
            break;
         } else {
            ++iToken;
            int dim = 0;
            isVarargs = false;
            if (this.readToken() == 6) {
               while(this.readToken() == 6) {
                  token = this.scanner.getCurrentTokenStartPosition();
                  this.consumeToken();
                  if (this.readToken() != 66) {
                     throw new InvalidInputException();
                  }

                  this.consumeToken();
                  dimPositions[dim++] = ((long)token << 32) + (long)this.scanner.getCurrentTokenEndPosition();
               }
            } else if (this.readToken() == 122) {
               token = this.scanner.getCurrentTokenStartPosition();
               dimPositions[dim++] = ((long)token << 32) + (long)this.scanner.getCurrentTokenEndPosition();
               this.consumeToken();
               isVarargs = true;
            }

            argNamePos = -1L;
            if (this.readToken() == 22) {
               this.consumeToken();
               if (firstArg) {
                  if (iToken != 1) {
                     break;
                  }
               } else if (iToken % modulo != 1) {
                  break;
               }

               if (argName == null && !firstArg) {
                  break;
               }

               argName = this.scanner.getCurrentIdentifierSource();
               argNamePos = ((long)this.scanner.getCurrentTokenStartPosition() << 32) + (long)this.scanner.getCurrentTokenEndPosition();
               ++iToken;
            } else if (argName != null) {
               break;
            }

            if (firstArg) {
               modulo = iToken + 1;
            } else if (iToken % modulo != modulo - 1) {
               break;
            }

            token = this.readToken();
            char[] name = argName == null ? CharOperation.NO_CHAR : argName;
            Object argument;
            if (token == 32) {
               argument = this.createArgumentReference(name, dim, isVarargs, typeRef, dimPositions, argNamePos);
               if (this.abort) {
                  return null;
               }

               arguments.add(argument);
               this.consumeToken();
               ++iToken;
            } else {
               if (token == 25) {
                  if (!this.verifySpaceOrEndComment()) {
                     int end = this.starPosition == -1 ? this.lineEnd : this.starPosition;
                     if (this.source[end] == '\n') {
                        --end;
                     }

                     if (this.reportProblems) {
                        this.sourceParser.problemReporter().javadocMalformedSeeReference(start, end);
                     }

                     return null;
                  }

                  argument = this.createArgumentReference(name, dim, isVarargs, typeRef, dimPositions, argNamePos);
                  if (this.abort) {
                     return null;
                  }

                  arguments.add(argument);
                  this.consumeToken();
                  return this.createMethodReference(receiver, arguments);
               }
               break;
            }
         }
      }

      throw new InvalidInputException();
   }

   protected boolean parseHtmlTag(int previousPosition, int endTextPosition) throws InvalidInputException {
      return false;
   }

   protected boolean parseHref() throws InvalidInputException {
      boolean skipComments = this.scanner.skipComments;
      this.scanner.skipComments = true;

      try {
         int start = this.scanner.getCurrentTokenStartPosition();
         char currentChar = this.readChar();
         if (currentChar == 'a' || currentChar == 'A') {
            this.scanner.currentPosition = this.index;
            if (this.readToken() == 22) {
               this.consumeToken();

               try {
                  if (CharOperation.equals(this.scanner.getCurrentIdentifierSource(), HREF_TAG, false) && this.readToken() == 72) {
                     this.consumeToken();
                     if (this.readToken() == 46) {
                        this.consumeToken();

                        label267:
                        while(this.index < this.javadocEnd) {
                           while(true) {
                              if (this.readToken() == 15) {
                                 this.consumeToken();

                                 while(this.readToken() != 11) {
                                    if (this.scanner.currentPosition >= this.scanner.eofPosition || this.scanner.currentCharacter == '@' || this.inlineTagStarted && this.scanner.currentCharacter == '}') {
                                       this.index = this.tokenPreviousPosition;
                                       this.scanner.currentPosition = this.tokenPreviousPosition;
                                       this.currentTokenType = -1;
                                       if (this.tagValue != 10 && this.reportProblems) {
                                          this.sourceParser.problemReporter().javadocInvalidSeeHref(start, this.lineEnd);
                                       }

                                       return false;
                                    }

                                    this.consumeToken();
                                 }

                                 this.consumeToken();
                                 start = this.scanner.getCurrentTokenStartPosition();
                                 currentChar = this.readChar();
                                 if (currentChar == '/') {
                                    currentChar = this.readChar();
                                    if (currentChar == 'a' || currentChar == 'A') {
                                       currentChar = this.readChar();
                                       if (currentChar == '>') {
                                          return true;
                                       }
                                    }
                                 }

                                 if (currentChar == '\r' || currentChar == '\n' || currentChar == '\t' || currentChar == ' ') {
                                    break label267;
                                 }
                              } else {
                                 if (this.scanner.currentPosition >= this.scanner.eofPosition || this.scanner.currentCharacter == '@' || this.inlineTagStarted && this.scanner.currentCharacter == '}') {
                                    this.index = this.tokenPreviousPosition;
                                    this.scanner.currentPosition = this.tokenPreviousPosition;
                                    this.currentTokenType = -1;
                                    if (this.tagValue != 10 && this.reportProblems) {
                                       this.sourceParser.problemReporter().javadocInvalidSeeHref(start, this.lineEnd);
                                    }

                                    return false;
                                 }

                                 this.currentTokenType = -1;
                              }
                           }
                        }
                     }
                  }
               } catch (InvalidInputException var7) {
               }
            }
         }

         this.index = this.tokenPreviousPosition;
         this.scanner.currentPosition = this.tokenPreviousPosition;
         this.currentTokenType = -1;
         if (this.tagValue != 10 && this.reportProblems) {
            this.sourceParser.problemReporter().javadocInvalidSeeHref(start, this.lineEnd);
         }

         return false;
      } finally {
         this.scanner.skipComments = skipComments;
      }
   }

   protected boolean parseIdentifierTag(boolean report) {
      int token = this.readTokenSafely();
      switch (token) {
         case 22:
            this.pushIdentifier(true, false);
            return true;
         default:
            if (report) {
               this.sourceParser.problemReporter().javadocMissingIdentifier(this.tagSourceStart, this.tagSourceEnd, this.sourceParser.modifiers);
            }

            return false;
      }
   }

   protected Object parseMember(Object receiver) throws InvalidInputException {
      this.identifierPtr = -1;
      this.identifierLengthPtr = -1;
      int start = this.scanner.getCurrentTokenStartPosition();
      this.memberStart = start;
      int previousPosition;
      if (this.readToken() == 22) {
         if (this.scanner.currentCharacter == '.') {
            this.parseQualifiedName(true);
         } else {
            this.consumeToken();
            this.pushIdentifier(true, false);
         }

         previousPosition = this.index;
         int end;
         if (this.readToken() == 23) {
            this.consumeToken();
            start = this.scanner.getCurrentTokenStartPosition();

            try {
               return this.parseArguments(receiver);
            } catch (InvalidInputException var5) {
               end = this.scanner.getCurrentTokenEndPosition() < this.lineEnd ? this.scanner.getCurrentTokenEndPosition() : this.scanner.getCurrentTokenStartPosition();
               end = end < this.lineEnd ? end : this.lineEnd;
               if (this.reportProblems) {
                  this.sourceParser.problemReporter().javadocInvalidSeeReferenceArgs(start, end);
               }

               return null;
            }
         } else {
            this.index = previousPosition;
            this.scanner.currentPosition = previousPosition;
            this.currentTokenType = -1;
            if (!this.verifySpaceOrEndComment()) {
               end = this.starPosition == -1 ? this.lineEnd : this.starPosition;
               if (this.source[end] == '\n') {
                  --end;
               }

               if (this.reportProblems) {
                  this.sourceParser.problemReporter().javadocMalformedSeeReference(start, end);
               }

               return null;
            } else {
               return this.createFieldReference(receiver);
            }
         }
      } else {
         previousPosition = this.getTokenEndPosition() - 1;
         previousPosition = start > previousPosition ? start : previousPosition;
         if (this.reportProblems) {
            this.sourceParser.problemReporter().javadocInvalidReference(start, previousPosition);
         }

         this.index = this.tokenPreviousPosition;
         this.scanner.currentPosition = this.tokenPreviousPosition;
         this.currentTokenType = -1;
         return null;
      }
   }

   protected boolean parseParam() throws InvalidInputException {
      int start = this.tagSourceStart;
      int end = this.tagSourceEnd;
      boolean tokenWhiteSpace = this.scanner.tokenizeWhiteSpace;
      this.scanner.tokenizeWhiteSpace = true;

      try {
         boolean isCompletionParser = (this.kind & 8) != 0;
         if (this.scanner.currentCharacter != ' ' && !ScannerHelper.isWhitespace(this.scanner.currentCharacter)) {
            if (this.reportProblems) {
               this.sourceParser.problemReporter().javadocInvalidTag(start, this.scanner.getCurrentTokenEndPosition());
            }

            if (!isCompletionParser) {
               this.scanner.currentPosition = start;
               this.index = start;
            }

            this.currentTokenType = -1;
            return false;
         }

         this.identifierPtr = -1;
         this.identifierLengthPtr = -1;
         boolean hasMultiLines = this.scanner.currentPosition > this.lineEnd + 1;
         boolean isTypeParam = false;
         boolean valid = true;
         boolean empty = true;
         boolean mayBeGeneric = this.sourceLevel >= 3211264L;
         int token = -1;

         label614:
         while(true) {
            this.currentTokenType = -1;

            try {
               token = this.readToken();
            } catch (InvalidInputException var23) {
               valid = false;
            }

            label687: {
               switch (token) {
                  case 22:
                     if (valid) {
                        this.pushIdentifier(true, false);
                        start = this.scanner.getCurrentTokenStartPosition();
                        end = hasMultiLines ? this.lineEnd : this.scanner.getCurrentTokenEndPosition();
                        break;
                     }
                  case 11:
                     if (valid && mayBeGeneric) {
                        this.pushIdentifier(true, true);
                        start = this.scanner.getCurrentTokenStartPosition();
                        end = hasMultiLines ? this.lineEnd : this.scanner.getCurrentTokenEndPosition();
                        isTypeParam = true;
                        break;
                     }
                  default:
                     if (token == 18) {
                        isTypeParam = true;
                     }

                     if (valid && !hasMultiLines) {
                        start = this.scanner.getCurrentTokenStartPosition();
                     }

                     valid = false;
                     if (!hasMultiLines) {
                        empty = false;
                        end = hasMultiLines ? this.lineEnd : this.scanner.getCurrentTokenEndPosition();
                        continue;
                     }

                     end = this.lineEnd;
                  case 1000:
                     if (this.scanner.currentPosition > this.lineEnd + 1) {
                        hasMultiLines = true;
                     }

                     if (valid) {
                        continue;
                     }
                  case 61:
                     break label687;
               }

               label688: {
                  if (isTypeParam && mayBeGeneric) {
                     label587:
                     while(true) {
                        this.currentTokenType = -1;

                        try {
                           token = this.readToken();
                        } catch (InvalidInputException var22) {
                           valid = false;
                        }

                        switch (token) {
                           case 22:
                              end = hasMultiLines ? this.lineEnd : this.scanner.getCurrentTokenEndPosition();
                              if (valid) {
                                 this.pushIdentifier(false, false);
                                 boolean spaces = false;

                                 while(true) {
                                    this.currentTokenType = -1;

                                    try {
                                       token = this.readToken();
                                    } catch (InvalidInputException var21) {
                                       valid = false;
                                    }

                                    switch (token) {
                                       case 15:
                                          end = hasMultiLines ? this.lineEnd : this.scanner.getCurrentTokenEndPosition();
                                          if (valid) {
                                             this.pushIdentifier(false, true);
                                             break label587;
                                          }
                                          break;
                                       case 61:
                                          break label614;
                                       case 1000:
                                          if (this.scanner.currentPosition > this.lineEnd + 1) {
                                             hasMultiLines = true;
                                             valid = false;
                                          }

                                          spaces = true;
                                          if (!valid) {
                                             break label614;
                                          }
                                          break;
                                       default:
                                          if (!spaces) {
                                             end = hasMultiLines ? this.lineEnd : this.scanner.getCurrentTokenEndPosition();
                                          }

                                          valid = false;
                                    }
                                 }
                              }
                              break;
                           case 61:
                              break label688;
                           case 1000:
                              if (!valid || this.scanner.currentPosition > this.lineEnd + 1) {
                                 break label688;
                              }
                              break;
                           default:
                              end = hasMultiLines ? this.lineEnd : this.scanner.getCurrentTokenEndPosition();
                              valid = false;
                        }
                     }
                  }

                  if (valid) {
                     this.currentTokenType = -1;
                     int restart = this.scanner.currentPosition;

                     try {
                        token = this.readTokenAndConsume();
                     } catch (InvalidInputException var20) {
                        valid = false;
                     }

                     if (token == 1000) {
                        this.scanner.resetTo(restart, this.javadocEnd);
                        this.index = restart;
                        boolean var13 = this.pushParamName(isTypeParam);
                        return var13;
                     }
                  }

                  this.currentTokenType = -1;
                  if (isCompletionParser) {
                     return false;
                  }

                  if (this.reportProblems) {
                     end = hasMultiLines ? this.lineEnd : this.scanner.getCurrentTokenEndPosition();

                     try {
                        while((token = this.readToken()) != 1000 && token != 61) {
                           this.currentTokenType = -1;
                           end = hasMultiLines ? this.lineEnd : this.scanner.getCurrentTokenEndPosition();
                        }
                     } catch (InvalidInputException var24) {
                        end = this.lineEnd;
                     }

                     if (mayBeGeneric && isTypeParam) {
                        this.sourceParser.problemReporter().javadocInvalidParamTypeParameter(start, end);
                     } else {
                        this.sourceParser.problemReporter().javadocInvalidParamTagName(start, end);
                     }
                  }

                  this.scanner.currentPosition = start;
                  this.index = start;
                  this.currentTokenType = -1;
                  return false;
               }

               if (this.reportProblems) {
                  this.sourceParser.problemReporter().javadocInvalidParamTypeParameter(start, end);
               }

               if (!isCompletionParser) {
                  this.scanner.currentPosition = start;
                  this.index = start;
               }

               this.currentTokenType = -1;
               return false;
            }

            if (this.reportProblems) {
               if (empty) {
                  this.sourceParser.problemReporter().javadocMissingParamName(start, end, this.sourceParser.modifiers);
               } else if (mayBeGeneric && isTypeParam) {
                  this.sourceParser.problemReporter().javadocInvalidParamTypeParameter(start, end);
               } else {
                  this.sourceParser.problemReporter().javadocInvalidParamTagName(start, end);
               }
            }

            if (!isCompletionParser) {
               this.scanner.currentPosition = start;
               this.index = start;
            }

            this.currentTokenType = -1;
            return false;
         }

         if (this.reportProblems) {
            this.sourceParser.problemReporter().javadocInvalidParamTypeParameter(start, end);
         }

         if (!isCompletionParser) {
            this.scanner.currentPosition = start;
            this.index = start;
         }

         this.currentTokenType = -1;
      } finally {
         this.scanner.tokenizeWhiteSpace = tokenWhiteSpace;
      }

      return false;
   }

   protected Object parseQualifiedName(boolean reset) throws InvalidInputException {
      if (reset) {
         this.identifierPtr = -1;
         this.identifierLengthPtr = -1;
      }

      int primitiveToken = -1;
      int parserKind = this.kind & 255;
      int iToken = 0;

      label64:
      while(true) {
         int token = this.readTokenSafely();
         switch (token) {
            case 1:
               if ((iToken & 1) == 0) {
                  throw new InvalidInputException();
               }

               this.consumeToken();
               break;
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
            case 123:
               if (iToken == 0) {
                  this.pushIdentifier(true, true);
                  primitiveToken = token;
                  this.consumeToken();
                  break label64;
               }
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
            case 20:
            case 21:
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
            case 82:
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
            case 120:
            case 121:
            case 122:
            default:
               if (iToken == 0) {
                  if (this.identifierPtr >= 0) {
                     this.lastIdentifierEndPosition = (int)this.identifierPositionStack[this.identifierPtr];
                  }

                  return null;
               }

               if ((iToken & 1) == 0) {
                  switch (parserKind) {
                     case 2:
                        if (this.currentTokenType != -1) {
                           this.index = this.tokenPreviousPosition;
                           this.scanner.currentPosition = this.tokenPreviousPosition;
                           this.currentTokenType = -1;
                        }
                     default:
                        throw new InvalidInputException();
                     case 8:
                        if (this.identifierPtr >= 0) {
                           this.lastIdentifierEndPosition = (int)this.identifierPositionStack[this.identifierPtr];
                        }

                        return this.syntaxRecoverQualifiedName(primitiveToken);
                  }
               }
               break label64;
            case 22:
               if ((iToken & 1) != 0) {
                  break label64;
               }

               this.pushIdentifier(iToken == 0, false);
               this.consumeToken();
         }

         ++iToken;
      }

      if (parserKind != 8 && this.currentTokenType != -1) {
         this.index = this.tokenPreviousPosition;
         this.scanner.currentPosition = this.tokenPreviousPosition;
         this.currentTokenType = -1;
      }

      if (this.identifierPtr >= 0) {
         this.lastIdentifierEndPosition = (int)this.identifierPositionStack[this.identifierPtr];
      }

      return this.createTypeReference(primitiveToken);
   }

   protected boolean parseReference() throws InvalidInputException {
      int currentPosition = this.scanner.currentPosition;

      try {
         Object typeRef = null;
         Object reference = null;
         int previousPosition = true;
         int typeRefStartPosition = -1;

         int currentIndex;
         label207:
         while(this.index < this.scanner.eofPosition) {
            int previousPosition = this.index;
            currentIndex = this.readTokenSafely();
            int start;
            switch (currentIndex) {
               case 11:
                  if (typeRef == null) {
                     this.consumeToken();
                     start = this.scanner.getCurrentTokenStartPosition();
                     if (this.parseHref()) {
                        this.consumeToken();
                        if (this.tagValue == 10) {
                           if (this.reportProblems) {
                              this.sourceParser.problemReporter().javadocInvalidValueReference(start, this.getIndexPosition(), this.sourceParser.modifiers);
                           }

                           return false;
                        }

                        if (this.verifyEndLine(previousPosition)) {
                           return this.createFakeReference(start);
                        }

                        if (this.reportProblems) {
                           this.sourceParser.problemReporter().javadocUnexpectedText(this.scanner.currentPosition, this.lineEnd);
                        }
                     } else if (this.tagValue == 10 && this.reportProblems) {
                        this.sourceParser.problemReporter().javadocInvalidValueReference(start, this.getIndexPosition(), this.sourceParser.modifiers);
                     }

                     return false;
                  }
                  break label207;
               case 22:
                  if (typeRef != null) {
                     break label207;
                  }

                  typeRefStartPosition = this.scanner.getCurrentTokenStartPosition();
                  typeRef = this.parseQualifiedName(true);
                  if (this.abort) {
                     return false;
                  }
                  break;
               case 46:
                  if (typeRef == null) {
                     this.consumeToken();
                     start = this.scanner.getCurrentTokenStartPosition();
                     if (this.tagValue == 10) {
                        if (this.reportProblems) {
                           this.sourceParser.problemReporter().javadocInvalidValueReference(start, this.getTokenEndPosition(), this.sourceParser.modifiers);
                        }

                        return false;
                     }

                     if (this.verifyEndLine(previousPosition)) {
                        return this.createFakeReference(start);
                     }

                     if (this.reportProblems) {
                        this.sourceParser.problemReporter().javadocUnexpectedText(this.scanner.currentPosition, this.lineEnd);
                     }

                     return false;
                  }
                  break label207;
               case 129:
                  this.consumeToken();
                  if (this.scanner.currentCharacter == '#') {
                     reference = this.parseMember(typeRef);
                     if (reference != null) {
                        return this.pushSeeRef(reference);
                     }

                     return false;
                  }

                  char[] currentError = this.scanner.getCurrentIdentifierSource();
                  if (currentError.length > 0 && currentError[0] == '"') {
                     if (this.reportProblems) {
                        boolean isUrlRef = false;
                        if (this.tagValue == 6) {
                           int length = currentError.length;

                           int i;
                           for(i = 1; i < length && ScannerHelper.isLetter(currentError[i]); ++i) {
                           }

                           if (i < length - 2 && currentError[i] == ':' && currentError[i + 1] == '/' && currentError[i + 2] == '/') {
                              isUrlRef = true;
                           }
                        }

                        if (isUrlRef) {
                           this.sourceParser.problemReporter().javadocInvalidSeeUrlReference(this.scanner.getCurrentTokenStartPosition(), this.getTokenEndPosition());
                        } else {
                           this.sourceParser.problemReporter().javadocInvalidReference(this.scanner.getCurrentTokenStartPosition(), this.getTokenEndPosition());
                        }
                     }

                     return false;
                  }
               default:
                  break label207;
            }
         }

         if (reference == null) {
            reference = typeRef;
         }

         if (reference == null) {
            this.index = this.tokenPreviousPosition;
            this.scanner.currentPosition = this.tokenPreviousPosition;
            this.currentTokenType = -1;
            if (this.tagValue == 10) {
               if ((this.kind & 2) != 0) {
                  this.createTag();
               }

               return true;
            } else {
               if (this.reportProblems) {
                  this.sourceParser.problemReporter().javadocMissingReference(this.tagSourceStart, this.tagSourceEnd, this.sourceParser.modifiers);
               }

               return false;
            }
         } else {
            if (this.lastIdentifierEndPosition > this.javadocStart) {
               this.index = this.lastIdentifierEndPosition + 1;
               this.scanner.currentPosition = this.index;
            }

            this.currentTokenType = -1;
            if (this.tagValue == 10) {
               if (this.reportProblems) {
                  this.sourceParser.problemReporter().javadocInvalidReference(typeRefStartPosition, this.lineEnd);
               }

               return false;
            } else {
               currentIndex = this.index;
               char ch = this.readChar();
               switch (ch) {
                  case '(':
                     if (this.reportProblems) {
                        this.sourceParser.problemReporter().javadocMissingHashCharacter(typeRefStartPosition, this.lineEnd, String.valueOf(this.source, typeRefStartPosition, this.lineEnd - typeRefStartPosition + 1));
                     }

                     return false;
                  case ':':
                     ch = this.readChar();
                     if (ch == '/' && ch == this.readChar() && this.reportProblems) {
                        this.sourceParser.problemReporter().javadocInvalidSeeUrlReference(typeRefStartPosition, this.lineEnd);
                        return false;
                     }
                  default:
                     this.index = currentIndex;
                     if (!this.verifySpaceOrEndComment()) {
                        this.index = this.tokenPreviousPosition;
                        this.scanner.currentPosition = this.tokenPreviousPosition;
                        this.currentTokenType = -1;
                        int end = this.starPosition == -1 ? this.lineEnd : this.starPosition;
                        if (this.source[end] == '\n') {
                           --end;
                        }

                        if (this.reportProblems) {
                           this.sourceParser.problemReporter().javadocMalformedSeeReference(typeRefStartPosition, end);
                        }

                        return false;
                     } else {
                        return this.pushSeeRef(reference);
                     }
               }
            }
         }
      } catch (InvalidInputException var12) {
         if (this.reportProblems) {
            this.sourceParser.problemReporter().javadocInvalidReference(currentPosition, this.getTokenEndPosition());
         }

         this.index = this.tokenPreviousPosition;
         this.scanner.currentPosition = this.tokenPreviousPosition;
         this.currentTokenType = -1;
         return false;
      }
   }

   protected abstract boolean parseTag(int var1) throws InvalidInputException;

   protected boolean parseThrows() {
      int start = this.scanner.currentPosition;

      try {
         Object typeRef = this.parseQualifiedName(true);
         if (this.abort) {
            return false;
         }

         if (typeRef != null) {
            return this.pushThrowName(typeRef);
         }

         if (this.reportProblems) {
            this.sourceParser.problemReporter().javadocMissingThrowsClassName(this.tagSourceStart, this.tagSourceEnd, this.sourceParser.modifiers);
         }
      } catch (InvalidInputException var3) {
         if (this.reportProblems) {
            this.sourceParser.problemReporter().javadocInvalidThrowsClass(start, this.getTokenEndPosition());
         }
      }

      return false;
   }

   protected char peekChar() {
      int idx = this.index;
      char c = this.source[idx++];
      if (c == '\\' && this.source[idx] == 'u') {
         ++idx;

         while(this.source[idx] == 'u') {
            ++idx;
         }

         int c1;
         int c2;
         int c3;
         int c4;
         if ((c1 = ScannerHelper.getHexadecimalValue(this.source[idx++])) <= 15 && c1 >= 0 && (c2 = ScannerHelper.getHexadecimalValue(this.source[idx++])) <= 15 && c2 >= 0 && (c3 = ScannerHelper.getHexadecimalValue(this.source[idx++])) <= 15 && c3 >= 0 && (c4 = ScannerHelper.getHexadecimalValue(this.source[idx++])) <= 15 && c4 >= 0) {
            c = (char)(((c1 * 16 + c2) * 16 + c3) * 16 + c4);
         }
      }

      return c;
   }

   protected void pushIdentifier(boolean newLength, boolean isToken) {
      int stackLength = this.identifierStack.length;
      if (++this.identifierPtr >= stackLength) {
         System.arraycopy(this.identifierStack, 0, this.identifierStack = new char[stackLength + 10][], 0, stackLength);
         System.arraycopy(this.identifierPositionStack, 0, this.identifierPositionStack = new long[stackLength + 10], 0, stackLength);
      }

      this.identifierStack[this.identifierPtr] = isToken ? this.scanner.getCurrentTokenSource() : this.scanner.getCurrentIdentifierSource();
      this.identifierPositionStack[this.identifierPtr] = ((long)this.scanner.startPosition << 32) + (long)(this.scanner.currentPosition - 1);
      if (newLength) {
         stackLength = this.identifierLengthStack.length;
         if (++this.identifierLengthPtr >= stackLength) {
            System.arraycopy(this.identifierLengthStack, 0, this.identifierLengthStack = new int[stackLength + 10], 0, stackLength);
         }

         this.identifierLengthStack[this.identifierLengthPtr] = 1;
      } else {
         int var10002 = this.identifierLengthStack[this.identifierLengthPtr]++;
      }

   }

   protected void pushOnAstStack(Object node, boolean newLength) {
      int stackLength;
      if (node == null) {
         stackLength = this.astLengthStack.length;
         if (++this.astLengthPtr >= stackLength) {
            System.arraycopy(this.astLengthStack, 0, this.astLengthStack = new int[stackLength + 10], 0, stackLength);
         }

         this.astLengthStack[this.astLengthPtr] = 0;
      } else {
         stackLength = this.astStack.length;
         if (++this.astPtr >= stackLength) {
            System.arraycopy(this.astStack, 0, this.astStack = new Object[stackLength + 10], 0, stackLength);
            this.astPtr = stackLength;
         }

         this.astStack[this.astPtr] = node;
         if (newLength) {
            stackLength = this.astLengthStack.length;
            if (++this.astLengthPtr >= stackLength) {
               System.arraycopy(this.astLengthStack, 0, this.astLengthStack = new int[stackLength + 10], 0, stackLength);
            }

            this.astLengthStack[this.astLengthPtr] = 1;
         } else {
            int var10002 = this.astLengthStack[this.astLengthPtr]++;
         }

      }
   }

   protected abstract boolean pushParamName(boolean var1);

   protected abstract boolean pushSeeRef(Object var1);

   protected void pushText(int start, int end) {
   }

   protected abstract boolean pushThrowName(Object var1);

   protected char readChar() {
      char c = this.source[this.index++];
      if (c == '\\' && this.source[this.index] == 'u') {
         int pos;
         for(pos = this.index++; this.source[this.index] == 'u'; ++this.index) {
         }

         int c1;
         int c2;
         int c3;
         int c4;
         if ((c1 = ScannerHelper.getHexadecimalValue(this.source[this.index++])) <= 15 && c1 >= 0 && (c2 = ScannerHelper.getHexadecimalValue(this.source[this.index++])) <= 15 && c2 >= 0 && (c3 = ScannerHelper.getHexadecimalValue(this.source[this.index++])) <= 15 && c3 >= 0 && (c4 = ScannerHelper.getHexadecimalValue(this.source[this.index++])) <= 15 && c4 >= 0) {
            c = (char)(((c1 * 16 + c2) * 16 + c3) * 16 + c4);
         } else {
            this.index = pos;
         }
      }

      return c;
   }

   protected int readToken() throws InvalidInputException {
      if (this.currentTokenType < 0) {
         this.tokenPreviousPosition = this.scanner.currentPosition;
         this.currentTokenType = this.scanner.getNextToken();
         if (this.scanner.currentPosition > this.lineEnd + 1) {
            for(this.lineStarted = false; this.currentTokenType == 8; this.currentTokenType = this.scanner.getNextToken()) {
            }
         }

         this.index = this.scanner.currentPosition;
         this.lineStarted = true;
      }

      return this.currentTokenType;
   }

   protected int readTokenAndConsume() throws InvalidInputException {
      int token = this.readToken();
      this.consumeToken();
      return token;
   }

   protected int readTokenSafely() {
      int token = 129;

      try {
         token = this.readToken();
      } catch (InvalidInputException var2) {
      }

      return token;
   }

   protected void recordInheritedPosition(long position) {
      if (this.inheritedPositions == null) {
         this.inheritedPositions = new long[4];
         this.inheritedPositionsPtr = 0;
      } else if (this.inheritedPositionsPtr == this.inheritedPositions.length) {
         System.arraycopy(this.inheritedPositions, 0, this.inheritedPositions = new long[this.inheritedPositionsPtr + 4], 0, this.inheritedPositionsPtr);
      }

      this.inheritedPositions[this.inheritedPositionsPtr++] = position;
   }

   protected void refreshInlineTagPosition(int previousPosition) {
   }

   protected void refreshReturnStatement() {
   }

   protected void setInlineTagStarted(boolean started) {
      this.inlineTagStarted = started;
   }

   protected Object syntaxRecoverQualifiedName(int primitiveToken) throws InvalidInputException {
      return null;
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer();
      int startPos = this.scanner.currentPosition < this.index ? this.scanner.currentPosition : this.index;
      int endPos = this.scanner.currentPosition < this.index ? this.index : this.scanner.currentPosition;
      if (startPos == this.source.length) {
         return "EOF\n\n" + new String(this.source);
      } else if (endPos > this.source.length) {
         return "behind the EOF\n\n" + new String(this.source);
      } else {
         char[] front = new char[startPos];
         System.arraycopy(this.source, 0, front, 0, startPos);
         int middleLength = endPos - 1 - startPos + 1;
         char[] middle;
         if (middleLength > -1) {
            middle = new char[middleLength];
            System.arraycopy(this.source, startPos, middle, 0, middleLength);
         } else {
            middle = CharOperation.NO_CHAR;
         }

         char[] end = new char[this.source.length - (endPos - 1)];
         System.arraycopy(this.source, endPos - 1 + 1, end, 0, this.source.length - (endPos - 1) - 1);
         buffer.append(front);
         if (this.scanner.currentPosition < this.index) {
            buffer.append("\n===============================\nScanner current position here -->");
         } else {
            buffer.append("\n===============================\nParser index here -->");
         }

         buffer.append(middle);
         if (this.scanner.currentPosition < this.index) {
            buffer.append("<-- Parser index here\n===============================\n");
         } else {
            buffer.append("<-- Scanner current position here\n===============================\n");
         }

         buffer.append(end);
         return buffer.toString();
      }
   }

   protected abstract void updateDocComment();

   protected void updateLineEnd() {
      while(this.index > this.lineEnd + 1) {
         if (this.linePtr >= this.lastLinePtr) {
            this.lineEnd = this.javadocEnd;
            return;
         }

         this.lineEnd = this.scanner.getLineEnd(++this.linePtr) - 1;
      }

   }

   protected boolean verifyEndLine(int textPosition) {
      boolean domParser = (this.kind & 2) != 0;
      if (this.inlineTagStarted) {
         if (this.peekChar() == '}') {
            if (domParser) {
               this.createTag();
               this.pushText(textPosition, this.index);
            }

            return true;
         } else {
            return false;
         }
      } else {
         int startPosition = this.index;
         int previousPosition = this.index;
         this.starPosition = -1;
         char ch = this.readChar();

         while(true) {
            label50: {
               switch (ch) {
                  case '\t':
                  case '\f':
                  case ' ':
                     if (this.starPosition < 0) {
                        break label50;
                     }
                     break;
                  case '\n':
                  case '\r':
                     if (domParser) {
                        this.createTag();
                        this.pushText(textPosition, previousPosition);
                     }

                     this.index = previousPosition;
                     return true;
                  case '*':
                     this.starPosition = previousPosition;
                     break label50;
                  case '/':
                     if (this.starPosition >= textPosition) {
                        if (domParser) {
                           this.createTag();
                           this.pushText(textPosition, this.starPosition);
                        }

                        return true;
                     }
               }

               this.index = startPosition;
               return false;
            }

            previousPosition = this.index;
            ch = this.readChar();
         }
      }
   }

   protected boolean verifySpaceOrEndComment() {
      this.starPosition = -1;
      int startPosition = this.index;
      char ch = this.peekChar();
      switch (ch) {
         case '}':
            return this.inlineTagStarted;
         default:
            if (ScannerHelper.isWhitespace(ch)) {
               return true;
            } else {
               int previousPosition = this.index;
               ch = this.readChar();

               while(this.index < this.source.length) {
                  switch (ch) {
                     case '*':
                        this.starPosition = previousPosition;
                        previousPosition = this.index;
                        ch = this.readChar();
                        break;
                     case '/':
                        if (this.starPosition >= startPosition) {
                           return true;
                        }
                     default:
                        this.index = startPosition;
                        return false;
                  }
               }

               this.index = startPosition;
               return false;
            }
      }
   }
}
