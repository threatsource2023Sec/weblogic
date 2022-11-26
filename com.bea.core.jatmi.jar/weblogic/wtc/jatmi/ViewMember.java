package weblogic.wtc.jatmi;

class ViewMember {
   protected int type;
   protected String cname;
   protected String fbname;
   protected int count;
   protected boolean hasCount;
   protected boolean hasLength;
   protected int size;
   protected String nullValue;
   protected String typeName;
   protected String encoder;
   protected String decoder;
   protected String nullVal;

   ViewMember(int type) {
      this.type = type;
      this.count = 1;
      this.hasCount = false;
      this.hasLength = false;
      this.size = 1;
      switch (type) {
         case 0:
            this.typeName = "short";
            this.nullVal = "0";
            this.encoder = "encoder.writeInt(";
            this.decoder = "(short)decoder.readInt()";
            break;
         case 1:
         case 7:
            this.typeName = "int";
            this.nullVal = "0";
            this.encoder = "encoder.writeInt(";
            this.decoder = "decoder.readInt()";
            break;
         case 2:
            this.typeName = "char";
            this.nullVal = "' '";
            this.encoder = "encoder.writeInt((int)";
            this.decoder = "(char)decoder.readInt()";
            break;
         case 3:
            this.typeName = "float";
            this.nullVal = "0";
            this.encoder = "encoder.writeFloat(";
            this.decoder = "decoder.readFloat()";
            break;
         case 4:
            this.typeName = "double";
            this.nullVal = "0.0";
            this.encoder = "encoder.writeDouble(";
            this.decoder = "decoder.readDouble()";
            break;
         case 5:
            this.typeName = "String";
            this.nullVal = "null";
            this.encoder = "Utilities.xdr_encode_string(encoder,";
            this.decoder = "Utilities.xdr_decode_string(decoder)";
            break;
         case 6:
            this.typeName = "byte[]";
            this.nullVal = "null";
            this.encoder = "Utilities.xdr_encode_bstring(encoder,";
            this.decoder = "Utilities.xdr_decode_bstring(decoder)";
            break;
         case 8:
            this.typeName = "Decimal";
            this.nullVal = "null";
            this.encoder = "Utilities.xdr_encode_decimal(encoder,";
            this.decoder = "Utilities.xdr_decode_decimal(decoder)";
         case 9:
         case 10:
         case 12:
         case 13:
         case 17:
         case 20:
         case 21:
         default:
            break;
         case 11:
            this.typeName = "class";
            this.nullVal = "null";
            this.encoder = "this._tmpresend";
            this.decoder = "this._tmpostrecv";
            break;
         case 14:
            this.typeName = "boolean";
            this.nullVal = "false";
            this.encoder = "encoder.writeInt(";
            this.decoder = "(decoder.readInt()!=0)";
            break;
         case 15:
            this.typeName = "short";
            this.nullVal = "0";
            this.encoder = "encoder.writeInt((int)";
            this.decoder = "(short)decoder.readInt()";
            break;
         case 16:
            this.typeName = "short";
            this.nullVal = "0";
            this.encoder = "encoder.writeInt((int)";
            this.decoder = "(short)decoder.readInt()";
            break;
         case 18:
            this.typeName = "long";
            this.nullVal = "0";
            this.encoder = "";
            this.decoder = "";
            break;
         case 19:
            this.typeName = "long";
            this.nullVal = "0";
            this.encoder = "";
            this.decoder = "";
            break;
         case 22:
            this.typeName = "double";
            this.nullVal = "0.0";
            this.encoder = "encoder.writeDouble(";
            this.decoder = "decoder.readDouble()";
      }

   }

   public String toString() {
      return new String("" + this.type + ":" + this.cname + ":" + this.fbname + ":" + this.count + ":" + this.hasCount + ":" + this.hasLength + ":" + this.size + ":" + this.nullValue);
   }
}
