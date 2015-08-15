package me.shreyasr.arrow.entity.attack;

import me.shreyasr.arrow.util.CartesianPosition;

public interface Attack {
    void update(double delta, CartesianPosition target);
}
