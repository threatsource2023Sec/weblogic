package weblogic.application;

import java.io.Serializable;

public final class Type implements Serializable {
   public static final Type WAR = new Type("WAR");
   public static final Type EAR = new Type("EAR");
   public static final Type EJB = new Type("EJB");
   public static final Type JAR = new Type("JAR");
   public static final Type GAR = new Type("GAR");
   private final String typeName;

   private Type(String typeName) {
      this.typeName = typeName;
   }

   public String toString() {
      return this.typeName;
   }
}
