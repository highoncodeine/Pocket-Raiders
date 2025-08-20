package com.pocketraiders.model;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("this is pocket raiders");
        Scanner scanner = new Scanner(System.in);
        Player hadjj = new Player(1, "hadjj_dev", "123", 100000);

        ArrayList<Raider> availableRaiders = new ArrayList<>();
        availableRaiders.add(new CommonRaider(1, "Staragazer"));
        availableRaiders.add(new CommonRaider(2, "Pluto"));
        availableRaiders.add(new CommonRaider(3, "Hubble"));
        availableRaiders.add(new RareRaider(4, "Nebula"));
        availableRaiders.add(new RareRaider(5, "Orion"));
        availableRaiders.add(new RareRaider(6, "Radiar T."));
        availableRaiders.add(new LegendaryRaider(7, "Astral Projector"));
        availableRaiders.add(new LegendaryRaider(8, "Voidrunner"));
        availableRaiders.add(new LegendaryRaider(9, "Aetherion"));
        availableRaiders.add(new MythicalRaider(10, "Star"));
        availableRaiders.add(new MythicalRaider(11, "Aetherion Prime"));
        availableRaiders.add(new MythicalRaider(12, "Andromeda"));
        NovaPod nova = new NovaPod(availableRaiders);

        while(true) {
            System.out.println("Press Enter to Draw a Raider");
            String scan = scanner.nextLine();
            hadjj.drawRaider(nova);
            System.out.println(hadjj.getLumens());
        }
    }

}
