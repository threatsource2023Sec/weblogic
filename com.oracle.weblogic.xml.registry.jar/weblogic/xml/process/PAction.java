package weblogic.xml.process;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class PAction {
   private static final boolean debug = false;
   private static final boolean verbose = false;
   private static int nextId = 128;
   private String elementName;
   private Set paths = new HashSet();
   private boolean isStartAction = false;
   private Map bindings = new HashMap();
   private Validation validation;
   private String javaCode;
   private final int id;

   public PAction() {
      this.id = nextId++;
   }

   public void setElementName(String val) {
      this.elementName = val;
   }

   public String getElementName() {
      return this.elementName;
   }

   public void addPath(String val) {
      this.paths.add(val);
   }

   public void setPaths(String[] vals) {
      this.paths = new HashSet(Arrays.asList((Object[])vals));
   }

   public void removePath(String val) {
      this.paths.remove(val);
   }

   public String[] getPaths() {
      return (String[])((String[])this.paths.toArray(new String[0]));
   }

   public void setIsStartAction(boolean val) {
      this.isStartAction = val;
   }

   public boolean isStartAction() {
      return this.isStartAction;
   }

   public void addBinding(Binding b) throws BindingException {
      Binding replaced = (Binding)this.bindings.put(b.getVariableName(), b);
      if (replaced != null) {
         throw new BindingException("Duplicate binding specified for processing action:" + b);
      }
   }

   public Collection getBindings() {
      return this.bindings.values();
   }

   public Binding getBinding(String varName) {
      return (Binding)this.bindings.get(varName);
   }

   public void setValidation(Validation v) {
      this.validation = v;
   }

   public Validation getValidation() {
      return this.validation;
   }

   public void setJavaCode(String val) {
      this.javaCode = val;
   }

   public String getJavaCode() {
      return this.javaCode;
   }

   public int getId() {
      return this.id;
   }

   public String toString() {
      StringBuffer sbuf = new StringBuffer();
      sbuf.append("PAction[" + this.hashCode() + "](");
      Iterator i = this.paths.iterator();
      if (i.hasNext()) {
         sbuf.append((String)i.next());
      }

      while(i.hasNext()) {
         sbuf.append("," + (String)i.next());
      }

      sbuf.append(")");
      return sbuf.toString();
   }
}
