package org.hibernate.validator.cdi.internal;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.classhierarchy.ClassHierarchyHelper;
import org.hibernate.validator.internal.util.classhierarchy.Filter;
import org.hibernate.validator.internal.util.privilegedactions.GetMethods;

public class InheritedMethodsHelper {
   private InheritedMethodsHelper() {
   }

   public static List getAllMethods(Class clazz) {
      Contracts.assertNotNull(clazz);
      List methods = CollectionHelper.newArrayList();
      Iterator var2 = ClassHierarchyHelper.getHierarchy(clazz, new Filter[0]).iterator();

      while(var2.hasNext()) {
         Class hierarchyClass = (Class)var2.next();
         Collections.addAll(methods, (Object[])run(GetMethods.action(hierarchyClass)));
      }

      return methods;
   }

   private static Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }
}
