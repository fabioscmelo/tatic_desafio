
import java.io.*;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Arquivo {

    String nomeArquivo;
    private BufferedReader bufferedReader;

    Map<String, TreeSet<String>> mapaDeArquivos = new HashMap<String, TreeSet<String>>();

    public static final int HEADER_BYTE_SIZE = 162;

    public Arquivo() {

    }

    public Arquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }


    //////////////// Ler aquivo /////////////////////
    public Map<String, TreeSet<String>> lerArquivo() {
        String aux[] = new String[3];
        String linha;
        try {
            bufferedReader = new BufferedReader(new FileReader(this.nomeArquivo));


            while (bufferedReader.ready()) {

                linha = bufferedReader.readLine();
                aux = linha.split(";");
                String datatruncada = aux[0].substring(0, 8);
                //System.out.print(datatruncada);
                TreeSet<String> linhas = mapaDeArquivos.get(datatruncada);

                if (linhas == null) {
                    linhas = new TreeSet<String>();
                    mapaDeArquivos.put(datatruncada, linhas);
                }
                linhas.add(linha);
            }


        } catch (Exception e) {

            e.printStackTrace();
        }
        return mapaDeArquivos;
    }



   /* public ArrayList<Registro> ordenarPorData(ArrayList<Registro> arquivo) {

        array.sort((o1, o2) -> o1.data.compareTo(o2.data));
        //   System.out.println(array);

        return array;
    }

    */

    public void escreverArquivo(Map<String, TreeSet<String>> mapa) {


        try {

            byte cont = 0;
            for (String arquivo : mapa.keySet()) {

                //System.out.print(mapa);
                FileOutputStream fileOutputStream = new FileOutputStream(new File(Util.PATH + arquivo + Util.TXT));
                FileOutputStream headerFos = new FileOutputStream(new File(Util.PATH + arquivo + Util.HEAD));

                StringBuilder chunk = new StringBuilder();
                ArrayList<Long> headerPosition = new ArrayList<Long>();
               // headerPosition.add(0L);
                Long longposition = 0L;
                String dataMinina = null;
                String dataMaxima = null;
                Long idMinimo = null;
                Long idMaximo = null;

                for (String linha : mapa.get(arquivo)) {
                    String[] split = linha.split(Util.SEPARATOR);
                    String dataAtual = split[0];
                    Long indexadorAtual = Long.parseLong(split[1], 16);

                    if (dataMinina == null || dataAtual.compareTo(dataMinina) < 0) {
                        dataMinina = dataAtual;
                    }

                    if (dataMaxima == null || dataAtual.compareTo(dataMaxima) > 0) {
                        dataMaxima = dataAtual;
                    }

                    if (idMinimo == null || indexadorAtual.compareTo(idMinimo) < 0) {
                        idMinimo = indexadorAtual;
                    }

                    if (idMaximo == null || indexadorAtual.compareTo(idMaximo) > 0) {
                        idMaximo = indexadorAtual;
                    }

                    chunk.append(linha).append("\n");
                    if (chunk.length() >= 4096) {
                        byte[] compactar = Compactador.compactar(chunk, dataMinina, dataMaxima, idMinimo, idMaximo);
                        fileOutputStream.write(compactar);
                        //headerPosition.add(headerPosition.get(headerPosition.size() - 1) + HEADER_SIZE + chunk.length());
                        headerPosition.add(longposition);
                        longposition += compactar.length;
                        chunk = new StringBuilder();

                    }

                }
                // writeChunk(fileOutputStream, chunk, dataMinina, dataMaxima, idMinimo, idMaximo);
                byte[] compactar = Compactador.compactar(chunk, dataMinina, dataMaxima, idMinimo, idMaximo);
                fileOutputStream.write(compactar);
                headerPosition.add(longposition);


                fileOutputStream.flush();
                fileOutputStream.close();


                for (Long position : headerPosition) {
                    ByteBuffer bb = ByteBuffer.allocate(Long.BYTES);
                    bb.putLong(position);
                    headerFos.write(bb.array());

                }
                headerFos.flush();
                headerFos.close();

            }


        } catch (Exception e) {

            e.printStackTrace();
        }
    }
/*
    private void writeChunk(FileOutputStream fileOutputStream, StringBuilder chunk, String dataMinina, String dataMaxima, Long idMinimo, Long idMaximo) throws IOException {

        Header header = new Header(dataMinina, dataMaxima, idMinimo, idMaximo);
        fileOutputStream.write(header.toByteBuffer().array());

        GZIPOutputStream gzip = new GZIPOutputStream(fileOutputStream);
        gzip.write(chunk.toString().getBytes());
        gzip.flush();

    }

 */


    public  ArrayList<MetaSearch> lerArquivoCompactado(String data1, String data2) {

        ArrayList<String> arquivosHEAD = new ArrayList<String>();
        InputStream is = null;
        File file = null;
        ArrayList<MetaSearch> metaSearches = new ArrayList<>();
        arquivosHEAD = BuscaArquivos.buscarArquivosPorData(Util.PATH, Util.HEAD, data1, data2);


        try {


            for (int i = 0; i < arquivosHEAD.size(); i++) {

                List<Long> headerPositions = new ArrayList<Long>();
                String arquivoHead = arquivosHEAD.get(i);

                byte[] bytes = Files.readAllBytes(Paths.get(Util.PATH+arquivoHead));
                arquivoHead = arquivoHead.substring(0,arquivoHead.lastIndexOf("."));
                ByteBuffer wrap = ByteBuffer.wrap(bytes);
                while (wrap.hasRemaining()) {
                    headerPositions.add(wrap.getLong());
                }
                metaSearches.add(new MetaSearch(arquivoHead,headerPositions));

            }


        } catch (Exception e) {

            e.printStackTrace();
        }
        return metaSearches;
    }


/*
    public ArrayList<Registro> buscarIntervalosDatas(String data1, String data2) {

        Comparator<Registro> c = new Comparator<Registro>() {
            public int compare(Registro data1, Registro data2) {
                return data1.data.compareTo(data2.data);
            }
        };

        //System.out.println(array);
        ArrayList<Registro> aux = new ArrayList<Registro>();
        int index = Collections.binarySearch(array, new Registro(data1, null, null), c);
        int index2 = Collections.binarySearch(array, new Registro(data2, null, null), c);

        for (int i = index; i <= index2; i++) {
            aux.add(array.get(i));
        }
        System.out.println(aux);
        return aux;
    }
    */

}
