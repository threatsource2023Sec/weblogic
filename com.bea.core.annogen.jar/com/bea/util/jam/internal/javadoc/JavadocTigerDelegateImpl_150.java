package com.bea.util.jam.internal.javadoc;

import com.bea.util.jam.JClass;
import com.bea.util.jam.internal.elements.ElementContext;
import com.bea.util.jam.mutable.MAnnotatedElement;
import com.bea.util.jam.mutable.MAnnotation;
import com.bea.util.jam.mutable.MClass;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationTypeDoc;
import com.sun.javadoc.AnnotationTypeElementDoc;
import com.sun.javadoc.AnnotationValue;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.ExecutableMemberDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.ProgramElementDoc;
import com.sun.javadoc.SourcePosition;
import com.sun.javadoc.Type;

public final class JavadocTigerDelegateImpl_150 extends JavadocTigerDelegate {
   private ElementContext mContext;

   public void init(ElementContext ctx) {
      if (ctx == null) {
         throw new IllegalArgumentException();
      } else {
         this.mContext = ctx;
      }
   }

   public void populateAnnotationTypeIfNecessary(ClassDoc cd, MClass clazz, JavadocClassBuilder builder) {
      if (cd instanceof AnnotationTypeDoc) {
         clazz.setIsAnnotationType(true);
         AnnotationTypeElementDoc[] elements = ((AnnotationTypeDoc)cd).elements();

         for(int i = 0; i < elements.length; ++i) {
            builder.addMethod(clazz, elements[i]);
         }
      }

   }

   public void extractAnnotations(MAnnotatedElement dest, ProgramElementDoc src) {
      if (dest == null) {
         throw new IllegalArgumentException("null dest");
      } else if (src == null) {
         throw new IllegalArgumentException("null src");
      } else {
         this.extractAnnotations(dest, src.annotations(), src.position());
      }
   }

   public void extractAnnotations(MAnnotatedElement dest, ExecutableMemberDoc method, Parameter src) {
      this.extractAnnotations(dest, src.annotations(), method.position());
   }

   public boolean isEnum(ClassDoc cd) {
      return cd.isEnum();
   }

   private void extractAnnotations(MAnnotatedElement dest, AnnotationDesc[] anns, SourcePosition sp) {
      if (anns != null) {
         for(int i = 0; i < anns.length; ++i) {
            AnnotationTypeDoc type = this.getAnnotationTypeFor(anns[i], sp);
            if (type != null) {
               String tn = JavadocClassBuilder.getFdFor(type);
               MAnnotation destAnn = dest.findOrCreateAnnotation(tn);
               this.populateAnnotation(destAnn, anns[i], sp);
            }
         }

      }
   }

   private void populateAnnotation(MAnnotation dest, AnnotationDesc src, SourcePosition sp) {
      if (sp != null) {
         JavadocClassBuilder.addSourcePosition(dest, (SourcePosition)sp);
      }

      AnnotationDesc.ElementValuePair[] mvps = src.elementValues();

      for(int i = 0; i < mvps.length; ++i) {
         Type jmt = mvps[i].element().returnType();
         String name = mvps[i].element().name();
         AnnotationValue aval = mvps[i].value();
         this.setAnnotationValue(name, jmt, aval, dest, sp);
      }

      AnnotationTypeDoc atd = this.getAnnotationTypeFor(src, sp);
      if (atd != null) {
         AnnotationTypeElementDoc[] elements = atd.elements();

         for(int i = 0; i < elements.length; ++i) {
            AnnotationValue value = elements[i].defaultValue();
            if (value != null) {
               String name = elements[i].name();
               if (dest.getValue(name) == null) {
                  this.setAnnotationValue(name, elements[i].returnType(), value, dest, sp);
               }
            }
         }

      }
   }

   private void setAnnotationValue(String memberName, Type returnType, AnnotationValue aval, MAnnotation dest, SourcePosition sp) {
      String typeName = this.getFdFor(returnType);

      Object valueObj;
      try {
         valueObj = aval.value();
      } catch (NullPointerException var11) {
         this.mContext.getLogger().warning("Encountered a known javadoc bug which usually \nindicates a syntax error in an annotation value declaration.\nThe value is being ignored.\n[file=" + sp.file() + ", line=" + sp.line() + "]");
         return;
      }

      if (this.mContext.getLogger().isVerbose((Object)this)) {
         this.mContext.getLogger().verbose(memberName + " is a " + typeName + " with valueObj " + valueObj + ", class is " + valueObj.getClass());
      }

      if (valueObj instanceof AnnotationDesc) {
         MAnnotation nested = dest.createNestedValue(memberName, typeName);
         this.populateAnnotation(nested, (AnnotationDesc)valueObj, sp);
      } else {
         JClass clazz;
         String v;
         if (!(valueObj instanceof Number) && !(valueObj instanceof Boolean)) {
            if (valueObj instanceof FieldDoc) {
               v = JavadocClassBuilder.getFdFor(((FieldDoc)valueObj).containingClass());
               clazz = this.mContext.getClassLoader().loadClass(v);
               String val = ((FieldDoc)valueObj).name();
               dest.setSimpleValue(memberName, val, clazz);
            } else if (valueObj instanceof ClassDoc) {
               v = JavadocClassBuilder.getFdFor((ClassDoc)valueObj);
               clazz = this.mContext.getClassLoader().loadClass(v);
               dest.setSimpleValue(memberName, clazz, this.loadClass(JClass.class));
            } else if (valueObj instanceof String) {
               v = ((String)valueObj).trim();
               if (v.startsWith("\"") && v.endsWith("\"")) {
                  valueObj = v.substring(1, v.length() - 1);
               }

               dest.setSimpleValue(memberName, valueObj, this.loadClass(String.class));
            } else if (valueObj instanceof AnnotationValue[]) {
               this.populateArrayMember(dest, memberName, returnType, (AnnotationValue[])((AnnotationValue[])valueObj), sp);
            } else if ("com.sun.tools.javadoc.PrimitiveType".equals(valueObj.getClass().getName()) && "void".equals(valueObj + "")) {
               this.mContext.getLogger().warning("Value of annotation member " + memberName + " is of an Primitive type: " + valueObj.getClass() + "   [" + valueObj + "]");
            } else {
               this.mContext.getLogger().error("Value of annotation member " + memberName + " is of an unexpected type: " + valueObj.getClass() + "   [" + valueObj + "]");
            }
         } else {
            v = JavadocClassBuilder.getFdFor(returnType);
            clazz = this.mContext.getClassLoader().loadClass(v);
            dest.setSimpleValue(memberName, valueObj, clazz);
         }
      }

   }

   private void populateArrayMember(MAnnotation dest, String memberName, Type returnType, AnnotationValue[] annValueArray, SourcePosition sp) {
      if (sp != null) {
         JavadocClassBuilder.addSourcePosition(dest, (SourcePosition)sp);
      }

      Object[] valueArray;
      if (annValueArray.length == 0) {
         valueArray = new Object[0];
         dest.setSimpleValue(memberName, valueArray, this.loadClass(returnType));
      } else {
         valueArray = new Object[annValueArray.length];

         for(int i = 0; i < valueArray.length; ++i) {
            try {
               valueArray[i] = annValueArray[i].value();
               if (valueArray[i] == null) {
                  this.mContext.getLogger().error("Javadoc provided an array annotation member value which contains [file=" + sp.file() + ", line=" + sp.line() + "]");
                  return;
               }
            } catch (NullPointerException var11) {
               this.mContext.getLogger().warning("Encountered a known javadoc bug which usually \nindicates a syntax error in an annotation value declaration.\nThe value is being ignored.\n[file=" + sp.file() + ", line=" + sp.line() + "]");
               return;
            }
         }

         int i;
         if (valueArray[0] instanceof AnnotationDesc) {
            AnnotationTypeDoc atd = this.getAnnotationTypeFor((AnnotationDesc)valueArray[0], sp);
            if (atd == null) {
               return;
            }

            String annType = this.getFdFor(atd);
            MAnnotation[] anns = dest.createNestedValueArray(memberName, annType, valueArray.length);

            for(i = 0; i < anns.length; ++i) {
               this.populateAnnotation(anns[i], (AnnotationDesc)valueArray[i], sp);
            }
         } else if (!(valueArray[0] instanceof Number) && !(valueArray[0] instanceof Boolean)) {
            if (valueArray[0] instanceof FieldDoc) {
               String enumTypeName = JavadocClassBuilder.getFdFor(((FieldDoc)valueArray[0]).containingClass());
               JClass memberType = this.loadClass("[L" + enumTypeName + ";");
               String[] value = new String[valueArray.length];

               for(i = 0; i < valueArray.length; ++i) {
                  value[i] = ((FieldDoc)valueArray[i]).name();
               }

               dest.setSimpleValue(memberName, value, memberType);
            } else {
               int i;
               if (valueArray[0] instanceof ClassDoc) {
                  JClass[] value = new JClass[valueArray.length];

                  for(i = 0; i < value.length; ++i) {
                     value[i] = this.loadClass((Type)((ClassDoc)valueArray[0]));
                  }

                  dest.setSimpleValue(memberName, value, this.loadClass(JClass[].class));
               } else if (valueArray[0] instanceof String) {
                  String[] value = new String[valueArray.length];

                  for(i = 0; i < value.length; ++i) {
                     String v = ((String)valueArray[i]).trim();
                     if (v.startsWith("\"") && v.endsWith("\"")) {
                        v = v.substring(1, v.length() - 1);
                     }

                     value[i] = v;
                  }

                  dest.setSimpleValue(memberName, value, this.loadClass(String[].class));
               } else {
                  this.mContext.getLogger().error("Value of array annotation member " + memberName + " is of an unexpected type: " + valueArray[0].getClass() + "   [" + valueArray[0] + "]");
               }
            }
         } else {
            JClass type = this.loadClass(returnType);
            dest.setSimpleValue(memberName, annValueArray, type);
         }

      }
   }

   private String getFdFor(Type t) {
      return JavadocClassBuilder.getFdFor(t);
   }

   private JClass loadClass(Type type) {
      return this.loadClass(this.getFdFor(type));
   }

   private JClass loadClass(Class clazz) {
      return this.loadClass(clazz.getName());
   }

   private JClass loadClass(String fd) {
      return this.mContext.getClassLoader().loadClass(fd);
   }

   private AnnotationTypeDoc getAnnotationTypeFor(AnnotationDesc d, SourcePosition sp) {
      try {
         return d.annotationType();
      } catch (ClassCastException var4) {
         this.mContext.getLogger().error((Throwable)(new JavadocParsingException(d, sp, var4)));
         return null;
      }
   }
}
