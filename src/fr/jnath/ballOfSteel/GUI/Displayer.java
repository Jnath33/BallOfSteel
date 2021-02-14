package fr.jnath.ballOfSteel.GUI;

import fr.jnath.Utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Displayer {
    private final String prefix, suffix, name;

    private final DisplayerType displayerType;
    private final int minCount, maxCount, defaultCount;
    private final double multiplier;
    private boolean isActivated;
    private final byte color;
    private final byte colorZero;
    private int value;
    private String valueStr = "modify";

    private final Material type;
    private final boolean isHead;
    private final String[] lore;
    private String headPlayerName;


    //value constructor

    public Displayer(String name, String prefix, String suffix, Material type, String[] lore, int minCount, int maxCount,  double multiplier, int defalutCount) {
        this.name=name;
        this.prefix=prefix;
        this.suffix = suffix;
        this.type = type;
        this.lore = lore;
        this.minCount=minCount;
        this.maxCount=maxCount;
        this.multiplier=multiplier;
        this.defaultCount =defalutCount;
        this.displayerType=DisplayerType.VALUE;
        isHead=false;
        this.color=0;
        this.colorZero=0;
        this.value=defalutCount;
        isActivated=false;
    }

    public Displayer(String name, String prefix, String suffix, Material type, String[] lore, int minCount, int maxCount,  double multiplier, int defalutCount, byte color, byte colorZero) {
        this.name=name;
        this.prefix=prefix;
        this.suffix = suffix;
        this.type = type;
        this.lore = lore;
        this.color=color;
        this.colorZero=colorZero;
        this.minCount=minCount;
        this.maxCount=maxCount;
        this.multiplier=multiplier;
        this.defaultCount =defalutCount;
        this.displayerType=DisplayerType.VALUE;
        isHead=false;
        this.value=defalutCount;
        isActivated=false;
    }

    public Displayer(String name, String prefix, String suffix, String[] lore, int minCount, int maxCount,  double multiplier, int defalutCount, String headPlayerName) {
        this.name=name;
        this.prefix=prefix;
        this.suffix = suffix;
        this.type = Material.SKULL_ITEM;
        this.lore = lore;
        this.headPlayerName=headPlayerName;
        isHead=true;
        this.minCount=minCount;
        this.maxCount=maxCount;
        this.multiplier=multiplier;
        this.defaultCount =defalutCount;
        this.displayerType=DisplayerType.VALUE;
        this.color=0;
        this.colorZero=0;
        this.value=defalutCount;
    }

    //switcher constructor

    public Displayer(String name, String prefix, String suffix, Material type, String[] lore, boolean isActivated) {
        this.name=name;
        this.prefix=prefix;
        this.suffix = suffix;
        this.type = type;
        this.lore = lore;
        this.minCount=0;
        this.maxCount=0;
        this.multiplier=0;
        this.defaultCount =0;
        this.displayerType=DisplayerType.SWITCHER;
        isHead=false;
        this.color=0;
        this.colorZero=0;
        this.value=0;
        this.isActivated=isActivated;
    }

    public Displayer(String name, String prefix, String suffix, Material type, String[] lore,boolean isActivated, byte color, byte colorZero) {
        this.name=name;
        this.prefix=prefix;
        this.suffix = suffix;
        this.type = type;
        this.lore = lore;
        this.color=color;
        this.colorZero=colorZero;
        this.minCount=0;
        this.maxCount=0;
        this.multiplier=0;
        this.defaultCount =0;
        this.displayerType=DisplayerType.SWITCHER;
        isHead=false;
        this.value=0;
        this.isActivated=isActivated;
    }

    public Displayer(String name, String prefix, String suffix, String[] lore, boolean isActivated, String headPlayerName) {
        this.name=name;
        this.prefix=prefix;
        this.suffix = suffix;
        this.type = Material.SKULL_ITEM;
        this.lore = lore;
        this.color=0;
        this.colorZero=0;
        this.minCount=0;
        this.maxCount=0;
        this.multiplier=0;
        this.defaultCount =0;
        this.displayerType=DisplayerType.SWITCHER;
        isHead=true;
        this.value=0;
        this.isActivated=isActivated;
    }

    //text constructor


    public Displayer(String name, String prefix, String suffix, Material type, String[] lore, String text) {
        this.name=name;
        this.prefix=prefix;
        this.suffix = suffix;
        this.type = type;
        this.lore = lore;
        this.minCount=0;
        this.maxCount=0;
        this.multiplier=0;
        this.defaultCount =0;
        this.displayerType=DisplayerType.DISPLAY_TEXT;
        isHead=false;
        this.color=0;
        this.colorZero=0;
        this.value=0;
        this.isActivated=false;
        this.valueStr=text;
    }

    public Displayer(String name, String prefix, String suffix, Material type, String[] lore,String text, byte color, byte colorZero) {
        this.name=name;
        this.prefix=prefix;
        this.suffix = suffix;
        this.type = type;
        this.lore = lore;
        this.color=color;
        this.colorZero=colorZero;
        this.minCount=0;
        this.maxCount=0;
        this.multiplier=0;
        this.defaultCount =0;
        this.displayerType=DisplayerType.DISPLAY_TEXT;
        isHead=false;
        this.value=0;
        this.isActivated=false;
        this.valueStr=text;
    }

    public Displayer(String name, String prefix, String suffix, String[] lore, String text, String headPlayerName) {
        this.name=name;
        this.prefix=prefix;
        this.suffix = suffix;
        this.type = Material.SKULL_ITEM;
        this.lore = lore;
        this.color=0;
        this.colorZero=0;
        this.minCount=0;
        this.maxCount=0;
        this.multiplier=0;
        this.defaultCount =0;
        this.displayerType=DisplayerType.DISPLAY_TEXT;
        isHead=true;
        this.value=0;
        this.isActivated=false;
        this.valueStr=text;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public int getDefaultCount() {
        return defaultCount;
    }

    public DisplayerType getDisplayerType() {
        return displayerType;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public byte getColor() {
        return color;
    }

    public byte getColorZero() {
        return colorZero;
    }

    public int getValue() {
        return value;
    }

    public String getHeadPlayerName() {
        return headPlayerName;
    }

    public final ItemStack toItemStack() {
        if(getDisplayerType()==DisplayerType.SWITCHER) {
            byte tmpColor = getColor();
            int tmpValue;
            String tmpText;
            if(isActivated()) {
                tmpValue = 1;
                tmpText = "§aactiver";
            }
            else {
                tmpValue = -1;
                tmpText = "§cdésactiver";
            }
            if (tmpValue <= 0) {
                tmpColor = getColorZero();
                if (tmpValue == 0) {
                    tmpValue = 1;
                }
            }
            if (isHead) {
                assert lore != null;
                return Utils.createItem(getPrefix() + tmpText + getSuffix(), getType(), tmpValue, getLore(), headPlayerName);
            }
            return Utils.createItem(getPrefix() + tmpText + getSuffix(), getType(), tmpValue, getLore(), tmpColor);
        }else if(getDisplayerType()==DisplayerType.VALUE){
            byte tmpColor = color;
            int tmpValue = value;
            if (value <= 0) {
                tmpColor = colorZero;
                if (value == 0) {
                    tmpValue = 1;
                }
            }
            if (isHead) {
                return Utils.createItem(getPrefix() + String.valueOf(((double) value) * multiplier) + getSuffix(), getType(), tmpValue, getLore(), getHeadPlayerName());
            }
            return Utils.createItem(getPrefix()+ String.valueOf(((double) value) * multiplier) + getSuffix(), getType(), tmpValue, getLore(), tmpColor);
        }else if(getDisplayerType()==DisplayerType.DISPLAY_TEXT){
            if (isHead) {
                return Utils.createItem(getPrefix() + valueStr + getSuffix(), getType(), 1, getLore(), getHeadPlayerName());
            }
            return Utils.createItem(getPrefix() + valueStr + getSuffix(), getType(), 1, getLore(), color);
        }
        return null;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }

    public String getSuffix() {
        return suffix;
    }

    public Material getType() {
        return type;
    }

    public String[] getLore() {
        return lore;
    }

    public int getMinCount() {
        return minCount;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public boolean toogleActevated(){
       isActivated=!isActivated;
       return isActivated;
    }

    public int addValue(int add) {
        if(!(displayerType==DisplayerType.VALUE))return 0;

        value = value + add;

        value = Math.max(value,minCount);
        value = Math.min(value,maxCount);

        return value;
    }

    public String getValueStr() {
        return valueStr;
    }
}
