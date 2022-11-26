package org.jboss.weld.resources.spi;

public interface ClassFileInfo {
   String getClassName();

   String getSuperclassName();

   boolean isAnnotationDeclared(Class var1);

   boolean containsAnnotation(Class var1);

   int getModifiers();

   boolean hasCdiConstructor();

   boolean isAssignableFrom(Class var1);

   boolean isAssignableTo(Class var1);

   boolean isVetoed();

   default boolean isTopLevelClass() {
      return this.getNestingType().equals(ClassFileInfo.NestingType.TOP_LEVEL);
   }

   NestingType getNestingType();

   public static enum NestingType {
      TOP_LEVEL,
      NESTED_INNER,
      NESTED_LOCAL,
      NESTED_ANONYMOUS,
      NESTED_STATIC;
   }
}
