
public class TermProject 
{
    public static void main(String[] args) 
    {
        String message = "Stan Twice for clear skin";
        String keyword = "TWICE";
        
        char[] messageArray = initCharArray(message);
        // for (int i = 0; i < messageArray.length; i++)
        // {
        //     System.out.print(messageArray[i]);
        // }
        // System.out.println(messageArray.length);
        char[][] encryptedMessage = rewriteAndEncrypt(messageArray, keyword); //will turn 1d array into 2d then encrypt and return encrypted
        for (int i = 0; i < encryptedMessage.length; i++)
        {
            for (int j = 0; j < encryptedMessage[0].length; j++)
            {
                System.out.print(encryptedMessage[i][j]);
            }
            System.out.println();
        }

        
        System.out.println();




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

    public static char[][] rewriteAndEncrypt (char[] array, String keyword) //array = message in array form, key = length of the keyword
    {
        //FOR GENERAL CASE (no order, just length) -> boolean = FALSE
        //FOR SPECIFIC CASE (we want to use HACK order 3,1,2,4) -> boolean = TRUE
        boolean useKeyWordOrder = false; //turn this on to test with specific keywords not just their length


        int key = keyword.length();
        int rows = key; //rows = keyLength
        int columns = Math.ceilDiv(array.length, key);  //will round up
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

        // for (int i = 0; i < encrypted.length; i++)
        // {
        //     for (int j = 0; j < encrypted[0].length; j++)
        //     {
        //         System.out.print(encrypted[i][j]);
        //     }
        //     System.out.println();
        
        // }

        
        if (useKeyWordOrder)
        {
            int[] keywordIntOrder = determineOrder(keyword);
            return rewritten = encryptWithOrder(rewritten, keywordIntOrder);
        }

        return rewritten = encryptMessage(rewritten); //passes off plain 2d array to be encrypted
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
        char defaultAppend = '?'; //needs a default, can be anything
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
                encrypted[thisRow][j] = message[thisRow][j]; 

            }
        }

        return encrypted;
    }
}