/*
 * Course: CS-1021-051
 * Fall 2018
 * Lab 9 - Final Project
 * Name: Brendan Ecker
 * Created: 08/20/18
 */
package sample;

import javafx.scene.paint.Color;

@FunctionalInterface
public interface Transformable {
    Color apply(int y, Color c);
}

