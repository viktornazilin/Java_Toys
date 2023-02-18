package controller;

import model.Repository;
import model.Toy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {
    private final Repository repository;

    public Controller(Repository repository) {
        this.repository = repository;
    }

    public void validateToyData(Toy toy) {
        if (toy.getName().isEmpty()) {
            throw new IllegalStateException("Fields are empty!");
        }
    }

    public void saveToy(Toy toy) {
        validateToyData(toy);
        repository.createToy(toy);
    }

    public Toy readToy(String toyId) throws Exception {
        List<Toy> toys = repository.getAllToys();
        for (Toy toy : toys) {
            if (toy.getId().equals(toyId)) {
                return toy;
            }
        }
        throw new Exception("Toy not found!");
    }

    public List<Toy> readToys() {
        return repository.getAllToys();
    }

    public void editToy(Toy toy) {
        validateToyData(toy);
        repository.updateToy(toy);
    }

    public void deleteToy(String toyId) {
        repository.deleteToy(toyId);
    }

    public Integer raffleToy() {
        Integer result = 0;
        List<Toy> toys = repository.getAllToys();
        Integer sumProbability = repository.getSumProbability(toys);
        Map<String, Integer[]> map = new HashMap<>();
        Integer startPosition = 1;
        for (Toy toy : toys) {
            Integer[] position = new Integer[2];
            position[0] = startPosition;
            position[1] = startPosition + toy.getProbability();
            map.put(toy.getId(), position);
            startPosition = position[1] + 1;
        }

        System.out.println(map);
        return result;
    }


}