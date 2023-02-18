package model;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private Mapper mapper = new Mapper();
    private FileOperation fileOperation;

    public Repository(FileOperation fileOperation) {
        this.fileOperation = fileOperation;
    }

    public List<Toy> getAllToys() {
        List<String> lines = fileOperation.readAllLines();
        List<Toy> toys = new ArrayList<>();
        for (String line : lines) {
            toys.add(mapper.map(line));
        }
        return toys;
    }

    private Integer getMaxId(List<Toy> toys) {
        int id = 0;
        for (Toy toy : toys) {
            if (Integer.parseInt(toy.getId()) > id) {
                id = Integer.parseInt(toy.getId());
            }
        }
        return id;
    }

    private void saveToys(List<Toy> toys) {
        List<String> lines = new ArrayList<>();
        for (Toy toy : toys) {
            lines.add(mapper.map(toy));
        }
        fileOperation.saveAllLines(lines);
    }

    private void saveToy(Toy toy, List<Toy> toys) {
        toys.add(toy);
        saveToys(toys);
    }

    public String createToy(Toy toy) {
        List<Toy> toys = getAllToys();
        int newId = getMaxId(toys);
        newId ++;
        String id = String.format("%d", newId);
        toy.setId(id);
        saveToy(toy, toys);
        return id;
    }

    private Toy findToy(String toyId, List<Toy> toys) {
        for (Toy toy : toys) {
            if (toy.getId().equals(toyId)) {
                return toy;
            }
        }
        throw new IllegalStateException("Toy not found!");
    }

    public void deleteToy(String toyId) {
        List<Toy> toys = getAllToys();
        toys.remove(findToy(toyId, toys));
        saveToys(toys);
    }

    public void updateToy(Toy toy) {
        deleteToy(toy.getId());
        List<Toy> toys = getAllToys();
        saveToy(toy, toys);
    }

    public Integer getSumProbability(List<Toy> toys) {
        int sum = 0;
        for (Toy toy : toys) {
            sum += toy.getProbability();
        }
        return sum;
    }
}