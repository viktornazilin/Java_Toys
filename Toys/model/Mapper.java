package model;

public class Mapper {
    public String map(Toy toy) {
        return String.format("%s;%s;%d", toy.getId(), toy.getName(), toy.getProbability());
    }

    public Toy map(String line) {
        String[] lines = line.split(";");
        return new Toy(lines[0], lines[1], Byte.valueOf(lines[2]));
    }
}