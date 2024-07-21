package algorithms.mazeGenerators;

import java.io.Serializable;

/**
 * Represents a position in a 2D grid with row and column indices.
 */
public class Position implements Serializable {
    private int rowIndex;
    private int columnIndex;

    /**
     * Constructs a new Position with the given row and column indices.
     *
     * @param rowIndex    The row index of the position.
     * @param columnIndex The column index of the position.
     */
    public Position(int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    /**
     * Retrieves the row index of the position.
     *
     * @return The row index.
     */
    public int getRowIndex() {
        return rowIndex;
    }

    /**
     * Retrieves the column index of the position.
     *
     * @return The column index.
     */
    public int getColumnIndex() {
        return columnIndex;
    }

    /**
     * Checks if this Position is equal to another object.
     *
     * @param o The object to compare with.
     * @return true if the positions are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        return rowIndex == position.rowIndex && columnIndex == position.columnIndex;
    }

    /**
     * Computes the hash code of this Position.
     *
     * @return The hash code value for this Position.
     */
    @Override
    public int hashCode() {
        int result = rowIndex;
        result = 31 * result + columnIndex;
        return result;
    }

    /**
     * Returns a string representation of this Position.
     *
     * @return A string representation of the Position in the format "(row,column)".
     */
    @Override
    public String toString() {
        return String.format("{%d,%d}", rowIndex, columnIndex);
    }
}
