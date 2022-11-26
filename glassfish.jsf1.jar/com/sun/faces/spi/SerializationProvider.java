package com.sun.faces.spi;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public interface SerializationProvider {
   ObjectInputStream createObjectInputStream(InputStream var1) throws IOException;

   ObjectOutputStream createObjectOutputStream(OutputStream var1) throws IOException;
}
