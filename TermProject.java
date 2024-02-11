import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class TermProject 
{
    public static void main(String[] args) 
    {
        // String message = "At four surveillance on enemy camp";
        // String keyword = "MAINE";
        // String keyword2 = "LESRF"; //both must be same length for keywords
        
        String fileName = "plaintext3.txt";
        String message = fileString(fileName);
        //System.out.println(message);
        int len = 5; //must be 5-10 length, user changeable
        String keyword = keyGenerator(len);
        String keyword2 = keyGenerator(len);
        
        
        char[] messageArray = initCharArray(message);
        //System.out.println("CharArray in main: " );
        // for (int i = 0; i < messageArray.length; i++)
        // {
        //     System.out.print(messageArray[i]);
        // }
        // System.out.println();

        //System.out.println(messageArray.length);
        char[][] encryptedMessage = rewriteAndEncrypt(messageArray, keyword, keyword2); //will turn 1d array into 2d then encrypt and return encrypted
        for (int i = 0; i < encryptedMessage.length; i++)
        {
            for (int j = 0; j < encryptedMessage[0].length; j++)
            {
                System.out.print(encryptedMessage[i][j]);
            }
            System.out.println();
        }

        
        System.out.println();
        //decryptCipher (encryptedMessage, keyword); //single encryption works fine
        
        
        decryptCipher(encryptedMessage, keyword, keyword2);//double encryption also working



    }

    public static int[] determineOrder (String keyword)
    {
        //find the order of each letter in the alphabet
        //for words of > 1 letter
        //each letter will correspond to that spot in the array
        //for example: HACK 
        // H = 1st, A = 2nd, C = 3rd, K = 4th (-1 of course cause of array numbering stuff)
        //the values will be different, in this case it should be H = 3rd, A = 1st, C = 2nd, K = 4th
        //has weird issues with same letter twice in one word

        int[] orderArray = initIntArray(keyword.length());
        char[] letters = initCharArray(keyword);
        for (int i = 0; i < keyword.length(); i++)
        {
            for (int j = 0; j < keyword.length(); j++)
            {
                if (j == i)
                {
                    
                }
                else if (letters[i] > letters[j])
                {
                    orderArray[i]++;
                }
                else 
                {

                }

            }
        }

        for (int i = 0; i < keyword.length(); i++)
        {
            orderArray[i]--;
        }
        return orderArray;


    }

    public static int[] initIntArray (int length)
    {
        int[] array = new int [length];
        for (int i = 0 ; i < length; i++)
        {
            array[i] = 1;
        }

        return array;
    }

    public static char[] initCharArray(String keyword)
    {       
        int index = 0;

        for (int i = 0; i < keyword.length(); i++)
        {
            char letter = keyword.charAt(i);
            if (!Character.isWhitespace(letter))
            {
                index++;
            }
        }
        //System.out.println("Size after whitespace removal count: " + index);

        char [] array = new char [index];
        int j = 0;
        for (int i = 0; i < keyword.length(); i++)
        {
            
            char letter = keyword.charAt(i);
            if (!Character.isWhitespace(letter))
            {
                letter = Character.toUpperCase(letter);
                array[j] = letter;
                j++;
            }
            
        }
        return array;
    }

    public static char[][] rewriteAndEncrypt (char[] array, String keyword, String keyword2) //array = message in array form, key = length of the keyword
    {
        //FOR GENERAL CASE (no order, just length) -> boolean = FALSE
        //FOR SPECIFIC CASE (we want to use HACK order 3,1,2,4) -> boolean = TRUE
        boolean useKeyWordOrder = true; //turn this on to test with specific keywords not just their length
        boolean useDoubleKeyWordOrder = true; //turn this true to test double transposition

        int key = keyword.length();
        int columns = key; //rows = keyLength
        int rows = Math.ceilDiv(array.length, key);  //will round up
        int check = rows* columns;



        if (check > array.length) //will pad array when necessary
        {
            array = padArray(array, check);
        }

        // for (int i = 0; i < array.length; i++)
        // {
        //     System.out.print(array[i]);
        // }
         
        // System.out.println();
        
        char [][] rewritten = new char [rows][columns];
        int index = 0;
        for (int i = 0; i < rows;i++) // writes message plainly into a 2d array
        {
            for (int j = 0; j < columns; j++)
            {
                rewritten[i][j] = array[index];
                index++;
            }
        }

        // for (int i = 0; i < rewritten.length; i++)
        // {
        //     for (int j = 0; j < rewritten[0].length; j++)
        //     {
        //         System.out.print(rewritten[i][j]);
        //     }
        //     System.out.println();
        
        // }

        


        rewritten = encryptMessage(rewritten); //passes off plain 2d array to be encrypted
        if (useKeyWordOrder)
        {
            int[] keywordIntOrder = determineOrder(keyword);
            if (useDoubleKeyWordOrder) //double transposition
            {
                int[] keywordIntOrder2 = determineOrder(keyword2);
                rewritten = encryptWithOrder(rewritten, keywordIntOrder);
                return rewritten = encryptWithOrder(rewritten, keywordIntOrder2);
            }
            return rewritten = encryptWithOrder(rewritten, keywordIntOrder);
        }
        //else with no keyword encryption
        return rewritten;
    }

    public static char[][] encryptMessage(char[][] message)
    {
        char[][] encrypted;
        int rows = message.length;
        int columns = message[0].length;      
        encrypted = new char[columns][rows];
        for (int j = 0; j < message[0].length; j++) //encrypts message
        {
            for (int i = 0; i < message.length; i++)
            {
                encrypted[j][i] = message[i][j];
                //System.out.print(message[i][j]);
            }
            //System.out.println();
        }

        // for (int i = 0; i < encrypted.length; i++)
        // {
        //     for (int j = 0; j < encrypted[0].length; j++)
        //     {
        //         System.out.print(encrypted[i][j]);
        //     }
        //     System.out.println();
        // }

        return encrypted;
        
    }

    public static char[] padArray (char[] array, int size)
    {
        char defaultAppend = 'Z'; //needs a default, can be anything
        char[] newArray = new char[size];
        int beginHere = array.length;

        //copy over the array
        for (int i = 0; i < array.length; i++)
        {
            newArray[i] = array[i];
        }

        for (int i = beginHere; i< size; i++)
        {
            newArray[i] = defaultAppend;
        }
        
        return newArray;

    }


    public static char[][] encryptWithOrder(char[][] message, int[] intOrder)
    {
        int row = message.length;
        int column = message[0].length;
        char[][] encrypted = new char[row][column];
        int thisRow;

        for (int i = 0; i < intOrder.length; i++)
        {
            thisRow = intOrder[i];
            //System.out.println(thisRow);
            for (int j = 0; j < message[0].length; j++) //encrypts message
            {
                encrypted[i][j] = message[thisRow][j]; 

            }
        }

        return encrypted;
    }

    public static void decryptCipher(char[][] encryptedMessage, String keyword)
    {
        int[] orderArray = determineOrder(keyword);
        int row = encryptedMessage.length;
        int column = encryptedMessage[0].length;
        //System.out.println(row + " , " + column);
        char[][] message = new char[row][column];
        int thisRow;
        char [][] finalMessage = new char[column][row];
        //rewrites back into order (breaks single encryption)

        for (int i = 0; i < orderArray.length; i++)
        {
            thisRow = orderArray[i];
            //System.out.println(thisRow);
            for (int j = 0; j < encryptedMessage[0].length; j++)
            {
                message[thisRow][j] = encryptedMessage[i][j]; 

            }
        }

        //rewrites into full order (goes from column order to row order)
        for (int j = 0; j < message[0].length; j++)
        {
            for (int i = 0; i < message.length; i++)
            {
                finalMessage[j][i] = message[i][j];
                //System.out.print(message[i][j]);
            }
            //System.out.println();
        }

        //turns into string (can keep as printed 2d char array if wanted as well)
        String messageString = ""; //can reutrn this and turn into a string function
        for (int i = 0; i < finalMessage.length; i++)
        {
            for (int j = 0; j < finalMessage[0].length; j++)
            {
                messageString += finalMessage[i][j];
                //System.out.print(finalMessage[i][j]);
            }
            //System.out.println();
        }
        System.out.println();
        System.out.println(messageString);
        System.out.println();

    }

    //overload for if double transposition is used
    public static void decryptCipher(char[][] encryptedMessage, String keyword, String keyword2)
    {
        int[] orderArray = determineOrder(keyword);
        int row = encryptedMessage.length;
        int column = encryptedMessage[0].length;
        char[][] message = new char[row][column];
        char[][] message2 = new char[row][column];
        int thisRow;
        char [][] finalMessage = new char[column][row];
        //rewrites back into order (breaks single encryption)
        int []orderArray2 = determineOrder(keyword2);
        for (int i = 0; i < orderArray2.length; i++)
        {
            thisRow = orderArray2[i];
            //System.out.println(thisRow);
            for (int j = 0; j < encryptedMessage[0].length; j++) //encrypts message
            {
                message2[thisRow][j] = encryptedMessage[i][j]; 

            }
        }

        //rewrite into original order (breaks double encryption)
        for (int i = 0; i < orderArray.length; i++)
        {
            thisRow = orderArray[i];
            //System.out.println(thisRow);
            for (int j = 0; j < encryptedMessage[0].length; j++) //encrypts message
            {
                message[thisRow][j] = message2[i][j]; 

            }
        }


        //rewrites into full order (goes from column order to row order)
        for (int j = 0; j < message[0].length; j++) //encrypts message
        {
            for (int i = 0; i < message.length; i++)
            {
                finalMessage[j][i] = message[i][j];
                //System.out.print(message[i][j]);
            }
            //System.out.println();
        }

        //turns into string (can keep as printed 2d char array if wanted as well)
        String messageString = ""; //can reutrn this and turn into a string function
        for (int i = 0; i < finalMessage.length; i++)
        {
            for (int j = 0; j < finalMessage[0].length; j++)
            {
                messageString += finalMessage[i][j];
                //System.out.print(finalMessage[i][j]);
            }
            //System.out.println();
        }
        System.out.println();
        System.out.println(messageString);
        System.out.println();
    }

    public static String keyGenerator (int length)
    {
        String keyword = "";
        Random random = new Random();
        boolean [] repeat = new boolean[25];
        
        for (int i = 0; i < length; i++)
        {
            
            int randInt = random.nextInt(25);
            if (repeat[randInt])
            {
                while (repeat[randInt])
                {
                    randInt = random.nextInt(25);
                }
            }
            char createString = (char) (randInt +'A');
            repeat[randInt] = true;
            keyword +=  createString;
        }
        return keyword;
    }

    public static String fileString(String fileName)
    {
        String line;
        StringBuilder content = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }

        return content.toString();

        //return line;

    }
}