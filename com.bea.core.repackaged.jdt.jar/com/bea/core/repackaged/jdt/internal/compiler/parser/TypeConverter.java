package com.bea.core.repackaged.jdt.internal.compiler.parser;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ArrayQualifiedTypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ArrayTypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ImportReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ParameterizedQualifiedTypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ParameterizedSingleTypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.QualifiedTypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.SingleTypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeParameter;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Wildcard;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;
import com.bea.core.repackaged.jdt.internal.compiler.problem.ProblemReporter;
import java.util.ArrayList;

public abstract class TypeConverter {
   int namePos;
   protected ProblemReporter problemReporter;
   protected boolean has1_5Compliance;
   private char memberTypeSeparator;

   protected TypeConverter(ProblemReporter problemReporter, char memberTypeSeparator) {
      this.problemReporter = problemReporter;
      this.has1_5Compliance = problemReporter.options.originalComplianceLevel >= 3211264L;
      this.memberTypeSeparator = memberTypeSeparator;
   }

   private void addIdentifiers(String typeSignature, int start, int endExclusive, int identCount, ArrayList fragments) {
      if (identCount == 1) {
         char[] identifier;
         typeSignature.getChars(start, endExclusive, identifier = new char[endExclusive - start], 0);
         fragments.add(identifier);
      } else {
         fragments.add(this.extractIdentifiers(typeSignature, start, endExclusive - 1, identCount));
      }

   }

   protected ImportReference createImportReference(String[] importName, int start, int end, boolean onDemand, int modifiers) {
      int length = importName.length;
      long[] positions = new long[length];
      long position = ((long)start << 32) + (long)end;
      char[][] qImportName = new char[length][];

      for(int i = 0; i < length; ++i) {
         qImportName[i] = importName[i].toCharArray();
         positions[i] = position;
      }

      return new ImportReference(qImportName, positions, onDemand, modifiers);
   }

   protected TypeParameter createTypeParameter(char[] typeParameterName, char[][] typeParameterBounds, int start, int end) {
      TypeParameter parameter = new TypeParameter();
      parameter.name = typeParameterName;
      parameter.sourceStart = start;
      parameter.sourceEnd = end;
      if (typeParameterBounds != null) {
         int length = typeParameterBounds.length;
         if (length > 0) {
            parameter.type = this.createTypeReference(typeParameterBounds[0], start, end);
            if (length > 1) {
               parameter.bounds = new TypeReference[length - 1];

               for(int i = 1; i < length; ++i) {
                  TypeReference bound = this.createTypeReference(typeParameterBounds[i], start, end);
                  bound.bits |= 16;
                  parameter.bounds[i - 1] = bound;
               }
            }
         }
      }

      return parameter;
   }

   protected TypeReference createTypeReference(char[] typeName, int start, int end, boolean includeGenericsAnyway) {
      int length = typeName.length;
      this.namePos = 0;
      return this.decodeType2(typeName, length, start, end, true);
   }

   protected TypeReference createTypeReference(char[] typeName, int start, int end) {
      int length = typeName.length;
      this.namePos = 0;
      return this.decodeType2(typeName, length, start, end, false);
   }

   protected TypeReference createTypeReference(String typeSignature, int start, int end) {
      int length = typeSignature.length();
      this.namePos = 0;
      return this.decodeType(typeSignature, length, start, end);
   }

   private TypeReference decodeType(String typeSignature, int length, int start, int end) {
      int identCount = 1;
      int dim = 0;
      int nameFragmentStart = this.namePos;
      int nameFragmentEnd = -1;
      boolean nameStarted = false;

      ArrayList fragments;
      int fragmentLength;
      label185:
      for(fragments = null; this.namePos < length; ++this.namePos) {
         fragmentLength = typeSignature.charAt(this.namePos);
         Wildcard result;
         switch (fragmentLength) {
            case 36:
               if (this.memberTypeSeparator != '$') {
                  break;
               }
            case 46:
               if (!nameStarted) {
                  nameFragmentStart = this.namePos + 1;
                  nameStarted = true;
               } else if (this.namePos > nameFragmentStart) {
                  ++identCount;
               }
               break;
            case 42:
               ++this.namePos;
               result = new Wildcard(0);
               result.sourceStart = start;
               result.sourceEnd = end;
               return result;
            case 43:
               ++this.namePos;
               result = new Wildcard(1);
               result.bound = this.decodeType(typeSignature, length, start, end);
               result.sourceStart = start;
               result.sourceEnd = end;
               return result;
            case 45:
               ++this.namePos;
               result = new Wildcard(2);
               result.bound = this.decodeType(typeSignature, length, start, end);
               result.sourceStart = start;
               result.sourceEnd = end;
               return result;
            case 59:
            case 62:
               nameFragmentEnd = this.namePos - 1;
               ++this.namePos;
               break label185;
            case 60:
               nameFragmentEnd = this.namePos - 1;
               if (!this.has1_5Compliance) {
                  break label185;
               }

               if (fragments == null) {
                  fragments = new ArrayList(2);
               }

               this.addIdentifiers(typeSignature, nameFragmentStart, nameFragmentEnd + 1, identCount, fragments);
               ++this.namePos;
               TypeReference[] arguments = this.decodeTypeArguments(typeSignature, length, start, end);
               fragments.add(arguments);
               identCount = 1;
               nameStarted = false;
               break;
            case 66:
               if (!nameStarted) {
                  ++this.namePos;
                  if (dim == 0) {
                     return new SingleTypeReference(TypeBinding.BYTE.simpleName, ((long)start << 32) + (long)end);
                  }

                  return new ArrayTypeReference(TypeBinding.BYTE.simpleName, dim, ((long)start << 32) + (long)end);
               }
               break;
            case 67:
               if (!nameStarted) {
                  ++this.namePos;
                  if (dim == 0) {
                     return new SingleTypeReference(TypeBinding.CHAR.simpleName, ((long)start << 32) + (long)end);
                  }

                  return new ArrayTypeReference(TypeBinding.CHAR.simpleName, dim, ((long)start << 32) + (long)end);
               }
               break;
            case 68:
               if (!nameStarted) {
                  ++this.namePos;
                  if (dim == 0) {
                     return new SingleTypeReference(TypeBinding.DOUBLE.simpleName, ((long)start << 32) + (long)end);
                  }

                  return new ArrayTypeReference(TypeBinding.DOUBLE.simpleName, dim, ((long)start << 32) + (long)end);
               }
               break;
            case 70:
               if (!nameStarted) {
                  ++this.namePos;
                  if (dim == 0) {
                     return new SingleTypeReference(TypeBinding.FLOAT.simpleName, ((long)start << 32) + (long)end);
                  }

                  return new ArrayTypeReference(TypeBinding.FLOAT.simpleName, dim, ((long)start << 32) + (long)end);
               }
               break;
            case 73:
               if (!nameStarted) {
                  ++this.namePos;
                  if (dim == 0) {
                     return new SingleTypeReference(TypeBinding.INT.simpleName, ((long)start << 32) + (long)end);
                  }

                  return new ArrayTypeReference(TypeBinding.INT.simpleName, dim, ((long)start << 32) + (long)end);
               }
               break;
            case 74:
               if (!nameStarted) {
                  ++this.namePos;
                  if (dim == 0) {
                     return new SingleTypeReference(TypeBinding.LONG.simpleName, ((long)start << 32) + (long)end);
                  }

                  return new ArrayTypeReference(TypeBinding.LONG.simpleName, dim, ((long)start << 32) + (long)end);
               }
               break;
            case 76:
            case 81:
            case 84:
               if (!nameStarted) {
                  nameFragmentStart = this.namePos + 1;
                  nameStarted = true;
               }
               break;
            case 83:
               if (!nameStarted) {
                  ++this.namePos;
                  if (dim == 0) {
                     return new SingleTypeReference(TypeBinding.SHORT.simpleName, ((long)start << 32) + (long)end);
                  }

                  return new ArrayTypeReference(TypeBinding.SHORT.simpleName, dim, ((long)start << 32) + (long)end);
               }
               break;
            case 86:
               if (!nameStarted) {
                  ++this.namePos;
                  return new SingleTypeReference(TypeBinding.VOID.simpleName, ((long)start << 32) + (long)end);
               }
               break;
            case 90:
               if (!nameStarted) {
                  ++this.namePos;
                  if (dim == 0) {
                     return new SingleTypeReference(TypeBinding.BOOLEAN.simpleName, ((long)start << 32) + (long)end);
                  }

                  return new ArrayTypeReference(TypeBinding.BOOLEAN.simpleName, dim, ((long)start << 32) + (long)end);
               }
               break;
            case 91:
               ++dim;
         }
      }

      int index;
      if (fragments == null) {
         if (identCount == 1) {
            char[] nameFragment;
            if (dim == 0) {
               nameFragment = new char[nameFragmentEnd - nameFragmentStart + 1];
               typeSignature.getChars(nameFragmentStart, nameFragmentEnd + 1, nameFragment, 0);
               return new SingleTypeReference(nameFragment, ((long)start << 32) + (long)end);
            } else {
               nameFragment = new char[nameFragmentEnd - nameFragmentStart + 1];
               typeSignature.getChars(nameFragmentStart, nameFragmentEnd + 1, nameFragment, 0);
               return new ArrayTypeReference(nameFragment, dim, ((long)start << 32) + (long)end);
            }
         } else {
            long[] positions = new long[identCount];
            long pos = ((long)start << 32) + (long)end;

            for(index = 0; index < identCount; ++index) {
               positions[index] = pos;
            }

            char[][] identifiers = this.extractIdentifiers(typeSignature, nameFragmentStart, nameFragmentEnd, identCount);
            return (TypeReference)(dim == 0 ? new QualifiedTypeReference(identifiers, positions) : new ArrayQualifiedTypeReference(identifiers, dim, positions));
         }
      } else {
         if (nameStarted) {
            this.addIdentifiers(typeSignature, nameFragmentStart, nameFragmentEnd + 1, identCount, fragments);
         }

         fragmentLength = fragments.size();
         if (fragmentLength == 2) {
            Object firstFragment = fragments.get(0);
            if (firstFragment instanceof char[]) {
               return new ParameterizedSingleTypeReference((char[])firstFragment, (TypeReference[])fragments.get(1), dim, ((long)start << 32) + (long)end);
            }
         }

         identCount = 0;

         for(int i = 0; i < fragmentLength; ++i) {
            Object element = fragments.get(i);
            if (element instanceof char[][]) {
               identCount += ((char[][])element).length;
            } else if (element instanceof char[]) {
               ++identCount;
            }
         }

         char[][] tokens = new char[identCount][];
         TypeReference[][] arguments = new TypeReference[identCount][];
         index = 0;

         int i;
         for(int i = 0; i < fragmentLength; ++i) {
            Object element = fragments.get(i);
            if (element instanceof char[][]) {
               char[][] fragmentTokens = (char[][])element;
               i = fragmentTokens.length;
               System.arraycopy(fragmentTokens, 0, tokens, index, i);
               index += i;
            } else if (element instanceof char[]) {
               tokens[index++] = (char[])element;
            } else {
               arguments[index - 1] = (TypeReference[])element;
            }
         }

         long[] positions = new long[identCount];
         long pos = ((long)start << 32) + (long)end;

         for(i = 0; i < identCount; ++i) {
            positions[i] = pos;
         }

         return new ParameterizedQualifiedTypeReference(tokens, arguments, dim, positions);
      }
   }

   private TypeReference decodeType2(char[] typeName, int length, int start, int end, boolean includeGenericsAnyway) {
      int identCount = 1;
      int dim = 0;
      int nameFragmentStart = this.namePos;
      int nameFragmentEnd = -1;
      ArrayList fragments = null;

      while(true) {
         label97: {
            if (this.namePos < length) {
               char currentChar = typeName[this.namePos];
               switch (currentChar) {
                  case ',':
                  case '>':
                     break;
                  case '.':
                     if (nameFragmentStart < 0) {
                        nameFragmentStart = this.namePos + 1;
                     }

                     ++identCount;
                     break label97;
                  case '<':
                     if ((this.has1_5Compliance || includeGenericsAnyway) && fragments == null) {
                        fragments = new ArrayList(2);
                     }

                     nameFragmentEnd = this.namePos - 1;
                     if (this.has1_5Compliance || includeGenericsAnyway) {
                        char[][] identifiers = CharOperation.splitOn('.', typeName, nameFragmentStart, this.namePos);
                        fragments.add(identifiers);
                     }

                     ++this.namePos;
                     TypeReference[] arguments = this.decodeTypeArguments(typeName, length, start, end, includeGenericsAnyway);
                     if (this.has1_5Compliance || includeGenericsAnyway) {
                        fragments.add(arguments);
                        identCount = 0;
                        nameFragmentStart = -1;
                        nameFragmentEnd = -1;
                     }
                     break label97;
                  case '?':
                     ++this.namePos;

                     while(typeName[this.namePos] == ' ') {
                        ++this.namePos;
                     }

                     int max;
                     int ahead;
                     Wildcard result;
                     label65:
                     switch (typeName[this.namePos]) {
                        case 'e':
                           max = TypeConstants.WILDCARD_EXTENDS.length - 1;

                           for(ahead = 1; ahead < max; ++ahead) {
                              if (typeName[this.namePos + ahead] != TypeConstants.WILDCARD_EXTENDS[ahead + 1]) {
                                 break label65;
                              }
                           }

                           this.namePos += max;
                           result = new Wildcard(1);
                           result.bound = this.decodeType2(typeName, length, start, end, includeGenericsAnyway);
                           result.sourceStart = start;
                           result.sourceEnd = end;
                           return result;
                        case 's':
                           max = TypeConstants.WILDCARD_SUPER.length - 1;
                           ahead = 1;

                           while(true) {
                              if (ahead >= max) {
                                 this.namePos += max;
                                 result = new Wildcard(2);
                                 result.bound = this.decodeType2(typeName, length, start, end, includeGenericsAnyway);
                                 result.sourceStart = start;
                                 result.sourceEnd = end;
                                 return result;
                              }

                              if (typeName[this.namePos + ahead] != TypeConstants.WILDCARD_SUPER[ahead + 1]) {
                                 break;
                              }

                              ++ahead;
                           }
                     }

                     Wildcard result = new Wildcard(0);
                     result.sourceStart = start;
                     result.sourceEnd = end;
                     return result;
                  case '[':
                     if (dim == 0 && nameFragmentEnd < 0) {
                        nameFragmentEnd = this.namePos - 1;
                     }

                     ++dim;
                  case ']':
                  default:
                     break label97;
               }
            }

            return this.decodeType3(typeName, length, start, end, identCount, dim, nameFragmentStart, nameFragmentEnd, fragments);
         }

         ++this.namePos;
      }
   }

   private TypeReference decodeType3(char[] typeName, int length, int start, int end, int identCount, int dim, int nameFragmentStart, int nameFragmentEnd, ArrayList fragments) {
      if (nameFragmentEnd < 0) {
         nameFragmentEnd = this.namePos - 1;
      }

      int index;
      int fragmentLength;
      int i;
      if (fragments == null) {
         if (identCount == 1) {
            if (dim != 0) {
               fragmentLength = nameFragmentEnd - nameFragmentStart + 1;
               char[] nameFragment = new char[fragmentLength];
               System.arraycopy(typeName, nameFragmentStart, nameFragment, 0, fragmentLength);
               return new ArrayTypeReference(nameFragment, dim, ((long)start << 32) + (long)end);
            } else {
               char[] nameFragment;
               if (nameFragmentStart == 0 && nameFragmentEnd < 0) {
                  nameFragment = typeName;
               } else {
                  i = nameFragmentEnd - nameFragmentStart + 1;
                  System.arraycopy(typeName, nameFragmentStart, nameFragment = new char[i], 0, i);
               }

               return new SingleTypeReference(nameFragment, ((long)start << 32) + (long)end);
            }
         } else {
            long[] positions = new long[identCount];
            long pos = ((long)start << 32) + (long)end;

            for(index = 0; index < identCount; ++index) {
               positions[index] = pos;
            }

            char[][] identifiers = CharOperation.splitOn('.', typeName, nameFragmentStart, nameFragmentEnd + 1);
            return (TypeReference)(dim == 0 ? new QualifiedTypeReference(identifiers, positions) : new ArrayQualifiedTypeReference(identifiers, dim, positions));
         }
      } else {
         if (nameFragmentStart > 0 && nameFragmentStart < length) {
            char[][] identifiers = CharOperation.splitOn('.', typeName, nameFragmentStart, nameFragmentEnd + 1);
            fragments.add(identifiers);
         }

         fragmentLength = fragments.size();
         char[][] tokens;
         if (fragmentLength == 2) {
            tokens = (char[][])fragments.get(0);
            if (tokens.length == 1) {
               return new ParameterizedSingleTypeReference(tokens[0], (TypeReference[])fragments.get(1), dim, ((long)start << 32) + (long)end);
            }
         }

         identCount = 0;

         for(i = 0; i < fragmentLength; ++i) {
            Object element = fragments.get(i);
            if (element instanceof char[][]) {
               identCount += ((char[][])element).length;
            }
         }

         tokens = new char[identCount][];
         TypeReference[][] arguments = new TypeReference[identCount][];
         index = 0;

         int i;
         for(int i = 0; i < fragmentLength; ++i) {
            Object element = fragments.get(i);
            if (element instanceof char[][]) {
               char[][] fragmentTokens = (char[][])element;
               i = fragmentTokens.length;
               System.arraycopy(fragmentTokens, 0, tokens, index, i);
               index += i;
            } else {
               arguments[index - 1] = (TypeReference[])element;
            }
         }

         long[] positions = new long[identCount];
         long pos = ((long)start << 32) + (long)end;

         for(i = 0; i < identCount; ++i) {
            positions[i] = pos;
         }

         return new ParameterizedQualifiedTypeReference(tokens, arguments, dim, positions);
      }
   }

   private TypeReference[] decodeTypeArguments(char[] typeName, int length, int start, int end, boolean includeGenericsAnyway) {
      ArrayList argumentList = new ArrayList(1);

      int count;
      for(count = 0; this.namePos < length; ++this.namePos) {
         TypeReference argument = this.decodeType2(typeName, length, start, end, includeGenericsAnyway);
         ++count;
         argumentList.add(argument);
         if (this.namePos >= length || typeName[this.namePos] == '>') {
            break;
         }
      }

      TypeReference[] typeArguments = new TypeReference[count];
      argumentList.toArray(typeArguments);
      return typeArguments;
   }

   private TypeReference[] decodeTypeArguments(String typeSignature, int length, int start, int end) {
      ArrayList argumentList = new ArrayList(1);
      int count = 0;

      while(this.namePos < length) {
         TypeReference argument = this.decodeType(typeSignature, length, start, end);
         ++count;
         argumentList.add(argument);
         if (this.namePos >= length || typeSignature.charAt(this.namePos) == '>') {
            break;
         }
      }

      TypeReference[] typeArguments = new TypeReference[count];
      argumentList.toArray(typeArguments);
      return typeArguments;
   }

   private char[][] extractIdentifiers(String typeSignature, int start, int endInclusive, int identCount) {
      char[][] result = new char[identCount][];
      int charIndex = start;
      int i = 0;

      while(true) {
         while(charIndex < endInclusive) {
            char currentChar;
            if ((currentChar = typeSignature.charAt(charIndex)) != this.memberTypeSeparator && currentChar != '.') {
               ++charIndex;
            } else {
               typeSignature.getChars(start, charIndex, result[i++] = new char[charIndex - start], 0);
               ++charIndex;
               start = charIndex;
            }
         }

         typeSignature.getChars(start, charIndex + 1, result[i++] = new char[charIndex - start + 1], 0);
         return result;
      }
   }
}
