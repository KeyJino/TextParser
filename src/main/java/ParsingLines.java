import java.util.ArrayList;
import java.util.StringTokenizer;

public class ParsingLines {

    private ArrayList<String> tmpArray = new ArrayList<>();

    public ArrayList<String> pars(ArrayList<String> array) {
        tmpArray.clear();
        for(String line : array){
            StringTokenizer st = new StringTokenizer(line, " \t\n\r,.");
            while (st.hasMoreTokens()) {
                tmpArray.add(st.nextToken());
            }
        }
        return  tmpArray;
    }
}
