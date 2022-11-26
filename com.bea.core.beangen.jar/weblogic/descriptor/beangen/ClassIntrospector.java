package weblogic.descriptor.beangen;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JMethod;
import java.util.HashMap;
import java.util.Map;

public class ClassIntrospector extends BeanElement {
   private Map methodMap;

   public ClassIntrospector(JClass clazz) {
      super(clazz);
      MethodFactory factory = MethodFactory.SINGLETON;
      JMethod[] methods = clazz.getMethods();
      this.methodMap = new HashMap(methods.length);

      for(int i = 0; i < methods.length; ++i) {
         if (methods[i].isPublic()) {
            MethodDeclaration decl = factory.createDeclaration((BeanClass)null, methods[i], true);
            this.methodMap.put(decl, decl);
         }
      }

   }

   public boolean definesMethod(String name, JClass[] signature) {
      Object key = MethodFactory.SINGLETON.createKey(name, signature);
      return this.methodMap.containsKey(key);
   }

   public boolean definesMethod(MethodDeclaration key) {
      return this.methodMap.containsKey(key);
   }

   public MethodDeclaration getMethod(MethodDeclaration key) {
      return (MethodDeclaration)this.methodMap.get(key);
   }
}
