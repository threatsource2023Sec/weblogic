package weblogic.common;

import java.io.IOException;
import java.io.ObjectInput;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public interface WLObjectInput extends ObjectInput {
   Object readObjectWL() throws IOException, ClassNotFoundException;

   Object readObjectWLValidated(Class var1) throws IOException, ClassNotFoundException;

   String readString() throws IOException;

   Date readDate() throws IOException;

   ArrayList readArrayList() throws IOException, ClassNotFoundException;

   Properties readProperties() throws IOException;

   byte[] readBytes() throws IOException;

   Object[] readArrayOfObjects() throws IOException, ClassNotFoundException;

   /** @deprecated */
   @Deprecated
   String readAbbrevString() throws IOException;

   Object readImmutable() throws IOException, ClassNotFoundException;
}
