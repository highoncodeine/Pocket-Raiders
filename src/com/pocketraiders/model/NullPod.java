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
        nullRaiders.add(new CommonRaider(11, "Bytebug", "Null", "/raider-images/bytebug.png"));
        nullRaiders.add(new CommonRaider(12, "Firewall", "Null", "/raider-images/firewall.png"));
        nullRaiders.add(new CommonRaider(13, "Packet Tracer", "Null", "/raider-images/packet_tracer.png"));
        nullRaiders.add(new RareRaider(14, "Scripython", "Null", "/raider-images/staragazer.png"));
        nullRaiders.add(new RareRaider(15, "Rabbit", "Null", "/raider-images/staragazer.png"));
        nullRaiders.add(new RareRaider(16, "Seer", "Null", "/raider-images/seer.png"));
        nullRaiders.add(new LegendaryRaider(17, "Overclocke", "Null", "/raider-images/staragazer.png"));
        nullRaiders.add(new LegendaryRaider(18, "Cipherstorm", "Null", "/raider-images/staragazer.png"));
        nullRaiders.add(new MythicalRaider(19, "WannaCry1", "Null", "/raider-images/staragazer.png"));
        nullRaiders.add(new MythicalRaider(20, "[REDACTED]", "Null", "/raider-images/staragazer.png"));

        return nullRaiders;
    }
}
