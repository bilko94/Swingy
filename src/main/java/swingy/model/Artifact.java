package swingy.model;


public class Artifact {
    private String type;
    private String name;
    private int damage;
    private int hp;
    private int armour;
    private int rarity;

    public Artifact(String ItemName){
        String[] drop = Item.item().getItem(ItemName);;
        name = drop[0];
        damage = Integer.parseInt(drop[1]);
        armour = Integer.parseInt(drop[2]);
        hp = Integer.parseInt(drop[3]);
        rarity = Integer.parseInt(drop[4]);
        type = drop[5];
    }

    public Artifact(double strength){
        String[] drop = Item.item().getDrop(strength);
        name = drop[0];
        damage = Integer.parseInt(drop[1]);
        armour = Integer.parseInt(drop[2]);
        hp = Integer.parseInt(drop[3]);
        rarity = Integer.parseInt(drop[4]);
        type = drop[5];
    }

    public String getName(){
        return name;
    }
    public String getType(){ return type; }
    public int getRarity(){ return rarity; }
    public int getDamage(){
        return damage;
    }
    public int getArmour(){
        return armour;
    }
    public int getHp(){ return hp; }

}

class Item {
    private static Item item = new Item();
    private static String[][] items = {
            // name , attack , armour , hp , rarity , class
            // weapons
            {"meth","32","0","-20","3", "weapon"},
            {"Rusted Spear","8","0","0","1","weapon"},
            {"Bone Spike","8","0","0","1","weapon"},
            {"Ludens Echo","13","0","0","2","weapon"},
            {"Two String Bow","14","0","0","2","weapon"},
            {"Attack dog","25","0","0","3","weapon"},
            {"Susanoo","28","0","0","3","weapon"},
            {"RTX 2080","29","0","0","3","weapon"},
            {"Chair","30","0","0","3","weapon"},
            {"Rocket arrows","23","0","0","3","weapon"},
            {"LTT bottle","26","0","0","3","weapon"},
            {"Simp","30","0","0","3","weapon"},
            {"Scissor Blades","43","0","0","4","weapon"},
            {"Shurikens","40","0","0","4","weapon"},
            {"Excalibur","40","0","0","4","weapon"},
            {"CSGO karambit","40","0","0","4","weapon"},
            {"Hellblade","100","0","0","5","weapon"},
            {"Carona Gun","158","0","0","5","weapon"},
            {"Kirito Dual blades","256","0","0","5","weapon"},
            {"Deamon Dweller","250","-5","0","5","weapon"},
            // armour
            {"Scouts Uniform","0","5","0","1","armour"},
            {"Tattered Rangs","0","5","0","1","armour"},
            {"Bronze Chestplate","0","7","0","2","armour"},
            {"Red Pants","0","7","0","2","armour"},
            {"Chieftians Armour","0","15","0","3","armour"},
            {"Warriors ShoulderPad","0","15","0","3","armour"},
            {"Elon Musk Hoodie","0","20","0","4","armour"},
            {"B.R.A (battle ready armour)","0","30","0","4","armour"},
            {"Trumps Wall tatoo","0","80","0","5","armour"},
            {"Anime Waifu Sidekick","10","70","0","5","armour"},
            {"Incursio","40","100","-200","5","armour"},
            {"Nakagami","480","300","-600","5","armour"},
            {"Yummi","10","100","200","5","armour"},
            // helm
            {"Mushroom Cap","0","0","10","1","helm"},
            {"Ashes Cap","0","0","10","1","helm"},
            {"Methheads Beanie","0","0","20","2","helm"},
            {"Waifu Panties","0","0","20","2","helm"},
            {"Kights Helm","0","0","40","3","helm"},
            {"Anime Hairstyle","0","0","40","3","helm"},
            {"Nyan cat","0","0","80","4","helm"},
            {"Senpai Glasses","5","0","70","4","helm"},
            {"Naruto Headband","0","0","300","5","helm"},
            {"Rinnegun","40","0","150","5","helm"},
            {"Amaterasu","300","0","-50","5","helm"},
    };
    public static Item item(){
        return (item);
    }
    public String[] getDrop(double randomEnemy) {
        int rarity = rollRarity(randomEnemy);
        while (countWeapons(rarity) == 0){
            rarity = rollRarity(randomEnemy);
        }
        int weapon = rollItem(rarity);
        return items[weapon];
    }
    public String[] getItem(String name){
        int pos = 0;
        while (pos < items.length){
            if (items[pos][0].equals(name)){
                return items[pos];
            }
            pos++;
        }
//        System.out.println("Item not found");
        return null;
    }

    public int rollItem(int rarity) {
        int[] drops = new int[countWeapons(rarity)];
        int len = items.length - 1;
        int dropPos = 0;
        while (len >= 0){
            if (Integer.parseInt(items[len][4]) == rarity) {
                drops[dropPos] = len;
                dropPos++;
            }
            len--;
        }
        return drops[(int) (Math.random() * ((drops.length - 1) + 1))];
    }

    public int rollRarity(double chanceEnemy){
        // r5 1% chance
        // r4 5% chance
        // r3 14% chance
        // r2 30% chance
        // r1 50% chance
        double chance = Math.random();
        if (chance - (chanceEnemy/10) <= 0.01) return 5;
        if (chance - (chanceEnemy/5) <= 0.06) return 4;
        if (chance <= 0.2) return 3;
        if (chance <= 0.5) return 2;
        return 1;
    }

    public int countWeapons(int rarity){
        int len = items.length - 1;
        int weapons = 0;
        while (len >= 0){
            if (Integer.parseInt(items[len][4]) == rarity)
                weapons++;
            len--;
        }
        return weapons;
    }
}
