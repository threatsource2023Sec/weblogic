package weblogic.xml.stream;

/** @deprecated */
@Deprecated
public interface ChangePrefixMapping extends XMLEvent {
   String getOldNamespaceUri();

   String getNewNamespaceUri();

   String getPrefix();
}
