package weblogic.iiop;

import java.io.EOFException;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.util.HashMap;
import java.util.Map;
import org.omg.CORBA.Any;
import org.omg.CORBA.BooleanSeqHolder;
import org.omg.CORBA.CODESET_INCOMPATIBLE;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Principal;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypeCodePackage.BadKind;
import org.omg.CORBA.portable.BoxedValueHelper;
import org.omg.CORBA.portable.CustomValue;
import org.omg.CORBA.portable.IDLEntity;
import org.omg.CORBA.portable.IndirectionException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.StreamableValue;
import org.omg.CORBA.portable.ValueBase;
import org.omg.CORBA.portable.ValueFactory;
import org.omg.CORBA.portable.ValueInputStream;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.corba.idl.AnyImpl;
import weblogic.corba.idl.TypeCodeImpl;
import weblogic.corba.repository.ReceivedClassesRepository;
import weblogic.corba.utils.ClassInfo;
import weblogic.corba.utils.CorbaUtils;
import weblogic.corba.utils.IndirectionHashtable;
import weblogic.corba.utils.MarshalledObject;
import weblogic.corba.utils.RemoteInfo;
import weblogic.corba.utils.RepositoryId;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.protocol.CodeSet;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.rmi.spi.MsgInput;
import weblogic.utils.Debug;
import weblogic.utils.collections.Pool;
import weblogic.utils.collections.StackPool;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ObjectInput;
import weblogic.utils.io.ObjectStreamClass;
import weblogic.utils.io.StringInput;

public final class IIOPInputStream extends CorbaInputStream implements PeerInfoable, StringInput, ObjectInput, MsgInput, ValueInputStream {
   private static final int NULL_TAG = 0;
   private static final int GUESSED_BYTES_PER_VALUE = 128;
   boolean DEBUG;
   private static final int TOP_LEVEL_END_TAG = -1;
   private boolean SUPPORT_JDK_13_CHUNKING;
   private int minorVersion;
   private ReceivedClassesRepository repository;
   private static final String READ_METHOD = "read";
   private static final Class READ_METHOD_ARG;
   private static final int VT_INDIRECTION = -1;
   private FragmentedInputStream rawInput;
   private ValueChunkState chunkingState;
   private boolean littleEndian;
   private int needLongAlignment;
   private boolean needEightByteAlignment;
   private boolean secure;
   private ORB orb;
   private static final boolean ASSERT = false;
   private final EndPoint endPoint;
   private int char_codeset;
   private int wchar_codeset;
   private java.io.ObjectInput oinput;
   private ObjectInputStream objectStream;
   private String possibleCodebase;
   private int nestingLevel;
   private Map indirectionMap;
   private IndirectionHashtable tcIndirections;
   private int tcNestingLevel;
   private IIOPInputStream parentStream;
   private static final int MAX_ENCAP_SIZE = 67108864;
   private static final int MAX_STRING_SIZE = 524288;
   private static final int MAP_POOL_SIZE = 1024;
   private static final Pool mapPool;
   private Marker marker;
   private IndirectionHashtable encapsulations;

   private static IndirectionHashtable getHashMap() {
      IndirectionHashtable map = (IndirectionHashtable)mapPool.remove();
      if (map == null) {
         map = new IndirectionHashtable();
      }

      return map;
   }

   private static void releaseHashMap(IndirectionHashtable map) {
      if (map != null) {
         map.clear();
         mapPool.add(map);
      }

   }

   public IIOPInputStream(Chunk head, boolean isSecure, EndPoint endPoint) {
      this.DEBUG = false;
      this.SUPPORT_JDK_13_CHUNKING = false;
      this.minorVersion = 2;
      this.rawInput = new FragmentedInputStream();
      this.chunkingState = new ValueChunkState();
      this.needLongAlignment = 0;
      this.needEightByteAlignment = false;
      this.secure = false;
      this.orb = null;
      this.nestingLevel = 0;
      this.indirectionMap = null;
      this.tcIndirections = null;
      this.tcNestingLevel = 0;
      this.marker = new Marker();
      this.encapsulations = null;
      if (this.DEBUG) {
         p("Starting new message");
      }

      this.rawInput = new FragmentedInputStream(head);
      this.endPoint = endPoint;
      this.createIndirectionsMap(this.rawInput.available() / 128);
      if (endPoint != null) {
         this.wchar_codeset = endPoint.getWcharCodeSet();
         this.char_codeset = endPoint.getCharCodeSet();
      } else {
         this.char_codeset = CodeSet.getDefaultCharCodeSet();
         this.wchar_codeset = CodeSet.getDefaultWcharCodeSet();
      }

      this.secure = isSecure;
   }

   public IIOPInputStream(byte[] buf, EndPoint endPoint) {
      this((Chunk)null, false, endPoint);
      this.rawInput = new FragmentedInputStream(buf);
      this.SUPPORT_JDK_13_CHUNKING = false;
      this.char_codeset = CodeSet.getDefaultCharCodeSet();
      this.wchar_codeset = CodeSet.getDefaultWcharCodeSet();
   }

   public IIOPInputStream(byte[] buf) {
      this(buf, (EndPoint)null);
   }

   public IIOPInputStream(IIOPInputStream in) {
      this((Chunk)null, false, in.endPoint);
      this.parentStream = in;
      int len = in.peek_long();
      long handle = in.startEncapsulation(false);
      this.SUPPORT_JDK_13_CHUNKING = in.SUPPORT_JDK_13_CHUNKING;
      this.nestingLevel = in.nestingLevel;
      Chunk firstChunk = null;

      int copyLen;
      for(Chunk chunk = null; len > 0; chunk.end = copyLen) {
         Chunk tmp = chunk;
         chunk = Chunk.getChunk();
         if (tmp != null) {
            tmp.next = chunk;
         } else {
            firstChunk = chunk;
         }

         copyLen = min(chunk.buf.length, len);
         in.read_octet_array(chunk.buf, 0, copyLen);
         len -= copyLen;
      }

      this.rawInput = new FragmentedInputStream(firstChunk);
      in.endEncapsulation(handle);
      this.consumeEndian();
   }

   final void setSupportsJDK13Chunking(boolean b) {
      this.SUPPORT_JDK_13_CHUNKING = b;
   }

   public void setPossibleCodebase(String possibleCodebase) {
      this.possibleCodebase = possibleCodebase;
   }

   private String getPossibleCodebase() {
      if (this.possibleCodebase != null) {
         return this.possibleCodebase;
      } else {
         return this.parentStream != null ? this.parentStream.getPossibleCodebase() : null;
      }
   }

   public int getMinorVersion() {
      return this.endPoint == null || this.nestingLevel != 0 && !this.SUPPORT_JDK_13_CHUNKING ? 2 : this.minorVersion;
   }

   public void setMinorVersion(int theMinorVersion) {
      this.minorVersion = theMinorVersion;
   }

   public PeerInfo getPeerInfo() {
      return this.endPoint != null && this.endPoint.getPeerInfo() != null ? this.endPoint.getPeerInfo() : PeerInfo.FOREIGN;
   }

   private void createIndirectionsMap(int initialSize) {
      this.indirectionMap = new HashMap(initialSize);
   }

   private void releaseIndirectionsMap() {
      if (this.indirectionMap != null) {
         this.indirectionMap.clear();
      }

      this.indirectionMap = null;
   }

   private Object getIndirection(int i) {
      return this.indirectionMap == null ? null : this.indirectionMap.get(i);
   }

   private void addIndirection(int pos, Object value) {
      this.indirectionMap.put(pos, value);
   }

   public EndPoint getEndPoint() {
      return this.endPoint;
   }

   public void addChunks(CorbaInputStream toadd) {
      this.rawInput.addChunks(((IIOPInputStream)toadd).rawInput);
   }

   public void setCodeSets(int char_codeset, int wchar_codeset) {
      this.char_codeset = char_codeset;
      this.wchar_codeset = wchar_codeset;
   }

   public int getCharCodeset() {
      return this.char_codeset;
   }

   public int getWcharCodeset() {
      return this.wchar_codeset;
   }

   private int getEffectiveWcharCodeSet() {
      return this.nestingLevel > 0 && !this.SUPPORT_JDK_13_CHUNKING ? 65801 : this.wchar_codeset;
   }

   public boolean isSecure() {
      return this.secure;
   }

   public java.io.ObjectInput getObjectInput(boolean idl) {
      if (!idl) {
         return this;
      } else {
         if (this.oinput == null) {
            this.oinput = new IDLMsgInput(this);
         }

         return this.oinput;
      }
   }

   public ObjectInputStream getObjectInputStream(Object value, ObjectStreamClass osc, boolean dfwo, byte sfv) throws IOException {
      if (this.objectStream == null) {
         this.objectStream = new ObjectInputStreamImpl(this, value, osc, dfwo, sfv);
      } else {
         ((ObjectInputStreamImpl)this.objectStream).pushCurrent(value, osc, dfwo, sfv);
      }

      return this.objectStream;
   }

   public boolean consumeEndian() {
      boolean old = this.littleEndian;
      this.littleEndian = (this.read_octet() & 1) == 1;
      return old;
   }

   public void setLittleEndian(boolean littleEndian) {
      this.littleEndian = littleEndian;
   }

   void setORB(ORB orb) {
      this.orb = orb;
   }

   public final byte read_octet() {
      this.checkForRoomInChunk(1);
      return (byte)(this.rawInput.read() & 255);
   }

   boolean eof() {
      return this.rawInput.eof() || this.chunkingState.isAtEndOfChunk();
   }

   private static int min(int a, int b) {
      return a <= b ? a : b;
   }

   public final void read_octet_array(byte[] b, int off, int len) {
      if (len != 0) {
         this.checkForRoomInChunk(len);
         this.rawInput.read(b, off, len);
      }
   }

   public byte[] read_octet_sequence() {
      return this.read_octet_sequence(67108864);
   }

   public byte[] read_octet_sequence(int maxSequenceSize) {
      byte[] seq = null;
      int len = this.read_long();
      if (len > maxSequenceSize) {
         throw new MARSHAL("Stream corrupted at " + this.pos() + ": tried to read octet sequence of length " + Integer.toHexString(len));
      } else {
         if (len > 0) {
            seq = new byte[len];
            this.read_octet_array(seq, 0, len);
         }

         return seq;
      }
   }

   public boolean read_boolean() {
      return this.read_octet() != 0;
   }

   public void read_boolean_array(boolean[] value, int off, int len) {
      if (value == null) {
         throw new MARSHAL("null array as parameter to read_boolean_array");
      } else {
         for(int i = 0; i < len; ++i) {
            value[i + off] = this.read_boolean();
         }

      }
   }

   public void read_boolean_array(BooleanSeqHolder seq, int off, int len) {
      if (seq == null) {
         throw new MARSHAL("null BooleanSeqHolder as parameter to read_boolean_array");
      } else {
         this.read_boolean_array(seq.value, off, len);
      }
   }

   private int getAlignmentIncrement(int alignSize) {
      if (this.needEightByteAlignment) {
         this.needEightByteAlignment = false;
         alignSize = 8;
      }

      int alignPos = this.rawInput.pos() + this.needLongAlignment;
      int remainder = alignPos % alignSize;
      return remainder == 0 ? 0 : alignSize - remainder;
   }

   public final void setNeedEightByteAlignment() {
      this.needEightByteAlignment = true;
   }

   public void mark(int readLimit) {
      this.rawInput.mark(readLimit);
      this.marker.mark(this);
   }

   public boolean markSupported() {
      return true;
   }

   public void reset() {
      this.rawInput.reset();
      this.marker.reset(this);
   }

   private void clearMark() {
      this.rawInput.clearMark();
   }

   public void mark(Marker marker) {
      marker.markAt(this);
   }

   void resetTo(Marker marker) {
      marker.resetTo(this);
   }

   private ValueChunkState cloneChunkState() {
      return new ValueChunkState(this.chunkingState);
   }

   public long skip(long len) {
      return this.rawInput.skip(len);
   }

   public final int bytesLeft(long handle) {
      handle &= 536870911L;
      return handle > (long)this.pos() ? (int)(handle - (long)this.pos()) : 0;
   }

   public final int pos() {
      return this.rawInput.pos();
   }

   public void close() {
      this.releaseIndirectionsMap();
      releaseHashMap(this.tcIndirections);
      this.tcIndirections = null;
      releaseHashMap(this.encapsulations);
      this.encapsulations = null;
      this.rawInput.close();
      this.possibleCodebase = null;
      this.parentStream = null;
   }

   public short read_short() {
      this.alignAndCheck(2, 2);
      int b1 = this.rawInput.read() & 255;
      int b2 = this.rawInput.read() & 255;
      return this.littleEndian ? (short)(b2 << 8 | b1) : (short)(b1 << 8 | b2);
   }

   public void read_short_array(short[] value, int off, int len) {
      if (value == null) {
         throw new MARSHAL("null array as parameter to read_short_array");
      } else {
         for(int i = 0; i < len; ++i) {
            value[i + off] = this.read_short();
         }

      }
   }

   public short read_ushort() {
      return this.read_short();
   }

   public int read_unsigned_short() {
      this.alignAndCheck(2, 2);
      int b1 = this.rawInput.read() & 255;
      int b2 = this.rawInput.read() & 255;
      int s;
      if (this.littleEndian) {
         s = b2 << 8 | b1;
      } else {
         s = b1 << 8 | b2;
      }

      return s;
   }

   public final int peek_long() {
      return this.rawInput.peekLong(this.getAlignmentIncrement(4), this.littleEndian);
   }

   private int readProtocolLong() {
      int offset = this.getAlignmentIncrement(4);
      this.rawInput.skip((long)offset);
      return this.rawInput.readLong(this.littleEndian);
   }

   public final int read_long() {
      this.alignAndCheck(4, 4);
      return this.rawInput.readLong(this.littleEndian);
   }

   private void alignAndCheck(int alignment, int bytesToRead) {
      this.chunkingState.assertNotAtEndOfChunk();
      int alignIncr = this.getAlignmentIncrement(alignment);
      this.chunkingState.assertMayReadBytes(bytesToRead + alignIncr);
      this.skip((long)alignIncr);
   }

   private void checkForRoomInChunk(int bytesToRead) {
      if (this.needEightByteAlignment) {
         this.alignAndCheck(1, bytesToRead);
      } else {
         this.chunkingState.assertNotAtEndOfChunk();
         this.chunkingState.assertMayReadBytes(bytesToRead);
      }

   }

   public void read_long_array(int[] value, int off, int len) {
      if (value == null) {
         throw new MARSHAL("null array as parameter to read_long_array");
      } else {
         this.alignAndCheck(4, 4 * len);

         for(int i = 0; i < len; ++i) {
            value[i + off] = this.rawInput.readLong(this.littleEndian);
         }

      }
   }

   public final int read_ulong() {
      return this.read_long();
   }

   public void read_ulong_array(int[] value, int off, int len) {
      if (value == null) {
         throw new MARSHAL("null array as parameter to read_ulong_array");
      } else {
         for(int i = 0; i < len; ++i) {
            value[i + off] = this.read_ulong();
         }

      }
   }

   public final long read_longlong() {
      this.alignAndCheck(8, 8);
      return this.rawInput.readLongLong(this.littleEndian);
   }

   public void read_longlong_array(long[] value, int off, int len) {
      if (value == null) {
         throw new MARSHAL("null array as parameter to read_longlong_array");
      } else {
         for(int i = 0; i < len; ++i) {
            value[i + off] = this.read_longlong();
         }

      }
   }

   public long read_ulonglong() {
      return this.read_longlong();
   }

   public void read_ulonglong_array(long[] value, int off, int len) {
      this.read_longlong_array(value, off, len);
   }

   public float read_float() {
      return Float.intBitsToFloat(this.read_long());
   }

   public void read_float_array(float[] value, int off, int len) {
      if (value == null) {
         throw new MARSHAL("null array as parameter to read_float_array");
      } else {
         for(int i = 0; i < len; ++i) {
            value[i + off] = this.read_float();
         }

      }
   }

   public double read_double() {
      return Double.longBitsToDouble(this.read_longlong());
   }

   public void read_double_array(double[] value, int off, int len) {
      if (value == null) {
         throw new MARSHAL("null array as parameter to read_double_array");
      } else {
         for(int i = 0; i < len; ++i) {
            value[i + off] = this.read_double();
         }

      }
   }

   public char read_char() {
      return (char)(this.read_octet() & 255);
   }

   public void read_char_array(char[] value, int off, int len) {
      if (value == null) {
         throw new MARSHAL("null array as parameter to read_char_array");
      } else {
         for(int i = 0; i < len; ++i) {
            value[i + off] = this.read_char();
         }

      }
   }

   public char read_wchar() {
      byte b1 = 0;
      byte b2 = 0;
      int wchar = 0;
      boolean isLittleEndian = this.littleEndian;
      switch (this.getEffectiveWcharCodeSet()) {
         case 65792:
            switch (this.getMinorVersion()) {
               case 0:
               case 1:
                  this.alignAndCheck(2, 2);
                  b1 = this.read_octet();
                  b2 = this.read_octet();
                  break;
               case 2:
                  this.read_octet();
                  b1 = this.read_octet();
                  b2 = this.read_octet();
            }

            if (isLittleEndian) {
               wchar = ((b2 & 255) << 8) + (b1 & 255);
            } else {
               wchar = ((b1 & 255) << 8) + (b2 & 255);
            }
            break;
         case 65801:
            switch (this.getMinorVersion()) {
               case 0:
               case 1:
                  this.alignAndCheck(2, 2);
                  b1 = this.read_octet();
                  b2 = this.read_octet();
                  break;
               case 2:
                  this.read_octet();
                  b1 = this.read_octet();
                  b2 = this.read_octet();
                  isLittleEndian = false;
                  if (b1 == -1 && b2 == -2) {
                     b1 = this.read_octet();
                     b2 = this.read_octet();
                     isLittleEndian = true;
                  } else if (b1 == -2 && b2 == -1) {
                     b1 = this.read_octet();
                     b2 = this.read_octet();
                  }
            }

            if (isLittleEndian) {
               wchar = ((b2 & 255) << 8) + (b1 & 255);
            } else {
               wchar = ((b1 & 255) << 8) + (b2 & 255);
            }
            break;
         case 83951617:
            wchar = this.read_UTF8wchar();
      }

      return (char)wchar;
   }

   public int read() {
      return this.rawInput.eof() ? -1 : this.read_octet() & 255;
   }

   public ORB orb() {
      return this.orb;
   }

   public void read_wchar_array(char[] value, int off, int len) {
      if (value == null) {
         throw new MARSHAL("null array as parameter to read_wchar_array");
      } else {
         for(int i = 0; i < len; ++i) {
            value[i + off] = this.read_wchar();
         }

      }
   }

   public org.omg.CORBA.Object read_Object(Class stubClass) {
      IOR ior = new IOR(this);
      if (ior.isNull()) {
         return null;
      } else {
         if (stubClass == null && ior.getTypeId().isIDLType()) {
            RemoteInfo rinfo = RemoteInfo.findRemoteInfo(ior.getTypeId(), ior.getCodebase());
            if (rinfo != null) {
               Class c = rinfo.getTheClass();
               if (c != null) {
                  stubClass = Utils.getStubFromClass(c);
               }
            }
         }

         try {
            if (ior.getTypeId().isIDLType()) {
               try {
                  return IIOPReplacer.createCORBAStub(ior, stubClass == null ? null : Utils.getClassFromStub(stubClass), stubClass);
               } catch (InstantiationException var5) {
                  return InvocationHandlerFactory.makeInvocationHandler(ior, stubClass);
               }
            } else {
               return InvocationHandlerFactory.makeInvocationHandler(ior, stubClass);
            }
         } catch (IOException var6) {
            throw new MARSHAL("IOException reading CORBA object " + var6.getMessage());
         } catch (IllegalAccessException var7) {
            throw new MARSHAL("IllegalAccessException reading CORBA object " + var7.getMessage());
         }
      }
   }

   public org.omg.CORBA.Object read_Object() {
      return this.read_Object((Class)null);
   }

   private char read_UTF8wchar() {
      if (this.getMinorVersion() >= 2) {
         this.read_octet();
      }

      return this.readUTF8wchar();
   }

   private char readUTF8wchar() {
      int c = this.read_octet() & 255;
      if ((c & 128) != 0) {
         int c2;
         if ((c & 224) == 192) {
            c2 = this.read_octet() & 255;
            c = ((c & 31) << 6) + (c2 & 63);
         } else {
            c2 = this.read_octet() & 255;
            int c3 = this.read_octet() & 255;
            c = ((c & 15) << 12) + ((c2 & 63) << 6) + (c3 & 63);
         }
      }

      return (char)c;
   }

   private String readUTF8String(int len) {
      this.checkForRoomInChunk(len);
      return this.rawInput.readUTF8String(len);
   }

   private String readUTF16String(int len) {
      if (len < 2) {
         return "";
      } else if (len % 2 != 0) {
         throw new MARSHAL("Trying to read UTF16 wstring with illegal odd length " + len);
      } else {
         this.checkForRoomInChunk(len);
         byte[] bytes = new byte[len];
         this.rawInput.read(bytes, 0, len);
         return this.decodeUTF16(bytes);
      }
   }

   private String decodeUTF16(byte[] bytes) {
      StringBuilder result = new StringBuilder();
      int index = 0;
      int b0 = bytes[index++] & 255;
      int b1 = bytes[index++] & 255;
      boolean littleEndian = this.littleEndian;
      if (b0 == 255 && b1 == 254) {
         littleEndian = true;
      } else if (b0 == 254 && b1 == 255) {
         littleEndian = false;
      } else if (littleEndian) {
         result.append((char)(b1 << 8 | b0));
      } else {
         result.append((char)(b0 << 8 | b1));
      }

      if (littleEndian) {
         while(index < bytes.length) {
            b0 = bytes[index++] & 255;
            b1 = bytes[index++] & 255;
            result.append((char)(b1 << 8 | b0));
         }
      } else {
         while(index < bytes.length) {
            b0 = bytes[index++] & 255;
            b1 = bytes[index++] & 255;
            result.append((char)(b0 << 8 | b1));
         }
      }

      return result.toString();
   }

   public final String read_wstring() {
      int len = this.read_ulong();
      if (len == 0) {
         return "";
      } else if (len > 67108864) {
         throw new MARSHAL("Stream corrupted at " + this.pos() + ": tried to read wstring of length " + Integer.toHexString(len));
      } else if (this.getMinorVersion() < 2) {
         return GIOP10Helper.read_wstring(this, this.getEffectiveWcharCodeSet(), this.littleEndian, len);
      } else {
         String ret;
         switch (this.getEffectiveWcharCodeSet()) {
            case 65792:
            case 65801:
               ret = this.readUTF16String(len);
               break;
            case 83951617:
               ret = this.readUTF8String(len);
               break;
            default:
               throw new CODESET_INCOMPATIBLE("Unsupported codeset: " + Integer.toHexString(this.getEffectiveWcharCodeSet()));
         }

         return ret;
      }
   }

   public void read_ushort_array(short[] value, int off, int len) {
      if (value == null) {
         throw new MARSHAL("null array as parameter to read_ushort_array");
      } else {
         for(int i = off; i < len; ++i) {
            value[i] = this.read_ushort();
         }

      }
   }

   public final Any read_any() {
      return this.read_any(this.read_TypeCode());
   }

   public final Any read_any(TypeCode type) {
      assert type != null : "read_any invoked with null type";

      AnyImpl any = new AnyImpl();
      any.type(type);
      any.read_value(this, any.type());
      return any;
   }

   public final void read_any(Any any, TypeCode type) {
      assert any != null : "read_any invoked with null Any object";

      if (any instanceof AnyImpl) {
         any.type(type);
         any.read_value(this, type);
      } else {
         Any anyimpl = this.read_any(type);
         OutputStream out = any.create_output_stream();
         anyimpl.write_value(out);
         any.read_value(out.create_input_stream(), any.type());
      }

   }

   public Object readAny() {
      if (this.DEBUG) {
         p("reading Any at " + this.pos());
      }

      TypeCode type = this.read_TypeCode();
      int tc = type.kind().value();
      if (tc == 21) {
         try {
            tc = type.content_type().kind().value();
            type = type.content_type();
         } catch (BadKind var11) {
            throw new MARSHAL("IOException reading Any " + var11.getMessage());
         }
      }

      RepositoryId repid = TypeCodeImpl.getRepositoryId(type);
      Object obj;
      switch (tc) {
         case 14:
            IOR ior = new IOR(this);

            try {
               obj = IIOPReplacer.resolveObject(ior);
               break;
            } catch (IOException var10) {
               throw new MARSHAL("IOException reading Any " + var10.getMessage());
            }
         case 29:
            obj = this.read_value(repid);
            break;
         case 30:
            obj = this.read_value(repid);
            break;
         case 32:
            boolean discrim = this.read_boolean();
            if (!discrim) {
               if (repid.equals((Object)RepositoryId.NULL)) {
                  obj = this.read_value();
               } else {
                  obj = this.read_value(repid);
               }
            } else {
               IOR ior2 = new IOR(this);

               try {
                  obj = IIOPReplacer.resolveObject(ior2);
               } catch (IOException var9) {
                  throw new MARSHAL("IOException reading Any " + var9.getMessage());
               }
            }
            break;
         default:
            throw new MARSHAL("Can't handle TypeCode: " + tc + " at pos: " + this.pos());
      }

      return obj;
   }

   public final long startEncapsulation() {
      return this.startEncapsulation(true);
   }

   private final long startEncapsulation(boolean consume) {
      int size = this.read_long();
      if (size <= 0) {
         return 0L;
      } else {
         ++this.nestingLevel;
         this.checkForRoomInChunk(size);
         int index = this.pos();
         long handle = (long)index;
         if (this.DEBUG) {
            p("startEncapsulation at " + index + " with length " + size + " in state " + this.chunkingState);
         }

         Debug.assertion((handle & -1073741824L) == 0L);
         if (this.littleEndian) {
            handle |= -2147483648L;
         }

         if (this.needLongAlignment > 0) {
            handle |= 1073741824L;
         }

         this.needLongAlignment = this.rawInput.pos() % 8 != 0 ? 4 : 0;
         if (!this.chunkingState.isChunking()) {
            handle += (long)size;
         } else {
            if (this.encapsulations == null) {
               this.encapsulations = getHashMap();
            }

            this.encapsulations.put(index, new EncapsulationWrapper(size, this.chunkingState));
            this.chunkingState = new ValueChunkState();
            handle |= 536870912L;
         }

         if (consume) {
            this.consumeEndian();
         }

         return handle;
      }
   }

   public void endEncapsulation(long handle) {
      if (handle != 0L) {
         --this.nestingLevel;
         this.littleEndian = (handle & -2147483648L) != 0L;
         this.needLongAlignment = (handle & 1073741824L) != 0L ? 4 : 0;
         boolean chunked = (handle & 536870912L) != 0L;
         handle &= 536870911L;
         if (!chunked) {
            this.chunkingState.clear();
         } else {
            EncapsulationWrapper encap = (EncapsulationWrapper)this.encapsulations.remove((int)handle);
            if (encap == null) {
               throw new MARSHAL("No encapsulation information at: " + this.pos());
            }

            if (this.DEBUG) {
               p("ending encapsulation " + handle + " at " + this.pos() + ", restored chunk state=" + encap.chunkState);
            }

            handle += (long)encap.encapLength;
            this.chunkingState.copyFrom(encap.chunkState);
         }

         if (handle > (long)this.pos()) {
            this.skip(handle - (long)this.pos());
         } else if (handle < (long)this.pos()) {
            throw new MARSHAL("read beyond encapsulation at position: " + this.pos());
         }
      }
   }

   public void start_value() {
      this.startValue();
   }

   boolean startValue() {
      int valueTag = this.chunkingState.readValueTag();
      if (this.DEBUG) {
         p("Starting value with tag " + Integer.toHexString(valueTag) + " at " + (this.pos() - 4) + " " + this.chunkingState);
      }

      if (valueTag == 0) {
         return false;
      } else if (valueTag == -1) {
         throw new MARSHAL("Illegal indirection for serial format version 2 data at " + this.pos());
      } else if ((valueTag & 6) != 2) {
         throw new MARSHAL("Illegal value tag: " + Integer.toHexString(valueTag) + " for serial format version 2 data at " + this.pos());
      } else {
         if (this.read_indirection() == null) {
            int ipos = this.pos();
            String repid = this.read_string();
            this.addIndirection(ipos, repid);
         }

         this.chunkingState.startValueProcessing(valueTag);
         return true;
      }
   }

   public void end_value() {
      if (this.DEBUG) {
         p("ending value at " + this.pos() + " " + this.chunkingState);
      }

      this.chunkingState.end_value();
   }

   public Serializable read_value() {
      return this.read_value((Class)null);
   }

   public Serializable read_value(BoxedValueHelper factory) {
      return this.read_value((Class)null);
   }

   public Serializable read_value(Class clz) {
      Class c = clz;
      if (this.rawInput.eof()) {
         return null;
      } else {
         int valueTag = this.chunkingState.readValueTag();
         if (this.DEBUG) {
            p("Starting value with tag " + Integer.toHexString(valueTag) + " at " + (this.pos() - 4) + " " + this.chunkingState);
         }

         if (valueTag == 0) {
            return null;
         } else if (valueTag == -1) {
            return this.getIndirectionValue(this.read_long());
         } else {
            int indirection = this.pos() - 4;
            String codebase = null;
            if (hasCodebase(valueTag) && (codebase = (String)this.read_indirection()) == null) {
               int ipos = this.pos();
               codebase = this.read_string();
               this.addIndirection(ipos, codebase);
            }

            if (codebase == null) {
               codebase = this.getPossibleCodebase();
            }

            ClassInfo cinfo = null;
            RepositoryId repid = null;
            switch (valueTag & 6) {
               case 0:
               default:
                  break;
               case 2:
                  cinfo = this.readIndirectingRepositoryId(codebase);
                  repid = cinfo.getRepositoryId();
                  break;
               case 6:
                  ClassInfo[] repids = this.readIndirectingRepositoryIdList(codebase);
                  cinfo = repids[0];
                  repid = cinfo.getRepositoryId();
            }

            this.chunkingState.startValueProcessing(valueTag);
            String name;
            if (repid != null && repid.isClassDesc()) {
               codebase = (String)this.read_value(String.class);
               String nrepid = (String)this.read_value(String.class);
               if (codebase != null && (codebase.startsWith("RMI:") || codebase.startsWith("IDL:"))) {
                  name = codebase;
                  codebase = nrepid;
                  nrepid = name;
               }

               Class ret = CorbaUtils.getClassFromID(new RepositoryId(nrepid), codebase);
               if (ret == null) {
                  throw new MARSHAL("Class not found: " + nrepid);
               } else {
                  this.addIndirection(indirection, ret);
                  this.end_value();
                  return ret;
               }
            } else {
               if (repid == null) {
                  cinfo = ClassInfo.findClassInfo(clz);
                  repid = cinfo.getRepositoryId();
               } else {
                  c = cinfo.forClass();
               }

               Object s;
               if (c != String.class && !repid.compareStrings(RepositoryId.STRING)) {
                  if (c != null && c.isEnum()) {
                     name = this.read_string();
                     s = Enum.valueOf(c, name);
                     this.addIndirection(indirection, s);
                  } else if (c != null && cinfo.isIDLEntity()) {
                     s = this.read_IDLValue(c);
                     this.addIndirection(indirection, s);
                  } else if (c != null && (c.isArray() || cinfo.getRepositoryId() == cinfo.getLocalRepositoryId()) && (Externalizable.class.isAssignableFrom(c) || ObjectStreamClass.supportsUnsafeSerialization())) {
                     try {
                        ObjectStreamClass osc = ObjectStreamClass.lookup(c);
                        s = (Serializable)ValueHandlerImpl.allocateValue(this, osc);
                        this.addIndirection(indirection, s);
                        Serializable news = (Serializable)ValueHandlerImpl.readValue(this, osc, s);
                        if (news != s) {
                           s = news;
                           this.addIndirection(indirection, news);
                        }
                     } catch (IOException | ClassNotFoundException var11) {
                        throw Utils.wrapMARSHALWithCause(var11);
                     }
                  } else {
                     s = ValueHandlerHolder.getValueHandler().readValue(this, indirection, c, repid.toString(), this.endPoint != null ? this.endPoint.getRemoteCodeBase() : null);
                     this.addIndirection(indirection, s);
                  }
               } else {
                  s = this.read_wstring();
                  this.addIndirection(indirection, s);
               }

               if (this.DEBUG) {
                  p("ending value at " + this.pos() + ", started at " + indirection + " " + this.chunkingState);
               }

               this.chunkingState.end_value();
               return (Serializable)s;
            }
         }
      }
   }

   private ReceivedClassesRepository getRepository() {
      this.repository = ReceivedClassesRepository.getRepository();
      return this.repository;
   }

   private Serializable getIndirectionValue(int indirection) {
      if (indirection >= -4) {
         throw new AssertionError("Bad indirection value " + indirection + " (" + Integer.toHexString(indirection) + ") at " + (this.pos() - 4));
      } else {
         indirection = indirection + this.pos() - 4;
         Debug.assertion(indirection > 0);
         Serializable s = (Serializable)this.getIndirection(indirection);
         if (s == null) {
            throw new IndirectionException(indirection);
         } else {
            return s;
         }
      }
   }

   private static boolean hasCodebase(int valueTag) {
      return (valueTag & 1) == 1;
   }

   private static boolean useChunking(int valueTag) {
      return (valueTag & 8) == 8;
   }

   public Serializable read_value(RepositoryId repid) {
      return this.read_value(this.getClassFor(repid));
   }

   private Class getClassFor(RepositoryId repid) {
      return repid == null ? null : this.getRepository().findClass(repid);
   }

   public Serializable read_value(String rep_id) {
      return rep_id != null && rep_id.length() > 0 ? this.read_value(new RepositoryId(rep_id)) : this.read_value((Class)null);
   }

   public Serializable read_value(Serializable s) {
      if (s instanceof StreamableValue) {
         StreamableValue sv = (StreamableValue)s;
         int ipos = this.pos();
         this.addIndirection(ipos, s);
         sv._read(this);
         return s;
      } else {
         throw Utils.wrapMARSHALWithCause(new NO_IMPLEMENT());
      }
   }

   private Serializable read_IDLValue(Class c) {
      try {
         if (CustomValue.class.isAssignableFrom(c)) {
            throw new MARSHAL("Custom marshalled valuetypes not supported");
         } else if (StreamableValue.class.isAssignableFrom(c)) {
            ValueFactory factory = (ValueFactory)Utils.getHelper(c, "DefaultFactory").newInstance();
            return factory.read_value(this);
         } else if (ValueBase.class.isAssignableFrom(c)) {
            BoxedValueHelper helper = (BoxedValueHelper)Utils.getHelper(c, "Helper").newInstance();
            return helper.read_value(this);
         } else {
            return this.read_IDLEntity(c);
         }
      } catch (IllegalAccessException | InstantiationException var3) {
         throw new MARSHAL(var3.getMessage());
      }
   }

   public IDLEntity read_IDLEntity(Class c) {
      return read_IDLEntity(this, c);
   }

   public static IDLEntity read_IDLEntity(InputStream is, Class c) {
      Class helper = Utils.getIDLHelper(c);
      if (helper == null) {
         throw new MARSHAL("Couldn't find helper for " + c.getName());
      } else {
         return (IDLEntity)readWithHelper(is, helper);
      }
   }

   public static Object readWithHelper(InputStream is, Class helper) {
      try {
         Method m = Utils.getDeclaredMethod(helper, "read", READ_METHOD_ARG);
         if (m == null) {
            throw new MARSHAL("No read method for " + helper.getName());
         } else {
            return m.invoke((Object)null, is);
         }
      } catch (IllegalAccessException var3) {
         throw Utils.wrapMARSHALWithCause(var3);
      } catch (InvocationTargetException var4) {
         throw Utils.wrapMARSHALWithCause(var4.getTargetException());
      }
   }

   public Object read_abstract_interface() {
      return this.read_boolean() ? this.read_Object() : this.read_value();
   }

   public Object read_abstract_interface(Class clz) {
      return this.read_boolean() ? this.read_Object() : this.read_value(clz);
   }

   String dumpBuf() {
      return this.rawInput.dumpBuf();
   }

   public String toString() {
      return "IIOPInputStream:[ pos=" + this.pos() + this.chunkingState + "\nnestingLevel=" + this.nestingLevel + " support JDK13=" + this.SUPPORT_JDK_13_CHUNKING + "\ncode_set=" + Integer.toHexString(this.char_codeset) + " wchar_codeset=" + Integer.toHexString(this.wchar_codeset) + "\n" + this.dumpBuf();
   }

   private static String getStringBytes(byte[] buf, int codeset) {
      try {
         switch (codeset) {
            case 65537:
               return new String(buf, "iso-8859-1");
            case 65568:
               return new String(buf, 0);
            case 83951617:
               return new String(buf, "utf-8");
            default:
               return new String(buf);
         }
      } catch (UnsupportedEncodingException var3) {
         return new String(buf);
      }
   }

   public final String read_string() {
      int len = this.read_ulong();
      if (len > 67108864) {
         throw new MARSHAL("Stream corrupted at " + this.pos() + ": tried to read string of length " + Integer.toHexString(len));
      } else if (len == 0) {
         return "";
      } else {
         byte[] buf = new byte[len - 1];
         this.read_octet_array(buf, 0, buf.length);
         String ret = getStringBytes(buf, this.char_codeset);
         this.read_octet();
         return ret;
      }
   }

   public final int read_numeric_string() throws NumberFormatException {
      int len = this.read_ulong();
      int num = 0;
      int mul = 1;
      if (len <= 1) {
         if (len == 1) {
            this.read_octet();
         }

         throw new NumberFormatException("");
      } else if (len > 524288) {
         throw new MARSHAL("Stream corrupted at " + this.pos() + ": tried to read string of length " + Integer.toHexString(len));
      } else {
         byte[] buf = new byte[len - 1];
         this.read_octet_array(buf, 0, buf.length);

         for(int i = len - 2; i >= 0; --i) {
            if (buf[i] <= 57 && buf[i] >= 48) {
               num += (buf[i] - 48) * mul;
               mul *= 10;
            } else if (buf[i] == 45) {
               num = -num;
            } else if (buf[i] != 43) {
               String ret = getStringBytes(buf, this.char_codeset);
               this.read_octet();
               throw new NumberFormatException(ret);
            }
         }

         this.read_octet();
         return num;
      }
   }

   public final RepositoryId read_repository_id() {
      return this.read_repository_id(this.read_ulong());
   }

   private RepositoryId read_repository_id(int len) {
      if (len > 524288) {
         throw new MARSHAL("Stream corrupted at " + this.pos() + ": tried to read string of length " + Integer.toHexString(len));
      } else if (len <= 1) {
         if (len == 1) {
            this.read_octet();
         }

         return null;
      } else {
         return new RepositoryId(this, len);
      }
   }

   private ClassInfo readIndirectingRepositoryId(String codebase) {
      ClassInfo cinfo = (ClassInfo)this.read_indirection();
      if (cinfo == null) {
         int len = this.read_ulong();
         int ipos = this.pos() - 4;
         RepositoryId repid = this.read_repository_id(len);
         if (repid != null) {
            cinfo = this.getRepository().findClassInfo(repid, codebase);
            this.addIndirection(ipos, cinfo);
         }
      }

      return cinfo;
   }

   private ClassInfo[] readIndirectingRepositoryIdList(String codebase) {
      ClassInfo[] repids = (ClassInfo[])((ClassInfo[])this.read_indirection());
      if (repids == null) {
         repids = new ClassInfo[this.read_long()];
         int indirection = this.pos() - 4;

         for(int i = 0; i < repids.length; ++i) {
            repids[i] = this.readIndirectingRepositoryId(codebase);
         }

         this.addIndirection(indirection, repids);
      }

      return repids;
   }

   private Object read_indirection() {
      Object obj = null;
      this.mark(0);
      if (this.read_long() == -1) {
         int indirection = this.read_long();
         Debug.assertion(indirection < -4);
         indirection = indirection + this.pos() - 4;
         Debug.assertion(indirection > 0);
         obj = this.getIndirection(indirection);
         this.clearMark();
         if (obj == null) {
            throw new IndirectionException(indirection);
         }
      } else {
         this.reset();
      }

      return obj;
   }

   final void discard_string() {
      this.read_octet_sequence();
   }

   public Principal read_Principal() {
      int len = this.read_ulong();
      this.skip((long)len);
      return null;
   }

   private TypeCode getTypeCodeIndirection(int i) {
      return this.tcIndirections == null ? null : (TypeCode)this.tcIndirections.get(i);
   }

   private int addTypeCodeIndirection(int i) {
      if (this.tcIndirections == null) {
         this.tcIndirections = getHashMap();
      }

      return this.tcIndirections.reserve(i);
   }

   public TypeCode read_TypeCode() {
      if (this.tcNestingLevel == 0 && this.tcIndirections != null) {
         this.tcIndirections.clear();
      }

      ++this.tcNestingLevel;
      int tc = this.read_long();
      int indirection = this.pos() - 4;
      Object type;
      if (tc != -1) {
         int reserved = this.addTypeCodeIndirection(indirection);
         type = new TypeCodeImpl(tc);
         this.tcIndirections.putReserved(reserved, indirection, type);
         ((TypeCodeImpl)type).read(this);
      } else {
         indirection = this.pos();
         indirection += this.read_ulong();
         type = this.getTypeCodeIndirection(indirection);
         if (type == null) {
            throw new MARSHAL("Couldn't read indirected TypeCode at " + this.pos() + " indirection to " + indirection);
         }
      }

      --this.tcNestingLevel;
      return (TypeCode)type;
   }

   private void throwOptionalData(int length) throws OptionalDataException {
      try {
         OptionalDataException ode = ObjectStreamClass.newOptionalDataException(true);
         ode.length = length;
         throw ode;
      } catch (Exception var3) {
         throw new AssertionError("Couldn't build an OptionalDataException");
      }
   }

   public final void readFully(byte[] b) throws IOException {
      if (b == null) {
         throw new MARSHAL("null array as parameter to readFully");
      } else {
         this.readFully(b, 0, b.length);
      }
   }

   public final void readFully(byte[] b, int i, int j) throws IOException {
      if (this.eof()) {
         throw new EOFException();
      } else {
         this.read_octet_array(b, i, j);
      }
   }

   public final int skipBytes(int i) throws IOException {
      if (this.eof()) {
         throw new EOFException();
      } else {
         return (int)this.skip((long)i);
      }
   }

   public final boolean readBoolean() throws IOException {
      if (this.eof()) {
         throw new EOFException();
      } else {
         return this.read_boolean();
      }
   }

   public final byte readByte() throws IOException {
      if (this.eof()) {
         throw new EOFException();
      } else {
         return this.read_octet();
      }
   }

   public final int readUnsignedByte() throws IOException {
      if (this.eof()) {
         throw new EOFException();
      } else {
         return this.read_octet() & 255;
      }
   }

   public final short readShort() throws IOException {
      if (this.eof()) {
         throw new EOFException();
      } else {
         return this.read_short();
      }
   }

   public final int readUnsignedShort() throws IOException {
      if (this.eof()) {
         throw new EOFException();
      } else {
         return this.read_unsigned_short();
      }
   }

   public final char readChar() throws IOException {
      if (this.eof()) {
         throw new EOFException();
      } else {
         return this.read_wchar();
      }
   }

   public final int readInt() throws IOException {
      if (this.eof()) {
         throw new EOFException();
      } else {
         return this.read_long();
      }
   }

   public final long readLong() throws IOException {
      if (this.eof()) {
         throw new EOFException();
      } else {
         return this.read_longlong();
      }
   }

   public final float readFloat() throws IOException {
      if (this.eof()) {
         throw new EOFException();
      } else {
         return this.read_float();
      }
   }

   public final double readDouble() throws IOException {
      if (this.eof()) {
         throw new EOFException();
      } else {
         return this.read_double();
      }
   }

   public final String readLine() throws IOException {
      if (this.eof()) {
         this.throwOptionalData(0);
      }

      throw new IOException("readLine() is not supportable for IIOP streams");
   }

   public final String readUTF() throws IOException {
      if (this.eof()) {
         this.throwOptionalData(0);
      }

      return this.read_wstring();
   }

   public String readUTF8() throws IOException {
      if (this.eof()) {
         this.throwOptionalData(0);
      }

      int len = this.read_ulong();
      if (len == 0) {
         return "";
      } else if (len > 67108864) {
         throw new MARSHAL("Stream corrupted at " + this.pos() + ": tried to read wstring of length " + Integer.toHexString(len));
      } else {
         return this.readUTF8String(len);
      }
   }

   public final String readASCII() throws IOException {
      if (this.eof()) {
         this.throwOptionalData(0);
      }

      return this.read_string();
   }

   public final Object readObject() throws ClassNotFoundException, IOException {
      if (this.eof()) {
         this.throwOptionalData(0);
      }

      return this.read_abstract_interface();
   }

   public final Object readObject(Class c) throws ClassNotFoundException, IOException {
      try {
         if (Remote.class.isAssignableFrom(c)) {
            IOR ior = new IOR(this);
            if (CorbaUtils.isARemote(c)) {
               RemoteInfo rinfo = RemoteInfo.findRemoteInfo(c);
               return IIOPReplacer.resolveObject(ior, rinfo);
            } else {
               return IIOPReplacer.resolveObject(ior);
            }
         } else if (!c.equals(Object.class) && !c.equals(Serializable.class) && !c.equals(Externalizable.class)) {
            if (org.omg.CORBA.Object.class.isAssignableFrom(c)) {
               return this.read_Object(c);
            } else if (Utils.isIDLException(c)) {
               return this.read_IDLEntity(c);
            } else {
               return Utils.isAbstractInterface(c) ? this.read_abstract_interface(c) : this.read_value(c);
            }
         } else {
            return this.readAny();
         }
      } catch (SystemException var4) {
         throw CorbaUtils.mapSystemException(var4);
      }
   }

   public final int read(byte[] b, int off, int len) throws IOException {
      if (this.eof()) {
         return -1;
      } else {
         this.checkForRoomInChunk(0);
         if (this.chunkingState.state == IIOPInputStream.State.in_a_chunk) {
            len = min(len, this.chunkingState.getBytesLeftInChunk());
            this.chunkingState.reduceChunkLength(len);
         }

         return this.rawInput.read(b, off, len);
      }
   }

   public final int available() throws IOException {
      return 0;
   }

   static void p(String s) {
      System.out.println("<IIOPInputStream> " + s);
   }

   static {
      MarshalledObject.setFilter(new MarshalledObjectClassFilter());
      READ_METHOD_ARG = InputStream.class;
      mapPool = new StackPool(1024);
   }

   private class ValueChunkState {
      private int chunkLength = 0;
      private int endTag = 0;
      private State state;
      private int numPseudoLevels;

      ValueChunkState() {
         this.state = IIOPInputStream.State.not_nesting;
         this.numPseudoLevels = 0;
      }

      ValueChunkState(ValueChunkState original) {
         this.state = IIOPInputStream.State.not_nesting;
         this.numPseudoLevels = 0;
         this.copyFrom(original);
      }

      private boolean isChunking() {
         return this.state != IIOPInputStream.State.not_nesting;
      }

      private void startValueProcessing(int valueTag) {
         if (IIOPInputStream.useChunking(valueTag)) {
            this.startChunk();
         } else if (this.state == IIOPInputStream.State.start_of_value) {
            this.state = IIOPInputStream.State.glassfish_pseudo_chunk;
            --this.endTag;
            ++this.numPseudoLevels;
         }

      }

      private void copyFrom(ValueChunkState original) {
         this.chunkLength = original.chunkLength;
         this.endTag = original.endTag;
         this.state = original.state;
         this.numPseudoLevels = original.numPseudoLevels;
      }

      ValueChunkState cloneState() {
         return IIOPInputStream.this.new ValueChunkState(this);
      }

      int getBytesLeftInChunk() {
         return this.chunkLength;
      }

      void startChunk(int length) {
         if (IIOPInputStream.this.DEBUG) {
            IIOPInputStream.p("Starting chunk at " + IIOPInputStream.this.pos() + " with length " + length);
         }

         this.chunkLength = length;
         this.state = IIOPInputStream.State.in_a_chunk;
      }

      void reduceChunkLength(int n) {
         this.chunkLength -= n;
         if (this.chunkLength == 0) {
            this.state = IIOPInputStream.State.end_of_chunk;
         }

         if (this.chunkLength < 0) {
            (new Exception("reduceChunkLength called")).printStackTrace();
         }

      }

      public String toString() {
         return "ValueChunkState[ state=" + this.state + " endTag=" + this.endTag + " chunkLength=" + this.chunkLength + " numPseudoLevels=" + this.numPseudoLevels + " ]";
      }

      private void startChunk() {
         --this.endTag;
         IIOPInputStream.this.rawInput.mark(0);
         int tag = IIOPInputStream.this.readProtocolLong();
         if (this.isValueTag(tag)) {
            IIOPInputStream.this.rawInput.reset();
            this.state = IIOPInputStream.State.nesting;
         } else if (this.isExpectedEndTag(tag)) {
            IIOPInputStream.this.rawInput.reset();
            this.state = IIOPInputStream.State.end_of_chunk;
         } else {
            IIOPInputStream.this.rawInput.clearMark();
            this.startChunk(tag);
         }

      }

      private boolean continuation() {
         int tag = IIOPInputStream.this.peek_long();
         if (tag == 0) {
            throw new MARSHAL("stream corrupted: '0' tag reserved");
         } else if (this.isContinuationChunkLength(tag)) {
            this.startChunk(IIOPInputStream.this.readProtocolLong());
            return true;
         } else {
            return false;
         }
      }

      private void skipToEndOfChunk() {
         if (this.getBytesLeftInChunk() > 0) {
            IIOPInputStream.this.skip((long)this.getBytesLeftInChunk());
            this.reduceChunkLength(this.getBytesLeftInChunk());
         }

      }

      private boolean mayReadBytes(int numBytesToRead) {
         if (this.state != IIOPInputStream.State.in_a_chunk) {
            return true;
         } else if (numBytesToRead <= this.getBytesLeftInChunk()) {
            this.reduceChunkLength(numBytesToRead);
            return true;
         } else {
            return this.getBytesLeftInChunk() != 0 ? false : this.isValueTag(IIOPInputStream.this.peek_long());
         }
      }

      private boolean isValueTag(int tag) {
         return tag >= 2147483392;
      }

      private void assertMayReadBytes(int n) {
         if (!this.mayReadBytes(n)) {
            throw new MARSHAL("stream corrupted: reading past end of chunk at: " + IIOPInputStream.this.pos(), 1330446344, CompletionStatus.COMPLETED_NO);
         }
      }

      private void assertNotAtEndOfChunk() {
         if (this.isAtEndOfChunk()) {
            throw new MARSHAL("stream corrupted: reading past end of chunk at: " + IIOPInputStream.this.pos(), 1330446344, CompletionStatus.COMPLETED_NO);
         }
      }

      private boolean isAtEndOfChunk() {
         return this.state == IIOPInputStream.State.end_of_chunk && !this.continuation();
      }

      private int readValueTag() {
         if (this.state == IIOPInputStream.State.end_of_chunk && this.isExpectedEndTag(IIOPInputStream.this.peek_long())) {
            if (IIOPInputStream.this.DEBUG) {
               IIOPInputStream.p("ending value at " + IIOPInputStream.this.pos() + " due to wrong end tag written by RI " + IIOPInputStream.this.chunkingState);
            }

            this.end_value();
            if (IIOPInputStream.this.rawInput.eof()) {
               return 0;
            }
         }

         if (this.state == IIOPInputStream.State.nesting || this.state == IIOPInputStream.State.glassfish_pseudo_chunk || this.state == IIOPInputStream.State.end_of_chunk && !this.continuation()) {
            this.state = IIOPInputStream.State.start_of_value;
         }

         int tag = IIOPInputStream.this.read_long();
         if (this.state == IIOPInputStream.State.start_of_value && !this.isValueTag(tag)) {
            this.state = IIOPInputStream.State.nesting;
         }

         return tag;
      }

      private boolean isContinuationChunkLength(int tag) {
         return tag > 0 && tag < 2147483392;
      }

      private boolean isExpectedEndTag(int tag) {
         return this.endTag <= tag && tag < 0;
      }

      private void end_value() {
         if (this.endTag < 0) {
            this.skipToEndOfChunk();
            IIOPInputStream.this.rawInput.mark(0);
            int streamTag = IIOPInputStream.this.readProtocolLong();
            if (this.state == IIOPInputStream.State.nesting && this.isContinuationChunkLength(streamTag)) {
               IIOPInputStream.this.rawInput.reset();
               this.continuation();
            } else if (streamTag > this.endTag) {
               if (IIOPInputStream.this.DEBUG) {
                  IIOPInputStream.p("    not consuming higher-level end tag " + (-100 < streamTag && streamTag < 0 ? streamTag : Integer.toHexString(streamTag)));
               }

               IIOPInputStream.this.rawInput.reset();
               ++this.endTag;
               if (this.numPseudoLevels > 0 && --this.numPseudoLevels == 0) {
                  this.continuation();
               }
            } else {
               ++this.endTag;
               if (IIOPInputStream.this.DEBUG) {
                  IIOPInputStream.p("    consuming end tag " + streamTag);
               }

               IIOPInputStream.this.rawInput.clearMark();
               if (streamTag == -1) {
                  this.state = IIOPInputStream.State.not_nesting;
               } else if (this.isPossibleEndTag(IIOPInputStream.this.peek_long(), streamTag)) {
                  this.state = IIOPInputStream.State.end_of_chunk;
               } else {
                  this.state = IIOPInputStream.State.nesting;
                  this.continuation();
               }
            }

            if (IIOPInputStream.this.DEBUG) {
               IIOPInputStream.p("    new state " + this);
            }

         }
      }

      private boolean isPossibleEndTag(int intValue, int currentEndTag) {
         return currentEndTag < intValue && intValue < -1;
      }

      public void clear() {
         this.state = IIOPInputStream.State.not_nesting;
         this.chunkLength = 0;
         this.endTag = 0;
         this.numPseudoLevels = 0;
      }
   }

   static enum State {
      not_nesting,
      in_a_chunk,
      end_of_chunk,
      nesting,
      glassfish_pseudo_chunk,
      start_of_value;
   }

   private static class EncapsulationWrapper {
      int encapLength;
      ValueChunkState chunkState;

      EncapsulationWrapper(int encapLength, ValueChunkState chunkState) {
         this.encapLength = encapLength;
         this.chunkState = chunkState;
      }
   }

   static class Marker {
      private FragmentedInputStream.Marker fragmentMarker = new FragmentedInputStream.Marker();
      private ValueChunkState chunkState;
      private boolean needEightByteAlignment;
      private boolean littleEndian;
      private int needLongAlignment = 0;

      void copyFrom(Marker maker) {
         this.fragmentMarker.copyFrom(maker.fragmentMarker);
         this.chunkState = maker.chunkState.cloneState();
         this.needEightByteAlignment = maker.needEightByteAlignment;
         this.needLongAlignment = maker.needLongAlignment;
         this.littleEndian = maker.littleEndian;
      }

      private void markAt(IIOPInputStream stream) {
         stream.rawInput.mark(this.fragmentMarker);
         this.mark(stream);
      }

      private void mark(IIOPInputStream stream) {
         this.chunkState = stream.cloneChunkState();
         this.needEightByteAlignment = stream.needEightByteAlignment;
         this.needLongAlignment = stream.needLongAlignment;
         this.littleEndian = stream.littleEndian;
      }

      private void resetTo(IIOPInputStream stream) {
         stream.rawInput.resetTo(this.fragmentMarker);
         this.reset(stream);
      }

      private void reset(IIOPInputStream stream) {
         stream.chunkingState.copyFrom(this.chunkState);
         stream.needEightByteAlignment = this.needEightByteAlignment;
         stream.needLongAlignment = this.needLongAlignment;
         stream.littleEndian = this.littleEndian;
      }
   }
}
