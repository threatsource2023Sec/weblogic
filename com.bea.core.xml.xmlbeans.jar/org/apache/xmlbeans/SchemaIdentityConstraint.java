package org.apache.xmlbeans;

import java.util.Map;

public interface SchemaIdentityConstraint extends SchemaComponent, SchemaAnnotated {
   int CC_KEY = 1;
   int CC_KEYREF = 2;
   int CC_UNIQUE = 3;

   String getSelector();

   Object getSelectorPath();

   String[] getFields();

   Object getFieldPath(int var1);

   Map getNSMap();

   int getConstraintCategory();

   SchemaIdentityConstraint getReferencedKey();

   Object getUserData();

   public static final class Ref extends SchemaComponent.Ref {
      public Ref() {
      }

      public Ref(SchemaIdentityConstraint idc) {
         super(idc);
      }

      public Ref(SchemaTypeSystem system, String handle) {
         super(system, handle);
      }

      public final int getComponentType() {
         return 5;
      }

      public final SchemaIdentityConstraint get() {
         return (SchemaIdentityConstraint)this.getComponent();
      }
   }
}
