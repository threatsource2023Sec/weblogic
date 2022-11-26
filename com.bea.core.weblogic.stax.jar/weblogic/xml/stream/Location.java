package weblogic.xml.stream;

/** @deprecated */
@Deprecated
public interface Location {
   int getColumnNumber();

   int getLineNumber();

   String getPublicId();

   String getSystemId();
}
