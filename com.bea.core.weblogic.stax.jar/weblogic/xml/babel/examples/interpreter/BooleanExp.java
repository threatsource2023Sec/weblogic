package weblogic.xml.babel.examples.interpreter;

import org.xml.sax.SAXException;
import weblogicx.xml.stream.XMLEventStream;

abstract class BooleanExp {
   public abstract boolean evaluate(Context var1);

   public abstract BooleanExp replace(String var1, BooleanExp var2);

   public abstract BooleanExp copy();

   public abstract void read(XMLEventStream var1) throws SAXException;

   public abstract String toString();
}
