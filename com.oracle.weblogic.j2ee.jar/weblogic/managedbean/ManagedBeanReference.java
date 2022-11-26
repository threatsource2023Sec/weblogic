package weblogic.managedbean;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import weblogic.jndi.ClassTypeOpaqueReference;
import weblogic.utils.classloaders.GenericClassLoader;

public final class ManagedBeanReference implements ClassTypeOpaqueReference {
   private final String className;
   private final ManagedBeanCreator creator;
   private final GenericClassLoader moduleCL;

   ManagedBeanReference(String className, ManagedBeanCreator mbc, GenericClassLoader gcl) {
      this.className = className;
      this.creator = mbc;
      this.moduleCL = gcl;
   }

   public Object getReferent(Name name, Context ctx) throws NamingException {
      try {
         Object instance = this.creator.createInstance(this.className);
         this.creator.notifyPostConstruct(this.className, instance);
         return instance;
      } catch (Exception var5) {
         NamingException ne = new NamingException("Cannot create managed bean instance, name: " + name + ", class name: " + this.className);
         ne.setRootCause(var5);
         throw ne;
      }
   }

   public Class getObjectClass() {
      try {
         return this.moduleCL.loadClass(this.className);
      } catch (ClassNotFoundException var2) {
         throw new AssertionError(var2);
      }
   }
}
