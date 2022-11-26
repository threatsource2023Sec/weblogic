package net.shibboleth.utilities.java.support.component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public final class ComponentSupport {
   private ComponentSupport() {
   }

   public static void destroy(@Nullable Object obj) {
      if (obj != null) {
         if (obj instanceof DestructableComponent) {
            DestructableComponent destructable = (DestructableComponent)obj;
            if (!destructable.isDestroyed()) {
               destructable.destroy();
            }
         }

      }
   }

   public static void initialize(@Nullable Object obj) throws ComponentInitializationException {
      if (obj != null) {
         if (obj instanceof InitializableComponent) {
            InitializableComponent initializable = (InitializableComponent)obj;
            if (!initializable.isInitialized()) {
               initializable.initialize();
            }
         }

      }
   }

   public static void ifDestroyedThrowDestroyedComponentException(@Nonnull DestructableComponent component) {
      Constraint.isNotNull(component, "Component cannot be null");
      if (component.isDestroyed()) {
         if (component instanceof IdentifiedComponent) {
            throw new DestroyedComponentException("Component '" + StringSupport.trimOrNull(((IdentifiedComponent)component).getId()) + "' has already been destroyed and can no longer be used.");
         } else {
            throw new DestroyedComponentException("Component has already been destroyed and can no longer be used");
         }
      }
   }

   public static void ifNotInitializedThrowUninitializedComponentException(@Nonnull InitializableComponent component) {
      Constraint.isNotNull(component, "Component cannot be null");
      if (!component.isInitialized()) {
         if (component instanceof IdentifiedComponent) {
            throw new UninitializedComponentException("Component '" + StringSupport.trimOrNull(((IdentifiedComponent)component).getId()) + "' has not yet been initialized and cannot be used.");
         } else {
            throw new UninitializedComponentException("Component has not yet been initialized and cannot be used.");
         }
      }
   }

   public static void ifInitializedThrowUnmodifiabledComponentException(@Nonnull InitializableComponent component) {
      Constraint.isNotNull(component, "Component cannot be null");
      if (component.isInitialized()) {
         if (component instanceof IdentifiedComponent) {
            throw new UnmodifiableComponentException("Component '" + StringSupport.trimOrNull(((IdentifiedComponent)component).getId()) + "' has already been initialized and can no longer be modified");
         } else {
            throw new UnmodifiableComponentException("Component has already been initialized and can no longer be modified");
         }
      }
   }
}
