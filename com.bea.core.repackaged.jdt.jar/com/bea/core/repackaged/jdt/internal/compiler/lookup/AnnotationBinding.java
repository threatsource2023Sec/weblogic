package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Annotation;
import java.util.Arrays;

public class AnnotationBinding {
   ReferenceBinding type;
   ElementValuePair[] pairs;

   public static AnnotationBinding[] addStandardAnnotations(AnnotationBinding[] recordedAnnotations, long annotationTagBits, LookupEnvironment env) {
      if ((annotationTagBits & 8646911250191613952L) == 0L) {
         return recordedAnnotations;
      } else {
         boolean haveDeprecated = false;
         AnnotationBinding[] var8 = recordedAnnotations;
         int var7 = recordedAnnotations.length;

         int index;
         for(index = 0; index < var7; ++index) {
            AnnotationBinding annotationBinding = var8[index];
            if (annotationBinding.getAnnotationType().id == 44) {
               haveDeprecated = true;
               break;
            }
         }

         int count = 0;
         if ((annotationTagBits & 2332882164804222976L) != 0L) {
            ++count;
         }

         if ((annotationTagBits & 52776558133248L) != 0L) {
            ++count;
         }

         if (!haveDeprecated && (annotationTagBits & 70368744177664L) != 0L) {
            ++count;
         }

         if ((annotationTagBits & 140737488355328L) != 0L) {
            ++count;
         }

         if ((annotationTagBits & 281474976710656L) != 0L) {
            ++count;
         }

         if ((annotationTagBits & 562949953421312L) != 0L) {
            ++count;
         }

         if ((annotationTagBits & 1125899906842624L) != 0L) {
            ++count;
         }

         if ((annotationTagBits & 4503599627370496L) != 0L) {
            ++count;
         }

         if ((annotationTagBits & 2251799813685248L) != 0L) {
            ++count;
         }

         if (count == 0) {
            return recordedAnnotations;
         } else {
            index = recordedAnnotations.length;
            AnnotationBinding[] result = new AnnotationBinding[index + count];
            System.arraycopy(recordedAnnotations, 0, result, 0, index);
            if ((annotationTagBits & 2332882164804222976L) != 0L) {
               result[index++] = buildTargetAnnotation(annotationTagBits, env);
            }

            if ((annotationTagBits & 52776558133248L) != 0L) {
               result[index++] = buildRetentionAnnotation(annotationTagBits, env);
            }

            if (!haveDeprecated && (annotationTagBits & 70368744177664L) != 0L) {
               result[index++] = buildMarkerAnnotation(TypeConstants.JAVA_LANG_DEPRECATED, env.javaBaseModule(), env);
            }

            if ((annotationTagBits & 140737488355328L) != 0L) {
               result[index++] = buildMarkerAnnotation(TypeConstants.JAVA_LANG_ANNOTATION_DOCUMENTED, env.javaBaseModule(), env);
            }

            if ((annotationTagBits & 281474976710656L) != 0L) {
               result[index++] = buildMarkerAnnotation(TypeConstants.JAVA_LANG_ANNOTATION_INHERITED, env.javaBaseModule(), env);
            }

            if ((annotationTagBits & 562949953421312L) != 0L) {
               result[index++] = buildMarkerAnnotation(TypeConstants.JAVA_LANG_OVERRIDE, env.javaBaseModule(), env);
            }

            if ((annotationTagBits & 1125899906842624L) != 0L) {
               result[index++] = buildMarkerAnnotation(TypeConstants.JAVA_LANG_SUPPRESSWARNINGS, env.javaBaseModule(), env);
            }

            if ((annotationTagBits & 4503599627370496L) != 0L) {
               result[index++] = buildMarkerAnnotationForMemberType(TypeConstants.JAVA_LANG_INVOKE_METHODHANDLE_$_POLYMORPHICSIGNATURE, env.javaBaseModule(), env);
            }

            if ((annotationTagBits & 2251799813685248L) != 0L) {
               result[index++] = buildMarkerAnnotation(TypeConstants.JAVA_LANG_SAFEVARARGS, env.javaBaseModule(), env);
            }

            return result;
         }
      }
   }

   private static AnnotationBinding buildMarkerAnnotationForMemberType(char[][] compoundName, ModuleBinding module, LookupEnvironment env) {
      ReferenceBinding type = env.getResolvedType(compoundName, module, (Scope)null);
      if (!type.isValidBinding()) {
         type = ((ProblemReferenceBinding)type).closestMatch;
      }

      return env.createAnnotation(type, Binding.NO_ELEMENT_VALUE_PAIRS);
   }

   private static AnnotationBinding buildMarkerAnnotation(char[][] compoundName, ModuleBinding module, LookupEnvironment env) {
      ReferenceBinding type = env.getResolvedType(compoundName, module, (Scope)null);
      return env.createAnnotation(type, Binding.NO_ELEMENT_VALUE_PAIRS);
   }

   private static AnnotationBinding buildRetentionAnnotation(long bits, LookupEnvironment env) {
      ReferenceBinding retentionPolicy = env.getResolvedJavaBaseType(TypeConstants.JAVA_LANG_ANNOTATION_RETENTIONPOLICY, (Scope)null);
      Object value = null;
      if ((bits & 52776558133248L) == 52776558133248L) {
         value = retentionPolicy.getField(TypeConstants.UPPER_RUNTIME, true);
      } else if ((bits & 35184372088832L) != 0L) {
         value = retentionPolicy.getField(TypeConstants.UPPER_CLASS, true);
      } else if ((bits & 17592186044416L) != 0L) {
         value = retentionPolicy.getField(TypeConstants.UPPER_SOURCE, true);
      }

      return env.createAnnotation(env.getResolvedJavaBaseType(TypeConstants.JAVA_LANG_ANNOTATION_RETENTION, (Scope)null), new ElementValuePair[]{new ElementValuePair(TypeConstants.VALUE, value, (MethodBinding)null)});
   }

   private static AnnotationBinding buildTargetAnnotation(long bits, LookupEnvironment env) {
      ReferenceBinding target = env.getResolvedJavaBaseType(TypeConstants.JAVA_LANG_ANNOTATION_TARGET, (Scope)null);
      if ((bits & 34359738368L) != 0L) {
         return new AnnotationBinding(target, Binding.NO_ELEMENT_VALUE_PAIRS);
      } else {
         int arraysize = 0;
         if ((bits & 4398046511104L) != 0L) {
            ++arraysize;
         }

         if ((bits & 1099511627776L) != 0L) {
            ++arraysize;
         }

         if ((bits & 137438953472L) != 0L) {
            ++arraysize;
         }

         if ((bits & 2199023255552L) != 0L) {
            ++arraysize;
         }

         if ((bits & 274877906944L) != 0L) {
            ++arraysize;
         }

         if ((bits & 8796093022208L) != 0L) {
            ++arraysize;
         }

         if ((bits & 549755813888L) != 0L) {
            ++arraysize;
         }

         if ((bits & 68719476736L) != 0L) {
            ++arraysize;
         }

         if ((bits & 9007199254740992L) != 0L) {
            ++arraysize;
         }

         if ((bits & 18014398509481984L) != 0L) {
            ++arraysize;
         }

         if ((bits & 2305843009213693952L) != 0L) {
            ++arraysize;
         }

         Object[] value = new Object[arraysize];
         if (arraysize > 0) {
            ReferenceBinding elementType = env.getResolvedType(TypeConstants.JAVA_LANG_ANNOTATION_ELEMENTTYPE, (Scope)null);
            int index = 0;
            if ((bits & 9007199254740992L) != 0L) {
               value[index++] = elementType.getField(TypeConstants.TYPE_USE_TARGET, true);
            }

            if ((bits & 4398046511104L) != 0L) {
               value[index++] = elementType.getField(TypeConstants.UPPER_ANNOTATION_TYPE, true);
            }

            if ((bits & 1099511627776L) != 0L) {
               value[index++] = elementType.getField(TypeConstants.UPPER_CONSTRUCTOR, true);
            }

            if ((bits & 137438953472L) != 0L) {
               value[index++] = elementType.getField(TypeConstants.UPPER_FIELD, true);
            }

            if ((bits & 274877906944L) != 0L) {
               value[index++] = elementType.getField(TypeConstants.UPPER_METHOD, true);
            }

            if ((bits & 8796093022208L) != 0L) {
               value[index++] = elementType.getField(TypeConstants.UPPER_PACKAGE, true);
            }

            if ((bits & 549755813888L) != 0L) {
               value[index++] = elementType.getField(TypeConstants.UPPER_PARAMETER, true);
            }

            if ((bits & 18014398509481984L) != 0L) {
               value[index++] = elementType.getField(TypeConstants.TYPE_PARAMETER_TARGET, true);
            }

            if ((bits & 68719476736L) != 0L) {
               value[index++] = elementType.getField(TypeConstants.TYPE, true);
            }

            if ((bits & 2199023255552L) != 0L) {
               value[index++] = elementType.getField(TypeConstants.UPPER_LOCAL_VARIABLE, true);
            }
         }

         return env.createAnnotation(target, new ElementValuePair[]{new ElementValuePair(TypeConstants.VALUE, value, (MethodBinding)null)});
      }
   }

   public AnnotationBinding(ReferenceBinding type, ElementValuePair[] pairs) {
      this.type = type;
      this.pairs = pairs;
   }

   AnnotationBinding(Annotation astAnnotation) {
      this((ReferenceBinding)astAnnotation.resolvedType, astAnnotation.computeElementValuePairs());
   }

   public char[] computeUniqueKey(char[] recipientKey) {
      char[] typeKey = this.type.computeUniqueKey(false);
      int recipientKeyLength = recipientKey.length;
      char[] uniqueKey = new char[recipientKeyLength + 1 + typeKey.length];
      System.arraycopy(recipientKey, 0, uniqueKey, 0, recipientKeyLength);
      uniqueKey[recipientKeyLength] = '@';
      System.arraycopy(typeKey, 0, uniqueKey, recipientKeyLength + 1, typeKey.length);
      return uniqueKey;
   }

   public ReferenceBinding getAnnotationType() {
      return this.type;
   }

   public void resolve() {
   }

   public ElementValuePair[] getElementValuePairs() {
      return this.pairs;
   }

   public static void setMethodBindings(ReferenceBinding type, ElementValuePair[] pairs) {
      int i = pairs.length;

      while(true) {
         --i;
         if (i < 0) {
            return;
         }

         ElementValuePair pair = pairs[i];
         MethodBinding[] methods = type.getMethods(pair.getName());
         if (methods != null && methods.length == 1) {
            pair.setMethodBinding(methods[0]);
         }
      }
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer(5);
      buffer.append('@').append(this.type.sourceName);
      if (this.pairs != null && this.pairs.length > 0) {
         buffer.append('(');
         if (this.pairs.length == 1 && CharOperation.equals(this.pairs[0].getName(), TypeConstants.VALUE)) {
            buffer.append(this.pairs[0].value);
         } else {
            int i = 0;

            for(int max = this.pairs.length; i < max; ++i) {
               if (i > 0) {
                  buffer.append(", ");
               }

               buffer.append(this.pairs[i]);
            }
         }

         buffer.append(')');
      }

      return buffer.toString();
   }

   public int hashCode() {
      int result = 17;
      int c = this.getAnnotationType().hashCode();
      result = 31 * result + c;
      c = Arrays.hashCode(this.getElementValuePairs());
      result = 31 * result + c;
      return result;
   }

   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (!(object instanceof AnnotationBinding)) {
         return false;
      } else {
         AnnotationBinding that = (AnnotationBinding)object;
         if (this.getAnnotationType() != that.getAnnotationType()) {
            return false;
         } else {
            ElementValuePair[] thisElementValuePairs = this.getElementValuePairs();
            ElementValuePair[] thatElementValuePairs = that.getElementValuePairs();
            int length = thisElementValuePairs.length;
            if (length != thatElementValuePairs.length) {
               return false;
            } else {
               label63:
               for(int i = 0; i < length; ++i) {
                  ElementValuePair thisPair = thisElementValuePairs[i];

                  for(int j = 0; j < length; ++j) {
                     ElementValuePair thatPair = thatElementValuePairs[j];
                     if (thisPair.binding == thatPair.binding) {
                        if (thisPair.value == null) {
                           if (thatPair.value != null) {
                              return false;
                           }
                        } else {
                           if (thatPair.value == null) {
                              return false;
                           }

                           if (thatPair.value instanceof Object[] && thisPair.value instanceof Object[]) {
                              if (!Arrays.equals((Object[])thisPair.value, (Object[])thatPair.value)) {
                                 return false;
                              }
                           } else if (!thatPair.value.equals(thisPair.value)) {
                              return false;
                           }
                        }
                        continue label63;
                     }
                  }

                  return false;
               }

               return true;
            }
         }
      }
   }
}
