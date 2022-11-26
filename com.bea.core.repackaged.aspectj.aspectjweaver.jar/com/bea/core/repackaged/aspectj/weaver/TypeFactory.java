package com.bea.core.repackaged.aspectj.weaver;

import java.util.ArrayList;

public class TypeFactory {
   public static ReferenceType createParameterizedType(ResolvedType aBaseType, UnresolvedType[] someTypeParameters, World inAWorld) {
      ResolvedType baseType = aBaseType;
      if (!aBaseType.isGenericType() && someTypeParameters != null && someTypeParameters.length > 0) {
         if (!aBaseType.isRawType()) {
            throw new IllegalStateException("Expecting raw type, not: " + aBaseType);
         }

         baseType = aBaseType.getGenericType();
         if (baseType == null) {
            throw new IllegalStateException("Raw type does not have generic type set");
         }
      }

      ResolvedType[] resolvedParameters = inAWorld.resolve(someTypeParameters);
      ReferenceType existingType = ((ReferenceType)baseType).findDerivativeType(resolvedParameters);
      ReferenceType pType = null;
      if (existingType != null) {
         pType = existingType;
      } else {
         pType = new ReferenceType((ResolvedType)baseType, resolvedParameters, inAWorld);
      }

      return (ReferenceType)pType.resolve(inAWorld);
   }

   public static UnresolvedType createUnresolvedParameterizedType(String sig, String erasuresig, UnresolvedType[] arguments) {
      return new UnresolvedType(sig, erasuresig, arguments);
   }

   static UnresolvedType convertSigToType(String aSignature) {
      UnresolvedType bound = null;
      int startOfParams = aSignature.indexOf(60);
      if (startOfParams == -1) {
         bound = UnresolvedType.forSignature(aSignature);
      } else {
         int endOfParams = aSignature.lastIndexOf(62);
         String signatureErasure = "L" + aSignature.substring(1, startOfParams) + ";";
         UnresolvedType[] typeParams = createTypeParams(aSignature.substring(startOfParams + 1, endOfParams));
         bound = new UnresolvedType("P" + aSignature.substring(1), signatureErasure, typeParams);
      }

      return bound;
   }

   public static UnresolvedType createTypeFromSignature(String signature) {
      char firstChar = signature.charAt(0);
      int leftAngleBracket;
      int endOfParams;
      StringBuffer erasureSig;
      String signatureErasure;
      String lastType;
      int nestedTypePosition;
      UnresolvedType[] typeParams;
      StringBuilder s;
      int firstAngleBracket;
      UnresolvedType[] arr$;
      int len$;
      int i$;
      UnresolvedType typeParameter;
      if (firstChar == 'P') {
         leftAngleBracket = signature.indexOf(60);
         if (leftAngleBracket == -1) {
            String signatureErasure = "L" + signature.substring(1);
            return new UnresolvedType(signature, signatureErasure, UnresolvedType.NONE);
         } else {
            endOfParams = locateMatchingEndAngleBracket(signature, leftAngleBracket);
            erasureSig = new StringBuffer(signature);
            erasureSig.setCharAt(0, 'L');

            while(leftAngleBracket != -1) {
               erasureSig.delete(leftAngleBracket, endOfParams + 1);
               leftAngleBracket = locateFirstBracket(erasureSig);
               if (leftAngleBracket != -1) {
                  endOfParams = locateMatchingEndAngleBracket(erasureSig, leftAngleBracket);
               }
            }

            signatureErasure = erasureSig.toString();
            lastType = null;
            nestedTypePosition = signature.indexOf("$", endOfParams);
            if (nestedTypePosition != -1) {
               lastType = signature.substring(nestedTypePosition + 1);
            } else {
               lastType = new String(signature);
            }

            leftAngleBracket = lastType.indexOf("<");
            typeParams = UnresolvedType.NONE;
            if (leftAngleBracket != -1) {
               endOfParams = locateMatchingEndAngleBracket(lastType, leftAngleBracket);
               typeParams = createTypeParams(lastType.substring(leftAngleBracket + 1, endOfParams));
            }

            s = new StringBuilder();
            firstAngleBracket = signature.indexOf(60);
            s.append("P").append(signature.substring(1, firstAngleBracket));
            s.append('<');
            arr$ = typeParams;
            len$ = typeParams.length;

            for(i$ = 0; i$ < len$; ++i$) {
               typeParameter = arr$[i$];
               s.append(typeParameter.getSignature());
            }

            s.append(">;");
            signature = s.toString();
            return new UnresolvedType(signature, signatureErasure, typeParams);
         }
      } else if ((firstChar == '?' || firstChar == '*') && signature.length() == 1) {
         return WildcardedUnresolvedType.QUESTIONMARK;
      } else {
         WildcardedUnresolvedType wildcardedUT;
         UnresolvedType lowerBound;
         if (firstChar == '+') {
            lowerBound = convertSigToType(signature.substring(1));
            wildcardedUT = new WildcardedUnresolvedType(signature, lowerBound, (UnresolvedType)null);
            return wildcardedUT;
         } else if (firstChar == '-') {
            lowerBound = convertSigToType(signature.substring(1));
            wildcardedUT = new WildcardedUnresolvedType(signature, (UnresolvedType)null, lowerBound);
            return wildcardedUT;
         } else if (firstChar == 'T') {
            String typeVariableName = signature.substring(1);
            if (typeVariableName.endsWith(";")) {
               typeVariableName = typeVariableName.substring(0, typeVariableName.length() - 1);
            }

            return new UnresolvedTypeVariableReferenceType(new TypeVariable(typeVariableName));
         } else if (firstChar == '[') {
            for(leftAngleBracket = 0; signature.charAt(leftAngleBracket) == '['; ++leftAngleBracket) {
            }

            UnresolvedType componentType = createTypeFromSignature(signature.substring(leftAngleBracket));
            return new UnresolvedType(signature, signature.substring(0, leftAngleBracket) + componentType.getErasureSignature());
         } else {
            if (signature.length() == 1) {
               switch (firstChar) {
                  case 'B':
                     return UnresolvedType.BYTE;
                  case 'C':
                     return UnresolvedType.CHAR;
                  case 'D':
                     return UnresolvedType.DOUBLE;
                  case 'E':
                  case 'G':
                  case 'H':
                  case 'K':
                  case 'L':
                  case 'M':
                  case 'N':
                  case 'O':
                  case 'P':
                  case 'Q':
                  case 'R':
                  case 'T':
                  case 'U':
                  case 'W':
                  case 'X':
                  case 'Y':
                  default:
                     break;
                  case 'F':
                     return UnresolvedType.FLOAT;
                  case 'I':
                     return UnresolvedType.INT;
                  case 'J':
                     return UnresolvedType.LONG;
                  case 'S':
                     return UnresolvedType.SHORT;
                  case 'V':
                     return UnresolvedType.VOID;
                  case 'Z':
                     return UnresolvedType.BOOLEAN;
               }
            } else {
               if (firstChar == '@') {
                  return ResolvedType.MISSING;
               }

               if (firstChar == 'L') {
                  leftAngleBracket = signature.indexOf(60);
                  if (leftAngleBracket == -1) {
                     return new UnresolvedType(signature);
                  }

                  endOfParams = locateMatchingEndAngleBracket(signature, leftAngleBracket);
                  erasureSig = new StringBuffer(signature);
                  erasureSig.setCharAt(0, 'L');

                  while(leftAngleBracket != -1) {
                     erasureSig.delete(leftAngleBracket, endOfParams + 1);
                     leftAngleBracket = locateFirstBracket(erasureSig);
                     if (leftAngleBracket != -1) {
                        endOfParams = locateMatchingEndAngleBracket(erasureSig, leftAngleBracket);
                     }
                  }

                  signatureErasure = erasureSig.toString();
                  lastType = null;
                  nestedTypePosition = signature.indexOf("$", endOfParams);
                  if (nestedTypePosition != -1) {
                     lastType = signature.substring(nestedTypePosition + 1);
                  } else {
                     lastType = new String(signature);
                  }

                  leftAngleBracket = lastType.indexOf("<");
                  typeParams = UnresolvedType.NONE;
                  if (leftAngleBracket != -1) {
                     endOfParams = locateMatchingEndAngleBracket(lastType, leftAngleBracket);
                     typeParams = createTypeParams(lastType.substring(leftAngleBracket + 1, endOfParams));
                  }

                  s = new StringBuilder();
                  firstAngleBracket = signature.indexOf(60);
                  s.append("P").append(signature.substring(1, firstAngleBracket));
                  s.append('<');
                  arr$ = typeParams;
                  len$ = typeParams.length;

                  for(i$ = 0; i$ < len$; ++i$) {
                     typeParameter = arr$[i$];
                     s.append(typeParameter.getSignature());
                  }

                  s.append(">;");
                  signature = s.toString();
                  return new UnresolvedType(signature, signatureErasure, typeParams);
               }
            }

            return new UnresolvedType(signature);
         }
      }
   }

   private static int locateMatchingEndAngleBracket(CharSequence signature, int startOfParams) {
      if (startOfParams == -1) {
         return -1;
      } else {
         int count = 1;
         int idx = startOfParams;
         int max = signature.length();

         while(idx < max) {
            ++idx;
            char ch = signature.charAt(idx);
            if (ch == '<') {
               ++count;
            } else if (ch == '>') {
               if (count == 1) {
                  break;
               }

               --count;
            }
         }

         return idx;
      }
   }

   private static int locateFirstBracket(StringBuffer signature) {
      int idx = 0;

      for(int max = signature.length(); idx < max; ++idx) {
         if (signature.charAt(idx) == '<') {
            return idx;
         }
      }

      return -1;
   }

   private static UnresolvedType[] createTypeParams(String typeParameterSpecification) {
      String remainingToProcess = typeParameterSpecification;

      ArrayList types;
      int endOfSig;
      for(types = new ArrayList(); remainingToProcess.length() != 0; remainingToProcess = remainingToProcess.substring(endOfSig)) {
         int endOfSig = false;
         int anglies = 0;
         boolean hadAnglies = false;
         boolean sigFound = false;

         for(endOfSig = 0; endOfSig < remainingToProcess.length() && !sigFound; ++endOfSig) {
            char thisChar = remainingToProcess.charAt(endOfSig);
            int nextChar;
            switch (thisChar) {
               case '*':
                  if (anglies == 0) {
                     nextChar = endOfSig + 1;
                     if (nextChar >= remainingToProcess.length()) {
                        sigFound = true;
                     } else {
                        char nextChar = remainingToProcess.charAt(nextChar);
                        if (nextChar != '+' && nextChar != '-') {
                           sigFound = true;
                        }
                     }
                  }
                  break;
               case ';':
                  if (anglies == 0) {
                     sigFound = true;
                  }
                  break;
               case '<':
                  ++anglies;
                  hadAnglies = true;
                  break;
               case '>':
                  --anglies;
                  break;
               case '[':
                  if (anglies == 0) {
                     for(nextChar = endOfSig + 1; remainingToProcess.charAt(nextChar) == '['; ++nextChar) {
                     }

                     if ("BCDFIJSZ".indexOf(remainingToProcess.charAt(nextChar)) != -1) {
                        sigFound = true;
                        endOfSig = nextChar;
                     }
                  }
            }
         }

         String forProcessing = remainingToProcess.substring(0, endOfSig);
         if (hadAnglies && forProcessing.charAt(0) == 'L') {
            forProcessing = "P" + forProcessing.substring(1);
         }

         types.add(createTypeFromSignature(forProcessing));
      }

      UnresolvedType[] typeParams = new UnresolvedType[types.size()];
      types.toArray(typeParams);
      return typeParams;
   }

   public static UnresolvedType createUnresolvedParameterizedType(String baseTypeSignature, UnresolvedType[] arguments) {
      StringBuffer parameterizedSig = new StringBuffer();
      parameterizedSig.append("P");
      parameterizedSig.append(baseTypeSignature.substring(1, baseTypeSignature.length() - 1));
      if (arguments.length > 0) {
         parameterizedSig.append("<");

         for(int i = 0; i < arguments.length; ++i) {
            parameterizedSig.append(arguments[i].getSignature());
         }

         parameterizedSig.append(">");
      }

      parameterizedSig.append(";");
      return createUnresolvedParameterizedType(parameterizedSig.toString(), baseTypeSignature, arguments);
   }
}
