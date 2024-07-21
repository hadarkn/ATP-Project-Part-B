package IO;

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple decompressor input stream.
 */
public class SimpleDecompressorInputStream extends InputStream {
    private InputStream in;

    public SimpleDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        int offset = 0;
        while (offset < b.length) {
            int count = in.read();
            if (count == -1) {
                break;
            }
            byte value = (byte) in.read();
            for (int i = 0; i < count; i++) {
                b[offset++] = value;
            }
        }
        return offset;
    }

    @Override
    public void close() throws IOException {
        in.close();
    }
}
