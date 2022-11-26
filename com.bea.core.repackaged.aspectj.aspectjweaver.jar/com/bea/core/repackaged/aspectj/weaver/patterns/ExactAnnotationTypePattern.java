package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.AnnotatedElement;
import com.bea.core.repackaged.aspectj.weaver.AnnotationAJ;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.ReferenceType;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.TypeVariableReference;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ExactAnnotationTypePattern extends AnnotationTypePattern {
   protected UnresolvedType annotationType;
   protected String formalName;
   protected boolean resolved = false;
   protected boolean bindingPattern = false;
   private Map annotationValues;
   private static byte VERSION = 1;

   public ExactAnnotationTypePattern(UnresolvedType annotationType, Map annotationValues) {
      this.annotationType = annotationType;
      this.annotationValues = annotationValues;
      this.resolved = annotationType instanceof ResolvedType;
   }

   private ExactAnnotationTypePattern(UnresolvedType annotationType) {
      this.annotationType = annotationType;
      this.resolved = annotationType instanceof ResolvedType;
   }

   protected ExactAnnotationTypePattern(String formalName) {
      this.formalName = formalName;
      this.resolved = false;
      this.bindingPattern = true;
   }

   public ResolvedType getResolvedAnnotationType() {
      if (!this.resolved) {
         throw new IllegalStateException("I need to be resolved first!");
      } else {
         return (ResolvedType)this.annotationType;
      }
   }

   public UnresolvedType getAnnotationType() {
      return this.annotationType;
   }

   public Map getAnnotationValues() {
      return this.annotationValues;
   }

   public FuzzyBoolean fastMatches(AnnotatedElement annotated) {
      return annotated.hasAnnotation(this.annotationType) && this.annotationValues == null ? FuzzyBoolean.YES : FuzzyBoolean.MAYBE;
   }

   public FuzzyBoolean matches(AnnotatedElement annotated) {
      return this.matches(annotated, (ResolvedType[])null);
   }

   public FuzzyBoolean matches(AnnotatedElement annotated, ResolvedType[] parameterAnnotations) {
      if (!this.isForParameterAnnotationMatch()) {
         boolean checkSupers = false;
         if (this.getResolvedAnnotationType().isInheritedAnnotation() && annotated instanceof ResolvedType) {
            checkSupers = true;
         }

         String v;
         ResolvedMember[] ms;
         boolean foundMatch;
         int i;
         String s;
         if (annotated.hasAnnotation(this.annotationType)) {
            if (this.annotationType instanceof ReferenceType) {
               ReferenceType rt = (ReferenceType)this.annotationType;
               if (rt.getRetentionPolicy() != null && rt.getRetentionPolicy().equals("SOURCE")) {
                  rt.getWorld().getMessageHandler().handleMessage(MessageUtil.warn(WeaverMessages.format("noMatchBecauseSourceRetention", this.annotationType, annotated), this.getSourceLocation()));
                  return FuzzyBoolean.NO;
               }
            }

            if (this.annotationValues != null) {
               AnnotationAJ theAnnotation = annotated.getAnnotationOfType(this.annotationType);
               Set keys = this.annotationValues.keySet();
               Iterator i$ = keys.iterator();

               while(true) {
                  while(i$.hasNext()) {
                     String k = (String)i$.next();
                     boolean notEqual = false;
                     v = (String)this.annotationValues.get(k);
                     if (k.endsWith("!")) {
                        notEqual = true;
                        k = k.substring(0, k.length() - 1);
                     }

                     if (theAnnotation.hasNamedValue(k)) {
                        if (notEqual) {
                           if (theAnnotation.hasNameValuePair(k, v)) {
                              return FuzzyBoolean.NO;
                           }
                        } else if (!theAnnotation.hasNameValuePair(k, v)) {
                           return FuzzyBoolean.NO;
                        }
                     } else {
                        ms = ((ResolvedType)this.annotationType).getDeclaredMethods();
                        foundMatch = false;

                        for(i = 0; i < ms.length && !foundMatch; ++i) {
                           if (ms[i].isAbstract() && ms[i].getParameterTypes().length == 0 && ms[i].getName().equals(k)) {
                              s = ms[i].getAnnotationDefaultValue();
                              if (s != null && s.equals(v)) {
                                 foundMatch = true;
                              }
                           }
                        }

                        if (notEqual) {
                           if (foundMatch) {
                              return FuzzyBoolean.NO;
                           }
                        } else if (!foundMatch) {
                           return FuzzyBoolean.NO;
                        }
                     }
                  }

                  return FuzzyBoolean.YES;
               }
            }

            return FuzzyBoolean.YES;
         }

         if (checkSupers) {
            for(ResolvedType toMatchAgainst = ((ResolvedType)annotated).getSuperclass(); toMatchAgainst != null; toMatchAgainst = toMatchAgainst.getSuperclass()) {
               if (toMatchAgainst.hasAnnotation(this.annotationType)) {
                  if (this.annotationValues != null) {
                     AnnotationAJ theAnnotation = toMatchAgainst.getAnnotationOfType(this.annotationType);
                     Set keys = this.annotationValues.keySet();
                     Iterator i$ = keys.iterator();

                     while(true) {
                        while(i$.hasNext()) {
                           String k = (String)i$.next();
                           v = (String)this.annotationValues.get(k);
                           if (theAnnotation.hasNamedValue(k)) {
                              if (!theAnnotation.hasNameValuePair(k, v)) {
                                 return FuzzyBoolean.NO;
                              }
                           } else {
                              ms = ((ResolvedType)this.annotationType).getDeclaredMethods();
                              foundMatch = false;

                              for(i = 0; i < ms.length && !foundMatch; ++i) {
                                 if (ms[i].isAbstract() && ms[i].getParameterTypes().length == 0 && ms[i].getName().equals(k)) {
                                    s = ms[i].getAnnotationDefaultValue();
                                    if (s != null && s.equals(v)) {
                                       foundMatch = true;
                                    }
                                 }
                              }

                              if (!foundMatch) {
                                 return FuzzyBoolean.NO;
                              }
                           }
                        }

                        return FuzzyBoolean.YES;
                     }
                  }

                  return FuzzyBoolean.YES;
               }
            }
         }
      } else {
         if (parameterAnnotations == null) {
            return FuzzyBoolean.NO;
         }

         for(int i = 0; i < parameterAnnotations.length; ++i) {
            if (this.annotationType.equals(parameterAnnotations[i])) {
               if (this.annotationValues != null) {
                  parameterAnnotations[i].getWorld().getMessageHandler().handleMessage(MessageUtil.error("Compiler limitation: annotation value matching for parameter annotations not yet supported"));
                  return FuzzyBoolean.NO;
               }

               return FuzzyBoolean.YES;
            }
         }
      }

      return FuzzyBoolean.NO;
   }

   public FuzzyBoolean matchesRuntimeType(AnnotatedElement annotated) {
      return this.getResolvedAnnotationType().isInheritedAnnotation() && this.matches(annotated).alwaysTrue() ? FuzzyBoolean.YES : FuzzyBoolean.MAYBE;
   }

   public void resolve(World world) {
      if (!this.resolved) {
         this.annotationType = this.annotationType.resolve(world);
         this.resolved = true;
      }

   }

   public AnnotationTypePattern resolveBindings(IScope scope, Bindings bindings, boolean allowBinding) {
      if (this.resolved) {
         return this;
      } else {
         this.resolved = true;
         String simpleName = this.maybeGetSimpleName();
         if (simpleName != null) {
            FormalBinding formalBinding = scope.lookupFormal(simpleName);
            if (formalBinding != null) {
               if (bindings == null) {
                  scope.message(IMessage.ERROR, this, "negation doesn't allow binding");
                  return this;
               }

               if (!allowBinding) {
                  scope.message(IMessage.ERROR, this, "name binding only allowed in @pcds, args, this, and target");
                  return this;
               }

               this.formalName = simpleName;
               this.bindingPattern = true;
               this.verifyIsAnnotationType(formalBinding.getType().resolve(scope.getWorld()), scope);
               BindingAnnotationTypePattern binding = new BindingAnnotationTypePattern(formalBinding);
               binding.copyLocationFrom(this);
               bindings.register(binding, scope);
               binding.resolveBinding(scope.getWorld());
               if (this.isForParameterAnnotationMatch()) {
                  binding.setForParameterAnnotationMatch();
               }

               return binding;
            }
         }

         String cleanname = this.annotationType.getName();
         this.annotationType = scope.getWorld().resolve(this.annotationType, true);
         if (ResolvedType.isMissing(this.annotationType)) {
            UnresolvedType type;
            int lastDot;
            for(type = null; ResolvedType.isMissing(type = scope.lookupType(cleanname, this)); cleanname = cleanname.substring(0, lastDot) + "$" + cleanname.substring(lastDot + 1)) {
               lastDot = cleanname.lastIndexOf(46);
               if (lastDot == -1) {
                  break;
               }
            }

            this.annotationType = scope.getWorld().resolve(type, true);
         }

         this.verifyIsAnnotationType((ResolvedType)this.annotationType, scope);
         return this;
      }
   }

   public AnnotationTypePattern parameterizeWith(Map typeVariableMap, World w) {
      UnresolvedType newAnnotationType = this.annotationType;
      if (this.annotationType.isTypeVariableReference()) {
         TypeVariableReference t = (TypeVariableReference)this.annotationType;
         String key = t.getTypeVariable().getName();
         if (typeVariableMap.containsKey(key)) {
            newAnnotationType = (UnresolvedType)typeVariableMap.get(key);
         }
      } else if (this.annotationType.isParameterizedType()) {
         newAnnotationType = this.annotationType.parameterize(typeVariableMap);
      }

      ExactAnnotationTypePattern ret = new ExactAnnotationTypePattern(newAnnotationType, this.annotationValues);
      ret.formalName = this.formalName;
      ret.bindingPattern = this.bindingPattern;
      ret.copyLocationFrom(this);
      if (this.isForParameterAnnotationMatch()) {
         ret.setForParameterAnnotationMatch();
      }

      return ret;
   }

   protected String maybeGetSimpleName() {
      if (this.formalName != null) {
         return this.formalName;
      } else {
         String ret = this.annotationType.getName();
         return ret.indexOf(46) == -1 ? ret : null;
      }
   }

   protected void verifyIsAnnotationType(ResolvedType type, IScope scope) {
      if (!type.isAnnotation()) {
         IMessage m = MessageUtil.error(WeaverMessages.format("referenceToNonAnnotationType", type.getName()), this.getSourceLocation());
         scope.getWorld().getMessageHandler().handleMessage(m);
         this.resolved = false;
      }

   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(1);
      s.writeByte(VERSION);
      s.writeBoolean(this.bindingPattern);
      if (this.bindingPattern) {
         s.writeUTF(this.formalName);
      } else {
         this.annotationType.write(s);
      }

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
      if (version > VERSION) {
         throw new BCException("ExactAnnotationTypePattern was written by a newer version of AspectJ");
      } else {
         boolean isBindingPattern = s.readBoolean();
         ExactAnnotationTypePattern ret;
         if (isBindingPattern) {
            ret = new ExactAnnotationTypePattern(s.readUTF());
         } else {
            ret = new ExactAnnotationTypePattern(UnresolvedType.read(s));
         }

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
      if (!(obj instanceof ExactAnnotationTypePattern)) {
         return false;
      } else {
         boolean var10000;
         label33: {
            ExactAnnotationTypePattern other = (ExactAnnotationTypePattern)obj;
            if (other.annotationType.equals(this.annotationType) && this.isForParameterAnnotationMatch() == other.isForParameterAnnotationMatch()) {
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
      return (this.annotationType.hashCode() * 37 + (this.isForParameterAnnotationMatch() ? 0 : 1)) * 37 + (this.annotationValues == null ? 0 : this.annotationValues.hashCode());
   }

   public String toString() {
      if (!this.resolved && this.formalName != null) {
         return this.formalName;
      } else {
         String ret = "@" + this.annotationType.toString();
         if (this.formalName != null) {
            ret = ret + " " + this.formalName;
         }

         return ret;
      }
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }
}
