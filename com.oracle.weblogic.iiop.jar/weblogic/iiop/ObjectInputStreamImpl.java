package weblogic.iiop;

import java.io.IOException;
import java.io.NotActiveException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.HashMap;
import weblogic.common.internal.ProxyClassResolver;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.Kernel;
import weblogic.utils.collections.NumericValueHashMap;
import weblogic.utils.collections.Stack;
import weblogic.utils.io.ObjectStreamClass;
import weblogic.utils.io.ObjectStreamField;

final class ObjectInputStreamImpl extends ObjectInputStream {
   private static final DebugLogger debugIIOPDetail = DebugLogger.getDebugLogger("DebugIIOPDetail");
   private IIOPInputStream delegate;
   private Object value;
   private ObjectStreamClass osc;
   private boolean dfwoCalled = false;
   private int streamFormatState;
   private static final int STREAM_VERSION_1 = 0;
   private static final int STREAM_VERSION_2 = 1;
   private static final int STREAM_VERSION_2_UNTERMINATED = 2;
   private GetFieldImpl getFields;
   private IIOPInputStream.Marker mark = new IIOPInputStream.Marker();
   private Stack streamStack;

   ObjectInputStreamImpl(IIOPInputStream in, Object value, ObjectStreamClass osc, boolean dfwo, byte sfv) throws IOException {
      this.delegate = in;
      this.value = value;
      this.osc = osc;
      this.streamFormatState = sfv > 1 ? 1 : 0;
      this.delegate.mark(0);
      this.delegate.mark(this.mark);
      this.dfwoCalled = dfwo;
   }

   void pushCurrent(Object value, ObjectStreamClass osc, boolean dfwo, byte sfv) {
      if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
         p("pushCurrent(" + osc + ")");
      }

      if (this.streamStack == null) {
         this.streamStack = new Stack();
      }

      this.streamStack.push(new StreamEntry());
      this.value = value;
      this.osc = osc;
      this.dfwoCalled = dfwo;
      this.getFields = null;
      this.streamFormatState = sfv > 1 ? 1 : 0;
      this.delegate.mark(this.mark);
   }

   private void popCurrent() {
      if (this.streamStack != null && this.streamStack.size() > 0) {
         StreamEntry entry = (StreamEntry)this.streamStack.pop();
         this.value = entry.value;
         this.osc = entry.osc;
         this.dfwoCalled = entry.dfwoCalled;
         this.getFields = entry.getFields;
         this.streamFormatState = entry.streamFormatState;
         this.mark.copyFrom(entry.mark);
      } else {
         this.value = null;
         this.osc = null;
      }

      if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
         p("popCurrent(" + this.osc + ")");
      }

   }

   public String readLine() throws IOException {
      throw new UnsupportedOperationException("readLine");
   }

   public int readInt() throws IOException {
      return this.delegate.readInt();
   }

   public String readUTF() throws IOException {
      return this.delegate.readUTF();
   }

   public long readLong() throws IOException {
      return this.delegate.readLong();
   }

   public byte readByte() throws IOException {
      return this.delegate.readByte();
   }

   public short readShort() throws IOException {
      return this.delegate.readShort();
   }

   public float readFloat() throws IOException {
      return this.delegate.readFloat();
   }

   public char readChar() throws IOException {
      return this.delegate.readChar();
   }

   public void readFully(byte[] byteArray) throws IOException {
      this.delegate.readFully(byteArray);
   }

   public void readFully(byte[] byteArray, int n, int n1) throws IOException {
      this.delegate.readFully(byteArray, n, n1);
   }

   public int read(byte[] buf, int off, int len) throws IOException {
      return this.delegate.read(buf, off, len);
   }

   public int read() throws IOException {
      return this.delegate.read();
   }

   public int skipBytes(int n) throws IOException {
      return this.delegate.skipBytes(n);
   }

   public boolean readBoolean() throws IOException {
      return this.delegate.readBoolean();
   }

   public int readUnsignedByte() throws IOException {
      return this.delegate.readUnsignedByte();
   }

   public int readUnsignedShort() throws IOException {
      return this.delegate.readUnsignedShort();
   }

   public double readDouble() throws IOException {
      return this.delegate.readDouble();
   }

   public Object readObjectOverride() throws IOException, ClassNotFoundException {
      return this.delegate.readObject();
   }

   public Object readUnshared() throws IOException, ClassNotFoundException {
      return this.delegate.readObject();
   }

   public void defaultReadObject() throws IOException, ClassNotFoundException {
      if (this.value == null) {
         throw new NotActiveException("Not in writeObject()");
      } else if (!this.dfwoCalled && this.osc.getFields().length != 0) {
         throw new StreamCorruptedException("defaultWriteObject was not called by the sender.");
      } else {
         this.osc.readFields(this.value, this.delegate);
         if (this.streamFormatState == 1 && this.delegate.startValue()) {
            this.streamFormatState = 2;
         }

      }
   }

   public ObjectInputStream.GetField readFields() throws IOException, ClassNotFoundException {
      if (this.value == null) {
         throw new NotActiveException("Not in writeObject()");
      } else {
         if (this.getFields == null) {
            this.getFields = new GetFieldImpl();
         }

         this.getFields.read();
         return this.getFields;
      }
   }

   protected Class resolveProxyClass(String[] interfaces) throws IOException, ClassNotFoundException {
      return ProxyClassResolver.resolveProxyClass(interfaces);
   }

   public void reset() throws IOException {
      this.delegate.resetTo(this.mark);
   }

   public void close() throws IOException {
      if (this.streamFormatState == 2) {
         this.delegate.end_value();
      }

      this.popCurrent();
   }

   private static void p(String msg) {
      System.out.println("<ObjectInputStreamImpl>: " + msg);
   }

   private final class GetFieldImpl extends ObjectInputStream.GetField {
      private final HashMap fieldMap;
      private final NumericValueHashMap primitiveFieldMap;

      private GetFieldImpl() {
         this.fieldMap = new HashMap();
         this.primitiveFieldMap = new NumericValueHashMap();
      }

      public boolean get(String name, boolean val) {
         return this.primitiveFieldMap.get(name, (long)(val ? 1 : 0)) == 1L;
      }

      public byte get(String name, byte val) {
         return (byte)((int)this.primitiveFieldMap.get(name, (long)val));
      }

      public char get(String name, char val) {
         return (char)((int)this.primitiveFieldMap.get(name, (long)val));
      }

      public short get(String name, short val) {
         return (short)((int)this.primitiveFieldMap.get(name, (long)val));
      }

      public int get(String name, int val) {
         return (int)this.primitiveFieldMap.get(name, (long)val);
      }

      public long get(String name, long val) {
         return this.primitiveFieldMap.get(name, val);
      }

      public float get(String name, float val) {
         return Float.intBitsToFloat((int)this.primitiveFieldMap.get(name, (long)Float.floatToIntBits(val)));
      }

      public double get(String name, double val) {
         return Double.longBitsToDouble(this.primitiveFieldMap.get(name, Double.doubleToLongBits(val)));
      }

      public Object get(String name, Object val) {
         return this.fieldMap.get(name);
      }

      public boolean defaulted(String name) {
         return !this.primitiveFieldMap.containsKey(name) && !this.fieldMap.containsKey(name);
      }

      public java.io.ObjectStreamClass getObjectStreamClass() {
         return ObjectInputStreamImpl.this.osc.getObjectStreamClass();
      }

      private void read() throws IOException, ClassNotFoundException {
         ObjectStreamField[] fields = ObjectInputStreamImpl.this.osc.getFields();

         for(int i = 0; i < fields.length; ++i) {
            ObjectStreamField f = fields[i];
            if (Kernel.DEBUG && ObjectInputStreamImpl.debugIIOPDetail.isDebugEnabled()) {
               ObjectInputStreamImpl.p("Reading: " + f);
            }

            switch (f.getTypeCode()) {
               case 'B':
               case 'Z':
                  this.primitiveFieldMap.put(f.getName(), (long)ObjectInputStreamImpl.this.delegate.readByte());
                  break;
               case 'C':
                  this.primitiveFieldMap.put(f.getName(), (long)ObjectInputStreamImpl.this.delegate.readChar());
                  break;
               case 'D':
               case 'J':
                  this.primitiveFieldMap.put(f.getName(), ObjectInputStreamImpl.this.delegate.readLong());
                  break;
               case 'E':
               case 'G':
               case 'H':
               case 'K':
               case 'M':
               case 'N':
               case 'O':
               case 'P':
               case 'Q':
               case 'R':
               case 'T':
               case 'U':
               case 'V':
               case 'W':
               case 'X':
               case 'Y':
               default:
                  throw new IOException("Bad typecode: " + f.getTypeCode());
               case 'F':
               case 'I':
                  this.primitiveFieldMap.put(f.getName(), (long)ObjectInputStreamImpl.this.delegate.readInt());
                  break;
               case 'L':
               case '[':
                  this.fieldMap.put(f.getName(), ObjectInputStreamImpl.this.delegate.readObject(f.getType()));
                  break;
               case 'S':
                  this.primitiveFieldMap.put(f.getName(), (long)ObjectInputStreamImpl.this.delegate.readShort());
            }
         }

         if (ObjectInputStreamImpl.this.streamFormatState == 1 && ObjectInputStreamImpl.this.delegate.startValue()) {
            ObjectInputStreamImpl.this.streamFormatState = 2;
         }

      }

      // $FF: synthetic method
      GetFieldImpl(Object x1) {
         this();
      }
   }

   private final class StreamEntry {
      private final Object value;
      private final ObjectStreamClass osc;
      private final boolean dfwoCalled;
      private final GetFieldImpl getFields;
      private final int streamFormatState;
      private final IIOPInputStream.Marker mark;

      private StreamEntry() {
         this.mark = new IIOPInputStream.Marker();
         this.value = ObjectInputStreamImpl.this.value;
         this.osc = ObjectInputStreamImpl.this.osc;
         this.dfwoCalled = ObjectInputStreamImpl.this.dfwoCalled;
         this.getFields = ObjectInputStreamImpl.this.getFields;
         this.streamFormatState = ObjectInputStreamImpl.this.streamFormatState;
         this.mark.copyFrom(ObjectInputStreamImpl.this.mark);
      }

      // $FF: synthetic method
      StreamEntry(Object x1) {
         this();
      }
   }
}
