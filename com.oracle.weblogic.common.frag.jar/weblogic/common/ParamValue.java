package weblogic.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

public final class ParamValue implements Cloneable, Externalizable {
   protected String paramName;
   protected String paramDesc;
   protected int paramType;
   protected int paramMode;
   private Object value;
   boolean verbose;
   private Vector valuevect = null;

   public void initialize() {
      this.paramName = null;
      this.paramDesc = null;
      this.paramType = 15;
      this.paramMode = 40;
      this.value = null;
      this.verbose = false;
      this.valuevect = null;
   }

   public void destroy() {
      this.paramName = null;
      this.paramDesc = null;
      this.paramType = 15;
      this.paramMode = 40;
      this.value = null;
      this.verbose = false;
      this.valuevect = null;
   }

   private Object currval(Object c) {
      if (this.verbose) {
         System.out.println(this.paramName + " now set to " + (c == null ? "(null)" : c.toString()));
      }

      this.value = c;
      return this.currval();
   }

   private Object currval() {
      return this.value;
   }

   public ParamValue() {
      this.initialize();
   }

   public ParamValue(String name, int datatype, int inouttype, String paramDesc) {
      this.initialize();
      this.paramName = name;
      this.paramType = datatype;
      this.paramMode = inouttype;
      this.paramDesc = paramDesc;
   }

   private ParamValue(ParamValue that) {
      this.paramName = that.paramName;
      this.paramDesc = that.paramDesc;
      this.paramType = that.paramType;
      this.paramMode = that.paramMode;
      this.value = that.value;
      this.verbose = that.verbose;
      this.valuevect = that.valuevect;
      if (this.isVector() && this.valuevect != null) {
         this.valuevect = new Vector(that.valuevect.size());
         Enumeration enum_ = this.valuevect.elements();

         while(enum_.hasMoreElements()) {
            ParamValue pv = (ParamValue)enum_.nextElement();
            this.valuevect.addElement(pv.clone());
         }
      }

   }

   public Object clone() {
      return new ParamValue(this);
   }

   private void readValue(WLObjectInput in) throws IOException, ParamSetException {
      Object O = null;
      boolean isnull = in.readBoolean();
      if (!isnull) {
         label39:
         switch (this.paramType()) {
            case 1:
               O = new Boolean(in.readBoolean());
               break;
            case 2:
               O = new Byte(in.readByte());
               break;
            case 3:
               O = new Integer(in.readInt());
               break;
            case 4:
               O = new Long(in.readLong());
               break;
            case 5:
               O = new Double(in.readDouble());
               break;
            case 6:
               O = new Float(in.readFloat());
               break;
            case 7:
               O = in.readString();
               break;
            case 8:
               O = in.readString();
               break;
            case 9:
               O = in.readDate();
               break;
            case 16:
               try {
                  O = in.readObjectWL();
                  break;
               } catch (ClassNotFoundException var7) {
                  throw new ParamSetException("Class not found: " + var7);
               }
            case 19:
               O = new Short(in.readShort());
               break;
            case 51:
               int numitems = in.readInt();
               this.valuevect = new Vector(numitems);
               int i = 0;

               while(true) {
                  if (i >= numitems) {
                     break label39;
                  }

                  ParamValue pv = new ParamValue();
                  pv.readExternal(in);
                  this.set(pv, i);
                  ++i;
               }
            default:
               throw new ParamSetException("Internal Error - Unknown Type: " + this.paramType);
         }

         this.currval(O);
         if (this.verbose) {
            System.out.println("Reading " + this.dump());
         }

      }
   }

   public void readExternal(ObjectInput oi) throws IOException {
      WLObjectInput in = (WLObjectInput)oi;
      this.paramName = in.readAbbrevString();
      this.paramType = in.readInt();

      try {
         this.readValue(in);
      } catch (ParamSetException var4) {
         throw new IOException("" + var4);
      }
   }

   private void writeValue(WLObjectOutput out) throws IOException, ParamSetException {
      if (this.verbose) {
         System.out.println("PV writing:" + this.dump());
      }

      boolean isnull = this.isNull();
      out.writeBoolean(isnull);
      if (!isnull) {
         switch (this.paramType()) {
            case 1:
               out.writeBoolean(this.asBoolean());
               break;
            case 2:
               out.writeByte(this.asByte());
               break;
            case 3:
               out.writeInt(this.asInt());
               break;
            case 4:
               out.writeLong(this.asLong());
               break;
            case 5:
               out.writeDouble(this.asDouble());
               break;
            case 6:
               out.writeFloat(this.asFloat());
               break;
            case 7:
               out.writeString(this.asString());
               break;
            case 8:
               out.writeString(this.asString());
               break;
            case 9:
               out.writeDate(this.asDate());
               break;
            case 16:
               out.writeObjectWL(this.asObject());
               break;
            case 19:
               out.writeShort(this.asShort());
               break;
            case 51:
               int numitems = this.size();
               out.writeInt(numitems);

               for(int i = 0; i < numitems; ++i) {
                  this.elementAt(i).writeExternal(out);
               }

               return;
            default:
               throw new ParamSetException("Unknown Type: " + this.paramType() + " for " + this.getClass().getName());
         }

      }
   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      WLObjectOutput out = (WLObjectOutput)oo;
      out.writeAbbrevString(this.name());
      out.writeInt(this.paramType());

      try {
         this.writeValue(out);
      } catch (ParamSetException var4) {
         throw new IOException("" + var4);
      }
   }

   public String name() {
      return this.paramName;
   }

   public String paramDesc() {
      return this.paramDesc;
   }

   public int paramType() {
      return this.paramType;
   }

   public String paramTypeString() {
      return ParamTypes.toString(this.paramType);
   }

   public String paramModeString() {
      return ParamTypes.toString(this.paramMode);
   }

   public boolean isNull(int index) throws ParamSetException {
      return this.elementAt(index).isNull();
   }

   public boolean isNull() {
      if (this.isVector()) {
         return this.valuevect == null;
      } else {
         return this.currval() == null;
      }
   }

   public int paramMode() {
      return this.paramMode;
   }

   public boolean isScalar() {
      return this.paramType != 51;
   }

   public boolean isVector() {
      return this.paramType == 51;
   }

   public boolean isInt() {
      return this.paramType == 3;
   }

   public boolean isShort() {
      return this.paramType == 19;
   }

   public boolean isFloat() {
      return this.paramType == 6;
   }

   public boolean isDouble() {
      return this.paramType == 5;
   }

   public boolean isDate() {
      return this.paramType == 9;
   }

   public boolean isString() {
      return this.paramType == 8;
   }

   public boolean isChar() {
      return this.paramType == 7;
   }

   public boolean isBoolean() {
      return this.paramType == 1;
   }

   public boolean isByte() {
      return this.paramType == 2;
   }

   public boolean isLong() {
      return this.paramType == 4;
   }

   public boolean isObject() {
      return this.paramType == 16;
   }

   void paramType(int val) {
      this.paramType = 3;
   }

   void paramType(short val) {
      this.paramType = 19;
   }

   void paramType(float val) {
      this.paramType = 6;
   }

   void paramType(double val) {
      this.paramType = 5;
   }

   void paramType(Date val) {
      this.paramType = 9;
   }

   void paramType(String val) {
      this.paramType = 8;
   }

   void paramType(char val) {
      this.paramType = 7;
   }

   void paramType(boolean val) {
      this.paramType = 1;
   }

   void paramType(byte val) {
      this.paramType = 2;
   }

   void paramType(long val) {
      this.paramType = 4;
   }

   void paramType(Object val) {
      this.paramType = 16;
   }

   public ParamValue elementAt(int index) throws ParamSetException {
      if (index + 1 > this.size()) {
         this.valuevect.setSize(index + 1);
      }

      ParamValue subPv = (ParamValue)this.valuevect.elementAt(index);
      if (subPv == null) {
         subPv = new ParamValue(this.name() + "[" + index + "]", 43, this.paramMode(), this.paramDesc());
         this.valuevect.setElementAt(subPv, index);
      }

      if (this.verbose) {
         System.out.println("elementAt: [" + this.dump() + "]\n\tfor item #" + index + "is \n\t[" + subPv.dump() + "]");
      }

      return subPv;
   }

   public void setElementAt(Object value, int index) throws ParamSetException {
      ParamValue subPv = this.elementAt(index);
      subPv.currval(value);
   }

   public int size() throws ParamSetException {
      if (!this.isVector()) {
         throw new ParamSetException("Cannot get size of a " + this.paramTypeString() + "[" + this.dump() + "]");
      } else {
         if (this.valuevect == null) {
            this.valuevect = new Vector(0);
         }

         if (this.verbose) {
            System.out.println("Vectorsize of " + this.dump() + " is " + this.valuevect.size());
         }

         return this.valuevect.size();
      }
   }

   public ParamValue set(ParamValue val, int index) throws ParamSetException {
      if (this.verbose) {
         System.out.println("\n-----------\nVthis = (" + this.dump() + ")\nVval  = (" + val.dump() + ")\n");
      }

      if (this.paramType() == 43) {
         this.paramType = 51;
      }

      if (this.isVector()) {
         this.elementAt(index).set(val);
      } else {
         this.currval(this.toObject(val));
      }

      if (this.verbose) {
         System.out.println("Vnow = (" + this.dump() + ")\n----------");
      }

      return this;
   }

   public ParamValue set(boolean val, int index) throws ParamSetException {
      if (this.paramType() == 43) {
         this.paramType = 51;
      }

      if (this.isVector()) {
         this.elementAt(index).set(val);
      } else {
         this.currval(this.toObject(val));
      }

      return this;
   }

   public ParamValue set(short val, int index) throws ParamSetException {
      if (this.paramType() == 43) {
         this.paramType = 51;
      }

      if (this.isVector()) {
         this.elementAt(index).set(val);
      } else {
         this.currval(this.toObject(val));
      }

      return this;
   }

   public ParamValue set(int val, int index) throws ParamSetException {
      if (this.paramType() == 43) {
         this.paramType = 51;
      }

      if (this.isVector()) {
         this.elementAt(index).set(val);
      } else {
         this.currval(this.toObject(val));
      }

      return this;
   }

   public ParamValue set(long val, int index) throws ParamSetException {
      if (this.paramType() == 43) {
         this.paramType = 51;
      }

      if (this.isVector()) {
         this.elementAt(index).set(val);
      } else {
         this.currval(this.toObject(val));
      }

      return this;
   }

   public ParamValue set(double val, int index) throws ParamSetException {
      if (this.paramType() == 43) {
         this.paramType = 51;
      }

      if (this.isVector()) {
         this.elementAt(index).set(val);
      } else {
         this.currval(this.toObject(val));
      }

      return this;
   }

   public ParamValue set(float val, int index) throws ParamSetException {
      if (this.paramType() == 43) {
         this.paramType = 51;
      }

      if (this.isVector()) {
         this.elementAt(index).set(val);
      } else {
         this.currval(this.toObject(val));
      }

      return this;
   }

   public ParamValue set(char val, int index) throws ParamSetException {
      if (this.paramType() == 43) {
         this.paramType = 51;
      }

      if (this.isVector()) {
         this.elementAt(index).set(val);
      } else {
         this.currval(this.toObject(val));
      }

      return this;
   }

   public ParamValue set(String val, int index) throws ParamSetException {
      if (this.paramType() == 43) {
         this.paramType = 51;
      }

      if (this.isVector()) {
         this.elementAt(index).set(val);
      } else {
         this.currval(this.toObject(val));
      }

      return this;
   }

   public ParamValue set(Date val, int index) throws ParamSetException {
      if (this.paramType() == 43) {
         this.paramType = 51;
      }

      if (this.isVector()) {
         this.elementAt(index).set(val);
      } else {
         this.currval(this.toObject(val));
      }

      return this;
   }

   public ParamValue set(Object val, int index) throws ParamSetException {
      if (this.paramType() == 43) {
         this.paramType = 51;
      }

      if (this.isVector()) {
         this.elementAt(index).set(val);
      } else {
         this.currval(this.toObject(val));
      }

      return this;
   }

   public ParamValue set(ParamValue val) throws ParamSetException {
      if (this.verbose) {
         System.out.println("\n-----------\nthis = (" + this.dump() + ")\nval  = (" + val.dump() + ")\n");
      }

      if (this.paramType == 43) {
         this.paramType = val.paramType();
         if (this.verbose) {
            System.out.println("Converting unknown   " + this.dump() + " to " + val.dump());
         }
      }

      if (this.isVector()) {
         if (val.isVector()) {
            for(int i = 0; i < val.size(); ++i) {
               this.elementAt(i).set(val.elementAt(i));
            }
         } else {
            this.set((ParamValue)val, 0);
         }
      } else {
         this.currval(this.toObject(val));
      }

      if (this.verbose) {
         System.out.println("now = (" + this.dump() + ")\n----------");
      }

      return this;
   }

   public ParamValue set(boolean val) throws ParamSetException {
      if (this.isVector()) {
         this.set(val, 0);
      } else {
         if (this.paramType == 43) {
            this.paramType(val);
         }

         this.currval(this.toObject(val));
      }

      return this;
   }

   public ParamValue set(int val) throws ParamSetException {
      if (this.isVector()) {
         this.set((int)val, 0);
      } else {
         if (this.paramType == 43) {
            this.paramType(val);
         }

         this.currval(this.toObject(val));
      }

      return this;
   }

   public ParamValue set(short val) throws ParamSetException {
      if (this.isVector()) {
         this.set((short)val, 0);
      } else {
         if (this.paramType == 43) {
            this.paramType(val);
         }

         this.currval(this.toObject(val));
      }

      return this;
   }

   public ParamValue set(byte val) throws ParamSetException {
      if (this.isVector()) {
         this.set((short)((short)val), 0);
      } else {
         if (this.paramType == 43) {
            this.paramType(val);
         }

         this.currval(this.toObject(val));
      }

      return this;
   }

   public ParamValue set(long val) throws ParamSetException {
      if (this.isVector()) {
         this.set(val, 0);
      } else {
         if (this.paramType == 43) {
            this.paramType(val);
         }

         this.currval(this.toObject(val));
      }

      return this;
   }

   public ParamValue set(double val) throws ParamSetException {
      if (this.isVector()) {
         this.set(val, 0);
      } else {
         if (this.paramType == 43) {
            this.paramType(val);
         }

         this.currval(this.toObject(val));
      }

      return this;
   }

   public ParamValue set(float val) throws ParamSetException {
      if (this.isVector()) {
         this.set(val, 0);
      } else {
         if (this.paramType == 43) {
            this.paramType(val);
         }

         this.currval(this.toObject(val));
      }

      return this;
   }

   public ParamValue set(char val) throws ParamSetException {
      if (this.isVector()) {
         this.set((char)val, 0);
      } else {
         if (this.paramType == 43) {
            this.paramType(val);
         }

         this.currval(this.toObject(val));
      }

      return this;
   }

   public ParamValue set(String val) throws ParamSetException {
      if (this.isVector()) {
         this.set((String)val, 0);
      } else {
         if (this.paramType == 43) {
            this.paramType(val);
         }

         this.currval(this.toObject(val));
      }

      return this;
   }

   public ParamValue set(Date val) throws ParamSetException {
      if (this.isVector()) {
         this.set((Date)val, 0);
      } else {
         if (this.paramType == 43) {
            this.paramType(val);
         }

         this.currval(this.toObject(val));
      }

      return this;
   }

   public ParamValue set(Object val) throws ParamSetException {
      if (this.isVector()) {
         this.set((Object)val, 0);
      } else {
         if (this.paramType == 43 || this.paramType == 15) {
            this.paramType(val);
         }

         this.currval(this.toObject(val));
      }

      return this;
   }

   Object toObject(ParamValue val) throws ParamSetException {
      switch (val.paramType()) {
         case 1:
            return this.toObject(val.asBoolean());
         case 2:
            return this.toObject(val.asByte());
         case 3:
            return this.toObject(val.asInt());
         case 4:
            return this.toObject(val.asLong());
         case 5:
            return this.toObject(val.asDouble());
         case 6:
            return this.toObject(val.asFloat());
         case 7:
            return this.toObject(val.asChar());
         case 8:
         default:
            return this.toObject(val.asString());
         case 9:
            return this.toObject(val.asDate());
         case 16:
            return this.toObject(val.asObject());
         case 19:
            return this.toObject(val.asShort());
         case 51:
            return null;
      }
   }

   Object toObject(boolean val) throws ParamSetException {
      switch (this.paramType()) {
         case 1:
            return new Boolean(val);
         case 8:
            return String.valueOf(val);
         default:
            throw new ParamSetException("Unknown Conversion from boolean to " + this.paramTypeString() + " for " + this.dump());
      }
   }

   Object toObject(short val) throws ParamSetException {
      switch (this.paramType()) {
         case 8:
            return String.valueOf(val);
         case 19:
            return new Short(val);
         default:
            throw new ParamSetException("Unknown Conversion from short to " + this.paramTypeString() + " for " + this.dump());
      }
   }

   Object toObject(int val) throws ParamSetException {
      switch (this.paramType()) {
         case 3:
            return new Integer(val);
         case 4:
            return new Long((long)val);
         case 5:
            return new Double((double)val);
         case 6:
            return new Float((float)val);
         case 7:
         default:
            throw new ParamSetException("Unknown Conversion from int to " + this.paramTypeString() + " for " + this.dump());
         case 8:
            return String.valueOf(val);
      }
   }

   Object toObject(byte val) throws ParamSetException {
      switch (this.paramType()) {
         case 2:
            return new Integer(val);
         case 3:
            return new Integer(val);
         case 4:
            return new Long((long)val);
         case 5:
            return new Double((double)val);
         case 6:
            return new Float((float)val);
         case 7:
         default:
            throw new ParamSetException("Unknown Conversion from byte to " + this.paramTypeString() + " for " + this.dump());
         case 8:
            return String.valueOf(val);
      }
   }

   Object toObject(long val) throws ParamSetException {
      switch (this.paramType()) {
         case 4:
            return new Long(val);
         case 5:
            return new Double((double)val);
         case 6:
            return new Float((float)val);
         case 7:
         default:
            throw new ParamSetException("Unknown Conversion from long to " + this.paramTypeString() + " for " + this.dump());
         case 8:
            return String.valueOf(val);
      }
   }

   Object toObject(double val) throws ParamSetException {
      switch (this.paramType()) {
         case 5:
            return new Double(val);
         case 8:
            return String.valueOf(val);
         default:
            throw new ParamSetException("Unknown Conversion from double to " + this.paramTypeString() + " for " + this.dump());
      }
   }

   Object toObject(float val) throws ParamSetException {
      switch (this.paramType()) {
         case 5:
            return new Double((double)val);
         case 6:
            return new Float(val);
         case 7:
         default:
            throw new ParamSetException("Unknown Conversion from float to " + this.paramTypeString() + " for " + this.dump());
         case 8:
            return String.valueOf(val);
      }
   }

   Object toObject(char val) throws ParamSetException {
      switch (this.paramType()) {
         case 7:
            return String.valueOf(val);
         case 8:
            return String.valueOf(val);
         default:
            throw new ParamSetException("Unknown Conversion from char to " + this.paramTypeString() + " for " + this.dump());
      }
   }

   Object toObject(String val) throws ParamSetException {
      try {
         if (val == null) {
            return null;
         } else {
            switch (this.paramType()) {
               case 1:
                  return new Boolean(val);
               case 2:
                  return new Byte(val);
               case 3:
                  return new Integer(val);
               case 4:
                  return new Long(val);
               case 5:
                  return new Double(val);
               case 6:
                  return new Float(val);
               case 7:
                  return val.substring(0, 1);
               case 8:
                  return val;
               case 9:
                  return new Date(val);
               case 10:
               case 11:
               case 12:
               case 13:
               case 14:
               case 15:
               case 16:
               case 17:
               case 18:
               default:
                  throw new ParamSetException("Unknown Conversion from String to " + this.paramTypeString() + " for " + this.dump());
               case 19:
                  return new Short(val);
            }
         }
      } catch (NumberFormatException var3) {
         throw new ParamSetException("[" + var3 + "] to type " + this.paramTypeString());
      }
   }

   Object toObject(Date val) throws ParamSetException {
      if (val == null) {
         return null;
      } else {
         switch (this.paramType()) {
            case 8:
               return val.toString();
            case 9:
               return val;
            default:
               throw new ParamSetException("Unknown Conversion from Date to " + this.paramTypeString() + " for " + this.dump());
         }
      }
   }

   Object toObject(Object val) throws ParamSetException {
      if (val == null) {
         return null;
      } else {
         switch (this.paramType()) {
            case 8:
               return val.toString();
            case 16:
               return val;
            default:
               throw new ParamSetException("Unknown Conversion from Object to " + this.paramTypeString() + " for " + this.dump());
         }
      }
   }

   ParamSetException convertFromExc(String dest) {
      return new ParamSetException("Unknown conversion from " + this.currval().getClass().getName() + " to " + dest + " for " + this.dump());
   }

   public boolean asBoolean() throws ParamSetException {
      if (this.isVector()) {
         return this.elementAt(0).asBoolean();
      } else if (this.isNull()) {
         return false;
      } else if (this.currval() instanceof Boolean) {
         return (Boolean)this.currval();
      } else if (this.currval() instanceof String) {
         return Boolean.valueOf((String)this.currval());
      } else {
         throw this.convertFromExc("boolean");
      }
   }

   public byte asByte() throws ParamSetException {
      if (this.isVector()) {
         return this.elementAt(0).asByte();
      } else if (this.isNull()) {
         return 0;
      } else if (this.currval() instanceof Number) {
         return (byte)((Number)this.currval()).intValue();
      } else if (this.currval() instanceof String) {
         return (byte)Integer.parseInt((String)this.currval());
      } else {
         throw this.convertFromExc("byte");
      }
   }

   public int asInt() throws ParamSetException {
      if (this.isVector()) {
         return this.elementAt(0).asInt();
      } else if (this.isNull()) {
         return 0;
      } else if (this.currval() instanceof Number) {
         return ((Number)this.currval()).intValue();
      } else if (this.currval() instanceof String) {
         return Integer.parseInt((String)this.currval());
      } else {
         throw this.convertFromExc("int");
      }
   }

   public short asShort() throws ParamSetException {
      if (this.isVector()) {
         return this.elementAt(0).asShort();
      } else if (this.isNull()) {
         return 0;
      } else if (this.currval() instanceof Number) {
         return (short)((Number)this.currval()).intValue();
      } else if (this.currval() instanceof String) {
         return Short.valueOf((String)this.currval());
      } else {
         throw this.convertFromExc("short");
      }
   }

   public long asLong() throws ParamSetException {
      if (this.isVector()) {
         return this.elementAt(0).asLong();
      } else if (this.isNull()) {
         return 0L;
      } else if (this.currval() instanceof Number) {
         return ((Number)this.currval()).longValue();
      } else if (this.currval() instanceof String) {
         return Long.parseLong((String)this.currval());
      } else {
         throw this.convertFromExc("long");
      }
   }

   public double asDouble() throws ParamSetException {
      if (this.isVector()) {
         return this.elementAt(0).asDouble();
      } else if (this.isNull()) {
         return 0.0;
      } else if (this.currval() instanceof Number) {
         return (double)((Number)this.currval()).floatValue();
      } else if (this.currval() instanceof String) {
         return Double.valueOf((String)this.currval());
      } else {
         throw this.convertFromExc("double");
      }
   }

   public float asFloat() throws ParamSetException {
      if (this.isVector()) {
         return this.elementAt(0).asFloat();
      } else if (this.isNull()) {
         return 0.0F;
      } else if (this.currval() instanceof Number) {
         return ((Number)this.currval()).floatValue();
      } else if (this.currval() instanceof String) {
         return Float.valueOf((String)this.currval());
      } else {
         throw this.convertFromExc("float");
      }
   }

   public char asChar() throws ParamSetException {
      if (this.isVector()) {
         return this.elementAt(0).asChar();
      } else if (this.isNull()) {
         return ' ';
      } else if (this.currval() instanceof Character) {
         return (Character)this.currval();
      } else {
         if (this.currval() instanceof String) {
            String st = (String)this.currval();
            if (st.length() == 1) {
               return st.charAt(0);
            }

            if (st.length() == 0) {
               return ' ';
            }
         }

         throw this.convertFromExc("char");
      }
   }

   public String asString() throws ParamSetException {
      if (this.isVector()) {
         return this.elementAt(0).asString();
      } else {
         return this.isNull() ? "" : this.currval().toString();
      }
   }

   public String toString() {
      try {
         return this.isVector() ? this.valuevect.toString() : this.currval().toString();
      } catch (Exception var2) {
         return "";
      }
   }

   public String dump() {
      return "" + this.name() + "/" + this.paramTypeString() + "/" + this.paramModeString() + "/" + this.paramDesc() + "/" + (this.currval() != null ? this.currval().toString() : (this.valuevect != null ? this.valuevect.toString() : ""));
   }

   public Date asDate() throws ParamSetException {
      if (this.isVector()) {
         return this.elementAt(0).asDate();
      } else if (this.isNull()) {
         return null;
      } else if (this.currval() instanceof Date) {
         return (Date)this.currval();
      } else if (this.currval() instanceof String) {
         return new Date((String)this.currval());
      } else {
         throw this.convertFromExc("Date");
      }
   }

   public Object asObject() throws ParamSetException {
      if (this.isVector()) {
         return this.elementAt(0).asObject();
      } else {
         return this.isNull() ? null : this.currval();
      }
   }
}
