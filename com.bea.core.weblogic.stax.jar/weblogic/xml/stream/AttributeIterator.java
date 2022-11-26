package weblogic.xml.stream;

/** @deprecated */
@Deprecated
public interface AttributeIterator {
   Attribute next();

   boolean hasNext();

   Attribute peek();

   void skip();
}
