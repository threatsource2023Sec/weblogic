package weblogic.security.spi;

public interface SelfDescribingResource extends Resource {
   int UNDEFINED_FIELD_TYPE = 0;
   int NORMAL_FIELD_TYPE = 1;
   int PATH_FIELD_TYPE = 2;
   int LIST_FIELD_TYPE = 3;

   int getFieldType(String var1);

   int getRepeatingFieldIndex();

   int getRepeatingFieldTerminatingIndex();
}
