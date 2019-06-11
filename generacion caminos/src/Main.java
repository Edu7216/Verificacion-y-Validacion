import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/data.csv"));
        List<Pair> parejas = new ArrayList<>();
        while(sc.hasNextLine()) {
            parejas.add(new Pair(sc.nextLine()));
        }
        sc.close();
        int max = parejas.size();
        int index = 0;
        int caminoi = 0;
        List<List<Pair>> caminos = new ArrayList<>();
        caminos.add(new ArrayList<>());
        while(!parejas.isEmpty()) {
            if (caminos.get(caminoi).isEmpty()) {
                caminos.get(caminoi).add(parejas.get(index));
                parejas.remove(index);
                index = 0;
                continue;
            }
            if(caminos.get(caminoi).get(caminos.get(caminoi).size() - 1).getTo() == parejas.get(index).getFrom()) {
                caminos.get(caminoi).add(parejas.get(index));
                parejas.remove(index);
                index = 0;
                continue;
            }

            if(index >= parejas.size() - 1) {
                caminoi++;
                index = 0;
                caminos.add(new ArrayList<>());
                continue;
            }
            index ++;
        }
        int n = 1;
        for(List<Pair> c : caminos) {
            System.out.println("Camino " + n++);
            for(Pair p : c){
                System.out.println(p);
            }
        }

    }
}

class Pair{
    private int from;
    private int to;

    Pair(String input) {
        String[] values = input.split(",");
        from = Integer.parseInt(values[0]);
        to = Integer.parseInt(values[1]);
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
