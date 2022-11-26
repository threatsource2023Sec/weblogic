package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.AnnotatedElement;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class WildAnnotationTypePattern extends AnnotationTypePattern {
   private TypePattern typePattern;
   private boolean resolved = false;
   Map annotationValues;
   private static final byte VERSION = 1;

   public WildAnnotationTypePattern(TypePattern typePattern) {
      this.typePattern = typePattern;
      this.setLocation(typePattern.getSourceContext(), typePattern.start, typePattern.end);
   }

   public WildAnnotationTypePattern(TypePattern typePattern, Map annotationValues) {
      this.typePattern = typePattern;
      this.annotationValues = annotationValues;
      this.setLocation(typePattern.getSourceContext(), typePattern.start, typePattern.end);
   }

   public TypePattern getTypePattern() {
      return this.typePattern;
   }

   public FuzzyBoolean matches(AnnotatedElement annotated) {
      return this.matches(annotated, (ResolvedType[])null);
   }

   protected void resolveAnnotationValues(ResolvedType annotationType, IScope scope) {
      if (this.annotationValues != null) {
         Map replacementValues = new HashMap();
         Set keys = this.annotationValues.keySet();
         ResolvedMember[] ms = annotationType.getDeclaredMethods();
         Iterator i$ = keys.iterator();

         while(i$.hasNext()) {
            String k = (String)i$.next();
            String key = k;
            if (k.endsWith("!")) {
               key = k.substring(0, k.length() - 1);
            }

            String v = (String)this.annotationValues.get(k);
            boolean validKey = false;

            for(int i = 0; i < ms.length; ++i) {
               ResolvedMember resolvedMember = ms[i];
               if (resolvedMember.getName().equals(key) && resolvedMember.isAbstract()) {
                  validKey = true;
                  ResolvedType t = resolvedMember.getReturnType().resolve(scope.getWorld());
                  int value;
                  IMessage m;
                  if (t.isEnum()) {
                     value = v.lastIndexOf(".");
                     if (value != -1) {
                        String typename = v.substring(0, value);
                        ResolvedType rt = scope.lookupType(typename, this).resolve(scope.getWorld());
                        v = rt.getSignature() + v.substring(value + 1);
                        replacementValues.put(k, v);
                        break;
                     }

                     m = MessageUtil.error(WeaverMessages.format("invalidAnnotationValue", v, "enum"), this.getSourceLocation());
                     scope.getWorld().getMessageHandler().handleMessage(m);
                  } else if (t.isPrimitiveType()) {
                     if (t.getSignature().equals("I")) {
                        try {
                           value = Integer.parseInt(v);
                           replacementValues.put(k, Integer.toString(value));
                           break;
                        } catch (NumberFormatException var22) {
                           m = MessageUtil.error(WeaverMessages.format("invalidAnnotationValue", v, "int"), this.getSourceLocation());
                           scope.getWorld().getMessageHandler().handleMessage(m);
                        }
                     } else if (t.getSignature().equals("F")) {
                        try {
                           float value = Float.parseFloat(v);
                           replacementValues.put(k, Float.toString(value));
                           break;
                        } catch (NumberFormatException var21) {
                           m = MessageUtil.error(WeaverMessages.format("invalidAnnotationValue", v, "float"), this.getSourceLocation());
                           scope.getWorld().getMessageHandler().handleMessage(m);
                        }
                     } else {
                        IMessage m;
                        if (t.getSignature().equals("Z")) {
                           if (!v.equalsIgnoreCase("true") && !v.equalsIgnoreCase("false")) {
                              m = MessageUtil.error(WeaverMessages.format("invalidAnnotationValue", v, "boolean"), this.getSourceLocation());
                              scope.getWorld().getMessageHandler().handleMessage(m);
                           }
                        } else if (t.getSignature().equals("S")) {
                           try {
                              short value = Short.parseShort(v);
                              replacementValues.put(k, Short.toString(value));
                              break;
                           } catch (NumberFormatException var20) {
                              m = MessageUtil.error(WeaverMessages.format("invalidAnnotationValue", v, "short"), this.getSourceLocation());
                              scope.getWorld().getMessageHandler().handleMessage(m);
                           }
                        } else if (t.getSignature().equals("J")) {
                           try {
                              replacementValues.put(k, Long.toString(Long.parseLong(v)));
                              break;
                           } catch (NumberFormatException var19) {
                              m = MessageUtil.error(WeaverMessages.format("invalidAnnotationValue", v, "long"), this.getSourceLocation());
                              scope.getWorld().getMessageHandler().handleMessage(m);
                           }
                        } else if (t.getSignature().equals("D")) {
                           try {
                              replacementValues.put(k, Double.toString(Double.parseDouble(v)));
                              break;
                           } catch (NumberFormatException var18) {
                              m = MessageUtil.error(WeaverMessages.format("invalidAnnotationValue", v, "double"), this.getSourceLocation());
                              scope.getWorld().getMessageHandler().handleMessage(m);
                           }
                        } else if (t.getSignature().equals("B")) {
                           try {
                              replacementValues.put(k, Byte.toString(Byte.parseByte(v)));
                              break;
                           } catch (NumberFormatException var17) {
                              m = MessageUtil.error(WeaverMessages.format("invalidAnnotationValue", v, "byte"), this.getSourceLocation());
                              scope.getWorld().getMessageHandler().handleMessage(m);
                           }
                        } else {
                           if (!t.getSignature().equals("C")) {
                              throw new RuntimeException("Not implemented for " + t);
                           }

                           if (v.length() == 3) {
                              replacementValues.put(k, v.substring(1, 2));
                              break;
                           }

                           m = MessageUtil.error(WeaverMessages.format("invalidAnnotationValue", v, "char"), this.getSourceLocation());
                           scope.getWorld().getMessageHandler().handleMessage(m);
                        }
                     }
                  } else if (!t.equals(ResolvedType.JL_STRING)) {
                     String typename;
                     ResolvedType rt;
                     IMessage m;
                     if (t.equals(ResolvedType.JL_CLASS) || t.isParameterizedOrGenericType() && t.getRawType().equals(ResolvedType.JL_CLASS)) {
                        typename = v.substring(0, v.lastIndexOf(46));
                        rt = scope.lookupType(typename, this).resolve(scope.getWorld());
                        if (rt.isMissing()) {
                           m = MessageUtil.error("Unable to resolve type '" + v + "' specified for value '" + k + "'", this.getSourceLocation());
                           scope.getWorld().getMessageHandler().handleMessage(m);
                        }

                        replacementValues.put(k, rt.getSignature());
                        break;
                     }

                     if (t.isAnnotation()) {
                        if (v.indexOf("(") != -1) {
                           throw new RuntimeException("Compiler limitation: annotation values can only currently be marker annotations (no values): " + v);
                        }

                        typename = v.substring(1);
                        rt = scope.lookupType(typename, this).resolve(scope.getWorld());
                        if (rt.isMissing()) {
                           m = MessageUtil.error("Unable to resolve type '" + v + "' specified for value '" + k + "'", this.getSourceLocation());
                           scope.getWorld().getMessageHandler().handleMessage(m);
                        }

                        replacementValues.put(k, rt.getSignature());
                        break;
                     }

                     scope.message(MessageUtil.error(WeaverMessages.format("unsupportedAnnotationValueType", t), this.getSourceLocation()));
                     replacementValues.put(k, "");
                  }
               }
            }

            if (!validKey) {
               IMessage m = MessageUtil.error(WeaverMessages.format("unknownAnnotationValue", annotationType, k), this.getSourceLocation());
               scope.getWorld().getMessageHandler().handleMessage(m);
            }
         }

         this.annotationValues.putAll(replacementValues);
      }
   }

   public FuzzyBoolean matches(AnnotatedElement annotated, ResolvedType[] parameterAnnotations) {
      if (!this.resolved) {
         throw new IllegalStateException("Can't match on an unresolved annotation type pattern");
      } else if (this.annotationValues != null && !this.typePattern.hasFailedResolution()) {
         throw new IllegalStateException("Cannot use annotationvalues with a wild annotation pattern");
      } else {
         if (this.isForParameterAnnotationMatch()) {
            if (parameterAnnotations != null && parameterAnnotations.length != 0) {
               for(int i = 0; i < parameterAnnotations.length; ++i) {
                  if (this.typePattern.matches(parameterAnnotations[i], TypePattern.STATIC).alwaysTrue()) {
                     return FuzzyBoolean.YES;
                  }
               }
            }
         } else {
            ResolvedType[] annTypes = annotated.getAnnotationTypes();
            if (annTypes != null && annTypes.length != 0) {
               for(int i = 0; i < annTypes.length; ++i) {
                  if (this.typePattern.matches(annTypes[i], TypePattern.STATIC).alwaysTrue()) {
                     return FuzzyBoolean.YES;
                  }
               }
            }
         }

         return FuzzyBoolean.NO;
      }
   }

   public void resolve(World world) {
      if (!this.resolved) {
         if (this.typePattern instanceof WildTypePattern && (this.annotationValues == null || this.annotationValues.isEmpty())) {
            WildTypePattern wildTypePattern = (WildTypePattern)this.typePattern;
            String fullyQualifiedName = wildTypePattern.maybeGetCleanName();
            if (fullyQualifiedName != null && fullyQualifiedName.indexOf(".") != -1) {
               ResolvedType resolvedType = world.resolve(UnresolvedType.forName(fullyQualifiedName));
               if (resolvedType != null && !resolvedType.isMissing()) {
                  this.typePattern = new ExactTypePattern(resolvedType, false, false);
               }
            }
         }

         this.resolved = true;
      }

   }

   public AnnotationTypePattern resolveBindings(IScope scope, Bindings bindings, boolean allowBinding) {
      if (!scope.getWorld().isInJava5Mode()) {
         scope.message(MessageUtil.error(WeaverMessages.format("annotationsRequireJava5"), this.getSourceLocation()));
         return this;
      } else if (this.resolved) {
         return this;
      } else {
         this.typePattern = this.typePattern.resolveBindings(scope, bindings, false, false);
         this.resolved = true;
         if (this.typePattern instanceof ExactTypePattern) {
            ExactTypePattern et = (ExactTypePattern)this.typePattern;
            if (!et.getExactType().resolve(scope.getWorld()).isAnnotation()) {
               IMessage m = MessageUtil.error(WeaverMessages.format("referenceToNonAnnotationType", et.getExactType().getName()), this.getSourceLocation());
               scope.getWorld().getMessageHandler().handleMessage(m);
               this.resolved = false;
            }

            ResolvedType annotationType = et.getExactType().resolve(scope.getWorld());
            this.resolveAnnotationValues(annotationType, scope);
            ExactAnnotationTypePattern eatp = new ExactAnnotationTypePattern(annotationType, this.annotationValues);
            eatp.copyLocationFrom(this);
            if (this.isForParameterAnnotationMatch()) {
               eatp.setForParameterAnnotationMatch();
            }

            return eatp;
         } else {
            return this;
         }
      }
   }

   public AnnotationTypePattern parameterizeWith(Map typeVariableMap, World w) {
      WildAnnotationTypePattern ret = new WildAnnotationTypePattern(this.typePattern.parameterizeWith(typeVariableMap, w));
      ret.copyLocationFrom(this);
      ret.resolved = this.resolved;
      return ret;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(8);
      s.writeByte(1);
      this.typePattern.write(s);
      this.writeLocation(s);
      s.writeBoolean(this.isForParameterAnnotationMatch());
      if (this.annotationValues == null) {
         s.writeInt(0);
      } else {
         s.writeInt(this.annotationValues.size());
         Set key = this.annotationValues.keySet();
         Iterator keys = key.iterator();

         while(keys.hasNext()) {
            String k = (String)keys.next();
            s.writeUTF(k);
            s.writeUTF((String)this.annotationValues.get(k));
         }
      }

   }

   public static AnnotationTypePattern read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      byte version = s.readByte();
      if (version > 1) {
         throw new BCException("ExactAnnotationTypePattern was written by a newer version of AspectJ");
      } else {
         TypePattern t = TypePattern.read(s, context);
         WildAnnotationTypePattern ret = new WildAnnotationTypePattern(t);
         ret.readLocation(context, s);
         if (s.getMajorVersion() >= 4 && s.readBoolean()) {
            ret.setForParameterAnnotationMatch();
         }

         if (s.getMajorVersion() >= 5) {
            int annotationValueCount = s.readInt();
            if (annotationValueCount > 0) {
               Map aValues = new HashMap();

               for(int i = 0; i < annotationValueCount; ++i) {
                  String key = s.readUTF();
                  String val = s.readUTF();
                  aValues.put(key, val);
               }

               ret.annotationValues = aValues;
            }
         }

         return ret;
      }
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof WildAnnotationTypePattern)) {
         return false;
      } else {
         boolean var10000;
         label33: {
            WildAnnotationTypePattern other = (WildAnnotationTypePattern)obj;
            if (other.typePattern.equals(this.typePattern) && this.isForParameterAnnotationMatch() == other.isForParameterAnnotationMatch()) {
               if (this.annotationValues == null) {
                  if (other.annotationValues == null) {
                     break label33;
                  }
               } else if (this.annotationValues.equals(other.annotationValues)) {
                  break label33;
               }
            }

            var10000 = false;
            return var10000;
         }

         var10000 = true;
         return var10000;
      }
   }

   public int hashCode() {
      return ((17 + 37 * this.typePattern.hashCode()) * 37 + (this.isForParameterAnnotationMatch() ? 0 : 1)) * 37 + (this.annotationValues == null ? 0 : this.annotationValues.hashCode());
   }

   public String toString() {
      return "@(" + this.typePattern.toString() + ")";
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }
}
