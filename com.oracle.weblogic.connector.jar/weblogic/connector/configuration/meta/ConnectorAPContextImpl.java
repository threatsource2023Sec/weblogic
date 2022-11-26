package weblogic.connector.configuration.meta;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import weblogic.connector.utils.BaseValidationMessageImpl;
import weblogic.connector.utils.ConnectorAPContext;

public class ConnectorAPContextImpl extends BaseValidationMessageImpl implements ConnectorAPContext {
   private final Set metaTracker = new HashSet();
   private static final String PATH_FORMAT = "%1$s #+# %2$s";
   private static final int ORDER = 0;

   public ConnectorAPContextImpl() {
      super(ConnectorAPContext.NullContext);
   }

   public void readPath(String type, String... name) {
      this.metaTracker.add(this.genKey(type, name));
   }

   public boolean fromAnnotation(String name, String... type) {
      return this.metaTracker.contains(this.genKey(name, type));
   }

   private String genKey(String type, String... name) {
      return String.format("%1$s #+# %2$s", type, Arrays.toString(name));
   }

   public void warning(String message) {
      this.warning(message, 0);
   }

   public void error(String message) {
      this.error(this.getSubComponent(), this.getKey(), message, 0);
   }

   protected final String getSubComponent() {
      return "General";
   }

   protected final String getKey() {
      return "General";
   }

   public boolean annotationFound() {
      return true;
   }
}
