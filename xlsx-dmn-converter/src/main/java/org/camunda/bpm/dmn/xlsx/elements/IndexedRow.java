/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.dmn.xlsx.elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xlsx4j.sml.Cell;
import org.xlsx4j.sml.Row;

/**
 * @author Thorben Lindhauer
 *
 */
public class IndexedRow {

  public static final Pattern CELL_REF_PATTERN = Pattern.compile("([A-Z]+)([0-9]+)");

  protected Row row;
  protected List<IndexedCell> cells;
  protected Map<String, IndexedCell> cellsByColumn;

  public IndexedRow(Row row) {
    this.row = row;
    this.cells = new ArrayList<IndexedCell>();
    this.cellsByColumn = new HashMap<String, IndexedCell>();

    for (Cell cell : row.getC()) {
      IndexedCell indexedCell = new IndexedCell(cell);
      String column = indexedCell.getColumn();
      cells.add(indexedCell);
      cellsByColumn.put(column, indexedCell);
    }
  }

  protected String extractColumn(Cell cell) {
    String cellReference = cell.getR();
    Matcher matcher = CELL_REF_PATTERN.matcher(cellReference);
    return matcher.group(1);
  }

  public Row getRow() {
    return row;
  }

  public Collection<String> getColumns() {
    return cellsByColumn.keySet();
  }

  public IndexedCell getCell(String column) {
    return cellsByColumn.get(column);
  }

  public boolean hasCells() {
    return !cells.isEmpty();
  }

  public List<IndexedCell> getCells() {
    return cells;
  }
}
