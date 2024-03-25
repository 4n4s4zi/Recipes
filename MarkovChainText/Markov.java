/**
 * @author me
 * NOVEMBER 4, 2019
 */
import java.util.*;
import java.io.*;

/**
 * Markov- handles command line arguments and uses StringChain to create output
 * Notes: 
 * -when "char" argument is used, the program works but there are spaces between each letter.
 * -output is generated in one line, no newlines. 
 */
public class Markov{
    
    /**
     * main- handles command line arguments and uses StringChain to create output
     */
    public static void main(String[] args){
        final int n = Integer.parseInt(args[2]);//number of output words/characters
        StringChain chain = new StringChain(Integer.parseInt(args[0]));//creates new stringchain object with the order as the argument
        String regex = "";
        Scanner input;
        //assigns the value for the regular expression- word or character based
        if (args[1].equals("word")){
            regex= "(?<=\\w\\W)";
        } else if (args[1].equals("char")){
            regex= "(?<=.)";
        } else{
            System.out.println("invalid regex");
        }
        //read in first file
        try{
            input = new Scanner(new File(args[3]));
        }
        catch (FileNotFoundException e){
            System.out.println("file: " + args[3] + " not found");
            return;
        }
        input.useDelimiter(regex);
        chain.addItems(input);
        //read in other files
        if(args.length>4){
            for(int i=4; i<args.length; i++){
                
                try{
                    input = new Scanner(new File(args[i]));
                    input.useDelimiter(regex);
                    chain.addItems(input);
                }
                catch (FileNotFoundException e){
                    System.out.println("file: " + args[i] + " not found");
                }
            }
        }
        Random r = new Random();
        //generate output
        List<String> output=chain.generate(n, r);
        //print output
        int i=0;
        for(String s: output){
            if(i%10 == 0){
            System.out.print("\n");
            }
            System.out.print(s);
            i++;
        }
        
    }
}
