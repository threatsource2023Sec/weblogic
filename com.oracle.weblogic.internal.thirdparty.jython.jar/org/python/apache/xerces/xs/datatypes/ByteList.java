package org.python.apache.xerces.xs.datatypes;

import java.util.List;
import org.python.apache.xerces.xs.XSException;

public interface ByteList extends List {
   int getLength();

   boolean contains(byte var1);

   byte item(int var1) throws XSException;

   byte[] toByteArray();
}
