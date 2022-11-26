package weblogic.ejbgen;

public @interface SqlShapeTable {
   String name();

   String ejbRelationshipRoleName() default "UNSPECIFIED";

   String[] columns();
}
