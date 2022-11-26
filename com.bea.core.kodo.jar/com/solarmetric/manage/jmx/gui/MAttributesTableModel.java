package com.solarmetric.manage.jmx.gui;

import java.lang.reflect.Constructor;
import javax.management.Attribute;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.swing.table.AbstractTableModel;

public class MAttributesTableModel extends AbstractTableModel {
   private MBeanServer _server;
   private ObjectInstance _instance;
   private MBeanInfo _mbInfo;
   private boolean _editable = true;
   private String[] columnNames = new String[]{"Name", "Description", "Value"};

   public MAttributesTableModel(MBeanServer server, ObjectInstance instance, MBeanInfo mbInfo) {
      this._server = server;
      this._instance = instance;
      this._mbInfo = mbInfo;
   }

   public int getColumnCount() {
      return this.columnNames.length;
   }

   public int getRowCount() {
      return this._mbInfo.getAttributes().length;
   }

   public String getColumnName(int col) {
      return this.columnNames[col];
   }

   public Object getValueAt(int row, int col) {
      switch (col) {
         case 0:
            return this._mbInfo.getAttributes()[row].getName();
         case 1:
            return this._mbInfo.getAttributes()[row].getDescription();
         case 2:
            return this.getValueAsString(row);
         default:
            return "";
      }
   }

   public Class getColumnClass(int col) {
      return String.class;
   }

   public boolean isCellEditable(int row, int col) {
      if (col < 2) {
         return false;
      } else {
         return this._editable && this._mbInfo.getAttributes()[row].isWritable();
      }
   }

   public void setValueAt(Object value, int row, int col) {
      MBeanAttributeInfo attrInfo = this._mbInfo.getAttributes()[row];
      String typeName = attrInfo.getType();

      try {
         if (typeName.equals("int")) {
            this._server.setAttribute(this._instance.getObjectName(), new Attribute(attrInfo.getName(), new Integer((String)value)));
         } else if (typeName.equals("double")) {
            this._server.setAttribute(this._instance.getObjectName(), new Attribute(attrInfo.getName(), new Double((String)value)));
         } else if (typeName.equals("boolean")) {
            this._server.setAttribute(this._instance.getObjectName(), new Attribute(attrInfo.getName(), Boolean.valueOf((String)value)));
         } else if (typeName.equals("long")) {
            this._server.setAttribute(this._instance.getObjectName(), new Attribute(attrInfo.getName(), new Long((String)value)));
         } else if (typeName.equals("byte")) {
            this._server.setAttribute(this._instance.getObjectName(), new Attribute(attrInfo.getName(), new Byte((String)value)));
         } else if (typeName.equals("char")) {
            this._server.setAttribute(this._instance.getObjectName(), new Attribute(attrInfo.getName(), new Character(((String)value).charAt(0))));
         } else if (typeName.equals("short")) {
            this._server.setAttribute(this._instance.getObjectName(), new Attribute(attrInfo.getName(), new Short((String)value)));
         } else {
            Class valClass = Class.forName(typeName);
            Constructor c = valClass.getConstructor(String.class);
            Object val = c.newInstance(value);
            this._server.setAttribute(this._instance.getObjectName(), new Attribute(attrInfo.getName(), val));
         }
      } catch (Exception var9) {
         var9.printStackTrace();
      }

   }

   public String getValueAsString(int row) {
      MBeanAttributeInfo attrInfo = this._mbInfo.getAttributes()[row];
      String val = "unknown";

      try {
         val = "" + this._server.getAttribute(this._instance.getObjectName(), attrInfo.getName());
      } catch (Exception var5) {
      }

      return val;
   }

   public void setEditable(boolean editable) {
      this._editable = editable;
   }

   public boolean getEditable() {
      return this._editable;
   }
}
