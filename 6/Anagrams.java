package CS570;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Anagrams {
    final Integer[] primes;
    Map<Character,Integer> letterTable;
    Map<Long, ArrayList<String>> anagramTable;

    /**
     *  constructor function
     */
    public Anagrams(){
        this.letterTable = new HashMap<>();
        this.anagramTable = new HashMap<>();
        this.primes = new Integer[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101};
        this.buildLetterTable();
    }

    /**
     * build hash table
     */
    private void buildLetterTable(){
        for(int i = 0;i<this.primes.length;i++){
            letterTable.put((char)(97+i),this.primes[i]);
        }
    }

    /**
     * add word to map
     * @param s
     */
    private void addWord(String s){
        Long hashCode = this.myHashCode(s);
        if(anagramTable.containsKey(hashCode)){
            anagramTable.get(hashCode).add(s);
        }else {
            ArrayList<String> res = new ArrayList<String>();
            res.add(s);
            anagramTable.put(hashCode,res);
        }

    }

    /**
     * calculate the hash code
     * @param s
     * @return
     */
    private Long myHashCode(String s){
        long hashCode = 1;
        for(int i = 0; i < s.length(); i++) {
            hashCode = hashCode * letterTable.get(s.charAt(i));
        }
        return hashCode;

    }

    /**
     * read from file
     * @param s
     * @throws IOException
     */
    public void processFile(String s) throws IOException {
        FileInputStream fstream = new FileInputStream ( s );
        BufferedReader br = new BufferedReader ( new InputStreamReader( fstream ));
        String strLine ;
        while (( strLine = br .readLine ()) != null ){
            this.addWord ( strLine );
        }
        br . close ();
    }

    /**
     * get the max entry
     * @return
     */
    private ArrayList<Map.Entry<Long,ArrayList<String>>> getMaxEntries(){
        ArrayList<Map.Entry<Long,ArrayList<String>>> result = new ArrayList<Map.Entry<Long,ArrayList<String>>>();
        int maxValue = 0;
        for (Map.Entry<Long,ArrayList<String>> entry : this.anagramTable.entrySet()) {
            if(entry.getValue().size() > maxValue) {
                result.clear();
                result.add(entry);
                maxValue = entry.getValue().size();
            } else if(entry.getValue().size() == maxValue) {
                result.add(entry);
            }
        }
        return result;
    }
    public static void main(String[] args){
        Anagrams a = new Anagrams ();
        final long startTime = System . nanoTime ();
        try {
            a.processFile ( "words_alpha.txt" );
        } catch ( IOException e1 ) {
            e1 .printStackTrace ();
        }
        ArrayList < Map . Entry < Long , ArrayList < String > > > maxEntries = a . getMaxEntries ();
        final long estimatedTime = System.nanoTime () - startTime ;
        final double seconds = (( double ) estimatedTime /1000000000) ;
        System.out .println ( " Time : " + seconds );
        System .out .println ( " Length of list of max anagrams : " + maxEntries );
    }

}
