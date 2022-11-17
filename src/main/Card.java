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

    /**
     * A method that takes the place of putPOJO for the getCardsAtPosition command
     */
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

    /**
     * A method that takes the place of putPOJO for the getCardsInHand command
     */
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

    /**
     * A method that is overwritten in subclasses of the Card class
     */
    public int useEnvironmentCardAbility(final GameBoard gameBoard, final Player player,
                                         final int affectedRow, final ObjectNode node,
                                         final int handIdx, final ArrayNode output) {
        return 0;
    }

    /**
     * A method that is overwritten in subclasses of the Card class
     */
    public void useCardAbility(final CardWithCoords attacked) { }

    /**
     * mana setter
     * @param mana
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     * attackDamage setter
     * @param attackDamage
     */
    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     * health setter
     * @param health
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     * description setter
     * @param description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * colors setter
     * @param colors
     */
    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    /**
     * name setter
     * @param name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * isFrozen setter
     * @param isFrozen
     */
    public void setIsFrozen(final double isFrozen) {
        this.isFrozen = isFrozen;
    }

    /**
     * hasAttacked setter
     * @param hasAttacked
     */
    public void setHasAttacked(final int hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    /**
     * isEnvironment setter
     * @param isEnvironment
     */
    public void setIsEnvironment(final int isEnvironment) {
        this.isEnvironment = isEnvironment;
    }

    /**
     * isFrontRow setter
     * @param isFrontRow
     */
    public void setIsFrontRow(final int isFrontRow) {
        this.isFrontRow = isFrontRow;
    }

    /**
     * isBackRow setter
     * @param isBackRow
     */
    public void setIsBackRow(final int isBackRow) {
        this.isBackRow = isBackRow;
    }

    /**
     * isTank setter
     * @param isTank
     */
    public void setIsTank(final int isTank) {
        this.isTank = isTank;
    }

    /**
     * mana getter
     * @return
     */
    public int getMana() {
        return mana;
    }

    /**
     * attackDamage getter
     * @return
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * health getter
     * @return
     */
    public int getHealth() {
        return health;
    }

    /**
     * description getter
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * colors getter
     * @return
     */
    public ArrayList<String> getColors() {
        return colors;
    }

    /**
     * name getter
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * isFrozen getter
     * @return
     */
    public double getIsFrozen() {
        return isFrozen;
    }

    /**
     * hasAttacked getter
     * @return
     */
    public int getHasAttacked() {
        return hasAttacked;
    }

    /**
     * isEnvironment getter
     * @return
     */
    public int getIsEnvironment() {
        return isEnvironment;
    }

    /**
     * isFrontRow getter
     * @return
     */
    public int getIsFrontRow() {
        return isFrontRow;
    }

    /**
     * isBackRow getter
     * @return
     */
    public int getIsBackRow() {
        return isBackRow;
    }

    /**
     * isTank getter
     * @return
     */
    public int getIsTank() {
        return isTank;
    }
}
