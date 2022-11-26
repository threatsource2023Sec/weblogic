package com.bea.core.repackaged.springframework.expression.spel.support;

import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.TypeLocator;
import com.bea.core.repackaged.springframework.expression.spel.SpelEvaluationException;
import com.bea.core.repackaged.springframework.expression.spel.SpelMessage;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class StandardTypeLocator implements TypeLocator {
   @Nullable
   private final ClassLoader classLoader;
   private final List knownPackagePrefixes;

   public StandardTypeLocator() {
      this(ClassUtils.getDefaultClassLoader());
   }

   public StandardTypeLocator(@Nullable ClassLoader classLoader) {
      this.knownPackagePrefixes = new LinkedList();
      this.classLoader = classLoader;
      this.registerImport("java.lang");
   }

   public void registerImport(String prefix) {
      this.knownPackagePrefixes.add(prefix);
   }

   public void removeImport(String prefix) {
      this.knownPackagePrefixes.remove(prefix);
   }

   public List getImportPrefixes() {
      return Collections.unmodifiableList(this.knownPackagePrefixes);
   }

   public Class findType(String typeName) throws EvaluationException {
      String nameToLookup = typeName;

      try {
         return ClassUtils.forName(nameToLookup, this.classLoader);
      } catch (ClassNotFoundException var7) {
         Iterator var3 = this.knownPackagePrefixes.iterator();

         while(var3.hasNext()) {
            String prefix = (String)var3.next();

            try {
               nameToLookup = prefix + '.' + typeName;
               return ClassUtils.forName(nameToLookup, this.classLoader);
            } catch (ClassNotFoundException var6) {
            }
         }

         throw new SpelEvaluationException(SpelMessage.TYPE_NOT_FOUND, new Object[]{typeName});
      }
   }
}
