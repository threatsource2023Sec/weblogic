package weblogic.utils.lang;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WLClass implements Writable {
   int magic;
   short minor_version;
   short major_version;
   short constant_pool_count;
   WLConstantPoolEntry[] constant_pool;
   short access_flags;
   short this_class;
   short super_class;
   short interfaces_count;
   short[] interfaces;
   short fields_count;
   WLField[] fields;
   short methods_count;
   WLMethod[] methods;
   short attributes_count;
   WLAttribute[] attributes;

   public void write(OutputStream os) throws IOException {
      DataOutputStream dos = new DataOutputStream(os);
      dos.writeInt(this.magic);
      dos.writeShort(this.minor_version);
      dos.writeShort(this.major_version);
      dos.writeShort(this.constant_pool_count);

      int i;
      for(i = 0; i < this.constant_pool_count - 1; ++i) {
         this.constant_pool[i].write(os);
         if (this.constant_pool[i].info instanceof LongInfo || this.constant_pool[i].info instanceof DoubleInfo) {
            ++i;
         }
      }

      dos.writeShort(this.access_flags);
      dos.writeShort(this.this_class);
      dos.writeShort(this.super_class);
      dos.writeShort(this.interfaces_count);

      for(i = 0; i < this.interfaces_count; ++i) {
         dos.writeShort(this.interfaces[i]);
      }

      dos.writeShort(this.fields_count);

      for(i = 0; i < this.fields_count; ++i) {
         this.fields[i].write(os);
      }

      dos.writeShort(this.methods_count);

      for(i = 0; i < this.methods_count; ++i) {
         this.methods[i].write(os);
      }

      dos.writeShort(this.attributes_count);

      for(i = 0; i < this.attributes_count; ++i) {
         this.attributes[i].write(os);
      }

   }

   public WLClass read(InputStream is) throws IOException {
      DataInputStream dis = new DataInputStream(is);
      this.magic = dis.readInt();
      this.minor_version = dis.readShort();
      this.major_version = dis.readShort();
      this.constant_pool_count = dis.readShort();
      this.constant_pool = new WLConstantPoolEntry[this.constant_pool_count - 1];

      int i;
      for(i = 0; i < this.constant_pool_count - 1; ++i) {
         this.constant_pool[i] = (new WLConstantPoolEntry()).read(is);
         if (this.constant_pool[i].info instanceof LongInfo || this.constant_pool[i].info instanceof DoubleInfo) {
            ++i;
         }
      }

      this.access_flags = dis.readShort();
      this.this_class = dis.readShort();
      this.super_class = dis.readShort();
      this.interfaces_count = dis.readShort();
      this.interfaces = new short[this.interfaces_count];

      for(i = 0; i < this.interfaces_count; ++i) {
         this.interfaces[i] = dis.readShort();
      }

      this.fields_count = dis.readShort();
      this.fields = new WLField[this.fields_count];

      for(i = 0; i < this.fields_count; ++i) {
         this.fields[i] = (new WLField()).read(is);
      }

      this.methods_count = dis.readShort();
      this.methods = new WLMethod[this.methods_count];

      for(i = 0; i < this.methods_count; ++i) {
         this.methods[i] = (WLMethod)(new WLMethod()).read(is);
      }

      this.attributes_count = dis.readShort();
      this.attributes = new WLAttribute[this.attributes_count];

      for(i = 0; i < this.attributes_count; ++i) {
         this.attributes[i] = (new WLAttribute()).read(is);
      }

      return this;
   }
}
