package weblogic.utils.bean;

public interface Converter {
   Object getInput();

   Object getOutput();

   Object convert(Object var1) throws ConversionException;
}
