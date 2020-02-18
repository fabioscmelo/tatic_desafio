import java.util.List;

public class MetaSearch {

    private String file;
    private List<Long> headerPostions;

    public MetaSearch(String file,List<Long> headerPostions){

        this.file = file;
        this.headerPostions = headerPostions;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public List<Long> getHeaderPostions() {
        return headerPostions;
    }

    public void setHeaderPostions(List<Long> headerPostions) {
        this.headerPostions = headerPostions;
    }
}
