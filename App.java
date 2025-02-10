import java.util.Scanner;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        ArrayList<String> choiceArr = new ArrayList<String>();
        choiceArr.add("gpt-4o-mini");
        choiceArr.add("gpt-4o");
        choiceArr.add("o1-mini");


        String model = "gpt-4o-mini";
        Scanner scanner = new Scanner(System.in);
        System.out.println("What model would you like to use?");
        System.out.println(choiceArr.get(0) + ": 1");
        System.out.println(choiceArr.get(1) + ": 2");
        System.out.println(choiceArr.get(2) + ": 3");
        int modelChoice = scanner.nextInt();
        if (modelChoice == 1) {
            model = choiceArr.get(0);
        } else if(modelChoice == 2) {
            model = choiceArr.get(1);
        } else if(modelChoice == 3) {
            model = choiceArr.get(2);
        }

        GUI gui = new GUI(model);
        gui.repaint();


        scanner.close();
    }
}
