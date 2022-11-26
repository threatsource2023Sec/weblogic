package com.bea.core.repackaged.aspectj.weaver.reflect;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.AnnotationDefault;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Attribute;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.JavaClass;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.LocalVariable;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.LocalVariableTable;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Method;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.AnnotationGen;
import com.bea.core.repackaged.aspectj.apache.bcel.util.NonCachingClassLoaderRepository;
import com.bea.core.repackaged.aspectj.apache.bcel.util.Repository;
import com.bea.core.repackaged.aspectj.weaver.AnnotationAJ;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.bcel.BcelAnnotation;
import com.bea.core.repackaged.aspectj.weaver.bcel.BcelWeakClassLoaderReference;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Java15AnnotationFinder implements AnnotationFinder, ArgNameFinder {
   private Repository bcelRepository;
   private BcelWeakClassLoaderReference classLoaderRef;
   private World world;
   public static final ResolvedType[][] NO_PARAMETER_ANNOTATIONS = new ResolvedType[0][];

   public void setClassLoader(ClassLoader aLoader) {
      this.classLoaderRef = new BcelWeakClassLoaderReference(aLoader);
      this.bcelRepository = new NonCachingClassLoaderRepository(this.classLoaderRef);
   }

   public void setWorld(World aWorld) {
      this.world = aWorld;
   }

   public Object getAnnotation(ResolvedType annotationType, Object onObject) {
      try {
         Class annotationClass = Class.forName(annotationType.getName(), false, this.getClassLoader());
         if (onObject.getClass().isAnnotationPresent(annotationClass)) {
            return onObject.getClass().getAnnotation(annotationClass);
         }
      } catch (ClassNotFoundException var4) {
      }

      return null;
   }

   public Object getAnnotationFromClass(ResolvedType annotationType, Class aClass) {
      try {
         Class annotationClass = Class.forName(annotationType.getName(), false, this.getClassLoader());
         if (aClass.isAnnotationPresent(annotationClass)) {
            return aClass.getAnnotation(annotationClass);
         }
      } catch (ClassNotFoundException var4) {
      }

      return null;
   }

   public Object getAnnotationFromMember(ResolvedType annotationType, Member aMember) {
      if (!(aMember instanceof AccessibleObject)) {
         return null;
      } else {
         AccessibleObject ao = (AccessibleObject)aMember;

         try {
            Class annotationClass = Class.forName(annotationType.getName(), false, this.getClassLoader());
            if (ao.isAnnotationPresent(annotationClass)) {
               return ao.getAnnotation(annotationClass);
            }
         } catch (ClassNotFoundException var5) {
         }

         return null;
      }
   }

   private ClassLoader getClassLoader() {
      return this.classLoaderRef.getClassLoader();
   }

   public AnnotationAJ getAnnotationOfType(UnresolvedType ofType, Member onMember) {
      if (!(onMember instanceof AccessibleObject)) {
         return null;
      } else {
         try {
            JavaClass jc = this.bcelRepository.loadClass(onMember.getDeclaringClass());
            AnnotationGen[] anns = new AnnotationGen[0];
            Method bcelMethod;
            if (onMember instanceof java.lang.reflect.Method) {
               bcelMethod = jc.getMethod((java.lang.reflect.Method)onMember);
               if (bcelMethod != null) {
                  anns = bcelMethod.getAnnotations();
               }
            } else if (onMember instanceof Constructor) {
               bcelMethod = jc.getMethod((Constructor)onMember);
               anns = bcelMethod.getAnnotations();
            } else if (onMember instanceof Field) {
               com.bea.core.repackaged.aspectj.apache.bcel.classfile.Field bcelField = jc.getField((Field)onMember);
               anns = bcelField.getAnnotations();
            }

            this.bcelRepository.clear();
            if (anns == null) {
               anns = new AnnotationGen[0];
            }

            for(int i = 0; i < anns.length; ++i) {
               if (anns[i].getTypeSignature().equals(ofType.getSignature())) {
                  return new BcelAnnotation(anns[i], this.world);
               }
            }

            return null;
         } catch (ClassNotFoundException var6) {
            return null;
         }
      }
   }

   public String getAnnotationDefaultValue(Member onMember) {
      try {
         JavaClass jc = this.bcelRepository.loadClass(onMember.getDeclaringClass());
         if (onMember instanceof java.lang.reflect.Method) {
            Method bcelMethod = jc.getMethod((java.lang.reflect.Method)onMember);
            if (bcelMethod != null) {
               Attribute[] attrs = bcelMethod.getAttributes();

               for(int i = 0; i < attrs.length; ++i) {
                  Attribute attribute = attrs[i];
                  if (attribute.getName().equals("AnnotationDefault")) {
                     AnnotationDefault def = (AnnotationDefault)attribute;
                     return def.getElementValue().stringifyValue();
                  }
               }

               return null;
            }
         }
      } catch (ClassNotFoundException var8) {
      }

      return null;
   }

   public Set getAnnotations(Member onMember) {
      if (!(onMember instanceof AccessibleObject)) {
         return Collections.EMPTY_SET;
      } else {
         HashSet annSet;
         int i;
         try {
            JavaClass jc = this.bcelRepository.loadClass(onMember.getDeclaringClass());
            AnnotationGen[] anns = new AnnotationGen[0];
            Method bcelMethod;
            if (onMember instanceof java.lang.reflect.Method) {
               bcelMethod = jc.getMethod((java.lang.reflect.Method)onMember);
               if (bcelMethod != null) {
                  anns = bcelMethod.getAnnotations();
               }
            } else if (onMember instanceof Constructor) {
               bcelMethod = jc.getMethod((Constructor)onMember);
               anns = bcelMethod.getAnnotations();
            } else if (onMember instanceof Field) {
               com.bea.core.repackaged.aspectj.apache.bcel.classfile.Field bcelField = jc.getField((Field)onMember);
               anns = bcelField.getAnnotations();
            }

            this.bcelRepository.clear();
            if (anns == null) {
               anns = new AnnotationGen[0];
            }

            annSet = new HashSet();

            for(i = 0; i < anns.length; ++i) {
               annSet.add(this.world.resolve(UnresolvedType.forSignature(anns[i].getTypeSignature())));
            }

            return annSet;
         } catch (ClassNotFoundException var6) {
            AccessibleObject ao = (AccessibleObject)onMember;
            Annotation[] anns = ao.getDeclaredAnnotations();
            annSet = new HashSet();

            for(i = 0; i < anns.length; ++i) {
               annSet.add(UnresolvedType.forName(anns[i].annotationType().getName()).resolve(this.world));
            }

            return annSet;
         }
      }
   }

   public ResolvedType[] getAnnotations(Class forClass, World inWorld) {
      try {
         JavaClass jc = this.bcelRepository.loadClass(forClass);
         AnnotationGen[] anns = jc.getAnnotations();
         this.bcelRepository.clear();
         if (anns == null) {
            return ResolvedType.NONE;
         } else {
            ResolvedType[] ret = new ResolvedType[anns.length];

            for(int i = 0; i < ret.length; ++i) {
               ret[i] = inWorld.resolve(UnresolvedType.forSignature(anns[i].getTypeSignature()));
            }

            return ret;
         }
      } catch (ClassNotFoundException var7) {
         Annotation[] classAnnotations = forClass.getAnnotations();
         ResolvedType[] ret = new ResolvedType[classAnnotations.length];

         for(int i = 0; i < classAnnotations.length; ++i) {
            ret[i] = inWorld.resolve(classAnnotations[i].annotationType().getName());
         }

         return ret;
      }
   }

   public String[] getParameterNames(Member forMember) {
      if (!(forMember instanceof AccessibleObject)) {
         return null;
      } else {
         try {
            JavaClass jc = this.bcelRepository.loadClass(forMember.getDeclaringClass());
            LocalVariableTable lvt = null;
            int numVars = 0;
            Method bcelCons;
            if (forMember instanceof java.lang.reflect.Method) {
               bcelCons = jc.getMethod((java.lang.reflect.Method)forMember);
               lvt = bcelCons.getLocalVariableTable();
               numVars = bcelCons.getArgumentTypes().length;
            } else if (forMember instanceof Constructor) {
               bcelCons = jc.getMethod((Constructor)forMember);
               lvt = bcelCons.getLocalVariableTable();
               numVars = bcelCons.getArgumentTypes().length;
            }

            return this.getParameterNamesFromLVT(lvt, numVars);
         } catch (ClassNotFoundException var6) {
            return null;
         }
      }
   }

   private String[] getParameterNamesFromLVT(LocalVariableTable lvt, int numVars) {
      if (lvt == null) {
         return null;
      } else {
         LocalVariable[] vars = lvt.getLocalVariableTable();
         if (vars.length < numVars) {
            return null;
         } else {
            String[] ret = new String[numVars];

            for(int i = 0; i < numVars; ++i) {
               ret[i] = vars[i + 1].getName();
            }

            return ret;
         }
      }
   }

   public ResolvedType[][] getParameterAnnotationTypes(Member onMember) {
      if (!(onMember instanceof AccessibleObject)) {
         return NO_PARAMETER_ANNOTATIONS;
      } else {
         ResolvedType[][] result;
         int i;
         int j;
         try {
            JavaClass jc = this.bcelRepository.loadClass(onMember.getDeclaringClass());
            AnnotationGen[][] anns = (AnnotationGen[][])null;
            Method bcelMethod;
            if (onMember instanceof java.lang.reflect.Method) {
               bcelMethod = jc.getMethod((java.lang.reflect.Method)onMember);
               if (bcelMethod != null) {
                  anns = bcelMethod.getParameterAnnotations();
               }
            } else if (onMember instanceof Constructor) {
               bcelMethod = jc.getMethod((Constructor)onMember);
               anns = bcelMethod.getParameterAnnotations();
            } else if (onMember instanceof Field) {
            }

            this.bcelRepository.clear();
            if (anns == null) {
               return NO_PARAMETER_ANNOTATIONS;
            } else {
               result = new ResolvedType[anns.length][];

               for(i = 0; i < anns.length; ++i) {
                  if (anns[i] != null) {
                     result[i] = new ResolvedType[anns[i].length];

                     for(j = 0; j < anns[i].length; ++j) {
                        result[i][j] = this.world.resolve(UnresolvedType.forSignature(anns[i][j].getTypeSignature()));
                     }
                  }
               }

               return result;
            }
         } catch (ClassNotFoundException var7) {
            AccessibleObject ao = (AccessibleObject)onMember;
            Annotation[][] anns = (Annotation[][])null;
            if (onMember instanceof java.lang.reflect.Method) {
               anns = ((java.lang.reflect.Method)ao).getParameterAnnotations();
            } else if (onMember instanceof Constructor) {
               anns = ((Constructor)ao).getParameterAnnotations();
            } else if (onMember instanceof Field) {
            }

            if (anns == null) {
               return NO_PARAMETER_ANNOTATIONS;
            } else {
               result = new ResolvedType[anns.length][];

               for(i = 0; i < anns.length; ++i) {
                  if (anns[i] != null) {
                     result[i] = new ResolvedType[anns[i].length];

                     for(j = 0; j < anns[i].length; ++j) {
                        result[i][j] = UnresolvedType.forName(anns[i][j].annotationType().getName()).resolve(this.world);
                     }
                  }
               }

               return result;
            }
         }
      }
   }
}
