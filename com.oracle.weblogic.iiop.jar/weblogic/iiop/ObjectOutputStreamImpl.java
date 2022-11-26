package weblogic.iiop;

import java.io.IOException;
import java.io.NotActiveException;
import java.io.NotSerializableException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.Kernel;
import weblogic.utils.UnsyncStringBuffer;
import weblogic.utils.collections.NumericValueHashMap;
import weblogic.utils.collections.Stack;
import weblogic.utils.io.ObjectStreamClass;
import weblogic.utils.io.ObjectStreamField;

final class ObjectOutputStreamImpl extends ObjectOutputStream {
   private static final DebugLogger debugIIOPDetail = DebugLogger.getDebugLogger("DebugIIOPDetail");
   private final IIOPOutputStream delegate;
   private Object value;
   private ObjectStreamClass osc;
   private boolean dfwoHandled = false;
   private int streamFormatState;
   private static final int STREAM_VERSION_1 = 0;
   private static final int STREAM_VERSION_2 = 1;
   private static final int STREAM_VERSION_2_UNTERMINATED = 2;
   private PutFieldImpl putFields;
   private IIOPOutputStream.Marker mark = new IIOPOutputStream.Marker();
   private Stack streamStack;

   ObjectOutputStreamImpl(IIOPOutputStream out, Object value, ObjectStreamClass osc, byte sfv) throws IOException {
      this.delegate = out;
      this.value = value;
      this.osc = osc;
      this.streamFormatState = sfv > 1 ? 1 : 0;
      this.delegate.setMark(this.mark);
   }

   void pushCurrent(Object value, ObjectStreamClass osc, byte sfv) {
      if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
         p("pushCurrent(" + osc + ")");
      }

      if (this.streamStack == null) {
         this.streamStack = new Stack();
      }

      this.streamStack.push(new StreamEntry());
      this.value = value;
      this.osc = osc;
      this.dfwoHandled = false;
      this.putFields = null;
      this.streamFormatState = sfv > 1 ? 1 : 0;
      this.delegate.setMark(this.mark);
   }

   private void popCurrent() {
      if (this.streamStack != null && this.streamStack.size() > 0) {
         StreamEntry entry = (StreamEntry)this.streamStack.pop();
         this.value = entry.value;
         this.osc = entry.osc;
         this.dfwoHandled = entry.dfwoHandled;
         this.putFields = entry.putFields;
         this.streamFormatState = entry.streamFormatState;
         this.mark = entry.mark;
      } else {
         this.value = null;
         this.osc = null;
      }

      if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
         p("popCurrent(" + this.osc + ")");
      }

   }

   public void write(byte[] byteArray) throws IOException {
      this.handleOptionalData();
      this.delegate.write(byteArray);
   }

   public void write(byte[] byteArray, int n, int n1) throws IOException {
      this.handleOptionalData();
      this.delegate.write(byteArray, n, n1);
   }

   public void write(int n) throws IOException {
      this.handleOptionalData();
      this.delegate.write(n);
   }

   public void writeInt(int n) throws IOException {
      this.handleOptionalData();
      this.delegate.writeInt(n);
   }

   public void writeUTF(String string) throws IOException {
      this.handleOptionalData();
      this.delegate.writeUTF(string);
   }

   public void writeLong(long l) throws IOException {
      this.handleOptionalData();
      this.delegate.writeLong(l);
   }

   public void writeByte(int n) throws IOException {
      this.handleOptionalData();
      this.delegate.writeByte(n);
   }

   public void writeShort(int n) throws IOException {
      this.handleOptionalData();
      this.delegate.writeShort(n);
   }

   public void writeBytes(String string) throws IOException {
      this.handleOptionalData();
      this.delegate.writeBytes(string);
   }

   public void writeFloat(float f) throws IOException {
      this.handleOptionalData();
      this.delegate.writeFloat(f);
   }

   public void writeChar(int n) throws IOException {
      this.handleOptionalData();
      this.delegate.writeChar(n);
   }

   public void writeBoolean(boolean flag) throws IOException {
      this.handleOptionalData();
      this.delegate.writeBoolean(flag);
   }

   public void writeDouble(double d) throws IOException {
      this.handleOptionalData();
      this.delegate.writeDouble(d);
   }

   public void writeChars(String string) throws IOException {
      this.handleOptionalData();
      this.delegate.writeChars(string);
   }

   protected final void writeObjectOverride(Object obj) throws IOException {
      this.handleOptionalData();
      this.delegate.writeObject(obj);
   }

   public void writeUnshared(Object obj) throws IOException {
      this.handleOptionalData();
      this.delegate.writeObject(obj);
   }

   public void defaultWriteObject() throws IOException {
      if (this.value == null) {
         throw new NotActiveException("Not in writeObject()");
      } else if (this.dfwoHandled) {
         throw new IOException("Called defaultWriteObject()/writeFields() twice or after writing optional data.");
      } else {
         this.delegate.write_octet((byte)1);
         this.dfwoHandled = true;
         this.osc.writeFields(this.value, this.delegate);
      }
   }

   public ObjectOutputStream.PutField putFields() throws IOException {
      if (this.value == null) {
         throw new NotActiveException("Not in writeObject()");
      } else {
         if (this.putFields == null) {
            this.putFields = new PutFieldImpl();
         }

         return this.putFields;
      }
   }

   public void writeFields() throws IOException {
      if (this.value != null && this.putFields != null) {
         this.putFields.write();
      } else {
         throw new NotActiveException("Not in writeObject()");
      }
   }

   private void handleOptionalData() {
      if (!this.dfwoHandled) {
         if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
            p("handleOptionalData() at " + this.delegate.pos());
         }

         this.delegate.write_octet((byte)0);
         this.dfwoHandled = true;
      }

      if (this.streamFormatState == 1) {
         String repid = (new UnsyncStringBuffer("RMI:org.omg.custom.")).append(ValueHandlerImpl.getRepositoryID(this.osc.forClass()).substring(4)).toString();
         this.delegate.start_value(repid);
         this.streamFormatState = 2;
      }

   }

   public void reset() throws IOException {
      this.delegate.restoreMark(this.mark);
   }

   public void flush() throws IOException {
   }

   public void drain() throws IOException {
   }

   public void close() throws IOException {
      if (!this.dfwoHandled) {
         this.delegate.write_octet((byte)0);
      }

      if (this.streamFormatState == 1) {
         this.delegate.write_long(0);
      } else if (this.streamFormatState == 2) {
         this.delegate.end_value();
      }

      this.popCurrent();
   }

   private static void p(String msg) {
      System.out.println("<ObjectOutputStreamImpl>: " + msg);
   }

   private final class PutFieldImpl extends ObjectOutputStream.PutField {
      private final HashMap fieldMap;
      private final NumericValueHashMap primitiveFieldMap;

      private PutFieldImpl() {
         this.fieldMap = new HashMap();
         this.primitiveFieldMap = new NumericValueHashMap();
      }

      public void put(String name, boolean val) {
         this.primitiveFieldMap.put(name, (long)(val ? 1 : 0));
      }

      public void put(String name, byte val) {
         this.primitiveFieldMap.put(name, (long)val);
      }

      public void put(String name, char val) {
         this.primitiveFieldMap.put(name, (long)val);
      }

      public void put(String name, short val) {
         this.primitiveFieldMap.put(name, (long)val);
      }

      public void put(String name, int val) {
         this.primitiveFieldMap.put(name, (long)val);
      }

      public void put(String name, long val) {
         this.primitiveFieldMap.put(name, val);
      }

      public void put(String name, float val) {
         this.primitiveFieldMap.put(name, (long)Float.floatToIntBits(val));
      }

      public void put(String name, double val) {
         this.primitiveFieldMap.put(name, Double.doubleToLongBits(val));
      }

      public void put(String name, Object val) {
         this.fieldMap.put(name, val);
      }

      public void write(ObjectOutput out) throws IOException {
         throw new NotSerializableException("PutField.write() is not supported");
      }

      private void write() throws IOException {
         if (ObjectOutputStreamImpl.this.dfwoHandled) {
            throw new IOException("Called defaultWriteObject()/writeFields() twice or after writing optional data.");
         } else {
            if (Kernel.DEBUG && ObjectOutputStreamImpl.debugIIOPDetail.isDebugEnabled()) {
               ObjectOutputStreamImpl.p("PutField.write() writing defaultWriteObject at " + ObjectOutputStreamImpl.this.delegate.pos());
            }

            ObjectOutputStreamImpl.this.delegate.write_octet((byte)1);
            ObjectOutputStreamImpl.this.dfwoHandled = true;
            ObjectStreamField[] fields = ObjectOutputStreamImpl.this.osc.getFields();

            for(int i = 0; i < fields.length; ++i) {
               ObjectStreamField f = fields[i];
               if (Kernel.DEBUG && ObjectOutputStreamImpl.debugIIOPDetail.isDebugEnabled()) {
                  ObjectOutputStreamImpl.p("Writing: " + f);
               }

               switch (f.getTypeCode()) {
                  case 'B':
                  case 'Z':
                     ObjectOutputStreamImpl.this.delegate.writeByte((byte)((int)this.primitiveFieldMap.get(f.getName())));
                     break;
                  case 'C':
                     ObjectOutputStreamImpl.this.delegate.writeChar((char)((int)this.primitiveFieldMap.get(f.getName())));
                     break;
                  case 'D':
                  case 'J':
                     ObjectOutputStreamImpl.this.delegate.writeLong(this.primitiveFieldMap.get(f.getName()));
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
                     ObjectOutputStreamImpl.this.delegate.writeInt((int)this.primitiveFieldMap.get(f.getName()));
                     break;
                  case 'L':
                  case '[':
                     ObjectOutputStreamImpl.this.delegate.writeObject(this.fieldMap.get(f.getName()), f.getType());
                     break;
                  case 'S':
                     ObjectOutputStreamImpl.this.delegate.writeShort((short)((int)this.primitiveFieldMap.get(f.getName())));
               }
            }

         }
      }

      // $FF: synthetic method
      PutFieldImpl(Object x1) {
         this();
      }
   }

   private final class StreamEntry {
      private final Object value;
      private final ObjectStreamClass osc;
      private final boolean dfwoHandled;
      private final PutFieldImpl putFields;
      private final int streamFormatState;
      private final IIOPOutputStream.Marker mark;

      private StreamEntry() {
         this.mark = new IIOPOutputStream.Marker();
         this.value = ObjectOutputStreamImpl.this.value;
         this.osc = ObjectOutputStreamImpl.this.osc;
         this.dfwoHandled = ObjectOutputStreamImpl.this.dfwoHandled;
         this.putFields = ObjectOutputStreamImpl.this.putFields;
         this.streamFormatState = ObjectOutputStreamImpl.this.streamFormatState;
         this.mark.chunk = ObjectOutputStreamImpl.this.mark.chunk;
         this.mark.pos = ObjectOutputStreamImpl.this.mark.pos;
      }

      // $FF: synthetic method
      StreamEntry(Object x1) {
         this();
      }
   }
}
