package com.bea.core.repackaged.springframework.core.convert.converter;

public interface ConverterRegistry {
   void addConverter(Converter var1);

   void addConverter(Class var1, Class var2, Converter var3);

   void addConverter(GenericConverter var1);

   void addConverterFactory(ConverterFactory var1);

   void removeConvertible(Class var1, Class var2);
}
