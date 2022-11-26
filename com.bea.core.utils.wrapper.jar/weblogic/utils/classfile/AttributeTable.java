package weblogic.utils.classfile;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.utils.classfile.cp.CPUtf8;
import weblogic.utils.classfile.cp.ConstantPool;

public class AttributeTable {
   private final ConstantPool cp;
   private final AttributeParent parent;
   private final List attributes = new ArrayList();

   public AttributeTable(AttributeParent parent, ClassFile classFile) {
      this.parent = parent;
      this.cp = classFile.getConstantPool();
   }

   public void addAttribute(String name, byte[] data) {
      CPUtf8 realname = this.cp.getUtf8(name);
      this.attributes.add(new Generic_attribute(realname, data));
   }

   public void addAttribute(attribute_info attribute) {
      this.attributes.add(attribute);
   }

   public Iterator getAttributes() {
      return this.attributes.iterator();
   }

   public attribute_info getAttribute(String name) {
      Iterator var2 = this.attributes.iterator();

      attribute_info attr;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         attr = (attribute_info)var2.next();
      } while(!attr.getAttributeName().toString().equals(name));

      return attr;
   }

   private attribute_info newInstance(String name) {
      if ("ConstantValue".equals(name)) {
         return new ConstantValue_attribute();
      } else if ("Exceptions".equals(name)) {
         return new Exceptions_attribute();
      } else if ("LineNumberTable".equals(name)) {
         return new LineNumberTable_attribute();
      } else if ("LocalVariableTable".equals(name)) {
         return new LocalVariableTable_attribute();
      } else if ("GenericData".equals(name)) {
         return new Generic_attribute();
      } else {
         return (attribute_info)("SourceFile".equals(name) ? new SourceFile_attribute() : new Unrecognized_attribute());
      }
   }

   private attribute_info getAttribute(int idx) throws MalformedClassException {
      CPUtf8 attribute_name = this.cp.utf8At(idx);
      attribute_info attr = this.newInstance(attribute_name.getValue());
      attr.setAttributeName(attribute_name);
      attr.setConstantPool(this.cp);
      attr.setParent(this.parent);
      return attr;
   }

   public void read(DataInput in) throws IOException, BadBytecodesException {
      try {
         int count = in.readUnsignedShort();

         for(int i = 0; i < count; ++i) {
            int attribute_name_index = in.readUnsignedShort();
            attribute_info attr = this.getAttribute(attribute_name_index);
            attr.read(in);
            this.attributes.add(attr);
         }

      } catch (MalformedClassException var6) {
         throw new IOException(String.valueOf(var6));
      }
   }

   public void write(DataOutput out) throws IOException, BadBytecodesException {
      int size = this.attributes.size();
      out.writeShort(size);

      for(int i = 0; i < size; ++i) {
         ((attribute_info)this.attributes.get(i)).write(out);
      }

   }

   public void dump(PrintStream out) throws BadBytecodesException {
      out.println("attributes:");
      int size = this.attributes.size();

      for(int i = 0; i < size; ++i) {
         ((attribute_info)this.attributes.get(i)).dump(out);
      }

   }
}
