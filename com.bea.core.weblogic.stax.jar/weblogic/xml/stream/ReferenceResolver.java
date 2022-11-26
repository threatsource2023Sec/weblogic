package weblogic.xml.stream;

/** @deprecated */
@Deprecated
public interface ReferenceResolver {
   XMLInputStream resolve(String var1) throws XMLStreamException;

   String getId(String var1);
}
