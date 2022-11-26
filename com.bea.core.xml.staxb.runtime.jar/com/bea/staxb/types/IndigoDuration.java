package com.bea.staxb.types;

import com.bea.xml.GDuration;
import com.bea.xml.GDurationSpecification;
import java.math.BigDecimal;
import javax.xml.namespace.QName;

public class IndigoDuration implements DotNetType, GDurationSpecification {
   public static final QName QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "duration");
   private GDuration value;

   public IndigoDuration(String value) {
      this.valueOf(value);
   }

   public void valueOf(String value) {
      this.value = new GDuration(value);
   }

   public String getStringValue() {
      return this.value.toString();
   }

   public boolean isImmutable() {
      return this.value.isImmutable();
   }

   public int getSign() {
      return this.value.getSign();
   }

   public int getYear() {
      return this.value.getYear();
   }

   public int getMonth() {
      return this.value.getMonth();
   }

   public int getDay() {
      return this.value.getDay();
   }

   public int getHour() {
      return this.value.getHour();
   }

   public int getMinute() {
      return this.value.getMinute();
   }

   public int getSecond() {
      return this.value.getSecond();
   }

   public BigDecimal getFraction() {
      return this.value.getFraction();
   }

   public boolean isValid() {
      return this.value.isValid();
   }

   public int compareToGDuration(GDurationSpecification gDurationSpecification) {
      return this.value.compareToGDuration(gDurationSpecification);
   }

   public String toString() {
      return this.value.toString();
   }

   public boolean equals(Object o) {
      return !(o instanceof IndigoDuration) ? false : this.value.equals(((IndigoDuration)o).value);
   }

   public int hashCode() {
      return this.value.hashCode();
   }
}
