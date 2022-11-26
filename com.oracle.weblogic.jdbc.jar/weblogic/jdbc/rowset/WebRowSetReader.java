package weblogic.jdbc.rowset;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.sql.rowset.spi.SyncFactory;
import weblogic.xml.stream.CharacterData;
import weblogic.xml.stream.EndElement;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLInputStreamFactory;
import weblogic.xml.stream.util.TypeFilter;

public final class WebRowSetReader implements XMLSchemaConstants {
   private static final boolean DEBUG = false;
   private CachedRowSetImpl rowSet;
   private CachedRowSetMetaData metaData;
   private XMLInputStreamFactory factory = XMLInputStreamFactory.newInstance();
   private ArrayList keys = new ArrayList();

   public WebRowSetReader(CachedRowSetImpl rowSet) throws SQLException {
      this.rowSet = rowSet;
      this.metaData = (CachedRowSetMetaData)rowSet.getMetaData();
   }

   public void loadXML(XMLInputStream stream) throws IOException, SQLException {
      XMLInputStream xis = this.factory.newInputStream(stream, new TypeFilter(22));
      this.parseRowSet(xis);
   }

   private void parseRowSet(XMLInputStream xis) throws IOException, SQLException {
      boolean hasMetaData = this.metaData.getColumnCount() > 0;
      xis.next();

      while(xis.hasNext()) {
         String name = this.getName(xis.peek());
         if ("webRowSet".equals(name)) {
            xis.next();
            break;
         }

         if ("properties".equals(name)) {
            this.parseProperties(xis);
         } else if ("metadata".equals(name)) {
            this.parseMetaData(xis);
            hasMetaData = true;
         } else {
            if (!"data".equals(name)) {
               throw new IOException("XML document has incorrect format. The current element is " + name);
            }

            if (!hasMetaData) {
               throw new IOException("metadata must be established before data get parsed.");
            }

            this.parseData(xis);
         }
      }

   }

   private int parseSimpleElements4Int(XMLInputStream xis) throws IOException, SQLException {
      return Integer.parseInt(this.parseSimpleElements(xis));
   }

   private boolean parseSimpleElements4Boolean(XMLInputStream xis) throws IOException, SQLException {
      return new Boolean(this.parseSimpleElements(xis));
   }

   private String parseSimpleElements(XMLInputStream xis) throws IOException, SQLException {
      String tagName = null;
      String val = null;
      XMLEvent e = xis.next();
      if (!(e instanceof StartElement)) {
         throw new IOException("XML document has incorrect format.");
      } else {
         tagName = this.getName(e);
         e = xis.next();
         if (e instanceof CharacterData) {
            val = ((CharacterData)e).getContent();
            e = xis.next();
            if (!(e instanceof EndElement) || !tagName.equals(this.getName(e))) {
               throw new IOException("XML document has incorrect format. The current element is " + this.getName(e));
            }
         } else if (!(e instanceof EndElement) || !tagName.equals(this.getName(e))) {
            throw new IOException("XML document has incorrect format. The current element is " + this.getName(e));
         }

         return val;
      }
   }

   private void parseProperties(XMLInputStream xis) throws IOException, SQLException {
      xis.next();

      while(xis.hasNext()) {
         String name = this.getName(xis.peek());
         if ("properties".equals(name)) {
            xis.next();
            break;
         }

         if (!"map".equals(name)) {
            if ("key-columns".equals(name)) {
               xis.next();

               while(xis.hasNext()) {
                  name = this.getName(xis.peek());
                  if ("key-columns".equals(name)) {
                     xis.next();
                     break;
                  }

                  if (!"column".equals(name)) {
                     throw new IOException("XML document has incorrect format. The current element is " + name);
                  }

                  this.keys.add(this.parseSimpleElements(xis));
               }
            } else if ("sync-provider".equals(name)) {
               xis.next();

               while(xis.hasNext()) {
                  name = this.getName(xis.peek());
                  if ("sync-provider".equals(name)) {
                     xis.next();
                     break;
                  }

                  if ("sync-provider-name".equals(name)) {
                     String id = this.parseSimpleElements(xis);

                     try {
                        Class.forName(id);
                        SyncFactory.registerProvider(id);
                        this.rowSet.setSyncProvider(id);
                     } catch (ClassNotFoundException var5) {
                     }
                  } else if ("sync-provider-vendor".equals(name)) {
                     this.parseSimpleElements(xis);
                  } else if ("sync-provider-version".equals(name)) {
                     this.parseSimpleElements(xis);
                  } else if ("sync-provider-grade".equals(name)) {
                     this.parseSimpleElements(xis);
                  } else {
                     if (!"data-source-lock".equals(name)) {
                        throw new IOException("XML document has incorrect format. The current element is " + name);
                     }

                     this.rowSet.getSyncProvider().setDataSourceLock(this.parseSimpleElements4Int(xis));
                  }
               }
            } else if ("command".equals(name)) {
               this.rowSet.setCommand(this.parseSimpleElements(xis));
            } else if ("concurrency".equals(name)) {
               this.rowSet.setConcurrency(this.parseSimpleElements4Int(xis));
            } else if ("datasource".equals(name)) {
               this.rowSet.setDataSourceName(this.parseSimpleElements(xis));
            } else if ("escape-processing".equals(name)) {
               this.rowSet.setEscapeProcessing(this.parseSimpleElements4Boolean(xis));
            } else if ("fetch-direction".equals(name)) {
               this.rowSet.setFetchDirection(this.parseSimpleElements4Int(xis));
            } else if ("fetch-size".equals(name)) {
               this.rowSet.setFetchSize(this.parseSimpleElements4Int(xis));
            } else if ("isolation-level".equals(name)) {
               this.rowSet.setTransactionIsolation(this.parseSimpleElements4Int(xis));
            } else if ("max-field-size".equals(name)) {
               this.rowSet.setMaxFieldSize(this.parseSimpleElements4Int(xis));
            } else if ("max-rows".equals(name)) {
               this.rowSet.setMaxRows(this.parseSimpleElements4Int(xis));
            } else if ("query-timeout".equals(name)) {
               this.rowSet.setQueryTimeout(this.parseSimpleElements4Int(xis));
            } else if ("read-only".equals(name)) {
               this.rowSet.setReadOnlyInternal(this.parseSimpleElements4Boolean(xis));
            } else if ("rowset-type".equals(name)) {
               this.rowSet.setType(this.parseSimpleElements4Int(xis));
            } else if ("show-deleted".equals(name)) {
               this.rowSet.setShowDeleted(this.parseSimpleElements4Boolean(xis));
            } else if ("table-name".equals(name)) {
               this.rowSet.setTableName(this.parseSimpleElements(xis));
            } else {
               if (!"url".equals(name)) {
                  throw new IOException("XML document has incorrect format. The current element is " + name);
               }

               this.rowSet.setUrl(this.parseSimpleElements(xis));
            }
         } else {
            HashMap map = new HashMap();
            xis.next();

            while(xis.hasNext()) {
               name = this.getName(xis.peek());
               if ("map".equals(name)) {
                  xis.next();
                  break;
               }

               if (!"type".equals(name)) {
                  throw new IOException("XML document has incorrect format. The current element is " + name);
               }

               try {
                  map.put(this.parseSimpleElements(xis), Class.forName(this.parseSimpleElements(xis)));
               } catch (ClassNotFoundException var6) {
               }
            }

            this.rowSet.setTypeMap(map);
         }
      }

   }

   private void parseMetaData(XMLInputStream xis) throws IOException, SQLException {
      int count = 0;
      int i = 0;
      xis.next();

      while(xis.hasNext()) {
         String name = this.getName(xis.peek());
         if ("metadata".equals(name)) {
            xis.next();
            break;
         }

         if ("column-count".equals(name)) {
            count = this.parseSimpleElements4Int(xis);
            this.metaData.setColumnCountInternal(count);
         } else {
            if (!"column-definition".equals(name)) {
               throw new IOException("XML document has incorrect format. The current element is " + name);
            }

            this.parseColumnDef(xis);
            ++i;
            if (i > count) {
               throw new IOException("column-count " + count + " doesn't match with the number of column-definition.");
            }
         }
      }

      int size = this.keys.size();
      if (size > 0) {
         int[] keyColumns = new int[size];

         for(int x = 0; x < size; ++x) {
            keyColumns[x] = this.metaData.findColumn((String)this.keys.get(x));
         }

         this.rowSet.setKeyColumns(keyColumns);
      }

   }

   private void parseColumnDef(XMLInputStream xis) throws IOException, SQLException {
      xis.next();
      int index = 1;

      while(xis.hasNext()) {
         String name = this.getName(xis.peek());
         if ("column-definition".equals(name)) {
            xis.next();
            break;
         }

         if ("column-index".equals(name)) {
            index = this.parseSimpleElements4Int(xis);
         } else if ("auto-increment".equals(name)) {
            this.metaData.setAutoIncrement(index, this.parseSimpleElements4Boolean(xis));
         } else if ("definitely-writable".equals(name)) {
            this.metaData.setDefinitelyWritable(index, this.parseSimpleElements4Boolean(xis));
         } else if ("case-sensitive".equals(name)) {
            this.metaData.setCaseSensitive(index, this.parseSimpleElements4Boolean(xis));
         } else if ("currency".equals(name)) {
            this.metaData.setCurrency(index, this.parseSimpleElements4Boolean(xis));
         } else if ("nullable".equals(name)) {
            this.metaData.setNullable(index, this.parseSimpleElements4Int(xis));
         } else if ("signed".equals(name)) {
            this.metaData.setSigned(index, this.parseSimpleElements4Boolean(xis));
         } else if ("searchable".equals(name)) {
            this.metaData.setSearchable(index, this.parseSimpleElements4Boolean(xis));
         } else if ("column-display-size".equals(name)) {
            this.metaData.setColumnDisplaySize(index, this.parseSimpleElements4Int(xis));
         } else if ("column-label".equals(name)) {
            this.metaData.setColumnLabel(index, this.parseSimpleElements(xis));
         } else if ("column-name".equals(name)) {
            this.metaData.setColumnName(index, this.parseSimpleElements(xis));
         } else if ("column-class-name".equals(name)) {
            this.metaData.setColumnClassName(index, this.parseSimpleElements(xis));
         } else if ("schema-name".equals(name)) {
            this.metaData.setSchemaName(index, this.parseSimpleElements(xis));
         } else if ("column-precision".equals(name)) {
            this.metaData.setPrecision(index, this.parseSimpleElements4Int(xis));
         } else if ("column-scale".equals(name)) {
            this.metaData.setScale(index, this.parseSimpleElements4Int(xis));
         } else if ("table-name".equals(name)) {
            this.metaData.setTableName(index, this.parseSimpleElements(xis));
         } else if ("catalog-name".equals(name)) {
            this.metaData.setCatalogName(index, this.parseSimpleElements(xis));
         } else if ("column-type".equals(name)) {
            this.metaData.setColumnType(index, this.parseSimpleElements4Int(xis));
         } else {
            if (!"column-type-name".equals(name)) {
               throw new IOException("XML document has incorrect format. The current element is " + name);
            }

            this.metaData.setColumnTypeName(index, this.parseSimpleElements(xis));
         }
      }

   }

   private CachedRow parseRow(XMLInputStream xis) throws IOException, SQLException {
      CachedRow row = new CachedRow(this.metaData);
      String rowName = this.getName(xis.next());
      int colCount = 0;

      while(xis.hasNext()) {
         String name = this.getName(xis.peek());
         if (name.equals(rowName)) {
            xis.next();
            break;
         }

         Object val;
         if ("columnValue".equals(name)) {
            ++colCount;
            val = TypeMapper.getJavaValue(this.metaData.getColumnType(colCount), this.parseSimpleElements(xis));
            row.setOriginal(colCount, val);
         } else {
            if (!"updateValue".equals(name)) {
               throw new IOException("XML document has incorrect format. The current element is " + name);
            }

            val = TypeMapper.getJavaValue(this.metaData.getColumnType(colCount), this.parseSimpleElements(xis));
            row.updateColumn(colCount, val);
         }
      }

      return row;
   }

   private void parseData(XMLInputStream xis) throws IOException, SQLException {
      ArrayList rows = new ArrayList();
      CachedRow row = null;
      xis.next();

      for(; xis.hasNext(); rows.add(row)) {
         String name = this.getName(xis.peek());
         if ("data".equals(name)) {
            xis.next();
            break;
         }

         if ("currentRow".equals(name)) {
            row = this.parseRow(xis);
            row.setUpdatedRow(false);
            row.setDeletedRow(false);
            row.setInsertRow(false);
         } else if ("insertRow".equals(name)) {
            row = this.parseRow(xis);
            row.setInsertRow(true);
            row.setDeletedRow(false);
         } else if ("deleteRow".equals(name)) {
            row = this.parseRow(xis);
            row.setDeletedRow(true);
            row.setInsertRow(false);
         } else {
            if (!"modifyRow".equals(name)) {
               throw new IOException("XML document has incorrect format. The current element is " + name);
            }

            row = this.parseRow(xis);
            row.setUpdatedRow(true);
            row.setDeletedRow(false);
            row.setInsertRow(false);
         }
      }

      this.rowSet.getCachedRows().addAll(rows);
   }

   private String getName(XMLEvent e) {
      return e.getName().getLocalName();
   }
}
