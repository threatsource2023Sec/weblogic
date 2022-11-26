package weblogic.xml.stream;

/** @deprecated */
@Deprecated
public interface StartDocument extends XMLEvent {
   String getSystemId();

   String getCharacterEncodingScheme();

   boolean isStandalone();

   String getVersion();
}
