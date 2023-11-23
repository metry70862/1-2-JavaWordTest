import java.io.File;
import java.io.FileNotFoundException;


import java.util.*;

public class VocManager {
    String username;
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();


    public VocManager(String username) {
        super();
        this.username = username;
    }

    ArrayList<Word> wordArrayList = new ArrayList<>();

    void addWord(Word w) {
        wordArrayList.add(w);
    }

    void makeVoc(String filename) {
        try (Scanner file = new Scanner(new File(filename))) {
            while (file.hasNextLine()) {
                String str = file.nextLine();
                String[] result = str.split("\t");
                this.addWord(new Word(result[0].trim(), result[1].trim()));
            }
            System.out.println(this.username + "의 단어장이 생성되었습니다.");
            menu();
        } catch (FileNotFoundException e) {
            System.out.println("파일이름을 확인해 주세요.");
        }
    }

    private void menu() {
        int choice;
        while (true) {
            System.out.println("1)주관식 퀴즈, 2)객관식 퀴즈, 3)오답노트, 4)단어검색, 5)종료");
            System.out.print("메뉴를 선택하세요 : ");
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("입력한 값을 확인하세요.");
                scanner.nextLine();
                continue;
            }
            scanner.nextLine();
            switch (choice) {
                case 1 -> shortAnswerTest();
                case 2 -> multipleChoiceTest();
                case 3 -> reviewNote();
                case 4 -> vocSearch();
                case 5 -> sysExit();
                default -> System.out.println("입력한 값을 확인하세요.");
            }
        }
    }

    private void shortAnswerTest() {
        ArrayList<Word> wordArrayListClone = (ArrayList<Word>) wordArrayList.clone();
        Collections.shuffle(wordArrayListClone);
        wordArrayListClone = deDuplication(wordArrayListClone);
        int count = 0;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            System.out.println("------ 주관식 퀴즈 " + (i + 1) + "번 ------");
            String kor = wordArrayListClone.get(i).kor;
            System.out.println("\"" + kor + "\"의 뜻을 가진 영어 단어는 무엇일까요?");
            System.out.println("답을 입력하세요.");
            String wordAnswer = scanner.nextLine();
            wordAnswer.trim();
            Iterator<Word> iterator = wordArrayList.iterator();
            boolean isAnswerCorrect = false;
            while (iterator.hasNext()) {
                Word word = iterator.next();
                if (word.eng.equals(wordAnswer)) {
                    isAnswerCorrect = true;
                    break;
                }
            }
            if (isAnswerCorrect) {
                System.out.print("정답입니다.\n");
                increaseCount(wordArrayList, kor, true);
                count++;
            } else {
                ArrayList<Word> answerList;
                answerList = findSameWord(wordArrayList, wordArrayListClone.get(i));
                System.out.print("틀렸습니다. 정답은 ");
                for (Word word : answerList) {
                    System.out.print(word.eng + "    ");
                }
                System.out.println("입니다.");
                increaseCount(wordArrayList, kor, false);
            }

        }
        long finishTime = System.currentTimeMillis();
        long elapsedTime = (finishTime - startTime) / 1000;
        System.out.println(this.username + "님" + count + "개 맞추셨고, 총" + elapsedTime + "초 소요되셨습니다.");
    }

    private void increaseCount(ArrayList<Word> wordArrayList, String kor, boolean isAnswerCorrect) {
        for (Word compareWord : wordArrayList) {
            if (kor.equals(compareWord.kor)) {
                compareWord.increasePresentCount();
                if (!isAnswerCorrect) {
                    compareWord.increaseIncorrectAnswerCount();
                }
            }
        }
    }

    private ArrayList<Word> deDuplication(ArrayList<Word> wordArrayList) {
        ArrayList<Word> uniqueWords = new ArrayList<>();
        for (Word word : wordArrayList) {
            if (!containsWord(uniqueWords, word)) {
                uniqueWords.add(word);
            }
        }
        return uniqueWords;
    }

    private boolean containsWord(ArrayList<Word> list, Word word){
        for (Word w : list){
            if(w.kor.equals(word.kor)){
                return true;
            }
        }
        return false;
    }

    private ArrayList<Word>findSameWord(ArrayList<Word> list, Word word){
        ArrayList<Word> answerList = new ArrayList<>();
        for(Word w : list){
            if(w.kor.equals(word.kor)){
                answerList.add(w);
            }
        }
        return answerList;
    }
    private void multipleChoiceTest(){
        int count = 0;
        long startTime = System.currentTimeMillis();
        ArrayList<Word> wordArrayListClone = (ArrayList<Word>) wordArrayList.clone();
        uniqueRandomNumbers(wordArrayListClone);
        for(int i = 0; i <10; i++){
            System.out.println("------ 객관식 퀴즈 " + (i + 1) + "번 ------");
            random.setSeed(System.currentTimeMillis());
            int correctIndex = random.nextInt(3);
            int randomIndex = uniqueRandomNumbers(wordArrayListClone).get(i);
            Word answerWord = wordArrayListClone.get(randomIndex);
            ArrayList<Word> choices = makeProblemChoice(wordArrayListClone, answerWord, correctIndex);
            System.out.println(answerWord.eng+"의 뜻은 무엇일까요?");
            for(int j = 0; j < 4; j++){
                System.out.println((j+1)+") " + choices.get(j).kor);
            }
            System.out.println("답을 입력하세요.");
            int wordAnswer;
            try{
                wordAnswer = scanner.nextInt();

            }catch (InputMismatchException e){
                scanner.nextLine();
                wordAnswer = -1;
            }
            if(wordAnswer == correctIndex+1){
                System.out.println("정답입니다.");
                answerWord.increasePresentCount();
                count++;
            }else{
                System.out.println("틀렸습니다. 정답은"+(correctIndex+1)+"번 입니다.");
                answerWord.increasePresentCount();
                answerWord.increaseIncorrectAnswerCount();
            }

        }
        long finishTime = System.currentTimeMillis();
        long elapsedTime = (finishTime - startTime) / 1000;
        System.out.println(this.username + "님" + count + "개 맞추셨고, 총" + elapsedTime + "초 소요되셨습니다.");
    }

    private ArrayList<Word> makeProblemChoice(ArrayList<Word> arrayList, Word word, int correctIndex) {
        arrayList = deDuplication(arrayList);
        arrayList.remove(word);
        Collections.shuffle(arrayList);
        ArrayList<Word> choices = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            if (i != correctIndex) {
                choices.add(arrayList.get(i));
            }
        }
        choices.add(correctIndex, word);
        return choices;
    }

    private ArrayList<Integer> uniqueRandomNumbers(ArrayList<Word> arrayList){
        ArrayList<Integer> uniqueRandomNumbers = new ArrayList<>();
        for(int i = 0 ; i<arrayList.size(); i++){
            uniqueRandomNumbers.add(i);
        }
        Collections.shuffle(uniqueRandomNumbers);
        return uniqueRandomNumbers;
    }
    private void reviewNote(){
        ArrayList<Word> wrongWordList = new ArrayList<>();
        makeWrongWordList(wordArrayList, wrongWordList);
        if(wrongWordList.isEmpty()){
            System.out.println("틀린 문제가 없습니다.");
            return;
        }
        for (Word word: wrongWordList) {
            System.out.println(word);
            System.out.println("출제횟수: "+word.presentCount+"   오답횟수: "+word.incorrectAnswerCount);
            System.out.println("-".repeat(20));
        }
    }
    private void makeWrongWordList(ArrayList<Word> list, ArrayList<Word> newlist){
        for(Word word : list){
            if(word.incorrectAnswerCount >=1 ){
                newlist.add(word);
            }
        }
        newlist.sort(Comparator.comparingInt(o -> (o.incorrectAnswerCount)*-1));
    }

    private void vocSearch() {
        System.out.println("--------단어 검색 메뉴---------");
        System.out.print("검색하려는 영어 단어를 입력하세요: ");
        String userInput = scanner.nextLine();
        System.out.println();
        userInput = userInput.trim();
        Iterator<Word> iterator = wordArrayList.iterator();
        boolean wordFound = false;

        while (iterator.hasNext()) {
            Word word = iterator.next();
            if (word != null && word.eng.equals(userInput)) {
                System.out.println(word + "  ");
                System.out.println("출제횟수: "+word.presentCount+"   오답횟수: "+word.incorrectAnswerCount);
                System.out.println("-".repeat(28));
                wordFound = true;
            }
        }

        if (!wordFound) {
            System.out.println("단어장에 등록되지 않은 단어입니다.");
            System.out.println("-".repeat(28));
        }

    }
    private static void sysExit() {
        System.out.println("프로그램을 종료합니다");//사용자에게 종료 메시지 출력
        scanner.close();// 스캐너 닫기(자원 해제)
        System.exit(0);// 프로그램을 종료하는 메소드 호촐
    }
}
