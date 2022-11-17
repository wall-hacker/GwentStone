package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class Card {

    private int mana;
    private int attackDamage;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;
    @JsonIgnore
    private double isFrozen;
    @JsonIgnore
    private int hasAttacked;
    @JsonIgnore
    private int isEnvironment;
    @JsonIgnore
    private int isFrontRow;
    @JsonIgnore
    private int isBackRow;
    @JsonIgnore
    private int isTank;

    public Card() {

    }

    public Card(final CardInput cardInput) {
        if (cardInput == null) {
            return;
        }

        this.mana = cardInput.getMana();
        this.attackDamage = cardInput.getAttackDamage();
        this.health = cardInput.getHealth();
        this.description = cardInput.getDescription();
        this.colors = cardInput.getColors();
        this.name = cardInput.getName();

    }

    public ObjectNode outputCard(final ObjectNode node) {
        ObjectNode out = (new ObjectMapper()).createObjectNode();
        out.put("mana", this.getMana());
        out.put("attackDamage", this.getAttackDamage());
        out.put("health", this.getHealth());
        out.put("description", this.getDescription());
        ArrayNode colorsNode = out.putArray("colors");
        for (String color : this.getColors()) {
            colorsNode.add(color);
        }
        out.put("name", this.getName());
        node.set("output", out);
        return node;
    }

    public ObjectNode outputCard2() {
        ObjectNode node = (new ObjectMapper()).createObjectNode();
        node.put("mana", this.getMana());
        if (this.getIsEnvironment() == 0) {
            node.put("attackDamage", this.getAttackDamage());
            node.put("health", this.getHealth());
        }
        node.put("description", this.getDescription());
        ArrayNode colorsNode = node.putArray("colors");
        for (String color : this.getColors()) {
            colorsNode.add(color);
        }
        node.put("name", this.getName());
        return node;
    }

    public int useEnvironmentCardAbility(final GameBoard gameBoard, final Player player,
                                         final int affectedRow, final ObjectNode node,
                                         final int handIdx, final ArrayNode output) {
        return 0;
    }

    public void useCardAbility(final CardWithCoords attacked) { }

    public void setMana(final int mana) {
        this.mana = mana;
    }

    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public void setHealth(final int health) {
        this.health = health;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setIsFrozen(final double isFrozen) {
        this.isFrozen = isFrozen;
    }

    public void setHasAttacked(final int hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    public void setIsEnvironment(final int isEnvironment) {
        this.isEnvironment = isEnvironment;
    }

    public void setIsFrontRow(final int isFrontRow) {
        this.isFrontRow = isFrontRow;
    }

    public void setIsBackRow(final int isBackRow) {
        this.isBackRow = isBackRow;
    }

    public void setIsTank(final int isTank) {
        this.isTank = isTank;
    }

    public int getMana() {
        return mana;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public int getHealth() {
        return health;
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

    public double getIsFrozen() {
        return isFrozen;
    }

    public int getHasAttacked() {
        return hasAttacked;
    }

    public int getIsEnvironment() {
        return isEnvironment;
    }

    public int getIsFrontRow() {
        return isFrontRow;
    }

    public int getIsBackRow() {
        return isBackRow;
    }
    
    public int getIsTank() {
        return isTank;
    }
}
