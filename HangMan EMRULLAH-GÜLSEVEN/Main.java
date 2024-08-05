

import java.io.*;
import java.util.Random;
import java.util.Scanner;
import org.w3c.dom.ls.LSOutput;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        QueueMethod QName = new QueueMethod(100);
        QueueMethod QScore = new QueueMethod(100);

        StackMethod AnimalStack = new StackMethod(100);
        StackMethod LetterStack = new StackMethod(100);
        StackMethod MissingLetterStack = new StackMethod(100);

        File highScore = new File("C:\\Users\\Lenovo\\OneDrive\\Masaüstü\\highscoretable.txt");
        File animals = new File("C:\\Users\\Lenovo\\OneDrive\\Masaüstü\\Animals.txt");

        try (BufferedReader x = new BufferedReader(new FileReader(animals))) {
            String word;
            while ((word = x.readLine()) != (null)) { // hayvan isimlerini dosyadan stack'e aktardım.
                AnimalStack.push(word);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int ascii = 65; ascii <= 90; ascii++) {
            char letter = (char) ascii; // harfleri LetterStack'e aktardım .
            LetterStack.push(letter);
        }

        int wordNumber = 8; //random.nextInt(AnimalStack.size());
        String temphangWord = "";
        StackMethod temp = new StackMethod(100);
        int j = 0;
        while (!AnimalStack.isEmpty()) {
            if (j == wordNumber) {
                temphangWord = (String) AnimalStack.peek();
                temp.push(AnimalStack.pop());
            } else // random bir hayvan ismi belirleyip bunu
            { // bir değişkene atadım .
                temp.push(AnimalStack.pop());
            }
            j++;
        }
        while (!temp.isEmpty()) {
            AnimalStack.push(temp.pop());
        }
        String hangWord = temphangWord.toUpperCase();
        StackMethod WordStack = new StackMethod(hangWord.length() + 10);
        StackMethod BoardStack = new StackMethod(hangWord.length() + 10);

        for (int i = 0; i < hangWord.length(); i++) {
            temp.push(hangWord.charAt(i)); // seçilen hayvan isminin harf harf şeklinde
            BoardStack.push('_'); // temp'e taşıdım .
        }

        while (!temp.isEmpty()) { // seçilen hayvan ismini harf harf şeklinde temp'ten
            WordStack.push(temp.pop()); // WordStack'e aktardım.
        }

//		System.out.println(hangWord);
        int playerPoint = 120;
        int jokerborder = 0;
        StackMethod secondTemp = new StackMethod(100);
        StackMethod thirdTemp = new StackMethod(100);
        MissingLetterStack.push("");
        do {
            boolean flag = false;
            display(BoardStack, MissingLetterStack, playerPoint, LetterStack);
            System.out.print("\nGuess: ");
            String answer = scanner.next();

            if ((answer.equalsIgnoreCase("joker")) && jokerborder == 0) {
                int jokerLetterNumber = random.nextInt(hangWord.length());
                Character jokerLetter = null;
                for (int i = 0; i < hangWord.length(); i++) {
                    if (i == jokerLetterNumber) {
                        jokerLetter = (Character) WordStack.peek();
                        secondTemp.push(WordStack.pop());
                    } else {
                        secondTemp.push(WordStack.pop());
                    }
                }
                while (!secondTemp.isEmpty()) {
                    WordStack.push(secondTemp.pop());
                }
                for (int i = 0; i < hangWord.length(); i++) {
                    if (i == jokerLetterNumber) {
                        secondTemp.push(jokerLetter);
                        BoardStack.pop();
                    } else {
                        secondTemp.push(BoardStack.pop());
                    }
                }
                while (!secondTemp.isEmpty()) {
                    BoardStack.push(secondTemp.pop());
                }
                jokerborder++;

                boolean finishFlag = false;
                while (!BoardStack.isEmpty()) {
                    if ((Character) BoardStack.peek() == '_') {
                        finishFlag = true;
                        break;
                    } else {

                        finishFlag = false;
                    }
                    thirdTemp.push(BoardStack.pop());
                }
                while (!thirdTemp.isEmpty()) {
                    BoardStack.push(thirdTemp.pop());
                }
                if (finishFlag == false) {
                    display(BoardStack, MissingLetterStack, playerPoint, LetterStack);
                    break;
                }
            } else if (answer.equalsIgnoreCase("joker")) {
                System.out.println("You already used your joker!! ");
            } else {
                char tempguessLetter;
                char guessLetter;
                tempguessLetter = answer.charAt(0);
                guessLetter = Character.toUpperCase(tempguessLetter);

//				boolean isSameMissing = isSame(guessLetter, MissingLetterStack);
//				boolean isSameBoard = isSame(guessLetter, BoardStack);
//				while (isSameBoard == true || isSameMissing == true) {
//					System.out.println("\nGuess:");
//					tempguessLetter = answer.charAt(0);
//					guessLetter = Character.toUpperCase(tempguessLetter);
//					isSameMissing = isSame(guessLetter, MissingLetterStack);
//					isSameBoard = isSame(guessLetter, BoardStack);
//				}
                for (int i = 0; i < hangWord.length(); i++) {
                    if (guessLetter == (Character) WordStack.peek()) {
                        temp.push(guessLetter);
                        BoardStack.pop();
                        secondTemp.push(WordStack.pop());
                        flag = true;

                        while (!LetterStack.isEmpty()) {
                            if ((Character) LetterStack.peek() == guessLetter) {
                                LetterStack.pop();
                            } else {
                                thirdTemp.push(LetterStack.pop());
                            }
                        }
                        while (!thirdTemp.isEmpty()) {
                            LetterStack.push(thirdTemp.pop());
                        }

                    } else {
                        temp.push(BoardStack.pop());
                        secondTemp.push(WordStack.pop());
                        if (flag != true) {
                            flag = false;
                        }
                    }
                }

                while (!temp.isEmpty()) {
                    BoardStack.push(temp.pop());
                    WordStack.push(secondTemp.pop());
                }

                if (flag == false) {
                    if (guessLetter == 'A' || guessLetter == 'E' || guessLetter == 'O' || guessLetter == 'U'
                            || guessLetter == 'I') {
                        playerPoint -= 20;
                    } else {
                        playerPoint -= 15;
                    }

                    MissingLetterStack.push(guessLetter);
                    while (!LetterStack.isEmpty()) {
                        if ((Character) LetterStack.peek() == guessLetter) {
                            LetterStack.pop();
                        } else {
                            temp.push(LetterStack.pop());
                        }
                    }
                    while (!temp.isEmpty()) {
                        LetterStack.push(temp.pop());
                    }

                }
                System.out.println();

                boolean secondfinishFlag = false;
                while (!BoardStack.isEmpty()) {
                    if ((Character) BoardStack.peek() == '_') {
                        secondfinishFlag = true;
                        break;
                    } else {
                        secondfinishFlag = false;
                    }
                    thirdTemp.push(BoardStack.pop());
                }
                while (!thirdTemp.isEmpty()) {
                    BoardStack.push(thirdTemp.pop());
                }
                if (secondfinishFlag == false) {
                    display(BoardStack, MissingLetterStack, playerPoint, LetterStack);
                    break;
                }
            }
        } while (playerPoint > 0);

        if (playerPoint <= 0) {
            System.out.println("\nYou lost!!");
            System.out.println("Your score is " + playerPoint + ".");
        } else {
            System.out.println("\nYou win!!");
            System.out.println("Your score is " + playerPoint + ".");
        }
        System.out.print("What is your name:");
        String playerName = scanner.next();

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(highScore)))
        {
            writer.write(playerName);

            writer.write(playerPoint);
            writer.newLine();
        }
        catch (IOException e)
        {}

        try(BufferedReader reader = new BufferedReader(new FileReader(highScore)))
        {
            String name = null;
            while ((name = reader.readLine()) != (null)) {
                int i=0;
                Object [] datas = name.split(" ");
                QName.enqueue(datas[i]);
                QScore.enqueue(datas[i+1]);
                i++;}

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        QueueMethod tempQueue = new QueueMethod(100);
        QueueMethod tempNameQueue = new QueueMethod(100);
        for(int i=0;i<QScore.size();i++)
        {
            tempQueue.enqueue(QScore.peek());
            QScore.enqueue(QScore.dequeue());
            tempNameQueue.enqueue(QName.peek());
            QName.enqueue(QName.dequeue());
        }
        for(int i=0;i<QName.size();i++)
        {
            System.out.print(QName.peek()+"  ");
            System.out.print(QScore.peek());
            System.out.println();
            QName.enqueue(QName.dequeue());
            QScore.enqueue(QScore.dequeue());
        }



    }

    public static void writer(StackMethod x) {
        StackMethod xTemp = new StackMethod(100);
        while (!x.isEmpty()) {
            System.out.print(x.peek() + " ");
            xTemp.push(x.pop());
        }
        while (!xTemp.isEmpty()) {
            x.push(xTemp.pop());
        }
    }

    public static void reverse(StackMethod x) {
        StackMethod SecondTemp = new StackMethod(100);
        StackMethod xSecondTemp = new StackMethod(100);
        while (!x.isEmpty()) {
            SecondTemp.push(x.pop());
        }
        while (!SecondTemp.isEmpty()) {
            xSecondTemp.push(SecondTemp.pop());
        }
        while (!xSecondTemp.isEmpty()) {
            x.push(xSecondTemp.pop());
        }
    }

    public static void display(StackMethod a, StackMethod b, int c, StackMethod d) {
        System.out.print("Word:  ");
        StackMethod SecondTemp = new StackMethod(100);

        writer(a);
        System.out.print("        Misses: ");

        reverse(b);
        writer(b);

        System.out.print("        Score: " + c + "        ");

        while (!d.isEmpty()) {
            SecondTemp.push(d.pop());
        }
        while (!SecondTemp.isEmpty()) {
            d.push(SecondTemp.peek());
            System.out.print(SecondTemp.pop());
        }
    }

    public static boolean isSame(char enteredLetter, StackMethod MissingStack) {
        boolean isSameLetter = false;
        StackMethod secondTemp = new StackMethod(100);
        for (int i = 0; i < MissingStack.size(); i++) {
            char x =(char) MissingStack.peek();
            if (enteredLetter == x) {
                isSameLetter = true;
                secondTemp.push(MissingStack.pop());
            } else {
                secondTemp.push(MissingStack.pop());
            }
        }
        while (!secondTemp.isEmpty()) {
            MissingStack.push(secondTemp.pop());
        }

        if (isSameLetter == true)
            return true;
        else
            return false;

    }
}
