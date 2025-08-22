package com.pocketraiders.model;

import java.util.ArrayList;

public class NullPod extends Pod{
    public NullPod() {
        super("Null", 160);
        this.description = "A relic of the cyber frontier, a pod stitched together from corrupted code, broken firewalls, " +
                "and lines of logic that should never have compiled. Its surface flickers with glitching symbols and streams " +
                "of binary, as if reality itself struggles to process its [REDACTED] prowess.";

        sortRaiders(initializeNullRaiders());
    }

    private ArrayList<Raider> initializeNullRaiders() {
        ArrayList<Raider> nullRaiders = new ArrayList<>();
        nullRaiders.add(new CommonRaider(11, "Bytebug", "/raider-images/staragazer.png"));
        nullRaiders.add(new CommonRaider(12, "Firewall", "/raider-images/staragazer.png"));
        nullRaiders.add(new CommonRaider(13, "Packet Tracer", "/raider-images/staragazer.png"));
        nullRaiders.add(new RareRaider(14, "Scripython", "/raider-images/staragazer.png"));
        nullRaiders.add(new RareRaider(15, "Rabbit", "/raider-images/staragazer.png"));
        nullRaiders.add(new RareRaider(16, "Seer", "/raider-images/staragazer.png"));
        nullRaiders.add(new LegendaryRaider(17, "Overclocke", "/raider-images/staragazer.png"));
        nullRaiders.add(new LegendaryRaider(18, "Cipherstorm", "/raider-images/staragazer.png"));
        nullRaiders.add(new MythicalRaider(19, "WannaCry1", "/raider-images/staragazer.png"));
        nullRaiders.add(new MythicalRaider(20, "[REDACTED]", "/raider-images/staragazer.png"));

        return nullRaiders;
    }
}
