package weblogic.tools.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.ListIterator;

public class TableLayout implements LayoutManager2, Serializable, TableLayoutConstants {
   protected static final double[][] defaultSize = new double[][]{new double[0], new double[0]};
   protected double[] columnSpec;
   protected double[] rowSpec;
   protected int[] columnSize;
   protected int[] rowSize;
   protected int[] columnOffset;
   protected int[] rowOffset;
   protected LinkedList list;
   protected boolean dirty;
   protected int oldWidth;
   protected int oldHeight;

   public TableLayout() {
      this(defaultSize);
   }

   public TableLayout(double[][] size) {
      double[] tempCol;
      double[] tempRow;
      if (size != null && size.length == 2) {
         tempCol = size[0];
         tempRow = size[1];
         this.columnSpec = new double[tempCol.length];
         this.rowSpec = new double[tempRow.length];
         System.arraycopy(tempCol, 0, this.columnSpec, 0, this.columnSpec.length);
         System.arraycopy(tempRow, 0, this.rowSpec, 0, this.rowSpec.length);

         int counter;
         for(counter = 0; counter < this.columnSpec.length; ++counter) {
            if (this.columnSpec[counter] < 0.0 && this.columnSpec[counter] != -1.0 && this.columnSpec[counter] != -2.0 && this.columnSpec[counter] != -3.0) {
               this.columnSpec[counter] = 0.0;
            }
         }

         for(counter = 0; counter < this.rowSpec.length; ++counter) {
            if (this.rowSpec[counter] < 0.0 && this.rowSpec[counter] != -1.0 && this.rowSpec[counter] != -2.0 && this.rowSpec[counter] != -3.0) {
               this.rowSpec[counter] = 0.0;
            }
         }
      } else {
         tempCol = new double[]{-1.0};
         tempRow = new double[]{-1.0};
         this.setColumn(tempCol);
         this.setRow(tempRow);
      }

      this.list = new LinkedList();
      this.dirty = true;
   }

   public TableLayoutConstraints getConstraints(Component component) {
      ListIterator iterator = this.list.listIterator(0);

      Entry entry;
      do {
         if (!iterator.hasNext()) {
            return null;
         }

         entry = (Entry)iterator.next();
      } while(entry.component != component);

      return new TableLayoutConstraints(entry.col1, entry.row1, entry.col2, entry.row2, entry.hAlign, entry.vAlign);
   }

   public void setConstraints(Component component, TableLayoutConstraints constraint) {
      if (component == null) {
         throw new IllegalArgumentException("Parameter component cannot be null.");
      } else if (constraint == null) {
         throw new IllegalArgumentException("Parameter constraint cannot be null.");
      } else {
         ListIterator iterator = this.list.listIterator(0);

         while(iterator.hasNext()) {
            Entry entry = (Entry)iterator.next();
            if (entry.component == component) {
               iterator.set(new Entry(component, constraint));
            }
         }

      }
   }

   public void setColumn(double[] column) {
      this.columnSpec = new double[column.length];
      System.arraycopy(column, 0, this.columnSpec, 0, this.columnSpec.length);

      for(int counter = 0; counter < this.columnSpec.length; ++counter) {
         if (this.columnSpec[counter] < 0.0 && this.columnSpec[counter] != -1.0 && this.columnSpec[counter] != -2.0 && this.columnSpec[counter] != -3.0) {
            this.columnSpec[counter] = 0.0;
         }
      }

      this.dirty = true;
   }

   public void setRow(double[] row) {
      this.rowSpec = new double[row.length];
      System.arraycopy(row, 0, this.rowSpec, 0, this.rowSpec.length);

      for(int counter = 0; counter < this.rowSpec.length; ++counter) {
         if (this.rowSpec[counter] < 0.0 && this.rowSpec[counter] != -1.0 && this.rowSpec[counter] != -2.0 && this.rowSpec[counter] != -3.0) {
            this.rowSpec[counter] = 0.0;
         }
      }

      this.dirty = true;
   }

   public void setColumn(int i, double size) {
      if (size < 0.0 && size != -1.0 && size != -2.0 && size != -3.0) {
         size = 0.0;
      }

      this.columnSpec[i] = size;
      this.dirty = true;
   }

   public void setRow(int i, double size) {
      if (size < 0.0 && size != -1.0 && size != -2.0 && size != -3.0) {
         size = 0.0;
      }

      this.rowSpec[i] = size;
      this.dirty = true;
   }

   public double[] getColumn() {
      double[] column = new double[this.columnSpec.length];
      System.arraycopy(this.columnSpec, 0, column, 0, column.length);
      return column;
   }

   public double[] getRow() {
      double[] row = new double[this.rowSpec.length];
      System.arraycopy(this.rowSpec, 0, row, 0, row.length);
      return row;
   }

   public double getColumn(int i) {
      return this.columnSpec[i];
   }

   public double getRow(int i) {
      return this.rowSpec[i];
   }

   public int getNumColumn() {
      return this.columnSpec.length;
   }

   public int getNumRow() {
      return this.rowSpec.length;
   }

   public void insertColumn(int i, double size) {
      if (i >= 0 && i <= this.columnSpec.length) {
         if (size < 0.0 && size != -1.0 && size != -2.0 && size != -3.0) {
            size = 0.0;
         }

         double[] column = new double[this.columnSpec.length + 1];
         System.arraycopy(this.columnSpec, 0, column, 0, i);
         System.arraycopy(this.columnSpec, i, column, i + 1, this.columnSpec.length - i);
         column[i] = size;
         this.columnSpec = column;
         ListIterator iterator = this.list.listIterator(0);

         while(iterator.hasNext()) {
            Entry entry = (Entry)iterator.next();
            if (entry.col1 >= i) {
               ++entry.col1;
            }

            if (entry.col2 >= i) {
               ++entry.col2;
            }
         }

         this.dirty = true;
      } else {
         throw new IllegalArgumentException("Parameter i is invalid.  i = " + i + ".  Valid range is [0, " + this.columnSpec.length + "].");
      }
   }

   public void insertRow(int i, double size) {
      if (i >= 0 && i <= this.rowSpec.length) {
         if (size < 0.0 && size != -1.0 && size != -2.0 && size != -3.0) {
            size = 0.0;
         }

         double[] row = new double[this.rowSpec.length + 1];
         System.arraycopy(this.rowSpec, 0, row, 0, i);
         System.arraycopy(this.rowSpec, i, row, i + 1, this.rowSpec.length - i);
         row[i] = size;
         this.rowSpec = row;
         ListIterator iterator = this.list.listIterator(0);

         while(iterator.hasNext()) {
            Entry entry = (Entry)iterator.next();
            if (entry.row1 >= i) {
               ++entry.row1;
            }

            if (entry.row2 >= i) {
               ++entry.row2;
            }
         }

         this.dirty = true;
      } else {
         throw new IllegalArgumentException("Parameter i is invalid.  i = " + i + ".  Valid range is [0, " + this.rowSpec.length + "].");
      }
   }

   public void deleteColumn(int i) {
      if (i >= 0 && i < this.columnSpec.length) {
         double[] column = new double[this.columnSpec.length - 1];
         System.arraycopy(this.columnSpec, 0, column, 0, i);
         System.arraycopy(this.columnSpec, i + 1, column, i, this.columnSpec.length - i - 1);
         this.columnSpec = column;
         ListIterator iterator = this.list.listIterator(0);

         while(iterator.hasNext()) {
            Entry entry = (Entry)iterator.next();
            if (entry.col1 >= i) {
               --entry.col1;
            }

            if (entry.col2 >= i) {
               --entry.col2;
            }
         }

         this.dirty = true;
      } else {
         throw new IllegalArgumentException("Parameter i is invalid.  i = " + i + ".  Valid range is [0, " + (this.columnSpec.length - 1) + "].");
      }
   }

   public void deleteRow(int i) {
      if (i >= 0 && i < this.rowSpec.length) {
         double[] row = new double[this.rowSpec.length - 1];
         System.arraycopy(this.rowSpec, 0, row, 0, i);
         System.arraycopy(this.rowSpec, i + 1, row, i, this.rowSpec.length - i - 1);
         this.rowSpec = row;
         ListIterator iterator = this.list.listIterator(0);

         while(iterator.hasNext()) {
            Entry entry = (Entry)iterator.next();
            if (entry.row1 >= i) {
               --entry.row1;
            }

            if (entry.row2 >= i) {
               --entry.row2;
            }
         }

         this.dirty = true;
      } else {
         throw new IllegalArgumentException("Parameter i is invalid.  i = " + i + ".  Valid range is [0, " + (this.rowSpec.length - 1) + "].");
      }
   }

   public String toString() {
      String value = "TableLayout {{";
      int counter;
      if (this.columnSpec.length > 0) {
         for(counter = 0; counter < this.columnSpec.length - 1; ++counter) {
            value = value + this.columnSpec[counter] + ", ";
         }

         value = value + this.columnSpec[this.columnSpec.length - 1] + "}, {";
      } else {
         value = value + "}, {";
      }

      if (this.rowSpec.length > 0) {
         for(counter = 0; counter < this.rowSpec.length - 1; ++counter) {
            value = value + this.rowSpec[counter] + ", ";
         }

         value = value + this.rowSpec[this.rowSpec.length - 1] + "}}";
      } else {
         value = value + "}}";
      }

      return value;
   }

   public void drawGrid(Container container, Graphics g) {
      Dimension d = container.getSize();
      if (this.dirty || d.width != this.oldWidth || d.height != this.oldHeight) {
         this.calculateSize(container);
      }

      int y = 0;

      for(int row = 0; row < this.rowSize.length; ++row) {
         int x = 0;

         for(int column = 0; column < this.columnSize.length; ++column) {
            Color color = new Color((int)(Math.random() * 1.6777215E7));
            g.setColor(color);
            g.fillRect(x, y, this.columnSize[column], this.rowSize[row]);
            x += this.columnSize[column];
         }

         y += this.rowSize[row];
      }

   }

   public boolean hidden() {
      boolean hidden = false;
      ListIterator iterator = this.list.listIterator(0);

      while(iterator.hasNext()) {
         Entry entry = (Entry)iterator.next();
         if (entry.row1 < 0 || entry.col1 < 0 || entry.row2 > this.rowSpec.length || entry.col2 > this.columnSpec.length) {
            hidden = true;
            break;
         }
      }

      return hidden;
   }

   public boolean overlapping() {
      int numEntry = this.list.size();
      if (numEntry == 0) {
         return false;
      } else {
         boolean overlapping = false;
         Entry[] entry = (Entry[])((Entry[])this.list.toArray(new Entry[numEntry]));

         for(int knowUnique = 1; knowUnique < numEntry; ++knowUnique) {
            for(int checking = knowUnique - 1; checking >= 0; --checking) {
               if (entry[checking].col1 >= entry[knowUnique].col1 && entry[checking].col1 <= entry[knowUnique].col2 && entry[checking].row1 >= entry[knowUnique].row1 && entry[checking].row1 <= entry[knowUnique].row2 || entry[checking].col2 >= entry[knowUnique].col1 && entry[checking].col2 <= entry[knowUnique].col2 && entry[checking].row2 >= entry[knowUnique].row1 && entry[checking].row2 <= entry[knowUnique].row2) {
                  overlapping = true;
                  break;
               }
            }
         }

         return overlapping;
      }
   }

   protected void calculateSize(Container container) {
      int numColumn = this.columnSpec.length;
      int numRow = this.rowSpec.length;
      this.columnSize = new int[numColumn];
      this.rowSize = new int[numRow];
      Insets inset = container.getInsets();
      Dimension d = container.getSize();
      int totalWidth = d.width - inset.left - inset.right;
      int totalHeight = d.height - inset.top - inset.bottom;
      int availableWidth = totalWidth;
      int availableHeight = totalHeight;

      int counter;
      for(counter = 0; counter < numColumn; ++counter) {
         if (this.columnSpec[counter] >= 1.0 || this.columnSpec[counter] == 0.0) {
            this.columnSize[counter] = (int)(this.columnSpec[counter] + 0.5);
            availableWidth -= this.columnSize[counter];
         }
      }

      for(counter = 0; counter < numRow; ++counter) {
         if (this.rowSpec[counter] >= 1.0 || this.rowSpec[counter] == 0.0) {
            this.rowSize[counter] = (int)(this.rowSpec[counter] + 0.5);
            availableHeight -= this.rowSize[counter];
         }
      }

      int relativeWidth;
      ListIterator iterator;
      Entry entry;
      Dimension p;
      int slackWidth;
      for(counter = 0; counter < numColumn; ++counter) {
         if (this.columnSpec[counter] == -2.0 || this.columnSpec[counter] == -3.0) {
            relativeWidth = 0;
            iterator = this.list.listIterator(0);

            while(iterator.hasNext()) {
               entry = (Entry)iterator.next();
               if (entry.col1 == counter && entry.col2 == counter) {
                  p = this.columnSpec[counter] == -2.0 ? entry.component.getPreferredSize() : entry.component.getMinimumSize();
                  slackWidth = p == null ? 0 : p.width;
                  if (relativeWidth < slackWidth) {
                     relativeWidth = slackWidth;
                  }
               }
            }

            this.columnSize[counter] = relativeWidth;
            availableWidth -= relativeWidth;
         }
      }

      for(counter = 0; counter < numRow; ++counter) {
         if (this.rowSpec[counter] == -2.0 || this.rowSpec[counter] == -3.0) {
            relativeWidth = 0;
            iterator = this.list.listIterator(0);

            while(iterator.hasNext()) {
               entry = (Entry)iterator.next();
               if (entry.row1 == counter && entry.row2 == counter) {
                  p = this.rowSpec[counter] == -2.0 ? entry.component.getPreferredSize() : entry.component.getMinimumSize();
                  slackWidth = p == null ? 0 : p.height;
                  if (relativeWidth < slackWidth) {
                     relativeWidth = slackWidth;
                  }
               }
            }

            this.rowSize[counter] = relativeWidth;
            availableHeight -= relativeWidth;
         }
      }

      relativeWidth = availableWidth;
      int relativeHeight = availableHeight;
      if (availableWidth < 0) {
         relativeWidth = 0;
      }

      if (availableHeight < 0) {
         relativeHeight = 0;
      }

      for(counter = 0; counter < numColumn; ++counter) {
         if (this.columnSpec[counter] > 0.0 && this.columnSpec[counter] < 1.0) {
            this.columnSize[counter] = (int)(this.columnSpec[counter] * (double)relativeWidth + 0.5);
            availableWidth -= this.columnSize[counter];
         }
      }

      for(counter = 0; counter < numRow; ++counter) {
         if (this.rowSpec[counter] > 0.0 && this.rowSpec[counter] < 1.0) {
            this.rowSize[counter] = (int)(this.rowSpec[counter] * (double)relativeHeight + 0.5);
            availableHeight -= this.rowSize[counter];
         }
      }

      if (availableWidth < 0) {
         availableWidth = 0;
      }

      if (availableHeight < 0) {
         availableHeight = 0;
      }

      int numFillWidth = 0;
      int numFillHeight = 0;

      for(counter = 0; counter < numColumn; ++counter) {
         if (this.columnSpec[counter] == -1.0) {
            ++numFillWidth;
         }
      }

      for(counter = 0; counter < numRow; ++counter) {
         if (this.rowSpec[counter] == -1.0) {
            ++numFillHeight;
         }
      }

      slackWidth = availableWidth;
      int slackHeight = availableHeight;

      for(counter = 0; counter < numColumn; ++counter) {
         if (this.columnSpec[counter] == -1.0) {
            this.columnSize[counter] = availableWidth / numFillWidth;
            slackWidth -= this.columnSize[counter];
         }
      }

      for(counter = 0; counter < numRow; ++counter) {
         if (this.rowSpec[counter] == -1.0) {
            this.rowSize[counter] = availableHeight / numFillHeight;
            slackHeight -= this.rowSize[counter];
         }
      }

      int[] var10000;
      for(counter = numColumn - 1; counter >= 0; --counter) {
         if (this.columnSpec[counter] == -1.0) {
            var10000 = this.columnSize;
            var10000[counter] += slackWidth;
            break;
         }
      }

      for(counter = numRow - 1; counter >= 0; --counter) {
         if (this.rowSpec[counter] == -1.0) {
            var10000 = this.rowSize;
            var10000[counter] += slackHeight;
            break;
         }
      }

      this.columnOffset = new int[numColumn + 1];
      this.columnOffset[0] = inset.left;

      for(counter = 0; counter < numColumn; ++counter) {
         this.columnOffset[counter + 1] = this.columnOffset[counter] + this.columnSize[counter];
      }

      this.rowOffset = new int[numRow + 1];
      this.rowOffset[0] = inset.top;

      for(counter = 0; counter < numRow; ++counter) {
         this.rowOffset[counter + 1] = this.rowOffset[counter] + this.rowSize[counter];
      }

      this.dirty = false;
      this.oldWidth = totalWidth;
      this.oldHeight = totalHeight;
   }

   public void layoutContainer(Container container) {
      Dimension d = container.getSize();
      if (this.dirty || d.width != this.oldWidth || d.height != this.oldHeight) {
         this.calculateSize(container);
      }

      Component[] component = container.getComponents();

      for(int counter = 0; counter < component.length; ++counter) {
         try {
            ListIterator iterator = this.list.listIterator(0);

            Entry entry;
            for(entry = null; iterator.hasNext(); entry = null) {
               entry = (Entry)iterator.next();
               if (entry.component == component[counter]) {
                  break;
               }
            }

            if (entry == null) {
               break;
            }

            int x;
            int y;
            int w;
            int h;
            if (!entry.singleCell) {
               x = this.columnOffset[entry.col1];
               y = this.rowOffset[entry.row1];
               w = this.columnOffset[entry.col2 + 1] - this.columnOffset[entry.col1];
               h = this.rowOffset[entry.row2 + 1] - this.rowOffset[entry.row1];
            } else {
               int preferredWidth = 0;
               int preferredHeight = 0;
               if (entry.hAlign != 2 || entry.vAlign != 2) {
                  Dimension preferredSize = component[counter].getPreferredSize();
                  preferredWidth = preferredSize.width;
                  preferredHeight = preferredSize.height;
               }

               int cellWidth = this.columnSize[entry.col1];
               int cellHeight = this.rowSize[entry.row1];
               if (entry.hAlign != 2 && cellWidth >= preferredWidth) {
                  w = preferredWidth;
               } else {
                  w = cellWidth;
               }

               switch (entry.hAlign) {
                  case 0:
                     x = this.columnOffset[entry.col1];
                     break;
                  case 1:
                     x = this.columnOffset[entry.col1] + (cellWidth - w >> 1);
                     break;
                  case 2:
                     x = this.columnOffset[entry.col1];
                     break;
                  case 3:
                     x = this.columnOffset[entry.col1 + 1] - w;
                     break;
                  default:
                     x = 0;
               }

               if (entry.vAlign != 2 && cellHeight >= preferredHeight) {
                  h = preferredHeight;
               } else {
                  h = cellHeight;
               }

               switch (entry.vAlign) {
                  case 0:
                     y = this.rowOffset[entry.row1];
                     break;
                  case 1:
                     y = this.rowOffset[entry.row1] + (cellHeight - h >> 1);
                     break;
                  case 2:
                     y = this.rowOffset[entry.row1];
                     break;
                  case 3:
                     y = this.rowOffset[entry.row1 + 1] - h;
                     break;
                  default:
                     y = 0;
               }
            }

            component[counter].setBounds(x, y, w, h);
         } catch (Exception var15) {
         }
      }

   }

   public Dimension preferredLayoutSize(Container container) {
      int scaledWidth = 0;
      int scaledHeight = 0;
      double fillWidthRatio = 1.0;
      double fillHeightRatio = 1.0;
      int numFillWidth = 0;
      int numFillHeight = 0;

      int counter;
      for(counter = 0; counter < this.columnSpec.length; ++counter) {
         if (this.columnSpec[counter] > 0.0 && this.columnSpec[counter] < 1.0) {
            fillWidthRatio -= this.columnSpec[counter];
         } else if (this.columnSpec[counter] == -1.0) {
            ++numFillWidth;
         }
      }

      for(counter = 0; counter < this.rowSpec.length; ++counter) {
         if (this.rowSpec[counter] > 0.0 && this.rowSpec[counter] < 1.0) {
            fillHeightRatio -= this.rowSpec[counter];
         } else if (this.rowSpec[counter] == -1.0) {
            ++numFillHeight;
         }
      }

      if (numFillWidth > 1) {
         fillWidthRatio /= (double)numFillWidth;
      }

      if (numFillHeight > 1) {
         fillHeightRatio /= (double)numFillHeight;
      }

      if (fillWidthRatio < 0.0) {
         fillWidthRatio = 0.0;
      }

      if (fillHeightRatio < 0.0) {
         fillHeightRatio = 0.0;
      }

      int[] columnPrefMin = new int[this.columnSpec.length];

      ListIterator iterator;
      Entry entry;
      int scalableHeight;
      for(counter = 0; counter < this.columnSpec.length; ++counter) {
         if (this.columnSpec[counter] == -2.0 || this.columnSpec[counter] == -3.0) {
            int maxWidth = 0;
            iterator = this.list.listIterator(0);

            while(iterator.hasNext()) {
               entry = (Entry)iterator.next();
               if (entry.col1 == counter && entry.col2 == counter) {
                  Dimension p = this.columnSpec[counter] == -2.0 ? entry.component.getPreferredSize() : entry.component.getMinimumSize();
                  scalableHeight = p == null ? 0 : p.width;
                  if (maxWidth < scalableHeight) {
                     maxWidth = scalableHeight;
                  }
               }
            }

            columnPrefMin[counter] = maxWidth;
         }
      }

      int[] rowPrefMin = new int[this.rowSpec.length];

      for(counter = 0; counter < this.rowSpec.length; ++counter) {
         if (this.rowSpec[counter] == -2.0 || this.rowSpec[counter] == -3.0) {
            int maxHeight = 0;
            ListIterator iterator = this.list.listIterator(0);

            while(iterator.hasNext()) {
               Entry entry = (Entry)iterator.next();
               if (entry.row1 == counter && entry.row2 == counter) {
                  Dimension p = this.rowSpec[counter] == -2.0 ? entry.component.getPreferredSize() : entry.component.getMinimumSize();
                  int height = p == null ? 0 : p.height;
                  if (maxHeight < height) {
                     maxHeight = height;
                  }
               }
            }

            rowPrefMin[counter] += maxHeight;
         }
      }

      iterator = this.list.listIterator(0);

      while(true) {
         int scalableWidth;
         do {
            do {
               do {
                  do {
                     do {
                        do {
                           if (!iterator.hasNext()) {
                              int totalWidth = scaledWidth;

                              for(counter = 0; counter < this.columnSpec.length; ++counter) {
                                 if (this.columnSpec[counter] >= 1.0) {
                                    totalWidth += (int)(this.columnSpec[counter] + 0.5);
                                 } else if (this.columnSpec[counter] == -2.0 || this.columnSpec[counter] == -3.0) {
                                    totalWidth += columnPrefMin[counter];
                                 }
                              }

                              scalableWidth = scaledHeight;

                              for(counter = 0; counter < this.rowSpec.length; ++counter) {
                                 if (this.rowSpec[counter] >= 1.0) {
                                    scalableWidth += (int)(this.rowSpec[counter] + 0.5);
                                 } else if (this.rowSpec[counter] == -2.0 || this.rowSpec[counter] == -3.0) {
                                    scalableWidth += rowPrefMin[counter];
                                 }
                              }

                              Insets inset = container.getInsets();
                              totalWidth += inset.left + inset.right;
                              scalableWidth += inset.top + inset.bottom;
                              ppp("DIMENSION OF THE PANEL:" + totalWidth + " " + scalableWidth);
                              return new Dimension(totalWidth, scalableWidth);
                           }

                           entry = (Entry)iterator.next();
                        } while(entry.col1 < 0);
                     } while(entry.col1 >= this.columnSpec.length);
                  } while(entry.col2 >= this.columnSpec.length);
               } while(entry.row1 < 0);
            } while(entry.row1 >= this.rowSpec.length);
         } while(entry.row2 >= this.rowSpec.length);

         Dimension size = entry.component.getPreferredSize();
         scalableWidth = size.width;
         scalableHeight = size.height;

         for(counter = entry.col1; counter <= entry.col2; ++counter) {
            if (this.columnSpec[counter] >= 1.0) {
               scalableWidth = (int)((double)scalableWidth - this.columnSpec[counter]);
            } else if (this.columnSpec[counter] == -2.0 || this.columnSpec[counter] == -3.0) {
               scalableWidth -= columnPrefMin[counter];
            }
         }

         for(counter = entry.row1; counter <= entry.row2; ++counter) {
            if (this.rowSpec[counter] >= 1.0) {
               scalableHeight = (int)((double)scalableHeight - this.rowSpec[counter]);
            } else if (this.rowSpec[counter] == -2.0 || this.rowSpec[counter] == -3.0) {
               scalableHeight -= rowPrefMin[counter];
            }
         }

         double relativeWidth = 0.0;

         for(counter = entry.col1; counter <= entry.col2; ++counter) {
            if (this.columnSpec[counter] > 0.0 && this.columnSpec[counter] < 1.0) {
               relativeWidth += this.columnSpec[counter];
            } else if (this.columnSpec[counter] == -1.0 && fillWidthRatio != 0.0) {
               relativeWidth += fillWidthRatio;
            }
         }

         int temp;
         if (relativeWidth == 0.0) {
            temp = 0;
         } else {
            temp = (int)((double)scalableWidth / relativeWidth + 0.5);
         }

         if (scaledWidth < temp) {
            scaledWidth = temp;
         }

         double relativeHeight = 0.0;

         for(counter = entry.row1; counter <= entry.row2; ++counter) {
            if (this.rowSpec[counter] > 0.0 && this.rowSpec[counter] < 1.0) {
               relativeHeight += this.rowSpec[counter];
            } else if (this.rowSpec[counter] == -1.0 && fillHeightRatio != 0.0) {
               relativeHeight += fillHeightRatio;
            }
         }

         if (relativeHeight == 0.0) {
            temp = 0;
         } else {
            temp = (int)((double)scalableHeight / relativeHeight + 0.5);
         }

         if (scaledHeight < temp) {
            scaledHeight = temp;
         }
      }
   }

   public Dimension minimumLayoutSize(Container container) {
      int scaledWidth = 0;
      int scaledHeight = 0;
      int fillWidth = false;
      int fillHeight = false;
      double fillWidthRatio = 1.0;
      double fillHeightRatio = 1.0;
      int numFillWidth = 0;
      int numFillHeight = 0;

      int counter;
      for(counter = 0; counter < this.columnSpec.length; ++counter) {
         if (this.columnSpec[counter] > 0.0 && this.columnSpec[counter] < 1.0) {
            fillWidthRatio -= this.columnSpec[counter];
         } else if (this.columnSpec[counter] == -1.0) {
            ++numFillWidth;
         }
      }

      for(counter = 0; counter < this.rowSpec.length; ++counter) {
         if (this.rowSpec[counter] > 0.0 && this.rowSpec[counter] < 1.0) {
            fillHeightRatio -= this.rowSpec[counter];
         } else if (this.rowSpec[counter] == -1.0) {
            ++numFillHeight;
         }
      }

      if (numFillWidth > 1) {
         fillWidthRatio /= (double)numFillWidth;
      }

      if (numFillHeight > 1) {
         fillHeightRatio /= (double)numFillHeight;
      }

      if (fillWidthRatio < 0.0) {
         fillWidthRatio = 0.0;
      }

      if (fillHeightRatio < 0.0) {
         fillHeightRatio = 0.0;
      }

      ListIterator iterator = this.list.listIterator(0);

      while(true) {
         Entry entry;
         int scalableWidth;
         int scalableHeight;
         do {
            do {
               do {
                  do {
                     do {
                        do {
                           if (!iterator.hasNext()) {
                              int totalWidth = scaledWidth;

                              for(counter = 0; counter < this.columnSpec.length; ++counter) {
                                 if (this.columnSpec[counter] >= 1.0) {
                                    totalWidth += (int)(this.columnSpec[counter] + 0.5);
                                 } else if (this.columnSpec[counter] == -2.0 || this.columnSpec[counter] == -3.0) {
                                    scalableWidth = 0;
                                    iterator = this.list.listIterator(0);

                                    while(iterator.hasNext()) {
                                       Entry entry = (Entry)iterator.next();
                                       if (entry.col1 == counter && entry.col2 == counter) {
                                          Dimension p = this.columnSpec[counter] == -2.0 ? entry.component.getPreferredSize() : entry.component.getMinimumSize();
                                          int width = p == null ? 0 : p.width;
                                          if (scalableWidth < width) {
                                             scalableWidth = width;
                                          }
                                       }
                                    }

                                    totalWidth += scalableWidth;
                                 }
                              }

                              scalableWidth = scaledHeight;

                              for(counter = 0; counter < this.rowSpec.length; ++counter) {
                                 if (this.rowSpec[counter] >= 1.0) {
                                    scalableWidth += (int)(this.rowSpec[counter] + 0.5);
                                 } else if (this.rowSpec[counter] == -2.0 || this.rowSpec[counter] == -3.0) {
                                    scalableHeight = 0;
                                    iterator = this.list.listIterator(0);

                                    while(iterator.hasNext()) {
                                       Entry entry = (Entry)iterator.next();
                                       if (entry.row1 == counter && entry.row2 == counter) {
                                          Dimension p = this.rowSpec[counter] == -2.0 ? entry.component.getPreferredSize() : entry.component.getMinimumSize();
                                          int height = p == null ? 0 : p.height;
                                          if (scalableHeight < height) {
                                             scalableHeight = height;
                                          }
                                       }
                                    }

                                    scalableWidth += scalableHeight;
                                 }
                              }

                              Insets inset = container.getInsets();
                              totalWidth += inset.left + inset.right;
                              scalableWidth += inset.top + inset.bottom;
                              return new Dimension(totalWidth, scalableWidth);
                           }

                           entry = (Entry)iterator.next();
                        } while(entry.col1 < 0);
                     } while(entry.col1 >= this.columnSpec.length);
                  } while(entry.col2 >= this.columnSpec.length);
               } while(entry.row1 < 0);
            } while(entry.row1 >= this.rowSpec.length);
         } while(entry.row2 >= this.rowSpec.length);

         Dimension size = entry.component.getMinimumSize();
         scalableWidth = size.width;
         scalableHeight = size.height;

         for(counter = entry.col1; counter <= entry.col2; ++counter) {
            if (this.columnSpec[counter] >= 1.0) {
               scalableWidth = (int)((double)scalableWidth - this.columnSpec[counter]);
            }
         }

         for(counter = entry.row1; counter <= entry.row2; ++counter) {
            if (this.rowSpec[counter] >= 1.0) {
               scalableHeight = (int)((double)scalableHeight - this.rowSpec[counter]);
            }
         }

         double relativeWidth = 0.0;

         for(counter = entry.col1; counter <= entry.col2; ++counter) {
            if (this.columnSpec[counter] > 0.0 && this.columnSpec[counter] < 1.0) {
               relativeWidth += this.columnSpec[counter];
            } else if (this.columnSpec[counter] == -1.0 && fillWidthRatio != 0.0) {
               relativeWidth += fillWidthRatio;
            }
         }

         int temp;
         if (relativeWidth == 0.0) {
            temp = 0;
         } else {
            temp = (int)((double)scalableWidth / relativeWidth + 0.5);
         }

         if (scaledWidth < temp) {
            scaledWidth = temp;
         }

         double relativeHeight = 0.0;

         for(counter = entry.row1; counter <= entry.row2; ++counter) {
            if (this.rowSpec[counter] > 0.0 && this.rowSpec[counter] < 1.0) {
               relativeHeight += this.rowSpec[counter];
            } else if (this.rowSpec[counter] == -1.0 && fillHeightRatio != 0.0) {
               relativeHeight += fillHeightRatio;
            }
         }

         if (relativeHeight == 0.0) {
            temp = 0;
         } else {
            temp = (int)((double)scalableHeight / relativeHeight + 0.5);
         }

         if (scaledHeight < temp) {
            scaledHeight = temp;
         }
      }
   }

   public void addLayoutComponent(String name, Component component) {
      this.addLayoutComponent((Component)component, (Object)name);
   }

   public void addLayoutComponent(Component component, Object constraint) {
      if (constraint instanceof String) {
         ppp("ADDING " + component.getClass() + " CONSTRAINT:" + constraint);
         Object constraint = new TableLayoutConstraints((String)constraint);
         this.list.add(new Entry(component, (TableLayoutConstraints)constraint));
      } else {
         if (!(constraint instanceof TableLayoutConstraints)) {
            if (constraint == null) {
               throw new IllegalArgumentException("No constraint for the component");
            }

            throw new IllegalArgumentException("Cannot accept a constraint of class " + constraint.getClass());
         }

         this.list.add(new Entry(component, (TableLayoutConstraints)constraint));
      }

   }

   public void removeLayoutComponent(Component component) {
      this.list.remove(component);
   }

   public Dimension maximumLayoutSize(Container target) {
      return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
   }

   public float getLayoutAlignmentX(Container parent) {
      return 0.5F;
   }

   public float getLayoutAlignmentY(Container parent) {
      return 0.5F;
   }

   public void invalidateLayout(Container target) {
      this.dirty = true;
   }

   private static void ppp(String s) {
      System.out.println("[TableLayout] " + s);
   }

   protected class Entry extends TableLayoutConstraints {
      protected Component component;
      protected boolean singleCell;

      public Entry(Component component, TableLayoutConstraints constraint) {
         super(constraint.col1, constraint.row1, constraint.col2, constraint.row2, constraint.hAlign, constraint.vAlign);
         this.singleCell = this.row1 == this.row2 && this.col1 == this.col2;
         this.component = component;
      }

      public int hashCode() {
         return this.component == null ? 0 : this.component.hashCode();
      }

      public boolean equals(Object object) {
         if (object == null) {
            return false;
         } else if (!(object instanceof Entry)) {
            return false;
         } else {
            Entry other = (Entry)object;
            return this.component == other.component;
         }
      }
   }
}
