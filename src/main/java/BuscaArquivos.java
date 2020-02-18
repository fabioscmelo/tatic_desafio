import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class BuscaArquivos {


    private BuscaArquivos() {

    }


    public static ArrayList<String> buscarTodosArquivos(String nameDir, String extensao) {
        File dir = new File(nameDir);
        ArrayList<String> aux = new ArrayList<String>();

        File[] list = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if (extensao == null) {
                    return true;
                } else if (extensao == (Util.TXT)) {
                    return name.endsWith(Util.TXT);
                } else if (extensao == (Util.HEAD)) {
                    return name.endsWith(Util.HEAD);
                } else {
                    return name.endsWith(Util.TXT) || name.endsWith(Util.HEAD);
                }
            }
        });


        for (int i = 0; i < list.length; i++) {

            aux.add(list[i].getName());

        }
        return aux;
    }


    public static ArrayList<String> buscarArquivosPorData(String nameDir, String extensao,String data1,String data2) {
        File dir = new File(nameDir);
        ArrayList<String> aux = new ArrayList<String>();

        File[] list = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if (extensao == null) {
                    return true;
                } else if (extensao == (Util.TXT)) {
                    return name.endsWith(Util.TXT) && (data1.substring(0, 8).compareTo(name.substring(0, 8)) <= 0) && (data2.substring(0, 8).compareTo(name.substring(0, 8)) >=0);
                } else if (extensao == (Util.HEAD)) {
                    return name.endsWith(Util.HEAD) && (data1.substring(0, 8).compareTo(name.substring(0, 8)) <= 0) && (data2.substring(0, 8).compareTo(name.substring(0, 8))>=0 );
                } else {
                    return name.endsWith(Util.TXT) || name.endsWith(Util.HEAD) && (data1.substring(0, 8).compareTo(name) <= 0) && (data2.substring(0, 8).compareTo(name) >= 0);
                }
            }
        });


        for (int i = 0; i < list.length; i++) {

            aux.add(list[i].getName());

        }
        return aux;
    }
}
