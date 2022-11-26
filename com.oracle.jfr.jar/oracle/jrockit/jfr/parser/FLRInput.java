package oracle.jrockit.jfr.parser;

import java.io.IOException;

interface FLRInput {
   byte get() throws IOException;

   void get(byte[] var1, int var2, int var3) throws IOException;

   void get(byte[] var1) throws IOException;

   char getChar() throws IOException;

   double getDouble() throws IOException;

   float getFloat() throws IOException;

   int getInt() throws IOException;

   long getLong() throws IOException;

   short getShort() throws IOException;

   long position() throws IOException;

   void position(long var1) throws IOException;

   long size() throws IOException;

   void close() throws IOException;
}
