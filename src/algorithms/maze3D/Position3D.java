package algorithms.maze3D;


/**
 * Represents a position in a 3D grid with depth, row, and column indices.
 */
public class Position3D {
    private int depthIndex;
    private int rowIndex;
    private int columnIndex;

    /**
     * Constructs a new Position3D with the given depth, row, and column indices.
     *
     * @param depthIndex   The depth index of the position.
     * @param rowIndex     The row index of the position.
     * @param columnIndex  The column index of the position.
     */
    public Position3D(int depthIndex, int rowIndex, int columnIndex) {
        this.depthIndex = depthIndex;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    /**
     * Retrieves the depth index of the position.
     *
     * @return The depth index.
     */
    public int getDepthIndex() {
        return depthIndex;
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
     * Checks if this Position3D is equal to another object.
     *
     * @param o The object to compare with.
     * @return true if the positions are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position3D that = (Position3D) o;

        return depthIndex == that.depthIndex &&
                rowIndex == that.rowIndex &&
                columnIndex == that.columnIndex;
    }

    /**
     * Computes the hash code of this Position3D.
     *
     * @return The hash code value for this Position3D.
     */
    @Override
    public int hashCode() {
        int result = depthIndex;
        result = 31 * result + rowIndex;
        result = 31 * result + columnIndex;
        return result;
    }

    /**
     * Returns a string representation of this Position3D.
     *
     * @return A string representation of the Position3D in the format "(depth,row,column)".
     */
    @Override
    public String toString() {
        return String.format("(%d,%d,%d)", depthIndex, rowIndex, columnIndex);
    }
}

