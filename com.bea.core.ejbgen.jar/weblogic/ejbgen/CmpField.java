package weblogic.ejbgen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD})
public @interface CmpField {
   String column();

   Constants.Bool readOnlyInValueObject() default Constants.Bool.UNSPECIFIED;

   String tableName() default "UNSPECIFIED";

   String groupNames() default "UNSPECIFIED";

   String orderingNumber() default "0";

   Constants.Bool excludeFromValueObject() default Constants.Bool.UNSPECIFIED;

   ColumnType columnType() default CmpField.ColumnType.UNSPECIFIED;

   Constants.Bool primkeyField() default Constants.Bool.UNSPECIFIED;

   Constants.Bool dbmsDefaultValue() default Constants.Bool.UNSPECIFIED;

   public static enum ColumnType {
      UNSPECIFIED,
      CLOB,
      BLOB,
      LONG_STRING,
      SYBASE_BINARY;
   }
}
