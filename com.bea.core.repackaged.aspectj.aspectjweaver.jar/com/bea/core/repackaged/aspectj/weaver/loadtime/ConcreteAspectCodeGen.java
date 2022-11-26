package com.bea.core.repackaged.aspectj.weaver.loadtime;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.JavaClass;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.AnnotationGen;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.ClassElementValue;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.NameValuePair;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.SimpleElementValue;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.FieldGen;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Instruction;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionConstants;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionFactory;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionHandle;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionList;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.LocalVariableTag;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.ObjectType;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Type;
import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.Message;
import com.bea.core.repackaged.aspectj.weaver.AjAttribute;
import com.bea.core.repackaged.aspectj.weaver.AnnotationAJ;
import com.bea.core.repackaged.aspectj.weaver.GeneratedReferenceTypeDelegate;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.ReferenceType;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.bcel.BcelAnnotation;
import com.bea.core.repackaged.aspectj.weaver.bcel.BcelPerClauseAspectAdder;
import com.bea.core.repackaged.aspectj.weaver.bcel.BcelWorld;
import com.bea.core.repackaged.aspectj.weaver.bcel.LazyClassGen;
import com.bea.core.repackaged.aspectj.weaver.bcel.LazyMethodGen;
import com.bea.core.repackaged.aspectj.weaver.loadtime.definition.Definition;
import com.bea.core.repackaged.aspectj.weaver.patterns.BasicTokenSource;
import com.bea.core.repackaged.aspectj.weaver.patterns.DeclareAnnotation;
import com.bea.core.repackaged.aspectj.weaver.patterns.ISignaturePattern;
import com.bea.core.repackaged.aspectj.weaver.patterns.ITokenSource;
import com.bea.core.repackaged.aspectj.weaver.patterns.PatternParser;
import com.bea.core.repackaged.aspectj.weaver.patterns.PerClause;
import com.bea.core.repackaged.aspectj.weaver.patterns.TypePattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ConcreteAspectCodeGen {
   private static final String[] EMPTY_STRINGS = new String[0];
   private static final Type[] EMPTY_TYPES = new Type[0];
   private final Definition.ConcreteAspect concreteAspect;
   private final World world;
   private boolean isValid = false;
   private ResolvedType parent;
   private PerClause perclause;
   private byte[] bytes;

   ConcreteAspectCodeGen(Definition.ConcreteAspect concreteAspect, World world) {
      this.concreteAspect = concreteAspect;
      this.world = world;
   }

   public boolean validate() {
      if (!(this.world instanceof BcelWorld)) {
         this.reportError("Internal error: world must be of type BcelWorld");
         return false;
      } else {
         ResolvedType current = this.world.lookupBySignature(UnresolvedType.forName(this.concreteAspect.name).getSignature());
         if (current != null && !current.isMissing()) {
            this.reportError("Attempt to concretize but chosen aspect name already defined: " + this.stringify());
            return false;
         } else if (this.concreteAspect.pointcutsAndAdvice.size() != 0) {
            this.isValid = true;
            return true;
         } else if (this.concreteAspect.declareAnnotations.size() != 0) {
            this.isValid = true;
            return true;
         } else if (this.concreteAspect.extend == null && this.concreteAspect.precedence != null) {
            if (this.concreteAspect.pointcuts.isEmpty()) {
               this.isValid = true;
               this.parent = null;
               return true;
            } else {
               this.reportError("Attempt to use nested pointcuts without extends clause: " + this.stringify());
               return false;
            }
         } else {
            String parentAspectName = this.concreteAspect.extend;
            int i;
            if (parentAspectName.indexOf("<") != -1) {
               this.parent = this.world.resolve(UnresolvedType.forName(parentAspectName), true);
               if (this.parent.isMissing()) {
                  this.reportError("Unable to resolve type reference: " + this.stringify());
                  return false;
               }

               if (this.parent.isParameterizedType()) {
                  UnresolvedType[] typeParameters = this.parent.getTypeParameters();

                  for(i = 0; i < typeParameters.length; ++i) {
                     UnresolvedType typeParameter = typeParameters[i];
                     if (typeParameter instanceof ResolvedType && ((ResolvedType)typeParameter).isMissing()) {
                        this.reportError("Unablet to resolve type parameter '" + typeParameter.getName() + "' from " + this.stringify());
                        return false;
                     }
                  }
               }
            } else {
               this.parent = this.world.resolve(this.concreteAspect.extend, true);
            }

            if (this.parent.isMissing()) {
               String fixedName = this.concreteAspect.extend;
               i = fixedName.lastIndexOf(46);

               while(i > 0) {
                  char[] fixedNameChars = fixedName.toCharArray();
                  fixedNameChars[i] = '$';
                  fixedName = new String(fixedNameChars);
                  i = fixedName.lastIndexOf(46);
                  this.parent = this.world.resolve(UnresolvedType.forName(fixedName), true);
                  if (!this.parent.isMissing()) {
                     break;
                  }
               }
            }

            if (this.parent.isMissing()) {
               this.reportError("Cannot find parent aspect for: " + this.stringify());
               return false;
            } else if (!this.parent.isAbstract() && !this.parent.equals(ResolvedType.OBJECT)) {
               this.reportError("Attempt to concretize a non-abstract aspect: " + this.stringify());
               return false;
            } else if (!this.parent.isAspect() && !this.parent.equals(ResolvedType.OBJECT)) {
               this.reportError("Attempt to concretize a non aspect: " + this.stringify());
               return false;
            } else {
               List elligibleAbstractions = new ArrayList();
               Collection abstractMethods = this.getOutstandingAbstractMethods(this.parent);
               Iterator i$ = abstractMethods.iterator();

               String elligiblePc;
               while(i$.hasNext()) {
                  ResolvedMember method = (ResolvedMember)i$.next();
                  if (!"()V".equals(method.getSignature())) {
                     if (!method.getName().startsWith("ajc$pointcut") && !this.hasPointcutAnnotation(method)) {
                        this.reportError("Abstract method '" + method.toString() + "' cannot be concretized in XML: " + this.stringify());
                        return false;
                     }

                     this.reportError("Abstract method '" + method.toString() + "' cannot be concretized as a pointcut (illegal signature, must have no arguments, must return void): " + this.stringify());
                     return false;
                  }

                  elligiblePc = method.getName();
                  if (elligiblePc.startsWith("ajc$pointcut")) {
                     elligiblePc = elligiblePc.substring(14);
                     elligiblePc = elligiblePc.substring(0, elligiblePc.indexOf("$"));
                     elligibleAbstractions.add(elligiblePc);
                  } else {
                     if (!this.hasPointcutAnnotation(method)) {
                        this.reportError("Abstract method '" + method.toString() + "' cannot be concretized in XML: " + this.stringify());
                        return false;
                     }

                     elligibleAbstractions.add(method.getName());
                  }
               }

               List pointcutNames = new ArrayList();
               Iterator i$ = this.concreteAspect.pointcuts.iterator();

               while(i$.hasNext()) {
                  Definition.Pointcut abstractPc = (Definition.Pointcut)i$.next();
                  pointcutNames.add(abstractPc.name);
               }

               i$ = elligibleAbstractions.iterator();

               do {
                  if (!i$.hasNext()) {
                     if (this.concreteAspect.perclause != null) {
                        String perclauseString = this.concreteAspect.perclause;
                        if (!perclauseString.startsWith("persingleton") && !perclauseString.startsWith("percflow") && !perclauseString.startsWith("pertypewithin") && !perclauseString.startsWith("perthis") && !perclauseString.startsWith("pertarget") && !perclauseString.startsWith("percflowbelow")) {
                           this.reportError("Unrecognized per clause specified " + this.stringify());
                           return false;
                        }
                     }

                     this.isValid = true;
                     return this.isValid;
                  }

                  elligiblePc = (String)i$.next();
               } while(pointcutNames.contains(elligiblePc));

               this.reportError("Abstract pointcut '" + elligiblePc + "' not configured: " + this.stringify());
               return false;
            }
         }
      }
   }

   private Collection getOutstandingAbstractMethods(ResolvedType type) {
      Map collector = new HashMap();
      this.getOutstandingAbstractMethodsHelper(type, collector);
      return collector.values();
   }

   private void getOutstandingAbstractMethodsHelper(ResolvedType type, Map collector) {
      if (type != null) {
         if (!type.equals(ResolvedType.OBJECT) && type.getSuperclass() != null) {
            this.getOutstandingAbstractMethodsHelper(type.getSuperclass(), collector);
         }

         ResolvedMember[] rms = type.getDeclaredMethods();
         if (rms != null) {
            for(int i = 0; i < rms.length; ++i) {
               ResolvedMember member = rms[i];
               String key = member.getName() + member.getSignature();
               if (member.isAbstract()) {
                  collector.put(key, member);
               } else {
                  collector.remove(key);
               }
            }
         }

      }
   }

   private String stringify() {
      StringBuffer sb = new StringBuffer("<concrete-aspect name='");
      sb.append(this.concreteAspect.name);
      sb.append("' extends='");
      sb.append(this.concreteAspect.extend);
      sb.append("' perclause='");
      sb.append(this.concreteAspect.perclause);
      sb.append("'/> in aop.xml");
      return sb.toString();
   }

   private boolean hasPointcutAnnotation(ResolvedMember member) {
      AnnotationAJ[] as = member.getAnnotations();
      if (as != null && as.length != 0) {
         for(int i = 0; i < as.length; ++i) {
            if (as[i].getTypeSignature().equals("Lorg/aspectj/lang/annotation/Pointcut;")) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public String getClassName() {
      return this.concreteAspect.name;
   }

   public byte[] getBytes() {
      if (!this.isValid) {
         throw new RuntimeException("Must validate first");
      } else if (this.bytes != null) {
         return this.bytes;
      } else {
         PerClause.Kind perclauseKind = PerClause.SINGLETON;
         PerClause parentPerClause = this.parent != null ? this.parent.getPerClause() : null;
         if (parentPerClause != null) {
            perclauseKind = parentPerClause.getKind();
         }

         String perclauseString = null;
         if (this.concreteAspect.perclause != null) {
            perclauseString = this.concreteAspect.perclause;
            if (perclauseString.startsWith("persingleton")) {
               perclauseKind = PerClause.SINGLETON;
            } else if (perclauseString.startsWith("percflow")) {
               perclauseKind = PerClause.PERCFLOW;
            } else if (perclauseString.startsWith("pertypewithin")) {
               perclauseKind = PerClause.PERTYPEWITHIN;
            } else if (perclauseString.startsWith("perthis")) {
               perclauseKind = PerClause.PEROBJECT;
            } else if (perclauseString.startsWith("pertarget")) {
               perclauseKind = PerClause.PEROBJECT;
            } else if (perclauseString.startsWith("percflowbelow")) {
               perclauseKind = PerClause.PERCFLOW;
            }
         }

         String parentName = "java/lang/Object";
         if (this.parent != null) {
            if (this.parent.isParameterizedType()) {
               parentName = this.parent.getGenericType().getName().replace('.', '/');
            } else {
               parentName = this.parent.getName().replace('.', '/');
            }
         }

         LazyClassGen cg = new LazyClassGen(this.concreteAspect.name.replace('.', '/'), parentName, (String)null, 33, EMPTY_STRINGS, this.world);
         if (this.parent != null && this.parent.isParameterizedType()) {
            cg.setSuperClass(this.parent);
         }

         if (perclauseString == null) {
            AnnotationGen ag = new AnnotationGen(new ObjectType("com/bea/core/repackaged/aspectj/lang/annotation/Aspect"), Collections.emptyList(), true, cg.getConstantPool());
            cg.addAnnotation(ag);
         } else {
            List elems = new ArrayList();
            elems.add(new NameValuePair("value", new SimpleElementValue(115, cg.getConstantPool(), perclauseString), cg.getConstantPool()));
            AnnotationGen ag = new AnnotationGen(new ObjectType("com/bea/core/repackaged/aspectj/lang/annotation/Aspect"), elems, true, cg.getConstantPool());
            cg.addAnnotation(ag);
         }

         if (this.concreteAspect.precedence != null) {
            SimpleElementValue svg = new SimpleElementValue(115, cg.getConstantPool(), this.concreteAspect.precedence);
            List elems = new ArrayList();
            elems.add(new NameValuePair("value", svg, cg.getConstantPool()));
            AnnotationGen agprec = new AnnotationGen(new ObjectType("com/bea/core/repackaged/aspectj/lang/annotation/DeclarePrecedence"), elems, true, cg.getConstantPool());
            cg.addAnnotation(agprec);
         }

         LazyMethodGen init = new LazyMethodGen(1, Type.VOID, "<init>", EMPTY_TYPES, EMPTY_STRINGS, cg);
         InstructionList cbody = init.getBody();
         cbody.append((Instruction)InstructionConstants.ALOAD_0);
         cbody.append((Instruction)cg.getFactory().createInvoke(parentName, "<init>", Type.VOID, EMPTY_TYPES, (short)183));
         cbody.append(InstructionConstants.RETURN);
         cg.addMethodGen(init);
         Iterator it = this.concreteAspect.pointcuts.iterator();

         while(it.hasNext()) {
            Definition.Pointcut abstractPc = (Definition.Pointcut)it.next();
            LazyMethodGen mg = new LazyMethodGen(1, Type.VOID, abstractPc.name, EMPTY_TYPES, EMPTY_STRINGS, cg);
            SimpleElementValue svg = new SimpleElementValue(115, cg.getConstantPool(), abstractPc.expression);
            List elems = new ArrayList();
            elems.add(new NameValuePair("value", svg, cg.getConstantPool()));
            AnnotationGen mag = new AnnotationGen(new ObjectType("com/bea/core/repackaged/aspectj/lang/annotation/Pointcut"), elems, true, cg.getConstantPool());
            AnnotationAJ max = new BcelAnnotation(mag, this.world);
            mg.addAnnotation(max);
            InstructionList body = mg.getBody();
            body.append(InstructionConstants.RETURN);
            cg.addMethodGen(mg);
         }

         int adviceCounter;
         Iterator i$;
         if (this.concreteAspect.deows.size() > 0) {
            adviceCounter = 1;
            i$ = this.concreteAspect.deows.iterator();

            while(i$.hasNext()) {
               Definition.DeclareErrorOrWarning deow = (Definition.DeclareErrorOrWarning)i$.next();
               FieldGen field = new FieldGen(16, ObjectType.STRING, "rule" + adviceCounter++, cg.getConstantPool());
               SimpleElementValue svg = new SimpleElementValue(115, cg.getConstantPool(), deow.pointcut);
               List elems = new ArrayList();
               elems.add(new NameValuePair("value", svg, cg.getConstantPool()));
               AnnotationGen mag = new AnnotationGen(new ObjectType("com/bea/core/repackaged/aspectj/lang/annotation/Declare" + (deow.isError ? "Error" : "Warning")), elems, true, cg.getConstantPool());
               field.addAnnotation(mag);
               field.setValue(deow.message);
               cg.addField(field, (ISourceLocation)null);
            }
         }

         if (this.concreteAspect.pointcutsAndAdvice.size() > 0) {
            adviceCounter = 1;

            for(i$ = this.concreteAspect.pointcutsAndAdvice.iterator(); i$.hasNext(); ++adviceCounter) {
               Definition.PointcutAndAdvice paa = (Definition.PointcutAndAdvice)i$.next();
               this.generateAdviceMethod(paa, adviceCounter, cg);
            }
         }

         if (this.concreteAspect.declareAnnotations.size() > 0) {
            adviceCounter = 1;
            i$ = this.concreteAspect.declareAnnotations.iterator();

            while(i$.hasNext()) {
               Definition.DeclareAnnotation da = (Definition.DeclareAnnotation)i$.next();
               this.generateDeclareAnnotation(da, adviceCounter++, cg);
            }
         }

         ReferenceType rt = new ReferenceType(ResolvedType.forName(this.concreteAspect.name).getSignature(), this.world);
         GeneratedReferenceTypeDelegate grtd = new GeneratedReferenceTypeDelegate(rt);
         grtd.setSuperclass(this.parent);
         rt.setDelegate(grtd);
         BcelPerClauseAspectAdder perClauseMunger = new BcelPerClauseAspectAdder(rt, perclauseKind);
         perClauseMunger.forceMunge(cg, false);
         JavaClass jc = cg.getJavaClass((BcelWorld)this.world);
         ((BcelWorld)this.world).addSourceObjectType(jc, true);
         this.bytes = jc.getBytes();
         return this.bytes;
      }
   }

   private void generateDeclareAnnotation(Definition.DeclareAnnotation da, int decCounter, LazyClassGen cg) {
      AnnotationAJ constructedAnnotation = this.buildDeclareAnnotation_actualAnnotation(cg, da);
      if (constructedAnnotation != null) {
         String nameComponent = da.declareAnnotationKind.name().toLowerCase();
         String declareName = "ajc$declare_at_" + nameComponent + "_" + decCounter;
         LazyMethodGen declareMethod = new LazyMethodGen(1, Type.VOID, declareName, Type.NO_ARGS, EMPTY_STRINGS, cg);
         InstructionList declareMethodBody = declareMethod.getBody();
         declareMethodBody.append(InstructionFactory.RETURN);
         declareMethod.addAnnotation(constructedAnnotation);
         DeclareAnnotation deca = null;
         ITokenSource tokenSource = BasicTokenSource.makeTokenSource(da.pattern, (ISourceContext)null);
         PatternParser pp = new PatternParser(tokenSource);
         if (da.declareAnnotationKind != Definition.DeclareAnnotationKind.Method && da.declareAnnotationKind != Definition.DeclareAnnotationKind.Field) {
            if (da.declareAnnotationKind == Definition.DeclareAnnotationKind.Type) {
               TypePattern tp = pp.parseTypePattern();
               deca = new DeclareAnnotation(DeclareAnnotation.AT_TYPE, tp);
            }
         } else {
            ISignaturePattern isp = da.declareAnnotationKind == Definition.DeclareAnnotationKind.Method ? pp.parseCompoundMethodOrConstructorSignaturePattern(true) : pp.parseCompoundFieldSignaturePattern();
            deca = new DeclareAnnotation(da.declareAnnotationKind == Definition.DeclareAnnotationKind.Method ? DeclareAnnotation.AT_METHOD : DeclareAnnotation.AT_FIELD, isp);
         }

         deca.setAnnotationMethod(declareName);
         deca.setAnnotationString(da.annotation);
         AjAttribute attribute = new AjAttribute.DeclareAttribute(deca);
         cg.addAttribute((AjAttribute)attribute);
         cg.addMethodGen(declareMethod);
      }
   }

   private AnnotationAJ buildDeclareAnnotation_actualAnnotation(LazyClassGen cg, Definition.DeclareAnnotation da) {
      AnnotationGen anno = this.buildAnnotationFromString(cg.getConstantPool(), cg.getWorld(), da.annotation);
      if (anno == null) {
         return null;
      } else {
         AnnotationAJ bcelAnnotation = new BcelAnnotation(anno, this.world);
         return bcelAnnotation;
      }
   }

   private AnnotationGen buildAnnotationFromString(ConstantPool cp, World w, String annotationString) {
      int paren = annotationString.indexOf(40);
      if (paren == -1) {
         AnnotationGen aaj = this.buildBaseAnnotationType(cp, this.world, annotationString);
         return aaj;
      } else {
         String name = annotationString.substring(0, paren);
         List values = new ArrayList();
         int pos = paren + 1;
         int depth = 0;
         int len = annotationString.length();

         int start;
         for(start = pos; pos < len; ++pos) {
            char ch = annotationString.charAt(pos);
            if (ch == ')' && depth == 0) {
               break;
            }

            if (ch != '(' && ch != '[') {
               if (ch == ')' || ch == ']') {
                  --depth;
               }
            } else {
               ++depth;
            }

            if (ch == ',' && depth == 0) {
               values.add(annotationString.substring(start, pos).trim());
               start = pos + 1;
            }
         }

         if (start != pos) {
            values.add(annotationString.substring(start, pos).trim());
         }

         AnnotationGen aaj = this.buildBaseAnnotationType(cp, this.world, name);
         if (aaj == null) {
            return null;
         } else {
            String typename = aaj.getTypeName();
            ResolvedType type = UnresolvedType.forName(typename).resolve(this.world);
            ResolvedMember[] rms = type.getDeclaredMethods();
            Iterator i$ = values.iterator();

            String key;
            boolean keyIsOk;
            do {
               if (!i$.hasNext()) {
                  return aaj;
               }

               String value = (String)i$.next();
               int equalsIndex = value.indexOf("=");
               key = "value";
               if (value.charAt(0) != '"' && equalsIndex != -1) {
                  key = value.substring(0, equalsIndex).trim();
                  value = value.substring(equalsIndex + 1).trim();
               }

               keyIsOk = false;

               for(int m = 0; m < rms.length; ++m) {
                  NameValuePair nvp = null;
                  if (rms[m].getName().equals(key)) {
                     keyIsOk = true;
                     UnresolvedType rt = rms[m].getReturnType();
                     if (rt.isPrimitiveType()) {
                        int shortValue;
                        switch (rt.getSignature().charAt(0)) {
                           case 'B':
                              try {
                                 byte byteValue = Byte.parseByte(value);
                                 nvp = new NameValuePair(key, new SimpleElementValue(66, cp, byteValue), cp);
                                 break;
                              } catch (NumberFormatException var28) {
                                 this.reportError("unable to interpret annotation value '" + value + "' as a byte");
                                 return null;
                              }
                           case 'C':
                              if (value.length() < 2) {
                                 this.reportError("unable to interpret annotation value '" + value + "' as a char");
                                 return null;
                              }

                              nvp = new NameValuePair(key, new SimpleElementValue(67, cp, value.charAt(1)), cp);
                              break;
                           case 'D':
                              try {
                                 double doubleValue = Double.parseDouble(value);
                                 nvp = new NameValuePair(key, new SimpleElementValue(68, cp, doubleValue), cp);
                                 break;
                              } catch (NumberFormatException var30) {
                                 this.reportError("unable to interpret annotation value '" + value + "' as a double");
                                 return null;
                              }
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
                           case 'V':
                           case 'W':
                           case 'X':
                           case 'Y':
                           default:
                              this.reportError("not yet supporting XML setting of annotation values of type " + rt.getName());
                              return null;
                           case 'F':
                              try {
                                 float floatValue = Float.parseFloat(value);
                                 nvp = new NameValuePair(key, new SimpleElementValue(70, cp, floatValue), cp);
                                 break;
                              } catch (NumberFormatException var31) {
                                 this.reportError("unable to interpret annotation value '" + value + "' as a float");
                                 return null;
                              }
                           case 'I':
                              try {
                                 shortValue = Integer.parseInt(value);
                                 nvp = new NameValuePair(key, new SimpleElementValue(73, cp, shortValue), cp);
                                 break;
                              } catch (NumberFormatException var29) {
                                 this.reportError("unable to interpret annotation value '" + value + "' as an integer");
                                 return null;
                              }
                           case 'J':
                              try {
                                 long longValue = Long.parseLong(value);
                                 nvp = new NameValuePair(key, new SimpleElementValue(74, cp, longValue), cp);
                                 break;
                              } catch (NumberFormatException var33) {
                                 this.reportError("unable to interpret annotation value '" + value + "' as a long");
                                 return null;
                              }
                           case 'S':
                              try {
                                 shortValue = Short.parseShort(value);
                                 nvp = new NameValuePair(key, new SimpleElementValue(83, cp, (short)shortValue), cp);
                                 break;
                              } catch (NumberFormatException var32) {
                                 this.reportError("unable to interpret annotation value '" + value + "' as a short");
                                 return null;
                              }
                           case 'Z':
                              try {
                                 boolean booleanValue = Boolean.parseBoolean(value);
                                 nvp = new NameValuePair(key, new SimpleElementValue(90, cp, booleanValue), cp);
                              } catch (NumberFormatException var27) {
                                 this.reportError("unable to interpret annotation value '" + value + "' as a boolean");
                                 return null;
                              }
                        }
                     } else if (UnresolvedType.JL_STRING.equals(rt)) {
                        if (value.length() < 2) {
                           this.reportError("Invalid string value specified in annotation string: " + annotationString);
                           return null;
                        }

                        value = value.substring(1, value.length() - 1);
                        nvp = new NameValuePair(key, new SimpleElementValue(115, cp, value), cp);
                     } else if (UnresolvedType.JL_CLASS.equals(rt)) {
                        if (value.length() < 6) {
                           this.reportError("Not a well formed class value for an annotation '" + value + "'");
                           return null;
                        }

                        String clazz = value.substring(0, value.length() - 6);
                        boolean qualified = clazz.indexOf(".") != -1;
                        if (!qualified) {
                           clazz = "java.lang." + clazz;
                        }

                        nvp = new NameValuePair(key, new ClassElementValue(new ObjectType(clazz), cp), cp);
                     }
                  }

                  if (nvp != null) {
                     aaj.addElementNameValuePair(nvp);
                  }
               }
            } while(keyIsOk);

            this.reportError("annotation @" + typename + " does not have a value named " + key);
            return null;
         }
      }
   }

   private AnnotationGen buildBaseAnnotationType(ConstantPool cp, World world, String typename) {
      String annoname = typename;
      if (typename.startsWith("@")) {
         annoname = typename.substring(1);
      }

      ResolvedType annotationType = UnresolvedType.forName(annoname).resolve(world);
      if (!annotationType.isAnnotation()) {
         this.reportError("declare is not specifying an annotation type :" + typename);
         return null;
      } else if (!annotationType.isAnnotationWithRuntimeRetention()) {
         this.reportError("declare is using an annotation type that does not have runtime retention: " + typename);
         return null;
      } else {
         List elems = new ArrayList();
         return new AnnotationGen(new ObjectType(annoname), elems, true, cp);
      }
   }

   private void generateAdviceMethod(Definition.PointcutAndAdvice paa, int adviceCounter, LazyClassGen cg) {
      ResolvedType delegateClass = this.world.resolve(UnresolvedType.forName(paa.adviceClass));
      if (delegateClass.isMissing()) {
         this.reportError("Class to invoke cannot be found: '" + paa.adviceClass + "'");
      } else {
         String adviceName = "generated$" + paa.adviceKind.toString().toLowerCase() + "$advice$" + adviceCounter;
         AnnotationAJ aaj = this.buildAdviceAnnotation(cg, paa);
         String method = paa.adviceMethod;
         int paren = method.indexOf("(");
         String methodName = method.substring(0, paren);
         String signature = method.substring(paren);
         if (signature.charAt(0) == '(' && signature.endsWith(")")) {
            List paramTypes = new ArrayList();
            List paramNames = new ArrayList();
            int nextChunkEndPos;
            String sig;
            int space;
            if (signature.charAt(1) != ')') {
               StringBuilder convertedSignature = new StringBuilder("(");
               boolean paramsBroken = false;

               for(int pos = 1; pos < signature.length() && signature.charAt(pos) != ')' && !paramsBroken; pos = nextChunkEndPos + 1) {
                  nextChunkEndPos = signature.indexOf(44, pos);
                  if (nextChunkEndPos == -1) {
                     nextChunkEndPos = signature.indexOf(41, pos);
                  }

                  sig = signature.substring(pos, nextChunkEndPos).trim();
                  space = sig.indexOf(" ");
                  ResolvedType resolvedParamType = null;
                  if (space == -1) {
                     if (sig.equals("JoinPoint")) {
                        sig = "com.bea.core.repackaged.aspectj.lang.JoinPoint";
                     } else if (sig.equals("JoinPoint.StaticPart")) {
                        sig = "com.bea.core.repackaged.aspectj.lang.JoinPoint$StaticPart";
                     } else if (sig.equals("ProceedingJoinPoint")) {
                        sig = "com.bea.core.repackaged.aspectj.lang.ProceedingJoinPoint";
                     }

                     UnresolvedType unresolvedParamType = UnresolvedType.forName(sig);
                     resolvedParamType = this.world.resolve(unresolvedParamType);
                  } else {
                     String typename = sig.substring(0, space);
                     if (typename.equals("JoinPoint")) {
                        typename = "com.bea.core.repackaged.aspectj.lang.JoinPoint";
                     } else if (typename.equals("JoinPoint.StaticPart")) {
                        typename = "com.bea.core.repackaged.aspectj.lang.JoinPoint$StaticPart";
                     } else if (typename.equals("ProceedingJoinPoint")) {
                        typename = "com.bea.core.repackaged.aspectj.lang.ProceedingJoinPoint";
                     }

                     UnresolvedType unresolvedParamType = UnresolvedType.forName(typename);
                     resolvedParamType = this.world.resolve(unresolvedParamType);
                     String paramname = sig.substring(space).trim();
                     paramNames.add(paramname);
                  }

                  if (resolvedParamType.isMissing()) {
                     this.reportError("Cannot find type specified as parameter: '" + sig + "' from signature '" + signature + "'");
                     paramsBroken = true;
                  }

                  paramTypes.add(Type.getType(resolvedParamType.getSignature()));
                  convertedSignature.append(resolvedParamType.getSignature());
               }

               convertedSignature.append(")");
               signature = convertedSignature.toString();
               if (paramsBroken) {
                  return;
               }
            }

            Type returnType = Type.VOID;
            int i;
            if (paa.adviceKind == Definition.AdviceKind.Around) {
               ResolvedMember[] methods = delegateClass.getDeclaredMethods();
               ResolvedMember found = null;
               ResolvedMember[] arr$ = methods;
               i = methods.length;

               for(space = 0; space < i; ++space) {
                  ResolvedMember candidate = arr$[space];
                  if (candidate.getName().equals(methodName)) {
                     UnresolvedType[] cparms = candidate.getParameterTypes();
                     if (cparms.length == paramTypes.size()) {
                        boolean paramsMatch = true;

                        for(int i = 0; i < cparms.length; ++i) {
                           if (!cparms[i].getSignature().equals(((Type)paramTypes.get(i)).getSignature())) {
                              paramsMatch = false;
                              break;
                           }
                        }

                        if (paramsMatch) {
                           found = candidate;
                           break;
                        }
                     }
                  }
               }

               if (found == null) {
                  this.reportError("Unable to find method to invoke.  In class: " + delegateClass.getName() + " cant find " + paa.adviceMethod);
                  return;
               }

               returnType = Type.getType(found.getReturnType().getSignature());
            }

            LazyMethodGen advice = new LazyMethodGen(1, (Type)returnType, adviceName, (Type[])paramTypes.toArray(new Type[paramTypes.size()]), EMPTY_STRINGS, cg);
            InstructionList adviceBody = advice.getBody();
            nextChunkEndPos = 1;

            for(i = 0; i < paramTypes.size(); ++i) {
               adviceBody.append((Instruction)InstructionFactory.createLoad((Type)paramTypes.get(i), nextChunkEndPos));
               nextChunkEndPos += ((Type)paramTypes.get(i)).getSize();
            }

            adviceBody.append((Instruction)cg.getFactory().createInvoke(paa.adviceClass, methodName, signature + ((Type)returnType).getSignature(), (short)184));
            if (returnType == Type.VOID) {
               adviceBody.append(InstructionConstants.RETURN);
            } else if (((Type)returnType).getSignature().length() < 2) {
               sig = ((Type)returnType).getSignature();
               if (sig.equals("F")) {
                  adviceBody.append(InstructionConstants.FRETURN);
               } else if (sig.equals("D")) {
                  adviceBody.append(InstructionConstants.DRETURN);
               } else if (sig.equals("J")) {
                  adviceBody.append(InstructionConstants.LRETURN);
               } else {
                  adviceBody.append(InstructionConstants.IRETURN);
               }
            } else {
               adviceBody.append(InstructionConstants.ARETURN);
            }

            advice.addAnnotation(aaj);
            InstructionHandle start = adviceBody.getStart();
            String sig = this.concreteAspect.name.replace('.', '/');
            start.addTargeter(new LocalVariableTag("L" + sig + ";", "this", 0, start.getPosition()));
            if (paramNames.size() > 0) {
               for(int i = 0; i < paramNames.size(); ++i) {
                  start.addTargeter(new LocalVariableTag(((Type)paramTypes.get(i)).getSignature(), (String)paramNames.get(i), i + 1, start.getPosition()));
               }
            }

            cg.addMethodGen(advice);
         } else {
            this.reportError("Badly formatted parameter signature: '" + method + "'");
         }
      }
   }

   private AnnotationAJ buildAdviceAnnotation(LazyClassGen cg, Definition.PointcutAndAdvice paa) {
      SimpleElementValue svg = new SimpleElementValue(115, cg.getConstantPool(), paa.pointcut);
      List elems = new ArrayList();
      elems.add(new NameValuePair("value", svg, cg.getConstantPool()));
      AnnotationGen mag = new AnnotationGen(new ObjectType("com/bea/core/repackaged/aspectj/lang/annotation/" + paa.adviceKind.toString()), elems, true, cg.getConstantPool());
      AnnotationAJ aaj = new BcelAnnotation(mag, this.world);
      return aaj;
   }

   private void reportError(String message) {
      this.world.getMessageHandler().handleMessage(new Message(message, IMessage.ERROR, (Throwable)null, (ISourceLocation)null));
   }
}
