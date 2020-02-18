
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.zip.GZIPInputStream;

public class Buscador {
	public static void main(String[] args) {
        
        long tempoInicial = System.currentTimeMillis();
        String entrada1 = args[0];
        String entrada2 = args[1];
        String indexador= "0000000";
        boolean id = false;

        ArrayList<Long> indexadores = new ArrayList<Long>();
        if(args.length>2){
        id = true;
        for(int i=2 ; i < args.length;i++){
        indexadores.add(Long.parseLong(args[i],16));
        }
        }else{	
            indexadores.add(Long.parseLong("0000000",16));
        }

        String data1 = entrada1.substring(0, 8);
        String data2 = entrada2.substring(0, 8);

    
        Arquivo arquivo = new Arquivo();
        
        
        ArrayList<MetaSearch> metaSearches = arquivo.lerArquivoCompactado(data1, data2);
        
        for (MetaSearch metaSearch : metaSearches) {
            try {
                RandomAccessFile randomAccessFile =
                        new RandomAccessFile(new File(Util.PATH + metaSearch.getFile() + Util.TXT), "r");

                ArrayList<Integer> integers = new ArrayList<>(runBinarySearchRecursively(randomAccessFile, metaSearch.getHeaderPostions(), entrada1, entrada2, indexadores));
                   // integers.sort(Comparator.comparingInt(o -> o));
                for (int i=0;i<integers.size();i++) {

                    int integer = integers.get(i);
                    Long aLong = metaSearch.getHeaderPostions().get(integer);
                    long offset = aLong+Arquivo.HEADER_BYTE_SIZE;

                    int length = (int) (( metaSearch.getHeaderPostions().size()-1 == integer ? randomAccessFile.length() :
                            metaSearch.getHeaderPostions().get(integer+1) ) - aLong - Arquivo.HEADER_BYTE_SIZE);
                    if(length+Arquivo.HEADER_BYTE_SIZE==0)
                        break;
                    byte[] bytes = new byte[length];
                    randomAccessFile.seek(offset);
                    randomAccessFile.read(bytes);

                    ByteArrayInputStream byteInputStream = new ByteArrayInputStream(bytes);
                    GZIPInputStream gzipInputStream = new GZIPInputStream(byteInputStream);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    byte[] buffer = new byte[200*41];
                    int len;
                    String resp ="";

                    BufferedReader bf = new BufferedReader(new InputStreamReader(gzipInputStream, "UTF-8"));
                    StringBuilder outStr = new StringBuilder();
                    String line;
                    while ((line = bf.readLine()) != null)
                    {
                        		for (int j = 0; j < indexadores.size(); j++) {
                        		
                               		if(indexadores.get(j).equals(Long.parseLong(line.split(";")[1],16))){
                        			System.out.println(line);
                       
                        		
								}
                        
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

	public static Set<Integer> runBinarySearchRecursively(RandomAccessFile raf, List<Long> headerPositions,
			String dataMinima, String dataMaxima, List<Long> indexadores) throws IOException {

		int low = 0, high = headerPositions.size() - 1;

		Set<Integer> integers = runBinarySearchRecursively(raf, headerPositions, dataMinima, dataMaxima, indexadores,
				low, high, true);
		integers.addAll(runBinarySearchRecursively(raf, headerPositions, dataMinima, dataMaxima, indexadores, low, high,
				false));
		return integers;
	}

	public static Set<Integer> runBinarySearchRecursively(RandomAccessFile raf, List<Long> headerPositions,
			String dataMinima, String dataMaxima, List<Long> indexadores, int low, int high, boolean flag)
			throws IOException {
		int middle = (low + high) / 2;

		if (high < low || middle < 0) {
			return new TreeSet<>();
		}

		long aLong = headerPositions.get(middle);

		byte[] bytes = new byte[Arquivo.HEADER_BYTE_SIZE];

		raf.seek(aLong);
		raf.read(bytes, 0, bytes.length);

		Header header = Header.fromByteBuffer(ByteBuffer.wrap(bytes));

		if (dataMaxima.compareTo(header.getDataMinina()) < 0) {
			return runBinarySearchRecursively(raf, headerPositions, dataMinima, dataMaxima, indexadores, middle + 1,
					high, flag);
		} else if (dataMinima.compareTo(header.getDataMaxima()) > 0) {
			return runBinarySearchRecursively(raf, headerPositions, dataMinima, dataMaxima, indexadores, low,
					middle - 1, flag);
		} else if (indexadores.stream().anyMatch(i -> {
			return i >= header.getIdMinimo() && i <= header.getIdMaximo();

		})) {
			Set<Integer> integers = new TreeSet<>();
			integers.add(middle);
			if ((flag && high != middle)) {
				integers.addAll(runBinarySearchRecursively(raf, headerPositions, dataMinima, dataMaxima, indexadores,
						middle - 1, middle, true));
			}

			if ((!flag && middle < headerPositions.size() - 1)) {
				integers.addAll(runBinarySearchRecursively(raf, headerPositions, dataMinima, dataMaxima, indexadores,
						middle, middle + 2, false));
			}
			return integers;
		}
		return new TreeSet<>();

	}

}
