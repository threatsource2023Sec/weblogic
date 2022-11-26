package org.apache.commons.io.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class ValidatingObjectInputStream extends ObjectInputStream {
   private final List acceptMatchers = new ArrayList();
   private final List rejectMatchers = new ArrayList();

   public ValidatingObjectInputStream(InputStream input) throws IOException {
      super(input);
   }

   private void validateClassName(String name) throws InvalidClassException {
      Iterator var2 = this.rejectMatchers.iterator();

      while(var2.hasNext()) {
         ClassNameMatcher m = (ClassNameMatcher)var2.next();
         if (m.matches(name)) {
            this.invalidClassNameFound(name);
         }
      }

      boolean ok = false;
      Iterator var6 = this.acceptMatchers.iterator();

      while(var6.hasNext()) {
         ClassNameMatcher m = (ClassNameMatcher)var6.next();
         if (m.matches(name)) {
            ok = true;
            break;
         }
      }

      if (!ok) {
         this.invalidClassNameFound(name);
      }

   }

   protected void invalidClassNameFound(String className) throws InvalidClassException {
      throw new InvalidClassException("Class name not accepted: " + className);
   }

   protected Class resolveClass(ObjectStreamClass osc) throws IOException, ClassNotFoundException {
      this.validateClassName(osc.getName());
      return super.resolveClass(osc);
   }

   public ValidatingObjectInputStream accept(Class... classes) {
      Class[] var2 = classes;
      int var3 = classes.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class c = var2[var4];
         this.acceptMatchers.add(new FullClassNameMatcher(new String[]{c.getName()}));
      }

      return this;
   }

   public ValidatingObjectInputStream reject(Class... classes) {
      Class[] var2 = classes;
      int var3 = classes.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class c = var2[var4];
         this.rejectMatchers.add(new FullClassNameMatcher(new String[]{c.getName()}));
      }

      return this;
   }

   public ValidatingObjectInputStream accept(String... patterns) {
      String[] var2 = patterns;
      int var3 = patterns.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String pattern = var2[var4];
         this.acceptMatchers.add(new WildcardClassNameMatcher(pattern));
      }

      return this;
   }

   public ValidatingObjectInputStream reject(String... patterns) {
      String[] var2 = patterns;
      int var3 = patterns.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String pattern = var2[var4];
         this.rejectMatchers.add(new WildcardClassNameMatcher(pattern));
      }

      return this;
   }

   public ValidatingObjectInputStream accept(Pattern pattern) {
      this.acceptMatchers.add(new RegexpClassNameMatcher(pattern));
      return this;
   }

   public ValidatingObjectInputStream reject(Pattern pattern) {
      this.rejectMatchers.add(new RegexpClassNameMatcher(pattern));
      return this;
   }

   public ValidatingObjectInputStream accept(ClassNameMatcher m) {
      this.acceptMatchers.add(m);
      return this;
   }

   public ValidatingObjectInputStream reject(ClassNameMatcher m) {
      this.rejectMatchers.add(m);
      return this;
   }
}
