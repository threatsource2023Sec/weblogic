package weblogic.descriptor.beangen;

import com.bea.util.jam.JClass;

public class BeanField extends BeanElement implements Comparable {
   public static BeanField NULL_FIELD = new BeanField();
   private JClass clazz;
   private String type;
   private String name;
   private String initializer;
   private int idx;
   private boolean isTransient;

   private BeanField() {
      this.idx = -1;
   }

   public BeanField(JClass clazz, String name, String initializer) {
      this();
      this.clazz = clazz;
      this.type = Context.get().abbreviateClass(clazz.getQualifiedName());
      this.name = name;
      this.initializer = initializer;
   }

   public String getDeclaration() {
      String declaration = "private " + (this.isTransient ? "transient " : "") + this.getType() + " " + this.getName();
      return this.initializer == null ? declaration + ";" : declaration + " = " + this.initializer + ";";
   }

   public String getName() {
      return this.name;
   }

   public String getType() {
      return this.type;
   }

   public boolean isArray() {
      return this.clazz.isArrayType();
   }

   public boolean isList() {
      return JClasses.LIST.isAssignableFrom(this.clazz);
   }

   public int compareTo(Object o) {
      return !(o instanceof BeanField) ? -1 : this.getName().compareTo(((BeanField)o).getName());
   }

   public String toString() {
      return this.getDeclaration();
   }

   public JClass getJClass() {
      return this.clazz;
   }

   public int getIndex() {
      return this.idx;
   }

   public void setIndex(int idx) {
      this.idx = idx;
   }

   public boolean isTransient() {
      return this.isTransient;
   }

   public void setTransient(boolean isTransient) {
      this.isTransient = isTransient;
   }
}
