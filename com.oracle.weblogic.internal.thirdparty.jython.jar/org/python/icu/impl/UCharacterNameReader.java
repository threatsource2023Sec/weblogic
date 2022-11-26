package org.python.icu.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

final class UCharacterNameReader implements ICUBinary.Authenticate {
   private ByteBuffer m_byteBuffer_;
   private static final int GROUP_INFO_SIZE_ = 3;
   private int m_tokenstringindex_;
   private int m_groupindex_;
   private int m_groupstringindex_;
   private int m_algnamesindex_;
   private static final int ALG_INFO_SIZE_ = 12;
   private static final int DATA_FORMAT_ID_ = 1970168173;

   public boolean isDataVersionAcceptable(byte[] version) {
      return version[0] == 1;
   }

   protected UCharacterNameReader(ByteBuffer bytes) throws IOException {
      ICUBinary.readHeader(bytes, 1970168173, this);
      this.m_byteBuffer_ = bytes;
   }

   protected void read(UCharacterName data) throws IOException {
      this.m_tokenstringindex_ = this.m_byteBuffer_.getInt();
      this.m_groupindex_ = this.m_byteBuffer_.getInt();
      this.m_groupstringindex_ = this.m_byteBuffer_.getInt();
      this.m_algnamesindex_ = this.m_byteBuffer_.getInt();
      int count = this.m_byteBuffer_.getChar();
      char[] token = ICUBinary.getChars(this.m_byteBuffer_, count, 0);
      int size = this.m_groupindex_ - this.m_tokenstringindex_;
      byte[] tokenstr = new byte[size];
      this.m_byteBuffer_.get(tokenstr);
      data.setToken(token, tokenstr);
      count = this.m_byteBuffer_.getChar();
      data.setGroupCountSize(count, 3);
      count *= 3;
      char[] group = ICUBinary.getChars(this.m_byteBuffer_, count, 0);
      size = this.m_algnamesindex_ - this.m_groupstringindex_;
      byte[] groupstring = new byte[size];
      this.m_byteBuffer_.get(groupstring);
      data.setGroup(group, groupstring);
      count = this.m_byteBuffer_.getInt();
      UCharacterName.AlgorithmName[] alg = new UCharacterName.AlgorithmName[count];

      for(int i = 0; i < count; ++i) {
         UCharacterName.AlgorithmName an = this.readAlg();
         if (an == null) {
            throw new IOException("unames.icu read error: Algorithmic names creation error");
         }

         alg[i] = an;
      }

      data.setAlgorithm(alg);
   }

   protected boolean authenticate(byte[] dataformatid, byte[] dataformatversion) {
      return Arrays.equals(ICUBinary.getVersionByteArrayFromCompactInt(1970168173), dataformatid) && this.isDataVersionAcceptable(dataformatversion);
   }

   private UCharacterName.AlgorithmName readAlg() throws IOException {
      UCharacterName.AlgorithmName result = new UCharacterName.AlgorithmName();
      int rangestart = this.m_byteBuffer_.getInt();
      int rangeend = this.m_byteBuffer_.getInt();
      byte type = this.m_byteBuffer_.get();
      byte variant = this.m_byteBuffer_.get();
      if (!result.setInfo(rangestart, rangeend, type, variant)) {
         return null;
      } else {
         int size = this.m_byteBuffer_.getChar();
         if (type == 1) {
            char[] factor = ICUBinary.getChars(this.m_byteBuffer_, variant, 0);
            result.setFactor(factor);
            size -= variant << 1;
         }

         StringBuilder prefix = new StringBuilder();

         for(char c = (char)(this.m_byteBuffer_.get() & 255); c != 0; c = (char)(this.m_byteBuffer_.get() & 255)) {
            prefix.append(c);
         }

         result.setPrefix(prefix.toString());
         size -= 12 + prefix.length() + 1;
         if (size > 0) {
            byte[] string = new byte[size];
            this.m_byteBuffer_.get(string);
            result.setFactorString(string);
         }

         return result;
      }
   }
}
