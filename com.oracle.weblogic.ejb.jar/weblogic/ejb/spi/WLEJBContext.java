package weblogic.ejb.spi;

public interface WLEJBContext {
   String getComponentName();

   String getComponentURI();

   String getEJBName();
}
