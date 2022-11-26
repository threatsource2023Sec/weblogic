package org.apache.xmlbeans;

public interface SchemaGlobalAttribute extends SchemaLocalAttribute, SchemaComponent {
   Ref getRef();

   public static final class Ref extends SchemaComponent.Ref {
      public Ref() {
      }

      public Ref(SchemaGlobalAttribute element) {
         super(element);
      }

      public Ref(SchemaTypeSystem system, String handle) {
         super(system, handle);
      }

      public final int getComponentType() {
         return 3;
      }

      public final SchemaGlobalAttribute get() {
         return (SchemaGlobalAttribute)this.getComponent();
      }
   }
}
