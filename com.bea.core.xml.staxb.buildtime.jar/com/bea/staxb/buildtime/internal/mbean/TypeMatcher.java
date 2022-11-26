package com.bea.staxb.buildtime.internal.mbean;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JMethod;
import com.bea.util.jam.JParameter;
import com.bea.util.jam.JProperty;
import com.bea.xml.SchemaProperty;
import com.bea.xml.SchemaType;
import com.bea.xml.SchemaTypeSystem;

public interface TypeMatcher {
   void init(TypeMatcherContext var1);

   MatchedType[] matchTypes(JClass[] var1, SchemaTypeSystem var2);

   MatchedProperties[] matchProperties(JClass var1, SchemaType var2);

   JClass substituteClass(JClass var1);

   public static class MatchedProperties {
      private JProperty jProperty;
      private SchemaProperty sProperty;
      private JMethod isSetter;
      private JMethod factoryMethod;

      public MatchedProperties(JProperty jProperty, SchemaProperty sProperty) {
         this(jProperty, sProperty, (JMethod)null);
      }

      public MatchedProperties(JProperty jProperty, SchemaProperty sProperty, JMethod isSetter, JMethod factoryMethod) {
         this(jProperty, sProperty, isSetter);
         this.factoryMethod = factoryMethod;
      }

      public MatchedProperties(JProperty jProperty, SchemaProperty sProperty, JMethod isSetter) {
         this.isSetter = null;
         this.factoryMethod = null;
         this.jProperty = jProperty;
         this.sProperty = sProperty;
         if (isSetter != null) {
            if (isSetter.getParameters().length > 0) {
               throw new IllegalArgumentException("an isSetter method must take no parameters ('" + isSetter.getQualifiedName() + "')");
            }

            if (!isSetter.getReturnType().getQualifiedName().equals("boolean")) {
               throw new IllegalArgumentException("an isSetter method must return 'boolean' ('" + isSetter.getQualifiedName() + "')");
            }
         }

         this.isSetter = isSetter;
      }

      public JProperty getJProperty() {
         return this.jProperty;
      }

      public SchemaProperty getSProperty() {
         return this.sProperty;
      }

      public JMethod getIsSetter() {
         return this.isSetter;
      }

      public JMethod getFactoryMethod() {
         return this.factoryMethod;
      }

      private static final void validateFactoryMethod(JMethod factory, JProperty prop) {
         JParameter[] params = factory.getParameters();
         if (params.length != 1) {
            throw new IllegalArgumentException(factory.getQualifiedName() + " not a valid factory factory, must have exactly 1 parameter");
         } else if (!params[0].getType().getQualifiedName().equals("java.lang.Class")) {
            throw new IllegalArgumentException(factory.getQualifiedName() + " must take a java.lang.Class as a parameter");
         } else if (!prop.getType().isAssignableFrom(factory.getReturnType())) {
            throw new IllegalArgumentException(factory.getQualifiedName() + " must return an instance of " + prop.getType().getQualifiedName());
         }
      }
   }

   public static class MatchedType {
      private JClass jClass;
      private SchemaType sType;

      public MatchedType(JClass jClass, SchemaType sType) {
         this.jClass = jClass;
         this.sType = sType;
      }

      public JClass getJClass() {
         return this.jClass;
      }

      public SchemaType getSType() {
         return this.sType;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (!(o instanceof MatchedType)) {
            return false;
         } else {
            MatchedType matchedType = (MatchedType)o;
            if (!this.jClass.equals(matchedType.jClass)) {
               return false;
            } else {
               return this.sType.equals(matchedType.sType);
            }
         }
      }

      public int hashCode() {
         int result = this.jClass.hashCode();
         result = 29 * result + this.sType.hashCode();
         return result;
      }
   }
}
