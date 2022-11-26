package weblogic.xml.stream;

/** @deprecated */
@Deprecated
public interface XMLName {
   String getNamespaceUri();

   String getLocalName();

   String getPrefix();

   String getQualifiedName();
}
