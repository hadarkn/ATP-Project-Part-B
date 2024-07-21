package IO;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {
    private InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        int index = 0;
        int value, count;

        while (index < b.length) {
            value = in.read();
            count = in.read();
            for (int i = 0; i < count; i++) {
                if (index < b.length) {
                    b[index++] = (byte) value;
                }
            }
        }
        return index;
    }

    @Override
    public void close() throws IOException {
        in.close();
        super.close();
    }

    public InputStream getIn() {
        return in;
    }
}
