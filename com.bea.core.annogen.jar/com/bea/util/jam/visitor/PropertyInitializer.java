package com.bea.util.jam.visitor;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JMethod;
import com.bea.util.jam.JProperty;
import com.bea.util.jam.internal.elements.PropertyImpl;
import com.bea.util.jam.mutable.MClass;
import java.util.HashMap;
import java.util.Map;

public class PropertyInitializer extends MVisitor {
   public void visit(MClass clazz) {
      this.addProperties(clazz, true);
      this.addProperties(clazz, false);
   }

   private void addProperties(MClass clazz, boolean declared) {
      JMethod[] methods = declared ? clazz.getDeclaredMethods() : clazz.getMethods();
      Map name2prop = new HashMap();

      for(int i = 0; i < methods.length; ++i) {
         String name = methods[i].getSimpleName();
         JClass typ;
         JProperty prop;
         if (name.startsWith("get") && name.length() > 3 || name.startsWith("is") && name.length() > 2) {
            typ = methods[i].getReturnType();
            if (typ == null || methods[i].getParameters().length > 0) {
               continue;
            }

            if (name.startsWith("get")) {
               name = name.substring(3);
            } else {
               name = name.substring(2);
            }

            prop = (JProperty)name2prop.get(name);
            if (prop == null) {
               prop = declared ? clazz.addNewDeclaredProperty(name, methods[i], (JMethod)null) : clazz.addNewProperty(name, methods[i], (JMethod)null);
               name2prop.put(name, prop);
            } else if (typ.equals(prop.getType())) {
               ((PropertyImpl)prop).setGetter(methods[i]);
            }
         }

         if (name.startsWith("set") && name.length() > 3 && methods[i].getParameters().length == 1) {
            typ = methods[i].getParameters()[0].getType();
            name = name.substring(3);
            prop = (JProperty)name2prop.get(name);
            if (prop == null) {
               prop = declared ? clazz.addNewDeclaredProperty(name, (JMethod)null, methods[i]) : clazz.addNewProperty(name, (JMethod)null, methods[i]);
               name2prop.put(name, prop);
            } else if (typ.equals(prop.getType())) {
               ((PropertyImpl)prop).setSetter(methods[i]);
            }
         }
      }

   }
}
