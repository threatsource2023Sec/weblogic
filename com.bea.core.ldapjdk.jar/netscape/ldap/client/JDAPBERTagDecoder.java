package netscape.ldap.client;

import java.io.IOException;
import java.io.InputStream;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BERInteger;
import netscape.ldap.ber.stream.BEROctetString;
import netscape.ldap.ber.stream.BERSequence;
import netscape.ldap.ber.stream.BERTagDecoder;

public class JDAPBERTagDecoder extends BERTagDecoder {
   public BERElement getElement(BERTagDecoder var1, int var2, InputStream var3, int[] var4, boolean[] var5) throws IOException {
      Object var6 = null;
      switch (var2) {
         case 96:
         case 97:
         case 99:
         case 100:
         case 101:
         case 103:
         case 105:
         case 106:
         case 107:
         case 109:
         case 111:
         case 115:
         case 120:
            var6 = new BERSequence(var1, var3, var4);
            var5[0] = true;
            break;
         case 98:
         case 102:
         case 104:
         case 108:
         case 110:
         case 112:
         case 113:
         case 114:
         case 116:
         case 117:
         case 118:
         case 119:
         case 121:
         case 122:
         case 123:
         case 124:
         case 125:
         case 126:
         case 127:
         case 129:
         case 130:
         case 131:
         case 132:
         case 134:
         case 136:
         case 137:
         case 140:
         case 141:
         case 142:
         case 143:
         case 144:
         case 145:
         case 146:
         case 147:
         case 148:
         case 149:
         case 150:
         case 151:
         case 152:
         case 153:
         case 154:
         case 155:
         case 156:
         case 157:
         case 158:
         case 159:
         case 161:
         case 162:
         case 164:
         case 165:
         case 166:
         default:
            throw new IOException();
         case 128:
            var6 = new BERInteger(var3, var4);
            var5[0] = true;
            break;
         case 133:
            var6 = new BERInteger(var3, var4);
            var5[0] = true;
            break;
         case 135:
            var6 = new BEROctetString(var1, var3, var4);
            var5[0] = true;
            break;
         case 138:
            var6 = new BEROctetString(var1, var3, var4);
            var5[0] = true;
            break;
         case 139:
            var6 = new BEROctetString(var1, var3, var4);
            var5[0] = true;
            break;
         case 160:
            var6 = new BERSequence(var1, var3, var4);
            var5[0] = true;
            break;
         case 163:
            var6 = new BERSequence(var1, var3, var4);
            var5[0] = true;
            break;
         case 167:
            var6 = new BERSequence(var1, var3, var4);
            var5[0] = true;
      }

      return (BERElement)var6;
   }
}
