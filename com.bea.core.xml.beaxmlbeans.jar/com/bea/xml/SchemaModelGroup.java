package com.bea.xml;

import javax.xml.namespace.QName;

public interface SchemaModelGroup extends SchemaComponent, SchemaAnnotated {
   int getComponentType();

   QName getName();

   Object getUserData();

   public static final class Ref extends SchemaComponent.Ref {
      public Ref() {
      }

      public Ref(SchemaModelGroup modelGroup) {
         super(modelGroup);
      }

      public Ref(SchemaTypeSystem system, String handle) {
         super(system, handle);
      }

      public final int getComponentType() {
         return 6;
      }

      public final SchemaModelGroup get() {
         return (SchemaModelGroup)this.getComponent();
      }
   }
}
