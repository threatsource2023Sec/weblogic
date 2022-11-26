package weblogic.iiop.spi;

public interface Message {
   int getMinorVersion();

   byte getMaxStreamFormatVersion();
}
