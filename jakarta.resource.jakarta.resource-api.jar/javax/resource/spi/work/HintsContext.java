package javax.resource.spi.work;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class HintsContext implements WorkContext {
   private static final long serialVersionUID = 7956353628297167255L;
   public static final String NAME_HINT = "javax.resource.Name";
   public static final String LONGRUNNING_HINT = "javax.resource.LongRunning";
   protected String description = "Hints Context";
   protected String name = "HintsContext";
   Map hints = new HashMap();

   public String getDescription() {
      return this.description;
   }

   public String getName() {
      return this.name;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setHint(String hintName, Serializable value) {
      this.hints.put(hintName, value);
   }

   public Map getHints() {
      return this.hints;
   }
}
