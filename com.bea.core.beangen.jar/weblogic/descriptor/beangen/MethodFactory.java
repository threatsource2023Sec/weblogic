package weblogic.descriptor.beangen;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JMethod;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MethodFactory {
   public static final MethodFactory SINGLETON = new MethodFactory();
   private MethodType[] supportedTypes;
   private List implFactories;

   private MethodFactory() {
      this.supportedTypes = new MethodType[]{MethodType.EXPLICIT_OPERATION, PropertyMethodType.ISSET, PropertyMethodType.GETTER, PropertyMethodType.SETTER, PropertyMethodType.ADDER, PropertyMethodType.REMOVER, PropertyMethodType.CREATOR, PropertyMethodType.DESTROYER, PropertyMethodType.FINDER, MethodType.IMPLICIT_OPERATION};
      this.implFactories = new ArrayList();
   }

   public MethodDeclaration createDeclaration(BeanClass bean, JMethod method, boolean declare) {
      MethodDeclaration decl = null;

      for(int i = 0; i < this.supportedTypes.length; ++i) {
         if (this.supportedTypes[i].matches(method)) {
            decl = this.supportedTypes[i].createDeclaration(bean, method);
            if (decl != null) {
               decl.setDeclared(declare);
               break;
            }
         }
      }

      return decl;
   }

   public MethodDeclaration createKey(String name, JClass[] signature) {
      JMethod method = new SyntheticJMethod(JClasses.VOID, name, signature, new JClass[0]);
      return new MethodDeclaration((BeanClass)null, (MethodType)null, method);
   }

   public MethodImplementation createImplementation(MethodDeclaration decl, BeanCustomizer customizer) {
      MethodImplementation impl = null;
      Iterator it = this.implFactories.iterator();

      while(it.hasNext()) {
         ImplementationFactory f = (ImplementationFactory)it.next();
         impl = f.create(decl, customizer);
         if (impl != null) {
            break;
         }
      }

      return impl;
   }

   public void registerImplementation(ImplementationFactory subFactory) {
      this.implFactories.add(0, subFactory);
   }

   public static class ImplementationFactory {
      private MethodType type;
      private Class clazz;
      private Constructor constructor;

      protected ImplementationFactory() {
      }

      protected ImplementationFactory(MethodType type, Class clazz) {
         this.type = type;
         this.clazz = clazz;

         try {
            this.constructor = clazz.getConstructor(MethodDeclaration.class, BeanCustomizer.class);
         } catch (NoSuchMethodException var4) {
            throw new AssertionError(clazz.getName() + " must define public " + clazz.getName() + "(MethodDeclaration, BeanCustomizer)");
         }
      }

      public MethodImplementation create(MethodDeclaration decl, BeanCustomizer customizer) {
         if (!decl.isType(this.type)) {
            return null;
         } else {
            try {
               return (MethodImplementation)this.constructor.newInstance(decl, customizer);
            } catch (RuntimeException var4) {
               throw var4;
            } catch (InvocationTargetException var5) {
               if (var5.getCause() instanceof RuntimeException) {
                  throw (RuntimeException)var5.getCause();
               } else {
                  throw new AssertionError("UnexpectedException: " + var5.getCause());
               }
            } catch (Exception var6) {
               throw new AssertionError("UnexpectedException: " + var6);
            }
         }
      }
   }
}
