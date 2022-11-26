package org.apache.log.output.jms;

public class PropertyInfo {
   private final String m_name;
   private final int m_type;
   private final String m_aux;

   public PropertyInfo(String name, int type, String aux) {
      this.m_type = type;
      this.m_aux = aux;
      this.m_name = name;
   }

   public String getName() {
      return this.m_name;
   }

   public int getType() {
      return this.m_type;
   }

   public String getAux() {
      return this.m_aux;
   }
}
