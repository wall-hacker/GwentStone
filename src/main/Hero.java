package main;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

import java.util.ArrayList;

public class Hero{

    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private int health;

    private int hasAttacked;

    public Hero(CardInput cardInput) {
        if (cardInput == null) {
            return;
        }

        this.mana = cardInput.getMana();
        this.description = cardInput.getDescription();
        this.colors = cardInput.getColors();
        this.name = cardInput.getName();
        setHealth(30);

    }

    public ObjectNode outputHero(ObjectNode node) {
        ObjectNode out = (new ObjectMapper()).createObjectNode();
        out.put("mana", this.getMana());
        out.put("description", this.getDescription());
        ArrayNode colorsNode = out.putArray("colors");
        for (String color : this.getColors()) {
            colorsNode.add(color);
        }
        out.put("name", this.getName());
        out.put("health", this.getHealth());
        node.set("output", out);
        return node;
    }

    public int useHeroAbility(GameBoard gameBoard, int affectedRow, Player player) {
        return 0;
    }

    public int getMana() {
        return mana;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getHasAttacked() {
        return hasAttacked;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setHasAttacked(int hasAttacked) {
        this.hasAttacked = hasAttacked;
    }
}
