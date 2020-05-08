package model;

import java.util.LinkedList;
import java.util.Random;

public class LottoNumber {
    private int[] numbers;
    Random random = new Random();

    private void initialNumbers(int number) {
        numbers = new int[number];
        LinkedList<Integer> numberSet = new LinkedList<>();
        for (int i = 0; i < 30; i++) {
            numberSet.add(i + 1);
        }
        int count = 30;
        for (int i = 0; i < number; i++) {
            int rand = random.nextInt(count) + 1;
            this.numbers[i] = numberSet.get(rand - 1);
            numberSet.remove(rand - 1);
            count--;
        }
    }

    public int[] getNumbers(int number) {
        initialNumbers(number);
        return numbers;
    }
}
