package IO;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A simple compressor output stream that compresses data using a simple RLE algorithm.
 */
public class SimpleCompressorOutputStream extends OutputStream {
    private OutputStream out;

    public SimpleCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        if (b.length == 0) {
            return;
        }

        int count = 1;
        byte current = b[0];

        for (int i = 1; i < b.length; i++) {
            if (b[i] == current) {
                count++;
                if (count == 255) {
                    out.write(count);
                    out.write(current);
                    count = 0;
                }
            } else {
                out.write(count);
                out.write(current);
                current = b[i];
                count = 1;
            }
        }

        if (count > 0) {
            out.write(count);
            out.write(current);
        }
    }

    @Override
    public void close() throws IOException {
        out.close();
    }
}
