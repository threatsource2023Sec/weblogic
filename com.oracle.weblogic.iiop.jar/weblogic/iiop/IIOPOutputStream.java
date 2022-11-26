package weblogic.iiop;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.rmi.Remote;
import javax.management.MBeanInfo;
import org.omg.CORBA.Any;
import org.omg.CORBA.CODESET_INCOMPATIBLE;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.Context;
import org.omg.CORBA.ContextList;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Principal;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.BoxedValueHelper;
import org.omg.CORBA.portable.CustomValue;
import org.omg.CORBA.portable.IDLEntity;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.Streamable;
import org.omg.CORBA.portable.StreamableValue;
import org.omg.CORBA.portable.ValueBase;
import org.omg.CORBA.portable.ValueOutputStream;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.corba.idl.TypeCodeImpl;
import weblogic.corba.repository.SentClassesRepository;
import weblogic.corba.utils.ClassInfo;
import weblogic.corba.utils.CorbaUtils;
import weblogic.corba.utils.IndirectionHashtable;
import weblogic.corba.utils.IndirectionValueHashtable;
import weblogic.corba.utils.MarshalledObject;
import weblogic.corba.utils.RepositoryId;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.protocol.CodeSet;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.iiop.spi.Message;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelStream;
import weblogic.rmi.spi.MsgOutput;
import weblogic.rmi.utils.io.Codebase;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.security.service.SecurityServiceManager;
import weblogic.utils.Debug;
import weblogic.utils.Hex;
import weblogic.utils.collections.Pool;
import weblogic.utils.collections.StackPool;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkOutput;
import weblogic.utils.io.ObjectOutput;
import weblogic.utils.io.ObjectStreamClass;
import weblogic.utils.io.StringOutput;

public class IIOPOutputStream extends CorbaOutputStream implements ObjectOutput, PeerInfoable, ServerChannelStream, StringOutput, ValueOutputStream, ChunkOutput, MsgOutput {
   private static final boolean debugValueTypes = false;
   private static boolean USE_JAVA_SERIALIZATION_FOR_JMX = true;
   private static final boolean DEBUG = false;
   private static final int VT_NON_CHUNKING = 2147483394;
   private static final int VT_CHUNKING = 2147483402;
   private static final int VT_INDIRECTION = -1;
   private static final boolean SUPPORT_CHUNKING = true;
   private static final byte TRUE = 1;
   private static final byte FALSE = 0;
   private Chunk head;
   private Chunk current;
   private int currentPos;
   private int chunkPos;
   private Chunk queued;
   private int queuedLength;
   private final Marker[] markers;
   private int minorVersion;
   private boolean SUPPORT_JDK_13_CHUNKING;
   private int nestingLevel;
   private boolean nesting;
   private ORB orb;
   private int needLongAlignment;
   private boolean needEightByteAlignment;
   static final long HANDLE_ENDIAN_MASK = -2147483648L;
   static final long HANDLE_ALIGN_MASK = 1073741824L;
   static final long HANDLE_CHUNK_MASK = 536870912L;
   static final long HANDLE_NESTING_MASK = 268435456L;
   private static final int CHAR_BUF_SIZE = 256;
   private final char[] cbuf;
   private boolean littleEndian;
   private EndPoint endPoint;
   private int char_codeset;
   private int wchar_codeset;
   private java.io.ObjectOutput ooutput;
   private ObjectOutputStream objectStream;
   private IndirectionHashtable encapsulations;
   private IndirectionValueHashtable indirections;
   private IndirectionValueHashtable tcIndirections;
   private int tcNestingLevel;
   private Message message;
   private byte maxFormatVersion;
   private static final int MAP_POOL_SIZE = 1024;
   private static final Pool mapPool;
   private static final Pool mapPool2;
   private boolean chunking;
   private int currentChunk;
   private int currentChunkPos;
   private Chunk currentChunkChunk;
   private int endTag;
   private int lastEnd;

   public IIOPOutputStream() {
      this(false, (EndPoint)null, (Message)null);
   }

   public IIOPOutputStream(ORB orb) {
      this();
      this.orb = orb;
   }

   public IIOPOutputStream(boolean littleEndian, EndPoint endPoint) {
      this(littleEndian, endPoint, (Message)null);
   }

   public IIOPOutputStream(boolean littleEndian, EndPoint endPoint, Message message) {
      this.currentPos = 0;
      this.chunkPos = 0;
      this.markers = new Marker[]{new Marker(), new Marker()};
      this.minorVersion = 2;
      this.SUPPORT_JDK_13_CHUNKING = false;
      this.nestingLevel = 0;
      this.nesting = true;
      this.orb = null;
      this.needLongAlignment = 0;
      this.needEightByteAlignment = false;
      this.cbuf = new char[256];
      this.char_codeset = CodeSet.getDefaultCharCodeSet();
      this.wchar_codeset = CodeSet.getDefaultWcharCodeSet();
      this.encapsulations = null;
      this.indirections = null;
      this.tcIndirections = null;
      this.tcNestingLevel = 0;
      this.maxFormatVersion = 1;
      this.chunking = false;
      this.currentChunk = 0;
      this.currentChunkPos = 0;
      this.endTag = 0;
      this.lastEnd = 0;
      this.littleEndian = littleEndian;
      this.endPoint = endPoint;
      this.message = message;
      if (endPoint != null) {
         this.wchar_codeset = endPoint.getWcharCodeSet();
         this.char_codeset = endPoint.getCharCodeSet();
      }

      if (message != null) {
         this.minorVersion = message.getMinorVersion();
         this.maxFormatVersion = message.getMaxStreamFormatVersion();
      }

      this.head = this.current = Chunk.getChunk();
   }

   public void setMaxStreamFormatVersion(byte maxFormatVersion) {
      this.maxFormatVersion = maxFormatVersion;
   }

   public byte getMaxStreamFormatVersion() {
      return this.maxFormatVersion;
   }

   public long startEncapsulation() {
      return this.startEncapsulation(false, true, this.nesting);
   }

   long startEncapsulationNoNesting() {
      return this.startEncapsulation(false, true, false);
   }

   private long startEncapsulation(boolean newEndian, boolean putEndian, boolean newNesting) {
      this.write_long(0);
      int index = this.pos();
      long handle = (long)index;
      Debug.assertion((handle & -1073741824L) == 0L);
      if (this.littleEndian) {
         handle |= -2147483648L;
      }

      if (this.needLongAlignment > 0) {
         handle |= 1073741824L;
      }

      if (this.nesting) {
         ++this.nestingLevel;
         handle |= 268435456L;
      }

      this.needLongAlignment = this.chunkPos % 8 != 0 ? 4 : 0;
      if (this.chunking) {
         handle |= 536870912L;
         if (this.encapsulations == null) {
            this.encapsulations = getIndirectionHashMap();
         }

         this.encapsulations.put(index, new EncapsulationWrapper(this.lastEnd, this.chunking, this.currentChunk, this.endTag));
         this.chunking = false;
         this.currentChunk = 0;
         this.currentChunkChunk = null;
         this.currentChunkPos = 0;
         this.endTag = 0;
         this.lastEnd = 0;
      }

      this.littleEndian = newEndian;
      this.nesting = newNesting;
      if (putEndian) {
         this.putEndian();
      }

      return handle;
   }

   public void endEncapsulation(long handle) {
      this.littleEndian = (handle & -2147483648L) != 0L;
      this.nesting = (handle & 268435456L) != 0L;
      if (this.nesting) {
         --this.nestingLevel;
      }

      this.needLongAlignment = (handle & 1073741824L) != 0L ? 4 : 0;
      boolean chunked = (handle & 536870912L) != 0L;
      handle &= 268435455L;
      if (chunked) {
         EncapsulationWrapper encap = (EncapsulationWrapper)this.encapsulations.remove((int)handle);
         if (encap == null) {
            throw new MARSHAL("No encapsulation information at: " + this.pos());
         }

         this.chunking = encap.chunking;
         this.endTag = encap.endTag;
         this.currentChunk = encap.chunkLength;
         this.currentChunkChunk = null;
         this.currentChunkPos = 0;
         this.lastEnd = encap.encapLength;
      } else {
         this.chunking = false;
      }

      long oldPos = (long)this.pos();
      this.setPosition((int)(handle - 4L));
      this.write_long((int)(oldPos - handle));
      this.setPosition((int)oldPos);
   }

   public final void write_octet(byte value) {
      if (this.needEightByteAlignment) {
         this.checkEightByteAlignment();
      }

      if (this.chunking && this.currentChunk == 0) {
         this.startChunk();
      }

      this.advance();
      this.current.buf[this.chunkPos++] = value;
   }

   private final void align(int n) {
      if (this.needEightByteAlignment) {
         this.checkEightByteAlignment();
      }

      if (this.chunking && this.currentChunk == 0) {
         this.startChunk();
      }

      for(int alignPos = this.chunkPos + this.needLongAlignment; alignPos % n != 0; ++alignPos) {
         this.advance();
         this.current.buf[this.chunkPos++] = 0;
      }

   }

   public final void setNeedEightByteAlignment() {
      this.needEightByteAlignment = true;
   }

   private final void checkEightByteAlignment() {
      this.needEightByteAlignment = false;
      this.align(8);
   }

   public final void putEndian() {
      if (this.littleEndian) {
         this.write_octet((byte)1);
      } else {
         this.write_octet((byte)0);
      }

   }

   public void startUnboundedEncapsulation() {
      this.putEndian();
      ++this.nestingLevel;
   }

   void reset() {
      Chunk tmp = this.head;
      this.head = this.head.next;
      this.close();
      this.head = tmp;
      this.head.next = null;
      this.head.end = 0;
      this.chunkPos = 0;
      this.current = this.head;
      this.currentPos = 0;
      this.nestingLevel = 0;
      this.nesting = true;
      this.needLongAlignment = 0;
      this.needEightByteAlignment = false;
   }

   public String dumpBuf() {
      byte[] buf = this.getBuffer();
      return Hex.dump(buf, 0, buf.length);
   }

   public final int getMinorVersion() {
      return this.nestingLevel > 0 && !this.SUPPORT_JDK_13_CHUNKING ? 2 : this.minorVersion;
   }

   private boolean useCompliantChunking() {
      return !this.SUPPORT_JDK_13_CHUNKING || this.nestingLevel != 0;
   }

   public final void setMinorVersion(int minorVersion) {
      this.minorVersion = minorVersion;
      this.SUPPORT_JDK_13_CHUNKING = minorVersion < 2;
   }

   public java.io.ObjectOutput getObjectOutput(boolean idl) {
      if (!idl) {
         return this;
      } else {
         if (this.ooutput == null) {
            this.ooutput = new IDLMsgOutput(this);
         }

         return this.ooutput;
      }
   }

   public ObjectOutputStream getObjectOutputStream(Object value, ObjectStreamClass osc, byte streamFormatVersion) throws IOException {
      if (this.objectStream == null) {
         this.objectStream = new ObjectOutputStreamImpl(this, value, osc, streamFormatVersion);
      } else {
         ((ObjectOutputStreamImpl)this.objectStream).pushCurrent(value, osc, streamFormatVersion);
      }

      return this.objectStream;
   }

   public PeerInfo getPeerInfo() {
      return this.endPoint != null && this.endPoint.getPeerInfo() != null ? this.endPoint.getPeerInfo() : PeerInfo.getPeerInfo();
   }

   public void setCodeSets(int char_codeset, int wchar_codeset) {
      this.char_codeset = char_codeset;
      this.wchar_codeset = wchar_codeset;
   }

   private int getWcharCodeSet() {
      return this.nestingLevel > 0 && !this.SUPPORT_JDK_13_CHUNKING ? 65801 : this.wchar_codeset;
   }

   private static int min(int a, int b) {
      return a <= b ? a : b;
   }

   private static int max(int a, int b) {
      return a >= b ? a : b;
   }

   private void setMark(int marker) {
      this.markers[marker].chunk = this.current;
      this.markers[marker].pos = this.chunkPos;
      this.markers[marker].currentPos = this.currentPos;
   }

   final void setMark(Marker marker) {
      marker.chunk = this.current;
      marker.pos = this.chunkPos;
      marker.currentPos = this.currentPos;
   }

   private final void restoreMark(int marker) {
      this.current = this.markers[marker].chunk;
      this.chunkPos = this.markers[marker].pos;
      this.currentPos = this.markers[marker].currentPos;
   }

   final void restoreMark(Marker marker) {
      this.current = marker.chunk;
      this.chunkPos = marker.pos;
      this.currentPos = marker.currentPos;
   }

   private final void setPosition(int position) {
      if (this.currentPos <= position && position - this.currentPos < Chunk.CHUNK_SIZE) {
         this.chunkPos = position - this.currentPos;
      } else {
         this.current = this.head;

         for(this.currentPos = 0; position > Chunk.CHUNK_SIZE; this.current = this.current.next) {
            if (this.current.next == null) {
               this.current.next = Chunk.getChunk();
            }

            position -= this.current.end;
            this.currentPos += this.current.end;
         }

         this.chunkPos = position;
      }

   }

   private final int setLength() {
      this.current.end = this.chunkPos;
      int len = this.getSize();
      this.setPosition(8);
      this.write_long(len - 12);
      return len;
   }

   public final void writeTo(OutputStream out) throws IOException {
      if (this.queued == null) {
         if (this.current == null || this.head == null) {
            throw new IOException("writeTo() called on closed stream");
         }

         this.enqueue();
      }

      while(this.queued != null) {
         if (this.queued.end > 0) {
            out.write(this.queued.buf, 0, this.queued.end);
         }

         Chunk tmp = this.queued;
         this.queued = this.queued.next;
         Chunk.releaseChunk(tmp);
      }

      this.queuedLength = 0;
   }

   public Chunk getChunks() {
      return this.queued != null ? this.queued : this.head;
   }

   public void cleanup() {
      while(this.queued != null) {
         Chunk tmp = this.queued.next;
         Chunk.releaseChunk(this.queued);
         this.queued = tmp;
      }

   }

   public final void enqueue() {
      if (this.queued == null) {
         this.queuedLength = this.setLength();
         this.queued = this.head;
         this.head = null;
         this.close();
      }

   }

   public final int getLength() throws IOException {
      return this.queued != null ? this.queuedLength : this.getSize();
   }

   public int pos() {
      return this.currentPos + this.chunkPos;
   }

   public byte[] getBuffer() {
      this.current.end = this.chunkPos;
      byte[] b = new byte[this.getSize()];
      Chunk tmp = this.head;

      for(int off = 0; tmp != null; tmp = tmp.next) {
         System.arraycopy(tmp.buf, 0, b, off, tmp.end);
         off += tmp.end;
      }

      return b;
   }

   public byte[] getBufferToWrite() {
      if (this.queued == null) {
         this.enqueue();
      }

      byte[] b = new byte[this.queuedLength];
      int off = 0;

      while(this.queued != null) {
         System.arraycopy(this.queued.buf, 0, b, off, this.queued.end);
         off += this.queued.end;
         Chunk tmp = this.queued;
         this.queued = this.queued.next;
         Chunk.releaseChunk(tmp);
      }

      this.queuedLength = 0;
      return b;
   }

   public void close() {
      while(this.head != null) {
         Chunk tmp = this.head.next;
         Chunk.releaseChunk(this.head);
         this.head = tmp;
      }

      releaseHashMap(this.indirections);
      this.indirections = null;
      releaseHashMap(this.tcIndirections);
      this.tcIndirections = null;
      releaseHashMap(this.encapsulations);
      this.encapsulations = null;
      this.current = null;
      this.message = null;
   }

   private int getSize() {
      Chunk tmp = this.head;

      int pos;
      for(pos = 0; tmp != this.current; tmp = tmp.next) {
         pos += tmp.end;
      }

      return pos + this.chunkPos;
   }

   public boolean isSecure() {
      return this.endPoint != null && this.endPoint.isSecure();
   }

   public EndPoint getEndPoint() {
      return this.endPoint;
   }

   public boolean supportsTLS() {
      return this.getServerChannel() != null && this.getServerChannel().supportsTLS();
   }

   public ServerChannel getServerChannel() {
      return this.endPoint != null ? this.endPoint.getServerChannel() : null;
   }

   public InputStream create_input_stream() {
      this.current.end = this.chunkPos;
      return this.createInputStream(this.head);
   }

   public IIOPInputStream createExactInputStream() {
      this.current.end = this.chunkPos;
      Chunk newhead = new Chunk(this.getSize());
      newhead.end = newhead.buf.length;
      int offset = 0;

      while(this.head != null) {
         System.arraycopy(this.head.buf, 0, newhead.buf, offset, this.head.end);
         offset += this.head.end;
         Chunk tmp = this.head;
         this.head = this.head.next;
         Chunk.releaseChunk(tmp);
      }

      return this.createInputStream(newhead);
   }

   private IIOPInputStream createInputStream(Chunk newhead) {
      IIOPInputStream is = new IIOPInputStream(newhead, this.isSecure(), this.endPoint);
      is.setCodeSets(this.char_codeset, this.wchar_codeset);
      is.setLittleEndian(this.littleEndian);
      is.setORB(this.orb);
      is.mark(0);
      this.head = this.current = null;
      this.close();
      return is;
   }

   public void write_boolean(boolean value) {
      this.write_octet((byte)(value ? 1 : 0));
   }

   public void write_char(char value) {
      this.write_octet((byte)(value & 255));
   }

   public void write_wchar(char value) {
      label28:
      switch (this.getWcharCodeSet()) {
         case 65792:
            switch (this.getMinorVersion()) {
               case 0:
               case 1:
                  this.align(2);
                  break label28;
               case 2:
                  this.write_octet((byte)2);
               default:
                  break label28;
            }
         case 65801:
            switch (this.getMinorVersion()) {
               case 0:
               case 1:
                  this.align(2);
                  break label28;
               case 2:
                  this.write_octet((byte)4);
                  if (this.littleEndian) {
                     this.write_octet((byte)-1);
                     this.write_octet((byte)-2);
                  } else {
                     this.write_octet((byte)-2);
                     this.write_octet((byte)-1);
                  }
               default:
                  break label28;
            }
         case 83951617:
            this.write_UTF8wchar(value);
            return;
      }

      if (this.littleEndian) {
         this.write_octet((byte)value);
         this.write_octet((byte)(value >>> 8));
      } else {
         this.write_octet((byte)(value >>> 8));
         this.write_octet((byte)value);
      }

   }

   private void advance() {
      if (this.chunkPos == Chunk.CHUNK_SIZE) {
         if (this.current.next == null) {
            this.current.next = Chunk.getChunk();
         }

         this.current.end = this.chunkPos;
         this.currentPos += this.chunkPos;
         this.current = this.current.next;
         this.chunkPos = 0;
      }

   }

   public void write_unsigned_short(int value) {
      this.align(2);
      if (this.chunkPos + 2 <= Chunk.CHUNK_SIZE) {
         if (this.littleEndian) {
            this.current.buf[this.chunkPos++] = (byte)value;
            this.current.buf[this.chunkPos++] = (byte)(value >>> 8);
         } else {
            this.current.buf[this.chunkPos++] = (byte)(value >>> 8);
            this.current.buf[this.chunkPos++] = (byte)value;
         }
      } else if (this.littleEndian) {
         this.write_octet((byte)(value & 255));
         this.write_octet((byte)(value >>> 8));
      } else {
         this.write_octet((byte)(value >>> 8));
         this.write_octet((byte)value);
      }

   }

   public void write_ushort(short value) {
      this.write_unsigned_short(value);
   }

   public void write_short(short value) {
      this.write_unsigned_short(value);
   }

   public final void write_long(int value) {
      this.align(4);
      if (this.chunkPos + 4 <= Chunk.CHUNK_SIZE && !this.littleEndian) {
         this.current.buf[this.chunkPos++] = (byte)(value >>> 24);
         this.current.buf[this.chunkPos++] = (byte)(value >>> 16);
         this.current.buf[this.chunkPos++] = (byte)(value >>> 8);
         this.current.buf[this.chunkPos++] = (byte)value;
      } else {
         this.write_slow_long(value);
      }

   }

   private void write_slow_long(int value) {
      if (this.littleEndian) {
         if (this.chunkPos + 4 <= Chunk.CHUNK_SIZE) {
            this.current.buf[this.chunkPos++] = (byte)value;
            this.current.buf[this.chunkPos++] = (byte)(value >>> 8);
            this.current.buf[this.chunkPos++] = (byte)(value >>> 16);
            this.current.buf[this.chunkPos++] = (byte)(value >>> 24);
         } else {
            this.write_octet((byte)value);
            this.write_octet((byte)(value >>> 8));
            this.write_octet((byte)(value >>> 16));
            this.write_octet((byte)(value >>> 24));
         }
      } else {
         this.write_octet((byte)(value >>> 24));
         this.write_octet((byte)(value >>> 16));
         this.write_octet((byte)(value >>> 8));
         this.write_octet((byte)value);
      }

   }

   public final void write_ulong(int value) {
      this.write_long(value);
   }

   public void write_longlong(long value) {
      this.align(8);
      if (this.chunkPos + 8 <= Chunk.CHUNK_SIZE) {
         if (this.littleEndian) {
            this.current.buf[this.chunkPos++] = (byte)((int)value);
            this.current.buf[this.chunkPos++] = (byte)((int)(value >>> 8));
            this.current.buf[this.chunkPos++] = (byte)((int)(value >>> 16));
            this.current.buf[this.chunkPos++] = (byte)((int)(value >>> 24));
            this.current.buf[this.chunkPos++] = (byte)((int)(value >>> 32));
            this.current.buf[this.chunkPos++] = (byte)((int)(value >>> 40));
            this.current.buf[this.chunkPos++] = (byte)((int)(value >>> 48));
            this.current.buf[this.chunkPos++] = (byte)((int)(value >>> 56));
         } else {
            this.current.buf[this.chunkPos++] = (byte)((int)(value >>> 56));
            this.current.buf[this.chunkPos++] = (byte)((int)(value >>> 48));
            this.current.buf[this.chunkPos++] = (byte)((int)(value >>> 40));
            this.current.buf[this.chunkPos++] = (byte)((int)(value >>> 32));
            this.current.buf[this.chunkPos++] = (byte)((int)(value >>> 24));
            this.current.buf[this.chunkPos++] = (byte)((int)(value >>> 16));
            this.current.buf[this.chunkPos++] = (byte)((int)(value >>> 8));
            this.current.buf[this.chunkPos++] = (byte)((int)value);
         }
      } else if (this.littleEndian) {
         this.write_octet((byte)((int)value));
         this.write_octet((byte)((int)(value >>> 8)));
         this.write_octet((byte)((int)(value >>> 16)));
         this.write_octet((byte)((int)(value >>> 24)));
         this.write_octet((byte)((int)(value >>> 32)));
         this.write_octet((byte)((int)(value >>> 40)));
         this.write_octet((byte)((int)(value >>> 48)));
         this.write_octet((byte)((int)(value >>> 56)));
      } else {
         this.write_octet((byte)((int)(value >>> 56)));
         this.write_octet((byte)((int)(value >>> 48)));
         this.write_octet((byte)((int)(value >>> 40)));
         this.write_octet((byte)((int)(value >>> 32)));
         this.write_octet((byte)((int)(value >>> 24)));
         this.write_octet((byte)((int)(value >>> 16)));
         this.write_octet((byte)((int)(value >>> 8)));
         this.write_octet((byte)((int)value));
      }

   }

   public void write_ulonglong(long value) {
      this.write_longlong(value);
   }

   public void write_float(float value) {
      this.write_long(Float.floatToIntBits(value));
   }

   public void write_double(double value) {
      this.write_longlong(Double.doubleToLongBits(value));
   }

   public void write_string(String value) {
      if (value == null) {
         throw new MARSHAL("null value passed to write_string(String)");
      } else {
         switch (this.char_codeset) {
            case 65537:
            case 65568:
               this.write_ulong(value.length() + 1);
               this.writeASCII(value);
               this.write_octet((byte)0);
               break;
            case 83951617:
               this.write_stringUTF8(value);
               break;
            default:
               throw new CODESET_INCOMPATIBLE("Unknown char codeset");
         }

      }
   }

   private void write_stringUTF8(String value) {
      int off = 0;
      int size = value.length();

      boolean canWriteASCII;
      int chunk;
      for(canWriteASCII = true; off < size; off += chunk) {
         chunk = min(256, size - off);
         value.getChars(off, off + chunk, this.cbuf, 0);

         for(int i = 0; i < chunk; ++i) {
            char c = this.cbuf[i];
            if ((c & 'ﾀ') != 0) {
               canWriteASCII = false;
               break;
            }
         }

         if (!canWriteASCII) {
            break;
         }
      }

      if (!canWriteASCII) {
         this.writeStringUTF8_1Pass(value, true);
      } else {
         this.write_ulong(value.length() + 1);
         this.writeASCII(value);
         this.write_octet((byte)0);
      }

   }

   public final void write_repository_id(RepositoryId repid) {
      if (repid == null) {
         this.write_ulong(1);
         this.write_octet((byte)0);
      } else {
         repid.write(this);
      }

   }

   private void writeRepositoryIdList(RepositoryId[] repids) {
      if (!this.writeIndirectionMaybe(repids)) {
         this.write_long(repids.length);
         RepositoryId[] var2 = repids;
         int var3 = repids.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            RepositoryId repid = var2[var4];
            if (!this.writeIndirectionMaybe(repid)) {
               this.write_repository_id(repid);
            }
         }
      }

   }

   private void writeStringUTF16(String value, boolean bom) {
      int len = value.length();
      this.write_ulong(len == 0 ? len : len * 2 + (bom ? 2 : 0));
      if (len != 0 && bom) {
         if (this.littleEndian) {
            this.write_octet((byte)-1);
            this.write_octet((byte)-2);
         } else {
            this.write_octet((byte)-2);
            this.write_octet((byte)-1);
         }
      }

      int off = 0;

      while(off < len) {
         int toCopy = min(len - off, 256);
         value.getChars(off, off + toCopy, this.cbuf, 0);
         off += toCopy;
         this.writeCharsUTF16(this.cbuf, toCopy);
      }

   }

   private void writeCharsUTF16(char[] chars, int size) {
      this.align(1);
      int len = size * 2;
      int off = 0;
      int lowShift = 8;
      int highShift = 0;
      if (this.littleEndian) {
         highShift = 8;
         lowShift = 0;
      }

      while(off < size) {
         int avail = Chunk.CHUNK_SIZE - this.chunkPos;
         if (avail == 0) {
            this.advance();
         } else if (avail == 1) {
            this.current.buf[this.chunkPos++] = (byte)(chars[off] >>> lowShift);
            this.advance();
            this.current.buf[this.chunkPos++] = (byte)(chars[off++] >>> highShift);
            len -= 2;
         }

         avail = Chunk.CHUNK_SIZE - this.chunkPos;
         avail -= avail % 2;

         for(int toCopy = min(avail, len); toCopy > 0; len -= 2) {
            this.current.buf[this.chunkPos++] = (byte)(chars[off] >>> lowShift);
            this.current.buf[this.chunkPos++] = (byte)(chars[off++] >>> highShift);
            toCopy -= 2;
         }
      }

   }

   public final void writeUTF8(String value) {
      int size = value.length();

      int off;
      int chunk;
      label27:
      for(off = 0; off < size; off += chunk) {
         chunk = min(256, size - off);
         value.getChars(off, off + chunk, this.cbuf, 0);

         for(int i = 0; i < chunk; ++i) {
            char c = this.cbuf[i];
            if ((c & 'ﾀ') != 0) {
               break label27;
            }
         }
      }

      if (off == size) {
         this.write_ulong(size);
         this.writeASCII(value);
      } else {
         this.writeStringUTF8_1Pass(value, false);
      }

   }

   private void writeStringUTF8_1Pass(String value, boolean addTerminator) {
      this.align(4);
      int lenPos = this.pos();
      this.write_slow_long(0);
      int size = value.length();
      int utflen = 0;
      int off = 0;

      int chunk;
      while(off < size) {
         while(off < size && this.chunkPos < Chunk.CHUNK_SIZE - 3) {
            chunk = min(256, size - off);
            value.getChars(off, off + chunk, this.cbuf, 0);
            int i = 0;

            int pos;
            for(pos = this.chunkPos; i < chunk && pos < Chunk.CHUNK_SIZE - 3; ++i) {
               char c = this.cbuf[i];
               if ((c & 'ﾀ') == 0) {
                  this.current.buf[pos++] = (byte)c;
               } else if ((c & '\uf800') == 0) {
                  this.current.buf[pos++] = (byte)(192 | c >> 6 & 31);
                  this.current.buf[pos++] = (byte)(128 | c >> 0 & 63);
               } else {
                  this.current.buf[pos++] = (byte)(224 | c >> 12 & 15);
                  this.current.buf[pos++] = (byte)(128 | c >> 6 & 63);
                  this.current.buf[pos++] = (byte)(128 | c >> 0 & 63);
               }
            }

            utflen += pos - this.chunkPos;
            this.chunkPos = pos;
            off += i;
         }

         while(off < size && this.chunkPos >= Chunk.CHUNK_SIZE - 3) {
            utflen += this.writeUTF8Char(value.charAt(off++));
         }
      }

      chunk = this.pos();
      this.setPosition(lenPos);
      if (addTerminator) {
         this.write_slow_long(utflen + 1);
         this.setPosition(chunk);
         this.write_octet((byte)0);
      } else {
         this.write_slow_long(utflen);
         this.setPosition(chunk);
      }

   }

   public final void writeASCII(String value) {
      if (this.chunking && this.currentChunk == 0) {
         this.startChunk();
      }

      int size = value.length();

      int toCopy;
      for(int off = 0; off < size; off += toCopy) {
         if (this.chunkPos == Chunk.CHUNK_SIZE) {
            this.advance();
         }

         toCopy = min(Chunk.CHUNK_SIZE - this.chunkPos, size - off);
         value.getBytes(off, off + toCopy, this.current.buf, this.chunkPos);
         this.chunkPos += toCopy;
      }

   }

   private int writeUTF8Char(char c) {
      if ((c & 'ﾀ') == 0) {
         this.write_octet((byte)c);
         return 1;
      } else if ((c & '\uf800') == 0) {
         this.write_octet((byte)(192 | c >> 6 & 31));
         this.write_octet((byte)(128 | c >> 0 & 63));
         return 2;
      } else {
         this.write_octet((byte)(224 | c >> 12 & 15));
         this.write_octet((byte)(128 | c >> 6 & 63));
         this.write_octet((byte)(128 | c >> 0 & 63));
         return 3;
      }
   }

   private void write_UTF8wchar(char c) {
      this.align(1);
      if (this.getMinorVersion() >= 2) {
         if ((c & 'ﾀ') == 0) {
            this.write_octet((byte)1);
         } else if ((c & '\uf800') == 0) {
            this.write_octet((byte)2);
         } else {
            this.write_octet((byte)3);
         }
      }

      this.writeUTF8Char(c);
   }

   public void write_wstring(String value) {
      if (value == null) {
         throw new MARSHAL("null value passes to write_wstring(String)");
      } else {
         byte[] buf = null;
         if (this.getMinorVersion() < 2) {
            GIOP10Helper.write_wstring(value, this, this.getWcharCodeSet(), this.littleEndian);
         } else {
            switch (this.getWcharCodeSet()) {
               case 65792:
                  this.writeStringUTF16(value, false);
                  break;
               case 65801:
                  this.writeStringUTF16(value, true);
                  break;
               case 83951617:
                  this.writeUTF8(value);
                  break;
               default:
                  throw new CODESET_INCOMPATIBLE();
            }

         }
      }
   }

   public final void writeChunks(Chunk cos) {
      this.current.end = Math.max(this.current.end, this.chunkPos);
      Chunk newhead = cos;

      int len;
      for(len = 0; cos.next != null; cos = cos.next) {
         len += cos.end;
      }

      len += cos.end;
      this.write_long(len);
      this.current.next = newhead;
      this.chunkPos = cos.end;
      this.current = cos;
      this.currentPos = this.currentPos + len - this.chunkPos;
   }

   public void write_boolean_array(boolean[] value, int offset, int length) {
      if (value == null) {
         throw new MARSHAL("null value passes to write_boolean_array");
      } else {
         for(int i = offset; i < length; ++i) {
            this.write_boolean(value[i]);
         }

      }
   }

   public void write_char_array(char[] value, int offset, int length) {
      if (value == null) {
         throw new MARSHAL("null value passes to write_char_array");
      } else {
         for(int i = offset; i < length; ++i) {
            this.write_char(value[i]);
         }

      }
   }

   public void write_wchar_array(char[] value, int offset, int length) {
      if (value == null) {
         throw new MARSHAL("null value passes to write_wchar_array");
      } else {
         for(int i = offset; i < length; ++i) {
            this.write_wchar(value[i]);
         }

      }
   }

   public void write_octet_array(byte[] value, int offset, int len) {
      if (value == null) {
         throw new MARSHAL("null array as parameter to write_octet_array");
      } else {
         if (this.needEightByteAlignment) {
            this.checkEightByteAlignment();
         }

         if (this.chunking && this.currentChunk == 0) {
            this.startChunk();
         }

         while(len > 0) {
            this.advance();
            int toCopy = min(Chunk.CHUNK_SIZE - this.chunkPos, len);
            System.arraycopy(value, offset, this.current.buf, this.chunkPos, toCopy);
            offset += toCopy;
            len -= toCopy;
            this.chunkPos += toCopy;
         }

      }
   }

   public void write_short_array(short[] value, int offset, int length) {
      if (value == null) {
         throw new MARSHAL("null value passes to write_short_array");
      } else {
         for(int i = offset; i < length; ++i) {
            this.write_short(value[i]);
         }

      }
   }

   public void write_ushort_array(short[] value, int offset, int length) {
      if (value == null) {
         throw new MARSHAL("null value passes to write_ushort_array");
      } else {
         for(int i = offset; i < length; ++i) {
            this.write_ushort(value[i]);
         }

      }
   }

   public void write_long_array(int[] value, int offset, int length) {
      if (value == null) {
         throw new MARSHAL("null value passes to write_long_array");
      } else {
         for(int i = offset; i < length; ++i) {
            this.write_long(value[i]);
         }

      }
   }

   public void write_ulong_array(int[] value, int offset, int length) {
      if (value == null) {
         throw new MARSHAL("null value passes to write_ulong_array");
      } else {
         for(int i = offset; i < length; ++i) {
            this.write_ulong(value[i]);
         }

      }
   }

   public void write_longlong_array(long[] value, int offset, int length) {
      if (value == null) {
         throw new MARSHAL("null value passes to write_longlong_array");
      } else {
         for(int i = offset; i < length; ++i) {
            this.write_longlong(value[i]);
         }

      }
   }

   public void write_ulonglong_array(long[] value, int offset, int length) {
      if (value == null) {
         throw new MARSHAL("null value passes to write_ulonglong_array");
      } else {
         for(int i = offset; i < length; ++i) {
            this.write_ulonglong(value[i]);
         }

      }
   }

   public void write_float_array(float[] value, int offset, int length) {
      if (value == null) {
         throw new MARSHAL("null value passes to write_float_array");
      } else {
         for(int i = offset; i < length; ++i) {
            this.write_float(value[i]);
         }

      }
   }

   public void write_double_array(double[] value, int offset, int length) {
      if (value == null) {
         throw new MARSHAL("null value passes to write_double_array");
      } else {
         for(int i = offset; i < length; ++i) {
            this.write_double(value[i]);
         }

      }
   }

   public void write_Object(org.omg.CORBA.Object value) {
      this.writeRemote(value);
   }

   private static IndirectionValueHashtable getHashMap() {
      IndirectionValueHashtable map = (IndirectionValueHashtable)mapPool.remove();
      if (map == null) {
         map = new IndirectionValueHashtable();
      }

      return map;
   }

   private static void releaseHashMap(IndirectionValueHashtable map) {
      if (map != null) {
         map.clear();
         mapPool.add(map);
      }

   }

   private static IndirectionHashtable getIndirectionHashMap() {
      IndirectionHashtable map = (IndirectionHashtable)mapPool2.remove();
      if (map == null) {
         map = new IndirectionHashtable();
      }

      return map;
   }

   private static void releaseHashMap(IndirectionHashtable map) {
      if (map != null) {
         map.clear();
         mapPool2.add(map);
      }

   }

   public void write_TypeCode(TypeCode value) {
      if (value == null) {
         this.write_long(0);
      } else {
         if (this.tcNestingLevel == 0 && this.tcIndirections != null) {
            this.tcIndirections.clear();
         }

         if (value.kind().value() > 13) {
            if (this.tcIndirections == null) {
               this.tcIndirections = getHashMap();
            }

            int offset;
            if ((offset = this.tcIndirections.get(value, 0)) >= 0) {
               this.write_ulong(-1);
               this.write_ulong(offset - this.pos());
               return;
            }

            this.align(4);
            this.tcIndirections.put(value, 0, this.pos(), offset);
         }

         ++this.tcNestingLevel;
         TypeCodeImpl.write(value, this);
         --this.tcNestingLevel;
      }
   }

   void writeAny(Object obj) {
      try {
         obj = IIOPReplacer.getIIOPReplacer().replaceObject(obj);
      } catch (IOException var3) {
         throw new MARSHAL("IOException writing Any " + var3.getMessage());
      }

      if (obj == null && this.useCompliantChunking()) {
         this.write_TypeCode(TypeCodeImpl.NULL);
         this.write_boolean(false);
         this.write_long(0);
      } else if (obj instanceof String) {
         String s = (String)obj;
         this.write_TypeCode(TypeCodeImpl.STRING);
         this.write_value(s);
      } else if (obj instanceof IOR) {
         this.write_TypeCode(TypeCodeImpl.OBJECT);
         ((IOR)obj).write(this);
      } else {
         this.write_TypeCode(new TypeCodeImpl(29, this.findRepositoryIdForValue((Serializable)obj), "", TypeCodeImpl.NULL_TC));
         this.write_value((Serializable)obj);
      }

   }

   public final void write_any(Any any) {
      if (any == null) {
         this.write_TypeCode((TypeCode)null);
      } else {
         this.write_TypeCode(any.type());
         any.write_value(this);
      }

   }

   public final void write_any(Any any, TypeCode type) {
      any.write_value(this);
   }

   public void write_Principal(Principal value) {
      throw new NO_IMPLEMENT("write_Principal not implemented");
   }

   public void write(int b) throws IOException {
      this.write_octet((byte)(b & 255));
   }

   public void write_fixed(BigDecimal value) {
      throw new NO_IMPLEMENT("write_fixed() not implemented");
   }

   public void write_Context(Context ctx, ContextList contexts) {
      throw new NO_IMPLEMENT("write_Context() not implemented");
   }

   public ORB orb() {
      return this.orb;
   }

   public Message getMessage() {
      return this.message;
   }

   public void setMessage(Message message) {
      this.message = message;
   }

   private boolean writeIndirectionMaybe(Serializable ser) {
      int i = this.indirections.get(ser, this.nestingLevel);
      if (i > 0) {
         this.write_long(-1);
         this.write_long(i - this.pos());
         return true;
      } else {
         this.align(4);
         this.indirections.put(ser, this.nestingLevel, this.pos(), i);
         return false;
      }
   }

   private void terminateChunk(int posSave) {
      int clen = posSave - this.currentChunk - 4;
      Debug.assertion(this.currentChunk > 0);
      if (this.currentChunkChunk != null) {
         this.currentChunkChunk.buf[this.currentChunkPos++] = (byte)(clen >>> 24);
         this.currentChunkChunk.buf[this.currentChunkPos++] = (byte)(clen >>> 16);
         this.currentChunkChunk.buf[this.currentChunkPos++] = (byte)(clen >>> 8);
         this.currentChunkChunk.buf[this.currentChunkPos++] = (byte)clen;
         this.currentChunkChunk = null;
         this.currentChunkPos = 0;
      } else {
         this.setPosition(this.currentChunk);
         this.write_slow_long(clen);
         this.setPosition(posSave);
      }

      this.currentChunk = 0;
   }

   private void startChunk() {
      this.chunking = false;
      this.align(4);
      this.currentChunk = this.pos();
      if (this.chunkPos + 4 <= Chunk.CHUNK_SIZE && !this.littleEndian) {
         this.currentChunkPos = this.chunkPos;
         this.currentChunkChunk = this.current;
         this.chunkPos += 4;
      } else {
         this.write_slow_long(0);
      }

      this.chunking = true;
   }

   private void endChunk() {
      int posSave = this.pos();
      this.chunking = false;
      if (this.lastEnd != posSave) {
         if (this.currentChunk > 0) {
            this.terminateChunk(posSave);
         }
      } else {
         this.setPosition(this.lastEnd - 4);
         this.currentChunk = 0;
         this.currentChunkChunk = null;
      }

      this.write_long(this.endTag);
      if (this.useCompliantChunking()) {
         ++this.endTag;
      }

      if (this.endTag > 0) {
         throw new AssertionError("Chunking error at " + this.pos() + ": end tags must not be +ve: " + this.endTag);
      } else {
         if (this.endTag == 0) {
            this.lastEnd = 0;
         } else {
            this.lastEnd = this.pos();
         }

      }
   }

   public void start_value(String repid) {
      Debug.assertion(repid != null);
      this.chunking = false;
      if (this.currentChunk != 0) {
         this.terminateChunk(this.pos());
      }

      this.write_long(2147483402);
      RepositoryId r = ClassInfo.getRepositoryId(repid);
      if (!this.writeIndirectionMaybe(r)) {
         this.write_repository_id(r);
      }

      this.chunking = true;
      --this.endTag;
   }

   public void end_value() {
      if (this.chunking) {
         this.endChunk();
      }

      this.chunking = true;
   }

   public void write_value(Serializable value) {
      if (value == null) {
         this.write_long(0);
      } else {
         if (this.indirections == null) {
            this.indirections = getHashMap();
         }

         int i = this.indirections.get(value, this.nestingLevel);
         if (i > 0) {
            this.write_long(-1);
            this.write_long(i - this.pos());
         } else {
            Class c = value.getClass();
            if (Proxy.class.isAssignableFrom(c)) {
               value = new ProxyDesc((Proxy)value);
               c = ProxyDesc.class;
            }

            ClassInfo cinfo = SentClassesRepository.findClassInfo(c);
            if (cinfo.isPortableReplaceable()) {
               try {
                  value = PortableReplacer.getReplacer().replaceObject((Serializable)value);
                  c = value.getClass();
                  cinfo = SentClassesRepository.findClassInfo(c);
               } catch (IOException var15) {
                  throw new MARSHAL("IOException writing PortableReplaceable " + var15.getMessage());
               }
            }

            Serializable value = cinfo.writeReplace(value);
            if (value.getClass() != c) {
               c = value.getClass();
               cinfo = SentClassesRepository.findClassInfo(c);
            }

            boolean stillChunking = this.chunking;
            boolean needsChunking = this.chunking || cinfo.getDescriptor().isCustomMarshaled();
            this.chunking = false;
            if (stillChunking && this.currentChunk != 0) {
               this.terminateChunk(this.pos());
            }

            int valueTag = needsChunking ? 2147483402 : 2147483394;
            RepositoryId[] repids = cinfo.getRepositoryIdList();
            if (repids != null && repids.length > 1) {
               valueTag |= 6;
            }

            boolean writeCodebase = false;
            String codebase = null;
            if (writeCodebase) {
               String app = Utils.getAnnotation(cinfo.forClass().getClassLoader());
               if (app != null) {
                  codebase = Codebase.getDefaultCodebase() + app + '/';
                  valueTag |= 1;
               }
            }

            this.write_long(valueTag);
            this.indirections.put(value, this.nestingLevel, this.pos() - 4, i);
            if (codebase != null && !this.writeIndirectionMaybe(codebase)) {
               this.write_string(codebase);
            }

            RepositoryId repid = cinfo.getRepositoryId();
            if (repids != null) {
               this.writeRepositoryIdList(repids);
            } else {
               if (cinfo.isValueBase()) {
                  repid = new RepositoryId(((ValueBase)value)._truncatable_ids()[0]);
               }

               if (repid == null || repid.length() == 0 || !this.writeIndirectionMaybe(repid)) {
                  this.write_repository_id(repid);
               }
            }

            this.chunking = needsChunking;
            if (needsChunking || !this.useCompliantChunking()) {
               --this.endTag;
            }

            if (cinfo.isEnum()) {
               this.write_string(value.toString());
            } else if (cinfo.isString()) {
               this.write_wstring((String)value);
            } else if (repid.isClassDesc()) {
               this.write_value("");
               this.write_value(SentClassesRepository.findClassInfo((Class)value).getRepositoryId().toString());
            } else if (cinfo.isIDLEntity() && !org.omg.CORBA.Object.class.isAssignableFrom(c)) {
               this.write_IDLValue(value, c);
            } else if (cinfo.isStreamable()) {
               ((Streamable)value)._write(this);
            } else {
               try {
                  ValueHandlerImpl.writeValue(this, value, this.maxFormatVersion, cinfo);
               } catch (IOException var14) {
                  throw (MARSHAL)(new MARSHAL(var14.getMessage())).initCause(var14);
               }
            }

            if (this.chunking) {
               this.endChunk();
            }

            if (!this.useCompliantChunking()) {
               ++this.endTag;
            }

            this.chunking = stillChunking;
         }
      }
   }

   public void write_value(Serializable value, Class clz) {
      if (clz.equals(AuthenticatedUser.class) && AuthenticatedSubject.class.isInstance(value)) {
         value = SecurityServiceManager.convertToAuthenticatedUser((AuthenticatedUser)value);
         if (value == null) {
            this.write_long(0);
            return;
         }
      }

      this.write_value((Serializable)value);
   }

   public void write_value(Serializable value, String repository_id) {
      this.write_value(value);
   }

   public RepositoryId findRepositoryIdForValue(Serializable value) {
      Class c = value.getClass();
      ClassInfo cinfo = SentClassesRepository.findClassInfo(c);
      if (cinfo.isPortableReplaceable()) {
         try {
            value = PortableReplacer.getReplacer().replaceObject(value);
            c = value.getClass();
            cinfo = SentClassesRepository.findClassInfo(c);
         } catch (IOException var5) {
            throw new MARSHAL("IOException writing value " + var5.getMessage());
         }
      }

      value = cinfo.writeReplace(value);
      if (value.getClass() != c) {
         c = value.getClass();
         cinfo = SentClassesRepository.findClassInfo(c);
      }

      return cinfo.isValueBase() ? new RepositoryId(((ValueBase)value)._truncatable_ids()[0]) : cinfo.getRepositoryId();
   }

   public void write_value(Serializable value, BoxedValueHelper factory) {
      this.write_value(value);
   }

   public void write_abstract_interface(Object obj) {
      if ((obj instanceof Remote || obj instanceof org.omg.CORBA.Object || obj instanceof InvokeHandler || obj instanceof IOR) && !(obj instanceof Proxy)) {
         this.write_boolean(true);
         this.writeRemote(obj);
      } else {
         this.write_boolean(false);
         this.write_value((Serializable)obj);
      }

   }

   private void write_IDLValue(Serializable s, Class c) {
      try {
         if (s instanceof StreamableValue) {
            ((StreamableValue)s)._write(this);
         } else {
            if (s instanceof CustomValue) {
               throw new MARSHAL("Custom marshalled valuetypes not supported");
            }

            if (s instanceof ValueBase) {
               BoxedValueHelper helper = (BoxedValueHelper)Utils.getHelper(c, "Helper").newInstance();
               helper.write_value(this, s);
            } else {
               this.write_IDLEntity(s, c);
            }
         }

      } catch (IllegalAccessException | InstantiationException var4) {
         throw new MARSHAL(var4.getMessage());
      }
   }

   public void write_IDLEntity(Object o, Class c) {
      write_IDLEntity(this, o, c);
   }

   public static void write_IDLEntity(org.omg.CORBA.portable.OutputStream out, Object o, Class c) {
      Debug.assertion(IDLEntity.class.isAssignableFrom(c));
      Method m = Utils.getIDLWriter(c);
      if (m == null) {
         throw new MARSHAL("Couldn't find helper for " + c.getName());
      } else {
         writeWithHelper(out, o, m);
      }
   }

   public static void writeWithHelper(org.omg.CORBA.portable.OutputStream out, Object o, Method writer) {
      try {
         writer.invoke((Object)null, out, o);
      } catch (IllegalAccessException var5) {
         throw new MARSHAL(var5.getMessage());
      } catch (InvocationTargetException var6) {
         MARSHAL m = new MARSHAL(var6.getTargetException().getMessage());
         m.initCause(var6.getTargetException());
         throw m;
      }
   }

   public void writeRemote(Object o) {
      if (o == null) {
         IOR.NULL.write(this);
      } else {
         IOR ior;
         try {
            ior = IIOPReplacer.getIIOPReplacer().replaceRemote(o);
         } catch (IOException var4) {
            IIOPLogger.logFailedToExport(o.getClass().getName(), var4);
            throw new MARSHAL("Couldn't export " + o.getClass().getName(), 0, CompletionStatus.COMPLETED_MAYBE);
         }

         ior.write(this);
      }
   }

   public void write_octet_sequence(byte[] value) {
      if (value == null) {
         this.write_long(0);
      } else {
         this.write_long(value.length);
         this.write_octet_array(value, 0, value.length);
      }

   }

   public final void write(byte[] b) throws IOException {
      if (b == null) {
         throw new MARSHAL("null array as parameter to readFully");
      } else {
         this.write(b, 0, b.length);
      }
   }

   public final void write(byte[] b, int i, int j) throws IOException {
      this.write_octet_array(b, i, j);
   }

   public final void writeBoolean(boolean b) throws IOException {
      this.write_boolean(b);
   }

   public final void writeByte(int b) throws IOException {
      this.write_octet((byte)(b & 255));
   }

   public final void writeShort(int s) throws IOException {
      this.write_short((short)(s & '\uffff'));
   }

   public final void writeChar(int c) throws IOException {
      this.write_wchar((char)(c & '\uffff'));
   }

   public final void writeInt(int v) throws IOException {
      this.write_long(v);
   }

   public final void writeLong(long l) throws IOException {
      this.write_longlong(l);
   }

   public final void writeFloat(float f) throws IOException {
      this.write_float(f);
   }

   public final void writeDouble(double d) throws IOException {
      this.write_double(d);
   }

   public final void writeBytes(String s) throws IOException {
      this.writeASCII(s);
   }

   public final void writeChars(String s) throws IOException {
      char[] c = s.toCharArray();
      this.write_wchar_array(c, 0, c.length);
   }

   public final void writeUTF(String s) throws IOException {
      this.write_wstring(s);
   }

   public final void writeObject(Object obj) throws IOException {
      this.write_abstract_interface(obj);
   }

   public final void writeObject(Object obj, Class c) throws IOException {
      try {
         if (!Remote.class.isAssignableFrom(c) && !InvokeHandler.class.isAssignableFrom(c) && !org.omg.CORBA.Object.class.isAssignableFrom(c)) {
            if (!c.equals(Object.class) && !c.equals(Serializable.class) && !c.equals(Externalizable.class)) {
               if (Utils.isIDLException(c)) {
                  this.write_IDLEntity(obj, c);
               } else if (Utils.isAbstractInterface(c)) {
                  this.write_abstract_interface(obj);
               } else {
                  obj = RemoteObjectReplacer.getReplacer().replaceObject(obj);
                  if (USE_JAVA_SERIALIZATION_FOR_JMX && MBeanInfo.class.isAssignableFrom(c)) {
                     obj = new MarshalledObject(obj);
                     c = MarshalledObject.class;
                  }

                  this.write_value((Serializable)obj, c);
               }
            } else {
               this.writeAny(obj);
            }
         } else {
            this.writeRemote(obj);
         }

      } catch (SystemException var4) {
         throw CorbaUtils.mapSystemException(var4);
      }
   }

   public final void flush() {
      this.current.end = this.chunkPos;
   }

   void p(String msg) {
      System.err.println("<IIOPOutputStream>: " + msg);
   }

   public void setLittleEndian(boolean littleEndian) {
      this.littleEndian = littleEndian;
   }

   static {
      String prop = System.getProperty("weblogic.iiop.useJavaSerializationForJMX", "true");
      USE_JAVA_SERIALIZATION_FOR_JMX = Boolean.valueOf(prop);
      mapPool = new StackPool(1024);
      mapPool2 = new StackPool(1024);
   }

   static class Marker {
      Chunk chunk;
      int pos;
      int currentPos;
   }

   private static class EncapsulationWrapper {
      private int encapLength;
      private int endTag;
      private int chunkLength;
      private boolean chunking;

      EncapsulationWrapper(int encapLength, boolean chunking, int chunkLength, int endTag) {
         this.encapLength = encapLength;
         this.chunking = chunking;
         this.chunkLength = chunkLength;
         this.endTag = endTag;
      }
   }
}
