package org.apache.taglibs.standard.lang.jstl.test;

import java.util.List;
import java.util.Map;

public class Bean1 {
   boolean mBoolean1;
   byte mByte1;
   char mChar1;
   short mShort1;
   int mInt1;
   long mLong1;
   float mFloat1;
   double mDouble1;
   Boolean mBoolean2;
   Byte mByte2;
   Character mChar2;
   Short mShort2;
   Integer mInt2;
   Long mLong2;
   Float mFloat2;
   Double mDouble2;
   String mString1;
   String mString2;
   Bean1 mBean1;
   Bean1 mBean2;
   String mNoGetter;
   String[] mStringArray1;
   List mList1;
   Map mMap1;

   public boolean getBoolean1() {
      return this.mBoolean1;
   }

   public void setBoolean1(boolean pBoolean1) {
      this.mBoolean1 = pBoolean1;
   }

   public byte getByte1() {
      return this.mByte1;
   }

   public void setByte1(byte pByte1) {
      this.mByte1 = pByte1;
   }

   public char getChar1() {
      return this.mChar1;
   }

   public void setChar1(char pChar1) {
      this.mChar1 = pChar1;
   }

   public short getShort1() {
      return this.mShort1;
   }

   public void setShort1(short pShort1) {
      this.mShort1 = pShort1;
   }

   public int getInt1() {
      return this.mInt1;
   }

   public void setInt1(int pInt1) {
      this.mInt1 = pInt1;
   }

   public long getLong1() {
      return this.mLong1;
   }

   public void setLong1(long pLong1) {
      this.mLong1 = pLong1;
   }

   public float getFloat1() {
      return this.mFloat1;
   }

   public void setFloat1(float pFloat1) {
      this.mFloat1 = pFloat1;
   }

   public double getDouble1() {
      return this.mDouble1;
   }

   public void setDouble1(double pDouble1) {
      this.mDouble1 = pDouble1;
   }

   public Boolean getBoolean2() {
      return this.mBoolean2;
   }

   public void setBoolean2(Boolean pBoolean2) {
      this.mBoolean2 = pBoolean2;
   }

   public Byte getByte2() {
      return this.mByte2;
   }

   public void setByte2(Byte pByte2) {
      this.mByte2 = pByte2;
   }

   public Character getChar2() {
      return this.mChar2;
   }

   public void setChar2(Character pChar2) {
      this.mChar2 = pChar2;
   }

   public Short getShort2() {
      return this.mShort2;
   }

   public void setShort2(Short pShort2) {
      this.mShort2 = pShort2;
   }

   public Integer getInt2() {
      return this.mInt2;
   }

   public void setInt2(Integer pInt2) {
      this.mInt2 = pInt2;
   }

   public Long getLong2() {
      return this.mLong2;
   }

   public void setLong2(Long pLong2) {
      this.mLong2 = pLong2;
   }

   public Float getFloat2() {
      return this.mFloat2;
   }

   public void setFloat2(Float pFloat2) {
      this.mFloat2 = pFloat2;
   }

   public Double getDouble2() {
      return this.mDouble2;
   }

   public void setDouble2(Double pDouble2) {
      this.mDouble2 = pDouble2;
   }

   public String getString1() {
      return this.mString1;
   }

   public void setString1(String pString1) {
      this.mString1 = pString1;
   }

   public String getString2() {
      return this.mString2;
   }

   public void setString2(String pString2) {
      this.mString2 = pString2;
   }

   public Bean1 getBean1() {
      return this.mBean1;
   }

   public void setBean1(Bean1 pBean1) {
      this.mBean1 = pBean1;
   }

   public Bean1 getBean2() {
      return this.mBean2;
   }

   public void setBean2(Bean1 pBean2) {
      this.mBean2 = pBean2;
   }

   public void setNoGetter(String pNoGetter) {
      this.mNoGetter = pNoGetter;
   }

   public String getErrorInGetter() {
      throw new NullPointerException("Error!");
   }

   public String[] getStringArray1() {
      return this.mStringArray1;
   }

   public void setStringArray1(String[] pStringArray1) {
      this.mStringArray1 = pStringArray1;
   }

   public List getList1() {
      return this.mList1;
   }

   public void setList1(List pList1) {
      this.mList1 = pList1;
   }

   public Map getMap1() {
      return this.mMap1;
   }

   public void setMap1(Map pMap1) {
      this.mMap1 = pMap1;
   }

   public String getIndexed1(int pIndex) {
      return this.mStringArray1[pIndex];
   }
}
