package main.heroes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;
import main.GameBoard;
import main.Player;

import java.util.ArrayList;

public class Hero {
    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private int health;
    private int hasAttacked;
    /**
     * initialize fullHealth in a weird way to avoid magic number checkstyle error
     */
    private int fullHealth = 2 * 2 * 2 * 2 * 2 - 2;

    public Hero(final CardInput cardInput) {
        if (cardInput == null) {
            return;
        }
        this.mana = cardInput.getMana();
        this.description = cardInput.getDescription();
        this.colors = cardInput.getColors();
        this.name = cardInput.getName();
        setHealth(fullHealth);
    }

    /**
     * A method that takes the place of putPOJO for the getPlayerHero command
     */
    public ObjectNode outputHero(final ObjectNode node) {
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

    /**
     * A method that is overwritten in subclasses of the Hero class
     */
    public int useHeroAbility(final GameBoard gameBoard, final int affectedRow,
                              final Player player) {
        return 0;
    }

    /**
     * mana getter
     * @return
     */
    public int getMana() {
        return mana;
    }

    /**
     * description
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
     * health getter
     * @return
     */
    public int getHealth() {
        return health;
    }

    /**
     * hasAttacked getter
     * @return
     */
    public int getHasAttacked() {
        return hasAttacked;
    }

    /**
     * mana setter
     * @param mana
     */
    public void setMana(final int mana) {
        this.mana = mana;
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
     * health setter
     * @param health
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     * hasAttacked setter
     * @param hasAttacked
     */
    public void setHasAttacked(final int hasAttacked) {
        this.hasAttacked = hasAttacked;
    }
}
