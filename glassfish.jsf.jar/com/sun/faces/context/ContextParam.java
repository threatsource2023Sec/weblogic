package com.sun.faces.context;

public enum ContextParam {
   EnableDistributable("com.sun.faces.enableDistributable", Boolean.class, false),
   SendPoweredByHeader("com.sun.faces.sendPoweredByHeader", Boolean.class, false),
   WebsocketEndpointPort("javax.faces.WEBSOCKET_ENDPOINT_PORT", Integer.class, 0);

   private final Object defaultValue;
   private final String name;
   private final Class type;

   private ContextParam(String name, Class type, Object defaultValue) {
      this.name = name;
      this.type = type;
      this.defaultValue = defaultValue;
   }

   public Object getDefaultValue() {
      return this.defaultValue;
   }

   public Object getDefaultValue(Class clazz) {
      return clazz.cast(this.defaultValue);
   }

   public String getName() {
      return this.name;
   }

   public Class getType() {
      return this.type;
   }
}
