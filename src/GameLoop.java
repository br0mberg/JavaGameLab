import java.util.ArrayList;
import java.util.Random;

public class GameLoop {
    public String[] weaponsNames = new String[] {"Ak47", "M4A4", "M4A1-S", "Kar98k", "SCAR-L", "Beryl-79"};
    public String[] mobsNames = new String[] {"Chervyak", "Jopik", "Krip", "Rubaka", "Sonic", "John Snoy", "SonicX", "Vonychka"};

    ArrayList<Mob> mobs = new ArrayList<>();
    ArrayList<Mob> equips = new ArrayList<>();
    ArrayList<Mob> items = new ArrayList<>();
    public GameLogic baseLogic = new GameLogic();
    public void fillArrayMobs(String name,  int healthPoints, int levelDefense, int attackPower, int expPoints, int level, int reward) {
        this.mobs.add(new Mob(name, healthPoints, levelDefense, attackPower, expPoints, level, reward));
    }

    public void getFightResult(Player player) {
        if(player.healthPoints <= 0) {
            System.out.printf("Main player %s is die by group of mobs", player.name);
        } else {
            System.out.printf("Main player %s is win and killed group of mobs", player.name);
        }
    }

    public void FightToTheDeath (Player player, Mob mob) {
        while (player.healthPoints > 0 && mob.healthPoints > 0) {
            baseLogic.fight(player, mob);

            if(mob.healthPoints <= 0) {
                player.inventor.putItemList(mob.dropLoot());
                player.inventor.putEquipmentList(mob.dropEquipLoot());
                player.rewardMoney(mob.rewardMoney);
                System.out.printf("%s был повержен в схватке\n", mob.name);
            } else {
                baseLogic.fight(mob, player);
            }
        }
    }
    public void start(){
        Random rand = new Random();

        Player Elena = new Player("Elena",rand.nextInt(1500), rand.nextInt(50), rand.nextInt(50),rand.nextInt(50), rand.nextInt(50));
        Weapon Kalash = new Weapon("Kalashnikov", rand.nextInt(50), rand.nextInt(50), rand.nextInt(50));

        Elena.putOneEquipment(Kalash);
        Elena.getOneWeaponToHands();

        for(int i = 0; i < rand.nextInt(9) + 1; ++i) {
            String randNameEquip = this.weaponsNames[rand.nextInt(6)];
            String randNameMob = this.mobsNames[rand.nextInt(8)];

            this.fillArrayMobs(randNameMob, rand.nextInt(500), rand.nextInt(50), rand.nextInt(50), rand.nextInt(50), rand.nextInt(50), rand.nextInt(50));
            Weapon newWeapon = new Weapon(randNameEquip, rand.nextInt(50), rand.nextInt(50), rand.nextInt(50));

            this.mobs.get(i).getEquipment(newWeapon);
            this.mobs.get(i).getOneWeaponToHands();
        }

        for(Mob temp: mobs) {
            this.FightToTheDeath(Elena, temp);
            if(Elena.healthPoints <= 0) {
                break;
            }
        }

        getFightResult(Elena);
    }
};
