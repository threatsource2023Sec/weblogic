package weblogic.xml.stream;

/** @deprecated */
@Deprecated
public interface StartPrefixMapping extends XMLEvent {
   String getNamespaceUri();

   String getPrefix();
}
