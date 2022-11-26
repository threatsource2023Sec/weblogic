package com.trilead.ssh2;

import com.trilead.ssh2.packets.TypesReader;
import com.trilead.ssh2.packets.TypesWriter;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Vector;

public class SFTPv3Client {
   final Connection conn;
   final Session sess;
   final PrintStream debug;
   boolean flag_closed;
   InputStream is;
   OutputStream os;
   int protocol_version;
   HashMap server_extensions;
   int next_request_id;
   String charsetName;

   /** @deprecated */
   public SFTPv3Client(Connection conn, PrintStream debug) throws IOException {
      this.flag_closed = false;
      this.protocol_version = 0;
      this.server_extensions = new HashMap();
      this.next_request_id = 1000;
      this.charsetName = null;
      if (conn == null) {
         throw new IllegalArgumentException("Cannot accept null argument!");
      } else {
         this.conn = conn;
         this.debug = debug;
         if (debug != null) {
            debug.println("Opening session and starting SFTP subsystem.");
         }

         this.sess = conn.openSession();
         this.sess.startSubSystem("sftp");
         this.is = this.sess.getStdout();
         this.os = new BufferedOutputStream(this.sess.getStdin(), 2048);
         if (this.is != null && this.os != null) {
            this.init();
         } else {
            throw new IOException("There is a problem with the streams of the underlying channel.");
         }
      }
   }

   public SFTPv3Client(Connection conn) throws IOException {
      this(conn, (PrintStream)null);
   }

   public void setCharset(String charset) throws IOException {
      if (charset == null) {
         this.charsetName = charset;
      } else {
         try {
            Charset.forName(charset);
         } catch (Exception var3) {
            throw (IOException)(new IOException("This charset is not supported")).initCause(var3);
         }

         this.charsetName = charset;
      }
   }

   public String getCharset() {
      return this.charsetName;
   }

   private final void checkHandleValidAndOpen(SFTPv3FileHandle handle) throws IOException {
      if (handle.client != this) {
         throw new IOException("The file handle was created with another SFTPv3FileHandle instance.");
      } else if (handle.isClosed) {
         throw new IOException("The file handle is closed.");
      }
   }

   private final void sendMessage(int type, int requestId, byte[] msg, int off, int len) throws IOException {
      int msglen = len + 1;
      if (type != 1) {
         msglen += 4;
      }

      this.os.write(msglen >> 24);
      this.os.write(msglen >> 16);
      this.os.write(msglen >> 8);
      this.os.write(msglen);
      this.os.write(type);
      if (type != 1) {
         this.os.write(requestId >> 24);
         this.os.write(requestId >> 16);
         this.os.write(requestId >> 8);
         this.os.write(requestId);
      }

      this.os.write(msg, off, len);
      this.os.flush();
   }

   private final void sendMessage(int type, int requestId, byte[] msg) throws IOException {
      this.sendMessage(type, requestId, msg, 0, msg.length);
   }

   private final void readBytes(byte[] buff, int pos, int len) throws IOException {
      while(true) {
         if (len > 0) {
            int count = this.is.read(buff, pos, len);
            if (count < 0) {
               throw new IOException("Unexpected end of sftp stream.");
            }

            if (count != 0 && count <= len) {
               len -= count;
               pos += count;
               continue;
            }

            throw new IOException("Underlying stream implementation is bogus!");
         }

         return;
      }
   }

   private final byte[] receiveMessage(int maxlen) throws IOException {
      byte[] msglen = new byte[4];
      this.readBytes(msglen, 0, 4);
      int len = (msglen[0] & 255) << 24 | (msglen[1] & 255) << 16 | (msglen[2] & 255) << 8 | msglen[3] & 255;
      if (len <= maxlen && len > 0) {
         byte[] msg = new byte[len];
         this.readBytes(msg, 0, len);
         return msg;
      } else {
         throw new IOException("Illegal sftp packet len: " + len);
      }
   }

   private final int generateNextRequestID() {
      synchronized(this) {
         return this.next_request_id++;
      }
   }

   private final void closeHandle(byte[] handle) throws IOException {
      int req_id = this.generateNextRequestID();
      TypesWriter tw = new TypesWriter();
      tw.writeString(handle, 0, handle.length);
      this.sendMessage(4, req_id, tw.getBytes());
      this.expectStatusOKMessage(req_id);
   }

   private SFTPv3FileAttributes readAttrs(TypesReader tr) throws IOException {
      SFTPv3FileAttributes fa = new SFTPv3FileAttributes();
      int flags = tr.readUINT32();
      if ((flags & 1) != 0) {
         if (this.debug != null) {
            this.debug.println("SSH_FILEXFER_ATTR_SIZE");
         }

         fa.size = new Long(tr.readUINT64());
      }

      if ((flags & 2) != 0) {
         if (this.debug != null) {
            this.debug.println("SSH_FILEXFER_ATTR_V3_UIDGID");
         }

         fa.uid = new Integer(tr.readUINT32());
         fa.gid = new Integer(tr.readUINT32());
      }

      if ((flags & 4) != 0) {
         if (this.debug != null) {
            this.debug.println("SSH_FILEXFER_ATTR_PERMISSIONS");
         }

         fa.permissions = new Integer(tr.readUINT32());
      }

      if ((flags & 8) != 0) {
         if (this.debug != null) {
            this.debug.println("SSH_FILEXFER_ATTR_V3_ACMODTIME");
         }

         fa.atime = new Integer(tr.readUINT32());
         fa.mtime = new Integer(tr.readUINT32());
      }

      if ((flags & Integer.MIN_VALUE) != 0) {
         int count = tr.readUINT32();
         if (this.debug != null) {
            this.debug.println("SSH_FILEXFER_ATTR_EXTENDED (" + count + ")");
         }

         while(count > 0) {
            tr.readByteString();
            tr.readByteString();
            --count;
         }
      }

      return fa;
   }

   public SFTPv3FileAttributes fstat(SFTPv3FileHandle handle) throws IOException {
      this.checkHandleValidAndOpen(handle);
      int req_id = this.generateNextRequestID();
      TypesWriter tw = new TypesWriter();
      tw.writeString(handle.fileHandle, 0, handle.fileHandle.length);
      if (this.debug != null) {
         this.debug.println("Sending SSH_FXP_FSTAT...");
         this.debug.flush();
      }

      this.sendMessage(8, req_id, tw.getBytes());
      byte[] resp = this.receiveMessage(34000);
      if (this.debug != null) {
         this.debug.println("Got REPLY.");
         this.debug.flush();
      }

      TypesReader tr = new TypesReader(resp);
      int t = tr.readByte();
      int rep_id = tr.readUINT32();
      if (rep_id != req_id) {
         throw new IOException("The server sent an invalid id field.");
      } else if (t == 105) {
         return this.readAttrs(tr);
      } else if (t != 101) {
         throw new IOException("The SFTP server sent an unexpected packet type (" + t + ")");
      } else {
         int errorCode = tr.readUINT32();
         throw new SFTPException(tr.readString(), errorCode);
      }
   }

   private SFTPv3FileAttributes statBoth(String path, int statMethod) throws IOException {
      int req_id = this.generateNextRequestID();
      TypesWriter tw = new TypesWriter();
      tw.writeString(path, this.charsetName);
      if (this.debug != null) {
         this.debug.println("Sending SSH_FXP_STAT/SSH_FXP_LSTAT...");
         this.debug.flush();
      }

      this.sendMessage(statMethod, req_id, tw.getBytes());
      byte[] resp = this.receiveMessage(34000);
      if (this.debug != null) {
         this.debug.println("Got REPLY.");
         this.debug.flush();
      }

      TypesReader tr = new TypesReader(resp);
      int t = tr.readByte();
      int rep_id = tr.readUINT32();
      if (rep_id != req_id) {
         throw new IOException("The server sent an invalid id field.");
      } else if (t == 105) {
         return this.readAttrs(tr);
      } else if (t != 101) {
         throw new IOException("The SFTP server sent an unexpected packet type (" + t + ")");
      } else {
         int errorCode = tr.readUINT32();
         throw new SFTPException(tr.readString(), errorCode);
      }
   }

   public SFTPv3FileAttributes stat(String path) throws IOException {
      return this.statBoth(path, 17);
   }

   public SFTPv3FileAttributes lstat(String path) throws IOException {
      return this.statBoth(path, 7);
   }

   public String readLink(String path) throws IOException {
      int req_id = this.generateNextRequestID();
      TypesWriter tw = new TypesWriter();
      tw.writeString(path, this.charsetName);
      if (this.debug != null) {
         this.debug.println("Sending SSH_FXP_READLINK...");
         this.debug.flush();
      }

      this.sendMessage(19, req_id, tw.getBytes());
      byte[] resp = this.receiveMessage(34000);
      if (this.debug != null) {
         this.debug.println("Got REPLY.");
         this.debug.flush();
      }

      TypesReader tr = new TypesReader(resp);
      int t = tr.readByte();
      int rep_id = tr.readUINT32();
      if (rep_id != req_id) {
         throw new IOException("The server sent an invalid id field.");
      } else {
         int errorCode;
         if (t == 104) {
            errorCode = tr.readUINT32();
            if (errorCode != 1) {
               throw new IOException("The server sent an invalid SSH_FXP_NAME packet.");
            } else {
               return tr.readString(this.charsetName);
            }
         } else if (t != 101) {
            throw new IOException("The SFTP server sent an unexpected packet type (" + t + ")");
         } else {
            errorCode = tr.readUINT32();
            throw new SFTPException(tr.readString(), errorCode);
         }
      }
   }

   private void expectStatusOKMessage(int id) throws IOException {
      byte[] resp = this.receiveMessage(34000);
      if (this.debug != null) {
         this.debug.println("Got REPLY.");
         this.debug.flush();
      }

      TypesReader tr = new TypesReader(resp);
      int t = tr.readByte();
      int rep_id = tr.readUINT32();
      if (rep_id != id) {
         throw new IOException("The server sent an invalid id field.");
      } else if (t != 101) {
         throw new IOException("The SFTP server sent an unexpected packet type (" + t + ")");
      } else {
         int errorCode = tr.readUINT32();
         if (errorCode != 0) {
            throw new SFTPException(tr.readString(), errorCode);
         }
      }
   }

   public void setstat(String path, SFTPv3FileAttributes attr) throws IOException {
      int req_id = this.generateNextRequestID();
      TypesWriter tw = new TypesWriter();
      tw.writeString(path, this.charsetName);
      tw.writeBytes(this.createAttrs(attr));
      if (this.debug != null) {
         this.debug.println("Sending SSH_FXP_SETSTAT...");
         this.debug.flush();
      }

      this.sendMessage(9, req_id, tw.getBytes());
      this.expectStatusOKMessage(req_id);
   }

   public void fsetstat(SFTPv3FileHandle handle, SFTPv3FileAttributes attr) throws IOException {
      this.checkHandleValidAndOpen(handle);
      int req_id = this.generateNextRequestID();
      TypesWriter tw = new TypesWriter();
      tw.writeString(handle.fileHandle, 0, handle.fileHandle.length);
      tw.writeBytes(this.createAttrs(attr));
      if (this.debug != null) {
         this.debug.println("Sending SSH_FXP_FSETSTAT...");
         this.debug.flush();
      }

      this.sendMessage(10, req_id, tw.getBytes());
      this.expectStatusOKMessage(req_id);
   }

   public void createSymlink(String src, String target) throws IOException {
      int req_id = this.generateNextRequestID();
      TypesWriter tw = new TypesWriter();
      tw.writeString(target, this.charsetName);
      tw.writeString(src, this.charsetName);
      if (this.debug != null) {
         this.debug.println("Sending SSH_FXP_SYMLINK...");
         this.debug.flush();
      }

      this.sendMessage(20, req_id, tw.getBytes());
      this.expectStatusOKMessage(req_id);
   }

   public String canonicalPath(String path) throws IOException {
      int req_id = this.generateNextRequestID();
      TypesWriter tw = new TypesWriter();
      tw.writeString(path, this.charsetName);
      if (this.debug != null) {
         this.debug.println("Sending SSH_FXP_REALPATH...");
         this.debug.flush();
      }

      this.sendMessage(16, req_id, tw.getBytes());
      byte[] resp = this.receiveMessage(34000);
      if (this.debug != null) {
         this.debug.println("Got REPLY.");
         this.debug.flush();
      }

      TypesReader tr = new TypesReader(resp);
      int t = tr.readByte();
      int rep_id = tr.readUINT32();
      if (rep_id != req_id) {
         throw new IOException("The server sent an invalid id field.");
      } else {
         int errorCode;
         if (t == 104) {
            errorCode = tr.readUINT32();
            if (errorCode != 1) {
               throw new IOException("The server sent an invalid SSH_FXP_NAME packet.");
            } else {
               return tr.readString(this.charsetName);
            }
         } else if (t != 101) {
            throw new IOException("The SFTP server sent an unexpected packet type (" + t + ")");
         } else {
            errorCode = tr.readUINT32();
            throw new SFTPException(tr.readString(), errorCode);
         }
      }
   }

   private final Vector scanDirectory(byte[] handle) throws IOException {
      Vector files = new Vector();

      while(true) {
         int req_id = this.generateNextRequestID();
         TypesWriter tw = new TypesWriter();
         tw.writeString(handle, 0, handle.length);
         if (this.debug != null) {
            this.debug.println("Sending SSH_FXP_READDIR...");
            this.debug.flush();
         }

         this.sendMessage(12, req_id, tw.getBytes());
         byte[] resp = this.receiveMessage(65536);
         if (this.debug != null) {
            this.debug.println("Got REPLY.");
            this.debug.flush();
         }

         TypesReader tr = new TypesReader(resp);
         int t = tr.readByte();
         int rep_id = tr.readUINT32();
         if (rep_id != req_id) {
            throw new IOException("The server sent an invalid id field.");
         }

         int errorCode;
         if (t != 104) {
            if (t != 101) {
               throw new IOException("The SFTP server sent an unexpected packet type (" + t + ")");
            }

            errorCode = tr.readUINT32();
            if (errorCode == 1) {
               return files;
            }

            throw new SFTPException(tr.readString(), errorCode);
         }

         errorCode = tr.readUINT32();
         if (this.debug != null) {
            this.debug.println("Parsing " + errorCode + " name entries...");
         }

         for(; errorCode > 0; --errorCode) {
            SFTPv3DirectoryEntry dirEnt = new SFTPv3DirectoryEntry();
            dirEnt.filename = tr.readString(this.charsetName);
            dirEnt.longEntry = tr.readString(this.charsetName);
            dirEnt.attributes = this.readAttrs(tr);
            files.addElement(dirEnt);
            if (this.debug != null) {
               this.debug.println("File: '" + dirEnt.filename + "'");
            }
         }
      }
   }

   private final byte[] openDirectory(String path) throws IOException {
      int req_id = this.generateNextRequestID();
      TypesWriter tw = new TypesWriter();
      tw.writeString(path, this.charsetName);
      if (this.debug != null) {
         this.debug.println("Sending SSH_FXP_OPENDIR...");
         this.debug.flush();
      }

      this.sendMessage(11, req_id, tw.getBytes());
      byte[] resp = this.receiveMessage(34000);
      TypesReader tr = new TypesReader(resp);
      int t = tr.readByte();
      int rep_id = tr.readUINT32();
      if (rep_id != req_id) {
         throw new IOException("The server sent an invalid id field.");
      } else if (t == 102) {
         if (this.debug != null) {
            this.debug.println("Got SSH_FXP_HANDLE.");
            this.debug.flush();
         }

         byte[] handle = tr.readByteString();
         return handle;
      } else if (t != 101) {
         throw new IOException("The SFTP server sent an unexpected packet type (" + t + ")");
      } else {
         int errorCode = tr.readUINT32();
         String errorMessage = tr.readString();
         throw new SFTPException(errorMessage, errorCode);
      }
   }

   private final String expandString(byte[] b, int off, int len) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < len; ++i) {
         int c = b[off + i] & 255;
         if (c >= 32 && c <= 126) {
            sb.append((char)c);
         } else {
            sb.append("{0x" + Integer.toHexString(c) + "}");
         }
      }

      return sb.toString();
   }

   private void init() throws IOException {
      int client_version = true;
      if (this.debug != null) {
         this.debug.println("Sending SSH_FXP_INIT (3)...");
      }

      TypesWriter tw = new TypesWriter();
      tw.writeUINT32(3);
      this.sendMessage(1, 0, tw.getBytes());
      if (this.debug != null) {
         this.debug.println("Waiting for SSH_FXP_VERSION...");
      }

      TypesReader tr = new TypesReader(this.receiveMessage(34000));
      int type = tr.readByte();
      if (type != 2) {
         throw new IOException("The server did not send a SSH_FXP_VERSION packet (got " + type + ")");
      } else {
         this.protocol_version = tr.readUINT32();
         if (this.debug != null) {
            this.debug.println("SSH_FXP_VERSION: protocol_version = " + this.protocol_version);
         }

         if (this.protocol_version != 3) {
            throw new IOException("Server version " + this.protocol_version + " is currently not supported");
         } else {
            while(tr.remain() != 0) {
               String name = tr.readString();
               byte[] value = tr.readByteString();
               this.server_extensions.put(name, value);
               if (this.debug != null) {
                  this.debug.println("SSH_FXP_VERSION: extension: " + name + " = '" + this.expandString(value, 0, value.length) + "'");
               }
            }

         }
      }
   }

   public int getProtocolVersion() {
      return this.protocol_version;
   }

   public void close() {
      this.sess.close();
   }

   public Vector ls(String dirName) throws IOException {
      byte[] handle = this.openDirectory(dirName);
      Vector result = this.scanDirectory(handle);
      this.closeHandle(handle);
      return result;
   }

   public void mkdir(String dirName, int posixPermissions) throws IOException {
      int req_id = this.generateNextRequestID();
      TypesWriter tw = new TypesWriter();
      tw.writeString(dirName, this.charsetName);
      tw.writeUINT32(4);
      tw.writeUINT32(posixPermissions);
      this.sendMessage(14, req_id, tw.getBytes());
      this.expectStatusOKMessage(req_id);
   }

   public void rm(String fileName) throws IOException {
      int req_id = this.generateNextRequestID();
      TypesWriter tw = new TypesWriter();
      tw.writeString(fileName, this.charsetName);
      this.sendMessage(13, req_id, tw.getBytes());
      this.expectStatusOKMessage(req_id);
   }

   public void rmdir(String dirName) throws IOException {
      int req_id = this.generateNextRequestID();
      TypesWriter tw = new TypesWriter();
      tw.writeString(dirName, this.charsetName);
      this.sendMessage(15, req_id, tw.getBytes());
      this.expectStatusOKMessage(req_id);
   }

   public void mv(String oldPath, String newPath) throws IOException {
      int req_id = this.generateNextRequestID();
      TypesWriter tw = new TypesWriter();
      tw.writeString(oldPath, this.charsetName);
      tw.writeString(newPath, this.charsetName);
      this.sendMessage(18, req_id, tw.getBytes());
      this.expectStatusOKMessage(req_id);
   }

   public SFTPv3FileHandle openFileRO(String fileName) throws IOException {
      return this.openFile(fileName, 1, (SFTPv3FileAttributes)null);
   }

   public SFTPv3FileHandle openFileRW(String fileName) throws IOException {
      return this.openFile(fileName, 3, (SFTPv3FileAttributes)null);
   }

   public SFTPv3FileHandle createFile(String fileName) throws IOException {
      return this.createFile(fileName, (SFTPv3FileAttributes)null);
   }

   public SFTPv3FileHandle createFile(String fileName, SFTPv3FileAttributes attr) throws IOException {
      return this.openFile(fileName, 11, attr);
   }

   public SFTPv3FileHandle createFileTruncate(String fileName) throws IOException {
      return this.createFileTruncate(fileName, (SFTPv3FileAttributes)null);
   }

   public SFTPv3FileHandle createFileTruncate(String fileName, SFTPv3FileAttributes attr) throws IOException {
      return this.openFile(fileName, 27, attr);
   }

   private byte[] createAttrs(SFTPv3FileAttributes attr) {
      TypesWriter tw = new TypesWriter();
      int attrFlags = 0;
      if (attr == null) {
         tw.writeUINT32(0);
      } else {
         if (attr.size != null) {
            attrFlags |= 1;
         }

         if (attr.uid != null && attr.gid != null) {
            attrFlags |= 2;
         }

         if (attr.permissions != null) {
            attrFlags |= 4;
         }

         if (attr.atime != null && attr.mtime != null) {
            attrFlags |= 8;
         }

         tw.writeUINT32(attrFlags);
         if (attr.size != null) {
            tw.writeUINT64(attr.size);
         }

         if (attr.uid != null && attr.gid != null) {
            tw.writeUINT32(attr.uid);
            tw.writeUINT32(attr.gid);
         }

         if (attr.permissions != null) {
            tw.writeUINT32(attr.permissions);
         }

         if (attr.atime != null && attr.mtime != null) {
            tw.writeUINT32(attr.atime);
            tw.writeUINT32(attr.mtime);
         }
      }

      return tw.getBytes();
   }

   private SFTPv3FileHandle openFile(String fileName, int flags, SFTPv3FileAttributes attr) throws IOException {
      int req_id = this.generateNextRequestID();
      TypesWriter tw = new TypesWriter();
      tw.writeString(fileName, this.charsetName);
      tw.writeUINT32(flags);
      tw.writeBytes(this.createAttrs(attr));
      if (this.debug != null) {
         this.debug.println("Sending SSH_FXP_OPEN...");
         this.debug.flush();
      }

      this.sendMessage(3, req_id, tw.getBytes());
      byte[] resp = this.receiveMessage(34000);
      TypesReader tr = new TypesReader(resp);
      int t = tr.readByte();
      int rep_id = tr.readUINT32();
      if (rep_id != req_id) {
         throw new IOException("The server sent an invalid id field.");
      } else if (t == 102) {
         if (this.debug != null) {
            this.debug.println("Got SSH_FXP_HANDLE.");
            this.debug.flush();
         }

         return new SFTPv3FileHandle(this, tr.readByteString());
      } else if (t != 101) {
         throw new IOException("The SFTP server sent an unexpected packet type (" + t + ")");
      } else {
         int errorCode = tr.readUINT32();
         String errorMessage = tr.readString();
         throw new SFTPException(errorMessage, errorCode);
      }
   }

   public int read(SFTPv3FileHandle handle, long fileOffset, byte[] dst, int dstoff, int len) throws IOException {
      this.checkHandleValidAndOpen(handle);
      if (len <= 32768 && len > 0) {
         int req_id = this.generateNextRequestID();
         TypesWriter tw = new TypesWriter();
         tw.writeString(handle.fileHandle, 0, handle.fileHandle.length);
         tw.writeUINT64(fileOffset);
         tw.writeUINT32(len);
         if (this.debug != null) {
            this.debug.println("Sending SSH_FXP_READ...");
            this.debug.flush();
         }

         this.sendMessage(5, req_id, tw.getBytes());
         byte[] resp = this.receiveMessage(34000);
         TypesReader tr = new TypesReader(resp);
         int t = tr.readByte();
         int rep_id = tr.readUINT32();
         if (rep_id != req_id) {
            throw new IOException("The server sent an invalid id field.");
         } else {
            int errorCode;
            if (t == 103) {
               if (this.debug != null) {
                  this.debug.println("Got SSH_FXP_DATA...");
                  this.debug.flush();
               }

               errorCode = tr.readUINT32();
               if (errorCode >= 0 && errorCode <= len) {
                  tr.readBytes(dst, dstoff, errorCode);
                  return errorCode;
               } else {
                  throw new IOException("The server sent an invalid length field.");
               }
            } else if (t != 101) {
               throw new IOException("The SFTP server sent an unexpected packet type (" + t + ")");
            } else {
               errorCode = tr.readUINT32();
               if (errorCode == 1) {
                  if (this.debug != null) {
                     this.debug.println("Got SSH_FX_EOF.");
                     this.debug.flush();
                  }

                  return -1;
               } else {
                  String errorMessage = tr.readString();
                  throw new SFTPException(errorMessage, errorCode);
               }
            }
         }
      } else {
         throw new IllegalArgumentException("invalid len argument");
      }
   }

   public void write(SFTPv3FileHandle handle, long fileOffset, byte[] src, int srcoff, int len) throws IOException {
      this.checkHandleValidAndOpen(handle);

      TypesReader tr;
      int errorCode;
      do {
         if (len <= 0) {
            return;
         }

         int writeRequestLen = len;
         if (len > 32768) {
            writeRequestLen = 32768;
         }

         int req_id = this.generateNextRequestID();
         TypesWriter tw = new TypesWriter();
         tw.writeString(handle.fileHandle, 0, handle.fileHandle.length);
         tw.writeUINT64(fileOffset);
         tw.writeString(src, srcoff, writeRequestLen);
         if (this.debug != null) {
            this.debug.println("Sending SSH_FXP_WRITE...");
            this.debug.flush();
         }

         this.sendMessage(6, req_id, tw.getBytes());
         fileOffset += (long)writeRequestLen;
         srcoff += writeRequestLen;
         len -= writeRequestLen;
         byte[] resp = this.receiveMessage(34000);
         tr = new TypesReader(resp);
         int t = tr.readByte();
         int rep_id = tr.readUINT32();
         if (rep_id != req_id) {
            throw new IOException("The server sent an invalid id field.");
         }

         if (t != 101) {
            throw new IOException("The SFTP server sent an unexpected packet type (" + t + ")");
         }

         errorCode = tr.readUINT32();
      } while(errorCode == 0);

      String errorMessage = tr.readString();
      throw new SFTPException(errorMessage, errorCode);
   }

   public void closeFile(SFTPv3FileHandle handle) throws IOException {
      if (handle == null) {
         throw new IllegalArgumentException("the handle argument may not be null");
      } else {
         try {
            if (!handle.isClosed) {
               this.closeHandle(handle.fileHandle);
            }
         } finally {
            handle.isClosed = true;
         }

      }
   }

   public boolean exists(String path) throws IOException {
      return this._stat(path) != null;
   }

   public SFTPv3FileAttributes _stat(String path) throws IOException {
      try {
         return this.stat(path);
      } catch (SFTPException var4) {
         int c = var4.getServerErrorCode();
         if (c != 2 && c != 10) {
            throw var4;
         } else {
            return null;
         }
      }
   }

   public void mkdirs(String path, int posixPermission) throws IOException {
      SFTPv3FileAttributes atts = this._stat(path);
      if (atts == null || !atts.isDirectory()) {
         int idx = path.lastIndexOf("/");
         if (idx > 0) {
            this.mkdirs(path.substring(0, idx), posixPermission);
         }

         try {
            this.mkdir(path, posixPermission);
         } catch (IOException var6) {
            throw (IOException)(new IOException("Failed to mkdir " + path)).initCause(var6);
         }
      }
   }

   public OutputStream writeToFile(String path) throws IOException {
      final SFTPv3FileHandle h = this.createFile(path);
      return new OutputStream() {
         private long offset = 0L;

         public void write(int b) throws IOException {
            this.write(new byte[]{(byte)b});
         }

         public void write(byte[] b, int off, int len) throws IOException {
            SFTPv3Client.this.write(h, this.offset, b, off, len);
            this.offset += (long)len;
         }

         public void close() throws IOException {
            SFTPv3Client.this.closeFile(h);
         }
      };
   }

   public InputStream read(String file) throws IOException {
      final SFTPv3FileHandle h = this.openFileRO(file);
      return new InputStream() {
         private long offset = 0L;

         public int read() throws IOException {
            byte[] b = new byte[1];
            return this.read(b) < 0 ? -1 : b[0];
         }

         public int read(byte[] b, int off, int len) throws IOException {
            int r = SFTPv3Client.this.read(h, this.offset, b, off, len);
            if (r < 0) {
               return -1;
            } else {
               this.offset += (long)r;
               return r;
            }
         }

         public long skip(long n) throws IOException {
            this.offset += n;
            return n;
         }

         public void close() throws IOException {
            SFTPv3Client.this.closeFile(h);
         }
      };
   }

   public void chmod(String path, int permissions) throws IOException {
      SFTPv3FileAttributes atts = new SFTPv3FileAttributes();
      atts.permissions = permissions;
      this.setstat(path, atts);
   }
}
