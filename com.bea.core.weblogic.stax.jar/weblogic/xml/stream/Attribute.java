package weblogic.xml.stream;

/** @deprecated */
@Deprecated
public interface Attribute {
   XMLName getName();

   String getValue();

   String getType();

   XMLName getSchemaType();
}
