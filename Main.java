import java.util.Scanner;

class BankersAlgorithm {
    int n; // Numarul de procese
    int m; // Numarul de resurse
    int[][] need;
    int[][] max;
    int[][] alloc;
    int[] avail;
    int[] safeSequence;

 void initializeValues() {
    // Date predefinite actualizate
    n = 4; // Schimbat numărul de procese
    m = 3; // Numărul de resurse 

    //  matrice alocare
    alloc = new int[][] { { 1, 2, 3 }, 
                          { 2, 1, 1 }, 
                          { 3, 1, 2 }, 
                          { 1, 3, 1 }}; 

    // maxim necesar
    max = new int[][] { { 6, 3, 5 }, 
                        { 4, 2, 2 }, 
                        { 8, 3, 4 }, 
                        { 3, 4, 3 }}; 

    // resurse disponibile
    avail = new int[] { 4, 3, 3 };

    //  matricea need
    need = new int[n][m];

    // Calcul nevoii
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            need[i][j] = max[i][j] - alloc[i][j];
        }
    }

    safeSequence = new int[n];
}
       void readValues() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti numarul de procese:");
        n = scanner.nextInt();

        System.out.println("Introduceti numarul de resurse:");
        m = scanner.nextInt();

        need = new int[n][m];
        max = new int[n][m];
        alloc = new int[n][m];
        avail = new int[m];
        safeSequence = new int[n];

        System.out.println("Introduceti matricea de alocare:");
        for (int i = 0; i < n; i++) {
            System.out.println("Proces " + i + ":");
            for (int j = 0; j < m; j++) {
                System.out.print("Alocare pentru resursa " + j + ": ");
                alloc[i][j] = scanner.nextInt();
            }
        }

        System.out.println("Introduceti matricea maximului necesar:");
        for (int i = 0; i < n; i++) {
            System.out.println("Proces " + i + ":");
            for (int j = 0; j < m; j++) {
                System.out.print("Maxim necesar pentru resursa " + j + ": ");
                max[i][j] = scanner.nextInt();
            }
        }

        System.out.println("Introduceti resursele disponibile:");
        for (int j = 0; j < m; j++) {
            System.out.print("Resursa disponibila " + j + ": ");
            avail[j] = scanner.nextInt();
        }
    }


    void calculateNeed() {
        System.out.println("Calculul matricei de nevoi:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                need[i][j] = max[i][j] - alloc[i][j];
                System.out.println("Need[" + i + "][" + j + "] = Max[" + i + "][" + j + "] - Alloc[" + i + "][" + j + "] = " + max[i][j] + " - " + alloc[i][j] + " = " + need[i][j]);
            }
        }
        System.out.println();
    }

    void isSafe() {
    boolean[] finished = new boolean[n];
    int[] work = avail.clone();
    int sequenceIndex = 0;
    System.out.println("Inceperea algoritmului pentru verificarea starii sigure:");
    

    while (true) {
        boolean progressMade = false;

        for (int i = 0; i < n; i++) {
            if (!finished[i]) {
                boolean canAllocate = true;

                for (int j = 0; j < m; j++) {
                    if (need[i][j] > work[j]) {
                        canAllocate = false;
                        break;
                    }
                }

                if (canAllocate) {
                    System.out.print("Procesul P" + i + " poate fi alocat cu resursele disponibile: ");
                    for (int j = 0; j < m; j++) {
                        System.out.print(work[j] + " ");
                    }
                    System.out.println();

                    for (int j = 0; j < m; j++) {
                        work[j] += alloc[i][j];
                    }
                    System.out.print("Resursele disponibile dupa alocarea lui P" + i + ": ");
                    for (int j = 0; j < m; j++) {
                        System.out.print(work[j] + " ");
                    }
                    System.out.println();

                    safeSequence[sequenceIndex] = i;
                    sequenceIndex++;
                    finished[i] = true;
                    progressMade = true;
                }
            }
        }

        if (!progressMade) {
            break;
        }
    }

    boolean allFinished = true;
    for (boolean f : finished) {
        if (!f) {
            allFinished = false;
            break;
        }
    }

    if (allFinished) {
        System.out.println("Sistemul se afla intr-o stare sigura.");
        System.out.print("Secventa sigura: ");
        for (int i = 0; i < sequenceIndex; i++) {
            System.out.print("P" + safeSequence[i] + " ");
        }
        System.out.println();
    } else {
        System.out.println("Sistemul nu se afla intr-o stare sigura.");
    }
}
}

public class Main {
    public static void main(String[] args) {
        BankersAlgorithm algorithm = new BankersAlgorithm();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Alegeti modul de introducere a datelor: 1 pentru automat, 2 pentru manual:");
        int choice = scanner.nextInt();

        if (choice == 1) {
            algorithm.initializeValues();
        } else {
            algorithm.readValues();
        }

        algorithm.calculateNeed();
        algorithm.isSafe();
    }
}
