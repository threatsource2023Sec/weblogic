package weblogic.xml.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Validation {
   private static final boolean debug = false;
   private static final boolean verbose = false;
   private boolean isNullable = true;
   private List values = new ArrayList();
   private String methodName;

   public void setIsNullable(boolean val) {
      this.isNullable = val;
   }

   public boolean isNullable() {
      return this.isNullable;
   }

   public void addValidValue(String val) {
      this.values.add(val);
   }

   public void addValidValues(String[] vals) {
      this.values.addAll(Arrays.asList((Object[])vals));
   }

   public List getValidValues() {
      return this.values;
   }

   public void setMethodName(String val) {
      this.methodName = val;
   }

   public String getMethodName() {
      return this.methodName;
   }
}
