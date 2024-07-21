package IO;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {
    private OutputStream out;

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        int length = b.length;
        int count = 1;

        for (int i = 0; i < length - 1; i++) {
            while (i < length - 1 && b[i] == b[i + 1]) {
                count++;
                i++;
            }
            out.write(b[i]);
            out.write(count);
            count = 1;
        }

        if (length > 0) {
            out.write(b[length - 1]);
            out.write(count);
        }
    }
    @Override
    public void flush() throws IOException {
        out.flush();
        super.flush();
    }

    @Override
    public void close() throws IOException {
        out.close();
        super.close();
    }

    public OutputStream getOut() {return out;}
}

