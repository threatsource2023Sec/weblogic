package com.sun.faces.facelets.util;

import com.sun.faces.facelets.tag.TagLibrary;
import com.sun.faces.util.Util;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;

public class FunctionLibrary implements TagLibrary {
   public static final String Namespace = "http://java.sun.com/jsp/jstl/functions";
   public static final String XMLNSNamespace = "http://xmlns.jcp.org/jsp/jstl/functions";
   private String _namespace;
   private Map functions;

   public FunctionLibrary(Class functionsClass, String namespace) {
      Util.notNull("functionsClass", functionsClass);
      Util.notNull("namespace", namespace);
      this._namespace = namespace;

      try {
         Method[] methods = functionsClass.getMethods();
         this.functions = new HashMap(methods.length, 1.0F);
         Method[] var4 = methods;
         int var5 = methods.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Method method = var4[var6];
            if (Modifier.isStatic(method.getModifiers()) && Modifier.isPublic(method.getModifiers())) {
               this.functions.put(method.getName(), method);
            }
         }

      } catch (Exception var8) {
         throw new RuntimeException(var8);
      }
   }

   public boolean containsNamespace(String ns, Tag t) {
      return this._namespace.equals(ns);
   }

   public boolean containsTagHandler(String ns, String localName) {
      return false;
   }

   public TagHandler createTagHandler(String ns, String localName, TagConfig tag) throws FacesException {
      return null;
   }

   public boolean containsFunction(String ns, String name) {
      return this._namespace.equals(ns) && this.functions.containsKey(name);
   }

   public Method createFunction(String ns, String name) {
      return this._namespace.equals(ns) ? (Method)this.functions.get(name) : null;
   }
}
