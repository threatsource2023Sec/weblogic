package com.oracle.wls.shaded.org.apache.xalan.transformer;

public class DecimalToRoman {
   public long m_postValue;
   public String m_postLetter;
   public long m_preValue;
   public String m_preLetter;

   public DecimalToRoman(long postValue, String postLetter, long preValue, String preLetter) {
      this.m_postValue = postValue;
      this.m_postLetter = postLetter;
      this.m_preValue = preValue;
      this.m_preLetter = preLetter;
   }
}