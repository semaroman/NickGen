import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindrome = new Thread(() -> {
            for (String text : texts) {
                if (palindrome(text) && !sameLetter(text)) {
                    incrementCounter(text.length());
                }
            }
        });
        palindrome.start();

        Thread sameLetter = new Thread(() -> {
            for (String text : texts) {
                if (sameLetter(text)) {
                    incrementCounter(text.length());
                }
            }
        });
        sameLetter.start();

        Thread alphabetOrder = new Thread(() -> {
            for (String text : texts) {
                if (alphabetOrder(text) && !sameLetter(text)) {
                    incrementCounter(text.length());
                }
            }
        });
        alphabetOrder.start();

        palindrome.join();
        sameLetter.join();
        alphabetOrder.join();

        System.out.println("Красивых слов с длиной 3: " + counter3 + " шт.");
        System.out.println("Красивых слов с длиной 4: " + counter4 + " шт.");
        System.out.println("Красивых слов с длиной 5: " + counter5 + " шт.");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static AtomicInteger counter3 = new AtomicInteger();
    public static AtomicInteger counter4 = new AtomicInteger();
    public static AtomicInteger counter5 = new AtomicInteger();

    public static boolean palindrome(String text) {
        return text.contentEquals(new StringBuilder(text).reverse());
    }

    public static boolean sameLetter(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean alphabetOrder(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i - 1))
                return false;
        }
        return true;
    }

    public static void incrementCounter(int textLength) {
        switch (textLength) {
            case (3): {
                counter3.getAndIncrement();
                break;
            }
            case (4): {
                counter4.getAndIncrement();
                break;
            }
            default: {
                counter5.getAndIncrement();
                break;
            }
        }
    }
}