package fr.jnath.ballOfSteel.GUI;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Button {
    private final Consumer<Player> fonctionRightClick, fonctionLeftClick, fonctionShiftRightClick, fonctionShiftLeftClick;
    private final List<ClickType> buttonTypes = new ArrayList<>();
    private final String name;

    public Button(Consumer<Player> fonctionRightClick, Consumer<Player> fonctionLeftClick, Consumer<Player> fonctionShiftRightClick, Consumer<Player> fonctionShiftLeftClick, String name) {
        if(fonctionRightClick!=null)buttonTypes.add(ClickType.RIGHT);
        if(fonctionLeftClick!=null)buttonTypes.add(ClickType.LEFT);
        if(fonctionShiftRightClick!=null)buttonTypes.add(ClickType.SHIFT_RIGHT);
        if(fonctionShiftLeftClick!=null)buttonTypes.add(ClickType.SHIFT_LEFT);
        this.fonctionRightClick = fonctionRightClick;
        this.fonctionLeftClick = fonctionLeftClick;
        this.fonctionShiftRightClick = fonctionShiftRightClick;
        this.fonctionShiftLeftClick = fonctionShiftLeftClick;
        this.name = name;
    }

    public void use(ClickType click, Player player){
        if(buttonTypes.contains(click)){
            switch (click){
                case LEFT:
                    fonctionLeftClick.accept(player);
                    break;
                case SHIFT_LEFT:
                    fonctionShiftLeftClick.accept(player);
                    break;
                case RIGHT:
                    fonctionRightClick.accept(player);
                    break;
                case SHIFT_RIGHT:
                    fonctionShiftRightClick.accept(player);
                    break;
                default:
                    break;
            }
        }
    }
}
