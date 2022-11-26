package weblogic.diagnostics.harvester;

import weblogic.diagnostics.type.DiagnosticException;

/** @deprecated */
@Deprecated
public abstract class HarvesterException extends DiagnosticException {
   private static final long serialVersionUID = 6543121586699392533L;

   HarvesterException() {
   }

   HarvesterException(String msg) {
      super(msg);
   }

   HarvesterException(Throwable t) {
      super(t);
   }

   HarvesterException(String msg, Throwable t) {
      super(msg, t);
   }

   /** @deprecated */
   @Deprecated
   public static class HarvestingNotEnabled extends HarvesterException {
      private static final long serialVersionUID = -1627081411171866169L;

      public HarvestingNotEnabled(String msg) {
         super(msg);
      }
   }

   /** @deprecated */
   @Deprecated
   public static class AmbiguousTypeName extends HarvesterException {
      private static final long serialVersionUID = 2269840477482306971L;

      public AmbiguousTypeName(String msg) {
         super(msg);
      }
   }

   /** @deprecated */
   @Deprecated
   public static class AmbiguousInstanceName extends HarvesterException {
      private static final long serialVersionUID = -1321259366046733169L;

      public AmbiguousInstanceName(String msg) {
         super(msg);
      }
   }

   /** @deprecated */
   @Deprecated
   public static class TypeNotHarvestable extends HarvesterException {
      private static final long serialVersionUID = -3500880323895128895L;

      public TypeNotHarvestable(String typeName) {
         super(typeName);
      }

      public TypeNotHarvestable(String typeName, Throwable t) {
         super(typeName, t);
      }
   }

   /** @deprecated */
   @Deprecated
   public static class MissingConfigurationType extends HarvesterException {
      private static final long serialVersionUID = -8166118132211170437L;

      public MissingConfigurationType(String typeName) {
         super(typeName);
      }

      public MissingConfigurationType(String typeName, Throwable t) {
         super(typeName, t);
      }
   }

   /** @deprecated */
   @Deprecated
   public static class HarvestableTypesNotFoundException extends HarvesterException {
      private static final long serialVersionUID = -7333436748348616174L;

      public HarvestableTypesNotFoundException(String msg) {
         super(msg);
      }

      public HarvestableTypesNotFoundException(String msg, Throwable t) {
         super(msg, t);
      }
   }

   /** @deprecated */
   @Deprecated
   public static class HarvestableInstancesNotFoundException extends HarvesterException {
      private static final long serialVersionUID = -8456929703554392498L;

      public HarvestableInstancesNotFoundException(String msg) {
         super(msg);
      }
   }
}
