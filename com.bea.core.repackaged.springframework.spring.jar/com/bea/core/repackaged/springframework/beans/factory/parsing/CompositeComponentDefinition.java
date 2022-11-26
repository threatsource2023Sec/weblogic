package com.bea.core.repackaged.springframework.beans.factory.parsing;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.ArrayList;
import java.util.List;

public class CompositeComponentDefinition extends AbstractComponentDefinition {
   private final String name;
   @Nullable
   private final Object source;
   private final List nestedComponents = new ArrayList();

   public CompositeComponentDefinition(String name, @Nullable Object source) {
      Assert.notNull(name, (String)"Name must not be null");
      this.name = name;
      this.source = source;
   }

   public String getName() {
      return this.name;
   }

   @Nullable
   public Object getSource() {
      return this.source;
   }

   public void addNestedComponent(ComponentDefinition component) {
      Assert.notNull(component, (String)"ComponentDefinition must not be null");
      this.nestedComponents.add(component);
   }

   public ComponentDefinition[] getNestedComponents() {
      return (ComponentDefinition[])this.nestedComponents.toArray(new ComponentDefinition[0]);
   }
}
