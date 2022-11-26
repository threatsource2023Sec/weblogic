package org.python.compiler.custom_proxymaker;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.python.compiler.Code;
import org.python.compiler.JavaMaker;
import org.python.compiler.ProxyCodeHelpers;
import org.python.core.Py;
import org.python.core.PyDictionary;
import org.python.core.PyObject;
import org.python.core.__builtin__;
import org.python.util.Generic;

public class MiniClampMaker extends JavaMaker {
   private final Map methodsToAdd = Generic.map();
   private final Map constructorsToAdd = Generic.map();
   private ProxyCodeHelpers.AnnotationDescr[] classAnnotations = new ProxyCodeHelpers.AnnotationDescr[0];

   private static ProxyCodeHelpers.AnnotationDescr[] extractAnnotation(PyDictionary dict) {
      List annotationDescrs = Generic.list();

      ProxyCodeHelpers.AnnotationDescr annotationDescr;
      for(Iterator var2 = dict.iteritems().asIterable().iterator(); var2.hasNext(); annotationDescrs.add(annotationDescr)) {
         PyObject annotationIter = (PyObject)var2.next();
         PyObject annotationClass = annotationIter.__getitem__(0);
         PyObject annotationFields = annotationIter.__getitem__(1);
         annotationDescr = null;
         if (annotationFields == Py.None) {
            annotationDescr = new ProxyCodeHelpers.AnnotationDescr((Class)Py.tojava(annotationClass, Class.class));
         } else {
            Map fields = Generic.map();
            Iterator var8 = ((PyDictionary)annotationFields).iteritems().asIterable().iterator();

            while(var8.hasNext()) {
               PyObject item = (PyObject)var8.next();
               fields.put(Py.tojava(item.__getitem__(0), String.class), Py.tojava(item.__getitem__(1), Object.class));
            }

            annotationDescr = new ProxyCodeHelpers.AnnotationDescr((Class)Py.tojava(annotationClass, Class.class), fields);
         }
      }

      return (ProxyCodeHelpers.AnnotationDescr[])((ProxyCodeHelpers.AnnotationDescr[])annotationDescrs.toArray(new ProxyCodeHelpers.AnnotationDescr[annotationDescrs.size()]));
   }

   public MiniClampMaker(Class superclass, Class[] interfaces, String pythonClass, String pythonModule, String myClass, PyObject methods) {
      super(superclass, interfaces, pythonClass, pythonModule, myClass, methods);
      PyObject javaPackage = methods.__finditem__("__java_package__");
      if (javaPackage != null) {
         String newMyClass = new String((String)javaPackage.__tojava__(String.class));
         newMyClass = newMyClass + "." + pythonClass;
         this.myClass = newMyClass;
      }

      PyObject clampAttr = Py.newString("_clamp");
      Iterator var9 = methods.asIterable().iterator();

      while(var9.hasNext()) {
         PyObject pykey = (PyObject)var9.next();
         String key = (String)Py.tojava(pykey, String.class);
         PyObject value = methods.__finditem__(key);
         PyObject clampObj = __builtin__.getattr(value, clampAttr, Py.None);
         if (clampObj != Py.None) {
            String name = (String)clampObj.__getattr__("name").__tojava__(String.class);
            if (name.equals("__init__")) {
               this.constructorsToAdd.put(key, clampObj);
            } else {
               this.methodsToAdd.put(key, clampObj);
            }
         }
      }

      PyObject pyAnnotations = methods.__finditem__("_clamp_class_annotations");
      if (pyAnnotations != null) {
         this.classAnnotations = extractAnnotation((PyDictionary)pyAnnotations);
      }

   }

   protected void visitClassAnnotations() throws Exception {
      ProxyCodeHelpers.AnnotationDescr[] var1 = this.classAnnotations;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         ProxyCodeHelpers.AnnotationDescr annotation = var1[var3];
         this.addClassAnnotation(annotation);
      }

   }

   protected void visitConstructors() throws Exception {
      Set superConstructors = Generic.set();
      Constructor[] var2 = this.superclass.getDeclaredConstructors();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Constructor constructor = var2[var4];
         superConstructors.add(constructor);
      }

      Iterator var16 = this.constructorsToAdd.entrySet().iterator();

      while(var16.hasNext()) {
         Map.Entry meth = (Map.Entry)var16.next();
         Constructor superToCall = null;
         String pyName = (String)meth.getKey();
         PyObject clampObj = (PyObject)meth.getValue();
         Class[] thrownClasses = (Class[])Py.tojava(clampObj.__getattr__("throws"), Class[].class);
         Class[] parameterClasses = (Class[])Py.tojava(clampObj.__getattr__("argtypes"), Class[].class);
         if (clampObj.__findattr__("super_constructor") != null) {
            superToCall = (Constructor)clampObj.__getattr__("super_constructor").__tojava__(Constructor.class);
         } else {
            try {
               superToCall = this.superclass.getDeclaredConstructor(parameterClasses);
            } catch (NoSuchMethodException var15) {
               superToCall = (Constructor)superConstructors.iterator().next();
            }
         }

         Iterator var9 = superConstructors.iterator();

         while(var9.hasNext()) {
            Constructor constructor = (Constructor)var9.next();
            if (Arrays.equals(constructor.getParameterTypes(), superToCall.getParameterTypes())) {
               superConstructors.remove(constructor);
            }
         }

         ProxyCodeHelpers.AnnotationDescr[] methodAnnotations = extractAnnotation((PyDictionary)clampObj.__getattr__("method_annotations"));
         PyObject[] parameterAnnotationObjs = (PyObject[])((PyObject[])clampObj.__getattr__("parameter_annotations").__tojava__(PyObject[].class));
         ProxyCodeHelpers.AnnotationDescr[][] parameterAnnotations = new ProxyCodeHelpers.AnnotationDescr[parameterAnnotationObjs.length][];

         for(int i = 0; i < parameterAnnotationObjs.length; ++i) {
            if (parameterAnnotationObjs[i].isMappingType()) {
               parameterAnnotations[i] = extractAnnotation((PyDictionary)parameterAnnotationObjs[i]);
            }
         }

         String fullsig = makeSig(Void.TYPE, parameterClasses);
         String[] mappedExceptions = mapExceptions(thrownClasses);
         Code code = this.classfile.addMethod("<init>", fullsig, 1, mappedExceptions, methodAnnotations, parameterAnnotations);
         this.callSuper(code, "<init>", mapClass(this.superclass), superToCall.getParameterTypes(), Void.TYPE, false);
         this.addConstructorMethodCode(pyName, superToCall.getParameterTypes(), thrownClasses, 1, this.superclass, code);
      }

      var16 = superConstructors.iterator();

      while(var16.hasNext()) {
         Constructor constructor = (Constructor)var16.next();
         this.addConstructor(constructor.getParameterTypes(), 1);
      }

   }

   protected void visitMethods() throws Exception {
      Iterator var1 = this.methodsToAdd.entrySet().iterator();

      while(var1.hasNext()) {
         Map.Entry meth = (Map.Entry)var1.next();
         PyObject clampObj = (PyObject)meth.getValue();
         String methodName = (String)clampObj.__getattr__("name").__tojava__(String.class);
         Class returnClass = (Class)clampObj.__getattr__("returntype").__tojava__(Class.class);
         Class[] thrownClasses = (Class[])Py.tojava(clampObj.__getattr__("throws"), Class[].class);
         Class[] parameterClasses = (Class[])Py.tojava(clampObj.__getattr__("argtypes"), Class[].class);
         ProxyCodeHelpers.AnnotationDescr[] methodAnnotations = extractAnnotation((PyDictionary)clampObj.__getattr__("method_annotations"));
         PyObject[] parameterAnnotationObjs = (PyObject[])((PyObject[])clampObj.__getattr__("parameter_annotations").__tojava__(PyObject[].class));
         ProxyCodeHelpers.AnnotationDescr[][] parameterAnnotations = new ProxyCodeHelpers.AnnotationDescr[parameterAnnotationObjs.length][];

         for(int i = 0; i < parameterAnnotationObjs.length; ++i) {
            if (parameterAnnotationObjs[i].isMappingType()) {
               parameterAnnotations[i] = extractAnnotation((PyDictionary)parameterAnnotationObjs[i]);
            }
         }

         this.addMethod(methodName, (String)meth.getKey(), returnClass, parameterClasses, thrownClasses, 1, this.superclass, methodAnnotations, parameterAnnotations);
      }

   }
}
