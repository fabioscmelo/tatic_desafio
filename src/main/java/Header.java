import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Header {

    private String dataMinina;
    private String dataMaxima;
    private long idMinimo;
    private long idMaximo;


    public Header(String dataMinina, String dataMaxima, long idMinimo, long idMaximo) {
        this.dataMinina = dataMinina;
        this.dataMaxima = dataMaxima;
        this.idMinimo = idMinimo;
        this.idMaximo = idMaximo;
    }

    public static Header fromByteBuffer(ByteBuffer header) {

        byte[] dataMinina = new byte[17];
        header.get(dataMinina);
        byte[] dataMaxima = new byte[17];
        header.get(dataMaxima);
        long idMinimo = header.getLong();
        long idMaximo = header.getLong();
        return new Header(new String(dataMinina), new String(dataMaxima), idMinimo, idMaximo);
    }


    public ByteBuffer toByteBuffer() {
        ByteBuffer allocate = ByteBuffer.allocate(Arquivo.HEADER_BYTE_SIZE);
        allocate.put(dataMinina.getBytes());
        allocate.put(dataMaxima.getBytes());
        allocate.putLong(idMinimo);
        allocate.putLong(idMaximo);
        return allocate;
    }


    public String getDataMinina() {
        return dataMinina;
    }

    public void setDataMinina(String dataMinina) {
        this.dataMinina = dataMinina;
    }

    public String getDataMaxima() {
        return dataMaxima;
    }

    public void setDataMaxima(String dataMaxima) {
        this.dataMaxima = dataMaxima;
    }

    public long getIdMinimo() {
        return idMinimo;
    }

    public void setIdMinimo(long idMinimo) {
        this.idMinimo = idMinimo;
    }

    public long getIdMaximo() {
        return idMaximo;
    }

    public void setIdMaximo(long idMaximo) {
        this.idMaximo = idMaximo;
    }
}
