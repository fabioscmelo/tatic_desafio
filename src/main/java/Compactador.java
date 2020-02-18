import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Compactador {


    private Compactador() {
    }


    public static byte[] compactar( StringBuilder chunk, String dataMinina, String dataMaxima, Long idMinimo, Long idMaximo) {
        try {
            Header header = new Header(dataMinina, dataMaxima, idMinimo, idMaximo);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(header.toByteBuffer().array());
            byteArrayOutputStream.flush();
            GZIPOutputStream gzip = new GZIPOutputStream(byteArrayOutputStream);
            gzip.write(chunk.toString().getBytes());
            gzip.close();
            byteArrayOutputStream.flush();
           return byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void descompactar(String dataMinina, String dataMaxima, Long idMinimo, Long idMaximo){

       // GZIPInputStream gis = new GZIPInputStream(fis);

    }
}
