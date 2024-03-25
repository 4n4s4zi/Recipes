/**
 * @author me
 * NOVEMBER 4, 2019
 */
import java.util.*;

/**
 * StringChain- populates a map with prefixes and suffixes from given text and generates
 * output based on probabilities in the input
 */
public class StringChain{
    private final int ORDER;
    private Map<String, Suffix> map = new HashMap<>();
    /**
     * constructor for StringChain
     * -assigns instance variable ORDER(final)
     * @param order- markov chain order
     */
    public StringChain(int order){
        this.ORDER=order;
    }
    
    //suffix class- has a list of string suffixes
    class Suffix{
        List<String> suffixList = new ArrayList<>();
    }
    
    /**
     * populates map with prefix keys and suffix values
     * @param itemIter- iterator from scanner of input text
     */
    public void addItems(Iterator<String> itemIter){
        ArrayList<String> p = new ArrayList<>();
        for(int i=0; i<ORDER; i++){
            p.add(itemIter.next().trim()+" ");
        }
        Suffix s = new Suffix();
        s.suffixList.add(itemIter.next().trim()+" ");
        String pkey="";
        for(String str:p){
            pkey+=str;
        }
        map.put(pkey, s);
        p.add(s.suffixList.get(0));
        p.remove(0);
        while(itemIter.hasNext()){
            String suffix = itemIter.next().trim()+" ";
            s = new Suffix();
            try{
                s.suffixList.add(suffix);
            }
            catch(NoSuchElementException e){
            }
            pkey="";
            for(String str:p){
                pkey+=str;
            }
            if(!(map.containsKey(pkey))){
                map.put(pkey,s);
            } else if(map.containsKey(pkey)){
                Suffix s1 = map.get(pkey);
                s1.suffixList.add(s.suffixList.get(0));
                map.put(pkey,s1);
            }
            p.add(suffix);
            p.remove(0);  
        }
    }
    
    /**
     * generates output text using probabilities between prefixes and suffixes in the map
     * @param n- number of words or characters to output
     * @param rand- random number generator for choosing indexes
     * @return list of output text
     */
    public List<String> generate(int n, Random rand){
        List<String> output = new ArrayList<>();
        List<String> window = new ArrayList<>();
        Object[] prefixes = map.keySet().toArray();
        
        boolean b=true;
        while(b==true){
            int num = rand.nextInt(prefixes.length);
            String s1 = prefixes[num].toString();
            if((map.get(s1).suffixList.size()) >0){
                b=false;
                String[] s1a=s1.split(" ");
                for(String s:s1a){
                    s+=" ";
                    window.add(s);
                    output.add(s);
                }
            }
        }
        
        for(int i=ORDER; i<n; i++){
            String prefix = "";
            for(String s:window){
                prefix+=s;
            }
            Suffix suffix = map.get(prefix);
            String s = suffix.suffixList.get(rand.nextInt(suffix.suffixList.size()));
            window.add(s);
            output.add(s);
            window.remove(0);
        }
        
        return output;
    }
}
